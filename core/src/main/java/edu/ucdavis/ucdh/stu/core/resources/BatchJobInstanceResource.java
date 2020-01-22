package edu.ucdavis.ucdh.stu.core.resources;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javassist.NotFoundException;

import edu.ucdavis.ucdh.stu.core.beans.BatchJobEvent;
import edu.ucdavis.ucdh.stu.core.beans.BatchJobInstance;
import edu.ucdavis.ucdh.stu.core.beans.BatchJobSchedule;
import edu.ucdavis.ucdh.stu.core.beans.BatchJobStatistic;
import edu.ucdavis.ucdh.stu.core.manager.BatchJobInstanceManager;
import edu.ucdavis.ucdh.stu.core.manager.BatchJobScheduleManager;
import edu.ucdavis.ucdh.stu.core.utils.BatchJobService;

@Path("/instance")
@Component
@Scope("request")
public class BatchJobInstanceResource {
	private Log log = LogFactory.getLog(getClass());
	private DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
	private BatchJobScheduleManager batchJobScheduleManager;
	private BatchJobInstanceManager batchJobInstanceManager;

	@GET
	@Path("/{id}")
	@Produces("text/xml")
	public BatchJobInstance getBatchJobInstance(@PathParam("id") Integer id) throws NotFoundException {
		BatchJobInstance batchJobInstance = batchJobInstanceManager.findById(id);
		if (batchJobInstance == null) {
			throw new NotFoundException("No such batchJobInstance.");
		}
		if (log.isDebugEnabled()) {
			log.debug("Returning batchJobInstance " + batchJobInstance.getId());
		}
		return batchJobInstance;
	}
	
	@GET
	@Path("/")
	@Produces("text/xml")
	public List<BatchJobInstance> getBatchJobInstance(@Context HttpServletRequest req,
			@QueryParam("context") String context,
			@QueryParam("name") String name,
			@QueryParam("startDate") String startDateString,
			@QueryParam("endDate") String endDateString) throws NotFoundException {
		List<BatchJobInstance> batchJobInstances = null;
		if (StringUtils.isEmpty(context) &&
				StringUtils.isEmpty(name) &&
				StringUtils.isEmpty(startDateString) &&
				StringUtils.isEmpty(endDateString)) {
			batchJobInstances = batchJobInstanceManager.findAll();
		} else {
			Date startDate = null;
			if (StringUtils.isNotEmpty(startDateString)) {
				startDate = createDate(startDateString);
				if (log.isDebugEnabled()) {
					log.debug("Start date: " + startDate);
				}
			}
			Date endDate = null;
			if (StringUtils.isNotEmpty(endDateString)) {
				endDate = createDate(endDateString);
				if (log.isDebugEnabled()) {
					log.debug("End date: " + endDate);
				}
			}
			batchJobInstances = batchJobInstanceManager.findByContextNameAndDate(context, name, startDate, endDate);
		}
		if (batchJobInstances == null || batchJobInstances.size() == 0) {
			throw new NotFoundException("There are no batchJobInstances on file matching your criteria.");
		}
		if (log.isDebugEnabled()) {
			log.debug("Returning " + batchJobInstances.size() + " batchJobInstances.");
		}
		return batchJobInstances;
	}

	@POST
	@Path("/create/{context}/{jobname}/{schedulename}")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/xml")
	public BatchJobInstance createBatchJobInstance(@Context HttpServletRequest req,
			@PathParam("context") String context,
			@PathParam("jobname") String jobName, 
			@PathParam("schedulename") String schlName,
			@FormParam("description") String description) throws NotFoundException {
		
		BatchJobSchedule batchJobSchedule = batchJobScheduleManager.findByContextJobSchedule(context, jobName, schlName);

		if (batchJobSchedule == null) {
			log.error("The requested batch job schedule is not on file.");
			throw new NotFoundException("The requested batch job schedule is not on file.");
		}

		Date rightNow = new Date();
		String userId = req.getRemoteUser();
		if (StringUtils.isEmpty(userId)) {
			userId = "BatchJobInstanceResource";
		}
		if (StringUtils.isEmpty(description)) {
			description = "New instance created on " + rightNow;
		}
		BatchJobInstance batchJobInstance = new BatchJobInstance();
		batchJobInstance.setBatchJobSchedule(batchJobSchedule);
		batchJobInstance.setStatus(BatchJobService.STATUS_CREATED);
		batchJobInstance.setHost(req.getRemoteHost());
		batchJobInstance.setCreationDate(rightNow);
		batchJobInstance.setCreatedBy(userId);
		batchJobInstance.setLastUpdate(rightNow);
		batchJobInstance.setLastUpdateBy(userId);
		BatchJobEvent batchJobEvent = new BatchJobEvent();
		batchJobEvent.setEvent(BatchJobService.STATUS_CREATED);
		batchJobEvent.setDescription(description);
		batchJobEvent.setCreationDate(rightNow);
		batchJobEvent.setCreatedBy(userId);
		batchJobEvent.setLastUpdate(rightNow);
		batchJobEvent.setLastUpdateBy(userId);
		batchJobInstance.addEvent(batchJobEvent);

		if (log.isDebugEnabled()) {
			log.debug("Creating new batchJobInstance for schedule #" + batchJobSchedule.getId());
		}
		batchJobInstanceManager.save(batchJobInstance);

		return batchJobInstance;
	}

