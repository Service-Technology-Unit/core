package edu.ucdavis.ucdh.stu.core.batch.job;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.ucdavis.ucdh.stu.core.batch.SpringBatchJob;
import edu.ucdavis.ucdh.stu.core.beans.BatchJobEvent;
import edu.ucdavis.ucdh.stu.core.beans.BatchJobInstance;
import edu.ucdavis.ucdh.stu.core.manager.BatchJobInstanceManager;
import edu.ucdavis.ucdh.stu.core.service.Notifier;
import edu.ucdavis.ucdh.stu.core.utils.BatchJobService;
import edu.ucdavis.ucdh.stu.core.utils.BatchJobServiceStatistic;

/**
 * <p>Generates various Java modules based on run parameters.</p>
 */
public class PeriodicBatchJobReview implements SpringBatchJob {
	private Log log = LogFactory.getLog(getClass().getName());
	private BatchJobInstanceManager batchJobInstanceManager = null;
	private Notifier notifier = null;
	private DataSource dataSource = null;
	private String alertTemplateId = null;
	private String alertRecipients = null;
	private int allJobs = 0;
	private int abandonedJobs = 0;
	private int updatedJobs = 0;
	private int failedUpdates = 0;
	private int failedJobs = 0;
	private int noticesSent = 0;

	/**
	 * <p>Main run method.</p>
	 * 
	 * @throws SQLException 
	 */
	public List<BatchJobServiceStatistic> run(String[] args, int batchJobInstanceId) throws SQLException {
		log.info("PeriodicBatchJobReview starting ...");
		log.info(" ");

		// get the count of all jobs in the past 24 hours
		allJobs = getTotalNumberOfJobs();

		// complete any jobs still running after 24 hours
		Integer[] job = getAbandonedJobs();
		for (int i=0; i<job.length; i++) {
			completeAbandonedJob(job[i]);
		}

		// get all of the failed jobs in the past 24 hours
		List<BatchJobInstance> jobs = getFailedJobs();
		failedJobs = jobs.size();
		if (failedJobs > 0) {
			sendOutFailedJobAlert(jobs);
		}

		List<BatchJobServiceStatistic> stats = new ArrayList<BatchJobServiceStatistic>();
		stats.add(new BatchJobServiceStatistic("Total jobs in past 24 hrs", BatchJobService.FORMAT_INTEGER, new BigInteger(allJobs + "")));
		stats.add(new BatchJobServiceStatistic("Abandonded jobs found", BatchJobService.FORMAT_INTEGER, new BigInteger(abandonedJobs + "")));
		stats.add(new BatchJobServiceStatistic("Abandonded jobs updated", BatchJobService.FORMAT_INTEGER, new BigInteger(updatedJobs + "")));
		stats.add(new BatchJobServiceStatistic("Abandonded jobs not updated", BatchJobService.FORMAT_INTEGER, new BigInteger(failedUpdates + "")));
		stats.add(new BatchJobServiceStatistic("Failed jobs found", BatchJobService.FORMAT_INTEGER, new BigInteger(failedJobs + "")));
		stats.add(new BatchJobServiceStatistic("Failed job e-mail alerts sent", BatchJobService.FORMAT_INTEGER, new BigInteger(noticesSent + "")));

		log.info("PeriodicBatchJobReview complete.");

		return stats;
	}

