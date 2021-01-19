package com.qa.transferFunds.pages;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;
import com.qa.transferFunds.util.TestBase;

public class HomePage extends TestBase{
	
	@FindBy(xpath = "//li[contains(@id,'ns-header-menu-userrole-item')]/a/span[@class='ns-role-menuitem-text']")
	List<WebElement> rolesList;
	
	@FindBy(xpath = "//div[@class='ns-role']/span[2]")
	WebElement role;

	@FindBy(xpath = "//span[text()='Transactions']")
	WebElement transactionsLink;
	
	@FindBy(xpath = "//span[text()='Bank']")
	WebElement bankLink;
	
	@FindBy(xpath = "//span[text()='Transfer Funds']")
	WebElement transferFundsLink;
	
	@FindBy(xpath = "//input[@name='inpt_fromaccount']")
	WebElement fromAccountDropdown;
	
	@FindBy(xpath = "//div[@class='dropdownDiv']//div")
	List<WebElement> dropdownList;
	
	@FindBy(xpath = "//input[@name='inpt_toaccount']")
	WebElement toAccountDropdown;
	
	@FindBy(xpath = "//input[@name='fromamount_formattedValue']")
	WebElement amountBox;
	
	@FindBy(xpath = "//input[@name='inpt_department']")
	WebElement departmentDropdown;
	
	@FindBy(xpath = "//input[@name='inpt_class']")
	WebElement classDropdown;
	
	@FindBy(xpath = "//input[@name='inpt_location']")
	WebElement locationDropdown;
	
	@FindBy(xpath = "//h1[text()='Transfer']")
	WebElement transferLabel;
	
	@FindBy(xpath = "//input[@id='btn_multibutton_submitter']")
	WebElement saveBtn;
	
	@FindBy(xpath = "//div[@class='descr']")
	WebElement confirmationMsg;
	
	@FindBy(xpath = "//span[@id='transactionnumber_fs_lbl_uir_label']//following-sibling::span")
	WebElement tranNoField;
	
	public HomePage() {
		PageFactory.initElements(driver, this);
		action = new Actions(driver);
	}
	
