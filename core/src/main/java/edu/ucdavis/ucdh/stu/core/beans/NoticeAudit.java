package edu.ucdavis.ucdh.stu.core.beans;


/**
 * <p>This entity bean defines a single notice audit log entry.</p>
 */
public class NoticeAudit extends PersistentBeanBase {
	private static final long serialVersionUID = 1;
	private int id = -1;
	private String remoteAddr = null;
	private String remoteHost = null;
	private String remoteUser = null;
	private String stackTrace = null;
	private String addressing = null;
	private String contentType = null;
	private String deliveryMethod = null;
	private String titleSubject = null;
	private String body = null;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the remoteAddr
	 */
	public String getRemoteAddr() {
		return remoteAddr;
	}
	/**
	 * @param remoteAddr the remoteAddr to set
	 */
	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}
	/**
	 * @return the remoteHost
	 */
	public String getRemoteHost() {
		return remoteHost;
	}
	/**
	 * @param remoteHost the remoteHost to set
	 */
	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}
	/**
	 * @return the remoteUser
	 */
	public String getRemoteUser() {
		return remoteUser;
	}
	/**
	 * @param remoteUser the remoteUser to set
	 */
	public void setRemoteUser(String remoteUser) {
		this.remoteUser = remoteUser;
	}
	/**
	 * @return the stackTrace
	 */
	public String getStackTrace() {
		return stackTrace;
	}
	/**
	 * @param stackTrace the stackTrace to set
	 */
	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}
	/**
	 * @return the addressing
	 */
	public String getAddressing() {
		return addressing;
	}
	/**
	 * @param addressing the addressing to set
	 */
	public void setAddressing(String addressing) {
		this.addressing = addressing;
	}
	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}
	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	/**
	 * @return the deliveryMethod
	 */
	public String getDeliveryMethod() {
		return deliveryMethod;
	}
	/**
	 * @param deliveryMethod the deliveryMethod to set
	 */
	public void setDeliveryMethod(String deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}
	/**
	 * @return the titleSubject
	 */
	public String getTitleSubject() {
		return titleSubject;
	}
	/**
	 * @param titleSubject the titleSubject to set
	 */
	public void setTitleSubject(String titleSubject) {
		this.titleSubject = titleSubject;
	}
	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}
	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}
}