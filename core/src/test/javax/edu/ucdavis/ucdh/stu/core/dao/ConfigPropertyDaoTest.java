package edu.ucdavis.ucdh.stu.core.dao;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;

import edu.ucdavis.ucdh.stu.core.beans.ConfigProperty;
import edu.ucdavis.ucdh.stu.core.test.dao.BaseDaoTestCase;

public class ConfigPropertyDaoTest extends BaseDaoTestCase {

	@Autowired
	protected ConfigPropertyDao dao;

	/**
	 * <p>Tests the findAll() method.</p>
	 */
	@Test
	public void testFindAll() {
		// get some test records
		ConfigProperty configProperty1 = createTestRecord1();
		ConfigProperty configProperty2 = createTestRecord2();
		// put the data in the database
		dao.save(configProperty1);
		dao.save(configProperty2);
		
		// run the findAll() method
		log.info("About to run dao.findAll() ... ");
		List<ConfigProperty> response = dao.findAll();
		
		// verify results
		Assert.assertTrue("Expected records not returned.", response.size() > 1);

		// clean up the database
		dao.delete(configProperty1);
		dao.delete(configProperty2);
	}

	/**
	 * <p>Tests the findByExample(ConfigProperty configProperty) method.</p>
	 */
	@Test
	public void testFindByExample() {
		// get some test records
		ConfigProperty configProperty1 = createTestRecord1();
		ConfigProperty configProperty2 = createTestRecord2();
		// put the data in the database
		dao.save(configProperty1);
		dao.save(configProperty2);
		
		// find configProperty1
		ConfigProperty configProperty = new ConfigProperty();
		configProperty.setName("configProperty1");
		log.info("About to run dao.findByExample(configProperty) ... ");
		List<ConfigProperty> configPropertys = dao.findByExample(configProperty);

		// verify results
		Assert.assertTrue(configPropertys != null && configPropertys.size() > 0);

		// find configProperty2
		configProperty = new ConfigProperty();
		configProperty.setName("configProperty2");
		log.info("About to run dao.findByExample(configProperty) ... ");
		configPropertys = dao.findByExample(configProperty);

		// verify results
		Assert.assertTrue(configPropertys != null && configPropertys.size() > 0);

		// clean up the database
		dao.delete(configProperty1);
		dao.delete(configProperty2);
	}

	/**
	 * <p>Tests the findByProperty(String propertyName, Object propertyValue) method.</p>
	 */
	@Test
	public void testFindByProperty() {
		// get some test records
		ConfigProperty configProperty1 = createTestRecord1();
		ConfigProperty configProperty2 = createTestRecord2();
		// put the data in the database
		dao.save(configProperty1);
		dao.save(configProperty2);
		
		// find configProperty1
		log.info("About to run dao.findByProperty(\"name\", \"configProperty1\") ... ");
		List<ConfigProperty> configPropertys = dao.findByProperty("name", "configProperty1");

		// verify results
		Assert.assertTrue(configPropertys != null && configPropertys.size() > 0);

		// find configProperty2
		log.info("About to run dao.findByProperty(\"name\", \"configProperty2\") ... ");
		configPropertys = dao.findByProperty("name", "configProperty2");

		// verify results
		Assert.assertTrue(configPropertys != null && configPropertys.size() > 0);

		// clean up the database
		dao.delete(configProperty1);
		dao.delete(configProperty2);
	}

	/**
	 * <p>Tests the findByContext(String context) method.</p>
	 */
	@Test
	public void testFindByContext() {
		// get some test records
		ConfigProperty configProperty1 = createTestRecord1();
		ConfigProperty configProperty2 = createTestRecord2();
		// put the data in the database
		dao.save(configProperty1);
		dao.save(configProperty2);
		
		// find test records
		log.info("About to run dao.findByContext(\"testonly\") ... ");
		List<ConfigProperty> configPropertys = dao.findByContext("testonly");

		// verify results
		Assert.assertTrue(configPropertys != null && configPropertys.size() > 1);

		// clean up the database
		dao.delete(configProperty1);
		dao.delete(configProperty2);
	}

