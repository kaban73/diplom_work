package pages;

import core.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CartPage extends BasePage {

    public CartPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(className = "cart_item")
    private List<WebElement> cartItems;

    @FindBy(css = ".cart_button")
    private List<WebElement> removeButtons;

    // ======================
    // BASIC
    // ======================

    @Step("Получить количество товаров в корзине")
    public int getCartItemsCount() {
        return cartItems.size();
    }

    @Step("Проверка, что корзина не пустая")
    public boolean isCartNotEmpty() {
        return !cartItems.isEmpty();
    }

    // ======================
    // ACTIONS
    // ======================

    @Step("Удалить первый товар из корзины")
    public void removeFirstProduct() {
        removeButtons.get(0).click();
    }
}

