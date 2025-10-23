package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class SummaryPage {
    WebDriver driver;

    @FindBy(xpath = "//div[text()='Sauce Labs Backpack']")
    private WebElement backpackText;

    @FindBy(xpath = "//div[text()='Sauce Labs Bike Light']")
    private WebElement bikeLightText;

    @FindBy(id = "finish")
    private WebElement finishButton;

    @FindBy(id = "react-burger-menu-btn")
    private WebElement menuButton;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;

    public SummaryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void verifyOrderSummary() {
        Assert.assertEquals(backpackText.getText(), "Sauce Labs Backpack");
        Assert.assertEquals(bikeLightText.getText(), "Sauce Labs Bike Light");
    }

    public void finishOrder() {
        finishButton.click();
    }

    public void logout() throws InterruptedException {
        menuButton.click();
        Thread.sleep(1000);
        logoutLink.click();
    }
}
