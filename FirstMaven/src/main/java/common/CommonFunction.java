package common;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommonFunction {
    static WebDriver driver;
    static Actions actions;
    public static WebDriverWait wait;
    public CommonFunction (WebDriver _driver){
        this.driver = _driver;
        actions = new Actions(_driver);
        wait = new WebDriverWait(_driver, Duration.ofSeconds(GlobalVariable.LONG_TIME_OUT));
        PageFactory.initElements(driver, this);
    }
    public static String currentDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
        return dtf.format(now);
    }
    public void changeWindowSize(Integer width, Integer height) {
        driver.manage().window().setSize(new Dimension(width, height));
    }
    public static void sendkeyElement(WebElement element, String text) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            actions.moveToElement(element);
            element.sendKeys(Keys.chord(Keys.CONTROL,"a"));
            element.sendKeys(text);
        } catch (Exception e){
            System.out .println(String.format("- Element %s does not exist : ",element));
            System.out.println(e);
        }
    }

    public static void clickElement(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            actions.moveToElement(element);
            element.click();
        } catch (Exception e){
            System.out .println(String.format("- Element %s does not exist : ",element));
            System.out.println(e);
        }
    }
    public static boolean clickAble(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
        } catch (Exception e){
            System.out .println(String.format("- Element %s does not exist : ",element));
            System.out.println(e);
        }
        return false;
    }
    public static void pressEnter() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.delay(100);

            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.delay(100);

        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public String getPathImage(String folderImage) {
        String projectPath = System.getProperty("project.path");
        Path path = Paths.get(projectPath, folderImage);
        return path.toString();
    }

    public static String generateString(int leng) {
        SecureRandom strRD = new SecureRandom();
        StringBuilder sb = new StringBuilder(leng);
        for (int i = 0; i < leng; i++)
            sb.append(GlobalVariable.ALPHABET.charAt(strRD.nextInt(GlobalVariable.ALPHABET.length())));
        return sb.toString();
    }


    public static File screenShotElement(WebElement element) throws Exception {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        wait.until(ExpectedConditions.visibilityOf(element));
        BufferedImage fullImg = ImageIO.read(screenshot);

        int eleWidth = element.getSize().getWidth();
        int eleHeight = element.getSize().getHeight();
        System.out.println("Width element: " + eleWidth);
        System.out.println("Hight element: " + eleHeight);

        Point point = element.getLocation();
        System.out.println("Location of element: " + point);

        BufferedImage eleScreenshot = fullImg.getSubimage(point.getX(), point.getY(),
                eleWidth, eleHeight);
        ImageIO.write(eleScreenshot, GlobalVariable.TYPE_IMG, screenshot);

        File screenshotLocation = new File(GlobalVariable.LOCATION_IMAGE + "element_" + generateString(10) + ".png");
        FileUtils.copyFile(screenshot, screenshotLocation);
        return screenshotLocation;
    }
    public static File screenShotPage(String folderName) throws Exception {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Thread.sleep(3000);
        // Copy the element screenshot to disk
        File screenshotLocation = new File(GlobalVariable.LOCATION_IMAGE + folderName + currentDate() + ".png");
        FileUtils.copyFile(screenshot, screenshotLocation);
        System.out.println(screenshotLocation.getName());
        return screenshotLocation;
    }

    public void comparison2Img(File pathImg_1, File pathImg_2) throws Exception {
        File img1 = new File(GlobalVariable.LOCATION_IMAGE + pathImg_1.getName());
        File img2 = new File(GlobalVariable.LOCATION_IMAGE + pathImg_2.getName());

        BufferedImage readImg1 = ImageIO.read(img1);
        DataBuffer getDataImg1 = readImg1.getData().getDataBuffer();
        int sizeImg1 = getDataImg1.getSize();

        BufferedImage readImg2 = ImageIO.read(img2);
        DataBuffer getDataImg2 = readImg2.getData().getDataBuffer();
        int sizeImg2 = getDataImg2.getSize();

        System.out.println("sizeImg1 = " + sizeImg1);
        System.out.println("sizeImg2 = " + sizeImg2);

        Boolean matchFlag = true;
        if (sizeImg1 == sizeImg2) {
            for (int i = 0; i < sizeImg1; i++) {
                if (getDataImg1.getElem(i) != getDataImg2.getElem(i)) {
                    matchFlag = false;
                    break;
                }
            }
            System.out.println("matchFlag = " + matchFlag);
        }
        if (matchFlag == false) {
            System.out.println("Images are not same");
        } else {
            System.out.println("Images are same");
        }
    }
    public void openNewTab(){}
    public String prototype(@NotNull String url){
        String http = url.startsWith("http://") ? "0" : "1";
        if(http=="0"){
            return "http";
        }
        return "https";
    }
}
