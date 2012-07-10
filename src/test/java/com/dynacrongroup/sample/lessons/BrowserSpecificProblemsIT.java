package com.dynacrongroup.sample.lessons;

import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by IntelliJ IDEA.
 * User: yurodivuie
 * Date: 7/9/12
 * Time: 9:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class BrowserSpecificProblemsIT {

    private static final Logger LOG = LoggerFactory.getLogger( LocatorSampleIT.class );
    private static final int DELAY = 50;
    private static String sauceUser = System.getenv( "SAUCELABS_USER" );
    private static String sauceKey = System.getenv("SAUCELABS_KEY");

    static WebDriver driver;

    @After
    public void killDriver() {
        driver.quit();
    }

    public static void setupDriver(DesiredCapabilities capabilities) throws MalformedURLException {
        capabilities.setCapability( "version", "8" );
        capabilities.setCapability( "platform", Platform.XP );
        capabilities.setCapability( "name", "A real test in a real lab" );

        driver = new RemoteWebDriver(new URL("http://" + sauceUser + ":" + sauceKey + "@ondemand.saucelabs.com:80/wd/hub"),
                                     capabilities);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void firefoxResizing() throws MalformedURLException {
        setupDriver( DesiredCapabilities.firefox() );
        resizeTest();
    }

    @Test
    public void ieResizing() throws MalformedURLException {
        setupDriver( DesiredCapabilities.internetExplorer() );
        resizeTest();
    }

    private void resizeTest() {
        driver.get( "http://localhost:8080/webtest-quickstart/" );
        LOG.info("Initial dimensions: {}", driver.manage().window().getSize());
        pause( DELAY );

        Dimension newDimension = new Dimension(200,200);
        driver.manage().window().setSize(newDimension);

        Dimension resultDimension = driver.manage().window().getSize();
        LOG.info( "New dimensions: {}", driver.manage().window().getSize() );
        pause( DELAY );
        assertThat( resultDimension.getWidth(), equalTo( newDimension.getWidth() ) );
        assertThat(resultDimension.getHeight(), equalTo( newDimension.getHeight() ));

    }

    private static final void pause( int millis ) {
        try {
            Thread.sleep( millis );
        }
        catch ( InterruptedException e ) {
            LOG.info( e.getMessage() );
        }
    }
}
