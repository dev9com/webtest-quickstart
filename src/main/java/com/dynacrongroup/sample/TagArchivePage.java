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
 * This is a second incomplete example of a PageObject, modeling the page delivered
 * when selecting a tag from the tag cloud.  More extensive explanations can be found
 * under HomePage
 */
public class TagArchivePage extends PageObject {

    private final static Logger LOG = LoggerFactory.getLogger(TagArchivePage.class);

    @FindBy(className = "page-header")
    private WebElement contentHeader;

    public TagArchivePage(WebDriver driver) {
        super(driver);

        /* This page is dynamically generated, so there is no consistent way of asserting
            it has loaded 
         */
    }

    public String getTagDisplayed() {
        
        String contentTitle = contentHeader.getText();
        String tag = null;

        if (contentTitle.startsWith("Tag Archives: ")) {
            LOG.trace("Trying to parse tag out of title [{}]", contentTitle);
            tag = contentTitle.substring( contentTitle.indexOf( ":" ) + 1 ).trim();
        }

        return tag;
    }
}
