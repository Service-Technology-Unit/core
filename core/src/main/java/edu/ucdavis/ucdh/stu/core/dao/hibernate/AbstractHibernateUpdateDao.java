package edu.ucdavis.ucdh.stu.core.dao.hibernate;

import org.hibernate.SessionFactory;

/**
 * <p>Extends the <code>AbstractHibernateDao</code> to create an update version,
 * adding standard data maintenance methods.</p>
 */
public abstract class AbstractHibernateUpdateDao<E> extends AbstractHibernateDao<E> {

	/**
	 * <p>Constructs a new <code>AbstractHibernateUpdateDao</code> using the
	 * parameters provided.</p>
	 */
	public AbstractHibernateUpdateDao(Class<E> entityClass, SessionFactory sessionFactory) {
		super(entityClass, sessionFactory);
	}

	/**
	 * <p>Saves the entity passed.</p>
	 * 
	 * @param entity the entity to save
	 */
	public void save(E entity) {
		currentSession().saveOrUpdate(entity);
	}

	/**
	 * <p>Deletes the entity.</p>
	 * 
	 * @param entity the entity to delete
	 */
	public void delete(E entity) {
		currentSession().delete(entity);
	}
}
