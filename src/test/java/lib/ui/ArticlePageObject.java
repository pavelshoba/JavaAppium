package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class ArticlePageObject extends MainPageObject
{
    protected static String
        TITLE,
        FOOTER_ELEMENT,
        OPTION_BUTTON,
        OPTIONS_ADD_TO_MY_LIST_BUTTON,
        OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
        ADD_TO_MY_LIST_OVERLAY,
        MY_LIST_NAME_INPUT,
        MY_LIST_OK_BUTTON,
        CLOSE_ARTICLE_BUTTON,
        CREATED_LIST_BUTTON,
        CLOSE_SYNC_WINDOW_BUTTON,
        IMAGE_OF_ARTICLE,
        CLOSE_IMAGE_BUTTON,
        NAME_OF_IMAGE;


    public ArticlePageObject(RemoteWebDriver driver)
    {
        super(driver);
    }

    public WebElement waitForTitleElement()
    {
        return this.waitForElementPresent(TITLE, "Cannot find article title on page!",  15);
    }

    public String getArticleTitle()
    {
        WebElement title_element = waitForTitleElement();
        if (Platform.getInstance().isAndroid()) {
            return title_element.getAttribute("text");
        } else if (Platform.getInstance().isIOS()) {
            return title_element.getAttribute("name");
        } else {
            return title_element.getText();
        }
    }

    public void swipeToFooter()
    {
        if (Platform.getInstance().isAndroid()) {
            this.swipeUpToFindElement(
                    FOOTER_ELEMENT,
                    "Cannot find the end of article",
                    40
            );
        } else if (Platform.getInstance().isIOS()) {
            this.swipeUpTillElementAppear(FOOTER_ELEMENT,
                    "Cannot find the end of article",
                    40);
        } else {
            this.scrollWebPageTillElementNotVisible(
                    FOOTER_ELEMENT,
                    "Cannot find the end of article",
                    40
            );
        }
    }

    public void addArticleToMyList(String name_of_folder)
    {
        this.waitForElementAndClick(
                OPTION_BUTTON,
                "Cannot find button to open article options",
                15
        );

        this.waitForElementPresent(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                15
        );

        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot click option to add article to reading list",
                5
        );

        this.waitForElementAndClick(
                ADD_TO_MY_LIST_OVERLAY,
                "Cannot find 'Got it' tip overlay",
                5
        );

        this.waitForElementAndClear(
                MY_LIST_NAME_INPUT,
                "Cannot find input to set name of articles folder",
                5
        );

        this.waitForElementAndSendKeys(
                MY_LIST_NAME_INPUT,
                name_of_folder,
                "Cannot put text into articles folder input",
                5
        );

        this.waitForElementAndClick(
                MY_LIST_OK_BUTTON,
                "Cannot press 'OK' button",
                5
        );
    }

    public void addSecondArticleToMyList(String name_of_folder)
    {
        this.waitForElementAndClick(
                OPTION_BUTTON,
                "Cannot find button to open article options",
                15
        );

        this.waitForElementPresent(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                15
        );

        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot click option to add article to reading list",
                5
        );

        this.waitForElementAndClick(
                CREATED_LIST_BUTTON,
                "Cannot find created list",
                5
        );
    }

    public void addArticlesToMySaved()
    {
        if (Platform.getInstance().isMw()) {
            this.removeArticleFromSavedIfItAdded();
        }
        this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON, "Cannot find option to add article to reading list", 5);
    }

    public void removeArticleFromSavedIfItAdded()
    {
        if (this.isElementPresent(OPTIONS_REMOVE_FROM_MY_LIST_BUTTON)) {
            this.waitForElementAndClick(
                    OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
                    "Cannot click button to remove an article from saved",
                    1
            );
            this.waitForElementPresent(
                    OPTIONS_ADD_TO_MY_LIST_BUTTON,
                    "Cannot find button to add an article to saved list after removing it from this list before"
            );
        }
    }

    public void closeArticle()
    {
        if (Platform.getInstance().isIOS() || Platform.getInstance().isAndroid()){
            this.waitForElementAndClick(
                    CLOSE_ARTICLE_BUTTON,
                    "Cannot close article, cannot find X link",
                    15
            );
        } else {
            System.out.println("Method closeArticle() do nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    public void closeSyncWindow()
    {
        this.waitForElementAndClick(
                CLOSE_SYNC_WINDOW_BUTTON,
                "Cannot close sync window",
                5
        );
    }

    public void openImageOfArticle()
    {
        this.waitForElementAndClick(
                IMAGE_OF_ARTICLE,
                "Cannot open image of article",
                5
        );
    }

    public WebElement waitForNameOfImage()
    {
        return this.waitForElementPresent(NAME_OF_IMAGE, "Cannot find name of image",  15);
    }

    public String getNameOfImage()
    {
        WebElement image_element = waitForNameOfImage();
        if (Platform.getInstance().isAndroid()) {
            return image_element.getAttribute("text");
        } else if (Platform.getInstance().isIOS()){
            return image_element.getAttribute("name");
        } else {
            return image_element.getText();
        }
    }

    public void closeImageOfArticle()
    {
        this.waitForElementAndClick(
                CLOSE_IMAGE_BUTTON,
                "Cannot close image",
                15
        );
    }
}
