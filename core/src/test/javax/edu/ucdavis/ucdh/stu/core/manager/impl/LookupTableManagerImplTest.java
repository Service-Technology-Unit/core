package edu.ucdavis.ucdh.stu.core.manager.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;

import edu.ucdavis.ucdh.stu.core.dao.LookupTableDao;
import edu.ucdavis.ucdhs.isweb.core.beans.LookupTable;

public class LookupTableManagerImplTest extends MockObjectTestCase {
	private final Log log = LogFactory.getLog(LookupTableManagerImplTest.class);
	private LookupTableManagerImpl mgr = new LookupTableManagerImpl();
	private Mock mockDao = null;

	protected void setUp() throws Exception {
		mockDao = new Mock(LookupTableDao.class);
		mgr.setLookupTableDao((LookupTableDao) mockDao.proxy());
	}

	public void testAddAndRemoveLookupTable() throws Exception {
		Date rightNow = new Date();
		String userId = "LookupTableManagerImplTest";
		LookupTable lookupTable = new LookupTable();
		lookupTable.setContext("testonly");
		lookupTable.setTableName("testtable");
		lookupTable.setDisplayName("Test Table");
		lookupTable.setDescription("This is a test table.");
		lookupTable.setCreationDate(rightNow);
		lookupTable.setCreatedBy(userId);
		lookupTable.setLastUpdate(rightNow);
		lookupTable.setLastUpdateBy(userId);

		// set expected behavior on dao
		mockDao.expects(once()).method("save").with(same(lookupTable));

		mgr.save(lookupTable);

		// verify expectations
		mockDao.verify();

		assertEquals(lookupTable.getDisplayName(), "Test Table");

		if (log.isDebugEnabled()) {
			log.debug("removing lookupTable...");
		}

		mockDao.expects(once()).method("delete")
				.with(eq(lookupTable));

		mgr.delete(lookupTable);

		// verify expectations
		mockDao.verify();

		try {
			// set expectations
			Throwable ex =
					new ObjectRetrievalFailureException(LookupTable.class, "testtable");
			mockDao.expects(once()).method("findById")
					.with(eq("testtable")).will(throwException(ex));

			lookupTable = mgr.findById("testtable");

			// verify expectations
			mockDao.verify();
			fail("LookupTable 'testtable' found in database");
		} catch (DataAccessException dae) {
			log.debug("Expected exception: " + dae.getMessage());
			assertNotNull(dae);
		}
	}
}
