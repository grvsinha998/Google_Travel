package main;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("deprecation")
public class Flights {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "/Users/gauravsinha/Downloads/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://www.google.com/travel/");

        Check_advisory(driver);

        Select_Trip("One way",driver);
        Itinerary("BLR","Bengaluru","15","June",driver);

        driver.quit();
    }

    public static void Check_advisory(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'advisory')]")));

        if (driver.findElement(By.xpath("//h2[contains(text(),'advisory')]")).isDisplayed()) {

            String adv = driver.findElement(By.xpath("//h2[contains(text(),'advisory')]/following-sibling::div")).getText();
            System.out.println(adv);

            String link = driver.findElement(By.xpath("//h2[contains(text(),'advisory')]/following-sibling::div/a")).getAttribute("href");
            System.out.println("Advisory Link:" + link);
        }

        else {
            driver.navigate().refresh();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='heading'][text()='Travel']")));
        }

    }
    public static void Select_Trip(String trip, WebDriver driver) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.findElement(By.xpath("//div[@class='PH4Kgc']/span[text()='Flights']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='Eo39gc'][text()='Flights']")));

        driver.findElement(By.xpath("//div[@class='RLVa8 GeHXyb']")).click();

        String list_xpath = "//ul[@aria-label='Select your ticket type.']//li[@role='option']";

        List<WebElement> trip_list = driver.findElements(By.xpath(list_xpath));
        for (int i = 0; i < trip_list.size(); i++) {
            String trip_type = trip_list.get(i).getText();
            if (trip_type.contains(trip)) {
                driver.findElements(By.xpath(list_xpath)).get(i).click();
            }
        }
        Thread.sleep(3000);
    }
    public static void Itinerary(String Origin, String Destination, String in_date, String in_month, WebDriver driver) throws InterruptedException {
        /*
        Origin & Destination - Use IATA codes (https://www.prokerala.com/travel/airports/india/)
         */
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        Actions axn = new Actions(driver);

        WebElement Dest_searchbar = driver.findElement(By.xpath("(//input[@jsname='yrriRe'])[3]"));
        axn.moveToElement(Dest_searchbar).doubleClick();
        Dest_searchbar.sendKeys(Destination);
        Thread.sleep(1000);

//        List<WebElement> options = driver.findElements(By.cssSelector("li[class='ui-menu-item'] a"));
//        for(WebElement option :options)
//        {
//            if(option.getText().equalsIgnoreCase(country))
//            {
//                option.click();
//                break;
//            }
//        }

//        Thread.sleep(1000);

//        driver.findElement(By.xpath("(//div[@jsname='yrriRe'])[3]")).click();

//        String mpath = "//div[@class='BgYkof L62Kqc qZwLKe']";
//        String dpath = "//div[@class='BgYkof L62Kqc qZwLKe']/parent::div//div[@jsname='Mgvhmd']/div/div/div/div";
//
//        List<WebElement> months = driver.findElements(By.xpath(mpath));
//        for (WebElement month : months) {
//            String element = month.getText();
//            if (element.contains(in_month)) {
//                List<WebElement> dates = driver.findElements(By.xpath(dpath));
//                for (WebElement date : dates) {
//                    String element_d = date.getText();
//                    if (element_d.contains(in_date)) {
//                        driver.findElement(By.xpath(dpath)).click();
//                        System.out.println(driver.findElement(By.xpath(dpath)).getText());
//                    }
//                }
//            }
//        }
    }

}
