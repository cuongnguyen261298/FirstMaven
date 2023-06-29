package demos.bellatrix.solutions;

import base.setup.Driver;
import net.bytebuddy.implementation.bind.annotation.Default;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static common.CommonFunction.generateString;

public class PurchaseTest extends Driver implements CheckingOut{
    private WebDriver driver;
    static String coupon = "happybirthday";
    static String qTy = "2";
    static String rd_10 = generateString(10);
    @BeforeClass
    public void setUp() {
        driver = getDriver();
    }
    public Purchase falcon9;
    @Test
    public void purchaseItemSuccessfully() throws Exception{
        falcon9 = new Purchase(driver);
        falcon9.addToCartWithCoupon("Falcon 9", qTy, coupon);
        Assert.assertEquals(Purchase.lblAlert.getText(), "Coupon code applied successfully.");
        falcon9.proceedToCheckout();
        falcon9.fillCheckout(firstName, lastName, companyName, address, townCity, phoneNumber, email, countryRegionName, stateCountryName, postCode);
    }
}
