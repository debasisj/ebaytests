package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchAndAddToCartPage {
    public WebDriver driver;

    @FindBy(id = "gh-ac")
    private WebElement serachBox;

    @FindBy(css = "#gh-btn")
    private WebElement btnSearch;

    @FindBy(css = "#ListViewInner > li:nth-of-type(1) > div:nth-of-type(1)")
    private WebElement firstListItemId;

    @FindBy(css = "#ListViewInner > li:nth-of-type(1) > div:nth-of-type(1) a:nth-of-type(1)")
    private WebElement firstListItem;

    @FindBy(css = "#isCartBtn_btn")
    private WebElement addToCartBtn;


    @FindBy(xpath = "//button[text()='No thanks']")
    private WebElement noThanksBtn;

    @FindBy(id = "gh-cart-i")
    private WebElement cartBtn;

    public SearchAndAddToCartPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this    );
    }


    public void serachItem(String item){
        serachBox.sendKeys(item);
        btnSearch.click();
    }

    public String getiid(){
        return firstListItemId.getAttribute("iid");

    }

    public void clickOnFirstItemAndAddToCart(){
        firstListItem.click();
        addToCartBtn.click();
        if(driver.findElements(By.xpath("//button[text()='No thanks']")).size()>0) {
            noThanksBtn.click();
        }
    }

    public void clickOnCartButton(){
        cartBtn.click();
    }

    public String getAddedItemIdFromCart(String cssPath){
        return driver.findElement(By.cssSelector(cssPath)).getAttribute("data-itemid");
    }

}
