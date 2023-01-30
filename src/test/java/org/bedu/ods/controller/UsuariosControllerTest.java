package org.bedu.ods.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bedu.ods.entity.dto.UsuariosDTO;
import org.bedu.ods.service.impl.UsuariosImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.SecureRandom;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UsuariosControllerTest {

    private MockMvc mockMvc;

    @Autowired
    UsuariosImpl usuariosService;

    Long usrIdCreated;

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
    @Order(1)
    @DisplayName("Se registra un nuevo usuario, la autorización es anonima.")
    @WithAnonymousUser()
    void createUsuario() throws Exception  {
        /*Set<String> roles = new HashSet<>();

        roles.add("MANAGER");
        UsuariosDTO usuarioToMock = new UsuariosDTO(3,"Javier X.","javier@bedu.org","javi","123456$890.","MANAGER","123456$890.", roles);
        StringBuilder usuarioBuffer = new StringBuilder(asJsonString(usuarioToMock));

        usuarioBuffer.insert(Math.max(usuarioBuffer.length() - 1, 0), ",\"password\":\"123456$890.\",\"rePassword\":\"123456$890.\"");
        log.info(String.valueOf(usuarioBuffer));*/
        SecureRandom rand = new SecureRandom();
        String email = "javier" + rand.nextInt(1000) + "@bedu.org";

        MockHttpServletRequestBuilder builderController = MockMvcRequestBuilders
                .post("/api/usuarios/registro")
                .content("{\"usuario\":\"Javier X.\",\"correo\":\""+email+"\",\"alias\":\"javiBedu\",\"rol\":\"MANAGER\",\"password\":\"123456$890.\",\"rePassword\":\"123456$890.\"}");

        MvcResult result = this.mockMvc
                .perform(
                        builderController
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated()).andDo(print())
                .andExpect(content().contentType("application/hal+json"))
                .andReturn();
        /* this.usrIdCreated = result.getResponse().getContentAsString();
        JSONObject jsonObject= new JSONObject(this.usrIdCreated);
        log.info("ID USR: " +jsonObject.get("id").toString()); */
        result.getResponse();
    }

    @Test
    @Order(3)
    @WithMockUser(username = "jorge.ramon@bedu.org", password = "$10$WCCrN6o/ZvjmNG6d3smVUO6701WAcpTTWcFCj9Cg5yGPgRV7Mp9Mq", roles = "ADMIN")
    void getByIdUsuario() throws Exception {
        UsuariosDTO user = usuariosService.findLastUser();
        this.usrIdCreated = user.getId();
        this.mockMvc
                .perform(
                        get("/api/usuarios/"+this.usrIdCreated)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.OK.value())).andDo(print())
                .andReturn().getResponse();
    }

    @Test
    @Order(2)
    @WithMockUser(username = "jorge.ramon@bedu.org", password = "$10$WCCrN6o/ZvjmNG6d3smVUO6701WAcpTTWcFCj9Cg5yGPgRV7Mp9Mq", roles = "ADMIN")
    void getAllUsuarios() throws Exception {
        this.mockMvc
                .perform(
                        get("/api/usuarios")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is(HttpStatus.OK.value())).andDo(print())
                .andReturn().getResponse();
    }

    @Test
    @Order(4)
    @WithMockUser(username = "gabriel.silva@bedu.org", password = "10$inXfzjZojaJK5rF88rkDkO04zpeWgfq/PlQIAFQrNXKIjIeRMvIba", roles = "USER")
    void updateUsuario() throws  Exception {
        Thread.sleep(1000);
        UsuariosDTO user = usuariosService.findLastUser();
        this.usrIdCreated = user.getId();
        MockHttpServletRequestBuilder builderController = MockMvcRequestBuilders
                .put("/api/usuarios/"+this.usrIdCreated)
                .content("{\"usuario\":\"Gabriel Silva\",\"correo\":\"gabo_sv@bedu.org\",\"alias\":\"gsilvav\",\"rol\":\"MANAGER\",\"password\":\"12345Abc\",\"rePassword\":\"12345Abc\"}");

        this.mockMvc
                .perform(
                        builderController
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated()).andDo(print())
                .andExpect(content().contentType("application/hal+json"))
                .andReturn().getResponse();
    }

    @Test()
    @Order(5)
    @WithMockUser(username = "jorge.ramon@bedu.org", password = "$10$WCCrN6o/ZvjmNG6d3smVUO6701WAcpTTWcFCj9Cg5yGPgRV7Mp9Mq", roles = "ADMIN")
    void deleteUsuario() throws Exception{
        Thread.sleep(2000);
        UsuariosDTO user = usuariosService.findLastUser();
        this.usrIdCreated = user.getId();
        MockHttpServletRequestBuilder builderController = MockMvcRequestBuilders
                .delete("/api/usuarios/"+this.usrIdCreated);

        this.mockMvc
                .perform(
                        builderController
                )
                .andExpect(status().isNoContent())
                .andReturn().getResponse();
    }

}