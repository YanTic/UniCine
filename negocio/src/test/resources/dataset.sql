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

insert into administrador values (1,"35ad", "harry@email.com", "Harry Styles", 1);
insert into administrador values (2,"4add", "frank@email.com", "Frank Ocean", 3);
insert into administrador values (3,"3jka", "rex@email.com", "Rex Orange", 5);
insert into administrador values (4,"35ba", "pualo@email.com", "Paulo Snezze", 5);
insert into administrador values (5,"l5yk", "drake@email.com", "Drake yeye", 5);

insert into evento values (1, '2022-08-17', '10:30:00', '09:30:00', "url", "Evento Fin de Año", 1);
insert into evento values (2, '2022-06-10', '04:00:00', '02:15:00', "url", "Evento Familia", 1);
insert into evento values (3, '2022-11-26', '05:30:00', '01:20:00', "url", "Evento Spider-Man", 2);
insert into evento values (4, '2022-12-01', '07:30:00', '04:30:00', "url", "Evento Arroz y pollo", 3);
insert into evento values (5, '2022-10-04', '11:15:00', '01:00:00', "url", "Evento Arroz y pollo", 5);

insert into confiteria values (1, "url", 15000, "Crispetas Mediana");
insert into confiteria values (2, "url", 6000, "Agua");
insert into confiteria values (3, "url", 10000, "Gaseosa Mediana");
insert into confiteria values (4, "url", 40000, "Combo parejita");
insert into confiteria values (5, "url", 13000, "Nachos");

insert into cliente values (1,"1234","julian@email.com","url","Julian Acosta");
insert into cliente values (2,"3jn1","juan@email.com","url","Juan Bunny");
insert into cliente values (3,"34a4","pepe@email.com","url","Pepe Lipa");
insert into cliente values (4,"a378","mailo@email.com","url","Mailo Styles");
insert into cliente values (5,"b35a","diego@email.com","url","Diego Cloud");

insert into cliente_telefonos values (1,"3353");
insert into cliente_telefonos values (1,"2441");
insert into cliente_telefonos values (2,"2451");
insert into cliente_telefonos values (5,"1111");
insert into cliente_telefonos values (3,"0282");

/*insert into cupon values (id,criterio,desc,fechavencimiento,valordescuento,cedulacliente)*/
insert into cupon values (1, "registro", "Cupon por registrarse", '2022-04-12', 10, 1);
insert into cupon values (2, "registro", "Cupon por registrarse", '2022-05-13', 10, 2);
insert into cupon values (3, "cumpleaños", "Cupon por cumpleaños", '2022-11-24', 30, 1);
insert into cupon values (4, "cumpleaños", "Cupon por cumpleaños", '2022-04-01', 30, 4);
insert into cupon values (5, "Premium", "Cupon por cliente premium", '2022-07-18', 35, 5);

/*insert into compra values (id,fecha,metodopago,valortotal,clientecedula,cuponid );*/
insert into compra values (1, '2022-05-03', 'EFECTY',    55200,  1, 1);
insert into compra values (2, '2022-05-03', 'EFECTY',    40200,  1, 3);
insert into compra values (3, '2022-05-03', 'VISA',      17000,  3, null);
insert into compra values (4, '2022-05-03', 'DAVIPLATA', 15500,  4, 4);
insert into compra values (5, '2022-06-10', 'EFECTY',    25000,  2, 2);
insert into compra values (6, '2022-06-12', 'EFECTY',    50300,  5, 5);
insert into compra values (7, '2022-06-24', 'VISA',      35080,  3, null);
insert into compra values (8, '2022-07-13', 'EFECTY',    130170, 2, null);
insert into compra values (9, '2022-07-17', 'NEQUI',     35012,  4, null);

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

insert into genero values (1, "Terror");
insert into genero values (2, "Aventura");
insert into genero values (3, "Comedia");
insert into genero values (4, "Accion");
insert into genero values (5, "Animacion");
insert into genero values (6, "Drama");

