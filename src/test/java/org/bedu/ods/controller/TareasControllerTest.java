package org.bedu.ods.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bedu.ods.entity.Proyectos;
import org.bedu.ods.entity.Usuarios;
import org.bedu.ods.entity.dto.TareasDTO;
import org.bedu.ods.repository.IProyectosRepository;
import org.bedu.ods.repository.IUsuariosRepository;
import org.bedu.ods.service.impl.TareasImpl;
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

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TareasControllerTest {

    private MockMvc mockMvc;

    Proyectos proyecto;

    Usuarios usuario;

    Long tareaIdCreated;

    @Autowired
    TareasImpl tareasService;

    @Autowired
    IProyectosRepository proyectosRepository;

    @Autowired
    IUsuariosRepository usuariosRepository;

    @Autowired
    WebApplicationContext applicationContext;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        this.mockMvc = webAppContextSetup(this.applicationContext)
                .build();
    }

    @Test
    @WithMockUser(username = "gabriel.silva@bedu.org", password = "10$inXfzjZojaJK5rF88rkDkO04zpeWgfq/PlQIAFQrNXKIjIeRMvIba", roles = "USER")
    @Order(1)
    void getTareasById() throws Exception {
        this.mockMvc
                .perform(
                        get("/api/tareas/{id}", 1L)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.OK.value())).andDo(print())
                .andReturn().getResponse();
    }

    @Test
    @Order(2)
    @WithMockUser(username = "gabriel.silva@bedu.org", password = "10$inXfzjZojaJK5rF88rkDkO04zpeWgfq/PlQIAFQrNXKIjIeRMvIba", roles = "USER")
    void addTareaByProyectoAndUsuario() throws Exception {

        this.getUsuarioYProyecto();

        /*SimpleDateFormat fechaFormat = new SimpleDateFormat("yyyy-MM-dd");
        TareasDTO tareasToMock = new TareasDTO(1,"CARD-BEDU-010",fechaFormat.parse("2023-02-10"),2,"quiet","pendiente","Se deben de recolectar mas equipos.","10 días", proyecto, usuario);
        StringBuilder usuarioBuffer = new StringBuilder(asJsonString(tareasToMock));
        log.info(String.valueOf(usuarioBuffer));
         */

        MockHttpServletRequestBuilder builderController = MockMvcRequestBuilders
                .post("/api/tareas/proyecto/"+proyecto.getId()+"/usuario/"+usuario.getId())
                .content("{\"cardID\":\"BEDU-CULTURA-010\",\"fechaTarea\":1676008800000,\"prioridad\":2,\"transicion\":\"quiet\",\"estado\":\"pendiente\",\"descripcion\":\"Se deben de recolectar mas equipos.\",\"tiempoEstimado\":\"10 días\"}");
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

    @Test
    @Order(3)
    @WithMockUser(username = "gabriel.silva@bedu.org", password = "10$inXfzjZojaJK5rF88rkDkO04zpeWgfq/PlQIAFQrNXKIjIeRMvIba", roles = "USER")
    void updateTarea() throws Exception {
        Thread.sleep(1000);
        TareasDTO tarea = tareasService.findLastTarea();
        this.tareaIdCreated = tarea.getId();
        MockHttpServletRequestBuilder builderController = MockMvcRequestBuilders
                .put("/api/tareas/"+tareaIdCreated)
                .content("{\"cardID\":\"CARD-CULTURA-010\",\"fechaTarea\":1676008800000,\"prioridad\":2,\"transicion\":\"ToDo\",\"estado\":\"trabajando\",\"descripcion\":\"Se deben de recolectar mas equipos.\",\"tiempoEstimado\":\"10 días\"}");

        this.mockMvc
                .perform(
                        builderController
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated()).andDo(print())
                .andExpect(content().contentType("application/hal+json"))
                .andReturn().getResponse();
    }

    @Test
    @Order(4)
    @WithMockUser(username = "gabriel.silva@bedu.org", password = "10$inXfzjZojaJK5rF88rkDkO04zpeWgfq/PlQIAFQrNXKIjIeRMvIba", roles = "USER")
    void deleteTarea() throws Exception {
        Thread.sleep(2000);
        TareasDTO tarea = tareasService.findLastTarea();
        this.tareaIdCreated = tarea.getId();
        MockHttpServletRequestBuilder builderController = MockMvcRequestBuilders
                .delete("/api/tareas/"+tareaIdCreated);

        this.mockMvc
                .perform(
                        builderController
                )
                .andExpect(status().isNoContent())
                .andReturn().getResponse();
    }

    private void getUsuarioYProyecto() {
        List<Proyectos> listaProyectos = new ArrayList<>();
        proyectosRepository.findAll().forEach(listaProyectos::add);
        proyecto = listaProyectos.get(listaProyectos.size()-1);

        List<Usuarios> listaUsuarios = new ArrayList<>();
        usuariosRepository.findAll().forEach(listaUsuarios::add);
        usuario = listaUsuarios.get(listaUsuarios.size()-1);
    }

}