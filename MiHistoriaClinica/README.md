# MiHistoriaClinica

## Proyecto para Laboratorio 1 - Universidad Austral

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

Asegúrate de tener **Java 17** instalado y seleccionado como versión activa.

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

### 4. **Resumen de URLs**

* **Backend:** [http://localhost:8080](http://localhost:8080)
* **Frontend:** [http://localhost:4200](http://localhost:4200)

---


### Casos de Uso CURSADA: 

                                                                        BACK              FRONT                                                        

  * Login - Logout						                                	        OK	              OK

  * AMB medicamentos desde el medico
        --> createPatientMedicine 					                            OK	              OK
        --> getPatientMedicine						                              OK              	OK
        --> updatePatientMedicine					                              OK                OK
        --> deletePatientMedicine                                       OK                OK
	
  * ABM estudios--> AnalysisModel
      --> createPatientAnalysis (medic)                                 OK                OK
      --> getPatientAnalysis    (medic)                                 OK                OK       
      --> getAnalysis           (patient)                               OK                OK
      --> updateAnalysisStatus  (patient)                               OK                OK
      --> deletePatientAnalysis (medic)                                 OK                OK

  * Altas historias clinicas ---> createPatientMedicalHistory 	        OK 	              OK

  * Agregar paciente --> 	LinkPatient 				                          OK 	              OK

  * Ver lista de pacientes desde un medico --> getPatients 		          OK  	            OK
  * Ver lista de medicos desde un paciente --> getMedics		            OK	              OK

  * Ver historia clinica como medico                                    OK                OK
  * Modificar historia clinica como medico                              OK                OK
  * Alta  de consulta 						                                      OK	              OK
  * Ver Historial de consulta        (como médico y como paciente)      OK                OK 

  * Ver lista de medicamentos desde el paciente 			                  OK	              OK
  * Ver estudios como paciente                                          OK                OK

  * Cambiar estado medicamentos desde el paciente 			                OK	              OK 
  * Cambiar estado de estudios como paciente                            OK                OK 
  * Cambiar estado medicamento desde el paciente 			                  OK	              OK 
  * Cambiar estado de estudios como paciente                            OK                OK
    --> si esta pendiente o realizado                                                       

  * Filtrar listas por estado                                                             
  --> Medicamentos como paciente                                        OK                  OK
  --> Medicamentos como medico                                          OK                  OK
  --> Analysis como paciente                                            OK                  OK
  --> Análisis como médico                                              OK                  OK

  * Agendar turnos propios                                                                
  --> createTurno                                                       OK                  OK
  * 
  * Listar turnos propios                                               OK                  OK

    --> que el paciente pueda añadir su propio turno, viendo SU lista de medicos y teniendo la opcion de cargar un medico nuevo
    --> el método que usemos para cargar un nuevo médico debería verificar que no esté ya cargado
    --> que para el médico nuevo se muestre toda la lista de medicos ya registrados
    --> CAMBIAR ESPECIALIDADES DE MEDICOS para que desde el front se elija la especialidad desde una lista para poder filtrar



### Casos de Uso FINAL:

                                                                        BACK              FRONT
  * Agregar nuevos filtros para sacar turnos
  --> Por rangos de fechas                                               --                --
  --> Por especialidad de médico                                         --                --
  --> Por centro médico                                                  --                --

  * Descargar 
  --> Descargar historia general                                         --                --          
  --> Descargar historial de consultas médicas filtradas                 --                --

  * Iniciar consulta desde turnos acordados                              --                --
  --> Agregar estado a las consultas médicas (default=pendiente)         --                --

  * Calendarios
  --> Agenda de médicos                                                  --                --
  --> Agenda de turnos paciente (ver agendados y nuevos disponibles)     --                --

  * Mails
  --> Mail de confirmación al agendar turno o estudio                    --                ?
  --> Mail de recordatorio día anterior al turno o estudio               --                ?

    
