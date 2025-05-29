package ge.tbc.testautomation.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingCase {
    private int id;
    private String destination;
    private Date checkIn;
    private Date checkOut;
    private int guests;
}
