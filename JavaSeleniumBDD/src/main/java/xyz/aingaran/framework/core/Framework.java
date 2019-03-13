package xyz.aingaran.framework.core;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Core class that manages Driver.
 * All functions inside this will and should be static.
 */
public class Framework {

    private static final Logger logger = Logger.getLogger(Framework.class);

    private static WebDriver webDriver = null;

    private Framework() {
        // Private constructor to hide the default implicit constructor.
        // This class should not be used as an instance.
    }

    public static void init()  {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriverlinux");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.setHeadless(true);
        chromeOptions.addArguments("--window-size=1366x768");
        chromeOptions.setExperimentalOption("useAutomationExtension", false);
        webDriver = new ChromeDriver(chromeOptions);
        webDriver.manage().window().maximize();
    }

    public static WebDriver getWebDriver()  {
        if (null == webDriver)  {
            init();
            return webDriver;
        } else  {
            return webDriver;
        }
    }

    public static byte[] takeScreenShot(String scenario, String testCase) {
        File shot = ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(shot, new File("src/test/resources/screenshot/" + LocalDate.now()
                    + File.separator + scenario + File.separator + testCase + ".jpeg"));
        } catch (IOException ioException)   {
            logger.error("Cannot save screenshot", ioException);
        }
        return ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    public static void destroy()    {
        if(null != webDriver)   {
            webDriver.close();
            webDriver.quit();
            webDriver = null;
        }
    }
}
