package edu.ucdavis.ucdh.stu.core.manager.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;

import edu.ucdavis.ucdh.stu.core.dao.LookupTableEntryDao;
import edu.ucdavis.ucdhs.isweb.core.beans.LookupTableEntry;

public class LookupTableEntryManagerImplTest extends MockObjectTestCase {
	private final Log log = LogFactory.getLog(LookupTableEntryManagerImplTest.class);
	private LookupTableEntryManagerImpl mgr = new LookupTableEntryManagerImpl();
	private Mock mockDao = null;

	protected void setUp() throws Exception {
		mockDao = new Mock(LookupTableEntryDao.class);
		mgr.setLookupTableEntryDao((LookupTableEntryDao) mockDao.proxy());
	}

	public void testAddAndRemoveLookupTableEntry() throws Exception {
		Date rightNow = new Date();
		String userId = "LookupTableEntryEntryManagerImplTest";
		LookupTableEntry lookupTableEntry = new LookupTableEntry();
		lookupTableEntry.setContext("testonly");
		lookupTableEntry.setTableName("test1");
		lookupTableEntry.setEntryId("entry1");
		lookupTableEntry.setDescription("This is a test table entry.");
		lookupTableEntry.setCreationDate(rightNow);
		lookupTableEntry.setCreatedBy(userId);
		lookupTableEntry.setLastUpdate(rightNow);
		lookupTableEntry.setLastUpdateBy(userId);

		// set expected behavior on dao
		mockDao.expects(once()).method("save").with(same(lookupTableEntry));

		mgr.save(lookupTableEntry);
		
		// get new id
		int id = lookupTableEntry.getId();

		// verify expectations
		mockDao.verify();

		assertEquals(lookupTableEntry.getEntryId(), "entry1");

		if (log.isDebugEnabled()) {
			log.debug("removing lookupTableEntry...");
		}

		mockDao.expects(once()).method("delete")
				.with(eq(lookupTableEntry));

		mgr.delete(lookupTableEntry);

		// verify expectations
		mockDao.verify();

		try {
			// set expectations
			Throwable ex =
					new ObjectRetrievalFailureException(LookupTableEntry.class, "testtable");
			mockDao.expects(once()).method("findById")
					.with(eq(id)).will(throwException(ex));

			lookupTableEntry = mgr.findById(id);

			// verify expectations
			mockDao.verify();
			fail("LookupTableEntry '" + id + "' found in database");
		} catch (DataAccessException dae) {
			log.debug("Expected exception: " + dae.getMessage());
			assertNotNull(dae);
		}
	}
}
