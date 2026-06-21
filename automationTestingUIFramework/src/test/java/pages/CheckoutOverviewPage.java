package pages;

import core.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutOverviewPage extends BasePage {

    public CheckoutOverviewPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "finish")
    private WebElement finishButton;

    @FindBy(className = "cart_item")
    private java.util.List<WebElement> items;

    @Step("Проверка, что страница overview открыта")
    public boolean isOpened() {
        return !items.isEmpty();
    }

    @Step("Нажать Finish")
    public void clickFinish() {
        finishButton.click();
    }
}
