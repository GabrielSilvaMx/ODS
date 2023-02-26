package org.bedu.ods;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.bedu.ods.entity.dto.ProyectosDTO;
import org.bedu.ods.service.impl.ProyectosImpl;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Calendar;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:db-test.properties")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProyectosControllerTest {

    private MockMvc mockMvc;

    Long pryIdCreated;

    @Autowired
    ProyectosImpl proyectosService;

    @Autowired
    WebApplicationContext applicationContext;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        this.mockMvc = webAppContextSetup(this.applicationContext)
                .build();
    }

    @WithMockUser(username = "javier@bedu.org", password = "$2a$10$rs.epXuYugLwqh2QfppSSummBr81nmF1tAnh2jTUI1mgCYajkzsmW", roles = "MANAGER")
    @Order(1)
    @Test
    void getProyectosByIdTest() throws Exception  {
        this.mockMvc
                .perform(
                        get("/api/proyectos/{id}", 1L)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.OK.value())).andDo(print())
                .andReturn().getResponse();
    }

    @WithMockUser(username = "javier@bedu.org", password = "$2a$10$rs.epXuYugLwqh2QfppSSummBr81nmF1tAnh2jTUI1mgCYajkzsmW", roles = "MANAGER")
    @Order(2)
    @Test
    void getAllProyectosTest() throws Exception  {
        this.mockMvc
                .perform(
                        get("/api/proyectos")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.OK.value())).andDo(print())
                .andReturn().getResponse();
    }

    @WithMockUser(username = "javier@bedu.org", password = "$2a$10$rs.epXuYugLwqh2QfppSSummBr81nmF1tAnh2jTUI1mgCYajkzsmW", roles = "MANAGER")
    @Order(3)
    @Test
    void createProyectoTest() throws Exception  {

        MockHttpServletRequestBuilder builderController = MockMvcRequestBuilders
                .post("/api/proyectos")
                .content("{\"nombre\":\"Proyecto Cultural\",\"descripcion\":\"DIFUSIÓN de los ODS que alcance al 10% población.\",\"fechaProyecto\":"+getFecha(2023,Calendar.AUGUST,1).getTime()+",\"fechaCompromiso\":"+getFecha(2023,Calendar.AUGUST,15).getTime()+",\"fechaEntrega\":"+getFecha(2023,Calendar.AUGUST,30).getTime()+",\"claseServicio\":\"servicio1\",\"limiteWip\":10,\"estatus\":\"pendiente\"}");

        MvcResult result = this.mockMvc
                .perform(
                        builderController
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated()).andDo(print())
                .andExpect(content().contentType("application/hal+json"))
                .andReturn();

        result.getResponse();
    }

    @WithMockUser(username = "javier@bedu.org", password = "$2a$10$rs.epXuYugLwqh2QfppSSummBr81nmF1tAnh2jTUI1mgCYajkzsmW", roles = "MANAGER")
    @Order(4)
    @Test
    void updateProyectoTest() throws Exception {

        ProyectosDTO proyecto = proyectosService.findLastProyecto();
        this.pryIdCreated = proyecto.getId();
        MockHttpServletRequestBuilder builderController = MockMvcRequestBuilders
                .put("/api/proyectos/"+pryIdCreated)
                .content("{\"nombre\":\"Proyecto Cultural\",\"descripcion\":\"DIFUSIÓN de los ODS que alcance al 10% población.\",\"fechaProyecto\":"+getFecha(2023,Calendar.AUGUST,1).getTime()+",\"fechaCompromiso\":"+getFecha(2024,Calendar.AUGUST,20).getTime()+",\"fechaEntrega\":"+getFecha(2023,Calendar.SEPTEMBER,30).getTime()+",\"claseServicio\":\"servicio1\",\"limiteWip\":10,\"estatus\":\"pendiente\"}");

        this.mockMvc
                .perform(
                        builderController
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated()).andDo(print())
                .andExpect(content().contentType("application/hal+json"))
                .andReturn().getResponse();
    }

    @WithMockUser(username = "javier@bedu.org", password = "$2a$10$rs.epXuYugLwqh2QfppSSummBr81nmF1tAnh2jTUI1mgCYajkzsmW", roles = "MANAGER")
    @Order(5)
    @Test
    void deleteProyectoTest() throws Exception {

        ProyectosDTO proyecto = proyectosService.findLastProyecto();
        this.pryIdCreated = proyecto.getId();
        MockHttpServletRequestBuilder builderController = MockMvcRequestBuilders
                .delete("/api/proyectos/"+pryIdCreated);

        this.mockMvc
                .perform(
                        builderController
                )
                .andExpect(status().isNoContent())
                .andReturn().getResponse();
    }

    private Date getFecha(int year, int month, int day)
    {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }

}