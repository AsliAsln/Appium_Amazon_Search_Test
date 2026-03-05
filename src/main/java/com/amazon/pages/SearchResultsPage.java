package com.amazon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Amazon mobil uygulaması Arama Sonuçları Sayfası Page Object.
 * Arama yapıldıktan sonra gösterilen ürün listesi sayfası.
 */
public class SearchResultsPage extends BasePage {

    // ─ Locators ─

    private final By resultItems = By.xpath(
            "//android.widget.ListView//android.view.View" +
                    " | //androidx.recyclerview.widget.RecyclerView//android.view.ViewGroup"
    );

    private final By productTitles = By.xpath(
            "//android.widget.TextView[contains(@resource-id, 'item_title')]"
    );

    private final By productPrices = By.xpath(
            "//android.widget.TextView[contains(@resource-id, 'item_price')]" +
                    " | //android.widget.TextView[contains(@text, '$')]" +
                    " | //android.widget.TextView[contains(@text, '₺')]"
    );

    private final By noResultsMessage = By.xpath(
            "//*[contains(@text, 'No results') or contains(@text, 'sonuç bulunamadı')" +
                    " or contains(@text, 'did not match') or contains(@text, 'eşleşmedi')]"
    );

    private final By resultCountText = By.xpath(
            "//*[contains(@text, 'results') or contains(@text, 'sonuç')]"
    );

    private final By filterButton = By.xpath(
            "//*[@content-desc='Filters' or @content-desc='Filtrele' or @text='Filter']"
    );

    private final By sortButton = By.xpath(
            "//*[@content-desc='Sort by' or @text='Sort by' or @text='Sırala']"
    );

    private final By searchBarOnResults = By.id(
            "com.amazon.mShop.android.shopping:id/chrome_search_hint_view"
    );

    private final By departmentFilters = By.xpath(
            "//android.widget.Button[contains(@text, 'Department')]" +
                    " | //android.widget.TextView[contains(@text, 'Department')]"
    );

    private final By sponsoredLabel = By.xpath(
            "//*[contains(@text, 'Sponsored') or contains(@text, 'Sponsorlu')]"
    );




    public boolean areResultsDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(productTitles));
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public boolean isNoResultsMessageDisplayed() {
        return isElementDisplayed(noResultsMessage);
    }


    public List<String> getProductTitles() {
        List<WebElement> titles = findElements(productTitles);
        return titles.stream()
                .map(WebElement::getText)
                .filter(text -> text != null && !text.isEmpty())
                .collect(Collectors.toList());
    }


    public int getVisibleResultCount() {
        return findElements(productTitles).size();
    }


    public boolean doResultsContainKeyword(String keyword) {
        List<String> titles = getProductTitles();
        String lowerKeyword = keyword.toLowerCase();
        return titles.stream()
                .anyMatch(title -> title.toLowerCase().contains(lowerKeyword));
    }


    public boolean doResultsContainAnyKeyword(String... keywords) {
        List<String> titles = getProductTitles();
        for (String title : titles) {
            String lowerTitle = title.toLowerCase();
            for (String keyword : keywords) {
                if (lowerTitle.contains(keyword.toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }


    public List<String> getProductPrices() {
        List<WebElement> prices = findElements(productPrices);
        return prices.stream()
                .map(WebElement::getText)
                .filter(text -> text != null && !text.isEmpty())
                .collect(Collectors.toList());
    }


    public boolean arePricesDisplayed() {
        return !findElements(productPrices).isEmpty();
    }



    public boolean isFilterButtonDisplayed() {
        return isElementDisplayed(filterButton);
    }


    public boolean isSortButtonDisplayed() {
        return isElementDisplayed(sortButton);
    }


    public SearchResultsPage tapFilter() {
        click(filterButton);
        return this;
    }


    public SearchResultsPage tapSort() {
        click(sortButton);
        return this;
    }


    public SearchPage tapSearchBarForNewSearch() {
        click(searchBarOnResults);
        return new SearchPage();
    }


    public void tapFirstProduct() {
        List<WebElement> titles = findElements(productTitles);
        if (!titles.isEmpty()) {
            titles.get(0).click();
        }
    }


    public SearchResultsPage scrollDownForMore() {
        driver.executeScript("mobile: scrollGesture", java.util.Map.of(
                "left", 100,
                "top", 1000,
                "width", 800,
                "height", 500,
                "direction", "down",
                "percent", 0.75
        ));
        return this;
    }


    public String getResultCountText() {
        try {
            return getText(resultCountText);
        } catch (Exception e) {
            return "";
        }
    }


    public boolean areSponsoredProductsDisplayed() {
        return isElementDisplayed(sponsoredLabel);
    }


    public SearchResultsPage waitForPageToLoad() {
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(productTitles),
                    ExpectedConditions.visibilityOfElementLocated(noResultsMessage)
            ));
        } catch (Exception e) {
        }
        return this;
    }
}