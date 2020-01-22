package edu.ucdavis.ucdh.stu.core.dao;

import java.io.Serializable;
import java.util.List;

import edu.ucdavis.ucdh.stu.core.beans.User;

/**
 * <p>This is the User data access object interface.</p>
 */
public interface UserDao extends Dao {

	/**
	 * <p>Returns all Users in the database.</p>
	 * 
	 * @return all Users in the database
	 */
	public List<User> findAll();

	/**
	 * <p>Returns all Users in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param user an example User
	 * @return all Users in the database that
	 * match the specified search criteria
	 */
	public List<User> findByExample(User user);

	/**
	 * <p>Returns all Users in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param propertyName the name of the specified property
	 * @param propertyValue the search value for the specified
	 * property
	 * @return all Users in the database that
	 * match the specified search criteria
	 */
	public List<User> findByProperty(String propertyName, Object propertyValue);

	/**
	 * <p>Returns all Users in the database for the specified
	 * context.</p>
	 * 
	 * @param context the requested context
	 * @return all Users in the database for the specified
	 * context
	 */
	public List<User> findByContext(String context);

	/**
	 * <p>Returns the User with the specified context and name.</p>
	 * 
	 * @param context the context of the requested User
	 * @param name the username of the requested User
	 * @return the User with the specified context and name
	 */
	public User findByContextAndName(String context, String name);

	/**
	 * <p>Returns the User with the specified id.</p>
	 * 
	 * @param id the id of the requested user
	 * @return the User with the specified id
	 */
	public User findById(Serializable id);

	/**
	 * <p>Saves the User passed.</p>
	 * 
	 * @param user the user to save
	 */
	public void save(User user);

	/**
	 * <p>Deletes the User with the specified id.</p>
	 * 
	 * @param user the user to delete
	 */
	public void delete(User user);
}
