## Cómo levantar el backend y el frontend

### 1. **Levantar la base de datos (MySQL)**

Desde la carpeta raíz del proyecto, ejecuta:

```bash
cd MiHistoriaClinica/MiHistoriaClinica
docker-compose up -d
```

Esto creará un contenedor MySQL con:
* **Base de datos:** `miHistoriaClinicaDB`
* **Usuario:** `root`
* **Contraseña:** `root`
* **Puerto:** `3306`

---

### 2. **Levantar el backend (Spring Boot)**

#### IMPORTANTE
Con las env que necesita sengrid es importante correr el backend con el script `run.sh` o exportar las variables de entorno manualmente antes de levantar el backend.
el script `run.sh` carga las variables desde el archivo `.env` y luego ejecuta el backend.
hay un .env de ejemplo `.env.example` en la raíz del proyecto que podes copiar a `.env` y completar con tus credenciales de sengrid.

**Opción 1 - Con el script (Recomendado):**
```bash
./run.sh
```

Desde la carpeta `MiHistoriaClinica/MiHistoriaClinica/`, ejecuta:

```bash
cd MiHistoriaClinica/MiHistoriaClinica

# Da permisos de ejecución al wrapper si es necesario
chmod +x ./gradlew

# Levanta el backend
./gradlew bootRun
```

El backend quedará disponible en:  
[http://localhost:8080](http://localhost:8080)

---

### 3. **Levantar el frontend (Angular)**

Asegúrate de tener **Node.js** (v16 o superior) y **Angular CLI** instalados.

```bash
node -v
# Debería mostrar una versión 16.x, 18.x o superior

npm -v
# Debería mostrar la versión de npm (Node Package Manager)

ng version
# Debería mostrar la versión de Angular CLI si está instalado
```

Desde la carpeta `MiHistoriaClinica/MiHistoriaClinica/frontend/`, ejecuta:

```bash
cd MiHistoriaClinica/MiHistoriaClinica

npm install
ng serve
```

El frontend estará disponible en:  
[http://localhost:4200](http://localhost:4200)

---


### **Resumen de URLs**

* **Backend:** [http://localhost:8080](http://localhost:8080)
* **Frontend:** [http://localhost:4200](http://localhost:4200)
---