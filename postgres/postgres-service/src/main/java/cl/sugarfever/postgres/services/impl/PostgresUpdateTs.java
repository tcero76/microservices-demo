package cl.sugarfever.postgres.services.impl;

import cl.sugarfever.outbox.OutboxStatus;
import cl.sugarfever.postgres.mapper.TsMapper;
import cl.sugarfever.postgres.model.Imagen;
import cl.sugarfever.postgres.model.Outbox;
import cl.sugarfever.postgres.model.Servicio;
import cl.sugarfever.postgres.model.Ts;
import cl.sugarfever.postgres.repo.PostgresCatalogoOutbox;
import cl.sugarfever.postgres.repo.PostgresTsRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class PostgresUpdateTs implements cl.sugarfever.postgres.services.UpdateTs<Ts> {
    private final TsMapper tsMapper;
    private final ObjectMapper mapper;
    private final PostgresTsRepo postgresTsRepo;
    private final PostgresCatalogoOutbox postgresCatalogoOutbox;
    private final Validator validator;
    @Override
    public void update(Ts ts) {
        Set<ConstraintViolation<Ts>> violations = validator.validate(ts);
        if (violations.isEmpty()) {
            log.info("Ts: validado para persistir: {}", ts.getId_ts());
            Set<Servicio> servicios = ts.getServicios().stream().map(servicio -> {
                servicio.setTs(ts);
                return servicio;
            }).collect(Collectors.toSet());
            Set<Imagen> imagenes = ts.getImagenes().stream().map(imagen -> {
                imagen.setTs(ts);
                return imagen;
            }).collect(Collectors.toSet());
            try {
                Ts event = postgresTsRepo.save(ts);
                Outbox outbox = outboxPersist(event);
                log.info("Ts: Se almacenó Ts {}, de id: {} junto con outbox: {}", event.getNombre(), event.getId_ts(), outbox.getId_outbox());
            } catch (Exception e) {
                log.error(e.getMessage());
                log.error("DataException: No se almacenó Ts {}, de id: {} junto con outbox: {}");
                System.out.println(ts);
            }
        }
        violations.stream().forEach(error -> {
            String field = error.getConstraintDescriptor().getMessageTemplate();
            String message = error.getMessage();
            log.error("Error para ts {} en campo {}, mensaje: {}", ts.getNombre(), field, message);
        });
    }
    @Override
    public void deleteTsesDropped(List<Ts> tses) {
        String cartel = getCartel(tses);
        List<Ts> tsesActual = postgresTsRepo.findByCartel(cartel);
        log.debug("Ts: filtrados por {}, se encontraron: {}", cartel, tsesActual.size());
        postgresTsRepo.deleteAll(tsesActual);
        log.debug("Ts: se eliminaron {} tses, pertenecientes a {}", tsesActual.size(), cartel);
    }
    private String getCartel(List<Ts> tses) {
        for (Ts ts:tses) {
            if (!ts.getCartel().isEmpty()) {
                return ts.getCartel();
            }
        }
        return null;
    }
    @Override
    public Outbox outboxPersist(Ts ts) {
        Outbox catalogoOutbox = Outbox.builder()
                .id_outbox(UUID.randomUUID())
                .outboxStatus(OutboxStatus.STARTED)
                .createdAt(Timestamp.from(Instant.now()))
                .processedAt(Timestamp.from(Instant.now()))
                .payload(getPayload(tsMapper.mapToTsScrapEventPayload(ts)))
                .build();
        return postgresCatalogoOutbox.save(catalogoOutbox);
    }
    private <T> String getPayload(T payload) {
        try {
            return mapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            log.error("Ts: ObjectMapper no pudo serializar el objeto");
            throw new RuntimeException(e);
        }
    }
}
