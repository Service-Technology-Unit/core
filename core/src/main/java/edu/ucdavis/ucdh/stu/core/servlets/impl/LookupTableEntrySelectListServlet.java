package edu.ucdavis.ucdh.stu.core.servlets.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import edu.ucdavis.ucdh.stu.core.beans.LookupTable;
import edu.ucdavis.ucdh.stu.core.beans.LookupTableProperty;
import edu.ucdavis.ucdh.stu.core.manager.LookupTableManager;
import edu.ucdavis.ucdh.stu.core.servlets.SelectListServletBase;

/**
 * <p>LookupTableEntry data picker servlet.</p>
 */
public class LookupTableEntrySelectListServlet extends SelectListServletBase {
	private static final long serialVersionUID = 1;
	private static final String TABLE_REQ_ATTR_KEY = "select.list.lookup.table";
	private String servletPath = "/optlist/";
	private LookupTableManager lookupTableManager = null;
	private Map<String,LookupTable> tableDefinition = new HashMap<String,LookupTable>();

	/**
	 * <p>This method returns the current map of data fields.</p>
	 *
	 * @param req the <code>HttpServletRequest</code> object
	 * @return the current map of data fields
	 */
	protected Map<String,String> getCurrentDataFields(HttpServletRequest req) {
		Map<String,String> dataFields = new HashMap<String,String>();

		dataFields.put("id", "id");
		dataFields.put("description", "description");
		String id = getIdFromUrl(req, servletPath);
		if (id.indexOf(".xml") != -1) {
			id = id.substring(0, id.indexOf(".xml"));
		}
		LookupTable thisTable = null;
		if (id.indexOf("/") != -1) {
			// split id into context and table name
			String[] parts = id.split("/");
			String context = parts[0];
			String tableName = parts[1];
			thisTable = getTableDefinition(context, tableName);
		}
		req.setAttribute(TABLE_REQ_ATTR_KEY, thisTable);
		if (thisTable != null && thisTable.getProperties() != null && thisTable.getProperties().size() > 0) {
			Iterator<LookupTableProperty> i = thisTable.getProperties().iterator();
			while (i.hasNext()) {
				LookupTableProperty thisProperty = i.next();
				dataFields.put(thisProperty.getName(), thisProperty.getName());
			}
		}

		return dataFields;
	}

	/**
	 * <p>This method returns the table definition for the specified table.</p>
	 *
	 * @param context the context of the requested lookupTable
	 * @param tableName the name of the requested lookupTable
	 * @return the table definition for the specified table
	 */
	private LookupTable getTableDefinition(String context, String tableName) {
		String key = context + "/" + tableName;
		if (!tableDefinition.containsKey(key)) {
			tableDefinition.put(key, lookupTableManager.findByContextAndTableName(context, tableName));
		}
		return tableDefinition.get(key);
	}

	/**
	 * <p>This method returns any additional qualifiers for the key to the query cache.</p>
	 *
	 * @param req the <code>HttpServletRequest</code> object
	 * @return the cache key qualifier
	 */
	protected String getCacheKeyQualifier(HttpServletRequest req) {
		String tableId = "";
		LookupTable thisTable = (LookupTable) req.getAttribute(TABLE_REQ_ATTR_KEY);
		if (thisTable != null) {
			tableId = thisTable.getContext() + ":" + thisTable.getTableName();
		}
		return ":" + tableId;
	}

	/**
	 * <p>This method creates the SQL statement.</p>
	 *
	 * @param startsWith the "starts with" query parameter
	 * @param contains the "contains" query parameter
	 * @param orderBy the sort order
	 * @return the SQL statement
	 */
	protected String getQueryStatement(String startsWith, String contains, String orderBy) {
		return null;
	}

	/**
	 * <p>This method creates the SQL statement.</p>
	 *
	 * @param req the <code>HttpServletRequest</code> object
	 * @param startsWith the "starts with" query parameter
	 * @param contains the "contains" query parameter
	 * @param orderBy the sort order
	 * @return the SQL statement
	 */
	protected String getQueryStatement(HttpServletRequest req, String startsWith, String contains, String orderBy) {
		StringBuffer buffer = new StringBuffer();

		String context = "";
		String tableName = "";
		LookupTable thisTable = (LookupTable) req.getAttribute(TABLE_REQ_ATTR_KEY);
		if (thisTable != null) {
			context = thisTable.getContext();
			tableName = thisTable.getTableName();
		}
		Map<String,LookupTableProperty> propertyMap = new HashMap<String,LookupTableProperty>();
		buffer.append(" select\n");
		buffer.append("    entryId as id,\n");
		buffer.append("    description");
		if (thisTable != null && thisTable.getProperties() != null && thisTable.getProperties().size() > 0) {
			Iterator<LookupTableProperty> i = thisTable.getProperties().iterator();
			while (i.hasNext()) {
				LookupTableProperty thisProperty = i.next();
				propertyMap.put(thisProperty.getName(), thisProperty);
				buffer.append(",\n    property");
				if (thisProperty.getSequence() < 10) {
					buffer.append("0");
				}
				buffer.append(thisProperty.getSequence());
				buffer.append(" as ");
				buffer.append(thisProperty.getName());
			}
		}
		buffer.append("\n from\n");
		buffer.append("    lookuptableentry\n");
		buffer.append(" where\n");
		buffer.append("    context = '");
		buffer.append(context);
		buffer.append("' and\n");
		buffer.append("    tableName = '");
		buffer.append(tableName);
		buffer.append("' and\n");
		if ("description".equalsIgnoreCase(orderBy)) {
			// sort and select by description
			if (StringUtils.isNotEmpty(contains)) {
				buffer.append("    lower(description) like '%" + contains.toLowerCase() + "%'\n");
			} else {
				buffer.append("    lower(description) like '" + startsWith.toLowerCase() + "%'\n");
			}
			buffer.append(" order by\n");
			buffer.append("    description, entryId\n");
		} else if (StringUtils.isNotEmpty(orderBy) && propertyMap.containsKey(orderBy)) {
			// sort and select by specified property
			LookupTableProperty thisProperty = (LookupTableProperty) propertyMap.get(orderBy);
			String tagName = "property" + thisProperty.getSequence();
			if (thisProperty.getSequence() < 10) {
				tagName = "property0" + thisProperty.getSequence();
			}
			if (StringUtils.isNotEmpty(contains)) {
				buffer.append("    lower(" + tagName + ") like '%" + contains.toLowerCase() + "%'\n");
			} else {
				buffer.append("    lower(" + tagName + ") like '" + startsWith.toLowerCase() + "%'\n");
			}
			buffer.append(" order by\n");
			buffer.append("    " + tagName + ", entryId\n");
		} else {
			// sort and select by id
			if (StringUtils.isNotEmpty(contains)) {
				buffer.append("    lower(entryId) like '%" + contains.toLowerCase() + "%'\n");
			} else {
				buffer.append("    lower(entryId) like '" + startsWith.toLowerCase() + "%' \n");
			}
			buffer.append(" order by\n");
			buffer.append("    entryId\n");
		}

		return buffer.toString();
	}

	public LookupTableManager getLookupTableManager() {
		return lookupTableManager;
	}

	public void setLookupTableManager(LookupTableManager lookupTableManager) {
		this.lookupTableManager = lookupTableManager;
	}

	public String getServletPath() {
		return servletPath;
	}

	public void setServletPath(String servletPath) {
		this.servletPath = servletPath;
	}
}