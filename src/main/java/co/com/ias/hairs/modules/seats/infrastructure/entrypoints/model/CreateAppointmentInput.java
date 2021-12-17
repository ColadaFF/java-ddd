package co.com.ias.hairs.modules.seats.infrastructure.entrypoints.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Data
public class CreateAppointmentInput {
    private String initHour;
    private String clientId;
    private List<String> services;
}
