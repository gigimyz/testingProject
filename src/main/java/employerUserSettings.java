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

public class employerUserSettings {
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

            System.out.println("📄 Navigating to 'User Settings'...");
            WebElement uploadDocumentsCard = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//h2[text()='User Settings']/ancestor::div[@role='button']")));
            uploadDocumentsCard.click();
            System.out.println("✅ Clicked 'User Settings' card.");

            WebElement displayNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//input[@id='displayName' and @type='text' and @disabled]")
            ));
            assert(displayNameInput.getAttribute("value").equals("Gigi Ma"));
            System.out.println("✅ User name correct! ");

            WebElement displayEmail = driver.findElement(By.xpath("//input[@id='email']"));
            assert(displayEmail.getAttribute("value").equals(employeeEmail));
            System.out.println("✅ User email correct! ");


            WebElement displayCompanyName = driver.findElement(By.xpath("//input[@id='companyName']"));
            if (displayCompanyName.getAttribute("value").equals("Software Testing")) {
                System.out.println("✅ Company name correct");
            } else {
                System.out.println("❌ Error company name");
            }

            System.out.println("Change Company name to XXXXX");
            displayCompanyName.sendKeys("XXXXX");
            WebElement saveButton = driver.findElement(By.xpath("//button[text()='Save']"));
            saveButton.click();
            Thread.sleep(2000);
            driver.navigate().refresh();

            Thread.sleep(5000);
            WebElement displayCompanyName_2 = driver.findElement(By.xpath("//input[@id='companyName']"));
            if (displayCompanyName_2.getAttribute("value").equals("XXXXX")) {
                System.out.println("✅ Company name correct after change");
            } else {
                System.out.println("❌ Error company name after change");
            }


            WebElement EmployerPasskey = driver.findElement(By.xpath("//input[@id='employerPasskey']"));
            if (EmployerPasskey.getAttribute("value").equals("12345678")) {
                System.out.println("✅ EmployerPasskey correct");
            } else {
                System.out.println("❌ EmployerPasskey incorrect");
            }

            WebElement EmployeePasskey = driver.findElement(By.xpath("//input[@id='employeePasskey']"));
            if (EmployeePasskey.getAttribute("value").equals("12345678")) {
                System.out.println("✅ EmployeePasskey correct");
            } else {
                System.out.println("❌ EmployeePasskey incorrect");
            }

            WebElement numberStaff = driver.findElement(By.xpath("//input[@id='staffCount']"));
            if (numberStaff.getAttribute("value").equals("1")) {
                System.out.println("✅ correct count of staff");
            } else {
                System.out.println("❌ staff count incorrect");
            }


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
