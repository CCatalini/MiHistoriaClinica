# Speech Presentación Final - MiHistoriaClinica

## Equipo
- **Persona A** (maneja la computadora)
- **Persona B** (presenta principalmente)

> **Nota:** Ambas personas hablan durante toda la presentación, pero A se encarga de la navegación/demo.

---

# PARTE 1: PRESENTACIÓN NO TÉCNICA (~10-12 min)

## 1.1 Introducción - ¿Qué es MiHistoriaClinica?

**[B habla]**

> "Buenos días/tardes. Les presentamos **MiHistoriaClinica**, una aplicación web que permite a pacientes y médicos gestionar toda la información de salud de forma digital, centralizada y accesible desde cualquier lugar.

> El problema que resolvemos es el siguiente: actualmente, cuando vas a un médico nuevo, tenés que llevar tu historial en papel, recordar todos los medicamentos que tomás, los estudios que te hiciste... y muchas veces esa información está incompleta o dispersa entre distintos profesionales.

> **MiHistoriaClinica** centraliza toda esa información: tu historia clínica, medicamentos recetados, estudios pendientes y realizados, turnos médicos, y permite que tanto el paciente como sus médicos tengan acceso a ella de forma segura."

---

## 1.2 Comparación con Apps Similares

**[A habla]**

> "Existen varias apps de salud en el mercado. Vamos a compararnos con las principales:"

| App | Qué ofrece | En qué nos diferenciamos |
|-----|------------|--------------------------|
| **Mi Argentina (SISA)** | Historia clínica unificada nacional, vacunas, recetas digitales | Es gubernamental y depende de la integración de cada efector. Nosotros somos más ágiles y no dependemos de convenios |
| **OSDE/Swiss Medical apps** | Turnos, cartilla médica, autorizaciones | Son cerradas para afiliados de cada obra social. Nosotros somos agnósticos: cualquier paciente y médico puede usarla |
| **HealthTap / Doctolib** | Turnos online, telemedicina | Se enfocan en la reserva de turnos. Nosotros integramos toda la historia clínica y el seguimiento de tratamientos |
| **Apps de hospitales individuales** | Turnos y resultados de ese hospital | La información queda fragmentada. Nosotros la unificamos independientemente del centro médico |

**[B continúa]**

> "Nuestra propuesta de valor es ser un **hub centralizado** donde el paciente tiene el control de su información, puede compartirla con cualquier médico mediante un código temporal, y recibe notificaciones y recordatorios de sus tratamientos y turnos."

---

## 1.3 Demo con Casos de Uso

> **IMPORTANTE:** Antes de la demo, tener preparados:
> - 2 navegadores (Chrome normal + Chrome incógnito O Chrome + Firefox)
> - Usuarios pre-cargados: 1 paciente, 1 médico
> - Algunos turnos ya reservados para mostrar el flujo completo

---

### HISTORIA 1: María se registra y reserva un turno con el cardiólogo

**[B narra mientras A ejecuta]**

> "Vamos a conocer a **María**, una paciente de 35 años que necesita hacer una consulta con un cardiólogo. María escuchó sobre nuestra aplicación y decide registrarse."

#### Flujo para A (pantalla):
1. Abrir `http://localhost:4200`
2. Click en **"Registrarse como Paciente"**
3. Completar formulario con datos de María
4. Mostrar el mensaje de verificación de email

**[B explica]**

> "Como ven, al registrarse María recibe un **email de verificación**. Esto es importante para validar que el email es real y poder enviarle notificaciones después."

#### Flujo para A:
5. (Si tienen acceso al email) Mostrar el email de verificación
6. Verificar cuenta
7. Hacer login como María

**[B continúa]**

> "Ahora María está en su **Portal de Paciente**. Desde acá puede ver su historia clínica, medicamentos, estudios, médicos y turnos. Como María es nueva, vamos a reservar su primer turno."

#### Flujo para A:
8. Click en **"Reservar Turno"**
9. Seleccionar especialidad: **"Cardiología"**
10. Seleccionar fecha (elegir una con turnos disponibles)
11. Mostrar la lista de médicos disponibles con sus turnos
12. Filtrar por centro médico (si aplica)
13. Reservar un turno

**[B explica]**

> "María puede filtrar por **fecha**, **especialidad**, **médico** y **centro médico**. Esto le permite encontrar el turno que mejor se adapte a su disponibilidad y ubicación."

#### Flujo para A:
14. Mostrar confirmación del turno
15. (Si tienen acceso) Mostrar el email de confirmación que recibió María
16. Ir a **"Mis Turnos"** para ver el turno reservado

**[B cierra esta historia]**

