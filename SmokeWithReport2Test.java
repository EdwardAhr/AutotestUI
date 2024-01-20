package scenarios;

import Collegial.CollegialMeetingCardPage;
import Collegial.CollegialMeetingsPage;
import Collegial.CollegialSignUp;
import PageObject.*;
import PageObject.Directories.CollegialBodiesPage;
import PageObject.Directories.CompaniesPage;
import PageObject.Directories.EmployeesPage;
import PageObject.Directories.QuestionsTypesPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.restassured.response.Response;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.*;
import org.testng.annotations.Test;

import java.awt.*;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;



import static java.lang.Thread.sleep;
import static org.testng.Assert.assertEquals;

public class SmokeWithReport2Test extends ConfigHelper {

    String fromTimeMain = null; //Время ОТ, передаяется во время создания заседания, используются для нахождения созданного заседания
    String currentDate = null;
    WebDriver driver;
    WebDriverWait wait;
    String companyName;
    String meetingNumber;
    String meetingName;
    String meetingId;
    Boolean right = true;
    String id;
    String collegialDepartment;

    String post = "ул Автоматизаторов д 1349";
    String legalPost = "г Москва, ул Автоматизаторов д 1349, 565656";
    String modifiedCompany = " на Ареопаде";
    String kO = "Automation KO";
    String modifiedKO = "Измененое АКО";
    String cbInTable;
    String mainUser = "AutoUser AutoUser AutoUser";
    TestHelper testHelper;
    String questionId;
    String customer;
    String userName;
    String userPass;
    String userLogin;
    String userSecondName;
    String userSecondPass;
    String userSecondLogin;
    String protocol;
    String pm;
    String company;
    String mainUserPassword;
    String mainUserLogin;
    String rest;
    String quorum;
    String extract;
    String agenda;
    String copiedQuestionName;
    String projectDecisionCopiedQuestion;
    String toTime;
    String meetingPastDate;
    String pastMeetingName;
    String subQuestionOne;
    String subQuestionTwo;
    String subQuestionThree;
    String subQuestionFour;
    String subQuestionProjectDecision;
    String subQuestionId;
    String googleMail;
    String googlePass;
    String organizationalQuestionTypeName;
    String packageTitle;

    int questionsSizeOnQuestionsPage;
    int questionsSizeInAgenda;
    int materialsAmountInQuestionFromMainPage = 5;
    int materialsAmountInNewQuestion = 4;
    int i = 0;


    @BeforeSuite
    public static void setDriver() throws IOException {
        WebDriverManager.chromedriver().setup();

//        WebDriverManager.firefoxdriver().setup();
    }

    @BeforeClass
    public void setUp()  {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu", "--window-size=1980,1080","--ignore-certificate-errors","--disable-extensions","--no-sandbox","--disable-dev-shm-usage",
                "--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.71 Safari/537.36");
        driver = new ChromeDriver(options);
//        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        driver.manage().window().maximize();
        collegialDepartment = getConfig("collegial");

        String customerParamFromEnv = System.getProperty("customer");
        customer = customerParamFromEnv == null ? getConfig("customer") : customerParamFromEnv;

        userName = getConfig("userName");
        userSecondName ="FORKO";
        userPass = getConfig("userPass");
        driver.get("getCustomerUrl(customer)");
        userSecondPass = getConfig("userPass");
        userLogin = getConfig("userLogin");
        protocol = getConfig(customer + ".protocol");
        extract = getConfig(customer + ".extract");
        agenda = getConfig(customer + ".agenda");
        pm = getConfig(customer + ".pm");
        quorum = getConfig("suek.quorum");
        company = getConfig("division");
        mainUserPassword = getConfig("password");
        mainUserLogin = getConfig("login");
        rest = getConfig(customer + ".rest");
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formatterData= new SimpleDateFormat("dd.MM.yyyy' 'HH:mm");
        id = formatterData.format(date);
        subQuestionOne = "SUBQUESTION ONE " + id;
        subQuestionTwo = "SUBQUESTION TWO " + id;
        subQuestionThree = "SUBQUESTION THREE " + id;
        subQuestionFour = "SUBQUESTION FOUR " + id;
        subQuestionProjectDecision = "SUBQUESTION TEST PROJECT DECISION";
        googleMail = getConfig("googleMail");
        googlePass = getConfig("googlePass");

