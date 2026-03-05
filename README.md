# Amazon Mobile App - Search Functionality Test Automation

Appium test automation project for the **Amazon Android** mobile app's search feature using **Java**, **TestNG**, and **Page Object Model (POM)**.

## Tech Stack

- Java 11
- Appium Java Client 8.6.0
- Selenium 4.14.0
- TestNG 7.10.2
- Maven

## Project Structure

```
src/
├── main/java/com/amazon/
│   ├── pages/
│   │   ├── BasePage.java
│   │   ├── HomePage.java
│   │   ├── SearchPage.java
│   │   └── SearchResultsPage.java
│   └── utils/
│       ├── DriverManager.java
│       └── TestListener.java
└── test/java/com/amazon/tests/
    ├── BaseTest.java
    └── PositiveSearchTest.java
```

## Test Cases (Positive Search)

| # | Test | Description |
|---|------|-------------|
| 1 | Single Keyword | Search "laptop" and verify relevant results |
| 2 | Multiple Keywords | Search "wireless headphones" and verify match |
| 3 | Exact Match | Search "project management book" |
| 4 | Special Characters | Search "C++ programming" |
| 5 | Numeric Values | Search "iPhone 15" |
| 6 | Brand Name | Search "Samsung" and verify brand results |
| 7 | Case Insensitive | Search "LaPToP" and verify results still return |
| 8 | Product Category | Search "running shoes" |
| 9 | Short Query | Search "TV" (2 characters) |
| 10 | Price Visibility | Search and verify prices are displayed in results |

## Prerequisites

- Java JDK 11+
- Maven
- Appium Server 2.x (`npm install -g appium`)
- UiAutomator2 driver (`appium driver install uiautomator2`)
- Android emulator or real device with Amazon app installed

## Setup & Run

```bash
# Clone
git clone https://github.com/AsliAsln/amazon-search-tests.git
cd amazon-search-tests

# Update device info in src/test/resources/config.properties

# Install dependencies
mvn clean install -DskipTests

# Start Appium server
appium --port 4723

# Run tests
mvn test
```

## Configuration

Edit `src/test/resources/config.properties`:

```properties
appium.server.url=http://127.0.0.1:4723
platform.name=Android
device.name=YOUR_DEVICE_NAME
platform.version=14
app.package=com.amazon.mShop.android.shopping
app.activity=com.amazon.mShop.home.HomeActivity
```
