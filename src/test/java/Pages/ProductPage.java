package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductPage {
    WebDriver driver;

    @FindBy(id = "add-to-cart-sauce-labs-backpack")
    private WebElement addBackpack;

    @FindBy(id = "add-to-cart-sauce-labs-bike-light")
    private WebElement addBikeLight;

    @FindBy(className = "shopping_cart_link")
    private WebElement cartLink;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void addProductsToCart() throws InterruptedException {
        addBackpack.click();
        Thread.sleep(1000);
        addBikeLight.click();
        Thread.sleep(1000);
    }

    public void goToCart() {
        cartLink.click();
    }

    public void proceedToCheckout() {
        checkoutButton.click();
    }
}
