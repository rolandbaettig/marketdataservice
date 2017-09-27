package ch.steponline.core.model;

import ch.steponline.Application;
import ch.steponline.core.dto.DomainDTO;
import ch.steponline.core.repository.DomainRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
@WebAppConfiguration
@ActiveProfiles(profiles="test")
public class RestTest {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void init() throws Exception {
        this.mockMvc=webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getDomains() throws Exception {
        MvcResult mvcResult=mockMvc.perform(get("/domains")
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andReturn();
        String s=mvcResult.getResponse().getContentAsString();
        ObjectMapper mapper=new ObjectMapper();
        SimpleFilterProvider filter=new SimpleFilterProvider();
        String[] properties=DomainDTO.getPossiblePropertiesAsString();
        filter.addFilter("DomainFilter", SimpleBeanPropertyFilter.filterOutAllExcept(properties));
        mapper.setFilterProvider(filter);
        List<DomainDTO> domainDTOS=mapper.readValue(s,new TypeReference<List<DomainDTO>>(){});
        assert(domainDTOS.size()>0);
    }
}
