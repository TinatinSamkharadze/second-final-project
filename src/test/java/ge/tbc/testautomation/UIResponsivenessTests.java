package ge.tbc.testautomation;

import ge.tbc.testautomation.data.DataSupplier;
import ge.tbc.testautomation.data.models.BookingCase;
import ge.tbc.testautomation.utils.ViewPortUtil;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.data.Constants.*;

public class UIResponsivenessTests extends BaseTest{
    @Test(dataProvider = "bookingTestData", dataProviderClass = DataSupplier.class)
    public void desktopResponsiveTest(BookingCase bookingCase) {
        ViewPortUtil.setViewportSize(page, WIDTH_FOR_DESKTOP, HEIGHT_FOR_DESKTOP);
        homeSteps
                .validateNavBarIsVisible()
                .validateSearchBarIsVisible()
                .searchLocation(bookingCase.getDestination())
                .selectLocationOption(bookingCase.getDestination())
                .clickOnCalendar()
                .clickSearchButton()
                .validateHamburgerMenuIsNotVisible()
                .validateHeaderDisappearsWhenScrolling();
        listingSteps
                .validatePropertyCardsGridLayout(1);
    }

    @Test(dataProvider = "bookingTestData", dataProviderClass = DataSupplier.class)
    public void tabletResponsiveTest(BookingCase bookingCase) {
        ViewPortUtil.setViewportSize(page, WIDTH_FOR_TABLET, HEIGHT_FOR_TABLET);
        homeSteps
                .validateNavBarIsVisible()
                .validateSearchBarIsVisible()
                .searchLocation(bookingCase.getDestination())
                .selectLocationOption(bookingCase.getDestination())
                .clickOnCalendar()
                .clickSearchButton()
                .validateHamburgerMenuIsVisible()
                .validateFooterLinksAreHorizontal()
                .validateHeaderDisappearsWhenScrolling();
        listingSteps
                .validatePropertyCardsGridLayout(1);
    }

    @Test(dataProvider = "bookingTestData", dataProviderClass = DataSupplier.class)
    public void mobileResponsiveTest(BookingCase bookingCase) {
       ViewPortUtil.setViewportSize(page, WIDTH_FOR_MOBILE, HEIGHT_FOR_MOBILE);
        homeSteps
                .validateNavBarIsVisible()
                .validateSearchBarIsVisible()
                .searchLocation(bookingCase.getDestination())
                .selectLocationOption(bookingCase.getDestination())
                .clickOnCalendar()
                .clickSearchButton()
                .validateHamburgerMenuIsVisible()
                .validateFooterLinksAreHorizontal()
                .validateHeaderDisappearsWhenScrolling();
        listingSteps
                .validatePropertyCardsGridLayout(1);
    }
}
