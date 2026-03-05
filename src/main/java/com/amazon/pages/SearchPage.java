package com.amazon.pages;

import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;


public class SearchPage extends BasePage {

    // ─ Locators ─

    private final By searchInputField = By.id("com.amazon.mShop.android.shopping:id/rs_search_src_text");

    private final By clearSearchButton = By.id("com.amazon.mShop.android.shopping:id/rs_search_clear_btn");

    private final By backButton = By.xpath("//android.widget.ImageButton[@content-desc='Back' or @content-desc='Geri']");

    private final By autocompleteSuggestions = By.id("com.amazon.mShop.android.shopping:id/iss_search_dropdown_item_text");

    private final By suggestionRows = By.xpath("//android.widget.ListView//android.widget.RelativeLayout");

    private final By voiceSearchButton = By.id("com.amazon.mShop.android.shopping:id/chrome_voice_search_button");

    private final By cameraSearchButton = By.id("com.amazon.mShop.android.shopping:id/chrome_camera_search_button");

    // ─Actions ─


    public SearchPage enterSearchQuery(String query) {
        type(searchInputField, query);
        return this;
    }


    public SearchResultsPage submitSearch() {
        driver.pressKey(new KeyEvent(AndroidKey.ENTER));
        return new SearchResultsPage();
    }


    public SearchResultsPage searchFor(String query) {
        enterSearchQuery(query);
        return submitSearch();
    }


    public String getSearchFieldText() {
        return waitForVisible(searchInputField).getText();
    }


    public boolean isSearchFieldEmpty() {
        String text = getSearchFieldText();
        return text == null || text.isEmpty() || text.equals("Search Amazon");
    }


    public boolean isSearchFieldDisplayed() {
        return isElementDisplayed(searchInputField);
    }


    public SearchPage clearSearch() {
        if (isElementDisplayed(clearSearchButton)) {
            click(clearSearchButton);
        }
        return this;
    }


    public HomePage pressBack() {
        click(backButton);
        return new HomePage();
    }

    // ─── Autocomplete İşlemleri ────────────────────────────────


    public boolean areAutocompleteSuggestionsDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(autocompleteSuggestions));
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public List<String> getAutocompleteSuggestions() {
        List<WebElement> suggestions = findElements(autocompleteSuggestions);
        return suggestions.stream()
                .map(WebElement::getText)
                .filter(text -> text != null && !text.isEmpty())
                .collect(Collectors.toList());
    }


    public SearchResultsPage selectFirstSuggestion() {
        List<WebElement> suggestions = findElements(autocompleteSuggestions);
        if (!suggestions.isEmpty()) {
            suggestions.get(0).click();
        }
        return new SearchResultsPage();
    }


    public SearchResultsPage selectSuggestionContaining(String text) {
        List<WebElement> suggestions = findElements(autocompleteSuggestions);
        for (WebElement suggestion : suggestions) {
            if (suggestion.getText().toLowerCase().contains(text.toLowerCase())) {
                suggestion.click();
                return new SearchResultsPage();
            }
        }
        throw new RuntimeException("'" + text + "' içeren autocomplete önerisi bulunamadı.");
    }


    public int getAutocompleteSuggestionCount() {
        return findElements(autocompleteSuggestions).size();
    }



    public boolean isVoiceSearchButtonDisplayed() {
        return isElementDisplayed(voiceSearchButton);
    }


    public boolean isCameraSearchButtonDisplayed() {
        return isElementDisplayed(cameraSearchButton);
    }
}