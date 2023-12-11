package cl.sugarfever.outbox.to.kafka.Datagen;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class DataPayload {

    private Integer id;

    private String Nombre;
}
