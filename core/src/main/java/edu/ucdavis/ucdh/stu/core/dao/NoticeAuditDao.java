package edu.ucdavis.ucdh.stu.core.dao;

import java.io.Serializable;
import java.util.List;

import edu.ucdavis.ucdh.stu.core.beans.NoticeAudit;

/**
 * <p>This is the NoticeAudit data access object interface.</p>
 */
public interface NoticeAuditDao extends Dao {

	/**
	 * <p>Returns all NoticeAudits in the database.</p>
	 * 
	 * @return all NoticeAudits in the database
	 */
	public List<NoticeAudit> findAll();

	/**
	 * <p>Returns all NoticeAudits in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param noticeAudit an example NoticeAudit
	 * @return all NoticeAudits in the database that
	 * match the specified search criteria
	 */
	public List<NoticeAudit> findByExample(NoticeAudit noticeAudit);

	/**
	 * <p>Returns all NoticeAudits in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param propertyName the name of the specified property
	 * @param propertyValue the search value for the specified
	 * property
	 * @return all NoticeAudits in the database that
	 * match the specified search criteria
	 */
	public List<NoticeAudit> findByProperty(String propertyName, Object propertyValue);

	/**
	 * <p>Returns the NoticeAudit with the specified id.</p>
	 * 
	 * @param id the id of the requested noticeAudit
	 * @return the NoticeAudit with the specified id
	 */
	public NoticeAudit findById(Serializable id);

	/**
	 * <p>Saves the NoticeAudit passed.</p>
	 * 
	 * @param noticeAudit the noticeAudit to save
	 */
	public void save(NoticeAudit noticeAudit);

	/**
	 * <p>Deletes the NoticeAudit.</p>
	 * 
	 * @param noticeAudit the noticeAudit to delete
	 */
	public void delete(NoticeAudit noticeAudit);
}
