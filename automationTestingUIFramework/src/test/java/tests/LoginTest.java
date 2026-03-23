package tests;

import core.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    private LoginPage loginPage;
    private InventoryPage inventoryPage;

    @BeforeMethod
    public void initPages() {
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
    }

    @Test
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
    public void testLoginWithInvalidPassword() {
        loginPage
                .login(
                        "standard_user",
                        "wrong_password"
                );
        Assert.assertTrue(
                loginPage
                        .getErrorMessage()
                        .contains("Username and password do not match")
        );
    }

    @Test
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
    public void testLoginWithEmptyFields() {
        loginPage.clickLogin();
        Assert.assertTrue(
                loginPage
                        .getErrorMessage()
                        .contains("Username is required")
        );
    }

    @Test
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

