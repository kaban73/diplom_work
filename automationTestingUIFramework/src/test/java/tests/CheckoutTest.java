package tests;

import core.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;

@Epic("Checkout")
@Feature("Order Processing")
public class CheckoutTest extends BaseTest {

    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CartPage cartPage;
    private CheckoutStepOnePage checkoutStepOnePage;
    private CheckoutOverviewPage checkoutOverviewPage;
    private CheckoutCompletePage checkoutCompletePage;

    @BeforeMethod
    public void initPages() {
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        cartPage = new CartPage(driver);
        checkoutStepOnePage = new CheckoutStepOnePage(driver);
        checkoutOverviewPage = new CheckoutOverviewPage(driver);
        checkoutCompletePage = new CheckoutCompletePage(driver);

        // логин + добавление товара (общая предусловие)
        loginPage.login("standard_user", "secret_sauce");
        inventoryPage.addFirstProductToCart();
    }

    // =========================
    // TC-CH-01
    // =========================
    @Test
    @Story("Navigate to checkout")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Переход к оформлению заказа")
    public void testNavigateToCheckout() {

        inventoryPage.openCart();
        cartPage.clickCheckout();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("checkout-step-one"),
                "Checkout page is not opened"
        );
    }

    // =========================
    // TC-CH-02
    // =========================
    @Test
    @Story("Checkout with valid data")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Заполнение формы валидными данными")
    public void testCheckoutWithValidData() {

        inventoryPage.openCart();
        cartPage.clickCheckout();

        checkoutStepOnePage.fillForm("Ivan", "Ivanov", "123456");
        checkoutStepOnePage.clickContinue();

        Assert.assertTrue(
                checkoutOverviewPage.isOpened(),
                "Did not navigate to overview page"
        );
    }

    // =========================
    // TC-CH-03
    // =========================
    @Test
    @Story("Checkout with empty fields")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Попытка оформления заказа с пустыми полями")
    public void testCheckoutWithEmptyFields() {

        inventoryPage.openCart();
        cartPage.clickCheckout();

        checkoutStepOnePage.clickContinue();

        Assert.assertTrue(
                checkoutStepOnePage.getErrorMessage().contains("Error"),
                "Error message is not displayed"
        );
    }

    // =========================
    // TC-CH-04
    // =========================
    @Test
    @Story("Checkout error message validation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверка текста ошибки")
    public void testCheckoutErrorMessage() {

        inventoryPage.openCart();
        cartPage.clickCheckout();

        checkoutStepOnePage.clickContinue();

        Assert.assertEquals(
                checkoutStepOnePage.getErrorMessage(),
                "Error: First Name is required"
        );
    }

    // =========================
    // TC-CH-05
    // =========================
    @Test(groups = {"smoke"})
    @Story("Successful checkout")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Успешное завершение оформления заказа")
    public void testSuccessfulCheckout() {

        inventoryPage.openCart();
        cartPage.clickCheckout();

        checkoutStepOnePage.fillForm("Ivan", "Ivanov", "123456");
        checkoutStepOnePage.clickContinue();

        checkoutOverviewPage.clickFinish();

        Assert.assertTrue(
                checkoutCompletePage.isOrderComplete(),
                "Order was not completed"
        );
    }

    // =========================
    // TC-CH-06
    // =========================
    @Test
    @Story("Order confirmation page")
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверка страницы подтверждения заказа")
    public void testOrderConfirmationPage() {

        inventoryPage.openCart();
        cartPage.clickCheckout();

        checkoutStepOnePage.fillForm("Ivan", "Ivanov", "123456");
        checkoutStepOnePage.clickContinue();

        checkoutOverviewPage.clickFinish();

        Assert.assertEquals(
                checkoutCompletePage.getSuccessMessage(),
                "THANK YOU FOR YOUR ORDER"
        );

        Assert.assertTrue(
                checkoutCompletePage.getCurrentUrl().contains("checkout-complete"),
                "URL is incorrect"
        );
    }
}

