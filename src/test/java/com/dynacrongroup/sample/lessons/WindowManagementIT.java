package com.dynacrongroup.sample.lessons;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalToIgnoringCase;

/**
 * Created by IntelliJ IDEA.
 * User: yurodivuie
 * Date: 7/9/12
 * Time: 9:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class WindowManagementIT {

    private static final Logger LOG = LoggerFactory.getLogger( LocatorSampleIT.class );
    private static final int DELAY = 3000;

    static WebDriver driver;

    @BeforeClass
    public static void provisionWebDriver() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait( 5, TimeUnit.SECONDS );
        driver.get( "http://localhost:8080/webtest-quickstart/" );
        pause( DELAY );
    }

    @AfterClass
    public static void killDriver() {
        driver.quit();
    }

    @Test
    public void normalLink() {
        driver.findElement( By.linkText( "Regular Link" ) ).click();
        pause( DELAY );
        assertThat( driver.getTitle(), equalToIgnoringCase( "This window is new!" ) );
        driver.navigate().back();
        assertThat( driver.getTitle(), containsString("Hi"));
    }

    @Test
    public void popupLink() {
        driver.findElement( By.linkText( "New Window Link" ) ).click();

        try {
            switchWindows();
            pause( DELAY );
            assertThat( driver.getTitle(), equalToIgnoringCase( "This window is new!" ) );
        }
        finally {
            driver.close();  //Close the popup...
            switchWindows(); //switch back.
            pause( DELAY );
        }
    }
    
    @Test
    public void simulateAjax() {
        driver.findElement( By.id("aSlowElement") ).click();
        assertThat(driver.findElement(By.id("newStuff")).getText(),
                   equalToIgnoringCase("new stuff"));
        
    }

    @Test
    public void slowPopupLink() {
        driver.findElement( By.id("btnSlowNewNamelessWindow") ).click();

        try {
            LOG.info("Switching to popup...");

            waitForSecondWindow();
            switchWindows();
            pause( DELAY );
            assertThat( driver.getTitle(), equalToIgnoringCase( "This window is new!" ) );
        }
        finally {
            LOG.info("Switching back...");
            driver.close();
            switchWindows();
            pause( DELAY );
        }
    }

    private Boolean waitForSecondWindow() {
        return (new WebDriverWait(driver, 10))
                      .until(new ExpectedCondition<Boolean>(){
                          @Override
                          public Boolean apply(WebDriver d) {
                              return d.getWindowHandles().size() > 1;
                          }});
    }


    private void switchWindows() {
        String currentHandle = null;
        try {
            currentHandle = driver.getWindowHandle();
        }
        catch (NoSuchWindowException e) {
            LOG.info("No current window.  I probably just closed it.");
        }
        
        Set<String> allHandles = driver.getWindowHandles();
        LOG.info("There are currently {} windows", allHandles.size());
        
        for ( String handle : allHandles ) {
            if ( !handle.equals( currentHandle ) ) {
                LOG.info("Switching to a new window!");
                driver.switchTo().window( handle );
                break;
            }
        }

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
