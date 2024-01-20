package PageObject.Directories;

import PageObject.TestHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CompaniesPage extends TestHelper {

    public CompaniesPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }
    private By companies = By.cssSelector("ul.nav.nav-tabs li:nth-child(1)");
    private By addBtn = By.cssSelector("button.btn.btn-success");
    private By overlay = By.cssSelector("div.overlay");

    //Modal
    private By shortCompanyName = By.cssSelector("div.form-group.row:nth-child(2) input");
    private By fullCompanyName = By.cssSelector("div.form-group.row:nth-child(3) input");
    private By postAddress = By.cssSelector("div.form-group.row:nth-child(4) input");
    private By legalAddress = By.cssSelector("div.form-group.row:nth-child(5) input");
    private By tin = By.cssSelector("div.form-group.row:nth-child(6) input");//rshb
    private By createBtn = By.cssSelector("input[value='Создать']");
    private By saveBtn = By.cssSelector("input[value='Сохранить']");
    private By modal = By.cssSelector("div.modal-content");

    //Tabs

    //Templates fields
    private By intramuralProtocolField = By.cssSelector("div.ng-scope div:nth-child(1) input");
    private By absenteeProtocolField = By.cssSelector("div.ng-scope div:nth-child(2) input");
    private By opinion = By.cssSelector("div.ng-scope div:nth-child(2) input");
    private By questionnaireField = By.cssSelector("div.ng-scope div:nth-child(4) input");
    private By opinionConsolidatedQuestionnaireField  = By.cssSelector("div.ng-scope div:nth-child(5) input");
    private By extractField  = By.cssSelector("div.ng-scope div:nth-child(6) input");
    private By absenteeMeetingExtractField  = By.cssSelector("div.ng-scope div:nth-child(7) input");
    private By agendaField  = By.cssSelector("div.ng-scope div:nth-child(8) input");
    private By absenteeAgendaField  = By.cssSelector("div.ng-scope div:nth-child(9) input");
    private By meetingNotificationField  = By.cssSelector("div.ng-scope div:nth-child(10) input");
    private By absenteeMeetingNotificationField = By.cssSelector("div.ng-scope div:nth-child(11) input");
    private By uDPTitle = By.cssSelector("div.ng-scope div:nth-child(12) input");

    //Page
    private By listOfEntities = By.cssSelector("table.table");
    private By companyBlock = By.cssSelector("tr.dict__groups.ng-scope");
    private By entityEditLink = By.cssSelector("a:nth-child(1)");
    private By entityDeleteLink = By.cssSelector("a:nth-child(2)");
    //alert
    private By alert = By.cssSelector("div.alertify-dialog");
    private By ok = By.cssSelector("button#alertify-ok");
    private By cancel = By.cssSelector("button#alertify-cancel");

    public void goToCompanies(){
        click(companies);
        overlayGone();
    }

    public void addCompany(String shortName, String fullName, String post, String legalPost, String customer) throws InterruptedException {
        click(addBtn);
        gone(overlay);
        elementAvailability(modal);
        type(shortCompanyName, shortName);
        type(fullCompanyName, fullName);
        type(postAddress, post);
        type(legalAddress, legalPost);
        if(customer.equals("rshb")){
            type(tin, randomNumber(10, 10));
            submitCreation();
            Boolean al = alertIsDisplayed(alert);
            while(al == true){
                alertCancel();
                clearField(tin);
                type(tin, randomNumber(10,10));
                submitCreation();
                al = alertIsDisplayed(alert);
            }
        }else{
            submitCreation();
        }


    }
    public void submitCreation(){
        elementAvailability(createBtn);
        jsClick(createBtn);
    }

    public void save(){
        elementAvailability(saveBtn);
        jsClick(saveBtn);
    }

    public Boolean isAddedCompanyDisplayed(String name){
        Boolean a = false;
        for(WebElement c : getListOfWebElements(companyBlock)){
            if(c.getText().equals(name)){
                a = true;
                break;
            }
        }
        System.out.println(a);
        return a;
    }


    public void listOfEntitiesIsLoaded(){
        elementAvailability(listOfEntities);
    }

    public void getInCompany(String name){
        for(WebElement c : getListOfWebElements(companyBlock)){
            System.out.println(c.getText());
            if(c.getText().equals(name)){
                mouseOver(c);
                WebElement a = c.findElement(entityEditLink);
                jsClick(a);
            }
        }
        elementAvailability(modal);
    }

    public void deleteCompany(String name){
        for(WebElement c : getListOfWebElements(companyBlock)){
            if(c.getText().equals(name)){

                mouseOver(c);

                c.findElement(entityDeleteLink).click();

                alertOk();
                break;
            }
        }

    }

    public int companiesAmount(){
        return getListOfWebElements(companyBlock).size();
    }

    public void editShortName(String name){
        type(shortCompanyName, name);
    }
    public void editFullName(String name){
        type(fullCompanyName, name);
    }
    public void editPostName(String name){
        type(postAddress, name);
    }
    public void editLegalPostName(String name){
        type(legalAddress, name);
    }

    public String getShortName(By locator){
        return getText(locator);
    }
    public String getFullName(By locator){
        return getShortName(locator);
    }
    public String getPostName(By locator){
        return getText(locator);
    }
    public String getLegalPostName(By locator){
        return getText(locator);
    }

    public void alertOk(){
        alert(alert, ok);
    }
    public void alertCancel(){
        alert(alert, cancel);
    }

    //Templates
    public void fillIntramuralProtocolField(String text){
        type(intramuralProtocolField, text);
    }

    public void fillAbsenteeProtocolField(String text){
        type(absenteeProtocolField, text);
    }

    public void fillOpinion(String text){
        type(opinion, text);
    }
    public void fillQuestionnaireField(String text){
        type(questionnaireField, text);
    }

    public void fillOpinionConsolidatedQuestionnaireField(String text){
        type(opinionConsolidatedQuestionnaireField, text);
    }

    public void fillExtractField(String text){
        type(extractField, text);
    }

    public void fillAbsenteeMeetingExtractField(String text){
        type(absenteeMeetingExtractField, text);
    }

    public void fillAgendaField(String text){
        type(agendaField, text);
    }

    public void fillAbsenteeAgendaField(String text){
        type(absenteeAgendaField, text);
    }

    public void fillMeetingNotificationField(String text){
        type(meetingNotificationField, text);
    }

    public void fillAbsenteeMeetingNotificationField(String text){
        type(absenteeMeetingNotificationField, text);
    }

    public void fillUDPTitle(String text){
        type(uDPTitle, text);
    }

}
