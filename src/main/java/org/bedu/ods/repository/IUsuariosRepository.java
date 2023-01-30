package org.bedu.ods.repository;

import org.bedu.ods.entity.Usuarios;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.LinkedList;
import java.util.Optional;

public interface IUsuariosRepository extends JpaRepository<Usuarios, Long> {
    LinkedList<Usuarios> findByNombreContaining(String nombre);

    /*
    Regresa todos los Usuarios que se relacionan con el id del Proyecto
     */
    LinkedList<Usuarios> findUsuariosByProyectosId(Long proyectoId);

    Usuarios findByIdAndProyectosId(Long idUsuario, Long idProyecto);

    @Cacheable
    Optional<Usuarios> findByCorreo(String correo);

    Optional<Usuarios> findByAlias(String username);

}
