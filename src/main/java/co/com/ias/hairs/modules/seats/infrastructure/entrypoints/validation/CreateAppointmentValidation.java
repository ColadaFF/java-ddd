package co.com.ias.hairs.modules.seats.infrastructure.entrypoints.validation;

import co.com.ias.hairs.modules.seats.core.domain.Appointment;
import co.com.ias.hairs.modules.seats.core.domain.ClientId;
import co.com.ias.hairs.modules.seats.core.domain.SeatNumber;
import co.com.ias.hairs.modules.seats.core.domain.ServiceType;
import co.com.ias.hairs.modules.seats.infrastructure.entrypoints.model.CreateAppointmentInput;
import co.com.ias.hairs.modules.seats.infrastructure.entrypoints.model.CreateAppointmentValidated;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import org.checkerframework.checker.units.qual.A;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class CreateAppointmentValidation {


    private static Validation<InvalidFieldData, SeatNumber> validateSeatNumber(String unsafeSeatNumber) {
        try {
            Integer seatNumberAsInt = Integer.parseInt(unsafeSeatNumber);
            SeatNumber seatNumber = new SeatNumber(seatNumberAsInt);
            return Validation.valid(seatNumber);
        } catch (NumberFormatException e) {
            InvalidFieldData invalidFieldData = new InvalidFieldData(
                    "seatNumber",
                    "Seat number value is not a number"
            );
            return Validation.invalid(invalidFieldData);
        } catch (IllegalArgumentException | NullPointerException exception) {
            InvalidFieldData invalidFieldData = new InvalidFieldData(
                    "seatNumber",
                    exception.getMessage()
            );
            return Validation.invalid(invalidFieldData);
        }
    }

    private static Validation<InvalidFieldData, LocalDate> validateDate(String unsafeDate) {
        try {
            LocalDate value = LocalDate.parse(unsafeDate);
            return Validation.valid(value);
        } catch (DateTimeParseException | NullPointerException ignored) {
            InvalidFieldData invalidFieldData = new InvalidFieldData(
                    "date",
                    "Invalid date value: " + unsafeDate
            );
            return Validation.invalid(invalidFieldData);
        }
    }

    private static Validation<InvalidFieldData, LocalTime> validateTime(String unsafeTime) {
        try {
            LocalTime value = LocalTime.parse(unsafeTime);
            return Validation.valid(value);
        } catch (DateTimeParseException | NullPointerException e) {
            InvalidFieldData invalidFieldData = new InvalidFieldData(
                    "initHour",
                    e.getMessage()
            );
            return Validation.invalid(invalidFieldData);
        }
    }

    private static Validation<InvalidFieldData, ClientId> validateClient(String unsafeValue) {
        try {
            UUID uuid = UUID.fromString(unsafeValue);
            ClientId value = new ClientId(uuid);
            return Validation.valid(value);
        } catch (NullPointerException | IllegalArgumentException e) {
            InvalidFieldData invalidFieldData = new InvalidFieldData(
                    "clientId",
                    e.getMessage()
            );
            return Validation.invalid(invalidFieldData);
        }
    }

    private static Validation<InvalidFieldData, ServiceType> validateServiceType(String unsafeValue) {
        try {
            ServiceType value = ServiceType.valueOf(unsafeValue);
            return Validation.valid(value);
        } catch (NullPointerException | IllegalArgumentException e) {
            InvalidFieldData invalidFieldData = new InvalidFieldData(
                    "serviceType",
                    e.getMessage()
            );
            return Validation.invalid(invalidFieldData);
        }
    }


    public static Validation<InvalidInputData, CreateAppointmentValidated> validate(
            String unsafeSeatNumber,
            String unsafeDate,
            CreateAppointmentInput unsafeInput
    ) {
        List<Validation<io.vavr.collection.List<InvalidFieldData>, ServiceType>> validatedServiceType =
                Optional.ofNullable(unsafeInput.getServices())
                        .orElse(List.of())
                        .stream()
                        .map(unsafeValue -> validateServiceType(unsafeValue)
                                .mapError(io.vavr.collection.List::of)
                        )
                        .toList();

        Validation<Seq<InvalidFieldData>, Seq<ServiceType>> servicesValidation = Validation.sequence(validatedServiceType);


        return Validation.combine(
                        validateSeatNumber(unsafeSeatNumber).mapError(io.vavr.collection.List::of),
                        validateDate(unsafeDate).mapError(io.vavr.collection.List::of),
                        validateTime(unsafeInput.getInitHour()).mapError(io.vavr.collection.List::of),
                        validateClient(unsafeInput.getClientId()).mapError(io.vavr.collection.List::of),
                        servicesValidation
                )
                .ap((seatNumber, localDate, initHour, clientId, serviceTypes) -> {
                    Appointment appointment = new Appointment(
                            initHour,
                            clientId,
                            serviceTypes.asJava()
                    );
                    return new CreateAppointmentValidated(
                            seatNumber,
                            localDate,
                            appointment
                    );
                })
                .mapError(seqs -> {
                    List<InvalidFieldData> invalidFieldData = seqs
                            .flatMap(seq -> seq)
                            .asJava();

                    return new InvalidInputData(invalidFieldData);
                });
    }
}
