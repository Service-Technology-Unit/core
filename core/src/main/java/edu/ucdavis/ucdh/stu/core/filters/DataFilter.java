package edu.ucdavis.ucdh.stu.core.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DataFilter implements Filter {
	Log log = LogFactory.getLog(getClass());

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,	ServletException {
		// convert request and response objects
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

log.info("[In] DataFilter: user principal is " + req.getRemoteUser());

		chain.doFilter(req, res);
	}

	public void init(FilterConfig arg0)	throws ServletException {
	}

	public void destroy() {
	}
}
