package edu.ucdavis.ucdh.stu.core.servlets.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import edu.ucdavis.ucdh.stu.core.beans.MessageAddress;
import edu.ucdavis.ucdh.stu.core.beans.Person;
import edu.ucdavis.ucdh.stu.core.service.Notifier;
import edu.ucdavis.ucdh.stu.core.servlets.RestServletBase;

/**
 * <p>This servlet handles NoticeTemplates.</p>
 */
public class SendNotificationServlet extends RestServletBase {
	private static final long serialVersionUID = 1;
	private Notifier notifier;

	/**
	 * <p>The Servlet "doGet()" method.</p>
	 *
	 * @param req the <code>HttpServletRequest</code> object
	 * @param res the <code>HttpServletResponse</code> object
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		sendError(req, res, 405, "Method Not Allowed. Only the \"POST\" method is allowed for this URL.");
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
		// log request, if enabled
		if (log.isDebugEnabled()) {
			String message = "Processing POST request";
			if (StringUtils.isNotEmpty(req.getQueryString())) {
				message += "; query string=" + req.getQueryString();
			}
			log.debug(message);
		}

		// get required parameters
		String sendTo = req.getParameter("sendTo");
		String templateId = req.getParameter("templateId");
		if (StringUtils.isNotEmpty(sendTo) && StringUtils.isNotEmpty(templateId)) {
			// send notification
			sendNotification(req, res);
		} else {
			// send error
			sendError(req, res, 400, "This request is incomplete. Required parameters \"sendTo\" and/or \"templateId\" are missing or invalid.");
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
		sendError(req, res, 405, "Method Not Allowed. Only the \"POST\" method is allowed for this URL.");
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
		sendError(req, res, 405, "Method Not Allowed. Only the \"POST\" method is allowed for this URL.");
	}

	/**
	 * <p>Handles a get request for a single NoticeTemplate.</p>
	 *
	 * @param req the <code>HttpServletRequest</code> object
	 * @param res the <code>HttpServletResponse</code> object
	 */
	private void sendNotification(HttpServletRequest req, HttpServletResponse res) throws IOException {
		boolean usePersonClass = req.getParameter("usePersonClass") != null;
		String sendToProperty = usePersonClass ? "email" : null;
		
		// gather up all of the sendTo addresses
		List<Object> sendTo = new ArrayList<Object>();
		String[] sendToArray = req.getParameterValues("sendTo");
		
		for (int i=0; i<sendToArray.length; i++) {
			if (sendToArray[i] != null)
				sendToArray[i] = sendToArray[i].trim();
			if (StringUtils.isNotEmpty(sendToArray[i])) {
				// The sendTo string should be in form of "email" or "email|name"
				String[] nameEmail = sendToArray[i].split("\\|");
				
				if (usePersonClass) {
					Person person = new Person();
					person.setEmail(nameEmail[0]);
					if (nameEmail.length > 1) {
						person.setName(nameEmail[1]);
					} else {
						person.setName("");
					}
					sendTo.add(person);
				} else {
					sendTo.add(nameEmail[0]);
				}
			}
		}
		String[] sendCcArray = req.getParameterValues("sendCc");
		if (sendCcArray != null && sendCcArray.length > 0) {
			MessageAddress ccAddress = new MessageAddress("mailCc");
			ccAddress.setAddressValues(sendCcArray);
			sendTo.add(ccAddress);
		}
		String[] sendBcArray = req.getParameterValues("sendBc");
		if (sendBcArray != null && sendBcArray.length > 0) {
			MessageAddress bcAddress = new MessageAddress("mailBc");
			bcAddress.setAddressValues(sendBcArray);
			sendTo.add(bcAddress);
		}

		// get the optional sendFrom address
		String sendFrom = req.getParameter("sendFrom");
		// get the template id
		String templateId = req.getParameter("templateId");
		// set up some data for the notice
		Object noticeData = notifier.getStandardNoticeData(req, res);
		// send the notification
		notifier.sendNotification(sendFrom, sendTo, sendToProperty, templateId, noticeData);
		// send success message back to browser
		PrintWriter pw = res.getWriter();
		if (pw != null) {
			pw.print("<message>Notification sent.</message>");
		} else {
			sendError(req, res, 500, "There was a technical error while attempting to process this request. Details of this error have been logged on the server.");
		}
	}

	/**
	 * @return the notifier
	 */
	public Notifier getNotifier() {
		return notifier;
	}

	/**
	 * @param notifier the notifier to set
	 */
	public void setNotifier(Notifier notifier) {
		this.notifier = notifier;
	}
}
