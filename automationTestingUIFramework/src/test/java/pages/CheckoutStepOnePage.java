package pages;

import core.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutStepOnePage extends BasePage {

    public CheckoutStepOnePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "first-name")
    private WebElement firstNameInput;

    @FindBy(id = "last-name")
    private WebElement lastNameInput;

    @FindBy(id = "postal-code")
    private WebElement zipInput;

    @FindBy(id = "continue")
    private WebElement continueButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    // ======================

    @Step("Заполнить форму: {firstName} {lastName}, {zip}")
    public void fillForm(String firstName, String lastName, String zip) {
        firstNameInput.sendKeys(firstName);
        lastNameInput.sendKeys(lastName);
        zipInput.sendKeys(zip);
    }

    @Step("Нажать Continue")
    public void clickContinue() {
        continueButton.click();
    }

    @Step("Получить текст ошибки")
    public String getErrorMessage() {
        return errorMessage.getText();
    }
}
