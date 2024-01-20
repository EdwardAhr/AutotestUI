package scenarios;

import PageObject.ConfigHelper;
import PageObject.Directories.CollegialBodiesPage;
import PageObject.Directories.CompaniesPage;
import PageObject.Directories.EmployeesPage;
import PageObject.MainPage;
import PageObject.SetPasswordFeature.EmailPage;
import PageObject.SetPasswordFeature.SetPasswordPage;
import PageObject.SignUpPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import static java.lang.Thread.sleep;

public class CompanyKOEmployeesTest extends ConfigHelper {
    String customer;
    WebDriver driver;
    WebDriverWait wait;
    String id;
    String company;
    String post = "ул Автоматизаторов д 1349";
    String legalPost = "г Москва, ул Автоматизаторов д 1349, 565656";
    String modifiedCompany = " на Ареопаде";
    String kO = "Automation KO";
    String modifiedKO = "Измененое АКО";
    String cbInTable;
    String userName;
    String userPass;
    String userLogin;

    @BeforeSuite
    public static void setDriver(){
        WebDriverManager.chromedriver().setup();
    }

    @BeforeClass
    public void setUp(){
        driver = new ChromeDriver();
                wait = new WebDriverWait(driver, Duration.ofSeconds(40) );
        driver.manage().window().maximize();
        customer = getConfig("customer");
        userName = getConfig("userName");
        userPass = getConfig("userPass");
        userLogin = getConfig("userLogin");

        driver.get(getCustomerUrl(customer));

        SignUpPage signUpPage = new SignUpPage(driver, wait);
        signUpPage.logIn(getConfig("login"),
                getConfig("password"));

    }

    @AfterMethod
    public void onTestFailure(ITestResult tr) {
        if(!tr.isSuccess()){
            makeScreenshot();
        }
    }

    @Attachment(value = "Attachment Screenshot", type = "image/png")
    public byte[] makeScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Test
    public void newCompanyCreationAndCheckTest() throws InterruptedException {
        MainPage mainPage = new MainPage(driver, wait);
        mainPage.goToDirectories();

        CompaniesPage companiesPage = new CompaniesPage(driver, wait);
        companiesPage.goToCompanies();
        mainPage.overlayGone();
        companiesPage.listOfEntitiesIsLoaded();
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formatterData= new SimpleDateFormat("dd.MM.yyyy'T'HH:mm");
        id = formatterData.format(date);
        company = "Для Автотеста" + id;
        companiesPage.addCompany(company, company + "на тестовом стенде", post, legalPost, customer);
        companiesPage.isAddedCompanyDisplayed(company);
        companiesPage.getInCompany(company);
        companiesPage.editShortName(modifiedCompany);
        companiesPage.save();
        company += modifiedCompany;
        companiesPage.isAddedCompanyDisplayed(company);

//        Assert.assertEquals(directoriesPage.getInCompany("ЗАО \"Диджитал Дизайн\""), "ЗАО \"Диджитал Дизайн\"");
    }

    @Test
    public void cBCreationAndCheckTest() throws InterruptedException {
        MainPage mainPage = new MainPage(driver, wait);
        mainPage.goToDirectories();
        mainPage.overlayGone();
        CollegialBodiesPage collegialBodiesPage = new CollegialBodiesPage(driver, wait);
        collegialBodiesPage.goToCollegialBody();
        collegialBodiesPage.listOfEntitiesIsLoaded();
        collegialBodiesPage.createKO(customer,company, "Простое большинство", kO, "Что то там","Юридическая информация" );
        mainPage.overlayGone();
        cbInTable = collegialBodiesPage.cbNameInTable(company, kO);
        Assert.assertTrue(collegialBodiesPage.isAddedCollegialBodyDisplayed(cbInTable));
        collegialBodiesPage.getInCollegialBody(cbInTable);
        collegialBodiesPage.editCBName(modifiedKO, customer);
        collegialBodiesPage.editRule("Не установлены");
        collegialBodiesPage.save();
        cbInTable = collegialBodiesPage.cbNameInTable(company, modifiedKO);
        Assert.assertTrue(collegialBodiesPage.isAddedCollegialBodyDisplayed(cbInTable));
       if(customer.equals("test")){
           collegialBodiesPage.getInCollegialBody(cbInTable);
           collegialBodiesPage.goToQuestionTypes();
           collegialBodiesPage.setRuleInQuestionType("Новый тип","Простое большинство");
           makeScreenshot();
           collegialBodiesPage.save();
       }
    }

//    @Test
//    public void deleteCompanyTest(){
//        MainPage mainPage = new MainPage(driver, wait);
//        mainPage.goToDirectories();
//        CompaniesPage companiesPage = new CompaniesPage(driver, wait);
//        companiesPage.goToCompanies();
//        mainPage.preloaderGone();
//        companiesPage.listOfEntitiesIsLoaded();
//        int beforeDelete = companiesPage.companiesAmount();
////        companiesPage.scrollDownPage(1000);
//        companiesPage.deleteCompany("Для Автотеста после изменения");
//        companiesPage.alertOk();
//        companiesPage.refresh();
//        companiesPage.goToCompanies();
////        companiesPage.scrollDownPage(1000);
//        companiesPage.listOfEntitiesIsLoaded();
//        int afterDelete = companiesPage.companiesAmount();
//        Assert.assertEquals(afterDelete, beforeDelete-1);
//
//    }

    @Test
    public void createUserTest() throws InterruptedException {
        MainPage mainPage = new MainPage(driver, wait);
        mainPage.goToDirectories();
        EmployeesPage employeesPage = new EmployeesPage(driver, wait);
        employeesPage.goToEmployees();
        employeesPage.listOfEntitiesIsLoaded();
        employeesPage.openAddModal();
        employeesPage.goToExternalForm();
        userLogin += id;
        userLogin = userLogin.replaceAll("\\.","");
        userLogin = userLogin.replaceAll("\\:", "");
        userName +=id;
        System.out.println(userLogin);
        employeesPage.fillUser(userLogin, userName, userPass,company,
                modifiedKO,"pimenov_test1@digdes.com", customer);
        employeesPage.isAddedEmployeeDisplayed(userName);
    }
    @Test
    public void setPasswordTestAndLogInTest() throws InterruptedException {
        MainPage mainPage = new MainPage(driver, wait);
        mainPage.logOut();
        SignUpPage signUpPage = new SignUpPage(driver, wait);
        if(customer.equals("rshb") || customer.equals("test")){
            driver.get("https://mail.digdes.com/owa");
            EmailPage emailPage = new EmailPage(driver, wait);
//            emailPage.logIn();
            emailPage.emailIsLoaded();
            emailPage.getInEmail();
            emailPage.goToSetPasswordPage();
            SetPasswordPage setPasswordPage = new SetPasswordPage(driver, wait);
            setPasswordPage.setPassword("P@ssw0rdA5t0");
            driver.get(getCustomerUrl(customer));
        }
        signUpPage.isLoaded();
        sleep(15000);
        signUpPage.logIn(userLogin, "P@ssw0rdA5t0");
        Assert.assertEquals(mainPage.checkUser(), userName);
    }
    @Test
    public void logOutAndLoginUnderNewUserTest(){
        MainPage mainPage = new MainPage(driver, wait);
        mainPage.logOut();
        SignUpPage signUpPage = new SignUpPage(driver, wait);
        signUpPage.isLoaded();
        signUpPage.logIn(userLogin, userPass);

    }

    @AfterClass
    public void shotDown(){
        if (driver != null)
            driver.quit();
    }
}
