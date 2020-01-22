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

import edu.ucdavis.ucdh.stu.core.beans.Media;
import edu.ucdavis.ucdh.stu.core.manager.MediaManager;

@Path("/media/{context}")
@Component
@Scope("request")
public class MediaResource {
	private Log log = LogFactory.getLog(getClass());
	private MediaManager mediaManager;

	@GET
	@Path("/{folder1}/{folder2}/{folder3}/{folder4}/{folder5}/{name}")
	@Produces("text/xml")
	public Media getMedia(@Context HttpServletRequest req, @PathParam("context") String context, @PathParam("folder1") String folder1, @PathParam("folder2") String folder2, @PathParam("folder3") String folder3, @PathParam("folder4") String folder4, @PathParam("folder5") String folder5, @PathParam("name") String name) throws NotFoundException {
		return fetchMedia(req, context, folder1 + "/" + folder2 + "/" + folder3 + "/" + folder4 + "/" + folder5, name);
	}

	@GET
	@Path("/{folder1}/{folder2}/{folder3}/{folder4}/{name}")
	@Produces("text/xml")
	public Media getMedia(@Context HttpServletRequest req, @PathParam("context") String context, @PathParam("folder1") String folder1, @PathParam("folder2") String folder2, @PathParam("folder3") String folder3, @PathParam("folder4") String folder4, @PathParam("name") String name) throws NotFoundException {
		return fetchMedia(req, context, folder1 + "/" + folder2 + "/" + folder3 + "/" + folder4, name);
	}

	@GET
	@Path("/{folder1}/{folder2}/{folder3}/{name}")
	@Produces("text/xml")
	public Media getMedia(@Context HttpServletRequest req, @PathParam("context") String context, @PathParam("folder1") String folder1, @PathParam("folder2") String folder2, @PathParam("folder3") String folder3, @PathParam("name") String name) throws NotFoundException {
		return fetchMedia(req, context, folder1 + "/" + folder2 + "/" + folder3, name);
	}

	@GET
	@Path("/{folder1}/{folder2}/{name}")
	@Produces("text/xml")
	public Media getMedia(@Context HttpServletRequest req, @PathParam("context") String context, @PathParam("folder1") String folder1, @PathParam("folder2") String folder2, @PathParam("name") String name) throws NotFoundException {
		return fetchMedia(req, context, folder1 + "/" + folder2, name);
	}

	@GET
	@Path("/{folder1}/{name}")
	@Produces("text/xml")
	public Media getMedia(@Context HttpServletRequest req, @PathParam("context") String context, @PathParam("folder1") String folder1, @PathParam("name") String name) throws NotFoundException {
		return fetchMedia(req, context, folder1, name);
	}

	@GET
	@Path("/{name}")
	@Produces("text/xml")
	public Media getMedia(@Context HttpServletRequest req, @PathParam("context") String context, @PathParam("name") String name) throws NotFoundException {
		return fetchMedia(req, context, null, name);
	}

	private Media fetchMedia(HttpServletRequest req, String context, String location, String name) throws NotFoundException {
		Media media = mediaManager.findByContextLocationAndName(context, location, name);
		if (media == null) {
			throw new NotFoundException("This is no media on file named \"" + name + "\" in the context \"" + context + "\" at location \"/" + location + "\".");
		}
		if (log.isDebugEnabled()) {
			log.debug("Returning media " + media.getId());
		}
		return media;
	}

	@GET
	@Path("/")
	@Produces("text/xml")
	public List<Media> getMedias(@Context HttpServletRequest req, @PathParam("context") String context) throws NotFoundException {
		List<Media> medias = mediaManager.findByProperty("context", context);
		if (medias == null || medias.isEmpty()) {
			throw new NotFoundException("There are no medias on file in the context \"" + context + "\".");
		}
		if (log.isDebugEnabled()) {
			log.debug("Returning " + medias.size() + " media(s).");
		}
		return medias;
	}

	@POST
	@Path("/{folder1}/{folder2}/{folder3}/{folder4}/{folder5}/{name}")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/xml")
	public Media saveMedia(@Context HttpServletRequest req,
			@PathParam("context") String context,
			@PathParam("folder1") String folder1,
			@PathParam("folder2") String folder2,
			@PathParam("folder3") String folder3,
			@PathParam("folder4") String folder4,
			@PathParam("folder5") String folder5,
			@PathParam("name") String name,
			@FormParam("description") String description,
			@FormParam("contentType") String contentType,
			@FormParam("content") String content) {
		return updateMedia(req, context, folder1 + "/" + folder2 + "/" + folder3 + "/" + folder4 + "/" + folder5, name, description, contentType, content);
	}

	@POST
	@Path("/{folder1}/{folder2}/{folder3}/{folder4}/{name}")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/xml")
	public Media saveMedia(@Context HttpServletRequest req,
			@PathParam("context") String context,
			@PathParam("folder1") String folder1,
			@PathParam("folder2") String folder2,
			@PathParam("folder3") String folder3,
			@PathParam("folder4") String folder4,
			@PathParam("name") String name,
			@FormParam("description") String description,
			@FormParam("contentType") String contentType,
			@FormParam("content") String content) {
		return updateMedia(req, context, folder1 + "/" + folder2 + "/" + folder3 + "/" + folder4, name, description, contentType, content);
	}

	@POST
	@Path("/{folder1}/{folder2}/{folder3}/{name}")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/xml")
	public Media saveMedia(@Context HttpServletRequest req,
			@PathParam("context") String context,
			@PathParam("folder1") String folder1,
			@PathParam("folder2") String folder2,
			@PathParam("folder3") String folder3,
			@PathParam("name") String name,
			@FormParam("description") String description,
			@FormParam("contentType") String contentType,
			@FormParam("content") String content) {
		return updateMedia(req, context, folder1 + "/" + folder2 + "/" + folder3, name, description, contentType, content);
	}

