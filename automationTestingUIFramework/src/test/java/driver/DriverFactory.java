package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import utils.ConfigReader;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.net.URL;

import java.time.Duration;

public class DriverFactory {

    private static WebDriver driver;

    public static WebDriver getDriver() {

        if (driver == null) {

            String browser = ConfigReader.getProperty("browser");
            String execution = ConfigReader.getProperty("execution");

            try {

                if (execution.equalsIgnoreCase("remote")) {

                    String gridUrl = ConfigReader.getProperty("gridUrl");

                    switch (browser.toLowerCase()) {
                        case "chrome":
                            driver = new RemoteWebDriver(
                                    new URL(gridUrl),
                                    new ChromeOptions()
                            );
                            break;

                        case "firefox":
                            driver = new RemoteWebDriver(
                                    new URL(gridUrl),
                                    new FirefoxOptions()
                            );
                            break;

                        default:
                            throw new RuntimeException("Unsupported browser");
                    }

                } else {
                    // локальный запуск (как у тебя уже есть)
                    switch (browser.toLowerCase()){
                        case "chrome":
                            WebDriverManager.chromedriver().setup();
                            driver = new ChromeDriver();
                            break;

                        case "firefox":
                            WebDriverManager.firefoxdriver().setup();
                            driver = new FirefoxDriver();
                            break;
                    }
                }

                driver.manage().window().maximize();
                driver.manage().timeouts().implicitlyWait(
                        Duration.ofSeconds(
                                Integer.parseInt(ConfigReader.getProperty("implicitWait"))
                        )
                );

            } catch (Exception e) {
                throw new RuntimeException("Failed to create driver", e);
            }
        }

        return driver;
    }

    public static void quitDriver() {
        if(driver != null) {
            driver.quit();
            driver = null;
        }
    }

}
