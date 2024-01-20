package PageObject;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import static java.lang.Thread.sleep;

public class CreateNewQuestionPage  extends TestHelper{
    WebDriver driver;
    WebDriverWait wait;

    private By createQuestionModal = By.cssSelector("div.modal-content");
    private By questionNameField = By.cssSelector("div.modal-content textarea.edit-question-name");
    private By divisionNameField  = By.cssSelector("div.form-group.row:nth-child(3) button");
    private By divisionNameFieldOption = By.cssSelector("div.form-group.row:nth-child(3) a");

    private By directorNameAndMeetingFields  = By.cssSelector("select#directorSelect");
    private By directorSelect = By.cssSelector("div.form-group.row:nth-child(4) button");
    private By directorSelectOption = By.cssSelector("div.form-group.row:nth-child(4) a");

    private By questionTypeField  = By.cssSelector("div.form-group.row:nth-child(5) button");
    private By questionTypeFieldOption = By.cssSelector("div.form-group.row:nth-child(5) a");

    private By meetingSelect = By.cssSelector("div.form-group.row:nth-child(6) button");
    private By meetingSelectOption = By.cssSelector("div.form-group.row:nth-child(6) a");

    private By readyOrNotRadio = By.cssSelector("div.btn-group label.btn-radio");
    private By readyOrNotRadioInModal = By.cssSelector("div.modal-content div.btn-group label.btn-radio");
    private By submitButton = By.cssSelector("div.modal-content input[type='submit']");
    private By projectDescisionIframe = By.tagName("iframe");
    //materials tab
    private By fileInput = By.cssSelector("input[type='file']");
    private By materialsTab = By.cssSelector("body > div.modal.fade.ng-isolate-scope.modal-wide.in > div > div > div > div.secondary-editor-section.mainTab.edit-tabs.question-create-tabs > ul > li.question__tabs_materials > a");
    private By questionFolderDDMenu = By.cssSelector("div.dropdown.ng-scope > button");
    private By addMaterialOption = By.cssSelector("body > div.toolbar-popup-container > ul > li:nth-child(1) > a");
    //Таб докладчиков
    private By goToSpeakersTab = By.cssSelector("li.question__tabs_members");
    private By goToSpeakersTabExpo =By.xpath("//*[contains(text(),\"Докладчики\")]");
    private By addNewSpeaker = By.cssSelector("div.tabContentPopup button.btn.btn-default.ng-binding");
    private By addNewSpeakerModal =By.cssSelector("");
    private By speakerName = By.cssSelector("div.input-group.full-width input");
    private By speakerPosition = By.cssSelector("input#position");
    private By submitSpeaker = By.cssSelector("input[value='Добавить']");
    private By nspkSubmitSpeaker = By.cssSelector("input[value='Создать']");

    public CreateNewQuestionPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void fillQuestionForm(String questionName, String divisionName, String directorName, String projectDecision, String customer) throws InterruptedException {
        elementAvailability(createQuestionModal);
        waitUntilDisabled(submitButton);
        type(questionNameField, questionName);
        if(!customer.equals("dkudzo")){
            selectThrowSearch(divisionNameField, divisionNameFieldOption, divisionName);
        }
        selectThrowSearch(directorSelect, directorSelectOption, directorName);
//        customSelect(divisionNameField, divisionNameFieldOption, divisionName);
//        customSelect(directorSelect, directorSelectOption, directorName);
//        driver.findElement((By) elems.get(1)).sendKeys(meetingName);
        if(!customer.equals("alrosa")){
            click(readyOrNotRadio);
        }
       iFrameSendKeys(projectDescisionIframe, projectDecision);
    }
    public void fillQuestionType(String questionTypeName){
        customSelect(questionTypeField, questionTypeFieldOption, questionTypeName);
    }
    public void fillMeeting(String meeting){
        customSelect(meetingSelect, meetingSelectOption, meeting);
    }

    public void saveQuestion(){
        isEnabled(submitButton);
        click(submitButton);
        System.out.println("Кнопка нажата");
    }

    public void saveButtonHoveAndClick(){
        isEnabled(submitButton);
        hoveAndClick(submitButton);
    }

    public void modalGone(){
        gone(By.cssSelector("div.modal-content"));
    }

    public void goToMaterials(){
        click(materialsTab);
    }
    public void addMaterial(String path) {

        uploadFile(path);
    }

    public void uploadMaterials(String[] filepath){
        goToMaterials();
        elementAvailability(questionFolderDDMenu);
//        click(questionFolderDDMenu);
//        click(addMaterialOption);
        uploadFromArray(fileInput, filepath);

    }

    public static void uploadFile(String filePath)  {
        StringSelection stringSelection = new StringSelection(filePath);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        try{
            Robot robot = new Robot();
            robot.delay(300);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.delay(200);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.delay(300);
        }catch (AWTException e){
            e.printStackTrace();
        }
    }

    public void safeAlertDissmiss() {
        try {
            driver.switchTo().alert().dismiss();
        } catch (NoAlertPresentException e) {
            // ничего не делаем, алерта итак нет
        }
    }

    public int materialsSize(){
        elementAvailability(By.cssSelector("div.material-item-container"));
        int size = 0;

        try{
            size = getListOfWebElements(By.cssSelector("div.material-item-container")).size();
        }catch (TimeoutException te){
            System.out.println("Материалы не добавлены: "+size);
        }

        return size;
    }

    public void addSpeaker(String name, String position, String customer) throws InterruptedException {
        if(!customer.equals("expob")){
            click(goToSpeakersTab);
        }else {
            click(goToSpeakersTabExpo);
        }

        click(addNewSpeaker);
        click(speakerName);

        type(speakerName, name);
        sleep(1000);
        pickFromSearchMenu(speakerName);
        type(speakerPosition, position);
        if(customer.equals("nspk")){
            click(nspkSubmitSpeaker);
        }else{
            click(submitSpeaker);
        }
        preloaderGone();
    }
    public void fillCopiedQuestion(String divisionName, String directorName, String customer){
        elementAvailability(createQuestionModal);
//        try {
//            waitUntilDisabled(submitButton);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        try{
            if(!customer.equals("dkudzo")){
                selectThrowSearch(divisionNameField, divisionNameFieldOption, divisionName);
            }
        }catch (ElementClickInterceptedException e){

        }
        try{
            selectThrowSearch(directorSelect, directorSelectOption, directorName);
        }catch (ElementClickInterceptedException e){

        }

        click(readyOrNotRadio);
    }

    public String getQuestionTitle(){
        return getText(questionNameField);
    }
    public String getProjectDecision(){
        return getTextFromIFrame(projectDescisionIframe);
    }

    public void fillSubquestion(String questionName, String questionType, String projectDecision) throws InterruptedException {
        elementAvailability(createQuestionModal);
        waitUntilDisabled(submitButton);
        type(questionNameField, questionName);
        if(!questionType.equals("")){
            fillQuestionType(questionType);
        }
        jsClick(readyOrNotRadioInModal);
        iFrameSendKeys(projectDescisionIframe, projectDecision);
    }

    public int getTabsAmount() {
        int res = 0;
        for(WebElement ele : getListOfWebElements(By.cssSelector("div.modal ul.nav.nav-tabs li"))){
            if(ele.isDisplayed()){
                res = res + 1;
            }
        }
        return res;
    }
}
