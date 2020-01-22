package edu.ucdavis.ucdh.stu.core.servlets.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.betwixt.io.BeanReader;
import org.apache.commons.betwixt.io.read.BeanCreationList;
import org.apache.commons.betwixt.strategy.ConvertUtilsObjectStringConverter;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;

import edu.ucdavis.ucdh.stu.core.beans.LookupTable;
import edu.ucdavis.ucdh.stu.core.beans.LookupTableProperty;
import edu.ucdavis.ucdh.stu.core.manager.LookupTableManager;
import edu.ucdavis.ucdh.stu.core.servlets.RestServletBase;
import edu.ucdavis.ucdh.stu.core.utils.BetwixtTool;

/**
 * <p>This servlet handles LookupTables.</p>
 */
public class LookupTableServlet extends RestServletBase {
	private static final long serialVersionUID = 1;
	private LookupTableManager lookupTableManager;

	/**
	 * <p>The Servlet "doGet()" method.</p>
	 *
	 * @param req the <code>HttpServletRequest</code> object
	 * @param res the <code>HttpServletResponse</code> object
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String id = getIdFromUrl(req, "/table/");

		// log request, if enabled
		if (log.isDebugEnabled()) {
			String message = "Processing GET request; id=" + id;
			if (StringUtils.isNotEmpty(req.getQueryString())) {
				message += "; query string=" + req.getQueryString();
			}
			log.debug(message);
		}

		if (StringUtils.isNotEmpty(id)) {
			if (id.indexOf("/") != -1) {
				// split id into context and table name
				String[] parts = id.split("/");
				String context = parts[0];
				String tableName = parts[1];
				// send requested lookupTable
				sendLookupTable(context, tableName, req, res);
			} else {
				// id is context only; send all tables for this context
				sendQueryResults(id, req, res);
			}
		} else {
			// send query results
			sendQueryResults(null, req, res);
		}
	}

	/**
	 * <p>The Servlet "doPost()" method.</p>
	 * 
	 * @param req the <code>HttpServletRequest</code> object
	 * @param res the <code>HttpServletResponse</code> object
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String id = getIdFromUrl(req, "/table/");

		// log request, if enabled
		if (log.isDebugEnabled()) {
			String message = "Processing POST request; id=" + id;
			if (StringUtils.isNotEmpty(req.getQueryString())) {
				message += "; query string=" + req.getQueryString();
			}
			log.debug(message);
		}

		if (StringUtils.isNotEmpty(id)) {
			if (id.indexOf("/") != -1) {
				// split id into context and table name
				String[] parts = id.split("/");
				String context = parts[0];
				String tableName = parts[1];
				// update lookupTable
				updateLookupTable(context, tableName, req, res);
			} else {
				// send error
				sendError(req, res, 405, "Method Not Allowed. Only the \"GET\" method is allowed for this URL (/table/<context> with no table name).");
			}
		} else {
			// send error
			sendError(req, res, 405, "Method Not Allowed. Only the \"GET\" method is allowed for this URL (/table with no context or table name).");
		}
	}

	/**
	 * <p>The Servlet "doPut()" method.</p>
	 *
	 * @param req the <code>HttpServletRequest</code> object
	 * @param res the <code>HttpServletResponse</code> object
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String id = getIdFromUrl(req, "/table/");

		// log request, if enabled
		if (log.isDebugEnabled()) {
			log.debug("Processing PUT request; id=" + id);
		}

		if (StringUtils.isNotEmpty(id)) {
			if (id.indexOf("/") != -1) {
				// split id into context and table name
				String[] parts = id.split("/");
				String context = parts[0];
				String tableName = parts[1];
				// build LookupTable from user input
				LookupTable lookupTable = buildLookupTableFromInput(context, tableName, req, res);
				if (lookupTable != null) {
					// insert LookupTable
					lookupTableManager.save(lookupTable);
					// confirm insert
					sendLookupTable(context, tableName, req, res);
				} else {
					// send error
					sendError(req, res, 400, "Nothing to PUT.");
				}
			} else {
				// send error
				sendError(req, res, 405, "Method Not Allowed. Only the \"GET\" method is allowed for this URL (/table/<context> with no table name).");
			}
		} else {
			// send error
			sendError(req, res, 405, "Method Not Allowed. Only the \"GET\" method is allowed for this URL (/table with no context or table name).");
		}
	}

	/**
	 * <p>The Servlet "doDelete()" method.</p>
	 *
	 * @param req the <code>HttpServletRequest</code> object
	 * @param res the <code>HttpServletResponse</code> object
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String id = getIdFromUrl(req, "/table/");

		// log request, if enabled
		if (log.isDebugEnabled()) {
			log.debug("Processing DELETE request; id=" + id);
		}

		if (StringUtils.isNotEmpty(id)) {
			if (id.indexOf("/") != -1) {
				// split id into context and table name
				String[] parts = id.split("/");
				String context = parts[0];
				String tableName = parts[1];
				// delete LookupTable
				deleteLookupTable(context, tableName, req, res);
			} else {
				// send error
				sendError(req, res, 405, "Method Not Allowed. Only the \"GET\" method is allowed for this URL (/table/<context> with no table name).");
			}
		} else {
			// send error
			sendError(req, res, 405, "Method Not Allowed. Only the \"GET\" method is allowed for this URL (/table with no context or table name).");
		}
	}

	/**
	 * <p>Handles a get request for a single LookupTable.</p>
	 *
	 * @param context the context of the requested lookupTable
	 * @param tableName the name of the requested lookupTable
	 * @param req the <code>HttpServletRequest</code> object
	 * @param res the <code>HttpServletResponse</code> object
	 */
	private void sendLookupTable(String context, String tableName, HttpServletRequest req, HttpServletResponse res) throws IOException {
		LookupTable lookupTable = null;

		try {
			lookupTable = lookupTableManager.findByContextAndTableName(context, tableName);
			PrintWriter pw = res.getWriter();
			if (pw != null) {
				pw.print(BetwixtTool.toXml(lookupTable, "/core/xsl/lookupTable.xsl"));
			} else {
				sendError(req, res, 500, "There was a technical error while attempting to access this resource. Details of this error have been logged on the server.");
			}
		} catch (DataAccessException dae) {
			sendError(req, res, 404, "The requested resource was not found on this server. If you entered the URL manually please check your spelling and try again.");
		} catch (Exception e) {
			sendError(req, res, 500, "There was a technical error while attempting to access this resource. Details of this error have been logged on the server.", e);
		}
	}

