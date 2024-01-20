package PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Thread.sleep;

public class CreateNewMeetingPage extends TestHelper {
    WebDriver driver;
    WebDriverWait wait;
    public CreateNewMeetingPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        this.driver = driver;
        this.wait = wait;
    }
    private By overlay = By.cssSelector("div.overlay");
    private By groupsFactory = By.cssSelector("div.form-group.row:nth-child(2) button");
    private By groupsFactoryOption = By.cssSelector("div.form-group.row:nth-child(2) a");
    private By collegialDepartment = By.cssSelector("div.form-group.row:nth-child(3) button");
    private By collegialDepartmentOption = By.cssSelector("div.form-group.row:nth-child(3) a");
    private By locationField = By.xpath("//div[@class=\"corners\"]//*[contains(text(),\"Место проведения\")]/../div/input");
    private By alrosameetingNameInput = By.cssSelector("div.form-group.row:nth-child(4) input");

    private By locationField2 = By.cssSelector("div.form-group.row:nth-child(5) input");//dkudzo and skype for chtpz
    private By skypeField = By.cssSelector("div.form-group.row:nth-child(6) input");
    private By locationFieldAvtodor = By.cssSelector("div.modal-body > div:nth-child(1) > div:nth-child(5) > div > input");

    private By endManuallyCheckbox = By.cssSelector("label[for='endManually-checkbox']");
    private By eMCheckboxState = By.cssSelector("input#endManually-checkbox");
    private By meetingNumber = By.cssSelector("input.form-control.ng-pristine.ng-untouched.ng-valid.ng-not-empty");//avtodor
    private By meetingFormatCheckbox = By.cssSelector("label[for='meetingFormat-create']");

    private By fromTime = By.cssSelector("div.popup-calendar__time_from.ng-scope > timepicker-pop > input");
//    @FindBy(css = "div.popup-calendar__time_to.ng-scope > timepicker-pop > input")
//    private WebElement ToTime;
    private By toTime = By.cssSelector("div.popup-calendar__time_to.ng-scope > timepicker-pop > input");
    private By endDate = By.cssSelector("div.popup-calendar__time_to input");
    private By submitBtn = By.cssSelector("input.btn.btn-success");
    private By calendarsAmount = By.cssSelector("p.input-group.popup-calendar input");


    private By meetingCreateModalContent = By.cssSelector("div.modal-content");


    public void submitMeetingCreation(){
        jsClick(submitBtn);
        makeScreenshot();
    }
    public Boolean isEndManuallySelected(){
        System.out.println("ENDMANUALLY");
        return isSelected(endManuallyCheckbox);
    }

    public void waitMeetingCreatModal(){
        elementAvailability(meetingCreateModalContent);
        System.out.println("Good");
    }

    public void fillMeeting(String divisionName, String collegialName, String meetingName, String place,
    String customer){
//        customSelect(groupsFactory, groupsFactoryOption, divisionName);
//        customSelect(collegialDepartment, collegialDepartmentOption, collegialName);
        if(!customer.equals("dkudzo")){
            selectThrowSearch(groupsFactory, groupsFactoryOption, divisionName);
        }

        selectThrowSearch(collegialDepartment, collegialDepartmentOption, collegialName);
        gone(overlay);
        type(locationField, place);
        if(customer.equals("alrosa")){

            //для алросы нужно вводить название заседания в ручную
            type(alrosameetingNameInput, meetingName);
        }

        makeScreenshot();

    }

    public void sendEndDate(String EndDateOfExtramural){
        type(endDate, EndDateOfExtramural);
    }

    public void startDate(String FromTime1){
        elementAvailability(fromTime);
        clearField(fromTime);
        type(fromTime, FromTime1);
    }

    public void clearFromTime(){
        if(getElement(fromTime).isEnabled()){
            getElement(fromTime).clear();
        }
    }

    public void finishDate(String ToTime1){
        elementAvailability(toTime);
        clearField(toTime);
        type(toTime, ToTime1);
    }

    public void clearToTime(){
        getElement(toTime).clear();
    }


    public void disableEndManually() {
        jsClick(endManuallyCheckbox);
    }

    public String getMeetingNumber(){
        elementAvailability(meetingNumber);
        String number = getTextJS(meetingNumber);
        return number;
    }

    public Boolean isCheckboxChecked(){
        WebElement cb = driver.findElement(eMCheckboxState);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Boolean s = (Boolean) js.executeScript(" return arguments[0].checked", cb);
        System.out.println(s);
        return s;
    }

    public void makeMeetingAbsentee(){
        jsClick(meetingFormatCheckbox);
    }

    public int isOnlyEndDate(){
        return getListOfWebElements(calendarsAmount).size();
    }
    public void setStartDate(String date){
        setDateJS(getListOfWebElements(calendarsAmount).get(0), date);

    }

    public void setEndDate(String date){
        System.out.println(date);
        setDateJS(getListOfWebElements(calendarsAmount).get(1), date);
    }
    public void setMeetingDate(String date){
        WebElement webElement = driver.findElement(By.cssSelector("input.form-control.ng-pristine.ng-isolate-scope.ng-valid-date.ng-not-empty.ng-valid"));
        ((JavascriptExecutor)driver).executeScript ("document.querySelector('input.form-control.ng-pristine.ng-isolate-scope.ng-valid-date.ng-not-empty.ng-valid').removeAttribute('readonly',0);");
        webElement.clear();
        webElement.sendKeys(date);
    }

    public void addSkypeLink(String link,String customer){
        if(customer.equals("alrosa") || customer.equals("expob")){
            type(skypeField, link);
        }else{
            type(locationField2, link);
        }

    }
}
