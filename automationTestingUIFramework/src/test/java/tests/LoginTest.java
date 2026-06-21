package tests;

import core.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;


@Epic("Authentication")
@Feature("Login")
public class LoginTest extends BaseTest {

    private LoginPage loginPage;
    private InventoryPage inventoryPage;

    @BeforeMethod
    public void initPages() {
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
    }

    @Test
    @Story("Successful login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверка успешной авторизации с валидными данными")
    public void testSuccessfulLogin() {
        loginPage.
                login(
                        "standard_user",
                        "secret_sauce"
                );
        Assert.assertTrue(
                        inventoryPage.isOpened(),
                        "Inventory page is not opened"
                );
    }

    @Test
    @Story("Login with invalid password")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Попытка входа с неверным паролем")
    public void testLoginWithInvalidPassword() {
        loginPage
                .login(
                        "standard_user",
                        ""
                );
        Assert.assertTrue(
                loginPage
                        .getErrorMessage()
                        .contains("Epic sadface: Password is required")
        );
    }

    @Test
    @Story("Login with invalid username")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Попытка входа с неверным логином")
    public void testLoginWithInvalidUsername() {
        loginPage.login(
                "wrong_user",
                "secret_sauce"
        );
        Assert.assertTrue(
                loginPage
                        .getErrorMessage()
                        .contains("Username and password do not match")
        );
    }

    @Test
    @Story("Login with empty fields")
    @Severity(SeverityLevel.NORMAL)
    @Description("Попытка входа с пустыми полями")
    public void testLoginWithEmptyFields() {
        loginPage.clickLogin();
        Assert.assertTrue(
                loginPage
                        .getErrorMessage()
                        .contains("Username is required")
        );
    }

    @Test
    @Story("Error message validation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверка точного текста ошибки")
    public void testErrorMessageText() {
        loginPage
                .login(
                        "wrong_user",
                        "wrong_password"
                );
        Assert.assertEquals(
                loginPage.getErrorMessage(),
                "Epic sadface: Username and password do not match any user in this service"
        );
    }

    @Test
    @Story("Locked user login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Попытка входа заблокированного пользователя")
    public void testLoginWithLockedUser() {
        loginPage
                .login(
                        "locked_out_user",
                        "secret_sauce"
                );
        Assert.assertTrue(
                loginPage
                        .getErrorMessage()
                        .contains("Epic sadface: Sorry, this user has been locked out.")
        );
    }
}

