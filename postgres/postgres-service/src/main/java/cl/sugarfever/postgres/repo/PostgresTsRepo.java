package cl.sugarfever.postgres.repo;

import cl.sugarfever.postgres.model.Ts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostgresTsRepo extends JpaRepository<Ts, UUID> {
    public List<Ts> findByCartel(String cartel);
}
