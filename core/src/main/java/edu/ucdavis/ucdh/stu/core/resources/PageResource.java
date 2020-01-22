package edu.ucdavis.ucdh.stu.core.resources;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javassist.NotFoundException;

import edu.ucdavis.ucdh.stu.core.beans.Page;
import edu.ucdavis.ucdh.stu.core.manager.PageManager;

@Path("/page/{context}")
@Component
@Scope("request")
public class PageResource {
	private Log log = LogFactory.getLog(getClass());
	private PageManager pageManager;

	@GET
	@Path("/{folder1}/{folder2}/{folder3}/{folder4}/{folder5}/{name}")
	@Produces("text/xml")
	public Page getPage(@Context HttpServletRequest req, @PathParam("context") String context, @PathParam("folder1") String folder1, @PathParam("folder2") String folder2, @PathParam("folder3") String folder3, @PathParam("folder4") String folder4, @PathParam("folder5") String folder5, @PathParam("name") String name) throws NotFoundException {
		return fetchPage(req, context, folder1 + "/" + folder2 + "/" + folder3 + "/" + folder4 + "/" + folder5 + "/" + name);
	}

	@GET
	@Path("/{folder1}/{folder2}/{folder3}/{folder4}/{name}")
	@Produces("text/xml")
	public Page getPage(@Context HttpServletRequest req, @PathParam("context") String context, @PathParam("folder1") String folder1, @PathParam("folder2") String folder2, @PathParam("folder3") String folder3, @PathParam("folder4") String folder4, @PathParam("name") String name) throws NotFoundException {
		return fetchPage(req, context, folder1 + "/" + folder2 + "/" + folder3 + "/" + folder4 + "/" + name);
	}

	@GET
	@Path("/{folder1}/{folder2}/{folder3}/{name}")
	@Produces("text/xml")
	public Page getPage(@Context HttpServletRequest req, @PathParam("context") String context, @PathParam("folder1") String folder1, @PathParam("folder2") String folder2, @PathParam("folder3") String folder3, @PathParam("name") String name) throws NotFoundException {
		return fetchPage(req, context, folder1 + "/" + folder2 + "/" + folder3 + "/" + name);
	}

	@GET
	@Path("/{folder1}/{folder2}/{name}")
	@Produces("text/xml")
	public Page getPage(@Context HttpServletRequest req, @PathParam("context") String context, @PathParam("folder1") String folder1, @PathParam("folder2") String folder2, @PathParam("name") String name) throws NotFoundException {
		return fetchPage(req, context, folder1 + "/" + folder2 + "/" + name);
	}

	@GET
	@Path("/{folder1}/{name}")
	@Produces("text/xml")
	public Page getPage(@Context HttpServletRequest req, @PathParam("context") String context, @PathParam("folder1") String folder1, @PathParam("name") String name) throws NotFoundException {
		return fetchPage(req, context, folder1 + "/" + name);
	}

	@GET
	@Path("/{name}")
	@Produces("text/xml")
	public Page getPage(@Context HttpServletRequest req, @PathParam("context") String context, @PathParam("name") String name) throws NotFoundException {
		return fetchPage(req, context, name);
	}

	private Page fetchPage(HttpServletRequest req, String context, String name) throws NotFoundException {
		Page page = pageManager.findByContextAndName(context, name);
		if (page == null) {
			throw new NotFoundException("This is no page on file named \"" + name + "\" in the context \"" + context + "\".");
		}
		if (log.isDebugEnabled()) {
			log.debug("Returning page " + page.getId());
		}
		return page;
	}

	@GET
	@Path("/")
	@Produces("text/xml")
	public List<Page> getPages(@Context HttpServletRequest req, @PathParam("context") String context) throws NotFoundException {
		List<Page> pages = pageManager.findByProperty("context", context);
		if (pages == null || pages.isEmpty()) {
			throw new NotFoundException("There are no pages on file in the context \"" + context + "\".");
		}
		if (log.isDebugEnabled()) {
			log.debug("Returning " + pages.size() + " page(s).");
		}
		return pages;
	}

	@POST
	@Path("/{folder1}/{folder2}/{folder3}/{folder4}/{folder5}/{name}")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/xml")
	public Page savePage(@Context HttpServletRequest req,
			@PathParam("context") String context,
			@PathParam("folder1") String folder1,
			@PathParam("folder2") String folder2,
			@PathParam("folder3") String folder3,
			@PathParam("folder4") String folder4,
			@PathParam("folder5") String folder5,
			@PathParam("name") String name,
			@FormParam("description") String description,
			@FormParam("title") String title,
			@FormParam("head") String head,
			@FormParam("body") String body) {
		return updatePage(req, context, folder1 + "/" + folder2 + "/" + folder3 + "/" + folder4 + "/" + folder5 + "/" + name, description, title, head, body);
	}

