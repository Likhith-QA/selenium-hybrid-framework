package com.hybrid.framework.executionEngine;

import java.lang.reflect.Method;
import java.util.ResourceBundle;

import com.hybrid.framework.helpers.Time;
import com.hybrid.framework.utility.ExtentReport;
import org.apache.log4j.xml.DOMConfigurator;

import com.hybrid.framework.config.DriverActions;
import com.hybrid.framework.config.Constants;
import com.hybrid.framework.utility.ExcelUtils;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * Class in-charge of executing all the keywords
 * inputted in the OR (Object Repository) file.
 */

public class Executor {

    public static ResourceBundle OR;
    public static DriverActions driverActions;
    public static String strDriverAction;
    public static String strPageObject;
    public static String strLocator;
    public static Method methods[];

    public static int intTestStep;
    public static int intTestLastStep;
    public static String strTestCaseID;
    public static String strRunMode;
    public static String strData;
    public static boolean boolResult;

    public static ExtentReport extentReport;

    /**
     * Class construct that initiates all the class(es) and method(s) of required test case(s) to run.
     *
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    public Executor() throws NoSuchMethodException, SecurityException {
        driverActions = new DriverActions();
        methods = driverActions.getClass().getDeclaredMethods();
    }

    /**
     * initializeTest() :
     *  main method to start all the run.
     *  Annotated by @Test as TestNG method.
     *
     * @throws Exception
     */
    @Test
    @Parameters({"browser"})
    public static void initializeTest() throws Exception {
        ExcelUtils.setExcelFile(Constants.EXCEL_PATH);

        // Configure the logger at log4j.xml file.
        DOMConfigurator.configure("log4j.xml");

        // Prepare the .properties file
        OR = ResourceBundle.getBundle("OR");
        Executor startEngine = new Executor();
        startEngine.executeTestCase();
    }

    /**
     * executeTestCase() :
     *  in-charge of executing the test case(s) regardless of its number (amount).
     *
     * @throws Exception
     */

    private void executeTestCase() throws Exception {
        // Counts the test case(s) in the 'Test Cases' sheet in 'DataEngine.xlsm'
        int intTotalTestCases = ExcelUtils.getRowCount(Constants.SHEET_TESTCASES);
        int i = 1;

        // Execute each test case through a loop.
        for(int intTestcase=1; intTestcase < intTotalTestCases; intTestcase++) {

            // Extract the 'Test Case ID' and 'Runmode' values in the sheet 'Test Cases'
            strTestCaseID = ExcelUtils.getCellData(intTestcase, Constants.COL_TESTCASEID, Constants.SHEET_TESTCASES);
            strRunMode = ExcelUtils.getCellData(intTestcase, Constants.COL_RUNMODE, Constants.SHEET_TESTCASES);

            // Run the test case if its 'Runmode' is set to 'Yes'
            if (strRunMode.equalsIgnoreCase("YES")) {
                if (i > 1) {
                    extentReport = new ExtentReport(false);
                }
                else {
                    extentReport = new ExtentReport(true);
                }

                // Start ExtentReporting engine.
                extentReport.logger = extentReport.extent.startTest(strTestCaseID);

                // Get the starting row and last row of the specified test case.
                intTestStep = ExcelUtils.getRowContains(strTestCaseID, Constants.COL_TESTCASEID, Constants.SHEET_TESTSTEPS);
                intTestLastStep = ExcelUtils.getTestStepsCount(Constants.SHEET_TESTSTEPS, strTestCaseID, intTestStep);

                // Delete all the past results.
                ExcelUtils.deleteColumnContents(intTestStep, intTestLastStep, 8, Constants.SHEET_TESTSTEPS);
                ExcelUtils.deleteColumnContents(intTestStep, intTestLastStep, 9, Constants.SHEET_TESTSTEPS);

                // Step invocation is initiated as 'true'; will change to 'false' once error was found at runtime.
                boolResult = true;

                // Skip reading the description by adding the count of 'intTestStep'
                if (intTestStep < ExcelUtils.getRowCount(Constants.SHEET_TESTSTEPS)) intTestStep++;

                // Execute each step.
                for (; intTestStep < intTestLastStep; intTestStep++) {

                    // Extract the action keyword from the sheet 'Test Steps' column 'Action Keyword'
                    strDriverAction = ExcelUtils.getCellData(intTestStep, Constants.COL_DRIVERACTIONS,Constants.SHEET_TESTSTEPS);

                    // Extract the page object from the sheet 'Test Steps' column 'Page Object'
                    strPageObject = ExcelUtils.getCellData(intTestStep, Constants.COL_PAGEOBJECT, Constants.SHEET_TESTSTEPS);
                    strLocator = ExcelUtils.getCellData(intTestStep, Constants.COL_LOCATOR, Constants.SHEET_TESTSTEPS);
                    strData = ExcelUtils.getCellData(intTestStep, Constants.COL_DATASET, Constants.SHEET_TESTSTEPS);

                    // Invoke execution of each step.
                    executeTestStepActions();

                    if(!boolResult) {
                        ExcelUtils.setCellData(Constants.KEYWORD_FAIL, intTestcase, Constants.COL_RESULT,Constants.SHEET_TESTCASES);
                        ExcelUtils.setCellData(Time.getCurrentTimeAndDate(), intTestcase, Constants.COL_TESTCASE_TIMEANDDATE, Constants.SHEET_TESTCASES);
                        break;
                    }
                }

                if(boolResult) {
                    ExcelUtils.setCellData(Constants.KEYWORD_PASS,intTestcase,Constants.COL_RESULT,Constants.SHEET_TESTCASES);
                    ExcelUtils.setCellData(Time.getCurrentTimeAndDate(), intTestcase, Constants.COL_TESTCASE_TIMEANDDATE, Constants.SHEET_TESTCASES);
                }

                // Generate report.
                extentReport.generateReport();
                i++;
            }
        }

        // Close the stream.
        extentReport.closeExtentReport();
    }
    /**
     * executeTestStepActions() :
     *  in-charge of enumerating all the test steps
     *  indicated from the DataEngine.xlsm data source
     *
     * @throws Exception
     */
    private static void executeTestStepActions() throws Exception {
        for (Method method : methods) {

            if(method.getName().equals(strDriverAction)) {

                // Invokes the method indicated in the 'Test Steps' sheet of 'DataEngine.xlsm'
                method.invoke(driverActions, strPageObject, strLocator, strData);

                if(boolResult) {
                    ExcelUtils.setCellData(Constants.KEYWORD_PASS, intTestStep, Constants.COL_TESTSTEP_RESULT, Constants.SHEET_TESTSTEPS);
                    ExcelUtils.setCellData(Time.getCurrentTimeAndDate(), intTestStep, Constants.COL_TESTSTEP_TIMEANDDATE, Constants.SHEET_TESTSTEPS);
                    break;
                }
                else {
                    System.out.println(Constants.ERR_MSG);
                    ExcelUtils.setCellData(Constants.KEYWORD_FAIL, intTestStep, Constants.COL_TESTSTEP_RESULT, Constants.SHEET_TESTSTEPS);
                    ExcelUtils.setCellData(Time.getCurrentTimeAndDate(), intTestStep, Constants.COL_TESTSTEP_TIMEANDDATE, Constants.SHEET_TESTSTEPS);
                    DriverActions.closeBrowser(null, null, null);
                    break;
                }
            }
        }
    }
}
