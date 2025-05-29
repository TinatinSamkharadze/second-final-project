package ge.tbc.testautomation;

import ge.tbc.testautomation.data.DataSupplier;
import ge.tbc.testautomation.data.enums.PropertyRating;
import ge.tbc.testautomation.data.enums.PropertyType;
import ge.tbc.testautomation.data.models.BookingCase;
import org.testng.annotations.Test;

public class CoreFunctionalityTests extends BaseTest{

    @Test(dataProvider = "bookingTestData", dataProviderClass = DataSupplier.class)
    public void searchTest(BookingCase bookingCase)
    {
          homeSteps
                  .searchLocation(bookingCase.getDestination())
                  .selectLocationOption(bookingCase.getDestination())
                  .clickSearchButton();
          searchResultsSteps
                  .validateAlertWindowToAppear();
          searchResultsSteps
                  .validateResultsAppear();
          homeSteps
                  .clickOnCalendar();
          searchResultsSteps
                  .validateSearchHeaderContainsCorrectText(bookingCase.getDestination())
                  .validateResults(bookingCase.getDestination());
    }

    @Test(dataProvider = "bookingTestData", dataProviderClass = DataSupplier.class)
    public void dateSelectionTest(BookingCase bookingCase)
    {
        homeSteps
                .searchLocation(bookingCase.getDestination())
                .selectLocationOption(bookingCase.getDestination())
                .selectTravelDates(bookingCase.getCheckIn(), bookingCase.getCheckOut())
                .clickSearchButton();
    }

    @Test(dataProvider = "bookingTestData", dataProviderClass = DataSupplier.class)
    public void filterApplicationTest(BookingCase bookingCase)
    {
        homeSteps
                .searchLocation(bookingCase.getDestination())
                .selectLocationOption(bookingCase.getDestination())
                .clickSearchButton()
                .waitForLoadState()
                .selectPropertyType(PropertyType.VILLAS)
                .selectPropertyRating(PropertyRating.FIVE_STARS);
        searchResultsSteps
                .validateResultsAfterApplyingPropertyType(PropertyType.VILLAS)
                .validateResultsAfterApplyingRating(PropertyRating.FIVE_STARS);
    }

    @Test(dataProvider = "bookingTestData", dataProviderClass = DataSupplier.class)
    public void sortByReviewScoreTest(BookingCase bookingCase)
    {
        homeSteps
                .searchLocation(bookingCase.getDestination())
                .selectLocationOption(bookingCase.getDestination())
                .selectTravelDates(bookingCase.getCheckIn(), bookingCase.getCheckOut())
                .clickSearchButton();
        searchResultsSteps
                .clickSortButton()
                .clickOnPropertyRatingHighToLow()
                .validateResultsAppear()
                .validatePropertyCardsAreCorrectlyFiltered();
    }

    @Test(dataProvider = "bookingTestData", dataProviderClass = DataSupplier.class)
    public void propertyDetailsConsistencyTest(BookingCase bookingCase)
    {
        homeSteps
                .searchLocation(bookingCase.getDestination())
                .selectLocationOption(bookingCase.getDestination())
                .clickSearchButton()
                .waitForLoadState()
                .selectPropertyType(PropertyType.HOTELS)
                .selectPropertyRating(PropertyRating.FIVE_STARS);
        searchResultsSteps
                .validateResultsAfterApplyingPropertyType(PropertyType.HOTELS)
                .validateStarRating(5)
                .goToDetailsPage();
        detailsSteps
                .validateWeAreOnDetailsPage()
                .validateHeaderContainsPropertyType(PropertyType.HOTELS);
    }
}
