package com.amazon.tests;

import com.amazon.pages.HomePage;
import com.amazon.pages.SearchPage;
import com.amazon.utils.DriverManager;
import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;


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
        try {
            driver.activateApp("com.amazon.mShop.android.shopping");
            Thread.sleep(2000);

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


    protected SearchPage navigateToSearch() {
        return homePage.tapSearchBar();
    }
}