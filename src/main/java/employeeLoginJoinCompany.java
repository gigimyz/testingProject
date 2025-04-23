import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class employeeLoginJoinCompany {

    public static void main(String[] args) {
        // Set up ChromeDriver
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.manage().window().maximize();

        String employeeEmail = "yma115@jh.edu";
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

            System.out.println("🧑‍💼 Selecting 'I'm an Employee'...");
            WebElement employeeCard = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@role='button' and .//h2[text()=\"I'm an Employee\"]]")));
            employeeCard.click();

            System.out.println("➡️ Clicking 'Continue' to proceed from role selection...");
            WebElement continueBtn3 = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(text(),'Continue')]")));
            continueBtn3.click();

            System.out.println("🏢 Entering company name...");
            WebElement companyInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//label[text()='Company Name']/following-sibling::div//input")));
            companyInput.click();
            companyInput.sendKeys(companyName); // ← replace with actual name

            System.out.println("🔑 Entering employee passcode...");
            WebElement passcodeInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//label[text()='Employee Passcode']/following-sibling::div//input")));
            passcodeInput.click();
            passcodeInput.sendKeys(companyPasscode); // ← replace with actual passcode

            System.out.println("➡️ Clicking 'Sign In'...");
            WebElement signInButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[@type='submit' and contains(text(),'Sign In')]")));
            signInButton.click();
            Thread.sleep(300);

            System.out.println("🧠 Final page check: URL, header, and email...");
            assert driver.getCurrentUrl().contains("/pending-approval");

            WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//h1[contains(text(),'Pending Approval')]")));
            WebElement email = driver.findElement(By.xpath("//*[contains(text(),'yma115@jh.edu')]"));

            assert header.isDisplayed();
            assert email.isDisplayed();
            System.out.println("🎉 Login flow completed and validated successfully!");


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