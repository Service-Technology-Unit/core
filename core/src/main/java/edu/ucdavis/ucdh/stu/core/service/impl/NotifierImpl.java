package edu.ucdavis.ucdh.stu.core.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.ucdavis.ucdh.stu.core.beans.MessageAddress;
import edu.ucdavis.ucdh.stu.core.beans.NoticeAudit;
import edu.ucdavis.ucdh.stu.core.beans.NoticeTemplate;
import edu.ucdavis.ucdh.stu.core.manager.NoticeAuditManager;
import edu.ucdavis.ucdh.stu.core.manager.NoticeTemplateManager;
import edu.ucdavis.ucdh.stu.core.service.DeliveryService;
import edu.ucdavis.ucdh.stu.core.service.NotifierBase;
import edu.ucdavis.ucdh.stu.core.utils.SimpleVelocityService;

/**
 * <p>This class is a service module that delivers notifications.</p>
 */
public class NotifierImpl extends NotifierBase {
	private final Log log = LogFactory.getLog(NotifierImpl.class);
	private NoticeTemplateManager noticeTemplateManager;
	private NoticeAuditManager noticeAuditManager;
	private DeliveryService deliveryService;
	private String defaultTemplateContext = "global";
	private String auditNotifications = "false";

	/**
	 * <p>Delivers the specified notification.</p>
	 * 
	 * @param sendFrom the "from" address
	 * @param sendTo the list of objects representing the notice recipients.
	 *        It can directly contain MessageAddress objects which can be mailTo, mailCc or mailBc types.
	 * @param sendToProperty the name of the property in the recipient object
	 * that specifies the "to" address. If this parameter is null or blank, it
	 * is assumed that the sendTo list contains simple "to" address strings
	 * @param noticeTemplate the notice template
	 * @param noticeData optional data used to resolve variables in the notice
	 */
	public void sendNotification(String sendFrom, List<Object> sendTo, String sendToProperty, NoticeTemplate noticeTemplate, Object noticeData) {
		if (log.isDebugEnabled()) {
			log.debug("Processing notification request; sendFrom: " + sendFrom);
		}
		if (noticeTemplate != null) {
			if (sendTo != null && sendTo.size() > 0) {
				List<Object> expandedSendToList = new ArrayList<Object>();
				List<MessageAddress> otherAddrList = new ArrayList<MessageAddress>();
				
				// The sendTo object list may be a mixture of MessageAddress objects and other objects.
				// Handle MessageAddress objects this way:
				// - If it is "Cc" or "Bcc", move them in a separate list so that all emails will be Cc or Bcc to these recipients
				// - If it is "To", then, if sendToProperty is null, retrieve the addresses from the MessageAddresses, otherwise ignore them.
				for (Object toObject : sendTo) {
					if (toObject instanceof MessageAddress) {
						if (((MessageAddress) toObject).getAddressType().equalsIgnoreCase("mailCc") ||
								((MessageAddress) toObject).getAddressType().equalsIgnoreCase("mailBc")) {
							otherAddrList.add((MessageAddress) toObject);
						} else if (((MessageAddress) toObject).getAddressType().equalsIgnoreCase("mailTo")) {
							// "mailTo" MessageAddress object is valid only if not using sendToProperty to resolve the address
							if (!StringUtils.isNotEmpty(sendToProperty)) {
								String[] addrs = ((MessageAddress) toObject).getAddressValues();
								if (addrs != null) {
									for (String a : addrs)
										expandedSendToList.add(a);
								}
							}
						}
					} else {
						expandedSendToList.add(toObject);
					}
				}
				
				Iterator<Object> i = expandedSendToList.iterator();
				while (i.hasNext()) {
					Object recipient = i.next();

					// extract the "to" address string
					String toAddress = "";
					if (StringUtils.isNotEmpty(sendToProperty)) {
						try {
							toAddress = BeanUtils.getProperty(recipient, sendToProperty);
						} catch (IllegalAccessException e) {
							throw new IllegalArgumentException("Exception encountered processing sendToProperty.", e);
						} catch (InvocationTargetException e) {
							throw new IllegalArgumentException("Exception encountered processing sendToProperty.", e);
						} catch (NoSuchMethodException e) {
							throw new IllegalArgumentException("Exception encountered processing sendToProperty.", e);
						}
					} else {
						toAddress = recipient.toString();
					}

					// resolve the variable data
					Map<String,Object> sourceData = new HashMap<String,Object>();
					sourceData.put("recipient", recipient);
					sourceData.put("data", noticeData);
					String titleSubject = SimpleVelocityService.evaluate(noticeTemplate.getTitleSubject(), sourceData);
					String body = SimpleVelocityService.evaluate(noticeTemplate.getBody(), sourceData);

					// set up the from and to addresses
					MessageAddress[] addresses = new MessageAddress[1];
					MessageAddress toAddr = new MessageAddress();
					toAddr.setAddressType("mailTo");
					toAddr.addAddressValue(toAddress);
					if (StringUtils.isNotEmpty(sendFrom)) {
						MessageAddress fromAddress = new MessageAddress();
						fromAddress.setAddressType("mailFrom");
						fromAddress.addAddressValue(sendFrom);
						addresses = new MessageAddress[2];
						addresses[0] = fromAddress;
						addresses[1] = toAddr;
					} else {
						addresses[0] = toAddr;
					}

					// log, if enabled
					if (log.isDebugEnabled()) {
						log.debug("Sending notification; toAddress: " + toAddress + "; titleSubject: " + titleSubject);
					}

					addresses = mergeMessageAddresses(addresses, otherAddrList);
					
					// deliver message
					deliveryService.deliverMessage(noticeTemplate.getDefaultDeliveryMethod(), addresses, titleSubject, noticeTemplate.getContentType(), body);

					// audit, if enabled
					if (isAuditLogActive(sendFrom, sendTo, sendToProperty, noticeTemplate, noticeData)) {
						auditNotification(noticeTemplate.getDefaultDeliveryMethod(), addresses, titleSubject, noticeTemplate.getContentType(), body, noticeData);
					}
				}
			} else {
				if (log.isDebugEnabled()) {
					log.debug("Invalid request -- recipient list is null or empty");
				}
				throw new IllegalArgumentException("Recipient list cannot be null or empty.");
			}
		} else {
			if (log.isDebugEnabled()) {
				log.debug("Invalid request -- notice template is null");
			}
			throw new IllegalArgumentException("Notice template cannot be null.");
		}
	}
	
