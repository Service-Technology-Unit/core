package edu.ucdavis.ucdh.stu.core.dao;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;

import edu.ucdavis.ucdhs.isweb.core.beans.NoticeTemplate;
import edu.ucdavis.ucdhs.isweb.core.test.dao.BaseDaoTestCase;

public class NoticeTemplateDaoTest extends BaseDaoTestCase {

	@Autowired
	protected NoticeTemplateDao dao;

	/**
	 * <p>Tests the findAll() method.</p>
	 */
	@Test
	public void testFindAll() {
		// get some test records
		NoticeTemplate noticeTemplate1 = createTestRecord1();
		NoticeTemplate noticeTemplate2 = createTestRecord2();
		// put the data in the database
		dao.save(noticeTemplate1);
		dao.save(noticeTemplate2);
		
		// run the findAll() method
		log.info("About to run dao.findAll() ... ");
		List<NoticeTemplate> response = dao.findAll();
		
		// verify results
		Assert.assertTrue("Expected records not returned.", response.size() > 1);

		// clean up the database
		dao.delete(noticeTemplate1);
		dao.delete(noticeTemplate2);
	}

	/**
	 * <p>Tests the findByExample(NoticeTemplate noticeTemplate) method.</p>
	 */
	@Test
	public void testFindByExample() {
		// get some test records
		NoticeTemplate noticeTemplate1 = createTestRecord1();
		NoticeTemplate noticeTemplate2 = createTestRecord2();
		// put the data in the database
		dao.save(noticeTemplate1);
		dao.save(noticeTemplate2);
		
		// find noticeTemplate1
		NoticeTemplate noticeTemplate = new NoticeTemplate();
		noticeTemplate.setContext("testtemplates");
		noticeTemplate.setName("testtemplate1");
		log.info("About to run dao.findByExample(noticeTemplate) ... ");
		List<NoticeTemplate> noticeTemplates = dao.findByExample(noticeTemplate);

		// verify results
		Assert.assertTrue(noticeTemplates != null && noticeTemplates.size() > 0);

		// find noticeTemplate2
		noticeTemplate = new NoticeTemplate();
		noticeTemplate.setContext("testtemplates");
		noticeTemplate.setName("testtemplate2");
		log.info("About to run dao.findByExample(noticeTemplate) ... ");
		noticeTemplates = dao.findByExample(noticeTemplate);

		// verify results
		Assert.assertTrue(noticeTemplates != null && noticeTemplates.size() > 0);

		// clean up the database
		dao.delete(noticeTemplate1);
		dao.delete(noticeTemplate2);
	}

	/**
	 * <p>Tests the findByProperty(String propertyName, Object propertyValue) method.</p>
	 */
	@Test
	public void testFindByProperty() {
		// get some test records
		NoticeTemplate noticeTemplate1 = createTestRecord1();
		NoticeTemplate noticeTemplate2 = createTestRecord2();
		// put the data in the database
		dao.save(noticeTemplate1);
		dao.save(noticeTemplate2);

		// find noticeTemplate1
		log.info("About to run dao.findByProperty(\"name\", \"testtemplate1\") ... ");
		List<NoticeTemplate> noticeTemplates = dao.findByProperty("name", "testtemplate1");

		// verify results
		Assert.assertTrue(noticeTemplates != null && noticeTemplates.size() > 0);

		// find noticeTemplate2
		log.info("About to run dao.findByProperty(\"name\", \"testtemplate2\") ... ");
		noticeTemplates = dao.findByProperty("name", "testtemplate2");

		// verify results
		Assert.assertTrue(noticeTemplates != null && noticeTemplates.size() > 0);

		// clean up the database
		dao.delete(noticeTemplate1);
		dao.delete(noticeTemplate2);
	}

	/**
	 * <p>Tests the testFindByContextAndName(String context, String name) method.</p>
	 */
	@Test
	public void testFindByContextAndName() {
		// get some test records
		NoticeTemplate noticeTemplate1 = createTestRecord1();
		NoticeTemplate noticeTemplate2 = createTestRecord2();
		// put the data in the database
		dao.save(noticeTemplate1);
		dao.save(noticeTemplate2);

		// find noticeTemplate1
		log.info("About to run dao.findByContextAndName(\"testtemplates\", \"testtemplate1\") ... ");
		NoticeTemplate noticeTemplate = dao.findByContextAndName("testtemplates", "testtemplate1");

		// verify results
		Assert.assertTrue(noticeTemplate != null);

		// find noticeTemplate2
		log.info("About to run dao.findByContextAndName(\"testtemplates\", \"testtemplate2\") ... ");
		noticeTemplate = dao.findByContextAndName("testtemplates", "testtemplate2");

		// verify results
		Assert.assertTrue(noticeTemplate != null);

		// clean up the database
		dao.delete(noticeTemplate1);
		dao.delete(noticeTemplate2);
	}

