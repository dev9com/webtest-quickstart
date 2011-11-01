package com.dynacrongroup.sample;

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
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.startsWith;

/**
 * This test builds off of the same structure used in SampleJunitSelenium, but introduces the
 * use of page objects to model the web pages under test.
 *
 * Page objects can be used to neatly separate the model of the web site from tests that use the
 * model.  When the website is updated, the model is updated, rather than the tests.  As a result,
 * test maintenance is much lighter, and less code ends up copy/pasted between tests.
 */
@RunWith(ParallelRunner.class)
public class SampleJunitPageObjectTest extends WebDriverBase {

    private static final Logger LOG = LoggerFactory.getLogger(SampleJunitPageObjectTest.class);
    Path p = new Path("www.dynacrongroup.com", 80);

    public SampleJunitPageObjectTest(String browser, String browserVersion) {
        super(browser, browserVersion);
    }

    /**
     * Sample successful search test using page objects.
     */
    @Test
    public void successfulPageObjectSearchTest() {
        LOG.info("Starting test: {}", name.getMethodName());

        driver.get(p._("/"));

        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        PageObject result = homePage.search("fun");

        assertThat(result.getBodyText(), containsString("Company Values"));
    }

    /**
     * Sample successful search test without using page objects
     */
    @Test
    public void successfulProceduralSearchTest() {
        LOG.info("Starting test: {}", name.getMethodName());

        driver.get(p._("/"));

        assertThat(driver.getTitle(),  startsWith("Dynacron Group"));
        String query = "fun";
        LOG.debug("Searching for [{}]", query);
        WebElement queryInput = driver.findElement(By.id("search-text"));
        queryInput.clear();
        queryInput.sendKeys(query);
        WebElement goButton = driver.findElement(By.id("search-button"));
        goButton.click();
        WebElement body = driver.findElement(By.tagName("Body"));

        assertThat(body.getText(), containsString("Company Values"));
    }

    //Normally one would also include negative tests here...
}