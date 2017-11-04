package com.b2b.automation.utility;

import com.b2b.automation.config.Constants;
import com.b2b.automation.config.DriverActions;

import java.io.IOException;

public class BrowserSelector {
    public static void invokeBrowser(int runMode) throws IOException {
        if (runMode == 2) {
            DriverActions.openBrowser("", "", Constants.CHROME);
        }
        else if (runMode == 5) {
            DriverActions.openBrowser("", "", Constants.FIREFOX);
        }
    }
}
