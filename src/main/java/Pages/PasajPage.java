package Pages;

import common.Util;
import io.qameta.allure.Step;
import logger.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PasajPage extends Util {
    private final WebDriver driver;

    public PasajPage(WebDriver driver) {

        super(driver);
        this.driver = driver;

    }

    private final By passageIcon = By.xpath("//a[@href='/pasaj?place=menu']");
    private final By myAccount = By.xpath("//a[@class='o-p-header__my-account__button js-account-button']");
    private final By myUserInformation = By.xpath("//a[@href='/pasaj/hesabim/kullanici-bilgilerim']");
    private final By jobBox = By.xpath("//div[@class='m-select__input-container css-ackcql']");
    private final By updateButton = By.xpath("//button[@class='a-p-btn a-update-btn']");
    private final By myContactInfoNeingSavedCkeckbox = By.xpath("//span[@aria-label='İletişim bilgilerimin kaydedilmesine izin veriyorum.']");

    private final By bilgisayarTablet = By.xpath("(//a[@href='/pasaj/bilgisayar-tablet'])[1]");
    private final By firstDropdown = By.xpath("//div[@class='m-accordion__head groupm-container-head']");
    private final By secondDropdown = By.xpath("(//div[@class='m-accordion__head '])[1]");
    private final By firstSelect = By.xpath("//label[@for='sort-3']");
    private final By secondSelect = By.xpath("//label[@for='sort-3']");
    private final By price = By.xpath("//div[@class='m-p-pc__foot']");
    private final By transactionSuccessful = By.xpath("//div[@class='components-molecules-modal_m-modal-custom__body__2J20h']");
    Log log = new Log();

    @Step("Meslek bilgisi 'Doktor' olarak güncellendi.")
    public void changeJobandSubmit(String newJob) {
        click(passageIcon);
        click(myAccount);
        waitFor(3000);
        log.info(" Hesabım dropdown menüsü açıldı. ");
        click(myUserInformation);
        WebElement meslekButton = driver.findElement(jobBox);
        Actions actions = new Actions(driver);
        actions.click(meslekButton).sendKeys(newJob).sendKeys(Keys.ENTER).perform();
        log.info(" Yeni meslek bilgisi girildi. ");
        clickWithActions(myContactInfoNeingSavedCkeckbox);
        click(updateButton);
        waitFor(5000);
    }

    @Step("Meslek bilgisinin güncellendiği doğrulandı.")
    public void verifySuccessfulTransaction() {

        if (isVisible(transactionSuccessful)) {
            log.info(" Meslek bilgisi başarılı bir şekilde güncellendi. ");
        } else {
            Assert.fail(" Meslek bilgisi güncellenemedi !");
        }
    }

    public void clickTheHighestPriceOptionInDropdown() {
        Util util = new Util(driver);
        try {
            waitForClickable(firstDropdown);
            click(firstDropdown);
            util.waitFor(3000);
            click(firstSelect);
        } catch (Exception e) {
            waitForClickable(secondDropdown);
            click(secondDropdown);
            util.waitFor(3000);
            click(secondSelect);
        }
    }

    @Step("Pc-Tablet bölümüne gidildi ve fiyatlar yüksekten düşüğe sıralandı.")
    public void listPricesHighestToLowest() {
        click(passageIcon);
        click(bilgisayarTablet);
        log.info(" Bilgisayar-Tablet bölümüne girildi.");
        waitFor(5000);
        scrollDown(650);
        waitFor(3000);
        clickTheHighestPriceOptionInDropdown();
        log.info(" En Yüksek Fiyat seçeneği seçildi.");
        scrollDown(1500);
    }

    // Fiyatları çeker ve işler.
    private List<Double> extractPrices(List<WebElement> priceElements) { // Çekilen fiyatların listesini -priceElements- oluşturur.
        List<Double> prices = new ArrayList<>(); // prices boş arraylistini oluşturur.

        for (WebElement priceElement : priceElements) { // Her element için döner.
            String priceText = priceElement.getText().replaceAll("[^0-9.]", ""); // Sayı ve nokta dışındakileri çıkartır.
            if (!priceText.isEmpty()) {
                try {
                    double price = Double.parseDouble(priceText); // Boş değilse ondalığa çevirir ve price listesine ekler.
                    prices.add(price);
                } catch (NumberFormatException ignored) {
                }
            }
        }

        return prices;
    }

    private void verifySortedPrices(List<Double> prices) {
        List<Double> sortedPrices = new ArrayList<>(prices);
        sortedPrices.sort(Collections.reverseOrder()); // Fiyatları büyükten küçüğe sıralar

        Assert.assertEquals(prices, sortedPrices, " Fiyatlar doğru şekilde sıralanmadı. "); // Listeler karşılaştırılır ve doğrular.
    }

    private String createPriceList(List<Double> prices) { // Double fiyat listesini alır metine dönüştürür.
        StringBuilder stringBuilder = new StringBuilder(" Fiyat Listesi: [");
        for (Double price : prices) {
            stringBuilder.append(price).append(" , ");
        }
        stringBuilder.setLength(stringBuilder.length() - 3); // Son virgül ve boşluğu siler.
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Step("Fiyatların yüksekten düşüğe sıralandığı doğrulandı.")
    public void verifyPricesListedCorrectly() {
        List<WebElement> priceElements = driver.findElements(price);

        List<Double> prices = extractPrices(priceElements);

        verifySortedPrices(prices);

        String priceString = createPriceList(prices);
        log.info(priceString);

        waitFor(4000);
    }
}