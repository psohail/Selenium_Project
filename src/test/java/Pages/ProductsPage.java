package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class ProductsPage {
    WebDriver driver;

    private By addToCartButtons = By.cssSelector("button.btn_inventory");
    private By shoppingCartLink = By.className("shopping_cart_link");

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
    }

    public void addProductsToCart(int[] productIndexes) {
        List<WebElement> buttons = driver.findElements(addToCartButtons);
        for (int index : productIndexes) {
            if (index < buttons.size()) {
                buttons.get(index).click();
            }
        }
    }

    public void goToCart() {
        driver.findElement(shoppingCartLink).click();
    }
}
