package main;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Sandbox {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "/Users/gauravsinha/Downloads/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://www.google.com/travel/flights");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='Eo39gc'][text()='Flights']")));

        Select_Trip(driver,"One way");

        driver.quit();

        }

        public static void Select_Trip(WebDriver driver, String trip) {
            driver.findElement(By.xpath("//div[@class='RLVa8 GeHXyb']")).click();

            String list_xpath = "//ul[@aria-label='Select your ticket type.']//li[@role='option']";

            List<WebElement> trip_list = driver.findElements(By.xpath(list_xpath));

            for (int i=0; i<trip_list.size(); i++) {
                String trip_type = trip_list.get(i).getText();
                if (trip_type.contains(trip)) driver.findElements(By.xpath(list_xpath)).get(i).click();
            }


    }
    }
