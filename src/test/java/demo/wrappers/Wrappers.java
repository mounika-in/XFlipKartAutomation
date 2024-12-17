package demo.wrappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */

     WebDriver driver;
     public Wrappers(WebDriver driver){
        this.driver=driver;
     }
     //////TESTCASE01////
     /*Go to Url-"www.flipkart.com." Search "Washing Machine".
      Sort by popularity and print the count of items with rating less than or equal to 4 stars. */
     public boolean navigateToUrl(String string){
        driver.get(string);
        if(driver.getCurrentUrl().equals(string)){
            return true;
        }
        else{
            return false;
        }
     }
     public Boolean searchProduct(String product){
      WebElement productSearch = driver.findElement(By.xpath("//input[@placeholder='Search for Products, Brands and More']"));
      productSearch.sendKeys(product);
      productSearch.submit();
      if(driver.getCurrentUrl().contains("search")){
         return true;
      }else{
         return false;
      }
     }
     public Boolean popularSearch(){
      WebElement popularity = driver.findElement(By.xpath("//div[text()='Popularity']"));
      popularity.click();
      return true;
     }

    public boolean productsRatingCount(double ratingLimit){
    try {
        
       // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> ratingLists = 
            driver.findElements(By.xpath("//span[contains(@id,'productRating')]"));
        double ratingValue = 0;
        int countOfSelectedProduct = 0;

        for(WebElement ratingElement : ratingLists){
            ratingValue = Double.parseDouble(ratingElement.getText());
            if(ratingValue<=ratingLimit){
                ++countOfSelectedProduct;
            }
        }
    
            if(countOfSelectedProduct==0){
                System.out.println("no products were found");
            }else{
                System.out.println();
                System.out.println(countOfSelectedProduct+" Matching products with less than or equal to " 
                +ratingLimit + " were found");
            }
    
            return true;
    } catch(Exception e){
        System.out.println("Exception encountered");
        System.out.println(e.getMessage());
        return false;
    }
   
}


     //////////*TESTCASSE02*/////////
      /* Search "iPhone", 
      print the Titles and discount % of items with more than 17% discount */
     public void navigateToUrl1(String string){
      driver.get(string);
   }
   public Boolean searchiPhoneProduct(String product){
    WebElement productSearch = driver.findElement(By.xpath("//input[@placeholder='Search for Products, Brands and More']"));
    productSearch.sendKeys(product);
    productSearch.submit();
    return true;
   }
   public Boolean productsMorethanDiscount(int discountLimit){
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
      //List<WebElement> dicountProducts = driver.findElements(By.xpath("//div[@class='UkUFwK']"));
      List<WebElement> discountProducts= wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath("//div[@class='UkUFwK']"))));
      int currentProductDiscount=0;
      List<String> titlesOfProducts = new ArrayList<>();
      for(WebElement discProduct : discountProducts){
         currentProductDiscount = Integer.parseInt(discProduct.getText().split("%")[0]);
         if(currentProductDiscount>discountLimit){
            WebElement productTitle = discProduct.findElement(By.xpath(("//div[@class='KzDlHZ']")));
            titlesOfProducts.add(productTitle.getText()+ " " +currentProductDiscount+"%discount");
         }
      }
   
      if(titlesOfProducts.size()!=0){
         System.out.println();
         System.out.println("Products with discount% more than limit: " +discountLimit+ "%:");
         System.out.println();
         for(String productTitle : titlesOfProducts){
             System.out.println(productTitle);
         }
     }else
         System.out.println("no matching products found");


     return true;
 }

    //////////////TESTCASE03/////////////////
    /*Search "Coffee Mug", 
    Select 4 stars and above, 
    and print the Title and image URL of the 5 items with highest number of reviews*/
    public void navigateToUrl2(String string){
      driver.get(string);
   }
   public Boolean cofeeMugProduct(String product){
    WebElement productSearch = driver.findElement(By.xpath("//input[@placeholder='Search for Products, Brands and More']"));
    productSearch.sendKeys(product);
    productSearch.submit();
    return true;
   }
    public boolean productwithHighestReviews() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@title,'4')]"))).click();
            // Wait for the review elements to be visible
            List<WebElement> reviewOfProducts = wait.until(ExpectedConditions.visibilityOfAllElements(
                driver.findElements(By.xpath("//div[@data-id]//span[@class='Wphh3N']"))
            ));
    
            // List to store reviews with the index of products
            List<int[]> reviewOfLists = new ArrayList<>();
            int index = 0;
            int reviewCount;
    
            // Loop through the review elements and store review count and index
            for (WebElement review : reviewOfProducts) {
                reviewCount = Integer.parseInt(review.getText().replaceAll("[^0-9]", ""));
                reviewOfLists.add(new int[]{reviewCount, ++index}); // Store review count and index
            }
    
            // Sort the review list in descending order by review count
            reviewOfLists.sort((a, b) -> Integer.compare(b[0], a[0])); // Sort by review count in reverse order
    
            // Get the top 5 reviews
            List<int[]> top5Reviews = reviewOfLists.subList(0, Math.min(reviewOfLists.size(), 5));
    
            int indexCount = 1;
            for (int[] review : top5Reviews) {
                int reviewIndex = review[1]; // Get the index of the product
    
                // Print Products title and count of the review
                System.out.println("Title of product " + (indexCount++) + ": " +
                    driver.findElement(By.xpath("(//div[@data-id]//span[@class='Wphh3N']/parent::div/preceding-sibling::a[1])[" + reviewIndex + "]")).getText() +
                    " and # reviews = " + review[0]);
    
                // Print Products image Url
                System.out.println("Image URL of product " + (indexCount) + ": " +
                    driver.findElement(By.xpath("(//div[@data-id]//span[@class='Wphh3N']/parent::div/preceding-sibling::a[2]//img)[" + reviewIndex + "]")).getAttribute("src"));
                System.out.println(); // Print a new line for readability
            }
    
            return true;
        } catch (Exception e) {
            // Print the exception message if something goes wrong
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    




   }




