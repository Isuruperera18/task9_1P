package web.service;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;

public class FunctionalTestsOnMathQuestionService {

    private WebDriver driver;

    @Before
    public void setUp() {
        // Set the path to the chromedriver executable
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

	@Test
	public void testFlow_InvalidQ1Input() {
	    driver.get("http://localhost:8080/login");
	    driver.findElement(By.name("username")).sendKeys("isuru");
	    driver.findElement(By.name("passwd")).sendKeys("isuru_pass");
	    driver.findElement(By.xpath("//input[@type='submit']")).click();

	    driver.get("http://localhost:8080/q1");
	    driver.findElement(By.id("number1")).sendKeys("abc");  
	    driver.findElement(By.id("number2")).sendKeys("5");
	    driver.findElement(By.id("result")).sendKeys("10");
	    driver.findElement(By.xpath("//input[@type='submit']")).click();

	    
        Assert.assertTrue(driver.findElement(By.tagName("h1")).getText().contains("Whitelabel Error Page"));
	}


	   @Test
	    public void testFlow_ValidSequence() {
	        completeValidSequenceThroughQ1();

	        driver.findElement(By.id("number1")).sendKeys("10");
	        driver.findElement(By.id("number2")).sendKeys("4");
	        driver.findElement(By.xpath("//input[@type='submit']")).click();

	        Assert.assertTrue(driver.findElement(By.tagName("h1")).getText().contains("Whitelabel Error Page"));
	    }

	    @Test
	    public void testFlow_InvalidQ2Input() {
	        completeValidSequenceThroughQ1();

	        driver.get("http://localhost:8080/q2");
	        driver.findElement(By.id("number1")).sendKeys("abc");
	        driver.findElement(By.id("number2")).sendKeys("3");
		    driver.findElement(By.id("result")).sendKeys("2");
	        driver.findElement(By.xpath("//input[@type='submit']")).click();

	        Assert.assertTrue(driver.findElement(By.tagName("h1")).getText().contains("Whitelabel Error Page"));
	    }

	    private void completeValidSequenceThroughQ1() {
	        driver.get("http://localhost:8080/login");
	        driver.findElement(By.name("username")).sendKeys("isuru");
	        driver.findElement(By.name("passwd")).sendKeys("isuru_pass");
	        driver.findElement(By.xpath("//input[@type='submit']")).click();

	        driver.get("http://localhost:8080/q1");
	        driver.findElement(By.id("number1")).sendKeys("5");
	        driver.findElement(By.id("number2")).sendKeys("3");
		    driver.findElement(By.id("result")).sendKeys("8");
	        driver.findElement(By.xpath("//input[@type='submit']")).click();
	    }
	
	
	@Test
	public void testQ3() {
	    driver.get("http://localhost:8080/q3");
	    Assert.assertTrue(driver.findElement(By.tagName("h2")).getText().contains("Q3"));
	}


	public boolean testOnBrowser(String browserName) {
	    WebDriver driver;
	    switch (browserName.toLowerCase()) {
	        case "chrome":
	            System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver.exe");
	            driver = new ChromeDriver();
	            break;
	        case "firefox":
	            System.setProperty("webdriver.gecko.driver", "C:\\geckodriver\\geckodriver.exe");
	            driver = new FirefoxDriver();
	            break;
	        default:
	            System.out.println("Browser not supported");
	            return false;
	    }

	    try {
	        driver.get("http://localhost:8080/login");
	        // Perform your test actions here
	        driver.findElement(By.name("username")).sendKeys("isuru");
	        driver.findElement(By.name("passwd")).sendKeys("isuru_pass");
	        driver.findElement(By.xpath("//input[@type='submit']")).click();

	        WebElement successElement = driver.findElement(By.tagName("h2"));
	        return successElement.getText().contains("Q1");
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    } finally {
	        driver.quit();
	    }
	}


	@Test
	public void testBrowserCompatibility() {
	    Assert.assertTrue("Test failed on Chrome", testOnBrowser("Chrome"));
	    Assert.assertTrue("Test failed on Firefox", testOnBrowser("Firefox"));
	}

	public boolean loadTestApplication() {
        int numberOfUsers = 10;
        Thread[] userThreads = new Thread[numberOfUsers];
        boolean[] results = new boolean[numberOfUsers];

        for (int i = 0; i < numberOfUsers; i++) {
            int finalI = i;
            userThreads[i] = new Thread(() -> {
                System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver.exe");
                WebDriver driver = new ChromeDriver();
                try {
                    driver.get("http://localhost:8080/login");
                    driver.findElement(By.name("username")).sendKeys("isuru" + finalI);
                    driver.findElement(By.name("passwd")).sendKeys("isuru_pass" + finalI);
                    driver.findElement(By.xpath("//input[@type='submit']")).click();

                    Thread.sleep(1000); 
                    results[finalI] = true;
                } catch (Exception e) {
                    results[finalI] = false;
                } finally {
                    driver.quit();
                }
            });
            userThreads[i].start();
        }

        for (Thread thread : userThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
        }

        for (boolean result : results) {
            if (!result) return false;
        }

        return true;
    }
	
	@Test
	public void testPerformance_UnderLoad() {
	    Assert.assertTrue("Performance under load is unacceptable", loadTestApplication());
	}


	@Test
	public void testSecurity_SQLInjection() {
	    driver.get("http://localhost:8080/login");
	    driver.findElement(By.name("username")).sendKeys("1' OR '1'='1");
	    driver.findElement(By.name("passwd")).sendKeys("1' OR '1'='1");
	    driver.findElement(By.xpath("//input[@type='submit']")).click();

	    Assert.assertFalse(driver.findElement(By.tagName("h2")).getText().contains("Q1"));
	}

}
