package com.dynacrongroup.sample;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;

/**
 * This is an example of an incomplete page object modeling the Dynacron home page.  It only exercises
 * the page's search function.  A complete testing suite for the Dynacron site would include models for
 * all of the pages hosted on the site and tests that exercises those models, verifying the services'
 * behavior.
 */
public class TestPage extends PageObject {

    public final static String TITLE = "Webtest Sample Test Page";

    private final static Logger LOG = LoggerFactory.getLogger(TestPage.class);

    /**
     * The page factory tries to initialize WebElements by matching their names to an id.  "fancy"
     * isn't a very informative id, so this element has been named "testButton", and the FindBy annotation
     * is used to locate the element.
     */
    @FindBy(id = "fancy")
    private WebElement testButton;

    @FindBy(tagName = "h1")
    private WebElement pageHeading;

    @FindBy(tagName = "h2")
    private WebElement subHeading;

    /**
     * This constructor is necessary to provide a WebDriver for the services the page uses.  It's also
     * used by default when this page is initialized using a PageFactory
     * <p/>
     * See http://code.google.com/p/selenium/wiki/PageFactory for documentation on how to use page factories
     * to initialize WebElements.
     *
     * @param driver The WebDriver that this page will use.  The test assumes that the driver is already on the
     *               correct page, but the constructor could be designed to check the title and use driver.get
     *               to load the correct page if necessary.
     */
    public TestPage(WebDriver driver) {
        super(driver);

        /* Generally, the page object should not use assertions (leave those for the junit tests that
            exercise the page objects), but it's a good idea to at least make sure you're on the right
            page when initializing.
         */
        assertThat(getTitle(), equalToIgnoringWhiteSpace(TITLE));
    }

    /*-----------------------------------------------------------
     *  Services defined for this page.  Note that none of these includes an assertion; these are present
     *  in the test code.
     *-----------------------------------------------------------*/

    /**
     * This service is fairly straightforward.  The function provides a layer of abstraction between the
     * implementation on the page and the abstract service provided by the page.
     */
    public void clickTestButton() {
        testButton.click();
    }

    /**
     * This service checks if an alert is present on the page, and returns the text for the alert,
     * present.
     *
     * @return Alert text, or null if alert is not present.
     */
    public String getAlertText() {
        String text = null;
        if (isAlertPresent()) {
            Alert alert = driver.switchTo().alert();
            text = alert.getText();
        }
        return text;
    }

    /**
     * This service tries to close an alert, if it is present.  This sort of service should probably be called
     * in a "finally" block, since an open alert window could interfere with subsequent tests.
     */
    public void closeAlert() {
        if (isAlertPresent()) {
            Alert alert = driver.switchTo().alert();
            alert.accept();
        }
    }

    /**
     * This service simply returns whether or not an alert is present on the page; it's useful as both a test
     * and as a utility preventing exceptions in closeAlert and getAlertText.
     * @return  Returns true if the alert box is present.
     */
    public Boolean isAlertPresent() {
        Boolean present = true;
        try {
            driver.switchTo().alert().getText();
        } catch (NoAlertPresentException e) {
            present = false;
        }
        return present;
    }

    /* These "services" are actually referring to static content on the page, which normally wouldn't need
     * automated tests; they're just not interesting from a functional perspective.  They're included as
     * examples.
     */
    public String getHeader() {
        return pageHeading.getText();
    }

    public String getSubHeader() {
        return subHeading.getText();
    }
}
