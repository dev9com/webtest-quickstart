/**
 * This is a canonical example of a Selenium web test executing against a local
 * build.
 */
package com.dynacrongroup.sample;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.dynacrongroup.webtest.ParallelRunner;
import com.dynacrongroup.webtest.WebDriverBase;
import com.dynacrongroup.webtest.util.Path;

// This RunWith annotation provides the hook into the Selenium parallel
// execution (the stuff that runs multiple Selenium browsers in parallel
// with SauceLabs
@RunWith(ParallelRunner.class)
public class SampleJUnitSeleniumIT extends WebDriverBase {

	/** The Path object picks up standard naming rules for finding the target. */
	Path p = new Path();

	/** This constructor is required to support the parallel test execution. */
	public SampleJUnitSeleniumIT(String browser, String browserVersion) {
		super(browser, browserVersion);
	}

	@Before
	public void setPath() {
		p.setContext(WebTestConstants.CONTEXT);
		getLogger().info(p.getExpectedSauceConnectString());
	}

	/**
	 * A simple web test. The driver.get command tells the remote browser to
	 * fetch a web page. The driver.getPageSource returns the source of the page
	 * from the remote browser to this local test.
	 * 
	 * This test is declared to throw Exception - this allows your test code to
	 * ignore a lot of error handling - you can just let JUnit take care of
	 * reporting any Exceptions or problems for you.
	 */
	@Test
	public void testSimple() throws Exception {
		driver.get(p._("/"));
		assertNotNull(driver.getPageSource());
	}

	/**
	 * A slightly more complex web test. This test was generated by simply
	 * pasting in the code directly from the SauceLabs version of the Selenium
	 * IDE.
	 */
	@Test
	public void requiresSauceConnect() throws Exception {

		driver.get(p._("/"));
		assertTrue(driver.getPageSource().contains("Hello"));
	}
}
