package ge.tbc.testautomation.data;

import ge.tbc.testautomation.config.DataBaseConfig;
import ge.tbc.testautomation.data.models.BookingCase;
import ge.tbc.testautomation.mappers.BookingCaseMapper;
import org.testng.annotations.DataProvider;

import java.util.List;

public class DataSupplier {

    @DataProvider(name = "bookingTestData")
    public static Object[][] getBookingTestData() {
        BookingCaseMapper mapper = DataBaseConfig.dbMapper();
        List<BookingCase> bookingCases = mapper.findAll();
        Object[][] data = new Object[bookingCases.size()][1];
        for (int i = 0; i < bookingCases.size(); i++) {
            data[i][0] = bookingCases.get(i);
        }

        return data;
    }

}