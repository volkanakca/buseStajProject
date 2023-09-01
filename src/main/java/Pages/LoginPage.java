package Pages;

import common.Util;
import io.qameta.allure.Step;
import logger.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class LoginPage extends Util {
    Log log = new Log();

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    private final By yourProfile = By.xpath("//em[@class='icon-account-loggedin-placeholder']");
    private final By welcomeText = By.xpath("//div[@class='o-header__welcome']");
    private final By popup = By.cssSelector("button.hype_popup-close-btn");

    public void popupIfExist() {
        waitFor(5000);
        if (isElementPresent(popup)) {
            click(popup);
            waitFor(3000);
            log.info(" Popup kapatıldı. ");
        } else {
            log.info(" Popup çıkmadı. ");
        }
    }


    @Step("Kullanıcının sisteme başarılı giriş yaptığı doğrulandı.")
    public void verifyLogin() {
        waitForVisibility(yourProfile);
        click(yourProfile);
        waitFor(3000);
        waitForVisibility(welcomeText);
        waitFor(3000);

        if (isElementPresent(welcomeText)) {
            log.info(" Kullanıcının başarılı bir şekilde sisteme giriş yaptığı doğrulandı. ");
        } else {
            Assert.fail(" Giriş yapılamadı! ");
        }
    }

    @Step("Kullanıcı sisteme giriş yapmış durumda.")
    public void succesfullyLoggedInUser() {

        MainPage mainPage = new MainPage(getDriver());
        FastLoginPage fastLoginPage = new FastLoginPage(getDriver());

        mainPage.goToFastLoginPage();
        fastLoginPage.successfulFastLogin();
        waitFor(3000);

    }
}
