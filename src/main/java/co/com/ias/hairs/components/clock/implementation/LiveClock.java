package co.com.ias.hairs.components.clock.implementation;

import co.com.ias.hairs.components.clock.Clock;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class LiveClock implements Clock {

    @Override
    public Instant now() {
        return Instant.now();
    }
}
