package edu.ucdavis.ucdh.stu.core.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * <p>Utility methods to work with the Batch Job REST services</p> 
 */
public class BatchJobService {
	public static final String STATUS_CREATED = "Created";
	public static final String STATUS_SCHEDULED = "Scheduled";
	public static final String STATUS_RUNNING = "Running";
	public static final String STATUS_FAILED = "Failed";
	public static final String STATUS_ABANDONED = "Abandoned";
	public static final String STATUS_COMPLETED = "Completed";
	public static final String FORMAT_INTEGER = "integer";
	public static final String FORMAT_CURRENCY = "currency";
	public static final String FORMAT_PERCENTAGE = "percent";
	public static final String FORMAT_DATE = "date";
	public static final String FORMAT_DATE_TIME = "datetime";
	public static final String FORMAT_DURATION = "duration";
	private Log log = LogFactory.getLog(getClass());
	private String baseURL = null;

	/**
	 * <p>Primary constructor</p>
	 * 
	 *  @param baseURL the base URL of the REST services
	 */
	public BatchJobService(String baseURL) {
		super();
		this.baseURL = baseURL;
	}

	/**
	 * <p>Returns true if the format passed is a valid statistic format.</p>
	 * 
	 * @param format the format to evaluate
	 * @return true if the format passed is a valid statistic format
	 */
	public static final boolean validFormat(String format) {
		return FORMAT_INTEGER.equalsIgnoreCase(format) || FORMAT_CURRENCY.equalsIgnoreCase(format) || FORMAT_PERCENTAGE.equalsIgnoreCase(format) || FORMAT_DATE.equalsIgnoreCase(format) || FORMAT_DATE_TIME.equalsIgnoreCase(format) || FORMAT_DURATION.equalsIgnoreCase(format);
	}

	/**
	 * <p>First step in running a new instance of a batch job.</p>
	 * 
	 *  @param batchJobScheduleId the id of the BatchJobSchedule associated with this instance
	 *  @param description the optional message associated with the "create instance" event
	 *  @return the id of the newly created BatchJobInstance
	 */
	public int createNewInstance(String appName, String jobName, String batchJobScheduleId, String description) throws ExecutionException {
		return createNewInstance(appName, jobName, batchJobScheduleId, description, false);
	}

	/**
	 * <p>First step in running a new instance of a batch job.</p>
	 * 
	 *  @param batchJobScheduleId the id of the BatchJobSchedule associated with this instance
	 *  @param description the optional message associated with the "create instance" event
	 *  @param startJob when true, the status of the job will be updated to "Running"
	 *  @return the id of the newly created BatchJobInstance
	 */
	public int createNewInstance(String appName, String jobName, String batchJobScheduleId, String description, boolean startJob) throws ExecutionException {
		int batchJobInstanceId = 0;

		String errmsg = "";
		String url = baseURL + "/core/data/instance/create/" + appName + "/" + jobName + "/" + batchJobScheduleId;
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("description", description));
		if (log.isDebugEnabled()) {
			log.debug("About to POST to " + url);
		}
		Map<String,String> response = post(url, parameters);
		if ("200".equals(response.get("statusCode"))) {
			String instanceXML = response.get("responseBody");
			if (instanceXML.indexOf("<id>") != -1) {
				String temp = instanceXML.substring(instanceXML.indexOf("<id>") + 4);
				temp = temp.substring(0, temp.indexOf("</id>"));
				try {
					batchJobInstanceId = Integer.parseInt(temp);
					if (startJob) {
						updateInstance(batchJobInstanceId, STATUS_RUNNING, "Status set to Running at instance creation");
					}
				} catch (Exception e) {
					errmsg = "Exception encountered parsing returned ID (\"" + temp + "\"): " + e.getClass().getName() + "; " + e.getMessage();
				}
			} else {
				errmsg = "Invalid response returned from POST: " + response.get("responseBody");
			}
		} else {
			errmsg = "Invalid response code returned from POST: " + response.get("statusCode") + "; " + response.get("responseBody");
			if (StringUtils.isNotEmpty(response.get("exception"))) {
				errmsg += "\n" + response.get("exception");
			}
		}
		if (StringUtils.isNotEmpty(errmsg)) {
			log.error(errmsg);
			throw new ExecutionException(errmsg, null);
		}