> "María reservó su turno con un solo click. Recibió confirmación por email y el día anterior recibirá un **recordatorio automático**."

---

### HISTORIA 2: El Dr. García atiende a María

**[B narra]**

> "Ahora vamos a ver el lado del **médico**. El Dr. García es cardiólogo y hoy tiene pacientes agendados."

#### Flujo para A:
1. Abrir navegador incógnito/alternativo
2. Ir a `http://localhost:4200`
3. Login como médico (Dr. García - debe estar pre-cargado)
4. Mostrar el **Portal Médico** con la lista de pacientes del día

**[B explica]**

> "El Dr. García ve en su pantalla principal todos los **pacientes del día**, con el horario de cada turno y el estado de la consulta: pendiente, realizada o cancelada."

#### Flujo para A (navegador de María):
5. Volver al navegador de María
6. Ir a **"Código de Vinculación"**
7. Generar código
8. Mostrar el código en pantalla

**[B explica]**

> "Para que el médico pueda acceder a la historia clínica de María, ella debe generar un **código de vinculación temporal**. Esto garantiza la privacidad: el médico solo accede con autorización explícita del paciente."

#### Flujo para A (navegador del médico):
9. Click en **"Iniciar Consulta"** en el turno de María
10. Ingresar el código de vinculación
11. Se abre la pantalla de atención

**[A habla]**

> "Ahora el Dr. García tiene acceso completo a la información de María: puede ver su historia clínica, los medicamentos que toma, los estudios previos."

#### Flujo para A:
12. Mostrar las opciones: Historia Clínica, Medicamentos, Estudios, Consultas
13. Crear una nueva **Historia Clínica** (si María es nueva) o editarla
   - Completar: peso, altura, grupo sanguíneo, alergias, antecedentes

**[B explica]**

> "El médico puede crear o actualizar la historia clínica del paciente. Esta información queda guardada y disponible para futuras consultas con cualquier médico autorizado."

#### Flujo para A:
14. Agregar un **medicamento** a María (ej: Aspirina 100mg)
15. Agregar un **estudio** pendiente (ej: Electrocardiograma)
16. Crear una **consulta médica** con el diagnóstico y observaciones
17. Click en **"Finalizar Consulta"**

**[B cierra]**

> "Al finalizar la consulta, María recibe un **email de resumen** con todo lo que se indicó: medicamentos nuevos, estudios pedidos, y las observaciones del médico. Esto le sirve como recordatorio y registro de la consulta."

---

### HISTORIA 3: María gestiona sus medicamentos y estudios

**[B narra]**

> "Unos días después, María recibe un recordatorio de que tiene que tomarse la medicación y hacerse el electrocardiograma."

#### Flujo para A (navegador de María):
1. Login como María
2. Ir a **"Medicamentos"**
3. Mostrar la lista con filtros por estado (Pendiente/Tomado)
4. Marcar un medicamento como **"Tomado"**

**[B explica]**

> "María puede llevar un control de sus medicamentos, marcando cuáles ya tomó. Esto es especialmente útil para tratamientos largos o múltiples medicamentos."

#### Flujo para A:
5. Ir a **"Estudios"**
6. Mostrar los estudios pendientes
7. Marcar el electrocardiograma como **"Realizado"** (cuando se lo hizo)

**[A habla]**

> "Lo mismo con los estudios: María puede marcarlos como realizados cuando los complete. El médico también puede ver el estado actualizado."

---

### HISTORIA 4: El Dr. García organiza su agenda

**[B narra]**

> "Veamos ahora cómo el médico gestiona su disponibilidad."

#### Flujo para A (navegador médico):
1. Ir a **"Agenda"** (calendario)
2. Mostrar la vista mensual con turnos disponibles y reservados
3. Click en un día para ver el detalle
4. Mostrar las acciones: bloquear turno, liberar turno, reservar para paciente

**[B explica]**

> "El médico puede ver su calendario completo. Los turnos en **verde** están disponibles, los **azules** están reservados. Puede bloquear turnos si no va a atender ese día, o reservar directamente para un paciente."

#### Flujo para A:
5. Click en **"Crear Agenda"** (si no lo hicieron antes)
6. Mostrar el formulario: días de atención, horarios, duración de turnos, centro médico
7. Generar la agenda

**[A habla]**

> "El sistema genera automáticamente los turnos para los próximos 30 días, respetando los horarios elegidos y dejando una pausa para almuerzo."

---

### HISTORIA 5: María descarga su historia clínica

**[B narra]**

> "María tiene una consulta con otro médico y necesita llevar su historial. Con nuestra app, no necesita papeles."