	/**
	 * <p>Handles a query request for a list of LookupTables.</p>
	 *
	 * <p>Currently, no query parameters are accepted or evaluated. All
	 * lookup tables defined in the database are returned unless there
	 * is a context provided, in which case all lookup tables defined in
	 * the database for that context are returned.</p>
	 *
	 * @param contexts the context of the requested lookupTable
	 * @param req the <code>HttpServletRequest</code> object
	 * @param res the <code>HttpServletResponse</code> object
	 */
	private void sendQueryResults(String context, HttpServletRequest req, HttpServletResponse res) throws IOException {
		List<LookupTable> list = null;
		
		if (StringUtils.isNotEmpty(context)) {
			list = lookupTableManager.findByContext(context);
		} else {
			list = lookupTableManager.findAll();
		}

		if (list != null && list.size() > 0) {
			PrintWriter pw = res.getWriter();
			pw.println(lookupTableListToXml(list));
		} else {
			sendError(req, res, 404, "No items matching your search criteria were found on this server.");
		}
	}

	/**
	 * <p>Handles a post request for a single LookupTable.</p>
	 *
	 * @param context the context of the requested lookupTable
	 * @param tableName the name of the requested lookupTable
	 * @param req the <code>HttpServletRequest</code> object
	 * @param res the <code>HttpServletResponse</code> object
	 */
	private void updateLookupTable(String context, String tableName, HttpServletRequest req, HttpServletResponse res) throws IOException {
		// validate required parameters
		String validationErrors = validateUserInput(req, res);
		if (StringUtils.isNotEmpty(validationErrors)) {
			// send error
			sendError(req, res, 406, validationErrors);
		} else {
			LookupTable lookupTable = lookupTableManager.findByContextAndTableName(context, tableName);
			if (lookupTable == null) {
				// send error
				sendError(req, res, 404, "The requested resource was not found on this server. If you entered the URL manually please check your spelling and try again.");
			} else {
				String userId = req.getRemoteUser();
				Date rightNow = new Date();
				lookupTable.setDisplayName(req.getParameter("displayName"));
				lookupTable.setDescription(req.getParameter("description"));
				lookupTable.setLastUpdate(rightNow);
				lookupTable.setLastUpdateBy(userId);
				List<LookupTableProperty> existingProperties = new ArrayList<LookupTableProperty>(lookupTable.getProperties());
				lookupTable.getProperties().removeAll(existingProperties);
				boolean done = false;
				for (int i=0; i<20 && !done; i++) {
					String name = req.getParameter("name[" + i + "]");
					if (StringUtils.isNotEmpty(name)) {
						LookupTableProperty thisProperty = new LookupTableProperty();
						thisProperty.setCreationDate(rightNow);
						thisProperty.setCreatedBy(userId);
						if (i < existingProperties.size()) {
							thisProperty = (LookupTableProperty) existingProperties.get(i);
						}
						thisProperty.setName(name);
						if (StringUtils.isNotEmpty(req.getParameter("type[" + i + "]"))) {
							thisProperty.setType(req.getParameter("type[" + i + "]"));
						} else {
							thisProperty.setType(null);
						}
						if (StringUtils.isNotEmpty(req.getParameter("size[" + i + "]"))) {
							thisProperty.setSize(req.getParameter("size[" + i + "]"));
						} else {
							thisProperty.setSize(null);
						}
						if (StringUtils.isNotEmpty(req.getParameter("label[" + i + "]"))) {
							thisProperty.setLabel(req.getParameter("label[" + i + "]"));
						} else {
							thisProperty.setLabel(null);
						}
						if (StringUtils.isNotEmpty(req.getParameter("colHeading[" + i + "]"))) {
							thisProperty.setColHeading(req.getParameter("colHeading[" + i + "]"));
						} else {
							thisProperty.setColHeading(null);
						}
						if ("true".equalsIgnoreCase(req.getParameter("inputRequired[" + i + "]"))) {
							thisProperty.setInputRequired(true);
						} else {
							thisProperty.setInputRequired(false);
						}
						if ("true".equalsIgnoreCase(req.getParameter("displayOnList[" + i + "]"))) {
							thisProperty.setDisplayOnList(true);
						} else {
							thisProperty.setDisplayOnList(false);
						}
						if (StringUtils.isNotEmpty(req.getParameter("source[" + i + "]"))) {
							thisProperty.setSource(req.getParameter("source[" + i + "]"));
						} else {
							thisProperty.setSource(null);
						}
						if (StringUtils.isNotEmpty(req.getParameter("notes[" + i + "]"))) {
							thisProperty.setNotes(req.getParameter("notes[" + i + "]"));
						} else {
							thisProperty.setNotes(null);
						}
						thisProperty.setLastUpdate(rightNow);
						thisProperty.setLastUpdateBy(userId);
						lookupTable.addProperty(thisProperty);
					} else {
						done = true;
					}
				}
				lookupTableManager.save(lookupTable);
				// send requested lookupTable
				sendLookupTable(context, tableName, req, res);
			}
		}
	}

