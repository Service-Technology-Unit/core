package edu.ucdavis.ucdh.stu.core.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>This entity bean defines a single event related to a batch job instance.</p>
 */
@Entity
@Table(name = "batchjobevent")
@XmlRootElement
@XmlType(propOrder={"id", "batchJobInstanceId", "event", "description", "creationDate", "createdBy", "lastUpdate", "lastUpdateBy"})
public class BatchJobEvent {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id = null;
	@Column(name = "batchJobInstanceId")
	private Integer batchJobInstanceId = null;
	@Column(name = "event")
	private String event = null;
	@Column(name = "description")
	private String description = null;
	@Column(name = "creationDate")
	private Date creationDate = null;
	@Column(name = "createdBy")
	private String createdBy = null;
	@Column(name = "lastUpdate")
	private Date lastUpdate = null;
	@Column(name = "lastUpdateBy")
	private String lastUpdateBy = null;

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
	 * @return the batchJobInstanceId
	 */
	public Integer getBatchJobInstanceId() {
		return batchJobInstanceId;
	}
	/**
	 * @param batchJobInstanceId the batchJobInstanceId to set
	 */
	public void setBatchJobInstanceId(Integer batchJobInstanceId) {
		this.batchJobInstanceId = batchJobInstanceId;
	}
	/**
	 * @return the event
	 */
	public String getEvent() {
		return event;
	}
	/**
	 * @param event the event to set
	 */
	public void setEvent(String event) {
		this.event = event;
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
