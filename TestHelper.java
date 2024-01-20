package PageObject;

import PageObject.TokenModel.TokenModel;
import com.google.gson.Gson;
import io.qameta.allure.Attachment;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static java.lang.Thread.sleep;
import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfWindowsToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.or;

public class TestHelper {
    WebDriverWait wait;
    WebDriver driver;

    public TestHelper(WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
    }
    private By errorAlert = By.cssSelector("article.alertify-log-error");
    private By alertSave = By.cssSelector("article.alertify-log.alertify-log-success.alertify-log-show");

    public WebElement elementAvailability(By locator){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    public WebElement elementAvailability(WebElement el){
        return wait.until(ExpectedConditions.visibilityOf(el));
    }

    public WebElement elementClickability(By locator){
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement elementClickability(WebElement element){
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void jsClick(By locator){
        elementAvailability(locator);
        JavascriptExecutor js = (JavascriptExecutor)driver;
        WebElement ele = driver.findElement(locator);
        js.executeScript("arguments[0].click()", ele);
    }


    public Boolean isSelected(By locator){
        elementAvailability(locator);
        return driver.findElement(locator).isSelected();
    }
    public Boolean isSelectedWithoutWait(By locator){

        return driver.findElement(locator).isSelected();
    }
    public Boolean isSelectedWithoutWait(WebElement element){

        return element.isSelected();
    }

    public void jsClick(WebElement element){
        elementAvailability(element);
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].click();", element);
    }

    public void click(By locator){
        elementAvailability(locator);
        driver.findElement(locator).click();
    }
    public void click(WebElement element){
        elementAvailability(element);
        element.click();
    }

    public void type(By locator, String text){
        elementAvailability(locator);
        driver.findElement(locator).sendKeys(text);
    }

    public void forUpload(By locator, String path){
        driver.findElement(locator).sendKeys(path);
    }
    public void typeWebelement(WebElement el, String text){
        elementAvailability(el);
        el.sendKeys(text);
    }

    public void selectByLocator(By locator, String option){
        elementAvailability(locator);
        Select directorSelect = new Select(driver.findElement(locator));
        System.out.println(option);
        directorSelect.selectByVisibleText(option);
    }
    public void selectByIndex(By locator, int index){
        elementAvailability(locator);
        Select directorSelect = new Select(driver.findElement(locator));
        directorSelect.selectByIndex(index);
    }

    public void selectByWebElement(WebElement element, String option){
        Select directorSelect = new Select(element);
        directorSelect.selectByVisibleText(option);
    }
    public void customSelect(By locator, By option, String name){
        elementAvailability(locator);
        jsClick(locator);
        elementAvailability(option);
        for(WebElement el : getListOfWebElements(option)){
            if(el.getText().equals(name)){
                jsClick(el);
                break;
            }
        }
    }

    public void selectThrowSearch(By locator, By option, String value){
        elementAvailability(locator);
        jsClick(locator);

        elementAvailability(By.cssSelector("div.btn-group.multiselect-dropdown__wrapper.single.open input"));
        click(By.cssSelector("div.btn-group.multiselect-dropdown__wrapper.single.open input"));
        type(By.cssSelector("div.btn-group.multiselect-dropdown__wrapper.single.open input"), value);
        elementAvailability(option);
        jsClick(option);
    }

    public void iFrameSendKeys(By locator, String text){
        String window = driver.getWindowHandle();
        WebElement frame = driver.findElement(locator);
        driver.switchTo().frame(frame);
        List<WebElement> bodies = driver.findElements(By.tagName("body"));
        type(By.tagName("body"), text);
        driver.switchTo().window(window);
    }
    public String getTextFromIFrame(By locator){
        String window = driver.getWindowHandle();
        WebElement frame = driver.findElement(locator);
        driver.switchTo().frame(frame);
        List<WebElement> bodies = driver.findElements(By.tagName("body"));

        String text = getText(By.tagName("body"));
        driver.switchTo().window(window);
        return text;
    }

    public List<WebElement> getListOfWebElements(By locator){
        elementAvailability(locator);
//        isDisplayed(locator);

        List<WebElement> elements = driver.findElements(locator);
        return elements;
    }
    public List<WebElement> getListOfWebElementsWithoutWait(By locator){
//        elementAvailability(locator);
//        isDisplayed(locator);

        List<WebElement> elements = driver.findElements(locator);
        return elements;
    }


    public List<WebElement> getListOfWebElementsFromElement(By elLocator, By locator){
        elementAvailability(elLocator);
        WebElement el = driver.findElement(elLocator);
        List<WebElement> elements = el.findElements(locator);
        return elements;
    }
    public List<WebElement> getListOfWebElementsFromElement(WebElement el, By locator){

        List<WebElement> elements = el.findElements(locator);
        return elements;
    }

    public WebElement getElement(By locator){
        return driver.findElement(locator);
    }

    public void clickOnWebElement(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        jsClick(element);
    }

    public void pickFromSearchMenu(By locator){
        driver.findElement(locator).sendKeys(Keys.ARROW_DOWN);
        driver.findElement(locator).sendKeys(Keys.ENTER);
    }

    public void rightMeeting(By locator, String meetingName, String customer, String number){

        String rightName = "";
        if(customer.equals("rshb")){
            rightName = meetingName + " (с учетом письменного мнения)";
        }else if(customer.equals("avtodor")){
            rightName = number + " " + meetingName;
        }else if(customer.equals("rico")){
            rightName = meetingName.substring(0,meetingName.length()-17)+", очная встреча";
        }else{
            rightName = meetingName;
        }
        List<WebElement> meetings = driver.findElements(locator);
        for(WebElement e : meetings){
            String currentName  = e.findElement(By.cssSelector("p.boldNames")).getText();

            System.out.println(currentName.equals(rightName)+ " " + rightName + " "+rightName.length()+" " + " Meeting Name FROM SYSTEM: " + currentName + " " + currentName.length());
            if(currentName.contains(rightName)){
//                Actions action = new Actions(driver);
//                action.moveToElement(e.findElement(By.cssSelector("a"))).perform();
//                action.click(e.findElement(By.cssSelector("a"))).perform();
                System.out.println("GET IN MEETING: " + currentName);
                jsClick(e.findElement(By.cssSelector("a")));
                break;
            }
        }
    }

    public Boolean isEnabled(By locator){
        return driver.findElement(locator).isEnabled();
    }

    public Boolean gone(By locator){
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
    public Boolean gone(WebElement element){
        return wait.until(ExpectedConditions.invisibilityOf(element));
    }
    public void refreshWithWait(By locator){
        driver.navigate().refresh();
        elementAvailability(locator);
    }
    public void refresh(){
        driver.navigate().refresh();
    }

    public Boolean pageIsLoaded(){
        Boolean a = false;
        JavascriptExecutor page = (JavascriptExecutor)driver;
        System.out.println(page.executeScript("return document.readyState").equals("complete"));
        while(a == false){
            a = page.executeScript("return document.readyState").equals("complete");
        }
        return a;
    }

    public void waitUnitPageLoad(){
        Boolean isLoaded = false;
        while(isLoaded == false){
            isLoaded = pageIsLoaded();
        }
        System.out.println("end");
    }
    public void scroll(int px){
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("window.scrollBy(0,"+ px +")");


    }

    public void scrollDownPage(int px){
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        WebElement t = driver.findElement(By.cssSelector("body.scrollbar"));
        t.click();
        jse.executeScript("window.scrollBy(0,"+ px +")", t);
    }

    public void scrollToElement(WebElement element){
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.perform();
    }

    public void scrollToElement(By selector){
        Actions actions = new Actions(driver);
        WebElement element = driver.findElement(selector);
        actions.moveToElement(element);
        actions.perform();
    }

    public String getTextJS(By locator){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement t = driver.findElement(locator);
        String text = (String) js.executeScript("return arguments[0].value", t);
        return text;
    }
    public void waitUntilDisabled(By locator) throws InterruptedException {
        if(driver.findElement(locator).isEnabled()){
            sleep(5000);
        }
    }

    public String meetingName(String timeOfStart, String data, String company,
            String collegialDepartment){
        String meetingName = collegialDepartment + " " + company + ", " + data + " в " +timeOfStart+", очное заседание";
        return meetingName;
    }

    public void setDateJS(WebElement el, String date){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].removeAttribute('readonly')", el);
        js.executeScript("arguments[0].value=''", el);
        typeWebelement(el, date);
//        js.executeScript("arguments[0].value='"+date+"'", el);
    }
    public void clearField(By locator){
        elementAvailability(locator);
        click(locator);
        driver.findElement(locator).sendKeys(Keys.CONTROL + "a");
        driver.findElement(locator).sendKeys(Keys.DELETE);
    }

    public String getText(By locator){
        elementAvailability(locator);
        return driver.findElement(locator).getText();
    }
    public String getText(WebElement element){
        elementAvailability(element);
        return element.getText();
    }

    public String getValue(By selector, String attribute){
        elementAvailability(selector);
        return driver.findElement(selector).getAttribute(attribute);
    }

    public void alert(By locator1, By locator2){
        elementAvailability(locator1);
        click(locator2);
    }

    public Boolean alertIsDisplayed(By locator) throws InterruptedException {
        Boolean a = false;
        sleep(1000);
        try{
            a = driver.findElement(locator).isDisplayed();
        }catch(NoSuchElementException e){

        }
        return a;
    }

    public void returnToPreviousPage(){
        String originalWindow = driver.getWindowHandle();
        assert driver.getWindowHandles().size() == 2;

        for(String windowHandle : driver.getWindowHandles()){
            if(!originalWindow.contentEquals(windowHandle)){
                driver.switchTo().window(windowHandle);
                break;
            }
        }
    }
    public void goToAnotherTab(){
        String originalWindow = driver.getWindowHandle();
        for(String w: driver.getWindowHandles()){
            if(!originalWindow.equals(w)){
                driver.switchTo().window(w);
                break;
            }
        }
    }

    public String getWindowHandle(){
        String original = driver.getWindowHandle();
        return original;
    }
    public void switchToWindow(String window){
        driver.switchTo().window(window);

    }


    public void clickOnLink(By locator){
        String originalWindow = driver.getWindowHandle();
        assert driver.getWindowHandles().size() == 1;
        jsClick(locator);
        wait.until(numberOfWindowsToBe(2));
        for(String windowHandle : driver.getWindowHandles()){
            if(!originalWindow.contentEquals(windowHandle)){
                driver.switchTo().window(windowHandle);
                break;
            }
        }
    }

    public void closeTab(){
        driver.close();
    }

    public String getUrl(){
        return driver.getCurrentUrl();
    }

    public void openNewBrowserTab() {
//        Robot robot = new Robot();
//        robot.delay(200);
//        robot.keyPress(KeyEvent.VK_CONTROL);
//        robot.keyPress(KeyEvent.VK_T);
//        robot.keyRelease(KeyEvent.VK_CONTROL);
//        robot.keyRelease(KeyEvent.VK_T);
        ((JavascriptExecutor) driver).executeScript("window.open()");
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
    }

    public void mouseOver(WebElement element){
        elementAvailability(element);
        Actions action = new Actions(driver);
        action.moveToElement(element).build().perform();
    }
    public void mouseOver(By locator){
        elementAvailability(locator);
        Actions action = new Actions(driver);
        WebElement element = driver.findElement(locator);
        action.moveToElement(element).build().perform();
    }

    public String randomNumber(int length, int range){
        Random random = new Random();
        String result = "";
        for(int i = 0; i < length; i++){
            result += String.valueOf(random.nextInt(range));
        }
       return result;
    }
    public Boolean overlayGone(){
        return gone(By.cssSelector("div.overlay"));
    }
    public Boolean preloaderGone(){
        return gone(By.cssSelector("div.preloader"));
    }

    public void openCollegial(String url){
        driver.get(url);
    }
    public Boolean isDisplayed(By locator){
        return driver.findElement(locator).isDisplayed();
    }
    public void uploadFromArray(By locator,String[] filepath){
        for(int i = 0; i < filepath.length; i++){
            forUpload(locator, filepath[i]);
        }
    }
    public void alertOk(){
        elementAvailability(By.cssSelector("div.alertify-dialog"));
        jsClick(By.cssSelector("button.alertify-button.alertify-button-ok"));
        gone(By.cssSelector("div.alertify-dialog"));
    }

    public boolean enabled(By locator){
        return driver.findElement(locator).isEnabled();
    }

    public void waitModalClose(){
        gone(By.cssSelector("div.modal-content"));
    }
    public void closeModal(){
        jsClick(By.cssSelector("button.close"));
    }

    public void ifModalDisplayedThenFail(){
        try {
            waitModalClose();
        }catch (TimeoutException te){
            closeModal();
            org.testng.Assert.fail("Модальное окно было закрыто принудительно");
        }
    }

    public String getQuestionIdFromUrl(){
        String url = driver.getCurrentUrl();
        String[] b = url.split("/");
        return b[5];
    }
    public String getColor(By locator){
        elementAvailability(locator);
        return driver.findElement(locator).getCssValue("background-color");
    }

    public Response getTokenApi(String rest, String encodedLoginPass){
        Response response = given()
                .log().all()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body(encodedLoginPass)
                .post(rest + "api/access/token");

        return response;
    }

    public Response getTokenApiHttps(String rest, String encodedLoginPass){
        Response response = given()
                .log().all()

                .relaxedHTTPSValidation()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body(encodedLoginPass)
                .post(rest + "api/access/token");

        return response;
    }
    public Response getTokenForMainUser(String rest, String customer){
        Response response = null;
        if(customer.equals("dkudzo")){
            response = getTokenApiHttps(rest,"Login=AutoUser&Password=P%40ssw0rd");
        }else if(customer.equals("expob")){
            response = getTokenApiHttps(rest, "Login=AutoUser&Password=P%40ssw0rd&ProgramId=1&Version=1&Description=1&IsPermanentCookie=true");
        } else{
            response = getTokenApi(rest, "Login=AutoUser&Password=P%40ssw0rd");
        }
        return response;
    }

    public Response getTokenForPredsedatel(String rest, String customer){
        Response response = null;
        if(customer.equals("expob")){
            response = getTokenApiHttps(rest, "Login=digdes%5Cpimenov_test2&Password=P%40ssw0rd&ProgramId=1&Version=&Description=&IsPermanentCookie=false");
        }else{
            response = getTokenApi(rest, "Login=digdes%5Cpimenov_test2&Password=P%40ssw0rd");
        }
        return response;
    }


    public void changeProjectDecisionApi(String rest, Response response, String body, String questionId, String customer){
        TokenModel tokenModel = new Gson().fromJson(response.getBody().asString(), TokenModel.class);
        System.out.println("-----------------TOKEN--------------------");
        System.out.println(tokenModel.getAccessToken());
        if(customer.equals("dkudzo") || customer.equals("expob")){
            given()
                    .log().all()
                    .relaxedHTTPSValidation()
                    .header("Authorization", "Bearer " + tokenModel.getAccessToken())
                    .accept(ContentType.TEXT)
                    .contentType(ContentType.JSON)
                    .body(body)
                    .put(rest + "api/questions/"+questionId+"/draft-decision").then().statusCode(204);
        }else {
            given()
                    .log().all()
                    .header("Authorization", "Bearer " + tokenModel.getAccessToken())
                    .accept(ContentType.TEXT)
                    .contentType(ContentType.JSON)
                    .body(body)
                    .put(rest + "api/questions/"+questionId+"/draft-decision").then().statusCode(204);
        }

    }

    public void acceptMeetingWithReason(String rest, Response response, String meetingId, String customer){
        TokenModel tokenModel = new Gson().fromJson(response.getBody().asString(), TokenModel.class);
        System.out.println("-----------------TOKEN--------------------");
        System.out.println(tokenModel.getAccessToken());
        if(customer.equals("expob")){
            given().relaxedHTTPSValidation()
                    .header("Authorization", "Bearer " + tokenModel.getAccessToken())
                    .accept(ContentType.TEXT)
                    .contentType(ContentType.JSON)
                    .body("{ \"Reason\": \"test test test\"}")
                    .when()
                    .post(rest + "api/meetings/" + meetingId + "/accept").then().log().all().statusCode(204);

        }else{
            given().header("Authorization", "Bearer " + tokenModel.getAccessToken())
                    .accept(ContentType.TEXT)
                    .contentType(ContentType.JSON)
                    .body("{ \"Reason\": \"test test test\"}")
                    .when()
                    .post(rest + "api/meetings/" + meetingId + "/accept").then().log().all().statusCode(204);
        }


    }

    public void deleteElement(By locator){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement t = driver.findElement(locator);
        js.executeScript("arguments[0].style.display = \"none\"", t);
    }
    public void jsClickWithoutWait(By locator){
        JavascriptExecutor js = (JavascriptExecutor)driver;
        WebElement ele = driver.findElement(locator);
        js.executeScript("arguments[0].click()", ele);
    }

    public void jsClickWithoutWait(WebElement element){
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].click()", element);
    }

    public void hoveAndClick(By selector){
        Actions actions = new Actions(driver);
        WebElement ele = driver.findElement(selector);
        actions.click().build().perform();
    }
    public void hove(By selector){
        Actions actions = new Actions(driver);
        WebElement ele = driver.findElement(selector);
        actions.moveToElement(ele).build().perform();
    }
    public void hove(WebElement element){
        Actions actions = new Actions(driver);
        actions.moveToElement(element).build().perform();
    }
    public void hoveAndClick(WebElement element){
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().build().perform();
    }
    public void pageDown(By selector){
        driver.findElement(selector).sendKeys(Keys.PAGE_DOWN);
    }

    public void articleSaveGone(){
        gone(alertSave);
    }

    @Attachment(value = "Attachment Screenshot", type = "image/png")
    public byte[] makeScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

}
