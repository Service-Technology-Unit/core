package edu.ucdavis.ucdh.stu.core.service.impl;


import edu.ucdavis.ucdh.stu.core.beans.MessageAddress;
import edu.ucdavis.ucdh.stu.core.service.DeliveryService;
import edu.ucdavis.ucdh.stu.core.service.MessageCourier;
import edu.ucdavis.ucdh.stu.core.service.MessageCourierFactory;

/**
 * <p>This is the DeliveryService interface.</p>
 */
public class DeliveryServiceImpl implements DeliveryService {
	private MessageCourierFactory messageCourierFactory;

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
	public void deliverMessage(String deliveryMethod, MessageAddress[] addresses, String titleSubject, String contentType, String body) {
		MessageCourier messageCourier = messageCourierFactory.getMessageCourier(deliveryMethod);
		messageCourier.deliverMessage(addresses, titleSubject, contentType, body);
	}

	/**
	 * @return the messageCourierFactory
	 */
	public MessageCourierFactory getMessageCourierFactory() {
		return messageCourierFactory;
	}

	/**
	 * @param messageCourierFactory the messageCourierFactory to set
	 */
	public void setMessageCourierFactory(MessageCourierFactory messageCourierFactory) {
		this.messageCourierFactory = messageCourierFactory;
	}
}
