package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;

/**
 * Extent Report Manager
 * Manages Extent Reports configuration
 */
public class ExtentReportManager {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    /**
     * Initialize Extent Reports
     */
    public static ExtentReports createInstance() {
        String reportPath = ConfigReader.getProperty("extent.report.path");

        // Create report directory if not exists
        File reportDir = new File(reportPath).getParentFile();
        if (!reportDir.exists()) {
            reportDir.mkdirs();
        }

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

        // Configuration
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setDocumentTitle(ConfigReader.getProperty("extent.report.title"));
        sparkReporter.config().setReportName(ConfigReader.getProperty("extent.report.name"));
        sparkReporter.config().setEncoding("utf-8");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // System Info
        extent.setSystemInfo("Application", "SauceDemo");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Browser", ConfigReader.getBrowser());
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("User", System.getProperty("user.name"));

        return extent;
    }

    /**
     * Get Extent Reports instance
     */
    public static ExtentReports getInstance() {
        if (extent == null) {
            createInstance();
        }
        return extent;
    }

    /**
     * Set current test
     */
    public static void setTest(ExtentTest test) {
        extentTest.set(test);
    }

    /**
     * Get current test
     */
    public static ExtentTest getTest() {
        return extentTest.get();
    }
}