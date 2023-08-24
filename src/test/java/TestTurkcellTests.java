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

    @Test(description = "Web sitesine başarılı kullanıcı girişi yapma.")
    @Severity(NORMAL)
    @Description("Web sitesine başarılı kullanıcı girişi yap ve kontrol et.")
    @Story("Turkcell web sitesine git. Hızlı giriş ekranından giriş yap ve kontrol et.")
    @Order(1)
    public void LoginToTurkcellWebsite() {

        MainPage mainPage = new MainPage(driver);
        FastLoginPage fastLoginPage = new FastLoginPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        mainPage.goToFastLoginPage();
        fastLoginPage.successfulFastLogin();
        loginPage.popupIfExist();
        loginPage.verifySuccessfulLogin();
    }
    @Test(description = "Meslek bilgisini güncelleme.")
    @Severity(NORMAL)
    @Description("Meslek bilgisini güncelleme ve kontrol etme.")
    @Story("Turkcell web sitesine giriş yap. Meslek bilgisini 'Doktor' olarak güncelle ve değiştiğini kontrol et. ")
    @Order(2)
    public void jobUpdate(){

        LoginPage loginPage = new LoginPage(driver);
        PasajPage pasajPage = new PasajPage(driver);
        loginPage.alreadyLoggedInUser();
        pasajPage.changeJobandSubmit("Doktor");
        pasajPage.verifySuccessfulTransaction();
    }
    @Test(description = "Pc-Tablet fiyatlarını en yüksek fiyata göre sıralama.")
    @Severity(NORMAL)
    @Description("Pc-Tablet bölümündeki ürün fiyatlarını en yüksek fiyata göre sıralama ve kontol etme.")
    @Story("Web sitesine giriş yap. Pasaj'da Pc-Tablet bölümündeki ürün fiyatlarını yüksekten düşüğe sırala ve kontrol et.")
    @Order(3)
    public void sortComputerAndTabletPrices() {

        PasajPage pasajPage = new PasajPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.alreadyLoggedInUser();
        pasajPage.listPricesHighestToLowest();
        pasajPage.verifyPricesListedCorrectly();

    }

}