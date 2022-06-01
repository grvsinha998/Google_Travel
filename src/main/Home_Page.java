package main;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

public class Home_Page {
    public static void main(String[] args) throws IOException {
        System.setProperty("webdriver.chrome.driver", "/Users/gauravsinha/Downloads/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.google.com/travel/");

        Check_advisory(driver);
        Check_all_Options(driver);

        driver.quit();

    }
    public static void Check_advisory(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'update')]")));

        if (driver.findElement(By.xpath("//h2[contains(text(),'update')]")).isDisplayed()) {

            String adv = driver.findElement(By.xpath("//h2[contains(text(),'update')]/following-sibling::div")).getText();
            System.out.println(adv);

            String link = driver.findElement(By.xpath("//h2[contains(text(),'update')]/following-sibling::div/a")).getAttribute("href");
            System.out.println("Advisory Link:" + link);
        }
        else {
            driver.navigate().refresh();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='heading'][text()='Travel']")));
        }
    }
    public static void Check_all_Options(WebDriver driver) throws IOException {
        List<WebElement> Travel_urls = driver.findElements(By.xpath("//nav[@class='rlGvde']//a"));
        SoftAssert sa = new SoftAssert();
        for (WebElement Travel_url: Travel_urls) {
            String url = Travel_url.getAttribute("href");
            HttpsURLConnection Travel_conn = (HttpsURLConnection) new URL(url).openConnection();
            Travel_conn.setRequestMethod("HEAD");
            Travel_conn.connect();
            int Resp_Code = Travel_conn.getResponseCode();
            sa.assertEquals(Resp_Code, 200, url + " is working.");
        }
        sa.assertAll();
    }
}
