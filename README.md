# The selenium-hybrid Automation Framework

A Java for Selenium API powered Framework that uses Hybrid Framework


## What's inside?

* A combination of Data-Driven and Keyword-Driven Framework.

### A Hybrid Framework

Since this is a `Hybrid Framework` it means all raw data and driver actions to be used in execution are stored in a data source. With that being said, all data are stored in Excel (`.xlsx`) file.

The files' path is :
```
selenium-hybrid-framework/src/test/java/com/hybrid/framework/dataEngine/DataEngine.xlsx
```

## System Requirements
**Dependencies**
* Selenium - whatever latest version (recommended)
* Maven 3 or later
* Surefire Plugin 2 or later
* Google Guava 21.0 (recommended)
* org.apache.commons — commons-exec 1.3 or later
* org.apache.commons — commons-lang3 3.5 or later

**Environment**
* Java - JDK 8 (1.8.0_121)
* IDE - IntelliJ IDEA (Community or Ultimate)

**NOTE :**

For browser browser_binaries, the WebDriverManager keeps downloading the latest version of binary of the chosen browser so it is better to make sure or re-assure from time to time that the Selenium API version works fine with the browser. Otherwise, an upgrade is necessary. Reason is, we need to keep to our browsers updated to latest so a latest version of Selenium API is a must (if it's proven stable).

