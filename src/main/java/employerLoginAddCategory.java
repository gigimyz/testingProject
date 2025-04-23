import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.List;

public class employerLoginAddCategory {

    // 🔧 Configurable variables
    static String baseUrl = "https://www.pdrai.online/";
    static String employeeEmail = "mayz.gigi2016@outlook.com";
    static String employeePassword = "SoftwareTesting2025";
    static WebDriver driver;
    static WebDriverWait wait;

    public static void main(String[] args) {
        // Set up ChromeDriver
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.manage().window().maximize();

        try {
            //login first
            utils utils = new utils();
            utils.loginTest(baseUrl,employeeEmail,employeePassword, (ChromeDriver) driver,wait);

            System.out.println("📄 Navigating to 'Upload Documents'...");
            WebElement uploadDocumentsCard = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//h2[text()='Upload Documents']/ancestor::div[@role='button']")));
            uploadDocumentsCard.click();
            System.out.println("✅ Clicked 'Upload Documents' card.");

            addCategory("project");
            addCategory("project"); // try adding duplicate
            checkDuplicateCategoryAdd("project");

        } catch (Exception e) {
            System.out.println("❌ Error during test execution.");
            e.printStackTrace();
        } finally {
            System.out.println("Closing browser...");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ignored) {}
            driver.quit();
        }
    }

    public static void addCategory(String categoryName) throws InterruptedException {
        System.out.println("➕ Adding new category: " + categoryName);
        String expectedUrl = "https://www.pdrai.online/employer/upload";
        // Locate the input field and type the category name
        WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("form.Upload_addCategoryForm__iiidY input[placeholder='New category name']")));
        inputField.clear();
        inputField.sendKeys(categoryName);

        // Click the "Add Category" button
        WebElement addButton = driver.findElement(
                By.cssSelector("form.Upload_addCategoryForm__iiidY button[type='submit']"));
        addButton.click();

        // Optional: Wait for the category to appear in the list
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//ul[contains(@class,'categoryList')]//li[span[text()='" + categoryName + "']]")));

        System.out.println("✅ Successfully added category: " + categoryName);

        // Verify URL is still /upload
        Thread.sleep(1000);
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.equals(expectedUrl)) {
            System.out.println("✅ Still on upload page after adding category.");
        } else {
            System.out.println("❌ URL changed unexpectedly! Current URL: " + currentUrl);
        }
    }

    public static void checkDuplicateCategoryAdd(String categoryName) throws InterruptedException {
        List<WebElement> duplicates = driver.findElements(
                By.xpath("//ul[contains(@class,'categoryList')]//li[span[text()='" + categoryName + "']]"));

        if (duplicates.size() > 1) {
            System.out.println("❌ Duplicate category allowed (" + duplicates.size() + " times) — should be blocked!");
        } else {
            System.out.println("✅ Duplicate category was correctly prevented.");
        }
    }


}
