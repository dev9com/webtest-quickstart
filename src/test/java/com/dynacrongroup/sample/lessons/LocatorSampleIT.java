package com.dynacrongroup.sample.lessons;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

/**
 * Created by IntelliJ IDEA.
 * User: yurodivuie
 * Date: 7/2/12
 * Time: 10:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class LocatorSampleIT {
    
    private static final Logger LOG = LoggerFactory.getLogger(LocatorSampleIT.class);
    
    WebDriver driver;
    
    @Before
    public void provisionWebDriver() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    /**
     * @After blocks are always executed after each test method, regardless of success
     * or failure.
     * 
     * Quitting the driver in the @After block ensures it will always be shutdown, and
     * each test case will receive a fresh driver (so subsequent tests won't be affected
     * by a crashed driver).  Even so, it's not generally recommended in real test suites
     * since initializing the driver is such an expensive (slow) operation.
     */
    @After
    public void killDriver() {
        driver.quit();
    }
    
    @Test
    public void locatingNav() {
        driver.get("http://www.saucelabs.com");
        
        By[] navLocators = {    By.id("mainNav"),
                                By.cssSelector("#mainNav"),
                                By.xpath("//*[@id='mainNav']"),
                                By.linkText("Products"),
                                By.partialLinkText("duct")
                           };
        
        for (By by : navLocators) {
            WebElement navContainer = driver.findElement(by);
            String text = navContainer.getText();
            LOG.info(text);
            assertThat( text, containsString( "Products" ) );
        }
    }
}