		return batchJobInstanceId;
	}

	/**
	 * <p>Update a job instance that has already been created.</p>
	 * 
	 *  @param batchJobInstanceId the id of the BatchJobInstance
	 *  @param event the event related to this update
	 *  job statistics for this instance
	 */
	public void updateInstance(int batchJobInstanceId, String event) throws ExecutionException {
		updateInstance(batchJobInstanceId, event, null, null);
	}

	/**
	 * <p>Update a job instance that has already been created.</p>
	 * 
	 *  @param batchJobInstanceId the id of the BatchJobInstance
	 *  @param event the event related to this update
	 *  @param description the optional message associated with this event
	 *  job statistics for this instance
	 */
	public void updateInstance(int batchJobInstanceId, String event, String description) throws ExecutionException {
		updateInstance(batchJobInstanceId, event, description, null);
	}

	/**
	 * <p>Update a job instance that has already been created.</p>
	 * 
	 *  @param batchJobInstanceId the id of the BatchJobInstance
	 *  @param event the event related to this update
	 *  @param description the optional message associated with this event
	 *  @param statistics used in conjunction with the job completion event, this is a list of
	 *  job statistics for this instance
	 */
	public void updateInstance(int batchJobInstanceId, String event, String description, List<BatchJobServiceStatistic> statistics) throws ExecutionException {
		String errmsg = "";
		String url = baseURL + "/core/data/instance/" + batchJobInstanceId;
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		if (StringUtils.isNotEmpty(event)) {
			parameters.add(new BasicNameValuePair("event", event));
			if (StringUtils.isNotEmpty(description)) {
				parameters.add(new BasicNameValuePair("description", description));
			}
			if (STATUS_COMPLETED.equalsIgnoreCase(event)) {
				if (statistics != null && statistics.size() > 0) {
					for (int i=0; i<statistics.size(); i++) {
						BatchJobServiceStatistic statistic = statistics.get(i);
						if (StringUtils.isNotEmpty(statistic.getLabel())) {
							parameters.add(new BasicNameValuePair("label_" + i, statistic.getLabel()));
							String format = FORMAT_INTEGER;
							if (StringUtils.isNotEmpty(statistic.getFormat())) {
								format = statistic.getFormat();
							}
							parameters.add(new BasicNameValuePair("format_" + i, format));
							BigInteger value = statistic.getValue();
							parameters.add(new BasicNameValuePair("value_" + i, value.toString()));
						}
					}
					
				}
			}
			if (log.isDebugEnabled()) {
				log.debug("About to POST to " + url);
			}
			Map<String,String> response = post(url, parameters);
			if ("200".equals(response.get("statusCode"))) {
				String instanceXML = response.get("responseBody");
				if (instanceXML.indexOf("<id>") != -1) {
					String temp = instanceXML.substring(instanceXML.indexOf("<id>") + 4);
					temp = temp.substring(0, temp.indexOf("</id>"));
					try {
						if (batchJobInstanceId != Integer.parseInt(temp)) {
							errmsg = "Invalid instance returned from POST: " + response.get("responseBody");
						}
					} catch (Exception e) {
						errmsg = "Exception encountered parsing returned ID (\"" + temp + "\"): " + e.getClass().getName() + "; " + e.getMessage();
					}
				} else {
					errmsg = "Invalid response returned from POST: " + response.get("responseBody");
				}
			} else {
				errmsg = "Invalid response code returned from POST: " + response.get("statusCode") + "; " + response.get("responseBody");
				if (StringUtils.isNotEmpty(response.get("exception"))) {
					errmsg += "\n" + response.get("exception");
				}
			}
		} else {
			errmsg = "Required parameter \"event\" not provided; request aborted.";
		}
		if (StringUtils.isNotEmpty(errmsg)) {
			log.error(errmsg);
			throw new ExecutionException(errmsg, null);
		}
	}

	/**
	 * <p>Posts to the specified REST Web Service.</p>
	 * 
	 * @param url the address of the service
	 * @param parameters the mapped parameters to be posted
	 * @return a Map containing two elements: 1) the HTTP Response Code and 2) the response body
	 */
	private Map<String,String> post(String url, List<NameValuePair> parameters) {
		Map<String,String> returnData = new HashMap<String,String>();

		returnData.put("statusCode", "000");
		if (log.isDebugEnabled()) {
			log.debug("Posting to URL " + url);
		}
		HttpClient client = new DefaultHttpClient();
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
				}
				public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
				}
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};
			ctx.init(null, new TrustManager[]{tm}, null);
			SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			ClientConnectionManager ccm = client.getConnectionManager();
			SchemeRegistry sr = ccm.getSchemeRegistry();
			sr.register(new Scheme("https", 443, ssf));
			client = new DefaultHttpClient(ccm, client.getParams());
		} catch (Exception e) {
			log.error("Exception encountered: " + e.getClass().getName() + "; " + e.getMessage(), e);
		}
		HttpPost post = new HttpPost(url);
		try {
			post.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			log.error("Exception encountered: " + e.getClass().getName() + "; " + e.getMessage(), e);
		}
		try {
			HttpResponse response = client.execute(post);
			returnData.put("statusCode", response.getStatusLine().getStatusCode() + "");
			returnData.put("responseBody", EntityUtils.toString(response.getEntity()));
		} catch (Exception e) {
			returnData.put("exception", "Exception encountered: " + e.getClass().getName() + "; " + e.getMessage());
			log.error("Exception encountered posting to URL " + url, e);
		}

		return returnData;
	}
}
