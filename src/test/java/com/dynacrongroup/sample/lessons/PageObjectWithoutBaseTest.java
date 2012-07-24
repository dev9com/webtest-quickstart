package com.dynacrongroup.sample.lessons;

import com.dynacrongroup.sample.TestPage;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: yurodivuie
 * Date: 7/24/12
 * Time: 3:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class PageObjectWithoutBaseTest {

    private static final Logger LOG = LoggerFactory.getLogger( PageObjectWithoutBaseTest.class );
    private static final int DELAY = 3000;

    static WebDriver driver;
    TestPage testPage;

    @BeforeClass
    public static void provisionWebDriver() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait( 5, TimeUnit.SECONDS );
    }

    @Before
    public void getTestPage(){
        testPage = TestPage.getTestPage(driver);
        pause( DELAY );
    }

    @After
    public void closeAlertIfPresent() {
        testPage.closeAlert();
    }

    @AfterClass
    public static void killDriver() {
        driver.quit();
    }

    @Test
    public void testAPage() {
        testPage.clickTestButton();
        pause(DELAY);
        assertTrue(testPage.isAlertPresent());
    }

    @Test
    public void closingAlerts() {
        testPage.clickTestButton();
        pause(DELAY);
        testPage.closeAlert();
        pause(DELAY);
        assertFalse(testPage.isAlertPresent());
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
