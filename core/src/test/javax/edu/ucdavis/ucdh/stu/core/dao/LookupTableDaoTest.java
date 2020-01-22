package edu.ucdavis.ucdh.stu.core.dao;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;

import edu.ucdavis.ucdhs.isweb.core.beans.LookupTable;
import edu.ucdavis.ucdhs.isweb.core.beans.LookupTableProperty;
import edu.ucdavis.ucdhs.isweb.core.test.dao.BaseDaoTestCase;

public class LookupTableDaoTest extends BaseDaoTestCase {

	@Autowired
	protected LookupTableDao dao;

	/**
	 * <p>Tests the findAll() method.</p>
	 */
	@Test
	public void testFindAll() {
		// get some test records
		LookupTable table1 = createTestRecord1();
		LookupTable table2 = createTestRecord2();
		// put the data in the database
		dao.save(table1);
		dao.save(table2);
		
		// run the findAll() method
		log.info("About to run dao.findAll() ... ");
		List<LookupTable> response = dao.findAll();
		
		// verify results
		Assert.assertTrue("Expected records not returned.", response.size() > 1);

		// clean up the database
		dao.delete(table1);
		dao.delete(table2);
	}

	/**
	 * <p>Tests the findByExample(LookupTable lookupTable) method.</p>
	 */
	@Test
	public void testFindByExample() {
		// get some test records
		LookupTable table1 = createTestRecord1();
		LookupTable table2 = createTestRecord2();
		// put the data in the database
		dao.save(table1);
		dao.save(table2);
		
		// find testtable
		LookupTable lookupTable = new LookupTable();
		lookupTable.setDisplayName("Test Table");
		log.info("About to run dao.findByExample(lookupTable) ... ");
		List<LookupTable> tables = dao.findByExample(lookupTable);

		// verify results
		Assert.assertTrue(tables != null && tables.size() > 0);

		// find testtable2
		lookupTable = new LookupTable();
		lookupTable.setDisplayName("Test Table #2");
		log.info("About to run dao.findByExample(lookupTable) ... ");
		tables = dao.findByExample(lookupTable);

		// verify results
		Assert.assertTrue(tables != null && tables.size() > 0);

		// clean up the database
		dao.delete(table1);
		dao.delete(table2);
	}

	/**
	 * <p>Tests the findByProperty(String propertyName, Object propertyValue) method.</p>
	 */
	@Test
	public void testFindByProperty() {
		// get some test records
		LookupTable table1 = createTestRecord1();
		LookupTable table2 = createTestRecord2();
		// put the data in the database
		dao.save(table1);
		dao.save(table2);
		
		// find testtable
		log.info("About to run dao.findByProperty(\"displayName\", \"Test Table\") ... ");
		List<LookupTable> tables = dao.findByProperty("displayName", "Test Table");

		// verify results
		Assert.assertTrue(tables != null && tables.size() > 0);

		// find testtable2
		log.info("About to run dao.findByProperty(\"displayName\", \"Test Table #2\") ... ");
		tables = dao.findByProperty("displayName", "Test Table #2");

		// verify results
		Assert.assertTrue(tables != null && tables.size() > 0);

		// clean up the database
		dao.delete(table1);
		dao.delete(table2);
	}

	/**
	 * <p>Tests the findByContext(String context) method.</p>
	 */
	@Test
	public void testFindByContext() {
		// get some test records
		LookupTable table1 = createTestRecord1();
		LookupTable table2 = createTestRecord2();
		// put the data in the database
		dao.save(table1);
		dao.save(table2);
		
		// find test records
		log.info("About to run dao.findByContext(\"testonly\") ... ");
		List<LookupTable> tables = dao.findByContext("testonly");

		// verify results
		Assert.assertTrue(tables != null && tables.size() > 1);

		// clean up the database
		dao.delete(table1);
		dao.delete(table2);
	}

	/**
	 * <p>Tests the findByContextAndTableName(String context, String tableName) method.</p>
	 */
	@Test
	public void testFindByContextAndTableName() {
		// get some test records
		LookupTable table1 = createTestRecord1();
		LookupTable table2 = createTestRecord2();
		// put the data in the database
		dao.save(table1);
		dao.save(table2);
		
		// find testtable
		log.info("About to run dao.findByContextAndTableName(\"testonly\", \"testtable\") ... ");
		LookupTable table = dao.findByContextAndTableName("testonly", "testtable");

		// verify results
		Assert.assertTrue(table != null);

		// find testtable2
		log.info("About to run dao.findByContextAndTableName(\"testonly\", \"testtable2\") ... ");
		table = dao.findByContextAndTableName("testonly", "testtable2");

		// verify results
		Assert.assertTrue(table != null);

		// clean up the database
		dao.delete(table1);
		dao.delete(table2);
	}

