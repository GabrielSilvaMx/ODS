package org.bedu.ods.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.bedu.ods.entity.dto.ProyectosDTO;
import org.bedu.ods.service.impl.ProyectosImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProyectosControllerTest {

    private MockMvc mockMvc;

    //SimpleDateFormat fechaFormat = new SimpleDateFormat("yyyy-MM-dd");

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
    void getProyectosById() throws Exception  {
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
    void getAllProyectos() throws Exception  {
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
    void createProyecto() throws Exception  {
        /* ProyectosDTO proyectoToMock = new ProyectosDTO(2,"Proyecto Cultural","DIFUSIÓN de los ODS que alcance al 10% población.",fechaFormat.parse("2023-02-01"),fechaFormat.parse("2024-01-15"),fechaFormat.parse("2024-01-30"),"servicio1",10,"pendiente");
        StringBuilder proyectoBuffer = new StringBuilder(asJsonString(proyectoToMock));
        log.info(String.valueOf(proyectoBuffer)); */
        MockHttpServletRequestBuilder builderController = MockMvcRequestBuilders
                .post("/api/proyectos")
                .content("{\"nombre\":\"Proyecto Cultural\",\"descripcion\":\"DIFUSIÓN de los ODS que alcance al 10% población.\",\"fechaProyecto\":1675231200000,\"fechaCompromiso\":1705298400000,\"fechaEntrega\":1706594400000,\"claseServicio\":\"servicio1\",\"limiteWip\":10,\"estatus\":\"pendiente\"}");

        MvcResult result = this.mockMvc
                .perform(
                        builderController
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated()).andDo(print())
                .andExpect(content().contentType("application/hal+json"))
                .andReturn();

        //log.info("ID PRY: " +jsonObject.get("id").toString());
        result.getResponse();
    }

    @WithMockUser(username = "javier@bedu.org", password = "$2a$10$rs.epXuYugLwqh2QfppSSummBr81nmF1tAnh2jTUI1mgCYajkzsmW", roles = "MANAGER")
    @Order(4)
    @Test
    void updateProyecto() throws Exception {
        Thread.sleep(1000);
        ProyectosDTO proyecto = proyectosService.findLastProyecto();
        this.pryIdCreated = proyecto.getId();
        MockHttpServletRequestBuilder builderController = MockMvcRequestBuilders
                .put("/api/proyectos/"+pryIdCreated)
                .content("{\"nombre\":\"Proyecto Cultural\",\"descripcion\":\"DIFUSIÓN de los ODS que alcance al 10% población.\",\"fechaProyecto\":1675231200000,\"fechaCompromiso\":1705298400000,\"fechaEntrega\":1706594400000,\"claseServicio\":\"servicio1\",\"limiteWip\":10,\"estatus\":\"pendiente\"}");

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
    void deleteProyecto() throws Exception {
        Thread.sleep(2000);
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

}