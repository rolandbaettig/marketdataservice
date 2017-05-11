package ch.steponline.core.service;

import ch.steponline.core.model.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Roland on 11.05.17.
 */
@Repository
public interface DomainRepository extends CrudRepository<Domain,Long>{
}
