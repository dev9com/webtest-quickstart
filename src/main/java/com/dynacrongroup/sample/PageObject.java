package com.dynacrongroup.sample;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

/**
 * PageObject is a generate page from any given site.  It is designed to support the page object
 * design pattern, as described in http://code.google.com/p/selenium/wiki/PageObjects.
 *
 * Page objects are used to model a given page on a site, wrapping the individual calls to the
 * webDriver object.  The Page object provides services that are designed to model the functional
 * behavior of a given web page.
 */
public class PageObject {

    private final static Logger LOG = LoggerFactory.getLogger(PageObject.class);

    public WebDriver driver;

    public PageObject(WebDriver driver) {
        this.driver = driver;
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public String getBodyText() {
        return driver.findElement(By.tagName("Body")).getText();
    }
}
