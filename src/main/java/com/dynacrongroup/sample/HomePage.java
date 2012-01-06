package com.dynacrongroup.sample;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.startsWith;

/**
 * This is an example of an incomplete page object modeling the Dynacron home page.  It only exercises
 * the page's search function.  A complete testing suite for the Dynacron site would include models for
 * all of the pages hosted on the site and tests that exercises those models, verifying the services'
 * behavior.
 */
public class HomePage extends PageObject {

    public final static String TITLE = "Dynacron Group";

    private final static Logger LOG = LoggerFactory.getLogger(HomePage.class);

    /* The page factory tries to initialize WebElements by matching their names to an id.  "tagcloud"
        isn't an id, so the "FindBy" annotation is used to search for it by className */
    
    @FindBy(className = "tagcloud")
    private WebElement tagCloud;

    /**
     * This constructor is necessary to provide a WebDriver for the services the page uses.  It's also
     * used by default when this page is initialized using a PageFactory
     *
     * See http://code.google.com/p/selenium/wiki/PageFactory for documentation on how to use page factories
     * to initialize WebElements.
     *
     * @param driver
     *          The WebDriver that this page will use.  The test assumes that the driver is already on the
     *          correct page, but the constructor could be designed to check the title and use driver.get
     *          to load the correct page if necessary.
     */
    public HomePage(WebDriver driver) {
        super(driver);

        /* Generally, the page object should not use assertions (leave those for the junit tests that
            exercise the page objects), but it's a good idea to at least make sure you're on the right
            page when initializing.
         */
        assertThat(getTitle(), startsWith(TITLE));
    }

    /**
     * Selects an arbitrary tag from the home page's tag cloud.
     * 
     * @param tag   The name of the tag to select in the tag cloud
     * @return      A new page loaded by clicking the tag
     */
    public TagArchivePage selectTag(String tag) {
        LOG.trace("Attempting to select tag with name [{}]", tag);
        
        TagArchivePage newPage = null;

        if (isTagPresent(tag)) {
            WebElement tagLink = tagCloud.findElement( By.linkText(tag) );
            tagLink.click();
            newPage = PageFactory.initElements(driver, TagArchivePage.class);
        }
        else {
            LOG.error("tag [{}] not found", tag);
        }

        return newPage;
    }

    /**
     * Verifies that a tag is present.
     * @param tag
     * @return
     */
    public Boolean isTagPresent(String tag) {
        LOG.trace("Attempting to find tag with name [{}]", tag);
    
        Boolean success = Boolean.TRUE;
        try {
            tagCloud.findElement( By.linkText(tag) );
        }
        catch (NoSuchElementException e) {
            success = Boolean.FALSE;
        }
        return success;
    }
}
