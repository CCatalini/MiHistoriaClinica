# MiHistoriaClinica

## Proyecto para Laboratorio 1 - Universidad Austral


### Casos de Uso: 

                                                                        BACK              FRONT                                                        

  * Login - Logout						                    	        OK	                OK

  * medicamentos desde el medico
        --> createPatientMedicine 					                    OK	                OK
        --> getPatientMedicine						                    OK              	OK
        --> updatePatientMedicine					                    OK
        --> deletePatientMedicine
	
  * ABM estudios desde el medico --> AnalysisModel
      --> createPatientAnalysis
      --> getPatientAnalysis
      --> updatePatientAnalysis
      --> deletePatientAnalysis

  * Altas historias clinicas ---> createPatientMedicalHistory 	        OK 	                OK

  * Agregar paciente --> 	LinkPatient 				                OK 	                OK

  * Ver lista de pacientes desde un medico --> getPatients 		        OK	                OK
  * Ver lista de medicos desde un paciente --> getMedics		        OK	                OK

  * Ver y Modificar historia clinica como medico
  * Alta  de consulta 						                            OK	                OK
  * Ver Historial de consulta

  * Ver lista de medicamentos desde el paciente 			            OK	                OK
  * Ver estudios como paciente 

  * Cambiar estado medicamentos desde el paciente 			            OK	              enproceso 
  * Cambiar estado de estudios como paciente 
    --> si esta pendiente o realizado 

  * Filtrar listas
    --> CAMBIAR ESPECIALIDADES DE MEDICOS para que desde el front se elija la especialidad desde una lista de especialidades para poder filtrar
  * Agenda de turnos propios
    --> que el paciente pueda añadir su propio turno, viendo su lista de medicos y teniendo la opcion de cargar un medico nuevo 
    --> que para el medico nuevo se muestre toda la lista de medicos ya registrados






## Casos de Usos Deseables:
* Descargar historia clinica
* creación de la entidad Administrador, que se encarga del ABM de estudios y vacunas obligatorios
(Bajo los criterios de sexo, edad) 