        SignUpPage signUpPage = new SignUpPage(driver, wait);
        if(customer.equals("beeline")){
            screenShot();
            signUpPage.logIn("Autotester","P@ssw0rd");
        }
        if(customer.equals("alrosa")){
            screenShot();
            signUpPage.googleLogIn(googleMail, googlePass);
        }else{
            signUpPage.logIn(mainUserLogin, mainUserPassword);
        }
    }


    @AfterMethod
    public void onTestFailure(ITestResult tr) {
        if(!tr.isSuccess()){
            screenShot();
        }
    }

    public void screenShot(){
        testHelper = new TestHelper(driver,wait);
        testHelper.makeScreenshot();
    }


    @Test
    @Description("Создание предприятие, проверка его добавления, изменение имени предприятия и проверка что изменения применены")
    public void newCompanyCreationAndCheckTest() throws InterruptedException {
        MainPage mainPage = new MainPage(driver, wait);
        mainPage.preloaderGone();
        mainPage.addButtonAvailable();
        mainPage.goToDirectories();

        companyName = "Авто" + id;
        if(!customer.equals("dkudzo")){

            CompaniesPage companiesPage = new CompaniesPage(driver, wait);
            companiesPage.goToCompanies();
            companiesPage.listOfEntitiesIsLoaded();
            companiesPage.addCompany(companyName, companyName + "на тестовом стенде", post, legalPost, customer);
            companiesPage.scroll(700);
            companiesPage.goToCompanies();
            companiesPage.isAddedCompanyDisplayed(companyName);
            companiesPage.getInCompany(companyName);
            companiesPage.editShortName(modifiedCompany);
            companiesPage.save();

            companyName = companyName + modifiedCompany;
            sleep(1000);
            companiesPage.isAddedCompanyDisplayed(companyName);
            System.out.println("Первый тест прошел");

        }else{
            throw new SkipException("Для данного заказчика функционал не доступен");
        }
//        Assert.assertEquals(directoriesPage.getInCompany("ЗАО \"Диджитал Дизайн\""), "ЗАО \"Диджитал Дизайн\"");
    }

    @Test
    @Description("Создание КО, проверка его добавления, изменение имени КО")
    public void cBCreationAndCheckTest() throws InterruptedException {
        MainPage mainPage = new MainPage(driver, wait);
        mainPage.goToDirectories();
        CollegialBodiesPage collegialBodiesPage = new CollegialBodiesPage(driver, wait);
        collegialBodiesPage.goToCollegialBody();
        collegialBodiesPage.listOfEntitiesIsLoaded();
        kO += id;
       if(customer.equals("dkudzo")){
           collegialBodiesPage.createKO(customer,company, "Простое большинство", kO, "Что то там","Юридическая информация" );
       }else{
           collegialBodiesPage.createKO(customer,companyName, "Простое большинство", kO, "Что то там","Юридическая информация" );
       }

        if(customer.equals("dkudzo")){
            cbInTable = collegialBodiesPage.cbNameInTable(company, kO);
        }else{
            cbInTable = collegialBodiesPage.cbNameInTable(companyName, kO);
        }
        sleep(1000);
        Assert.assertTrue(collegialBodiesPage.isAddedCollegialBodyDisplayed(cbInTable));
    }

    @Test
    @Description("Переход в КО и изменение его названия")
    public void changeCBNameTest(){
        CollegialBodiesPage collegialBodiesPage = new CollegialBodiesPage(driver, wait);
        collegialBodiesPage.getInCollegialBody(cbInTable);
        modifiedKO +=" "+id;
        collegialBodiesPage.editCBName(modifiedKO, customer);
        collegialBodiesPage.editRule("Не установлены");
        collegialBodiesPage.save();
        screenShot();
        if(customer.equals("dkudzo")){
            cbInTable = collegialBodiesPage.cbNameInTable(company, modifiedKO);
        }else{
            cbInTable = collegialBodiesPage.cbNameInTable(companyName, modifiedKO);
        }
        Assert.assertTrue(collegialBodiesPage.isAddedCollegialBodyDisplayed(cbInTable));
        collegialBodiesPage.scroll(700);
        if(customer.equals("test")){
            collegialBodiesPage.getInCollegialBody(cbInTable);
            collegialBodiesPage.goToQuestionTypes();
            collegialBodiesPage.setRuleInQuestionType("Новый тип","Простое большинство");
            screenShot();
            collegialBodiesPage.save();
            collegialBodiesPage.ifModalDisplayedThenFail();
        }

    }

    @Test
    @Description("Добавление шаблонов и проверка что изменения применены")
    public void addTemplatesTest(){
        CollegialBodiesPage collegialBodiesPage = new CollegialBodiesPage(driver,wait);
        collegialBodiesPage.getInCollegialBody(cbInTable);
        collegialBodiesPage.goToTemplates();
        collegialBodiesPage.fillIntramuralProtocolField(protocol);
        collegialBodiesPage.fillOpinion(pm);
        collegialBodiesPage.fillQuestionnaireField(pm);
        if(customer.equals("avtodor") || customer.equals("rico") || customer.equals("mmk") || customer.equals("rshb") || customer.equals("expob")){
            collegialBodiesPage.fillExtractField(extract);
            collegialBodiesPage.fillAbsenteeMeetingExtractField(extract);
        }

        if(customer.equals("mmk") || customer.equals("rshb") || customer.equals("avtodor")){
            collegialBodiesPage.fillAgendaField(agenda);
            collegialBodiesPage.fillAbsenteeAgendaField(agenda);
        }

        if(customer.equals("suek")){
            collegialBodiesPage.fillQuorum(quorum);
        }
        screenShot();
        collegialBodiesPage.save();
        collegialBodiesPage.ifModalDisplayedThenFail();
    }

    @Test
    @Description("Создание тестового пользователя")
    public void createUserTest() throws InterruptedException {
        MainPage mainPage = new MainPage(driver, wait);
        mainPage.goToDirectories();

        EmployeesPage employeesPage = new EmployeesPage(driver, wait);
        employeesPage.goToEmployees();

        employeesPage.listOfEntitiesIsLoaded();
        employeesPage.openAddModal();
        employeesPage.goToExternalForm();

        userLogin += id;
        userName +=id;
        userLogin = userLogin.replaceAll("\\:","");
        userLogin = userLogin.replaceAll("\\.", "");
        if(customer.equals("dkudzo")){
            employeesPage.fillUser(userLogin, userName, userPass, company,
                    modifiedKO,"pimenov_test4@digdes.com", customer);
        }else{
            employeesPage.fillUser(userLogin, userName, userPass, companyName,
                    modifiedKO,"pimenov_test4@digdes.com", customer);
        }
        employeesPage.ifModalDisplayedThenFail();
        employeesPage.isAddedEmployeeDisplayed(userName);
    }


    @Test
    @Description("Создание тестового пользователя для добавления через вкладку КО")
    public void createUserForKOAddingTest() throws InterruptedException {
        if(!customer.equals("nspk") || !customer.equals("expob")){
        MainPage mainPage = new MainPage(driver, wait);
        mainPage.goToDirectories();

        EmployeesPage employeesPage = new EmployeesPage(driver, wait);
        employeesPage.goToEmployees();

        employeesPage.listOfEntitiesIsLoaded();
        employeesPage.openAddModal();
        employeesPage.goToExternalForm();

        userSecondLogin += id;
        userSecondName +=id;
        userSecondLogin = userSecondLogin.replaceAll("\\:","");
        userSecondLogin = userSecondLogin.replaceAll("\\.", "");
        if(customer.equals("dkudzo")){
            employeesPage.fillWithoutKO(userSecondLogin, userSecondName, userSecondPass,
                    "pimenov_test4@digdes.com", customer);
        }else{
            employeesPage.fillWithoutKO(userSecondLogin, userSecondName, userSecondPass,
                    "pimenov_test4@digdes.com", customer);
        }
        employeesPage.ifModalDisplayedThenFail();
        employeesPage.isAddedEmployeeDisplayed(userName);}else{
            throw new SkipException("Для данного заказчика функционал не доступен");
        }
    }

    @Test
    @Description("Добавление роли секретаря главному тестовому пользователю")
    public void addMainUserInTestCB() throws InterruptedException {
        EmployeesPage employeesPage = new EmployeesPage(driver, wait);
        employeesPage.listOfEntitiesIsLoaded();
        employeesPage.openUserForEdit(mainUser);
        if(customer.equals("dkudzo")){
            employeesPage.addSecretaryRoleInCB(company, modifiedKO);
        }else{
            employeesPage.addSecretaryRoleInCB(companyName, modifiedKO);
        }
        Assert.assertTrue(employeesPage.checkDirectorsRole(cbInTable, "Секретарь"));
        employeesPage.saveChanges();
        employeesPage.ifModalDisplayedThenFail();
    }

    @Test
    @Description("Добавление роли председателя тестовому председателю")
    public void addChairmanInTestCB() {
        if(customer.equals("mmk") || customer.equals("rshb") || customer.equals("test") || customer.equals("rico") || customer.equals("expob")){
            EmployeesPage employeesPage = new EmployeesPage(driver, wait);
            employeesPage.refresh();
            employeesPage.listOfEntitiesIsLoaded();
            employeesPage.openUserForEdit("Predsedatel Predsedatel Predsedatel");
            if(customer.equals("dkudzo")){
                employeesPage.addChairmanRoleInCB(company, modifiedKO);
                employeesPage.finishChairmanRoleAdding();
            }else if(customer.equals("expob")){
                employeesPage.addChairmanRoleInCB(companyName, modifiedKO);
            }
            else {
                employeesPage.addChairmanRoleInCB(companyName, modifiedKO);
                employeesPage.finishChairmanRoleAdding();
            }

            if(!customer.equals("expob")){
                Assert.assertTrue(employeesPage.checkDirectorsRole(cbInTable, "Председатель"));
                employeesPage.saveChanges();
                employeesPage.ifModalDisplayedThenFail();
            }
        }else{throw new SkipException("Для данного заказчика функционал не доступен");}
    }

    @Test
    @Description
    public void checkVetoRightInEmployeeCardTest(){
        if(customer.equals("expob")){
            EmployeesPage employeesPage = new EmployeesPage(driver,wait);
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Assert.assertTrue(employeesPage.isCheckedVetoRightInEmployeeCard());
        }else {
         throw new SkipException("Для данного заказчика функционал не доступен");
        }
    }

    @Test
    @Description
    public void checkVetoRightWarningTest(){
        if(customer.equals("expob")){
            EmployeesPage employeesPage = new EmployeesPage(driver,wait);
            employeesPage.uncheckVotingRight();
            employeesPage.submitChanges();
            Assert.assertTrue(employeesPage.checkVetoRightWarning("Право вето доступно только для участников с правом голоса"));
        }else {
            throw new SkipException("Для данного заказчика функционал не доступен");
        }
    }

    @Test
    @Description
    public void finishAddingChairmanInCBTest(){
        if(customer.equals("expob")){
            EmployeesPage employeesPage = new EmployeesPage(driver,wait);
            employeesPage.uncheckVotingRight();
            employeesPage.submitChanges();
            employeesPage.saveChanges();
            employeesPage.ifModalDisplayedThenFail();
        }else{
            throw new SkipException("Для данного заказчика функционал не доступен");
        }
    }

    @Test
    @Description("Добавление нового пользователя через КО")
    public void addMemberFromKOTabTest(){
        if(!customer.equals("nspk")){
        CollegialBodiesPage collegialBodiesPage = new CollegialBodiesPage(driver,wait);
        collegialBodiesPage.goToCollegialBody();
        collegialBodiesPage.listOfEntitiesIsLoaded();

        collegialBodiesPage.getInCollegialBody(cbInTable);

        collegialBodiesPage.goToMembers();
        collegialBodiesPage.addMemberInKO(userSecondName);
        screenShot();
        Assert.assertTrue(collegialBodiesPage.isUserAdded(userSecondName));
        collegialBodiesPage.closeModal();}else {
            throw new SkipException("Для данного заказчика функционал не доступен");
        }
    }

    @Test
    @Description("Установить роль заместителя председателя")
    public void setChairmanRoleFromKOTabTest(){
        if(customer.equals("expob")){
            CollegialBodiesPage collegialBodiesPage = new CollegialBodiesPage(driver,wait);

            collegialBodiesPage.getInCollegialBody(cbInTable);
            collegialBodiesPage.goToMembers();
            collegialBodiesPage.setRole(userName, 3);

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Assert.assertTrue(collegialBodiesPage.isCheckedVetoRight());


        }else{
            throw new SkipException("Для данного заказчика функционал не доступен");
        }
    }


    @Test
    @Description("Проверка чек-боксов у председателя и заместителя председателя")
    public void checkVetoRightTest(){
        if(customer.equals("expob")){
            CollegialBodiesPage collegialBodiesPage = new CollegialBodiesPage(driver,wait);
            collegialBodiesPage.searchMemberInKOModal("Predsedatel");
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Assert.assertTrue(collegialBodiesPage.isCheckedVetoRight());

        }else{
            throw new SkipException("Для данного заказчика функционал не доступен");
        }
    }

    @Test
    public void submitChangesForExoBTest(){
        if(customer.equals("expob")){
            CollegialBodiesPage collegialBodiesPage = new CollegialBodiesPage(driver,wait);
            collegialBodiesPage.save();
        }else{
            throw new SkipException("Для данного заказчика функционал не доступен");
        }
    }

    @Test
    @Description("Создание организационного вопроса и отключение вкладки материалы")
    public void createOrganizationTypeTest(){
        if(customer.equals("mmk")){
            QuestionsTypesPage questionsTypesPage = new QuestionsTypesPage(driver,wait);
            questionsTypesPage.goToQuestionTypes();
            organizationalQuestionTypeName = "Organizational Question " + id;
            questionsTypesPage.addOrganizationType(organizationalQuestionTypeName);
            questionsTypesPage.disableMaterials();
            questionsTypesPage.submitCreation();
            Assert.assertTrue(questionsTypesPage.isOrganizationQuestion(organizationalQuestionTypeName));
        }else {
            throw new SkipException("Для данного заказчика функционал не доступен");
        }
    }

    @Test
    @Description("Создание нового митинга и отправка на утверждение")
    public void sendToApproveTest() throws InterruptedException {
        MainPage mainPage = new MainPage(driver, wait);
        mainPage.goToMeetings();
        MeetingsPage meetingsPage = new MeetingsPage(driver, wait);

        int meetingsSizeBefore = meetingsPage.getMeetingsSize();
        System.out.println(meetingsSizeBefore);

        mainPage.addMeeting();
        mainPage.preloaderGone();

        CreateNewMeetingPage createNewMeetingPage = new CreateNewMeetingPage(driver, wait);
        createNewMeetingPage.waitMeetingCreatModal();
        SimpleDateFormat formatter= new SimpleDateFormat("HH:mm");

            Date date = new Date(System.currentTimeMillis()+4*3600000+60000*10);
            fromTimeMain = formatter.format(date);
            System.out.println("FromTimeMain: " + fromTimeMain);
            long curTimeInMs = date.getTime();
            Date afterAddingMins = new Date(curTimeInMs + 60000*10);
            fromTimeMain = formatter.format(date);
            System.out.println("FromTimeMain: " + fromTimeMain);


            toTime = formatter.format(afterAddingMins);
            System.out.println("To time: " + toTime);
            SimpleDateFormat formatterData= new SimpleDateFormat("dd.MM.yyyy");
            currentDate = formatterData.format(date);
            System.out.println("Current date: " + currentDate);


        createNewMeetingPage.waitMeetingCreatModal();

        createNewMeetingPage.startDate(fromTimeMain);
        createNewMeetingPage.finishDate(toTime);
        //стандартное имя для большинства заказчиков но оно может отличаться
        if(customer.equals("dkudzo")){
            meetingName = meetingsPage.meetingName(fromTimeMain, currentDate, company, modifiedKO);
        }else{
            meetingName = meetingsPage.meetingName(fromTimeMain, currentDate, companyName, modifiedKO);
        }

        if (customer.equals("dkudzo")){
            createNewMeetingPage.fillMeeting(companyName, modifiedKO, meetingName,"Место проведения для автотестов", customer);
        }else{
            createNewMeetingPage.fillMeeting(companyName, modifiedKO, meetingName,"Место проведения для автотестов", customer);
        }

        System.out.println(meetingName);
        if(customer.equals("avtodor") || customer.equals("dkudzo")){
            Assert.assertTrue(createNewMeetingPage.isCheckboxChecked());
            createNewMeetingPage.disableEndManually();
        }
        if(customer.equals("avtodor")){
            meetingNumber = createNewMeetingPage.getMeetingNumber();
            System.out.println("Meeting number " + meetingNumber);
        }

        createNewMeetingPage.submitMeetingCreation();

//        if(customer.equals("rimera") || customer.equals("chtpz")){
//            MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver, wait);
//            meetingPageEdit.sendMaterialByEmail();
//        }
        sleep(1000);
        mainPage.overlayGone();
        sleep(1000);

        int meetingsSizeAfter = meetingsPage.getMeetingsSize();

        assertEquals(meetingsSizeBefore + 1, meetingsSizeAfter);

        meetingsPage.clickRightMeeting(meetingName, customer, meetingNumber);

        MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver, wait);


        meetingId = meetingPageEdit.getQuestionIdFromUrl();
        System.out.println("MEETING ID IS: " + meetingId);
       if(customer.equals("test") || customer.equals("mmk") || customer.equals("rshb") || customer.equals("rico") || customer.equals("expob")){
           meetingPageEdit.sendToApprove(customer);
           if (customer.equals("expob")){
               meetingPageEdit.sendMaterialByEmail();
           }
           meetingPageEdit.saveButtonClick();
           meetingPageEdit.backToCalendarClick();
           meetingPageEdit.preloaderGone();
           meetingsPage.waitMeetingsLoad();
           Assert.assertEquals(meetingsPage.rightMeetingInTableStatusGet(meetingName, customer, meetingNumber), "На утверждении");
       }

    }

    @Test
    @Description("Утвердить заседание через апи")
    public void approveMeetingTest() throws InterruptedException {
        if(customer.equals("test") || customer.equals("mmk") || customer.equals("rshb") || customer.equals("rico") || customer.equals("expob")){
            MeetingsPage meetingsPage = new MeetingsPage(driver, wait);
            Response predsedatelToken = meetingsPage.getTokenForPredsedatel(rest,customer);

            meetingsPage.acceptMeetingWithReason(rest, predsedatelToken, meetingId, customer);

            meetingsPage.refresh();
            meetingsPage.preloaderGone();
            meetingsPage.waitMeetingsLoad();
            if(customer.equals("chtpz") || customer.equals("rimera")){
                Assert.assertEquals(meetingsPage.rightMeetingInTableStatusGet(meetingName, customer, meetingNumber), "Созвано");
            }else{
                Assert.assertEquals(meetingsPage.rightMeetingInTableStatusGet(meetingName, customer, meetingNumber), "Утверждено");
            }
        }else{
            throw new SkipException("Для данного заказчика функционал не доступен");
        }
    }

    @Test
    @Description("Отозвать заседание после утверждения")
    public void withdrawMeetingTest() throws InterruptedException {
        MeetingsPage meetingsPage = new MeetingsPage(driver, wait);
        MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver, wait);
        if(customer.equals("test") || customer.equals("mmk") || customer.equals("rshb") || customer.equals("rico") || customer.equals("expob")){

            meetingsPage.clickRightMeeting(meetingName, customer, meetingNumber);

            meetingPageEdit.approveClick();//отозыв заседания
            meetingPageEdit.alertOk();
            meetingPageEdit.saveButtonClick();
            sleep(2000);
            meetingPageEdit.backToCalendarClick();
            meetingPageEdit.preloaderGone();
        }else{
            meetingPageEdit.approveClick();
            if (customer.equals("chtpz") || customer.equals("rimera")){
                meetingPageEdit.sendMaterialByEmail();
            }

            meetingPageEdit.saveButtonClick();
            meetingPageEdit.preloaderGone();
//            if(customer.equals("avtodor")){
//                meetingPageEdit.alertOk();
//            }
            sleep(2000);
            meetingPageEdit.backToCalendarClick();
            if(customer.equals("avtodor") || customer.equals("suek")){
                try{
                    meetingPageEdit.alertOk();
                }catch (TimeoutException te){

                }
            }

            meetingsPage.waitMeetingsLoad();
            if(customer.equals("chtpz") || customer.equals("rimera")){
                Assert.assertEquals(meetingsPage.rightMeetingInTableStatusGet(meetingName, customer, meetingNumber), "Созвано");
            }else if(customer.equals("dkudzo")){
                Assert.assertEquals(meetingsPage.rightMeetingInTableStatusGet(meetingName, customer, meetingNumber), "Утверждено Подготовка не запущена");
            }
            else{
                Assert.assertEquals(meetingsPage.rightMeetingInTableStatusGet(meetingName, customer, meetingNumber), "Утверждено");
            }

            meetingsPage.clickRightMeeting(meetingName, customer, meetingNumber);

            meetingPageEdit.approveClick();//отозыв заседания
            meetingPageEdit.alertOk();
            meetingPageEdit.saveButtonClick();
            sleep(2000);
            meetingPageEdit.backToCalendarClick();
            meetingPageEdit.preloaderGone();
            meetingsPage.waitMeetingsLoad();
            if(!customer.equals("dkudzo")){
                Assert.assertEquals(meetingsPage.rightMeetingInTableStatusGet(meetingName, customer, meetingNumber), "Планирование");
            }else{
                Assert.assertEquals(meetingsPage.rightMeetingInTableStatusGet(meetingName, customer, meetingNumber), "Планирование Подготовка не запущена");
            }

        }
    }

    @Test
    @Description("Удаление митинга")
    public void deleteMeetingTest(){
        MeetingsPage meetingsPage = new MeetingsPage(driver, wait);
        int meetingsSizeBefore = meetingsPage.getMeetingsSize();

        meetingsPage.clickRightMeeting(meetingName, customer, meetingNumber);

        MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver, wait);
        meetingPageEdit.preloaderGone();
        meetingPageEdit.deleteMeeting();

        int meetingsSizeAfterDelete = meetingsPage.getMeetingsSize();
        Assert.assertEquals(meetingsSizeBefore - 1, meetingsSizeAfterDelete);
    }

    @Test
    @Description("Создание митинга на прошедшую дату")
    public void createMeetingInThePastTest(){
        if (customer.equals("suek") || customer.equals("dkudzo") || customer.equals("alrosa")){
            MainPage mainPage = new MainPage(driver, wait);
            mainPage.goToMeetings();
            MeetingsPage meetingsPage = new MeetingsPage(driver, wait);

            mainPage.addMeeting();
            mainPage.preloaderGone();

            CreateNewMeetingPage createNewMeetingPage = new CreateNewMeetingPage(driver, wait);
            createNewMeetingPage.waitMeetingCreatModal();
            SimpleDateFormat formatter= new SimpleDateFormat("HH:mm");

            Date date = new Date(System.currentTimeMillis()+4*3600000+60000*10);
            fromTimeMain = formatter.format(date);
            System.out.println("FromTimeMain: " + fromTimeMain);
            long curTimeInMs = date.getTime();
            Date afterAddingMins = new Date(curTimeInMs + 60000*10);
            fromTimeMain = formatter.format(date);
            System.out.println("FromTimeMain: " + fromTimeMain);


            toTime = formatter.format(afterAddingMins);
            System.out.println("To time: " + toTime);

            System.out.println("Current date: " + currentDate);

            SimpleDateFormat lastDate= new SimpleDateFormat("dd.MM.yyyy");
            Date pastDate = new Date(System.currentTimeMillis()-1000*60*60*24);
            meetingPastDate = lastDate.format(pastDate);
            createNewMeetingPage.setMeetingDate(meetingPastDate);


            createNewMeetingPage.waitMeetingCreatModal();

            createNewMeetingPage.startDate(fromTimeMain);
            createNewMeetingPage.finishDate(toTime);
            //стандартное имя для большинства заказчиков но оно может отличаться
            if(customer.equals("dkudzo")){
                pastMeetingName = meetingsPage.meetingName(fromTimeMain, meetingPastDate, company, modifiedKO);
            }else{
                pastMeetingName = meetingsPage.meetingName(fromTimeMain, meetingPastDate, companyName, modifiedKO);
            }

            if (customer.equals("dkudzo")){
                createNewMeetingPage.fillMeeting(companyName, modifiedKO, pastMeetingName,"Место проведения для автотестов", customer);
            }else{
                createNewMeetingPage.fillMeeting(companyName, modifiedKO, pastMeetingName,"Место проведения для автотестов", customer);
            }

            System.out.println("PAST MEETING: "+pastMeetingName);
            if(customer.equals("avtodor") || customer.equals("dkudzo")){
                Assert.assertTrue(createNewMeetingPage.isCheckboxChecked());
                createNewMeetingPage.disableEndManually();
            }
            if(customer.equals("avtodor")){
                meetingNumber = createNewMeetingPage.getMeetingNumber();
                System.out.println("Meeting number " + meetingNumber);
            }

            createNewMeetingPage.submitMeetingCreation();
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else{
            throw new SkipException("Для данного заказчика функционал не доступен");
        }
    }

    @Test
    @Description("Проверка что заседание создано на дату в прошлом")
    public void checkMeetingInThePast(){
        if (customer.equals("suek") || customer.equals("dkudzo") || customer.equals("alrosa")){
            MainPage mainPage = new MainPage(driver,wait);
            mainPage.pickDayInCalendar(meetingPastDate);
            MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver,wait);
            Assert.assertTrue(meetingPageEdit.isMeetingCreated(pastMeetingName));
        } else{
        throw new SkipException("Для данного заказчика функционал не доступен");
    }
    }


    @Test
    @Description("Открытие модального окна создания вопроса с главной страницы и  добавление докладчика")
    public void creationQuestionFromMainPageAndAddSpeakerTest() throws InterruptedException {

        MainPage mainPage = new MainPage(driver, wait);
        if (customer.equals("suek") || customer.equals("dkudzo") || customer.equals("alrosa")){
            mainPage.today();
        }
        mainPage.pageLoadWait();


        mainPage.goToQuestions();

        QuestionsPage questionsPage = new QuestionsPage(driver, wait);
        Assert.assertEquals(questionsPage.pageIsLoaded(), right);
        questionsSizeOnQuestionsPage = questionsPage.getQuestionsSize();

        mainPage.addQuestion();

        CreateNewQuestionPage createNewQuestionPage = new CreateNewQuestionPage(driver, wait);
        Date questionDate = new Date(System.currentTimeMillis());
        copiedQuestionName = "Autotest"+questionDate;
        projectDecisionCopiedQuestion = "Automation test project decision for dev";
        createNewQuestionPage.fillQuestionForm(copiedQuestionName,
                companyName,
                modifiedKO,
                projectDecisionCopiedQuestion,
                customer);
        if(customer.equals("test") ){
            createNewQuestionPage.fillQuestionType("Новый тип");
        }else if(customer.equals("suek")){
            createNewQuestionPage.fillQuestionType("Договор");
        }else if(customer.equals("dkudzo")){
            createNewQuestionPage.fillQuestionType("xxxxxxxxxxxxxxxxx");
        }

        if(customer.equals("test")){
            createNewQuestionPage.addSpeaker("Пименов Первый", "Тестовый аккаунт", customer);
        }else {
            createNewQuestionPage.addSpeaker("Pimenov Aleksandr", "TEST", customer);
        }
    }

    @Test
    @Description("Добавление материалов и проверка что материалы отображаются при добавлении")
    public void addMaterialsInQuestionFromMainPageTest(){
        CreateNewQuestionPage createNewQuestionPage = new CreateNewQuestionPage(driver, wait);
        //Добавление материалов в создаваемый вопрос
        String[] paths = {
                "C:\\TestData\\Attach\\DOC.doc",
                "C:\\TestData\\Attach\\DOCX.docx",
                "C:\\TestData\\Attach\\XLS.xls",
                "C:\\TestData\\Attach\\PPT.ppt",
                "C:\\TestData\\Attach\\PDF.pdf"
        };
        createNewQuestionPage.uploadMaterials(paths);

        //тест падает если из-за ошибка конвертации
//        createNewQuestionPage.addMaterial("Размеры\\14-mb.doc");

        int addedMaterials = createNewQuestionPage.materialsSize();
        Assert.assertEquals(addedMaterials, materialsAmountInQuestionFromMainPage);
    }

    @Test
    @Description("Добавление вопроса и проверка его создания")
    public void addQuestion() throws InterruptedException {
        CreateNewQuestionPage createNewQuestionPage = new CreateNewQuestionPage(driver, wait);
        createNewQuestionPage.saveQuestion();
        createNewQuestionPage.ifModalDisplayedThenFail();

        MainPage mainPage = new MainPage(driver,wait);
        mainPage.overlayGone();
        mainPage.goToQuestions();

        QuestionsPage questionsPage = new QuestionsPage(driver, wait);
        Assert.assertEquals(questionsPage.pageIsLoaded(), right);

        int questionsSizeAfter = questionsPage.getQuestionsSize();
        sleep(1000);
        Assert.assertEquals(questionsSizeAfter, questionsSizeOnQuestionsPage + 1);
        sleep(10000);
    }

    @Test
    @Description("Переход в созданный вопрос, проверка что докладчики добавлены")
    public void isSpeakerAddedAndConvertedTest(){

        QuestionsPage questionsPage = new QuestionsPage(driver,wait);
        questionsPage.getInLastAddedQuestion();
        MainPage mainPage = new MainPage(driver, wait);
        QuestionPageEdit questionPageEdit = new QuestionPageEdit(driver, wait);
        questionId = questionPageEdit.getQuestionIdFromUrl();
        System.out.println("THIS IS QUESTION ID: " + questionId);
        mainPage.overlayGone();
        questionPageEdit.questionPageEditIsLoaded();
        questionPageEdit.goToSpeakers();
        Assert.assertEquals(questionPageEdit.getSpeakersSize().size(), 1);
        screenShot();
    }

    @Test
    @Description("Проверка что материалы добавлены и сконвертированы")
    public void isMaterialsAddedAndConvertedTest(){
        QuestionPageEdit questionPageEdit = new QuestionPageEdit(driver, wait);
        questionPageEdit.goToMaterials();
        MainPage mainPage = new MainPage(driver,wait);
        mainPage.overlayGone();

        int visibleMaterials = questionPageEdit.materialsSize();
        //Проверка кновертации
//        Assert.assertTrue(questionPageEdit.cheackConvertation(materialsAmountInQuestionFromMainPage));
        questionPageEdit.scroll(250);
        screenShot();
        Assert.assertFalse(questionPageEdit.checkEveryStatus().contains("Error"));

        Assert.assertEquals(materialsAmountInQuestionFromMainPage, visibleMaterials);
    }

    @Test
    @Description("Добавление двух подвопросов и проверка что они отображаются")
    public void addTwoSubQuestion() {
        if(customer.equals("avtodor") || customer.equals("suek")){
            QuestionPageEdit questionPageEdit = new QuestionPageEdit(driver,wait);
            questionPageEdit.goToSubquestion();

            questionPageEdit.newSubquestionCreation();

            CreateNewQuestionPage createNewQuestionPage = new CreateNewQuestionPage(driver, wait);
            try {
                createNewQuestionPage.fillSubquestion(subQuestionOne, "", subQuestionProjectDecision);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            createNewQuestionPage.saveQuestion();
            createNewQuestionPage.modalGone();


            questionPageEdit.newSubquestionCreation();
            try {
                createNewQuestionPage.fillSubquestion(subQuestionTwo, "", subQuestionProjectDecision);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            createNewQuestionPage.saveQuestion();
            createNewQuestionPage.modalGone();
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Assert.assertEquals(questionPageEdit.getQuestionTitle(subQuestionOne), subQuestionOne);
            MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver,wait);
            meetingPageEdit.getInQuestion(subQuestionOne);
            subQuestionId = questionPageEdit.getQuestionIdFromUrl();
            System.out.println("SUBQUESTION ID: " + subQuestionId);
            meetingPageEdit.backToMeeting();
            meetingPageEdit.preloaderGone();
            questionPageEdit.goToSubquestion();
            Assert.assertEquals(questionPageEdit.getQuestionTitle(subQuestionTwo), subQuestionTwo);

        } else {
            throw new SkipException("Для данного заказчика функционал не доступен");
        }
    }

    @Test
    @Description("Создать вопрос с типом организационный вопрос  и проверить что вкладки 'материалы' нет")
    public void createOrganizationalQuestionTest() throws InterruptedException {
        if(customer.equals("mmk")){
            MainPage mainPage = new MainPage(driver, wait);
            mainPage.goToQuestions();

            CreateNewQuestionPage createNewQuestionPage = new CreateNewQuestionPage(driver,wait);
            mainPage.addQuestion();
            createNewQuestionPage.fillQuestionForm("Question with organizational type", companyName,
                    modifiedKO, "1234",customer);
            int tabsBefore = createNewQuestionPage.getTabsAmount();
            createNewQuestionPage.fillQuestionType(organizationalQuestionTypeName);
            sleep(2000);
            int tabsAfter = createNewQuestionPage.getTabsAmount();
            System.out.println("------------------------------------------------");
            System.out.println(tabsBefore + " ORGANIZATION " + tabsAfter);
            System.out.println("------------------------------------------------");
            Assert.assertEquals(tabsBefore -1, tabsAfter);
            createNewQuestionPage.saveQuestion();
        }else{
            throw new SkipException("Для данного заказчика функционал не доступен");
        }
    }


    @Test
    @Description("Создание очного заседания, задание ему время проведенения и проверка что заседание создано")
    public void createMeetingTest() throws InterruptedException {

        MainPage mainPage = new MainPage(driver, wait);
        mainPage.goToMeetings();

        MeetingsPage meetingsPage = new MeetingsPage(driver, wait);
        mainPage.overlayGone();

        int meetingsSizeBefore = meetingsPage.getMeetingsSize();

        mainPage.addMeeting();
        mainPage.preloaderGone();

        CreateNewMeetingPage createNewMeetingPage = new CreateNewMeetingPage(driver, wait);
        createNewMeetingPage.waitMeetingCreatModal();

        //Задаем время
        SimpleDateFormat formatter= new SimpleDateFormat("HH:mm");
        // Date date = new Date(System.currentTimeMillis()+4*3600000+300000);
        Date date = new Date(System.currentTimeMillis()+4*3600000+600000*4);
        fromTimeMain = formatter.format(date);
        System.out.println("FromTimeMain: " + fromTimeMain);
        long curTimeInMs = date.getTime();
        // Date afterAddingMins = new Date(curTimeInMs + 4*3600000 + 60000*8);
        Date afterAddingMins = new Date(curTimeInMs + 60000*8);
        String toTime = formatter.format(afterAddingMins);

        SimpleDateFormat formatterData= new SimpleDateFormat("dd.MM.yyyy");
        currentDate = formatterData.format(date);
        System.out.println("Current date: " + currentDate);
        createNewMeetingPage.waitMeetingCreatModal();

        createNewMeetingPage.startDate(fromTimeMain);
        createNewMeetingPage.finishDate(toTime);

        System.out.println(fromTimeMain + " " + currentDate);
        if(customer.equals("dkudzo")){
            meetingName = meetingsPage.meetingName(fromTimeMain, currentDate, company, modifiedKO);
        }else{
            meetingName = meetingsPage.meetingName(fromTimeMain, currentDate, companyName, modifiedKO);
        }

        System.out.println(meetingName);

        createNewMeetingPage.fillMeeting(companyName, modifiedKO, meetingName, "Место проведения для автотестов", customer);
        if(customer.equals("chtpz") || customer.equals("rimera") || customer.equals("alrosa") || customer.equals("expob")){
            createNewMeetingPage.addSkypeLink("https://www.skype.com/ru/", customer);
        }

        System.out.println(meetingName);
        if(customer.equals("avtodor")){
            Assert.assertTrue(createNewMeetingPage.isCheckboxChecked());
            createNewMeetingPage.disableEndManually();
        }
        if(customer.equals("avtodor")){
            meetingNumber = createNewMeetingPage.getMeetingNumber();
            System.out.println("Meeting number " + meetingNumber);
        }
        screenShot();
        createNewMeetingPage.submitMeetingCreation();


        sleep(1000);
        mainPage.overlayGone();
        mainPage.goToMeetings();

        sleep(1000);
        int meetingsSizeAfter = meetingsPage.getMeetingsSize();

        Assert.assertEquals(meetingsSizeBefore + 1, meetingsSizeAfter);
    }

    @Test
    @Description("Переход в созданное заседание, добавление существующего вопроса и проверка его добавления в повестку")
    public void addCreatedQuestionInCreatedMeetingTest() throws InterruptedException {

        MeetingsPage meetingsPage = new MeetingsPage(driver, wait);
        meetingsPage.clickRightMeeting(meetingName, customer, meetingNumber);

        MainPage mainPage = new MainPage(driver, wait);
        mainPage.overlayGone();

        MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver, wait);
        if(customer.equals("mmk")){
            meetingPageEdit.addExistQuestion();
        }
        meetingPageEdit.addExistQuestion();

        sleep(3000);
        meetingPageEdit.scrollDownPage(300);
        int afterAdd = meetingPageEdit.questionsCount();
        if(customer.equals("mmk")){
            Assert.assertEquals(2, afterAdd);
        }else{
            Assert.assertEquals(1, afterAdd);
        }

        meetingPageEdit.scroll(250);
        screenShot();
        //взять название вопроса
        if(customer.equals("avtodor") || customer.equals("suek")){
            Assert.assertEquals(meetingPageEdit.getSubQuestionTitle(subQuestionOne),subQuestionOne);
            Assert.assertEquals(meetingPageEdit.getSubQuestionTitle(subQuestionTwo),subQuestionTwo);
        }
    }

    @Test
    @Description("Создание нового вопроса, добавление в него докладчика")
    public void creationNewQuestionWithMaterialsAndSpeakerTest() throws InterruptedException {

        MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver, wait);
        questionsSizeInAgenda = meetingPageEdit.questionsCount();
        meetingPageEdit.chooseNewQuestion();
        meetingPageEdit.fillMainQuestionData("Question Created From Meeting",
                "Decision for question", customer);
        if(customer.equals("test")){
            meetingPageEdit.fillQuestionType("Новый тип");
        }else if(customer.equals("suek")){
            meetingPageEdit.fillQuestionType("Договор");
        }else if(customer.equals("dkudzo")){
            meetingPageEdit.fillQuestionType("xxxxxxxxxxxxxxxxx");
        }

        if(customer.equals("test")){
            meetingPageEdit.addSpeaker("Пименов Первый", "Тестовый аккаунт", customer);
        }else {
            meetingPageEdit.addSpeaker("Pimenov Aleksandr", "TEST", customer);
        }
    }

    @Test
    @Description("Добавление материалов в новый вопрос и проверка, что материалы отображаются при добавлении")
    public void addMaterialsInNewQuestionTest(){
        MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver, wait);
        String[] paths = {
                "C:\\TestData\\Attach\\DOC.doc",
                "C:\\TestData\\Attach\\XLS.xls",
                "C:\\TestData\\Attach\\PDF.pdf",
                "C:\\TestData\\Attach\\PPT.ppt"
        };
        meetingPageEdit.uploadMaterials(paths);

        int materialsBeforeAdd = meetingPageEdit.getMaterialsSize();
        Assert.assertEquals(materialsBeforeAdd, materialsAmountInNewQuestion);
    }

    @Test
    @Description("Добавление вопроса и проверка что вопрос отображается в повестке")
    public void submitNewQuestionCreationAndCheckTest() throws InterruptedException {
        MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver, wait);
        meetingPageEdit.submitQuestionCreation();
        meetingPageEdit.ifModalDisplayedThenFail();

        sleep(1000);

        MainPage mainPage = new MainPage(driver, wait);
        mainPage.overlayGone();
        meetingPageEdit.downScroll();
        meetingPageEdit.isVisibleSecondQuestion();
        int afterAdd = meetingPageEdit.questionsCount();

        Assert.assertEquals(afterAdd, questionsSizeInAgenda + 1);//
        screenShot();// проверка что вопрос создан
    }



    @Test
    @Description("Добавление вопроса для удаления")
    public void addNewQuestionForDeleteTest() throws InterruptedException {
        MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver,wait);
        meetingPageEdit.chooseNewQuestion();
        meetingPageEdit.fillMainQuestionData("For Question page question", "Какое то наполнение", customer);
        if(customer.equals("test")){
            meetingPageEdit.fillQuestionType("Новый тип");
        }else if(customer.equals("suek")){
            meetingPageEdit.fillQuestionType("Договор");
        }else if(customer.equals("dkudzo")){
            meetingPageEdit.fillQuestionType("xxxxxxxxxxxxxxxxx");
        }

        if(customer.equals("test")){
            meetingPageEdit.addSpeaker("Пименов Первый", "Тестовый аккаунт", customer);
        }else {
            meetingPageEdit.addSpeaker("Pimenov Aleksandr", "TEST", customer);
        }

        meetingPageEdit.submitQuestionCreation();

        meetingPageEdit.preloaderGone();
        meetingPageEdit.scroll(500);
        sleep(2000);
        if(customer.equals("mmk")){
            Assert.assertEquals(meetingPageEdit.questionsCount(), 4);
        }else{
            Assert.assertEquals(meetingPageEdit.questionsCount(), 3);
        }

    }

    @Test
    @Description("Переход в последний добавленный вопрос и добавление двух подвопросов")
    public void getInLastAddedQuestionTest(){
        if(customer.equals("avtodor") || customer.equals("suek")){
            MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver,wait);
            meetingPageEdit.getInQuestion("For Question page question");
            QuestionPageEdit questionPageEdit = new QuestionPageEdit(driver,wait);
            questionPageEdit.goToSubquestion();

            questionPageEdit.newSubquestionCreation();

            CreateNewQuestionPage createNewQuestionPage = new CreateNewQuestionPage(driver, wait);
            try {
                createNewQuestionPage.fillSubquestion(subQuestionThree, "", subQuestionProjectDecision);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            createNewQuestionPage.saveQuestion();
            createNewQuestionPage.modalGone();

            questionPageEdit.newSubquestionCreation();
            try {
                createNewQuestionPage.fillSubquestion(subQuestionFour, "", subQuestionProjectDecision);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            createNewQuestionPage.saveQuestion();
            createNewQuestionPage.modalGone();
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Assert.assertEquals(questionPageEdit.getQuestionTitle(subQuestionThree), subQuestionThree);
            Assert.assertEquals(questionPageEdit.getQuestionTitle(subQuestionFour), subQuestionFour);
        } else {
            throw new SkipException("Для данного заказчика функционал не доступен");
        }
    }

    @Test
    @Description("Удаление подвопроса")
    public void deleteSubQuestionFromMeetingTest(){
        if(customer.equals("avtodor") || customer.equals("suek")){
            MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver,wait);
            meetingPageEdit.backToMeeting();

            meetingPageEdit.deleteSubQuestion(subQuestionFour);
            meetingPageEdit.saveButtonClick();

        } else {
            throw new SkipException("Для данного заказчика функционал не доступен");
        }
    }

    @Test
    @Description("Удаление вопроса")
    public void deleteQuestionFromMeetingTest(){
        MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver,wait);
        meetingPageEdit.deleteQuestionByName("For Question page question");
        if(customer.equals("mmk")){
            Assert.assertEquals(meetingPageEdit.questionsCount(), 3);
        }else{
            Assert.assertEquals(meetingPageEdit.questionsCount(), 2);
        }
        meetingPageEdit.backToCalendarClick();
        meetingPageEdit.preloaderGone();
    }


    @Test
    @Description("Проверка что удаленные вопросы отображаются")
    public void checkDeletedQuestionsTest() throws InterruptedException {
        MainPage mainPage = new MainPage(driver, wait);
        mainPage.goToQuestions();

        QuestionsPage questionsPage = new QuestionsPage(driver, wait);
        Assert.assertEquals(questionsPage.getTitleOfQuestion("For Question page question"),"For Question page question");
        if(customer.equals("avtodor") || customer.equals("suek")) {
            Assert.assertEquals(questionsPage.getTitleOfQuestion(subQuestionFour), subQuestionFour);
        }

    }

    @Test
    @Description("Удаление вопроса из приложения")
    public void deleteQuestionFromAppTest(){
        QuestionsPage questionsPage = new QuestionsPage(driver, wait);
        int sizeBefore = questionsPage.getQuestionsSize();
        if(customer.equals("avtodor") || customer.equals("suek")){
            questionsPage.deleteQuestionByName(subQuestionFour);
        }else{
            questionsPage.deleteQuestionByName("For Question page question");
        }

        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int sizeAfter = questionsPage.getQuestionsSize();
        Assert.assertEquals(sizeBefore - 1, sizeAfter);
    }



    @Test
    @Description("Формирование повестки")
    public void createAgendaTest(){
        MainPage mainPage = new MainPage(driver, wait);
        mainPage.goToMeetings();

        MeetingsPage meetingsPage = new MeetingsPage(driver, wait);
        meetingsPage.clickRightMeeting(meetingName, customer, meetingNumber);
        if(customer.equals("mmk") || customer.equals("rshb")){
            MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver,wait);
            //у автодора повестка сразу скачивается и не добавляется в материалы

            meetingPageEdit.createAgenda(customer);
            meetingPageEdit.goToMaterialsTabInMeeting();

        }else {
            throw new SkipException("Для данного заказчика функционал не доступен");
        }
    }

    @Test
    @Description("Переход в событие,копирование вопроса и проверка, что поля заполнены")
    public void copyQuestionTest(){
        MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver,wait);
        if (customer.equals("mmk") || customer.equals("rshb")){
            meetingPageEdit.goToAgenda();
        }
        meetingPageEdit.getInQuestion(copiedQuestionName);
        QuestionPageEdit questionPageEdit = new QuestionPageEdit(driver,wait);
        questionPageEdit.copyQuestionBtnClick();

        CreateNewQuestionPage createNewQuestionPage = new CreateNewQuestionPage(driver,wait);
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(createNewQuestionPage.getQuestionTitle().replaceAll("[ \n]",""),
                copiedQuestionName.replaceAll(" ",""));
        Assert.assertEquals(createNewQuestionPage.getProjectDecision(),projectDecisionCopiedQuestion);
    }

    @Test
    @Description("Заполнение полей и добавление скопирования вопроса на страницу вопросов")
    public void submitQuestionCopyTest(){
        CreateNewQuestionPage createNewQuestionPage = new CreateNewQuestionPage(driver,wait);
        MainPage mainPage = new MainPage(driver,wait);
        mainPage.goToQuestions();
        createNewQuestionPage.fillCopiedQuestion(companyName, modifiedKO,customer);
        createNewQuestionPage.goToMaterials();
        screenShot();
        createNewQuestionPage.saveQuestion();

        createNewQuestionPage.preloaderGone();
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        createNewQuestionPage.refresh();
        createNewQuestionPage.preloaderGone();
        QuestionsPage questionsPage = new QuestionsPage(driver,wait);
        Assert.assertEquals(questionsPage.getTitleOfQuestion(copiedQuestionName),copiedQuestionName);
    }

    @Test
    @Description("Проверка что материалы отображаются")
    public void checkDocumentsInCopiedQuestionTest(){
        QuestionsPage questionsPage = new QuestionsPage(driver,wait);
        questionsPage.goInQuestion(copiedQuestionName);

        QuestionPageEdit questionPageEdit = new QuestionPageEdit(driver,wait);
        questionPageEdit.goToMaterials();
        questionPageEdit.pageDown();

        Assert.assertTrue(questionPageEdit.checkMaterialName("DOCX.docx (Версия №1)"));
        Assert.assertTrue(questionPageEdit.checkMaterialName("DOC.doc (Версия №1)"));
        Assert.assertTrue(questionPageEdit.checkMaterialName("PDF.pdf (Версия №1)"));
        Assert.assertTrue(questionPageEdit.checkMaterialName("XLS.xls (Версия №1)"));
        Assert.assertTrue(questionPageEdit.checkMaterialName("PPT.ppt (Версия №1)"));
    }

    @Test
    @Description("Проверка что докладчик добавлен")
    public void isSpeakerAddedInNewQuestionTest(){
        MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver,wait);
        MainPage mainPage = new MainPage(driver,wait);
        mainPage.goToMeetings();

        MeetingsPage meetingsPage = new MeetingsPage(driver,wait);
        meetingsPage.clickRightMeeting(meetingName, customer, meetingNumber);


        meetingPageEdit.getInQuestion("Question Created From Meeting");
