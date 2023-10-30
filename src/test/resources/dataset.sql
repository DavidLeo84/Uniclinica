insert into cuenta values(6, 'nuevo@email.com', '$2a$10$Ji66OrNiC7bfwkPqxlHMeOBa1aqS0oftB2T5udKm5OE4uFOqxyRpO');
insert into cuenta values(7, 'otro@email.com', '444');
insert into cuenta values(8, 'ejemplo@email.com', '555');
insert into cuenta values(9, 'prueba@email.com', '666');
insert into cuenta values(10, 'demo@email.com', '777');

insert into cuenta values(26, 'usuario@email.com', '888');
insert into cuenta values(27, 'correo@email.com', '999');
insert into cuenta values(28, 'email@email.com', '000');
insert into cuenta values(29, 'test@email.com', '111');
insert into cuenta values(30, 'dato@email.com', '222');

insert into cuenta values(31, 'nuevo2@email.com', '333');
insert into cuenta values(32, 'otro2@email.com', '444');
insert into cuenta values(33, 'ejemplo2@email.com', '555');
insert into cuenta values(34, 'prueba2@email.com', '666');
insert into cuenta values(35, 'demo2@email.com', '777');

insert into paciente values('11111111', 0, 0, 'Nuevo Paciente', '1234567', 'url_foto', 'Sin alergias', 1,'1985-03-15', 1, 6);
insert into paciente values('22222222', 1, 0, 'Otro Paciente', '2345678', 'url_foto', 'Alergia a medicamentos',2, '1990-07-20', 1, 7);
insert into paciente values('33333333', 0, 0, 'Ejemplo Paciente', '3456789', 'url_foto', 'Sin alergias', 3, '1977-11-10', 1, 8);
insert into paciente values('44444444', 1, 0, 'Prueba Paciente', '4567890', 'url_foto', 'Alergia al polen', 2, '2002-05-30', 0, 9);
insert into paciente values('55555555', 0, 0, 'Demo Paciente', '5678901', 'url_foto', 'Sin alergias', 5, '1996-12-25', 0, 10);

insert into medico values('66666666', 0, 0, 'Usuario Médico', '6789012', 'url_foto', 0, 26);
insert into medico values('77777777', 1, 0, 'Correo Médico', '7890123', 'url_foto', 1, 27);
insert into medico values('88888888', 0, 0, 'Email Médico', '8901234', 'url_foto', 2, 28);
insert into medico values('99999999', 1, 0, 'Test Médico', '9012345', 'url_foto', 3, 29);
insert into medico values('00000000', 0, 0, 'Dato Médico', '0123456', 'url_foto', 4, 30);

insert into administrador values(31);
insert into administrador values(32);
insert into administrador values(33);
insert into administrador values(34);
insert into administrador values(35);

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