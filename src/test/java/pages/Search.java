package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

public class Search extends PageBase {

	public Search(WebDriver driver) {
		super(driver);
	}

	public void searchText(String searchField, String text) throws Exception {
		WebElement searchBar = Driver.findElement(By.id(searchField)); //find field by ID
		searchBar.click(); // click element
		searchBar.sendKeys(text); //write desired text
		searchBar.sendKeys(Keys.ENTER); //keyboard enter
	}
}
