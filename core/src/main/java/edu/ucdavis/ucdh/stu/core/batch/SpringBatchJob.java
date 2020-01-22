package edu.ucdavis.ucdh.stu.core.batch;

import java.util.List;

import edu.ucdavis.ucdh.stu.core.utils.BatchJobServiceStatistic;

/**
 * 
 * <p>Spring batch job interface.</p>
 *
 */
public interface SpringBatchJob {

	/**
	 * <p>This is the main "run" method called by the job runner.</p>
	 * 
	 * @param batchJobInstanceId the ID of the running batch job instance
	 */
	public List<BatchJobServiceStatistic> run(String[] args, int batchJobInstanceId) throws Exception;
}
