/**

 With Selenium 2 aka WebDriver, there is no overt reason to use HTMLUnit directly -
 just use the WebDriver API but set the target browser to HTMLUnit.  This class
 remains just to show how there is no dependency between the Path object and the rest
 of the WebDriver code.
 */
package com.dynacrongroup.sample.local;

import com.dynacrongroup.webtest.util.Path;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test demonstrates how to construct a test using HtmlUnit, which executes in parallel alongside Selenium tests.
 * Selenium can also execute headless tests using the HtmlUnitDriver.
 */
public class HtmlUnitIT {

    Path p = new Path();

    @Test
    public void homePage() throws Exception {
        final WebClient webClient = new WebClient();
        final HtmlPage page = webClient.getPage( p._( "/" ) );
        assertEquals( "Hi", page.getTitleText() );

        final String pageAsXml = page.asXml();
        assertTrue( pageAsXml.contains( "<h2>" ) );

        final String pageAsText = page.asText();
        assertTrue( pageAsText.contains( "Hello World" ) );

        webClient.closeAllWindows();
    }
}
