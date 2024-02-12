# minibanco
MiniBanco: creación de un API REST

# Aplicación práctica Gerardo Rodriguez
-------------------------------------------------------------------
### Tecnologías Y herramientas usadas:
- Java 17
- Spring Boot 3.2.2
- Base de Datos MySql 8.0.36
- Lombok
- Junit 5
- Postman
- Spring Tool Suite 4
- Server: Docker Desktop 4.22.1
- Docker Engine 25.0.2

### Arquitectura
Se implementó una arquitectura N-tier o de capas, en la cual existen controladores que hacen uso de varios servicios 
los cuales contienen la lógica de negocio y estos a su vez hacen uso de las interfaces repository.
Controlador--> Servicio--> Repositorio 
-------------------------------------------------------------------

## Instalación/Ejecución
- Descargar y ejecutar el proyecto, puerto por defecto 8080.
- La base de datos está en memoria (h2), por lo tanto, no es necesario configurar una conexión adicional.
- Al momento de correr el proyecto se crea la Base de Datos.
- En caso que se necesite acceder a la BD se puede hacer por la url (http://localhost:8080/h2-console/) con los siguientes 
  parámetros:
    * Driver Class:  org.h2.Driver
    * JDBC URL: jdbc:h2:mem:BaseDatos
    * User Name: rootit
    * Password:  rootit
    
-Dentro de la carpeta resources del proyecto está el script de la base de datos con nombre "BaseDatos.sql"

Opcional (correr como contenedor)
=================================

- Crear imagen de docker con la aplicación: 
___________________________________________

- Ubicarse en la raíz del del proyecto y en la terminal ejecutar el comando (donde esta el archivo dockerfile) para crear la
imagen respectiva:
  
  docker build -t api-minibanco-rodriguezg:minibanco1 .

- Luego Ejecutar el siguiente comando para crear el contenedor a partir de la imagen creada anteriormente:

 docker run --name container-minibanco -p 8080:8080 api-minibanco-rodriguezg:minibanco1 .
 
 Para reiniciar, parar o iniciar el contenedor y tener base de datos sin data  ejecutar: 
 docker restart container-minibanco 
 docker stop container-minibanco
 docker start container-minibanco
 
 Listo, la app está corriendo en el puerto 8080. 

---------------------------------------------------------------------------------------------------------

## Funcionamiento

Luego de correr el proyecto se podrán crear los clientes, cuentas y movimientos. Adicionalmente se podrá generar reporte de movimientos
por cliente en un rango de fechas desde y hasta.

1) Cliente: la información se guarda en las tablas persona, existiendo una relación de 1 a 1.
-No puede haber 2 clientes con el mismo id de identificación en la tabla de persona.
-La eliminación es lógica por el campo "Estado"

2) Cuenta: se podrán crear cuentas de ahorro y corriente para un cliente previamente registrado.- Hay una relación de muchos  a 1 con cliente.
- El api no permite crear cuentas a clientes inactivos.

3) Movimientos: se podrán crear movimientos de débito, crédito y reversos de operaciones.- Hay una relación de muchos  a 1 con cuenta.
El api realiza las siguientes validaciones previas a la ejecución del tipo de movimiento:
-Error de Validación en Movimiento: no existe la cuenta
-Error de Validación en Movimiento: cuenta inactiva 
-Monto Credito Incorrecto
-Monto Debito Incorrecto
-Saldo no Disponible (0)
-Saldo Insuficiente
-Límite Diario Excedido
-Monto Debito Excede el monto restante permitido por día.

Nota: El api no elimina operaciones, hace reverso de las operaciones. 

4) Generar Reporte de Movimientos por Cliente
Solicita fecha desde, fecha hasta y id de cliente para generar el reporte de movimientos. 


Adjunto algunos json de prueba para carga de datos desde postman. 

- Crear Cliente (@PostMapping)
  * http://localhost:8080/api/clientes/crear
  
	{
	  "idPersona": "18192460",
	  "nombre": "Jennyree Rodriguez",
	  "contrasena": "456789",
	  "estado": "ACTIVO",
	  "direccion": "Calle 21 entre 22 y 23", 
	  "genero": "FEMENINO",
	  "edad":36,
	  "telefono":"0412-3029522"
	}

- Modifciar Cliente (@PatchMapping)
	http://localhost:8080/api/clientes/1

	{
	  "idPersona": "18192460",
	  "nombre": "Jennyree Rodriguez",
	  "contrasena": "456789",
	  "estado": "ACTIVO",
	  "direccion": "Calle 21 entre 22 y 23", 
	  "genero": "FEMENINO",
	  "edad":36,
	  "telefono":"0412-45467812"
	}

- Eliminar Cliente (@DeleteMapping)
http://localhost:8080/api/clientes/2
-------------------------------------------------------------------

- Crear Cuenta (@PostMapping)
  * http://localhost:8080/api/cuentas/crear
  
	{
	    "nroCuenta": "18192460-1",
	    "idCliente": 1,
	    "tipo": "AHORRO",
	    "saldoInicial": 50,
	    "estado": "ACTIVO"
	}

- Modifciar Cliente (@PatchMapping)
	http://localhost:8080/api/cuentas/8192460-1

	{
	    "nroCuenta": "18192460-1",
	    "idCliente": 1,
	    "tipo": "AHORRO",
	    "saldoInicial": 80,
	    "estado": "ACTIVO"
	}

- Eliminar Cliente (@DeleteMapping)
http://localhost:8080/api/cuentas/8192460-1

-------------------------------------------------------------------

- Crear Movimientos (@PostMapping)
  * http://localhost:8080/api/movimientos/crear
  
	{
	    "idCliente": 1,
	    "nroCuenta": "18192460-1",
	    "fechaMovimiento": "2024-02-09",
	    "tipo_movimiento": "CREDITO",
	    "monto": 350
	}

- Eliminar Cliente (@PostMapping)
http://localhost:8080/api/movimientos/eliminar/1

Generar Reporte de Movimientos por Cliente
-------------------------------------------------------------------

http://localhost:8080/api/movimientos/reportes?fechaDesde=2024-02-06&fechaHasta=2024-02-28&idCliente=1

Adjunto está el archivo ***LOCAHOST_8080_REST_API_MINIBANCO.postman_collection.json**  que es una colección que  se puede importar en PostMan para probar el Api. 
