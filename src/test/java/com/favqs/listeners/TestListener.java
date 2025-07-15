package com.favqs.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.favqs.util.ExtentReportingUtil;

public class TestListener implements ITestListener {
	
	
	private ExtentTest extenttest;
	private ExtentReports extentreport;
	private static final Logger logger = LogManager.getLogger(TestListener.class);
	
	public void onStart(ITestContext context) {
		extentreport=ExtentReportingUtil.reportSetup();
	  }


	public void onTestStart(ITestResult result) {
		logger.info("Started!!!!" + result.getMethod().getMethodName());
		logger.info("Description!!" + result.getMethod().getDescription());
		
		extenttest=extentreport.createTest(result.getMethod().getMethodName());
		extenttest=extenttest.log(Status.INFO, "Test Name : "+result.getMethod().getMethodName()+"is Started ");
		extenttest=extenttest.log(Status.INFO, "Test Name : "+result.getMethod().getDescription()+"is Started ");
	}

	public void onTestSuccess(ITestResult result) {
		logger.info("Passed!!!!" + result.getMethod().getMethodName());
		extenttest=extenttest.log(Status.PASS, result.getMethod().getMethodName()+" is Passed");

	}

	public void onTestFailure(ITestResult result) {
		logger.error("Failed!!!!" + result.getMethod().getMethodName());
		extenttest=extenttest.log(Status.FAIL, result.getMethod().getMethodName()+" is Failed with error :"+result.getThrowable());

	}

	public void onTestSkipped(ITestResult result) {
		logger.info("Skipped!!!!" + result.getMethod().getMethodName());
		extenttest=extenttest.log(Status.SKIP, result.getMethod().getMethodName()+ " is Skipped");

	}


	public void onFinish(ITestContext context) {
	    
		extentreport.flush();
		
	  }
}
