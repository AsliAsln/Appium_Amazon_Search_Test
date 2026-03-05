package com.amazon.pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.amazon.utils.DriverManager;

import java.time.Duration;
import java.util.List;


public abstract class BasePage {

    protected AndroidDriver driver;
    protected WebDriverWait wait;

    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = DriverManager.getWait();
    }


    protected WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }


    protected WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }


    protected boolean isElementDisplayed(By locator) {
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
            boolean displayed = driver.findElement(locator).isDisplayed();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            return displayed;
        } catch (Exception e) {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            return false;
        }
    }


    protected List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }


    protected void click(By locator) {
        waitForClickable(locator).click();
    }


    protected void type(By locator, String text) {
        WebElement element = waitForVisible(locator);
        element.clear();
        element.sendKeys(text);
    }


    protected String getText(By locator) {
        return waitForVisible(locator).getText();
    }
}