//        meetingPageEdit.questionPageIsLoaded();

        meetingPageEdit.preloaderGone();
        meetingPageEdit.goToSpeakers(customer);
        Assert.assertEquals(1, meetingPageEdit.getSpeakersSize().size());
        screenShot();
    }

    @Test
    @Description("Проверка что материалы добавлены и сконвертированы")
    public void isMaterialsAddedAndConvertedInNewQuestionTest(){

        MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver, wait);
        meetingPageEdit.goToMaterialsTab();
        Assert.assertEquals(meetingPageEdit.pageIsLoaded(), right);
        int materialsAfterAdd = meetingPageEdit.getMaterialsSize();
        Assert.assertEquals(materialsAmountInNewQuestion, materialsAfterAdd);
        QuestionPageEdit questionPageEdit = new QuestionPageEdit(driver, wait);
//        Assert.assertTrue(questionPageEdit.cheackConvertation(materialsAfterAdd));
//        meetingPageEdit.upScroll();
        Assert.assertFalse(questionPageEdit.checkEveryStatus().contains("Error"));
        questionPageEdit.scroll(250);
        screenShot();
    }

    @Test
    @Description("Удаление материалов из вопроса")
    public void deleteMaterialFromNewQuestionTest() throws InterruptedException {
        QuestionPageEdit questionPageEdit = new QuestionPageEdit(driver, wait);
        questionPageEdit.deleteFirstAttach(customer);
        MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver, wait);
        Assert.assertEquals(meetingPageEdit.getMaterialsSize(), 3);
    }

    @Test
    @Description("Увеличение версии документа")
    public void changeVersionOfDocumentTest(){

        QuestionPageEdit questionPageEdit = new QuestionPageEdit(driver, wait);
        MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver, wait);
        if(customer.equals("test") ){
            String[] paths = {
                    "C:\\TestData\\Attach\\PPT.ppt",
            };
            meetingPageEdit.uploadMaterials(paths);
            questionPageEdit.saveQuestion();
            questionPageEdit.checkEveryStatus();

            Assert.assertTrue(questionPageEdit.isVersionUp("PPT.ppt", 2));
        }else{
            String[] paths = {
                    "C:\\TestData\\Attach\\DOC.doc",
            };
            meetingPageEdit.uploadMaterials(paths);
            questionPageEdit.saveQuestion();
            questionPageEdit.checkEveryStatus();

            Assert.assertTrue(questionPageEdit.isVersionUp("DOC.doc", 2));
        }
    }

    @Test
    @Description("Добавление и удаление участника")
    public void addAndDeleteParticipantsTest() throws InterruptedException {

        MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver, wait);
        meetingPageEdit.backToMeeting();
        meetingPageEdit.preloaderGone();
        meetingPageEdit.goToParticipant();
        int before = meetingPageEdit.participantsSize();
        meetingPageEdit.addParticipant("Пименов Первый");
        meetingPageEdit.scroll(200);
        sleep(2000);
        int after = meetingPageEdit.participantsSize();

        screenShot();
        Assert.assertEquals(before + 1, after);

        meetingPageEdit.deleteParticipant("Пименов Первый", customer);
        after = meetingPageEdit.participantsSize();
        Assert.assertEquals(before, after);
        screenShot();
    }

    @Test
    @Description("Проверка права вето в заседании")
    public void vetoRightInMeetingTest(){
        if(customer.equals("expob")){
            MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver,wait);
            Assert.assertTrue(meetingPageEdit.isCheckedVetoRuleInMeeting("Predsedatel Predsedatel Predsedatel"));
            meetingPageEdit.uncheckVotingRule("Predsedatel Predsedatel Predsedatel");
            Assert.assertFalse(meetingPageEdit.isCheckedVetoRuleInMeeting("Predsedatel Predsedatel Predsedatel"));
            meetingPageEdit.uncheckVotingRule("Predsedatel Predsedatel Predsedatel");
            Assert.assertFalse(meetingPageEdit.isCheckedVetoRuleInMeeting("Predsedatel Predsedatel Predsedatel"));

            Assert.assertTrue(meetingPageEdit.isCheckedVetoRuleInMeeting(userName));
            meetingPageEdit.uncheckVotingRule(userName);
            Assert.assertFalse(meetingPageEdit.isCheckedVetoRuleInMeeting(userName));
            meetingPageEdit.uncheckVotingRule(userName);
            Assert.assertFalse(meetingPageEdit.isCheckedVetoRuleInMeeting(userName));
            screenShot();
        }else {
            throw new SkipException("Для данного заказчика функционал не доступен");
        }
    }

    @Test
    @Description("Проверка что повестка сформировалась")
    public void checkAgendaTest(){
        if(customer.equals("mmk") || customer.equals("rshb")){
            MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver, wait);
            meetingPageEdit.goToMaterialsTabInMeeting();
            Assert.assertTrue(meetingPageEdit.checkAgenda(agenda,meetingName));
        }else {
            throw new SkipException("Для данного заказчика функционал не доступен");
        }
    }

    @Test
    @Description("Выставление рекомендаий по голосованию с вариантом 'Воздержался'")
    public void recommendationsTest(){
        if(customer.equals("dkudzo")){
            MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver, wait);
            meetingPageEdit.goToRecommendations();
            meetingPageEdit.selectAllAbstained();

            Assert.assertTrue(meetingPageEdit.checkAllRecommendations());
        }else{
            throw new SkipException("Для данного заказчика функционал не доступен");
        }
    }


    @Test
    @Description("Утверждение заседание и проверка что оно в статусе 'Утверждено'")
    public void approveMeetingAndCheckTest() throws InterruptedException {

        MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver, wait);
        if(customer.equals("mmk") || customer.equals("rshb") ){
            meetingPageEdit.goToAgenda();
        }
        meetingPageEdit.approveClick();
        if(customer.equals("chtpz") || customer.equals("test") || customer.equals("rimera") || customer.equals("expob")){
            meetingPageEdit.saveChooseMaterialsWindowClick();
        }

        meetingPageEdit.saveButtonClick();
        sleep(2000);
        MainPage mainPage  = new MainPage(driver, wait);
        mainPage.overlayGone();
        meetingPageEdit.saveMessageGone();
        sleep(2000);
        meetingPageEdit.backToCalendarClick();
        sleep(2000);
        mainPage.overlayGone();

        MeetingsPage meetingsPage = new MeetingsPage(driver, wait);
        meetingsPage.meetingPageRefresh();

        if(customer.equals("chtpz") || customer.equals("rimera")){
            Assert.assertTrue(meetingsPage.rightMeetingInTableStatusGet(meetingName,customer,meetingNumber).equals("Созвано"));
            Assert.assertTrue(meetingsPage.rightMeetingInTableStatusGet(meetingName,customer,meetingNumber).equals("Созвано"));
        }else if(customer.equals("dkudzo")){
            Assert.assertTrue(meetingsPage.rightMeetingInTableStatusGet(meetingName,customer,meetingNumber).equals("Утверждено Подготовка не запущена"));
        }
        else{
            Assert.assertTrue(meetingsPage.rightMeetingInTableStatusGet(meetingName,customer,meetingNumber).equals("Утверждено"));
        }
    }

    @Test
    @Description("Переход в коллегиал и проверка что голосование по вопросам не доступно + фича голосование в утвержденном")
    public void openCollegialAndCheckVoteDisabled() {
        CollegialSignUp collegialSignUp = new CollegialSignUp(driver, wait);
        collegialSignUp.openNewBrowserTab();

        driver.get(getConfig(customer + ".collegial"));

        if(!customer.equals("chtpz")) {
            if(customer.equals("alrosa")){
                collegialSignUp.googleSignIn();
            }else {
                collegialSignUp.signIn(mainUserLogin, mainUserPassword);
            }

        }
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CollegialMeetingsPage collegialMeetingsPage = new CollegialMeetingsPage(driver, wait);
        collegialMeetingsPage.getInRightMeetingCollegial(meetingName, customer);
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CollegialMeetingCardPage collegialMeetingCardPage = new CollegialMeetingCardPage(driver, wait);
        if(customer.equals("test") || customer.equals("dkudzo")
                || customer.equals("avtodor") || customer.equals("mmk")){
            if(customer.equals("avtodor")){
                collegialMeetingCardPage.nextQuestion();
            }
            Assert.assertFalse(collegialMeetingCardPage.checkVoteDisabled());
            screenShot();
        }else{
            if(customer.equals("suek")){
                collegialMeetingCardPage.nextQuestion();
            }
            Assert.assertTrue(collegialMeetingCardPage.checkVoteDisabled());
            screenShot();
        }
    }

    @Test
    @Description("Проверка того что рекоммендации не отображаются")
    public void checkRecommendationDisabledTest(){
        if (customer.equals("dkudzo")){
            CollegialMeetingCardPage collegialMeetingCardPage = new CollegialMeetingCardPage(driver,wait);
            Assert.assertTrue(collegialMeetingCardPage.isRecommendationDisabled());
        }else{
            throw new SkipException("Для данного заказчика функционал не доступен");
        }

    }

    @Test
    @Description("Изменение времени утвержденного заседания")
    public void changeTimeInApprovedMeetingTest() throws InterruptedException {
        MeetingsPage meetingsPage = new MeetingsPage(driver,wait);
        meetingsPage.goToAnotherTab();

        meetingsPage.clickRightMeeting(meetingName, customer, meetingNumber);
        MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver, wait);

        SimpleDateFormat formatter= new SimpleDateFormat("HH:mm");
