package co.com.ias.hairs.modules.seats.core.services;

import co.com.ias.hairs.modules.seats.core.domain.Appointment;
import co.com.ias.hairs.modules.seats.core.domain.DayShift;
import co.com.ias.hairs.modules.seats.core.domain.DayShiftCommandError;
import co.com.ias.hairs.modules.seats.core.domain.SeatNumber;
import com.google.common.base.Preconditions;
import io.vavr.control.Either;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CreateAppointmentService {


    public Either<DayShiftCommandError, DayShift> execute(SeatNumber seatNumber, LocalDate date, Appointment appointment) {
        Preconditions.checkNotNull(seatNumber, "seatNumber can not be null");
        Preconditions.checkNotNull(date, "date can not be null");
        Preconditions.checkNotNull(appointment, "appointment can not be null");

        return Either.right(null);
    }
}
