package org.example;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;


import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class TestSuit {
    protected static WebDriver driver;

    @BeforeMethod
    public void openBrowser() {
        System.setProperty("webdriver.chrome.driver", "src/test/java/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://demo.nopcommerce.com");
    }

    @Test
    public void UserIsAbleToRegister() {    // click on register button
        clickElement(By.className("ico-register"));
        // click on  male or female
        driver.findElement(By.id("gender-female")).click();
        // enter the firstname
        sendKeys(By.xpath("//input[@name =\"FirstName\"]"), "Nina");
        // enter the last name
        sendKeys(By.id("LastName"), "Smith");
        // select date of birthday
        selectDropdownIndex(By.name("DateOfBirthDay"), 3);
        // select date of birthMonth
        selectDropdownValue(By.name("DateOfBirthMonth"), "3");
        // select date of birthyear
        selectDropdownText(By.name("DateOfBirthYear"), "1981");
        // enter the email
        sendKeys(By.xpath("//input[@name=\"Email\"]"), "Nina" + randomDate() + "@gmail.com");
        System.out.println(randomDate());
        // enter the password
        sendKeys(By.xpath("//input[@name =\"Password\"]"), "password123");

        // enter the password to confirm
        sendKeys(By.xpath("//input[@name = 'ConfirmPassword']"), "password123");

        // click on register button

        clickElement((By.xpath("//button[@class = \"button-1 register-next-step-button\"]")));

        //Result verification
        assertEquals("Your registration completed", By.xpath("//div[@class = \"result\"]"), "registration is not working");
    }

    @Test
    public void FromHomePageToAddToCart() {  // user is on homepage and adding a product to cart
        //click on a computer
        clickElement(By.partialLinkText("Computers"));
        //click on Desktop
        clickElement(By.partialLinkText("Desktops"));
        //click on Add to cart button
        clickElement(By.xpath("//button[contains(@onclick,'/addproducttocart/catalog/1/1/1')]"));
        // select from processor dropdown
        selectDropdownValue(By.xpath("//select[@name=\"product_attribute_1\"]"), "1");
        // select RAM
        selectDropdownValue(By.xpath("//select[@name=\"product_attribute_2\"]"), "3");
        // click on HDD radioButton
        clickElement(By.xpath("//input[@name=\"product_attribute_3\"and@id=\"product_attribute_3_6\"]"));
        //click on OS radiobutton
        clickElement(By.xpath("//input[@id=\"product_attribute_4_9\"and@name =\"product_attribute_4\"]"));
        // select  software checkbox all
        clickElement(By.xpath("//*[@id=\"product_attribute_5_10\"]"));
        clickElement(By.xpath("//*[@id=\"product_attribute_5_11\"]"));
        clickElement(By.xpath("//*[@id=\"product_attribute_5_12\"]"));
        // click on  Add to cart button
        clickElement(By.xpath("//*[@id=\"add-to-cart-button-1\"]"));
        // click on shopping cart button
        clickElement(By.xpath("//*[@id=\"topcartlink\"]/a/span[1]"));
        // Assertion to verify the correct product in the cart
        assertEquals("Build your own computer", By.xpath("//a[@class=\"product-name\"]"), "This is not correct product");
        //Assertion to verify the shopping cart
        assertEquals("Shopping cart", By.linkText("Shopping cart"), "Invalid page");
    }

    @Test
    public void CheckTheCurrencyForProduct() {
        //selectEuro
        selectDropdownText(By.xpath("//select[@id=\"customerCurrency\"]"), "Euro");
        // verify if product price is in Euro
        assertEquals("€1032.00", By.xpath("//span[.=\"€1032.00\"]"), "product price is not in  Euro ");
        //select USDollar
        selectDropdownText(By.xpath("//select[@id=\"customerCurrency\"]"), "US Dollar");
        // verify if product price is in Dollar
        assertEquals("$1,200.00", By.xpath("//span[.=\"$1,200.00\"]"), "Product price is not in Dollar");
    }

    @Test
    public void ReferAProductToFriend() {
        //Registration flow
        UserIsAbleToRegister();
        // click on continue button
        clickElement(By.xpath("//a[@class=\"button-1 register-continue-button\"]"));
        //click on a computer
        clickElement(By.partialLinkText("Computers"));
        //click on Desktop
        clickElement(By.partialLinkText("Desktops"));
        // click on build your own computer
        clickElement(By.xpath("//h2/a[@href=\"/build-your-own-computer\"]"));
        //click on  email a friend
        clickElement(By.xpath("//button[@class =\"button-2 email-a-friend-button\"]"));
        // Enter a friend's email
        sendKeys(By.xpath("//input[@class=\"friend-email\"]"), "w@gmail.com");
        //Enter a personal message
        sendKeys(By.xpath("//textarea[@class=\"your-email\"]"), "This product is excellent");
        // click on sendmail button
        clickElement(By.xpath("//button[@name = \"send-email\"]"));
        // Assertion to verify for email has sent
        assertEquals("Your message has been sent.", By.xpath("//div[@class=\"result\"]"), "Email has not sent successfully");

    }

    @AfterMethod
    public void closeBrowser() {
        driver.quit();
    }


    // ---------------------------UserDefineMethods(Utills)-----------------------------//
    // click on element
    public static void clickElement(By by) {
        driver.findElement(by).click();
    }

    // To send the value
    public static void sendKeys(By by, String text) {
        driver.findElement(by).sendKeys(text);
    }

    // To get the text
    public static String getTextElement(By by) {
        return driver.findElement(by).getText();
    }

    //To use a Timestamp
    public static String randomDate() {
        Date date = new Date();
        SimpleDateFormat formater = new SimpleDateFormat("ddMMyyyyHHmmss");
        return formater.format(date);
    }

    public static void selectDropdownText(By by, String textValue) {
        Select dropdown1 = new Select(driver.findElement(by));
        dropdown1.selectByVisibleText(textValue);
    }

    public static void selectDropdownIndex(By by, int index) {
        Select dropdown2 = new Select(driver.findElement(by));
        dropdown2.selectByIndex(index);
    }

    public static void selectDropdownValue(By by, String value) {
        Select dropdown3 = new Select(driver.findElement(by));
        dropdown3.selectByValue(value);
    }

    public static void assertEquals(String expected, By by, String errorMsg) {
        String actualResult = driver.findElement(by).getText();
        String expectedResult = expected;
        Assert.assertEquals(expected, actualResult, errorMsg);
    }
    //---------------------- Explicit waits ----------------------------------------------------//

    //  Explicit Wait for the clickable element
    public static void driverElementToBeClickable(int time, By by) {
        WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(time));
        wait1.until(ExpectedConditions.elementToBeClickable(by)).click();
    }

    // Explicit wait for the url fraction
    public static void driverUrlContains(int time, String urlFraction) {
        WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(time));
        wait2.until(ExpectedConditions.urlContains(urlFraction));
    }

    public static void driverTitleContains(int time, String title) {
        WebDriverWait wait4 = new WebDriverWait(driver, Duration.ofSeconds(time));
        wait4.until(ExpectedConditions.titleContains(title));
    }
}