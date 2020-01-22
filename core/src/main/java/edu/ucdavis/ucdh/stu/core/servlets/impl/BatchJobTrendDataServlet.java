package edu.ucdavis.ucdh.stu.core.servlets.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.TreeMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;

import edu.ucdavis.ucdh.stu.core.beans.BatchJobInstance;
import edu.ucdavis.ucdh.stu.core.beans.BatchJobSchedule;
import edu.ucdavis.ucdh.stu.core.beans.BatchJobStatistic;
import edu.ucdavis.ucdh.stu.core.manager.BatchJobInstanceManager;
import edu.ucdavis.ucdh.stu.core.manager.BatchJobScheduleManager;
import edu.ucdavis.ucdh.stu.core.servlets.ServletBase;
import edu.ucdavis.ucdh.stu.core.utils.BatchJobService;

/**
 * <p>This servlet produces a JSON file of batch job statistics.</p>
 */
public class BatchJobTrendDataServlet extends ServletBase {
	private static final long serialVersionUID = 1;
	private static final String[] LINE_COLOR = {"002666","BF9900","CA0015","004A3D","5D005D","000000","2F4D6A","77160B","CCD4E0","F2EBCC","F4CCD0","004A3D"};
	private static final DateFormat DF = DateFormat.getDateInstance(DateFormat.SHORT);
	private static final DateFormat TF = DateFormat.getTimeInstance(DateFormat.SHORT);
	private DataSource dataSource;
	private String dataSourceName;
	private BatchJobScheduleManager batchJobScheduleManager;
	private BatchJobInstanceManager batchJobInstanceManager;

