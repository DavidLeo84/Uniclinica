
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

/*
INSERT INTO paciente VALUES('11111111', 0, 'Nuevo Paciente', '1234567', 0, 'url_foto', '1985-03-15','Sin alergias', 1, 1);
insert into paciente values('22222222', 1, 'Otro Paciente', '2345678', 1, 'url_foto', '1990-07-20', 'Alergia a medicamentos', 2, 2);
insert into paciente values('33333333', 2, 'Ejemplo Paciente', '3456789', 2, 'url_foto', '1977-11-10', 'Sin alergias', 3, 3);
insert into paciente values('44444444', 3,  'Prueba Paciente', '4567890', 1, 'url_foto', '2002-05-30', 'Alergia al polen', 2, 4);
insert into paciente values('55555555', 4, 'Demo Paciente', '5678901', 2, 'url_foto', '1996-12-25', 'Sin alergias', 6, 5);


 */

insert into medico (cedula, ciudad, nombre, telefono, tipo_sangre, url_foto, especialidad, id) values('66666666', 0, 'nombre Médico1', '6789012', 1, 'url_foto', 0, 6);
insert into medico (cedula, ciudad, nombre, telefono, tipo_sangre, url_foto, especialidad, id) values('77777777', 1, 'nombre Médico2', '7890123', 2, 'url_foto', 1, 7);
insert into medico (cedula, ciudad, nombre, telefono, tipo_sangre, url_foto, especialidad, id) values('88888888', 0, 'nombre Médico3', '8901234', 3, 'url_foto', 2, 8);
insert into medico (cedula, ciudad, nombre, telefono, tipo_sangre, url_foto, especialidad, id) values('99999999', 1, 'nombre Médico4', '9012345', 2, 'url_foto', 1, 9);
insert into medico (cedula, ciudad, nombre, telefono, tipo_sangre, url_foto, especialidad, id) values('00000000', 0, 'nombre Médico5', '0123456', 1, 'url_foto', 3, 10);

/*
insert into administrador values('admin1',11);
insert into administrador values('admin2',12);
insert into administrador values('admin3',13);
insert into administrador values('admin4',14);
insert into administrador values('admin5',15);

insert into cita (codigo, estado, fecha_cita, fecha_creacion, motivo, medico_codigo, paciente_codigo) values(6,0,'2023-11-15 09:30:00', NOW(), 'Motivo 1', 26,6);
insert into cita (codigo, estado, fecha_cita, fecha_creacion, motivo, medico_codigo, paciente_codigo) values(7,0,'2023-11-16 10:00:00', NOW(), 'Motivo 2', 26,7);
insert into cita (codigo, estado, fecha_cita, fecha_creacion, motivo, medico_codigo, paciente_codigo) values(8,0,'2023-11-17 11:30:00', NOW(), 'Motivo 3', 26,8);
insert into cita (codigo, estado, fecha_cita, fecha_creacion, motivo, medico_codigo, paciente_codigo) values(9,0,'2023-11-18 14:00:00', NOW(), 'Motivo 4', 29,9);
insert into cita (codigo, estado, fecha_cita, fecha_creacion, motivo, medico_codigo, paciente_codigo) values(10,0,'2023-11-19 15:30:00', NOW(), 'Motivo 5', 30,10);

insert into atencion values(6, 'Nuevo diagnóstico', 'Nuevas notas médicas', 'Nuevo tratamiento',6);
insert into atencion values(7, 'Otro diagnóstico', 'Otras notas médicas', 'Otro tratamiento',7);
insert into atencion values(8, 'Diagnóstico ejemplo', 'Notas médicas de ejemplo', 'Tratamiento de ejemplo',8);
insert into atencion values(9, 'Diagnóstico prueba', 'Notas médicas de prueba', 'Tratamiento de prueba',9);
insert into atencion values(10, 'Diagnóstico demo', 'Notas médicas demo', 'Tratamiento demo',10);

insert into dia_libre values(6,'2023-11-11', 26);
insert into dia_libre values(7,'2023-11-12',27);
insert into dia_libre values(8,'2023-11-13',28);
insert into dia_libre values(9,'2023-11-14',29);
insert into dia_libre values(10,'2023-11-15',30);

insert into horario_medico values(6,'LUNES','16:00:00', '09:00:00', 26);
insert into horario_medico values(7,'MARTES','16:00:00', '09:00:00', 27);
insert into horario_medico values(8,'MIÉRCOLES','16:00:00', '09:00:00', 28);
insert into horario_medico values(9,'JUEVES','16:00:00', '09:00:00', 29);
insert into horario_medico values(10,'VIERNES','16:00:00', '09:00:00', 30);

insert into pqrs values(6,1,'2023-10-16 14:30:00', 'Resolución', 'Comentario',6);
insert into pqrs values(7,1,'2023-10-17 14:30:00', 'Resolución', 'Comentario',7);
insert into pqrs values(8,1,'2023-10-18 14:30:00', 'Resolución', 'Comentario',8);
insert into pqrs values(9,1,'2023-10-19 14:30:00', 'Resolución', 'Comentario',9);
insert into pqrs values(10,1,'2023-10-20 14:30:00', 'Resolución', 'Comentario',10);

insert into mensaje values(6,'Hola de nuevo','2023-10-16 14:30:00', 'Respuesta', 6,6,6);
insert into mensaje values(7,'Estoy bien, gracias','2023-10-17 14:30:00', 'Respuesta', 7,7,6);
insert into mensaje values(8,'Más contenido','2023-10-18 14:30:00', 'Respuesta', 8,8,6);
insert into mensaje values(9,'Más contenido','2023-10-19 14:30:00', 'Respuesta', 9,9,7);
insert into mensaje values(10,'Más contenido','2023-10-20 14:30:00', 'Respuesta', 10,10,7);

 */