//             Date date = new Date(System.currentTimeMillis()+3600000*4);
        Date date = new Date(System.currentTimeMillis());
        String start = formatter.format(date);

        meetingPageEdit.setStartDate(start);

        meetingPageEdit.saveButtonClick();
        sleep(1000);
        meetingPageEdit.overlayGone();
        screenShot();
        sleep(2000);

    }

    @Test
    @Description("Делегирование голоса для главного тестового пользователя")
    public void delegateVoteInFirstQuestionTest(){
        if(customer.equals("avtodor") || customer.equals("mmk") || customer.equals("rtk") || customer.equals("suek")
                || customer.equals("dkudzo") || customer.equals("rico")){
            MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver,wait);
            if(customer.equals("avtodor") || customer.equals("suek")){
                meetingPageEdit.getInQuestion(subQuestionOne);
            }else{
                meetingPageEdit.getInQuestion(copiedQuestionName);
            }


            QuestionPageEdit questionPageEdit = new QuestionPageEdit(driver, wait);
            questionPageEdit.goToDelegation();

            questionPageEdit.delegateVoteForUser(mainUser,"Воздержался");
            questionPageEdit.refresh();
            questionPageEdit.goToDelegation();
            Assert.assertTrue(questionPageEdit.checkDelegateVote(mainUser,"Воздержался"));


        }else {
            throw new SkipException("Для данного заказчика функционал не доступен");
        }
    }

    @Test
    @Description("Проверка что заседание в статусе активно")
    public void checkMeetingStatusTest(){
        MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver,wait);
        if(customer.equals("avtodor") || customer.equals("mmk") || customer.equals("rtk") || customer.equals("suek")
                || customer.equals("dkudzo") || customer.equals("rico") ){
            meetingPageEdit.backToMeeting();
            meetingPageEdit.refresh();
            meetingPageEdit.preloaderGone();
        }
        meetingPageEdit.backToCalendarClick();
        meetingPageEdit.refresh();
        meetingPageEdit.preloaderGone();
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MeetingsPage meetingsPage = new MeetingsPage(driver,wait);
        meetingsPage.refresh();
        meetingsPage.waitMeetingsLoad();
        if(customer.equals("dkudzo")){
            Assert.assertTrue(meetingsPage.rightMeetingInTableStatusGet(meetingName,customer,meetingNumber).equals("Активно Подготовка не запущена"));
        }else{
            System.out.println("=================================");
            System.out.println(meetingName);
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");
            System.out.println(simpleDateFormat.format(date));
            Assert.assertTrue(meetingsPage.rightMeetingInTableStatusGet(meetingName,customer,meetingNumber).equals("Активно"));
        }
    }

    @Test
    @Description("Включение доступа к просмотру кратких рекомендаций")
    public void enableRecommendationsForUserTest(){
        if (customer.equals("dkudzo")){
            MainPage mainPage = new MainPage(driver,wait);
            mainPage.goToDirectories();
            EmployeesPage employeesPage = new EmployeesPage(driver,wait);
            employeesPage.goToEmployees();
            employeesPage.openUserForEdit(mainUser);
            screenShot();
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            employeesPage.enableRecommendationsVisibility();

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Assert.assertTrue(employeesPage.isRecommendationEnabled());
            employeesPage.saveChanges();
        }else{
            throw new SkipException("Для данного заказчика функционал не доступен");
        }

    }

    @Test
    @Description("Ссылка скайп")
    public void skypeLinkTest(){
        CollegialMeetingsPage collegialMeetingsPage = new CollegialMeetingsPage(driver, wait);
        collegialMeetingsPage.goToAnotherTab();

        collegialMeetingsPage.refresh();
        collegialMeetingsPage.preloaderGone();
        String window = collegialMeetingsPage.getWindowHandle();
        System.out.println("-------------------------------------------" + window);
        if(customer.equals("chtpz") || customer.equals("rimera") || customer.equals("alrosa")){
            Assert.assertTrue(collegialMeetingsPage.isSkypeDisplayed());
//
//            collegialMeetingsPage.clickSkypeLink();
//            try {
//                sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            collegialMeetingsPage.goToAnotherTab();
//            collegialMeetingsPage.goToAnotherTab();
//            collegialMeetingsPage.goToAnotherTab();
//            System.out.println(collegialMeetingsPage.getUrl());
//            Assert.assertEquals(collegialMeetingsPage.getUrl(),"https://www.skype.com/ru/");
//
//            try {
//                sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            collegialMeetingsPage.closeTab();
//            try {
//                sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            collegialMeetingsPage.switchToWindow(window);
        }else {
            throw new SkipException("Для данного заказчика функционал не доступен");
        }
    }

    @Test
    @Description("Проверка отображения докладчика в первом вопросе")
    public void checkSpeakerInCollegialTest(){
        CollegialMeetingCardPage collegialMeetingCardPage = new CollegialMeetingCardPage(driver,wait);
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(customer.equals("test") ){
            Assert.assertTrue(collegialMeetingCardPage.checkUser().equals("Пименов Первый"));
        }
        if(customer.equals("rshb") || customer.equals("rimera")){
            Assert.assertTrue(collegialMeetingCardPage.checkUser().equals("Pimenov Aleksandr"));
        }
    }

    @Test
    @Description("Организационные вопросы не отображаются в повестке")
    public void checkOrganizationalQuestionInAgendaTest(){
        if(customer.equals("mmk")){
            CollegialMeetingCardPage collegialMeetingCardPage =new CollegialMeetingCardPage(driver,wait);
            collegialMeetingCardPage.openCloseAgenda();
            int amountQuestionsInAgenda = collegialMeetingCardPage.getQuestionSizeInAgenda();
            Assert.assertEquals(amountQuestionsInAgenda, 2);
        }else {
            throw new SkipException("Для данного заказчика функционал не доступен");
        }
    }


    @Test
    @Description("Переход в коллегиал и голосование по первому вопросу ")
    public void voteOnTheFirstQuestionTest() throws InterruptedException {
        CollegialMeetingCardPage collegialMeetingCardPage = new CollegialMeetingCardPage(driver,wait);
        if(customer.equals("mmk")){
            collegialMeetingCardPage.openCloseAgenda();
            screenShot();
            collegialMeetingCardPage.nextQuestion();
            sleep(2000);
        }
        if(customer.equals("avtodor") || customer.equals("suek")){
            Assert.assertTrue(collegialMeetingCardPage.checkVoteDisabled());

        }else{
            collegialMeetingCardPage.vote("Yes", customer);
            collegialMeetingCardPage.preloaderGone();
        }

        if(!customer.equals("avtodor") || !customer.equals("suek")){
            if(customer.equals("nspk") || customer.equals("alrosa") || customer.equals("rico")){
                Assert.assertEquals(collegialMeetingCardPage.yesBtnColor(),"rgba(204, 234, 226, 1)");
            } else{
                sleep(1000);
                Assert.assertEquals(collegialMeetingCardPage.yesBtnColor(),"rgba(134, 203, 87, 0.5)");
            }
        }
        screenShot();

//        if(!customer.equals("mmk")){
//            collegialMeetingCardPage.fillOpinion("Особое мнение участник " + userName);
//            collegialMeetingCardPage.preloaderGone();
//        }

    }

    @Test
    @Description("Проверка отображения рекомендаций в первом вопросе")
    public void checkRecommendationsInFirstQuestionCollegialTest(){
        if(customer.equals("dkudzo")){
        CollegialMeetingCardPage collegialMeetingCardPage = new CollegialMeetingCardPage(driver,wait);
        collegialMeetingCardPage.checkRecommendation("Рекомендовано голосовать «ЗА»");
        }else{
            throw new SkipException("Для данного заказчика функционал не доступен");
        }
    }

    @Test
    @Description("Голосование по первому ПОДВОПРОСУ СУЭК и АВТОДОР")
    public void voteOnTheFirstSubQuestionTest(){
        CollegialMeetingCardPage collegialMeetingCardPage = new CollegialMeetingCardPage(driver,wait);
        if(customer.equals("avtodor") || customer.equals("suek")){
            //голосование первый подвопрос
            collegialMeetingCardPage.nextQuestion();
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(customer.equals("avtodor")){
                collegialMeetingCardPage.revote("Yes", customer);
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                collegialMeetingCardPage.denyOpinionAdddingMMK();
            }else{
                collegialMeetingCardPage.revote("Yes", customer);
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                collegialMeetingCardPage.preloaderGone();
            }

            //проверка по первому
            if(customer.equals("suek")){
                Assert.assertEquals(collegialMeetingCardPage.yesBtnColor(),"rgba(155, 192, 9, 0.25)");
            } else{
                Assert.assertEquals(collegialMeetingCardPage.yesBtnColor(),"rgba(134, 203, 87, 0.5)");
            }
            //голосование по второму подвопросу

        }else {
            throw new SkipException("Для данного заказчика функционал не доступен");
        }
    }

    @Test
    @Description("Голосование по второму ПОДВОПРОСУ СУЭК и АВТОДОР")
    public void voteOnTheSecondSubQuestionTest(){
        CollegialMeetingCardPage collegialMeetingCardPage = new CollegialMeetingCardPage(driver,wait);
        if(customer.equals("avtodor") || customer.equals("suek")){
            collegialMeetingCardPage.nextQuestion();
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(customer.equals("avtodor")){
                collegialMeetingCardPage.revote("No", customer);
                collegialMeetingCardPage.denyOpinionAdddingMMK();
            }else{
                collegialMeetingCardPage.revote("No", customer);
                collegialMeetingCardPage.preloaderGone();
            }
            //проверка по второму
            if(customer.equals("suek")){
                Assert.assertEquals(collegialMeetingCardPage.noBtnColor(),"rgba(235, 25, 46, 0.25)");
            }
            else{
                Assert.assertEquals(collegialMeetingCardPage.noBtnColor(),"rgba(249, 169, 169, 1)");
            }
        }else{
            throw new SkipException("Для данного заказчика функционал не доступен");
        }
    }


    @Test
    @Description("Голосование по второму вопросу")
    public void voteOnTheSecondQuestionTest(){
        CollegialMeetingCardPage collegialMeetingCardPage = new CollegialMeetingCardPage(driver,wait);
        collegialMeetingCardPage.nextQuestion();
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if( customer.equals("avtodor")){
            collegialMeetingCardPage.vote("No", customer);
            collegialMeetingCardPage.acceptOpinionAdddingMMK("SPECIAL OPINION TEST");

        }else{
            collegialMeetingCardPage.vote("No", customer);
            collegialMeetingCardPage.preloaderGone();
            if(!customer.equals("mmk")){
                collegialMeetingCardPage.addSpecialOpinionForcibly("SPECIAL OPINION TEST");
            }
        }

        if(customer.equals("nspk")|| customer.equals("alrosa") || customer.equals("rico")){
            Assert.assertEquals(collegialMeetingCardPage.noBtnColor(),"rgba(253, 214, 215, 1)");
        }else if(customer.equals("suek")){
            Assert.assertEquals(collegialMeetingCardPage.noBtnColor(),"rgba(235, 25, 46, 0.25)");
        }
        else{
            Assert.assertEquals(collegialMeetingCardPage.noBtnColor(),"rgba(249, 169, 169, 1)");
        }
        screenShot();
    }

    @Test
    @Description("Проверка отображения рекомендаций во втором вопросе")
    public void checkRecommendationsInSecondQuestionCollegialTest(){
        if(customer.equals("dkudzo")){
            CollegialMeetingCardPage collegialMeetingCardPage = new CollegialMeetingCardPage(driver,wait);
            collegialMeetingCardPage.checkRecommendation("Рекомендовано голосовать «ЗА»");
        }else{
            throw new SkipException("Для данного заказчика функционал не доступен");
        }

    }


    @Test
    @Description("Уведомление о необходимости формирования ПМ/ОЛ АЛРОСА")
    public void checkOlPmAlertTest(){
        if(customer.equals("alrosa")){
            CollegialMeetingCardPage collegialMeetingCardPage = new CollegialMeetingCardPage(driver,wait);
            collegialMeetingCardPage.checkOlPMAlert();
        }else {
            throw new SkipException("Для данного заказчика функционал не доступен");
        }
    }

    @Test
    @Description("Проверка докладчиков во втором вопросе")
    public void checkSpeakerInSecondQuestionTest(){
        CollegialMeetingCardPage collegialMeetingCardPage = new CollegialMeetingCardPage(driver,wait);
        if(customer.equals("test") ){
            Assert.assertTrue(collegialMeetingCardPage.checkUser().equals("Пименов Первый"));
        }
        if(customer.equals("rshb") || customer.equals("rimera")){
            Assert.assertTrue(collegialMeetingCardPage.checkUser().equals("Pimenov Aleksandr"));
        }
    }


    @Test
    @Description("Проверка результатов голосования и фичи Алроса")
    public void checkVoteResultsTest() throws InterruptedException {
        CollegialMeetingCardPage collegialMeetingCardPage = new CollegialMeetingCardPage(driver, wait);
        collegialMeetingCardPage.nextQuestion();

        if(customer.equals("suek") || customer.equals("avtodor")){
            Assert.assertTrue(collegialMeetingCardPage.checkVoteOnSubQuestion("За", "Против"));
            Assert.assertTrue(collegialMeetingCardPage.checkSecondQuestionVoteWithoutSubQuestion("Против"));
        }else{
            Assert.assertTrue(collegialMeetingCardPage.checkVoteResults("За", "Против"));
        }

        if(customer.equals("alrosa")){
            Assert.assertTrue(collegialMeetingCardPage.checkAlertInscriptionOnVotingResults());
        }
    }

    @Test
    @Description("Проверка что особое мнение отображается в результатах голосования")
    public void checkSpecialOpinionInVoteResultsTest(){
        if(!customer.equals("mmk")){
            CollegialMeetingCardPage collegialMeetingCardPage = new CollegialMeetingCardPage(driver, wait);
            Assert.assertTrue(collegialMeetingCardPage.checkSpecialOpinionInVoteResults("SPECIAL OPINION TEST"));
        }else {
            throw new SkipException("Для данного заказчика функционал не доступен");
        }

    }

    @Test
    @Description("Проверка что голос сбрасывается при изменении проекта решения")
    public void changeProjectDecisionAndDropVoteTest() throws InterruptedException {
        CollegialMeetingCardPage collegialMeetingCardPage = new CollegialMeetingCardPage(driver, wait);
        if(!customer.equals("alrosa")){
            String body = "{\"DraftDecision\": \"Измененный проект решения!\"\n}";
            Response response = collegialMeetingCardPage.getTokenForMainUser(rest,customer);
            if (customer.equals("avtodor") || customer.equals("suek")){
                collegialMeetingCardPage.changeProjectDecisionApi(rest, response, body, subQuestionId, customer);
            }else{
                collegialMeetingCardPage.changeProjectDecisionApi(rest, response, body, questionId, customer);
            }

            collegialMeetingCardPage.refresh();
            collegialMeetingCardPage.preloaderGone();

            if(customer.equals("suek") || customer.equals("avtodor") || customer.equals("mmk")){
                collegialMeetingCardPage.nextQuestion();
                System.out.println("THIS IS COLOR: " + collegialMeetingCardPage.yesBtnColor());
                assertEquals("rgba(0, 0, 0, 0.05)", collegialMeetingCardPage.yesBtnColor());
            }else{
                System.out.println("THIS IS COLOR: " + collegialMeetingCardPage.yesBtnColor());
                assertEquals("rgba(0, 0, 0, 0.05)", collegialMeetingCardPage.yesBtnColor());
            }

            if( customer.equals("avtodor")){
                collegialMeetingCardPage.vote("Yes", customer);
                collegialMeetingCardPage.denyOpinionAdddingMMK();

            }else{
                collegialMeetingCardPage.vote("Yes", customer);
                collegialMeetingCardPage.preloaderGone();
            }

            if(customer.equals("nspk") || customer.equals("alrosa") || customer.equals("rico")){
                Assert.assertEquals(collegialMeetingCardPage.yesBtnColor(),"rgba(204, 234, 226, 1)");
            }else if(customer.equals("suek")){
                Assert.assertEquals(collegialMeetingCardPage.yesBtnColor(),"rgba(155, 192, 9, 0.25)");
            } else{
                Assert.assertEquals(collegialMeetingCardPage.yesBtnColor(),"rgba(134, 203, 87, 0.5)");
            }
            screenShot();
        }else{
            collegialMeetingCardPage.refresh();
            throw new SkipException("Для данного заказчика функционал не доступен");
        }
    }

    @Test
    @Description("Создание ПМ и проверка что ПМ сформировано")
    public void createPMTest() throws InterruptedException {
        if(!customer.equals("expob")){
        CollegialMeetingCardPage collegialMeetingCardPage = new CollegialMeetingCardPage(driver, wait);
        if (customer.equals("suek") || customer.equals("avtodor") || customer.equals("mmk")){
            collegialMeetingCardPage.nextQuestion();
            sleep(1000);
            screenShot();
            collegialMeetingCardPage.nextQuestion();
            sleep(1000);
            screenShot();
            collegialMeetingCardPage.nextQuestion();
            sleep(1000);
            screenShot();
        }else{
            collegialMeetingCardPage.nextQuestion();
            sleep(1000);
            screenShot();
            collegialMeetingCardPage.nextQuestion();
            sleep(1000);
            screenShot();
        }
        collegialMeetingCardPage.createOlPm(customer);
        sleep(3000);
        if(customer.equals("dkudzo")){
            CollegialMeetingsPage collegialMeetingsPage = new CollegialMeetingsPage(driver,wait);
            collegialMeetingsPage.getInRightMeetingCollegial(meetingName,customer);
        }else{
            collegialMeetingCardPage.nextQuestion();
            collegialMeetingCardPage.nextQuestion();
        }

        sleep(2000);

        Assert.assertTrue(collegialMeetingCardPage.isOlPmCreated());
        collegialMeetingCardPage.closeModal();}else {
            throw new SkipException("Для данного заказчика функционал не доступен");
        }
    }


    @Test
    @Description("Переход в заседание и формирование выписки по вопросу")
    public void createAndCheckExtractTest(){
        MeetingsPage meetingsPage = new MeetingsPage(driver, wait);
        meetingsPage.goToAnotherTab();
        if(customer.equals("dkudzo")){
            MainPage mainPage = new MainPage(driver,wait);
            mainPage.goToMeetings();
        }
        meetingsPage.clickRightMeeting(meetingName, customer, meetingNumber);

       if(customer.equals("rico") || customer.equals("rshb") || customer.equals("mmk") || customer.equals("avtodor") || customer.equals("expob")){
           MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver,wait);
           meetingPageEdit.getInQuestion("Question Created From Meeting");
           meetingPageEdit.goToMaterialsTab();
           //Тут нажать кнопку выписка
           meetingPageEdit.createExtract();

           Assert.assertTrue(meetingPageEdit.checkExtract(extract));
           screenShot();
       }else {
           throw new SkipException("Для данного заказчика функционал не доступен");
       }
    }

    @Test
    @Description("Изменение время окончания заседания и проверка что заседание закончено")
    public void openKSAndFinishMeetingTest() throws InterruptedException {

        MeetingsPage meetingsPage = new MeetingsPage(driver, wait);
        MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver, wait);
        if(customer.equals("rico") || customer.equals("rshb") || customer.equals("mmk") || customer.equals("avtodor") || customer.equals("expob")){
            meetingPageEdit.backToMeeting();
        }
        if(customer.equals("mmk") || customer.equals("dkudzo") || customer.equals("avtodor")){
            meetingPageEdit.endMeeting();
        }else{
            SimpleDateFormat formatter= new SimpleDateFormat("HH:mm");
//             Date date = new Date(System.currentTimeMillis()+3600000*4 + 60000);
            Date date = new Date(System.currentTimeMillis() + 60000);
            fromTimeMain = formatter.format(date);

            meetingPageEdit.setEndDate(fromTimeMain);
        }
        meetingPageEdit.saveButtonClick();
        meetingPageEdit.overlayGone();

        sleep(2000);
        meetingPageEdit.backToCalendarClick();
        sleep(1000);
        meetingsPage.refresh();
        meetingsPage.waitMeetingsLoad();

        if(customer.equals("dkudzo")){
            Assert.assertEquals(meetingsPage.checkStatus(meetingName, "Активно", customer, meetingNumber), "Завершено Подготовка не запущена");
        }else{
            Assert.assertEquals(meetingsPage.checkStatus(meetingName, "Активно", customer, meetingNumber), "Завершено");
        }
    }

    @Test
    @Description("Переход в Коллегиал и проверка, что заседание завершено и отображается во вкладке 'Прошедшие'")
    public void checkMeetingFinishedInCollegialTest() throws AWTException {
        CollegialSignUp collegialSignUp = new CollegialSignUp(driver, wait);
        collegialSignUp.goToAnotherTab();

        CollegialMeetingsPage collegialMeetingsPage = new CollegialMeetingsPage(driver, wait);
        collegialMeetingsPage.goToMainPage();
        collegialMeetingsPage.goToPastMeetings();
        collegialMeetingsPage.refresh();
        collegialMeetingsPage.preloaderGone();

        Assert.assertTrue(collegialMeetingsPage.checkEndMeeting(meetingName, customer,meetingNumber));
    }

    @Test
    @Description("Переход в прошедшее заседание и проверка расположения страницы")
    public void checkResultsInPastMeetingTest(){
        CollegialMeetingsPage collegialMeetingsPage = new CollegialMeetingsPage(driver, wait);
        collegialMeetingsPage.getInPastMeeting(meetingName, customer, meetingNumber);

        CollegialMeetingCardPage collegialMeetingCardPage = new CollegialMeetingCardPage(driver, wait);
        if(customer.equals("alrosa")){
            collegialMeetingCardPage.nextQuestion();
            collegialMeetingCardPage.nextQuestion();
            Assert.assertTrue(collegialMeetingCardPage.isOnResultsPageInPastMeeting());

        }else{
            Assert.assertTrue(collegialMeetingCardPage.isOnResultsPageInPastMeeting());
        }
        collegialMeetingCardPage.refresh();
        collegialMeetingCardPage.preloaderGone();
    }

    @Test
    @Description("Проверка отсутсвия возможности голосовать")
    public void checkVotePossibilityTest(){
        CollegialMeetingCardPage collegialMeetingCardPage = new CollegialMeetingCardPage(driver, wait);
        collegialMeetingCardPage.nextQuestion();
        if(customer.equals("avtodor") || customer.equals("suek")){
            collegialMeetingCardPage.nextQuestion();
        }
        try{
            collegialMeetingCardPage.vote("No", customer);
        }catch (TimeoutException te){
            System.out.println("НЕТ ВОЗМОЖНОСТИ ГОЛОСОВАТЬ");
        }
    }

    @Test
    @Description("Переход в завершенное заседание, проверка результатов голосования")
    public void checkVoteResultsInKSTest() throws InterruptedException {

        MeetingsPage meetingsPage = new MeetingsPage(driver, wait);
        meetingsPage.goToAnotherTab();
        meetingsPage.clickRightMeeting(meetingName, customer, meetingNumber);

        MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver, wait);
        meetingPageEdit.goToVoteResultsTab();
        screenShot();
        if(customer.equals("suek") || customer.equals("avtodor")){
            Assert.assertTrue(meetingPageEdit.voteResultInFirstSubQuestion(mainUser));
            Assert.assertTrue(meetingPageEdit.voteResultInSecondSubQuestion(mainUser));
            Assert.assertTrue(meetingPageEdit.voteResultInSecondQuestion(mainUser));
        }else if(customer.equals("mmk") || customer.equals("expob")){
            Assert.assertTrue(meetingPageEdit.voteResultsInFirstQuestionMMK(mainUser));
            Assert.assertTrue(meetingPageEdit.voteResultsInSecondQuestionMMK(mainUser));
        }else{
            Assert.assertTrue(meetingPageEdit.voteResultInFirstQuestion(mainUser));
            Assert.assertTrue(meetingPageEdit.voteResultInSecondQuestion(mainUser));
        }
    }

    @Test
    @Description("Проверка что особое мнение отображается в результате голосования")
    public void checkSpecialOpinionTest(){
        MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver, wait);
        if(customer.equals("suek") || customer.equals("dkudzo") || customer.equals("rtk") || customer.equals("expob")){
            Assert.assertTrue(meetingPageEdit.checkSpecialOpinion("SPECIAL OPINION TEST", mainUser, customer));
        }else{
            throw new SkipException("Для данного заказчика функционал не доступен");
        }
    }

    @Test
    @Description("Формирование протокола и проверка что он отображается")
    public void createProtocolTest(){
        MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver, wait);
        if(customer.equals("suek") || customer.equals("rtk") || customer.equals("dkudzo") || customer.equals("expob")){
            meetingPageEdit.closeOpinionModal();
        }
        meetingPageEdit.createProtocol();
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        screenShot();
        meetingPageEdit.refresh();
        meetingPageEdit.overlayGone();
        meetingPageEdit.goToMaterialsTabInMeeting();
        Assert.assertTrue(meetingPageEdit.protocolConverted(protocol));
    }

    @Test
    @Description("ПМ отображается в результатах голосования")
    public void pmDisplayedTest(){
        if(customer.equals("expod")){
        MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver,wait);
        Assert.assertTrue(meetingPageEdit.pmIsDisplayed(pm,mainUser));
        }else {
            throw new SkipException("Для данного заказчика функционал не доступен");
        }
    }

    @Test
    @Description("Формирование единого пакета документов")
    public void createUnitedDocumentsPackageTest(){
        MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver, wait);
        meetingPageEdit.createUDP();
