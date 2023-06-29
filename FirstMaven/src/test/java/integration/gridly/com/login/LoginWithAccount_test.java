package integration.gridly.com.login;

import base.setup.Driver;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginWithAccount_test extends Driver {

    private WebDriver driver;
    public LoginWithAccount objLogin;
    public LoginWithAccount dylelaje;
    static String usr = "dylelaje@decabg.eu";
    static String pw = "123456789@Test";
    @BeforeClass
    public void setUp() {
        driver = getDriver();
    }

    @DataProvider(name = "account-provider")
    public Object[][] accounts() {
        return new Object[][] {
                {"zxovaxi2463@areosur.com","123456789@Test"},
                {"kugafo@finews.biz","123456789@Test"},
                {"xovaxi2463@areosur.com","123456789@Test"}
        };
    }
    @DataProvider(name = "charsSearch-provider")
    public Object[][] charsSearch() {
        return new Object[][] {
                {""},
                {""},
                {""},
                {""}
        };
    }
    @Test(dataProvider = "account-provider")
    public void loginMultipleAccount(String userName, String pw) throws Exception{
        //use dependency-injection
        objLogin = new LoginWithAccount(driver);
        objLogin.loginWithAcc(userName, pw);
    }
    @Test
    public void loginSimpleAccount() throws Exception{
        //use dependency-injection
        dylelaje = new LoginWithAccount(driver);
        dylelaje.loginWithAcc(usr, pw);
        dylelaje.companyRole();
    }
}
