package edu.ucdavis.ucdh.stu.core.service.impl;

import java.util.Map;

import edu.ucdavis.ucdh.stu.core.service.MessageCourier;
import edu.ucdavis.ucdh.stu.core.service.MessageCourierFactory;

public class MessageCourierFactoryImpl implements MessageCourierFactory {
	private Map<String, MessageCourier> messageCourierMap;

	/**
	 * <p>Creates and returns the requested MessageCourier.</p>
	 * 
	 * @param deliveryMethod the delivery method for which a courier is being
	 * requested
	 * @return the requested MessageCourier
	 */
	public MessageCourier getMessageCourier(String deliveryMethod) {
		if (messageCourierMap != null) {
			if (messageCourierMap.containsKey(deliveryMethod)) {
				return (MessageCourier) messageCourierMap.get(deliveryMethod);
			} else {
				throw new IllegalArgumentException("No message courier configured for delivery method \"" + deliveryMethod + "\".");
			}
		} else {
			throw new IllegalArgumentException("No message courier configured for delivery method \"" + deliveryMethod + "\".");
		}
	}

	/**
	 * @return the messageCourierMap
	 */
	public Map<String, MessageCourier> getMessageCourierMap() {
		return messageCourierMap;
	}

	/**
	 * @param messageCourierMap the messageCourierMap to set
	 */
	public void setMessageCourierMap(Map<String, MessageCourier> messageCourierMap) {
		this.messageCourierMap = messageCourierMap;
	}
}
