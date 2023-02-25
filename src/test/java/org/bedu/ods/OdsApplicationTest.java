package org.bedu.ods;

import org.bedu.ods.controller.TestController;
import org.bedu.ods.entity.UsuarioServiceTest;
import static org.assertj.core.api.Assertions.assertThat;

import org.bedu.ods.entity.mapper.UsuariosMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UsuarioServiceTest.class,
        AuthenticationTest.class,
        UsuariosControllerTest.class,
        ProyectosControllerTest.class,
        TareasControllerTest.class,
})
@SpringBootTest
class OdsApplicationTest {

    @Mock
    public TestController testController;

    @Test
    void contextLoads() {
        assertThat(testController).isNotNull();
    }

}
