package edu.ucdavis.ucdh.stu.core.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.activation.MailcapCommandMap;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.mail.SimpleEmail;

import edu.ucdavis.ucdh.stu.core.beans.MessageAddress;
import edu.ucdavis.ucdh.stu.core.service.MessageCourier;

/**
 * <p>This is the e-mail implementation of the MessageCourier interface.</p>
 */
public class EmailCourier implements MessageCourier {
	private final Log log = LogFactory.getLog(getClass());
	private String smtpHostName = "localhost";
	private String smtpPort = "25";
	private String defaultFromAddress;

	/**
	 * <p>Delivers the message to specified recipient(s).</p>
	 * 
	 * @param addresses an array of "address" objects containing the
	 * implementation-specific addresses required for successful delivery of
	 * the message
	 * @param titleSubject an optional subject and/or title of the message to
	 * be delivered
	 * @param contentType the mime type of the message body
	 * @param body the text of the message to be delivered
	 */
	public void deliverMessage(MessageAddress[] addresses, String titleSubject,  String contentType, String body) {
		List<InternetAddress> fromAddresses = new ArrayList<InternetAddress>(); 
		List<InternetAddress> toAddresses = new ArrayList<InternetAddress>(); 
		List<InternetAddress> ccAddresses = new ArrayList<InternetAddress>(); 
		List<InternetAddress> bcAddresses = new ArrayList<InternetAddress>(); 
		List<InternetAddress> replyToAddresses = new ArrayList<InternetAddress>();
		if (addresses != null) {
			for (int i=0; i<addresses.length; i++) {
				if ("mailFrom".equalsIgnoreCase(addresses[i].getAddressType())) {
					if (addresses[i].getAddressValues() != null) {
						for (int j=0; j<addresses[i].getAddressValues().length; j++) {
							try {
								fromAddresses.add(new InternetAddress(addresses[i].getAddressValues()[j]));
							} catch (AddressException e) {
								log.error("Discarding address due to exception: " + addresses[i].getAddressValues()[j], e);
							}
						}
					}
				} else if ("mailTo".equalsIgnoreCase(addresses[i].getAddressType())) {
					if (addresses[i].getAddressValues() != null) {
						for (int j=0; j<addresses[i].getAddressValues().length; j++) {
							try {
								toAddresses.add(new InternetAddress(addresses[i].getAddressValues()[j]));
							} catch (AddressException e) {
								log.error("Discarding address due to exception: " + addresses[i].getAddressValues()[j], e);
							}
						}
					}
				} else if ("mailCc".equalsIgnoreCase(addresses[i].getAddressType())) {
					if (addresses[i].getAddressValues() != null) {
						for (int j=0; j<addresses[i].getAddressValues().length; j++) {
							try {
								ccAddresses.add(new InternetAddress(addresses[i].getAddressValues()[j]));
							} catch (AddressException e) {
								log.error("Discarding address due to exception: " + addresses[i].getAddressValues()[j], e);
							}
						}
					}
				} else if ("mailBc".equalsIgnoreCase(addresses[i].getAddressType())) {
					if (addresses[i].getAddressValues() != null) {
						for (int j=0; j<addresses[i].getAddressValues().length; j++) {
							try {
								bcAddresses.add(new InternetAddress(addresses[i].getAddressValues()[j]));
							} catch (AddressException e) {
								log.error("Discarding address due to exception: " + addresses[i].getAddressValues()[j], e);
							}
						}
					}
				} else if ("mailReplyTo".equalsIgnoreCase(addresses[i].getAddressType())) {
					if (addresses[i].getAddressValues() != null) {
						for (int j=0; j<addresses[i].getAddressValues().length; j++) {
							try {
								replyToAddresses.add(new InternetAddress(addresses[i].getAddressValues()[j]));
							} catch (AddressException e) {
								log.error("Discarding address due to exception: " + addresses[i].getAddressValues()[j], e);
							}
						}
					}
				}
			}
		}
		String mailFrom = defaultFromAddress;
		if (fromAddresses.size() > 0) {
			mailFrom = ((InternetAddress) fromAddresses.get(0)).toString();
		}
		if (StringUtils.isEmpty(contentType)) {
			contentType = "text/plain";
		} else {
			if (contentType.indexOf("/") == -1) {
				contentType = "text/" + contentType;
			}
		}
		try {
			SimpleEmail email = new SimpleEmail();
			email.setHostName(smtpHostName);
			email.setFrom(mailFrom);
			if (toAddresses.size() > 0) {
				email.setTo(toAddresses);
			}
			if (ccAddresses.size() > 0) {
				email.setCc(ccAddresses);
			}
			if (bcAddresses.size() > 0) {
				email.setBcc(bcAddresses);
			}
			if (replyToAddresses.size() > 0) {
				Iterator<InternetAddress> i = replyToAddresses.iterator();
				while (i.hasNext()) {
					email.addReplyTo(i.next().toString());
				}
			}
			email.setSubject(titleSubject);
			// this hack fixes problems with calendar entries
			if ("text/calendar".equals(contentType)) {
				MailcapCommandMap mailcap = (MailcapCommandMap)MailcapCommandMap.getDefaultCommandMap();
				mailcap.addMailcap("text/calendar;; x-java-content-handler=com.sun.mail.handlers.text_plain");
			}
			email.setContent(body, contentType);
			email.send();
		} catch (Exception e) {
			log.error("Exception sending notification", e);
		}
	}

	/**
	 * @return the smtpHostName
	 */
	public String getSmtpHostName() {
		return smtpHostName;
	}

	/**
	 * @param smtpHostName the smtpHostName to set
	 */
	public void setSmtpHostName(String smtpHostName) {
		this.smtpHostName = smtpHostName;
	}

	/**
	 * @return the smtpPort
	 */
	public String getSmtpPort() {
		return smtpPort;
	}

	/**
	 * @param smtpPort the smtpPort to set
	 */
	public void setSmtpPort(String smtpPort) {
		this.smtpPort = smtpPort;
	}

	/**
	 * @return the defaultFromAddress
	 */
	public String getDefaultFromAddress() {
		return defaultFromAddress;
	}

	/**
	 * @param defaultFromAddress the defaultFromAddress to set
	 */
	public void setDefaultFromAddress(String defaultFromAddress) {
		this.defaultFromAddress = defaultFromAddress;
	}
}
