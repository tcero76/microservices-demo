package cl.sugarfever.postgres.repo;

import cl.sugarfever.postgres.model.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface PostgresImagenRepo extends JpaRepository<Imagen, UUID> {
}
