insert into ciudad values (1, "Armenia");
insert into ciudad values (2, "Pereira");
insert into ciudad values (3, "Cali");
insert into ciudad values (4, "Medellin");
insert into ciudad values (5, "Bogota");

insert into teatro values (1, "Carrera 18", "Unicine Armenia", 1);
insert into teatro values (2, "Carrera 10", "Unicine Arroz", 1);
insert into teatro values (3, "Carrera 14", "Unicine Cali", 3);
insert into teatro values (4, "Carrera 12", "Unicine Pereira", 2);
insert into teatro values (5, "Carrera 13", "Unicine Bogotá", 5);

insert into admin_general values (1,"35ad", "harry@email.com", "Harry Styles", 1);
insert into admin_general values (2,"4add", "frank@email.com", "Frank Ocean", 3);
insert into admin_general values (3,"3jka", "rex@email.com", "Rex Orange", 5);
insert into admin_general values (4,"35ba", "pualo@email.com", "Paulo Snezze", 5);
insert into admin_general values (5,"l5yk", "drake@email.com", "Drake yeye", 5);

insert into admin_teatro values (1,"33ks", "bruno@email.com", "Bruno Mars", 1);
insert into admin_teatro values (2,"35wa", "ferxxo@email.com", "Feid Mor", 3);
insert into admin_teatro values (3,"o705", "steve@email.com", "Steve Wonder", 5);
insert into admin_teatro values (4,"046j", "miles@email.com", "Miles Morales", 4);
insert into admin_teatro values (5,"3ow9", "fredy@email.com", "Fredy Mercury", 2);

insert into evento values (1, '2022-08-17', '10:30:00', '09:30:00', "url", "Evento Fin de Anio", 1);
insert into evento values (2, '2022-06-10', '04:00:00', '02:15:00', "url", "Evento Familia", 1);
insert into evento values (3, '2022-11-26', '05:30:00', '01:20:00', "url", "Evento Spider-Man", 2);
insert into evento values (4, '2022-12-01', '07:30:00', '04:30:00', "url", "Evento Arroz y pollo", 3);
insert into evento values (5, '2022-10-04', '11:15:00', '01:00:00', "url", "Evento Arroz y pollo", 5);

insert into confiteria values (1, "url", 15000, "Crispetas Mediana");
insert into confiteria values (2, "url", 6000, "Agua");
insert into confiteria values (3, "url", 10000, "Gaseosa Mediana");
insert into confiteria values (4, "url", 40000, "Combo parejita");
insert into confiteria values (5, "url", 13000, "Nachos");

insert into cliente values (1,"1234","julian@email.com", 1, "url","Julian Acosta");
insert into cliente values (2,"3jn1","juan@email.com", 1, "url","Juan Bunny");
insert into cliente values (3,"34a4","pepe@email.com", 0, "url","Pepe Lipa");
insert into cliente values (4,"a378","mailo@email.com", 0, "url","Mailo Styles");
insert into cliente values (5,"b35a","diego@email.com", 1, "url","Diego Cloud");
insert into cliente values (6,"97Tg","peter@email.com", 1, "url","Peter Parker");

insert into cliente_telefonos values (1,"3353");
insert into cliente_telefonos values (1,"2441");
insert into cliente_telefonos values (2,"2451");
insert into cliente_telefonos values (5,"1111");
insert into cliente_telefonos values (3,"0282");
insert into cliente_telefonos values (2,"0282");

/*insert into cupon values (id,criterio,desc,fechavencimiento,valordescuento)*/
insert into cupon values (1, "Registro", "Cupon por registrarse", '2022-04-12', 0.10);
insert into cupon values (2, "Cumpleaños", "Cupon por cumpleaños", '2022-11-24', 0.30);
insert into cupon values (3, "Premium", "Cupon por cliente premium", '2022-07-18', 0.35);
insert into cupon values (4, "Evento", "Cupon asignado por asistir a un evento", '2022-04-28', 0.5);
insert into cupon values (5, "Confiteria", "Cupon por comprar 30 combos", '2022-01-07', 0.70);

