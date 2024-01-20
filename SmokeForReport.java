package scenarios;

import PageObject.*;
import PageObject.Directories.CollegialBodiesPage;
import PageObject.Directories.CompaniesPage;
import PageObject.Directories.EmployeesPage;
import PageObject.SetPasswordFeature.EmailPage;
import PageObject.SetPasswordFeature.SetPasswordPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.*;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.annotations.Test;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;


import static java.lang.Thread.sleep;
import static org.testng.Assert.assertEquals;

public class SmokeForReport extends ConfigHelper {

    String fromTimeMain; //Время ОТ, передаяется во время создания заседания, используются для нахождения созданного заседания
    String currentDate;
    WebDriver driver;
    WebDriverWait wait;
    String companyName;
    String collegialDepartment;
    Boolean right = true;
    String materialsPath;
    String customer;
    String meetingNumber;
    String meetingName;
    String userName;
    String userPass;
    String userLogin;
    String protocol;

    String id;
    String company;
    String mainUser = "AutoUser AutoUser AutoUser";
    String post = "ул Автоматизаторов д 1349";
    String legalPost = "г Москва, ул Автоматизаторов д 1349, 565656";
    String modifiedCompany = " на Ареопаде";
    String kO = "Automation KO";
    String modifiedKO = "Измененое АКО";
    String cbInTable;

    int questionsSizeOnQuestionsPage;
    int questionsSizeInAgenda;
    int materialsAmountInQuestionFromMainPage = 5;
    int materialsAmountInNewQuestion = 3;
    int i = 0;

    @BeforeSuite
    public static void setDriver(){
        WebDriverManager.chromedriver().setup();
//        WebDriverManager.firefoxdriver().setup();
    }

    @BeforeClass
    public void setUp(){
        driver = new ChromeDriver();
//        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        driver.manage().window().maximize();
        customer = getConfig("customer");
        userName = getConfig("userName");
        userPass = getConfig("userPass");
        userLogin = getConfig("userLogin");

        protocol = getConfig(customer +".protocol");
        System.out.println(protocol);

        driver.get(getCustomerUrl(customer));

        company = getConfig("division");
        collegialDepartment = getConfig("collegial");
        materialsPath = getConfig("materialsPath");

        SignUpPage signUpPage = new SignUpPage(driver, wait);
        signUpPage.logIn(getConfig("login"),
                            getConfig("password"));

    }
    @AfterMethod
    public void onTestFailure(ITestResult tr) {
        if(!tr.isSuccess()){
            makeScreenshot();
        }
    }

    @Attachment(value = "Attachment Screenshot", type = "image/png")
    public byte[] makeScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Test
    @Description("Создание предприятие, проверка его добавления, изменение имени предприяти и проверка что изменения применены")
    public void newCompanyCreationAndCheckTest() throws InterruptedException {
        MainPage mainPage = new MainPage(driver, wait);
        mainPage.goToDirectories();

        CompaniesPage companiesPage = new CompaniesPage(driver, wait);
        companiesPage.goToCompanies();
        mainPage.overlayGone();
        companiesPage.listOfEntitiesIsLoaded();
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat formatterData= new SimpleDateFormat("dd.MM.yyyy'T'HH:mm");
        id = formatterData.format(date);
        companyName = "Для Автотеста" + id;
        companiesPage.addCompany(companyName, companyName + "на тестовом стенде", post, legalPost, customer);
        companiesPage.isAddedCompanyDisplayed(companyName);
        companiesPage.getInCompany(companyName);
        companiesPage.editShortName(modifiedCompany);
        companiesPage.save();
        companyName = companyName + modifiedCompany;
        companiesPage.isAddedCompanyDisplayed(companyName);

//        Assert.assertEquals(directoriesPage.getInCompany("ЗАО \"Диджитал Дизайн\""), "ЗАО \"Диджитал Дизайн\"");
    }

