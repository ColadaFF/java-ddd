package co.com.ias.hairs.components.ids.implementation;

import co.com.ias.hairs.components.ids.IdGenerator;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class LiveIdGenerator implements IdGenerator {

    @Override
    public UUID generate() {
        return UUID.randomUUID();
    }
}
