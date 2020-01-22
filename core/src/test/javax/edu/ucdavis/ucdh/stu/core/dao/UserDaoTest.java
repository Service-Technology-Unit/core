package edu.ucdavis.ucdh.stu.core.dao;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;

import edu.ucdavis.ucdhs.isweb.core.beans.User;
import edu.ucdavis.ucdhs.isweb.core.test.dao.BaseDaoTestCase;

public class UserDaoTest extends BaseDaoTestCase {

	@Autowired
	protected UserDao dao;

	/**
	 * <p>Tests the findAll() method.</p>
	 */
	@Test
	public void testFindAll() {
		// get some test records
		User user1 = createTestRecord1();
		User user2 = createTestRecord2();
		// put the data in the database
		dao.save(user1);
		dao.save(user2);
		
		// run the findAll() method
		log.info("About to run dao.findAll() ... ");
		List<User> response = dao.findAll();
		
		// verify results
		Assert.assertTrue("Expected records not returned.", response.size() > 1);

		// clean up the database
		dao.delete(user1);
		dao.delete(user2);
	}

	/**
	 * <p>Tests the findByExample(User user) method.</p>
	 */
	@Test
	public void testFindByExample() {
		// get some test records
		User user1 = createTestRecord1();
		User user2 = createTestRecord2();
		// put the data in the database
		dao.save(user1);
		dao.save(user2);
		
		// find user1
		User user = new User();
		user.setName("user1");
		log.info("About to run dao.findByExample(user) ... ");
		List<User> users = dao.findByExample(user);

		// verify results
		Assert.assertTrue(users != null && users.size() > 0);

		// find user2
		user = new User();
		user.setName("user2");
		log.info("About to run dao.findByExample(user) ... ");
		users = dao.findByExample(user);

		// verify results
		Assert.assertTrue(users != null && users.size() > 0);

		// clean up the database
		dao.delete(user1);
		dao.delete(user2);
	}

	/**
	 * <p>Tests the findByProperty(String propertyName, Object propertyValue) method.</p>
	 */
	@Test
	public void testFindByProperty() {
		// get some test records
		User user1 = createTestRecord1();
		User user2 = createTestRecord2();
		// put the data in the database
		dao.save(user1);
		dao.save(user2);
		
		// find user1
		log.info("About to run dao.findByProperty(\"name\", \"user1\") ... ");
		List<User> users = dao.findByProperty("name", "user1");

		// verify results
		Assert.assertTrue(users != null && users.size() > 0);

		// find user2
		log.info("About to run dao.findByProperty(\"name\", \"user2\") ... ");
		users = dao.findByProperty("name", "user2");

		// verify results
		Assert.assertTrue(users != null && users.size() > 0);

		// clean up the database
		dao.delete(user1);
		dao.delete(user2);
	}

	/**
	 * <p>Tests the findByContext(String context) method.</p>
	 */
	@Test
	public void testFindByContext() {
		// get some test records
		User user1 = createTestRecord1();
		User user2 = createTestRecord2();
		// put the data in the database
		dao.save(user1);
		dao.save(user2);
		
		// find test records
		log.info("About to run dao.findByContext(\"testonly\") ... ");
		List<User> users = dao.findByContext("testonly");

		// verify results
		Assert.assertTrue(users != null && users.size() > 1);

		// clean up the database
		dao.delete(user1);
		dao.delete(user2);
	}

	/**
	 * <p>Tests the findById(Serializable id) method.</p>
	 */
	@Test
	public void testFindById() {
		// get some test records
		User user1 = createTestRecord1();
		User user2 = createTestRecord2();
		// put the data in the database
		dao.save(user1);
		dao.save(user2);
		
		// find user
		log.info("About to run dao.findById(" + user1.getId() + ") ... ");
		User user = dao.findById(user1.getId());

		// verify results
		Assert.assertTrue(user != null);

		// find user2
		log.info("About to run dao.findById(" + user2.getId() + ") ... ");
		user = dao.findById(user2.getId());

		// verify results
		Assert.assertTrue(user != null);

		// clean up the database
		dao.delete(user1);
		dao.delete(user2);
	}

	/**
	 * <p>Tests the save(User User) method.</p>
	 */
	@Test
	public void testSave() {
		// get some test records
		User user1 = createTestRecord1();
		User user2 = createTestRecord2();

		// save user 1
		log.info("About to run dao.save(user1) ... ");
		dao.save(user1);

		// verify results
		User user = dao.findById(user1.getId());
		Assert.assertTrue(user != null);

		// save user 2
		log.info("About to run dao.save(user2) ... ");
		dao.save(user2);

		// verify results
		user = dao.findById(user2.getId());
		Assert.assertTrue(user != null);

		// clean up the database
		dao.delete(user1);
		dao.delete(user2);
	}

	/**
	 * <p>Tests the delete(User User) method.</p>
	 */
	@Test
	public void testDelete() {
		// get some test records
		User user1 = createTestRecord1();
		User user2 = createTestRecord2();
		// put the data in the database
		dao.save(user1);
		dao.save(user2);
		
		// run the dao.delete(user1) method
		int id1 = user1.getId();
		log.info("About to run dao.delete(user1) ... ");
		dao.delete(user1);
		
		// verify results
		try {
			Assert.assertTrue(dao.findById(id1) == null);
		} catch (ObjectRetrievalFailureException e) {
			log.info("Expected exception occurred after delete: " + e);
			Assert.assertTrue(1 == 1);
		}
		
		// run the dao.delete(user2) method
		int id2 = user2.getId();
		log.info("About to run dao.delete(user2) ... ");
		dao.delete(user2);
		
		// verify results
		try {
			Assert.assertTrue(dao.findById(id2) == null);
		} catch (ObjectRetrievalFailureException e) {
			log.info("Expected exception occurred after delete: " + e);
			Assert.assertTrue(1 == 1);
		}
	}

	/**
	 * <p>Builds and returns test record #1.</p>
	 */
	public User createTestRecord1() {
		User user = null;

		Date rightNow = new Date();
		String userId = "UserDaoTest";
		user = new User();
		user.setContext("testonly");
		user.setName("user1");
		user.setLastName("lastName");
		user.setFirstName("firstName");
		user.setMiddleName("middleName");
		user.setEmail("user1@ucdmc.ucdavis.edu");
		user.setCreationDate(rightNow);
		user.setCreatedBy(userId);
		user.setLastUpdate(rightNow);
		user.setLastUpdateBy(userId);

		return user;
	}

	/**
	 * <p>Builds and returns test record #2.</p>
	 */
	public User createTestRecord2() {
		User user = null;

		Date rightNow = new Date();
		String userId = "UserDaoTest";
		user = new User();
		user.setContext("testonly");
		user.setName("user2");
		user.setLastName("Doe");
		user.setFirstName("John");
		user.setMiddleName("");
		user.setEmail("user2@ucdmc.ucdavis.edu");
		user.setCreationDate(rightNow);
		user.setCreatedBy(userId);
		user.setLastUpdate(rightNow);
		user.setLastUpdateBy(userId);

		return user;
	}
}
