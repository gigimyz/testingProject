import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class utils {
    public static void loginTest(String baseUrl, String employeeEmail, String employeePassword, ChromeDriver driver,WebDriverWait wait) throws InterruptedException {
        System.out.println("Navigating to " + baseUrl + " ...");
        driver.get(baseUrl);

        System.out.println("Clicking 'Get Started'...");
        WebElement getStarted = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[text()='Get Started']")));
        getStarted.click();

        System.out.println("Waiting for Sign In form...");
        WebElement signInLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.linkText("Sign in")));
        signInLink.click();
        Thread.sleep(300);

        System.out.println("📧 Entering email/username...");
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[type='email'], input[type='text']")));
        emailInput.sendKeys(employeeEmail);

        System.out.println("➡️ Clicking visible 'Continue'...");
        WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[.//span[text()='Continue'] and not(@aria-hidden)]")));
        continueBtn.click();
        Thread.sleep(300);

        System.out.println("🔐 Waiting for password field...");
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[type='password']")));
        passwordInput.sendKeys(employeePassword);

        System.out.println("➡️ Clicking second 'Continue'...");
        WebElement continueBtn2 = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[.//span[text()='Continue'] and not(@aria-hidden)]")));
        continueBtn2.click();

        System.out.println("✅ Verifying dashboard loaded...");
        wait.until(ExpectedConditions.urlContains("/home"));
    }
}