    @Test
    @Description("Создание КО, проверка его добавления")
    public void cBCreationAndCheckTest() throws InterruptedException {
        MainPage mainPage = new MainPage(driver, wait);
        mainPage.goToDirectories();
        mainPage.overlayGone();

        CollegialBodiesPage collegialBodiesPage = new CollegialBodiesPage(driver, wait);
        collegialBodiesPage.goToCollegialBody();
        collegialBodiesPage.listOfEntitiesIsLoaded();

        collegialBodiesPage.createKO(customer,companyName, "Простое большинство", kO, "Что то там","Юридическая информация" );
        mainPage.overlayGone();
        cbInTable = collegialBodiesPage.cbNameInTable(companyName, kO);
        Assert.assertTrue(collegialBodiesPage.isAddedCollegialBodyDisplayed(cbInTable));

    }

    @Test
    @Description("Изменение имени КО и проверка что изменения применены")
    public void cBEditAndCheckTest(){
        CollegialBodiesPage collegialBodiesPage = new CollegialBodiesPage(driver, wait);
        collegialBodiesPage.getInCollegialBody(cbInTable);
        collegialBodiesPage.editCBName(modifiedKO, customer);
        collegialBodiesPage.editRule("Не установлены");
        collegialBodiesPage.save();
        cbInTable = collegialBodiesPage.cbNameInTable(companyName, modifiedKO);
        Assert.assertTrue(collegialBodiesPage.isAddedCollegialBodyDisplayed(cbInTable));
        if(customer.equals("test")){
            collegialBodiesPage.getInCollegialBody(cbInTable);
            collegialBodiesPage.goToQuestionTypes();
            collegialBodiesPage.setRuleInQuestionType("Новый тип","Простое большинство");
            makeScreenshot();
            collegialBodiesPage.save();
        }
        collegialBodiesPage.getInCollegialBody(cbInTable);
        collegialBodiesPage.goToTemplates();
        collegialBodiesPage.fillIntramuralProtocolField(protocol);
        collegialBodiesPage.save();
    }

