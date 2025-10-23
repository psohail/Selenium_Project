package TestScript;

import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.Sample.BaseTest;

public class WebDriver_Chrome extends BaseTest {
	
	

    @DataProvider(name = "loginData")
    public Object[][] getData() throws Exception {
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\TestData\\ExSheet.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheet("Sheet1");

        int rowCount = sheet.getLastRowNum();
        Object[][] data = new Object[rowCount][2];

        for (int i = 1; i <= rowCount; i++) {
            XSSFRow row = sheet.getRow(i);
            data[i - 1][0] = row.getCell(0).getStringCellValue(); // Username
            data[i - 1][1] = row.getCell(1).getStringCellValue(); // Password
        }

        workbook.close();
        fis.close();
        return data;
    }

    @Test(dataProvider = "loginData")
    public void testLogin(String username, String password) throws InterruptedException {
        driver.get("https://www.saucedemo.com/");
        

        driver.findElement(By.id("user-name")).clear();
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();

        Thread.sleep(2000); // Wait for response

        try {
            boolean isLoggedIn = driver.findElement(By.xpath("//div[@class='app_logo']")).isDisplayed();
            if (isLoggedIn) {
                System.out.println("✅ USERNAME: " + username + " | PASSWORD: " + password + " --> PASS");
                driver.findElement(By.id("react-burger-menu-btn")).click();
                driver.findElement(By.id("logout_sidebar_link")).click();
            }	
        } catch (Exception e) {
            System.out.println("❌ USERNAME: " + username + " | PASSWORD: " + password + " --> FAIL");
        }
    }

}