	public void createTransfer(String fromAccount, String toAccount, String amount, String department, String classData, String location, ExtentTest logInfo) throws InterruptedException {
		
		// Select From account
		boolean fromAccountFlag = false;
		eleClickable(driver, fromAccountDropdown, 10);
		fromAccountDropdown.click();
		for(int i=0;i<dropdownList.size();i++) {
			String formValue = dropdownList.get(i).getText().trim();
			if(formValue.equals(fromAccount)) {
				dropdownList.get(i).click();
				Thread.sleep(1500);
				fromAccountFlag = true;
				break;
			}
		}
		if(!fromAccountFlag) {
			System.out.println("Transfer unable to create. From Account: "+fromAccount+" not available in the list & unable to select");
			logInfo.fail("Transfer unable to create. From Account: "+fromAccount+" not available in the list & unable to select");
		}
		transferLabel.click();
		
		// Select To account
		boolean toAccountFlag = false;
		eleClickable(driver, toAccountDropdown, 10);
		toAccountDropdown.click();
		for(int i=0;i<dropdownList.size();i++) {
			String formValue = dropdownList.get(i).getText().trim();
			if(formValue.equals(toAccount)) {
				dropdownList.get(i).click();
				Thread.sleep(1500);
				toAccountFlag = true;
				break;
			}
		}
		if(!toAccountFlag) {
			System.out.println("Transfer unable to create. To Account: "+toAccount+" not available in the list & unable to select");
			logInfo.fail("Transfer unable to create. To Account: "+toAccount+" not available in the list & unable to select");
		}
		transferLabel.click();
		
		boolean amountFlag = true;
		amountBox.sendKeys(amount);
		transferLabel.click();
		Thread.sleep(1500);
		if(isAlertPresent()) {
			Thread.sleep(1000);
			Alert alert = driver.switchTo().alert();
			String alertMsg = alert.getText();
			if (alertMsg.contains("Invalid currency value")) {
				amountFlag = false;
				System.out.println("Transfer unable to create. Amount given: "+amount+" is invalid");
				logInfo.fail("Transfer unable to create. Amount given: "+amount+" is invalid");
			}
			alert.accept();
		}
		
		// Select Department
		boolean departmentFlag = false;
		eleClickable(driver, departmentDropdown, 10);
		departmentDropdown.click();
		for(int i=0;i<dropdownList.size();i++) {
			String formValue = dropdownList.get(i).getText().trim();
			if(formValue.equals(department)) {
				dropdownList.get(i).click();
				departmentFlag = true;
				break;
			}
		}
		if(!departmentFlag) {
			System.out.println("Transfer unable to create. Department: "+department+" not available in the list & unable to select");
			logInfo.fail("Transfer unable to create. Department: "+department+" not available in the list & unable to select");
		}
		transferLabel.click();		
		
		// Select Class
		boolean classFlag = false;
		eleClickable(driver, classDropdown, 10);
		classDropdown.click();
		for(int i=0;i<dropdownList.size();i++) {
			String formValue = dropdownList.get(i).getText().trim();
			if(formValue.equals(classData)) {
				dropdownList.get(i).click();
				classFlag = true;
				break;
			}
		}
		if(!classFlag) {
			System.out.println("Transfer unable to create. Class: "+classData+" not available in the list & unable to select");
			logInfo.fail("Transfer unable to create. Class: "+classData+" not available in the list & unable to select");
		}
		transferLabel.click();
		
		// Select Location
		boolean locationFlag = false;
		eleClickable(driver, locationDropdown, 10);
		locationDropdown.click();
		for(int i=0;i<dropdownList.size();i++) {
			String formValue = dropdownList.get(i).getText().trim();
			if(formValue.equals(location)) {
				dropdownList.get(i).click();
				locationFlag = true;
				break;
			}
		}
		if(!locationFlag) {
			System.out.println("Transfer unable to create. Location: "+location+" not available in the list & unable to select");
			logInfo.fail("Transfer unable to create. Location: "+location+" not available in the list & unable to select");
		}
		transferLabel.click();
		
		// Save Transfer
		if(fromAccountFlag && toAccountFlag && amountFlag && departmentFlag && classFlag && locationFlag) {
			action.moveToElement(saveBtn).click(saveBtn).build().perform();
			eleAvailability(driver, By.xpath("//div[@class='descr']"), 20);
			String confirmationMessage = confirmationMsg.getText().trim();
			String tranNo = tranNoField.getText().trim();
		    if(confirmationMessage.equals("Transaction successfully Saved")) {
		    	System.out.println("Transfer with Transaction Number: "+tranNo+" created successfully");
		    	logInfo.pass("Transfer with Transaction Number: "+tranNo+" created successfully");
		    }else {
		    	System.out.println("Transfer unable to create");
		    	logInfo.fail("Transfer unable to create");
		    }
		}
	}
	
	public void clickOnTransferFundsLink() throws InterruptedException {
		Thread.sleep(2000);
		eleAvailability(driver, transactionsLink, 10);
		action.moveToElement(transactionsLink).build().perform();
		eleAvailability(driver, bankLink, 10);
		action.moveToElement(bankLink).build().perform();
		eleAvailability(driver, transferFundsLink, 10);
		transferFundsLink.click();
		
		if(isAlertPresent()) {
			driver.switchTo().alert().accept();
		}
	}
	
	public void changeRole(String roleTextData, String roleTypeData) throws InterruptedException {
		String currentRole = role.getText().trim();
		System.out.println(currentRole);
		if(currentRole.equals(roleTextData)) {
			System.out.println("Role already selected");
		} else {
			Thread.sleep(1000);
			action.moveToElement(driver.findElement(By.xpath("//div[@id='spn_cRR_d1']/a"))).build().perform();
			
			for(int i=0;i<rolesList.size();i++) {
				WebElement roleElement = rolesList.get(i);
				String roleValue = roleElement.getText().trim();
				if(roleValue.equals(roleTextData)) {
					if(roleTypeData.equals("Production")) {
						JavascriptExecutor executor = (JavascriptExecutor)driver;
						WebElement liTagElement = (WebElement)executor.executeScript("return arguments[0].parentNode.parentNode;", roleElement);
						String id = liTagElement.getAttribute("id");
						try {
							WebElement roleType = driver.findElement(By.xpath("//li[@id='"+id+"']/a/span[@class='ns-role-accounttype']"));
							if(roleType.isDisplayed())
								continue;
						}
						catch(NoSuchElementException e) {
							driver.findElement(By.xpath("//li[@id='"+id+"']/a/span[@class='ns-role-menuitem-text']")).click();
							break;
						}
					}
				}
			}
		}
	}
}
