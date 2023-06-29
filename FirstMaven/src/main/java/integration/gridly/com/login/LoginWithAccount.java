package integration.gridly.com.login;

import common.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class LoginWithAccount extends CommonFunction {
    private WebDriver driver;
    public LoginWithAccount(WebDriver _driver){
        super(_driver);
        this.driver = _driver;
        PageFactory.initElements(driver, this);
    }
    @FindBy(xpath = "//*[@name='username']")
    static WebElement txtEmail;
    @FindBy(xpath = "//*[@name='password']")
    static WebElement txtPassWord;
    @FindBy(xpath = "//*/button[@type='submit']/*[contains(text(),'Sign in')]")
    static WebElement btnSignIn;
    @FindBy(id = "header-user")
    static List<WebElement> headerUsers;
    @FindBy(xpath = "(//*[@id='workspaceList'])[2]")
    static List<WebElement> workspaceList;
    @FindBy(xpath = "//*[@tr-dt='Open User Profile']")
    static List <WebElement> btnUserProfile;
    @FindBy(xpath = "//*[contains(text(),'Log out')]")
    static WebElement btnLogOut;
    @FindBy(xpath = "//*[@href='/company-settings/groups']")
    static WebElement Groups;
    @FindBy(xpath = "//*[@href='/company-settings/members']")
    static WebElement Members;
    @FindBy(xpath = "//*[@tr-dt='Open General Setting']")
    static WebElement btnGeneralSetting;
    @FindBy(xpath = "//*[contains(text(),'Company settings')]")
    static WebElement btnCompanySetting;
    @FindBy(xpath = "//*[contains(text(),'Localization settings')]")
    static WebElement localizationSetting;
    @FindBy(xpath = "//*[contains(text(),'Integration settings')]")
    static WebElement integrationSetting;
    @FindBy(xpath = "//*[contains(text(),'Switch company')]")
    static WebElement switchCompany;
    @FindBy(id = "groupList")
    static List<WebElement> groupList;
    @FindBy(xpath = "//*[@name ='code']")
    static WebElement digitsCode;
    @FindBy(xpath = "//*[contains(text(),'Bad credentials')]")
    static WebElement lblBadCredentials;
    @FindBy(xpath = "//*[contains(text(),'Try again')]")
    static WebElement btnTryAgain;
    public void loginWithAcc(String usr, String pw) throws Exception {
        sendkeyElement(txtEmail, usr);
        sendkeyElement(txtPassWord, pw);
        clickElement(btnSignIn);
        if (lblBadCredentials.isDisplayed()) {
            System.out.println("User: " + usr + " login failed! => " + lblBadCredentials.getText());
            screenShotPage("integration.gridly.com/login");
            System.out.println("Stopped.");
        }
        System.out.println("Login successfully!");
        int btnProfile = btnUserProfile.isEmpty() ? 0 : 1;
        if (btnProfile == 0){
            System.out.println("- Element "+ btnUserProfile + "doesn't exist!");
            System.out.println("Stopped.");
        }
//        while(wait.until(ExpectedConditions.visibilityOf(lblBadCredentials))){
//
//        }
    }
    public void logout(String usr){
        int key = 1;
        clickElement((WebElement) btnUserProfile);
        clickElement(btnLogOut);
        System.out.println(usr + "logout successfully");
    }
    public String companyRole(){
        String result = "USER";
        clickElement(btnCompanySetting);
        Boolean companySettingDisplay = btnCompanySetting.isDisplayed()? true : false;
        if (companySettingDisplay == true) {
            result = "ADMIN";
            System.out.println("Is admin");
        }
        return result;
    }
    public void getWorkSpaceList() throws InterruptedException {
        Actions actions = new Actions(driver);
        if(!workspaceList.isEmpty()){
            System.out.println(workspaceList.size());
            for(int i= 0; i< workspaceList.size(); i++){
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                actions.scrollToElement(workspaceList.get(i));
                System.out.println(workspaceList.get(i).getText());
            }
        }
    }
    public void tryAgain() throws InterruptedException {
        if(btnTryAgain.isDisplayed()){
            btnTryAgain.click();
        }
    }
}
