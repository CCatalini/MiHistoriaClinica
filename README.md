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
      --> createPatientAnalysis (medic)                                 OK                en proceso
      --> getPatientAnalysis    (medic)                                 OK                   -                
      --> getAnalysis           (patient)                               OK                   -
      --> updateAnalysisStatus  (patient)                               OK                   -
      --> deletePatientAnalysis (medic)                                 OK                   -

  * Altas historias clinicas ---> createPatientMedicalHistory 	        OK 	                OK

  * Agregar paciente --> 	LinkPatient 				                OK 	                OK

  * Ver lista de pacientes desde un medico --> getPatients 		        OK  	            OK
  * Ver lista de medicos desde un paciente --> getMedics		        OK	                OK

  * Ver historia clinica como medico                                    OK                  OK
  * Modificar historia clinica como medico                              OK                  - !!!
  * Alta  de consulta 						                            OK	                OK
  * Ver Historial de consulta                                           OK                  OK

  * Ver lista de medicamentos desde el paciente 			            OK	                OK
  * Ver estudios como paciente                                          OK                  -

  * Cambiar estado medicamentos desde el paciente 			            OK	                OK 
  * Cambiar estado de estudios como paciente                            OK                  OK 
    --> si esta pendiente o realizado                                                       

  * Filtrar listas                                                      -                   -

    --> CAMBIAR ESPECIALIDADES DE MEDICOS para que desde el front se elija la especialidad desde una lista de especialidades para poder filtrar
  * Agenda de turnos propios
    --> que el paciente pueda añadir su propio turno, viendo su lista de medicos y teniendo la opcion de cargar un medico nuevo 
    --> que para el medico nuevo se muestre toda la lista de medicos ya registrados






## Casos de Usos Deseables:
* Descargar historia clinica
* creación de la entidad Administrador, que se encarga del ABM de estudios y vacunas obligatorios
(Bajo los criterios de sexo, edad) 



## TODO
* Finalizar consulta desde paciente (método que borre el patientLinkCode)
* Mostrar datos de usuario (métodos getPatientDTO(token), getMedicDTO(token) )
* Agregar fecha de emisión a medicines, analysis y medicalAppointment ??
* FILTRAR listas 
  * Por nombre de medicamento, laboratorio, estado, nombre de estudio, etc
  * Armar métodos getMedicicinesByName(), getMedicinesByStatus(), etc
* Agregar LOGO
* Agregar "perfil"