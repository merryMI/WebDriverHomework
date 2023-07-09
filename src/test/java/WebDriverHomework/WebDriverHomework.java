package WebDriverHomework;

import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.By;


import java.time.Duration;

public class WebDriverHomework {
    // 1. Navigate to Login page http://training.skillo-bg.com/
    // 2. Click Login btn.
    // 3. Check that loading page is : http://training.skillo-bg.com/users/login.
    // 4. Check the Registration link that is visible and clickable.
    //5. Check that loading page is: http://training.skillo-bg.com/users/register
    // 6. Check that the "Sign up" header is displayed
    // 7. Enter a username with less than 2 characters. Validate that a red check is displayed after entering the value.
    // 8. Enter email. Validate that a red check is displayed after entering the value.
    // 9. Enter a password with less than 6 characters. Validate that a red check is displayed after entering the value.
    // 10. Enter incorrect confirm password. Validate that a red check is display after entering the value.
    // 11. Click Sign up btn.
    //12. Click Sign with empty form.
    //13. 13. Sending empty form. Validate an unsuccessful pop-up message.
    // 14. Enter registered user. Validate an unsuccessful pop-up message.

    private static WebDriver driver;
    private final String BASE_URL = "http://training.skillo-bg.com/";

    private final String LOGIN_URL = "users/login";
    private final String REGISTER_URL = "users/register";

    @BeforeTest
    public void setup() {
        System.out.println(" 1.Navigate to Login page http://training.skillo-bg.com/");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.get(BASE_URL);


    }


    @Test
    public void signInForm() {
        System.out.println("2. Click Login btn.");
        WebElement loginBtn = driver.findElement(By.id("nav-link-login"));
        Assert.assertTrue(loginBtn.isDisplayed(), "The Login button is not displayed!");
        clickBtn(loginBtn, 3);

        System.out.println("3. Check that loading page is : http://training.skillo-bg.com/users/login.");
        urlToBe(3, LOGIN_URL);

        // 4. Check the Registration link that is visible and clickable.
        System.out.println("4. Check the Registration link that is visible and clickable.");
        WebElement registerBtn = driver.findElement(By.linkText("Register"));
        clickBtn(registerBtn, 3);

        System.out.println("5. Check that loading page is: http://training.skillo-bg.com/users/register");
        urlToBe(3, REGISTER_URL);

        // 6. Check that the "Sign up" header is displayed
        System.out.println("6. Check that the 'Sign up' header is displayed");
        WebElement header = driver.findElement(By.tagName("h4"));
        String actualHeaderText = header.getText();
        Assert.assertEquals(actualHeaderText, "Sign up", "Expected header text to be Sign up, but got " +
                actualHeaderText);

    }

    @DataProvider(name = "incorrectFieldValue")

    public Object[][] incorrectValue() {
        return new Object[][]{
                {"k", "m", "12","54"}
        };
    }
    @Test(dataProvider = "incorrectFieldValue", dependsOnMethods = {"signInForm"})
    public void WebDriverHomework(String username, String email, String pass, String confirmPass) {

        System.out.println("7. Enter a username with less than 2 characters. Validate that a red check is displayed after entering the value.");
        enterUserField(3, username);
        //incorrectMsg();

        System.out.println("8. Enter email. Validate that a red check is displayed after entering the value.");
        emailField(3, email);

        System.out.println("9. Enter a password with less than 6 characters. Validate that a red check is displayed after entering the value.");

        enterPassField(3, pass);

        System.out.println("10. Enter incorrect confirm password. Validate that a red check is display after entering the value.");
        enterConfirmPassField(3, confirmPass);

        System.out.println("11. Click Sign.");
        clickSignInBtn();

    }


