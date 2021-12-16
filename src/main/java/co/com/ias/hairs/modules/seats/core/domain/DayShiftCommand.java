package co.com.ias.hairs.modules.seats.core.domain;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

public interface DayShiftCommand {
    SeatNumber seatNumber();
    LocalDate date();
    Instant at();


    record CreateAppointment(
            SeatNumber seatNumber,
            LocalDate date,
            Instant at,
            Appointment appointment
    ) implements DayShiftCommand {}


    record CancelAppointment(
            SeatNumber seatNumber,
            LocalDate date,
            Instant at,
            LocalTime appointmentInitTime
    ) implements DayShiftCommand {}

}
