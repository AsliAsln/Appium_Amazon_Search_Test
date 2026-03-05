package com.amazon.utils;

import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.ITestContext;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("═══════════════════════════════════════════");
        System.out.println("TEST STARTED: " + result.getMethod().getMethodName());
        System.out.println("  Description: " + result.getMethod().getDescription());
        System.out.println("═══════════════════════════════════════════");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println(" SUCESSFULL: " + result.getMethod().getMethodName());
        System.out.println("  Time: " + (result.getEndMillis() - result.getStartMillis()) + "ms");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println(" FAILED: " + result.getMethod().getMethodName());
        System.out.println("  Error: " + result.getThrowable().getMessage());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println(" SKIPED: " + result.getMethod().getMethodName());
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("\n---");
        System.out.println("  AMAZON SEARCH TEST SUITE STARTING        ║");
        System.out.println("  Test Group: " + context.getName());
        System.out.println("\n---");
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("\n---");
        System.out.println("║  TEST group completed ");
        System.out.println("║  success: " + context.getPassedTests().size());
        System.out.println("║  failed: " + context.getFailedTests().size());
        System.out.println("║  skipped: " + context.getSkippedTests().size());
        System.out.println("---\n");
    }
}