	@POST
	@Path("/{id}")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/xml")
	public BatchJobInstance updateBatchJobInstance(@Context HttpServletRequest req,
			@PathParam("id") Integer id,
			@FormParam("event") String event,
			@FormParam("description") String description,
			@FormParam("label_0") String label_0,
			@FormParam("format_0") String format_0,
			@FormParam("value_0") String value_0,
			@FormParam("label_1") String label_1,
			@FormParam("format_1") String format_1,
			@FormParam("value_1") String value_1,
			@FormParam("label_2") String label_2,
			@FormParam("format_2") String format_2,
			@FormParam("value_2") String value_2,
			@FormParam("label_3") String label_3,
			@FormParam("format_3") String format_3,
			@FormParam("value_3") String value_3,
			@FormParam("label_4") String label_4,
			@FormParam("format_4") String format_4,
			@FormParam("value_4") String value_4,
			@FormParam("label_5") String label_5,
			@FormParam("format_5") String format_5,
			@FormParam("value_5") String value_5,
			@FormParam("label_6") String label_6,
			@FormParam("format_6") String format_6,
			@FormParam("value_6") String value_6,
			@FormParam("label_7") String label_7,
			@FormParam("format_7") String format_7,
			@FormParam("value_7") String value_7,
			@FormParam("label_8") String label_8,
			@FormParam("format_8") String format_8,
			@FormParam("value_8") String value_8,
			@FormParam("label_9") String label_9,
			@FormParam("format_9") String format_9,
			@FormParam("value_9") String value_9) throws NotFoundException {
		BatchJobInstance batchJobInstance = batchJobInstanceManager.findById(id);

		if (batchJobInstance == null) {
			log.error("The requested batch job instance is not on file.");
			throw new NotFoundException("The requested batch job instance is not on file.");
		}

		Date rightNow = new Date();
		String userId = req.getRemoteUser();
		if (StringUtils.isEmpty(userId)) {
			userId = "BatchJobInstanceResource";
		}
		BatchJobEvent batchJobEvent = new BatchJobEvent();
		if (BatchJobService.STATUS_RUNNING.equalsIgnoreCase(event)) {
			batchJobInstance.setStatus(BatchJobService.STATUS_RUNNING);
			batchJobEvent.setEvent(BatchJobService.STATUS_RUNNING);
			batchJobInstance.setStartDateTime(rightNow);
		} else if (BatchJobService.STATUS_COMPLETED.equalsIgnoreCase(event)) {
			batchJobInstance.setStatus(BatchJobService.STATUS_COMPLETED);
			batchJobEvent.setEvent(BatchJobService.STATUS_COMPLETED);
			batchJobInstance.setEndDateTime(rightNow);
			if (batchJobInstance.getStartDateTime() != null) {
				long elapsedTime = rightNow.getTime() - batchJobInstance.getStartDateTime().getTime();
				batchJobInstance.addStatistic(processStatistic("Elapsed time", BatchJobService.FORMAT_DURATION, elapsedTime + "", rightNow, userId));
			}
			if (StringUtils.isNotEmpty(label_0)) {
				batchJobInstance.addStatistic(processStatistic(label_0, format_0, value_0, rightNow, userId));
			}
			if (StringUtils.isNotEmpty(label_1)) {
				batchJobInstance.addStatistic(processStatistic(label_1, format_1, value_1, rightNow, userId));
			}
			if (StringUtils.isNotEmpty(label_2)) {
				batchJobInstance.addStatistic(processStatistic(label_2, format_2, value_2, rightNow, userId));
			}
			if (StringUtils.isNotEmpty(label_3)) {
				batchJobInstance.addStatistic(processStatistic(label_3, format_3, value_3, rightNow, userId));
			}
			if (StringUtils.isNotEmpty(label_4)) {
				batchJobInstance.addStatistic(processStatistic(label_4, format_4, value_4, rightNow, userId));
			}
			if (StringUtils.isNotEmpty(label_5)) {
				batchJobInstance.addStatistic(processStatistic(label_5, format_5, value_5, rightNow, userId));
			}
			if (StringUtils.isNotEmpty(label_6)) {
				batchJobInstance.addStatistic(processStatistic(label_6, format_6, value_6, rightNow, userId));
			}
			if (StringUtils.isNotEmpty(label_7)) {
				batchJobInstance.addStatistic(processStatistic(label_7, format_7, value_7, rightNow, userId));
			}
			if (StringUtils.isNotEmpty(label_8)) {
				batchJobInstance.addStatistic(processStatistic(label_8, format_8, value_8, rightNow, userId));
			}
			if (StringUtils.isNotEmpty(label_9)) {
				batchJobInstance.addStatistic(processStatistic(label_9, format_9, value_9, rightNow, userId));
			}
		} else if (BatchJobService.STATUS_FAILED.equalsIgnoreCase(event)) {
			batchJobInstance.setStatus(BatchJobService.STATUS_FAILED);
			batchJobEvent.setEvent(BatchJobService.STATUS_FAILED);
			batchJobInstance.setEndDateTime(rightNow);
		} else if (BatchJobService.STATUS_ABANDONED.equalsIgnoreCase(event)) {
			batchJobInstance.setStatus(BatchJobService.STATUS_ABANDONED);
			batchJobEvent.setEvent(BatchJobService.STATUS_ABANDONED);
			batchJobInstance.setEndDateTime(rightNow);
		} else {
			batchJobInstance.setStatus(event);
			batchJobEvent.setEvent(event);
		}
		batchJobEvent.setDescription(description);
		batchJobEvent.setCreationDate(rightNow);
		batchJobEvent.setCreatedBy(userId);
		batchJobEvent.setLastUpdate(rightNow);
		batchJobEvent.setLastUpdateBy(userId);
		batchJobInstance.addEvent(batchJobEvent);
		batchJobInstance.setLastUpdate(rightNow);
		batchJobInstance.setLastUpdateBy(userId);
		if (log.isDebugEnabled()) {
			log.debug("Updating batchJobInstance #" + id);
		}
		batchJobInstanceManager.save(batchJobInstance);

		return batchJobInstanceManager.findById(id);
	}

