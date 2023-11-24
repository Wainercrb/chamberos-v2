package com.chamberos.chamberosapi.infrastructure.inputadapter.http;

import java.util.LinkedList;
import java.util.List;
import com.chamberos.chamberosapi.domain.Profession;
import com.chamberos.chamberosapi.domain.Role;
import com.chamberos.chamberosapi.infrastructure.inputport.ProfessionInputPort;
import org.junit.jupiter.api.DisplayName;
import org.hamcrest.Matchers;
import org.mockito.Mockito;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.data.domain.Pageable;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProfessionAPI.class)
public class ProfessionApiTest {
     @Autowired
     private MockMvc mockMvc;

     @MockBean
     private ProfessionInputPort professionInputPort;

     @Test
     @DisplayName("Should List All Profession When making GET request to endpoint - /profession/get-all")
     void shouldGetTheProfessionList() throws Exception {
         List<Profession> professionList = new LinkedList<>();
         Profession profession1 = new Profession("1", "name A", Role.FULLTIME);
         Profession profession2 = new Profession("2", "name B", Role.FULLTIME);

         professionList.add(profession1);
         professionList.add(profession2);

         Mockito.when(professionInputPort.getAll(Mockito.anyString(), Mockito.any(Pageable.class)))
                 .thenReturn(professionList);

         mockMvc.perform(get("/profession/get-all").param("name", "name A"))
                 .andExpect(status().is(200))
                 .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                 .andExpect(jsonPath("$", Matchers.hasSize(2)))
                 .andExpect(jsonPath("$[0].id", Matchers.is(profession1.getId())))
                 .andExpect(jsonPath("$[0].name", Matchers.is(profession1.getName())))
                 .andExpect(jsonPath("$[0].role", Matchers.is(profession1.getRole().toString())))
                 .andExpect(jsonPath("$[1].id", Matchers.is(profession2.getId())))
                 .andExpect(jsonPath("$[1].name", Matchers.is(profession2.getName())))
                 .andExpect(jsonPath("$[1].role", Matchers.is(profession2.getRole().toString())));
     }

     @Test
     @DisplayName("Should Trigger Parameter Required Error When making GET request to endpoint - /profession/get-all")
     void shouldCheckTheGetProfessionParameters() throws Exception {
         mockMvc.perform(get("/profession/get-all"))
                 .andExpect(status().is(400))
                 .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                 .andExpect(jsonPath("$.message[0]", Matchers.is("name parameter is missing")));
     }
}
