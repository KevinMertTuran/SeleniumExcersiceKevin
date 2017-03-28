package test;

import java.util.List;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.assertThat;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CarsTest1 {

  private static final int WAIT_MAX = 4;
  static WebDriver driver;


  @BeforeClass
  public static void setup() {
       
       System.setProperty("webdriver.gecko.driver", "/Users/kevinturan/Documents/Drivers/geckodriver");
        System.setProperty("webdriver.chrome.driver","/Users/kevinturan/Documents/Drivers/chromedriver");

        // Create a new instance of the Firefox driver
        // Notice that the remainder of the code relies on the interface, 
        // not the implementation.
      
    //Reset Database
    com.jayway.restassured.RestAssured.given().get("http://localhost:3000/reset");
    driver = new ChromeDriver();
    driver.get("http://localhost:3000");
  }

  @AfterClass
  public static void tearDown() {
    driver.quit();
    //Reset Database 
    com.jayway.restassured.RestAssured.given().get("http://localhost:3000/reset");
  }

  @Test
  //Verify that page is loaded and all expected data are visible
  public void test1() throws Exception {
    (new WebDriverWait(driver, WAIT_MAX)).until((ExpectedCondition<Boolean>) (WebDriver d) -> {
      WebElement e = d.findElement(By.tagName("tbody"));
      List<WebElement> rows = e.findElements(By.tagName("tr"));
      Assert.assertThat(rows.size(), is(5));
      return true;
    });
  }

  @Test
  public void test2() throws Exception {
     
     int size = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr")).size();
     assertThat(size, is(5));
    
  }

   @Test
    public void test3() throws Exception {
        driver.findElement(By.id("filter")).sendKeys("2002");
        int size = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr")).size();
        assertThat(size, is(2));
    }
    
    @Test
    public void test4() throws Exception{
        driver.findElement(By.id("filter")).clear();
        int size = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr")).size();
        assertThat(size, is(2));
    }
    
    @Test
    public void test5() throws Exception {
        driver.findElement(By.id("h_year")).click();
        
        List<WebElement> rows = driver.findElement(By.tagName("tbody"))
                .findElements(By.tagName("tr"));
        
        String firstId = rows.get(0).findElements(By.tagName("td")).get(0).getText();
        String lastId = rows.get(4).findElements(By.tagName("td")).get(0).getText();
        
        assertThat(firstId, is("938"));   
    }
    
    @Test
    public void test6() throws Exception {
        
        WebElement table = driver.findElement(By.tagName("tbody"));
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        List<WebElement> row = rows.get(0).findElements(By.tagName("td"));
        row.get(row.size() - 1).findElements(By.tagName("a")).get(0).click();
        
        WebElement desc = driver.findElement(By.id("description"));
        desc.clear();
        desc.sendKeys("Cool car");
        driver.findElement(By.id("save")).click();
        
        List<WebElement> after = rows.get(0).findElements(By.tagName("td"));
        assertThat(after.get(5).getText(), equalTo("Cool car"));
    }
    
    @Test
    public void test7() throws Exception{
        driver.findElement(By.id("new")).click();
        driver.findElement(By.id("save")).click();
        
        String error = driver.findElement(By.id("submiterr")).getText();
        
        int size = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr")).size();
        
        assertThat(error, is("All fields are required"));
        assertThat(size, is(5));
    }
    
    @Test
    //Adding new car
    public void test8() throws Exception {
        driver.findElement(By.id("new")).click();
        driver.findElement(By.id("year")).sendKeys("2008");
        driver.findElement(By.id("registered")).sendKeys("2002-5-5");
        driver.findElement(By.id("make")).sendKeys("Kia");
        driver.findElement(By.id("model")).sendKeys("Rio");
        driver.findElement(By.id("description")).sendKeys("As new");
        driver.findElement(By.id("price")).sendKeys("31000");
        driver.findElement(By.id("save")).click();
        
        int size = driver.findElement(By.tagName("tbody")).findElements(By.tagName("tr")).size();
        
        assertThat(size, is(6));

    }
}

  
  





