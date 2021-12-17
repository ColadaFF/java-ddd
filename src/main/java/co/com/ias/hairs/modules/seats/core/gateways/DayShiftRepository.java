package co.com.ias.hairs.modules.seats.core.gateways;

import co.com.ias.hairs.modules.seats.core.domain.DayShift;
import co.com.ias.hairs.modules.seats.core.domain.SeatNumber;

import java.time.LocalDate;
import java.util.Optional;

public interface DayShiftRepository {
    void save(DayShift dayShift);

    Optional<DayShift> findOne(SeatNumber seatNumber, LocalDate localDate);
}
