# Mi Historia Clinica

**Mi Historia Clinica** es una aplicación web para la gestión de historias clínicas digitales, desarrollada como proyecto de la materia **Laboratorio 1** en la **Universidad Austral**.

La plataforma permite a pacientes y médicos gestionar de manera centralizada información médica: historias clínicas, turnos, medicamentos, estudios y consultas médicas. Incluye funcionalidades de notificaciones por email, calendarios de turnos y descarga de documentación médica.

### Stack Tecnológico
- **Backend:** Java + Spring Boot
- **Frontend:** Angular
- **Base de datos:** MySQL

---

## URLs de Desarrollo

| Servicio | URL |
|----------|-----|
| Backend | http://localhost:8080 |
| Frontend | http://localhost:4200 |

---

## Casos de Uso

### Autenticación
1. Login y Logout de usuarios

### Gestión de Medicamentos (desde el médico)
2. Crear medicamento para paciente
3. Ver medicamentos del paciente
4. Modificar medicamento
5. Eliminar medicamento

### Gestión de Estudios/Análisis
6. Crear estudio para paciente (médico)
7. Ver estudios del paciente (médico)
8. Ver estudios propios (paciente)
9. Actualizar estado del estudio (paciente)
10. Eliminar estudio (médico)

### Historias Clínicas
11. Alta de historia clínica
12. Ver historia clínica como médico
13. Modificar historia clínica como médico

### Relación Médico-Paciente
14. Agregar/vincular paciente
15. Ver lista de pacientes (desde médico)
16. Ver lista de médicos (desde paciente)

### Consultas Médicas
17. Alta de consulta médica
18. Ver historial de consultas (médico y paciente)
19. Iniciar consulta desde turnos acordados
20. Gestión de estados de consulta (pendiente, en curso, finalizada)

### Visualización y Estados (Paciente)
21. Ver lista de medicamentos
22. Ver estudios
23. Cambiar estado de medicamentos
24. Cambiar estado de estudios (pendiente/realizado)

### Filtros
25. Filtrar medicamentos por estado (paciente y médico)
26. Filtrar análisis por estado (paciente y médico)

### Turnos
27. Agendar turnos propios
28. Listar turnos propios
29. Filtrar turnos por rango de fechas
30. Filtrar turnos por especialidad
31. Filtrar turnos por médico
32. Filtrar turnos por centro médico

### Descargas
33. Descargar historia clínica general
34. Descargar historial de consultas médicas filtradas

### Calendarios y Agendas
35. Asignar horarios disponibles de atención (médico)
36. Ver turnos reservados y disponibles (médico)
37. Ver calendario con turnos disponibles al programar (paciente)

### Notificaciones por Email
38. Confirmar y validar cuenta al registrarse
39. Email de confirmación al agendar turno o estudio
40. Email de recordatorio día anterior al turno o estudio
41. Email de resumen post consulta médica
