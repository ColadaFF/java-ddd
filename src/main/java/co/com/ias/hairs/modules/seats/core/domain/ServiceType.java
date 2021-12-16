package co.com.ias.hairs.modules.seats.core.domain;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

public enum ServiceType {
    HAIRCUT(Duration.ofMinutes(30), Price.of(25_000)),
    MANICURE(Duration.ofMinutes(45), Price.of(20_000)),
    PEDICURE(Duration.ofMinutes(45), Price.of(20_000)),
    BEARD_SHAVE(Duration.ofMinutes(20), Price.of(15_000)),
    HAIR_COLORING(Duration.ofHours(2), Price.of(150_000));

    private final Duration duration;
    private final Price price;

    ServiceType(Duration duration, Price price) {
        this.duration = duration;
        this.price = price;
    }

    public Duration getDuration() {
        return duration;
    }

    public Price getPrice() {
        return price;
    }

    public static Duration durationOf(List<ServiceType> services) {
        return services
                .stream()
                .map(ServiceType::getDuration)
                .reduce(Duration.ZERO, Duration::plus);
    }
}
