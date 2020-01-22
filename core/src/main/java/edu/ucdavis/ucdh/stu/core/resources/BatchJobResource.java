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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javassist.NotFoundException;

import edu.ucdavis.ucdh.stu.core.beans.BatchJob;
import edu.ucdavis.ucdh.stu.core.manager.BatchJobManager;

@Path("/job")
@Component
@Scope("request")
public class BatchJobResource {
	private Log log = LogFactory.getLog(getClass());
	private BatchJobManager batchJobManager;

	@GET
	@Path("/{context}/{name}")
	@Produces("text/xml")
	public BatchJob getBatchJob(@PathParam("context") String context, @PathParam("name") String name) throws NotFoundException {
		BatchJob batchJob = batchJobManager.findByContextAndName(context, name);
		if (batchJob == null) {
			throw new NotFoundException("No such batchJob.");
		}
		if (log.isDebugEnabled()) {
			log.debug("Returning batchJob " + batchJob.getId());
		}
		return batchJob;
	}
	
	@GET
	@Path("/{context}")
	@Produces("text/xml")
	public List<BatchJob> getBatchJob(@PathParam("context") String context) throws NotFoundException {
		List<BatchJob> batchJobs = batchJobManager.findByProperty("context", context);
		if (batchJobs == null || batchJobs.size() == 0) {
			throw new NotFoundException("No such batchJobs.");
		}
		if (log.isDebugEnabled()) {
			log.debug("Returning " + batchJobs.size() + " batchJobs.");
		}
		return batchJobs;
	}
	
	@GET
	@Path("/")
	@Produces("text/xml")
	public List<BatchJob> getBatchJob() throws NotFoundException {
		List<BatchJob> batchJobs = batchJobManager.findAll();
		if (batchJobs == null || batchJobs.size() == 0) {
			throw new NotFoundException("No such batchJobs.");
		}
		if (log.isDebugEnabled()) {
			log.debug("Returning " + batchJobs.size() + " batchJobs.");
		}
		return batchJobs;
	}

	@POST
	@Path("/{context}/{name}")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/xml")
	public BatchJob saveBatchJob(@Context HttpServletRequest req,
			@PathParam("context") String context,
			@PathParam("name") String name,
			@FormParam("description") String description) {
		String userId = req.getRemoteUser();
		if (StringUtils.isEmpty(userId)) {
			userId = name;
		}
		BatchJob batchJob = batchJobManager.findByContextAndName(context, name);

		Date rightNow = new Date();
		if (batchJob == null) {
			batchJob = new BatchJob();
			batchJob.setContext(context);
			batchJob.setName(name);
			batchJob.setCreationDate(rightNow);
			batchJob.setCreatedBy(userId);
		}
		batchJob.setDescription(description);
		batchJob.setLastUpdate(rightNow);
		batchJob.setLastUpdateBy(userId);
		if (log.isDebugEnabled()) {
			log.debug("Updating batchJob " + batchJob.getId());
		}
		batchJobManager.save(batchJob);

		return batchJobManager.findByContextAndName(context, name);
	}

	@DELETE
	@Path("/{context}/{name}")
	@Produces("text/xml")
	public String deleteBatchJob(@PathParam("context") String context, @PathParam("name") String name) throws NotFoundException {
		BatchJob batchJob = batchJobManager.findByContextAndName(context, name);
		if (batchJob == null) {
			throw new NotFoundException("No such batchJob.");
		}
		if (log.isDebugEnabled()) {
			log.debug("Deleting batchJob " + batchJob.getId());
		}
		batchJobManager.delete(batchJob);
		return "<message>Batch Job deleted.</message>";
	}

	/**
	 * @param batchJobManager the batchJobManager to set
	 */
	public void setBatchJobManager(BatchJobManager batchJobManager) {
		this.batchJobManager = batchJobManager;
	}
}
