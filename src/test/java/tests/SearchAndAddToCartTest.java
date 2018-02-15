package tests;


import base.Base;
import base.WaitHelper;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.SearchAndAddToCartPage;

import java.net.MalformedURLException;
import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchAndAddToCartTest extends Base{

    @Test(dataProvider = "browsersToTest")
    public void testCart(String browser) throws InterruptedException, MalformedURLException, UnexpectedException {
        this.createDriver(browser);
        WebDriver driver = this.getWebDriver();
        driver.manage().window().maximize();

        Thread.sleep(2000);
        SearchAndAddToCartPage page = new SearchAndAddToCartPage(driver);
        ArrayList<String> idsBeforeAddToCart = new ArrayList<String>();
        ArrayList<String> idsAfterAddToCart = new ArrayList<String>();
        List<String> itemsToSearch = Arrays.asList("iPhone 8", "lawn Mower");
        driver.navigate().to("https://www.ebay.com.au/");

        for (int i=0; i<itemsToSearch.size();i++) {


            page.serachItem(itemsToSearch.get(i));
            WaitHelper.waitUntilAnElementIsVisibleByCss(driver,"span[title = 'All listings'",10);

            idsBeforeAddToCart.add(page.getiid());

            page.clickOnFirstItemAndAddToCart();


            page.clickOnCartButton();

            WaitHelper.waitUntilAnElementIsVisibleByCss(driver,"h1.mb15",5);
            String cssPath = ".c-std>div:nth-of-type(1) div[data-displayorder='1']";

            idsAfterAddToCart.add(page.getAddedItemIdFromCart(cssPath));

        }
        Assert.assertEquals(idsBeforeAddToCart, idsAfterAddToCart);

    }
}
