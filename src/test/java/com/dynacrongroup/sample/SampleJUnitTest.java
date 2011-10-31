package com.dynacrongroup.sample;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dynacrongroup.webtest.ParallelRunner;
import com.dynacrongroup.webtest.WebDriverBase;
import com.dynacrongroup.webtest.util.Path;

/**
 * This test runs as part of the lifecycle BEFORE the WAR has been created and
 * the web server has been started. That's why it has to go out to google.com -
 * that server is running regardless of this local web app.I
 * 
 * It's mainly a nice, easy example of a test that should work with SauceLabs
 * even if you haven't set up sauce_connect.
 */
@RunWith(ParallelRunner.class)
public class SampleJUnitTest extends WebDriverBase {

    private final static Logger log = LoggerFactory
	    .getLogger(SampleJUnitTest.class);

    Path p = new Path("www.dynacrongroup.com", 80);

    public SampleJUnitTest(String browser, String browserVersion) {
	super(browser, browserVersion);
    }

    @Test
    public void firstTest() {
	log.info("the browser is: " + this.getTargetWebBrowser().browser);
	driver.get(p._("/"));
	assertTrue(driver.getPageSource().contains("Dynacron"));
    }

    @Test
    public void secondTest() {
	log.info("this version is: " + this.getTargetWebBrowser().version);

	driver.get(p._("/"));
	assertTrue(driver.getPageSource().contains("Group"));
    }

    @Test
    public void logTest() {
	log.trace("This is a trace message.  It will only appear if the log");
	log.trace("level is set to \"TRACE\".");
	log.debug("This is a debug message.  It will only appear if the log");
	log.debug("level is set to \"TRACE\" or \"DEBUG\".");
	log.info("This is an info message.  It will only appear if the log");
	log.info("level is set to \"TRACE\", \"DEBUG\", or \"INFO\".");
	log.warn("This is a warn message.  It will only appear if the log");
	log.warn("level is set to \"TRACE\", \"DEBUG\", \"INFO\", or \"WARN\".");
	log.error("This is an error message.  It will only appear if the log");
	log.error("level is set to any valid level.");
    }
}
