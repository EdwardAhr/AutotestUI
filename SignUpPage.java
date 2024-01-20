package PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignUpPage extends TestHelper {
    WebDriver driver;
    WebDriverWait wait;
    public SignUpPage(WebDriver driver, WebDriverWait wait){
        super(driver, wait);
        this.driver = driver;
        this.wait = wait;
    }

    By login = By.cssSelector("input[type='text']");
    By password = By.cssSelector("input[type='password']");
    By submit = By.cssSelector("input[type='submit']");
    By googleEnterBtn = By.cssSelector("div a");
    By googleMailInput = By.cssSelector("input#identifierId");
    By googlePasswordInput = By.cssSelector("input[name='password']");
    By googleNextBtn = By.cssSelector("div#identifierNext button");
    By googlePassNextBtn = By.cssSelector("div#passwordNext button");

    public void isLoaded() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(login));
    }

    public void logIn(String log, String pass){
        elementAvailability(login);
        type(login, log);
        type(password, pass);
        click(submit);
    }

    public void googleLogIn(String mail, String pass) {
        jsClick(googleEnterBtn);
        jsClick(googleMailInput);
        type(googleMailInput, mail);
        jsClick(googleNextBtn);
        jsClick(googlePasswordInput);
        type(googlePasswordInput, pass);

        jsClick(googlePassNextBtn);

    }
}
