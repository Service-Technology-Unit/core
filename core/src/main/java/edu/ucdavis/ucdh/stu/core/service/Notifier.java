package edu.ucdavis.ucdh.stu.core.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ucdavis.ucdh.stu.core.beans.NoticeTemplate;

/**
 * <p>This is the Notifier interface.</p>
 */
public interface Notifier {

	/**
	 * <p>Builds and returns the standard noticeData object.</p>
	 * 
	 * @param req the <code>HttpServletRequest</code> object
	 * @param res the <code>HttpServletResponse</code> object
	 * @return the standard noticeData object
	 */
	public Object getStandardNoticeData(HttpServletRequest req, HttpServletResponse res);

	/**
	 * <p>Delivers the specified notification.</p>
	 * 
	 * @param sendToAddress the "to" address of the message
	 * @param templateId the id of the notice template in the form of context/name
	 * @param noticeData optional data used to resolve variables in the notice
	 */
	public void sendNotification(String sendToAddress, String templateId, Object noticeData);

	/**
	 * <p>Delivers the specified notification.</p>
	 * 
	 * @param sendTo the list of objects representing the notice recipients
	 * @param templateId the id of the notice template in the form of context/name
	 * @param noticeData optional data used to resolve variables in the notice
	 */
	public void sendNotification(List<Object> sendTo, String templateId, Object noticeData);

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
	public void sendNotification(List<Object> sendTo, String sendToProperty, String templateId, Object noticeData);

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
	public void sendNotification(String sendFrom, List<Object> sendTo, String sendToProperty, String templateId, Object noticeData);

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
	public void sendNotification(String sendFrom, List<Object> sendTo, String sendToProperty, NoticeTemplate noticeTemplate, Object noticeData);
}
