package edu.ucdavis.ucdh.stu.core.filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.ucdavis.ucdh.stu.core.beans.Media;
import edu.ucdavis.ucdh.stu.core.beans.Page;
import edu.ucdavis.ucdh.stu.core.manager.MediaManager;
import edu.ucdavis.ucdh.stu.core.manager.PageManager;

/**
 * <p>Dynamic Page Content Filter for Velocity pages.</p>
 */
public class DynamicPageContentFilter  implements Filter {
	Log log = LogFactory.getLog(getClass());
	private String context = "global";
	private String layoutFileName = "layout.vm";
	private String pageTemplate;
	private String pageTemplateName;
	private PageManager pageManager;
	private MediaManager mediaManager;
	private Map<String,Page> pageCache = new HashMap<String,Page>();
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,	ServletException {
		if (log.isDebugEnabled()) {
			log.debug("DynamicPageContentFilter.doFilter() begins.");
		}
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		if ("true".equalsIgnoreCase(req.getParameter("flushcache"))) {
			pageTemplate = null;
			pageCache = new HashMap<String,Page>();
		}
		if (pageTemplateExists()) {
			req.setAttribute("pageTemplate", pageTemplate);
		}
		Map<String,String> pathInfo = getPathInfo(req);
		String name = pathInfo.get("name");
		String pageName = pathInfo.get("pageName");
		String returnPath = pathInfo.get("returnPath");
		if (log.isDebugEnabled()) {
			log.debug("Database key is " + name);
		}
		
		Page page = getPage(name);
		if (page != null) {
			if (log.isDebugEnabled()) {
				log.debug("Page \"" + name + "\" found in database.");
			}
			req.setAttribute("pageName", page.getName());
			req.setAttribute("pageTitle", page.getTitle());
			req.setAttribute("pageHead", page.getHead());
			req.setAttribute("pageBody", page.getBody());
		} else {
			if (log.isDebugEnabled()) {
				log.debug("Page \"" + name + "\" not found in database.");
			}
			page = getPage("notfound");
			if (page != null) {
				if (log.isDebugEnabled()) {
					log.debug("Sending \"Not Found\" page from database.");
				}
				req.setAttribute("pageName", page.getName());
				req.setAttribute("pageTitle", page.getTitle());
				req.setAttribute("pageHead", page.getHead());
				req.setAttribute("pageBody", page.getBody());
			} else {
				if (log.isDebugEnabled()) {
					log.debug("Sending hard-coded \"Not Found\" page from filter.");
				}
				req.setAttribute("pageName", "Page Not Found");
				req.setAttribute("pageTitle", "Page Not Found");
				req.setAttribute("pageHead", "");
				req.setAttribute("pageBody", "<p>The Web page you are attempting to view (" + pageName + ") is not available. You may want to check the spelling of the address.</p>");
			}
		}
		
		if (log.isDebugEnabled()) {
			log.debug("DynamicPageContentFilter.doFilter() ends; redirecting to " + pageTemplate);
		}

		req.getRequestDispatcher(returnPath + layoutFileName).forward(req, res);
	}

	private Map<String,String> getPathInfo(HttpServletRequest req) {
		Map<String,String> pathInfo = new HashMap<String,String>();

		String name = "";
		String pageName = "";
		String location = "";
		String returnPath = "";
		String uri = req.getRequestURI();
		String[] parts = uri.split("/");
		int nameIndex = parts.length - 1;
		pageName = parts[nameIndex];
		int dot = pageName.indexOf(".");
		if (dot != -1) {
			name = pageName.substring(0, dot);
		} else {
			name = pageName;
		}
		if (nameIndex > 2) {
			for (int i=2; i<nameIndex; i++) {
				location += parts[i];
				location += "/";
				returnPath += "../";
			}
		}
		pathInfo.put("name", location + name);
		pathInfo.put("pageName", location + pageName);
		pathInfo.put("returnPath", returnPath);

		return pathInfo;
	}

	private Page getPage(String name) {
		Page page = pageCache.get(name);
		if (page == null) {
			page = pageManager.findByContextAndName(context, name);
			if (page != null) {
				pageCache.put(name, page);
			}
		}
		return page;
	}

	private boolean pageTemplateExists() {
		boolean exists = false;

		if (StringUtils.isNotEmpty(pageTemplate)) {
			exists = true;
		} else if (StringUtils.isNotEmpty(pageTemplateName)) {
			if (mediaManager != null) {
				String location = "";
				String name = pageTemplateName;
				if (pageTemplateName.indexOf("/") != -1) {
					location = pageTemplateName.substring(0, pageTemplateName.lastIndexOf("/"));
					name = pageTemplateName.substring(pageTemplateName.lastIndexOf("/") + 1);
				}
				Media media = mediaManager.findByContextLocationAndName(context, location, name);
				if (media != null) {
					pageTemplate = media.getContent();
					if (StringUtils.isNotEmpty(pageTemplate)) {
						exists = true;
						if (log.isDebugEnabled()) {
							log.debug("Using page template " + pageTemplateName);
						}
					} else {
						log.error("Page template \"" + pageTemplateName + "\" media record found, but content is empty; no page template will be used.");
					}
				} else {
					log.error("Page template \"" + pageTemplateName + "\" media record not found; no page template will be used.");
				}
			} else {
				log.error("Configured for page template \"" + pageTemplateName + "\", but mediaManager is null; no page template will be used.");
			}
		} else {
			if (log.isDebugEnabled()) {
				log.debug("No page template configured for this application.");
			}
		}

		return exists;
	}

	public void init(FilterConfig arg0)	throws ServletException {
	}

	public void destroy() {
	}

	/**
	 * @param context the context to set
	 */
	public void setContext(String context) {
		this.context = context;
	}

	/**
	 * @param layoutFileName the layoutFileName to set
	 */
	public void setLayoutFileName(String layoutFileName) {
		this.layoutFileName = layoutFileName;
	}

	/**
	 * @param pageTemplateName the pageTemplateName to set
	 */
	public void setPageTemplateName(String pageTemplateName) {
		this.pageTemplateName = pageTemplateName;
	}

	/**
	 * @param pageManager the pageManager to set
	 */
	public void setPageManager(PageManager pageManager) {
		this.pageManager = pageManager;
	}

	/**
	 * @param mediaManager the mediaManager to set
	 */
	public void setMediaManager(MediaManager mediaManager) {
		this.mediaManager = mediaManager;
	}
}
