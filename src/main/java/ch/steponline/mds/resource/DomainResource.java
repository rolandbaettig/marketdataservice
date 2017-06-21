package ch.steponline.mds.resource;

import ch.steponline.core.dto.DomainDTO;
import ch.steponline.core.dto.TextEntryDTO;
import ch.steponline.core.model.Currency;
import ch.steponline.core.model.Domain;
import ch.steponline.core.model.DomainRole;
import ch.steponline.core.model.DomainTextEntry;
import ch.steponline.core.repository.DomainRepository;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.google.common.base.Splitter;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyDescriptor;
import java.util.*;
import java.util.stream.Collectors;

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
    public @ResponseBody MappingJacksonValue getCurrencies(@RequestHeader(value = "x-steponline-attributes") String attributes) {
        List<Domain> domains= domainRepo.findAllDomainsWithRole(DomainRole.ROLES.CURRENCY.toString());
        List<DomainDTO> currencies= getDomainDtos(domains);

        String[] properties=getStringFromEnum(DomainDTO.POSSIBLE_PROPERTIES.values());

        String[] textProperties= TextEntryDTO.POSSIBLE_PROPERTIES;
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(currencies);
        SimpleFilterProvider filter = new SimpleFilterProvider();
        if (attributes!=null && !attributes.isEmpty()) {
            properties=new String[]{};
        }
        filter.addFilter("DomainFilter", SimpleBeanPropertyFilter.filterOutAllExcept(properties))
                .addFilter("TextEntryFilter",SimpleBeanPropertyFilter.filterOutAllExcept(textProperties));
        mappingJacksonValue.setFilters(filter);
        return mappingJacksonValue;
    }

    private String[] getStringFromEnum(Enum[] e) {
        return Arrays.stream(e).map(Enum::toString).toArray(String[]::new);
    }

// TODO: Source Object übergeben und anhand der Properties (mit . getrennt den entprechenden Filter unc die Properties returnieren. Das ganze via refleciont.

    @RequestMapping(path="nation", method= RequestMethod.GET)
    @ApiOperation(value="List of all Nations")
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
