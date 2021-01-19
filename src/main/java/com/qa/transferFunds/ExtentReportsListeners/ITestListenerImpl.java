package com.qa.transferFunds.ExtentReportsListeners;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;

public class ITestListenerImpl extends ExtentReportListener implements ITestListener {

	private static ExtentReports extent;

	public void onTestStart(ITestResult result) {

	}

	public void onTestFinish(ITestResult result) {

	}

	public void onStart(ITestContext context) {
		System.out.println("test execution started");
		extent = setUp();
	}

	public void onFinish(ITestContext context) {
		System.out.println("execution completed");
		extent.flush();
		System.out.println("generated report");
		try {
			sendEmail("sravan.k@tvarana.com");
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}
	
	public void sendEmail(String receipient) throws EmailException {
		// Create the attachment
		EmailAttachment attachment = new EmailAttachment();
		attachment.setPath("./Extent_report/Transfer Funds Process Automation.html");
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		
		// Create the email message
		MultiPartEmail email = new MultiPartEmail();
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator("sravan.k@tvarana.com", "Sravan@310"));
		email.setSSLOnConnect(true);
		email.addTo(receipient);
		email.setFrom("sravan.k@tvarana.com");
		email.setSubject("Transfer Funds Process Automation Test Report");
		email.setMsg("Hey, Automation testing for Transfer Funds is completed. Please find the test report below");
		
		// add the attachment
		email.attach(attachment);
		
		// send the email
		email.send();
	}

}