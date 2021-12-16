package co.com.ias.hairs.modules.seats.core.domain;

import java.math.BigDecimal;

public record Price(BigDecimal value) {
    public static final Price ZERO = new Price(BigDecimal.ZERO);


    public static Price of(Double unsafeValue) {
        return new Price(BigDecimal.valueOf(unsafeValue));
    }

    public static Price of(long unsafeValue) {
        return new Price(BigDecimal.valueOf(unsafeValue));
    }

    public Price plus(Price price) {
        return new Price(
                value.add(price.value)
        );
    }
}
