package org.bedu.ods.entity;

import org.bedu.ods.entity.dto.UsuariosDTO;
import org.bedu.ods.entity.mapper.UsuariosMapper;
import org.bedu.ods.service.impl.UsuariosImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = "spring.datasource.url=jdbc:mysql://${AZ_DATABASE_NAME}.mysql.database.azure.com:3306/ods_test?serverTimezone=UTC")
@TestPropertySource(locations = "classpath:db-test.properties")
@Sql("classpath:test-mysql.sql")
public class UsuarioServiceTest {

    @Mock
    public UsuariosMapper usrMapper;

    @Test
    void testFindAll(@Autowired UsuariosImpl usuariosService) {
        String nombre = "";
        List<UsuariosDTO> allUsuarios = usuariosService.findAll(nombre);
        assertEquals(2, allUsuarios.size());

    }
}
