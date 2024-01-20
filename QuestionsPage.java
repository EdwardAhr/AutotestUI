package PageObject;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.lang.Thread.sleep;

public class QuestionsPage extends TestHelper {
    WebDriver driver;
    WebDriverWait wait;
    public QuestionsPage(WebDriver driver, WebDriverWait wait){
        super(driver, wait);

    }

    private By questionTitle = By.cssSelector("a.question-title");
    private By questionItem = By.cssSelector("div.question-item");
    private By questionRow = By.cssSelector("div.question-row");
    private By removeFirstQuestion = By.cssSelector("div.question-item div.col-sm-1 :nth-child(2)");
    private By removeBtn = By.cssSelector("a.ng-scope:nth-child(2)");
    @FindBy(xpath = "//*[@id='page-content-wrapper']/div/div/ng-include[2]/div[2]")
    private WebElement QuestionsTable;

    @FindBy(xpath = "//*[@id='page-content-wrapper']/div/div/ng-include[2]/div[2]/div[1]/div/div[2]/a[2]/i")
    private WebElement firstRemoveGlyph;

    @FindBy(xpath = "//*[@id='alertify-ok']")
    private WebElement yesOnPopUpWindow;


    public boolean isInitialized() {

        String window = driver.getWindowHandle();
        driver.switchTo().window(window);
        return QuestionsTable.isDisplayed();
    }

    public void clickLastQuestion(){

        int CountElements = driver.findElements(By.xpath("//*[@id='page-content-wrapper']/div/div/ng-include[2]/div[2]/div[*]/div/div[1]/a")).size();

        WebElement LastQuestion = driver.findElement(By.xpath("//*[@id='page-content-wrapper']/div/div/ng-include[2]/div[2]/div["+CountElements+"]/div/div[1]/a"));
        JavascriptExecutor LastQuestionJS = (JavascriptExecutor)driver;
        LastQuestionJS.executeScript("arguments[0].click()", LastQuestion);

    }

    public void clickRightQuestion(String Name, String Time){

        int CountElements = driver.findElements(By.xpath("//*[@id='page-content-wrapper']/div/div/ng-include[2]/div[2]/div[*]/div/div[1]/a")).size();

        String QuestionName = Name+Time;

        for(int i = 1; i<=CountElements; i++){
            WebElement RightQuestion = driver.findElement(By.xpath("//*[@id='page-content-wrapper']/div/div/ng-include[2]/div[2]/div["+i+"]/div/div[1]/a"));
            String CurentName = RightQuestion.getText();
            if(QuestionName.equals(CurentName)){
                RightQuestion.click();
                break;
            }

        }

    }

    public String getNameLastQuestion(){

        int CountElements = driver.findElements(By.xpath("//*[@id='page-content-wrapper']/div/div/ng-include[2]/div[2]/div[*]/div/div[1]/a")).size();
        WebElement LastQuestion = driver.findElement(By.xpath("//*[@id='page-content-wrapper']/div/div/ng-include[2]/div[2]/div["+CountElements+"]/div/div[1]/a"));
        String nameLastQuestion = LastQuestion.getText();
        return nameLastQuestion;
    }

    public String getStatusLastQuestion(){

        int CountElements = driver.findElements(By.xpath("//*[@id='page-content-wrapper']/div/div/ng-include[2]/div[2]/div[*]/div/div[1]/a")).size();
        WebElement LastQuestion = driver.findElement(By.xpath("//*[@id='page-content-wrapper']/div/div/ng-include[2]/div[2]/div["+CountElements+"]/div/div[1]/div"));
        String statusLastQuestion = LastQuestion.getText();
        return statusLastQuestion;
    }

