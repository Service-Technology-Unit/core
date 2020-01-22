package edu.ucdavis.ucdh.stu.core.manager;

import java.util.List;

import edu.ucdavis.ucdh.stu.core.beans.Ping;

/**
 * <p>This is the Ping maningr interface.</p>
 */
public interface PingManager {

	/**
	 * <p>Returns all Pings in the database.</p>
	 * 
	 * @return all Pings in the database
	 */
	public List<Ping> findAll();

	/**
	 * <p>Returns all Pings in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param ping an example Ping
	 * @return all Pings in the database that
	 * match the specified search criteria
	 */
	public List<Ping> findByExample(Ping ping);

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
	public List<Ping> findByProperty(String propertyName, Object propertyValue);

	/**
	 * <p>Returns all Pings in the database that match the specified identifier.</p>
	 * 
	 * @param identifier the identifier of the requested Ping(s)
	 * @return all Pings in the database that match the specified identifier
	 */
	public List<Ping> findByIdentifier(String identifier);

	/**
	 * <p>Returns the Ping with the specified id.</p>
	 * 
	 * @param id the id of the requested ping
	 * @return the Ping with the specified id
	 */
	public Ping findById(Integer id);

	/**
	 * <p>Saves the Ping passed.</p>
	 * 
	 * @param ping the ping to save
	 */
	public void save(Ping ping);

	/**
	 * <p>Deletes the Ping with the specified id.</p>
	 * 
	 * @param ping the ping to delete
	 */
	public void delete(Ping ping);
}
