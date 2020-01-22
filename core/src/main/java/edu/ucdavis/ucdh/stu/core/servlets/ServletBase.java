package edu.ucdavis.ucdh.stu.core.servlets;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>This is the base class for all of the core servlets.</p>
 */
public abstract class ServletBase extends HttpServlet {
	private static final long serialVersionUID = 1;
	protected ServletContext context = null;
	protected Log log = LogFactory.getLog(getClass());

	/**
	 * <p>Returns the id present in the URL, if any, as an <code>int</code>.</p>
	 *
	 * @param req the <code>HttpServletRequest</code> object
	 * @param servletPath the path to the servlet, including both the leading and trailing "/"
	 * @return the id present in the URL, if any
	 */
	protected int getNumericIdFromUrl(HttpServletRequest req, String servletPath) {
		int id = 0;

		try {
			id = Integer.parseInt(getIdFromUrl(req, servletPath));
		} catch (NumberFormatException e) {
			//
		}
		
		return id;
	}

	/**
	 * <p>Returns the id present in the URL, if any.</p>
	 *
	 * @param req the <code>HttpServletRequest</code> object
	 * @param servletPath the path to the servlet, including both the leading and trailing "/"
	 * @return the id present in the URL, if any
	 */
	protected String getIdFromUrl(HttpServletRequest req, String servletPath) {
		String id = null;

		String basePath = req.getContextPath() + servletPath;
		if (req.getRequestURI().length() > basePath.length()) {
			id = req.getRequestURI().substring(basePath.length());
		}

		return id;
	}

	/**
	 * <p>Returns the name space url.</p>
	 *
	 * @param req the <code>HttpServletRequest</code> object
	 * @return the name space url
	 */
	protected String getNameSpaceUrl(HttpServletRequest req) {
		return (req.getRequestURL().toString().substring(0, req.getRequestURL().toString().indexOf(req.getContextPath())) + req.getContextPath()).toLowerCase();
	}

	/**
	 * Filter the specified string for characters that are sensitive to
	 * HTML interpreters, returning the string with these characters replaced
	 * by the corresponding character entities. Lifted from Struts ResponseUtils
	 *
	 * @param value The string to be filtered and returned
	 * @return The filtered string
	 */
	protected static String filter(String value) {

		if (value == null || value.length() == 0) {
			return value;
		}

		StringBuffer result = null;
		String filtered = null;
		for (int i = 0; i < value.length(); i++) {
			filtered = null;
			switch (value.charAt(i)) {
				case '<':
					filtered = "&lt;";
					break;
				case '>':
					filtered = "&gt;";
					break;
				case '&':
					filtered = "&amp;";
					break;
				case '"':
					filtered = "&quot;";
					break;
				case '\'':
					filtered = "&#39;";
					break;
			}

			if (result == null) {
				if (filtered != null) {
					result = new StringBuffer(value.length() + 50);
					if (i > 0) {
						result.append(value.substring(0, i));
					}
					result.append(filtered);
				}
			} else {
				if (filtered == null) {
					result.append(value.charAt(i));
				} else {
					result.append(filtered);
				}
			}
		}

		return result == null ? value : result.toString();
	}

	/**
	 * <p>Sends the HTTP error code and message, and logs the code and message if enabled.</p>
	 *
	 * @param req the <code>HttpServletRequest</code> object
	 * @param res the <code>HttpServletResponse</code> object
	 * @param errorCode the error code to send
	 * @param errorMessage the error message to send
	 */
	protected void sendError(HttpServletRequest req, HttpServletResponse res, int errorCode, String errorMessage) throws IOException {
		sendError(req, res, errorCode, errorMessage, null);
	}

	/**
	 * <p>Sends the HTTP error code and message, and logs the code and message if enabled.</p>
	 *
	 * @param req the <code>HttpServletRequest</code> object
	 * @param res the <code>HttpServletResponse</code> object
	 * @param errorCode the error code to send
	 * @param errorMessage the error message to send
	 * @param throwable an optional exception
	 */
	protected void sendError(HttpServletRequest req, HttpServletResponse res, int errorCode, String errorMessage, Throwable throwable) throws IOException {
		// log message
		if (throwable != null) {
			log.error("Sending error " + errorCode + "; message=" + errorMessage, throwable);
		} else if (log.isDebugEnabled()) {
			log.debug("Sending error " + errorCode + "; message=" + errorMessage);
		}

		// send error
		res.setContentType("text/plain");
		res.sendError(errorCode, errorMessage);
	}
}
