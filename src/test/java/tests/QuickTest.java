package tests;

import org.testng.annotations.Test;
import pages.Search;

public class QuickTest extends TestBase {
	
	Search objSearch;
	
	@Test
	public void SearchAndClick() throws Exception {
		objSearch = new Search(Driver);
		//Search using Field ID from env property selected
		objSearch.searchText(Environment.SearchField(), "Saucelabs");
		Thread.sleep(5000);
	}

	
}
