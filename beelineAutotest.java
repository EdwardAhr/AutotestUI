package scenarios;

import PageObject.ConfigHelper;
import PageObject.Directories.CollegialBodiesPage;
import PageObject.Directories.CompaniesPage;
import PageObject.Directories.EmployeesPage;
import PageObject.MainPage;
import PageObject.SignUpPage;
import PageObject.TestHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.Date;

import static java.lang.Thread.sleep;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class beelineAutotest extends ConfigHelper {
    WebDriver driver;
    WebDriverWait wait;
    String mainUserLogin = "Autotester";
    String mainUserPassword = "P@ssw0rd";
    Date DataTime = new Date();
    String NewCreateUserLogin = "SmokeTester" + DataTime;
    String NewCreateUserFio = "SmokeTester " + DataTime;
    String NewCreateCompany = "WebAutomation";
    String NewCreateKO = "WebAutomationCollegial";
    String NewCreateUserEmail = "Barhatov.R@digdes.com";
    String NewCreateCustomer = "beeline";
    String post = "ул Автоматизаторов д 1349";
    String legalPost = "г Москва, ул Автоматизаторов д 1349, 565656";
    String modifiedCompany = " на Ареопаде";
    String customer = "beeline";
    String ko = "AutoTestKO";
    String cbInTable;
    String protocol = getConfig(customer + ".protocol");
    String pm = getConfig(customer + ".pm");

    @BeforeSuite
    public static void setDriver() throws IOException {
        WebDriverManager.chromedriver().setup();

    }

    public void screenShot(){
        TestHelper testHelper = new TestHelper(driver, wait);
        testHelper.makeScreenshot();
    }
    @BeforeClass
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu", "--window-size=1980,1080", "--ignore-certificate-errors", "--disable-extensions", "--no-sandbox", "--disable-dev-shm-usage",
                "--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.71 Safari/537.36");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        driver.manage().window().maximize();
        driver.get("https://beeline-areopad.digdes.com/");
        SignUpPage signUpPage = new SignUpPage(driver, wait);
        signUpPage.logIn(mainUserLogin, mainUserPassword);
    }

    @Test(priority = 1)
   // @Description ("Создание внешнего пользователя")
    public void GenerateExternalUser() throws InterruptedException {

        MainPage mainPage = new MainPage(driver, wait);
        mainPage.burgerBtnClick();
        mainPage.goToDirectories();
        EmployeesPage employeesPage = new EmployeesPage(driver, wait);
        employeesPage.goToEmployees();
        mainPage.CloseMenu();
        employeesPage.openAddModal();
        employeesPage.goToExternalForm();
        employeesPage.fillUserBeeline(NewCreateUserLogin,NewCreateUserFio,NewCreateCompany,NewCreateKO,NewCreateUserEmail,NewCreateCustomer);
        employeesPage.isAddedEmployeeDisplayed(NewCreateUserFio);
    }

    @Test(priority = 2)
    // @Description("Создание предприятие, проверка его добавления, изменение имени предприятия и проверка что изменения применены")
    public void NewCompanyCreationAndCheckTest() throws InterruptedException {
        MainPage mainPage = new MainPage(driver, wait);
        mainPage.preloaderGone();
        mainPage.burgerBtnClick();
        mainPage.addButtonAvailable();
        mainPage.goToDirectories();
        mainPage.CloseMenu();
        String companyName = "Auto " + DataTime;

        CompaniesPage companiesPage = new CompaniesPage(driver, wait);
        companiesPage.goToCompanies();
        companiesPage.listOfEntitiesIsLoaded();
        companiesPage.addCompany(companyName, companyName + "на тестовом стенде", post, legalPost, customer);
        companiesPage.scroll(700);
        companiesPage.goToCompanies();
        companiesPage.isAddedCompanyDisplayed(companyName);
        companiesPage.getInCompany(companyName);
        companiesPage.editShortName(modifiedCompany);
        companiesPage.save();

        companyName = companyName + modifiedCompany;
        sleep(1000);
        companiesPage.isAddedCompanyDisplayed(companyName);
        System.out.println("Первый тест прошел");

    }
    @Test(priority = 3)
    //@Description("Создание КО, проверка его добавления, изменение имени КО")
    public void cBCreationAndCheckTest() throws InterruptedException {
        MainPage mainPage = new MainPage(driver, wait);
        mainPage.burgerBtnClick();
        mainPage.goToDirectories();
        mainPage.CloseMenu();
        CollegialBodiesPage collegialBodiesPage = new CollegialBodiesPage(driver, wait);
        collegialBodiesPage.goToCollegialBody();
        collegialBodiesPage.listOfEntitiesIsLoaded();

            collegialBodiesPage.createKO(customer,NewCreateCompany, "Простое большинство", ko, "Что то там","Юридическая информация" );
        String cbInTable = collegialBodiesPage.cbNameInTable(NewCreateCompany, ko);

        sleep(1000);
        Assert.assertTrue(collegialBodiesPage.isAddedCollegialBodyDisplayed(cbInTable));
    }

    @Test(priority = 4)
  //  @Description("Переход в КО и изменение его названия")
    public void сhangeCBNameTest(){
        CollegialBodiesPage collegialBodiesPage = new CollegialBodiesPage(driver, wait);
        collegialBodiesPage.getInCollegialBody(cbInTable);
        String modifiedKO = "Измененное ";
        collegialBodiesPage.editCBName(modifiedKO, customer);
        collegialBodiesPage.editRule("Не установлены");
        collegialBodiesPage.save();
        screenShot();
            cbInTable = collegialBodiesPage.cbNameInTable(NewCreateCompany, modifiedKO);

        Assert.assertTrue(collegialBodiesPage.isAddedCollegialBodyDisplayed(cbInTable));
        collegialBodiesPage.scroll(700);

    }
    @Test(priority = 5)
  //  @Description("Добавление шаблонов и проверка что изменения применены")
    public void addTemplatesTest(){
        CollegialBodiesPage collegialBodiesPage = new CollegialBodiesPage(driver,wait);
        MainPage mainPage = new MainPage(driver, wait);
        mainPage.burgerBtnClick();
        collegialBodiesPage.getInCollegialBody(cbInTable);
        mainPage.CloseMenu();
        collegialBodiesPage.goToTemplates();
        collegialBodiesPage.fillIntramuralProtocolField(protocol);
        collegialBodiesPage.fillOpinion(pm);
        collegialBodiesPage.fillQuestionnaireField(pm);
//        if(customer.equals("avtodor") || customer.equals("rico") || customer.equals("mmk") || customer.equals("rshb") || customer.equals("expob")){
//            collegialBodiesPage.fillExtractField(extract);
//            collegialBodiesPage.fillAbsenteeMeetingExtractField(extract);
//        }
//
//        if(customer.equals("mmk") || customer.equals("rshb") || customer.equals("avtodor")){
//            collegialBodiesPage.fillAgendaField(agenda);
//            collegialBodiesPage.fillAbsenteeAgendaField(agenda);
//        }
//
//        if(customer.equals("suek")){
//            collegialBodiesPage.fillQuorum(quorum);
//        }
        screenShot();
        collegialBodiesPage.save();
        collegialBodiesPage.ifModalDisplayedThenFail();
    }
    @AfterClass
    public void shotDown() {
        if (driver != null)
            driver.quit();
    }
}