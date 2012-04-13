package com.dynacrongroup.sample.remote;

import com.dynacrongroup.sample.TestPage;
import com.dynacrongroup.webtest.ParallelRunner;
import com.dynacrongroup.webtest.WebDriverBase;
import com.dynacrongroup.webtest.util.Path;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

/**
 * This test builds off of the same structure used in SeleniumSimpleIT, but introduces the
 * use of page objects to model the web pages under test.
 * <p/>
 * Page objects can be used to neatly separate the model of the web site from tests that use the
 * model.  When the website is updated, the model is updated, rather than the tests.  As a result,
 * test maintenance is much lighter, and less code ends up copy/pasted between tests.
 */
@RunWith(ParallelRunner.class)
public class SeleniumPageObjectIT extends WebDriverBase {

    Path p = new Path("www.dynacrongroup.com", 80);

    public SeleniumPageObjectIT(String browser, String browserVersion) {
        super(browser, browserVersion);
        p.setContext("");
    }

    @Before
    public void preparePage() {
        // These tests require a level of javascript support not provided with htmlunit
        assumeTrue(! this.getTargetWebBrowser().isHtmlUnit());
        driver.get(p._("/webtest.html"));
    }

    /**
     * Sample javascript button test using page objects.
     */
    @Test
    public void pageObjectButtonTextTest() {
        this.getLogger().info("Starting test: {}", name.getMethodName());

        TestPage testPage = PageFactory.initElements(driver, TestPage.class);
        try {
            testPage.clickTestButton();
            assertThat("alert failed to open", testPage.isAlertPresent(), is(true));
            assertThat("alert text is incorrect", testPage.getAlertText(),
                    equalToIgnoringCase("You clicked a fancy bit of text!"));
        } finally {
            testPage.closeAlert();
        }
    }

    /**
     * Sample javascript button test without using page objects.  Notice that
     * the test records low-level details about how the page is implemented.
     */
    @Test
    public void proceduralButtonTextTest() {
        this.getLogger().info("Starting test: {}", name.getMethodName());

        WebElement button = driver.findElement(By.id("fancy"));
        try {
            button.click();
            Alert alert = driver.switchTo().alert();
            assertThat("alert text is incorrect", alert.getText(),
                    equalToIgnoringCase("You clicked a fancy bit of text!"));
        } catch (NoAlertPresentException e) {
            fail( "alert failed to open" );
        } finally {
            Alert alert = driver.switchTo().alert();
            alert.accept();
        }
    }


}