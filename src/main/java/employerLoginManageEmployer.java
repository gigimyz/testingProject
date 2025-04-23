import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class employerLoginManageEmployer {

    public static void main(String[] args) {
        // Set up ChromeDriver
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.manage().window().maximize();

        String employeeEmail = "mayz.gigi2016@outlook.com";
        String employeePassword = "SoftwareTesting2025";
        String companyName = "Software Testing";
        String companyPasscode = "12345678";

        try {
            System.out.println("Navigating to https://www.pdrai.online/ ...");
            driver.get("https://www.pdrai.online/");

            System.out.println("Clicking 'Get Started'...");
            System.out.println("Clicking 'Get Started' button...");
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

            // Wait for the employer home/dashboard page
            System.out.println("✅ Verifying dashboard loaded...");
            wait.until(ExpectedConditions.urlContains("/home")); // Adjust if different

            // Click on "Manage Employees"
            System.out.println("🧑‍💼 Clicking on 'Manage Employees'...");
            WebElement manageEmployeesBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//h2[text()='Manage Employees']/ancestor::div[@role='button']")));
            manageEmployeesBtn.click();

            // Wait for the employee list to appear
            System.out.println("📄 Waiting for pending approvals list...");
            WebElement pendingList = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'Pending Approvals')]")));

            // Locate the pending employee row by email
            // Define the employee name you're looking for
            String employeeName = "Thomas Ma";
            System.out.println("🔍 Looking for employee: " + employeeName);
            // Locate the row that contains the employee name
            WebElement employeeRow = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//table[contains(@class, 'employeeTable')]//tr[td[text()='" + employeeName + "']]")));

            // Within that row, find the Approve button and click it
            WebElement approveButton = employeeRow.findElement(By.cssSelector("button.EmployeeManagement_approveButton__30Gqu"));
            approveButton.click();
            System.out.println("✅ Approved employee: " + employeeName);

            // Optional: Wait for approval confirmation
            Thread.sleep(1000);

            System.out.println("🗑️ Removing employee: " + employeeName);
            // Locate the row in the "All Employees" section that contains the name
            WebElement employeeRow2 = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//section[contains(@class, 'employeeSection')]//table//tr[td[text()='" + employeeName + "']]")));

            // Within that row, find and click the "Remove" button by class
            WebElement removeButton = employeeRow2.findElement(By.cssSelector("button.EmployeeManagement_removeButton__No_Bb"));
            removeButton.click();
            System.out.println("✅ Successfully removed: " + employeeName);

            // Confirm removal if needed
            Thread.sleep(500);
            System.out.println("🕒 Waiting for employee row to be removed...");

            // Wait until the row is no longer visible in the DOM
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.xpath("//section[contains(@class, 'employeeSection')]//table//tr[td[text()='" + employeeName + "']]")));
            System.out.println("✅ Successfully removed: " + employeeName);

        } catch (Exception e) {
            System.out.println("❌ Login failed.");
            e.printStackTrace();
        } finally {
            System.out.println("Closing browser...");
            try {
                Thread.sleep(5000); // Give you a moment to see result
            } catch (InterruptedException ignored) {}
            driver.quit();
        }
    }
}