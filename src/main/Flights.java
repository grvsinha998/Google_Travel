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
        driver.get("https://www.google.com/travel/flights/");

        Check_advisory(driver);

        Select_Trip("One way",driver);
        Number_of_passengers(3,1,0,driver);
        Seating_Class("Premium economy",driver);
        Itinerary("IXR","BLR","October,19",driver);

        Fetch_Flights(driver);

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
            System.out.println("No Travel Restrictions!");
        }

    }
    public static void Select_Trip(String trip, WebDriver driver) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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
    public static void Number_of_passengers(int Adults, int Children, int Infants, WebDriver driver) {
        driver.findElement(By.xpath("(//span[@class='VfPpkd-kBDsod UmgCUb'])[2]")).click();

        int i,j,k;
        i=j=k=0;

        while (i<Adults-1) {
            driver.findElement(By.xpath("//button[@aria-label='Add adult']")).click();
            i++;
        }
        while (j<Children) {
            driver.findElement(By.xpath("//button[@aria-label='Add child']")).click();
            j++;
        }
        while (k<Infants) {
            driver.findElement(By.xpath("//button[@aria-label='Add infant in seat']")).click();
            k++;
        }

        driver.findElement(By.xpath("(//span[text()='Done'])[1]")).click();

    }
    public static void Seating_Class(String Seat, WebDriver driver) {
        driver.findElement(By.xpath("//button[@aria-label='Economy, Change seating class.']")).click();

        List<WebElement> Seats = driver.findElements(By.xpath("//ul[@aria-label='Select your preferred seating class.']/li"));
        for (WebElement seat_web : Seats) {
            String Seat_list = seat_web.getText();
            if (Seat_list.contains(Seat)) {
                seat_web.click();
                break;
            }
        }
    }
    public static void Itinerary(String Origin, String Destination, String Departure_Date, WebDriver driver) throws InterruptedException {
        /*
        Origin & Destination - Use IATA codes (https://www.prokerala.com/travel/airports/india/)
         */
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        Actions axn = new Actions(driver);

        WebElement Origin_searchbar = driver.findElement(By.xpath("(//input[@jsname='yrriRe'])[1]"));
        axn.moveToElement(Origin_searchbar).doubleClick().sendKeys(Origin).build().perform();
        driver.findElement(By.xpath("(//li[@class='n4HaVc '])[1]")).click();
        Thread.sleep(1000);

        WebElement Dest_searchbar = driver.findElement(By.xpath("(//input[@jsname='yrriRe'])[3]"));
        axn.moveToElement(Dest_searchbar).doubleClick().sendKeys(Destination).build().perform();
        driver.findElement(By.xpath("(//li[@class='n4HaVc '])[1]")).click();
        Thread.sleep(1000);

        WebElement Date_bar = driver.findElement(By.xpath("(//div[@class='GpDmDb q5Vmde'])[1]"));
        axn.moveToElement(Date_bar).click().sendKeys(Departure_Date).sendKeys(Keys.ENTER).build().perform();
        driver.findElement(By.xpath("(//span[text()='Done'])[2]")).click();
        Thread.sleep(1000);
    }
    public static void Fetch_Flights(WebDriver driver) throws InterruptedException {
        Actions axn = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[contains(text(),'flights')]")));

        WebElement expand_button = driver.findElement(By.xpath("//button[@jsname='ornU0b']"));
        if (expand_button.isDisplayed()) {
            axn.moveToElement(expand_button).click().build().perform();
        }

        Thread.sleep(5000);

        int Flights = driver.findElements(By.xpath("//div[@jsname='lwc3Jf']")).size();
        System.out.println(Flights);
    }

}