#### Flujo para A:
1. Login como María
2. Ir a **"Historia Clínica"**
3. Click en **"Descargar PDF"**
4. Mostrar las opciones: incluir historia, estudios, medicamentos, consultas
5. Filtrar consultas por estado o especialidad
6. Descargar y mostrar el PDF

**[B cierra]**

> "María puede generar un PDF completo con toda su información médica, filtrando exactamente lo que necesita compartir. Esto le da control total sobre su información de salud."

---

# PARTE 2: PRESENTACIÓN TÉCNICA (~8-10 min)

## 2.1 Arquitectura del Sistema

**[A habla - puede mostrar un diagrama si tienen]**

> "Nuestra arquitectura es un **monolito modular** con separación clara entre frontend y backend:"

```
┌─────────────────────────────────────────────────────────┐
│                    FRONTEND (Angular 15)                 │
│  ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────────┐   │
│  │ Patient │ │  Medic  │ │  Lists  │ │ Components  │   │
│  │  Pages  │ │  Pages  │ │  Pages  │ │  (Navbar)   │   │
│  └─────────┘ └─────────┘ └─────────┘ └─────────────┘   │
│                         │                               │
│                   HTTP / REST API                       │
└─────────────────────────────────────────────────────────┘
                          │
                          ▼
┌─────────────────────────────────────────────────────────┐
│               BACKEND (Spring Boot 3.0)                  │
│  ┌──────────────────────────────────────────────────┐   │
│  │              Presentation Layer                   │   │
│  │   Controllers + DTOs + Exception Handlers         │   │
│  └──────────────────────────────────────────────────┘   │
│  ┌──────────────────────────────────────────────────┐   │
│  │               Service Layer                       │   │
│  │   Business Logic + Email Service + PDF Service    │   │
│  └──────────────────────────────────────────────────┘   │
│  ┌──────────────────────────────────────────────────┐   │
│  │             Persistence Layer                     │   │
│  │         JPA Entities + Repositories               │   │
│  └──────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────┘
                          │
                          ▼
┌─────────────────────────────────────────────────────────┐
│                  MySQL (Docker)                          │
└─────────────────────────────────────────────────────────┘
```

---

## 2.2 Tecnologías Usadas y Justificación

**[B habla]**

### Backend

| Tecnología | Justificación |
|------------|---------------|
| **Java 17 + Spring Boot 3.0** | Framework maduro, excelente para APIs REST, gran comunidad y documentación |
| **Spring Data JPA** | Simplifica el acceso a datos con repositorios declarativos |
| **JWT (jjwt)** | Autenticación stateless, ideal para SPAs, tokens seguros |
| **MySQL 8** | Base de datos relacional robusta, ideal para datos estructurados como historias clínicas |
| **Docker** | Facilita el despliegue y garantiza consistencia entre entornos |
| **SendGrid** | Servicio de email confiable con alta deliverability |
| **iText 7** | Generación de PDFs profesionales para historias clínicas |
| **Lombok** | Reduce boilerplate code en entidades y DTOs |
| **Quartz Scheduler** | Programación de tareas (recordatorios de turnos y estudios) |

### Frontend

| Tecnología | Justificación |
|------------|---------------|
| **Angular 15** | Framework completo con TypeScript, ideal para aplicaciones empresariales |
| **Angular Material** | Componentes UI consistentes y accesibles |
| **PrimeNG** | Componentes adicionales como tablas avanzadas y calendarios |
| **FullCalendar** | Visualización de calendarios profesional para la agenda médica |
| **SweetAlert2** | Alertas y confirmaciones con mejor UX que los alerts nativos |

---

## 2.3 Decisiones de Diseño Importantes

**[A habla]**

### 1. Sistema de Vinculación por Código

> "Elegimos un sistema de **códigos temporales** para vincular pacientes y médicos en lugar de una relación directa. Esto da más **privacidad** al paciente: decide cuándo y con quién compartir su información."

### 2. Separación de Roles

> "Tenemos dos flujos completamente separados: **Portal Paciente** y **Portal Médico**. Cada uno tiene su login, su home y sus funcionalidades específicas. Esto simplifica la UX y la seguridad."

### 3. Estados en Consultas y Turnos

> "Implementamos una máquina de estados para turnos: DISPONIBLE → RESERVADO → REALIZADA/CANCELADA/VENCIDO. Esto permite tracking completo del ciclo de vida de cada turno."

### 4. Emails Transaccionales

> "Usamos **SendGrid** porque tiene alta tasa de entrega y no termina en spam. Implementamos emails para: verificación de cuenta, confirmación de turno, recordatorio 24hs antes, y resumen post-consulta."

