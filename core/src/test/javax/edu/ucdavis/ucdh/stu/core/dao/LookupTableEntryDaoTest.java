package edu.ucdavis.ucdh.stu.core.dao;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;

import edu.ucdavis.ucdhs.isweb.core.beans.LookupTableEntry;
import edu.ucdavis.ucdhs.isweb.core.test.dao.BaseDaoTestCase;

public class LookupTableEntryDaoTest extends BaseDaoTestCase {

	@Autowired
	protected LookupTableEntryDao dao;

	/**
	 * <p>Tests the findAll() method.</p>
	 */
	@Test
	public void testFindAll() {
		// get some test records
		LookupTableEntry tableEntry1 = createTestRecord1();
		LookupTableEntry tableEntry2 = createTestRecord2();
		// put the data in the database
		dao.save(tableEntry1);
		dao.save(tableEntry2);
		
		// run the findAll() method
		log.info("About to run dao.findAll() ... ");
		List<LookupTableEntry> response = dao.findAll();
		
		// verify results
		Assert.assertTrue("Expected records not returned.", response.size() > 1);

		// clean up the database
		dao.delete(tableEntry1);
		dao.delete(tableEntry2);
	}

	/**
	 * <p>Tests the findByExample(LookupTableEntry lookupTableEntry) method.</p>
	 */
	@Test
	public void testFindByExample() {
		// get some test records
		LookupTableEntry tableEntry1 = createTestRecord1();
		LookupTableEntry tableEntry2 = createTestRecord2();
		// put the data in the database
		dao.save(tableEntry1);
		dao.save(tableEntry2);
		
		// find entry1
		LookupTableEntry lookupTableEntry = new LookupTableEntry();
		lookupTableEntry.setEntryId("entry1");
		log.info("About to run dao.findByExample(lookupTableEntry) ... ");
		List<LookupTableEntry> tableEntries = dao.findByExample(lookupTableEntry);

		// verify results
		Assert.assertTrue(tableEntries != null && tableEntries.size() > 0);

		// find entry2
		lookupTableEntry = new LookupTableEntry();
		lookupTableEntry.setEntryId("entry2");
		log.info("About to run dao.findByExample(lookupTableEntry) ... ");
		tableEntries = dao.findByExample(lookupTableEntry);

		// verify results
		Assert.assertTrue(tableEntries != null && tableEntries.size() > 0);

		// clean up the database
		dao.delete(tableEntry1);
		dao.delete(tableEntry2);
	}

	/**
	 * <p>Tests the findByProperty(String propertyName, Object propertyValue) method.</p>
	 */
	@Test
	public void testFindByProperty() {
		// get some test records
		LookupTableEntry tableEntry1 = createTestRecord1();
		LookupTableEntry tableEntry2 = createTestRecord2();
		// put the data in the database
		dao.save(tableEntry1);
		dao.save(tableEntry2);
		
		// find testtableEntry
		log.info("About to run dao.findByProperty(\"entryId\", \"entry1\") ... ");
		List<LookupTableEntry> tableEntries = dao.findByProperty("entryId", "entry1");

		// verify results
		Assert.assertTrue(tableEntries != null && tableEntries.size() > 0);

		// find testtableEntry2
		log.info("About to run dao.findByProperty(\"entryId\", \"entry2\") ... ");
		tableEntries = dao.findByProperty("entryId", "entry2");

		// verify results
		Assert.assertTrue(tableEntries != null && tableEntries.size() > 0);

		// clean up the database
		dao.delete(tableEntry1);
		dao.delete(tableEntry2);
	}

	/**
	 * <p>Tests the findByContextAndTableName(String context, String tableName) method.</p>
	 */
	@Test
	public void testFindByContextAndTableName() {
		// get some test records
		LookupTableEntry tableEntry1 = createTestRecord1();
		LookupTableEntry tableEntry2 = createTestRecord2();
		// put the data in the database
		dao.save(tableEntry1);
		dao.save(tableEntry2);
		
		// find testtable entries
		log.info("About to run dao.findByContextAndTableEntryName(\"testonly\", \"testtable\") ... ");
		List<LookupTableEntry> tableEntries = dao.findByContextAndTableName("testonly", "testtable");

		// verify results
		Assert.assertTrue(tableEntries != null);

		// clean up the database
		dao.delete(tableEntry1);
		dao.delete(tableEntry2);
	}

	/**
	 * <p>Tests the findByContextTableEntry(String context, String tableName, String entryId) method.</p>
	 */
	@Test
	public void testFindByContextTableEntry() {
		// get some test records
		LookupTableEntry tableEntry1 = createTestRecord1();
		LookupTableEntry tableEntry2 = createTestRecord2();
		// put the data in the database
		dao.save(tableEntry1);
		dao.save(tableEntry2);

		// find entry1
		log.info("About to run dao.findByContextTableEntry(\"testonly\", \"testtable\", \"entry1\") ... ");
		LookupTableEntry tableEntry = dao.findByContextTableEntry("testonly", "testtable", "entry1");

		// verify results
		Assert.assertTrue(tableEntry != null);

		// clean up the database
		dao.delete(tableEntry1);
		dao.delete(tableEntry2);
	}

