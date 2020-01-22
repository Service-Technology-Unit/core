package edu.ucdavis.ucdh.stu.core.dao.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;

import edu.ucdavis.ucdh.stu.core.beans.Ping;
import edu.ucdavis.ucdh.stu.core.dao.PingDao;

/**
 * <p>This is the Ping data access object.</p>
 */
public class PingDaoHibernate extends AbstractHibernateUpdateDao<Ping> implements PingDao {

	/**
	 * <p>Constructs a new PingDaoHibernate using the parameters provided.</p>
	 */
	public PingDaoHibernate(SessionFactory sessionFactory) {
		super(Ping.class, sessionFactory);
	}

	/**
	 * <p>Returns all Pings in the database that match the specified identifier.</p>
	 * 
	 * @param identifier the identifier of the requested Ping(s)
	 * @return all Pings in the database that match the specified identifier
	 */
	public List<Ping> findByIdentifier(String identifier) {
		return findByProperty("identifier", identifier);
	}
}