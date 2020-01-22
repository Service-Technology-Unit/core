package edu.ucdavis.ucdh.stu.core.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ObjectRetrievalFailureException;

import edu.ucdavis.ucdh.stu.core.batch.SpringBatchJob;
import edu.ucdavis.ucdh.stu.core.beans.NoticeTemplate;
import edu.ucdavis.ucdh.stu.core.manager.NoticeTemplateManager;
import edu.ucdavis.ucdh.stu.core.service.impl.LookupTableService;

/**
 * <p>Generates various Java modules based on run parameters.</p>
 */
public class CodeGenerator implements SpringBatchJob {
	private Log log = LogFactory.getLog(getClass().getName());
	private LookupTableService lookupTableService = null;
	private NoticeTemplateManager noticeTemplateManager = null;
	private DataSource dataSource = null;
	private String objectName = null;
	private String tableName = null;
	private String targetDirectory = null;
	private String packageRoot = null;
	private String defaultTemplateContext = null;
	private String sql2javaRefTableName = null;
	private String javaTypeRefTableName = null;
	private String componentRefTableName = null;
	private String instanceName = null;
	private Map<String,Map<String,Object>> sql2java = null;
	private Map<String,Map<String,Object>> javaType = null;
	private Map<String,Map<String,Object>> component = null;
	private List<Map<String,String>> fields = new ArrayList<Map<String,String>>();
	private List<Map<String,Object>> components = new ArrayList<Map<String,Object>>();
	private List<String> imports = null;
	private int componentCt = 0;

	/**
	 * <p>Main run method.</p>
	 * 
	 * @throws SQLException 
	 */
	public List<BatchJobServiceStatistic> run(String[] args, int batchJobInstanceId) throws SQLException {
		log.info("Code generation starting with the following parameters:");
		log.info(" ");
		log.info("   Object name:      " + objectName);
		instanceName = objectName.substring(0,1).toLowerCase() + objectName.substring(1);
		log.info("   Instance name:    " + instanceName);
		log.info("   Table name:       " + tableName);
		log.info("   Target directory: " + targetDirectory);
		log.info("   Package root:     " + packageRoot);
		log.info("   Lookup Tables:");
		log.info("      SQL to Java:   " + sql2javaRefTableName);
		log.info("      Java Type:     " + javaTypeRefTableName);
		log.info("      Component:     " + componentRefTableName);
		log.info(" ");

		// load look-up tables
		if (log.isDebugEnabled()) {
			log.debug("Loading lookup tables ...");
		}
		sql2java = lookupTableService.getTable(sql2javaRefTableName);
		javaType = lookupTableService.getTable(javaTypeRefTableName);
		component = lookupTableService.getTable(componentRefTableName);
		if (log.isDebugEnabled()) {
			log.debug("Lookup tables loaded.");
		}

		// verify directories
		verifyDirectories();

		// gather table metadata
		if (log.isDebugEnabled()) {
			log.debug("Connecting to database to get column information");
		}
		Connection con = dataSource.getConnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select * from " + tableName);
		ResultSetMetaData metaData = rs.getMetaData();
		for (int i=1; i<=metaData.getColumnCount(); i++) {
			Map<String,String> thisColumn = new HashMap<String,String>();
			thisColumn.put("name", metaData.getColumnName(i));
			thisColumn.put("length", metaData.getColumnDisplaySize(i) + "");
			thisColumn.put("typeName", metaData.getColumnTypeName(i));
			thisColumn.put("type", metaData.getColumnType(i) + "");
			thisColumn.put("className", metaData.getColumnClassName(i));
			thisColumn.put("label", metaData.getColumnLabel(i));
			Map<String,Object> javaTypeData = null;
			Map<String,Object> javaTypeRef = sql2java.get(thisColumn.get("typeName").toLowerCase());
			if (javaTypeRef != null) {
				javaTypeData = (Map<String,Object>) javaType.get(javaTypeRef.get("javaType").toString());
			}
			if (javaTypeData != null) {
				thisColumn.put("javaType", javaTypeData.get("description").toString());
				thisColumn.put("defaultValue", javaTypeData.get("defaultValue").toString());
				thisColumn.put("import", javaTypeData.get("import").toString());
			} else {
				log.debug("Undefined column type: " + thisColumn.get("typeName").toLowerCase() + "; defaulting to String.");
				thisColumn.put("javaType", "String");
				thisColumn.put("defaultValue", "null");
				thisColumn.put("import", "");
			}
			thisColumn.put("nameCapped", metaData.getColumnName(i).substring(0,1).toUpperCase() + metaData.getColumnName(i).substring(1));
			if (log.isDebugEnabled()) {
				log.debug("Adding column: " + thisColumn);
			}
			fields.add(thisColumn);
		}

		// consolidate import list
		Map<String,String> importMap = new TreeMap<String,String>();
		Iterator<Map<String,String>> i = fields.iterator();
		while (i.hasNext()) {
			Map<String,String> thisField = i.next();
			if (!StringUtils.isEmpty(thisField.get("import"))) {
				importMap.put(thisField.get("import"), thisField.get("import"));
			}
		}
		imports = new ArrayList<String>();
		Iterator<String> j = importMap.keySet().iterator();
		while (j.hasNext()) {
			imports.add(j.next());
		}

		// generate components
		j = component.keySet().iterator();
		while (j.hasNext()) {
			generateComponent(component.get(j.next()));
		}
		

		log.info(" ");
		log.info("Code generation complete.");
		log.info(" ");
		log.info("  Database fields discovered: " + fields.size());
		log.info("  Components generated: " + componentCt);

		return null;
	}

