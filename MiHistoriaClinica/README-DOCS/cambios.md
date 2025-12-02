# ESTADO ACTUAL - Mi Historia Clínica 

### **Servicios Activos**

| Servicio | Estado | URL | Notas |
|----------|--------|-----|-------|
| **MySQL** | ✅ Corriendo | `localhost:3306` | Docker container activo |
| **Backend (Spring Boot)** | ✅ Corriendo | `http://localhost:8080` | Respondiendo correctamente |
| **Frontend (Angular)** | ✅ Corriendo | `http://localhost:4200` | Compilado sin errores |

---

## CORRECCIONES 

#### 1. Actualización de Lombok
- **Problema:** Incompatibilidad entre Lombok 1.18.28 y Java 21
- **Solución:** Actualizado a Lombok 1.18.30

#### 2. Configuración de SendGrid
  
- implementacion para enviar mails de confirmacion de cuenta listo, 
desde back y front, la cuenta queda desahbilitada hasta que se confirme mail 

---

## AGENDA - IMPLEMENTACIÓN 

### Funcionalidades para Médicos

#### Gestión de Disponibilidad
- **Creación de agenda:** Los médicos pueden definir su disponibilidad seleccionando:
  - Días de la semana (Lunes a Domingo)
  - Horario de inicio y fin
  - Duración de cada turno (15, 30, 45, 60 minutos)
  - Centro médico donde atenderán
- **Generación automática:** El sistema crea turnos para los próximos 30 días desde la fecha de creación
- **Pausa automática:** Se aplica automáticamente una pausa de 13:00-14:00 (horario de almuerzo)
- **Espaciado entre turnos:** Incluye 5 minutos de margen entre cada turno

#### Visualización del Calendario
- **Vista mensual:** Calendario con resumen diario (turnos disponibles vs. reservados)
- **Vista detallada por día:** Al hacer clic en un día, se abre un modal con todos los turnos:
  - Lista completa de turnos del día seleccionado
  - Información: hora, centro médico, estado, paciente (si está reservado)
  - Filtros por estado: todos, solo disponibles, solo reservados

#### Gestión Individual de Turnos
Cada turno tiene acciones específicas según su estado:
- **Turnos disponibles:**
  - **Reservar:** Asignar manualmente a un paciente asociado
  - **Bloquear:** Marcar como no disponible sin asignar a nadie
- **Turnos reservados:**
  - **Cancelar reserva:** Liberar el turno y devolverlo a disponible
- **Turnos bloqueados:**
  - **Desbloquear:** Volver a hacer el turno disponible

#### Actualización Automática
- El calendario se actualiza automáticamente cada 15 segundos
- Refleja cambios realizados por pacientes (reservas/cancelaciones) en tiempo real



### 🏥 Funcionalidades para Pacientes

#### Búsqueda de Turnos
- **Búsqueda por especialidad y fecha:**
  - Selección de especialidad médica
  - Selección de fecha deseada
  - Vista de todos los médicos disponibles con turnos en esa fecha
- **Información mostrada:**
  - Nombre completo del médico
  - Especialidad
  - Turnos disponibles agrupados por centro médico

#### Reserva de Turnos
- Reserva con un solo clic desde la lista de turnos disponibles
- Confirmación inmediata del turno reservado
- El turno pasa a estado "reservado" y se asigna al paciente

#### Mis Turnos
- Vista de todos los turnos reservados del paciente
- Información completa: fecha, hora, médico, especialidad, ubicación
- Opción de cancelar turnos reservados
- Confirmación antes de cancelar con modal personalizado
