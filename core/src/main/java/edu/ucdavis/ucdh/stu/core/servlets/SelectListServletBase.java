package edu.ucdavis.ucdh.stu.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * <p>This abstract class is the base code for select list servlets.</p>
 */
public abstract class SelectListServletBase extends RestServletBase {
	private static final long serialVersionUID = 1;
	private static final String FIELD_MAP_REQ_ATTR_KEY = "select.list.data.field.map";
	private String contextKey = "";
	private String dataSourceName = "";
	private String defaultStartsWith = "";
	private String defaultContains = "";
	private String defaultOrderBy = "id";
	private Map<String,String> dataFields = new HashMap<String,String>();

	/**
	 * <p>The Servlet "doGet()" method.</p>
	 *
	 * @param req the <code>HttpServletRequest</code> object
	 * @param res the <code>HttpServletResponse</code> object
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		StringBuffer buffer = new StringBuffer();

		req.setAttribute(FIELD_MAP_REQ_ATTR_KEY, getCurrentDataFields(req));
		buffer.append("<?xml version=\"1.0\"?>\n");
		buffer.append("<options size=\"");
		List options = getOptions(req);
		if (options != null) {
			buffer.append(options.size());
			buffer.append("\">\n");
			buffer.append(formatOptions(req, options));
		} else {
			buffer.append("0\">\n");
		}
		buffer.append("</options>\n");

		PrintWriter pw = res.getWriter();
		pw.println(buffer);
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
		res.sendError(405, "Method Not Allowed. Use the \"GET\" method for this URL");
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
		res.sendError(405, "Method Not Allowed. Use the \"GET\" method for this URL");
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
		res.sendError(405, "Method Not Allowed. Use the \"GET\" method for this URL");
	}

	/**
	 * <p>This method returns the current map of data fields.</p>
	 *
	 * @param req the <code>HttpServletRequest</code> object
	 * @return the current map of data fields
	 */
	@SuppressWarnings("rawtypes")
	protected Map getCurrentDataFields(HttpServletRequest req) {
		return dataFields;
	}

	/**
	 * <p>This method formats the options.</p>
	 *
	 * @param req the <code>HttpServletRequest</code> object
	 * @param options the <code>List</code> of options
	 * @return the formatted options
	 */
	@SuppressWarnings("rawtypes")
	protected String formatOptions(HttpServletRequest req, List options) {
		StringBuffer buffer = new StringBuffer();

		int startIndex = 0;
		int count = 0;
		String start = req.getParameter("start");
		if (start != null && start.length() > 0) {
			startIndex = Integer.parseInt(start.trim());
		}
		String countString = req.getParameter("count");
		if (countString != null && countString.length() > 0) {
			count = Integer.parseInt(countString.trim());
		}
		if (count < 1) {
			count = 10;
		}
		if (startIndex > 0) {
			startIndex = startIndex - 1;
		}
		int endIndex = startIndex + count;
		if (endIndex > options.size()) {
			endIndex = options.size();
		}

		Map fields = (Map) req.getAttribute(FIELD_MAP_REQ_ATTR_KEY);
		for (int x=startIndex; x<endIndex; x++) {
			int index = x + 1;
			Map thisItem = (Map) options.get(x);
			buffer.append(" <option index=\"");
			buffer.append(index);
			buffer.append("\">\n");
	   		Iterator i = fields.keySet().iterator();
	   		while (i.hasNext()) {
	   			String fieldName = (String) i.next();
				buffer.append("  <field name=\"");
				buffer.append(fieldName);
				buffer.append("\">");
				if (thisItem.get(fieldName) != null) {
					buffer.append(thisItem.get(fieldName));
				}
				buffer.append("</field>\n");
	   		}
			buffer.append(" </option>\n");
		}

		return buffer.toString();
	}

