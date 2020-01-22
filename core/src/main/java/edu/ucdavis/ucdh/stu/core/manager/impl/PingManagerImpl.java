package edu.ucdavis.ucdh.stu.core.manager.impl;

import java.util.List;

import edu.ucdavis.ucdh.stu.core.beans.Ping;
import edu.ucdavis.ucdh.stu.core.dao.PingDao;
import edu.ucdavis.ucdh.stu.core.manager.PingManager;

/**
 * <p>Concrete implementation of the Ping manager interface.</p>
 */
public class PingManagerImpl implements PingManager {
	private PingDao dao;

	/**
	 * <p>Returns all Pings in the database.</p>
	 * 
	 * @return all Pings in the database
	 */
	public List<Ping> findAll() {
		return dao.findAll();
	}

	/**
	 * <p>Returns all Pings in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param ping an example Ping
	 * @return all Pings in the database that
	 * match the specified search criteria
	 */
	public List<Ping> findByExample(Ping ping) {
		return dao.findByExample(ping);
	}

	/**
	 * <p>Returns all Pings in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param propertyName the name of the specified property
	 * @param propertyValue the search value for the specified
	 * property
	 * @return all Pings in the database that
	 * match the specified search criteria
	 */
	public List<Ping> findByProperty(String propertyName, Object propertyValue) {
		return dao.findByProperty(propertyName, propertyValue);
	}

	/**
	 * <p>Returns all Pings in the database that match the specified identifier.</p>
	 * 
	 * @param identifier the identifier of the requested Ping(s)
	 * @return all Pings in the database that match the specified identifier
	 */
	public List<Ping> findByIdentifier(String identifier) {
		return dao.findByIdentifier(identifier);
	}

	/**
	 * <p>Returns the Ping with the specified id.</p>
	 * 
	 * @param id the id of the requested ping
	 * @return the Ping with the specified id
	 */
	public Ping findById(Integer id) {
		return dao.findById(id);
	}

	/**
	 * <p>Saves the Ping passed.</p>
	 * 
	 * @param ping the ping to save
	 */
	public void save(Ping ping) {
		dao.save(ping);
	}

	/**
	 * <p>Deletes the Ping with the specified id.</p>
	 * 
	 * @param ping the ping to delete
	 */
	public void delete(Ping ping) {
		dao.delete(ping);
	}

	/**
	 * <p>Sets the PingDao.</p>
	 * 
	 * @param pingDao the pingDao to set
	 */
	public void setPingDao(PingDao dao) {
		this.dao = dao;
	}
}
