package edu.ucdavis.ucdh.stu.core.service;

/**
 * <p>This is the MessageCourierFactory interface.</p>
 */
public interface MessageCourierFactory {

	/**
	 * <p>Creates and returns the requested MessageCourier.</p>
	 * 
	 * @param deliveryMethod the delivery method for which a courier is being
	 * requested
	 * @return the requested MessageCourier
	 */
	public MessageCourier getMessageCourier(String deliveryMethod);
}
