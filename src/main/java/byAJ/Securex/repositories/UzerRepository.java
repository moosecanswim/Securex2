package byAJ.Securex.repositories;

import byAJ.Securex.models.Uzer;
import org.springframework.data.repository.CrudRepository;

public interface UzerRepository extends CrudRepository<Uzer,Long>{
    Uzer findByUsername(String username);
    Long countByUsername(String username);

    Uzer findByFirstName(String name);
    Long countByFirstName(String name);

    Uzer findByEmail(String email);
    Long countByEmail(String email);
}
