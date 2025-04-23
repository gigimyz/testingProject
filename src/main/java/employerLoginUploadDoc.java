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

public class employerLoginUploadDoc {

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

            String relativePath = "src/main/resources/projectoutline.pdf";
            String absolutePath = new File(relativePath).getAbsolutePath();
            uploadDocument("Project Outline", "project", absolutePath);
            verifyDocumentInViewer("project","Project Outline");

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

    public static void uploadDocument(String title, String category, String filePath) {
        System.out.println("📤 Starting document upload...");

        // Step 1: Set the document title
        WebElement titleInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@placeholder='Enter document title']")));
        titleInput.clear();
        titleInput.sendKeys(title);
        System.out.println("✅ Title set.");

        // Step 2: Verify and select category
        WebElement categorySelect = driver.findElement(By.cssSelector("select[name='category']"));
        List<WebElement> categoryOptions = categorySelect.findElements(By.tagName("option"));

        boolean categoryFound = categoryOptions.stream()
                .anyMatch(option -> option.getText().trim().equalsIgnoreCase(category));

        if (!categoryFound) {
            System.out.println("❌ Category '" + category + "' not found.");
            return;
        }

        categorySelect.click();
        WebElement categoryOption = categorySelect.findElement(By.xpath("//option[text()='" + category + "']"));
        categoryOption.click();
        System.out.println("✅ Category selected: " + category);

        // Step 3: Upload the file via the hidden input
        WebElement fileInput = driver.findElement(By.cssSelector("input[type='file']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.display = 'block';", fileInput);
        fileInput.sendKeys(filePath);
        System.out.println("✅ File selected: " + filePath);

        // Step 4: Click "Upload 1 file" button
        WebElement upload1FileBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[text()='Upload 1 file']")));
        upload1FileBtn.click();
        System.out.println("📥 Clicked 'Upload 1 file' button");

        // Step 5: Wait 3 seconds to simulate upload processing
        try {
            System.out.println("⏳ Waiting 3 seconds for file upload to process...");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Step 6: Verify file name is shown (upload successful)
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div.Upload_fileInfo__Zohiy span.Upload_fileName__pSaO4")));
        System.out.println("✅ File upload confirmed by presence of file info.");

        // Step 7: Click the "Upload Document" button to submit the full form
        WebElement finalSubmit = driver.findElement(By.cssSelector("button.Upload_submitButton__nTwkK"));
        finalSubmit.click();
        System.out.println("📨 Final 'Upload Document' button clicked.");

        // Optional: Confirm you're still on the upload page
        wait.until(ExpectedConditions.urlContains("/upload"));
        System.out.println("✅ Upload process completed and still on /upload.");
    }

    public static void verifyDocumentInViewer(String category, String documentTitle) {
        System.out.println("🔍 Verifying uploaded document...");

        // Step 1: Confirm we are on the correct page
        wait.until(ExpectedConditions.urlToBe("https://www.pdrai.online/employer/documents"));
        System.out.println("✅ URL is correct.");

        // Step 2: Wait for viewer container
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div.DocumentViewer_container__w24Ev")));
        System.out.println("✅ Document viewer container is visible.");

        // Step 3: Find the category
        WebElement categoryHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='DocumentViewer_categoryHeader__vLdUW']/span[text()='" + category + "']")));
        System.out.println("✅ Category '" + category + "' is listed.");

        // Step 4: Find the document title under the category
        WebElement documentEntry = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[.//span[text()='" + category + "']]//span[text()='" + documentTitle + "']")));
        System.out.println("✅ Document '" + documentTitle + "' is listed under category '" + category + "'.");
    }





}
