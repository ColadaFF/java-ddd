package co.com.ias.hairs.components.clock.implementation;

import co.com.ias.hairs.components.clock.Clock;

import java.time.Instant;

public class TestClock implements Clock {
    private final Instant fixed;

    public TestClock(Instant fixed) {
        this.fixed = fixed;
    }

    @Override
    public Instant now() {
        return fixed;
    }
}
