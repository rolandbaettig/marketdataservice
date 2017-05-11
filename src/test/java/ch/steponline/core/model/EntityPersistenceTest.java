package ch.steponline.core.model;

import ch.steponline.core.service.DomainRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 * Created by Roland on 11.05.17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@SpringBootConfiguration
@SpringBootTest
@ComponentScan(basePackages = {"ch.steponline"})
@EnableJpaRepositories(basePackages = "ch.steponline")
public class EntityPersistenceTest {

    @Autowired
    private DomainRepository domainRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void jpa_test() {
        Currency entity = new Currency();
        entity.setIsoAlphabetic("XXX");
        DomainTextEntry deTextEntry=new DomainTextEntry(entity,"de");
        DomainTextEntry frTextEntry=new DomainTextEntry(entity,"fr");
        deTextEntry.setAbbreviation(entity.getIsoCode());
        deTextEntry.setDescription("Test XXX");
        frTextEntry.setAbbreviation("F"+entity.getIsoCode());
        frTextEntry.setDescription("Test FXXX");
        entity.getTextEntries().add(deTextEntry);
        entity.getTextEntries().add(frTextEntry);

        entityManager.persist(entity);
        System.out.println(entity.getId());
        assert(entity.getAbbreviation("de").equals("XXX"));
        assert(entity.getAbbreviation("fr").equals("FXXX"));
    }


}
