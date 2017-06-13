package ch.steponline.mds.resource;

import ch.steponline.core.dto.DomainDTO;
import ch.steponline.core.model.Currency;
import ch.steponline.core.model.Domain;
import ch.steponline.core.model.DomainRole;
import ch.steponline.core.service.DomainRepository;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

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
        return getDomainDtos(domains);
    }

    @RequestMapping(path="currency", method= RequestMethod.GET)
    @ApiOperation(value="List of all Currencies",response = Currency.class,responseContainer = "List")
    public @ResponseBody MappingJacksonValue getCurrencies() {
        List<Domain> domains= domainRepo.findAllDomainsWithRole(DomainRole.ROLES.CURRENCY.toString());
        List<DomainDTO> currencies= getDomainDtos(domains);
        String[] properties=new String[]{"id","isoAlphabetic","textEntries"};
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(currencies);
        FilterProvider filter = new SimpleFilterProvider().addFilter("DomainFilter", SimpleBeanPropertyFilter.filterOutAllExcept(properties));
        mappingJacksonValue.setFilters(filter);
        return mappingJacksonValue;
    }

    @RequestMapping(path="nation", method= RequestMethod.GET)
    public @ResponseBody List<DomainDTO> getNations() {
        List<Domain> domains= domainRepo.findAllDomainsWithRole(DomainRole.ROLES.NATION.toString());
        return getDomainDtos(domains);
    }

    private List<DomainDTO> getDomainDtos(List<Domain> domains) {
        List<DomainDTO> dtos=new ArrayList<DomainDTO>();
        for (Domain domain : domains) {
            dtos.add(new DomainDTO(domain));
        }
        return dtos;
    }

}
