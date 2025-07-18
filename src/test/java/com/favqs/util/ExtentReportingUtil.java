package com.favqs.util;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportingUtil {

    private static final ThreadLocal<ExtentTest> extent_test = new ThreadLocal<>();

	public static ExtentReports reportSetup() {

        ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter(
                System.getProperty("user.dir") + "/test-output/extentReport.html");
		extentSparkReporter.config().setDocumentTitle("Trello API Test");
		extentSparkReporter.config().setReportName("Trello");
		extentSparkReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
		extentSparkReporter.config().setTheme(Theme.STANDARD);
		extentSparkReporter.config()
				.setCss("body { overflow: auto !important;} " + ".nav-wrapper {overflow: auto !important; }");

        ExtentReports extentReports = new ExtentReports();
		extentReports.attachReporter(extentSparkReporter);

		return extentReports;

	}

	public static ExtentTest getTest() {

		return extent_test.get();
	}

	public static void setTest(ExtentTest test_name) {

		extent_test.set(test_name);
	}

	public static void removeTest() {

		extent_test.remove();
	}

}
