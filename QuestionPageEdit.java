package PageObject;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static java.lang.Thread.sleep;

import static java.lang.Thread.sleep;

public class
QuestionPageEdit extends TestHelper {
    WebDriver driver;
    WebDriverWait wait;

    public QuestionPageEdit(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }


    //Общие элементы
    private By copyQuestionBtn = By.cssSelector("label.btn.btn-default.meetingEdit-Btn.ng-binding.ng-scope");
    private By questionTitleTextarea = By.cssSelector("div.form-group.row:nth-child(2) textarea");

    private By saveBtn = By.cssSelector("input.btn.btn-success");

    //Элементы страницы Докладчики
    private By goToSpeakers = By.xpath("//*[contains(text(),'Докладчики')]");
    private By speaker = By.cssSelector("div.member_unit");
    private By questionInfo = By.cssSelector("div.corners.edit.no-bottom-shadow");

    //Элементы страницы Материалы
    private By materials = By.cssSelector("li.question__tabs_materials a");
    private By materialsRow = By.cssSelector("div.material-item-container");
    private By materialsDropDownBtn = By.cssSelector("div.material-item-container button");
    private By dropDownMenuFirstAttach = By.cssSelector("body > div:nth-child(5)");

    private By deleteMaterial = By.cssSelector("div:nth-child(5) > ul > li:nth-child(1) > a");

    private By dropDownMenuFirstAttachMMK =  By.cssSelector("body > div:nth-child(5)");

    private By deleteMaterialMMK = By.cssSelector("body > div:nth-child(5) > ul > li a");

    private By materialName = By.cssSelector("span.file-name");
    private By materialVersion = By.cssSelector("span.file-version");
    //Подвопросы
    private By subquestion = By.cssSelector("li.question__tabs_subquestions a");
    private By addSubquestionBtn = By.cssSelector("button.btn.btn-default.ng-binding");
    private By addNewSubquestionOption = By.cssSelector("question-panel ul > li:nth-child(1) > a");
    private By addExistedSubquestionOption = By.cssSelector("question-panel ul > li:nth-child(2) > a");
    private By questionNameTitle = By.cssSelector("a.edit-meetings__meeting_title.ng-binding");
    private By decision = By.cssSelector("li.question__tabs_decision a");
    private By createDecisionBtn = By.cssSelector("button.btn.btn-default.ng-binding");
    private By modal = By.cssSelector("div.modal-content");
    private By saveDecisionBtn = By.cssSelector("div.modal-content input[value=\"Сохранить\"]");
    private By textAreaModal = By.cssSelector("div.modal-content textarea.form-control.ng-pristine.ng-valid.ng-empty");
    private By executorInput = By.cssSelector("div.modal-content div.form-group.row:nth-child(4) input");
    private By decisionName = By.cssSelector("ul.decision-list li a.ng-binding");
    private By decisionCloseBtn = By.cssSelector("ul.decision-list li a:nth-child(2)");
    //Элементы страницы Проект решения


    //Элементы страницы Делегирование
    private By delegation = By.cssSelector("li.question__tabs_delegation a");
    private By delegateAbstained = By.cssSelector("tbody tr td:nth-child(4) label:nth-child(3)");
    private By voteDomain = By.cssSelector("tbody tr td:nth-child(2)");
    private By voteRowInTable = By.cssSelector("table tbody tr");
    private By nameInRow = By.cssSelector("td:nth-child(1)");
    private By voteInRow = By.cssSelector("td:nth-child(2)");
    private By abstainedInRow = By.cssSelector("td:nth-child(4) label:nth-child(3)");
    private By denyInRow = By.cssSelector("td:nth-child(4) label:nth-child(2)");
    private By forInRow = By.cssSelector("td:nth-child(4) label:nth-child(1)");

    public void saveQuestion(){
        jsClick(saveBtn);
        preloaderGone();
    }

    public void switchToWindow(WebDriver driver) {
        String window = driver.getWindowHandle();
        driver.switchTo().window(window);
    }

//    public String saveNotificationShow(){
//        WebElement DoscumentSavedPopUp = waitForFuckingQuestionSaved.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='alertify-logs']/article")));
//        String s = driver.findElement(By.xpath("//*[@id='alertify-logs']/article")).getText();
//        return s;
//    }


    //Методы для страницы Материалы

    public void goToMaterials() {
        elementAvailability(materials);
        jsClick(materials);
    }
    public void goToDecision(){
        elementAvailability(decision);
        jsClick(decision);
    }

    public void pageDown(){
        pageDown(questionTitleTextarea);
    }

    public int materialsSize() {
        elementAvailability(By.cssSelector("div.material-item-container"));
        int size = getListOfWebElements(By.cssSelector("div.material-item-container")).size();
        return size;
    }

    public void goToSubquestion(){
        elementAvailability(subquestion);
        jsClick(subquestion);
    }

    public boolean checkMaterialName(String attachName){
        Boolean res = false;
        for(WebElement element : getListOfWebElements(By.cssSelector("a.file-link"))){
            String material = element.getText();
            System.out.println(material);
            if(material.equals(attachName)){
                res = true;
                break;
            }
        }
        return res;
    }

    public List<String> materialStatus() {
        List<String> statuses = new ArrayList<String>();
        elementAvailability(By.cssSelector("div.material-item-container"));
        for (WebElement status : getListOfWebElements(By.cssSelector("div.material-item-container"))) {
            statuses.add(status.findElement(By.cssSelector("div.material-status")).getText());
        }

        return statuses;
    }

    //Говно
    public Boolean cheackConvertation(int value) {
        List<String> validStatuses = new ArrayList<String>();
        for (int i = 0; i < value; i++) {
            validStatuses.add("Комментарии: 0");
        }
        Boolean ok = validStatuses.equals(materialStatus());
        long startTime = System.currentTimeMillis();
        long currentTime = 0;

        while (ok == false) {
            refresh();
            goToMaterials();
            currentTime = System.currentTimeMillis() - startTime;
            if (materialStatus().contains("Ошибка при конвертации PDF Комментарии: 0")) {
                scroll(250);
                break;
            } else if (currentTime > 600000) {
                System.out.println("Зависла");
                scroll(250);
                break;
            } else {
                ok = validStatuses.equals(materialStatus());
            }
        }

        return ok;
    }

    //вариант с каждым статусом
    public List<String> checkEveryStatus() {
        List<String> s = new ArrayList<String>();
        long startTime = System.currentTimeMillis();
        long currentTime = 0;
        for (int i = 0; i < materialStatus().size(); i++) {
            Boolean continueCycle = materialStatus().get(i).equals("Комментарии: 0");
            if (continueCycle) {
                s.add("Good");
            } else {
                while (continueCycle == false) {
                    refresh();
                    goToMaterials();
                    currentTime = System.currentTimeMillis() - startTime;
                    if (materialStatus().get(i).equals("Ошибка при конвертации PDF Комментарии: 0")) {
                        s.add("Error");
                        scroll(250);
                        continueCycle = true;
                    } else if (currentTime > 600000) {
                        scroll(259);
                        break;
                    } else if (materialStatus().get(i).equals("Комментарии: 0")) {
                        s.add("Good");
                        continueCycle = true;
                    }
                }
            }
        }
        return s;
    }

    public void goToSpeakers() {
        elementAvailability(goToSpeakers);
        jsClick(goToSpeakers);
    }


    public List<WebElement> getSpeakersSize(){
         return getListOfWebElements(speaker);
    }
    public void questionPageEditIsLoaded(){
        elementAvailability(questionInfo);
    }

    public void deleteFirstAttach(String customer) throws InterruptedException {
        jsClick(materialsDropDownBtn);

        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        isDisplayed(dropDownMenuFirstAttach);
       try{
           jsClickWithoutWait(deleteMaterial);
       }catch (NoSuchElementException nsee){
           jsClickWithoutWait(deleteMaterial);
       }

        sleep(1000);
//        if(customer.equals("mmk")){
//            isDisplayed(dropDownMenuFirstAttachMMK);
//            jsClick(deleteMaterialMMK);
//        }else{
//            isDisplayed(dropDownMenuFirstAttach);
//
//            jsClick(deleteMaterial);
//        }
        sleep(1000);
        alertOk();
        sleep(1000);
        saveQuestion();
    }

    public Boolean isVersionUp(String name, int version) {
        Boolean a = false;
        for(WebElement el : getListOfWebElements(materialsRow)){
            if(el.findElement(materialName).getText().equals(name)){
                System.out.println(el.findElement(materialName).getText());
                System.out.println(el.findElement(materialVersion).getText());
                if(el.findElement(materialVersion).getText().equals("(Версия №" + version + ")")){
                    a = true;
                }
            }
        }
        return a;
    }
    public void copyQuestionBtnClick(){
        jsClick(copyQuestionBtn);
    }

    public void newSubquestionCreation(){
        elementAvailability(addSubquestionBtn);
        jsClick(addSubquestionBtn);
        jsClick(addNewSubquestionOption);
    }

    public void existedSubquestionCreation(){
        elementAvailability(addSubquestionBtn);
        jsClick(addExistedSubquestionOption);
    }

    public String getQuestionTitle(String name){
        String title = null;
        for(WebElement element : getListOfWebElements(questionNameTitle)){
            if(element.getText().equals(name)){
                title = element.getText();
                break;
            }
        }
        return title;
    }

    public void createDecision(String text, String executor) {
        jsClick(createDecisionBtn);
        elementAvailability(modal);
        jsClick(textAreaModal);
        type(textAreaModal,text);
        type(executorInput,executor);
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pickFromSearchMenu(executorInput);
        jsClick(saveDecisionBtn);
        preloaderGone();

    }

    public String getDecisionName() {
        String name  = getText(decisionName);
        return name;
    }

    public void deleteDecision() {
        jsClick(decisionCloseBtn);
        preloaderGone();
    }

    public boolean checkDecision() {
        Boolean res = true;

        try {
            isDisplayed(decisionName);
        }catch (org.openqa.selenium.NoSuchElementException nsee){
            res = false;
        }
        return res;
    }

    public void goToDelegation() {
        jsClick(delegation);
    }

    public void delegateAbstainedVote(){
        for(WebElement e : getListOfWebElements(voteRowInTable)){
           jsClick(e.findElement(abstainedInRow));
            try{
                jsClick( e.findElement(abstainedInRow));
            }catch (StaleElementReferenceException se){
                articleSaveGone();
                try {
                    sleep(3000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                jsClick( e.findElement(abstainedInRow));
            }
        }
    }

    public boolean isAllVotedCorrect(String vote){
        boolean res = false;
        for(WebElement e : getListOfWebElements(voteDomain)){
            res = e.getText().equals(vote);
            if(res == false){
                break;
            }
        }
        return res;
    }

    public void delegateVoteForUser(String user, String vote) {
        for(WebElement e : getListOfWebElements(voteRowInTable)){
            if(e.findElement(nameInRow).getText().replaceAll("[\n]", "").contains(user)){
                if(vote.equals("За")){
                    jsClick(e.findElement(forInRow));
                }else if(vote.equals("Против")){
                    jsClick(e.findElement(denyInRow));
                }else{
                    jsClick(e.findElement(abstainedInRow));
                }
                break;
            }
        }
    }

    public boolean checkDelegateVote(String user, String vote) {
        Boolean res = false;
        for (WebElement e: getListOfWebElements(voteRowInTable)){
            if(e.findElement(nameInRow).getText().contains(user)){
                res = e.findElement(voteInRow).getText().equals(vote);
            }
        }
        return res;
    }
}