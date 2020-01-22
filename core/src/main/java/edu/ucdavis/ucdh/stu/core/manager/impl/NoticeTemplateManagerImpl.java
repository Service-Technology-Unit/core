package edu.ucdavis.ucdh.stu.core.manager.impl;

import java.util.List;

import edu.ucdavis.ucdh.stu.core.beans.NoticeTemplate;
import edu.ucdavis.ucdh.stu.core.dao.NoticeTemplateDao;
import edu.ucdavis.ucdh.stu.core.manager.NoticeTemplateManager;

/**
 * <p>Concrete implementation of the NoticeTemplate manager interface.</p>
 */
public class NoticeTemplateManagerImpl implements NoticeTemplateManager {
	private NoticeTemplateDao dao;

	/**
	 * <p>Returns all NoticeTemplates in the database.</p>
	 * 
	 * @return all NoticeTemplates in the database
	 */
	public List<NoticeTemplate> findAll() {
		return dao.findAll();
	}

	/**
	 * <p>Returns all NoticeTemplates in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param noticeTemplate an example NoticeTemplate
	 * @return all NoticeTemplates in the database that
	 * match the specified search criteria
	 */
	public List<NoticeTemplate> findByExample(NoticeTemplate noticeTemplate) {
		return dao.findByExample(noticeTemplate);
	}

	/**
	 * <p>Returns all NoticeTemplates in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param propertyName the name of the specified property
	 * @param propertyValue the search value for the specified
	 * property
	 * @return all NoticeTemplates in the database that
	 * match the specified search criteria
	 */
	public List<NoticeTemplate> findByProperty(String propertyName, Object propertyValue) {
		return dao.findByProperty(propertyName, propertyValue);
	}

	/**
	 * <p>Returns all objects of this entity that satisfy the specified query.</p>
	 *
	 * @param hql the HQL statement to execute
	 * @return all objects of this entity that satisfy the specified query
	 */
	public List<NoticeTemplate> executeQuery(String hql) {
		return dao.executeQuery(hql);
	}

	/**
	 * <p>Returns the NoticeTemplate with the specified id.</p>
	 * 
	 * @param id the id of the requested noticeTemplate
	 * @return the NoticeTemplate with the specified id
	 */
	public NoticeTemplate findById(int id) {
		return dao.findById(new Integer(id));
	}

	/**
	 * <p>Returns the NoticeTemplate with the specified context and name.</p>
	 * 
	 * @param context the context of the requested noticeTemplate
	 * @param name the name of the requested noticeTemplate
	 * @return the NoticeTemplate with the specified id
	 */
	public NoticeTemplate findByContextAndName(String context, String name) {
		return dao.findByContextAndName(context, name);
	}

	/**
	 * <p>Saves the NoticeTemplate passed.</p>
	 * 
	 * @param noticeTemplate the noticeTemplate to save
	 */
	public void save(NoticeTemplate noticeTemplate) {
		dao.save(noticeTemplate);
	}

	/**
	 * <p>Deletes the NoticeTemplate with the specified id.</p>
	 * 
	 * @param noticeTemplate the noticeTemplate to delete
	 */
	public void delete(NoticeTemplate noticeTemplate) {
		dao.delete(noticeTemplate);
	}

	public void setNoticeTemplateDao(NoticeTemplateDao dao) {
		this.dao = dao;
	}
}
