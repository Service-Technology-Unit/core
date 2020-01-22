package edu.ucdavis.ucdh.stu.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.dumbster.smtp.SimpleSmtpServer;
import com.dumbster.smtp.SmtpMessage;

import edu.ucdavis.ucdhs.isweb.core.beans.NoticeTemplate;
import edu.ucdavis.ucdhs.isweb.core.manager.NoticeTemplateManager;
import edu.ucdavis.ucdhs.isweb.core.service.Notifier;

@ContextConfiguration(locations = {"/context/applicationContext*.xml"})
public class NotifierImplTest extends AbstractJUnit4SpringContextTests {
	protected final Log log = logger;
	private NoticeTemplate noticeTemplate;
	@Autowired
	private NoticeTemplateManager noticeTemplateManager;
	@Autowired
	private Notifier notifier;

	/**
	 * Tests the notifier with one address
	 */ 
	@Test
	@SuppressWarnings("unchecked")
	public void testSendNotification1() {
		// start the dummy smtp server
	    SimpleSmtpServer server = SimpleSmtpServer.start();

	    // create a template for this test
		insertTestTemplate();

		// run the test
		notifier.sendNotification("test@localhost", "test/test", new HashMap<Object,Object>());

		// remove the template
		deleteTestTemplate();

		// stop the dummy smtp server
		server.stop();

		// verify the results
		Assert.assertTrue(server.getReceivedEmailSize() == 1);
		Iterator<SmtpMessage> emailIter = (Iterator<SmtpMessage>) server.getReceivedEmail();
		SmtpMessage email = emailIter.next();
		Assert.assertTrue(email.getHeaderValue("Subject").equals("This is a test template"));
		Assert.assertTrue(email.getBody().equals("This is a test template"));	
	}

	/**
	 * Tests the notifier with multiple addresses
	 */ 
	@Test
	@SuppressWarnings("unchecked")
	public void testSendNotification2() {
		// start the dummy smtp server
	    SimpleSmtpServer server = SimpleSmtpServer.start();

	    // create a template for this test
		insertTestTemplate();

		// run the test
		List<Object> sendTo = new ArrayList<Object>();
		sendTo.add("test1@localhost");
		sendTo.add("test2@localhost");
		notifier.sendNotification(sendTo, "test/test", new HashMap<Object,Object>());

		// remove the template
		deleteTestTemplate();

		// stop the dummy smtp server
		server.stop();

		// verify the results
		Assert.assertTrue(server.getReceivedEmailSize() == 2);
		Iterator<SmtpMessage> emailIter = (Iterator<SmtpMessage>) server.getReceivedEmail();
		SmtpMessage email = emailIter.next();
		Assert.assertTrue(email.getHeaderValue("Subject").equals("This is a test template"));
		Assert.assertTrue(email.getBody().equals("This is a test template"));	
	}

	/**
	 * Tests the notifier with complex recipients
	 */ 
	@Test
	@SuppressWarnings("unchecked")
	public void testSendNotification3() {
		// start the dummy smtp server
	    SimpleSmtpServer server = SimpleSmtpServer.start();

	    // create a template for this test
		insertTestTemplate();

		// run the test
		List sendTo = new ArrayList();
		Map recipient1 = new HashMap();
		recipient1.put("emailAddress", "test1@localhost");
		recipient1.put("name", "recipient1");
		sendTo.add(recipient1);
		Map recipient2 = new HashMap();
		recipient2.put("emailAddress", "test2@localhost");
		recipient2.put("name", "recipient2");
		sendTo.add(recipient2);
		Map recipient3 = new HashMap();
		recipient3.put("emailAddress", "test3@localhost");
		recipient3.put("name", "recipient3");
		sendTo.add(recipient3);
		notifier.sendNotification(sendTo, "emailAddress", "test/test", new HashMap());

		// remove the template
		deleteTestTemplate();

		// stop the dummy smtp server
		server.stop();

		// verify the results
		Assert.assertTrue(server.getReceivedEmailSize() == 3);
		Iterator<SmtpMessage> emailIter = (Iterator<SmtpMessage>) server.getReceivedEmail();
		SmtpMessage email = emailIter.next();
		Assert.assertTrue(email.getHeaderValue("Subject").equals("This is a test template"));
		Assert.assertTrue(email.getBody().equals("This is a test template"));	
	}

