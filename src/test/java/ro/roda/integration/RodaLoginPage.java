package ro.roda.integration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RodaLoginPage {
	private final WebDriver driver;

	public RodaLoginPage(WebDriver driver) {
		this.driver = driver;
	}

	public RodaLoginPage loginAs(String username, String password) {
		executeLogin(username, password);
		return new RodaLoginPage(driver);
	}

	private void executeLogin(String username, String password) {
		driver.findElement(By.name("j_username")).sendKeys(username);
		driver.findElement(By.name("j_password")).sendKeys(password);
		driver.findElement(By.id("proceed")).submit();
	}

	public void logout() {
		WebElement lclink = driver.findElement(By.linkText("Logout"));
		lclink.click();
	}

	public String getLogoutLink() {
		return driver.findElement(By.partialLinkText("Logout")).getText();
	}

}
