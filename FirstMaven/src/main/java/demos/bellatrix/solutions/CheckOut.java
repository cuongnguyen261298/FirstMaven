package demos.bellatrix.solutions;

import common.CommonFunction;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckOut extends CommonFunction {
    private WebDriver driver;
    public CheckOut(WebDriver _driver) {
        super(_driver);
        this.driver = _driver;
        PageFactory.initElements(driver, this);
    }
    @FindBy(id = "billing_first_name")
    static WebElement txtFirstName;
    @FindBy(id = "billing_last_name")
    static WebElement txtLastName;
    @FindBy(id = "billing_company")
    static WebElement txtCompany;
    @FindBy(id = "select2-billing_country-container")
    static WebElement cboCountryRegion;
    @FindBy(xpath = "//input[@aria-owns='select2-billing_country-results']")
    static WebElement txtCountryRegion;
    @FindBy(id = "billing_address_1")
    static WebElement txtAddress;
    @FindBy(id = "billing_city")
    static WebElement txtTownCity;
    @FindBy(id = "select2-billing_state-container")
    static WebElement cboStateCountry;
    @FindBy(xpath = "//input[@aria-owns='select2-billing_state-results']")
    static WebElement txtStateCountry;
    @FindBy(id = "billing_postcode")
    static WebElement txtPostCode;
    @FindBy(id = "billing_phone")
    static WebElement txtPhone;
    @FindBy(id = "billing_email")
    static WebElement txtEmail;
    @FindBy(id = "place_order")
    static WebElement btnPlaceOrder;
    @FindBy(xpath = "//*[contains(text(),'Thank you. Your order has been received')]")
    static WebElement textReceived;
    public void fillBilling(String firstName, String lastName,String companyName,String address,String TownCity, String phoneNumber, String email, String countryRegionName, String stateCountryName, String postCode) throws Exception {
        sendkeyElement(txtFirstName, firstName);
        sendkeyElement(txtLastName, lastName);
        sendkeyElement(txtCompany, companyName);
        sendkeyElement(txtAddress, address);
        sendkeyElement(txtTownCity, TownCity);
        sendkeyElement(txtPhone, phoneNumber);
        sendkeyElement(txtEmail, email);
        sendkeyElement(txtPostCode, postCode);

        clickElement(cboCountryRegion);
        sendkeyElement(txtCountryRegion, countryRegionName);
        pressEnter();

        clickElement(cboStateCountry);
        sendkeyElement(txtStateCountry, stateCountryName);
        pressEnter();

        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", btnPlaceOrder);

        if (textReceived.isDisplayed()){
            screenShotPage("demos.bellatrix.solutions/CheckOut");
        }
    }
}
