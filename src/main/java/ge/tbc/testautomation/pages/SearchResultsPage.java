package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class SearchResultsPage {

    public Locator propertyCards,
    searchHeader,
    dismissButton,
    alert,
    sortButton,
    propertyRating,
    propertyCardTitle;
    public SearchResultsPage(Page page)
    {
        this.propertyCards = page.locator("//div[@data-testid='property-card']");
        this.searchHeader = page.locator(".b87c397a13.cacb5ff522");
        this.dismissButton = page.locator("#b2searchresultsPage");
        this.alert = page.getByText("Sign in, save money");
        this.sortButton = page.locator(".cd46a6a263");
        this.propertyRating = page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("Top reviewed"));
        this.propertyCardTitle = page.locator(".b87c397a13.a3e0b4ffd1").first();

    }
}
