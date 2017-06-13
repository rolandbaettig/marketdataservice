package ch.steponline.mds.resource;

import ch.steponline.core.dto.DomainDTO;
import ch.steponline.core.model.Domain;
import ch.steponline.core.service.DomainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roland on 13.06.17.
 */
@RestController
@RequestMapping("/domains")
public class DomainResource {

    private final DomainRepository domainRepo;

    @Autowired
    public DomainResource(DomainRepository domainRepo) {
        this.domainRepo = domainRepo;
    }

    @RequestMapping(method= RequestMethod.GET)
    public @ResponseBody List<DomainDTO> getDomains() {
        List<Domain> domains= domainRepo.findAll();
        List<DomainDTO> dtos=new ArrayList<DomainDTO>();
        for (Domain domain : domains) {
            dtos.add(new DomainDTO(domain));
        }
        return dtos;
    }
}
