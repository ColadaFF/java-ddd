package co.com.ias.hairs.modules.seats.infrastructure.entrypoints.model;

import co.com.ias.hairs.modules.seats.core.domain.Appointment;
import co.com.ias.hairs.modules.seats.core.domain.SeatNumber;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateAppointmentOutput {
    private SeatNumber seatNumber;
    private LocalDate date;
    private Appointment appointment;
}
