package com.starAndShadow.may.sakila.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class FilmDTO {
    private Integer filmId;
    private String title;
    private String description;
    private Integer releaseYear;
    private String language;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer languageId;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer originalLanguageId;
    private Integer rentalDuration;
    private BigDecimal rentalRate;
    private Integer length;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private BigDecimal replacementCost;
    private String rating;
    private String[] specialFeatures;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String lastUpdate;
    private List cast;
    private Map<String, List<Integer>> inventory;
    private List category;
}
