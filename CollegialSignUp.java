package Collegial;

import PageObject.TestHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//import java.awt.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

public class CollegialSignUp extends TestHelper {
    WebDriverWait wait;
    WebDriver driver;
    private By login = By.cssSelector("input[name='login']");
    private By password = By.cssSelector("input[type='password']");
    private By signIn = By.cssSelector("button");
    private By googleEnterBtn = By.cssSelector("button.button");

    public CollegialSignUp(WebDriver driver, WebDriverWait wait){
        super(driver,wait);
        this.driver = driver;
        this.wait = wait;
    }
    private By menu = By.cssSelector("ul.header-menu");

    public void isLoaded(){
        driver.findElement(menu);
    }

    public void openCollegialInNewWindow(String url, String customer) throws AWTException {
        if(customer.equals("test")){
            driver.get(url);
        }
    }
    public void signIn(String userLogin, String userPassword){
        type(login, userLogin);
        type(password, userPassword);
        jsClick(signIn);
        preloaderGone();
        elementClickability(By.cssSelector("nav a.meetings-page-nav__link:nth-child(2)"));
    }

    public void googleSignIn() {
        jsClick(googleEnterBtn);
    }
}
