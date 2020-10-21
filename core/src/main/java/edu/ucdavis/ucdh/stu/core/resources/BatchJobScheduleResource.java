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
import edu.ucdavis.ucdh.stu.core.beans.BatchJobSchedule;
import edu.ucdavis.ucdh.stu.core.manager.BatchJobManager;
import edu.ucdavis.ucdh.stu.core.manager.BatchJobScheduleManager;

@Path("/schedule")
@Component
@Scope("request")
public class BatchJobScheduleResource {
	private Log log = LogFactory.getLog(getClass());
	private BatchJobManager batchJobManager;
	private BatchJobScheduleManager batchJobScheduleManager;
	
	@GET
	@Path("/{context}/{name}")
	@Produces("application/xml")
	public List<BatchJobSchedule> getBatchJobSchedule(@PathParam("context") String context, @PathParam("name") String name) throws NotFoundException {
		List<BatchJobSchedule> batchJobSchedules = batchJobScheduleManager.findByContextAndName(context, name);
		if (batchJobSchedules == null || batchJobSchedules.size() == 0) {
			throw new NotFoundException("There are no batchJobSchedules for this job.");
		}
		if (log.isDebugEnabled()) {
			log.debug("Returning " + batchJobSchedules.size() + " batchJobSchedules.");
		}
		return batchJobSchedules;
	}
	
	@GET
	@Path("/{context}/{jobname}/{schedulename}")
	@Produces("application/xml")
	public BatchJobSchedule getBatchJobSchedule(@PathParam("context") String context,
			@PathParam("jobname") String jobName, @PathParam("schedulename") String schlName) throws NotFoundException {
		BatchJobSchedule batchJobSchedule = batchJobScheduleManager.findByContextJobSchedule(context, jobName, schlName);
		if (batchJobSchedule == null) {
			throw new NotFoundException("No such batchJobSchedule.");
		}
		if (log.isDebugEnabled()) {
			log.debug("Returning batchJobSchedule " + batchJobSchedule.getId());
		}
		return batchJobSchedule;
	}
	
	@GET
	@Path("/")
	@Produces("application/xml")
	public List<BatchJobSchedule> getBatchJobSchedule() throws NotFoundException {
		List<BatchJobSchedule> batchJobSchedules = batchJobScheduleManager.findAll();
		if (batchJobSchedules == null || batchJobSchedules.size() == 0) {
			throw new NotFoundException("No such batchJobSchedules.");
		}
		if (log.isDebugEnabled()) {
			log.debug("Returning " + batchJobSchedules.size() + " batchJobSchedules.");
		}
		return batchJobSchedules;
	}

	@POST
	@Path("/{context}/{jobname}")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("application/xml")
	public BatchJobSchedule createBatchJobSchedule(@Context HttpServletRequest req,
			@PathParam("context") String context,
			@PathParam("jobname") String jobName,
			@FormParam("name") String name,
			@FormParam("description") String description,
			@FormParam("status") String status,
			@FormParam("schedule") String schedule) throws NotFoundException {
		BatchJob batchJob = batchJobManager.findByContextAndName(context, jobName);

		if (batchJob == null) {
			log.error("There is no batch job on file with the context \"" + context + "\" and the name of \"" + jobName + "\".");
			throw new NotFoundException("There is no batch job on file with the context \"" + context + "\" and the name of \"" + jobName + "\".");
		}

		Date rightNow = new Date();
		String userId = req.getRemoteUser();
		if (StringUtils.isEmpty(userId)) {
			userId = "BatchJobScheduleResource";
		}
		BatchJobSchedule batchJobSchedule = new BatchJobSchedule();
		batchJobSchedule.setBatchJob(batchJob);
		batchJobSchedule.setName(name);
		batchJobSchedule.setDescription(description);
		batchJobSchedule.setStatus(status);
		batchJobSchedule.setSchedule(schedule);
		batchJobSchedule.setCreationDate(rightNow);
		batchJobSchedule.setCreatedBy(userId);
		batchJobSchedule.setLastUpdate(rightNow);
		batchJobSchedule.setLastUpdateBy(userId);
		if (log.isDebugEnabled()) {
			log.debug("Creating new batchJobSchedule for batch job #" + batchJob.getId());
		}
		batchJobScheduleManager.save(batchJobSchedule);

		return batchJobSchedule;
	}

	@POST
	@Path("/{context}/{jobname}/{schedulename}")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("application/xml")
	public BatchJobSchedule saveBatchJobSchedule(@Context HttpServletRequest req,
			@PathParam("context") String context,
			@PathParam("jobname") String jobName, 
			@PathParam("schedulename") String schlName,
			@FormParam("name") String name,
			@FormParam("description") String description,
			@FormParam("status") String status,
			@FormParam("schedule") String schedule) throws NotFoundException {
		BatchJobSchedule batchJobSchedule = batchJobScheduleManager.findByContextJobSchedule(context, jobName, schlName);

		if (batchJobSchedule == null) {
			log.error("The requested batch job schedule is not on file.");
			throw new NotFoundException("The requested batch job schedule is not on file.");
		}

		Date rightNow = new Date();
		String userId = req.getRemoteUser();
		if (StringUtils.isEmpty(userId)) {
			userId = "BatchJobScheduleResource";
		}
		batchJobSchedule.setName(name);
		batchJobSchedule.setDescription(description);
		batchJobSchedule.setStatus(status);
		batchJobSchedule.setSchedule(schedule);
		batchJobSchedule.setLastUpdate(rightNow);
		batchJobSchedule.setLastUpdateBy(userId);
		if (log.isDebugEnabled()) {
			log.debug("Updating batchJobSchedule #" + batchJobSchedule.getId());
		}
		batchJobScheduleManager.save(batchJobSchedule);

		return batchJobScheduleManager.findByContextJobSchedule(context, jobName, schlName);
	}

	@DELETE
	@Path("/{context}/{jobname}/{schedulename}")
	@Produces("application/xml")
	public String deleteBatchJobSchedule(@PathParam("context") String context,
			@PathParam("jobname") String jobName, 
			@PathParam("schedulename") String schlName) throws NotFoundException {
		BatchJobSchedule batchJobSchedule = batchJobScheduleManager.findByContextJobSchedule(context, jobName, schlName);
		if (batchJobSchedule == null) {
			throw new NotFoundException("No such batchJobSchedule.");
		}
		if (log.isDebugEnabled()) {
			log.debug("Deleting batchJobSchedule " + batchJobSchedule.getId());
		}
		batchJobScheduleManager.delete(batchJobSchedule);
		return "<message>Batch Job Schedule deleted.</message>";
	}

	/**
	 * @param batchJobManager the batchJobManager to set
	 */
	public void setBatchJobManager(BatchJobManager batchJobManager) {
		this.batchJobManager = batchJobManager;
	}

	/**
	 * @param batchJobScheduleManager the batchJobScheduleManager to set
	 */
	public void setBatchJobScheduleManager(BatchJobScheduleManager batchJobScheduleManager) {
		this.batchJobScheduleManager = batchJobScheduleManager;
	}
}
