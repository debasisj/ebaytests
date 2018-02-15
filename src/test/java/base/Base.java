package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.UnexpectedException;

public class Base {
    private ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();


    @DataProvider(name = "browsersToTest", parallel = true)
    public static Object[][] browserDataProvider(Method testMethod) {
        return new Object[][]{
                new Object[]{"chrome"},
                new Object[]{"firefox"},
        };
    }

    public WebDriver getWebDriver() {
        return webDriver.get();
    }


    @BeforeSuite
    public void startHubNode() throws IOException {
        String path=System.getProperty("user.dir");
        path =  path+"\\src\\main\\resources\\";

        Runtime.getRuntime().exec("cmd /c start "+path+ "StartHub.bat");
        System.out.println("Hub Started");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Runtime.getRuntime().exec("cmd /c start "+ path+"\\StartNode.bat");
        System.out.println("Node Started");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    protected void createDriver(String browser)
            throws MalformedURLException, UnexpectedException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);

        webDriver.set(new RemoteWebDriver(
                new URL("http://localhost:4444/wd/hub"),
                capabilities));


    }

    @AfterMethod
    public void tearDown() throws Exception {
        webDriver.get().quit();
    }


}
