package tests;

import core.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.InventoryPage;
import pages.LoginPage;

@Epic("Cart")
@Feature("Shopping Cart")
public class CartTest extends BaseTest {

    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CartPage cartPage;

    @BeforeMethod
    public void initPages() {
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        cartPage = new CartPage(driver);

        // логин перед каждым тестом
        loginPage.login("standard_user", "secret_sauce");
    }

    // =========================
    // TC-CART-01
    // =========================
    @Test(groups = {"smoke"})
    @Story("Add single product to cart")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Добавление одного товара в корзину")
    public void testAddSingleProductToCart() {

        inventoryPage.addFirstProductToCart();

        Assert.assertEquals(
                inventoryPage.getCartCount(),
                1,
                "Cart count should be 1"
        );
    }

    // =========================
    // TC-CART-02
    // =========================
    @Test
    @Story("Add multiple products to cart")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Добавление нескольких товаров в корзину")
    public void testAddMultipleProductsToCart() {

        int count = 3;

        inventoryPage.addMultipleProducts(count);

        Assert.assertEquals(
                inventoryPage.getCartCount(),
                count,
                "Cart count mismatch"
        );
    }

    // =========================
    // TC-CART-03
    // =========================
    @Test
    @Story("Cart counter increment")
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверка увеличения счетчика корзины")
    public void testCartCounterIncrement() {

        int before = inventoryPage.getCartCount();

        inventoryPage.addFirstProductToCart();

        int after = inventoryPage.getCartCount();

        Assert.assertEquals(
                after,
                before + 1,
                "Cart counter did not increment"
        );
    }

    // =========================
    // TC-CART-04
    // =========================
    @Test
    @Story("Remove product from cart")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Удаление товара из корзины")
    public void testRemoveProductFromCart() {

        inventoryPage.addFirstProductToCart();
        inventoryPage.openCart();

        cartPage.removeFirstProduct();

        Assert.assertEquals(
                cartPage.getCartItemsCount(),
                0,
                "Cart is not empty after removal"
        );
    }

    // =========================
    // TC-CART-05
    // =========================
    @Test
    @Story("Cart counter decrement")
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверка уменьшения счетчика после удаления товара")
    public void testCartCounterDecrement() {

        inventoryPage.addFirstProductToCart();
        int before = inventoryPage.getCartCount();

        inventoryPage.openCart();
        cartPage.removeFirstProduct();

        // возвращаемся на inventory
        driver.navigate().back();

        int after = inventoryPage.getCartCount();

        Assert.assertEquals(
                after,
                before - 1,
                "Cart counter did not decrement"
        );
    }

    // =========================
    // TC-CART-06
    // =========================
    @Test
    @Story("Cart persistence")
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверка сохранения товаров в корзине")
    public void testCartPersistence() {

        inventoryPage.addFirstProductToCart();
        int expectedCount = inventoryPage.getCartCount();

        // имитация перехода
        driver.navigate().refresh();

        inventoryPage.openCart();

        Assert.assertEquals(
                cartPage.getCartItemsCount(),
                expectedCount,
                "Cart items not persisted"
        );
    }
}

