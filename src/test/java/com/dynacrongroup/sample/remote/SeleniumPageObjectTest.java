package com.dynacrongroup.sample.remote;

import com.dynacrongroup.sample.HomePage;
import com.dynacrongroup.sample.PageObject;
import com.dynacrongroup.sample.TagArchivePage;
import com.dynacrongroup.webtest.ParallelRunner;
import com.dynacrongroup.webtest.WebDriverBase;
import com.dynacrongroup.webtest.util.Path;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * This test builds off of the same structure used in SeleniumSimpleTest, but introduces the
 * use of page objects to model the web pages under test.
 *
 * Page objects can be used to neatly separate the model of the web site from tests that use the
 * model.  When the website is updated, the model is updated, rather than the tests.  As a result,
 * test maintenance is much lighter, and less code ends up copy/pasted between tests.
 */
@RunWith(ParallelRunner.class)
public class SeleniumPageObjectTest extends WebDriverBase {

    private static final Logger LOG = LoggerFactory.getLogger(SeleniumPageObjectTest.class);
    Path p = new Path("www.dynacrongroup.com", 80);

    public SeleniumPageObjectTest( String browser, String browserVersion ) {
        super(browser, browserVersion);
    }

    /**
     * Sample successful search test using page objects.
     */
    @Test
    public void pageObjectTagTest() {
        LOG.info("Starting test: {}", name.getMethodName());

        driver.get(p._("/"));

        String testTag = "agile";
        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        TagArchivePage result = homePage.selectTag(testTag);
        
        assertThat( result.getTagDisplayed(), equalToIgnoringCase( testTag ));
    }

    /**
     * Sample successful search test without using page objects.  Notice that
     * the test records low-level details about how the page is implemented.
     */
    @Test
    public void proceduralTagTest() {
        LOG.info("Starting test: {}", name.getMethodName());

        driver.get(p._("/"));

        assertThat(driver.getTitle(),  startsWith("Dynacron Group"));
        String tag = "agile";
        LOG.trace( "Attempting to select tag with name [{}]", tag );
        WebElement tagCloud = driver.findElement(By.className("tagcloud"));
        WebElement tagLink = tagCloud.findElement(By.linkText( tag ));
        tagLink.click();
        
        WebElement contentHeader = driver.findElement(By.className("page-header"));
        String contentTitle = contentHeader.getText();

        assertThat(contentTitle, endsWith("agile"));
    }


    @Test
    public void pageObjectUnsuccessfulTagSearchTest() {
        LOG.info("Starting test: {}", name.getMethodName());

        driver.get(p._("/"));

        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        assertThat(homePage.isTagPresent("faker"), is(Boolean.FALSE));
    }

    @Test
    public void pageObjectSuccessfulTagSearchTest() {
        LOG.info("Starting test: {}", name.getMethodName());

        driver.get(p._("/"));

        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        assertThat(homePage.isTagPresent("agile"), is(Boolean.TRUE));
    }


}