//        meetingPageEdit.waitArticle();
        meetingPageEdit.preloaderGone();
        try {
            sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        meetingPageEdit.waitArticle();
        meetingPageEdit.refresh();
        meetingPageEdit.goToMaterialsTabInMeeting();
        Assert.assertTrue(meetingPageEdit.checkUDP());
        //нажимается кнопка Сформировать пакет документов
    }
    @Test
    @Description("Коммерческая тайна")
    public void tradeSecretTest(){
        if(customer.equals("alrosa")){
            MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver,wait);
            String[] paths = {

                    "C:\\TestData\\Attach\\Тайна.docx"
            };
            meetingPageEdit.addTradeSecret(paths);
            meetingPageEdit.addPasswordToTradeSecret("P@ssw0rd");
            meetingPageEdit.saveButtonClick();
            meetingPageEdit.preloaderGone();
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Assert.assertFalse(meetingPageEdit.checkEveryStatus().contains("Error"));
            Assert.assertTrue(meetingPageEdit.isTradeSecretAdded());


        }else{
            throw new SkipException("Для данного заказчика функционал не доступен");
        }
    }


    @Test
    @Description("Присутствие участников ММК")
    public void membersPresenceTest(){

       if(customer.equals("mmk")){
           MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver,wait);
           meetingPageEdit.goToParticipant();

           Assert.assertTrue(meetingPageEdit.checkPresence());
           Assert.assertFalse(meetingPageEdit.uncheckedPresence());
           meetingPageEdit.saveButtonClick();
           screenShot();
       }else {
           throw new SkipException("Для данного заказчика функционал не доступен");
       }
    }

    @Test
    @Description("Создать решение по вопросу в прошедшем заседании")
    public void decisionTest(){
        if(customer.equals("rico") || customer.equals("dkudzo")){
            MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver,wait);
            meetingPageEdit.goToAgenda();

            meetingPageEdit.getInQuestion("Question Created From Meeting");
            meetingPageEdit.preloaderGone();
            QuestionPageEdit questionPageEdit = new QuestionPageEdit(driver,wait);
            questionPageEdit.goToDecision();
            if(customer.equals("rico")){
                questionPageEdit.createDecision("TEST DECISION OF QUESTION", "pimenov_test1 test");
            }else{
                questionPageEdit.createDecision("TEST DECISION OF QUESTION", "Пименов Первый");
            }
            Assert.assertEquals(questionPageEdit.getDecisionName(),"TEST DECISION OF QUESTION");
        }else{
            throw new SkipException("Для данного заказчика функционал не доступен");
        }
    }

    @Test
    @Description("Удаление решение")
    public void deleteDecisionTest(){
        if(customer.equals("rico")){
            QuestionPageEdit questionPageEdit = new QuestionPageEdit(driver,wait);
            questionPageEdit.deleteDecision();
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Assert.assertFalse(questionPageEdit.checkDecision());
        }else{
            throw new SkipException("Для данного заказчика функционал не доступен");
        }
    }
    @Test
    @Description("Делегирование в завершенном заседании")
    public void delegationInEndedMeetingTest(){
        if(customer.equals("mmk") || customer.equals("avtodor") || customer.equals("rtk")) {

            MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver, wait);
            meetingPageEdit.goToAgenda();
            meetingPageEdit.getInQuestion("Question Created From Meeting");

            QuestionPageEdit questionPageEdit = new QuestionPageEdit(driver, wait);
            questionPageEdit.goToDelegation();

            questionPageEdit.delegateVoteForUser(mainUser, "Воздержался");
            questionPageEdit.refresh();
            questionPageEdit.goToDelegation();
            Assert.assertTrue(questionPageEdit.checkDelegateVote(mainUser,"Воздержался"));
        }else {
            throw new SkipException("Для данного заказчика функционал не доступен");
        }
    }

    @Test
    @Description("Проверка что голоса отображаются после делегирования в результатах голосования")
    public void checkVotesAfterDelegationTest(){
        if(customer.equals("avtodor") || customer.equals("rtk")) {
            MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver, wait);
            meetingPageEdit.backToMeeting();

            meetingPageEdit.goToVoteResultsTab();
            Assert.assertTrue(meetingPageEdit.getVoteResultForQuestion("Воздержался - 3"));

        }else {
            throw new SkipException("Для данного заказчика функционал не доступен");
        }

    }

