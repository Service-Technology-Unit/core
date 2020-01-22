package edu.ucdavis.ucdh.stu.core.service.impl;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.dumbster.smtp.SimpleSmtpServer;
import com.dumbster.smtp.SmtpMessage;

import edu.ucdavis.ucdhs.isweb.core.beans.MessageAddress;

@ContextConfiguration(locations = {"/context/applicationContext*.xml"})
public class EmailCourierTest extends AbstractJUnit4SpringContextTests {
	@Autowired
	private EmailCourier emailCourier;

	/**
	 * Tests the EmailCourier 
	 */ 
	@Test
	@SuppressWarnings("unchecked")
	public void testDeliverMessage() {
		// start the dummy smtp server
	    SimpleSmtpServer server = SimpleSmtpServer.start();

		// set up the addresses for this test
		MessageAddress[] addresses = new MessageAddress[2];
		MessageAddress fromAddress = new MessageAddress();
		fromAddress.setAddressType("mailFrom");
		fromAddress.addAddressValue("fromAddress@localhost");
		addresses[0] = fromAddress;
		MessageAddress toAddress = new MessageAddress();
		toAddress.setAddressType("mailTo");
		toAddress.addAddressValue("toAddress@localhost");
		addresses[1] = toAddress;

		// run the test
		emailCourier.deliverMessage(addresses, "Test Message", "text/plain", "This is a test.");

		// stop the dummy smtp server
		server.stop();

		// verify the results
		Assert.assertTrue(server.getReceivedEmailSize() == 1);
		Iterator<SmtpMessage> emailIter = (Iterator<SmtpMessage>) server.getReceivedEmail();
		SmtpMessage email = emailIter.next();
		Assert.assertTrue(email.getHeaderValue("Subject").equals("Test Message"));
		Assert.assertTrue(email.getBody().equals("This is a test."));	
	}

	/**
	 * @return the emailCourier
	 */
	public EmailCourier getEmailCourier() {
		return emailCourier;
	}

	/**
	 * @param emailCourier the emailCourier to set
	 */
	public void setEmailCourier(EmailCourier emailCourier) {
		this.emailCourier = emailCourier;
	}
}
