package edu.ucdavis.ucdh.stu.core.dao;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;

import edu.ucdavis.ucdhs.isweb.core.beans.NoticeAudit;
import edu.ucdavis.ucdhs.isweb.core.test.dao.BaseDaoTestCase;

public class NoticeAuditDaoTest extends BaseDaoTestCase {

	@Autowired
	protected NoticeAuditDao dao;

	/**
	 * <p>Tests the findAll() method.</p>
	 */
	@Test
	public void testFindAll() {
		// get some test records
		NoticeAudit noticeAudit1 = createTestRecord1();
		NoticeAudit noticeAudit2 = createTestRecord2();
		// put the data in the database
		dao.save(noticeAudit1);
		dao.save(noticeAudit2);
		
		// run the findAll() method
		log.info("About to run dao.findAll() ... ");
		List<NoticeAudit> response = dao.findAll();
		
		// verify results
		Assert.assertTrue("Expected records not returned.", response.size() > 1);

		// clean up the database
		dao.delete(noticeAudit1);
		dao.delete(noticeAudit2);
	}

	/**
	 * <p>Tests the findByExample(NoticeAudit noticeAudit) method.</p>
	 */
	@Test
	public void testFindByExample() {
		// get some test records
		NoticeAudit noticeAudit1 = createTestRecord1();
		NoticeAudit noticeAudit2 = createTestRecord2();
		// put the data in the database
		dao.save(noticeAudit1);
		dao.save(noticeAudit2);
		
		// find noticeAudit1
		NoticeAudit noticeAudit = new NoticeAudit();
		noticeAudit.setTitleSubject("This is a test audit.");
		log.info("About to run dao.findByExample(noticeAudit) ... ");
		List<NoticeAudit> noticeAudits = dao.findByExample(noticeAudit);

		// verify results
		Assert.assertTrue(noticeAudits != null && noticeAudits.size() > 0);

		// find noticeAudit2
		noticeAudit = new NoticeAudit();
		noticeAudit.setTitleSubject("This is another test audit.");
		log.info("About to run dao.findByExample(noticeAudit) ... ");
		noticeAudits = dao.findByExample(noticeAudit);

		// verify results
		Assert.assertTrue(noticeAudits != null && noticeAudits.size() > 0);

		// clean up the database
		dao.delete(noticeAudit1);
		dao.delete(noticeAudit2);
	}

	/**
	 * <p>Tests the findByProperty(String propertyName, Object propertyValue) method.</p>
	 */
	@Test
	public void testFindByProperty() {
		// get some test records
		NoticeAudit noticeAudit1 = createTestRecord1();
		NoticeAudit noticeAudit2 = createTestRecord2();
		// put the data in the database
		dao.save(noticeAudit1);
		dao.save(noticeAudit2);

		// find noticeAudit1
		log.info("About to run dao.findByProperty(\"titleSubject\", \"This is a test audit.\") ... ");
		List<NoticeAudit> noticeAudits = dao.findByProperty("titleSubject", "This is a test audit.");

		// verify results
		Assert.assertTrue(noticeAudits != null && noticeAudits.size() > 0);

		// find noticeAudit2
		log.info("About to run dao.findByProperty(\"titleSubject\", \"This is another test audit.\") ... ");
		noticeAudits = dao.findByProperty("titleSubject", "This is another test audit.");

		// verify results
		Assert.assertTrue(noticeAudits != null && noticeAudits.size() > 0);

		// clean up the database
		dao.delete(noticeAudit1);
		dao.delete(noticeAudit2);
	}

