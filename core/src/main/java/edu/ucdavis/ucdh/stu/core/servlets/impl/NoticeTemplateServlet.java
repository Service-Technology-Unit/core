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

import edu.ucdavis.ucdh.stu.core.beans.NoticeTemplate;
import edu.ucdavis.ucdh.stu.core.manager.NoticeTemplateManager;
import edu.ucdavis.ucdh.stu.core.servlets.RestServletBase;
import edu.ucdavis.ucdh.stu.core.utils.BetwixtTool;

/**
 * <p>This servlet handles NoticeTemplates.</p>
 */
public class NoticeTemplateServlet extends RestServletBase {
	private static final long serialVersionUID = 1;
	private NoticeTemplateManager noticeTemplateManager;

	/**
	 * <p>The Servlet "doGet()" method.</p>
	 *
	 * @param req the <code>HttpServletRequest</code> object
	 * @param res the <code>HttpServletResponse</code> object
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String id = getIdFromUrl(req, "/template/");

		// extract name and context from id
		String context = getContextFromId(id);
		String name = getNameFromId(id);

		// log request, if enabled
		if (log.isDebugEnabled()) {
			String message = "Processing GET request; context=" + context + "; name=" + name;
			if (StringUtils.isNotEmpty(req.getQueryString())) {
				message += "; query string=" + req.getQueryString();
			}
			log.debug(message);
		}

		if (StringUtils.isNotEmpty(context) && StringUtils.isNotEmpty(name)) {
			// send requested noticeTemplate
			sendNoticeTemplate(context, name, req, res);
		} else {
			// send query results
			sendQueryResults(context, req, res);
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
		String id = getIdFromUrl(req, "/template/");

		// extract name and context from id
		String context = getContextFromId(id);
		String name = getNameFromId(id);

		// log request, if enabled
		if (log.isDebugEnabled()) {
			String message = "Processing POST request; context=" + context + "; name=" + name;
			if (StringUtils.isNotEmpty(req.getQueryString())) {
				message += "; query string=" + req.getQueryString();
			}
			log.debug(message);
		}

		if (StringUtils.isNotEmpty(context) && StringUtils.isNotEmpty(name)) {
			// update noticeTemplate
			updateNoticeTemplate(context, name, req, res);
		} else {
			// send error
			sendError(req, res, 405, "Method Not Allowed. Only the \"GET\" method is allowed for this URL (/template with no id).");
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
		String id = getIdFromUrl(req, "/template/");

		// extract name and context from id
		String context = getContextFromId(id);
		String name = getNameFromId(id);

		// log request, if enabled
		if (log.isDebugEnabled()) {
			log.debug("Processing PUT request; context=" + context + "; name=" + name);
		}

		if (StringUtils.isNotEmpty(context) && StringUtils.isNotEmpty(name)) {
			// build NoticeTemplate from user input
			NoticeTemplate noticeTemplate = buildNoticeTemplateFromInput(req, res);
			if (noticeTemplate != null) {
				// insert NoticeTemplate
				noticeTemplateManager.save(noticeTemplate);
				// confirm insert
				sendNoticeTemplate(context, name, req, res);
			} else {
				// send error
				sendError(req, res, 400, "Nothing to PUT.");
			}
		} else {
			// send error
			sendError(req, res, 405, "Method Not Allowed. Only the \"GET\" method is allowed for this URL (/template with no id).");
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
		String id = getIdFromUrl(req, "/template/");

		// extract name and context from id
		String context = getContextFromId(id);
		String name = getNameFromId(id);

		// log request, if enabled
		if (log.isDebugEnabled()) {
			log.debug("Processing DELETE request; context=" + context + "; name=" + name);
		}

		if (StringUtils.isNotEmpty(context) && StringUtils.isNotEmpty(name)) {
			// delete NoticeTemplate
			deleteNoticeTemplate(context, name, req, res);
		} else {
			// send error
			sendError(req, res, 405, "Method Not Allowed. Only the \"GET\" method is allowed for this URL.");
		}
	}

	/**
	 * <p>Handles a get request for a single NoticeTemplate.</p>
	 *
	 * @param context the context of the requested noticeTemplate
	 * @param name the name of the requested noticeTemplate
	 * @param req the <code>HttpServletRequest</code> object
	 * @param res the <code>HttpServletResponse</code> object
	 */
	private void sendNoticeTemplate(String context, String name, HttpServletRequest req, HttpServletResponse res) throws IOException {
		NoticeTemplate noticeTemplate = null;

		try {
			noticeTemplate = noticeTemplateManager.findByContextAndName(context, name);
			PrintWriter pw = res.getWriter();
			if (pw != null) {
				pw.print(BetwixtTool.toXml(noticeTemplate, "/core/xsl/noticeTemplate.xsl"));
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
	 * <p>Handles a query request for a list of NoticeTemplates.</p>
	 *
	 * <p>Currently, no query parameters are accepted or evaluated. All templates
	 * defined in the database are returned.</p>
	 *
	 * @param context the NoticeTemplate context
	 * @param req the <code>HttpServletRequest</code> object
	 * @param res the <code>HttpServletResponse</code> object
	 */
	private void sendQueryResults(String context, HttpServletRequest req, HttpServletResponse res) throws IOException {
		List<NoticeTemplate> list = getNoticeTemplateList(context, req);

		if (list != null && list.size() > 0) {
			PrintWriter pw = res.getWriter();
			pw.println(noticeTemplateListToXml(list));
		} else {
			sendError(req, res, 404, "No items matching your search criteria were found on this server.");
		}
	}

	/**
	 * <p>Handles a post request for a single NoticeTemplate.</p>
	 *
	 * @param context the context of the requested noticeTemplate
	 * @param name the name of the requested noticeTemplate
	 * @param req the <code>HttpServletRequest</code> object
	 * @param res the <code>HttpServletResponse</code> object
	 */
	private void updateNoticeTemplate(String context, String name, HttpServletRequest req, HttpServletResponse res) throws IOException {
		// validate required parameters
		String validationErrors = validateUserInput(req, res);
		if (StringUtils.isNotEmpty(validationErrors)) {
			// send error
			sendError(req, res, 406, validationErrors);
		} else {
			NoticeTemplate noticeTemplate = noticeTemplateManager.findByContextAndName(context, name);
			if (noticeTemplate == null) {
				// send error
				sendError(req, res, 404, "The requested resource was not found on this server. If you entered the URL manually please check your spelling and try again.");
			} else {
				String userId = req.getRemoteUser();
				Date rightNow = new Date();
				noticeTemplate.setDescription(req.getParameter("description"));
				noticeTemplate.setContentType(req.getParameter("contentType"));
				noticeTemplate.setDefaultDeliveryMethod(req.getParameter("defaultDeliveryMethod"));
				noticeTemplate.setTitleSubject(req.getParameter("titleSubject"));
				noticeTemplate.setBody(req.getParameter("body"));
				noticeTemplate.setLastUpdate(rightNow);
				noticeTemplate.setLastUpdateBy(userId);
				noticeTemplateManager.save(noticeTemplate);
				// send requested noticeTemplate
				sendNoticeTemplate(context, name, req, res);
			}
		}
	}

	/**
	 * <p>Handles a delete request for a single NoticeTemplate.</p>
	 *
	 * @param context the context of the requested noticeTemplate
	 * @param name the name of the requested noticeTemplate
	 * @param req the <code>HttpServletRequest</code> object
	 * @param res the <code>HttpServletResponse</code> object
	 */
	private void deleteNoticeTemplate(String context, String name, HttpServletRequest req, HttpServletResponse res) throws IOException {
		NoticeTemplate noticeTemplate = null;

		try {
			noticeTemplate = noticeTemplateManager.findByContextAndName(context, name);
			noticeTemplateManager.delete(noticeTemplate);
			PrintWriter pw = res.getWriter();
			if (pw != null) {
				pw.print("<message>Notice Template \"" + context + "/" + name + "\" deleted.</message>");
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
	 * <p>Builds the NoticeTemplate from user's input.</p>
	 *
	 * @param req the <code>HttpServletRequest</code> object
	 * @param res the <code>HttpServletResponse</code> object
	 * @return the constructed NoticeTemplate
	 */
	private NoticeTemplate buildNoticeTemplateFromInput(HttpServletRequest req, HttpServletResponse res) throws IOException {
		NoticeTemplate noticeTemplate = null;

		BeanCreationList chain = BeanCreationList.createStandardChain();
		BeanReader reader = new BeanReader();
		try {
			reader.registerBeanClass("notify:template", NoticeTemplate.class);
			reader.getReadConfiguration().setBeanCreationChain(chain);
			reader.getBindingConfiguration().setObjectStringConverter(new ConvertUtilsObjectStringConverter());
			noticeTemplate = (NoticeTemplate) reader.parse(req.getInputStream());
		} catch (Exception e) {
			// log error, if enabled
			if (log.isErrorEnabled()) {
				log.error("Error processing XML input: " + e.toString(), e);
			}
			// send error
			sendError(req, res, 500, "Internal server error. Details of this error can be found in the server's log file(s).");
		}

		// build NoticeTemplate from input
		if (noticeTemplate != null) {
			Date rightNow = new Date();
			String userId = req.getRemoteUser();
			noticeTemplate.setCreationDate(rightNow);
			noticeTemplate.setCreatedBy(userId);
			noticeTemplate.setLastUpdate(rightNow);
			noticeTemplate.setLastUpdateBy(userId);
		}

		return noticeTemplate;
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
	 * <p>This method obtains the requested persons.</p>
	 *
	 * @param context the NoticeTemplate context
	 * @param req the <code>HttpServletRequest</code> object
	 * @return the requested persons
	 */
	private List<NoticeTemplate> getNoticeTemplateList(String context, HttpServletRequest req) {
		List<NoticeTemplate> results = null;

		if (StringUtils.isEmpty(req.getQueryString())) {
			if (StringUtils.isEmpty(context)) {
				results = noticeTemplateManager.findAll();
			} else {
				results = noticeTemplateManager.findByProperty("context", context);
			}
		} else {
			String startsWith = req.getParameter("startsWith");
			String contains = req.getParameter("contains");
			String equals = req.getParameter("equals");
			String orderBy = req.getParameter("orderBy");
			if (StringUtils.isEmpty(orderBy)) {
				orderBy = "name";
			}
			String hql = getHibernateQueryStatement(context, startsWith, contains, equals, orderBy);
			results = noticeTemplateManager.executeQuery(hql);
		}

		return results;
	}

	/**
	 * <p>This method creates the NoticeTemplate list HQL statement.</p>
	 *
	 * @param context the NoticeTemplate context
	 * @param startsWith the "starts with" query parameter
	 * @param contains the "contains" query parameter
	 * @param equals the "equals" query parameter
	 * @param orderBy the sort order
	 * @return the HQL statement
	 */
	private String getHibernateQueryStatement(String context, String startsWith, String contains, String equals, String orderBy) { 
		StringBuffer buffer = new StringBuffer();

		buffer.append("from\n");
		buffer.append("   NoticeTemplate\n");
		buffer.append("where\n");
		if (!StringUtils.isEmpty(context)) {
			buffer.append("   context = '");
			buffer.append(context);
			buffer.append("' and\n");
		}
		if ("description".equalsIgnoreCase(orderBy)) {
			// sort and select by description
			if (!StringUtils.isEmpty(equals)) {
				buffer.append("   description = '" + equals.toLowerCase() + "'\n");
			} else if (!StringUtils.isEmpty(contains)) {
				buffer.append("   description like '%" + contains.toLowerCase() + "%'\n");
			} else {
				buffer.append("   description like '" + startsWith.toLowerCase() + "%'\n");
			}
			buffer.append("order by\n");
			buffer.append("   description, name, id");
		} else if ("contentType".equalsIgnoreCase(orderBy)) {
			// sort and select by contentType
			if (!StringUtils.isEmpty(equals)) {
				buffer.append("   contentType = '" + equals.toLowerCase() + "'\n");
			} else if (!StringUtils.isEmpty(contains)) {
				buffer.append("   contentType like '%" + contains.toLowerCase() + "%'\n");
			} else {
				buffer.append("   contentType like '" + startsWith.toLowerCase() + "%'\n");
			}
			buffer.append("order by\n");
			buffer.append("   contentType, name, id");
		} else if ("defaultDeliveryMethod".equalsIgnoreCase(orderBy)) {
			// sort and select by defaultDeliveryMethod
			if (!StringUtils.isEmpty(equals)) {
				buffer.append("   defaultDeliveryMethod = '" + equals.toLowerCase() + "'\n");
			} else if (!StringUtils.isEmpty(contains)) {
				buffer.append("   defaultDeliveryMethod like '%" + contains.toLowerCase() + "%'\n");
			} else {
				buffer.append("   defaultDeliveryMethod like '" + startsWith.toLowerCase() + "%'\n");
			}
			buffer.append("order by\n");
			buffer.append("   defaultDeliveryMethod, name, id");
		} else if ("titleSubjecte".equalsIgnoreCase(orderBy)) {
			// sort and select by titleSubjecte
			if (!StringUtils.isEmpty(equals)) {
				buffer.append("   titleSubjecte = '" + equals.toLowerCase() + "'\n");
			} else if (!StringUtils.isEmpty(contains)) {
				buffer.append("   titleSubjecte like '%" + contains.toLowerCase() + "%'\n");
			} else {
				buffer.append("   titleSubjecte like '" + startsWith.toLowerCase() + "%'\n");
			}
			buffer.append("order by\n");
			buffer.append("   titleSubjecte, name, id");
		} else if ("body".equalsIgnoreCase(orderBy)) {
			// sort and select by body
			if (!StringUtils.isEmpty(equals)) {
				buffer.append("   body = '" + equals.toLowerCase() + "'\n");
			} else if (!StringUtils.isEmpty(contains)) {
				buffer.append("   body like '%" + contains.toLowerCase() + "%'\n");
			} else {
				buffer.append("   body like '" + startsWith.toLowerCase() + "%'\n");
			}
			buffer.append("order by\n");
			buffer.append("   body, name, id");
		} else {
			// sort and select by name
			if (!StringUtils.isEmpty(equals)) {
				buffer.append("   name = '" + equals.toLowerCase() + "'\n");
			} else if (!StringUtils.isEmpty(contains)) {
				buffer.append("   name like '%" + contains.toLowerCase() + "%'\n");
			} else {
				buffer.append("   name like '" + startsWith.toLowerCase() + "%'\n");
			}
			buffer.append("order by\n");
			buffer.append("   name, id");
		}

		return buffer.toString();
	}

	/**
	 * <p>Converts a list of NoticeTemplates to XML.</p>
	 * 
	 * @param list the list of NoticeTemplates
	 * @return the list of NoticeTemplates in XML format
	 */
	private static String noticeTemplateListToXml(List<NoticeTemplate> list) {
		StringBuffer buffer = new StringBuffer();

		buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		buffer.append("<notify:templates xmlns:notify=\"http://www.ucdmc.ucdavis.edu/notify\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n");
		if (list != null && list.size() > 0) {
			Iterator<NoticeTemplate> i = list.iterator();
			while (i.hasNext()) {
				NoticeTemplate noticeTemplate = i.next();
				buffer.append("  <template id=\"");
				buffer.append(noticeTemplate.getId());
				buffer.append("\" xlink:href=\"/core/template/");
				buffer.append(noticeTemplate.getContext());
				buffer.append("/");
				buffer.append(noticeTemplate.getName());
				buffer.append("\"/>\n");
			}
		}
		buffer.append("</notify:templates>");

		return buffer.toString();
	}

	/**
	 * <p>Returns the context portion of the id.</p>
	 *
	 * @param id the id
	 * @return the context portion of the id
	 */
	private String getContextFromId(String id) {
		String context = "";

		if (StringUtils.isNotEmpty(id)) {
			String[] parts = id.split("/");
			if (parts != null && parts.length > 0) {
				context = parts[0];
			}
		}

		return context;
	}

	/**
	 * <p>Returns the name portion of the id.</p>
	 *
	 * @param id the id
	 * @return the name portion of the id
	 */
	private String getNameFromId(String id) {
		String name = "";

		if (StringUtils.isNotEmpty(id)) {
			String[] parts = id.split("/");
			if (parts != null && parts.length > 1) {
				name = parts[1];
			}
		}

		return name;
	}

	/**
	 * <p>Sets the NoticeTemplateManager.</p>
	 *
	 * @param noticeTemplateManager the NoticeTemplateManager
	 */
	public void setNoticeTemplateManager(NoticeTemplateManager noticeTemplateManager) {
		this.noticeTemplateManager = noticeTemplateManager;
	}
}
