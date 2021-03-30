package com.qa.transferFunds.stepDefinition;

import java.util.Map;

import com.aventstack.extentreports.ExtentTest;
import com.qa.transferFunds.pages.AuthenticationPage;
import com.qa.transferFunds.pages.HomePage;
import com.qa.transferFunds.pages.LoginPage;
import com.qa.transferFunds.util.ExcelDataToDataTable;
import com.qa.transferFunds.util.TestBase;

import cucumber.api.DataTable;
import cucumber.api.Transform;
import cucumber.api.java.After;
import cucumber.api.java.en.Then;

public class transferFundsStepDef extends TestBase {
	
	LoginPage loginPage;
	AuthenticationPage authPage;
	HomePage homePage;
	
	@After
	public void closeBrowser() {
		driver.quit();
	}
	
	@Then("^Create a Transfer using excel data at \"([^\"]*)\"$")
	public void create_a_Transfer_using_excel_data_at(@Transform(ExcelDataToDataTable.class) DataTable transferFundsData) throws InterruptedException {
		ExtentTest loginfo = null;
		try {
			test = extent.createTest("Verification of Transfer creation in NetSuite");
			loginfo = test.createNode("login");
			// User login to Netsuite with Tvarana Dev Test role
			TestBase.init();
			loginPage = new LoginPage();
			authPage = loginPage.login();
			homePage = authPage.Authentication();
			homePage.changeRole(prop.getProperty("roleText"), prop.getProperty("roleType"));
			loginfo.pass("Login Successful in Netsuite");
		} catch (Exception e) {
			testStepHandle("FAIL", driver, loginfo, e, "login");
		}
			
		ExtentTest loginfo2 = null;
		try {
			for(Map<String,String> data: transferFundsData.asMaps(String.class, String.class)) {
				String fromAccount = data.get("From Account");
				String toAccount = data.get("To Account");
				String amount = data.get("Amount");
				String department = data.get("Department");
				String classData = data.get("Class");
				String location = data.get("Location");
				loginfo2 = test.createNode("Verify creating a Transfer from Account: "+fromAccount+" to Account: "+toAccount+" with amount of '"+amount+"'");
				homePage.clickOnTransferFundsLink();
				homePage.createTransfer(fromAccount, toAccount, amount, department, classData, location, loginfo2);
			}
		} catch (Exception e) {
			testStepHandle("FAIL", driver, loginfo2, e, "Verification of Bill exception when the PO is billed before receiving the items");
		}
	}
}