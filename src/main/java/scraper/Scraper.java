package scraper;

import model.Model;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import webdriver.CustomWebDriver;

import java.util.ArrayList;
import java.util.List;

public class Scraper {

    private static final String URL = "https://www.tokopedia.com/search?st=product&q=handphone";
    private static final String ADS_URL = "https://ta.tokopedia.com/promo";

    private static final String XPATH_PRODUCT_LIST = "//div[@data-testid='master-product-card']/div";
    private static final String XPATH_PRODUCT_NAME = "//h1[@data-testid='lblPDPDetailProductName']";
    private static final String XPATH_PRODUCT_DESCRIPTION = "//div[@data-testid='lblPDPDescriptionProduk']";
    private static final String XPATH_PRODUCT_IMG_LINK = "//div[@data-testid='PDPImageMain']//img";
    private static final String XPATH_PRODUCT_PRICE = "//*[@data-testid='lblPDPDetailProductPrice']";
    private static final String XPATH_PRODUCT_RATING = "//span[@data-testid='lblPDPDetailProductRatingNumber']";
    private static final String XPATH_MERCHANT_NAME = "//*[@data-testid='llbPDPFooterShopName']//h2";

    private static int MAX = 100;

    public List<Model> getModels() {
        System.out.println("start scraping...");
        CustomWebDriver webDriver = new CustomWebDriver();
        List<String> tabs = webDriver.prepareTwoTabs();
        webDriver.getUrl(URL, tabs.get(0));
        List<Model> models = new ArrayList<>();
        List<String> visitedLinks = new ArrayList<>();
        try {
            while (models.size() < MAX) {
                List<WebElement> webElements = webDriver.getElementListByXpath(XPATH_PRODUCT_LIST);
                for (WebElement webElement : webElements) {
                    try {
                        String productLink = webElement.findElement(By.tagName("a")).getAttribute("href");
                        if (!productLink.startsWith(ADS_URL) && !visitedLinks.contains(productLink)) {
                            System.out.println(productLink);
                            webDriver.getUrl(productLink, tabs.get(1));
                            webDriver.scroll();
                            webDriver.waitUntilElementVisible(XPATH_PRODUCT_NAME);
                            String name = webDriver.getTextByElement(XPATH_PRODUCT_NAME);
                            webDriver.waitUntilElementVisible(XPATH_PRODUCT_DESCRIPTION);
                            String description = webDriver.getTextByElement(XPATH_PRODUCT_DESCRIPTION);
                            webDriver.waitUntilElementVisible(XPATH_PRODUCT_IMG_LINK);
                            String imageLink = webDriver.getTextByElementAttribute(
                                    XPATH_PRODUCT_IMG_LINK, "src");
                            webDriver.waitUntilElementVisible(XPATH_PRODUCT_PRICE);
                            int price = Integer.parseInt(
                                    webDriver.getTextByElement(XPATH_PRODUCT_PRICE)
                                            .replace("Rp", "").replace(".", ""));
                            webDriver.waitUntilElementVisible(XPATH_PRODUCT_RATING);
                            String rating = webDriver.getTextByElement(XPATH_PRODUCT_RATING);
                            webDriver.waitUntilElementVisible(XPATH_MERCHANT_NAME);
                            String merchant = webDriver.getTextByElement(XPATH_MERCHANT_NAME);

                            Model model = Model.builder()
                                    .name(name)
                                    .description(description)
                                    .imageLink(imageLink)
                                    .price(price)
                                    .rating(rating)
                                    .merchant(merchant)
                                    .build();

                            models.add(model);
                            System.out.println("size = " + models.size());

                            if (models.size() == MAX) {
                                break;
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("exception when finding element, retrying...");
                        webDriver.switchTab(tabs.get(0));
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("exception during scraping...");
            e.printStackTrace();
        } finally {
            webDriver.close();
        }
        return models;
    }
}
