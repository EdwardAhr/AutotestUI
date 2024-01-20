package PageObject;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class MeetingsPage extends TestHelper{
    WebDriver driver;
    WebDriverWait wait;
    public MeetingsPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        this.driver = driver;
        this.wait = wait;
    }
    String rightMeetingName;

    @FindBy(xpath = "//*[@id='meetingListContainer']/ng-include[2]/div[1]/div/label[1]")
    private WebElement DayButton;
    //*[@id="meetingListContainer"]/ng-include[2]/div[1]/div/label[2]
    @FindBy(xpath = "//*[@id='meetingListContainer']/ng-include[2]/div[1]/div/label[2]")
    private WebElement MonthButton;


    @FindBy(xpath = "//*[@id='meetingListContainer']/div/div/ul/li[1]/div/div[1]/a/p")
    private WebElement lastMeeting;

    @FindBy(xpath = "//*[@id='meetingListContainer']/div/div/ul/li/div/div[2]/div[3]")
    private WebElement firstMeetingInTableStatus;

    By meetings = By.cssSelector("div.meeting-group.ng-scope");

    public boolean isInitialized() {

        String window = driver.getWindowHandle();
        driver.switchTo().window(window);
        return true;
    }


    public void clickRightMeeting(String meetingName, String customer, String number){
        System.out.println("THIS IS MEETING: " + meetingName);
            elementAvailability(By.cssSelector("div.full-week-days__row"));
            rightMeeting(By.cssSelector("div.full-week-days__row"),
                    meetingName, customer, number);
            preloaderGone();
//            try{
//                getText(By.cssSelector("h2.form-control-label.form-title.ng-binding"));
//            }catch(NoSuchElementException nsee){
//                rightMeeting(By.cssSelector("div.full-week-days__row"),
//                        meetingName, customer, number);
//            }catch (TimeoutException t){
//                System.out.println(t);
//            }

    }

    public boolean goneArticle(){
        return gone(By.cssSelector("article.alertify-log.alertify-log-success.alertify-log-show"));
    }

    public String rightMeetingInTableStatusGet(String meetingName, String customer, String number){

        rightMeetingName = meetingName;

        String status = "";
        if(customer.equals("rshb")){
            rightMeetingName = rightMeetingName + " (с учетом письменного мнения)";
            System.out.println(rightMeetingName);
        }else if(customer.equals("avtodor")){
            rightMeetingName = number + " " + rightMeetingName;
        }else if(customer.equals("rico")){
            rightMeetingName = meetingName.substring(0,rightMeetingName.length()-17)+", очная встреча";
        }
        List<WebElement> meetings = driver.findElements(By.cssSelector("li.list-group-item"));
        for(WebElement m : meetings){
            String name = m.findElement(By.cssSelector("p.boldNames.ng-binding")).getText();
            if(name.equals(rightMeetingName)){
                System.out.println("STATUS_STATUS_STATUS_STATUS_STATUS_STATUS_STATUS_STATUS");
                status = status + m.findElement(By.cssSelector("li div:nth-child(2)")).getText();
                System.out.println("STATUS_STATUS_STATUS_STATUS_STATUS_STATUS_STATUS_STATUS");
        }
        }
        if(customer.equals("dkudzo")){
            status = status.replaceAll("[\n]", " ");
        }
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("Status equals: " + status);
        System.out.println("-----------------------------------------------------------------------");
        return status;
    }

    public int getMeetingsSize() {
        int countElements = 0;

        try{
            elementAvailability(By.cssSelector("div.full-week-days__row"));
            countElements = getListOfWebElements(By.cssSelector("div.full-week-days__row")).size();
            System.out.println("События найденые "+ countElements);
            return countElements;
        }
        catch (TimeoutException e){
            return countElements;
        }
    }

    public void waitMeetingsLoad(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(meetings));
    }
    public void meetingPageRefresh(){
        refreshWithWait(meetings);
    }


    public String checkStatus(String meetingName, String status, String customer, String number) throws InterruptedException {
        String s = rightMeetingInTableStatusGet(meetingName, customer, number);
        long start = System.currentTimeMillis();
        long end = 0;
        while (s.equals(status)){
            end = System.currentTimeMillis() - start;
            meetingPageRefresh();
            elementAvailability(meetings);
            s = rightMeetingInTableStatusGet(meetingName, customer, number);
            if(end == 600000){
                break;
            }
        }
        return s;
    }
}
