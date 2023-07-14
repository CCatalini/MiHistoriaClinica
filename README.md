# MiHistoriaClinica

## Proyecto para Laboratorio 1 - Universidad Austral


### Casos de Uso CURSADA: 

                                                                        BACK              FRONT                                                        

  * Login - Logout						                    	        OK	                OK

  * AMB medicamentos desde el medico
        --> createPatientMedicine 					                    OK	                OK
        --> getPatientMedicine						                    OK              	OK
        --> updatePatientMedicine					                    OK                  OK
        --> deletePatientMedicine                                       OK                  OK
	
  * ABM estudios--> AnalysisModel
      --> createPatientAnalysis (medic)                                 OK                  OK
      --> getPatientAnalysis    (medic)                                 OK                  OK                
      --> getAnalysis           (patient)                               OK                  OK
      --> updateAnalysisStatus  (patient)                               OK                  OK
      --> deletePatientAnalysis (medic)                                 OK                  OK

  * Altas historias clinicas ---> createPatientMedicalHistory 	        OK 	                OK

  * Agregar paciente --> 	LinkPatient 				                OK 	                OK

  * Ver lista de pacientes desde un medico --> getPatients 		        OK  	            OK
  * Ver lista de medicos desde un paciente --> getMedics		        OK	                OK

  * Ver historia clinica como medico                                    OK                  OK
  * Modificar historia clinica como medico                              OK                  OK
  * Alta  de consulta 						                            OK	                OK
  * Ver Historial de consulta        (como médico y como paciente)      OK                  OK 

  * Ver lista de medicamentos desde el paciente 			            OK	                OK
  * Ver estudios como paciente                                          OK                  OK

  * Cambiar estado medicamentos desde el paciente 			            OK	                OK 
  * Cambiar estado de estudios como paciente                            OK                  OK 
  * Cambiar estado medicamento desde el paciente 			            OK	                OK 
  * Cambiar estado de estudios como paciente                            OK                  OK
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
  * Agregar nuevos filtros 
  --> Por rangos de fechas                                               --                --
  --> Por especialidad de médico                                         --                --
  --> Por centro médico                                                  --                --

  * Descargar 
  --> Descargar historia general                                         --                --          
  --> Descargar historial de consultas médicas filtradas                 --                --

  * Iniciar consulta desde turnos acordados                              --                --

  * Calendarios
  --> Agenda de médicos                                                  --                --
  --> Agenda de turnos paciente (ver agendados y nuevos disponibles)     --                --

  * Mails
  --> Mail de confirmación al agendar turno o estudio                    --                --
  --> Mail de recordatorio día anterior al turno o estudio               --                --






## Casos de Usos Deseables:

  * Descargar historia clinica
  * Creación de la entidad Administrador, que se encarga del ABM de estudios y vacunas obligatorios
(Bajo los criterios de sexo, edad) 
  * Filtrar médicos por especialidad
  * Filtrar consultas por estado
  * Linkear turno con consulta médica para que el paciente "inicie la consulta" desde la lista de turnos generando el linkCode
  * Agregar asistencia a la lista de turnos (debería cambiar a "Presente" cuando el paciente inicie la consulta)
  * Mostrar la agenda con formato de calendario
  * Agregar FECHA de emisión a medicines, analysis y medicalAppointment
  * FILTRAR listas por fecha


.

## TODO
* Agregar LOGO
* Finalizar consulta desde paciente (método que borre el patientLinkCode)
* Al cargar un nuevo medicamento o análisis, cargar estado = "pendiente" por default
* Agregar e* Agregar especialidad a medicalAppointment para poder filtrar consultas por estadospecialidad a medicalAppointment para poder filtrar consultas por estado