	/**
	 * <p>Verifies all of the directories that will be used in this process.</p>
	 */
	private void generateComponent(Map<String,Object> componentDetails) {
		String componentName = ((String) componentDetails.get("name")).replace("${bean}", objectName);
		if (log.isDebugEnabled()) {
			log.debug("Generating component " + componentName);
		}
		String template = getTemplate((String) componentDetails.get("template"));
		if (StringUtils.isEmpty(template)) {
			log.info("Template unavailable for component " + componentName + "; skipping component.");
		} else {
			String path = packageRoot.replace(".", "/") + "/" + ((String) componentDetails.get("package")).replace(".", "/");
			String realPath = targetDirectory + "/" + componentDetails.get("location") + "/" + path;
			String fileName = realPath + "/" + componentName;
			componentDetails.put("componentName", componentName);
			componentDetails.put("objectName", objectName);
			componentDetails.put("instanceName", instanceName);
			componentDetails.put("tableName", tableName);
			componentDetails.put("targetDirectory", targetDirectory);
			componentDetails.put("packageRoot", packageRoot);
			componentDetails.put("path", path);
			componentDetails.put("realPath", realPath);
			componentDetails.put("fileName", fileName);
			componentDetails.put("imports", imports);
			componentDetails.put("fields", fields);
			componentDetails.put("currentDate", new CurrentDate());
			components.add(componentDetails);

			// generate code
			if (log.isDebugEnabled()) {
				log.debug("Invoking Velocity service for component " + componentName);
			}
			String generatedComponent = SimpleVelocityService.evaluate(template, componentDetails);
			if (log.isDebugEnabled()) {
				log.debug("Velocity service results:\n\n" + generatedComponent);
			}

			// save component to file system
			if (log.isDebugEnabled()) {
				log.debug("Saving component in " + fileName);
			}
			if (!verifyDirectory(realPath)) {
				log.error("Someone needs to fix this!!");
			}
			File outputFile = new File(fileName);
			try {
				BufferedWriter out = new BufferedWriter(new FileWriter(outputFile));
				out.write(generatedComponent);
				out.close();
				componentCt++;
				if (log.isDebugEnabled()) {
					log.debug("Component " + componentName + " saved.");
				}
			} catch (IOException e) {
				log.error("Exception encountered while writing generated code to " + fileName, e);
			}
		}
	}

	/**
	 * <p>Returns the requested template.</p>
	 * 
	 * @param templateId the id of the requested template
	 * @return the requested template
	 */
	private String getTemplate(String templateId) {
		String template = null;

		String context = defaultTemplateContext;
		String templateName = templateId;
		if (templateId.indexOf("/") != -1) {
			String parts[] = templateId.split("/");
			context = parts[0];
			templateName = parts[1];
		}
		if (log.isDebugEnabled()) {
			log.debug("Retrieving template; context: " + context + "; template: " + templateName);
		}
		try {
			NoticeTemplate noticeTemplate = noticeTemplateManager.findByContextAndName(context, templateName);
			if (noticeTemplate != null) {
				if (log.isDebugEnabled()) {
					log.debug("Template retrieved.");
				}
				template = noticeTemplate.getBody();
			}
		} catch (ObjectRetrievalFailureException e) {
			log.info("Template not found: " + templateId);
		}

		return template;
	}

