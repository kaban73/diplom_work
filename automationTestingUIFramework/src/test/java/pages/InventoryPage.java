package pages;

import core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class InventoryPage extends BasePage {

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(className = "inventory_item")
    private List<WebElement> products;

    public boolean isOpened() {
        return !products.isEmpty();
    }

    public int getProductsCount() {
        return products.size();
    }
}