    @Test

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
        employeesPage.fillUser(userLogin, userName, userPass, companyName,
                modifiedKO,"pimenov_test4@digdes.com", customer);
        employeesPage.isAddedEmployeeDisplayed(userName);
    }

    @Test
    public void addMainUserInTestCB() throws InterruptedException {
        EmployeesPage employeesPage = new EmployeesPage(driver, wait);
        employeesPage.listOfEntitiesIsLoaded();
        employeesPage.openUserForEdit(mainUser);
        employeesPage.addSecretaryRoleInCB( companyName, modifiedKO);
        Assert.assertTrue(employeesPage.checkDirectorsRole(cbInTable, "Секретарь"));
        employeesPage.saveChanges();
    }

    @Test
    @Description("Открытие модального окна создания вопроса с главной страницы и  добавление докладчика")
    public void creationQuestionFromMainPageAndAddSpeakerTest() throws InterruptedException {

        MainPage mainPage = new MainPage(driver, wait);
        mainPage.pageLoadWait();
        mainPage.goToQuestions();

        QuestionsPage questionsPage = new QuestionsPage(driver, wait);
        Assert.assertEquals(questionsPage.pageIsLoaded(), right);
        questionsSizeOnQuestionsPage = questionsPage.getQuestionsSize();

        mainPage.addQuestion();

        CreateNewQuestionPage createNewQuestionPage = new CreateNewQuestionPage(driver, wait);
        Date questionDate = new Date(System.currentTimeMillis());

        createNewQuestionPage.fillQuestionForm("Autotest"+questionDate,
                companyName,
                modifiedKO,
                "Automation test project decision for dev", customer);
        if(customer.equals("test") ){
            createNewQuestionPage.fillQuestionType("Новый тип");
        }else if(customer.equals("suek")){
            createNewQuestionPage.fillQuestionType("Договоры");
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
        createNewQuestionPage.goToMaterials();
        createNewQuestionPage.addMaterial(materialsPath + "DOC.doc");
        createNewQuestionPage.addMaterial(materialsPath + "DOCX.docx");
        createNewQuestionPage.addMaterial(materialsPath + "XLS.xls");
        createNewQuestionPage.addMaterial(materialsPath + "PPT.ppt");
        createNewQuestionPage.addMaterial(materialsPath + "PDF.pdf");
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

        MainPage mainPage = new MainPage(driver,wait);
        mainPage.overlayGone();
        mainPage.goToQuestions();
        mainPage.overlayGone();

        QuestionsPage questionsPage = new QuestionsPage(driver, wait);
        Assert.assertEquals(questionsPage.pageIsLoaded(), right);

        int questionsSizeAfter = questionsPage.getQuestionsSize();
        sleep(1000);
        Assert.assertEquals(questionsSizeAfter, questionsSizeOnQuestionsPage + 1);
        sleep(10000);
    }
    @Test
    @Description("Переход в созданный вопрос, проверка что докладчики, материалы добавлены и сконвертированы")
    public void isSpeakerAndMaterialsAddedAndConvertedTest(){

        QuestionsPage questionsPage = new QuestionsPage(driver,wait);
        questionsPage.getInLastAddedQuestion();
        MainPage mainPage = new MainPage(driver, wait);
        QuestionPageEdit questionPageEdit = new QuestionPageEdit(driver, wait);
        mainPage.overlayGone();
        questionPageEdit.questionPageEditIsLoaded();
        questionPageEdit.goToSpeakers();
        Assert.assertEquals(questionPageEdit.getSpeakersSize().size(), 1);
        makeScreenshot();

        questionPageEdit.goToMaterials();
        mainPage.overlayGone();

        int visibleMaterials = questionPageEdit.materialsSize();
        //Проверка кновертации
//        Assert.assertTrue(questionPageEdit.cheackConvertation(materialsAmountInQuestionFromMainPage));
        questionPageEdit.scroll(250);
        makeScreenshot();
        Assert.assertFalse(questionPageEdit.checkEveryStatus().contains("Error"));


        Assert.assertEquals(materialsAmountInQuestionFromMainPage, visibleMaterials);
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

        CreateNewMeetingPage createNewMeetingPage = new CreateNewMeetingPage(driver, wait);
        createNewMeetingPage.waitMeetingCreatModal();

        //Задаем время
        SimpleDateFormat formatter= new SimpleDateFormat("HH:mm");
        Date date = new Date(System.currentTimeMillis() + 4*3600000);
        fromTimeMain = formatter.format(date);

        long curTimeInMs = date.getTime();
        Date afterAddingMins = new Date(curTimeInMs + 4*3600000 + 120000*7);
        String toTime = formatter.format(afterAddingMins);

        SimpleDateFormat formatterData= new SimpleDateFormat("dd.MM.yyyy");
        currentDate = formatterData.format(date);

        createNewMeetingPage.waitMeetingCreatModal();
        createNewMeetingPage.clearFromTime();
        createNewMeetingPage.startDate(fromTimeMain);
        createNewMeetingPage.clearToTime();
        createNewMeetingPage.finishDate(toTime);

        createNewMeetingPage.fillMeeting(companyName, modifiedKO,meetingName, "Место проведения для автотестов", customer);

        if(customer.equals("avtodor") || customer.equals("dkudzo")){
            Assert.assertTrue(createNewMeetingPage.isCheckboxChecked());
            createNewMeetingPage.disableEndManually();
        }
        if(customer.equals("avtodor")){
            meetingNumber = createNewMeetingPage.getMeetingNumber();
            System.out.println("Meeting number " + meetingNumber);
        }

        createNewMeetingPage.submitMeetingCreation();

        sleep(1000);
        mainPage.overlayGone();
        mainPage.goToMeetings();
        mainPage.overlayGone();
        sleep(1000);
        int meetingsSizeAfter = meetingsPage.getMeetingsSize();

        assertEquals(meetingsSizeBefore + 1, meetingsSizeAfter);
    }
    @Test
    @Description("Переход в созданное заседание, добавление существующего вопроса и проверка его добавления в повестку")
    public void addCreatedQuestionInCreatedMeetingTest() throws InterruptedException {

        MeetingsPage meetingsPage = new MeetingsPage(driver, wait);
        meetingName = meetingsPage.meetingName(fromTimeMain, currentDate, companyName, modifiedKO);
        System.out.println(meetingName);
        meetingsPage.clickRightMeeting(meetingName, customer, meetingNumber);

        MainPage mainPage = new MainPage(driver, wait);
        mainPage.overlayGone();

        MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver, wait);
        meetingPageEdit.addExistQuestion();

        sleep(1000);
        int afterAdd = meetingPageEdit.questionsCount();
        Assert.assertEquals(1, afterAdd);
        meetingPageEdit.scroll(250);
        makeScreenshot();
    }
    @Test
    @Description("Создание нового вопроса, добавление в него докладчика")
    public void creationNewQuestionWithMaterialsAndSpeakerTest() throws InterruptedException {

        MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver, wait);

        questionsSizeInAgenda = meetingPageEdit.questionsCount();
        meetingPageEdit.chooseNewQuestion();
        meetingPageEdit.fillMainQuestionData("Question Created From Meeting",
                "Decision for question",customer);

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
            meetingPageEdit.addSpeaker("Pimenov Aleksandr", "TEST",customer);
        }
    }
    @Test
    @Description("Добавление материалов в новый вопрос и проверка, что материалы отображаются при добавлении")
    public void addMaterialsInNewQuestionTest(){
        MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver, wait);
        meetingPageEdit.addMaterial(materialsPath + "DOC.doc");
        meetingPageEdit.addMaterial(materialsPath + "XLS.xls");
        meetingPageEdit.addMaterial(materialsPath + "PDF.pdf");
