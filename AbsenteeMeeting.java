package scenarios;

import PageObject.ConfigHelper;
import PageObject.CreateNewMeetingPage;
import PageObject.MainPage;
import PageObject.SignUpPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class AbsenteeMeeting extends ConfigHelper {
    WebDriver driver;
    WebDriverWait wait;
    String rightDivision;
    String collegialDepartment;
    Boolean right = true;
    String materialsPath;
    String customer;
    String meetingNumber;
    String meetingName;

    @Test
    public void testT(){
        System.out.println(getConfig("dkudzo.pm"));
    }

//    @BeforeSuite
//    public static void setDriver(){
//        WebDriverManager.chromedriver().setup();
//    }
//
//    @BeforeClass
//    public void setUp(){
//        driver = new ChromeDriver();
//        wait = new WebDriverWait(driver, 40);
//        driver.manage().window().maximize();
//        customer = getConfig("customer");
//
//        driver.get(getCustomerUrl(customer));
//
//        rightDivision = getConfig("division");
//        collegialDepartment = getConfig("collegial");
//        materialsPath = getConfig("materialsPath");
//
//        SignUpPage signUpPage = new SignUpPage(driver, wait);
//        signUpPage.logIn(getConfig("login"),
//                getConfig("password"));
//    }
//
//    @AfterMethod
//    public void onTestFailure(ITestResult tr) {
//        if(!tr.isSuccess()){
//            makeScreenshot();
//        }
//    }
//
//    @Attachment(value = "Attachment Screenshot", type = "image/png")
//    public byte[] makeScreenshot() {
//        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
//    }
//
//    @Test
//    public void createAbsenteeMeeting(){
//        MainPage mainPage = new MainPage(driver, wait);
//        mainPage.addMeeting();
//
//        CreateNewMeetingPage createNewMeetingPage = new CreateNewMeetingPage(driver, wait);
//        createNewMeetingPage.waitMeetingCreatModal();
//        createNewMeetingPage.fillMeeting(rightDivision, collegialDepartment, "aaa","Место для заочного заседания 33", customer);
//        createNewMeetingPage.makeMeetingAbsentee();
//
//        Date date = new Date(System.currentTimeMillis()+86400000);
//        Date date2 = new Date(System.currentTimeMillis()+86400000*7);
//        System.out.println(date);
//        long cur = date.getTime();
//        long next = date2.getTime();
//        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
//        String startDate = sdf.format(cur);
//        String endDate = sdf.format(next);
//        System.out.println(startDate);
//        System.out.println(createNewMeetingPage.isOnlyEndDate());
//        createNewMeetingPage.setStartDate(startDate);
//        createNewMeetingPage.setEndDate(endDate);
//        createNewMeetingPage.submitMeetingCreation();
//
//    }
//    @AfterClass
//    public void shotDown(){
//        if (driver != null)
//            driver.quit();
//    }

}
