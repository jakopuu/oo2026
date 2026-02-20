package ee.jakopuu.veebipood.dto;

public record OrderRowDto(
        Long productId,
        int quantity
) {
}
