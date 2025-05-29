package ge.tbc.testautomation.mappers;

import ge.tbc.testautomation.data.models.BookingCase;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface BookingCaseMapper {

    @Select("SELECT * FROM booking_cases")
    List<BookingCase> findAll();

    @Select("SELECT * FROM booking_cases WHERE id = #{id}")
    BookingCase findById(int id);

    @Insert("INSERT INTO booking_cases (destination, check_in, check_out, guests) " +
            "VALUES (#{destination}, #{checkIn}, #{checkOut}, #{guests})")
    void insert(BookingCase bookingCase);

    @Update("UPDATE booking_cases SET destination=#{destination}, check_in=#{checkIn}, " +
            "check_out=#{checkOut}, guests=#{guests} WHERE id=#{id}")
    void update(BookingCase bookingCase);

    @Delete("DELETE FROM booking_cases WHERE id = #{id}")
    void delete(int id);
}
