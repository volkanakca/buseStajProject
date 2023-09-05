package Pages;

import ConfigReader.ConfigReader;
import common.Util;
import io.qameta.allure.Step;
import logger.Log;
import org.openqa.selenium.By;
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
    private final By categoryHighlights = By.xpath("(//div[@class='ins-editable ins-element-editable'])[1]");
    Log log = new Log();

    @Step("Meslek bilgisi 'Akademisyen' olarak güncellendi.")
    public void changeJobandSubmit() {
        click(passageIcon);
        click(myAccount);
        waitFor(3000);
        log.info(" Hesabım dropdown menüsü açıldı. ");
        click(myUserInformation);
        WebElement meslekButton = driver.findElement(jobBox);
        Actions actions = new Actions(driver);
        actions.click(meslekButton).sendKeys(ConfigReader.getNewJob()).perform();
        log.info(" Yeni meslek bilgisi girildi. ");
        clickWithActions(myContactInfoNeingSavedCkeckbox);
        click(updateButton);
    }

    @Step("Meslek bilgisinin güncellendiği doğrulandı.")
    public void verifySuccessfulTransaction() {

        if (isVisible(transactionSuccessful)) {
            log.info(" Meslek bilgisi başarılı bir şekilde güncellendi. ");
        } else {
            Assert.fail(" Meslek bilgisi güncellenemedi !");
        }
    }


    @Step(" Pc-Tablet bölümüne gidildi. ")
    public void PcTabletPage() {
        click(passageIcon);
        click(bilgisayarTablet);
        log.info(" Bilgisayar-Tablet bölümüne girildi.");
        waitForVisibility(categoryHighlights);
        scrollDown(700);
        waitFor(3000);
    }

    @Step(" En Yüksek Fiyata göre sırala seçenği seçildi. ")
    public void clickTheHighestPriceOptionInDropdown() {
        Util util = new Util(driver);
        try {
            if (isElementPresent(firstDropdown)) {
                waitForClickable(firstDropdown);
                click(firstDropdown);
                util.waitFor(3000);
                click(firstSelect);
                log.info(" En Yüksek Fiyat seçeneği seçildi. ");
                scrollDown(1500);
            } else if (isElementPresent(secondDropdown)) {
                waitForClickable(secondDropdown);
                click(secondDropdown);
                util.waitFor(3000);
                click(secondSelect);
                log.info(" En Yüksek Fiyat seçeneği seçildi. ");
                scrollDown(1500);
            } else {
                Assert.fail(" Sırala butonu görülemedi!");
            }
        } catch (Exception e) {
            Assert.fail(" Sıralama dropdownu bulunurken hata oluştu! " + e.getMessage());
        }
    }


    private List<Double> extractPrices(List<WebElement> priceElements) {
        List<Double> prices = new ArrayList<>();

        for (WebElement priceElement : priceElements) {
            String priceText = priceElement.getText().replaceAll("[^0-9.]", "").trim();

            if (!priceText.isEmpty()) {
                try {
                    double price = Double.parseDouble(priceText);
                    prices.add(price);
                } catch (NumberFormatException e) {
                    log.error(" Hatalı fiyat: " + priceText);
                }
            }
        }
        return prices;
    }


    private void verifySortedPrices(List<Double> prices) {
        List<Double> sortedPrices = new ArrayList<>(prices);
        sortedPrices.sort(Collections.reverseOrder());

        Assert.assertEquals(prices, sortedPrices, " Fiyatlar doğru şekilde sıralanmadı. ");
    }


    private String createPriceList(List<Double> prices) {
        StringBuilder stringBuilder = new StringBuilder(" Fiyat Listesi: [");
        for (Double price : prices) {
            stringBuilder.append(price).append(" , ");
        }
        stringBuilder.setLength(stringBuilder.length() - 3);
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