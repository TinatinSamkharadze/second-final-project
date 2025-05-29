package ge.tbc.testautomation;

import com.microsoft.playwright.*;
import ge.tbc.testautomation.steps.DetailsSteps;
import ge.tbc.testautomation.steps.HomeSteps;
import ge.tbc.testautomation.steps.SearchResultsSteps;
import org.testng.annotations.*;

import java.util.Arrays;

import static ge.tbc.testautomation.data.Constants.BOOKING_BASE_URL;

public class BaseTest {
    public Playwright playwright;
    public Browser browser;
    public BrowserContext browserContext;
    public Page page;
    public HomeSteps homeSteps;
    public SearchResultsSteps searchResultsSteps;
    public DetailsSteps detailsSteps;


    @BeforeClass
    @Parameters({"browserType"})
    public void setUp(@Optional("chromium") String browserType){
        playwright = Playwright.create();
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        launchOptions.setArgs(Arrays.asList("--disable-gpu", "--disable-extensions", "--start-maximized"));
        launchOptions.setHeadless(false);

        if (browserType.equalsIgnoreCase("chromium")){
            browser = playwright.chromium().launch(launchOptions);
        } else if (browserType.equalsIgnoreCase("safari")) {
            browser = playwright.webkit().launch(launchOptions);
        }
        browserContext = browser.newContext();
        browserContext.onDialog(dialog -> {
            System.out.println("Dialog appeared: " + dialog.message());
            dialog.dismiss();
        });

        page = browserContext.newPage();
    }

    @BeforeMethod
    public void resetContext() {
        page.navigate(BOOKING_BASE_URL);
        this.homeSteps = new HomeSteps(page);
        this.searchResultsSteps = new SearchResultsSteps(page);
        this.detailsSteps = new DetailsSteps(page);
    }


    @AfterClass
    public void tearDown() {
        browserContext.close();
        browser.close();
        playwright.close();
    }


    @AfterMethod
    public void tearDownPerTest() {
    }
}