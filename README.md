# Proyecto programación 1ºDAM

## Buscador de Credenciales y Administración de Apps (CRUD)
## Desarrollado en Java, con Base de Datos MariaDB y consultas SQL
## GitHub repo -> https://github.com/n3oari/FiltracionesTracker-AdminAPP-TrabajoClase

---



### Tablas
- Credenciales(fk_id_filtracion)
- Filtraciones
- Administradores
- Historial (fk_id_administrador)

---


### Hay 3 GUI:

GUI
│
├── GUI User
│
└── GUI Admin

---

### Como usuario la app permite:

	- Buscar si sus credenciales  han sido filtradas en algún ataque de bases de datos conocidas. Solo tienes que 	ingresar tu información y el sistema verificará si ha sido comprometida. 
	- Consultar la suma total de las credenciales actuales en la base de datos
	- Loggearte como administrador

---

### Como administrador la app permite:

	- Añadir filtración
	- Realizar consultas SQL (solo a usuarios)
	- Actualizar tabla
	- Borrar filtración
	- Limpiar tabla
	- Modificar columna
	- Consultar historial de modificaciones

	-Todas las acciones se ejecutan tanto en la GUI como en la base de datos. (excepto actualizar tabla)



