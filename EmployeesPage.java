package PageObject.Directories;

import PageObject.TestHelper;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.lang.Thread.sleep;

public class EmployeesPage extends TestHelper {

    public EmployeesPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }


    private By employees = By.cssSelector("ul.nav.nav-tabs li:nth-child(3)");
    private By addBtn = By.cssSelector("button.btn.btn-success");
    private By listOfEntities = By.cssSelector("table.table");

    //
    private By employeeBlock = By.cssSelector("tr.dict_tbl.ng-scope");
    private By employeeNameInBlock = By.cssSelector("a.dict_user_name.ng-binding");

    private By createBtn = By.cssSelector("input[value='Создать']");
    private By saveBtn = By.cssSelector("input[value='Сохранить']");
    private By modal = By.cssSelector("div.modal-content");

    //form
    private By login = By.cssSelector("div.form-group.row.ng-scope input");
    private By fio = By.cssSelector("div.username-autocomplete input");
    private By password = By.cssSelector("div.form-group.row.user-password.ng-scope input:nth-child(1)");
    private By repeatPas = By.cssSelector("div.form-group.row.user-password.ng-scope input:nth-child(2)");
    private By addCB = By.cssSelector("a.directorSelect-add.ng-binding");//для заказчиков с полем пароль

    private By email = By.cssSelector("div.form-group.row:nth-child(6) input");
    private By email2 = By.cssSelector("div.form-group.row:nth-child(7) input");//для заказчиков с полем пароль
    private By email3 = By.cssSelector("div.form-group.row:nth-child(9) input");
    private By language = By.cssSelector("select#memberTypeSelect");
    private By autocomplete = By.cssSelector("input[value='Автозаполнение']");
    private By userDirectorsGroupRow = By.cssSelector("ul.user-directors-list li");
    private By usersDirectorsGroupTitle = By.cssSelector("div.ng-binding");
    private By getUsersDirectorsGroupRole = By.cssSelector("div:nth-child(3)");
    private By vetoCheckBox = By.cssSelector("input#has-veto-power");
    private By vetoWarning = By.cssSelector("ul.validation-errors li");


    private By checkboxAdmin = By.cssSelector("label[for='Администратор']");
    private By checkboxSecretary = By.cssSelector("label[for='Секретарь']");
    private By checkboxMember = By.cssSelector("label[for='Участник']");
    private By checkboxAdminIB = By.cssSelector("label[for='Администратор ИБ']");

    //Collegial body add
    private By selectCompanyModalAdd = By.cssSelector("div.form-group.row:nth-child(1) button");
    private By selectCompanyModalAddOption = By.cssSelector("div.form-group.row:nth-child(1) li a");

    private By selectCBModalAdd = By.cssSelector("div.form-group.row:nth-child(2) button");
    private By selectCBModalAddOption = By.cssSelector("div.form-group.row:nth-child(2) li a");

    private By selectRoleModalAdd = By.cssSelector("div.form-group.row:nth-child(3) select");

    private By cBModal = By.cssSelector("div.modal-header.ng-scope");
    private By submitBtn = By.cssSelector("input[value='Применить']");
    private By deleteUserBtn = By.cssSelector("div.modal-footer input.btn.btn-danger");

    private By votingRightCheckBox = By.cssSelector("div.form-group.row:nth-child(5) div label");

    private By externalUser = By.cssSelector("div.form-group.row:nth-child(1) label:nth-child(2)");

    public void goToEmployees(){
        click(employees);
    }
    public void listOfEntitiesIsLoaded(){
        elementAvailability(listOfEntities);
    }

    public void openAddModal(){
        jsClick(addBtn);
        elementAvailability(modal);
    }

    public void fillUserBeeline(String userLogin,String userFio,String company,String ko,String userEmail,String customer) throws InterruptedException {
        type(login, userLogin);
        type(fio,userFio);
        jsClick(autocomplete);
//        scrollDownPage(400);
//        sleep(10000);
//        fillEmail(userEmail, customer);
        openModalAddingRole();
        selectCompanyInModalAdd(company);
        selectCBInModalAdd(ko);
        selectRoleInModalAdd("Секретарь");
        jsClick(submitBtn);
        gone(cBModal);
        fillEmail(userEmail, customer);
        setGlobalRoles(customer);
        selectByLocator(language, "Русский");
        jsClick(createBtn);
        gone(modal);
    }
    public void fillUser(String userLogin, String userFio,String userPassword, String company, String ko, String userEmail,String customer) throws InterruptedException {
        type(login, userLogin);
        if(!customer.equals("test") || !customer.equals("rshb")){
            fillPassword(userPassword, customer);
        }
        type(fio,userFio);
        jsClick(autocomplete);
        openModalAddingRole();
        selectCompanyInModalAdd(company);
        selectCBInModalAdd(ko);
        selectRoleInModalAdd("Секретарь");
        jsClick(submitBtn);
        gone(cBModal);
        fillEmail(userEmail, customer);
        setGlobalRoles(customer);
        scroll(500);
        selectByLocator(language, "Русский");
        jsClick(createBtn);
    }
    public void fillWithoutKO(String userLogin, String userFio, String userPassword,String userEmail, String customer){
        type(login, userLogin);
        if(!customer.equals("test") || !customer.equals("rshb")){
            fillPassword(userPassword, customer);
        }
        type(fio,userFio);
        jsClick(autocomplete);
        fillEmail(userEmail, customer);
        setGlobalRoles(customer);
        scroll(500);
        selectByLocator(language, "Русский");
        jsClick(createBtn);
    }


