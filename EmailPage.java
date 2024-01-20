package PageObject.SetPasswordFeature;

import PageObject.TestHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EmailPage extends TestHelper {
    WebDriver driver;
    WebDriverWait wait;
    public EmailPage(WebDriver driver, WebDriverWait wait) {

        super(driver, wait);
    }
    private By login = By.cssSelector("input#username");
    private By password = By.cssSelector("input#password");
    private By submit = By.cssSelector("div.signinbutton");

    private By email = By.cssSelector("div._lvv_w");
    private By emailLink = By.cssSelector("#Item\\.MessageUniqueBody > div > div > div > a");
    private By emailObject = By.cssSelector("span.lvHighlightSubjectClass");
    private By emailBody = By.cssSelector("span.ms-font-weight-semilight");

    public void logIn(String email, String pass){
        type(login, email);
        type(password, pass);
        jsClick(submit);
    }

    public void emailIsLoaded(){
        elementAvailability(email);
    }

    public void getInEmail(){
        for(WebElement e : getListOfWebElements(email)){
            System.out.println(e.getText());
            if(e.findElement(emailObject).getText().equals("Задайте пароль для учётной записи") ){
                mouseOver(e);
                jsClick(e);
                jsClick(e);
                break;
            }
        }
    }
    public void goToSetPasswordPage(){
        elementAvailability(emailLink);
        clickOnLink(emailLink);
    }
    public void openEmailPage(){

    }

}
