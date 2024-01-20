package PageObject;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

public class MeetingPageEdit extends TestHelper {
    WebDriver driver;
    WebDriverWait wait;
    public MeetingPageEdit(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        this.driver = driver;
        this.wait = wait;
    }

    //Общие элементы

    private By modal = By.cssSelector("div.modal-content");
    private By addBtn = By.cssSelector("div.modal-footer input[type='submit']");

    private By fromTime = By.cssSelector("div.popup-calendar__time_from.ng-scope > timepicker-pop > input");
    private By toTime = By.cssSelector("div.popup-calendar__time_to.ng-scope > timepicker-pop > input");
    private By statusButton = By.cssSelector("span.form-control ");

    private By approve = By.cssSelector("div.meeting-title__meeting-controls li:nth-child(1)");
    private By sendToApprove = By.cssSelector("div.meeting-title__meeting-controls li:nth-child(2)");

    private By saveButton = By.cssSelector("input.btn.btn-success.btn-save");
    private By backToCalendar = By.cssSelector("div.back_btn a");
    private By deleteBtn = By.cssSelector("label.deleteText");
    private By endMeetingBtn = By.cssSelector("label.btn.btn-default.meetingEdit-Btn.endText.ng-binding.ng-scope");

    private By removeQuestion = By.cssSelector("a.meeting_questions-remove.ng-scope");

    //Элементы страницы повестка
    private By addQuestionButton = By.cssSelector("question-panel > div.btn-wrapper > div > button");
    private By addExistQuestionButton = By.cssSelector("question-panel ul > li:nth-child(2) > a");
    private By addNewQuestionButton = By.cssSelector("question-panel ul > li:nth-child(1) > a");
    private By questionsCount = By.cssSelector("ul.meeting-question-list.ng-scope div.question-container");
    private By questionNameTitle = By.cssSelector("a.edit-meetings__meeting_title.ng-binding");
    private By secondQuestion = By.cssSelector("ul.meeting-question-list.ng-scope li:nth-child(2)");
    private By questionContainer = By.cssSelector("div.question-container");
    private By goToAgenda = By.cssSelector("li.edit__tabs_agenda a");
    private By subQuestionInAgendaTitle = By.cssSelector("ul.question-sublist__question-items.ng-scope li a.edit-meetings__meeting_title");
    private By subQuestionInAgenda = By.cssSelector("ul.question-sublist__question-items.ng-scope li");
    private By subQuestionInAgendaRemoveBtn = By.cssSelector("a.meeting_questions-remove");
    private By subQuestionInAgendaTitleInElement = By.cssSelector("a.edit-meetings__meeting_title");
    //учатники
    private By goToParticipants = By.cssSelector("li.edit__tabs_members a");
    private By participantsRow = By.cssSelector("tr.ng-scope");
    private By removeParticipant = By.cssSelector("td:nth-child(5) a");
    private By removeParticipantMMK = By.cssSelector("td:nth-child(6) a");
    private By removeParticipantExpo = By.cssSelector("td:nth-child(7) a");
    private By addParticipantButton = By.cssSelector("div.tabContent button");
    private By participantName = By.cssSelector("div.input-group.full-width input");
    private By voting = By.cssSelector("input#votingInput");
    private By labelVoting = By.cssSelector("label[for='votingInput']");
    private By participantNameInRow = By.cssSelector("td:nth-child(1)");
    //Модалка существующих вопросов
    private By existQuestionsModal = By.cssSelector("div.modal-dialog");
    private By existQuestionsList = By.cssSelector("div.question-meeting-list label");
    private By submitBtn = By.cssSelector("div.modal input.btn-success");
    //Модалка нового вопроса
    private By newQuestionModal = By.cssSelector("div.modal-dialog");
    private By questionName = By.cssSelector("form textarea");
    private By questionType = By.cssSelector("div.form-group.row:nth-child(5) button");
    private By questionTypeOption = By.cssSelector("div.form-group.row:nth-child(5) a");
    private By statusReady = By.cssSelector("label.btn-radio:nth-child(1)");
    private By statusNotReady = By.cssSelector("label.btn-radio:nth-child(2)");
    private By projectDecisionIframe = By.tagName("iframe");

