import io.qameta.allure.Step;
import logger.Log;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;



public class BaseTest {
    Log log = new Log();

    WebDriver driver ;

    @Step("Tarayıcı ayarları yapıldı ve web sitesine gidildi.")
    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        ChromeOptions co=new ChromeOptions();
        co.addArguments("--remote-allow-origins=*");
        co.addArguments("--headless");
        co.addArguments("--window-size=1920x1080");
        driver = new ChromeDriver(co);
        driver.get("https://www.turkcell.com.tr");
        driver.manage().window().maximize();
        log.info("Turkcell web sitesine gidildi.");
    }

    @Step("Web sitesi kapatıldı.")
    @AfterMethod
    public void tearDown() {
        driver.quit();
        log.info("Web sitesi kapatıldı.");
    }
}