//    @Test
//    @Description("Добавить папку в библиотеку")
//    public void addFolderInLibraryTest(){
//        LibraryPage libraryPage = new LibraryPage(driver,wait);
//        libraryPage.goToLibrary();
//        packageTitle = "Auto Folder " + id;
//        libraryPage.addFolderInMainFolder(packageTitle);
//        Assert.assertTrue(libraryPage.isAddedFolder(packageTitle));
//    }
//
//    @Test
//    @Description("Добавить документ в папку")
//    public void addDocumentInFolderTest(){
//        LibraryPage libraryPage = new LibraryPage(driver,wait);
//        String[] paths = {
//                "C:\\Users\\Pimenov.Alex\\Desktop\\Auto\\test_automation_web\\src\\test\\java\\TestData\\Attach\\Документ библиотеки.docx"
//        };
//        libraryPage.addDocumentIntoFolder(paths);
//        libraryPage.save();
//    }
//
//    @Test
//    @Description
//    public void setAccessForDocumentTest(){
//
//    }
//
//    @Test
//    @Description
//    public void checkAccessInCollegial(){
//
//    }
//
//    @Test
//    @Description
//    public void deleteDocumentFromLibraryTest(){
//
//    }
//
//    @Test
//    @Description
//    public void deleteFolderFromLibraryTest(){
//
//    }

    @Test
    @Description("Убрать галочку рекомендаций")
    public void disableRecommendationsTest(){
        if(customer.equals("dkudzo")){
            MainPage mainPage = new MainPage(driver, wait);
            mainPage.goToDirectories();
            EmployeesPage employeesPage = new EmployeesPage(driver, wait);
            employeesPage.openUserForEdit(mainUser);
            employeesPage.enableRecommendationsVisibility();
            Assert.assertFalse(employeesPage.isRecommendationEnabled());

        }else {
            throw new SkipException("Для данного заказчика функционал не доступен");
        }
    }

    @Test
    @Description("Удаление созданного юзера в КС")
    public void deleteCreatedUser(){

//        if(customer.equals("mmk")){
//            MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver,wait);
//            meetingPageEdit.saveButtonClick();
//            meetingPageEdit.preloaderGone();
//            try {
//                sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
        if(customer.equals("dkudzo")){
            EmployeesPage employeesPage = new EmployeesPage(driver, wait);
            employeesPage.saveChanges();
        }
        MainPage mainPage = new MainPage(driver, wait);
        mainPage.goToDirectories();

        EmployeesPage employeesPage = new EmployeesPage(driver, wait);
        employeesPage.deleteUser(userName);
        employeesPage.refresh();
    }

    @Test
    @Description("Удаление созданного юзера для добавления из КО в КС")
    public void deleteCreatedUserFromKO(){
        if(!customer.equals("expob") || !customer.equals("nspk")){
        EmployeesPage employeesPage = new EmployeesPage(driver, wait);
        employeesPage.deleteUser(userSecondName);
        employeesPage.refresh();}else{
            throw new SkipException("Для данного заказчика функционал не доступен");
        }
    }

    @Test
    @Description("Удаление созданного предприятия")
    public void deleteDivision(){

        CompaniesPage companiesPage = new CompaniesPage(driver, wait);
        companiesPage.goToCompanies();
        companiesPage.deleteCompany(companyName);
    }