    //Таб докладчиков
    private By goToSpeakersTab = By.cssSelector("li.question__tabs_members a");
    private By goToSpeakersTabExob = By.xpath("//*[contains(text(),\"Докладчики\")]");
    private By addNewSpeaker = By.cssSelector("div.tabContentPopup button.btn.btn-default.ng-binding");
    private By addNewSpeakerModal =By.cssSelector("");
    private By speakerName = By.cssSelector("div.input-group.full-width input");
    private By speakerPosition = By.cssSelector("input#position");
    private By submitSpeaker = By.cssSelector("input[value='Добавить']");
    private By nspkSubmitSpeaker = By.cssSelector("input[value='Создать']");
    private By speakers = By.cssSelector("div.member_unit");


    private By sendMaterialInEmailBtn = By.cssSelector("div.modal-footer > input.btn.btn-success");
    //Материалы
    private By materialsTabIsActive = By.cssSelector("li.question__tabs_materials");
    private By goToMaterialsTab = By.cssSelector("li.question__tabs_materials a");
    private By material  = By.cssSelector("div.material-item-container");
    private By materialName = By.cssSelector("a.file-link span");
    private By materialStatus = By.cssSelector("div.material-status");
    private By questionFolderDDMenu = By.cssSelector("div.dropdown.ng-scope > button");
    private By addMaterialOption = By.cssSelector("body > div.toolbar-popup-container > ul > li:nth-child(1) > a");
    private By backToMeeting = By.cssSelector("a.edit-href.ng-binding");
    //materials tab
    private By goToMaterialsTabInMeeting = By.cssSelector("li.edit__tabs_material a");
    private By preloader = By.cssSelector("div.overlay");
    private By questionInfo = By.cssSelector("div.corners.edit.no-bottom-shadow");
    private By fileInput = By.cssSelector("input[type='file']");
    private By inputTradeSecret = By.cssSelector("label:nth-child(5) > input[type=file]");
    //vote results tab
    private By goToVoteResultsTab = By.cssSelector("li.edit__tabs_vote a");
    private By createProtocolButton = By.cssSelector("div.tabContent button.btn.btn-default");
    private By voteAgree = By.cssSelector("a.btn.btn-success");
    private By voteDisagree = By.cssSelector("a.btn.btn-danger");
    private By voteAbstain = By.cssSelector("a.btn.btn-primary");
    private By votersOpinion = By.cssSelector("a.btn-info");
    private By voteList = By.cssSelector("div.popover-content");
    private By firstQuestionAgree = By.cssSelector("div.tabContent tbody > tr:nth-child(2) a.btn.btn-success");
    private By secondQuestionDisagree = By.cssSelector("div.tabContent tbody > tr:nth-child(3) a.btn.btn-danger");
    private By opinionInResults = By.cssSelector("div.tabContent tbody > tr:nth-child(3) a.btn.btn-info.ng-binding.ng-scope.ng-isolate-scope");
    private By opinionInResultsWithSubQuestions = By.cssSelector("div.tabContent tbody > tr:nth-child(5) a.btn.btn-info.ng-binding.ng-scope.ng-isolate-scope");
    private By opinionInResultsExpoB = By.cssSelector("div.ng-scope.ng-isolate-scope > ng-include > div > div > table > tbody > tr:nth-child(3) > td:nth-child(2) > a > img");
    private By abstainedVoteInResultTable = By.cssSelector("tbody tr:nth-child(5) td:nth-child(2) a:nth-child(3)");

    private By firstSubQuestion = By.cssSelector("div.tabContent tbody > tr:nth-child(3) a.btn.btn-success");
    private By secondSubQuestion = By.cssSelector("div.tabContent tbody > tr:nth-child(4) a.btn.btn-danger");
    private By secondQuestionDisagreeWithSubQ = By.cssSelector("div.tabContent tbody > tr:nth-child(5) a.btn.btn-danger");
    private By closeOpinionModal = By.cssSelector("button.close");
    //рекомендации
    private By recommendations = By.cssSelector("li.edit__tabs_recommendations a");
    private By recommendationAllAbstainedBtn = By.cssSelector("div.btn-wrapper button:nth-child(3)");
    private By recommendationsRow = By.cssSelector("table tbody tr.ng-scope");
    private By passwordInputTS = By.cssSelector("input[type=\"password\"]");

    //Общие методы


    public void switchToWindow(WebDriver driver){
        String window = driver.getWindowHandle();
        driver.switchTo().window(window);
    }
    public void deleteSubQuestion(String subQuestionName) {
        for(WebElement element: getListOfWebElements(subQuestionInAgenda)){
            if(element.findElement(subQuestionInAgendaTitleInElement).getText().equals(subQuestionName)){
                jsClick(element.findElement(subQuestionInAgendaRemoveBtn));
                break;
            }
        }
        alertOk();
    }

