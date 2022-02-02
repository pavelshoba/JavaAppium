package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

@Epic("Test to save and delete article from my list")
public class MyListsTests extends CoreTestCase
{
    private static final String name_of_folder = "Learning programming";
    private static final String
            login = "Pavel.shoba",
            password = "ntktajy2536660";

    @Test
    @Features(value = {@Feature(value="Search"),@Feature(value="Article"),@Feature(value="MyList")})
    @DisplayName("Check saving and deleting article in my list")
    @Description("We search an article and open it, add the article in my list, check saving and delete it.")
    @Step("Starting test testSaveFirstArticleToMyList")
    @Severity(value = SeverityLevel.CRITICAL)
    public void testSaveFirstArticleToMyList() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        String article_title = ArticlePageObject.getArticleTitle();

        if(Platform.getInstance().isAndroid()){
            ArticlePageObject.addArticleToMyList(name_of_folder);
        } else if (Platform.getInstance().isIOS()){
            ArticlePageObject.closeSyncWindow();
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }
        if (Platform.getInstance().isMw()){
            AuthorizationPageObject Auth = new AuthorizationPageObject(driver);
            Auth.clickAuthButton();
            Auth.enterLoginData(login, password);
            Auth.submitForm();

            ArticlePageObject.waitForTitleElement();

            Assert.assertEquals("We are not on the same page after login",
                    article_title,
                    ArticlePageObject.getArticleTitle()
            );

            ArticlePageObject.addArticlesToMySaved();
        }

        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.openNavigation();
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()){
            MyListsPageObject.openFolderByName(name_of_folder);
        }

        MyListsPageObject.swipeByArticleToDelete(article_title);
    }

    @Test
    @Features(value = {@Feature(value="Search"),@Feature(value="Article"),@Feature(value="MyList")})
    @DisplayName("Check saving 2 articles and delete one")
    @Description("We search 2 articles, adding these articles to me list, visit list and delete one article, after that check remaining article")
    @Step("Starting test testSaveTwoArticlesToMyListAndDeleteOne")
    @Severity(value = SeverityLevel.NORMAL)
    public void testSaveTwoArticlesToMyListAndDeleteOne()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        String article_title = ArticlePageObject.getArticleTitle();

        if(Platform.getInstance().isAndroid()){
            ArticlePageObject.addArticleToMyList(name_of_folder);
        } else if (Platform.getInstance().isIOS()){
            ArticlePageObject.closeSyncWindow();
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }
        if (Platform.getInstance().isMw()){
            AuthorizationPageObject Auth = new AuthorizationPageObject(driver);
            Auth.clickAuthButton();
            Auth.enterLoginData(login, password);
            Auth.submitForm();

            ArticlePageObject.waitForTitleElement();

            Assert.assertEquals("We are not on the same page after login",
                    article_title,
                    ArticlePageObject.getArticleTitle()
            );

            ArticlePageObject.addArticlesToMySaved();
        }
        if ((Platform.getInstance().isAndroid()) || (Platform.getInstance().isIOS()))
        {
        ArticlePageObject.closeArticle();
        SearchPageObject.initSearchInput();
        }
        if(Platform.getInstance().isIOS()){
            SearchPageObject.clearSearchField();
        }
        String article_two = "Python (programming language)";
        if (Platform.getInstance().isMw()) {SearchPageObject.initSearchInput();}
        SearchPageObject.typeSearchLine(article_two);
        SearchPageObject.clickByArticleWithSubstring("General-purpose programming language");
        if(Platform.getInstance().isAndroid()){
            ArticlePageObject.addSecondArticleToMyList(name_of_folder);
        } else {
            ArticlePageObject.addArticlesToMySaved();
        }
        if((Platform.getInstance().isAndroid()) || Platform.getInstance().isIOS()) {ArticlePageObject.openImageOfArticle();}
        String name_of_image = ArticlePageObject.getNameOfImage();
        if((Platform.getInstance().isAndroid()) || Platform.getInstance().isIOS()) {ArticlePageObject.closeImageOfArticle();}
        if(Platform.getInstance().isAndroid()) {
            ArticlePageObject.waitForTitleElement();
        }
        ArticlePageObject.closeArticle();
        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.openNavigation();
        NavigationUI.clickMyLists();
        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);
        if (Platform.getInstance().isAndroid()){
            MyListsPageObject.openFolderByName(name_of_folder);
        }
        MyListsPageObject.swipeByArticleToDelete(article_title);
        MyListsPageObject.waitForArticleToAppearByTitle(article_two);
        MyListsPageObject.openSavedArticle();
        if((Platform.getInstance().isAndroid()) || Platform.getInstance().isIOS()) {ArticlePageObject.openImageOfArticle();}
        String name_image_locator = ArticlePageObject.getNameOfImage();
        Assert.assertEquals(
                "It's incorrect article",
                name_of_image,
                name_image_locator
        );
    }
}
