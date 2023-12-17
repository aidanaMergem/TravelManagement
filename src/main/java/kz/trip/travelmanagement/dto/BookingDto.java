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
    private long id;
    private long tourId;
    private String tourName;
    private long userId;
    private String userName;
    private Date bookingDateTime;
    private String bookingStatusValue;

}
