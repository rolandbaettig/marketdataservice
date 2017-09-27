package ch.steponline.core.resource;

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
import io.swagger.annotations.ApiParam;
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
    @ResponseBody
    @ApiOperation(
            value="Delivers all domains with the desired attributes." +
                    "The desired attributes must be delivered int the Request-Header" +
                    "If no attributes are defined, the method returns all properties of the domain object."
    )
    public MappingJacksonValue getDomains(
            @ApiParam(value="Possible Attributes:id,domainRole,validFrom,validTo,domainNo,custom,isoAlphabetic,isoNumeric,sortNo,textEntries")
            @RequestHeader(value="x-steponline-attributes",required = false)
            String attributes
    ) {
        List<Domain> domains= domainRepo.findAll();

        List<DomainDTO> domainDTOS= getDomainDtos(domains);
        MappingJacksonValue res=new MappingJacksonValue(domainDTOS);
        SimpleFilterProvider filter=new SimpleFilterProvider();
        String[] properties=DomainDTO.getPossiblePropertiesAsString();
        if (attributes!=null && !attributes.isEmpty()) {
            properties=new String[]{};
            properties=Splitter.on(",").trimResults().splitToList(attributes).toArray(properties);
        }
        filter.addFilter("DomainFilter",SimpleBeanPropertyFilter.filterOutAllExcept(properties));
        filter.addFilter("TextEntryFilter",SimpleBeanPropertyFilter.filterOutAllExcept(TextEntryDTO.getPossiblePropertiesAsString()));
        res.setFilters(filter);
        return res;
    }

    @RequestMapping(path="currency", method= RequestMethod.GET)
    @ApiOperation(value="List of all Currencies",response = Currency.class,responseContainer = "List")
    public @ResponseBody MappingJacksonValue getCurrencies(@RequestHeader(value = "x-steponline-attributes") String attributes) {
        List<Domain> domains= domainRepo.findAllDomainsWithRole(DomainRole.ROLES.CURRENCY.toString());
        List<DomainDTO> currencies= getDomainDtos(domains);

        String[] properties=DomainDTO.getPossiblePropertiesAsString();

        String[] textProperties= TextEntryDTO.getPossiblePropertiesAsString();
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
