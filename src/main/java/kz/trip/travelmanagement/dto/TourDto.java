package kz.trip.travelmanagement.dto;

import kz.trip.travelmanagement.models.TourTranslation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TourDto {
    private long id;
    private String tourName;
    private String description;
    private String tourType;
    private int durationInDays;
    private Double price;
    private LocalDate startDate;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;


    private List<TourTranslation> translations = new ArrayList<>();
    public TourDto(String tourName, String description, String tourType, int durationInDays, Double price, LocalDate startDate, LocalDateTime createdAt, LocalDateTime modifiedAt, List<TourTranslation> translations) {
        this.tourName = tourName;
        this.description = description;
        this.tourType = tourType;
        this.durationInDays = durationInDays;
        this.price = price;
        this.startDate = startDate;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.translations = translations;
    }


    public TourDto(String tourName, String description, String tourType, int durationInDays, Double price, LocalDate startDate) {
        this.tourName = tourName;
        this.description = description;
        this.tourType = tourType;
        this.durationInDays = durationInDays;
        this.price = price;
        this.startDate = startDate;
    }
}
