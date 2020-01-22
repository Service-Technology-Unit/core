package edu.ucdavis.ucdh.stu.core.dao.hibernate;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.Assert;

/**
 * <p>Originally written by Ray Krueger (http://raykrueger.blogspot.com/),
 * this abstract class forms the basis for all Hibernate data access objects
 * used in the various ucdhs projects. This version is an enhanced
 * version that includes a number public methods that Ray chose not to include
 * in the original. This is a read-only version, and does not include any
 * data maintenance methods.</p>
 */
@SuppressWarnings("deprecation")
public abstract class AbstractHibernateDao<E> {
	private final Class<E> entityClass;
	private final SessionFactory sessionFactory;

	/**
	 * <p>Constructs a new <code>AbstractHibernateDao</code> using the
	 * parameters provided.</p>
	 */
	public AbstractHibernateDao(Class<E> entityClass, SessionFactory sessionFactory) {
		Assert.notNull(entityClass, "entityClass must not be null");
		Assert.notNull(sessionFactory, "sessionFactory must not be null");
		this.entityClass = entityClass;
		this.sessionFactory = sessionFactory;
	}

	/**
	 * <p>Returns all entities in the database.</p>
	 * 
	 * @return all entities in the database
	 */
	public List<E> findAll() {
		return all();
	}

	/**
	 * <p>Returns all entities in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param referenceObject an example entity
	 * @return all entities in the database that
	 * match the specified search criteria
	 */
	public List<E> findByExample(E referenceObject) {
		Example example = Example.create(referenceObject);
		example.excludeProperty("id");
		return list(criteria().add(example));
	}

	/**
	 * <p>Returns all entities in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param propertyName the name of the specified property
	 * @param propertyValue the search value for the specified
	 * property
	 * @return all entities in the database that
	 * match the specified search criteria
	 */
	public List<E> findByProperty(String propertyName, Object propertyValue) {
		return list(criteria().add(Restrictions.eq(propertyName, propertyValue)));
	}

	/**
	 * <p>Returns the entity with the specified id.</p>
	 * 
	 * @param id the id of the requested entity
	 * @return the entity with the specified id
	 */
	public E findById(Serializable id) {
		return get(id);
	}

	/**
	 * <p>Returns the entity class.</p>
	 * 
	 * @return the entity class
	 */
	public Class<E> getEntityClass() {
		return entityClass;
	}

	/**
	 * <p>Returns a Hibernate Criteria object for the current session.</p>
	 * 
	 * @return a Hibernate Criteria object for the current session
	 */
	protected Criteria criteria() {
		Criteria criteria = currentSession().createCriteria(entityClass);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria;
	}

	/**
	 * <p>Returns a Hibernate Query object for the current session.</p>
	 * 
	 * @return a Hibernate Query object for the current session
	 */
	@SuppressWarnings("rawtypes")
	protected Query query(String hql) {
		return currentSession().createQuery(hql);
	}

	/**
	 * <p>Returns the current session.</p>
	 * 
	 * @return the current session
	 */
	protected Session currentSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * <p>Returns all objects of this entity.</p>
	 * 
	 * @return all objects of this entity
	 */
	protected List<E> all() {
		return list(criteria());
	}

	/**
	 * <p>Returns all objects of this entity that match the specified criteria.</p>
	 * 
	 * @param criteria the search criteria
	 * @return all objects of this entity that match the specified criteria
	 */
	@SuppressWarnings("unchecked")
	protected List<E> list(Criteria criteria) {
		return criteria.list();
	}

	/**
	 * <p>Returns all objects of this entity that satisfy the specified query.</p>
	 * 
	 * @param query the search query
	 * @return all objects of this entity that satisfy the specified query
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected List<E> list(Query query) {
		return query.list();
	}

	/**
	 * <p>Returns a single instance of this entity that matches the specified
	 * criteria.</p>
	 * 
	 * @param criteria the search criteria
	 * @return a single instance of this entity that matches the specified
	 * criteria
	 */
	@SuppressWarnings("unchecked")
	protected E uniqueResult(Criteria criteria) {
		return (E) criteria.uniqueResult();
	}

	/**
	 * <p>Returns a single instance of this entity that satisfies the specified
	 * query.</p>
	 * 
	 * @param query the search query
	 * @return a single instance of this entity that satisfies the specified
	 * query
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected E uniqueResult(Query query) {
		return (E) query.uniqueResult();
	}

	/**
	 * <p>Returns the instance of this entity with the specified id.</p>
	 * 
	 * @param id the id of the requested entity
	 * @return the instance of this entity with the specified id
	 */
	protected E get(Serializable id) {
		return (E) currentSession().get(entityClass, id);
	}
}