//    @Test
//    @Description("Залогиниться в КС под созданным пользователем")
//    public void setPasswordTestAndLogInTest() throws InterruptedException {
//        MainPage mainPage = new MainPage(driver, wait);
////        mainPage.logOut();
//        SignUpPage signUpPage = new SignUpPage(driver, wait);
//        if(customer.equals("rshb") || customer.equals("test")){
//            driver.get("https://mail.digdes.com/owa");
//            EmailPage emailPage = new EmailPage(driver, wait);
//            emailPage.logIn("digdes\\pimenov_test4", "P@ssw0rd");
//            emailPage.emailIsLoaded();
//            emailPage.getInEmail();
//            emailPage.goToSetPasswordPage();
//            SetPasswordPage setPasswordPage = new SetPasswordPage(driver, wait);
//            setPasswordPage.setPassword(userPass);
//            driver.get(getCustomerUrl(customer));
//            mainPage.logOut();
//        }
//        signUpPage.isLoaded();
//        signUpPage.logIn(userLogin, userPass);
//        if(customer.equals("rshb")){
//            Assert.assertEquals(mainPage.checkUser(), userName);
//        }else{
//            Assert.assertEquals(mainPage.checkUser(), "Automation U.");
//        }
//    }
            @AfterClass
        public void shotDown(){
//        Capabilities caps = ((RemoteWebDriver)driver).getCapabilities();
//        String browserVersion = caps.getVersion();
//        String browser = caps.getBrowserName();
//        writeVersionInReport(browserVersion, browser);

        if (driver != null)
            driver.quit();
    }
}
