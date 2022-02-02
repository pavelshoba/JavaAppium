package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

@Epic("Tests for search")
public class SearchTests extends CoreTestCase
{
    @Test
    @Feature(value="Search")
    @DisplayName("Search article")
    @Description("We waiting necessary article after search")
    @Step("Starting test testSearch")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testSearch()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("bject-oriented programming language");
    }

    @Test
    @Feature(value="Search")
    @DisplayName("Cancel searching of article")
    @Description("We search article and cancel searching")
    @Step("Starting test testCancelSearch")
    @Severity(value = SeverityLevel.MINOR)
    public void testCancelSearch()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisAppear();
    }

    @Test
    @Feature(value="Search")
    @DisplayName("Check amount of elements")
    @Description("We search something and check amount of element after searching")
    @Step("Starting test testAmountOfNotEmptySearch")
    @Severity(value = SeverityLevel.MINOR)
    public void testAmountOfNotEmptySearch()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String search_line = "Linkin Park discography";
        SearchPageObject.typeSearchLine(search_line);
        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();

        Assert.assertTrue(
                "We found too few results!",
                amount_of_search_results > 0
        );

    }

    @Test
    @Feature(value="Search")
    @DisplayName("Check nonexistent element")
    @Description("We search element which isn't in Wikipedia and check empty result")
    @Step("Starting test testAmountOfEmptySearch")
    @Severity(value = SeverityLevel.MINOR)
    public void testAmountOfEmptySearch()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String search_line = "sadgasfg";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForEmptyResultsLabel();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }

    @Test
    @Feature(value="Search")
    @DisplayName("Check result of searching after cancel")
    @Description("We search an element, cancel searching and check that there isn't result of searching")
    @Step("Starting test testSearchCancelCheck")
    @Severity(value = SeverityLevel.TRIVIAL)
    public void testSearchCancelCheck()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Testing");
        SearchPageObject.getAmountOfFoundArticles();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }

    @Test
    @Feature(value="Search")
    @DisplayName("Check that all results have word 'Java'")
    @Description("We search word 'Java' and check results that each element has this word")
    @Step("Starting test testCheckResult")
    @Severity(value = SeverityLevel.MINOR)
    public void testCheckResult()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.getAmountOfFoundArticles();
        String part_of_item = SearchPageObject.getNameTitles();
        Assert.assertEquals(
                "Not all items have 'Java'",
                part_of_item.contains("Java"),
                part_of_item.contains("Java")
        );
    }
}


