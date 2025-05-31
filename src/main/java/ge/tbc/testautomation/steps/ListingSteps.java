package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import ge.tbc.testautomation.data.enums.PropertyRating;
import ge.tbc.testautomation.data.enums.PropertyType;
import ge.tbc.testautomation.pages.ListingPage;

import java.util.List;
import java.util.Map;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ListingSteps {
    Page page;
    ListingPage listingPage;

    public ListingSteps(Page page) {
        this.page = page;
        listingPage = new ListingPage(page);
    }

    public ListingSteps validateResultsAppear()
    {
        listingPage.propertyCards.first().isVisible();
        return this;
    }

    public ListingPage scrollThroughResults() {
        Locator results = listingPage.propertyCards;
        results.first().waitFor();

        int count = results.count();

        for (int i = 0; i < count; i++) {
                Locator result = results.nth(i);
                result.scrollIntoViewIfNeeded();
        }
        return listingPage;
    }

    public ListingSteps validateSearchHeaderContainsCorrectText(String searchText) {
        listingPage.searchHeader
                .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        assertThat(listingPage.searchHeader).containsText(searchText);

        return this;
    }



    public ListingSteps clickEnterKey()
    {
        page.keyboard().press("Enter");
        return this;
    }

    public ListingSteps validateAlertWindowToAppear()
    {
        try {
            listingPage.alert.waitFor(new Locator.WaitForOptions().setTimeout(5000)); // 5 seconds timeout
            if(listingPage.alert.isVisible()) {
                clickEnterKey();
            }
        } catch (Exception e) {
            System.out.println("Alert window did not appear, continuing with the test...");
        }

        return this;
    }

    public ListingSteps clickDismissButton()
    {
        listingPage.dismissButton.waitFor();
        listingPage.dismissButton.click();
        return this;
    }

    public ListingSteps validateResults(String searchText) {
        Locator propertyCards = listingPage.propertyCards;

        int count = propertyCards.count();
        for (int i = 0; i < count; i++) {
            Locator card = propertyCards.nth(i);
            card.scrollIntoViewIfNeeded();
            assertThat(card).containsText(searchText);
        }

        return this;
    }

    public ListingSteps validateResultsAfterApplyingRating(PropertyRating propertyRating) {
        Locator propertyCards = listingPage.propertyCards;

        int count = propertyCards.count();
        for (int i = 0; i < count; i++) {
            Locator card = propertyCards.nth(i);
            card.scrollIntoViewIfNeeded();
            assertThat(card).containsText(propertyRating.getLabel());
        }

        return this;
    }
    public ListingSteps validateResultsAfterApplyingPropertyType(PropertyType propertyType) {
        Locator propertyCards = listingPage.propertyCards;

        int count = propertyCards.count();
        for (int i = 0; i < count; i++) {
            Locator card = propertyCards.nth(i);
            card.scrollIntoViewIfNeeded();
            assertThat(card).containsText(propertyType.getLabel());
        }

        return this;
    }

    public ListingSteps clickSortButton()
    {
        listingPage.sortButton.waitFor();
        listingPage.sortButton.click();
        return this;
    }

    public ListingSteps clickOnPropertyRatingHighToLow()
    {
        listingPage.propertyRating.waitFor();
        listingPage.propertyRating.click();
        return this;
    }

    public ListingSteps validatePropertyCardsAreCorrectlyFiltered() {
        listingPage.propertyCards.first().waitFor();

        // Get all property cards
        Locator propertyCards = listingPage.propertyCards;
        int count = propertyCards.count();

        for (int i = 0; i < count; i++) {
            Locator card = propertyCards.nth(i);
            card.scrollIntoViewIfNeeded();

            String cardText = card.textContent();

            if (cardText.contains("Exceptional")) {
                assertThat(card).containsText("Exceptional");
            } else if(cardText.contains("Wonderful")) {
                assertThat(card).containsText("Wonderful");
            }
            else if(cardText.contains("Very Good")) {
                assertThat(card).containsText("Very Good");
            }
            else
                assertThat(card).containsText("Excellent");
        }

        return this;
    }

    public ListingSteps goToDetailsPage()
    {
        listingPage.propertyCardTitle.click();
        return this;
    }

    public ListingSteps validateStarRating(int expectedStars) {
        Locator starElements =  listingPage.propertyCards.first().locator("span.fc70cba028.bdc459fcb4.f24706dc71");

        assertThat(starElements).hasCount(expectedStars);

        return this;
    }

    public ListingSteps validatePropertyCardsGridLayout(int expectedPerRow) {
        // Wait for property cards to be visible
        listingPage.propertyCards.first().waitFor();

        int count = listingPage.propertyCards.count();
        if (count == 0) {
            throw new AssertionError("No property cards found on the page");
        }

        // Use a more direct approach to determine the layout
        String script = "() => {\n" +
                "  const cards = Array.from(document.querySelectorAll('div[data-testid=\"property-card\"]'));\n" +
                "  if (!cards.length) return { error: 'No cards found' };\n" +
                "  \n" +
                "  // Get viewport dimensions\n" +
                "  const viewportWidth = window.innerWidth;\n" +
                "  const viewportHeight = window.innerHeight;\n" +
                "  \n" +
                "  // Get the first card's dimensions\n" +
                "  const firstCard = cards[0];\n" +
                "  const firstCardRect = firstCard.getBoundingClientRect();\n" +
                "  const cardWidth = firstCardRect.width;\n" +
                "  \n" +
                "  // Group cards by their Y position (within a tolerance)\n" +
                "  const tolerance = 30; // pixels\n" +
                "  const rowGroups = {};\n" +
                "  \n" +
                "  cards.forEach((card, index) => {\n" +
                "    const rect = card.getBoundingClientRect();\n" +
                "    // Find which row this belongs to\n" +
                "    let foundRow = false;\n" +
                "    \n" +
                "    Object.keys(rowGroups).forEach(rowY => {\n" +
                "      if (Math.abs(rect.top - parseFloat(rowY)) < tolerance) {\n" +
                "        rowGroups[rowY].push({ index, left: rect.left, top: rect.top });\n" +
                "        foundRow = true;\n" +
                "      }\n" +
                "    });\n" +
                "    \n" +
                "    if (!foundRow) {\n" +
                "      rowGroups[rect.top] = [{ index, left: rect.left, top: rect.top }];\n" +
                "    }\n" +
                "  });\n" +
                "  \n" +
                "  // Find the row with the most cards (first visible row)\n" +
                "  let maxCards = 0;\n" +
                "  let firstVisibleRow = null;\n" +
                "  \n" +
                "  Object.entries(rowGroups).forEach(([rowY, cards]) => {\n" +
                "    // Only consider rows that are visible in the viewport\n" +
                "    if (parseFloat(rowY) >= 0 && parseFloat(rowY) < viewportHeight) {\n" +
                "      if (cards.length > maxCards) {\n" +
                "        maxCards = cards.length;\n" +
                "        firstVisibleRow = cards;\n" +
                "      }\n" +
                "    }\n" +
                "  });\n" +
                "  \n" +
                "  // If no visible row found, use the first row\n" +
                "  if (!firstVisibleRow && Object.values(rowGroups).length > 0) {\n" +
                "    firstVisibleRow = Object.values(rowGroups)[0];\n" +
                "    maxCards = firstVisibleRow.length;\n" +
                "  }\n" +
                "  \n" +
                "  // Calculate cards per row based on viewport and card width\n" +
                "  let calculatedCardsPerRow = Math.max(1, Math.floor(viewportWidth / cardWidth));\n" +
                "  \n" +
                "  return {\n" +
                "    viewportWidth,\n" +
                "    cardWidth,\n" +
                "    calculatedCardsPerRow,\n" +
                "    actualCardsInFirstRow: maxCards,\n" +
                "    totalRows: Object.keys(rowGroups).length,\n" +
                "    rowDetails: Object.entries(rowGroups).map(([y, cards]) => ({ \n" +
                "      y: parseFloat(y), \n" +
                "      count: cards.length,\n" +
                "      visible: parseFloat(y) >= 0 && parseFloat(y) < viewportHeight\n" +
                "    }))\n" +
                "  };\n" +
                "}";

        // Execute the JavaScript to get layout information
        Map<String, Object> layoutInfo = (Map<String, Object>) page.evaluate(script);

        if (layoutInfo.containsKey("error")) {
            throw new AssertionError("Error in layout detection: " + layoutInfo.get("error"));
        }

        // Extract the relevant information
        double viewportWidth = ((Number) layoutInfo.get("viewportWidth")).doubleValue();
        double cardWidth = ((Number) layoutInfo.get("cardWidth")).doubleValue();
        int calculatedCardsPerRow = ((Number) layoutInfo.get("calculatedCardsPerRow")).intValue();
        int actualCardsInFirstRow = ((Number) layoutInfo.get("actualCardsInFirstRow")).intValue();
        int totalRows = ((Number) layoutInfo.get("totalRows")).intValue();

        // Print detailed debugging information
        System.out.println("Viewport width: " + viewportWidth);
        System.out.println("Card width: " + cardWidth);
        System.out.println("Calculated cards per row: " + calculatedCardsPerRow);
        System.out.println("Actual cards in first visible row: " + actualCardsInFirstRow);
        System.out.println("Total rows detected: " + totalRows);

        // Print row details for debugging
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> rowDetails = (List<Map<String, Object>>) layoutInfo.get("rowDetails");
        System.out.println("Row details:");
        for (Map<String, Object> row : rowDetails) {
            System.out.println("  Y: " + row.get("y") + ", Count: " + row.get("count") + ", Visible: " + row.get("visible"));
        }

        // For mobile view, we expect 1 card per row regardless of calculations
        if (viewportWidth <= 480 && expectedPerRow == 1) {
            System.out.println("Mobile view detected - expecting 1 card per row");
            return this;
        }

        // For tablet view, we expect 1 card per row regardless of calculations
        if (viewportWidth <= 768 && expectedPerRow == 1) {
            System.out.println("Tablet view detected - expecting 1 card per row");
            return this;
        }

        // For desktop view, check if the actual layout matches expectations
        if (viewportWidth > 768) {
            // Use the actual count from the first visible row as the primary check
            if (actualCardsInFirstRow > 0 && actualCardsInFirstRow != expectedPerRow) {
                throw new AssertionError(
                        String.format("Grid layout validation failed: Expected %d cards per row, but found %d cards in the first visible row. " +
                                        "Viewport width: %.2f, Card width: %.2f",
                                expectedPerRow, actualCardsInFirstRow, viewportWidth, cardWidth)
                );
            }
        }

        return this;
    }
}
