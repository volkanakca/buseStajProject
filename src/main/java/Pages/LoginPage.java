package Pages;

import common.Util;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends Util {

    public LoginPage(WebDriver driver) {
        super(driver);
    }
    private final By yourProfile = By.xpath("//em[@class='icon-account-loggedin-placeholder']");
    private final By welcomeText = By.xpath("//div[@class='o-header__welcome']");
    @Step("Kullanıcının sisteme başarılı giriş yaptığı doğrulandı.")
    public void verifySuccessfulLogin() {
        waitForVisibility(yourProfile);
        click(yourProfile);
        waitForVisibility(welcomeText);
        waitFor(5000);

        if (isElementPresent(welcomeText)) {
            System.out.println(" Kullanıcı başarılı bir şekilde giriş yaptı.");
        } else {
            System.out.println(" Giriş yapılamadı! ");
        }
    }

    @Step("Kullanıcı sisteme giriş yapmış durumda.")
    public void alreadyLoggedInUser(){

        MainPage mainPage = new MainPage(getDriver());
        FastLoginPage fastLoginPage = new FastLoginPage(getDriver());

        mainPage.goToFastLoginPage();
        fastLoginPage.successfulFastLogin();
        waitFor(3000);
        verifySuccessfulLogin();
    }
}
