package ch.steponline.core.model;

import ch.steponline.core.service.DomainRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Date;

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
        entity.setValidFrom(new Date(System.currentTimeMillis()));
        entity.setSortNo(0.0);

        entityManager.persist(entity);
        Long entityId=entity.getId();
        System.out.println(entity.getId());
        DomainTextEntry deTextEntry=new DomainTextEntry(entity,"de");
        DomainTextEntry frTextEntry=new DomainTextEntry(entity,"fr");
        deTextEntry.setAbbreviation(entity.getIsoCode());
        deTextEntry.setDescription("Test XXX");
        frTextEntry.setAbbreviation("F"+entity.getIsoCode());
        frTextEntry.setDescription("Test FXXX");
        entity.getTextEntries().add(deTextEntry);
        entity.getTextEntries().add(frTextEntry);
        entityManager.persist(entity);
        entityManager.flush();
        entityManager.clear();
        entity=entityManager.find(Currency.class,entityId);

        assert(entity.getAbbreviation("de").equals("XXX"));
        assert(entity.getAbbreviation("fr").equals("FXXX"));
        Nation swiss=(Nation) entityManager.createNamedQuery("NationByIsoCode")
                .setParameter("isoCode","CH")
                .setParameter("evalDate", new Date(System.currentTimeMillis()))
                .getSingleResult();
        assert(swiss!=null);
    }


}
