package co.com.ias.hairs.modules.seats.core.domain;

import com.google.common.base.Preconditions;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import io.vavr.control.Either;

import java.time.LocalDate;
import java.time.LocalTime;

public class DayShift {
    private final LocalDate date;
    private final SeatNumber seatNumber;
    private final RangeMap<LocalTime, Appointment> appointments;

    public DayShift(LocalDate date, SeatNumber seatNumber, RangeMap<LocalTime, Appointment> appointments) {
        Preconditions.checkNotNull(date, "Day shift date can not be null");
        Preconditions.checkNotNull(seatNumber, "Day shift seatNumber can not be null");
        Preconditions.checkNotNull(appointments, "Day shift appointments can not be null");


        this.date = date;
        this.seatNumber = seatNumber;
        this.appointments = appointments;
    }

    public Either<DayShiftCommandError, DayShift> handleCommand(DayShiftCommand command) {
        if (command instanceof DayShiftCommand.CreateAppointment cmd) {
            return createAppointment(cmd);
        }

        if (command instanceof DayShiftCommand.CancelAppointment cmd) {
            return cancelAppointment(cmd);
        }

        return null;

    }

    private Either<DayShiftCommandError, DayShift> cancelAppointment(DayShiftCommand.CancelAppointment cmd) {
        Appointment appointment = appointments.get(cmd.appointmentInitTime());
        boolean appointmentDoesNotExists = appointment == null;
        if(appointmentDoesNotExists) {
            return Either.left(DayShiftCommandError.APPOINTMENT_DOES_NOT_EXITS);
        }

        appointments.remove(appointment.getRange());
        return Either.right(this);
    }


    private Either<DayShiftCommandError, DayShift> createAppointment(DayShiftCommand.CreateAppointment cmd) {
        Appointment appointment = cmd.appointment();
        LocalTime initTime = appointment.getInitHour();
        LocalTime endTime = appointment.getEndHour();


        boolean appointmentInitExists = appointments.get(initTime) != null;
        boolean appointmentEndExists = appointments.get(endTime) != null;

        if (appointmentInitExists || appointmentEndExists) {
            return Either.left(DayShiftCommandError.APPOINTMENT_TIME_ALREADY_EXISTS);
        }



        appointments.put(appointment.getRange(), appointment);

        return Either.right(this);
    }


}