	/**
	 * Tests the notifier with complex recipients and from address
	 */ 
	@Test
	@SuppressWarnings("unchecked")
	public void testSendNotification4() {
		// start the dummy smtp server
	    SimpleSmtpServer server = SimpleSmtpServer.start();

	    // create a template for this test
		insertTestTemplate();

		// run the test
		List sendTo = new ArrayList();
		Map recipient1 = new HashMap();
		recipient1.put("emailAddress", "test1@localhost");
		recipient1.put("name", "recipient1");
		sendTo.add(recipient1);
		Map recipient2 = new HashMap();
		recipient2.put("emailAddress", "test2@localhost");
		recipient2.put("name", "recipient2");
		sendTo.add(recipient2);
		Map recipient3 = new HashMap();
		recipient3.put("emailAddress", "test3@localhost");
		recipient3.put("name", "recipient3");
		sendTo.add(recipient3);
		notifier.sendNotification("no-reply@localhost", sendTo, "emailAddress", "test/test", new HashMap());

		// remove the template
		deleteTestTemplate();

		// stop the dummy smtp server
		server.stop();

		// verify the results
		Assert.assertTrue(server.getReceivedEmailSize() == 3);
		Iterator<SmtpMessage> emailIter = (Iterator<SmtpMessage>) server.getReceivedEmail();
		SmtpMessage email = emailIter.next();
		Assert.assertTrue(email.getHeaderValue("Subject").equals("This is a test template"));
		Assert.assertTrue(email.getBody().equals("This is a test template"));	
	}

	/**
	 * Tests the notifier with variable data in the template
	 */ 
	@Test
	@SuppressWarnings("unchecked")
	public void testSendNotification5() {
		// start the dummy smtp server
	    SimpleSmtpServer server = SimpleSmtpServer.start();

		// run the test
		List sendTo = new ArrayList();
		Map recipient1 = new HashMap();
		recipient1.put("emailAddress", "test1@localhost");
		recipient1.put("name", "recipient1");
		sendTo.add(recipient1);
		notifier.sendNotification("no-reply@localhost", sendTo, "emailAddress", buildNoticeTemplate(), new HashMap());

		// stop the dummy smtp server
		server.stop();

		// verify the results
		Assert.assertTrue(server.getReceivedEmailSize() == 1);
		Iterator<SmtpMessage> emailIter = (Iterator<SmtpMessage>) server.getReceivedEmail();
		SmtpMessage email = emailIter.next();
		Assert.assertTrue(email.getHeaderValue("Subject").equals("Hello recipient1!"));
		Assert.assertTrue(email.getBody().equals("Your e-mail address is test1@localhost."));	
	}

	/**
	 * Inserts a test template to be used by the send notification test 
	 */ 
	private void insertTestTemplate() {
		Date rightNow = new Date();
		String userId = "NotifierImplTest";
		noticeTemplate = new NoticeTemplate();
		noticeTemplate.setContext("test");
		noticeTemplate.setName("test");
		noticeTemplate.setDescription("This is a test template");
		noticeTemplate.setContentType("text/plain");
		noticeTemplate.setDefaultDeliveryMethod("email");
		noticeTemplate.setTitleSubject("This is a test template");
		noticeTemplate.setBody("This is a test template");
		noticeTemplate.setCreationDate(rightNow);
		noticeTemplate.setCreatedBy(userId);
		noticeTemplate.setLastUpdate(rightNow);
		noticeTemplate.setLastUpdateBy(userId);
		noticeTemplateManager.save(noticeTemplate);
	}

	/**
	 * Removes the test template used by the send notification test 
	 */ 
	private void deleteTestTemplate() {
		noticeTemplateManager.delete(noticeTemplate);
	}

	/**
	 * Builds and returns a test template to be used by the send notification test 
	 */ 
	private NoticeTemplate buildNoticeTemplate() {
		Date rightNow = new Date();
		String userId = "NotifierImplTest";
		noticeTemplate = new NoticeTemplate();
		noticeTemplate.setContext("test");
		noticeTemplate.setName("test");
		noticeTemplate.setDescription("This is a test template");
		noticeTemplate.setContentType("text/plain");
		noticeTemplate.setDefaultDeliveryMethod("email");
		noticeTemplate.setTitleSubject("Hello $!{recipient.name}!");
		noticeTemplate.setBody("Your e-mail address is $!{recipient.emailAddress}.");
		noticeTemplate.setCreationDate(rightNow);
		noticeTemplate.setCreatedBy(userId);
		noticeTemplate.setLastUpdate(rightNow);
		noticeTemplate.setLastUpdateBy(userId);

		return noticeTemplate;
	}

	/**
	 * @return the notifier
	 */
	public Notifier getNotifier() {
		return notifier;
	}

	/**
	 * @param notifier the notifier to set
	 */
	public void setNotifier(Notifier notifier) {
		this.notifier = notifier;
	}

	/**
	 * @return the noticeTemplateManager
	 */
	public NoticeTemplateManager getNoticeTemplateManager() {
		return noticeTemplateManager;
	}

	/**
	 * @param noticeTemplateManager the noticeTemplateManager to set
	 */
	public void setNoticeTemplateManager(NoticeTemplateManager noticeTemplateManager) {
		this.noticeTemplateManager = noticeTemplateManager;
	}
}