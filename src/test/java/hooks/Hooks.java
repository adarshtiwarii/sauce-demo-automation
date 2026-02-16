package hooks;

import io.cucumber.java.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utils.ConfigReader;
import utils.DriverManager;
import utils.ScreenshotUtil;

/**
 * Cucumber Hooks
 * Before and After scenario execution
 */
public class Hooks {

    @Before
    public void setUp(Scenario scenario) {
        System.out.println("========================================");
        System.out.println("Starting Scenario: " + scenario.getName());
        System.out.println("Tags: " + scenario.getSourceTagNames());
        System.out.println("========================================");

        // Initialize driver
        DriverManager.initializeDriver();

        // Implicit wait
        int implicitWait = Integer.parseInt(ConfigReader.getProperty("implicit.wait"));
        DriverManager.getDriver().manage().timeouts()
                .implicitlyWait(java.time.Duration.ofSeconds(implicitWait));
    }

    @After
    public void tearDown(Scenario scenario) {
        // Take screenshot if scenario failed
        if (scenario.isFailed()) {
            System.out.println("❌ Scenario FAILED: " + scenario.getName());

            // Capture screenshot
            try {
                TakesScreenshot ts = (TakesScreenshot) DriverManager.getDriver();
                byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", scenario.getName());

                // Also save to file
                ScreenshotUtil.takeScreenshot(DriverManager.getDriver(),
                        scenario.getName().replaceAll(" ", "_"));

                System.out.println("📸 Screenshot captured for failed scenario");
            } catch (Exception e) {
                System.out.println("Failed to capture screenshot: " + e.getMessage());
            }
        } else {
            System.out.println("✅ Scenario PASSED: " + scenario.getName());
        }

        System.out.println("========================================");
        System.out.println("Completed Scenario: " + scenario.getName());
        System.out.println("Status: " + scenario.getStatus());
        System.out.println("========================================\n");

        // Quit driver
        DriverManager.quitDriver();
    }

    @BeforeStep
    public void beforeStep(Scenario scenario) {
        // Optional: Add any step-level setup
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        // Optional: Capture screenshot after each step
        // Uncomment if you want screenshots for every step
        /*
        if ("true".equalsIgnoreCase(ConfigReader.getProperty("screenshots.enabled"))) {
            byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver())
                              .getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Step Screenshot");
        }
        */
    }
}