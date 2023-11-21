
insert into cuenta (id, correo, estado, password) values(1, 'nuevo@email.com', 0, '$2a$10$Ji66OrNiC7bfwkPqxlHMeOBa1aqS0oftB2T5udKm5OE4uFOqxyRpO');
insert into cuenta (id, correo, estado, password) values(2, 'otro@email.com', 0 , '444');
insert into cuenta (id, correo, estado, password) values(3, 'ejemplo@email.com', 0, '555');
insert into cuenta (id, correo, estado, password) values(4, 'prueba@email.com', 0, '666');
insert into cuenta (id, correo, estado, password) values(5, 'demo@email.com', 0, '777');

insert into cuenta (id, correo, estado, password) values(6, 'usuario@email.com', 0, '888');
insert into cuenta (id, correo, estado, password) values(7, 'correo@email.com', 0, '999');
insert into cuenta (id, correo, estado, password) values(8, 'email@email.com', 0, '000');
insert into cuenta (id, correo, estado, password) values(9, 'test@email.com', 0, '111');
insert into cuenta (id, correo, estado, password) values(10, 'dato@email.com', 0, '222');

insert into cuenta (id, correo, estado, password) values(11, 'nuevo2@email.com',0, '333');
insert into cuenta (id, correo, estado, password) values(12, 'otro2@email.com',0 , '444');
insert into cuenta (id, correo, estado, password) values(13, 'ejemplo2@email.com',0 , '555');
insert into cuenta (id, correo, estado, password) values(14, 'prueba2@email.com',0 , '666');
insert into cuenta (id, correo, estado, password) values(15, 'demo2@email.com',0 , '777');

INSERT INTO paciente (ciudad, eps, fecha_nacimiento, id, tipo_sangre, cedula, telefono, nombre, alergias, url_foto) VALUES ( 0, 1, '1985-03-15', 1, 0, '11111111', '12345678', 'Nuevo Paciente', 'Sin alergias', 'url_foto');
INSERT INTO paciente (ciudad, eps, fecha_nacimiento, id, tipo_sangre, cedula, telefono, nombre, alergias, url_foto) VALUES ( 2, 2, '1990-07-20', 2, 3, '22222222', '87654321', 'Otro Paciente', 'Alergia a medicamentos','url_foto');
INSERT INTO paciente (ciudad, eps, fecha_nacimiento, id, tipo_sangre, cedula, telefono, nombre, alergias, url_foto) VALUES ( 0, 1, '1989-01-23', 3, 2, '33333333', '12343578', 'Nuevo Paciente2', 'Sin alergias', 'url_foto');
INSERT INTO paciente (ciudad, eps, fecha_nacimiento, id, tipo_sangre, cedula, telefono, nombre, alergias, url_foto) VALUES ( 2, 2, '1990-07-20', 4, 3, '44444444', '75654321', 'Otro Paciente2', 'Alergia a medicamentos','url_foto');
INSERT INTO paciente (ciudad, eps, fecha_nacimiento, id, tipo_sangre, cedula, telefono, nombre, alergias, url_foto) VALUES ( 0, 1, '1985-03-15', 5, 4, '55555555', '12347578', 'Nuevo Paciente3', 'Sin alergias', 'url_foto');

insert into medico (cedula, ciudad, nombre, telefono, tipo_sangre, url_foto, especialidad, id) values('666', 0, 'nombre Médico1', '6789012', 1, 'url_foto', 1, 6);
insert into medico (cedula, ciudad, nombre, telefono, tipo_sangre, url_foto, especialidad, id) values('777', 1, 'nombre Médico2', '7890123', 2, 'url_foto', 1, 7);
insert into medico (cedula, ciudad, nombre, telefono, tipo_sangre, url_foto, especialidad, id) values('888', 0, 'nombre Médico3', '8901234', 3, 'url_foto', 0, 8);
insert into medico (cedula, ciudad, nombre, telefono, tipo_sangre, url_foto, especialidad, id) values('999', 1, 'nombre Médico4', '9012345', 2, 'url_foto', 0, 9);
insert into medico (cedula, ciudad, nombre, telefono, tipo_sangre, url_foto, especialidad, id) values('323', 0, 'nombre Médico5', '0123456', 1, 'url_foto', 5, 10);


insert into administrador(id, nombre) values(11, 'admin1');
insert into administrador(id, nombre) values(12, 'admin2');
insert into administrador(id, nombre) values(13, 'admin3');
insert into administrador(id, nombre) values(14, 'admin4');
insert into administrador(id, nombre) values(15, 'admin5');


insert into cita (codigo, estado_cita, medico_id, paciente_id, fecha_cita, fecha_creacion, motivo) values (1, 0, 7, 1, '2023-10-23 09:30:00', NOW(), 'Motivo 1');
insert into cita (codigo, estado_cita, medico_id, paciente_id, fecha_cita, fecha_creacion, motivo) values (2, 0, 7, 1, '2023-11-25 10:00:00', NOW(), 'Motivo 2');
insert into cita (codigo, estado_cita, medico_id, paciente_id, fecha_cita, fecha_creacion, motivo) values (3, 0, 7, 2, '2023-11-19 11:30:00', NOW(), 'Motivo 3');
insert into cita (codigo, estado_cita, medico_id, paciente_id, fecha_cita, fecha_creacion, motivo) values (4, 0, 7, 2, '2023-11-20 14:00:00', NOW(), 'Motivo 4');
insert into cita (codigo, estado_cita, medico_id, paciente_id, fecha_cita, fecha_creacion, motivo) values (5, 0, 7, 3, '2023-11-21 15:30:00', NOW(), 'Motivo 5');