	/**
	 * <p>Tests the findById(Serializable id) method.</p>
	 */
	@Test
	public void testFindById() {
		// get some test records
		NoticeAudit noticeAudit1 = createTestRecord1();
		NoticeAudit noticeAudit2 = createTestRecord2();
		// put the data in the database
		dao.save(noticeAudit1);
		dao.save(noticeAudit2);
		
		// find noticeAudit
		log.info("About to run dao.findById(" + noticeAudit1.getId() + ") ... ");
		NoticeAudit noticeAudit = dao.findById(noticeAudit1.getId());

		// verify results
		Assert.assertTrue(noticeAudit != null);

		// find noticeAudit2
		log.info("About to run dao.findById(" + noticeAudit2.getId() + ") ... ");
		noticeAudit = dao.findById(noticeAudit2.getId());

		// verify results
		Assert.assertTrue(noticeAudit != null);

		// clean up the database
		dao.delete(noticeAudit1);
		dao.delete(noticeAudit2);
	}

	/**
	 * <p>Tests the save(NoticeAudit NoticeAudit) method.</p>
	 */
	@Test
	public void testSave() {
		// get some test records
		NoticeAudit noticeAudit1 = createTestRecord1();
		NoticeAudit noticeAudit2 = createTestRecord2();

		// save noticeAudit 1
		log.info("About to run dao.save(noticeAudit1) ... ");
		dao.save(noticeAudit1);

		// verify results
		NoticeAudit noticeAudit = dao.findById(noticeAudit1.getId());
		Assert.assertTrue(noticeAudit != null);

		// save noticeAudit 2
		log.info("About to run dao.save(noticeAudit2) ... ");
		dao.save(noticeAudit2);

		// verify results
		noticeAudit = dao.findById(noticeAudit2.getId());
		Assert.assertTrue(noticeAudit != null);

		// clean up the database
		dao.delete(noticeAudit1);
		dao.delete(noticeAudit2);
	}

	/**
	 * <p>Tests the delete(NoticeAudit NoticeAudit) method.</p>
	 */
	@Test
	public void testDelete() {
		// get some test records
		NoticeAudit noticeAudit1 = createTestRecord1();
		NoticeAudit noticeAudit2 = createTestRecord2();
		// put the data in the database
		dao.save(noticeAudit1);
		dao.save(noticeAudit2);
		
		// run the dao.delete(noticeAudit1) method
		int id1 = noticeAudit1.getId();
		log.info("About to run dao.delete(noticeAudit1) ... ");
		dao.delete(noticeAudit1);
		
		// verify results
		try {
			Assert.assertTrue(dao.findById(id1) == null);
		} catch (ObjectRetrievalFailureException e) {
			log.info("Expected exception occurred after delete: " + e);
			Assert.assertTrue(1 == 1);
		}
		
		// run the dao.delete(noticeAudit2) method
		int id2 = noticeAudit2.getId();
		log.info("About to run dao.delete(noticeAudit2) ... ");
		dao.delete(noticeAudit2);
		
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
	public NoticeAudit createTestRecord1() {
		NoticeAudit noticeAudit = null;

		Date rightNow = new Date();
		String userId = "NoticeAuditDaoTest";
		noticeAudit = new NoticeAudit();
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

		return noticeAudit;
	}

	/**
	 * <p>Builds and returns test record #2.</p>
	 */
	public NoticeAudit createTestRecord2() {
		NoticeAudit noticeAudit = null;

		Date rightNow = new Date();
		String userId = "NoticeAuditDaoTest";
		noticeAudit = new NoticeAudit();
		noticeAudit.setRemoteAddr("127.0.0.1");
		noticeAudit.setRemoteHost("localhost");
		noticeAudit.setRemoteUser("testuser");
		noticeAudit.setStackTrace(getStackTraceString());
		noticeAudit.setAddressing("mailFrom: testuser@localhost\nmailTo: testuser@localhost");
		noticeAudit.setContentType("text/hmtl");
		noticeAudit.setDeliveryMethod("email");
		noticeAudit.setTitleSubject("This is another test audit.");
		noticeAudit.setBody("<html><body><p>This is a test audit.</p><body></html>");
		noticeAudit.setCreationDate(rightNow);
		noticeAudit.setCreatedBy(userId);
		noticeAudit.setLastUpdate(rightNow);
		noticeAudit.setLastUpdateBy(userId);

		return noticeAudit;
	}

	/**
	 * <p>Builds and returns a stack trace string.</p>
	 */
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
