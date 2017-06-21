package ch.steponline.core.repository;

import ch.steponline.core.model.DomainRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Roland on 21.06.17.
 */
public interface DomainRoleRepository extends JpaRepository<DomainRole,String> {
}
