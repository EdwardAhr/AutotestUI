package PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.lang.Thread.sleep;

public class LibraryPage extends TestHelper{
    public LibraryPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }
    private By goToLibrary = By.cssSelector("li a[href=\"#/library\"]");
    private By firstDropDownMenu = By.cssSelector("button.dropbtn.dropdown-toggle.ng-isolate-scope.toolbar-popup-anchor");
    private By addFolderDDMenuOption = By.cssSelector("ul.ng-scope li:nth-child(2) a");
    private By accessOptionDD = By.cssSelector("body > div:nth-child(5) > ul > li:nth-child(2) > a");
    private By firstOptionInDDmenuOption = By.cssSelector("ul.ng-scope li:nth-child(1) a");
    private By inputForDocumentForSecondFolder = By.cssSelector("body label:nth-child(4) input[type=file]");
    private By modal = By.cssSelector("div.modal");
    private By modal2 = By.cssSelector("div.modal-content");
    private By textArea = By.cssSelector("div textarea");
    private By createBtn= By.cssSelector("input[value=\"Создать\"]");
    private By mainSaveBtn = By.cssSelector("button.btn.btn-success.btn-save.ng-binding");
    private By saveBtnInBook = By.cssSelector("form > div > div.modal-footer > input.btn.btn-success");
    private By saveBtnInAccessModal = By.cssSelector("div.modal-content input.btn.btn-success");

    private By row = By.cssSelector("div.directory-row-container");
    private By materialRow = By.cssSelector("div.material-wrapper.ng-scope");
    private By packageTitle = By.cssSelector("span.folder_title.ng-binding");
    private By documentTitle = By.cssSelector("span.file-name");
    private By searchInput = By.cssSelector("div.modal-content searchable-select input");
    private By ddInAccessModal = By.cssSelector("a#simple-dropdown");
    private By disableAccessBtn = By.cssSelector("div.file-access-item__wrapper ul.dropdown-menu li:nth-child(1) a");

    private By divisionInModal = By.cssSelector("div.struct-item__wrapper");
    private By koModal = By.cssSelector("div.struct__item");
    private By ddMenu = By.cssSelector("div.toolbar-popup-container");
    private By userIsAddedBlock = By.cssSelector("div.added-item__wrapper");

    private By accessDropDownModal = By.cssSelector("div.file-access-item__dropdown.dropdown a#simple-dropdown");
    private By disableAccessOptionInModal = By.cssSelector("div.modal-content ul.dropdown-menu li:nth-child(1) a");

    public void goToLibrary(){
        jsClick(goToLibrary);
    }

    public void save(){
        jsClick(mainSaveBtn);
        overlayGone();
    }

    public void addFolderInMainFolder(String title){
        jsClick(firstDropDownMenu);
        jsClick(addFolderDDMenuOption);
        elementAvailability(modal);
        type(textArea, title);
        jsClick(createBtn);
        jsClick(mainSaveBtn);
        overlayGone();
    }

    public boolean isAddedFolder(String title) {
        Boolean res = false;
        for (WebElement element : getListOfWebElements(row)){
            if(element.findElement(packageTitle).getText().equals(title)){
                res = true;
                break;
            }
        }
        return res;
    }

    public void addDocumentIntoFolder(String[] paths ) {
        uploadFromArray(inputForDocumentForSecondFolder, paths);
    }

    public boolean isDocumentAdded(String documentName){
        Boolean res = false;
        for(WebElement element : getListOfWebElements(documentTitle)){
            if(element.getText().equals(documentName)){
                res = true;
                break;
            }
        }
        return res;
    }
    public void setAccessForDocument(String doc, String division, String ko, String userName){
        for (WebElement element : getListOfWebElements(materialRow)){
            System.out.println(element.getText());
            if(element.findElement(By.cssSelector("span.file-name.ng-binding")).getText().equals(doc)){
                jsClick(element.findElement(By.cssSelector("button.dropbtn.dropdown-toggle")));
                for(WebElement element1 : getListOfWebElementsWithoutWait(By.cssSelector("div.toolbar-popup-container"))){
                    if(element1.isDisplayed()){
                        element1.findElement(By.cssSelector("ul li:nth-child(2)")).click();
                        break;
                    }
                }
                break;
            }
        }
        jsClick(By.cssSelector("div.search-book"));
        for(WebElement element : getListOfWebElements(divisionInModal)){
            if(element.getText().contains(division)){
                jsClick(element);
                break;
            }
        }
        scrollToElement(saveBtnInBook);
        for(WebElement element : getListOfWebElements(koModal)){
            if(element.getText().contains(ko)){
                jsClick(element);
                break;
            }
        }
        for(WebElement element : getListOfWebElements(koModal)){
            if (element.getText().contains(userName)){

                try {
                    sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                hove(element.findElement(By.cssSelector("span.struct__controls")));
                try {
                    sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                jsClickWithoutWait(element.findElement(By.cssSelector("span.struct__controls")));
                break;
            }
        }

        elementAvailability(userIsAddedBlock);

        jsClick(saveBtnInBook);

        jsClick(ddInAccessModal);

        jsClick(disableAccessBtn);

        jsClick(saveBtnInAccessModal);

        jsClick(mainSaveBtn);
    }
}