	@POST
	@Path("/{folder1}/{folder2}/{name}")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/xml")
	public Media saveMedia(@Context HttpServletRequest req,
			@PathParam("context") String context,
			@PathParam("folder1") String folder1,
			@PathParam("folder2") String folder2,
			@PathParam("name") String name,
			@FormParam("description") String description,
			@FormParam("contentType") String contentType,
			@FormParam("content") String content) {
		return updateMedia(req, context, folder1 + "/" + folder2, name, description, contentType, content);
	}

	@POST
	@Path("/{folder1}/{name}")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/xml")
	public Media saveMedia(@Context HttpServletRequest req,
			@PathParam("context") String context,
			@PathParam("folder1") String folder1,
			@PathParam("name") String name,
			@FormParam("description") String description,
			@FormParam("contentType") String contentType,
			@FormParam("content") String content) {
		return updateMedia(req, context, folder1, name, description, contentType, content);
	}

	@POST
	@Path("/{name}")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/xml")
	public Media saveMedia(@Context HttpServletRequest req,
			@PathParam("context") String context,
			@PathParam("name") String name,
			@FormParam("description") String description,
			@FormParam("contentType") String contentType,
			@FormParam("content") String content) {
		return updateMedia(req, context, null, name, description, contentType, content);
	}

	private Media updateMedia(HttpServletRequest req,
			String context,
			String location,
			String name,
			String description,
			String contentType,
			String content) {
		Media media = mediaManager.findByContextLocationAndName(context, location, name);

		String userId = req.getRemoteUser();
		Date rightNow = new Date();
		if (media == null) {
			media = new Media();
			media.setContext(context);
			media.setName(name);
			media.setCreationDate(rightNow);
			media.setCreatedBy(userId);
		}
		media.setDescription(description);
		media.setContentType(contentType);
		if (media.getContentType().startsWith("text")) {
			media.setContent(content);
		} else {
			media.setContent(null);
		}
		media.setLastUpdate(rightNow);
		media.setLastUpdateBy(userId);
		if (log.isDebugEnabled()) {
			log.debug("Updating media " + media.getId());
		}
		mediaManager.save(media);

		return mediaManager.findByContextLocationAndName(context, location, name);
	}

	@DELETE
	@Path("/{folder1}/{folder2}/{folder3}/{folder4}/{folder5}/{name}")
	@Produces("text/xml")
	public String deleteMedia(@Context HttpServletRequest req, @PathParam("context") String context, @PathParam("folder1") String folder1, @PathParam("folder2") String folder2, @PathParam("folder3") String folder3, @PathParam("folder4") String folder4, @PathParam("folder5") String folder5, @PathParam("name") String name) throws NotFoundException {
		return deleteMediaFile(req, context, folder1 + "/" + folder2 + "/" + folder3 + "/" + folder4 + "/" + folder5, name);
	}

	@DELETE
	@Path("/{folder1}/{folder2}/{folder3}/{folder4}/{name}")
	@Produces("text/xml")
	public String deleteMedia(@Context HttpServletRequest req, @PathParam("context") String context, @PathParam("folder1") String folder1, @PathParam("folder2") String folder2, @PathParam("folder3") String folder3, @PathParam("folder4") String folder4, @PathParam("name") String name) throws NotFoundException {
		return deleteMediaFile(req, context, folder1 + "/" + folder2 + "/" + folder3 + "/" + folder4, name);
	}

	@DELETE
	@Path("/{folder1}/{folder2}/{folder3}/{name}")
	@Produces("text/xml")
	public String deleteMedia(@Context HttpServletRequest req, @PathParam("context") String context, @PathParam("folder1") String folder1, @PathParam("folder2") String folder2, @PathParam("folder3") String folder3, @PathParam("name") String name) throws NotFoundException {
		return deleteMediaFile(req, context, folder1 + "/" + folder2 + "/" + folder3, name);
	}

	@DELETE
	@Path("/{folder1}/{folder2}/{name}")
	@Produces("text/xml")
	public String deleteMedia(@Context HttpServletRequest req, @PathParam("context") String context, @PathParam("folder1") String folder1, @PathParam("folder2") String folder2, @PathParam("name") String name) throws NotFoundException {
		return deleteMediaFile(req, context, folder1 + "/" + folder2, name);
	}

	@DELETE
	@Path("/{folder1}/{name}")
	@Produces("text/xml")
	public String deleteMedia(@Context HttpServletRequest req, @PathParam("context") String context, @PathParam("folder1") String folder1, @PathParam("name") String name) throws NotFoundException {
		return deleteMediaFile(req, context, folder1, name);
	}

	@DELETE
	@Path("/{name}")
	@Produces("text/xml")
	public String deleteMedia(@Context HttpServletRequest req, @PathParam("context") String context, @PathParam("name") String name) throws NotFoundException {
		return deleteMediaFile(req, context, null, name);
	}

	private String deleteMediaFile(HttpServletRequest req, String context, String location, String name) throws NotFoundException {
		Media media = mediaManager.findByContextLocationAndName(context, location, name);
		if (media == null) {
			throw new NotFoundException("This is no media on file named \"" + name + "\" in the context \"" + context + "\" at location \"/" + location + "\".");
		}
		if (log.isDebugEnabled()) {
			log.debug("Deleting media " + media.getId());
		}
		mediaManager.delete(media);
		return "<message>Record deleted.</message>";
	}

	/**
	 * @param mediaManager the mediaManager to set
	 */
	public void setMediaManager(MediaManager mediaManager) {
		this.mediaManager = mediaManager;
	}
}
