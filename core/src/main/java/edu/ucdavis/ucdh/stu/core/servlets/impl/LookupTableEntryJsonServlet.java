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

import edu.ucdavis.ucdh.stu.core.beans.LookupTableEntry;
import edu.ucdavis.ucdh.stu.core.manager.LookupTableEntryManager;
import edu.ucdavis.ucdh.stu.core.servlets.JavascriptServletBase;

/**
 * <p>This servlet handles LookupTableEntrys.</p>
 */
public class LookupTableEntryJsonServlet extends JavascriptServletBase {
	private static final long serialVersionUID = 1;
	private LookupTableEntryManager lookupTableEntryManager;

	/**
	 * <p>The Servlet "doGet()" method.</p>
	 *
	 * @param req the <code>HttpServletRequest</code> object
	 * @param res the <code>HttpServletResponse</code> object
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String id = getIdFromUrl(req, "/options/");

		// log request, if enabled
		if (log.isDebugEnabled()) {
			String message = "Processing GET request; id=" + id;
			if (req.getQueryString() != null && req.getQueryString().length() > 0) {
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
				sendOptionList(context, tableName, req, res);
			} else {
				// send error
				sendError(req, res, 404, "The requested resource was not found on this server.");
			}
		} else {
			// send error
			sendError(req, res, 404, "The requested resource was not found on this server.");
		}
	}

	/**
	 * <p>Handles a query request for a list of LookupTableEntrys.</p>
	 *
	 * @param context the context of the requested lookupTable
	 * @param tableName the name of the requested lookupTable
	 * @param req the <code>HttpServletRequest</code> object
	 * @param res the <code>HttpServletResponse</code> object
	 */
	private void sendOptionList(String context, String tableName, HttpServletRequest req, HttpServletResponse res) throws IOException {
		List<LookupTableEntry> list = lookupTableEntryManager.findByContextAndTableName(context, tableName);

		if (list != null && list.size() > 0) {
			PrintWriter pw = res.getWriter();
			pw.println(lookupTableEntryListToJson(context, tableName, list, req));
		} else {
			sendError(req, res, 404, "The requested resource was not found on this server.");
		}
	}

	/**
	 * <p>Converts a list of LookupTableEntries to JSON.</p>
	 * 
	 * @param context the context of the requested lookupTable
	 * @param tableName the name of the requested lookupTable
	 * @param list the list of LookupTableEntries
	 * @param req the <code>HttpServletRequest</code> object
	 * @return the list of LookupTableEntries in JSON format
	 */
	private static String lookupTableEntryListToJson(String context, String tableName, List<LookupTableEntry> list, HttpServletRequest req) {
		StringBuffer buffer = new StringBuffer();

		String var = req.getParameter("var");
		if (StringUtils.isNotEmpty(var)) {
			buffer.append("var ");
			buffer.append(var);
			buffer.append(" = ");
		}
		buffer.append("[");
		if (list != null && list.size() > 0) {
			Map<String,String> options = new TreeMap<String,String>();
			Iterator<LookupTableEntry> i = list.iterator();
			while (i.hasNext()) {
				LookupTableEntry lookupTableEntry = i.next();
				options.put(lookupTableEntry.getDescription(), lookupTableEntry.getEntryId());
			}
			String separator = "";
			Iterator<String> j = options.keySet().iterator();
			while (j.hasNext()) {
				String label = j.next();
				buffer.append(separator);
				buffer.append("{label: \"");
				buffer.append(label);
				buffer.append("\", value: \"");
				buffer.append(options.get(label));
				buffer.append("\"}");
				separator = ",\n";
			}
		}
		buffer.append("]");
		if (StringUtils.isNotEmpty(var)) {
			buffer.append(";");
		}

		return buffer.toString();
	}

	/**
	 * <p>Sets the LookupTableEntryManager.</p>
	 *
	 * @param lookupTableEntryManager the LookupTableEntryManager
	 */
	public void setLookupTableEntryManager(LookupTableEntryManager lookupTableEntryManager) {
		this.lookupTableEntryManager = lookupTableEntryManager;
	}
}