//        meetingPageEdit.addMaterial("Размеры\\14-mb.doc");
        int materialsBeforeAdd = meetingPageEdit.getMaterialsSize();
        Assert.assertEquals(materialsBeforeAdd, materialsAmountInNewQuestion);
    }

    @Test
    @Description("Добавление вопроса и проверка что вопрос отображается в повестке")
    public void addNewQuestionAndCheckTest() throws InterruptedException {
        MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver, wait);
        meetingPageEdit.submitQuestionCreation();

        sleep(1000);

        MainPage mainPage = new MainPage(driver, wait);
        mainPage.overlayGone();
        meetingPageEdit.downScroll();
        meetingPageEdit.isVisibleSecondQuestion();
        int afterAdd = meetingPageEdit.questionsCount();

        Assert.assertEquals(afterAdd, questionsSizeInAgenda + 1);//
        makeScreenshot();// проверка что вопрос создан
    }

    @Test
    @Description("Переход в новый вопрос, проверка что докладчик, материалы добавлены и сконвертированы")
    public void isSpeakerAndMaterialsAddedAndConvertedInNewQuestionTest(){

        MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver, wait);
        meetingPageEdit.goToLastQuestion();
        meetingPageEdit.questionPageIsLoaded();
        MainPage mainPage = new MainPage(driver, wait);
        mainPage.overlayGone();
        meetingPageEdit.goToSpeakers(customer);
        Assert.assertEquals(1, meetingPageEdit.getSpeakersSize().size());
        makeScreenshot();

        meetingPageEdit.goToMaterialsTab();
        Assert.assertEquals(meetingPageEdit.pageIsLoaded(), right);
        int materialsAfterAdd = meetingPageEdit.getMaterialsSize();
        Assert.assertEquals(materialsAmountInNewQuestion, materialsAfterAdd);

        QuestionPageEdit questionPageEdit = new QuestionPageEdit(driver, wait);
