package edu.ucdavis.ucdh.stu.core.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>This entity bean defines a single schedule for a batch job.</p>
 */
@Entity
@Table(name = "batchjobschedule")
@XmlRootElement
@XmlType(propOrder={"id", "batchJob", "name", "description", "status", "schedule", "creationDate", "createdBy", "lastUpdate", "lastUpdateBy"})
public class BatchJobSchedule {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id = null;
	@ManyToOne
	@JoinColumn(name = "batchJobId")
	private BatchJob batchJob = null;
	@Column(name = "name")
	private String name = null;
	@Column(name = "description")
	private String description = null;
	@Column(name = "status")
	private String status = null;
	@Column(name = "schedule")
	private String schedule = null;
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
	 * @return the batchJob
	 */
	public BatchJob getBatchJob() {
		return batchJob;
	}
	/**
	 * @param batchJob the batchJob to set
	 */
	public void setBatchJob(BatchJob batchJob) {
		this.batchJob = batchJob;
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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the schedule
	 */
	public String getSchedule() {
		return schedule;
	}
	/**
	 * @param schedule the schedule to set
	 */
	public void setSchedule(String schedule) {
		this.schedule = schedule;
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