insert into atencion(cita_codigo, codigo, precio, diagnostico, notas_medicas, tratamiento) values (1, 1, 80000, 'Nuevo diagnóstico', 'Nuevas notas médicas', 'Nuevo tratamiento');
insert into atencion(cita_codigo, codigo, precio, diagnostico, notas_medicas, tratamiento) values (2, 2, 80000, 'Otro diagnóstico', 'Otras notas médicas', 'Otro tratamiento');
insert into atencion(cita_codigo, codigo, precio, diagnostico, notas_medicas, tratamiento) values (3, 3, 80000, 'Diagnóstico ejemplo', 'Notas médicas de ejemplo', 'Tratamiento de ejemplo');
insert into atencion(cita_codigo, codigo, precio, diagnostico, notas_medicas, tratamiento) values (4, 4, 80000, 'Diagnóstico prueba', 'Notas médicas de prueba', 'Tratamiento de prueba');
insert into atencion(cita_codigo, codigo, precio, diagnostico, notas_medicas, tratamiento) values (5, 5, 80000, 'Diagnóstico demo', 'Notas médicas demo', 'Tratamiento demo');




insert into dia_libre_medico (codigo, fecha_libre, medico_id) VALUES (6,'2023-12-11' ,6);
insert into dia_libre_medico (codigo, fecha_libre, medico_id) VALUES (7,'2023-11-12', 7);
insert into dia_libre_medico (codigo, fecha_libre, medico_id) VALUES (8,'2023-11-13', 8);
insert into dia_libre_medico (codigo, fecha_libre, medico_id) VALUES (9,'2023-11-14' ,9);
insert into dia_libre_medico (codigo, fecha_libre, medico_id) VALUES (10,'2023-11-15', 10);


insert into horario_medico(codigo, medico_id, hora_inicio, hora_salida) VALUES (1, 6, '2023-10-09 07:00:00', '2023-10-09 17:00:00');
insert into horario_medico(codigo, medico_id, hora_inicio, hora_salida) VALUES (2, 6, '2023-10-10 07:00:00', '2023-10-10 17:00:00');
insert into horario_medico(codigo, medico_id, hora_inicio, hora_salida) VALUES (3, 6, '2023-10-11 07:00:00', '2023-10-11 17:00:00');
insert into horario_medico(codigo, medico_id, hora_inicio, hora_salida) VALUES (4, 6, '2023-10-12 07:00:00', '2023-10-12 17:00:00');
insert into horario_medico(codigo, medico_id, hora_inicio, hora_salida) VALUES (5, 6, '2023-10-13 07:00:00', '2023-10-13 17:00:00');



insert into pqrs(cita_codigo, codigo, estado_pqrs, tipo_pqrs, fecha_creacion, motivo) values (1, 1, 1, 0, '2023-10-16 14:30:00', 'motivo');
insert into pqrs(cita_codigo, codigo, estado_pqrs, tipo_pqrs, fecha_creacion, motivo) values (2, 2, 1, 0, '2023-10-17 15:30:00', 'motivo');
insert into pqrs(cita_codigo, codigo, estado_pqrs, tipo_pqrs, fecha_creacion, motivo) values (3, 3, 1, 0, '2023-10-18 16:30:00', 'motivo');
insert into pqrs(cita_codigo, codigo, estado_pqrs, tipo_pqrs, fecha_creacion, motivo) values (4, 4, 1, 0, '2023-10-19 17:30:00', 'motivo');
insert into pqrs(cita_codigo, codigo, estado_pqrs, tipo_pqrs, fecha_creacion, motivo) values (5 ,5, 1, 0, '2023-10-20 18:30:00', 'motivo');


/*
INSERT INTO mensaje(codigo, cuenta_id, pqrs_codigo, fecha_mensaje, contenido) values (1, 1, 1, 'Respuesta', 'Hola de nuevo','2023-10-16 14:30:00', , 6,6,6);
INSERT INTO mensaje(codigo, cuenta_id, pqrs_codigo, fecha_mensaje, contenido) values (2, 11, 1, 'Estoy bien, gracias','2023-10-17 14:30:00', 'Respuesta', 7,7,6);
INSERT INTO mensaje(codigo, cuenta_id, pqrs_codigo, fecha_mensaje, contenido) values (3, 2, 2, 'Más contenido','2023-10-18 14:30:00', 'Respuesta', 8,8,6);
INSERT INTO mensaje(codigo, cuenta_id, pqrs_codigo, fecha_mensaje, contenido) values (4, 12, 2, 'Más contenido','2023-10-19 14:30:00', 'Respuesta', 9,9,7);
INSERT INTO mensaje(codigo, cuenta_id, pqrs_codigo, fecha_mensaje, contenido) values (5, 1, 1, 'Más contenido','2023-10-20 14:30:00', 'Respuesta', 10,10,7);



 */