/*insert into pelicula values (id,estado,imagen,nombre,reparto,sinopsis,trailerurl);*/
insert into pelicula values (1,'PRESTRENO', "url", "SpiderMan-NoWayHome","Tom, Andrew, Tobey", "Spiderman pelea con ayuda de los otros spidermans", "url");
insert into pelicula values (2,'PRESTRENO', "url", "StarWars: Rogue One","Andor, Darth Vader", "Pelean por los planos de destruccion de la death star", "url");
insert into pelicula values (3,'ESTRENO',   "url", "Klaus", "Klaus, Repartidor", "El repartidor salva la navidad de una aldea", "url");
insert into pelicula values (4,'ESTRENO',   "url", "The Father", "Abuelito, Doctora", "El abuelito vive con alzheimer", "url");
insert into pelicula values (5,'PROXIMO',   "url", "Free Guy", "NPC, Policia", "El npc se da cuenta que vive en un mundo virtual", "url");
insert into pelicula values (6,'PROXIMO',   "url", "Tenet", "Negrito, Blanquito", "Salvan el mundo con viajes al futuro y pasado", "url");

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

/*insert into horario values (id, fecha, horafin, horainicio);*/
insert into horario values (1, '2022-05-03', '10:30:00', '08:15:00');
insert into horario values (2, '2022-05-03', '16:30:00', '13:15:00');
insert into horario values (3, '2022-05-03', '19:30:00', '16:30:00');
insert into horario values (4, '2022-05-04', '17:15:00', '14:30:00');
insert into horario values (5, '2022-05-05', '23:40:00', '21:10:00');

/*insert into sala values (id, tipo, teatroid);*/
insert into sala values (1, 'ESTANDAR', 1);
insert into sala values (2, '_3DX', 1);
insert into sala values (3, '_2DX', 2);
insert into sala values (4, 'ESTANDAR', 2);
insert into sala values (5, 'ESTANDAR', 3);
insert into sala values (6, 'ESTANDAR', 4);
insert into sala values (7, '_3DX', 4);
insert into sala values (8, '_2DX', 4);
insert into sala values (9, 'ESTANDAR', 5);

/*insert into funcion values (id, precio, horarioid, peliculaid, salaid, teatroid);*/
insert into funcion values (1, 200, 1, 1, 1, 1);
insert into funcion values (2, 100, 1, 2, 2, 1);
insert into funcion values (3, 240, 2, 3, 3, 2);
insert into funcion values (4, 210, 3, 4, 4, 2);
insert into funcion values (5, 150, 3, 2, 5, 3);
insert into funcion values (6, 500, 3, 2, 6, 4);
insert into funcion values (7, 110, 4, 3, 7, 4);
insert into funcion values (8, 330, 5, 4, 8, 4);
insert into funcion values (9, 240, 4, 1, 9, 5);

insert into tipo_silla values (1,"ESTANDAR",11000);
insert into tipo_silla values (2,"VIP",15000);
insert into tipo_silla values (3,"DISCAPACITADO",7000);

/*insert into silla values (id,columna,estado,fila,salaid,tipoid);*/
insert into silla values (1,"2",'DISPONIBLE',   "H",1,1);
insert into silla values (2,"6",'DISPONIBLE',   "A",1,2);
insert into silla values (3,"3",'DISPONIBLE',   "B",2,3);
insert into silla values (4,"1",'NO_DISPONIBLE',"C",3,1);
insert into silla values (5,"1",'NO_DISPONIBLE',"J",3,2);
insert into silla values (6,"5",'NO_DISPONIBLE',"F",6,3);
insert into silla values (7,"4",'DISPONIBLE',   "F",5,1);
insert into silla values (8,"4",'OCUPADA',      "A",4,2);
insert into silla values (9,"2",'OCUPADA',      "D",3,3);

/*insert into boleta values (id, precio, compraid, funcionid, silla_id);*/
insert into boleta values (1, 3200, 1, 2, 1);
insert into boleta values (2, 1200, 1, 2, 2);
insert into boleta values (3, 2200, 2, 1, 3);
insert into boleta values (4, 4150, 3, 1, 4);
insert into boleta values (5, 3050, 4, 3, 4);
insert into boleta values (6, 1000, 4, 4, 5);
insert into boleta values (7, 1000, 5, 4, 7);
insert into boleta values (8, 2200, 6, 5, 6);
insert into boleta values (9, 5200, 7, 7, 6);
insert into boleta values (10, 3200, 7, 6, 9);
insert into boleta values (11, 5600, 8, 6, 8);
insert into boleta values (12, 1100, 9, 6, 8);