    public void saveButtonClick(){

        elementAvailability(saveButton);
        jsClick(saveButton);
        //иначе нажатие не будет засчитываться и будет вылетать pop Up
    }
    public void saveMessageGone(){
        elementAvailability(By.cssSelector("article.alertify-log-show"));
        gone(By.cssSelector("section.alertify-logs-hidden"));
    }

    public void sendFromTime(String value){
        type(fromTime, value);
    }

    public void clearFromTime(){
        clearField(fromTime);
    }

    public void backToCalendarClick(){
        elementAvailability(backToCalendar);
        jsClick(backToCalendar);
    }

    public void approveClick(){
        jsClick(statusButton);
        jsClick(approve);
    }

    public void sendToApproveClick(){
        jsClick(statusButton);
        jsClick(sendToApprove);
    }

    public void saveChooseMaterialsWindowClick(){

        jsClick(addBtn);
    }
    //Методы страницы Повестка

    public void addExistQuestion(){

        jsClick(addQuestionButton);
        jsClick(addExistQuestionButton);
        elementAvailability(existQuestionsModal);
        clickOnWebElement(getListOfWebElements(existQuestionsList).get(0));
        click(submitBtn);

    }

    public void chooseNewQuestion(){
        jsClick(addQuestionButton);
        jsClick(addNewQuestionButton);
    }

    public void fillMainQuestionData(String name, String projectDecision, String customer) throws InterruptedException {
        elementAvailability(newQuestionModal);
        waitUntilDisabled(submitBtn);
        type(questionName, name);
        if(!customer.equals("alrosa")){
            click(statusReady);
        }
        iFrameSendKeys(projectDecisionIframe, projectDecision);
    }
    public void fillQuestionType(String type){
        customSelect(questionType, questionTypeOption, type);
    }

    public void submitQuestionCreation(){
        click(submitBtn);
    }

    public void addMaterial(String path) {
        if(!driver.findElement(materialsTabIsActive).getAttribute("class").contains("active")){
            click(goToMaterialsTab);
        }
        click(questionFolderDDMenu);
        click(addMaterialOption);
        uploadFile(path);
    }
    public void uploadMaterials(String[] paths){
        if(!driver.findElement(materialsTabIsActive).getAttribute("class").contains("active")){
            click(goToMaterialsTab);
        }
        elementAvailability(questionFolderDDMenu);
        uploadFromArray(fileInput, paths);
    }
    public void uploadMaterialsWithTradeSecret(String[] paths){
//        if(!driver.findElement(materialsTabIsActive).getAttribute("class").contains("active")){
//            click(goToMaterialsTab);
//        }
        jsClick(goToMaterialsTabInMeeting);
        scrollDownPage(500);
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        elementAvailability(questionFolderDDMenu);
        uploadFromArray(inputTradeSecret, paths);
    }
    public void addSpeaker(String name, String position, String customer) throws InterruptedException {
        click(goToSpeakersTab);
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

    }

