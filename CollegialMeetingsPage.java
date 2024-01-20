package Collegial;

import PageObject.TestHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CollegialMeetingsPage extends TestHelper {
    private By meeting = By.cssSelector("li.next-meeting");
    private By meetingName = By.cssSelector("p.next-meeting__name a");

    private By goToPastMeetings = By.cssSelector("a[href='#/meetings/past']");
    private By goToMeetings = By.cssSelector("a[href='#/meetings/next']");

    private By pastMeetingCard = By.cssSelector("article.past-meeting");
    private By pastMeetingName = By.cssSelector("header a");
    private By skypeBtn = By.cssSelector("a.skype_link");

    public CollegialMeetingsPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void getInRightMeetingCollegial(String mName, String customer){
        System.out.println("COLLEGIAL COLLEGIAL COLLEGIAL COLLEGIAL COLLEGIAL COLLEGIAL COLLEGIAL COLLEGIAL");
        for(WebElement e: getListOfWebElements(meeting)){
            WebElement name = e.findElement(meetingName);
            System.out.println("Collegial meeting NAME: " + name.getText());
            System.out.println("MEETING NAME WE GOT FROM KS " + mName);
            String customerMeetingName = "";
//            if(customer.equals("rshb")){
//                customerMeetingName = mName + " (с уче...";
//            }
             if(customer.equals("rico")){
                customerMeetingName = mName.substring(0,mName.length()-17)+", очная";
            }
            else{
                customerMeetingName = mName.substring(0,mName.length()-17);
            }
            System.out.println("CUSTOMER MEETING NAME: " + customerMeetingName);
            if(name.getText().contains(customerMeetingName)){
                System.out.println("2222222222222222222222222222222222222222222222222222222222222222222222");
                System.out.println("COLLEGIAL :" + customerMeetingName + " ACTUAL: " + name.getText());
                jsClick(name);
                preloaderGone();
                break;
            }
        }
        System.out.println("COLLEGIAL COLLEGIAL COLLEGIAL COLLEGIAL COLLEGIAL COLLEGIAL COLLEGIAL COLLEGIAL");
    }

    public Boolean checkEndMeeting(String meetingName, String customer,String number){
        String name = null;
        Boolean a = false;
         if(customer.equals("rico")){
            meetingName = meetingName.substring(0,meetingName.length()-17) + ", очная";
        }else if(customer.equals("avtodor")){
            meetingName = number+". " + meetingName.substring(0,meetingName.length()-17);
        }else{
            meetingName = meetingName.substring(0,meetingName.length()-17);
        }
        for (WebElement e: getListOfWebElements(pastMeetingCard)){
            name = e.findElement(pastMeetingName).getText();

            System.out.println("COLLEGIAL COLLEGIAL ");
            System.out.println(meetingName);
            System.out.println("COLLEGIAL COLLEGIAL -------RES -"+ name.equals(meetingName) + " ------------FROM COLLEGIAL "+name + " MY ------" + meetingName);
            if(name.contains(meetingName)){
                a = true;
                break;
            }
        }
        return a;
    }

    public void goToPastMeetings(){
        jsClick(goToPastMeetings);
        preloaderGone();
    }

    public void goToMainPage() {
        jsClick(By.cssSelector("div.logo-element"));
        preloaderGone();
    }


    public void getInPastMeeting(String meetingName, String customer, String number) {
        String name = null;
         if(customer.equals("rico")){
            meetingName = meetingName.substring(0,meetingName.length()-17) + ", очная";
        }else if(customer.equals("avtodor")){
            meetingName = number+". " + meetingName.substring(0,meetingName.length()-17);
        }else{
            meetingName = meetingName.substring(0,meetingName.length()-17);
        }
        for (WebElement e: getListOfWebElements(pastMeetingCard)){
            name = e.findElement(pastMeetingName).getText();

            if(name.contains(meetingName)){
                jsClick(e.findElement(pastMeetingName));
                break;
            }
        }
    }

    public boolean isSkypeDisplayed(){
        elementAvailability(skypeBtn);
        return isDisplayed(skypeBtn);
    }

    public void clickSkypeLink(){
        jsClick(skypeBtn);
    }
}