    @Test(dependsOnMethods = {"signInForm"})
    public void sendEmptyForm() {

        System.out.println("12. Click Sign with empty form");
        clickSignInBtn();

        System.out.println("13. Sending empty form. Validate an unsuccessful pop-up message.");
        WebElement toastElement = driver.findElement(By.cssSelector(".toast-message"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(toastElement));
        String allertMsg = toastElement.getText().trim();
        Assert.assertEquals(allertMsg, "Registration failed!", "Registration is successful: " + allertMsg);

    }

    @DataProvider(name = "registeredUser")

    public Object[][] dpMethod() {
        return new Object[][]{
                {"merry", "m@abv.bg", "123456"}
        };
    }

    @Test(dataProvider = "registeredUser", dependsOnMethods = {"signInForm"})

    public void sendRegisteredUser(String username, String email, String pass) {
        System.out.println("14.Enter registered user. Validate an unsuccessful pop-up message.");
        enterValidUserField(5, username);
        emailValidField(5, email);
        enterValidPassField(5, pass);
        enterValidConfirmPassField(5, pass);
        clickSignInBtn();
       // WebElement toastElement = driver.findElement(By.id("toast-container"));
        WebElement toastElement = driver.findElement(By.cssSelector(".toast-message"));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(toastElement));
        String allertMsg = toastElement.getText().trim();
        Assert.assertEquals(allertMsg, "Username taken", "Registration is successful: " + allertMsg);

    }


    public void clickBtn(WebElement element, int timeOff) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOff));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    public void urlToBe(int timeOff, String URL) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOff));
        wait.until(ExpectedConditions.urlToBe(BASE_URL + URL));

    }

    public void clickSignInBtn() {
        WebElement signInBtn = driver.findElement(By.id("sign-in-button"));
        clickBtn(signInBtn, 5);
    }

    public void populateFields(WebElement element, int timeOff, String inputValue) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOff));
        wait.until(ExpectedConditions.visibilityOf(element));
        String value = inputValue;
        element.sendKeys(value);
        String redMess = element.getAttribute("class");
        wait.until(ExpectedConditions.attributeToBe(element,"class","form-control ng-untouched ng-invalid is-invalid ng-dirty"));
    }

    public void populateRealValueFields(WebElement element, int timeOff, String inputValue) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOff));
        wait.until(ExpectedConditions.visibilityOf(element));
        String value = inputValue;
        element.sendKeys(value);
        String redMess = element.getAttribute("class");
        wait.until(ExpectedConditions.attributeToBe(element,"class","form-control ng-untouched ng-dirty is-valid ng-valid"));

    }


    public void enterUserField(int timeOff, String userNameFieldValue) {
        WebElement element = driver.findElement(By.cssSelector("input[name='username']"));
        populateFields(element, timeOff, userNameFieldValue);

    }

    public void emailField(int timeOff, String emailFieldValue) {
        WebElement element = driver.findElement(By.cssSelector("input[type='email']"));
        populateFields(element, timeOff, emailFieldValue);
    }
    public void enterPassField(int timeOff, String passFieldValue) {
        WebElement element = driver.findElement(By.cssSelector("input[name='password']"));
        populateFields(element, timeOff, passFieldValue);
    }

    public void enterConfirmPassField(int timeOff, String confirmPassFieldValue) {
        WebElement element = driver.findElement(By.cssSelector("input[name='verify-password']"));
        populateFields(element, timeOff, confirmPassFieldValue);
    }


    public void enterValidUserField(int timeOff, String userNameFieldValue) {
        WebElement element = driver.findElement(By.cssSelector("input[name='username']"));
        populateRealValueFields(element, timeOff, userNameFieldValue);

    }

    public void emailValidField(int timeOff, String emailFieldValue) {
        WebElement element = driver.findElement(By.cssSelector("input[type='email']"));
        populateRealValueFields(element, timeOff, emailFieldValue);
    }
    public void enterValidPassField(int timeOff, String passFieldValue) {
        WebElement element = driver.findElement(By.cssSelector("input[name='password']"));
        populateRealValueFields(element, timeOff, passFieldValue);
    }

    public void enterValidConfirmPassField(int timeOff, String confirmPassFieldValue) {
        WebElement element = driver.findElement(By.cssSelector("input[name='verify-password']"));
        populateRealValueFields(element, timeOff, confirmPassFieldValue);
    }



    @AfterTest

    public void closed() {
        driver.close();
    }

}