/*insert into cupon_cliente values (id, estado, cedulaClie, cuponid);*/
insert into cupon_cliente values (1, 1, 1, 1);
insert into cupon_cliente values (2, 1, 1, 2);
insert into cupon_cliente values (3, 0, 2, 1);
insert into cupon_cliente values (4, 0, 4, 1);
insert into cupon_cliente values (5, 1, 4, 3);
insert into cupon_cliente values (6, 1, 4, 2);
insert into cupon_cliente values (7, 0, 5, 4);
insert into cupon_cliente values (8, 1, 1, 5);

insert into genero values (1, "TERROR");
insert into genero values (2, "AVENTURA");
insert into genero values (3, "COMEDIA");
insert into genero values (4, "ACCION");
insert into genero values (5, "ANIMACION");
insert into genero values (6, "DRAMA");

/*insert into pelicula values (id,estado,imagen,nombre,reparto,sinopsis,trailerurl);*/
insert into pelicula values (1,'PRESTRENO', "url", "SpiderMan-NoWayHome","Tom, Andrew, Tobey", "Spiderman pelea con ayuda de los otros spidermans", "url");
insert into pelicula values (2,'PRESTRENO', "url", "StarWars: Rogue One","Andor, Darth Vader", "Pelean por los planos de destruccion de la death star", "url");
insert into pelicula values (3,'ESTRENO',   "url", "Klaus", "Klaus, Repartidor", "El repartidor salva la navidad de una aldea", "url");
insert into pelicula values (4,'ESTRENO',   "url", "The Father", "Abuelito, Doctora", "El abuelito vive con alzheimer", "url");
insert into pelicula values (5,'PROXIMO',   "url", "Free Guy", "NPC, Policia", "El npc se da cuenta que vive en un mundo virtual", "url");
insert into pelicula values (6,'PROXIMO',   "url", "Tenet", "Negrito, Blanquito", "Salvan el mundo con viajes al futuro y pasado", "url");
insert into pelicula values (7,'ESTRENO',   "url", "The Amazing SpiderMan", "Peter Parker, Gwen Stacy, May, Electro", "Spidy se enfrenta a electro, increible", "url");

/*insert into pelicula_generos values (peliculaid, generoid);*/
insert into pelicula_generos values (1, 2);
insert into pelicula_generos values (1, 4);
insert into pelicula_generos values (2, 2);
insert into pelicula_generos values (2, 4);
insert into pelicula_generos values (3, 5);
insert into pelicula_generos values (4, 6);
insert into pelicula_generos values (5, 5);
insert into pelicula_generos values (5, 4);
insert into pelicula_generos values (6, 4);
insert into pelicula_generos values (7, 2);
insert into pelicula_generos values (7, 4);

/*insert into horario values (id, fechafin, fechainicio, horafin, horainicio);*/
insert into horario values (1, '2022-08-13', '2022-08-03', '10:30:00', '08:15:00');
insert into horario values (2, '2022-05-13', '2022-05-03', '16:30:00', '13:15:00');
insert into horario values (3, '2022-05-13', '2022-05-03', '19:30:00', '16:30:00');
insert into horario values (4, '2022-05-14', '2022-05-03', '17:15:00', '14:30:00');
insert into horario values (5, '2022-05-15', '2022-05-03', '23:40:00', '21:10:00');

/*insert into distribucion_sillas values (id, col, fila, rutaEsquema, totalSillas);*/
insert into distribucion_sillas values (1, 10, 10, "ruta", 90);
insert into distribucion_sillas values (2, 9, 9, "ruta", 75);
insert into distribucion_sillas values (3, 9, 10, "ruta", 85);
insert into distribucion_sillas values (4, 4, 4, "ruta", 15);
insert into distribucion_sillas values (5, 5, 4, "ruta", 18);

