package PageObject.Directories;

import PageObject.TestHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class QuestionsTypesPage extends TestHelper {

    public QuestionsTypesPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    By createBtn = By.cssSelector("button.btn.btn-success");
    By saveBtn = By.cssSelector("input[value='Сохранить']");
    By modal = By.cssSelector("div.modal-content");
    By questionTypeName = By.cssSelector("div.modal-content input");
    By organizationCheckBox = By.cssSelector("div.modal-body > div > div.ng-scope > div:nth-child(1) > div > label");
    By materialsCheckBox = By.cssSelector("div div[ng-show=\"item.isOrganizational\"]:nth-child(3) div:nth-child(2) label");

    public void addOrganizationType(String name){
        jsClick(createBtn);
        elementAvailability(modal);
        type(questionTypeName, name);
        jsClick(organizationCheckBox);

    }

    public void disableMaterials(){
        jsClick(materialsCheckBox);
    }

    public void submitCreation(){
        jsClick(saveBtn);
        preloaderGone();
    }


    public boolean isOrganizationQuestion(String organizationalQuestionName) {
        Boolean res = false;
        for(WebElement ele : getListOfWebElements(By.cssSelector("td.ng-binding"))){
            if(ele.getText().equals(organizationalQuestionName)){
                res = true;
                break;
            }
        }
        return res;
    }

    public void goToQuestionTypes() {
        jsClick(By.cssSelector("ul.nav.nav-tabs li:nth-child(4) a"));
    }
}