	/**
	 * <p>Tests the findById(Serializable id) method.</p>
	 */
	@Test
	public void testFindById() {
		// get some test records
		LookupTable table1 = createTestRecord1();
		LookupTable table2 = createTestRecord2();
		// put the data in the database
		dao.save(table1);
		dao.save(table2);
		
		// find testtable
		log.info("About to run dao.findById(" + table1.getId() + ") ... ");
		LookupTable table = dao.findById(table1.getId());

		// verify results
		Assert.assertTrue(table != null);

		// find testtable2
		log.info("About to run dao.findById(" + table2.getId() + ") ... ");
		table = dao.findById(table2.getId());

		// verify results
		Assert.assertTrue(table != null);

		// clean up the database
		dao.delete(table1);
		dao.delete(table2);
	}

	/**
	 * <p>Tests the save(LookupTable LookupTable) method.</p>
	 */
	@Test
	public void testSave() {
		// get some test records
		LookupTable table1 = createTestRecord1();
		LookupTable table2 = createTestRecord2();

		// save table 1
		log.info("About to run dao.save(table1) ... ");
		dao.save(table1);

		// verify results
		LookupTable table = dao.findById(table1.getId());
		Assert.assertTrue(table != null);

		// save table 2
		log.info("About to run dao.save(table2) ... ");
		dao.save(table2);

		// verify results
		table = dao.findById(table2.getId());
		Assert.assertTrue(table != null);

		// clean up the database
		dao.delete(table1);
		dao.delete(table2);
	}

	/**
	 * <p>Tests the delete(LookupTable LookupTable) method.</p>
	 */
	@Test
	public void testDelete() {
		// get some test records
		LookupTable table1 = createTestRecord1();
		LookupTable table2 = createTestRecord2();
		// put the data in the database
		dao.save(table1);
		dao.save(table2);
		
		// run the dao.delete(table1) method
		int id1 = table1.getId();
		log.info("About to run dao.delete(table1) ... ");
		dao.delete(table1);
		
		// verify results
		try {
			Assert.assertTrue(dao.findById(id1) == null);
		} catch (ObjectRetrievalFailureException e) {
			log.info("Expected exception occurred after delete: " + e);
			Assert.assertTrue(1 == 1);
		}
		
		// run the dao.delete(table2) method
		int id2 = table2.getId();
		log.info("About to run dao.delete(table2) ... ");
		dao.delete(table2);
		
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
	public LookupTable createTestRecord1() {
		LookupTable lookupTable = null;

		Date rightNow = new Date();
		String userId = "LookupTableDaoTest";
		lookupTable = new LookupTable();
		lookupTable.setContext("testonly");
		lookupTable.setTableName("testtable");
		lookupTable.setDisplayName("Test Table");
		lookupTable.setDescription("This is a test table.");
		lookupTable.setCreationDate(rightNow);
		lookupTable.setCreatedBy(userId);
		lookupTable.setLastUpdate(rightNow);
		lookupTable.setLastUpdateBy(userId);

		return lookupTable;
	}

	/**
	 * <p>Builds and returns test record #2.</p>
	 */
	public LookupTable createTestRecord2() {
		LookupTable lookupTable = null;

		Date rightNow = new Date();
		String userId = "LookupTableDaoTest";
		lookupTable = new LookupTable();
		lookupTable.setContext("testonly");
		lookupTable.setTableName("testtable2");
		lookupTable.setDisplayName("Test Table #2");
		lookupTable.setDescription("This is another test table.");
		lookupTable.setCreationDate(rightNow);
		lookupTable.setCreatedBy(userId);
		lookupTable.setLastUpdate(rightNow);
		lookupTable.setLastUpdateBy(userId);

		LookupTableProperty lookupTableProperty = new LookupTableProperty();
		lookupTableProperty.setName("testProperty");
		lookupTableProperty.setType("String");
		lookupTableProperty.setSize("10");
		lookupTableProperty.setLabel("Test Property");
		lookupTableProperty.setColHeading("Test Prop");
		lookupTableProperty.setInputRequired(true);
		lookupTableProperty.setDisplayOnList(true);
		lookupTableProperty.setCreationDate(rightNow);
		lookupTableProperty.setCreatedBy(userId);
		lookupTableProperty.setLastUpdate(rightNow);
		lookupTableProperty.setLastUpdateBy(userId);
		lookupTable.addProperty(lookupTableProperty);

		lookupTableProperty = new LookupTableProperty();
		lookupTableProperty.setName("testProperty2");
		lookupTableProperty.setType("String");
		lookupTableProperty.setSize("20");
		lookupTableProperty.setLabel("Test Property #2");
		lookupTableProperty.setColHeading("Test Prop2");
		lookupTableProperty.setInputRequired(false);
		lookupTableProperty.setDisplayOnList(false);
		lookupTableProperty.setNotes("This is a test.");
		lookupTableProperty.setCreationDate(rightNow);
		lookupTableProperty.setCreatedBy(userId);
		lookupTableProperty.setLastUpdate(rightNow);
		lookupTableProperty.setLastUpdateBy(userId);
		lookupTable.addProperty(lookupTableProperty);

		return lookupTable;
	}
}
