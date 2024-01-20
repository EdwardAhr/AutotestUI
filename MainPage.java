package PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage  extends TestHelper{
    WebDriver driver;
    WebDriverWait wait;

    public MainPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    By leftMenuButton = By.cssSelector("button.btn.dropdown-toggle.create-button.ng-binding");
    By entityMenu = By.cssSelector("ul.dropdown-menu");
    By addQuestionButton = By.cssSelector("a[ng-click='openQuestionModal()']");
    By addMeetingButton = By.cssSelector("a[ng-click='openMeetingModal()']");
    By questionsButton = By.xpath("//*[@id='wrapper']/ng-include/div/div[2]/div/ul/li[3]/a");
    By meetings = By.xpath("//*[@id='wrapper']/ng-include/div/div[2]/div/ul/li[2]/a");
    By addButton2 = By.cssSelector("button[type='button']");
    By questionList = By.cssSelector("div.edit-questions");
    By preloader = By.cssSelector("div.preloader");
    By overlay = By.cssSelector("div.overlay");

    By directories = By.cssSelector("a[href='#/dict']");
    By logOut = By.cssSelector("a.logout-link.ng-binding");
    By user = By.cssSelector("div.user-info__name.ng-binding");

    By calendarBlock = By.cssSelector("table.calendar-table");
    By monthHeader = By.cssSelector("tr.header");
    By dayDomain = By.cssSelector("tr td");
    By burgerBtn =By.cssSelector("#open_menu-btn");
    By CloseMenuBtn = By.cssSelector("#close_menu-btn");
    public WebElement pageLoadWait(){
        return elementAvailability(addButton2);
    }

    public void leftMenuButtonClick(){
        driver.findElement(leftMenuButton).click();
    }

    public void addButtonClick(){
        jsClick(addButton2);
    }

    public void addButtonAvailable(){
        elementAvailability(addButton2);
    }
    public void burgerBtnClick(){
        jsClick(burgerBtn);
    }
    public void addQuestion(){
        addButtonClick();
        elementAvailability(entityMenu);
        jsClick(addQuestionButton);
        gone(overlay);
    }
    public void CloseMenu(){
        jsClick(CloseMenuBtn);
}
    public void addMeeting(){
        addButtonClick();
        try {
            elementAvailability(entityMenu);
        }catch (TimeoutException e){
            refresh();
            preloaderGone();
            addButtonClick();
            elementAvailability(entityMenu);
        }
        jsClick(addMeetingButton);

    }
    public Boolean overlayGone(){
        return gone(overlay);
    }

    //nav
    public void goToQuestions(){
        elementAvailability(questionsButton);
        jsClick(questionsButton);
        overlayGone();

    }

    public void goToMeetings(){
        elementAvailability(meetings);
        jsClick(meetings);
        preloaderGone();
    }
    public void goToDirectories(){
        jsClick(directories);
        overlayGone();
    }

    public void logOut(){
        jsClick(logOut);
    }

    public String checkUser(){
        elementAvailability(user);
        return getText(user);
    }

    public void pickDayInCalendar(String date){
        String [] months = {"Январь", "Февраль", "Март", "Апрель", "Май","Июнь","Июль","Август","Сентябрь","Октябрь","Ноябрь","Декабрь"};
        String day = Integer.valueOf(date.substring(0,2)) + "";
        System.out.println("-------------------------------------------------");
        System.out.println(day);
        int monthCount = Integer.valueOf(date.substring(3,5));
        System.out.println("-------------------------------------------------");
        System.out.println(monthCount);
        String month = months[monthCount-1];
        System.out.println("___________________________");
        System.out.println(month);
        for(WebElement element: getListOfWebElements(calendarBlock)){
            if(element.findElement(monthHeader).getText().contains(month)){
                for(WebElement e : getListOfWebElementsFromElement(element, dayDomain)){
                    if(e.getText().equals(day)){
                        jsClick(e);
                        break;
                    }
                }

            }
        }


    }

    public void today() {
        jsClick(By.cssSelector("span.calendar-controls__btn.ng-binding:nth-child(2)"));
    }
}
