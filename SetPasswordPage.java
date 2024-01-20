package PageObject.SetPasswordFeature;

import PageObject.TestHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SetPasswordPage extends TestHelper {

    public SetPasswordPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    private By password = By.cssSelector("#app > div > form > div.password-wrapper > div > input");
    private By repeatPass = By.cssSelector("#app > div > form > div.input-field > input");
    private By submit = By.cssSelector("button.btn");
    private By modal = By.cssSelector("div.modal-container.info-modal");

    public void setPassword(String pass){
        type(password, pass);
        type(repeatPass, pass);
        isEnabled(submit);
        click(submit);
        elementAvailability(modal);
    }
    public void returnToAreopadPage(){
        returnToPreviousPage();
    }
}