	/** Merge address objects into one array */
	MessageAddress[] mergeMessageAddresses(MessageAddress[] addresses, List<MessageAddress> otherAddrList) {
		if (otherAddrList == null || otherAddrList.isEmpty())
			return addresses;
		else {
			MessageAddress[] result = new MessageAddress[addresses.length + otherAddrList.size()];
			int i = 0;
			for (MessageAddress addr : addresses)
				result[i++] = addr;
			for (MessageAddress addr : otherAddrList)
				result[i++] = addr;
			return result;
		}
	}
	

	/**
	 * <p>Logs the details of this notification.</p>
	 * 
	 * @param deliveryMethod the delivery method used for this message
	 * @param addresses an array of "address" objects containing the
	 * implementation-specific addresses required for successful delivery of
	 * the message
	 * @param titleSubject an optional subject and/or title of the message to
	 * be delivered
	 * @param contentType the mime type of the message body
	 * @param body the text of the message to be delivered
	 * @param noticeData optional data used to resolve variables in the notice
	 */
	private void auditNotification(String deliveryMethod, MessageAddress[] addresses, String titleSubject,  String contentType, String body, Object noticeData) {
		NoticeAudit noticeAudit = new NoticeAudit();

		Date rightNow = new Date();
		String userId = "NotifierImpl";
		HttpServletRequest req = findHttpServletRequest(noticeData);
		if (req != null) {
			noticeAudit.setRemoteAddr(req.getRemoteAddr());
			noticeAudit.setRemoteHost(req.getRemoteHost());
			if (StringUtils.isNotEmpty(req.getRemoteUser())) {
				userId = req.getRemoteUser();
				noticeAudit.setRemoteUser(req.getRemoteUser());
			}
		}
		noticeAudit.setStackTrace(getStackTraceString());
		noticeAudit.setAddressing(formatAddresses(addresses));
		noticeAudit.setContentType(contentType);
		noticeAudit.setDeliveryMethod(deliveryMethod);
		noticeAudit.setTitleSubject(titleSubject);
		noticeAudit.setBody(body);
		noticeAudit.setCreationDate(rightNow);
		noticeAudit.setCreatedBy(userId);
		noticeAudit.setLastUpdate(rightNow);
		noticeAudit.setLastUpdateBy(userId);

		// save audit details
		noticeAuditManager.save(noticeAudit);
	}

	/**
	 * <p>Returns true if this notice should be audited.</p>
	 * 
	 * @param sendFrom the "from" address
	 * @param sendTo the list of objects representing the notice recipients
	 * @param sendToProperty the name of the property in the recipient object
	 * that specifies the "to" address. If this parameter is null or blank, it
	 * is assumed that the sendTo list contains simple "to" address strings
	 * @param noticeTemplate the notice template
	 * @param noticeData optional data used to resolve variables in the notice
	 * @return true if this notice should be audited
	 */
	private boolean isAuditLogActive(String sendFrom, List<Object> sendTo, String sendToProperty, NoticeTemplate noticeTemplate, Object noticeData) {
		return "true".equals(auditNotifications);
	}

