package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.AriaRole;
import ge.tbc.testautomation.data.enums.Meals;
import ge.tbc.testautomation.data.enums.PropertyRating;
import ge.tbc.testautomation.data.enums.PropertyType;
import ge.tbc.testautomation.pages.HomePage;

import java.sql.Date;
import java.util.Calendar;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class HomeSteps {
    Page page;
    HomePage homePage;

    public HomeSteps(Page page) {
        this.page = page;
        homePage = new HomePage(page);
    }

    public HomeSteps searchLocation(String location)
    {
        homePage.searchBar.fill(location);
        return this;
    }

    public HomeSteps clickSearchButton()
    {
        homePage.searchButton.click();
        return this;
    }

    public HomeSteps clickOnCalendar()
    {
        homePage.calendar.click();
        return this;
    }

    public HomeSteps selectLocationOption(String option)
    {
        homePage.listBox.getByText(option).first().click();
        return this;
    }

    public HomeSteps selectTravelDates(Date checkInDate, Date checkOutDate) {
        page.locator("//button[@aria-label='Next month']").click();
        Calendar cal = Calendar.getInstance();

        cal.setTime(checkInDate);
        String checkInDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));

        cal.setTime(checkOutDate);
        String checkOutDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));

        Locator checkIn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(checkInDay)).first();
        checkIn.click();

        Locator checkOut = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(checkOutDay)).last();
        checkOut.click();

        return this;
    }

    public void watchForPopup(Page page) {
        // Run this in a background thread so it doesn’t block test execution
        new Thread(() -> {
            try {
                Locator closeBtn = page.locator("[aria-label='Dismiss sign-in info.']"); // or the actual selector

                // Wait until the popup becomes visible (timeout in ms, adjust as needed)
                closeBtn.waitFor(new Locator.WaitForOptions().setTimeout(30000));

                // If visible, click to dismiss
                if (closeBtn.isVisible()) {
                    closeBtn.click();
                    System.out.println("Sign-in popup dismissed.");
                }
            } catch (PlaywrightException e) {
                // Ignore if not shown within timeout
            }
        }).start();
    }

    public HomeSteps selectPropertyType(PropertyType propertyType) {
        Locator checkbox = homePage.getPropertyTypeCheckbox(propertyType);
        checkbox.scrollIntoViewIfNeeded();
        checkbox.check();
        return this;
    }
    public HomeSteps selectPropertyRating(PropertyRating propertyRating) {
        Locator checkbox = homePage.getPropertyRatingCheckbox(propertyRating);
        checkbox.scrollIntoViewIfNeeded();
        checkbox.check();
        return this;
    }

    public HomeSteps meals(Meals meals) {
        Locator checkbox = homePage.getMealsCheckbox(meals);
        checkbox.scrollIntoViewIfNeeded();
        checkbox.check();
        return this;
    }

    public HomeSteps waitForLoadState()
    {
        page.waitForLoadState();
        return this;
    }

   public HomeSteps validateNavBarIsVisible()
   {
       assertThat(homePage.navBar).isVisible();
       return this;
   }

   public HomeSteps validateHamburgerMenuIsVisible()
   {
       assertThat(homePage.hamburgerMenu).isVisible();
       return this;
   }
   public HomeSteps validateHamburgerMenuIsNotVisible()
   {
       assertThat(homePage.hamburgerMenu).isHidden();
       return this;
   }

    public HomeSteps validateFooterLinksAreHorizontal()
    {
        Locator footerLinks = homePage.footerLinks;

        int count = footerLinks.count();

        if (count < 2) {
            throw new AssertionError("Not enough footer links to validate horizontal alignment");
        }

        double referenceY = footerLinks.nth(0).boundingBox().y;
        int tolerance = 45;

        for (int i = 1; i < count; i++) {
            double currentY = footerLinks.nth(i).boundingBox().y;
            double difference = Math.abs(currentY - referenceY);
            if (difference > tolerance) {
                throw new AssertionError(
                        String.format("Footer link at index %d is not horizontally aligned. " +
                                        "Y-coordinate difference: %.2f pixels (exceeds tolerance of %d pixels)",
                                i, difference, tolerance)
                );
            }
        }

        return this;
    }

    public HomeSteps validateSearchBarIsVisible()
    {
        assertThat(homePage.searchBar).isVisible();
        return this;
    }

    public HomeSteps validateHeaderDisappearsWhenScrolling() {
        // First check if header is visible
        homePage.header.waitFor();
        assertThat(homePage.header).isVisible();

        // Scroll down enough to move past the header
        page.evaluate("window.scrollBy(0, 500)");

        // Wait a moment for scroll to complete
        page.waitForTimeout(500);

        // Check if header is no longer in the viewport
        String isHeaderVisibleScript = "() => {" +
                "  const header = document.querySelector('.Header_main');" +
                "  if (!header) return false;" +
                "  const rect = header.getBoundingClientRect();" +
                "  return rect.bottom > 0 && rect.top < window.innerHeight;" +
                "}";

        Boolean isHeaderVisible = (Boolean) page.evaluate(isHeaderVisibleScript);

        if (isHeaderVisible) {
            throw new AssertionError("Header is still visible after scrolling, suggesting it might be sticky");
        }

        System.out.println("Header correctly disappears from view when scrolling");

        // Scroll back to the top
        page.evaluate("window.scrollTo(0, 0)");

        return this;
    }
}
