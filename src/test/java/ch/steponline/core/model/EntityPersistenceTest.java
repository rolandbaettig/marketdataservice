package ch.steponline.core.model;

import ch.steponline.core.repository.DomainRepository;
import ch.steponline.core.repository.DomainRoleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Date;
import java.util.List;

/**
 * Created by Roland on 11.05.17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = {"test"})
public class EntityPersistenceTest {

    @Autowired
    private DomainRepository domainRepository;

    @Autowired
    private DomainRoleRepository domainRoleRepository;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Test
    public void jpa_test() {
        DomainRole currencyRole=domainRoleRepository.findOne("CURRENCY");
        Currency entity = new Currency();
        entity.setDomainRole(currencyRole);
        entity.setIsoAlphabetic("XXX");
        entity.setValidFrom(new Date(System.currentTimeMillis()));
        entity.setSortNo(0.0);
        entity=domainRepository.saveAndFlush(entity);
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
        entity=domainRepository.saveAndFlush(entity);
        assert(entity.getAbbreviation("de").equals("XXX"));
        assert(entity.getAbbreviation("fr").equals("FXXX"));
        EntityManager entityManager=entityManagerFactory.createEntityManager();
        Nation swiss=(Nation) entityManager.createNamedQuery("NationByIsoCode")
                .setParameter("isoCode","CH")
                .setParameter("evalDate", new Date(System.currentTimeMillis()))
                .getSingleResult();
        assert(swiss!=null);
    }

    @Test
    public void jpa_repotest() {
        List<Domain> currencies=domainRepository.findAllDomainsWithRole("CURRENCY");
        assert(currencies.size()>0);
    }
}