    public int getMaterialsSize(){
        System.out.println("Материалы : " + getListOfWebElements(material));
        return getListOfWebElements(material).size();
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



    public void clickLastQuestion(){

        int CountElements = driver.findElements(By.xpath("html/body/div[5]/div/div/form/div[1]/div/div/div[*]/label")).size();

        WebElement LastQuestion = driver.findElement(By.xpath("html/body/div[5]/div/div/form/div[1]/div/div/div["+CountElements+"]/label"));
        JavascriptExecutor LastQuestionJS = (JavascriptExecutor)driver;
        LastQuestionJS.executeScript("arguments[0].click()", LastQuestion);

    }

    public void clickTenLastQuestions(){

        int CountElements = driver.findElements(By.xpath("html/body/div[5]/div/div/form/div[1]/div/div/div[*]/label")).size();
        int FirstElementOfTen = CountElements - 10;

        for(int i=1; i<=10; i++) {

            WebElement LastQuestion = driver.findElement(By.xpath("html/body/div[5]/div/div/form/div[1]/div/div/div[" + FirstElementOfTen + "]/label"));
            JavascriptExecutor LastQuestionJS = (JavascriptExecutor) driver;
            LastQuestionJS.executeScript("arguments[0].click()", LastQuestion);

            FirstElementOfTen++;
        }

    }

    public void isVisibleSecondQuestion(){
        elementAvailability(secondQuestion);

    }
    public int questionsCount(){
        elementAvailability(questionsCount);
        List<WebElement> questions = driver.findElements(questionsCount);
        return questions.size();
    }

    public void goToLastQuestion(){
        try{
            elementAvailability(questionNameTitle);
            getListOfWebElements(questionNameTitle).get(1).click();
        }catch(ElementClickInterceptedException ecic){
            gone(preloader);
            getListOfWebElements(questionNameTitle).get(1).click();
        }
    }

    public void getInQuestion(String questionName){
        for(WebElement element : getListOfWebElements(questionNameTitle)){
            if(element.getText().equals(questionName)){
                jsClick(element);
                break;
            }
        }
    }
    public void downScroll(){
        driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.HOME);


    }
    public void upScroll(){
        driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.HOME);
    }

    public void sendMaterialByEmail(){
        jsClick(sendMaterialInEmailBtn);
    }

    public void refreshMeetingPage() {
        refreshWithWait(By.cssSelector("div.meeting-group.ng-scope"));
    }
    public void goToMaterialsTab(){
        jsClick(goToMaterialsTab);
    }
    public void goToMaterialsTabInMeeting(){
        jsClick(goToMaterialsTabInMeeting);
        preloaderGone();
    }

    public Boolean protocolConverted(String protocol){
        Boolean isConverted = false;
        while (isConverted == false){
            String status = checkProtocolStatus(protocol);
            if(status.equals("Конвертирование в PDF Комментарии: 0")){
                refresh();
                goToMaterialsTabInMeeting();
                elementAvailability(material);
            }else if(status.equals("Ошибка при конвертации PDF Комментарии: 0")){
                break;
            }else if(status.equals("Комментарии: 0")){
                isConverted = true;
            }else{
                break;
            }
        }
        return isConverted;
    }

    public Boolean pmIsDisplayed(String pm, String user){
        Boolean a = false;
        String name = pm.substring(0,pm.length() - 5) + " " +
                user.substring(0, user.length() - 9) + pm.substring(pm.length() - 5, pm.length());
       elementAvailability(material);
        for(WebElement el: getListOfWebElementsFromElement(By.cssSelector("div.materials-container"), material)){
            if(el.findElement(materialName).getText().equals(name)){
                a = true;
                break;
            }
        }
        return a;
    }

    public String checkProtocolStatus(String protocol){
        String status = "";
        elementAvailability(By.cssSelector("div.materials-container"));
        for(WebElement el: getListOfWebElementsFromElement(By.cssSelector("div.materials-container"), material)){
            if (el.findElement(materialName).getText().equals("Протокол - " + protocol)){
                status =status + el.findElement(materialStatus).getText();
                break;
            }
        }
        System.out.println(status);
        return status;
    }

    public void goToVoteResultsTab(){
        jsClick(goToVoteResultsTab);
        preloaderGone();
    }
    public void createProtocol(){
        jsClick(createProtocolButton);
        overlayGone();
    }

    public String checkVoteAgreeResults(){
       return getText(voteAgree);
    }

    public String checkVoteDisagreeResults(){
        return getText(voteDisagree);
    }

    public String checkVoteAbstainResults(){
        return getText(voteDisagree);
    }

    public String agreeListOfVoters(){
        mouseOver(voteAgree);
        return getText(voteList);
    }

    public String disagreeListOfVoters(){
        mouseOver(voteDisagree);
        return getText(voteList);
    }

    public String abstainListOfVoters(){
        mouseOver(voteAbstain);
        return getText(voteList);
    }

    public String opinionList(){
        mouseOver(votersOpinion);
        return getText(voteList);
    }

    public Boolean voteResultInFirstQuestion(String result) throws InterruptedException {

        mouseOver(firstQuestionAgree);
        sleep(1000);
        String a = getText(voteList);
        System.out.println(a);
        return a.equals(result);
    }

    public Boolean voteResultInSecondQuestion(String result) throws InterruptedException {

        mouseOver(secondQuestionDisagree);
        sleep(1000);
        String a = getText(voteList);
        System.out.println(a);
        return a.equals(result);
    }

    public Boolean voteResultInFirstSubQuestion(String userName){
        mouseOver(firstSubQuestion);
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String a = getText(voteList);
        System.out.println(a);
        return a.equals(userName);
    }

    public Boolean voteResultsInFirstQuestionMMK(String userName){
        Boolean res = false;
        System.out.println("===================================================");
        System.out.println("ОТВЕТ: " + getValue(By.cssSelector("div.ng-scope.ng-isolate-scope > ng-include > div > div > table > tbody > tr:nth-child(2) > td:nth-child(2) > img"), "src")
                );
        if(getText(By.cssSelector("tbody tr th:nth-child(2)")).equals(userName) && getValue(By.cssSelector("div.ng-scope.ng-isolate-scope > ng-include > div > div > table > tbody > tr:nth-child(2) > td:nth-child(2) > img"), "src")
                .contains("/images/vote_agree.png")){
            res = true;
        }
        return res;

    }

    public Boolean voteResultsInSecondQuestionMMK(String userName){
        Boolean res = false;
        System.out.println("==================================================");
        System.out.println("Второй вопрос " + getValue(By.cssSelector("div.ng-scope.ng-isolate-scope > ng-include > div > div > table > tbody > tr:nth-child(3) > td:nth-child(2) > img"), "src")
                .contains("/images/vote_disagree.png"));
        if(getText(By.cssSelector("tbody tr th:nth-child(2)")).equals(userName) && getValue(By.cssSelector("div.ng-scope.ng-isolate-scope > ng-include > div > div > table > tbody > tr:nth-child(3) > td:nth-child(2) > img"), "src")
                .contains("/images/vote_disagree.png")){
            res = true;
        }
        return res;
    }

    public Boolean voteResultInSecondSubQuestion(String userName){
        mouseOver(secondSubQuestion);
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String a = getText(voteList);
        System.out.println(a);
        return a.equals(userName);
    }
    public Boolean voteResultInSecondQuestionIfSubQ(String userName){
        mouseOver(secondQuestionDisagreeWithSubQ);
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String a = getText(voteList);
        System.out.println(a);
        return a.equals(userName);
    }

    public void backToMeeting() {
        jsClick(backToMeeting);
    }

    public void goToSpeakers(String customer) {
        if(!customer.equals("expob")) {
            jsClick(goToSpeakersTab);
        }else {
            jsClick(goToSpeakersTabExob);
        }
    }

    public List<WebElement> getSpeakersSize(){
        return getListOfWebElements(speakers);
    }

    public void questionPageIsLoaded() {
        elementAvailability(questionInfo);
    }

    public void setEndDate(String value) {
        elementAvailability(toTime);
        clearField(toTime);
        type(toTime, value);
    }

    public void setStartDate(String value){
        elementAvailability(fromTime);
        clearField(fromTime);
        type(fromTime, value);
    }

    public void deleteMeeting(){
        jsClick(deleteBtn);
        alertOk();
        preloaderGone();
    }

    public void deleteQuestionByName(String name){
        for(WebElement e : getListOfWebElements(questionContainer)){
            if(getText(e.findElement(questionNameTitle)).equals(name)){
                System.out.println("УДАЛЕНИЕ ВОПРОСА: " + getText(e.findElement(questionNameTitle)));
                jsClick(e.findElement(removeQuestion));
                alertOk();
                try {
                    gone(e.findElement(questionNameTitle));
                }catch (StaleElementReferenceException sere){
                    System.out.println("Вопрос пропал");
                }
                elementAvailability(backToCalendar);
                break;
            }
        }

    }
    public void goToParticipant(){
        elementAvailability(goToParticipants);
        jsClick(goToParticipants);
    }
    public void addParticipant(String name){
        goToParticipant();
        jsClick(addParticipantButton);
        elementAvailability(modal);
        jsClick(labelVoting);
        jsClick(participantName);
        type(participantName, name);
        pickFromSearchMenu(participantName);
        gone(modal);
        saveButtonClick();
    }

    public int participantsSize(){
        return getListOfWebElements(participantsRow).size();
    }

    public void deleteParticipant(String name, String customer) {
        for(WebElement el : getListOfWebElements(participantsRow)){
            if(el.findElement(participantNameInRow).getText().contains(name)){
                if(customer.equals("mmk") ){
                    jsClick(el.findElement(removeParticipantMMK));
                    alertOk();
                }else if(customer.equals("expob")){
                    jsClick(el.findElement(removeParticipantExpo));
                    alertOk();
                }
                else{
                    jsClick(el.findElement(removeParticipant));
                    alertOk();
                }
                saveButtonClick();
                break;
            }
        }
    }
    public void sendToApprove(String customer) throws InterruptedException {
        sendToApproveClick();
        if(customer.equals("test")){
            sendMaterialByEmail();
        }
        saveButtonClick();
        preloaderGone();
        sleep(2000);
    }



    public void endMeeting(){
        jsClick(endMeetingBtn);
        preloaderGone();
    }

    public void createAgenda(String customer) {
        if(customer.equals("rshb")){
            jsClick(By.cssSelector("div.btn-wrapper button.btn.btn-default:nth-child(3)"));
        }else if(customer.equals("mmk")){
            jsClick(By.cssSelector("div.btn-wrapper button.btn.btn-default:nth-child(4)"));
        }
        else{
            jsClick(By.cssSelector("div.btn-wrapper button.btn.btn-default:nth-child(5)"));
        }
        preloaderGone();
    }

    public void createExtract() {
        jsClick(By.cssSelector("div.btn-wrapper button.btn"));
        preloaderGone();
    }

    public void createUDP(){
        jsClick(By.cssSelector("div.btn-wrapper button.btn"));
        preloaderGone();
    }

    public void createExtractForAll(){
        jsClick(By.cssSelector("div.btn-wrapper button.btn:nth-child(2)"));
    }

    public Boolean checkAgenda(String agenda,String meetingName) {
        Boolean res = false;
        String name = agenda.substring(0, agenda.length()-5)+"_"+meetingName.replaceAll("[:]","_");
        System.out.println("AGENDA SHOULD BE: " + name);
        for(WebElement ele : getListOfWebElements(By.cssSelector("div.directory-row span.file-name"))){
            if(ele.getText().contains(name)){
                res = true;
                break;
            }
        }
        return res;
    }

    public Boolean checkExtract(String extractName) {
        Boolean res = false;
        String name = extractName;
        for(WebElement ele : getListOfWebElements(By.cssSelector("div.directory-row span.file-name"))){
            if(ele.getText().contains(name)){
                res = true;
                break;
            }
        }
        return res;
    }

    public void waitArticle() {
        elementAvailability(By.cssSelector("article.alertify-log"));
        System.out.println(getText(By.cssSelector("article.alertify-log")));
    }

    public boolean checkUDP() {
        Boolean res = false;
        String info = getText(By.cssSelector("div.epd-info"));
        if(info.contains("Сформированы") && !info.contains("Сформировано не полностью"))
        {
            res =true;
        }
        return res;
    }

    public void goToAgenda(){
        elementAvailability(goToAgenda);
        jsClick(goToAgenda);
    }


    public Boolean checkPresence() {
        Boolean res = true;
        elementAvailability(By.cssSelector("th.col-ordered.ng-binding.ng-scope"));
        for(WebElement ele: getListOfWebElementsWithoutWait(By.cssSelector("td[ng-if=\"membersPresenceEnabled && isRegularMeeting()\"]"))){
            if(!ele.findElement(By.cssSelector("input")).isSelected()){
                res = false;
                break;
            }
        }
        return res;
    }

    public Boolean uncheckedPresence(){
        jsClickWithoutWait(By.cssSelector("td[ng-if=\"membersPresenceEnabled && isRegularMeeting()\"] label"));
        return getElement(By.cssSelector("input.ng-untouched.ng-valid.ng-empty.ng-dirty.ng-valid-parse")).isSelected();
    }

    public boolean isMeetingCreated(String pastMeetingName) {
        Boolean res = false;
        for(WebElement ele: getListOfWebElements(By.cssSelector("p.boldNames.ng-binding"))){
            if(ele.getText().equals(pastMeetingName)){
                res = true;
            }
        }
        return res;
    }

    public String getSubQuestionTitle(String name){
        String title = null;
        for(WebElement element : getListOfWebElements(subQuestionInAgendaTitle)){
            if(element.getText().equals(name)){
                title = element.getText();
                break;
            }
        }
        return title;
    }

    public boolean checkSpecialOpinion(String opinionText,String user, String customer) {
        boolean res = false;
        if(customer.equals("suek") || customer.equals("avtodor")){
            jsClick(opinionInResultsWithSubQuestions);
        }else if(customer.equals("expob")) {
            jsClick(opinionInResultsExpoB);
        }
        else{
            jsClick(opinionInResults);
        }
        res = checkOpinionInModal(opinionText,user);
        return res;
    }

    public boolean checkOpinionInModal(String opinionText,String user){
        boolean a  = false;
        elementAvailability(By.cssSelector("div.modal-content"));
        if(getText(By.cssSelector("div.modal-content tr.ng-scope td")).equals(user) &&
        getText(By.cssSelector("div.modal-content tr.ng-scope td:nth-child(3)")).equals(opinionText)){
            a = true;
        }
        return a;
    }

    public void closeOpinionModal(){
        jsClick(closeOpinionModal);
        gone(By.cssSelector("div.modal-content"));
    }

    public boolean getVoteResultForQuestion(String s) {
        boolean res = false;
        res = getText(abstainedVoteInResultTable).equals(s);
        return res;
    }

    public void goToRecommendations() {
        jsClick(recommendations);
        preloaderGone();
    }

    public void selectAllAbstained() {
        jsClick(recommendationAllAbstainedBtn);
        preloaderGone();
    }

    public boolean checkAllRecommendations(){
        Boolean res = false;
        for(WebElement el : getListOfWebElements(recommendationsRow)){
             if(el.findElement(By.cssSelector("table tbody tr.ng-scope label:nth-child(3) input")).getAttribute("checked").equalsIgnoreCase("true")){
                 res = true;
             }else{
                 res = false;
                 break;
             };
        }
        return res;
    }

    public void addTradeSecret(String[] paths) {

        uploadMaterialsWithTradeSecret(paths);
    }

    public void addPasswordToTradeSecret(String s) {
        elementAvailability(modal);
        type(passwordInputTS,s);
        jsClick(submitBtn);
    }

    public Boolean isTradeSecretAdded() {
        Boolean res = false;
        for(WebElement e : getListOfWebElements(By.cssSelector("div.directory-row-subcontainer"))){
            if((e.findElement(By.cssSelector("div.directory-row-subcontainer span:nth-child(4)")).getText().contains("Коммерческая тайна")) &&
                    (e.findElement(By.cssSelector("div.directory-row-subcontainer span:nth-child(2)")).getText().contains("Коммерческая тайна"))){
                res = true;
                break;
            };
        }
        return res;
    }
    public List<String> checkEveryStatus() {
        List<String> s = new ArrayList<String>();
        long startTime = System.currentTimeMillis();
        long currentTime = 0;
        for (int i = 0; i < materialStatus().size(); i++) {
            Boolean continueCycle = materialStatus().get(i).equals("Коммерческая тайна Комментарии: 0");
            if (continueCycle) {
                s.add("Good");
            } else {
                while (continueCycle == false) {
                    refresh();
                    goToMaterialsTabInMeeting();
                    currentTime = System.currentTimeMillis() - startTime;
                    if (materialStatus().get(i).equals("Коммерческая тайна Ошибка при конвертации PDF Комментарии: 0")) {
                        s.add("Error");
                        scroll(250);
                        continueCycle = true;
                    } else if (currentTime > 600000) {
                        scroll(259);
                        break;
                    } else if (materialStatus().get(i).equals("Коммерческая тайна Комментарии: 0")) {
                        s.add("Good");
                        continueCycle = true;
                    }
                }
            }
        }
        return s;
    }

    public List<String> materialStatus() {
        List<String> statuses = new ArrayList<String>();
        elementAvailability(By.cssSelector("div.material-item-container"));
        for (WebElement status : getListOfWebElements(By.cssSelector("div.material-item-container"))) {
            statuses.add(status.findElement(By.cssSelector("div.material-status")).getText());
        }

        return statuses;
    }

    public boolean isCheckedVetoRuleInMeeting(String name){
        boolean res = false;
        for(WebElement e : getListOfWebElements(By.cssSelector("div.tabContent tbody tr"))){
            System.out.println("===============Name in table========================");
            System.out.println(e.findElement(By.cssSelector("td:nth-child(1)")));
            if(name.contains(e.findElement(By.cssSelector("td:nth-child(1)")).getText())){
                res = isSelectedWithoutWait(e.findElement(By.cssSelector("td:nth-child(6) input")));
                break;
            }
        }
        return res;
    }

    public void uncheckVotingRule(String name){
        for(WebElement e : getListOfWebElements(By.cssSelector("div.tabContent tbody tr"))){
            if(name.equals(e.findElement(By.cssSelector("td:nth-child(1)")).getText())){
                jsClick(e.findElement(By.cssSelector("td:nth-child(5) label")));
                break;
            }
        }
    }
}
