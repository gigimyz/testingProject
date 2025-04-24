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

public class employerDocument {
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
            utils.loginTest(baseUrl, employeeEmail, employeePassword, (ChromeDriver) driver, wait);


            System.out.println("📄 Navigating to 'View Documents'...");
            WebElement uploadDocumentsCard = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//h2[text()='View Documents']/ancestor::div[@role='button']")));
            uploadDocumentsCard.click();
            System.out.println("✅ Clicked 'View Documents' card.");

            // search
            System.out.println("🔍 Testing Search Bar...");
            WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//input[@placeholder='Search documents...']"))); // Locator based on placeholder text
            searchInput.sendKeys("test");
            Thread.sleep(1000);
            WebElement testingItem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[.//span[text()='testing']]")));
            System.out.println("🎉 'testing' document item successfully found and is visible in the sidebar.");
            Thread.sleep(1000); // Small pause to observe

            // choose the file
            testingItem.click();
            System.out.println("➡️ click the file");
            WebElement pdfIframeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[contains(@class, 'DocumentViewer_pdfViewer')]")));
            String pdfSourceUrl = pdfIframeElement.getAttribute("src");
            assert(pdfSourceUrl!= null && !pdfSourceUrl.isEmpty());
            System.out.println("✅ Verifying PDF viewer appears on the right side...");
            Thread.sleep(1000);

            // select AI Q&A - Doc feature
            System.out.println("Click 'AI Q&A - Doc'...");
            WebElement aiQADocLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='AI Q&A + Doc']")));
            aiQADocLink.click();
            Thread.sleep(1000);
            System.out.println("'AI Q&A - Doc' clicked.");
            WebElement aiQAHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='AI Q&A']")));
            System.out.println("'AI Q&A' header is visible.");
            WebElement questionInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Ask a question about your documents...']")));
            System.out.println("Question input field is visible.");
            Thread.sleep(1000);
            System.out.println("Input the question");
            questionInput.sendKeys("What are the assignments of this course?");
            Thread.sleep(1000);
            System.out.println("click the askAI.");
            WebElement askAI = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Ask AI']")));
            askAI.click();
            Thread.sleep(5000);

            // AI response
            WebElement aiResponseElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(@class, 'text-gray-700')]")));
            String aiResponseText = aiResponseElement.getText();
            System.out.println("🤖 AI answers:");
            Thread.sleep(1000);

            // page navigation from AI response
            By referencePagesTitleLocator = By.xpath("//p[text()='Reference Pages:']");
            wait.until(ExpectedConditions.visibilityOfElementLocated(referencePagesTitleLocator));
            System.out.println("🎉 Reference Pages show");
            By allPageButtonsLocator = By.xpath("//button[starts-with(text(), 'Page ')]");
            List<WebElement> pageButtons = driver.findElements(allPageButtonsLocator);
            System.out.println("Find " + pageButtons.size() + " 个 Reference Page 按钮。");

            for (WebElement button : pageButtons) {
                String buttonText = button.getText();
                System.out.println("- " + buttonText);
                int pageNumber = Integer.parseInt(buttonText.replace("Page ", "").trim());
                System.out.println("➡️ Click the button '" + buttonText + "'...");
                button.click();
                System.out.println("Button '" + buttonText + "' has been clicked.");
                WebElement updatedIframe = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[contains(@class, 'DocumentViewer_pdfViewer')]")));
                wait.until(ExpectedConditions.attributeContains(updatedIframe, "src", "#page=" + pageNumber));
                Thread.sleep(1000);
            }

            // choose AI Q&A History - Doc feature
            System.out.println("Click 'AI Q&A History - Doc'...");
            WebElement aiHisDocLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='AI Q&A History + Doc']")));
            aiHisDocLink.click();
            Thread.sleep(1000);
            WebElement historyTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='Question History']")));
            System.out.println("Can see question history...");
            // locate last question
            String expectedDocumentName = "testing";
            System.out.println("🔍 Search last question...");
            WebElement historyEntryContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='What are the assignments of this course?']")));
            System.out.println("🎉 Find question history! ");
            Thread.sleep(1000);
            // locate expand button
            WebElement expandButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class, 'ml-2') and contains(@class, 'text-gray-400')]")));
            expandButton.click();
            System.out.println("Expand the history item");
            Thread.sleep(1000);
            By referencePagesTitleLocator_2 = By.xpath("//p[text()='Reference Pages:']");
            wait.until(ExpectedConditions.visibilityOfElementLocated(referencePagesTitleLocator_2));
            System.out.println("🎉 Reference Pages exist");
            expandButton.click();
            System.out.println("Unexpand the history item");

            // select Doc only feature
            System.out.println("Click Document Only'...");
            WebElement DocLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Document Only']")));
            DocLink.click();
            Thread.sleep(1000);
            WebElement pdfIframeElement_2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[contains(@class, 'DocumentViewer_pdfViewer')]")));
            String pdfSourceUrl_2 = pdfIframeElement_2.getAttribute("src");
            assert(pdfSourceUrl_2!= null && !pdfSourceUrl_2.isEmpty());
            System.out.println("✅ Verifying PDF viewer appears on the right side...");
            Thread.sleep(1000);




        }
        catch (Exception e) {
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
}