	/**
	 * <p>Tests the findById(Serializable id) method.</p>
	 */
	@Test
	public void testFindById() {
		// get some test records
		NoticeTemplate noticeTemplate1 = createTestRecord1();
		NoticeTemplate noticeTemplate2 = createTestRecord2();
		// put the data in the database
		dao.save(noticeTemplate1);
		dao.save(noticeTemplate2);
		
		// find noticeTemplate
		log.info("About to run dao.findById(" + noticeTemplate1.getId() + ") ... ");
		NoticeTemplate noticeTemplate = dao.findById(noticeTemplate1.getId());

		// verify results
		Assert.assertTrue(noticeTemplate != null);

		// find noticeTemplate2
		log.info("About to run dao.findById(" + noticeTemplate2.getId() + ") ... ");
		noticeTemplate = dao.findById(noticeTemplate2.getId());

		// verify results
		Assert.assertTrue(noticeTemplate != null);

		// clean up the database
		dao.delete(noticeTemplate1);
		dao.delete(noticeTemplate2);
	}

	/**
	 * <p>Tests the save(NoticeTemplate NoticeTemplate) method.</p>
	 */
	@Test
	public void testSave() {
		// get some test records
		NoticeTemplate noticeTemplate1 = createTestRecord1();
		NoticeTemplate noticeTemplate2 = createTestRecord2();

		// save noticeTemplate 1
		log.info("About to run dao.save(noticeTemplate1) ... ");
		dao.save(noticeTemplate1);

		// verify results
		NoticeTemplate noticeTemplate = dao.findById(noticeTemplate1.getId());
		Assert.assertTrue(noticeTemplate != null);

		// save noticeTemplate 2
		log.info("About to run dao.save(noticeTemplate2) ... ");
		dao.save(noticeTemplate2);

		// verify results
		noticeTemplate = dao.findById(noticeTemplate2.getId());
		Assert.assertTrue(noticeTemplate != null);

		// clean up the database
		dao.delete(noticeTemplate1);
		dao.delete(noticeTemplate2);
	}

	/**
	 * <p>Tests the delete(NoticeTemplate NoticeTemplate) method.</p>
	 */
	@Test
	public void testDelete() {
		// get some test records
		NoticeTemplate noticeTemplate1 = createTestRecord1();
		NoticeTemplate noticeTemplate2 = createTestRecord2();
		// put the data in the database
		dao.save(noticeTemplate1);
		dao.save(noticeTemplate2);
		
		// run the dao.delete(noticeTemplate1) method
		int id1 = noticeTemplate1.getId();
		log.info("About to run dao.delete(noticeTemplate1) ... ");
		dao.delete(noticeTemplate1);
		
		// verify results
		try {
			Assert.assertTrue(dao.findById(id1) == null);
		} catch (ObjectRetrievalFailureException e) {
			log.info("Expected exception occurred after delete: " + e);
			Assert.assertTrue(1 == 1);
		}
		
		// run the dao.delete(noticeTemplate2) method
		int id2 = noticeTemplate2.getId();
		log.info("About to run dao.delete(noticeTemplate2) ... ");
		dao.delete(noticeTemplate2);
		
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
	public NoticeTemplate createTestRecord1() {
		NoticeTemplate noticeTemplate = null;

		Date rightNow = new Date();
		String userId = "NoticeTemplateDaoTest";
		noticeTemplate = new NoticeTemplate();
		noticeTemplate = new NoticeTemplate();
		noticeTemplate.setName("testtemplate1");
		noticeTemplate.setContext("testtemplates");
		noticeTemplate.setDescription("This is a test template.");
		noticeTemplate.setContentType("text/plain");
		noticeTemplate.setDefaultDeliveryMethod("email");
		noticeTemplate.setBody("This is a test template.");
		noticeTemplate.setCreationDate(rightNow);
		noticeTemplate.setCreatedBy(userId);
		noticeTemplate.setLastUpdate(rightNow);
		noticeTemplate.setLastUpdateBy(userId);

		return noticeTemplate;
	}

	/**
	 * <p>Builds and returns test record #2.</p>
	 */
	public NoticeTemplate createTestRecord2() {
		NoticeTemplate noticeTemplate = null;

		Date rightNow = new Date();
		String userId = "NoticeTemplateDaoTest";
		noticeTemplate = new NoticeTemplate();
		noticeTemplate.setName("testtemplate2");
		noticeTemplate.setContext("testtemplates");
		noticeTemplate.setDescription("This is another test template.");
		noticeTemplate.setContentType("text/hmtl");
		noticeTemplate.setDefaultDeliveryMethod("email");
		noticeTemplate.setBody("<html><body><p>This is a test template.</p><body></html>");
		noticeTemplate.setCreationDate(rightNow);
		noticeTemplate.setCreatedBy(userId);
		noticeTemplate.setLastUpdate(rightNow);
		noticeTemplate.setLastUpdateBy(userId);

		return noticeTemplate;
	}
}