/*insert into sala values (id, tipo, distribucion_id,teatroid);*/
insert into sala values (1, "T1Sala1E_EST", 'ESTANDAR', 1, 1);
insert into sala values (2, "T1Sala2_3DX", '_3DX', 1, 1);
insert into sala values (3, "T2Sala3_2DX", '_2DX', 1, 2);
insert into sala values (4, "T2Sala4_EST", 'ESTANDAR', 2, 2);
insert into sala values (5, "T3Sala5_EST", 'ESTANDAR', 2, 3);
insert into sala values (6, "T4Sala6_EST", 'ESTANDAR', 3, 4);
insert into sala values (7, "T4Sala7_3DX", '_3DX', 3, 4);
insert into sala values (8, "T4Sala8_2Dx", '_2DX', 3, 4);
insert into sala values (9, "T5Sala9_EST", 'ESTANDAR', 1, 5);

/*insert into funcion values (id, precio, horarioid, peliculaid, salaid);*/
insert into funcion values (1, 200, 1, 1, 1);
insert into funcion values (2, 100, 2, 2, 2);
insert into funcion values (3, 240, 2, 3, 3);
insert into funcion values (4, 210, 3, 4, 4);
insert into funcion values (5, 150, 3, 2, 5);
insert into funcion values (6, 500, 3, 2, 6);
insert into funcion values (7, 110, 4, 3, 7);
insert into funcion values (8, 330, 5, 4, 8);
insert into funcion values (9, 240, 4, 1, 9);

/*insert into compra values (id,fecha,metodopago,valortotal,clientecedula,cuponClienteid,funcionid );*/
insert into compra values (1, '2022-05-03', 'EFECTY',    42200,  1, 1, 2);
insert into compra values (2, '2022-05-03', 'EFECTY',    30120,  1, 2, 2);
insert into compra values (3, '2022-05-03', 'VISA',      40200,  3, null, 1);
insert into compra values (4, '2022-05-03', 'DAVIPLATA', 52462,  4, 5, 4);
insert into compra values (5, '2022-06-10', 'EFECTY',    6240,  2, 3, 5);
insert into compra values (6, '2022-06-12', 'EFECTY',    51240,  5, null, 9);
insert into compra values (7, '2022-06-24', 'VISA',      45400,  3, null, 1);
insert into compra values (8, '2022-07-13', 'EFECTY',    120240, 2, null, 3);
insert into compra values (9, '2022-07-17', 'NEQUI',     10264,  4, 6, 8);

/*insert into confiteria_compra values (id,precio,unidades,compraid,confiteria_id);*/
insert into confiteria_compra values (1, 30000, 2, 1, 1);
insert into confiteria_compra values (2, 12000, 1, 1, 2);
insert into confiteria_compra values (3, 30000, 5, 2, 3);
insert into confiteria_compra values (4, 40000, 1, 3, 4);
insert into confiteria_compra values (5, 52000, 4, 4, 5);
insert into confiteria_compra values (6, 6000,  1, 5, 2);
insert into confiteria_compra values (7, 6000,  1, 6, 2);
insert into confiteria_compra values (8, 45000, 3, 6, 1);
insert into confiteria_compra values (9, 45000, 3, 7, 1);
insert into confiteria_compra values (10, 80000, 2, 8, 4);
insert into confiteria_compra values (11, 40000, 4, 8, 3);
insert into confiteria_compra values (12, 10000, 1, 9, 3);

/*insert into boleta values (id, col, fila, precio, tipo, compraid);*/
insert into boleta values (1, "2", "A", 100, 'ESTANDAR', 1);
insert into boleta values (2, "2", "B", 100, 'ESTANDAR', 1);
insert into boleta values (3, "3", "A", 120, 'VIP', 2);
insert into boleta values (4, "4", "A", 200, 'ESTANDAR', 3);
insert into boleta values (5, "2", "F", 210, 'ESTANDAR', 4);
insert into boleta values (6, "6", "A", 252, 'VIP', 4);
insert into boleta values (7, "8", "E", 150, 'ESTANDAR', 5);
insert into boleta values (8, "1", "F", 240, 'ESTANDAR', 6);
insert into boleta values (9, "1", "G", 160, 'DISCAPACITADO', 7);
insert into boleta values (10, "7", "A", 240, 'VIP', 7);
insert into boleta values (11, "4", "B", 240, 'ESTANDAR', 8);
insert into boleta values (12, "4", "C", 264, 'DISCAPACITADO', 9);