	/**
	 * <p>Handles a delete request for a single LookupTable.</p>
	 *
	 * @param context the context of the requested lookupTable
	 * @param tableName the name of the requested lookupTable
	 * @param req the <code>HttpServletRequest</code> object
	 * @param res the <code>HttpServletResponse</code> object
	 */
	private void deleteLookupTable(String context, String tableName, HttpServletRequest req, HttpServletResponse res) throws IOException {
		LookupTable lookupTable = null;

		try {
			lookupTable = lookupTableManager.findByContextAndTableName(context, tableName);
			lookupTableManager.delete(lookupTable);
			PrintWriter pw = res.getWriter();
			if (pw != null) {
				pw.print("<message>Look-up table \"" + context + "/" + tableName + "\" deleted.</message>");
			} else {
				sendError(req, res, 500, "There was a technical error while attempting to access this resource. Details of this error have been logged on the server.");
			}
		} catch (DataAccessException dae) {
			sendError(req, res, 404, "The requested resource was not found on this server. If you entered the URL manually please check your spelling and try again.");
		} catch (Exception e) {
			sendError(req, res, 500, "There was a technical error while attempting to access this resource. Details of this error have been logged on the server.", e);
		}
	}

	/**
	 * <p>Builds the LookupTable from user's input.</p>
	 *
	 * @param context the context of the requested lookupTable
	 * @param tableName the name of the requested lookupTable
	 * @param req the <code>HttpServletRequest</code> object
	 * @param res the <code>HttpServletResponse</code> object
	 * @return the constructed LookupTable
	 */
	private LookupTable buildLookupTableFromInput(String context, String tableName, HttpServletRequest req, HttpServletResponse res) throws IOException {
		LookupTable lookupTable = null;

		BeanCreationList chain = BeanCreationList.createStandardChain();
		BeanReader reader = new BeanReader();
		try {
			reader.registerBeanClass("lookup:table", LookupTable.class);
			reader.registerBeanClass("lookup:table/properties/property", LookupTableProperty.class);
			reader.getReadConfiguration().setBeanCreationChain(chain);
			reader.getBindingConfiguration().setObjectStringConverter(new ConvertUtilsObjectStringConverter());
			lookupTable = (LookupTable) reader.parse(req.getInputStream());
			lookupTable.setContext(context);
			lookupTable.setTableName(tableName);
		} catch (Exception e) {
			// log error, if enabled
			if (log.isErrorEnabled()) {
				log.error("Error processing XML input: " + e.toString(), e);
			}
			// send error
			sendError(req, res, 500, "Internal server error. Details of this error can be found in the server's log file(s).");
		}

		// build LookupTable from input
		if (lookupTable != null) {
			Date rightNow = new Date();
			// get authenticated user
			String userId = req.getRemoteUser();
			lookupTable.setCreationDate(rightNow);
			lookupTable.setCreatedBy(userId);
			lookupTable.setLastUpdate(rightNow);
			lookupTable.setLastUpdateBy(userId);
			if (lookupTable.getProperties() != null && lookupTable.getProperties().size() > 0) {
				for (int i=0; i<lookupTable.getProperties().size(); i++) {
					LookupTableProperty thisProperty = (LookupTableProperty) lookupTable.getProperties().get(i);
					thisProperty.setTableId(lookupTable.getId());
					thisProperty.setSequence(i);
					thisProperty.setCreationDate(rightNow);
					thisProperty.setCreatedBy(userId);
					thisProperty.setLastUpdate(rightNow);
					thisProperty.setLastUpdateBy(userId);
				}
			}
		}

		return lookupTable;
	}

