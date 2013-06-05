package ro.roda.integration;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.thoughtworks.selenium.DefaultSelenium;

public class SeleniumServerIT {

	private static final int seleniumServerPort = 4444;

	private static final String seleniumServerHost = "localhost";

	private static final String browserOptions = "*firefox /opt/local/lib/firefox-x11/firefox-bin";

	private static final String homepageUrl = "http://localhost:8080/roda/";

	private static DefaultSelenium s;

	@BeforeClass
	public static void beforeClass() {
		s = new DefaultSelenium(seleniumServerHost, seleniumServerPort,
				browserOptions, homepageUrl);
		s.start();
	}

	@AfterClass
	public static void afterClass() {
		s.stop();
	}

	@Test
	public void homepage() throws Exception {
		s.open(homepageUrl);
		Assert.assertTrue(s.isTextPresent("RODA"));
		Assert.assertTrue("Welcome to RODA".equals(s.getTitle()));
	}

}