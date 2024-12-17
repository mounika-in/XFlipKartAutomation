package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;


// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases {
    ChromeDriver driver;

    /*
     * TODO: Write your tests here with testng @Test annotation. 
     * Follow `testCase01` `testCase02`... format or what is provided in instructions
     */

     
    /*
     * Do not change the provided methods unless necessary, they will help in automation and assessment
     */

     @Test
     public void testCase01(){
        Wrappers wrappers = new Wrappers(driver);
        double ratingLessThan = 4.1;
        boolean status= false;
        wrappers.navigateToUrl("https://www.flipkart.com/");
        SoftAssert sAssert = new SoftAssert();
        sAssert.assertTrue(status, "Cannot navigate to home page");
        wrappers.searchProduct("Washing Machine");
        sAssert.assertTrue(status, "unable to dearchfor the product");
        wrappers.popularSearch();
        sAssert.assertTrue(status, "List of machines are not displayed according to popularity");
        status= wrappers.productsRatingCount(ratingLessThan);
        sAssert.assertTrue(status, "List of products less than rating are displayed");
     }

     @Test
     public void testCase02(){
        Wrappers wrappers = new Wrappers(driver);
        int discountLimit = 17;
        Boolean status= false;
        wrappers.navigateToUrl1("https://www.flipkart.com/");
        SoftAssert sAssert = new SoftAssert();
        sAssert.assertTrue(status, "Valid Url");
        wrappers.searchiPhoneProduct("iPhone");
        sAssert.assertTrue(status, "Searched for the product");
        wrappers.productsMorethanDiscount(discountLimit);
        sAssert.assertTrue(status, "Found the discount items titles");
     }
     
    @Test
     public void testCase03(){
        Wrappers wrappers = new Wrappers(driver);
        SoftAssert sAssert = new SoftAssert();
        Boolean status= false;
        wrappers.navigateToUrl2("https://www.flipkart.com/");
        sAssert.assertTrue(status, "Valid URL");
        wrappers.cofeeMugProduct("Coffee Mug");
        sAssert.assertTrue(status, "Searched product");
        wrappers.productwithHighestReviews();
        sAssert.assertTrue(status, "Reviewed highest number of products");
     }
    

    @BeforeTest
    public void startBrowser()
    {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log"); 

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }


    @AfterTest
    public void endTest()
    {
        driver.close();
        driver.quit();

    }
}