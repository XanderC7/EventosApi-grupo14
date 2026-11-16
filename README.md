# Sistema de Eventos y Registro de Participantes - Grupo 14 - Carrión Alexander

API REST desarrollada con Spring Boot para administrar categorias, eventos, participantes e inscripciones, aplicando arquitectura N capas, persistencia con Spring Data JPA, middleware con interceptores HTTP, manejo centralizado de excepciones y programacion orientada a aspectos (AOP).

## 1. Tecnologias Utilizadas
- Java 21
- Spring Boot 3.3.5
- Spring Web (Spring MVC / API REST)
- Spring Data JPA / Hibernate
- Spring AOP (AspectJ)
- MySQL 8 (Base de datos relacional)
- Maven

## 2. Arquitectura N Capas
El sistema se organiza bajo el paquete `ec.edu.espe.eventos_api` en 9 paquetes con responsabilidades separadas:
- `controller`: Recibe las peticiones HTTP y retorna respuestas JSON sin contener logica de negocio.
- `service`: Contiene la logica y las reglas de negocio, recibiendo dependencias por constructor (IoC/DI).
- `repository`: Interfaces que extienden de `JpaRepository` con operaciones CRUD y consultas personalizadas.
- `entity`: Clases persistentes mapeadas a las 4 tablas relacionales de MySQL.
- `dto`: Objetos de transferencia de datos para entrada y salida JSON.
- `exception`: Excepciones personalizadas de negocio y manejador global con `@RestControllerAdvice`.
- `interceptor`: Middleware HTTP (`LoggingInterceptor`) que registra metodo, URI, IP y tiempo de respuesta.
- `configuration`: Clase `WebConfig` que registra el interceptor en el flujo de Spring MVC.
- `aspect`: Aspecto transversal (`LoggingAspect`) con `@Before` y `@After` sobre la capa de servicio.

## 3. Modelo de Base de Datos Relacional (`eventos_db`)
Cuenta con 4 entidades relacionadas con claves primarias autogeneradas, claves foraneas y restricciones de integridad:
1. `Categoria` (`categorias`): `id` (PK), `nombre` (unico, obligatorio), `descripcion`.
2. `Evento` (`eventos`): `id` (PK), `nombre`, `descripcion`, `fecha`, `lugar`, `capacidad_maxima`, `cupos_disponibles`, `categoria_id` (FK hacia `categorias`).
3. `Participante` (`participantes`): `id` (PK), `nombre`, `apellido`, `email` (unico, obligatorio), `telefono`.
4. `Inscripcion` (`inscripciones`): `id` (PK), `fecha_inscripcion`, `estado`, `evento_id` (FK hacia `eventos`), `participante_id` (FK hacia `participantes`).

## 4. Reglas de Negocio y Excepciones Personalizadas
### Reglas de negocio implementadas:
1. No se permite registrar dos categorias con el mismo nombre.
2. La capacidad maxima de un evento debe ser estrictamente mayor a cero.
3. Al actualizar la capacidad de un evento, no puede ser menor al numero de participantes ya inscritos.
4. No se permite registrar dos participantes con el mismo correo electronico.
5. No se permite que un mismo participante se inscriba dos veces en el mismo evento ni inscribir cuando los cupos disponibles lleguen a cero.

### Excepciones personalizadas:
- `RecursoNoEncontradoException`: Retorna codigo HTTP `404 Not Found`.
- `CupoMaximoAlcanzadoException`: Retorna codigo HTTP `409 Conflict`.
- `ReglaNegocioException`: Retorna codigo HTTP `400 Bad Request`.

## 5. Analisis de Programacion Bloqueante y No Bloqueante
- **Que operacion se considera bloqueante:** Las peticiones atendidas por Spring MVC y las operaciones de lectura/escritura hacia MySQL mediante Spring Data JPA (JDBC) son bloqueantes, ya que el hilo asignado a la peticion HTTP permanece detenido esperando la respuesta de la base de datos antes de continuar con la siguiente instruccion.
- **Por que puede afectar el rendimiento:** Cuando se realizan tareas adicionales de entrada/salida (como el envio de notificaciones de confirmacion al inscribir a un participante) dentro del mismo hilo principal, el tiempo de espera se acumula, aumentando la latencia de respuesta al cliente y reteniendo hilos del servidor Tomcat ante multiples solicitudes concurrentes.
- **Alternativa no bloqueante o asincrona empleada:** En `InscripcionService` se implemento el metodo `enviarNotificacionAsincrona` utilizando `CompletableFuture.runAsync()`, el cual delega el envio de la notificacion a un hilo secundario sin detener el flujo principal de la inscripcion.
- **Ventajas y limitaciones:** Como ventaja, libera inmediatamente el hilo principal y reduce el tiempo de respuesta de la API al usuario; como limitacion, la persistencia hacia MySQL con JDBC sigue siendo sincrona y cualquier fallo dentro de la tarea asincrona ocurre fuera de la respuesta HTTP ya enviada al cliente.

## 6. Listado de Endpoints
### Categorias (`/api/categorias`)
- `GET /api/categorias` - Listar todas las categorias
- `GET /api/categorias/{id}` - Obtener categoria por ID
- `POST /api/categorias` - Crear categoria
- `DELETE /api/categorias/{id}` - Eliminar categoria

### Eventos (`/api/eventos`)
- `GET /api/eventos` - Listar todos los eventos
- `GET /api/eventos/{id}` - Obtener evento por ID
- `GET /api/eventos/categoria/{categoriaId}` - Consulta personalizada por categoria
- `GET /api/eventos/disponibles` - Consulta personalizada de eventos con cupos mayores a cero
- `POST /api/eventos` - Crear evento y definir capacidad maxima
- `PUT /api/eventos/{id}` - Actualizar evento y su capacidad maxima
- `DELETE /api/eventos/{id}` - Eliminar evento

### Participantes (`/api/participantes`)
- `GET /api/participantes` - Listar todos los participantes
- `GET /api/participantes/{id}` - Obtener participante por ID
- `POST /api/participantes` - Registrar participante
- `PUT /api/participantes/{id}` - Actualizar participante
- `DELETE /api/participantes/{id}` - Eliminar participante

### Inscripciones (`/api/inscripciones`)
- `POST /api/inscripciones` - Inscribir participante en un evento
- `GET /api/inscripciones/evento/{eventoId}` - Consultar inscripciones por evento
- `GET /api/inscripciones/evento/{eventoId}/participantes` - Consultar participantes inscritos por evento
- `PUT /api/inscripciones/{id}/cancelar` - Cancelar inscripcion y liberar cupo