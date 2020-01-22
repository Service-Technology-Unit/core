package edu.ucdavis.ucdh.stu.core.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Generic servlet class that holds the Spring WebApplicationContext in its
 * init method. It delegates to another servlet defined in the Spring context
 * and injected through Spring. A servlet using a delegate should be defined
 * with scope 'prototype'; otherwise, when the container tries to instantiate
 * more than one instance of the generic servlet the delegate will still
 * remain one singleton.
 */
public class GenericSpringServlet extends HttpServlet {
	private static final long serialVersionUID = 1;
	private HttpServlet delegate;

	/**
	 * just delegate to the "spring" servlet.
	 * 
	 * @inheritDoc
	 * @see javax.servlet.Servlet#service(javax.servlet.ServletRequest,
	 *	  javax.servlet.ServletResponse)
	 */
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		delegate.service(req,res);
	}

	/**
	 * Retrieves a servlet bean from the context with the same name that the
	 * generic servlet is configured. If one instance of the generic servlet
	 * is named 'test', a spring bean with id 'test' is searched and used as
	 * delegate.
	 * 
	 * @inheritDoc
	 * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
	 */
	public void init() throws ServletException {
		ServletConfig config = getServletConfig();
		delegate = (HttpServlet) WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext()).getBean(config.getServletName());
		delegate.init(config);
		delegate.init();
	}
}