	/**
	 * <p>Gets the total number of jobs in the past 24 hours.</p>
	 * 
	 * @return the total number of jobs in the past 24 hours
	 */
	private int getTotalNumberOfJobs() {
		log.info("Searching for all jobs ...");
		log.info(" ");

		int jobs = 0;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String qs = "select count(*) from batchjobinstance where startDateTime > now() + interval -1 day";
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(qs);
			if (rs.next()) {
				jobs = rs.getInt(1);
			}
		} catch (SQLException e) {
			log.error("Exception encountered while attempting to retrieve all jobs: " + e.toString() + "; " +  e.getMessage(), e);
			log.error("SQL statement: " + qs);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqle) {
					log.error("SQL error on rs.close(): " + sqle.toString() + "; " +  sqle.getMessage(), sqle);
				}
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqle) {
					log.error("SQL error on stmt.close(): " + sqle.toString() + "; " +  sqle.getMessage(), sqle);
				}
				stmt = null;
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException sqle) {
					log.error("SQL error on conn.close(): : " + sqle.toString() + "; " +  sqle.getMessage(), sqle);
				}
				conn = null;
			}
		}

		log.info(jobs + " job(s) found");
		log.info(" ");

		return jobs;
	}

	/**
	 * <p>Gets an array of instance ids for all abandoned jobs.</p>
	 * 
	 * @return an array of instance ids for all abandoned jobs
	 */
	private Integer[] getAbandonedJobs() {
		log.info("Searching for abandoned jobs ...");
		log.info(" ");

		List<Integer> jobs = new ArrayList<Integer>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String qs = "select id from batchjobinstance where status = 'Running' and startDateTime < now() + interval -1 day";
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(qs);
			while (rs.next()) {
				jobs.add(rs.getInt("id"));
			}
		} catch (SQLException e) {
			log.error("Exception encountered while attempting to retrieve abandoned jobs: " + e.toString() + "; " +  e.getMessage(), e);
			log.error("SQL statement: " + qs);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqle) {
					log.error("SQL error on rs.close(): " + sqle.toString() + "; " +  sqle.getMessage(), sqle);
				}
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqle) {
					log.error("SQL error on stmt.close(): " + sqle.toString() + "; " +  sqle.getMessage(), sqle);
				}
				stmt = null;
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException sqle) {
					log.error("SQL error on conn.close(): : " + sqle.toString() + "; " +  sqle.getMessage(), sqle);
				}
				conn = null;
			}
		}

		log.info(jobs.size() + " abandoned job(s) found");
		log.info(" ");

		return jobs.toArray(new Integer[0]);
	}

	/**
	 * <p>Gets an array of instance ids for all failed jobs.</p>
	 * 
	 * @return an array of instance ids for all failed jobs
	 */
	private List<BatchJobInstance> getFailedJobs() {
		log.info("Searching for failed jobs ...");
		log.info(" ");

		List<BatchJobInstance> jobs = new ArrayList<BatchJobInstance>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String qs = "select id from batchjobinstance where status = 'Failed' and startDateTime > now() + interval -1 day";
		try {
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(qs);
			while (rs.next()) {
				jobs.add(batchJobInstanceManager.findById(rs.getInt("id")));
			}
		} catch (SQLException e) {
			log.error("Exception encountered while attempting to retrieve abandoned jobs: " + e.toString() + "; " +  e.getMessage(), e);
			log.error("SQL statement: " + qs);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqle) {
					log.error("SQL error on rs.close(): " + sqle.toString() + "; " +  sqle.getMessage(), sqle);
				}
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqle) {
					log.error("SQL error on stmt.close(): " + sqle.toString() + "; " +  sqle.getMessage(), sqle);
				}
				stmt = null;
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException sqle) {
					log.error("SQL error on conn.close(): : " + sqle.toString() + "; " +  sqle.getMessage(), sqle);
				}
				conn = null;
			}
		}

		log.info(jobs.size() + " failed job(s) found");
		log.info(" ");

		return jobs;
	}

	/**
	 * <p>Completes an abandoned job.</p>
	 * 
	 * @param the id of the job to complete
	 */
	private void completeAbandonedJob(Integer id) {
		abandonedJobs++;
		BatchJobInstance batchJobInstance = batchJobInstanceManager.findById(id);
		if (batchJobInstance != null) {
			Date rightNow = new Date();
			batchJobInstance.setStatus(BatchJobService.STATUS_ABANDONED);
			BatchJobEvent batchJobEvent = new BatchJobEvent();
			batchJobEvent.setEvent(BatchJobService.STATUS_ABANDONED);
			batchJobEvent.setDescription("Job marked as abandoned by PeriodicBatchJobReview");
			batchJobEvent.setCreationDate(rightNow);
			batchJobEvent.setCreatedBy("PeriodicBatchJobReview");
			batchJobEvent.setLastUpdate(rightNow);
			batchJobEvent.setLastUpdateBy("PeriodicBatchJobReview");
			batchJobInstance.addEvent(batchJobEvent);
			batchJobInstance.setLastUpdate(rightNow);
			batchJobInstance.setLastUpdateBy("PeriodicBatchJobReview");
			batchJobInstanceManager.save(batchJobInstance);
			updatedJobs++;
		} else {
			log.error("Unable to retrieve instance " + id + "; job will not be updated.");
			failedUpdates++;
		}
	}

	/**
	 * <p>Sends out an alert e-mail about failed jobs.</p>
	 */
	@SuppressWarnings("unchecked")
	private void sendOutFailedJobAlert(List<BatchJobInstance> jobs) {
		if (StringUtils.isNotEmpty(alertRecipients)) {
			String[] sendToArray = alertRecipients.split(",");
			if (sendToArray.length > 0) {
				if (StringUtils.isNotEmpty(alertTemplateId)) {
					List<Object> sendTo = new ArrayList<Object>();
					for (int i=0; i<sendToArray.length; i++) {
						sendTo.add(sendToArray[i]);
					}
					Map<String,Object> noticeData = new HashMap<String,Object>();
					try {
						noticeData = (Map<String,Object>) notifier.getStandardNoticeData(null, null);
					} catch (ClassCastException e) {
						log.warn("Exception encountered when attempting to obtain standard notice data", e);
					}
					noticeData.put("jobs", jobs);
					notifier.sendNotification(sendTo, alertTemplateId, noticeData);
					noticesSent = sendTo.size();
				} else {
					log.error("Unable to send out alerts: no alert template configured");
				}
			} else {
				log.error("Unable to send out alerts: no alert recipients configured");
			}
		} else {
			log.error("Unable to send out alerts: no alert recipients configured");
		}
	}

	/**
	 * @param batchJobInstanceManager the batchJobInstanceManager to set
	 */
	public void setBatchJobInstanceManager(BatchJobInstanceManager batchJobInstanceManager) {
		this.batchJobInstanceManager = batchJobInstanceManager;
	}

	/**
	 * @param notifier the notifier to set
	 */
	public void setNotifier(Notifier notifier) {
		this.notifier = notifier;
	}

	/**
	 * @param dataSource the dataSource to set
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * @param alertTemplateId the alertTemplateId to set
	 */
	public void setAlertTemplateId(String alertTemplateId) {
		this.alertTemplateId = alertTemplateId;
	}

	/**
	 * @param alertRecipients the alertRecipients to set
	 */
	public void setAlertRecipients(String alertRecipients) {
		this.alertRecipients = alertRecipients;
	}
}
