package ro.roda.integration;

import java.util.NoSuchElementException;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.apache.log4j.Logger;

public class RodaHomePage {
	private final WebDriver driver;  
	static Logger log = Logger.getLogger(WebDriverIT.class);
	
	
    public RodaHomePage(WebDriver driver) {  
        this.driver = driver;  
    }  
  

	public boolean goesToCRUDTable(String lname) {
		log.trace("crud test: " + lname);
		try {
			WebElement lclink = driver.findElement(By.linkText(lname));
			lclink.click();
			WebElement element = driver.findElement(By.className("dijitTitlePaneTextNode"));
			String elcontent = element.getText();
			return elcontent.contains(lname);
		} catch (NoSuchElementException e) {
			return false;
		}
	}
    
    
	
	
    public String getLogoutLink() {
		return driver.findElement(By.partialLinkText("Logout")).getText();
    }
  
}
