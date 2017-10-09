package com.hybrid.framework.config;

import com.hybrid.framework.executionEngine.Executor;
import com.b2b.automation.helpers.*;
import com.hybrid.framework.helpers.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.io.IOException;

/**
 * Class containing all the keywords that can be
 * used along the UI interaction and inputted in Excel data engine.
 */
public class DriverActions {

    public static WebDriver driver;
    public static Wait wait;
    public static JSExecutor js;
    public static ElementActions elementActions;
    public static GlobalWebDriver globalWebDriver;

    /**
     * Browser actions.
     */

    public static void openBrowser(String object, String locator, String data) throws IOException {
        try {
            Executor.extentReport.logInfo("Opening Browser");

            if (data == null) {
                Executor.extentReport.logFailed("Cannot open a browser without defined one");
                return;
            }

            if (data.equalsIgnoreCase("firefox")) {
                driver = BrowserFactory.startBrowser(data);
            }
            else if (data.equalsIgnoreCase("chrome")) {
                driver = BrowserFactory.startBrowser(data);
            }
            driver.manage().window().maximize();
            globalWebDriver = new GlobalWebDriver(driver);
            wait = new Wait(driver);
            js  = new JSExecutor(driver);
            elementActions = new ElementActions(driver);
            Executor.extentReport.logPass("Browser was opened");
        }
        catch(Exception e) {
            Executor.extentReport.logFailed("Not able to open the browser : " + data + " | " + e.getMessage());
        }
    }

    public static void closeBrowser(String object, String locator, String data) throws IOException {
        try {
            Executor.extentReport.logInfo("Closing the browser");
            if (driver == null) {
                return;
            }
            driver.quit();
            driver = null;
        }
        catch(Exception e) {
            Executor.extentReport.logFailed("Not able to Close the Browser : " + e.getMessage());
            Executor.boolResult = false;
        }
    }

    /**
     * Browser redirection actions.
     */

    public static void goBack(String object, String locator, String data) throws IOException {
        try {
            Executor.extentReport.logInfo("Going back to the previous page");
            driver.navigate().back();
            wait.impWait(10);
            Executor.extentReport.logPass("Back to the previous page was successfully performed");
        }
        catch (Exception e) {
            Executor.extentReport.logFailed("Not able to go back to the previous page");
            Executor.boolResult = false;
        }
    }

    public static void goForward(String object, String locator, String data) throws IOException {
        try {
            Executor.extentReport.logInfo("Going forward from this page : " + driver.getCurrentUrl());
            driver.navigate().forward();
            wait.impWait(10);
            Executor.extentReport.logPass("Browse forward was successfully performed");
        }
        catch (Exception e) {
            Executor.extentReport.logFailed("Not able to go to target page");
            Executor.boolResult = false;
        }
    }

    public static void navigate(String object, String locator, String data) throws IOException {
        try {
            Executor.extentReport.logInfo("Navigating to URL");
            wait.impWait(10);
            driver.get(data);
            Executor.extentReport.logPass("Navigated to " + data);
        }
        catch(Exception e) {
            Executor.extentReport.logFailed("Not able to navigate the url : " + data + " | " + e.getMessage());
            Executor.boolResult = false;
        }
    }

    public static void refresh(String object, String locator, String data) throws IOException {
        try {
            Executor.extentReport.logInfo("Refreshing this page : " + driver.getCurrentUrl());
            driver.navigate().refresh();
            wait.impWait(10);
            Executor.extentReport.logPass("Refreshed");
        }
        catch (Exception e) {
            Executor.extentReport.logFailed("Not able to go to refresh the page : " + driver.getCurrentUrl());
            Executor.boolResult = false;
        }
    }

    /**
     * Element actions.
     */

    public static void click(String object, String locator, String data) throws IOException {
        try {
            Executor.extentReport.logInfo("Waiting until "+ object + " is clickable");
            wait.untilElementIsClickable(Elements.getElement(locator, Executor.OR.getString(object)));
            Executor.extentReport.logInfo("Wait succeeded and will click "+ object);
            Elements.getElement(locator, Executor.OR.getString(object)).click();
            Executor.extentReport.logPass("Click for "+ object + " was successfully performed");
        }
        catch(Exception e) {
            Executor.extentReport.logFailed("Not able to click the object name : " + object + " | " + e.getMessage());
            Executor.boolResult = false;
        }
    }

