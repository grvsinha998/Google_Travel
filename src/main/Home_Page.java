package main;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Home_Page {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.setProperty("webdriver.chrome.driver", "/Users/gauravsinha/Downloads/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
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
    public static void Check_all_Options(WebDriver driver) throws InterruptedException, IOException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement SideBar = driver.findElement(By.xpath("//nav[@class='rlGvde']"));
        Actions axn = new Actions(driver);

        List<WebElement> Travel_urls = driver.findElements(By.xpath("//nav[@class='rlGvde']//a"));
        for (WebElement Travel_url: Travel_urls) {
            String url = Travel_url.getAttribute("href");
            HttpsURLConnection Travel_conn = (HttpsURLConnection) new URL(url).openConnection();
            Travel_conn.setRequestMethod("HEAD");
            Travel_conn.connect();
            int Resp_Code = Travel_conn.getResponseCode();

            Assert.assertEquals(Resp_Code,200);
        }
        }
    }
