package org.bedu.ods.Config;

import lombok.RequiredArgsConstructor;
import org.bedu.ods.entity.Proyectos;
import org.bedu.ods.entity.Tareas;
import org.bedu.ods.entity.Usuarios;
import org.bedu.ods.repository.IProyectosRepository;
import org.bedu.ods.repository.ITareasRepository;
import org.bedu.ods.repository.IUsuariosRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

//@Component
@RequiredArgsConstructor
public class Seeder implements CommandLineRunner {

    private final IUsuariosRepository usuariosRepository;
    private final IProyectosRepository proyectosRepository;
    private final ITareasRepository tareasRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void run(String[] args) throws ParseException {

        Set<Usuarios> usuarios = new HashSet<>();

        SimpleDateFormat fechaFormat = new SimpleDateFormat("yyyy-MM-dd");

        Usuarios _usuario1 = new Usuarios(
                "Jorge Ramón",
                "jorge.ramon@bedu.org",
                "jorger",
                passwordEncoder.encode("bedu$12345"),
                "ADMIN"
        );
        usuariosRepository.save(_usuario1);
        Usuarios _usuario2 = new Usuarios(
                "Gabriel Silva",
                "gabriel.silva@bedu.org",
                "gsilvav",
                passwordEncoder.encode("12345Abc"),
                "USER"

        );
        usuariosRepository.save(_usuario1);
        Usuarios _usuario3 = new Usuarios(
                "Javi",
                "javier@bedu.org",
                "javi",
                passwordEncoder.encode("12345Abc"),
                "ADMIN"

        );

        usuariosRepository.save(_usuario2);

        usuarios.add(_usuario1);
        usuarios.add(_usuario2);

        /*
        Nombre del proyecto
        fecha compromiso
        fecha entrega
        clase de servicio  (fire, due date, normal<default>)
        limite wip
        finalizado
        estatus (waiting, progress, finalized, canceled)
                */
        Proyectos _proyecto = new Proyectos(
                "Proyecto Reciclaje de equipos y aparatos electrónicos.",
                "En BEDU se llevará a cabo la recolección de aparatos electrónicos, " +
                        "Jorge Ramón es el responsable del equipo y le informará a cada integrante " +
                        "del equipo cómo llevar a cabo la tarea que se le asignó y se irá registrando " +
                        "en el tablero Kanban. ",
                fechaFormat.parse("2022-12-18"),
                fechaFormat.parse("2023-01-15"),
                fechaFormat.parse("2023-01-16"),
                "normal",
                2,
                "progress"
        );
        _proyecto.setUsuarios(usuarios);
        proyectosRepository.save(_proyecto);

        /*
        card id
        prioridad (1,2,3)
        estado (Do/Progress/Done)
        transición ( waiting, working, review, terminated, blocked)
        descripcion
        tiempo estimado: n (horas/dias/semanas/meses)
        id proyecto
        id usuario
         */
        //Agregar tareas al tablero Kanban
        Tareas _tarea1 = new Tareas();
        _tarea1.setProyecto(_proyecto);
        _tarea1.setUsuario(_usuario2);
        _tarea1.setCardID("BEDU-RECICLA-001");
        _tarea1.setFechaTarea(fechaFormat.parse("2022-12-19"));
        _tarea1.setPrioridad(1);
        _tarea1.setTransicion("working");
        _tarea1.setEstado("progress");
        _tarea1.setDescripcion("a) Realizaré una encuesta al personal para ver si tienen algún" +
                " equipo que ya no les sirva.");
        _tarea1.setTiempoEstimado("1 semana");
        tareasRepository.save(_tarea1);

        Tareas _tarea2 = new Tareas();
        _tarea2.setProyecto(_proyecto);
        _tarea2.setUsuario(_usuario2);
        _tarea2.setCardID("BEDU-RECICLA-002");
        _tarea2.setFechaTarea(fechaFormat.parse("2023-01-10"));
        _tarea2.setPrioridad(3);
        _tarea2.setTransicion("waiting");
        _tarea2.setEstado("Do");
        _tarea2.setDescripcion("a) Pasaré con cada uno del personal para recoger " +
                "el equipo electrónico que ya no les sirva. " +
                "b) Se guardarán en almacén temporalmente.");
        _tarea2.setTiempoEstimado("3 días");
        tareasRepository.save(_tarea2);

    }
}
