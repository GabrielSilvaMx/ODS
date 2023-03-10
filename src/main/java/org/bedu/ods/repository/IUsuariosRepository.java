package org.bedu.ods.repository;

import org.bedu.ods.entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.LinkedList;
import java.util.Optional;

public interface IUsuariosRepository extends JpaRepository<Usuarios, Long> {
    LinkedList<Usuarios> findByNombreContaining(String nombre);

    LinkedList<Usuarios> findUsuariosByProyectosId(Long proyectoId); // Regresa todos los Usuarios que se relacionan con el id del Proyecto

    Usuarios findByIdAndProyectosId(Long idUsuario, Long idProyecto);

    Optional<Usuarios> findByCorreo(String correo);

}
