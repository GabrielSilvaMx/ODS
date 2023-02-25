package org.bedu.ods;

import org.bedu.ods.entity.UsuarioServiceTest;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
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

    @Test
    void contextLoads() {
    }

}