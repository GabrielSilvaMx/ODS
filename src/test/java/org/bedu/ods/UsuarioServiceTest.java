package org.bedu.ods;

import org.bedu.ods.entity.dto.UsuariosDTO;
import org.bedu.ods.service.impl.UsuariosImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.datasource.url=jdbc:mysql://${LOCAL_DATABASE_NAME}:3306/db_ods_test?serverTimezone=UTC")
@TestPropertySource(locations = "classpath:db-test.properties")
@Sql("classpath:test-mysql.sql")
class UsuarioServiceTest {

    @Test
    void testFindAll(@Autowired UsuariosImpl usuariosService) {
        String nombre = "";
        List<UsuariosDTO> allUsuarios = usuariosService.findAll(nombre);
        assertEquals(2, allUsuarios.size());

    }
}
