package com.amazon.tests;

import com.amazon.pages.SearchPage;
import com.amazon.pages.SearchResultsPage;
import org.testng.Assert;
import org.testng.annotations.Test;


public class PositiveSearchTest extends BaseTest {


    @Test(priority = 1,
            description = "Tek bir anahtar kelime ile arama yapılır ve sonuçların ilgili olduğu doğrulanır.")
    public void testSearchWithSingleKeyword() {
        String keyword = "laptop";

        SearchPage searchPage = navigateToSearch();
        SearchResultsPage resultsPage = searchPage.searchFor(keyword);
        resultsPage.waitForPageToLoad();

        Assert.assertTrue(resultsPage.areResultsDisplayed(),
                "Arama sonuçları görüntülenmeli.");
        Assert.assertTrue(resultsPage.doResultsContainKeyword(keyword),
                "Sonuçlar '" + keyword + "' kelimesini içermeli.");
    }


    @Test(priority = 2,
            description = "Birden fazla anahtar kelime ile arama yapılır ve sonuçların eşleştiği doğrulanır.")
    public void testSearchWithMultipleKeywords() {
        String query = "wireless headphones";

        SearchPage searchPage = navigateToSearch();
        SearchResultsPage resultsPage = searchPage.searchFor(query);
        resultsPage.waitForPageToLoad();

        Assert.assertTrue(resultsPage.areResultsDisplayed(),
                "Arama sonuçları görüntülenmeli.");
        Assert.assertTrue(
                resultsPage.doResultsContainAnyKeyword("wireless", "headphones", "headphone"),
                "Sonuçlar 'wireless' veya 'headphones' kelimelerini içermeli.");
    }


    @Test(priority = 3,
            description = "Tam eşleşme sorgusu ile arama yapılır ve ilgili sonuçların döndüğü doğrulanır.")
    public void testSearchWithExactMatch() {
        String query = "project management book";

        SearchPage searchPage = navigateToSearch();
        SearchResultsPage resultsPage = searchPage.searchFor(query);
        resultsPage.waitForPageToLoad();

        Assert.assertTrue(resultsPage.areResultsDisplayed(),
                "Tam eşleşme araması sonuç döndürmeli.");
        Assert.assertTrue(
                resultsPage.doResultsContainAnyKeyword("project", "management", "book"),
                "Sonuçlar arama terimlerini içermeli.");
    }


    @Test(priority = 4,
            description = "Özel karakter içeren geçerli bir terim (C++) ile arama yapılır.")
    public void testSearchWithSpecialCharacters() {
        String query = "C++ programming";

        SearchPage searchPage = navigateToSearch();
        SearchResultsPage resultsPage = searchPage.searchFor(query);
        resultsPage.waitForPageToLoad();

        Assert.assertTrue(resultsPage.areResultsDisplayed(),
                "Özel karakter içeren geçerli arama sonuç döndürmeli.");
        Assert.assertTrue(
                resultsPage.doResultsContainAnyKeyword("C++", "programming", "c plus"),
                "Sonuçlar programlama ile ilgili olmalı.");
    }


    @Test(priority = 5,
            description = "Sayısal değer içeren bir ürün adı ile arama yapılır (iPhone 13).")
    public void testSearchWithNumericValues() {
        String query = "iPhone 15";

        SearchPage searchPage = navigateToSearch();
        SearchResultsPage resultsPage = searchPage.searchFor(query);
        resultsPage.waitForPageToLoad();

        Assert.assertTrue(resultsPage.areResultsDisplayed(),
                "Sayısal değer içeren arama sonuç döndürmeli.");
        Assert.assertTrue(
                resultsPage.doResultsContainAnyKeyword("iPhone", "iphone", "15"),
                "Sonuçlar 'iPhone' veya '15' içermeli.");
    }


    @Test(priority = 6,
            description = "Marka adı ile arama yapılır ve markayla ilgili sonuçlar döner.")
    public void testSearchWithBrandName() {
        String brand = "Samsung";

        SearchPage searchPage = navigateToSearch();
        SearchResultsPage resultsPage = searchPage.searchFor(brand);
        resultsPage.waitForPageToLoad();

        Assert.assertTrue(resultsPage.areResultsDisplayed(),
                "Marka araması sonuç döndürmeli.");
        Assert.assertTrue(resultsPage.doResultsContainKeyword(brand),
                "Sonuçlar '" + brand + "' markasını içermeli.");
    }


    @Test(priority = 7,
            description = "Karışık büyük/küçük harf ile arama yapılır ve sonuçların döndüğü doğrulanır.")
    public void testSearchCaseInsensitive() {
        String query = "LaPToP";

        SearchPage searchPage = navigateToSearch();
        SearchResultsPage resultsPage = searchPage.searchFor(query);
        resultsPage.waitForPageToLoad();

        Assert.assertTrue(resultsPage.areResultsDisplayed(),
                "Büyük/küçük harf karışık arama sonuç döndürmeli.");
        Assert.assertTrue(
                resultsPage.doResultsContainAnyKeyword("laptop", "Laptop", "LAPTOP"),
                "Harf duyarlılığı olmadan sonuçlar dönmeli.");
    }


    @Test(priority = 8,
            description = "Ürün kategorisi ile arama yapılır ve kategoriyle ilgili sonuçlar döner.")
    public void testSearchWithProductCategory() {
        String category = "running shoes";

        SearchPage searchPage = navigateToSearch();
        SearchResultsPage resultsPage = searchPage.searchFor(category);
        resultsPage.waitForPageToLoad();

        Assert.assertTrue(resultsPage.areResultsDisplayed(),
                "Kategori araması sonuç döndürmeli.");
        Assert.assertTrue(
                resultsPage.doResultsContainAnyKeyword("running", "shoes", "shoe"),
                "Sonuçlar koşu ayakkabısı ile ilgili olmalı.");
    }


    @Test(priority = 9,
            description = "Çok kısa bir sorgu (2-3 karakter) ile arama yapılır.")
    public void testSearchWithShortQuery() {
        String query = "TV";

        SearchPage searchPage = navigateToSearch();
        SearchResultsPage resultsPage = searchPage.searchFor(query);
        resultsPage.waitForPageToLoad();

        Assert.assertTrue(resultsPage.areResultsDisplayed(),
                "Kısa sorgu ile arama sonuç döndürmeli.");
    }


    @Test(priority = 10,
            description = "Arama sonuçlarında ürün fiyatlarının görüntülendiği doğrulanır.")
    public void testSearchResultsShowPrices() {
        String query = "bluetooth speaker";

        SearchPage searchPage = navigateToSearch();
        SearchResultsPage resultsPage = searchPage.searchFor(query);
        resultsPage.waitForPageToLoad();

        Assert.assertTrue(resultsPage.areResultsDisplayed(),
                "Arama sonuçları görüntülenmeli.");
        Assert.assertTrue(resultsPage.arePricesDisplayed(),
                "Ürün fiyatları arama sonuçlarında görünmeli.");
    }
}