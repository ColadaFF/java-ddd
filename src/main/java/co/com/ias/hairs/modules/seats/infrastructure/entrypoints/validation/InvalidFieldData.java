package co.com.ias.hairs.modules.seats.infrastructure.entrypoints.validation;

public record InvalidFieldData(
        String fieldName,
        String errorMessage
) {
}
