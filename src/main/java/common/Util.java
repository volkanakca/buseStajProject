package common;

import logger.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class Util {

    Log log = new Log();
    private final WebDriver driver;

    public Util(WebDriver driver) {
        this.driver = driver;
    }

    public void click(By locator) {
        try {
            driver.findElement(locator).click();
        } catch (Exception e) {
            log.error(" Elemente tıklanamadı " + e.getMessage());
        }
    }

    protected WebDriver getDriver() {
        return driver;
    }

    public boolean isElementPresent(By locator) {
        boolean isPresent = !driver.findElements(locator).isEmpty();
        if (!isPresent) {
            log.error(" Element sayfada mevcut değil! " + locator.toString());
        }
        return isPresent;
    }


    public void waitForClickable(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement waitForVisibility(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
       return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void enterText(By locator, String text) {
        driver.findElement(locator).sendKeys(text);
    }

    public void waitFor(long mseconds) {
        try {
            Thread.sleep(mseconds);
        } catch (Exception ignored) {
        }
    }

    public void clickWithActions(By locator) {
        WebElement element = driver.findElement(locator);
        Actions actions = new Actions(driver);
        actions.click(element).perform();
    }

    public boolean isVisible(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            Assert.fail(" Element görünür halde değil! ");
            return false;
        }
    }

    public void scrollDown(int scrollAmount) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("window.scrollBy(0, " + scrollAmount + ");");
    }
}
