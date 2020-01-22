package edu.ucdavis.ucdh.stu.core.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ucdavis.ucdh.stu.core.beans.NoticeTemplate;
import edu.ucdavis.ucdh.stu.core.utils.CurrentDate;

/**
 * <p>This is the standard base code for all Notifiers.</p>
 */
public abstract class NotifierBase implements Notifier {

	/**
	 * <p>Builds and returns the standard noticeData object.</p>
	 *
	 * @param req the <code>HttpServletRequest</code> object
	 * @param res the <code>HttpServletResponse</code> object
	 * @return the standard noticeData object
	 */
	public Object getStandardNoticeData(HttpServletRequest req, HttpServletResponse res) {
		Map<String,Object> noticeData = new HashMap<String,Object>();

		noticeData.put("currentDate", new CurrentDate());
		if (req != null) {
			noticeData.put("request", req);
			if (req.getSession() != null) {
				noticeData.put("session", req.getSession());
			}
			if (req.getUserPrincipal() != null) {
				noticeData.put("user", req.getUserPrincipal());
			}
			noticeData.put("formfield", buildFormFieldMap(req.getParameterMap()));
			noticeData.put("fieldlist", req.getParameterMap());
		}
		if (res!= null) {
			noticeData.put("response", res);
		}

		return noticeData;
	}

	/**
	 * <p>Delivers the specified notification.</p>
	 *
	 * @param sendToAddress the "to" address of the message
	 * @param templateId the id of the notice template in the form of context/name
	 * @param noticeData optional data used to resolve variables in the notice
	 */
	public void sendNotification(String sendToAddress, String templateId, Object noticeData) {
		List<Object> sendTo = new ArrayList<Object>();
		sendTo.add(sendToAddress);
		this.sendNotification(null, sendTo, null, getNoticeTemplate(templateId), noticeData);
	}

	/**
	 * <p>Delivers the specified notification.</p>
	 *
	 * @param sendTo the list of objects representing the notice recipients
	 * @param templateId the id of the notice template in the form of context/name
	 * @param noticeData optional data used to resolve variables in the notice
	 */
	public void sendNotification(List<Object> sendTo, String templateId, Object noticeData) {
		this.sendNotification(null, sendTo, null, getNoticeTemplate(templateId), noticeData);
	}

	/**
	 * <p>Delivers the specified notification.</p>
	 *
	 * @param sendTo the list of objects representing the notice recipients
	 * @param sendToProperty the name of the property in the recipient object
	 * that specifies the "to" address. If this parameter is null or blank, it
	 * is assumed that the sendTo list contains simple "to" address strings
	 * @param templateId the id of the notice template in the form of context/name
	 * @param noticeData optional data used to resolve variables in the notice
	 */
	public void sendNotification(List<Object> sendTo, String sendToProperty, String templateId, Object noticeData) {
		this.sendNotification(null, sendTo, sendToProperty, getNoticeTemplate(templateId), noticeData);
	}

	/**
	 * <p>Delivers the specified notification.</p>
	 *
	 * @param sendFrom the "from" address
	 * @param sendTo the list of objects representing the notice recipients
	 * @param sendToProperty the name of the property in the recipient object
	 * that specifies the "to" address. If this parameter is null or blank, it
	 * is assumed that the sendTo list contains simple "to" address strings
	 * @param templateId the id of the notice template in the form of context/name
	 * @param noticeData optional data used to resolve variables in the notice
	 */
	public void sendNotification(String sendFrom, List<Object> sendTo, String sendToProperty, String templateId, Object noticeData) {
		this.sendNotification(sendFrom, sendTo, sendToProperty, getNoticeTemplate(templateId), noticeData);
	}

	/**
	 * <p>Delivers the specified notification.</p>
	 *
	 * @param sendFrom the "from" address
	 * @param sendTo the list of objects representing the notice recipients
	 * @param sendToProperty the name of the property in the recipient object
	 * that specifies the "to" address. If this parameter is null or blank, it
	 * is assumed that the sendTo list contains simple "to" address strings
	 * @param noticeTemplate the notice template
	 * @param noticeData optional data used to resolve variables in the notice
	 */
	public abstract void sendNotification(String sendFrom, List<Object> sendTo, String sendToProperty, NoticeTemplate noticeTemplate, Object noticeData);

	/**
	 * <p>Fetches the specified template.</p>
	 *
	 * @param templateId the id of the notice template in the form of context/name
	 * @return the requested NoticeTemplate
	 */
	public abstract NoticeTemplate getNoticeTemplate(String templateId);

	/**
	 * <p>Builds the form field map from the request parameters.</p>
	 *
	 * @return the form field map
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Map<String,String> buildFormFieldMap(Map parameterMap) {
		Map<String,String> formFieldMap = new HashMap<String,String>();

		if (parameterMap != null) {
			Iterator<String> i = parameterMap.keySet().iterator();
			while (i.hasNext()) {
				String key = i.next();
				String[] values = (String[]) parameterMap.get(key);
				if (values != null && values.length > 0) {
					formFieldMap.put(key, values[0]);
				}
			}
		}

		return formFieldMap;
	}
}