    public static void input(String object, String locator, String data) throws IOException {
        try {
            Executor.extentReport.logInfo("Entering the text in : " + object);
            Elements.getElement(locator, Executor.OR.getString(object)).sendKeys(data);
            wait.impWait(10);
            Executor.extentReport.logPass("Input " + data + " to " + object);
        }
        catch(Exception e) {
            Executor.extentReport.logFailed("Not able to enter " + data + " : "+ e.getMessage());
            Executor.boolResult = false;
        }
    }

    public static void selectByVisibleText(String object, String locator, String data) {
        Select select = new Select(Elements.getElement(locator, Executor.OR.getString(object)));
        select.selectByVisibleText(data);
        wait.impWait(10);
    }

    /**
     * Mouse hover actions.
     */

    public static void moveToElement(String object, String locator, String data) throws IOException {
        try {
            Executor.extentReport.logInfo("Moving to the element : " + object);
            elementActions.moveToElement(Elements.getElement(locator, Executor.OR.getString(object)));
            Executor.extentReport.logPass("Moved the mouse to " + object);
        }
        catch(Exception e) {
            Executor.extentReport.logFailed("Not able to move to element : " + e.getMessage());
            Executor.boolResult = false;
        }
    }

    /**
     * Wait actions.
     */

    public static void waitFor(String object, String locator, String data) throws IOException {
        // Force the machine to pause for given time (data param) seconds, otherwise will pause for 5 seconds.
        try {
            Executor.extentReport.logInfo("Waiting for " + data + " seconds delay");

            if (!data.isEmpty()) {
                Thread.sleep(Integer.parseInt(data) * 1000);
            }
            else {
                Thread.sleep(5000);
            }

            Executor.extentReport.logPass("Waited successfully");
        }
        catch(Exception e) {
            Executor.extentReport.logFailed("Not able to wait : " + e.getMessage());
            Executor.boolResult = false;
        }
    }

    public static void waitExplicitly(String object, String locator, String data) throws IOException {
        try {
            if (object == null && locator == null && data == null) {
                Executor.extentReport.logFailed("Cannot wait explicitly without complete parameters.");
                return;
            }

            Executor.extentReport.logInfo("Waiting for " + object + " explicitly in " + data + " seconds");
            wait.untilElementVisible(Elements.getElement(locator, Executor.OR.getString(object)));
            wait.impWait(Integer.parseInt(data));
            Executor.extentReport.logPass("Waited successfully within " + data + " seconds");
        }
        catch(Exception e) {
            Executor.extentReport.logFailed("Not able to wait explicitly : " + e.getMessage());
            Executor.boolResult = false;
        }
    }

    public static void waitForElementInvisibility(String object, String locator, String data) throws IOException {
        try {
            if (object == null && locator == null && data == null) {
                Executor.extentReport.logFailed("Cannot wait explicitly without complete parameters.");
                return;
            }

            Executor.extentReport.logInfo("Waiting for " + object + " to be invisible within " + data + " seconds");
            wait.untilElementNotVisible(Elements.getBy(locator, Executor.OR.getString(object)));
            Executor.extentReport.logPass("Object "+ object +" has been invisible");
        }
        catch(Exception e) {
            Executor.extentReport.logFailed("Not able to wait : " + e.getMessage());
            Executor.boolResult = false;
        }
    }

    /**
     * Assertion actions.
     */

    public static void assertElementVisible(String object, String locator, String data) throws IOException {
        try {
            Executor.extentReport.logInfo("Asserting if element is visible.");
            Assert.assertTrue(Elements.getElement(locator, Executor.OR.getString(object)).isDisplayed());
            Executor.extentReport.logPass("Assertion : Object " + object + " was visible");
        }
        catch (Exception e) {
            Executor.extentReport.logFailed("Assertion : Object " + object + " is not visible | " + e.getMessage());
            Executor.boolResult = false;
        }
    }

    public static void assertPageReached(String object, String locator, String data) throws IOException {
        try {
            Executor.extentReport.logInfo("Asserting if page was reached.");
            Assert.assertTrue(driver.getCurrentUrl().contains(data));
            Executor.extentReport.logPass("Assertion : Target page was reached");
        }
        catch (Exception e) {
            Executor.extentReport.logFailed("Assertion error. Page was not reached." + e.getMessage());
            Executor.boolResult = false;
        }
    }
}
