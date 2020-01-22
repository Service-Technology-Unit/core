package edu.ucdavis.ucdh.stu.core.manager.impl;

import java.util.List;

import edu.ucdavis.ucdh.stu.core.beans.NoticeAudit;
import edu.ucdavis.ucdh.stu.core.dao.NoticeAuditDao;
import edu.ucdavis.ucdh.stu.core.manager.NoticeAuditManager;

/**
 * <p>Concrete implementation of the NoticeAudit manager interface.</p>
 */
public class NoticeAuditManagerImpl implements NoticeAuditManager {
	private NoticeAuditDao dao;

	/**
	 * <p>Returns all NoticeAudits in the database.</p>
	 * 
	 * @return all NoticeAudits in the database
	 */
	public List<NoticeAudit> findAll() {
		return dao.findAll();
	}

	/**
	 * <p>Returns all NoticeAudits in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param noticeAudit an example NoticeAudit
	 * @return all NoticeAudits in the database that
	 * match the specified search criteria
	 */
	public List<NoticeAudit> findByExample(NoticeAudit noticeAudit) {
		return dao.findByExample(noticeAudit);
	}

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
	public List<NoticeAudit> findByProperty(String propertyName, Object propertyValue) {
		return dao.findByProperty(propertyName, propertyValue);
	}

	/**
	 * <p>Returns the NoticeAudit with the specified id.</p>
	 * 
	 * @param id the id of the requested noticeAudit
	 * @return the NoticeAudit with the specified id
	 */
	public NoticeAudit findById(int id) {
		return dao.findById(new Integer(id));
	}

	/**
	 * <p>Saves the NoticeAudit passed.</p>
	 * 
	 * @param noticeAudit the noticeAudit to save
	 */
	public void save(NoticeAudit noticeAudit) {
		dao.save(noticeAudit);
	}

	/**
	 * <p>Deletes the NoticeAudit with the specified id.</p>
	 * 
	 * @param noticeAudit the noticeAudit to delete
	 */
	public void delete(NoticeAudit noticeAudit) {
		dao.delete(noticeAudit);
	}

	public void setNoticeAuditDao(NoticeAuditDao dao) {
		this.dao = dao;
	}
}