	/**
	 * <p>The Servlet "service" method.</p>
	 *
	 * @param req the <code>HttpServletRequest</code> object
	 * @param res the <code>HttpServletResponse</code> object
	 * @throws ServletException
	 * @throws IOException
	 */
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String method = req.getMethod();
		if ("get".equalsIgnoreCase(method)) {
			doGet(req, res);
		} else {
			sendError(req, res, 405, "Method Not Allowed. The \"" + method.toUpperCase() + "\" is not appropriate for this resource.");
		}
	}

	/**
	 * <p>The Servlet "doGet" method -- the "GET" method is used to fetch the
	 * binary object from the database as if it were an actual file.</p>
	 *
	 * @param request the <code>HttpServletRequest</code> object
	 * @param response the <code>HttpServletResponse</code> object
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		BatchJobSchedule batchJobSchedule = batchJobScheduleManager.findById(getNumericIdFromUrl(req, "/trend/"));
		if (batchJobSchedule != null) {
			int max = 10;
			if (StringUtils.isNotEmpty(req.getParameter("max"))) {
				try {
					max = Integer.parseInt(req.getParameter("max"));
				} catch (Exception e) {
					// no one cares!
				}
			}
			List<Map<String,Object>> instances = getBatchJobInstances(batchJobSchedule, max);
			if (instances != null) {			
				res.setHeader("Pragma", "public");
				res.setHeader("Cache-Control", "public");
				res.setContentType("text/javascript");			
				PrintWriter pw = res.getWriter();
				pw.println(buildResponse(batchJobSchedule, instances, req));
			} else {
				sendError(req, res, 404, "The requested resource was not found on this server.");
			}
		} else {
			sendError(req, res, 404, "The requested resource was not found on this server.");
		}
	}

	/**
	 * <p>Returns the most recently run instances for the passed schedule</p>
	 *
	 * @param batchJobSchedule the <code>BatchJobSchedule</code> object
	 * @return the most recently run instances for the passed schedule
	 */
	public List<Map<String,Object>> getBatchJobInstances(BatchJobSchedule batchJobSchedule, int max) {
		List<Map<String,Object>> instances = null;

		DataSource dataSource = getDataSource();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			String sql = "select id, startDateTime from batchjobinstance where batchJobScheduleId=? and status='Completed' order by startDateTime desc limit ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, batchJobSchedule.getId());
			ps.setInt(2, max);
			rs = ps.executeQuery();
			while (rs.next()) {
				if (instances == null) {
					instances = new ArrayList<Map<String,Object>>();
				}
				Map<String,Object> thisInstance = new HashMap<String,Object>();
				thisInstance.put("id", rs.getInt("id"));
				instances.add(thisInstance);
			}
		} catch (SQLException e) {
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
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException sqle) {
					log.error("SQL error: " + sqle.toString() + "; " +  sqle.getMessage(), sqle);
				}
				ps = null;
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException sqle) {
					log.error("SQL error: " + sqle.toString() + "; " +  sqle.getMessage(), sqle);
				}
				conn = null;
			}
		}

		return instances;
    }

	/**
	 * <p>Returns the most recently run instances for the passed schedule</p>
	 *
	 * @param batchJobSchedule the <code>BatchJobSchedule</code> object
	 * @param instances the most recently run instances for the passed schedule
	 * @return the JSON data for these batch job instances
	 */
	public String buildResponse(BatchJobSchedule batchJobSchedule, List<Map<String,Object>> instances, HttpServletRequest req) {
		String js = "";

		if (StringUtils.isNotEmpty(req.getParameter("var"))) {
			js = "var " + req.getParameter("var") + " = ";
		}
		List<String> includeStat = getSelectedStatistics(req);
		BigDecimal max = BigDecimal.ZERO;
		Map<String,String> statLabel = new TreeMap<String,String>();
		Iterator<Map<String,Object>> i = instances.iterator();
		while (i.hasNext()) {
			Map<String,Object> thisInstance = i.next();
			BatchJobInstance batchJobInstance = batchJobInstanceManager.findById((Integer)thisInstance.get("id"));
			thisInstance.put("batchJobInstance", batchJobInstance);
			Iterator<BatchJobStatistic> j = batchJobInstance.getStatistic().iterator();
			while (j.hasNext()) {
				BatchJobStatistic thisStatistic = j.next();
				if (thisStatistic.getFormat().indexOf("date") == -1) {
					if (includeStat.size() == 0 || includeStat.contains(thisStatistic.getLabel())) {
						statLabel.put(thisStatistic.getLabel(), thisStatistic.getFormat());
					}
				}
			}
		}
		js += "{\n";
		js += "  \"title\":{\n";
		js += "    \"text\":  \"Trend Report for Batch Job Schedule #" + batchJobSchedule.getId() + ": " + batchJobSchedule.getDescription() + "\",\n";
		js += "    \"style\": \"{font-size: 20px; color:#0000ff; font-family: Verdana; text-align: center;}\"\n";
		js += "  },\n";
		js += "  \n";
		js += "  \"x_legend\":{\n";
		js += "    \"text\": \"Run Date/Time\",\n";
		js += "    \"style\": \"{color: #736AFF; font-size: 12px;}\"\n";
		js += "  },\n";
		js += "  \n";
		js += "  \"elements\":[";
		int x = 0;
		String separator = "";
		Iterator<String> k = statLabel.keySet().iterator();
		while (k.hasNext()) {
			String label = k.next();
			String format = statLabel.get(label);
			js += separator + "\n";
			js += "    {\n";
			js += "      \"type\":      \"line\",\n";
			js += "      \"alpha\":     0.5,\n";
			js += "      \"colour\":    \"#" + LINE_COLOR[x] + "\",\n";
			js += "      \"text\":      \"" + label;
			if (BatchJobService.FORMAT_CURRENCY.equalsIgnoreCase(format)) {
				js += " ($)";
			} else if (BatchJobService.FORMAT_PERCENTAGE.equalsIgnoreCase(format)) {
				js += " (%)";
			} else if (BatchJobService.FORMAT_DURATION.equalsIgnoreCase(format)) {
				js += " (seconds)";
			}
			js += "\",\n";
			js += "      \"font-size\": 10,\n";
			js += "      \"values\" :   [";
			String separator2 = "";
			ListIterator<Map<String,Object>> l = instances.listIterator(instances.size());
			while (l.hasPrevious()) {
				Map<String,Object> thisInstance = l.previous();
				BatchJobInstance batchJobInstance = (BatchJobInstance) thisInstance.get("batchJobInstance");
				js += separator2;
				String value = getStatisticValue(batchJobInstance, label);
				js += value;
				BigDecimal thisValue = BigDecimal.valueOf(Double.parseDouble(value));
				if (thisValue.compareTo(max) > 0) {
					max = thisValue;
				}
				separator2 = ",";
			}
			js += "]\n";
			js += "    }";
			x++;
			separator = ",";
		}
		js += "\n";
		js += "  ],\n";
		js += "  \n";
		js += "  \"x_axis\":{\n";
		js += "    \"stroke\":1,\n";
		js += "    \"tick_height\":10,\n";
		js += "    \"colour\":\"#d000d0\",\n";
		js += "    \"grid_colour\":\"#00ff00\",\n";
		js += "    \"labels\": {\n";
		js += "        \"labels\": [";
		String separator2 = "";
		ListIterator<Map<String,Object>> l = instances.listIterator(instances.size());
		while (l.hasPrevious()) {
			Map<String,Object> thisInstance = l.previous();
			BatchJobInstance batchJobInstance = (BatchJobInstance) thisInstance.get("batchJobInstance");
			js += separator2 + "\"";
			js += formatStartDateTime(batchJobInstance);
			js += "\"";
			separator2 = ",";
		}
		js += "]\n";
		js += "    }\n";
		js += "   },\n";
		js += "   \n";
		js += "  \"y_axis\":{\n";
		js += "    \"stroke\":      4,\n";
		js += "    \"tick_length\": 3,\n";
		js += "    \"colour\":      \"#d000d0\",\n";
		js += "    \"grid_colour\": \"#00ff00\",\n";
		js += "    \"offset\":      0,\n";
		js += "    \"max\":         ";
		js += max.multiply(BigDecimal.valueOf(1.1)).add(BigDecimal.ONE).intValue();
		js += "\n";
		js += "  }\n";
		js += "}";
		if (StringUtils.isNotEmpty(req.getParameter("var"))) {
			js += ";";
		}

		return js;
    }

	/**
	 * <p>This method is used to build the list of selected statistics from the request object.</p>
	 * 
	 * @param req the <code>HttpServletRequest</code> object
	 * @return the list of selected statistics 
	 */
	private List<String> getSelectedStatistics(HttpServletRequest req) {
		List<String> includeStat = new ArrayList<String>();

		if (StringUtils.isNotEmpty(req.getParameter("include"))) {
			String[] stats = req.getParameter("include").split(";");
			for (int i=0; i<stats.length; i++) {
				includeStat.add(stats[i]);
			}
		}

		return includeStat;
	}

	/**
	 * <p>This method is used to look up the value associated with the job instance
	 * and statistic label passed.</p>
	 * 
	 * @param batchJobInstance the batch job instance containing the statistics
	 * @param label the label of the statistic requested
	 * @return the value associated with the job instance and statistic label passed
	 */
	private String getStatisticValue(BatchJobInstance batchJobInstance, String label) {
		String value = "0";

		BatchJobStatistic batchJobStatistic = null;
		Iterator<BatchJobStatistic> i = batchJobInstance.getStatistic().iterator();
		while (batchJobStatistic == null && i.hasNext()) {
			BatchJobStatistic thisStatistic = i.next();
			if (StringUtils.isNotEmpty(thisStatistic.getLabel()) && thisStatistic.getLabel().equalsIgnoreCase(label)) {
				batchJobStatistic = thisStatistic;
			}
		}
		if (batchJobStatistic != null) {
			value = batchJobStatistic.getValue().toString();
			if (BatchJobService.FORMAT_CURRENCY.equals(batchJobStatistic.getFormat())) {
				value = addDecimal(value, 2);
			} else if (BatchJobService.FORMAT_DURATION.equals(batchJobStatistic.getFormat())) {
				value = addDecimal(value, 3);
			} else if (BatchJobService.FORMAT_PERCENTAGE.equals(batchJobStatistic.getFormat())) {
				value = addDecimal(value, 2);
			}
		}

		return value;
	}

	/**
	 * <p>This method is used to a decimal point to a numeric string.</p>
	 * 
	 * @param value the numeric value
	 * @param places the number of decimal places
	 * @return the formatted decimal
	 */
	private String addDecimal(String value, int places) {
		while (value.length() < (places + 1)) {
			value = "0" + value;
		}
		int index = value.length() - places;
		return value.substring(0, index) + "." + value.substring(index);
	}

	/**
	 * <p>This method is used to format the start date/time of a job instance.</p>
	 * 
	 * @param batchJobInstance the batch job instance
	 * @return the formatted start date/time
	 */
	private String formatStartDateTime(BatchJobInstance batchJobInstance) {
		String value = "(unknown)";

		if (batchJobInstance.getStartDateTime() != null) {
			value = DF.format(batchJobInstance.getStartDateTime());
			value += "\\n";
			value += TF.format(batchJobInstance.getStartDateTime());
		}

		return value;
	}

	/**
	 * <p>This method is used to look up the <code>DataSource</code>
	 * by name, if needed.</p>
	 * 
	 * @return dataSource - the application's <code>DataSource</code>
	 */
	private DataSource getDataSource() {
		if (dataSource == null) {
			try {
				Context ctx = new InitialContext(new Hashtable<Object,Object>());
				dataSource = (DataSource) ctx.lookup(dataSourceName);
			} catch (Throwable t) {
				log.error("Exception obtaining DataSource (\"" + dataSourceName + "\"): " + t.toString(), t);
			}
		}

		return dataSource;
	}

	/**
	 * @param batchJobScheduleManager the batchJobScheduleManager to set
	 */
	public void setBatchJobScheduleManager(BatchJobScheduleManager batchJobScheduleManager) {
		this.batchJobScheduleManager = batchJobScheduleManager;
	}

	/**
	 * @param batchJobInstanceManager the batchJobInstanceManager to set
	 */
	public void setBatchJobInstanceManager(BatchJobInstanceManager batchJobInstanceManager) {
		this.batchJobInstanceManager = batchJobInstanceManager;
	}

	/**
	 * @param dataSourceName the dataSourceName to set
	 */
	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}
}
