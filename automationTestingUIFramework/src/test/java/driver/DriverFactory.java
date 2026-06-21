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

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public static WebDriver getDriver() {

        if (driverThreadLocal.get() == null) {

            String browser = ConfigReader.getProperty("browser");
            String execution = ConfigReader.getProperty("execution");

            try {

                WebDriver driver;

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

                        default: throw new RuntimeException("Unsupported browser");
                    }
                }

                driver.manage().window().maximize();
                driver.manage().timeouts().implicitlyWait(
                        Duration.ofSeconds(
                                Integer.parseInt(ConfigReader.getProperty("implicitWait"))
                        )
                );

                driverThreadLocal.set(driver);

            } catch (Exception e) {
                throw new RuntimeException("Failed to create driver", e);
            }
        }

        return driverThreadLocal.get();
    }

    public static void quitDriver() {
        if(driverThreadLocal.get() != null) {
            driverThreadLocal.get().quit();
            driverThreadLocal.remove();
        }
    }

}