	/**
	 * <p>This method retrieves the requested options from the session.</p>
	 *
	 * @param req the <code>HttpServletRequest</code> object
	 * @return the options
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List getOptions(HttpServletRequest req) {
		List options = new ArrayList();
		Date rightNow = new Date();
		String startsWith = req.getParameter("startsWith");
		String contains = req.getParameter("contains");
		String orderBy = req.getParameter("orderBy");
		if (startsWith == null || startsWith.length() < 1) {
			startsWith = defaultStartsWith;
		}
		if (contains == null || contains.length() < 1) {
			contains = defaultContains;
		}
		if (orderBy == null || orderBy.length() < 1) {
			orderBy = defaultOrderBy;
		}
		String cacheKey = startsWith + ":" + contains + ":" + orderBy + getCacheKeyQualifier(req);
		Map queryCache = getQueryCache(req, contextKey);
		if (queryCache.containsKey(cacheKey)) {
			Map results = (Map) queryCache.get(cacheKey);
			options = (List) results.get("options");
			results.put("lastAccessed", rightNow);
			Integer accessCount = (Integer) results.get("accessCount");
			results.put("accessCount", new Integer(accessCount.intValue() + 1));
		} else {
			options = fetchOptions(req, startsWith, contains, orderBy);
			if (options != null && options.size() > 0) {
				Map results = new HashMap();
				results.put("creationDate", rightNow);
				results.put("lastAccessed", rightNow);
				results.put("accessCount", new Integer(1));
				results.put("options", options);
				queryCache.put(cacheKey, results);
			}
		}

		return options;
	}

	/**
	 * <p>This method returns any additional qualifiers for the key to the query cache.</p>
	 *
	 * @param req the <code>HttpServletRequest</code> object
	 * @return the cache key qualifier
	 */
	protected String getCacheKeyQualifier(HttpServletRequest req) {
		return "";
	}

	/**
	 * <p>This method obtains the requested options from the data source.</p>
	 *
	 * @param req the <code>HttpServletRequest</code> object
	 * @param startsWith the "starts with" query parameter
	 * @param contains the "contains" query parameter
	 * @param orderBy the sort order
	 * @return the options
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List fetchOptions(HttpServletRequest req, String startsWith, String contains, String orderBy) { 
		List options = new ArrayList();

		DataSource dataSource = getDataSource();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String qs = getQueryStatement(req, startsWith, contains, orderBy);
		Map items = new TreeMap();
		Map fields = (Map) req.getAttribute(FIELD_MAP_REQ_ATTR_KEY);
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(qs);
			while (rs.next()) {
				Map thisItem = new HashMap();
		   		Iterator i = fields.keySet().iterator();
		   		while (i.hasNext()) {
		   			String fieldName = (String) i.next();
		   			String tableField = (String) fields.get(fieldName);
		   			thisItem.put(fieldName, filter(rs.getString(tableField)));
		   		}
				if (orderBy == null || orderBy.length() < 1) {
					items.put(thisItem.get("id"), thisItem);
				} else {
	  				options.add(thisItem);
				}
			}
		} catch (SQLException e) {
			log.info("SQL statement: " + qs);
			log.error("SQL error: " + e.toString() + "; " +  e.getMessage(), e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqle) {
					log.error("SQL error: " + sqle.toString() + "; " +  sqle.getMessage(), sqle);
				}
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqle) {
					log.error("SQL error: " + sqle.toString() + "; " +  sqle.getMessage(), sqle);
				}
				stmt = null;
			}
			if (conn != null) {
				try {
					conn.commit();
					conn.close();
				} catch (SQLException sqle) {
					log.error("SQL error: " + sqle.toString() + "; " +  sqle.getMessage(), sqle);
				}
				conn = null;
			}
		}

		if (orderBy == null || orderBy.length() < 1) {
			if (!items.isEmpty()) {
				Iterator i = items.keySet().iterator();
				while (i.hasNext()) {
					String key = i.next().toString();
	  				options.add(items.get(key));
				}
			}
		}

		return options;
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
		return getQueryStatement(startsWith, contains, orderBy);
	}

	/**
	 * <p>This method creates the SQL statement.</p>
	 *
	 * @param startsWith the "starts with" query parameter
	 * @param contains the "contains" query parameter
	 * @param orderBy the sort order
	 * @return the SQL statement
	 */
	protected abstract String getQueryStatement(String startsWith, String contains, String orderBy);

