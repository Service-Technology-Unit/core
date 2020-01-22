package edu.ucdavis.ucdh.stu.core.manager.impl;

import java.util.List;

import edu.ucdavis.ucdh.stu.core.beans.User;
import edu.ucdavis.ucdh.stu.core.dao.UserDao;
import edu.ucdavis.ucdh.stu.core.manager.UserManager;

/**
 * <p>Concrete implementation of the User manager interface.</p>
 */
public class UserManagerImpl implements UserManager {
	private UserDao dao;

	/**
	 * <p>Returns all Users in the database.</p>
	 * 
	 * @return all Users in the database
	 */
	public List<User> findAll() {
		return dao.findAll();
	}

	/**
	 * <p>Returns all Users in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param user an example User
	 * @return all Users in the database that
	 * match the specified search criteria
	 */
	public List<User> findByExample(User user) {
		return dao.findByExample(user);
	}

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
	public List<User> findByProperty(String propertyName, Object propertyValue) {
		return dao.findByProperty(propertyName, propertyValue);
	}

	/**
	 * <p>Returns all Users in the database for the specified
	 * context.</p>
	 * 
	 * @param context the requested context
	 * @return all Users in the database for the specified
	 * context
	 */
	public List<User> findByContext(String context) {
		return dao.findByContext(context);
	}

	/**
	 * <p>Returns the User with the specified context and name.</p>
	 * 
	 * @param context the context of the requested User
	 * @param name the username of the requested User
	 * @return the User with the specified context and name
	 */
	public User findByContextAndName(String context, String name) {
		return dao.findByContextAndName(context, name);
	}

	/**
	 * <p>Returns the User with the specified id.</p>
	 * 
	 * @param id the id of the requested user
	 * @return the User with the specified id
	 */
	public User findById(String id) {
		return dao.findById(id);
	}

	/**
	 * <p>Saves the User passed.</p>
	 * 
	 * @param user the user to save
	 */
	public void save(User user) {
		dao.save(user);
	}

	/**
	 * <p>Deletes the User with the specified id.</p>
	 * 
	 * @param user the user to delete
	 */
	public void delete(User user) {
		dao.delete(user);
	}

	/**
	 * <p>Sets the UserDao.</p>
	 * 
	 * @param userDao the userDao to set
	 */
	public void setUserDao(UserDao dao) {
		this.dao = dao;
	}
}
