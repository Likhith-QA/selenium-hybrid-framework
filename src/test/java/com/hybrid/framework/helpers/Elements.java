package com.hybrid.framework.helpers;

import com.hybrid.framework.config.Constants;
import com.hybrid.framework.config.DriverActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Class storage for locators.
 * Ability to return required locator per element.
 */
public class Elements {

    public static WebElement getElement(String locator, String value) {
        WebElement element;
        switch (locator) {
            case Constants.LOCATOR_ID :
                element = DriverActions.globalWebDriver.getDriver().findElement(By.id(value));
                break;
            case Constants.LOCATOR_NAME :
                element = DriverActions.globalWebDriver.getDriver().findElement(By.name(value));
                break;
            case Constants.LOCATOR_CSS :
                element = DriverActions.globalWebDriver.getDriver().findElement(By.cssSelector(value));
                break;
            default :
                element = DriverActions.globalWebDriver.getDriver().findElement(By.xpath(value));
        }
        return element;
    }

    public static By getBy(String locator, String value) {
        By by;
        switch (locator) {
            case Constants.LOCATOR_ID :
                by = By.id(value);
                break;
            case Constants.LOCATOR_NAME :
                by = By.name(value);
                break;
            case Constants.LOCATOR_CSS :
                by = By.cssSelector(value);
                break;
            default :
                by = By.xpath(value);
        }
        return by;
    }
}
