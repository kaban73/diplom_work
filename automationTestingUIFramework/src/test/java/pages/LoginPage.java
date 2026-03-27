package pages;

import core.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "user-name")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    @Step("Ввод логина: {username}")
    public void enterUsername(String username) {
        usernameInput.sendKeys(username);
    }

    @Step("Ввод пароля")
    public void enterPassword(String password) {
        passwordInput.sendKeys(password);
    }

    @Step("Нажатие кнопки Login")
    public void clickLogin() {
        loginButton.click();
    }

    @Step("Логин как пользователь: {username}")
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    @Step("Получение текста ошибки")
    public String getErrorMessage() {
        return errorMessage.getText();
    }
}

