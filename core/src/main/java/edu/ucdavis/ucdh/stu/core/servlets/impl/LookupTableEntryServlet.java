package edu.ucdavis.ucdh.stu.core.servlets.impl;

import java.io.IOException;
import java.io.PrintWriter;
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

import edu.ucdavis.ucdh.stu.core.beans.LookupTableEntry;
import edu.ucdavis.ucdh.stu.core.manager.LookupTableEntryManager;
import edu.ucdavis.ucdh.stu.core.servlets.RestServletBase;
import edu.ucdavis.ucdh.stu.core.utils.BetwixtTool;

/**
 * <p>This servlet handles LookupTableEntrys.</p>
 */
public class LookupTableEntryServlet extends RestServletBase {
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
		String id = getIdFromUrl(req, "/entry/");

		// log request, if enabled
		if (log.isDebugEnabled()) {
			String message = "Processing GET request; id=" + id;
			if (req.getQueryString() != null && req.getQueryString().length() > 0) {
				message += "; query string=" + req.getQueryString();
			}
			log.debug(message);
		}

		String context = "";
		String tableName = "";
		String entryId = "";
		if (id != null) {
			String[] parts = id.split("/");
			if (parts.length > 0) {
				context = parts[0];
				if (parts.length > 1) {
					tableName = parts[1];
					if (parts.length > 2) {
						entryId = parts[2];
					}
				}
			}
		}
		if (StringUtils.isNotEmpty(context)) {
			if (StringUtils.isNotEmpty(tableName)) {
				if (StringUtils.isNotEmpty(entryId)) {
					// send requested lookupTableEntry
					sendLookupTableEntry(context, tableName, entryId, req, res);
				} else {
					// send query results
					sendQueryResults(context, tableName, req, res);
				}
			} else {
				// send error
				sendError(req, res, 404, "No items matching your search criteria were found on this server.");
			}
		} else {
			// send error
			sendError(req, res, 404, "No items matching your search criteria were found on this server.");
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
		String id = getIdFromUrl(req, "/entry/");

		// log request, if enabled
		if (log.isDebugEnabled()) {
			String message = "Processing POST request; id=" + id;
			if (StringUtils.isNotEmpty(req.getQueryString())) {
				message += "; query string=" + req.getQueryString();
			}
			log.debug(message);
		}

		String context = "";
		String tableName = "";
		String entryId = "";
		if (id != null) {
			String[] parts = id.split("/");
			if (parts.length > 0) {
				context = parts[0];
				if (parts.length > 1) {
					tableName = parts[1];
					if (parts.length > 2) {
						entryId = parts[2];
					}
				}
			}
		}
		if (StringUtils.isNotEmpty(context)) {
			if (StringUtils.isNotEmpty(tableName)) {
				if (StringUtils.isNotEmpty(entryId)) {
					// update lookupTableEntry
					updateLookupTableEntry(context, tableName, entryId, req, res);
				} else {
					// send error
					sendError(req, res, 405, "Method Not Allowed. Only the \"GET\" method is allowed for this URL (/entry/<context>/<tableName> with no entryId).");
				}
			} else {
				// send error
				sendError(req, res, 405, "Method Not Allowed. Only the \"GET\" method is allowed for this URL (/entry/<tableName> with no entryId).");
			}
		} else {
			// send error
			sendError(req, res, 405, "Invalid URL (/entry with no tableId).");
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
		String id = getIdFromUrl(req, "/entry/");

		// log request, if enabled
		if (log.isDebugEnabled()) {
			log.debug("Processing PUT request; id=" + id);
		}


		String context = "";
		String tableName = "";
		String entryId = "";
		if (id != null) {
			String[] parts = id.split("/");
			if (parts.length > 0) {
				context = parts[0];
				if (parts.length > 1) {
					tableName = parts[1];
					if (parts.length > 2) {
						entryId = parts[2];
					}
				}
			}
		}
		if (StringUtils.isNotEmpty(context)) {
			if (StringUtils.isNotEmpty(tableName)) {
				if (StringUtils.isNotEmpty(entryId)) {
					// build LookupTable from user input
					LookupTableEntry lookupTableEntry = buildLookupTableEntryFromInput(context, tableName, entryId, req, res);
					if (lookupTableEntry != null) {
						// insert LookupTableEntry
						lookupTableEntryManager.save(lookupTableEntry);
						// confirm insert
						sendLookupTableEntry(context, tableName, entryId, req, res);
					} else {
						// send error
						sendError(req, res, 400, "Nothing to PUT.");
					}
				} else {
					// send error
					sendError(req, res, 405, "Method Not Allowed. Only the \"GET\" method is allowed for this URL (/entry/<context>/<tableName> with no entryId).");
				}
			} else {
				// send error
				sendError(req, res, 405, "Method Not Allowed. Only the \"GET\" method is allowed for this URL (/entry/<context> with no tableName or entryId).");
			}
		} else {
			// send error
			sendError(req, res, 405, "Invalid URL (/entry with no context or tableName).");
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
		String id = getIdFromUrl(req, "/entry/");

		// log request, if enabled
		if (log.isDebugEnabled()) {
			log.debug("Processing DELETE request; id=" + id);
		}


		String context = "";
		String tableName = "";
		String entryId = "";
		if (id != null) {
			String[] parts = id.split("/");
			if (parts.length > 0) {
				context = parts[0];
				if (parts.length > 1) {
					tableName = parts[1];
					if (parts.length > 2) {
						entryId = parts[2];
					}
				}
			}
		}
		if (StringUtils.isNotEmpty(context)) {
			if (StringUtils.isNotEmpty(tableName)) {
				if (StringUtils.isNotEmpty(entryId)) {
					// delete LookupTable
					deleteLookupTableEntry(context, tableName, entryId, req, res);
				} else {
					// send error
					sendError(req, res, 405, "Method Not Allowed. Only the \"GET\" method is allowed for this URL (/entry/<context>/<tableName> with no entryId).");
				}
			} else {
				// send error
				sendError(req, res, 405, "Method Not Allowed. Only the \"GET\" method is allowed for this URL (/entry/<context> with no tableName or entryId).");
			}
		} else {
			// send error
			sendError(req, res, 405, "Invalid URL (/entry with no context or tableName).");
		}
	}

	/**
	 * <p>Handles a get request for a single LookupTableEntry.</p>
	 *
	 * @param context the context of the requested lookupTable
	 * @param tableName the name of the requested lookupTable
	 * @param entryId the id of the requested lookupTableEntry
	 * @param req the <code>HttpServletRequest</code> object
	 * @param res the <code>HttpServletResponse</code> object
	 */
	private void sendLookupTableEntry(String context, String tableName, String entryId, HttpServletRequest req, HttpServletResponse res) throws IOException {
		LookupTableEntry lookupTableEntry = null;

		try {
			lookupTableEntry = lookupTableEntryManager.findByContextTableEntry(context, tableName, entryId);
			PrintWriter pw = res.getWriter();
			if (pw != null) {
				pw.print(BetwixtTool.toXml(lookupTableEntry, "/core/xsl/lookupTableEntry.xsl"));
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
	 * <p>Handles a query request for a list of LookupTableEntrys.</p>
	 *
	 * @param context the context of the requested lookupTable
	 * @param tableName the name of the requested lookupTable
	 * @param req the <code>HttpServletRequest</code> object
	 * @param res the <code>HttpServletResponse</code> object
	 */
	private void sendQueryResults(String context, String tableName, HttpServletRequest req, HttpServletResponse res) throws IOException {
		List<LookupTableEntry> list = lookupTableEntryManager.findByContextAndTableName(context, tableName);

		if (list != null && list.size() > 0) {
			PrintWriter pw = res.getWriter();
			pw.println(lookupTableEntryListToXml(context, tableName, list));
		} else {
			sendError(req, res, 404, "No items matching your search criteria were found on this server.");
		}
	}

	/**
	 * <p>Handles a post request for a single LookupTableEntry.</p>
	 *
	 * @param tableId the id of the requested lookupTable
	 * @param entryId the id of the requested lookupTableEntry
	 * @param req the <code>HttpServletRequest</code> object
	 * @param res the <code>HttpServletResponse</code> object
	 */
	private void updateLookupTableEntry(String context, String tableName, String entryId, HttpServletRequest req, HttpServletResponse res) throws IOException {
		// validate required parameters
		String validationErrors = validateUserInput(req, res);
		if (StringUtils.isNotEmpty(validationErrors)) {
			// send error
			sendError(req, res, 406, validationErrors);
		} else {
			LookupTableEntry lookupTableEntry = lookupTableEntryManager.findByContextTableEntry(context, tableName, entryId);
			if (lookupTableEntry == null) {
				// send error
				sendError(req, res, 404, "The requested resource was not found on this server. If you entered the URL manually please check your spelling and try again.");
			} else {
				String userId = req.getRemoteUser();
				Date rightNow = new Date();
				lookupTableEntry.setDescription(req.getParameter("description"));
				lookupTableEntry.setProperty00(req.getParameter("property00"));
				lookupTableEntry.setProperty01(req.getParameter("property01"));
				lookupTableEntry.setProperty02(req.getParameter("property02"));
				lookupTableEntry.setProperty03(req.getParameter("property03"));
				lookupTableEntry.setProperty04(req.getParameter("property04"));
				lookupTableEntry.setProperty05(req.getParameter("property05"));
				lookupTableEntry.setProperty06(req.getParameter("property06"));
				lookupTableEntry.setProperty07(req.getParameter("property07"));
				lookupTableEntry.setProperty08(req.getParameter("property08"));
				lookupTableEntry.setProperty09(req.getParameter("property09"));
				lookupTableEntry.setProperty10(req.getParameter("property10"));
				lookupTableEntry.setProperty11(req.getParameter("property11"));
				lookupTableEntry.setProperty12(req.getParameter("property12"));
				lookupTableEntry.setProperty13(req.getParameter("property13"));
				lookupTableEntry.setProperty14(req.getParameter("property14"));
				lookupTableEntry.setProperty15(req.getParameter("property15"));
				lookupTableEntry.setProperty16(req.getParameter("property16"));
				lookupTableEntry.setProperty17(req.getParameter("property17"));
				lookupTableEntry.setProperty18(req.getParameter("property18"));
				lookupTableEntry.setProperty19(req.getParameter("property19"));
				lookupTableEntry.setLastUpdate(rightNow);
				lookupTableEntry.setLastUpdateBy(userId);
				lookupTableEntryManager.save(lookupTableEntry);
				// send requested lookupTableEntry
				sendLookupTableEntry(context, tableName, entryId, req, res);
			}
		}
	}

	/**
	 * <p>Handles a delete request for a single LookupTableEntry.</p>
	 *
	 * @param context the context of the requested lookupTable
	 * @param tableName the name of the requested lookupTable
	 * @param entryId the id of the requested lookupTableEntry
	 * @param req the <code>HttpServletRequest</code> object
	 * @param res the <code>HttpServletResponse</code> object
	 */
	private void deleteLookupTableEntry(String context, String tableName, String entryId, HttpServletRequest req, HttpServletResponse res) throws IOException {
		LookupTableEntry lookupTableEntry = null;

		try {
			lookupTableEntry = lookupTableEntryManager.findByContextTableEntry(context, tableName, entryId);
			lookupTableEntryManager.delete(lookupTableEntry);
			PrintWriter pw = res.getWriter();
			if (pw != null) {
				pw.print("<message>Look-up table entry \"" + entryId + "\" deleted.</message>");
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
	 * <p>Builds the LookupTableEntry from user's input.</p>
	 *
	 * @param context the context of the requested lookupTable
	 * @param tableName the name of the requested lookupTable
	 * @param entryId the id of the requested lookupTableEntry
	 * @param req the <code>HttpServletRequest</code> object
	 * @param res the <code>HttpServletResponse</code> object
	 * @return the constructed LookupTableEntry
	 */
	private LookupTableEntry buildLookupTableEntryFromInput(String context, String tableName, String entryId, HttpServletRequest req, HttpServletResponse res) throws IOException {
		LookupTableEntry lookupTableEntry = null;

		BeanCreationList chain = BeanCreationList.createStandardChain();
		BeanReader reader = new BeanReader();
		try {
			reader.registerBeanClass("entry", LookupTableEntry.class);
			reader.getReadConfiguration().setBeanCreationChain(chain);
			reader.getBindingConfiguration().setObjectStringConverter(new ConvertUtilsObjectStringConverter());
			lookupTableEntry = (LookupTableEntry) reader.parse(req.getInputStream());
		} catch (Exception e) {
			// log error, if enabled
			if (log.isErrorEnabled()) {
				log.error("Error processing XML input: " + e.toString(), e);
			}
			// send error
			sendError(req, res, 500, "Internal server error. Details of this error can be found in the server's log file(s).");
		}

		// build LookupTableEntry from input
		if (lookupTableEntry != null) {
			Date rightNow = new Date();
			// get authenticated user
			String userId = req.getRemoteUser();
			lookupTableEntry.setCreationDate(rightNow);
			lookupTableEntry.setCreatedBy(userId);
			lookupTableEntry.setLastUpdate(rightNow);
			lookupTableEntry.setLastUpdateBy(userId);
		}

		return lookupTableEntry;
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
	 * <p>Converts a list of LookupTableEntries to XML.</p>
	 * 
	 * @param context the context of the requested lookupTable
	 * @param tableName the name of the requested lookupTable
	 * @param list the list of LookupTableEntries
	 * @return the list of LookupTableEntries in XML format
	 */
	private static String lookupTableEntryListToXml(String context, String tableName, List<LookupTableEntry> list) {
		StringBuffer buffer = new StringBuffer();

		buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		buffer.append("<lookup:entries xmlns:lookup=\"http://www.ucdmc.ucdavis.edu/table\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n");
		if (list != null && list.size() > 0) {
			Iterator<LookupTableEntry> i = list.iterator();
			while (i.hasNext()) {
				LookupTableEntry lookupTableEntry = i.next();
				buffer.append("  <entry context=\"");
				buffer.append(context);
				buffer.append("\" table=\"");
				buffer.append(tableName);
				buffer.append("\" entryId=\"");
				buffer.append(lookupTableEntry.getEntryId());
				buffer.append("\" xlink:href=\"/core/entry/");
				buffer.append(context);
				buffer.append("/");
				buffer.append(tableName);
				buffer.append("/");
				buffer.append(lookupTableEntry.getEntryId());
				buffer.append("\"/>\n");
			}
		}
		buffer.append("</lookup:entries>");

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
