/**
 * This is a canonical example of a Selenium web test executing against a local
 * build.
 */
package com.dynacrongroup.sample.local;

import com.dynacrongroup.webtest.WebDriverBase;
import com.dynacrongroup.webtest.parameter.ParallelRunner;
import com.dynacrongroup.webtest.parameter.ParameterCombination;
import com.dynacrongroup.webtest.util.Path;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

/**
 * This test requires an active Sauce Connect tunnel to run in Sauce Labs.
 * It tests against the locally deployed web-app, which isn't published
 * to the world - it's only visible on the machine that runs the test.
 *
 * This RunWith annotation provides the hook into the Selenium parallel
 *  execution (the stuff that runs multiple Selenium browsers in parallel
 *  with SauceLabs
 */
@RunWith(ParallelRunner.class)
public class SeleniumIT extends WebDriverBase {

	/** The Path object picks up standard naming rules for finding the target. */
	Path p = new Path();

	/** This constructor is required to support the parallel test execution. */
	public SeleniumIT( ParameterCombination parameterCombination) {
		super(parameterCombination);
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
	public void requiresSauceConnect() throws Exception {
		driver.get(p._("/"));
		assertTrue(driver.getPageSource().contains("Hello"));
	}
}
