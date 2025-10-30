# poo2025-junin-lodeiro-martino
POO 2025 SEDE JUNIN LODEIRO MARTINO

# Arquitectura en capas (qué hay y dónde vive)

* **controller/**: expone REST (inscripción, torneos, competencias, roles…). Capa fina, sin reglas de negocio.
* **service/**: orquesta casos de uso. Aquí viven **reglas**, **transacciones**, **políticas** y **eventos**.
* **repository/**: acceso a datos con Spring Data JPA (consultas de escritura y lectura —CQRS— cuando conviene).
* **entity/**: modelo persistible (JPA) + **auditoría** y **locking optimista**.
* **dto/** + **mapper/**: contratos de entrada/salida; nunca exponemos entidades.
* **security/**: JWT + roles dinámicos (RBAC); soft delete para usuarios.
* **service/policy/**: **Strategy** de precio (decorable).
* **service/spec/** y **service/chain/**: **Specification** y **Chain of Responsibility** para validar inscripción.
* **service/factory/**: **Factory** de Inscripción (objeto nace válido).
* **service/event/**: **Domain Events** para desacoplar reacciones.
* **port/** + **util/**: puertos hexagonales (Clock, Notification, etc.) + adaptadores.

# Núcleo de dominio (reglas críticas)

## Torneo con **State pattern** (sin boolean “publicado”)

* Estados: **Borrador → Publicado → Finalizado** (y transiciones válidas).
* El Torneo delega en su `TorneoState` si **se puede editar** y si **se puede inscribir** (además de chequear fecha).
* Beneficio: agregar un estado nuevo (“Pausado”) es añadir una clase y sus reglas, sin `if` por todas partes.
* Escala: reglas complejas por estado (p. ej., edición limitada en Publicado) se encapsulan y testean aisladas.

## Competencia con **cupo** y **optimistic locking**

* Campos: `cupo`, `inscriptosActuales`, `@Version`.
* En inscripción: se incrementa `inscriptosActuales` y JPA verifica `version`. Si dos transacciones pisan el último cupo, solo una gana; la otra falla con 409.
* Beneficio: consistencia sin **locks pesados**. Escala con alta concurrencia.

## Participante y UserAccount (soft delete)

* **UserAccount** no tiene `enabled`; tiene `deletedAt`. Activo = `deletedAt == null`.
* **Filtro** (opcional) para ignorar borrados lógicamente.
* Beneficio: histórico preservado y seguridad (no “revivís” usuarios por error).

# Inscripción: pipeline robusto y extensible

## Strategy de **precio** (OCP listo)

* `PrecioInscripcionPolicy`: interfaz.
* Implementación actual: **Primera al 100%, resto 50%** dentro del mismo torneo.
* Extensión: impuestos, cupones o promos con **Decorator**. Cambiás la policy inyectada; no tocás servicios.
* Beneficio: reglas tarifarias cambian a ritmo de negocio sin tocar el caso de uso.

## **Specification** (reglas componibles)

* Especificaciones atómicas: `VentanaAbierta`, `TorneoPermiteInscribir`, `HayCupo`, `UnicidadInscripcion`.
* Se combinan con `and/or/not`.
* Beneficio: agregás o reordenás reglas sin ensuciar servicios; **test unitario** de cada regla.

## **Chain of Responsibility** (orden y mensajes claros)

* Chequeos secuenciales: Estado → Ventana → Unicidad → Cupo.
* Corta en el primer error con mensaje específico.
* Beneficio: controlas el **orden** y el **feedback** al usuario; fácil de añadir pasos (KYC, consentimientos, etc.).

## **Factory** de Inscripción

* Centraliza la creación: setea `participante`, `competencia`, `precio`, `fechaInscripcion` usando `ClockPort`.
* Beneficio: el objeto **siempre nace válido**; no repetís construcción en mil lados.

## **Domain Events**

* Tras guardar la inscripción y ocupar cupo, emitimos `InscripcionCreadaEvent`.
* Listeners (p. ej., notificación por email, métricas, auditoría externa) reaccionan aparte.
* Beneficio: **desacople fuerte** del caso de uso. Sumás efectos colaterales sin tocar el flujo.

# Value Objects (tipos fuertes que evitan bugs)

* `Money` (monto + moneda) en `precioBase` y `precioPagado`.
* `Documento` (tipo + número) y `Email` (normalizado).
* Beneficio: validaciones y semántica **dentro** del tipo; menos “strings mágicos” y errores de formato.

# Ports & Adapters (hexagonal)

* `ClockPort`: tiempo controlado → **testable** (simulás fechas sin hacks).
* `NotificationPort`: hoy no-op o log; mañana SMTP/SendGrid/WhatsApp sin cambiar dominio.
* Futuro: `PaymentPort` (MercadoPago) y `StoragePort` (comprobantes).
* Beneficio: proveedores **intercambiables**; dominio limpio.

# Seguridad y autorización

* **Roles dinámicos** (RBAC): `UserAccount ↔ Role` N:N.
* Control por `@PreAuthorize("hasRole('ADMIN')")` o combinaciones.
* Registro público crea `ROLE_PARTICIPANTE`; elevar roles solo vía backoffice.
* Beneficio: creás **nuevos roles** (p. ej. `ROLE_GESTOR_COMPETENCIAS`) **sin tocar código**; solo datos.

# Persistencia y consistencia

* **JPA** con entidades limpias, índices únicos (inscripción única por participante+competencia).
* **Auditoría** (`created_at`, `updated_at`) automática.
* **DER** con VOs embebidos (monto + currency, email normalizado).
* Beneficio: base sólida para reportes y trazabilidad.

# Lecturas que escalan: **CQRS light**

* Proyecciones de lectura (`InscriptoResumenDto`, `totalRecaudado`) con queries específicas.
* Beneficio: endpoints de consulta **rápidos** sin contaminar servicios de escritura; migrás a SQL nativo o vistas si el volumen crece.

# Paquetes organizados para crecer

* **controller/** y **service/** centralizan interacción y lógica. **repository/** y **entity/** encapsulan persistencia.
* **policy/spec/chain/factory/event** dan **puntos de extensión** claros.
* Beneficio: onboarding más fácil; los cambios van al lugar correcto, no a cualquier lado.

# Flujo de inscripción (resumen operativo)

1. **Auth**: JWT te da `userAccountId`.
2. **Load**: traés `Participante`, `Competencia` (con `@Version`) y `Torneo`.
3. **Reglas**: `Specification` y `Chain` validan estado/ventana/unicidad/cupo.
4. **Precio**: `PrecioInscripcionPolicy` computa `Money`.
5. **Crear**: `InscripcionFactory` instancia; guardás inscripción.
6. **Cupo**: `competencia.incrementarInscriptos()` + `save` → si colisiona, 409.
7. **Eventos**: `InscripcionCreadaEvent` → listeners (notificación, métricas).

# ¿Por qué esto **escala**?

* **Añadís funcionalidades** sin “romper huevos”: nuevas policies de precio, nuevos estados, nuevos checks, nuevos eventos, nuevos roles.
* **Concurrencia real**: optimistic locking evita overselling de cupos bajo carga.
* **Lecturas y reportes** no frenan escrituras (CQRS light).
* **Testabilidad**: Specs, Chain, Factory, Policy y Ports permiten test unitarios rápidos y precisos.
* **Equipo grande**: responsabilidades claras por paquete; menos pisarse en PRs.
* **Evolución**: si mañana hay pagos, cupones, sedes, multi-idioma o multi-moneda, el diseño ya tiene **puntos de anclaje** (ports, VOs, state, strategy).

# Beneficios prácticos (lista de impacto)

* **OCP real**: cambiás precio, agregás promos, estados o validaciones **sin** editar servicios.
* **Menos bugs** por tipado semántico (VOs) y pipeline de reglas explícito.
* **Menos deuda**: nada de `if/else` gigantes; cada regla en su clase.
* **Observabilidad**: eventos te permiten instrumentar (logs, métricas) sin ensuciar el caso de uso.
* **Seguridad controlada por datos**: roles nuevos requieren solo inserts, no deploy.
* **Mantenibilidad**: árboles de paquetes consistentes; DTOs/MapStruct reducen boilerplate.

# Qué queda opcional para el “modo turbo”

* **Feature flags** para alternar policies (precio con impuestos vs. neto).
* **Cache** (`@Cacheable`) en consultas de lectura pesada (p. ej., recaudación).
* **Outbox pattern** si escalás eventos a Kafka/Rabbit y querés garantías “exactly-once”.
* **Saga/Orquestación** si en el futuro inscripción implica pago + emisión de factura + cupón.

---