    public String getStatusRightQuestion(String Name, String Time){

        int CountElements = driver.findElements(By.xpath("//*[@id='page-content-wrapper']/div/div/ng-include[2]/div[2]/div[*]/div/div[1]/a")).size();
        int x = 0;


        String QuestionName = Name+Time;

        for(int i = 1; i<=CountElements; i++){
            WebElement RightQuestion = driver.findElement(By.xpath("//*[@id='page-content-wrapper']/div/div/ng-include[2]/div[2]/div["+i+"]/div/div[1]/a"));
            x=i;
            String CurentName = RightQuestion.getText();
            if(QuestionName.equals(CurentName)){

                break;
            }

        }
        WebElement LastQuestion = driver.findElement(By.xpath("//*[@id='page-content-wrapper']/div/div/ng-include[2]/div[2]/div["+x+"]/div/div[1]/div"));
        String statusLastQuestion = LastQuestion.getText();
        return statusLastQuestion;

    }

    public void deleteAllQuestions() throws InterruptedException {

        int CountElements = driver.findElements(By.xpath("//*[@id='page-content-wrapper']/div/div/ng-include[2]/div[2]/div[*]/div/div[1]/a")).size();

        while (CountElements!=0){

            CountElements = driver.findElements(By.xpath("//*[@id='page-content-wrapper']/div/div/ng-include[2]/div[2]/div[*]/div/div[1]/a")).size();

            JavascriptExecutor firstRemoveGlyphJS = (JavascriptExecutor)driver;
            firstRemoveGlyphJS.executeScript("arguments[0].click()", firstRemoveGlyph); //тут падает

            String window = driver.getWindowHandle();
            driver.switchTo().window(window);

            JavascriptExecutor yesOnPopUpWindowJS = (JavascriptExecutor)driver;
            yesOnPopUpWindowJS.executeScript("arguments[0].click()", yesOnPopUpWindow);


            sleep(500);

        };

    }

    public void deleteLastQuestion() throws InterruptedException {

        int CountElements = driver.findElements(By.xpath("//*[@id='page-content-wrapper']/div/div/ng-include[2]/div[2]/div[*]/div/div[1]/a")).size();

        WebElement lastRemoveGlyph = driver.findElement(By.xpath("//*[@id='page-content-wrapper']/div/div/ng-include[2]/div[2]/div["+CountElements+"]/div/div[2]/a[2]/i"));

        JavascriptExecutor lastRemoveGlyphJS = (JavascriptExecutor)driver;
        lastRemoveGlyphJS.executeScript("arguments[0].click()", lastRemoveGlyph); //тут падает

            String window = driver.getWindowHandle();
            driver.switchTo().window(window);

            JavascriptExecutor yesOnPopUpWindowJS = (JavascriptExecutor)driver;
            yesOnPopUpWindowJS.executeScript("arguments[0].click()", yesOnPopUpWindow);


            sleep(500);

    }

    public int getQuestionsSize() {
        try{
            elementAvailability(By.cssSelector("a.question-title"));
            int size = getListOfWebElements(By.cssSelector("a.question-title")).size();
            return size;
        }catch(TimeoutException e){
            return 0;
        }
    }

    public void getInLastAddedQuestion(){
        elementAvailability(questionTitle);
        click(questionTitle);
    }

    public void deleteFirstQuestion(){
        elementAvailability(questionTitle);
        mouseOver(questionRow);
        jsClick(removeFirstQuestion);
        alertOk();
        preloaderGone();
    }

    public String getTitleOfFirstQuestion(){
        elementAvailability(questionTitle);
        return getText(questionTitle);
    }
    public String getTitleOfQuestion(String qName){
        String res = null;
        elementAvailability(questionTitle);
        for(WebElement element : getListOfWebElements(questionTitle)){
            String name = element.getText();
            if(name.equals(qName)){
                res = name;
                break;
            }
        }
        return res;
    }
    public void goInQuestion(String qName){
        elementAvailability(questionTitle);
        for(WebElement element : getListOfWebElements(questionTitle)){
            if(element.getText().equals(qName)){
                jsClick(element);
                break;
            }
        }
    }

    public void deleteQuestionByName(String name){
        elementAvailability(questionTitle);
        for(WebElement element : getListOfWebElements(questionItem)){
            if(element.findElement(questionTitle).getText().equals(name)){
                hove(element);
                jsClick(element.findElement(removeBtn));
                break;
            }
        }
        alertOk();
        preloaderGone();
    }


}
