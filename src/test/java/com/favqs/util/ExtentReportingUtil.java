package com.favqs.util;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportingUtil {

	private static ExtentSparkReporter extentSparkReporter;
	private static ExtentReports extentReports;
	private static ThreadLocal<ExtentTest> extenttest = new ThreadLocal<>();

	public static ExtentReports reportSetup() {

		extentSparkReporter = new ExtentSparkReporter(
				System.getProperty("user.dir") + "/test-output/extentReport.html");
		extentSparkReporter.config().setDocumentTitle("Trello API Test");
		extentSparkReporter.config().setReportName("Trello");
		extentSparkReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
		extentSparkReporter.config().setTheme(Theme.STANDARD);

		extentReports = new ExtentReports();
		extentReports.attachReporter(extentSparkReporter);

		return extentReports;

	}

	public static ExtentTest getTest() {

		return extenttest.get();
	}

	public static void setTest(ExtentTest testname) {

		extenttest.set(testname);
	}

	public static void removeTest() {

		extenttest.remove();
	}

}
