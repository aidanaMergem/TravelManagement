package kz.trip.travelmanagement.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<TourTranslation> translations = new ArrayList<>();
    private int durationInDays;
    private Double price;
    private LocalDate startDate;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String tourType;
    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> reviews = new ArrayList<Booking>();



    public String getTranslatedTourName(String language) {
        return getTranslation(language, "tourName");
    }

    // Getter for translated description
    public String getTranslatedDescription(String language) {
        return getTranslation(language, "description");
    }

    private String getTranslation(String language, String fieldName) {
        return translations.stream()
                .filter(translation -> language.equals(translation.getLanguage()))
                .findFirst()
                .map(translation -> getFieldByPropertyName(fieldName, translation))
                .orElse(fieldName.equals("tourName") ? "" : null);
    }

    private String getFieldByPropertyName(String fieldName, TourTranslation translation) {
        switch (fieldName) {
            case "tourName":
                return translation.getTourName();
            case "description":
                return translation.getDescription();
            default:
                return null;
        }
    }

    public void addTranslation(Set<TourTranslation> translations) {
        this.translations.addAll(translations);
    }

    public void addTranslation(TourTranslation translation) {
        if (translation != null) {
            translation.setTour(this);
            this.translations.add(translation);
        }
    }


}
