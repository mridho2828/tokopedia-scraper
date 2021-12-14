package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CustomWebDriver {
    private static final String GOOGLE_URL = "https://www.google.com";
    private static final String USER_AGENT =
            "user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) "
                    + "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36";

    private WebDriver webDriver;
    private WebDriverWait webDriverWait;
    private JavascriptExecutor javascriptExecutor;

    public CustomWebDriver() {
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        ChromeOptions optionsChrome = new ChromeOptions();
        optionsChrome.setHeadless(true);
        optionsChrome.addArguments(USER_AGENT);

        webDriver =  new ChromeDriver();
        webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
        javascriptExecutor = (JavascriptExecutor) webDriver;
    }

    public void switchTab(String tab) {
        webDriver.switchTo().window(tab);
    }

    public List<String> prepareTwoTabs() {
        webDriver.get(GOOGLE_URL);
        javascriptExecutor.executeScript("window.open()");
        return new ArrayList<>(webDriver.getWindowHandles());
    }

    public void getUrl(String Url, String tab){
        switchTab(tab);
        webDriver.get(Url);
    }

    public List<WebElement> getElementListByXpath(String findElement){
        javascriptExecutor.executeScript("window.scrollBy(0,600)");
        return webDriver.findElements(By.xpath(findElement));
    }

    public void waitUntilElementVisible(String element){
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(element)));
    }

    public void scroll(){
        javascriptExecutor.executeScript("window.scrollBy(0,300)");
    }

    public String getTextByElement(String element){
        return webDriver.findElement(By.xpath(element)).getText();
    }

    public String getTextByElementAttribute(String element, String attribute){
        return webDriver.findElement(By.xpath(element)).getAttribute(attribute);
    }

    public void close(){
        webDriver.close();
    }
}
