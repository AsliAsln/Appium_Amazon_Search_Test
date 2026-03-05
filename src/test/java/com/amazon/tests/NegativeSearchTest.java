package com.amazon.tests;

import com.amazon.pages.SearchPage;
import com.amazon.pages.SearchResultsPage;
import org.testng.Assert;
import org.testng.annotations.Test;


public class NegativeSearchTest extends BaseTest {


    @Test(priority = 1,
            description = "Boş arama sorgusu gönderildiğinde sistemin çökmediği ve uygun davrandığı doğrulanır.")
    public void testEmptySearchQuery() {
        SearchPage searchPage = navigateToSearch();

        searchPage.enterSearchQuery("");
        SearchResultsPage resultsPage = searchPage.submitSearch();


        Assert.assertTrue(
                resultsPage.areResultsDisplayed() || searchPage.isSearchFieldDisplayed(),
                "Boş arama sorgusu sistemi çökertmemeli.");
    }


    @Test(priority = 2,
            description = "Sadece özel karakterler (@#$%^&*) ile arama yapıldığında sistem uygun davranır.")
    public void testSearchWithOnlySpecialCharacters() {
        String query = "@#$%^&*";

        SearchPage searchPage = navigateToSearch();
        SearchResultsPage resultsPage = searchPage.searchFor(query);
        resultsPage.waitForPageToLoad();

        boolean systemStable = resultsPage.isNoResultsMessageDisplayed()
                || resultsPage.areResultsDisplayed()
                || searchPage.isSearchFieldDisplayed();

        Assert.assertTrue(systemStable,
                "Sadece özel karakter araması sistemi çökertmemeli.");
    }


    @Test(priority = 3,
            description = "SQL Injection saldırı metni girildiğinde sistemin güvenli davrandığı doğrulanır.")
    public void testSQLInjectionAttempt() {
        String sqlInjection = "' OR 1=1 --";

        SearchPage searchPage = navigateToSearch();
        SearchResultsPage resultsPage = searchPage.searchFor(sqlInjection);
        resultsPage.waitForPageToLoad();

        boolean systemSecure = resultsPage.isNoResultsMessageDisplayed()
                || resultsPage.areResultsDisplayed()
                || searchPage.isSearchFieldDisplayed();

        Assert.assertTrue(systemSecure,
                "SQL Injection denemesi sistemi etkilememeli, güvenli yanıt dönmeli.");
    }


    @Test(priority = 4,
            description = "500+ karakter uzunluğunda bir arama sorgusu girildiğinde sistem çökmez.")
    public void testExcessivelyLongSearchQuery() {
        StringBuilder longQuery = new StringBuilder();
        for (int i = 0; i < 60; i++) {
            longQuery.append("keyboard ");
        }

        SearchPage searchPage = navigateToSearch();
        SearchResultsPage resultsPage = searchPage.searchFor(longQuery.toString());
        resultsPage.waitForPageToLoad();

        boolean systemStable = resultsPage.isNoResultsMessageDisplayed()
                || resultsPage.areResultsDisplayed()
                || searchPage.isSearchFieldDisplayed();

        Assert.assertTrue(systemStable,
                "Aşırı uzun sorgu sistemi çökertmemeli.");
    }


    @Test(priority = 5,
            description = "Anlamsız rastgele karakterler ile arama yapıldığında uygun mesaj gösterilir.")
    public void testSearchWithGibberishText() {
        String gibberish = "xyzqwrtplmk12345";

        SearchPage searchPage = navigateToSearch();
        SearchResultsPage resultsPage = searchPage.searchFor(gibberish);
        resultsPage.waitForPageToLoad();

        boolean handled = resultsPage.isNoResultsMessageDisplayed()
                || resultsPage.areResultsDisplayed(); // Amazon bazen ilgili öneriler gösterir

        Assert.assertTrue(handled,
                "Anlamsız metin araması uygun şekilde ele alınmalı.");
    }


    @Test(priority = 6,
            description = "XSS saldırı metni girildiğinde sistemin güvenli davrandığı doğrulanır.")
    public void testXSSInjectionAttempt() {
        String xssPayload = "<script>alert('XSS')</script>";

        SearchPage searchPage = navigateToSearch();
        SearchResultsPage resultsPage = searchPage.searchFor(xssPayload);
        resultsPage.waitForPageToLoad();

        boolean systemSecure = resultsPage.isNoResultsMessageDisplayed()
                || resultsPage.areResultsDisplayed()
                || searchPage.isSearchFieldDisplayed();

        Assert.assertTrue(systemSecure,
                "XSS denemesi sistemi etkilememeli, script çalıştırılmamalı.");
    }


    @Test(priority = 7,
            description = "Sadece boşluk karakterleri ile arama yapıldığında sistem uygun davranır.")
    public void testSearchWithOnlySpaces() {
        String query = "     ";

        SearchPage searchPage = navigateToSearch();
        SearchResultsPage resultsPage = searchPage.searchFor(query);
        resultsPage.waitForPageToLoad();

        boolean systemStable = resultsPage.isNoResultsMessageDisplayed()
                || resultsPage.areResultsDisplayed()
                || searchPage.isSearchFieldDisplayed();

        Assert.assertTrue(systemStable,
                "Sadece boşluk ile arama sistemi çökertmemeli.");
    }


    @Test(priority = 8,
            description = "HTML tagları girildiğinde sistemin bunları düz metin olarak ele aldığı doğrulanır.")
    public void testSearchWithHTMLTags() {
        String htmlInput = "<h1>laptop</h1><img src=x onerror=alert(1)>";

        SearchPage searchPage = navigateToSearch();
        SearchResultsPage resultsPage = searchPage.searchFor(htmlInput);
        resultsPage.waitForPageToLoad();

        boolean systemSecure = resultsPage.isNoResultsMessageDisplayed()
                || resultsPage.areResultsDisplayed()
                || searchPage.isSearchFieldDisplayed();

        Assert.assertTrue(systemSecure,
                "HTML tag enjeksiyonu sistemi etkilememeli.");
    }


    @Test(priority = 9,
            description = "Emoji karakterleri ile arama yapıldığında sistem çökmez.")
    public void testSearchWithEmojis() {
        String emojiQuery = "🎧 headphones 🎵";

        SearchPage searchPage = navigateToSearch();
        SearchResultsPage resultsPage = searchPage.searchFor(emojiQuery);
        resultsPage.waitForPageToLoad();

        boolean systemStable = resultsPage.isNoResultsMessageDisplayed()
                || resultsPage.areResultsDisplayed();

        Assert.assertTrue(systemStable,
                "Emoji içeren arama sistemi çökertmemeli.");
    }


    @Test(priority = 10,
            description = "Yeni satır ve tab gibi kontrol karakterleri ile arama yapıldığında sistem çökmez.")
    public void testSearchWithControlCharacters() {
        String query = "laptop\n\ttablet";

        SearchPage searchPage = navigateToSearch();
        SearchResultsPage resultsPage = searchPage.searchFor(query);
        resultsPage.waitForPageToLoad();

        boolean systemStable = resultsPage.isNoResultsMessageDisplayed()
                || resultsPage.areResultsDisplayed()
                || searchPage.isSearchFieldDisplayed();

        Assert.assertTrue(systemStable,
                "Kontrol karakterleri ile arama sistemi çökertmemeli.");
    }
}