package edu.ucdavis.ucdh.stu.core.beans;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>This abstract class defines the base elements common to all
 * entity beans.</p>
 */
public abstract class PersistentBeanBase implements Serializable {
	private static final long serialVersionUID = 1;
	private Date creationDate = null;
	private String createdBy = null;
	private Date lastUpdate = null;
	private String lastUpdateBy = null;

	/**
	 * <p>Returns the createdBy.</p>
	 * 
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * <p>Sets the createdBy.</p>
	 * 
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * <p>Returns the creationDate.</p>
	 * 
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * <p>Sets the creationDate.</p>
	 * 
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * <p>Returns the lastUpdate.</p>
	 * 
	 * @return the lastUpdate
	 */
	public Date getLastUpdate() {
		return lastUpdate;
	}

	/**
	 * <p>Sets the lastUpdate.</p>
	 * 
	 * @param lastUpdate the lastUpdate to set
	 */
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	/**
	 * <p>Returns the lastUpdateBy.</p>
	 * 
	 * @return the lastUpdateBy
	 */
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}

	/**
	 * <p>Sets the lastUpdateBy.</p>
	 * 
	 * @param lastUpdateBy the lastUpdateBy to set
	 */
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
}
