package edu.ucdavis.ucdh.stu.core.manager.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;

import edu.ucdavis.ucdh.stu.core.dao.NoticeTemplateDao;
import edu.ucdavis.ucdhs.isweb.core.beans.NoticeTemplate;

public class NoticeTemplateManagerImplTest extends MockObjectTestCase {
	private final Log log = LogFactory.getLog(NoticeTemplateManagerImplTest.class);
	private NoticeTemplateManagerImpl mgr = new NoticeTemplateManagerImpl();
	private Mock mockDao = null;

	protected void setUp() throws Exception {
		mockDao = new Mock(NoticeTemplateDao.class);
		mgr.setNoticeTemplateDao((NoticeTemplateDao) mockDao.proxy());
	}

	public void testAddAndRemoveNoticeTemplate() throws Exception {
		Date rightNow = new Date();
		String userId = "NoticeTemplateEntryManagerImplTest";
		NoticeTemplate noticeTemplate = new NoticeTemplate();
		noticeTemplate.setContext("testtemplates");
		noticeTemplate.setName("testtemplate1");
		noticeTemplate.setDescription("This is a test template.");
		noticeTemplate.setContentType("text/plain");
		noticeTemplate.setDefaultDeliveryMethod("email");
		noticeTemplate.setTitleSubject("This is a test template.");
		noticeTemplate.setBody("This is a test template.");
		noticeTemplate.setCreationDate(rightNow);
		noticeTemplate.setCreatedBy(userId);
		noticeTemplate.setLastUpdate(rightNow);
		noticeTemplate.setLastUpdateBy(userId);

		// set expected behavior on dao
		mockDao.expects(once()).method("save").with(same(noticeTemplate));

		mgr.save(noticeTemplate);

		// verify expectations
		mockDao.verify();

		assertEquals(noticeTemplate.getName(), "testtemplate1");

		if (log.isDebugEnabled()) {
			log.debug("removing noticeTemplate ...");
		}

		int id = noticeTemplate.getId();
		mockDao.expects(once()).method("delete").with(eq(noticeTemplate));

		mgr.delete(noticeTemplate);

		// verify expectations
		mockDao.verify();

		try {
			// set expectations
			Throwable ex = new ObjectRetrievalFailureException(NoticeTemplate.class, new Integer(id));
			mockDao.expects(once()).method("findById").with(eq(new Integer(id))).will(throwException(ex));

			noticeTemplate = mgr.findById(id);

			// verify expectations
			mockDao.verify();
			fail("NoticeTemplate 'testtemplate1' found in database");
		} catch (DataAccessException dae) {
			log.debug("Expected exception: " + dae.getMessage());
			assertNotNull(dae);
		}
	}
}
