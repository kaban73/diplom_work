package tests;

import core.BaseTest;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;

@Epic("Products")
@Feature("Inventory")
public class ProductsTest extends BaseTest {

    private LoginPage loginPage;
    private InventoryPage inventoryPage;

    @BeforeMethod
    public void initPages() {
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);

        // логинимся перед каждым тестом
        loginPage.login("standard_user", "secret_sauce");
    }

    // =========================
    // TC-PROD-01
    // =========================
    @Test(groups = {"smoke"})
    @Story("Products list display")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверка, что список товаров отображается и не пуст")
    public void testProductsListDisplayed() {

        Assert.assertTrue(
                inventoryPage.isOpened(),
                "Products page is not opened"
        );

        Assert.assertTrue(
                inventoryPage.getProductsCount() > 0,
                "Products list is empty"
        );
    }

    // =========================
    // TC-PROD-02
    // =========================
    @Test
    @Story("Product title display")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверка, что у всех товаров есть название")
    public void testProductTitleDisplayed() {

        Assert.assertTrue(
                inventoryPage.allProductsHaveTitles(),
                "Some products have empty titles"
        );
    }

    // =========================
    // TC-PROD-03
    // =========================
    @Test
    @Story("Product price display")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Проверка, что у всех товаров есть цена")
    public void testProductPriceDisplayed() {

        Assert.assertTrue(
                inventoryPage.allProductsHavePrices(),
                "Some products have invalid prices"
        );
    }

    // =========================
    // TC-PROD-04
    // =========================
    @Test
    @Story("Sort by price ascending")
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверка сортировки по цене (по возрастанию)")
    public void testSortByPriceAsc() {

        inventoryPage.selectSortOption("Price (low to high)");

        Assert.assertTrue(
                inventoryPage.isPriceSortedAsc(),
                "Products are not sorted by price ascending"
        );
    }

    // =========================
    // TC-PROD-05
    // =========================
    @Test
    @Story("Sort by price descending")
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверка сортировки по цене (по убыванию)")
    public void testSortByPriceDesc() {

        inventoryPage.selectSortOption("Price (high to low)");

        Assert.assertTrue(
                inventoryPage.isPriceSortedDesc(),
                "Products are not sorted by price descending"
        );
    }

    // =========================
    // TC-PROD-06
    // =========================
    @Test
    @Story("Sort by name A-Z")
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверка сортировки по названию A-Z")
    public void testSortByNameAsc() {

        inventoryPage.selectSortOption("Name (A to Z)");

        Assert.assertTrue(
                inventoryPage.isNameSortedAsc(),
                "Products are not sorted A-Z"
        );
    }

    // =========================
    // TC-PROD-07
    // =========================
    @Test
    @Story("Sort by name Z-A")
    @Severity(SeverityLevel.NORMAL)
    @Description("Проверка сортировки по названию Z-A")
    public void testSortByNameDesc() {

        inventoryPage.selectSortOption("Name (Z to A)");

        Assert.assertTrue(
                inventoryPage.isNameSortedDesc(),
                "Products are not sorted Z-A"
        );
    }
}

