package org.bedu.ods;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.bedu.ods.controller.AuthenticationRequest;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serial;

import static io.restassured.RestAssured.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:db-test.properties")
@Sql("classpath:test-mysql.sql")
@AutoConfigureMockMvc //need this in Spring Boot test
public class AuthenticationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    @LocalServerPort
    private int port;

    String token;

    @Mock
    UsernamePasswordAuthenticationToken authentication;

    @Mock
    UsernamePasswordAuthenticationToken  principal;


    public static class MockSecurityContext implements SecurityContext {

        @Serial
        private static final long serialVersionUID = -6986746375915710855L;

        private Authentication authentication;

        public MockSecurityContext(Authentication authentication) {
            this.authentication = authentication;
        }

        @Override
        public Authentication getAuthentication() {
            return this.authentication;
        }

        @Override
        public void setAuthentication(Authentication authentication) {
            this.authentication = authentication;
        }
    }


    @Before
    public void setup() {

        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();

        RestAssured.port = this.port;
        var usuario="gabriel.silva@bedu.org";

        this.token = given()
                .contentType(ContentType.JSON)
                .body(AuthenticationRequest
                        .builder()
                        .username(usuario)
                        .password("{bcrypt}$10$inXfzjZojaJK5rF88rkDkO04zpeWgfq/PlQIAFQrNXKIjIeRMvIba")
                        .build())
                .when().post("/auth/signin")
                .andReturn().jsonPath().getString("username");

    }

    @Test
    @WithMockUser(username = "gabriel.silva@bedu.org", password = "$10$inXfzjZojaJK5rF88rkDkO04zpeWgfq/PlQIAFQrNXKIjIeRMvIba", roles = "USER")
    public void signedIn() throws Exception {

        //principal = this.getPrincipal("gabriel.silva@bedu.org");

        MockHttpSession session = new MockHttpSession();
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                new MockSecurityContext(principal));

        String actual = mockMvc
                .perform(
                        get("/api/usuarios/welcome")
                                .header("Authorization", "Bearer "+ this.token)

                )
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertEquals("Bienvenido gabriel.silva@bedu.org", actual);
    }

}
