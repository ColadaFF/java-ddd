package co.com.ias.hairs.modules.seats.infrastructure.entrypoints;

import co.com.ias.hairs.modules.seats.core.domain.DayShift;
import co.com.ias.hairs.modules.seats.core.domain.DayShiftCommandError;
import co.com.ias.hairs.modules.seats.core.services.CreateAppointmentService;
import co.com.ias.hairs.modules.seats.infrastructure.entrypoints.model.CreateAppointmentInput;
import co.com.ias.hairs.modules.seats.infrastructure.entrypoints.model.CreateAppointmentOutput;
import co.com.ias.hairs.modules.seats.infrastructure.entrypoints.model.CreateAppointmentValidated;
import co.com.ias.hairs.modules.seats.infrastructure.entrypoints.validation.CreateAppointmentValidation;
import co.com.ias.hairs.modules.seats.infrastructure.entrypoints.validation.InvalidInputData;
import io.vavr.control.Either;
import io.vavr.control.Validation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/seats")
@AllArgsConstructor
public class SeatsController {
    private final CreateAppointmentService createAppointmentService;


    @PostMapping("/{seatNumber}/appointments/{date}")
    public ResponseEntity<Object> createAppointment(
            @PathVariable("seatNumber") String unsafeSeatNumber,
            @PathVariable("date") String unsafeDate,
            @RequestBody CreateAppointmentInput unsafeInput
    ) {
        Validation<InvalidInputData, CreateAppointmentValidated> validation = CreateAppointmentValidation.validate(
                unsafeSeatNumber,
                unsafeDate,
                unsafeInput
        );


        if (validation.isValid()) {
            CreateAppointmentValidated validated = validation.get();
            Either<DayShiftCommandError, DayShift> result = createAppointmentService.execute(
                    validated.seatNumber(),
                    validated.date(),
                    validated.appointment()
            );
            if (result.isRight()) {

                CreateAppointmentOutput output = new CreateAppointmentOutput(
                        validated.seatNumber(),
                        validated.date(),
                        validated.appointment()
                );
                return ResponseEntity.ok(output);
            } else {
                DayShiftCommandError errorValue = result.getLeft();
                Map<String, Object> errorResponse = Map.of(
                        "error", errorValue
                );
                return ResponseEntity.badRequest()
                        .body(errorResponse);
            }


        } else {
            InvalidInputData error = validation.getError();
            return ResponseEntity.badRequest()
                    .body(error);

        }
    }
}
