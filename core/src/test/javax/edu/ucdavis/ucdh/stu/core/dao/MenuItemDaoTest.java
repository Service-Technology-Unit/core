package edu.ucdavis.ucdh.stu.core.dao;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;

import edu.ucdavis.ucdhs.isweb.core.beans.MenuItem;
import edu.ucdavis.ucdhs.isweb.core.test.dao.BaseDaoTestCase;

public class MenuItemDaoTest extends BaseDaoTestCase {

	@Autowired
	protected MenuItemDao dao;

	/**
	 * <p>Tests the findAll() method.</p>
	 */
	@Test
	public void testFindAll() {
		// get some test records
		MenuItem menuItem1 = createTestRecord1();
		MenuItem menuItem2 = createTestRecord2();
		// put the data in the database
		dao.save(menuItem1);
		dao.save(menuItem2);
		
		// run the findAll() method
		log.info("About to run dao.findAll() ... ");
		List<MenuItem> response = dao.findAll();
		
		// verify results
		Assert.assertTrue("Expected records not returned.", response.size() > 1);

		// clean up the database
		dao.delete(menuItem1);
		dao.delete(menuItem2);
	}

	/**
	 * <p>Tests the findByExample(MenuItem menuItem) method.</p>
	 */
	@Test
	public void testFindByExample() {
		// get some test records
		MenuItem menuItem1 = createTestRecord1();
		MenuItem menuItem2 = createTestRecord2();
		// put the data in the database
		dao.save(menuItem1);
		dao.save(menuItem2);
		
		// find menuItem1
		MenuItem menuItem = new MenuItem();
		menuItem.setName("menuItem1");
		log.info("About to run dao.findByExample(menuItem) ... ");
		List<MenuItem> menuItems = dao.findByExample(menuItem);

		// verify results
		Assert.assertTrue(menuItems != null && menuItems.size() > 0);

		// find menuItem2
		menuItem = new MenuItem();
		menuItem.setName("menuItem2");
		log.info("About to run dao.findByExample(menuItem) ... ");
		menuItems = dao.findByExample(menuItem);

		// verify results
		Assert.assertTrue(menuItems != null && menuItems.size() > 0);

		// clean up the database
		dao.delete(menuItem1);
		dao.delete(menuItem2);
	}

	/**
	 * <p>Tests the findByProperty(String propertyName, Object propertyValue) method.</p>
	 */
	@Test
	public void testFindByProperty() {
		// get some test records
		MenuItem menuItem1 = createTestRecord1();
		MenuItem menuItem2 = createTestRecord2();
		// put the data in the database
		dao.save(menuItem1);
		dao.save(menuItem2);
		
		// find menuItem1
		log.info("About to run dao.findByProperty(\"name\", \"menuItem1\") ... ");
		List<MenuItem> menuItems = dao.findByProperty("name", "menuItem1");

		// verify results
		Assert.assertTrue(menuItems != null && menuItems.size() > 0);

		// find menuItem2
		log.info("About to run dao.findByProperty(\"name\", \"menuItem2\") ... ");
		menuItems = dao.findByProperty("name", "menuItem2");

		// verify results
		Assert.assertTrue(menuItems != null && menuItems.size() > 0);

		// clean up the database
		dao.delete(menuItem1);
		dao.delete(menuItem2);
	}

	/**
	 * <p>Tests the findById(Serializable id) method.</p>
	 */
	@Test
	public void testFindById() {
		// get some test records
		MenuItem menuItem1 = createTestRecord1();
		MenuItem menuItem2 = createTestRecord2();
		// put the data in the database
		dao.save(menuItem1);
		dao.save(menuItem2);
		
		// find menuItem
		log.info("About to run dao.findById(" + menuItem1.getId() + ") ... ");
		MenuItem menuItem = dao.findById(menuItem1.getId());

		// verify results
		Assert.assertTrue(menuItem != null);

		// find menuItem2
		log.info("About to run dao.findById(" + menuItem2.getId() + ") ... ");
		menuItem = dao.findById(menuItem2.getId());

		// verify results
		Assert.assertTrue(menuItem != null);

		// clean up the database
		dao.delete(menuItem1);
		dao.delete(menuItem2);
	}

