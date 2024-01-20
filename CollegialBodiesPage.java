package PageObject.Directories;

import PageObject.TestHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.lang.Thread.sleep;

public class CollegialBodiesPage extends TestHelper {

    public CollegialBodiesPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }
    private By collegiateBody = By.cssSelector("ul.nav.nav-tabs li:nth-child(2)");
    private By addBtn = By.cssSelector("button.btn.btn-success");
    private By overlay = By.cssSelector("div.overlay");

    //Page
    private By cbNameInBlock = By.cssSelector("tr.dict__directors.ng-scope td:nth-child(1)");
    private By cbBlock = By.cssSelector("tr.dict__directors.ng-scope");
    private By entityEditLink = By.cssSelector("a");
    private By entityDeleteLink = By.cssSelector("a:nth-child(2)");
    private By listOfEntities = By.cssSelector("table.table");

    //Tabs
    private By mainInfo = By.cssSelector("li.edit__tabs_templates a");
    private By templates = By.cssSelector("li.edit__tabs_templates a");
    private By questionTypes = By.cssSelector("li.edit__tabs_question_types a");
    private By members = By.cssSelector("li.edit__tabs_members a");
    private By addMemberBtn = By.cssSelector("input.btn.btn-success.pull-left");
    private By memberInput = By.cssSelector("input.form-control.autocomlete-loading.ng-valid.ng-isolate-scope");
    private By memberSearchInput = By.cssSelector("input.form-control.inline-search-box.ng-valid");
    private By memberNameAfterSearch = By.cssSelector("div.modal-content tbody tr td");

    //Templates fields
    private By intramuralProtocolField = By.cssSelector("div.form-group.row:nth-child(1) input");
    private By absenteeProtocolField = By.cssSelector("div.form-group.row:nth-child(2) input");
    private By opinion = By.cssSelector("div.form-group.row:nth-child(4) input");
    private By questionnaireField = By.cssSelector("div.form-group.row:nth-child(3) input");
    private By opinionConsolidatedQuestionnaireField  = By.cssSelector("div.form-group.row:nth-child(5) input");
    private By extractField  = By.cssSelector("div.form-group.row:nth-child(6) input");
    private By absenteeMeetingExtractField  = By.cssSelector("div.form-group.row:nth-child(7) input");
    private By agendaField  = By.cssSelector("div.form-group.row:nth-child(8) input");
    private By absenteeAgendaField  = By.cssSelector("div.form-group.row:nth-child(9) input");
    private By meetingNotificationField  = By.cssSelector("div.form-group.row:nth-child(10) input");
    private By absenteeMeetingNotificationField = By.cssSelector("div.form-group.row:nth-child(11) input");
    private By quorumIntramuralField = By.cssSelector("div.form-group.row:nth-child(13) input");
    private By quorumAbsenteeField = By.cssSelector("div.form-group.row:nth-child(14) input");
    private By uDPTitle = By.cssSelector("div.form-group.row:nth-child(12) input");

    //Question type
    private By questionTypeRows= By.cssSelector("div.form-group.row.ng-scope");
    private By nameInRow = By.cssSelector("div.col-sm-8.form-control-label.ng-binding");
    private By ruleInRow = By.cssSelector("select");

    //Modal
    private By createBtn = By.cssSelector("input[value='Создать']");
    private By saveBtn = By.cssSelector("input[value='Сохранить']");
    private By modal = By.cssSelector("div.modal-content");
    private By questionTypesTab = By.cssSelector("li.edit__tabs_question_types a");
    private By cbMembersTab = By.cssSelector("li.edit__tabs_members a");
    private By companyInCB = By.cssSelector("div.form-group.row:nth-child(2) button");
    private By companyInCBOptions = By.cssSelector("div.form-group.row:nth-child(2) li a");
    private By ruleSelect = By.cssSelector("div.form-group.row:nth-child(3) select");
    private By dgNameInput = By.cssSelector("div.form-group.row:nth-child(4) input");
    private By dgNameInputExpoB = By.cssSelector("div.form-group.row:nth-child(5) input");
    private By eventTypeSelect = By.cssSelector("div.form-group.row:nth-child(5) select");
    private By infoForKoMembers = By.cssSelector("div.form-group.row:nth-child(5) textarea");
    private By infoForKoMembersExpoB = By.cssSelector("div.form-group.row:nth-child(6) textarea");
    private By infoForKoMembersDKU = By.cssSelector("div.form-group.row:nth-child(6) textarea");
    private By legalJustification = By.cssSelector("div.form-group.row:nth-child(6) textarea");
    private By legalJustificationDku = By.cssSelector("div.form-group.row:nth-child(6) textarea");
    private By legalJustificationExpoB = By.cssSelector("div.form-group.row:nth-child(7) textarea");
    private By autocomplete = By.cssSelector("input.btn.btn-success.pull-right");

    private By searchInputInCBModal = By.cssSelector("input.form-control.inline-search-box");

    public void goToCollegialBody(){
        click(collegiateBody);
    }

    public void goToMainInfo(){
        click(mainInfo);
    }
    public void goToTemplates(){
        click(templates);
        elementAvailability(By.cssSelector("div.form-group.row"));
    }
    public void goToQuestionTypes(){
        click(questionTypes);
    }
    public void goToMembers(){
        click(members);
    }

    public void submitCreation(){
        elementAvailability(createBtn);
        jsClick(createBtn);
    }

    public void save(){
        elementAvailability(saveBtn);
        jsClick(saveBtn);
        preloaderGone();
    }

    public Boolean isAddedCollegialBodyDisplayed(String name){
        Boolean a = false;
        scroll(1000);
        for(WebElement g : getListOfWebElements(cbNameInBlock)){
            if(g.getText().equals(name)){
                System.out.println(g.getText());
                a = true;
                break;
            }
        }
        System.out.println("Created Collegial: " + a);
        return a;
    }

    public void getInCollegialBody(String name){
        scroll(1000);
        for(WebElement g : getListOfWebElements(cbBlock)){
            if(g.findElement(cbNameInBlock).getText().equals(name)){
                mouseOver(g);
                WebElement a = g.findElement(entityEditLink);
                jsClick(a);
                break;
            }
        }
        elementAvailability(modal);
    }

    public void createKO(String customer,String company, String rule, String dgName, String info, String legalSmth){
        click(addBtn);
        gone(overlay);
        elementAvailability(modal);
        selectThrowSearch(companyInCB, companyInCBOptions, company);
//        customSelect(companyInCB, companyInCBOptions, company);
        selectByLocator(ruleSelect, rule);
        if(customer.equals("expob")){
            type(dgNameInputExpoB,dgName);
        }else{
            type(dgNameInput, dgName);
        }
        click(autocomplete);

        if (customer.equals("dkudzo")){
            eventType(1);
            type(infoForKoMembersDKU, info);
            type(legalJustificationDku, legalSmth);
        }else if(customer.equals("expob")){
            type(infoForKoMembersExpoB, info);
            type(legalJustificationExpoB, legalSmth);
        } else{
            type(infoForKoMembers, info);
            type(legalJustification, legalSmth);
        }

        save();
        preloaderGone();

    }

    public void eventType(int index){
        selectByIndex(eventTypeSelect,index);
    }

    public void editCBName(String text, String customer) {
        if(customer.equals("expob")){
            clearField(dgNameInputExpoB);
            type(dgNameInputExpoB, text);
        }else{
            clearField(dgNameInput);
            type(dgNameInput, text);
        }

    }
    public void editInfoForMembers(String text){
        clearField(collegiateBody);
        type(collegiateBody, text);
    }
    public void editLegalJustification(String text){
        clearField(collegiateBody);
        type(collegiateBody, text);
    }

    public void editCompany(String company){
        selectByLocator(companyInCB, company);
    }

    public void editRule(String rule){
        selectByLocator(ruleSelect, rule);
    }

    public String getRule(){
        return getText(ruleSelect);
    }
    public int getLast(){
        return getListOfWebElements(cbNameInBlock).size();
    }

    public void listOfEntitiesIsLoaded(){
        elementAvailability(listOfEntities);
    }

    public String cbNameInTable(String company, String ko){
        System.out.println("-------------------------------------------");
        System.out.println("cbNameInTable");
        System.out.println("-------------------------------------------");
        String name = ko + " (" + company + ")";
        System.out.println("KO NAME IN TABLE :" + name);
        return name;
    }

    //Templates
    public void fillIntramuralProtocolField(String text){
        jsClick(intramuralProtocolField);
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

    public void setRuleInQuestionType(String questionName, String rule){
        for(WebElement row : getListOfWebElements(questionTypeRows)){
            if(row.findElement(nameInRow).getText().equals(questionName)){
                selectByWebElement(row.findElement(ruleInRow), rule);
            }
        }
    }

    public void fillQuorum(String quorum) {
        type(quorumIntramuralField,quorum);
        type(quorumAbsenteeField,quorum);
    }

    public void addMemberInKO(String name){
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        jsClick(memberInput);
        type(memberInput,name);
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pickFromSearchMenu(By.cssSelector("div.input-group.full-width input"));
        jsClick(addMemberBtn);
    }

    public boolean isUserAdded(String userName) {
        Boolean res = false;
        jsClick(memberSearchInput);
        type(memberSearchInput,userName);
        res = getText(memberNameAfterSearch).equals(userName);
        return res;
    }

    public void setRole(String name, int i) {
        searchMemberInKOModal(name);
        selectByIndex(By.cssSelector("select.form-control.ng-pristine.ng-valid.ng-not-empty.ng-valid-required"),
                i);
    }

    public void searchMemberInKOModal(String name){
        clearField(searchInputInCBModal);
        jsClick(searchInputInCBModal);
        type(searchInputInCBModal, name);
    }

    public Boolean isCheckedVetoRight(){
        return isSelectedWithoutWait(By.cssSelector("div.modal tbody tr td:nth-child(4) input"));
    }
}