//    public void addRoleInCB(String customer, String company, String collegial) throws InterruptedException {
//
//        openModalAddingRole();
//        selectCompanyInModalAdd(company);
//        selectCBInModalAdd(collegial);
//        selectRoleInModalAdd("Секретарь");
//        jsClick(submitBtn);
//    }

    public void addSecretaryRoleInCB(String company, String collegial) throws InterruptedException {

        openModalAddingRole();
        selectThrowSearch(selectCompanyModalAdd, selectCompanyModalAddOption, company  );
        selectThrowSearch(selectCBModalAdd, selectCBModalAddOption, collegial);
        selectRoleInModalAdd("Секретарь");
        jsClick(submitBtn);
    }

    public void addChairmanRoleInCB(String company, String collegial){
        openModalAddingRole();
        selectThrowSearch(selectCompanyModalAdd, selectCompanyModalAddOption, company  );
        selectThrowSearch(selectCBModalAdd, selectCBModalAddOption, collegial);
        selectRoleInModalAdd("Председатель");
    }

    public void finishChairmanRoleAdding(){
        jsClick(submitBtn);
    }

    public void fillPassword(String userPassword, String customer){
        if(customer.equals("test") || customer.equals("rshb")){

        }else{
            type(password, userPassword);
            type(repeatPas, userPassword);
        }
    }

//    public void selectCompany (String company, String customer){
//        scroll(500);
//        if(customer.equals("test") || customer.equals("rshb")){
//            selectByLocator(selectCompany, company);
//        }else{
//            selectByLocator(selectCompany2, company);
//        }
//    }

    public void fillEmail(String userEmail, String customer){
        if(customer.equals("test") || customer.equals("rshb")|| customer.equals("beeline")){
            type(email, userEmail);
        }else if(customer.equals("dkudzo")){
            type(email3, userEmail);
        }
        else{
            type(email2, userEmail);
        }
    }
    public void openModalAddingRole(){
            jsClick(addCB);
            elementAvailability(cBModal);
    }

    public void goToExternalForm(){
        click(externalUser);
    }
    //Modal
    public void selectCompanyInModalAdd(String company){
        customSelect(selectCompanyModalAdd, selectCompanyModalAddOption, company);
    }

    public void selectCBInModalAdd(String cb) {

        customSelect(selectCBModalAdd, selectCBModalAddOption, cb);
    }

    public void selectRoleInModalAdd(String role){
        selectByLocator(selectRoleModalAdd, role);
    }
    //set global role
    public void setGlobalRoles(String customer){
        if(customer.equals("rshb")){
            jsClick(checkboxAdminIB);
        }else{
            jsClick(checkboxAdmin);
        }

        jsClick(checkboxMember);
        jsClick(checkboxSecretary);
    }


    public Boolean isAddedEmployeeDisplayed(String name){
        elementAvailability(listOfEntities);
        Boolean a = false;
        scroll(1000);
        try{
            for(WebElement e : getListOfWebElements(employeeNameInBlock)){
                if(e.getText().equals(name)){
                    a = true;
                    break;
                }
            }
        }catch(StaleElementReferenceException sere){
            for(WebElement e : getListOfWebElements(employeeNameInBlock)){
                if(e.getText().equals(name)){
                    a = true;
                    break;
                }
            }
        }

        System.out.println("Created: " + a);
        return a;
    }
    public Boolean checkDirectorsRole(String cbName, String role){
        Boolean a = false;
        for(WebElement el : getListOfWebElements(userDirectorsGroupRow)){
            if(el.findElement(usersDirectorsGroupTitle).getText().equals(cbName) &&
            el.findElement(getUsersDirectorsGroupRole).getText().equals(role + ", Участник")){
                a = true;
                break;
            }
        }
        return a;
    }

    public void openUserForEdit(String name){

        for(WebElement e : getListOfWebElements(employeeBlock)){
            if(e.getText().equals(name)){
                WebElement a = e.findElement(employeeNameInBlock);
                jsClick(a);
                break;
            }
        }
        elementAvailability(modal);
    }

    public void saveChanges(){
        jsClick(saveBtn);
        makeScreenshot();
    }

    public void deleteUser(String name){
        for(WebElement e : getListOfWebElements(employeeBlock)){
            if(e.getText().equals(name)){
                WebElement a = e.findElement(employeeNameInBlock);
                jsClick(a);
                break;
            }
        }
        elementAvailability(modal);

        jsClick(deleteUserBtn);
        alertOk();
        gone(modal);

    }

    public void enableRecommendationsVisibility() {
        jsClick(By.cssSelector("div.form-group.row:nth-child(6) label:nth-child(2)"));
    }

    public boolean isRecommendationEnabled() {
        return isSelectedWithoutWait(By.cssSelector("div.form-group.row:nth-child(6) input"));
    }

    public Boolean isCheckedVetoRightInEmployeeCard(){
        return isSelectedWithoutWait(vetoCheckBox);
    }

    public void submitChanges(){
        jsClick(submitBtn);
    }

    public void uncheckVotingRight() {
        jsClick(votingRightCheckBox);
    }

    public boolean checkVetoRightWarning(String text) {
        String warning = getText(vetoWarning);
        return warning.equals(text);
    }
}
