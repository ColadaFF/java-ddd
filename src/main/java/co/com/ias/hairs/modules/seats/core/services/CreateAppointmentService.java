package co.com.ias.hairs.modules.seats.core.services;

import co.com.ias.hairs.modules.seats.core.domain.Appointment;
import co.com.ias.hairs.modules.seats.core.domain.DayShift;
import co.com.ias.hairs.modules.seats.core.domain.DayShiftCommandError;
import co.com.ias.hairs.modules.seats.core.domain.SeatNumber;
import io.vavr.control.Either;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CreateAppointmentService {


    public Either<DayShiftCommandError, DayShift> execute(SeatNumber seatNumber, LocalDate date, Appointment appointment) {
        return Either.right(null);
    }
}
