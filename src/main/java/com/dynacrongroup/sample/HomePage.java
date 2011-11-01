package com.dynacrongroup.sample;

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

    /* The page factory tries to initialize WebElements by matching their names to an id.  "search-input"
        isn't a nice java variable name, so the variable is given a custom name and the element is located
        using the FindBy annotation instead. */
    @FindBy(id="search-text")
    private WebElement queryInput;

    @FindBy(id="search-button")
    private WebElement goButton;

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
     * Executes a search from the home page.  The search function is modeled as a service of the
     * HomePage object.
     *
     * @param query
     *          The query to enter in the "search-input" field.
     * @return
     *          A new PageObject representing the browser page reached after the "GO" button is pressed.
     *              To expand on this set of tests, one might create a new "SearchResultsPage" object
     *              that modeled this new page, but a generic PageObject will suffice for the purposes
     *              of this example.
     */
    public PageObject search(String query) {
        LOG.debug("Searching for [{}]", query);

        queryInput.clear();
        queryInput.sendKeys(query);
        goButton.click();

        return PageFactory.initElements(driver, PageObject.class);
    }

}
