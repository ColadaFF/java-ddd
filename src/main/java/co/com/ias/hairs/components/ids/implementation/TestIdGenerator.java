package co.com.ias.hairs.components.ids.implementation;

import co.com.ias.hairs.components.ids.IdGenerator;
import org.springframework.stereotype.Component;

import java.util.UUID;

// @Component
public class TestIdGenerator implements IdGenerator {
    private final UUID fixedId;

    public TestIdGenerator(UUID fixedId) {
        this.fixedId = fixedId;
    }

    @Override
    public UUID generate() {
        return fixedId;
    }
}
