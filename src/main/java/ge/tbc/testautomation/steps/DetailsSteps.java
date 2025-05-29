package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Page;
import ge.tbc.testautomation.data.enums.PropertyType;
import ge.tbc.testautomation.pages.DetailsPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class DetailsSteps {
    Page page;
    DetailsPage detailsPage;

    public DetailsSteps(Page page)
    {
        this.page = page;
        this.detailsPage = new DetailsPage(page);
    }

    public DetailsSteps validateHeaderContainsPropertyType(PropertyType propertyType)
    {
        assertThat(detailsPage.header).containsText(propertyType.getLabel());
        return this;
    }

    public DetailsSteps validateWeAreOnDetailsPage()
    {
        detailsPage.reserveButton.waitFor();
        return this;
    }
}
