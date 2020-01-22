package edu.ucdavis.ucdh.stu.core.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.StringUtils;

/**
 * <p>This entity bean defines a single media object.</p>
 */
@Entity
@Table(name = "media")
@XmlRootElement
@XmlType(propOrder={"id", "context", "location", "name", "description", "contentType", "content", "creationDate", "createdBy", "lastUpdate", "lastUpdateBy"})
public class Media {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id = null;
	@Column(name = "context")
	private String context = null;
	@Column(name = "location")
	private String location = null;
	@Column(name = "name")
	private String name = null;
	@Column(name = "description")
	private String description = null;
	@Column(name = "contentType")
	private String contentType = null;
	@Column(name = "content")
	private String content = null;
	@Column(name = "binaryContent")
	private byte[] binaryContent = null;
	@Column(name = "creationDate")
	private Date creationDate = null;
	@Column(name = "createdBy")
	private String createdBy = null;
	@Column(name = "lastUpdate")
	private Date lastUpdate = null;
	@Column(name = "lastUpdateBy")
	private String lastUpdateBy = null;

	/**
	 * @return the size
	 */
	public int getSize() {
		int size = 0;

		if (StringUtils.isNotEmpty(contentType)) {
			if (contentType.startsWith("text")) {
				if (StringUtils.isNotEmpty(content)) {
					size = content.length();
				}
			} else {
				if (binaryContent != null) {
					size = binaryContent.length;
				}
			}
		}

		return size;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
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
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
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
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return the binaryContent
	 */
	@XmlTransient
	public byte[] getBinaryContent() {
		return binaryContent;
	}
	/**
	 * @param binaryContent the binaryContent to set
	 */
	public void setBinaryContent(byte[] binaryContent) {
		this.binaryContent = binaryContent;
	}
	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the lastUpdate
	 */
	public Date getLastUpdate() {
		return lastUpdate;
	}
	/**
	 * @param lastUpdate the lastUpdate to set
	 */
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	/**
	 * @return the lastUpdateBy
	 */
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	/**
	 * @param lastUpdateBy the lastUpdateBy to set
	 */
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
}
