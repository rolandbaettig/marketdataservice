package ch.steponline.core.service;

import ch.steponline.core.model.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Roland on 11.05.17.
 */
public interface DomainRepository extends JpaRepository<Domain,Long>{

    @Query(value = "select d from Domain d where d.domainRole.id=:role")
    public List<Domain> findAllDomainsWithRole(@Param(value="role") String role);
}