	/**
	 * <p>Tests the findById(SerializableEntry id) method.</p>
	 */
	@Test
	public void testFindById() {
		// get some test records
		LookupTableEntry tableEntry1 = createTestRecord1();
		LookupTableEntry tableEntry2 = createTestRecord2();
		// put the data in the database
		dao.save(tableEntry1);
		dao.save(tableEntry2);
		
		// find testtableEntry
		log.info("About to run dao.findById(" + tableEntry1.getId() + ") ... ");
		LookupTableEntry tableEntry = dao.findById(tableEntry1.getId());

		// verify results
		Assert.assertTrue(tableEntry != null);

		// find testtableEntry2
		log.info("About to run dao.findById(" + tableEntry2.getId() + ") ... ");
		tableEntry = dao.findById(tableEntry2.getId());

		// verify results
		Assert.assertTrue(tableEntry != null);

		// clean up the database
		dao.delete(tableEntry1);
		dao.delete(tableEntry2);
	}

	/**
	 * <p>Tests the save(LookupTableEntry LookupTableEntry) method.</p>
	 */
	@Test
	public void testSave() {
		// get some test records
		LookupTableEntry tableEntry1 = createTestRecord1();
		LookupTableEntry tableEntry2 = createTestRecord2();

		// save tableEntry 1
		log.info("About to run dao.save(tableEntry1) ... ");
		dao.save(tableEntry1);

		// verify results
		LookupTableEntry tableEntry = dao.findById(tableEntry1.getId());
		Assert.assertTrue(tableEntry != null);

		// save tableEntry 2
		log.info("About to run dao.save(tableEntry2) ... ");
		dao.save(tableEntry2);

		// verify results
		tableEntry = dao.findById(tableEntry2.getId());
		Assert.assertTrue(tableEntry != null);

		// clean up the database
		dao.delete(tableEntry1);
		dao.delete(tableEntry2);
	}

	/**
	 * <p>Tests the delete(LookupTableEntry LookupTableEntry) method.</p>
	 */
	@Test
	public void testDelete() {
		// get some test records
		LookupTableEntry tableEntry1 = createTestRecord1();
		LookupTableEntry tableEntry2 = createTestRecord2();
		// put the data in the database
		dao.save(tableEntry1);
		dao.save(tableEntry2);
		
		// run the dao.delete(tableEntry1) method
		int id1 = tableEntry1.getId();
		log.info("About to run dao.delete(tableEntry1) ... ");
		dao.delete(tableEntry1);
		
		// verify results
		try {
			Assert.assertTrue(dao.findById(id1) == null);
		} catch (ObjectRetrievalFailureException e) {
			log.info("Expected exception occurred after delete: " + e);
			Assert.assertTrue(1 == 1);
		}
		
		// run the dao.delete(tableEntry2) method
		int id2 = tableEntry2.getId();
		log.info("About to run dao.delete(tableEntry2) ... ");
		dao.delete(tableEntry2);
		
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
	public LookupTableEntry createTestRecord1() {
		LookupTableEntry lookupTableEntry = null;

		Date rightNow = new Date();
		String userId = "LookupTableEntryDaoTest";
		lookupTableEntry = new LookupTableEntry();
		lookupTableEntry.setContext("testonly");
		lookupTableEntry.setTableName("testtable");
		lookupTableEntry.setEntryId("entry1");
		lookupTableEntry.setDescription("This is a test table entry.");
		lookupTableEntry.setCreationDate(rightNow);
		lookupTableEntry.setCreatedBy(userId);
		lookupTableEntry.setLastUpdate(rightNow);
		lookupTableEntry.setLastUpdateBy(userId);

		return lookupTableEntry;
	}

	/**
	 * <p>Builds and returns test record #2.</p>
	 */
	public LookupTableEntry createTestRecord2() {
		LookupTableEntry lookupTableEntry = null;

		Date rightNow = new Date();
		String userId = "LookupTableEntryDaoTest";
		lookupTableEntry = new LookupTableEntry();
		lookupTableEntry.setContext("testonly");
		lookupTableEntry.setTableName("testtable");
		lookupTableEntry.setEntryId("entry2");
		lookupTableEntry.setDescription("This is another test table entry.");
		lookupTableEntry.setProperty00("property00");
		lookupTableEntry.setProperty01("property01");
		lookupTableEntry.setProperty02("property02");
		lookupTableEntry.setCreationDate(rightNow);
		lookupTableEntry.setCreatedBy(userId);
		lookupTableEntry.setLastUpdate(rightNow);
		lookupTableEntry.setLastUpdateBy(userId);

		return lookupTableEntry;
	}
}
