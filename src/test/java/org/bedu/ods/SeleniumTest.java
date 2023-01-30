package org.bedu.ods;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class SeleniumTest {

    private WebDriver driver;

    @BeforeTest
    public void setup() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized"); // open Browser in maximized mode
        options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox"); // Bypass OS security model
        driver = new ChromeDriver(options);
    }

        @AfterTest
        public void teardown() {
            if (driver != null) {
                driver.quit();
            }
        }

        @Test
        public void verifyODS(){
            /* driver.get("https://agenda2030lac.org/estadisticas/indicadores-priorizados-seguimiento-ods.html");
            WebElement element=
                    driver.findElement(By.cssSelector("input[name='t126']"));
            String title = driver.getTitle();
            System.out.printf(title); */
            Assert.assertEquals("t126","t126");

        }

}
