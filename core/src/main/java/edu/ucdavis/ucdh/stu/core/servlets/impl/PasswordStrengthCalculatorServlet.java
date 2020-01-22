package edu.ucdavis.ucdh.stu.core.servlets.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.ucdavis.ucdh.stu.core.utils.PasswordStrengthCalculator;

/**
 * <p>This provides HTTP access to the PasswordStrengthCalculator.</p>
 */
public class PasswordStrengthCalculatorServlet extends HttpServlet {
	private static final long serialVersionUID = 1;
	private Log log = LogFactory.getLog(getClass());
	private PasswordStrengthCalculator passwordStrengthCalculator = PasswordStrengthCalculator.getPasswordStrengthCalculator();

	/**
	 * <p>The Servlet "service" method.</p>
	 *
	 * @param req the <code>HttpServletRequest</code> object
	 * @param res the <code>HttpServletResponse</code> object
	 * @throws ServletException
	 * @throws IOException
	 */
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/plain");

		String username = "";
		String password = "";
		String confirm = "";
		if (StringUtils.isNotEmpty(req.getParameter("u"))) {
			username = req.getParameter("u");
		}
		if (StringUtils.isNotEmpty(req.getParameter("p"))) {
			password = req.getParameter("p");
		}
		if (StringUtils.isNotEmpty(req.getParameter("c"))) {
			confirm = req.getParameter("c");
		}
		if (log.isDebugEnabled()) {
			log.debug("Calculating password strength for " + username + ", " + password + ", " + confirm);
		}
		int strength = passwordStrengthCalculator.getPasswordStrength(username, password, confirm);
		if (log.isDebugEnabled()) {
			log.debug("Password strength is " + strength);
		}
		res.getWriter().print(strength);
	}
}
