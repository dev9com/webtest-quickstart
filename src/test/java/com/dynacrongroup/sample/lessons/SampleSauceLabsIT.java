package com.dynacrongroup.sample.lessons;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

/**
 * Created by IntelliJ IDEA.
 * User: yurodivuie
 * Date: 7/2/12
 * Time: 11:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class SampleSauceLabsIT {
    
    private static final Logger LOG = LoggerFactory.getLogger(SampleSauceLabsIT.class);

    private static String sauceUser = System.getenv("SAUCELABS_USER");
    private static String sauceKey = System.getenv("SAUCELABS_KEY");
    private static WebDriver driver;

    @BeforeClass
    public static void setupDriver() throws MalformedURLException {

        DesiredCapabilities capabillities = DesiredCapabilities.firefox();
        capabillities.setCapability("version", "9");
        capabillities.setCapability("platform", Platform.XP);
        capabillities.setCapability("name", "A real test in a real lab");

        driver = new RemoteWebDriver(new URL("http://" + sauceUser + ":" + sauceKey + "@ondemand.saucelabs.com:80/wd/hub"),
                                                 capabillities);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
    
    @AfterClass
    public static void afterEverything() {
        driver.quit();
    }
    
    @Test
    public void simpleSauceTest() {
        driver.get("http://www.saucelabs.com");
        WebElement nav = driver.findElement( By.id( "mainNav" ) );
        LOG.info("Session id: " + ((RemoteWebDriver)driver).getSessionId());
        assertThat( nav.getText(), containsString( "Products" ) );
    }
}
