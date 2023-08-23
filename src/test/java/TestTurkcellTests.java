import Pages.FastLoginPage;
import Pages.MainPage;
import Pages.PasajPage;
import Pages.LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import org.springframework.core.annotation.Order;
import org.testng.annotations.Test;

import static io.qameta.allure.SeverityLevel.NORMAL;


public class TestTurkcellTests extends BaseTest {

    @Test(description = "Degisikligi kontrol etme.")
    @Severity(NORMAL)
    @Description("Urun renginin degisikliğini kontrol etme.")
    @Story("Urunun olduğu URL'e git. Urunun rengini değistir ve degistigini kontrol et.")
    @Order(1)
    public void LoginToTurkcellWebsite() {

        MainPage mainPage = new MainPage(driver);
        FastLoginPage fastLoginPage = new FastLoginPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        mainPage.goToFastLoginPage();
        fastLoginPage.successfulFastLogin();
        loginPage.verifySuccessfulLogin();
    }
    @Test
    @Order(2)
    public void jobUpdate(){

        LoginPage loginPage = new LoginPage(driver);
        PasajPage pasajPage = new PasajPage(driver);
        loginPage.alreadyLoggedInUser();
        pasajPage.changeJobandSubmit("Doktor");
        pasajPage.verifySuccessfulTransaction();
    }
    @Test
    @Order(3)
    public void sortComputerAndTabletPrices() {

        PasajPage pasajPage = new PasajPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.alreadyLoggedInUser();
        pasajPage.listPricesHighestToLowest();
        pasajPage.verifyPricesListedCorrectly();

    }

}