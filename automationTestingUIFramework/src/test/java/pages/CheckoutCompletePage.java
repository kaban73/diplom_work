package pages;

import core.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutCompletePage extends BasePage {

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(className = "complete-header")
    private WebElement successMessage;

    @Step("Получить сообщение об успешном заказе")
    public String getSuccessMessage() {
        return successMessage.getText();
    }

    @Step("Проверка, что заказ завершен")
    public boolean isOrderComplete() {
        return successMessage.isDisplayed();
    }

    @Step("Получить текущий URL")
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}

