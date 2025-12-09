# MiHistoriaClinica

## Proyecto para Laboratorio 1 - Universidad Austral

### **Resumen de URLs**

* **Backend:** [http://localhost:8080](http://localhost:8080)
* **Frontend:** [http://localhost:4200](http://localhost:4200)
---


### Casos de Uso CURSADA:
                                                                        BACK              FRONT                                                        

  * Login - Logout						                     	        OK	              OK
  * AMB medicamentos desde el medico
        --> createPatientMedicine 			                            OK	              OK
        --> getPatientMedicine				                            OK                OK
        --> updatePatientMedicine			                            OK                OK
        --> deletePatientMedicine                                       OK                OK
	
  * ABM estudios--> AnalysisModel
      --> createPatientAnalysis (medic)                                 OK                OK
      --> getPatientAnalysis    (medic)                                 OK                OK       
      --> getAnalysis           (patient)                               OK                OK
      --> updateAnalysisStatus  (patient)                               OK                OK
      --> deletePatientAnalysis (medic)                                 OK                OK

  * Altas historias clinicas ---> createPatientMedicalHistory 	        OK 	              OK

  * Agregar paciente --> 	LinkPatient 				                OK 	              OK

  * Ver lista de pacientes desde un medico --> getPatients 		        OK  	          OK
  * Ver lista de medicos desde un paciente --> getMedics		        OK	              OK

  * Ver historia clinica como medico                                    OK                OK
  * Modificar historia clinica como medico                              OK                OK
  * Alta  de consulta 						                            OK	              OK
  * Ver Historial de consulta        (como médico y como paciente)      OK                OK 

  * Ver lista de medicamentos desde el paciente 			            OK	              OK
  * Ver estudios como paciente                                          OK                OK

  * Cambiar estado medicamentos desde el paciente 			            OK	              OK 
  * Cambiar estado de estudios como paciente                            OK                OK 
  * Cambiar estado medicamento desde el paciente 			            OK	              OK 
  * Cambiar estado de estudios como paciente                            OK                OK
    --> si esta pendiente o realizado                                                       

  * Filtrar listas por estado                                                             
  --> Medicamentos como paciente                                        OK                OK
  --> Medicamentos como medico                                          OK                OK
  --> Analysis como paciente                                            OK                OK
  --> Análisis como médico                                              OK                OK

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
  OBS: los filtros podemos hacerlos desde el front directamente
  --> Por rangos de fechas                                               --                OK
  --> Por especialidad                                                   --                OK
  --> Por médico                                                         --                OK
  --> Por centro médico                                                  --                OK

  * Descargar 
  --> Descargar historia general                                         OK                OK         
  --> Descargar historial de consultas médicas filtradas                 OK                OK

  * Iniciar consulta desde turnos acordados                              OK                OK
  --> Agregar estado a las consultas médicas (default=pendiente)         OK                OK

  * Calendarios
  Agenda de médicos 
  --> Asignar horarios disponibles de atención                           OK                OK
  --> Ver turnos reservados y disponibles                                OK                OK
  Agenda de pacientes
  --> Ver calendario con turnos disponibles a la hora de programar uno   OK                OK

    * Mails
    --> Confirmar y validar cuenta al registrarse                          OK                OK
    --> Mail de confirmación al agendar turno o estudio                    OK                OK
    --> Mail de recordatorio día anterior al turno o estudio               OK                OK 
    --> Mail de resumen post consulta médica                               OK                OK
