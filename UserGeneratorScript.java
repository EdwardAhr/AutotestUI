package scenarios;

import PageObject.Directories.EmployeesPage;
import PageObject.MainPage;
import PageObject.SignUpPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

public class UserGeneratorScript {
    WebDriver driver;
    WebDriverWait wait;

    String mainUserLogin = "digdes\\Areopad75";
    String mainUserPassword = "P@ssw0rd";


    String testUserPassword = "P@ssw0rd";


    @BeforeSuite
    public static void setDriver() throws IOException {
        WebDriverManager.chromedriver().setup();

    }
    @BeforeClass
    public void setUp(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu", "--window-size=1980,1080","--ignore-certificate-errors","--disable-extensions","--no-sandbox","--disable-dev-shm-usage",
                "--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.71 Safari/537.36");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        driver.manage().window().maximize();
        driver.get("https://beeline-areopad.digdes.com/");
    }

    @Test
    public void generate() throws InterruptedException {
        SignUpPage signUpPage = new SignUpPage(driver,wait);
        signUpPage.logIn("digdesTest", "P@ssw0rd");
        MainPage mainPage = new MainPage(driver, wait);
        mainPage.goToDirectories();


        EmployeesPage employeesPage = new EmployeesPage(driver, wait);
        employeesPage.goToEmployees();


        for(int i = 46; i <=151; i++) {
            employeesPage.listOfEntitiesIsLoaded();
            employeesPage.openAddModal();
            employeesPage.goToExternalForm();
            String testUser = "LOAD USER";
            String testUserLogin = "Loaduser";
            testUser += i;
            testUserLogin += i;
            //
            employeesPage.fillUser(testUserLogin, testUser, testUserPassword, "Load test",
                        "Load test", "test" + i + "@test.com", "beeline");
//            employeesPage.ifModalDisplayedThenFail();
            employeesPage.isAddedEmployeeDisplayed(testUser);

        }
    }

    @AfterClass
    public void shotDown(){
        if (driver != null)
            driver.quit();
    }

}
