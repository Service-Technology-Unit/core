package edu.ucdavis.ucdh.stu.core.manager.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;

import edu.ucdavis.ucdh.stu.core.dao.NoticeAuditDao;
import edu.ucdavis.ucdhs.isweb.core.beans.NoticeAudit;

public class NoticeAuditManagerImplTest extends MockObjectTestCase {
	private final Log log = LogFactory.getLog(NoticeAuditManagerImplTest.class);
	private NoticeAuditManagerImpl mgr = new NoticeAuditManagerImpl();
	private Mock mockDao = null;

	protected void setUp() throws Exception {
		mockDao = new Mock(NoticeAuditDao.class);
		mgr.setNoticeAuditDao((NoticeAuditDao) mockDao.proxy());
	}

	public void testAddAndRemoveNoticeAudit() throws Exception {
		Date rightNow = new Date();
		String userId = "NoticeAuditEntryManagerImplTest";
		NoticeAudit noticeAudit = new NoticeAudit();
		noticeAudit.setRemoteAddr("127.0.0.1");
		noticeAudit.setRemoteHost("localhost");
		noticeAudit.setRemoteUser("testuser");
		noticeAudit.setStackTrace(getStackTraceString());
		noticeAudit.setAddressing("mailFrom: testuser@localhost\nmailTo: testuser@localhost");
		noticeAudit.setContentType("text/plain");
		noticeAudit.setDeliveryMethod("email");
		noticeAudit.setTitleSubject("This is a test audit.");
		noticeAudit.setBody("This is a test audit.");
		noticeAudit.setCreationDate(rightNow);
		noticeAudit.setCreatedBy(userId);
		noticeAudit.setLastUpdate(rightNow);
		noticeAudit.setLastUpdateBy(userId);

		// set expected behavior on dao
		mockDao.expects(once()).method("save").with(same(noticeAudit));

		mgr.save(noticeAudit);

		// verify expectations
		mockDao.verify();

		assertEquals(noticeAudit.getTitleSubject(), "This is a test audit.");

		if (log.isDebugEnabled()) {
			log.debug("removing noticeAudit ...");
		}

		int id = noticeAudit.getId();
		mockDao.expects(once()).method("delete").with(eq(noticeAudit));

		mgr.delete(noticeAudit);

		// verify expectations
		mockDao.verify();

		try {
			// set expectations
			Throwable ex = new ObjectRetrievalFailureException(NoticeAudit.class, new Integer(id));
			mockDao.expects(once()).method("findById").with(eq(new Integer(id))).will(throwException(ex));

			noticeAudit = mgr.findById(id);

			// verify expectations
			mockDao.verify();
			fail("NoticeAudit 'testtemplate1' found in database");
		} catch (DataAccessException dae) {
			log.debug("Expected exception: " + dae.getMessage());
			assertNotNull(dae);
		}
	}

	private String getStackTraceString() {
		StringBuffer buffer = new StringBuffer();

		String separator = "";
		StackTraceElement[] element = Thread.currentThread().getStackTrace();
		for (int i=0; i<element.length; i++) {
			buffer.append(separator);
			buffer.append(element[i].toString());
			separator = "\n";
		}

		return buffer.toString();
	}
}
