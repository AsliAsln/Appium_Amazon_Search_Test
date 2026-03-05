package com.amazon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class HomePage extends BasePage {

    // ─ Locators ─

    private final By searchBar = By.id("com.amazon.mShop.android.shopping:id/chrome_search_hint_view");

    private final By searchBarAlt = By.xpath("//android.widget.EditText[@resource-id='com.amazon.mShop.android.shopping:id/rs_search_src_text']");

    private final By homeTab = By.xpath("//android.widget.ImageView[@content-desc='Home']");

    // ─ Actions ─


    public SearchPage tapSearchBar() {
        click(searchBar);
        return new SearchPage();
    }


    public boolean isHomePageLoaded() {
        return isElementDisplayed(searchBar);
    }


    public String getSearchBarHintText() {
        WebElement element = waitForVisible(searchBar);
        // Amazon uygulamasında hint text content-desc veya text olarak gelebilir
        String text = element.getText();
        if (text == null || text.isEmpty()) {
            text = element.getAttribute("content-desc");
        }
        return text != null ? text : "";
    }


    public HomePage navigateToHome() {
        click(homeTab);
        return this;
    }
}