	/**
	 * <p>Tests the save(MenuItem MenuItem) method.</p>
	 */
	@Test
	public void testSave() {
		// get some test records
		MenuItem menuItem1 = createTestRecord1();
		MenuItem menuItem2 = createTestRecord2();

		// save menuItem 1
		log.info("About to run dao.save(menuItem1) ... ");
		dao.save(menuItem1);

		// verify results
		MenuItem menuItem = dao.findById(menuItem1.getId());
		Assert.assertTrue(menuItem != null);

		// save menuItem 2
		log.info("About to run dao.save(menuItem2) ... ");
		dao.save(menuItem2);

		// verify results
		menuItem = dao.findById(menuItem2.getId());
		Assert.assertTrue(menuItem != null);

		// clean up the database
		dao.delete(menuItem1);
		dao.delete(menuItem2);
	}

	/**
	 * <p>Tests the delete(MenuItem MenuItem) method.</p>
	 */
	@Test
	public void testDelete() {
		// get some test records
		MenuItem menuItem1 = createTestRecord1();
		MenuItem menuItem2 = createTestRecord2();
		// put the data in the database
		dao.save(menuItem1);
		dao.save(menuItem2);
		
		// run the dao.delete(menuItem1) method
		int id1 = menuItem1.getId();
		log.info("About to run dao.delete(menuItem1) ... ");
		dao.delete(menuItem1);
		
		// verify results
		try {
			Assert.assertTrue(dao.findById(id1) == null);
		} catch (ObjectRetrievalFailureException e) {
			log.info("Expected exception occurred after delete: " + e);
			Assert.assertTrue(1 == 1);
		}
		
		// run the dao.delete(menuItem2) method
		int id2 = menuItem2.getId();
		log.info("About to run dao.delete(menuItem2) ... ");
		dao.delete(menuItem2);
		
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
	public MenuItem createTestRecord1() {
		MenuItem menuItem = null;

		Date rightNow = new Date();
		String userId = "MenuItemDaoTest";
		menuItem = new MenuItem();
		menuItem.setContext("testonly");
		menuItem.setName("menuItem1");
		menuItem.setLabel("label");
		menuItem.setDestination("destination");
		menuItem.setAuthorizedRoles("ROLE_USER");
		menuItem.setCreationDate(rightNow);
		menuItem.setCreatedBy(userId);
		menuItem.setLastUpdate(rightNow);
		menuItem.setLastUpdateBy(userId);

		return menuItem;
	}

	/**
	 * <p>Builds and returns test record #2.</p>
	 */
	public MenuItem createTestRecord2() {
		MenuItem menuItem = null;

		Date rightNow = new Date();
		String userId = "MenuItemDaoTest";
		menuItem = new MenuItem();
		menuItem.setContext("testonly");
		menuItem.setName("menuItem2");
		menuItem.setLabel("label");
		menuItem.setDestination("destination");
		menuItem.setAuthorizedRoles("ROLE_USER");
		menuItem.setCreationDate(rightNow);
		menuItem.setCreatedBy(userId);
		menuItem.setLastUpdate(rightNow);
		menuItem.setLastUpdateBy(userId);
		for (int i=0; i<5; i++) {
			MenuItem subItem = new MenuItem();
			subItem.setLabel("label" + i);
			subItem.setDestination("destination" + i);
			subItem.setAuthorizedRoles("ROLE_USER");
			subItem.setCreationDate(rightNow);
			subItem.setCreatedBy(userId);
			subItem.setLastUpdate(rightNow);
			subItem.setLastUpdateBy(userId);
			menuItem.addMenuItem(subItem);
		}

		return menuItem;
	}
}