//        Assert.assertTrue(questionPageEdit.cheackConvertation(materialsAfterAdd));
//        meetingPageEdit.upScroll();
        Assert.assertFalse(questionPageEdit.checkEveryStatus().contains("Error"));
        questionPageEdit.scroll(250);
        makeScreenshot();

    }
    @Flaky
    @Test
    @Description("Утверждение заседание и ожидание его перехода в статус 'Завершено'")
    public void approveAndWaitUntilTheEndTest() throws InterruptedException {

        MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver, wait);
        meetingPageEdit.backToMeeting();
        meetingPageEdit.approveClick();
        if(customer.equals("chtpz") || customer.equals("test")){
            meetingPageEdit.saveChooseMaterialsWindowClick();
        }

        meetingPageEdit.saveButtonClick();
        sleep(1000);
        MainPage mainPage  = new MainPage(driver, wait);
        mainPage.overlayGone();
        meetingPageEdit.saveMessageGone();
        sleep(1000);
        meetingPageEdit.backToCalendarClick();
        sleep(1000);
        mainPage.overlayGone();

        MeetingsPage meetingsPage = new MeetingsPage(driver, wait);
        meetingsPage.meetingPageRefresh();
        if(customer.equals("dkudzo")){
            Assert.assertEquals(meetingsPage.checkStatus(meetingName, "Активно", customer, meetingNumber), "Завершено\nПодготовка не запущена");
        }else{
            Assert.assertEquals(meetingsPage.checkStatus(meetingName, "Активно", customer, meetingNumber), "Завершено");
        }
    }

    @Test
    @Description("Переход в завершенное заседание, формирование протокола и проверка что он сконвертировался")
    public void createProtocolAndCheckItTest() throws InterruptedException {
        MeetingsPage meetingsPage = new MeetingsPage(driver, wait);
        meetingsPage.clickRightMeeting(meetingName, customer, meetingNumber);

        MeetingPageEdit meetingPageEdit = new MeetingPageEdit(driver, wait);

        meetingPageEdit.goToVoteResultsTab();
        meetingPageEdit.overlayGone();
        meetingPageEdit.createProtocol();
        sleep(2000);
        meetingPageEdit.overlayGone();

        meetingPageEdit.goToMaterialsTabInMeeting();

        Assert.assertTrue(meetingPageEdit.protocolConverted(protocol));
        makeScreenshot();
    }

    @Test
    public void setPasswordTestAndLogInTest() throws InterruptedException {
        MainPage mainPage = new MainPage(driver, wait);
//        mainPage.logOut();
        SignUpPage signUpPage = new SignUpPage(driver, wait);
        if(customer.equals("rshb") || customer.equals("test")){
            driver.get("https://mail.digdes.com/owa");
            EmailPage emailPage = new EmailPage(driver, wait);
            emailPage.logIn("digdes\\pimenov_test4", "P@ssw0rd");
            emailPage.emailIsLoaded();
            emailPage.getInEmail();
            emailPage.goToSetPasswordPage();
            SetPasswordPage setPasswordPage = new SetPasswordPage(driver, wait);
            setPasswordPage.setPassword(userPass);
            driver.get(getCustomerUrl(customer));
            mainPage.logOut();
        }
        signUpPage.isLoaded();
        signUpPage.logIn(userLogin, userPass);
        if(customer.equals("rshb")){
            Assert.assertEquals(mainPage.checkUser(), userName);
        }else{
            Assert.assertEquals(mainPage.checkUser(), "Automation U.");
        }

    }

            @AfterClass
        public void shotDown(){
        //Capabilities caps = ((RemoteWebDriver)driver).getCapabilities();
        //String browserVersion = caps.getVersion();
        //String browser = caps.getBrowserName();
        //writeVersionInReport(browserVersion, browser);

        if (driver != null)
            driver.quit();
    }
}
