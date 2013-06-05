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
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverIT {

	private static WebDriver driver;

	private static final int defaultWait = 10;

	private static final String homepageUrl = "http://localhost:8080/roda/";

	@BeforeClass
	public static void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts()
				.implicitlyWait(defaultWait, TimeUnit.SECONDS);
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
		FileUtils.copyFile(scrFile, new File("target/screenshot.png"));
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
		WebElement submitButton = (new WebDriverWait(driver, defaultWait))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.id("proceed")));
		WebElement usernameInput = driver.findElement(By.id("j_username"));
		WebElement passwordInput = driver.findElement(By.id("j_password"));
		Assert.assertNotNull(usernameInput);
		Assert.assertNotNull(passwordInput);
		usernameInput.sendKeys("admin");
		passwordInput.sendKeys("admin");
		submitButton.click();
		WebElement logoutLink = (new WebDriverWait(driver, defaultWait))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.partialLinkText("Logout")));
		Assert.assertNotNull(logoutLink);
		logoutLink.click();
		loginLink = (new WebDriverWait(driver, defaultWait))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.partialLinkText("Login")));
		Assert.assertNotNull(loginLink);
		Assert.assertEquals(homepageUrl, driver.getCurrentUrl());
	}
}