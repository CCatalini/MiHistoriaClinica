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


