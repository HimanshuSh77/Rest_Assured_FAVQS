package com.favqs.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.SkipException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.favqs.util.ExtentReportingUtil;
import com.favqs.util.LogUtil;

public class TestListener implements ITestListener {

	private ExtentReports extentreport;

	public void onStart(ITestContext context) {
		extentreport = ExtentReportingUtil.reportSetup();
	}

	public void onTestStart(ITestResult result) {
		if (ExtentReportingUtil.getTest() == null) {

			LogUtil.log("Started!!!!" + result.getMethod().getMethodName());
			LogUtil.log("Description!!" + result.getMethod().getDescription());

			ExtentTest extenttest = extentreport.createTest(result.getMethod().getMethodName(),
					result.getMethod().getDescription());
			ExtentReportingUtil.setTest(extenttest);
			ExtentReportingUtil.getTest().log(Status.INFO, result.getMethod().getMethodName() + " test is Started..");
		}
	}

	public void onTestSuccess(ITestResult result) {
				
			LogUtil.log("Passed!!!!" + result.getMethod().getMethodName());
			LogUtil.flush(result.getMethod().getMethodName());
			ExtentReportingUtil.getTest().log(Status.PASS, result.getMethod().getMethodName() + " is Passed.");
			ExtentReportingUtil.removeTest();

	}

	public void onTestFailure(ITestResult result) {
		 if (!shouldReport(result)) return;

		 LogUtil.log("Failed!!!!" + result.getMethod().getMethodName() + "with Error : " + result.getThrowable());
		 LogUtil.flush(result.getMethod().getMethodName());
		 ExtentReportingUtil.getTest().log(Status.FAIL,
					result.getMethod().getMethodName() + " is Failed with error :" + result.getThrowable());
		 ExtentReportingUtil.removeTest();

		
	}

	public void onTestSkipped(ITestResult result) {
		 if (!shouldReport(result)) return;
		
		 ITestContext context = result.getTestContext();
		 ITestNGMethod method = result.getMethod();

		 if (context.getPassedTests().getResults(method).size() > 0
					|| context.getFailedTests().getResults(method).size() > 0)
		 {
		        System.out.println("-------------------------------------------------Enter---------------");

				return;
		 }

			LogUtil.log("Skipped!!!!" + result.getMethod().getMethodName());
			LogUtil.flush(result.getMethod().getMethodName());
			ExtentReportingUtil.getTest().log(Status.SKIP, result.getMethod().getMethodName() + " is Skipped.");
			ExtentReportingUtil.removeTest();
	
	}

	public void onFinish(ITestContext context) {

		removeFailedOrSkippedRetryAttempts(context);
		extentreport.flush();

	}

	private void removeFailedOrSkippedRetryAttempts(ITestContext context) {

		for (ITestResult failed : context.getFailedTests().getAllResults()) {
			ITestNGMethod method = failed.getMethod();
			if (context.getPassedTests().getResults(method).size() > 0) {
				context.getFailedTests().removeResult(method);
			}
		}

		// Clean skipped retries
		for (ITestResult skipped : context.getSkippedTests().getAllResults()) {
			ITestNGMethod method = skipped.getMethod();
			if (context.getPassedTests().getResults(method).size() > 0
					|| context.getFailedTests().getResults(method).size() > 0) {
				context.getSkippedTests().removeResult(method);
				
			}
		}
	}

	  public static boolean shouldReport(ITestResult result) {
	        Throwable throwable = result.getThrowable();
	 
	        // 1. If test is genuinely skipped (e.g., user throws SkipException) — report it
	        if (throwable instanceof SkipException) {
	            return true;
	        }
	 
	        // 2. If no retry analyzer — always report
	        IRetryAnalyzer analyzer = result.getMethod().getRetryAnalyzer(result);
	        if (!(analyzer instanceof Retry)) {
	            return true;
	        }
	 
	        // 3. If retry is done (last attempt) — report it
	        Retry retryAnalyzer = (Retry) analyzer;
	        
	       int current =retryAnalyzer.getRetryCount();
	       int max =retryAnalyzer.getMaxRetryCount();
	       System.out.println("---------------"+current+"---------Enter---"+max+"------------"); 
	       
	       return  result.getMethod().getCurrentInvocationCount()==max+1 ;
	    
	}
}
