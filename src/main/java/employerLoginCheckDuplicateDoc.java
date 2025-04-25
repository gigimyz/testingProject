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

public class employerLoginCheckDuplicateDoc {

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
            checkForDuplicateOfTitle("project", "Project Outline");


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
    public static void checkForDuplicateOfTitle(String category, String documentTitle) {
        System.out.println("🔍 Checking how many times '" + documentTitle + "' appears in category '" + category + "'...");

        wait.until(ExpectedConditions.urlContains("/documents"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));

        System.out.println("📂 Expanding category: " + category);

        System.out.println("🔍 Counting all documents with title: '" + documentTitle + "'...");

        List<WebElement> matchingDocuments = driver.findElements(By.xpath(
                "//button//span[normalize-space(text())='" + documentTitle + "']"
        ));

        int count = matchingDocuments.size();

        if (count > 1) {
            System.out.println("❌ Duplicate found: '" + documentTitle + "' appears " + count + " times.");
        } else if (count == 1) {
            System.out.println("✅ Document '" + documentTitle + "' appears exactly once.");
        } else {
            System.out.println("⚠️ Document '" + documentTitle + "' not found.");
        }
    }


}
