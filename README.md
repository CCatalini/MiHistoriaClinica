# MiHistoriaClinica

## Proyecto para Laboratorio 1 - Universidad Austral


### Casos de Uso: 

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
  * Modificar historia clinica como medico                              OK                  - !!!
  * Alta  de consulta 						                            OK	                OK
  * Ver Historial de consulta        (como médico y como paciente)      OK                  OK 

  * Ver lista de medicamentos desde el paciente 			            OK	                OK
  * Ver estudios como paciente                                          OK                  OK

  * Cambiar estado medicamentos desde el paciente 			            OK	                OK 
  * Cambiar estado de estudios como paciente                            OK                  OK 
  * Cambiar estado medicamento desde el paciente 			            OK	                OK 
  * Cambiar estado de estudios como paciente                            OK                  OK
    --> si esta pendiente o realizado                                                       

  * Filtrar listas por estado                                           OK                  OK
  --> Medicamentos como paciente                                        OK
  --> Medicamentos como medico
  --> Analysis como paciente                                            OK
  --> Análisis como médico                                              OK   



  * Agenda de turnos propios                                            --                  --

    --> que el paciente pueda añadir su propio turno, viendo SU lista de medicos y teniendo la opcion de cargar un medico nuevo
    --> el método que usemos para cargar un nuevo médico debería verificar que no esté ya cargado
    --> que para el médico nuevo se muestre toda la lista de medicos ya registrados
    --> CAMBIAR ESPECIALIDADES DE MEDICOS para que desde el front se elija la especialidad desde una lista para poder filtrar

    



## Casos de Usos Deseables:
* Descargar historia clinica
* creación de la entidad Administrador, que se encarga del ABM de estudios y vacunas obligatorios
(Bajo los criterios de sexo, edad) 



## TODO
* Mostrar PERFIL con datos de usuario (métodos getPatientDTO(token), getMedicDTO(token) )
* Agregar FECHA de emisión a medicines, analysis y medicalAppointment ??
* FILTRAR listas por fecha
* Agregar LOGO
* Editar analysis que cargó un médico y no están finalizados
* Finalizar consulta desde paciente (método que borre el patientLinkCode)