	@POST
	@Path("/{folder1}/{folder2}/{folder3}/{folder4}/{name}")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/xml")
	public Page savePage(@Context HttpServletRequest req,
			@PathParam("context") String context,
			@PathParam("folder1") String folder1,
			@PathParam("folder2") String folder2,
			@PathParam("folder3") String folder3,
			@PathParam("folder4") String folder4,
			@PathParam("name") String name,
			@FormParam("description") String description,
			@FormParam("title") String title,
			@FormParam("head") String head,
			@FormParam("body") String body) {
		return updatePage(req, context, folder1 + "/" + folder2 + "/" + folder3 + "/" + folder4 + "/" + name, description, title, head, body);
	}

	@POST
	@Path("/{folder1}/{folder2}/{folder3}/{name}")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/xml")
	public Page savePage(@Context HttpServletRequest req,
			@PathParam("context") String context,
			@PathParam("folder1") String folder1,
			@PathParam("folder2") String folder2,
			@PathParam("folder3") String folder3,
			@PathParam("name") String name,
			@FormParam("description") String description,
			@FormParam("title") String title,
			@FormParam("head") String head,
			@FormParam("body") String body) {
		return updatePage(req, context, folder1 + "/" + folder2 + "/" + folder3 + "/" + name, description, title, head, body);
	}

	@POST
	@Path("/{folder1}/{folder2}/{name}")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/xml")
	public Page savePage(@Context HttpServletRequest req,
			@PathParam("context") String context,
			@PathParam("folder1") String folder1,
			@PathParam("folder2") String folder2,
			@PathParam("name") String name,
			@FormParam("description") String description,
			@FormParam("title") String title,
			@FormParam("head") String head,
			@FormParam("body") String body) {
		return updatePage(req, context, folder1 + "/" + folder2 + "/" + name, description, title, head, body);
	}

	@POST
	@Path("/{folder1}/{name}")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/xml")
	public Page savePage(@Context HttpServletRequest req,
			@PathParam("context") String context,
			@PathParam("folder1") String folder1,
			@PathParam("name") String name,
			@FormParam("description") String description,
			@FormParam("title") String title,
			@FormParam("head") String head,
			@FormParam("body") String body) {
		return updatePage(req, context, folder1 + "/" + name, description, title, head, body);
	}

	@POST
	@Path("/{name}")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/xml")
	public Page savePage(@Context HttpServletRequest req,
			@PathParam("context") String context,
			@PathParam("name") String name,
			@FormParam("description") String description,
			@FormParam("title") String title,
			@FormParam("head") String head,
			@FormParam("body") String body) {
		return updatePage(req, context, name, description, title, head, body);
	}

	private Page updatePage(HttpServletRequest req,
			String context,
			String name,
			String description,
			String title,
			String head,
			String body) {
		Page page = pageManager.findByContextAndName(context, name);

		String userId = req.getRemoteUser();
		Date rightNow = new Date();
		if (page == null) {
			page = new Page();
			page.setContext(context);
			page.setName(name);
			page.setCreationDate(rightNow);
			page.setCreatedBy(userId);
		}
		page.setDescription(description);
		page.setTitle(title);
		page.setHead(head);
		page.setBody(body);
		page.setLastUpdate(rightNow);
		page.setLastUpdateBy(userId);
		if (log.isDebugEnabled()) {
			log.debug("Updating page " + page.getId());
		}
		pageManager.save(page);

		return pageManager.findByContextAndName(context, name);
	}

	@DELETE
	@Path("/{name}")
	@Produces("text/xml")
	public String deletePage(@PathParam("context") String context, @PathParam("name") String name) throws NotFoundException {
		Page page = pageManager.findByContextAndName(context, name);
		if (page == null) {
			throw new NotFoundException("This is no page on file named \"" + name + "\" in the context \"" + context + "\".");
		}
		if (log.isDebugEnabled()) {
			log.debug("Deleting page " + page.getId());
		}
		pageManager.delete(page);
		return "<message>Property deleted.</message>";
	}

	/**
	 * @param pageManager the pageManager to set
	 */
	public void setPageManager(PageManager pageManager) {
		this.pageManager = pageManager;
	}
}
