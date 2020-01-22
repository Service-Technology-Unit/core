package edu.ucdavis.ucdh.stu.core.beans;


/**
 * <p>This entity bean defines a single notice template.</p>
 */
public class NoticeTemplate extends PersistentBeanBase {
	private static final long serialVersionUID = 1;
	private int id = -1;
	private String context = null;
	private String name = null;
	private String description = null;
	private String contentType = null;
	private String defaultDeliveryMethod = null;
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
	 * @return the context
	 */
	public String getContext() {
		return context;
	}
	/**
	 * @param context the context to set
	 */
	public void setContext(String context) {
		this.context = context;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @return the defaultDeliveryMethod
	 */
	public String getDefaultDeliveryMethod() {
		return defaultDeliveryMethod;
	}
	/**
	 * @param defaultDeliveryMethod the defaultDeliveryMethod to set
	 */
	public void setDefaultDeliveryMethod(String defaultDeliveryMethod) {
		this.defaultDeliveryMethod = defaultDeliveryMethod;
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