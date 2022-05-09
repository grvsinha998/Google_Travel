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

public class Home_Page {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "/Users/gauravsinha/Downloads/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://www.google.com/travel/");

        Check_advisory(driver);
        Options("Explore",driver);
//        Options("Things to do",driver);
        Options("Flights",driver);
//        Options("Hotels",driver);

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
    public static void Options(String type,WebDriver driver) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        List<WebElement> options = driver.findElements(By.xpath("//div[@class='PH4Kgc']/span"));
        int i=0;
        while (i<options.size()) {
            String option = options.get(i).getText();
            if (option.contains(type)) {
                options.get(i).click();
                Thread.sleep(10000);
                driver.findElement(By.xpath("//span[@jsname='iSelEd'][text()='Travel']")).click();
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='heading'][text()='Travel']")));
            }
            i++;
        }
    }
}
