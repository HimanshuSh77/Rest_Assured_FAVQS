package com.favqs.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.favqs.util.LogUtil;

public class Retry implements IRetryAnalyzer {

	private static final Logger logger = LogManager.getLogger(Retry.class);
	private final int retry_limit = 3;
	private int retry_count = 0;

	@Override
	public boolean retry(ITestResult result) {
		// TODO Auto-generated method stub
		if (retry_count < retry_limit) {
			LogUtil.log("Retrying test - " + result.getMethod().getQualifiedName() + " with Retry Attempt as "
					+ (retry_count + 1));
			logger.info("Retrying test - " + result.getMethod().getQualifiedName() + " with Retry Attempt as "
					+ (retry_count + 1));
			retry_count++;
			return true;
		}

		return false;
	}

	public int getRetryCount() {
		return retry_count;
	}

	public int getMaxRetryCount() {
		return retry_limit;
	}

}