### 5. Generación Dinámica de Turnos

> "El médico define su disponibilidad (días, horarios, duración) y el sistema **genera automáticamente** los turnos para 30 días. Esto evita que el médico tenga que cargar turno por turno."

---

## 2.4 Modelo de Datos (Simplificado)

**[B habla]**

> "Nuestras entidades principales son:"

- **Patient**: datos personales, linkCode para vinculación
- **Medic**: datos profesionales, especialidad, centro médico
- **MedicalFile**: historia clínica (peso, altura, grupo sanguíneo, alergias, antecedentes)
- **Medicine**: medicamentos recetados con estado (pendiente/tomado)
- **Analysis**: estudios pedidos con estado y fecha programada
- **MedicalAppointment**: consultas médicas con diagnóstico y observaciones
- **Turnos**: slots de atención con fecha, hora, estado y relaciones

**[A puede mostrar brevemente la carpeta de modelos si hay tiempo]**

---

## 2.5 Seguridad

**[A habla]**

> "Implementamos:"

1. **JWT con expiración** para autenticación
2. **Validación de email** obligatoria para activar cuentas
3. **Código de vinculación temporal** para acceso a datos de pacientes
4. **CORS configurado** para el frontend específico
5. **Contraseñas hasheadas** (no en texto plano)

---

# RESUMEN DE CASOS DE USO POR ACTOR

## Paciente puede:
- ✅ Registrarse y verificar email
- ✅ Login/Logout
- ✅ Ver su historia clínica
- ✅ Ver lista de medicamentos (filtrar por estado)
- ✅ Cambiar estado de medicamentos
- ✅ Ver lista de estudios (filtrar por estado)  
- ✅ Cambiar estado de estudios
- ✅ Ver lista de médicos vinculados
- ✅ Generar código de vinculación
- ✅ Buscar y reservar turnos (filtros: fecha, especialidad, médico, centro)
- ✅ Ver y cancelar sus turnos
- ✅ Ver historial de consultas médicas
- ✅ Descargar historia clínica en PDF (con filtros)

## Médico puede:
- ✅ Registrarse y verificar email
- ✅ Login/Logout
- ✅ Ver pacientes del día
- ✅ Vincular paciente por código
- ✅ Ver lista de pacientes
- ✅ Crear/editar historia clínica del paciente
- ✅ ABM medicamentos del paciente
- ✅ ABM estudios del paciente
- ✅ Crear consultas médicas
- ✅ Ver historial de consultas
- ✅ Gestionar agenda (crear disponibilidad)
- ✅ Ver calendario con turnos
- ✅ Bloquear/liberar turnos
- ✅ Reservar turnos para pacientes
- ✅ Iniciar y finalizar consultas desde turnos

## Sistema envía:
- ✅ Email de verificación de cuenta
- ✅ Email de confirmación de turno
- ✅ Email recordatorio (día anterior)
- ✅ Email resumen post-consulta

---

# CHECKLIST PRE-DEMO

- [ ] Docker corriendo con MySQL
- [ ] Backend levantado (`./run.sh`)
- [ ] Frontend levantado (`ng serve`)
- [ ] Variables de entorno de SendGrid configuradas
- [ ] Usuario paciente de prueba creado y verificado
- [ ] Usuario médico de prueba creado y verificado  
- [ ] Agenda del médico creada con turnos disponibles
- [ ] Al menos un turno reservado para mostrar el flujo de consulta
- [ ] Dos navegadores listos (normal + incógnito)
- [ ] Acceso al email para mostrar notificaciones (opcional pero recomendado)

---

# TIEMPOS SUGERIDOS

| Sección | Tiempo |
|---------|--------|
| Introducción + Comparación | 3-4 min |
| Historia 1 (registro + turno) | 3 min |
| Historia 2 (consulta médica) | 4 min |
| Historia 3 (medicamentos/estudios) | 2 min |
| Historia 4 (agenda médico) | 2 min |
| Historia 5 (descarga PDF) | 1 min |
| Arquitectura + Tecnologías | 4-5 min |
| Decisiones + Seguridad | 3 min |
| **Total** | **~20-25 min** |

---

# TIPS FINALES

1. **Practicar la demo** al menos 2 veces antes del final
2. **Tener datos realistas** en la base (nombres, diagnósticos, medicamentos reales)
3. **Si algo falla**: tener screenshots de respaldo o saber improvisar
4. **Mantener contacto visual** con los evaluadores mientras A hace la demo
5. **Turnarse para hablar** de forma natural, no leer
6. **Responder preguntas con confianza**: conocen el código, lo construyeron ustedes

¡Éxitos en el final! 🎓
