package edu.ucdavis.ucdh.stu.core.dao;

import java.io.Serializable;
import java.util.List;

import edu.ucdavis.ucdh.stu.core.beans.NoticeTemplate;

/**
 * <p>This is the NoticeTemplate data access object interface.</p>
 */
public interface NoticeTemplateDao extends Dao {

	/**
	 * <p>Returns all NoticeTemplates in the database.</p>
	 * 
	 * @return all NoticeTemplates in the database
	 */
	public List<NoticeTemplate> findAll();

	/**
	 * <p>Returns all NoticeTemplates in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param noticeTemplate an example NoticeTemplate
	 * @return all NoticeTemplates in the database that
	 * match the specified search criteria
	 */
	public List<NoticeTemplate> findByExample(NoticeTemplate noticeTemplate);

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
	public List<NoticeTemplate> findByProperty(String propertyName, Object propertyValue);

	/**
	 * <p>Returns all objects of this entity that satisfy the specified query.</p>
	 *
	 * @param hql the HQL statement to execute
	 * @return all objects of this entity that satisfy the specified query
	 */
	public List<NoticeTemplate> executeQuery(String hql);

	/**
	 * <p>Returns the NoticeTemplate with the specified id.</p>
	 * 
	 * @param id the id of the requested noticeTemplate
	 * @return the NoticeTemplate with the specified id
	 */
	public NoticeTemplate findById(Serializable id);

	/**
	 * <p>Returns the NoticeTemplate with the specified name and context.</p>
	 * 
	 * @param context the context of the requested noticeTemplate
	 * @param name the name of the requested noticeTemplate
	 * @return the NoticeTemplate with the specified context and name
	 */
	public NoticeTemplate findByContextAndName(String context, String name);

	/**
	 * <p>Saves the NoticeTemplate passed.</p>
	 * 
	 * @param noticeTemplate the noticeTemplate to save
	 */
	public void save(NoticeTemplate NoticeTemplate);

	/**
	 * <p>Deletes the NoticeTemplate with the specified id.</p>
	 * 
	 * @param noticeTemplate the noticeTemplate to delete
	 */
	public void delete(NoticeTemplate NoticeTemplate);
}
