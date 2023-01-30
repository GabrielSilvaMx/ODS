BEGIN;

DELETE FROM `tareas`;

DELETE FROM `proyecto_usuario`;

DELETE FROM `proyectos`;

DELETE FROM `usuarios`;

INSERT INTO `usuarios` (`id`, `alias`, `correo`, `created_at`, `nombre`, `password`, `rol`, `updated_at`) VALUES (1,'jorger','jorge.ramon@bedu.org','2023-01-29 11:06:31.726450','Jorge Ramón','$2a$10$oso4.TLU2af60UdO3BVxO.ZtkrI2VuiisaJgr5bYqL4BDcQKn5lrq','MANAGER','2023-01-29 11:06:32.007491');
INSERT INTO `usuarios` (`id`, `alias`, `correo`, `created_at`, `nombre`, `password`, `rol`, `updated_at`) VALUES (2,'gsilvav','gabriel.silva@bedu.org','2023-01-29 11:06:31.912209','Gabriel Silva','$2a$10$IkcMwrSmxzBFCHleuPB.zOQzE3gPuX9MbHXsAFq3DrSVK6I84m172','USER','2023-01-29 11:06:32.232848');

INSERT INTO `proyectos` (`id`, `clase_servicio`, `created_at`, `descripcion`, `estatus`, `fecha_compromiso`, `fecha_entrega`, `fecha`, `limite_wip`, `nombre`, `updated_at`) VALUES (1,'normal','2023-01-29 11:06:32.336859','En BEDU se llevará a cabo la recolección de aparatos electrónicos, Jorge Ramón es el responsable del equipo y le informará a cada integrante del equipo cómo llevar a cabo la tarea que se le asignó y se irá registrando en el tablero Kanban. ','progress','2023-01-15 00:00:00.000000','2023-01-16 00:00:00.000000','2022-12-18 00:00:00.000000',2,'Proyecto Reciclaje de equipos y aparatos electrónicos.','2023-01-29 11:06:32.340851');

INSERT INTO `proyecto_usuario` (`proyecto_id`, `usuario_id`) VALUES (1,1);
INSERT INTO `proyecto_usuario` (`proyecto_id`, `usuario_id`) VALUES (1,2);

INSERT INTO `tareas` (`id`, `card_id`, `created_at`, `descripcion`, `estado`, `fecha`, `prioridad`, `tiempo_estimado`, `transicion`, `updated_at`, `proyecto_id`, `usuario_id`) VALUES (1,'BEDU-RECICLA-001','2023-01-29 11:06:32.428848','a) Realizaré una encuesta al personal para ver si tienen algún equipo que ya no les sirva.','progress','2022-12-19 00:00:00.000000',1,'1 semana','working','2023-01-29 11:06:32.430851',1,2);
INSERT INTO `tareas` (`id`, `card_id`, `created_at`, `descripcion`, `estado`, `fecha`, `prioridad`, `tiempo_estimado`, `transicion`, `updated_at`, `proyecto_id`, `usuario_id`) VALUES (2,'BEDU-RECICLA-002','2023-01-29 11:06:32.496852','a) Pasaré con cada uno del personal para recoger el equipo electrónico que ya no les sirva. b) Se guardarán en almacén temporalmente.','Do','2023-01-10 00:00:00.000000',3,'3 días','waiting','2023-01-29 11:06:32.498858',1,2);

COMMIT;