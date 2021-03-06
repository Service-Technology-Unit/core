package edu.ucdavis.ucdh.stu.core.service;

import edu.ucdavis.ucdh.stu.core.beans.MessageAddress;

/**
 * <p>This is the DeliveryService interface.</p>
 */
public interface DeliveryService {

	/**
	 * <p>Delivers the message to the specified recipient(s) using the
	 * specified delivery method.</p>
	 * 
	 * @param deliveryMethod the method to be used to deliver the message
	 * @param addresses an array of "address" objects containing the
	 * implementation-specific addresses required for successful delivery of
	 * the message
	 * @param titleSubject an optional subject and/or title of the message to
	 * be delivered
	 * @param contentType the mime type of the message body
	 * @param body the text of the message to be delivered
	 */
	public void deliverMessage(String deliveryMethod, MessageAddress[] addresses, String titleSubject, String contentType, String body);
}
