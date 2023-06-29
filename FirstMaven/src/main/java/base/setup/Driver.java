package base.setup;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Driver {

    static WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }
    private void setDriver(String browserType, String appURL) {
        switch (browserType) {
            case "chrome":
                driver = initChromeDriver(appURL);
                break;
            default:
                System.out.println("Browser: " + browserType + " is invalid, Launching Chrome as browser of choice...");
                driver = initChromeDriver(appURL);
        }
    }

    private static WebDriver initChromeDriver(String appURL) {
        WebDriverManager.chromedriver().setup();// super class
        System.setProperty("chromeoptions.args", "--window-size=3840x2160");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.navigate().to(appURL);

        driver.manage().timeouts().pageLoadTimeout(GlobalVariable.LONG_TIME_OUT, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(GlobalVariable.LONG_TIME_OUT, TimeUnit.SECONDS);

        return driver;
    }
    @Parameters({ "browserType", "appURL" })
    @BeforeClass
    public void initializeTestBaseSetup(String browserType, String appURL) {
        try {
            setDriver(browserType, appURL);
        } catch (Exception e) {
            System.out.println("Error..." + e.getStackTrace());
        }
    }
    public void tearDown(){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(GlobalVariable.LONG_TIME_OUT));
        System.out.println("Quite browser.");
        driver.quit();
    }
}
