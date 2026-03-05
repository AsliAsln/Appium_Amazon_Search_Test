package com.amazon.tests;

import com.amazon.pages.HomePage;
import com.amazon.pages.SearchPage;
import com.amazon.utils.DriverManager;
import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

/**
 * Tüm test sınıflarının miras aldığı temel test sınıfı.
 * Driver başlatma/kapatma ve ortak setup işlemlerini yönetir.
 */
public abstract class BaseTest {

    protected AndroidDriver driver;
    protected HomePage homePage;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        driver = DriverManager.getDriver();
        homePage = new HomePage();
    }

    @BeforeMethod(alwaysRun = true)
    public void resetToHome() {
        // Her test öncesi ana sayfaya dön
        try {
            // Uygulamayı yeniden başlat (temiz durum)
            driver.activateApp("com.amazon.mShop.android.shopping");
            Thread.sleep(2000);

            // Eğer ana sayfada değilsek, geri tuşuyla dönmeyi dene
            if (!homePage.isHomePageLoaded()) {
                driver.navigate().back();
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.out.println("Ana sayfaya dönüş sırasında hata: " + e.getMessage());
        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        DriverManager.quitDriver();
    }

    /**
     * Ana sayfadan arama sayfasına geçiş yapar (ortak helper).
     */
    protected SearchPage navigateToSearch() {
        return homePage.tapSearchBar();
    }
}