package pages;

import core.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

public class InventoryPage extends BasePage {

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    // список товаров
    @FindBy(className = "inventory_item")
    private List<WebElement> products;

    // названия
    @FindBy(className = "inventory_item_name")
    private List<WebElement> productTitles;

    // цены
    @FindBy(className = "inventory_item_price")
    private List<WebElement> productPrices;

    // dropdown сортировки
    @FindBy(className = "product_sort_container")
    private WebElement sortDropdown;

    // ======================
    // BASIC CHECKS
    // ======================

    @Step("Проверка, что страница товаров открыта")
    public boolean isOpened() {
        return !products.isEmpty();
    }

    @Step("Получение количества товаров")
    public int getProductsCount() {
        return products.size();
    }

    // ======================
    // PRODUCTS DATA
    // ======================

    @Step("Получение списка названий товаров")
    public List<String> getProductTitles() {
        return productTitles.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    @Step("Получение списка цен товаров")
    public List<Double> getProductPrices() {
        return productPrices.stream()
                .map(e -> e.getText().replace("$", ""))
                .map(Double::parseDouble)
                .collect(Collectors.toList());
    }

    // ======================
    // VALIDATIONS
    // ======================

    @Step("Проверка, что у всех товаров есть название")
    public boolean allProductsHaveTitles() {
        return getProductTitles().stream()
                .allMatch(title -> !title.isEmpty());
    }

    @Step("Проверка, что у всех товаров есть цена")
    public boolean allProductsHavePrices() {
        return getProductPrices().stream()
                .allMatch(price -> price > 0);
    }

    // ======================
    // SORTING
    // ======================

    @Step("Выбор сортировки: {option}")
    public void selectSortOption(String option) {
        Select select = new Select(sortDropdown);
        select.selectByVisibleText(option);
    }

    // ======================
    // SORT VALIDATION
    // ======================

    @Step("Проверка сортировки цен по возрастанию")
    public boolean isPriceSortedAsc() {
        List<Double> prices = getProductPrices();
        return isSortedAsc(prices);
    }

    @Step("Проверка сортировки цен по убыванию")
    public boolean isPriceSortedDesc() {
        List<Double> prices = getProductPrices();
        return isSortedDesc(prices);
    }

    @Step("Проверка сортировки названий A-Z")
    public boolean isNameSortedAsc() {
        List<String> names = getProductTitles();
        return isSortedAsc(names);
    }

    @Step("Проверка сортировки названий Z-A")
    public boolean isNameSortedDesc() {
        List<String> names = getProductTitles();
        return isSortedDesc(names);
    }

    // ======================
    // UTILS
    // ======================

    private <T extends Comparable<T>> boolean isSortedAsc(List<T> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).compareTo(list.get(i + 1)) > 0) {
                return false;
            }
        }
        return true;
    }

    private <T extends Comparable<T>> boolean isSortedDesc(List<T> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).compareTo(list.get(i + 1)) < 0) {
                return false;
            }
        }
        return true;
    }
}