	/**
	 * <p>Tests the findById(Serializable id) method.</p>
	 */
	@Test
	public void testFindById() {
		// get some test records
		ConfigProperty configProperty1 = createTestRecord1();
		ConfigProperty configProperty2 = createTestRecord2();
		// put the data in the database
		dao.save(configProperty1);
		dao.save(configProperty2);
		
		// find configProperty
		log.info("About to run dao.findById(" + configProperty1.getId() + ") ... ");
		ConfigProperty configProperty = dao.findById(configProperty1.getId());

		// verify results
		Assert.assertTrue(configProperty != null);

		// find configProperty2
		log.info("About to run dao.findById(" + configProperty2.getId() + ") ... ");
		configProperty = dao.findById(configProperty2.getId());

		// verify results
		Assert.assertTrue(configProperty != null);

		// clean up the database
		dao.delete(configProperty1);
		dao.delete(configProperty2);
	}

	/**
	 * <p>Tests the save(ConfigProperty ConfigProperty) method.</p>
	 */
	@Test
	public void testSave() {
		// get some test records
		ConfigProperty configProperty1 = createTestRecord1();
		ConfigProperty configProperty2 = createTestRecord2();

		// save configProperty 1
		log.info("About to run dao.save(configProperty1) ... ");
		dao.save(configProperty1);

		// verify results
		ConfigProperty configProperty = dao.findById(configProperty1.getId());
		Assert.assertTrue(configProperty != null);

		// save configProperty 2
		log.info("About to run dao.save(configProperty2) ... ");
		dao.save(configProperty2);

		// verify results
		configProperty = dao.findById(configProperty2.getId());
		Assert.assertTrue(configProperty != null);

		// clean up the database
		dao.delete(configProperty1);
		dao.delete(configProperty2);
	}

	/**
	 * <p>Tests the delete(ConfigProperty ConfigProperty) method.</p>
	 */
	@Test
	public void testDelete() {
		// get some test records
		ConfigProperty configProperty1 = createTestRecord1();
		ConfigProperty configProperty2 = createTestRecord2();
		// put the data in the database
		dao.save(configProperty1);
		dao.save(configProperty2);
		
		// run the dao.delete(configProperty1) method
		int id1 = configProperty1.getId();
		log.info("About to run dao.delete(configProperty1) ... ");
		dao.delete(configProperty1);
		
		// verify results
		try {
			Assert.assertTrue(dao.findById(id1) == null);
		} catch (ObjectRetrievalFailureException e) {
			log.info("Expected exception occurred after delete: " + e);
			Assert.assertTrue(1 == 1);
		}
		
		// run the dao.delete(configProperty2) method
		int id2 = configProperty2.getId();
		log.info("About to run dao.delete(configProperty2) ... ");
		dao.delete(configProperty2);
		
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
	public ConfigProperty createTestRecord1() {
		ConfigProperty configProperty = null;

		Date rightNow = new Date();
		String userId = "ConfigPropertyDaoTest";
		configProperty = new ConfigProperty();
		configProperty.setContext("testonly");
		configProperty.setName("configProperty1");
		configProperty.setValue("This is test configuration property.");
		configProperty.setCreationDate(rightNow);
		configProperty.setCreatedBy(userId);
		configProperty.setLastUpdate(rightNow);
		configProperty.setLastUpdateBy(userId);

		return configProperty;
	}

	/**
	 * <p>Builds and returns test record #2.</p>
	 */
	public ConfigProperty createTestRecord2() {
		ConfigProperty configProperty = null;

		Date rightNow = new Date();
		String userId = "ConfigPropertyDaoTest";
		configProperty = new ConfigProperty();
		configProperty.setContext("testonly");
		configProperty.setName("configProperty2");
		configProperty.setValue("This is another test configuration property.");
		configProperty.setCreationDate(rightNow);
		configProperty.setCreatedBy(userId);
		configProperty.setLastUpdate(rightNow);
		configProperty.setLastUpdateBy(userId);

		return configProperty;
	}
}
