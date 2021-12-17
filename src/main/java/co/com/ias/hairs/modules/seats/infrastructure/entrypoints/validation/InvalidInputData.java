package co.com.ias.hairs.modules.seats.infrastructure.entrypoints.validation;

import java.util.List;

public record InvalidInputData(
        List<InvalidFieldData> errors
) {
}
