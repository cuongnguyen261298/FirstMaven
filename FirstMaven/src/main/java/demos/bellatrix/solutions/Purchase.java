package demos.bellatrix.solutions;

import common.CommonFunction;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

public class Purchase extends CommonFunction {
    private WebDriver driver;
    public Purchase(WebDriver _driver) {
        super(_driver);
        this.driver = _driver;
        PageFactory.initElements(driver, this);
    }
    public CheckOut fillCheckout;
    @FindBy(id = "coupon_code")
    static WebElement txtCouponCode;
    @FindBy(name = "apply_coupon")
    static WebElement btnApplyCoupon;
    @FindBy(xpath = "//*[@role='alert']")
    static WebElement lblAlert;
    @FindBy(xpath = "//*[@title='Qty']")
    static WebElement txtQuantity;
    @FindBy(xpath = "//*[contains(text(),'Quantity')]")
    static WebElement lblQuantity;
    @FindBy(name = "update_cart")
    static WebElement btnUpdateCart;
    @FindBy(xpath = "//*[contains(text(),'Proceed to checkout')]")
    static WebElement btnProceedToCheckout;

    public By addToCartByProductName(String name){
        String locatorElement = "//*[contains(text(),'"+name+"')]/parent::a/following-sibling::*";
        return By.xpath(locatorElement);
    }
    public By viewCartByProductName(String name){
        String locatorElement = "//*[contains(text(),'"+name+"')]/parent::a/following-sibling::*[@title='View cart']";
        return By.xpath(locatorElement);
    }
    public void addToCartWithCoupon(String productName, String qTy ,String coupon) throws Exception {
        WebElement btnAddCart = driver.findElement(addToCartByProductName(productName));
        clickElement(btnAddCart);
        WebElement btnViewCart = driver.findElement(viewCartByProductName(productName));
        if (btnViewCart.isDisplayed()){
            clickElement(btnViewCart);
            sendkeyElement(txtCouponCode, coupon);
            clickElement(btnApplyCoupon);
            sendkeyElement(txtQuantity, String.valueOf(Integer.parseInt(qTy)));
            clickElement(lblQuantity);
            clickElement(btnUpdateCart);
        } else {
            System.out.println("No actions continue!");
        }
    }
    public void proceedToCheckout() throws Exception {
        if (btnProceedToCheckout.isDisplayed()){
            JavascriptExecutor executor = (JavascriptExecutor)driver;
            executor.executeScript("arguments[0].click();", btnProceedToCheckout);
        } else {
            System.out.println("No actions continue!");
        }
    }

    public void fillCheckout(String firstName, String lastName,String companyName,String address,String TownCity, String phoneNumber, String email, String countryRegionName, String stateCountryName, String postCode) throws Exception {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        fillCheckout = new CheckOut(driver);
        fillCheckout.fillBilling(firstName, lastName, companyName, address, TownCity, phoneNumber, email, countryRegionName, stateCountryName, postCode);
    }
}
