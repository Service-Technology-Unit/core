package edu.ucdavis.ucdh.stu.core.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

/**
 * <p>This is the base class for all of the Javascript servlets.</p>
 */
public abstract class JavascriptServletBase extends ServletBase {
	private static final long serialVersionUID = 1;

	/**
	 * <p>The Servlet "service" method.</p>
	 *
	 * @param req the <code>HttpServletRequest</code> object
	 * @param res the <code>HttpServletResponse</code> object
	 * @throws ServletException
	 * @throws IOException
	 */
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/javascript");

		String method = req.getMethod();
		if ("get".equalsIgnoreCase(method)) {
			doGet(req, res);
		} else {
			res.sendError(405, "Method Not Allowed. The \"" + method.toUpperCase() + "\" is not appropriate for this resource.");
		}
	}

	/**
	 * <p>The Servlet "doGet()" method.</p>
	 *
	 * @param req the <code>HttpServletRequest</code> object
	 * @param res the <code>HttpServletResponse</code> object
	 * @throws ServletException
	 * @throws IOException
	 */
	public abstract void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException;

	/**
	 * <p>Returns the id present in the URL, if any.</p>
	 *
	 * @param req the <code>HttpServletRequest</code> object
	 * @param servletPath the path to the servlet, including both the leading and trailing "/"
	 * @return the id present in the URL, if any
	 */
	protected String getIdFromUrl(HttpServletRequest req, String servletPath) {
		String id = super.getIdFromUrl(req, servletPath);
		if (StringUtils.isNotEmpty(id)) {
			if (id.indexOf(".js") != -1) {
				id = id.substring(0, id.indexOf(".js"));
			}
		}

		return id;
	}
}
