package cl.sugarfever.postgres.repo;

import cl.sugarfever.postgres.model.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostgresTsRepo extends JpaRepository<History, UUID> {
    public List<History> findByCartel(String cartel);
    public List<History> findByIdjob(Long idjob);
}