	/**
	 * <p>Fetches the specified template.</p>
	 * 
	 * @param templateId the id of the notice template in the form of context/name
	 * @return the requested NoticeTemplate
	 */
	public NoticeTemplate getNoticeTemplate(String templateId) {
		String context = defaultTemplateContext;
		String name = templateId;
		if (templateId.indexOf("/") != -1) {
			String[] parts = templateId.split("/");
			context = parts[0];
			name = parts[1];
		}
		if (log.isDebugEnabled()) {
			log.debug("Fetching notice template; context: " + context + "; name: " + name);
		}
		return noticeTemplateManager.findByContextAndName(context, name);
	}

	/**
	 * <p>Returns the HttpServletRequest, if it can be located.</p>
	 * 
	 * @param noticeData optional data used to resolve variables in the notice
	 * @return the HttpServletRequest, if it can be located
	 */
	@SuppressWarnings("rawtypes")
	private HttpServletRequest findHttpServletRequest(Object noticeData) {
		HttpServletRequest request = null;

		try {
			// first, see if the noticeData is the request itself
			request = (HttpServletRequest) noticeData;
		} catch (ClassCastException e) {
			try {
				// next, see if the noticeData is a map
				Map noticeDataMap = (Map) noticeData;
				Iterator i = noticeDataMap.keySet().iterator();
				while (request == null && i.hasNext()) {
					Object key  = i.next();
					try {
						// then, see if the map element is the request object
						request = (HttpServletRequest) noticeDataMap.get(key);
					} catch (ClassCastException e1) {
						try {
							// ... and if not, see if the map element is another map
							Map noticeDataSubMap = (Map) noticeDataMap.get(key);
							Iterator j = noticeDataSubMap.keySet().iterator();
							while (request == null && j.hasNext()) {
								Object subkey  = j.next();
								try {
									// then, see if the map element's map element is the request object
									request = (HttpServletRequest) noticeDataSubMap.get(subkey);
								} catch (ClassCastException e2) {
									// ... and if that's not it, then give up!
								}
							}
						} catch (ClassCastException e2) {
							// ... and if not, then loop back!
						}
					}
				}
			} catch (ClassCastException e1) {
				// no one cares
			}
		}

		if (log.isDebugEnabled()) {
			log.debug("Search for HttpServletRequest object found: " + request);
		}

		return request;
	}

	/**
	 * <p>Returns the message addresses as a string.</p>
	 * 
	 * @return the message addresses as a string
	 */
	private static String formatAddresses(MessageAddress[] addresses) {
		StringBuffer buffer = new StringBuffer();

		String separator = "";
		for (int i=0; i<addresses.length; i++) {
			buffer.append(separator);
			buffer.append(addresses[i].getAddressType());
			buffer.append(": ");
			String separator2 = "";
			for (int j=0; j<addresses[i].getAddressValues().length; j++) {
				buffer.append(separator2);
				buffer.append(addresses[i].getAddressValues()[j]);
				separator2 = ",";
			}
			separator = "\n";
		}

		return buffer.toString();
	}

	/**
	 * <p>Returns the current stack trace as a string.</p>
	 * 
	 * @return the current stack trace as a string
	 */
	private static String getStackTraceString() {
		StringBuffer buffer = new StringBuffer();

		String separator = "";
		StackTraceElement[] element = Thread.currentThread().getStackTrace();
		for (int i=0; i<element.length; i++) {
			buffer.append(separator);
			buffer.append(element[i].toString());
			separator = "\n";
		}

		return buffer.toString();
	}

	/**
	 * @param noticeTemplateManager the noticeTemplateManager to set
	 */
	public void setNoticeTemplateManager(NoticeTemplateManager noticeTemplateManager) {
		this.noticeTemplateManager = noticeTemplateManager;
	}

	/**
	 * @param noticeAuditManager the noticeAuditManager to set
	 */
	public void setNoticeAuditManager(NoticeAuditManager noticeAuditManager) {
		this.noticeAuditManager = noticeAuditManager;
	}

	/**
	 * @param deliveryService the deliveryService to set
	 */
	public void setDeliveryService(DeliveryService deliveryService) {
		this.deliveryService = deliveryService;
	}

	/**
	 * @param defaultTemplateContext the defaultTemplateContext to set
	 */
	public void setDefaultTemplateContext(String defaultTemplateContext) {
		this.defaultTemplateContext = defaultTemplateContext;
	}

	/**
	 * @param auditNotifications the auditNotifications to set
	 */
	public void setAuditNotifications(String auditNotifications) {
		this.auditNotifications = auditNotifications;
	}
}
