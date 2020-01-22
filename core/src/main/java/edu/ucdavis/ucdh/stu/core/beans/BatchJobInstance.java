package edu.ucdavis.ucdh.stu.core.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>This entity bean defines a single instance of a batch job.</p>
 */
@Entity
@Table(name = "batchjobinstance")
@XmlRootElement
@XmlType(propOrder={"id", "batchJobSchedule", "status", "host", "startDateTime", "endDateTime", "event", "statistic", "creationDate", "createdBy", "lastUpdate", "lastUpdateBy"})
public class BatchJobInstance {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id = null;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "batchJobScheduleId")
	private BatchJobSchedule batchJobSchedule = null;
	@Column(name = "status")
	private String status = null;
	@Column(name = "host")
	private String host = null;
	@Column(name = "startDateTime")
	private Date startDateTime = null;
	@Column(name = "endDateTime")
	private Date endDateTime = null;
	@Column(name = "creationDate")
	private Date creationDate = null;
	@Column(name = "createdBy")
	private String createdBy = null;
	@Column(name = "lastUpdate")
	private Date lastUpdate = null;
	@Column(name = "lastUpdateBy")
	private String lastUpdateBy = null;
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="batchJobInstanceId")
    @OrderBy("id")
	private List<BatchJobEvent> event = null;
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="batchJobInstanceId")
    @OrderBy("id")
	private List<BatchJobStatistic> statistic = null;

	/**
	 * <p>Adds a BatchJobEvent to this BatchJobInstance.</p>
	 * 
	 * @param batchJobEvent the BatchJobEvent to add
	 */
	public void addEvent(BatchJobEvent batchJobEvent) {
		if (event == null) {
			event = new ArrayList<BatchJobEvent>();
		}
		batchJobEvent.setBatchJobInstanceId(id);
		event.add(batchJobEvent);
	}

	/**
	 * <p>Adds a BatchJobStatistic to this BatchJobInstance.</p>
	 * 
	 * @param batchJobStatistic the BatchJobStatistic to add
	 */
	public void addStatistic(BatchJobStatistic batchJobStatistic) {
		if (statistic == null) {
			statistic = new ArrayList<BatchJobStatistic>();
		}
		batchJobStatistic.setBatchJobInstanceId(id);
		statistic.add(batchJobStatistic);
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
	 * @return the batchJobSchedule
	 */
	public BatchJobSchedule getBatchJobSchedule() {
		return batchJobSchedule;
	}

	/**
	 * @param batchJobSchedule the batchJobSchedule to set
	 */
	public void setBatchJobSchedule(BatchJobSchedule batchJobSchedule) {
		this.batchJobSchedule = batchJobSchedule;
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
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the startDateTime
	 */
	public Date getStartDateTime() {
		return startDateTime;
	}

	/**
	 * @param startDateTime the startDateTime to set
	 */
	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}

	/**
	 * @return the endDateTime
	 */
	public Date getEndDateTime() {
		return endDateTime;
	}

	/**
	 * @param endDateTime the endDateTime to set
	 */
	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
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

	/**
	 * @return the event
	 */
	@XmlElementWrapper(name = "events")
	public List<BatchJobEvent> getEvent() {
		return event;
	}

	/**
	 * @param event the event to set
	 */
	public void setEvent(List<BatchJobEvent> event) {
		this.event = event;
	}

	/**
	 * @return the statistic
	 */
	@XmlElementWrapper(name = "statistics")
	public List<BatchJobStatistic> getStatistic() {
		return statistic;
	}

	/**
	 * @param statistic the statistic to set
	 */
	public void setStatistic(List<BatchJobStatistic> statistic) {
		this.statistic = statistic;
	}
}
