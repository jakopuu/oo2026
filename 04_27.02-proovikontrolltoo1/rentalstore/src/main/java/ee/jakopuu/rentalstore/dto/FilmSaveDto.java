package ee.jakopuu.rentalstore.dto;

import ee.jakopuu.rentalstore.entity.FilmType;

public record FilmSaveDto(
        String title,
        FilmType type
) {
}
