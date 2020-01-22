package edu.ucdavis.ucdh.stu.core.dao.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;

import edu.ucdavis.ucdh.stu.core.beans.User;
import edu.ucdavis.ucdh.stu.core.dao.UserDao;

/**
 * <p>This is the User data access object.</p>
 */
public class UserDaoHibernate extends AbstractHibernateUpdateDao<User> implements UserDao {

	/**
	 * <p>Constructs a new UserDaoHibernate using the parameters provided.</p>
	 */
	public UserDaoHibernate(SessionFactory sessionFactory) {
		super(User.class, sessionFactory);
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
		return findByProperty("context", context);
	}

	/**
	 * <p>Returns the User with the specified context and name.</p>
	 * 
	 * @param context the context of the requested User
	 * @param name the username of the requested User
	 * @return the User with the specified context and name
	 */
	public User findByContextAndName(String context, String name) {
		User user = new User();
		user.setId(null);
		user.setContext(context);
		user.setName(name);
		List<User> userEntries = findByExample(user);
		if (userEntries != null && userEntries.size() > 0) {
			user = userEntries.get(0);
		} else {
			user = null;
		}
		return user;
	}
}