package com.amazon.utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;


public class DriverManager {

    private static AndroidDriver driver;
    private static Properties properties;
    private static WebDriverWait wait;

    private DriverManager() {
        // Singleton - dışarıdan instance oluşturulmasını engelle
    }


    public static Properties loadProperties() {
        if (properties == null) {
            properties = new Properties();
            try (FileInputStream fis = new FileInputStream("src/test/resources/config.properties")) {
                properties.load(fis);
            } catch (IOException e) {
                throw new RuntimeException("config.properties dosyası okunamadı: " + e.getMessage());
            }
        }
        return properties;
    }


    public static AndroidDriver getDriver() {
        if (driver == null) {
            Properties props = loadProperties();

            UiAutomator2Options options = new UiAutomator2Options()
                    .setPlatformName(props.getProperty("platform.name"))
                    .setDeviceName(props.getProperty("device.name"))
                    .setPlatformVersion(props.getProperty("platform.version"))
                    .setAutomationName(props.getProperty("automation.name"))
                    .setAppPackage(props.getProperty("app.package"))
                    .setAppActivity(props.getProperty("app.activity"))
                    .setNoReset(true)
                    .autoGrantPermissions();

            try {
                driver = new AndroidDriver(
                        new URL(props.getProperty("appium.server.url")),
                        options
                );
                driver.manage().timeouts().implicitlyWait(
                        Duration.ofSeconds(Long.parseLong(props.getProperty("implicit.wait")))
                );
            } catch (Exception e) {
                throw new RuntimeException("Appium driver başlatılamadı: " + e.getMessage());
            }
        }
        return driver;
    }

    public static WebDriverWait getWait() {
        if (wait == null) {
            Properties props = loadProperties();
            wait = new WebDriverWait(
                    getDriver(),
                    Duration.ofSeconds(Long.parseLong(props.getProperty("explicit.wait")))
            );
        }
        return wait;
    }


    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
            wait = null;
        }
    }
}