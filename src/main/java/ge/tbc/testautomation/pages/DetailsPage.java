package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class DetailsPage {
    public Locator header,
    reserveButton;
    public DetailsPage(Page page)
    {
        this.header = page.locator("//h2").first();
        this.reserveButton = page.locator("//span[@class='bui-button__text' and normalize-space(text())='Reserve']").first();
    }
}
