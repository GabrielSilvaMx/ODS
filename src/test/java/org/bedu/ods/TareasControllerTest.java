package org.bedu.ods;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bedu.ods.entity.Proyectos;
import org.bedu.ods.entity.Usuarios;
import org.bedu.ods.entity.dto.TareasDTO;
import org.bedu.ods.repository.IProyectosRepository;
import org.bedu.ods.repository.IUsuariosRepository;
import org.bedu.ods.service.impl.TareasImpl;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:db-test.properties")
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
    void getTareasByIdTest() throws Exception {
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
    void addTareaByProyectoAndUsuarioTest() throws Exception {

        this.getUsuarioYProyectoTest();

        MockHttpServletRequestBuilder builderController = MockMvcRequestBuilders
                .post("/api/tareas/proyecto/"+proyecto.getId()+"/usuario/"+usuario.getId())
                .content("{\"cardID\":\"BEDU-CULTURA-010\",\"date\":"+getFecha(2023,Calendar.AUGUST,10).getTime()+",\"priority\":2,\"transicion\":\"quiet\",\"estado\":\"pendiente\",\"description\":\"Se deben de recolectar mas equipos.\",\"tiempoEstimado\":\"10 días\"}");
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
    void updateTareaTest() throws Exception {

        TareasDTO tarea = tareasService.findLastTarea();
        this.tareaIdCreated = tarea.getId();
        MockHttpServletRequestBuilder builderController = MockMvcRequestBuilders
                .put("/api/tareas/"+tareaIdCreated)
                .content("{\"cardID\":\"CARD-CULTURA-010\",\"date\":"+getFecha(2024,Calendar.SEPTEMBER,12).getTime()+",\"priority\":2,\"transicion\":\"ToDo\",\"estado\":\"trabajando\",\"description\":\"Se deben de recolectar mas equipos.\",\"tiempoEstimado\":\"10 días\"}");

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
    void deleteTareaTest() throws Exception {

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

    private void getUsuarioYProyectoTest() {
        List<Proyectos> listaProyectos = new ArrayList<>();
        try (Stream<Proyectos> proyectos = proyectosRepository.findAll().stream() ) {
            proyectos.forEach(listaProyectos::add);
        }
        proyecto = listaProyectos.get(listaProyectos.size()-1);

        List<Usuarios> listaUsuarios = new ArrayList<>();
        try (Stream<Usuarios> listUsuarios = usuariosRepository.findAll().stream() ) {
            listUsuarios.forEach(listaUsuarios::add);
        }
        usuario = listaUsuarios.get(listaUsuarios.size()-1);
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