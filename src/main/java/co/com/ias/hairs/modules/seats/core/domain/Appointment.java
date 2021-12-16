package co.com.ias.hairs.modules.seats.core.domain;

import com.google.common.base.Preconditions;
import com.google.common.collect.Range;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public class Appointment {
    public final static LocalTime SHIFT_START_HOUR = LocalTime.of(8, 0);
    public final static LocalTime SHIFT_END_HOUR = LocalTime.of(17, 0);

    private final LocalTime initHour;
    private final ClientId clientId;
    private final List<ServiceType> services;

    public Appointment(LocalTime initHour, ClientId clientId, List<ServiceType> services) {
        Objects.requireNonNull(initHour);
        Objects.requireNonNull(clientId);
        Objects.requireNonNull(services);

        Preconditions.checkArgument(services.size() > 0, "Services list can not be empty");
        Preconditions.checkArgument(
                initHour.isAfter(SHIFT_START_HOUR),
                "Appointment can not start before %s",
                SHIFT_START_HOUR
        );

        LocalTime endHour = calculateEndHour(services, initHour);
        Preconditions.checkArgument(
                endHour.isBefore(SHIFT_END_HOUR),
                "Appointment can not end after %s",
                SHIFT_END_HOUR
        );


        this.initHour = initHour;
        this.clientId = clientId;
        this.services = services;
    }

    public LocalTime getInitHour() {
        return initHour;
    }

    public ClientId getClientId() {
        return clientId;
    }

    public List<ServiceType> getServices() {
        return services;
    }

    public LocalTime getEndHour() {
       return calculateEndHour(services, initHour);
    }

    public Price getPrice() {
        return services.stream()
                .map(ServiceType::getPrice)
                .reduce(Price.ZERO, Price::plus);
    }


    public Range<LocalTime> getRange() {
        return Range.closed(initHour, getEndHour());
    }

    private static LocalTime calculateEndHour(List<ServiceType> services, LocalTime initHour) {
        Duration serviceDuration = ServiceType.durationOf(services);
        return initHour.plus(serviceDuration);
    }

}
