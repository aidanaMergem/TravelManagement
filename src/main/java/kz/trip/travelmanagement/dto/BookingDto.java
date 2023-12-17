package kz.trip.travelmanagement.dto;

import kz.trip.travelmanagement.models.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingDto {
    private int id;
    private int tourId;
    private int userId;
    private Date bookingDateTime;
    private BookingStatus bookingStatus;

}