	/**
	 * <p>Verifies all of the directories that will be used in this process.</p>
	 */
	private void verifyDirectories() {
		String currentDirectory = targetDirectory;
		if (currentDirectory.endsWith("/")) {
			currentDirectory = currentDirectory.substring(0, currentDirectory.length() - 1);
		}
		if (verifyDirectory(currentDirectory)) {
			if (verifyDirectory(currentDirectory + "/main")) {
				if (verifyDirectory(currentDirectory + "/test")) {
					verifyPackageDirectories(currentDirectory + "/main/java");
					verifyPackageDirectories(currentDirectory + "/main/resources");
					verifyPackageDirectories(currentDirectory + "/test/java");
					verifyPackageDirectories(currentDirectory + "/test/resources");
				}
			}
		}
	}

	/**
	 * <p>Verifies all of the directories related to the root package.</p>
	 */
	private void verifyPackageDirectories(String rootDirectory) {
		String thisDirectory = rootDirectory;

		if (verifyDirectory(thisDirectory)) {
			if (StringUtils.isEmpty(packageRoot)) {
				if (log.isDebugEnabled()) {
					log.debug("No package root -- using default package.");
				}
			} else {
				if (log.isDebugEnabled()) {
					log.debug("Processing package root: " + packageRoot);
				}
				if (packageRoot.indexOf(".") < 0) {
					verifyDirectory(thisDirectory + "/" + packageRoot);
				} else {
					String[] packageNameComponents = packageRoot.split("\\.");
					for (int i=0; i<packageNameComponents.length; i++) {
						thisDirectory += "/" + packageNameComponents[i];
						verifyDirectory(thisDirectory);
					}
				}
			}
		}
	}

	/**
	 * <p>Verifies the specific directory.</p>
	 * 
	 * @param thisDirectory the directory to verify
	 */
	private boolean verifyDirectory(String thisDirectory) {
		boolean verified = false;

		if (log.isDebugEnabled()) {
			log.debug("Verifying directory: " + thisDirectory);
		}
		File file = new File(thisDirectory);
		if (file.exists()) {
			if (file.isDirectory()) {
				verified = true;
				if (log.isDebugEnabled()) {
					log.debug("Directory verified.");
				}
			}
		} else {
			if (log.isDebugEnabled()) {
				log.debug("Directory does not exist ... attempting to create.");
			}
			if (file.mkdir()) {
				verified = true;
				if (log.isDebugEnabled()) {
					log.debug("Directory created.");
				}
			} else {
				log.error("Unable to locate or create directory: " + thisDirectory);
			}
		}

		return verified;
	}

	/**
	 * @param lookupTableService the lookupTableService to set
	 */
	public void setLookupTableService(LookupTableService lookupTableService) {
		this.lookupTableService = lookupTableService;
	}

	/**
	 * @param noticeTemplateManager the noticeTemplateManager to set
	 */
	public void setNoticeTemplateManager(NoticeTemplateManager noticeTemplateManager) {
		this.noticeTemplateManager = noticeTemplateManager;
	}

	/**
	 * @param dataSource the dataSource to set
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * @param defaultTemplateContext the defaultTemplateContext to set
	 */
	public void setDefaultTemplateContext(String defaultTemplateContext) {
		this.defaultTemplateContext = defaultTemplateContext;
	}

	/**
	 * @param objectName the objectName to set
	 */
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @param targetDirectory the targetDirectory to set
	 */
	public void setTargetDirectory(String targetDirectory) {
		this.targetDirectory = targetDirectory;
	}

	/**
	 * @param packageRoot the packageRoot to set
	 */
	public void setPackageRoot(String packageRoot) {
		this.packageRoot = packageRoot;
	}

	/**
	 * @param sql2javaRefTableName the sql2javaRefTableName to set
	 */
	public void setSql2javaRefTableName(String sql2javaRefTableName) {
		this.sql2javaRefTableName = sql2javaRefTableName;
	}

	/**
	 * @param javaTypeRefTableName the javaTypeRefTableName to set
	 */
	public void setJavaTypeRefTableName(String javaTypeRefTableName) {
		this.javaTypeRefTableName = javaTypeRefTableName;
	}

	/**
	 * @param componentRefTableName the componentRefTableName to set
	 */
	public void setComponentRefTableName(String componentRefTableName) {
		this.componentRefTableName = componentRefTableName;
	}
}
