package edu.ucdavis.ucdh.stu.core.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>This is the base class for all of the REST servlets.</p>
 */
public abstract class RestServletBase extends ServletBase {
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
		res.setContentType("text/xml");

		String method = req.getMethod();
		if ("get".equalsIgnoreCase(method)) {
			doGet(req, res);
		} else if ("post".equalsIgnoreCase(method)) {
			doPost(req, res);
		} else if ("put".equalsIgnoreCase(method)) {
			doPut(req, res);
		} else if ("delete".equalsIgnoreCase(method)) {
			doDelete(req, res);
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
	 * <p>The Servlet "doPost()" method.</p>
	 * 
	 * @param req the <code>HttpServletRequest</code> object
	 * @param res the <code>HttpServletResponse</code> object
	 * @throws ServletException
	 * @throws IOException
	 */
	public abstract void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException;

	/**
	 * <p>The Servlet "doPut()" method.</p>
	 *
	 * @param req the <code>HttpServletRequest</code> object
	 * @param res the <code>HttpServletResponse</code> object
	 * @throws ServletException
	 * @throws IOException
	 */
	public abstract void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException;

	/**
	 * <p>The Servlet "doDelete()" method.</p>
	 *
	 * @param req the <code>HttpServletRequest</code> object
	 * @param res the <code>HttpServletResponse</code> object
	 * @throws ServletException
	 * @throws IOException
	 */
	public abstract void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException;
}
