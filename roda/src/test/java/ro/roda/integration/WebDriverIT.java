package ro.roda.integration;

import java.io.File;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverIT {

	private static WebDriver driver;

	private static final int defaultTimeout = 10;

	private static final String homepageUrl = "http://localhost:8080/roda/";

	private static final String firefoxPath = "/opt/local/lib/firefox-x11/firefox-bin";

	private static final String defaultDisplay = ":20";

	private static final String screenshotFilename = "target/screenshot.png";

	@BeforeClass
	public static void beforeClass() {

		// use a specific Firefox binary
		FirefoxBinary fb = new FirefoxBinary(new File(firefoxPath));

		// set the DISPLAY to be used by Firefox when testing (if Xvfb is
		// running)
		fb.setEnvironmentProperty("DISPLAY", defaultDisplay);

		driver = new FirefoxDriver(fb, null);

		// set default timeout
		driver.manage().timeouts()
				.implicitlyWait(defaultTimeout, TimeUnit.SECONDS);
	}

	@AfterClass
	public static void afterClass() {
		driver.close();
	}

	@Test
	public void screenshot() throws Exception {
		driver.get(homepageUrl);
		File scrFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(screenshotFilename));
	}

	@Test
	public void homepage() throws Exception {
		driver.get(homepageUrl);
		Assert.assertEquals("Welcome to RODA", driver.getTitle());
		Assert.assertNotNull(driver.findElement(By.id("header")));
		Assert.assertNotNull(driver.findElement(By.id("language")));
		Assert.assertNotNull(driver.findElement(By.id("menu")));
		// Assert.assertNotNull(driver.findElement(By.id("meniuri")));
	}

	@Test
	public void loginAdmin() throws Exception {
		driver.get(homepageUrl);
		WebElement loginLink = driver.findElement(By.partialLinkText("Login"));
		Assert.assertNotNull(loginLink);
		loginLink.click();
		WebElement submitButton = (new WebDriverWait(driver, defaultTimeout))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id("proceed")));
		WebElement usernameInput = driver.findElement(By.id("j_username"));
		WebElement passwordInput = driver.findElement(By.id("j_password"));
		Assert.assertNotNull(usernameInput);
		Assert.assertNotNull(passwordInput);
		usernameInput.sendKeys("admin");
		passwordInput.sendKeys("admin");
		submitButton.click();
		WebElement logoutLink = (new WebDriverWait(driver, defaultTimeout))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.partialLinkText("Logout")));
		Assert.assertNotNull(logoutLink);
		logoutLink.click();
		loginLink = (new WebDriverWait(driver, defaultTimeout))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.partialLinkText("Login")));
		Assert.assertNotNull(loginLink);
		Assert.assertEquals(homepageUrl, driver.getCurrentUrl());
	}
}