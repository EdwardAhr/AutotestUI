package Collegial;

import PageObject.TestHelper;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CollegialMeetingCardPage extends TestHelper {
    WebDriver driver;
    WebDriverWait wait;

    public CollegialMeetingCardPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        this.driver = driver;
        this.wait = wait;
    }
    //Все модальные окна и кнопки да/нет
    private By modal = By.cssSelector("div.modal-container");
    private By acceptBtn = By.cssSelector("button.btn.btn-default:nth-child(1)");
    private By saveOpinion = By.cssSelector("button.btn.btn-success");
    private By cancel = By.cssSelector("button.modal-buttons__cancel.btn");
    private By notificationAlert = By.cssSelector("div.vue-notification-template.error");
    private By notificationInscription = By.cssSelector("span.notification");
    private By info = By.cssSelector("button.btn.-small.-icon");
    private By headerTitle = By.cssSelector("div.questions__header-title");
    private By attachment = By.cssSelector("div.modal-container div.attachment");
    private By attachmentOnVoteResults = By.cssSelector("div.attachment.attachment__document");
    private By voteYes = By.cssSelector("button.voting-button.-agree");
    private By voteNo = By.cssSelector("button.voting-button.-disagree");
    private By voteAbstained = By.cssSelector("button.voting-button.-abstain");
    private By nextQuestion = By.cssSelector("button.arrow-icon-button.arrow.-right");
    private By userName = By.cssSelector("div.user__name");

    private By closeBtn = By.cssSelector("div.modal-close.-external");
    private By voteResultsPageTitle = By.cssSelector("div.control-report__title");
    private By submitOpinionAdding = By.cssSelector("button.btn.btn-default");
    private By denyOpinionAdding =By.cssSelector("button.modal-buttons__cancel");



    private By opinionText = By.cssSelector("textarea.question-comment");

    private By createOmPm = By.cssSelector("div.footer__button-group button.btn");

    private By opinionBtn = By.cssSelector("button.voting-button.button-comment");

    private By voteBlock = By.cssSelector("div.voting");
    private By voteListElement = By.cssSelector("li.li");
    private By voteListName = By.className("div.truncated-text");
    private By voteListResult = By.cssSelector("li.li div.question-status span");

    public void voteYes(){
        jsClick(voteYes);
        System.out.println("Yes");
        preloaderGone();
    }
    public void voteNo(){
        jsClick(voteNo);
        System.out.println("No");
        preloaderGone();
    }
    public void voteAbstain(){
        jsClick(voteAbstained);
        preloaderGone();
    }
    public void vote(String vote, String customer){
        if(vote.equals("Yes")){
            if(customer.equals("rshb") || customer.equals("nspk")){
                voteYes();
                confirmVote();
            }else if(customer.equals("avtodor")){
                voteYes();
                areYouSure();
            }
            else{
                voteYes();
            }
        }
        if(vote.equals("No")){
            if(customer.equals("rshb") || customer.equals("nspk")){
                voteNo();
                confirmVote();
            }else if(customer.equals("avtodor")){
                voteNo();
                areYouSure();
            }
            else{
                voteNo();

            }
        }
    }

    public void revote(String vote, String customer){
        if(vote.equals("Yes")){
            if(customer.equals("rshb") || customer.equals("nspk")){
                voteYes();
                confirmVote();

            }else if(customer.equals("avtodor")){
                voteYes();
                areYouSure();
            }
            else{
                voteYes();
            }
        }
        if(vote.equals("No")){
            if(customer.equals("rshb") || customer.equals("nspk")){
                voteNo();
                confirmVote();

            }else if(customer.equals("avtodor")){
                voteNo();
                areYouSure();
            }
            else{
                voteNo();
            }
        }
    }

    public void nextQuestion(){
        jsClick(nextQuestion);
        preloaderGone();
    }
    public void goToVotingResults(){
        jsClick(nextQuestion);
    }
    public String checkUser(){
        return getText(userName);
    }


    public Boolean checkVoteResults(String firstResult, String secondResult) {
        boolean a = false;
        elementAvailability(voteListResult);
        List<WebElement> el = driver.findElements(voteListResult);
        if (el.get(0).getText().equals(firstResult) && el.get(1).getText().equals(secondResult)){
            a = true;
        }
        return a;
    }

    public Boolean checkVoteOnSubQuestion(String first, String second){
        boolean b = false;
        elementAvailability(voteListResult);
        if(getText(By.cssSelector("li.li.-sub:nth-child(2) div.question-status span")).equals(first) &&
        getText(By.cssSelector("li.li.-sub:nth-child(3) div.question-status span")).equals(second)){
            b = true;
        }
        return b;
    }

    public Boolean checkVote(WebElement el, String result){
        Boolean a = el.findElement(voteListResult).getText().equals(result);
        return a;
    }

    public void createOlPm(String customer) {

//        mouseOver(createOpinion);

        enabled(createOmPm);
        jsClick(createOmPm);
        makeScreenshot();
        preloaderGone();
        if(!customer.equals("dkudzo")){
            refresh();
            preloaderGone();
        }

    }

    public Boolean isOlPmCreated() throws InterruptedException {
        jsClick(info);
        elementAvailability(modal);
        elementAvailability(attachment);
        return isDisplayed(attachment);
    }
    public void closeModal(){
        jsClick(closeBtn);
        gone(modal);
    }
    public void confirmVote(){
        elementAvailability(modal);
        jsClick(saveOpinion);
        gone(modal);
        preloaderGone();
    }

    public void areYouSure(){
        elementAvailability(modal);
        jsClick(acceptBtn);
        gone(modal);
        preloaderGone();
    }

    public void denyOpinionAdddingMMK(){
        elementAvailability(modal);
        jsClick(denyOpinionAdding);
        gone(modal);
        preloaderGone();
    }
    public void acceptOpinionAdddingMMK(String text){
        elementAvailability(modal);
        jsClick(acceptBtn);
        addSpecialOpinion(text);
    }

    public void checkOlPMAlert() {
        elementAvailability(notificationAlert);
        System.out.println("NOTIFICATION " + getText(notificationAlert));
    }

    public Boolean checkAlertInscriptionOnVotingResults() {
        System.out.println(notificationInscription);
        return isDisplayed(notificationInscription);
    }
    public Boolean isOnResultVotePage(){
        elementAvailability(voteResultsPageTitle);
        return (isDisplayed(voteResultsPageTitle) && getText(voteResultsPageTitle).equals("Проверьте ваши ответы перед отправкой"));
    }

    public String yesBtnColor() {
        System.out.println("VOTE COLOR: " + getColor(voteYes));
        return getColor(voteYes);
    }
    public String noBtnColor() {

        System.out.println("VOTE COLOR: " + getColor(voteNo));
        return getColor(voteNo);
    }


    public Boolean checkVoteDisabled() {
        Boolean res = false;
        elementAvailability(info);

        try {
            isDisplayed(voteYes);
        }catch (NoSuchElementException nsee){
            res = true;
        }
        return res;
    }

    public boolean isOnResultsPageInPastMeeting() {
        elementAvailability(voteResultsPageTitle);
        return (isDisplayed(voteResultsPageTitle) && getText(voteResultsPageTitle).equals("Результаты голосования"));
    }

    public boolean checkSecondQuestionVoteWithoutSubQuestion(String voteResult) {
        boolean a = false;
        a = getText(By.cssSelector("li.li:nth-child(4) div.question-status span")).equals(voteResult);
        return a;
    }

    public void addSpecialOpinionForcibly(String text){
        jsClick(opinionBtn);
        addSpecialOpinion(text);
    }

    public void addSpecialOpinion(String text){
        elementAvailability(modal);
        type(opinionText, text);
        jsClick(saveOpinion);

        gone(modal);
        preloaderGone();
    }

    public boolean checkSpecialOpinionInVoteResults(String expectedText) {
        boolean a = false;
        elementAvailability(By.cssSelector("div.comment span"));
        a = getText(By.cssSelector("div.comment span")).equals(expectedText);
        return a;
    }

    public boolean isRecommendationDisabled() {
        Boolean res = false;
        try{
            elementAvailability(By.cssSelector("div.voiting-recommendations-info"));
        }catch (TimeoutException te){
            res = true;
        }
        return res;
    }

    public boolean checkRecommendation(String text){
        Boolean res = false;
        res = getText(By.cssSelector("div.voiting-recommendations-info span.text")).equals(text);
        return res;
    }

    public void openCloseAgenda() {
        jsClick(By.cssSelector("button.footer__menu.footer__button-default"));
    }

    public int getQuestionSizeInAgenda() {
        int res = getListOfWebElements(By.cssSelector("li.meeting__list")).size();
        return res;
    }
}
