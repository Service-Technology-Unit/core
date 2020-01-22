package edu.ucdavis.ucdh.stu.core.servlets.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import edu.ucdavis.ucdh.stu.core.beans.LookupTable;
import edu.ucdavis.ucdh.stu.core.manager.LookupTableManager;
import edu.ucdavis.ucdh.stu.core.servlets.JavascriptServletBase;

/**
 * <p>This servlet handles LookupTables.</p>
 */
public class LookupTableJsonServlet extends JavascriptServletBase {
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
		// log request, if enabled
		if (log.isDebugEnabled()) {
			String message = "Processing GET request.";
			if (req.getQueryString() != null && req.getQueryString().length() > 0) {
				message += "; query string=" + req.getQueryString();
			}
			log.debug(message);
		}

		sendOptionList(req, res);
	}

	/**
	 * <p>Handles a query request for a list of LookupTables.</p>
	 *
	 * @param req the <code>HttpServletRequest</code> object
	 * @param res the <code>HttpServletResponse</code> object
	 */
	private void sendOptionList(HttpServletRequest req, HttpServletResponse res) throws IOException {
		List<LookupTable> list = null;
		String context = req.getParameter("context");
		if (StringUtils.isNotEmpty(context)) {
			list = lookupTableManager.findByContext(context);
		} else {
			list = lookupTableManager.findAll();
		}

		if (list != null && list.size() > 0) {
			PrintWriter pw = res.getWriter();
			pw.println(lookupTableListToJson(list, req));
		} else {
			sendError(req, res, 404, "The requested resource was not found on this server.");
		}
	}

	/**
	 * <p>Converts a list of LookupTableEntries to JSON.</p>
	 * 
	 * @param list the list of LookupTableEntries
	 * @param req the <code>HttpServletRequest</code> object
	 * @return the list of LookupTableEntries in JSON format
	 */
	private static String lookupTableListToJson(List<LookupTable> list, HttpServletRequest req) {
		StringBuffer buffer = new StringBuffer();

		String var = req.getParameter("var");
		if (StringUtils.isNotEmpty(var)) {
			buffer.append("var ");
			buffer.append(var);
			buffer.append(" = ");
		}
		buffer.append("{");
		if (list != null && list.size() > 0) {
			Map<String,Map<String,String>> contexts = new TreeMap<String,Map<String,String>>();
			Iterator<LookupTable> i = list.iterator();
			while (i.hasNext()) {
				LookupTable lookupTable = i.next();
				String context = lookupTable.getContext();
				Map<String,String> contextMap = contexts.get(context);
				if (contextMap == null) {
					contextMap = new TreeMap<String,String>();
					contexts.put(context, contextMap);
				}
				contextMap.put(lookupTable.getDisplayName(), lookupTable.getTableName());
			}
			String separator = "";
			Iterator<String> j = contexts.keySet().iterator();
			while (j.hasNext()) {
				String context = j.next();
				buffer.append(separator);
				buffer.append(context);
				buffer.append(": [");
				Map<String,String> contextMap = contexts.get(context);
				String tableSeparator = "";
				Iterator<String> k = contextMap.keySet().iterator();
				while (k.hasNext()) {
					String label = k.next();
					buffer.append(tableSeparator);
					buffer.append("{label: \"");
					buffer.append(label);
					buffer.append("\", value: \"");
					buffer.append(contextMap.get(label));
					buffer.append("\"}");
					tableSeparator = ",\n";
				}
				buffer.append("]");
				separator = ",\n";
			}
		}
		buffer.append("}");
		if (StringUtils.isNotEmpty(var)) {
			buffer.append(";");
		}

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
