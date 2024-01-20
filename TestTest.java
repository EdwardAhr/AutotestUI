package scenarios;

import Collegial.CollegialMeetingCardPage;
import Collegial.CollegialMeetingsPage;
import Collegial.CollegialSignUp;
import PageObject.*;
import PageObject.TokenModel.TokenModel;
import com.google.gson.Gson;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import static java.lang.Thread.sleep;

public class TestTest extends ConfigHelper {
    String fromTimeMain = null; //Время ОТ, передаяется во время создания заседания, используются для нахождения созданного заседания
    String currentDate = null;
    WebDriver driver;
    WebDriverWait wait;
    String companyName;
    String meetingNumber;
    String meetingName;
    String meetingId;
    Boolean right = true;
    String id;
    String collegialDepartment;

    String post = "ул Автоматизаторов д 1349";
    String legalPost = "г Москва, ул Автоматизаторов д 1349, 565656";
    String modifiedCompany = " на Ареопаде";
    String kO = "Automation KO";
    String modifiedKO = "Измененое АКО";
    String cbInTable;
    String mainUser = "AutoUser AutoUser AutoUser";
    TestHelper testHelper;
    String questionId;
    String customer;
    String userName;
    String userPass;
    String userLogin;
    String protocol;
    String pm;
    String company;
    String mainUserPassword;
    String mainUserLogin;
    String rest;
    String quorum;
    String extract;
    String agenda;
    String copiedQuestionName;
    String projectDecisionCopiedQuestion;
    String toTime;
    String packageTitle;

    int questionsSizeOnQuestionsPage;
    int questionsSizeInAgenda;
    int materialsAmountInQuestionFromMainPage = 5;
    int materialsAmountInNewQuestion = 4;
    int i = 0;

    @BeforeSuite
    public static void setDriver() throws IOException {
        WebDriverManager.chromedriver().setup();

//        WebDriverManager.firefoxdriver().setup();
    }

    @BeforeClass
    public void setUp()  {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu", "--window-size=1980,1080","--ignore-certificate-errors","--disable-extensions","--no-sandbox","--disable-dev-shm-usage");
        driver = new ChromeDriver();
//        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        driver.manage().window().maximize();
        collegialDepartment = getConfig("collegial");


        String customerParamFromEnv = System.getProperty("customer");
        customer = customerParamFromEnv == null ? getConfig("customer") : customerParamFromEnv;


        userName = getConfig("userName");
        userPass = getConfig("userPass");
        userLogin = getConfig("userLogin");
        protocol = getConfig(customer + ".protocol");
        extract = getConfig(customer + ".extract");
        agenda = getConfig(customer + ".agenda");
        pm = getConfig(customer + ".pm");
        quorum = getConfig("suek.quorum");
        company = getConfig("division");
        mainUserPassword = getConfig("password");
        mainUserLogin = getConfig("login");
        rest = getConfig(customer + ".rest");


        driver.get(getCustomerUrl(customer));

        SignUpPage signUpPage = new SignUpPage(driver, wait);
        if (customer.equals("alrosa")){
            signUpPage.googleLogIn("areopad.autotest@gmail.com","Are0pad1");
        }else{
            signUpPage.logIn(mainUserLogin, mainUserPassword);
        }
    }



    @AfterMethod
    public void onTestFailure(ITestResult tr) {
        if(!tr.isSuccess()){
            screenShot();
        }
    }

