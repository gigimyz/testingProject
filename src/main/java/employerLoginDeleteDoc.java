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

public class employerLoginDeleteDoc {

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
            System.out.println("📄 Navigating to 'View Documents'...");
            WebElement uploadDocumentsCard = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//h2[text()='View Documents']/ancestor::div[@role='button']")));
            uploadDocumentsCard.click();
            System.out.println("✅ Clicked 'View Documents' card.");

            deleteDocument("project","Project Outline");

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
    public static void deleteDocument(String category, String documentTitle) {
        System.out.println("🗑️ Attempting to delete document: '" + documentTitle + "' from category: '" + category + "'");

        // Step 1: Confirm we're on the documents page
        wait.until(ExpectedConditions.urlToBe("https://www.pdrai.online/employer/documents"));
        System.out.println("✅ Confirmed on /documents page.");

        // Step 2: Expand the correct category (if collapsed)
        WebElement categoryElement = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@class='DocumentViewer_categoryHeader__vLdUW']/span[text()='" + category + "']")));
        categoryElement.click(); // expand if necessary
        System.out.println("📂 Category '" + category + "' expanded.");

        // Step 3: Click the document to select it
        WebElement documentButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[.//span[text()='" + category + "']]//button[.//span[text()='" + documentTitle + "']]")));
        documentButton.click();
        System.out.println("📄 Document '" + documentTitle + "' selected.");

        // Step 4: Wait for the remove button to appear (assumes it's now visible in main panel)
        WebElement removeBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(@class, 'Upload_removeFile__')]")));
        removeBtn.click();
        System.out.println("🗑️ Clicked 'Remove' button.");

        // Optional: Confirm the document is gone from the list
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//div[.//span[text()='" + category + "']]//span[text()='" + documentTitle + "']")));
        System.out.println("✅ Document '" + documentTitle + "' has been removed.");
    }






}
