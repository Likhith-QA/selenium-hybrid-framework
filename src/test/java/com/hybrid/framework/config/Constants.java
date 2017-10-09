package com.hybrid.framework.config;

/**
 * Contains all callable constant values.
 */
public class Constants {
    // Browsers
    public static final String FIREFOX = "firefox";
    public static final String CHROME = "chrome";
    public static final String IE = "ie";
    public static final String EDGE = "edge";
    public static final String PHANTOMJS = "phantomjs";
    public static final String HTMLUNIT = "htmlunit";

    // Elements
    public static final String LOCATOR_ID = "id";
    public static final String LOCATOR_NAME = "name";
    public static final String LOCATOR_CSS = "css";
    public static final String LOCATOR_XPATH = "xpath";

    // System variables
    public static final String KEYWORD_PASS = "PASS";
    public static final String KEYWORD_FAIL = "FAIL";

    // CSV File Paths
    public static final String SETTINGS_PATH = System.getProperty("user.dir") + "\\src\\test\\java\\com\\b2b\\automation\\dataEngine\\Settings.csv";
    public static final String TEST_CASES_PATH = System.getProperty("user.dir") + "\\src\\test\\java\\com\\b2b\\automation\\dataEngine\\Test Cases.csv";
    public static final String TEST_STEPS_PATH = System.getProperty("user.dir") + "\\src\\test\\java\\com\\b2b\\automation\\dataEngine\\Test Steps.csv";

    // Excel path
    public static final String EXCEL_PATH = System.getProperty("user.dir") + "\\src\\test\\java\\com\\b2b\\automation\\dataEngine\\DataEngine.xlsx";

    // Screenshot path
    public static final String SCREENSHOT_PATH = System.getProperty("user.dir") + "\\src\\test\\java\\com\\b2b\\automation\\reports\\screenshots\\";

    //Data Sheet Column Numbers
    public static final int COL_TESTCASEID = 0;
    public static final int COL_PAGEOBJECT = 4;
    public static final int COL_LOCATOR = 5;
    public static final int COL_DRIVERACTIONS = 6;
    public static final int COL_RUNMODE = 2;
    public static final int COL_RESULT = 3;
    public static final int COL_DATASET = 7;
    public static final int COL_TESTSTEP_RESULT = 8;
    public static final int COL_TESTSTEP_TIMEANDDATE = 9;
    public static final int COL_TESTCASE_TIMEANDDATE = 4;

    // Data Engine Excel sheets
    public static final String SHEET_TESTSTEPS = "Test Steps";
    public static final String SHEET_TESTCASES = "Test Cases";

    // Browser binaries
    public static final String CHROMEDRIVER = System.getProperty("user.dir") + "\\src\\test\\resources\\browser_binaries\\chromedriver.exe";
    public static final String FIREFOXDRIVER = System.getProperty("user.dir") + "\\src\\test\\resources\\browser_binaries\\geckodriver.exe";

    // Error Message
    public static final String ERR_MSG = "Check the 'Test Steps' sheet in Excel and/or 'extent-reporter.html'";
}