    public void screenShot(){
        testHelper = new TestHelper(driver,wait);
        testHelper.makeScreenshot();
    }



//    @Test
//    public void changeDate(){
//        System.out.println(Integer.valueOf("01"));
//        MainPage mainPage = new MainPage(driver, wait);
//        mainPage.goToMeetings();
//        MeetingsPage meetingsPage = new MeetingsPage(driver, wait);
//        mainPage.addMeeting();
//        mainPage.preloaderGone();
//        SimpleDateFormat formatter= new SimpleDateFormat("HH:mm");
//
//        Date date = new Date(System.currentTimeMillis()+4*3600000+60000*10);
//        fromTimeMain = formatter.format(date);
//        System.out.println("FromTimeMain: " + fromTimeMain);
//        long curTimeInMs = date.getTime();
//        Date afterAddingMins = new Date(curTimeInMs + 60000*10);
//        fromTimeMain = formatter.format(date);
//        System.out.println("FromTimeMain: " + fromTimeMain);
//
//
//        toTime = formatter.format(afterAddingMins);
//        System.out.println("To time: " + toTime);
//        SimpleDateFormat formatterData= new SimpleDateFormat("dd.MM.yyyy");
//        currentDate = formatterData.format(date);
//        System.out.println("Current date: " + currentDate);
//
//        CreateNewMeetingPage createNewMeetingPage = new CreateNewMeetingPage(driver,wait);
//        createNewMeetingPage.waitMeetingCreatModal();
//
//        createNewMeetingPage.startDate(fromTimeMain);
//        createNewMeetingPage.finishDate(toTime);
//
//        SimpleDateFormat lastDate= new SimpleDateFormat("dd.MM.yyyy");
//        Date pastDate = new Date(System.currentTimeMillis()-1000*60*60*24);
//        String d = lastDate.format(pastDate);
//        createNewMeetingPage.setMeetingDate(d);
//        try {
//            sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//
//        //стандартное имя для большинства заказчиков но оно может отличаться
//        if(customer.equals("dkudzo")){
//            meetingName = meetingsPage.meetingName(fromTimeMain, currentDate, company, modifiedKO);
//        }else{
//            meetingName = meetingsPage.meetingName(fromTimeMain, currentDate,  " WebAutomation", "WebAutomationCollegial");
//        }
//
//        if (customer.equals("dkudzo")){
//            createNewMeetingPage.fillMeeting(companyName, modifiedKO, meetingName,"Место проведения для автотестов", customer);
//        }else{
//            createNewMeetingPage.fillMeeting("WebAutomation", "WebAutomationCollegial", meetingName,"Место проведения для автотестов", customer);
//        }
//
//        System.out.println(meetingName);
//        if(customer.equals("avtodor") || customer.equals("dkudzo")){
//            Assert.assertTrue(createNewMeetingPage.isCheckboxChecked());
//            createNewMeetingPage.disableEndManually();
//        }
//        if(customer.equals("avtodor")){
//            meetingNumber = createNewMeetingPage.getMeetingNumber();
//            System.out.println("Meeting number " + meetingNumber);
//        }
//
//        createNewMeetingPage.submitMeetingCreation();
//
//
//        mainPage.pickDayInCalendar(d);
//        try {
//            sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void linkSkype(){
//        CollegialSignUp collegialSignUp = new CollegialSignUp(driver, wait);
//        driver.get(getConfig(customer + ".collegial"));
//
//        collegialSignUp.signIn(mainUserLogin, mainUserPassword);
//
//        CollegialMeetingsPage collegialMeetingsPage = new CollegialMeetingsPage(driver, wait);
//        collegialMeetingsPage.getInRightMeetingCollegial("WebAutomationCollegial WebAutomation, 03.12.2021 в 19:25, очное заседание", customer);
////
////        collegialMeetingsPage.goToAnotherTab();
////        collegialMeetingsPage.refresh();
////        collegialMeetingsPage.preloaderGone();
//        String w = collegialMeetingsPage.getWindowHandle();
//        if(customer.equals("chtpz") || customer.equals("rimera") || customer.equals("alrosa")){
//            Assert.assertTrue(collegialMeetingsPage.isSkypeDisplayed());
//
//            collegialMeetingsPage.clickSkypeLink();
//            collegialMeetingsPage.goToAnotherTab();
//            System.out.println(collegialMeetingsPage.getUrl());
//            Assert.assertEquals(collegialMeetingsPage.getUrl(),"https://www.skype.com/ru/");
//
//            try {
//                sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            collegialMeetingsPage.closeTab();
//            try {
//                sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            collegialMeetingsPage.switchToWindow(w);
//            Assert.assertTrue(collegialMeetingsPage.isSkypeDisplayed());
//        }

//    }
//    @Test
//    public void ts(){
//        MeetingsPage meetingsPage = new MeetingsPage(driver,wait);
//        meetingsPage.clickRightMeeting("fdsfdsfdsfds",
//                "alrosa", "");
//        MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver,wait);
//        meetingPageEdit.goToMaterialsTabInMeeting();
//        String[] paths = {
//                "C:\\Users\\Pimenov.Alex\\Desktop\\Auto\\test_automation_web\\src\\test\\java\\TestData\\Attach\\Тайна.docx"
//        };
//        meetingPageEdit.addTradeSecret(paths);
//        meetingPageEdit.addPasswordToTradeSecret("P@ssw0rd");
//        meetingPageEdit.saveButtonClick();
//        meetingPageEdit.preloaderGone();
//        try {
//            sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Assert.assertFalse(meetingPageEdit.checkEveryStatus().contains("Error"));
//        Assert.assertTrue(meetingPageEdit.isTradeSecretAdded());
//
//        try {
//            sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//    @Test
//    @Description("Добавить папку в библиотеку")
//    public void add1(){
//    LibraryPage libraryPage = new LibraryPage(driver,wait);
//    libraryPage.goToLibrary();
//        SimpleDateFormat formatter= new SimpleDateFormat("HH:mm");
//
//        Date date = new Date(System.currentTimeMillis()+4*3600000+60000*10);
//        id = formatter.format(date);
//    packageTitle = "Auto Folder " + id;
//    libraryPage.addFolderInMainFolder(packageTitle);
//    Assert.assertTrue(libraryPage.isAddedFolder(packageTitle));
//        try {
//            sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

//    @Test
//    @Description("Добавить документ в папку")
//    public void add2(){
//        LibraryPage libraryPage = new LibraryPage(driver,wait);
//        String[] paths = {
//                "C:\\Users\\Pimenov.Alex\\Desktop\\Auto\\test_automation_web\\src\\test\\java\\TestData\\Attach\\Документ библиотеки.docx"
//        };
//        libraryPage.addDocumentIntoFolder(paths);
//        libraryPage.save();
//        try {
//            sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        libraryPage.refresh();
//        Assert.assertTrue(libraryPage.isDocumentAdded("Документ библиотеки.docx"));
//        try {
//            sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        libraryPage.setAccessForDocument("Документ библиотеки.docx", modifiedCompany, modifiedKO, mainUser);
//    }
    @Test
    public void hghg(){
        MeetingsPage meetingsPage = new MeetingsPage(driver,wait);
        meetingsPage.clickRightMeeting("Измененое АКО 18.01.2022 07:48 Авто18.01.2022 07:48 на Ареопаде, 18.01.2022 в 12:30, очное заседание","mmk","");

        MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver,wait);
        meetingPageEdit.goToVoteResultsTab();

        meetingPageEdit.voteResultsInFirstQuestionMMK(mainUser);
    }



}
