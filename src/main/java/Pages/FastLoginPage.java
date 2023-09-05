package Pages;

import ConfigReader.ConfigReader;
import common.Util;
import io.qameta.allure.Step;
import logger.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Set;

public class FastLoginPage extends Util {
    private final WebDriver driver;
    Log log = new Log();

    public FastLoginPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    private final By phoneNo = By.xpath("//input[@id='phoneNo']");

    private final By loginWithFastLoginPasswordCheckbox = By.xpath("(//input[@id='loginWithPassword'])[1]");
    private final By login = By.xpath("//button[@id='webLogin-button']");
    private final By password = new By.ByCssSelector("input#password");
    private final By submit = By.xpath("//button[@id='password-login-forward-button']");


    @Step("Kullanıcı bilgileri ile giriş yapıldı.")
    public void successfulFastLogin() {

        String firstWindow = driver.getWindowHandle();
        Set<String> allWindows = driver.getWindowHandles();
        boolean fastLoginOpened = false;

        for (String window : allWindows) {
            if (!window.equals(firstWindow)) {
                driver.switchTo().window(window);
                log.info(" Hızlı giriş sayfası açıldı. ");
                waitForVisibility(phoneNo);
                enterText(phoneNo, ConfigReader.getPhoneNo());
                clickWithActions(loginWithFastLoginPasswordCheckbox);
                click(login);
                click(login);
                waitFor(2000);
                waitForVisibility(password);
                enterText(password, ConfigReader.getPassword());
                click(submit);
                waitFor(2000);

                fastLoginOpened = true;
                driver.switchTo().window(firstWindow);
                waitFor(2000);
                break;
            }
        }

        if (!fastLoginOpened) {
            log.info("Hızlı giriş sayfası açılamadı!");
        }
    }
}

