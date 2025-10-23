package TestScript;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.Sample.BaseTest;
import Pages.LoginPage;
import Pages.ProductPage;
import Pages.CheckoutPage;
import Pages.SummaryPage;

public class TestAll extends BaseTest {

    @DataProvider(name = "loginData")
    public Object[][] getData() throws Exception {
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\TestData\\ExSheet.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheet("Sheet1");
        DataFormatter formatter = new DataFormatter();
        List<Object[]> list = new ArrayList<>();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            XSSFRow row = sheet.getRow(i);
            if (row != null) {
                String user = formatter.formatCellValue(row.getCell(0));
                String pass = formatter.formatCellValue(row.getCell(1));
                if (!user.isEmpty() && !pass.isEmpty()) {
                    list.add(new Object[]{user, pass});
                }
            }
        }

        workbook.close();
        fis.close();
        return list.toArray(new Object[0][0]);
    }

    @Test(dataProvider = "loginData")
    public void testLogin(String username, String password) throws Exception {
        driver.get("https://www.saucedemo.com/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        LoginPage login = new LoginPage(driver);
        ProductPage product = new ProductPage(driver);
        CheckoutPage checkout = new CheckoutPage(driver);
        SummaryPage summary = new SummaryPage(driver);

        login.login(username, password);

        if (!driver.getCurrentUrl().contains("inventory.html")) {
            Assert.fail("Login failed!");
        }
        System.out.println("✅ Login successful");

        product.addProductsToCart();
        System.out.println("Products added successfully...........!");
        product.goToCart();
        product.proceedToCheckout();

        checkout.fillDetails("Sohail", "Basha", "560010");
        System.out.println("You have entered user details");

        System.out.println("📸 Screenshot saved!");

        summary.verifyOrderSummary();
        summary.finishOrder();
        // Take screenshot
        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);
        File dest = new File(System.getProperty("user.dir") + "\\Screenshots\\confirmation.png");
        Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

        summary.logout();
    }
}
