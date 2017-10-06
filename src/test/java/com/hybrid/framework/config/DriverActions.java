package com.hybrid.framework.config;

import com.hybrid.framework.executionEngine.Executor;
import com.hybrid.framework.helpers.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.io.IOException;
import java.util.Objects;

import static com.hybrid.framework.executionEngine.Executor.OR;

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

    public static void openBrowser(String object, String locator, String data) throws IOException {
        try {
            Executor.extentReport.logInfo("Opening Browser");

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

    public static void click(String object, String locator, String data) throws IOException {
        try {
            Executor.extentReport.logInfo("Clicking on WebElement : "+ object);
            Elements.getElement(locator, OR.getString(object)).click();
            Executor.extentReport.logPass("Click to "  + object + " was successful");
            if (data != null) {
                wait.impWait(Integer.parseInt(data));
                Executor.extentReport.logInfo("Click was explicitly waited for " + data + " seconds");
            }
            else {
                wait.impWait(10);
                Executor.extentReport.logInfo("Click was implicitly waited for " + data + " seconds");
            }
        }
        catch(Exception e) {
            Executor.extentReport.logFailed("Not able to click the object name : " + object + " | " + e.getMessage());
            Executor.boolResult = false;
        }
    }

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

    public static void input(String object, String locator, String data) throws IOException {
        try {
            Executor.extentReport.logInfo("Entering the text in : " + object);
            Elements.getElement(locator, OR.getString(object)).sendKeys(data);
            wait.impWait(10);
            Executor.extentReport.logPass("Input " + data + " to " + object);
        }
        catch(Exception e) {
            Executor.extentReport.logFailed("Not able to enter " + data + " : "+ e.getMessage());
            Executor.boolResult = false;
        }
    }

    public static void moveToElement(String object, String locator, String data) throws IOException {
        try {
            Executor.extentReport.logInfo("Moving to the element : " + object);
            elementActions.moveToElement(Elements.getElement(locator, OR.getString(object)));
            Executor.extentReport.logPass("Moved the mouse to " + object);
        }
        catch(Exception e) {
            Executor.extentReport.logFailed("Not able to move to element : " + e.getMessage());
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

    public static void waitFor(String object, String locator, String data) throws IOException {
        try {
            if (!Objects.equals(object, "")) {
                Executor.extentReport.logInfo("Waiting for " + object + " explicitly in " + data + " seconds");
                wait.untilElementVisible(Elements.getElement(locator, OR.getString(object)));
            }
            else {
                Executor.extentReport.logInfo("Wait for " + data);
                Thread.sleep(Integer.parseInt(data));
            }
            Executor.extentReport.logPass("Waited successfully");
        }
        catch(Exception e) {
            Executor.extentReport.logFailed("Not able to Wait : " + e.getMessage());
            Executor.boolResult = false;
        }
    }

    public static void waitExplicitly(String object, String locator, String data) throws IOException {
        try {
            Executor.extentReport.logInfo("Waiting for " + object + " explicitly in " + data + " seconds");
            wait.untilElementVisible(Elements.getElement(locator, OR.getString(object)));
            wait.impWait(Integer.parseInt(data));
            Executor.extentReport.logPass("Waited successfully for " + data + " seconds");
        }
        catch(Exception e) {
            Executor.extentReport.logFailed("Not able to Wait explicitly : " + e.getMessage());
            Executor.boolResult = false;
        }
    }

    public static void waitForElementInvisibility(String object, String locator, String data) throws IOException {
        try {
            if (object != null && !object.equalsIgnoreCase("NULL")) {
                Executor.extentReport.logInfo("Waiting for " + object + " to be invisible implicitly in " + data + " seconds");
                wait.untilElementNotVisible(Elements.getBy(locator, OR.getString(object)));
                Executor.extentReport.logPass("Object "+ object +" has been invisible");
            }
            else {
                Executor.extentReport.logInfo("Wait for " + data);
                wait.impWait(Integer.parseInt(data));
            }
        }
        catch(Exception e) {
            Executor.extentReport.logFailed("Not able to Wait : " + e.getMessage());
            Executor.boolResult = false;
        }
    }

    public static void assertElementVisible(String object, String locator, String data) throws IOException {
        try {
            Executor.extentReport.logInfo("Asserting if element is visible.");
            Assert.assertTrue(Elements.getElement(locator, OR.getString(object)).isDisplayed());
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