	/**
	 * <p>Validates the user input.</p>
	 *
	 * @param req the <code>HttpServletRequest</code> object
	 * @param res the <code>HttpServletResponse</code> object
	 * @return validation errors, if any
	 */
	private String validateUserInput(HttpServletRequest req, HttpServletResponse res) {
		return null;
	}

	/**
	 * <p>Converts a list of LookupTables to XML.</p>
	 * 
	 * @param list the list of LookupTables
	 * @return the list of LookupTables in XML format
	 */
	private static String lookupTableListToXml(List<LookupTable> list) {
		StringBuffer buffer = new StringBuffer();

		buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		buffer.append("<lookup:tables xmlns:lookup=\"http://www.ucdmc.ucdavis.edu/table\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n");
		if (list != null && list.size() > 0) {
			Iterator<LookupTable> i = list.iterator();
			while (i.hasNext()) {
				LookupTable lookupTable = i.next();
				buffer.append("  <table context=\"");
				buffer.append(lookupTable.getContext());
				buffer.append("\" tableName=\"");
				buffer.append(lookupTable.getTableName());
				buffer.append("\" xlink:href=\"/core/table/");
				buffer.append(lookupTable.getContext());
				buffer.append("/");
				buffer.append(lookupTable.getTableName());
				buffer.append("\"/>\n");
			}
		}
		buffer.append("</lookup:tables>");

		return buffer.toString();
	}

	/**
	 * <p>Sets the LookupTableManager.</p>
	 *
	 * @param lookupTableManager the LookupTableManager
	 */
	public void setLookupTableManager(LookupTableManager lookupTableManager) {
		this.lookupTableManager = lookupTableManager;
	}
}