	@DELETE
	@Path("/{id}")
	@Produces("text/xml")
	public String deleteBatchJobInstance(@PathParam("id") Integer id) throws NotFoundException {
		BatchJobInstance batchJobInstance = batchJobInstanceManager.findById(id);
		if (batchJobInstance == null) {
			throw new NotFoundException("No such batchJobInstance.");
		}
		if (log.isDebugEnabled()) {
			log.debug("Deleting batchJobInstance " + batchJobInstance.getId());
		}
		batchJobInstanceManager.delete(batchJobInstance);
		return "<message>Batch Job Instance deleted.</message>";
	}

	/**
	 * <p>Processes the batch jobs statistics posted with the job completion event.</p>
	 * 
	 * @param req the HttpServletRequest object
	 * @param rightNow the current date/time
	 * @param userId the current user
	 * @return a List of job statistics
	 */
	private BatchJobStatistic processStatistic(String label, String formatString, String value, Date rightNow, String userId) {
		BatchJobStatistic batchJobStatistic = new BatchJobStatistic();

		String format = BatchJobService.FORMAT_INTEGER;
		if (BatchJobService.validFormat(formatString)) {
			format = formatString;
		}
		batchJobStatistic.setLabel(label);
		batchJobStatistic.setFormat(format);
		batchJobStatistic.setValue(new BigInteger(value));
		batchJobStatistic.setCreationDate(rightNow);
		batchJobStatistic.setCreatedBy(userId);
		batchJobStatistic.setLastUpdate(rightNow);
		batchJobStatistic.setLastUpdateBy(userId);

		return batchJobStatistic;
	}

	/**
	 * <p>Creates a Date object from a date string.</p>
	 * 
	 * @param string the date string
	 * @return a Date object created from the date string
	 */
	private Date createDate(String string) {
		Date date = null;

		if (StringUtils.isNotEmpty(string)) {
			try {
				date = df.parse(string);           
			} catch (ParseException e) {
				// no one cares!
			}
		}

		return date;
	}

	/**
	 * @param batchJobScheduleManager the batchJobScheduleManager to set
	 */
	public void setBatchJobScheduleManager(BatchJobScheduleManager batchJobScheduleManager) {
		this.batchJobScheduleManager = batchJobScheduleManager;
	}

	/**
	 * @param batchJobInstanceManager the batchJobInstanceManager to set
	 */
	public void setBatchJobInstanceManager(BatchJobInstanceManager batchJobInstanceManager) {
		this.batchJobInstanceManager = batchJobInstanceManager;
	}
}
