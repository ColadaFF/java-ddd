package co.com.ias.hairs.modules.seats.core.domain;

import com.google.common.base.Preconditions;

import java.util.UUID;

public record ClientId(UUID value) {

    public ClientId {
        Preconditions.checkNotNull(value);
    }
}
