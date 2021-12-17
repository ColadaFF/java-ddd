package co.com.ias.hairs.modules.seats.infrastructure.entrypoints.model;

import co.com.ias.hairs.modules.seats.core.domain.Appointment;
import co.com.ias.hairs.modules.seats.core.domain.SeatNumber;

import java.time.LocalDate;

public record CreateAppointmentValidated(
        SeatNumber seatNumber,
        LocalDate date,
        Appointment appointment
) {

}
