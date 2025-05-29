package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import ge.tbc.testautomation.data.enums.Meals;
import ge.tbc.testautomation.data.enums.PropertyRating;
import ge.tbc.testautomation.data.enums.PropertyType;

public class HomePage {
    private final  Page page;
    public Locator searchBar,
    searchButton,
    calendar,
    listBox,
    navBar,
    hamburgerMenu,
    footerLinks,
    header;

    public HomePage(Page page)
    {
        this.page = page;
        this.searchBar = page.getByPlaceholder("Where are you going?");
        this.searchButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search"));
        this.calendar = page.locator("//button[@data-testid='searchbox-dates-container']");
        this.listBox = page.getByRole(AriaRole.LISTBOX).first();
        this.navBar = page.locator(".c4a6e8e871").first();
        this.hamburgerMenu = page.locator(".fc70cba028.e2a1cd6bfe").first();
        this.footerLinks = page.locator("//ul[@class='footer-top-links-list']/li");
        this.header = page.locator(".Header_main");
    }




    public Locator getPropertyTypeCheckbox(PropertyType propertyType) {
        String xpath = String.format("//div[contains(text(), '%s')]/ancestor::*/span[contains(@class, 'c850687b9b')]",
                propertyType.getLabel());
        return page.locator(xpath).first();
    }

    public Locator getPropertyRatingCheckbox(PropertyRating rating) {
        String xpath = String.format("//div[contains(text(), '%s')]/ancestor::*/span[contains(@class, 'c850687b9b')]",
                rating.getLabel());
        return page.locator(xpath).first();
    }

    public Locator getMealsCheckbox(Meals meals) {
        String xpath = String.format("//div[contains(text(), '%s')]/ancestor::*/span[contains(@class, 'c850687b9b')]",
                meals.getLabel());
        return page.locator(xpath).first();
    }
}
