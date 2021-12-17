package co.com.ias.hairs.modules.seats.core.domain;

import com.google.common.base.Preconditions;

public record SeatNumber(Integer number) {

    public SeatNumber {
        Preconditions.checkNotNull(number, "Seat number can not be null");
        Preconditions.checkArgument(number > 0, "Seat number can not be negative");
    }
}
