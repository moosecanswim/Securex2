package byAJ.Securex.repositories;

import byAJ.Securex.models.Uzer;
import org.springframework.data.repository.CrudRepository;

public interface UzerRepository extends CrudRepository<Uzer,Long>{
    Uzer findByUsername(String username);
    Long countByUsername(String username);
    Uzer findByName(String name);
    Long countByName(String name);
}