	/**
	 * <p>Fetch the query cache from the servlet context, or creates a new one.</p>
	 *
	 * @param req the <code>HttpServletRequest</code> object
	 * @param contextKey the servlet context key 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected static Map getQueryCache(HttpServletRequest req, String contextKey) {
		Map queryCache = (Map) req.getSession().getServletContext().getAttribute(contextKey);

		// if no cache, then make one
		if (queryCache == null) {
			queryCache = new HashMap();
			queryCache.put("_lastCacheReviewDateTime", new Date());
			req.getSession().getServletContext().setAttribute(contextKey, queryCache);
		}

		Date lastCacheReviewDate = (Date) queryCache.get("_lastCacheReviewDateTime");
		if (lastCacheReviewDate.before(getReviewCompareDate())) {
			reviewQueryCache(queryCache);
		}

		return queryCache;
	}

	/**
	 * <p>Review the query cache and remove expired items.</p>
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected static void reviewQueryCache(Map queryCache) {
		Date cutoffDate = getReviewCompareDate();

		List toBeRemoved = new ArrayList();
		Iterator i = queryCache.keySet().iterator();
		while (i.hasNext()) {
			String key = (String) i.next();
			if (!"_lastCacheReviewDateTime".equals(key)) {
				Map results = (Map) queryCache.get(key);
				Date creationDate = (Date) results.get("creationDate");
				if (creationDate.before(cutoffDate)) {
					toBeRemoved.add(key);
				}
			}
		}
		i = toBeRemoved.iterator();
		while (i.hasNext()) {
			queryCache.remove(i.next());
		}

		queryCache.put("_lastCacheReviewDateTime", new Date());
	}

	/**
	 * <p>Fetch the query cache from the servlet context, or creates a new one.</p>
	 */
	protected static Date getReviewCompareDate() {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}

	/**
	 * <p>This method is used to look up the <code>DataSource</code>
	 * by name.</p>
	 * 
	 * @return <code>dataSource</code> - the <code>DataSource</code>
	 */
	protected DataSource getDataSource() {
		DataSource dataSource = null;

		try {
			Context ctx = new InitialContext();
			dataSource = (DataSource) ctx.lookup(dataSourceName);
		} catch (Throwable t) {
			t.printStackTrace();
		}

		return dataSource;
	}

	/**
	 * @return Returns the contextKey.
	 */
	public String getContextKey() {
		return contextKey;
	}
	/**
	 * @param contextKey The contextKey to set.
	 */
	public void setContextKey(String contextKey) {
		this.contextKey = contextKey;
	}
	/**
	 * @return Returns the dataFields.
	 */
	public Map<String,String> getDataFields() {
		return dataFields;
	}
	/**
	 * @param dataFields The dataFields to set.
	 */
	public void setDataFields(Map<String,String> dataFields) {
		this.dataFields = dataFields;
	}
	/**
	 * @return Returns the dataSourceName.
	 */
	public String getDataSourceName() {
		return dataSourceName;
	}
	/**
	 * @param dataSourceName The dataSourceName to set.
	 */
	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}
	/**
	 * @return Returns the defaultContains.
	 */
	public String getDefaultContains() {
		return defaultContains;
	}
	/**
	 * @param defaultContains The defaultContains to set.
	 */
	public void setDefaultContains(String defaultContains) {
		this.defaultContains = defaultContains;
	}
	/**
	 * @return Returns the defaultOrderBy.
	 */
	public String getDefaultOrderBy() {
		return defaultOrderBy;
	}
	/**
	 * @param defaultOrderBy The defaultOrderBy to set.
	 */
	public void setDefaultOrderBy(String defaultOrderBy) {
		this.defaultOrderBy = defaultOrderBy;
	}
	/**
	 * @return Returns the defaultStartsWith.
	 */
	public String getDefaultStartsWith() {
		return defaultStartsWith;
	}
	/**
	 * @param defaultStartsWith The defaultStartsWith to set.
	 */
	public void setDefaultStartsWith(String defaultStartsWith) {
		this.defaultStartsWith = defaultStartsWith;
	}
}
