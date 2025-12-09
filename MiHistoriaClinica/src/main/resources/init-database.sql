-- ============================================
-- MI HISTORIA CLINICA - Init database inicial
-- ============================================

USE miHistoriaClinicaDB;

-- ==================== LIMPIAR TABLAS ====================
SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE patient_analysis;
TRUNCATE TABLE patient_medicine;
TRUNCATE TABLE patient_medic;
TRUNCATE TABLE turnos;
TRUNCATE TABLE medical_appointment;
TRUNCATE TABLE analysis;
TRUNCATE TABLE medicine;
TRUNCATE TABLE medical_file;
TRUNCATE TABLE study_schedule;
TRUNCATE TABLE patient;
TRUNCATE TABLE medic;

SET FOREIGN_KEY_CHECKS = 1;


-- ==================== MÉDICOS ====================
INSERT INTO medic (medic_id, name, lastname, email, dni, matricula, specialty, password, email_verified, enabled, verification_token) VALUES
(1, 'Carlos', 'González', 'carlos.gonzalez@hospital.com', 30123456, 100001, 0, 'test123', 1, 1, NULL),
(2, 'Ana', 'Martínez', 'ana.martinez@hospital.com', 30234567, 100002, 0, 'test123', 1, 1, NULL),
(3, 'Roberto', 'López', 'roberto.lopez@hospital.com', 30345678, 100003, 0, 'test123', 1, 1, NULL),
(4, 'Laura', 'Sánchez', 'laura.sanchez@hospital.com', 31123456, 100004, 1, 'test123', 1, 1, NULL),
(5, 'Jorge', 'Pérez', 'jorge.perez@hospital.com', 31234567, 100005, 1, 'test123', 1, 1, NULL),
(6, 'Sofía', 'García', 'sofia.garcia@hospital.com', 31345678, 100006, 1, 'test123', 1, 1, NULL),
(7, 'Juliana', 'Herrera', 'juliana.herrera@hospital.com', 32123456, 100007, 10, 'test123', 1, 1, NULL),
(8, 'Matías', 'Medina', 'matias.medina@hospital.com', 32234567, 100008, 10, 'test123', 1, 1, NULL),
(9, 'Victoria', 'Núñez', 'victoria.nunez@hospital.com', 32345678, 100009, 10, 'test123', 1, 1, NULL),
(10, 'Lucas', 'Rojas', 'lucas.rojas@hospital.com', 32456789, 100010, 10, 'test123', 1, 1, NULL),
(11, 'Florencia', 'Campos', 'florencia.campos@hospital.com', 32567890, 100011, 10, 'test123', 1, 1, NULL),
(12, 'Sandra', 'Miranda', 'sandra.miranda@hospital.com', 33123456, 100012, 20, 'test123', 1, 1, NULL),
(13, 'Claudio', 'Figueroa', 'claudio.figueroa@hospital.com', 33234567, 100013, 20, 'test123', 1, 1, NULL),
(14, 'Mariana', 'Ledesma', 'mariana.ledesma@hospital.com', 33345678, 100014, 20, 'test123', 1, 1, NULL),
(15, 'Leonardo', 'Peralta', 'leonardo.peralta@hospital.com', 33456789, 100015, 20, 'test123', 1, 1, NULL),
(16, 'Alejandra', 'Ibarra', 'alejandra.ibarra@hospital.com', 33567890, 100016, 20, 'test123', 1, 1, NULL),
(17, 'Alejandro', 'Vega', 'alejandro.vega@hospital.com', 34123456, 100017, 14, 'test123', 1, 1, NULL),
(18, 'Jimena', 'Luna', 'jimena.luna@hospital.com', 34234567, 100018, 14, 'test123', 1, 1, NULL),
(19, 'Rodrigo', 'Ríos', 'rodrigo.rios@hospital.com', 34345678, 100019, 14, 'test123', 1, 1, NULL),
(20, 'Micaela', 'Benítez', 'micaela.benitez@hospital.com', 34456789, 100020, 14, 'test123', 1, 1, NULL),
(21, 'Patricia', 'Aguilar', 'patricia.aguilar@hospital.com', 35123456, 100021, 16, 'test123', 1, 1, NULL),
(22, 'Adriana', 'Molina', 'adriana.molina@hospital.com', 35234567, 100022, 16, 'test123', 1, 1, NULL),
(23, 'Verónica', 'Cabrera', 'veronica.cabrera@hospital.com', 35345678, 100023, 16, 'test123', 1, 1, NULL),
(24, 'Daniela', 'Reyes', 'daniela.reyes@hospital.com', 35456789, 100024, 16, 'test123', 1, 1, NULL),
(25, 'Sebastián', 'Ramírez', 'sebastian.ramirez@hospital.com', 36123456, 100025, 6, 'test123', 1, 1, NULL),
(26, 'Carolina', 'Mendoza', 'carolina.mendoza@hospital.com', 36234567, 100026, 6, 'test123', 1, 1, NULL),
(27, 'Nicolás', 'Gutiérrez', 'nicolas.gutierrez@hospital.com', 36345678, 100027, 6, 'test123', 1, 1, NULL),
(28, 'Gabriela', 'Vargas', 'gabriela.vargas@hospital.com', 36456789, 100028, 6, 'test123', 1, 1, NULL),
(29, 'Federico', 'Silva', 'federico.silva@hospital.com', 37123456, 100029, 3, 'test123', 1, 1, NULL),
(30, 'Luciana', 'Morales', 'luciana.morales@hospital.com', 37234567, 100030, 3, 'test123', 1, 1, NULL),
(31, 'Emiliano', 'Castro', 'emiliano.castro@hospital.com', 37345678, 100031, 3, 'test123', 1, 1, NULL),
(32, 'Mariela', 'Acosta', 'mariela.acosta@hospital.com', 38123456, 100032, 11, 'test123', 1, 1, NULL),
(33, 'Gustavo', 'Paz', 'gustavo.paz@hospital.com', 38234567, 100033, 11, 'test123', 1, 1, NULL),
(34, 'Cecilia', 'Sosa', 'cecilia.sosa@hospital.com', 38345678, 100034, 11, 'test123', 1, 1, NULL),
(35, 'Facundo', 'Ramos', 'facundo.ramos@hospital.com', 39123456, 100035, 8, 'test123', 1, 1, NULL),
(36, 'Antonella', 'Ponce', 'antonella.ponce@hospital.com', 39234567, 100036, 8, 'test123', 1, 1, NULL),
(37, 'Maximiliano', 'Navarro', 'maximiliano.navarro@hospital.com', 39345678, 100037, 8, 'test123', 1, 1, NULL),
(38, 'Valeria', 'Torres', 'valeria.torres@hospital.com', 40123456, 100038, 2, 'test123', 1, 1, NULL),
(39, 'Martín', 'Flores', 'martin.flores@hospital.com', 40234567, 100039, 2, 'test123', 1, 1, NULL),
(40, 'Camila', 'Díaz', 'camila.diaz@hospital.com', 40345678, 100040, 2, 'test123', 1, 1, NULL),
(41, 'Daniel', 'Escobar', 'daniel.escobar@hospital.com', 41123456, 100041, 15, 'test123', 1, 1, NULL),
(42, 'Paola', 'Giménez', 'paola.gimenez@hospital.com', 41234567, 100042, 15, 'test123', 1, 1, NULL),
(43, 'Ricardo', 'Quiroga', 'ricardo.quiroga@hospital.com', 42123456, 100043, 18, 'test123', 1, 1, NULL),
(44, 'Mónica', 'Zárate', 'monica.zarate@hospital.com', 42234567, 100044, 18, 'test123', 1, 1, NULL),
(45, 'Guillermo', 'Coronel', 'guillermo.coronel@hospital.com', 42345678, 100045, 18, 'test123', 1, 1, NULL),
(46, 'Teresa', 'Delgado', 'teresa.delgado@hospital.com', 43123456, 100046, 9, 'test123', 1, 1, NULL),
(47, 'Eduardo', 'Soto', 'eduardo.soto@hospital.com', 43234567, 100047, 9, 'test123', 1, 1, NULL),
(48, 'Oscar', 'Montenegro', 'oscar.montenegro@hospital.com', 44123456, 100048, 13, 'test123', 1, 1, NULL),
(49, 'Elena', 'Cortés', 'elena.cortes@hospital.com', 44234567, 100049, 13, 'test123', 1, 1, NULL),
(50, 'Cristian', 'Bustos', 'cristian.bustos@hospital.com', 45123456, 100050, 7, 'test123', 1, 1, NULL);


-- ==================== PACIENTES ====================
INSERT INTO patient (patient_id, name, lastname, email, dni, password, birthdate, email_verified, enabled, verification_token, link_code) VALUES
(1, 'Juan', 'Pérez', 'juan.perez@gmail.com', 25123456, 'test123', '1980-03-15', 1, 1, NULL, 'LINK001'),
(2, 'María', 'González', 'maria.gonzalez@gmail.com', 25234567, 'test123', '1985-07-22', 1, 1, NULL, 'LINK002'),
(3, 'Pedro', 'Rodríguez', 'pedro.rodriguez@gmail.com', 25345678, 'test123', '1992-11-08', 1, 1, NULL, 'LINK003'),
(4, 'Laura', 'Fernández', 'laura.fernandez@gmail.com', 25456789, 'test123', '1978-02-14', 1, 1, NULL, 'LINK004'),
(5, 'Carlos', 'López', 'carlos.lopez@gmail.com', 25567890, 'test123', '1995-09-30', 1, 1, NULL, 'LINK005'),
(6, 'Ana', 'Martínez', 'ana.martinez.pac@gmail.com', 25678901, 'test123', '1988-05-18', 1, 1, NULL, 'LINK006'),
(7, 'Diego', 'Sánchez', 'diego.sanchez@gmail.com', 25789012, 'test123', '1983-12-25', 1, 1, NULL, 'LINK007'),
(8, 'Sofía', 'García', 'sofia.garcia.pac@gmail.com', 25890123, 'test123', '1990-06-11', 1, 1, NULL, 'LINK008'),
(9, 'Martín', 'Díaz', 'martin.diaz@gmail.com', 25901234, 'test123', '1987-01-07', 1, 1, NULL, 'LINK009'),
(10, 'Valentina', 'Torres', 'valentina.torres@gmail.com', 26012345, 'test123', '1993-08-19', 1, 1, NULL, 'LINK010'),
(11, 'Lucas', 'Romero', 'lucas.romero.pac@gmail.com', 26123456, 'test123', '1981-04-03', 1, 1, NULL, 'LINK011'),
(12, 'Camila', 'Flores', 'camila.flores@gmail.com', 26234567, 'test123', '1996-10-27', 1, 1, NULL, 'LINK012'),
(13, 'Santiago', 'Silva', 'santiago.silva@gmail.com', 26345678, 'test123', '1984-07-15', 1, 1, NULL, 'LINK013'),
(14, 'Florencia', 'Morales', 'florencia.morales.pac@gmail.com', 26456789, 'test123', '1991-03-09', 1, 1, NULL, 'LINK014'),
(15, 'Facundo', 'Castro', 'facundo.castro.pac@gmail.com', 26567890, 'test123', '1979-11-21', 1, 1, NULL, 'LINK015'),
(16, 'Agustina', 'Ortiz', 'agustina.ortiz@gmail.com', 26678901, 'test123', '1994-05-05', 1, 1, NULL, 'LINK016'),
(17, 'Nicolás', 'Ramírez', 'nicolas.ramirez.pac@gmail.com', 26789012, 'test123', '1986-12-12', 1, 1, NULL, 'LINK017'),
(18, 'Micaela', 'Mendoza', 'micaela.mendoza@gmail.com', 26890123, 'test123', '1989-08-28', 1, 1, NULL, 'LINK018'),
(19, 'Matías', 'Gutiérrez', 'matias.gutierrez@gmail.com', 26901234, 'test123', '1982-02-16', 1, 1, NULL, 'LINK019'),
(20, 'Luz', 'Vargas', 'luz.vargas@gmail.com', 27012345, 'test123', '1997-09-04', 1, 1, NULL, 'LINK020'),
(21, 'Joaquín', 'Herrera', 'joaquin.herrera@gmail.com', 27123456, 'test123', '1980-06-20', 1, 1, NULL, 'LINK021'),
(22, 'Emma', 'Medina', 'emma.medina@gmail.com', 27234567, 'test123', '1992-01-14', 1, 1, NULL, 'LINK022'),
(23, 'Tomás', 'Núñez', 'tomas.nunez@gmail.com', 27345678, 'test123', '1985-10-08', 1, 1, NULL, 'LINK023'),
(24, 'Isabella', 'Rojas', 'isabella.rojas@gmail.com', 27456789, 'test123', '1998-04-22', 1, 1, NULL, 'LINK024'),
(25, 'Benjamín', 'Campos', 'benjamin.campos@gmail.com', 27567890, 'test123', '1983-11-30', 1, 1, NULL, 'LINK025'),
(26, 'Alma', 'Vega', 'alma.vega@gmail.com', 27678901, 'test123', '1991-07-16', 1, 1, NULL, 'LINK026'),
(27, 'Felipe', 'Luna', 'felipe.luna@gmail.com', 27789012, 'test123', '1988-03-02', 1, 1, NULL, 'LINK027'),
(28, 'Delfina', 'Ríos', 'delfina.rios@gmail.com', 27890123, 'test123', '1995-12-18', 1, 1, NULL, 'LINK028'),
(29, 'Lautaro', 'Benítez', 'lautaro.benitez@gmail.com', 27901234, 'test123', '1981-08-24', 1, 1, NULL, 'LINK029'),
(30, 'Olivia', 'Aguilar', 'olivia.aguilar@gmail.com', 28012345, 'test123', '1993-05-10', 1, 1, NULL, 'LINK030'),
(31, 'Thiago', 'Molina', 'thiago.molina@gmail.com', 28123456, 'test123', '1987-01-26', 1, 1, NULL, 'LINK031'),
(32, 'Mía', 'Cabrera', 'mia.cabrera@gmail.com', 28234567, 'test123', '1996-09-12', 1, 1, NULL, 'LINK032'),
(33, 'Bruno', 'Reyes', 'bruno.reyes@gmail.com', 28345678, 'test123', '1984-06-28', 1, 1, NULL, 'LINK033'),
(34, 'Catalina', 'Ramos', 'catalina.ramos@gmail.com', 28456789, 'test123', '1990-02-13', 1, 1, NULL, 'LINK034'),
(35, 'Santino', 'Ponce', 'santino.ponce@gmail.com', 28567890, 'test123', '1982-11-01', 1, 1, NULL, 'LINK035');


-- ==================== RELACIONES MEDICO-PACIENTE ====================
INSERT INTO patient_medic (patient_id, medic_id) VALUES
(1, 1), (1, 12), (2, 7), (2, 4), (3, 17), (4, 21), (4, 13), (5, 25), (6, 29),
(7, 2), (7, 38), (8, 32), (9, 35), (10, 14), (10, 5), (11, 1), (11, 7),
(12, 21), (12, 14), (13, 17), (13, 25), (14, 32), (14, 12), (15, 29), (15, 38),
(16, 4), (16, 13), (17, 35), (17, 46), (18, 2), (18, 21), (19, 43), (19, 12),
(20, 7), (20, 4), (21, 1), (21, 25), (22, 14), (22, 32), (23, 17), (23, 48),
(24, 21), (24, 38), (25, 29), (25, 12), (26, 35), (26, 4), (27, 2), (27, 43),
(28, 7), (28, 14), (29, 41), (29, 12), (30, 21), (30, 32), (31, 1), (31, 17),
(32, 14), (32, 4), (33, 25), (33, 29), (34, 38), (34, 7), (35, 43), (35, 12);


-- ==================== HISTORIAS CLINICAS ====================
INSERT INTO medical_file (history_id, patient_id, weight, height, allergy, blood_type, chronic_disease, actual_medicine, family_medical_history) VALUES
(1, 1, '78', '175', 'Penicilina', 6, 'Hipertensión', 'Losartan 50mg', 'Padre con diabetes tipo 2'),
(2, 2, '62', '165', 'Ninguna conocida', 0, 'Ninguna', 'Ninguno', 'Madre con hipertensión'),
(3, 3, '85', '180', 'Ibuprofeno', 2, 'Diabetes tipo 2', 'Metformina 850mg', 'Abuelo con cáncer de colon'),
(4, 4, '55', '160', 'Mariscos', 0, 'Hipotiroidismo', 'Levotiroxina 50mcg', 'Abuela con artritis'),
(5, 5, '72', '172', 'Ninguna conocida', 6, 'Ninguna', 'Ninguno', 'Sin antecedentes relevantes'),
(6, 6, '58', '163', 'Aspirina', 1, 'Asma', 'Salbutamol inhalador', 'Hermano con asma'),
(7, 7, '90', '185', 'Ninguna conocida', 2, 'Colesterol alto', 'Atorvastatina 20mg', 'Padre con infarto'),
(8, 8, '52', '158', 'Polen', 0, 'Ansiedad', 'Escitalopram 10mg', 'Madre con depresión'),
(9, 9, '68', '170', 'Ninguna conocida', 6, 'Miopía', 'Ninguno', 'Sin antecedentes relevantes'),
(10, 10, '60', '167', 'Lactosa', 4, 'Ninguna', 'Ninguno', 'Padre con hipertensión'),
(11, 11, '95', '182', 'Sulfas', 6, 'Hipertensión, Diabetes', 'Losartan, Metformina', 'Ambos padres diabéticos'),
(12, 12, '48', '155', 'Ninguna conocida', 0, 'Ninguna', 'Anticonceptivos', 'Sin antecedentes'),
(13, 13, '82', '178', 'Penicilina', 2, 'Lumbalgia crónica', 'Pregabalina 75mg', 'Padre con hernia'),
(14, 14, '56', '162', 'Ninguna conocida', 0, 'Migraña', 'Sumatriptán', 'Madre con migrañas'),
(15, 15, '88', '176', 'Mariscos', 6, 'Reflujo gástrico', 'Omeprazol 20mg', 'Padre con úlcera'),
(16, 16, '54', '164', 'Ninguna conocida', 1, 'Dermatitis', 'Crema hidratante', 'Sin antecedentes'),
(17, 17, '70', '173', 'Ácaros', 6, 'Rinitis alérgica', 'Loratadina 10mg', 'Familia con alergias'),
(18, 18, '59', '166', 'Ninguna conocida', 0, 'Ninguna', 'Ninguno', 'Madre con osteoporosis'),
(19, 19, '92', '184', 'Ninguna conocida', 2, 'Sobrepeso', 'Ninguno', 'Padre con infarto'),
(20, 20, '46', '152', 'Ninguna conocida', 0, 'Ninguna', 'Ninguno', 'Sin antecedentes'),
(21, 21, '76', '174', 'Contraste yodado', 6, 'Arritmia', 'Amiodarona', 'Abuelo con marcapasos'),
(22, 22, '58', '165', 'Ninguna conocida', 0, 'Hipotiroidismo', 'Levotiroxina', 'Madre hipotiroidea'),
(23, 23, '80', '177', 'Ninguna conocida', 6, 'Artrosis rodilla', 'Glucosamina', 'Padre con artrosis'),
(24, 24, '50', '158', 'Ninguna conocida', 1, 'Ninguna', 'Anticonceptivos', 'Sin antecedentes'),
(25, 25, '86', '180', 'AINEs', 2, 'Gastritis', 'Pantoprazol', 'Madre con gastritis'),
(26, 26, '55', '163', 'Ninguna conocida', 6, 'Astigmatismo', 'Ninguno', 'Padre con cataratas'),
(27, 27, '78', '175', 'Ninguna conocida', 6, 'Hernia inguinal', 'Ninguno', 'Sin antecedentes'),
(28, 28, '52', '160', 'Ninguna conocida', 0, 'Ninguna', 'Ninguno', 'Sin antecedentes'),
(29, 29, '94', '183', 'Ninguna conocida', 6, 'Próstata aumentada', 'Tamsulosina', 'Padre con próstata'),
(30, 30, '57', '164', 'Ninguna conocida', 0, 'Endometriosis', 'Anticonceptivos', 'Madre con endometriosis');


-- ==================== CONSULTAS MEDICAS ====================
INSERT INTO medical_appointment (medical_appointment_id, patient_id, appointment_reason, current_illness, physical_exam, observations, medic_full_name, specialty, matricula, appointment_day, estado) VALUES
(1, 1, 'Control de hipertensión', 'Presión elevada últimos días', 'PA: 150/95, FC: 78', 'Ajustar medicación', 'Carlos González', 0, 100001, '2024-11-15', 1),
(2, 1, 'Control cardiológico', 'Control rutinario', 'PA: 130/85, FC: 72, ECG normal', 'Continuar tratamiento actual', 'Carlos González', 0, 100001, '2024-10-20', 1),
(3, 2, 'Control pediátrico hijo', 'Fiebre y tos', 'Temperatura 38.2, congestión nasal', 'Cuadro viral, reposo e hidratación', 'Juliana Herrera', 10, 100007, '2024-11-10', 1),
(4, 3, 'Dolor lumbar', 'Dolor intenso zona lumbar', 'Contractura muscular L4-L5', 'Kinesiología y analgésicos', 'Alejandro Vega', 14, 100017, '2024-11-05', 1),
(5, 4, 'Control ginecológico', 'Control anual', 'Examen normal', 'Solicitar PAP y mamografía', 'Patricia Aguilar', 16, 100021, '2024-10-25', 1),
(6, 5, 'Cefaleas frecuentes', 'Dolor de cabeza 3 veces por semana', 'Examen neurológico normal', 'Solicitar RMN cerebro', 'Sebastián Ramírez', 6, 100025, '2024-11-12', 1),
(7, 6, 'Dolor abdominal', 'Acidez y dolor después de comer', 'Abdomen doloroso epigastrio', 'Solicitar endoscopía', 'Federico Silva', 3, 100029, '2024-11-08', 1),
(8, 7, 'Control cardiovascular', 'Dolor en pecho ocasional', 'PA: 140/90, soplo grado I', 'Solicitar ecocardiograma', 'Ana Martínez', 0, 100002, '2024-11-01', 1),
(9, 8, 'Ansiedad', 'Ataques de pánico frecuentes', 'Estado ansioso, sin ideación', 'Iniciar tratamiento ansiolítico', 'Mariela Acosta', 11, 100032, '2024-10-30', 1),
(10, 9, 'Control oftalmológico', 'Visión borrosa', 'Agudeza visual disminuida', 'Actualizar lentes', 'Facundo Ramos', 8, 100035, '2024-11-18', 1),
(11, 10, 'Erupción cutánea', 'Manchas rojas en brazos', 'Dermatitis de contacto', 'Crema corticoide y evitar alérgeno', 'Jorge Pérez', 1, 100005, '2024-11-14', 1),
(12, 11, 'Control diabetes', 'Glucemia descompensada', 'Glucemia: 180 mg/dl', 'Ajustar dosis metformina', 'Sandra Miranda', 20, 100012, '2024-11-20', 1),
(13, 12, 'Control ginecológico', 'Irregularidad menstrual', 'Examen normal', 'Ecografía transvaginal', 'Adriana Molina', 16, 100022, '2024-11-16', 1),
(14, 13, 'Dolor cervical', 'Rigidez cuello hace 2 semanas', 'Contractura cervical', 'RX cervical y kinesiología', 'Jimena Luna', 14, 100018, '2024-11-11', 1),
(15, 14, 'Control psiquiátrico', 'Seguimiento depresión', 'Mejoría anímica', 'Continuar medicación actual', 'Gustavo Paz', 11, 100033, '2024-11-07', 1),
(16, 15, 'Control gastroenterológico', 'Seguimiento gastritis', NULL, NULL, 'Luciana Morales', 3, 100030, '2024-12-10', 0),
(17, 16, 'Consulta dermatológica', 'Acné persistente', NULL, NULL, 'Laura Sánchez', 1, 100004, '2024-12-12', 0),
(18, 17, 'Control oftalmológico', 'Revisión anual', NULL, NULL, 'Antonella Ponce', 8, 100036, '2024-12-15', 0),
(19, 18, 'Control cardiológico', 'Palpitaciones', NULL, NULL, 'Roberto López', 0, 100003, '2024-12-08', 0),
(20, 19, 'Consulta cirugía', 'Evaluación hernia', NULL, NULL, 'Ricardo Quiroga', 18, 100043, '2024-12-20', 0);


-- ==================== MEDICAMENTOS ====================
INSERT INTO medicine (medicine_id, name, description, comments, status, prescription_day) VALUES
(1, 'LOSARTAN', 'Bloqueador receptores angiotensina II', 'Tomar 1 comprimido por día', 'Activo', '2024-11-15'),
(2, 'METFORMINA', 'Antidiabético', 'Tomar con las comidas', 'Activo', '2024-11-20'),
(3, 'OMEPRAZOL', 'Inhibidor bomba protones', 'Tomar en ayunas', 'Activo', '2024-11-08'),
(4, 'ESCITALOPRAM', 'Antidepresivo ISRS', 'Tomar por la mañana', 'Activo', '2024-10-30'),
(5, 'ATORVASTATINA', 'Estatina para colesterol', 'Tomar por la noche', 'Activo', '2024-11-01'),
(6, 'LORATADINA', 'Antihistamínico', 'Tomar si hay síntomas', 'Activo', '2024-11-14'),
(7, 'PREGABALINA', 'Anticonvulsivante/analgésico', 'Tomar cada 12 horas', 'Activo', '2024-11-05'),
(8, 'IBUPROFENO', 'Antiinflamatorio', 'Tomar con comida si hay dolor', 'Finalizado', '2024-11-05'),
(9, 'PARACETAMOL', 'Analgésico/antipirético', 'Cada 8 horas si hay fiebre', 'Finalizado', '2024-11-10'),
(10, 'AMOXICILINA', 'Antibiótico', 'Cada 8 horas por 7 días', 'Finalizado', '2024-11-10'),
(11, 'PANTOPRAZOL', 'Protector gástrico', 'Antes del desayuno', 'Activo', '2024-10-25'),
(12, 'ALPRAZOLAM', 'Ansiolítico', 'Solo si hay crisis de ansiedad', 'Activo', '2024-10-30'),
(13, 'AMLODIPINO', 'Antihipertensivo', 'Una vez al día', 'Activo', '2024-11-15'),
(14, 'GABAPENTINA', 'Anticonvulsivante', 'Antes de dormir', 'Activo', '2024-11-12'),
(15, 'VITAMINA_D', 'Suplemento vitamínico', 'Una vez por semana', 'Activo', '2024-11-01');

INSERT INTO patient_medicine (patient_id, medicine_id) VALUES
(1, 1), (1, 13), (3, 2), (6, 3), (8, 4), (8, 12), (7, 5), (10, 6), 
(13, 7), (13, 8), (2, 9), (2, 10), (4, 11), (5, 14), (11, 1), (11, 2), 
(14, 4), (15, 3), (18, 15);


-- ==================== ANALISIS ====================
INSERT INTO analysis (analysis_id, name, medical_centere, description, status, scheduled_date, scheduled_time, schedule_id) VALUES
(1, 'HEMOGRAMA_COMPLETO', 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 'Control de rutina', 'Completado', '2024-11-10', '08:30:00', NULL),
(2, 'PERFIL_LIPIDICO', 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 'Control colesterol', 'Completado', '2024-11-10', '08:30:00', NULL),
(3, 'ELECTROCARDIOGRAMA', 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 'Control cardíaco', 'Completado', '2024-11-05', '09:00:00', NULL),
(4, 'GLUCEMIA_EN_AYUNAS', 'CENTRO_ESPECIALIDAD_OFFICIA', 'Control diabetes', 'Completado', '2024-11-15', '07:30:00', NULL),
(5, 'ECOGRAFIA_ABDOMINAL', 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 'Evaluación gastritis', 'Completado', '2024-11-12', '10:00:00', NULL),
(6, 'RADIOGRAFIA_DE_TORAX', 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 'Control pulmonar', 'Completado', '2024-11-08', '11:00:00', NULL),
(7, 'RESONANCIA_MAGNETICA', 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 'RMN cerebro por cefaleas', 'Programado', '2024-12-15', '14:00:00', NULL),
(8, 'COLONOSCOPIA', 'CENTRO_ESPECIALIDAD_LUJAN', 'Control preventivo', 'Programado', '2024-12-20', '08:00:00', NULL),
(9, 'MAMOGRAFIA', 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 'Control anual', 'Programado', '2024-12-18', '09:30:00', NULL),
(10, 'HOLTER_CARDIACO', 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 'Monitoreo arritmia', 'Programado', '2024-12-10', '08:00:00', NULL),
(11, 'DENSITOMETRIA_OSEA', NULL, 'Control osteoporosis', 'Pendiente', NULL, NULL, NULL),
(12, 'ANALISIS_DE_TIROIDES', NULL, 'Control función tiroidea', 'Pendiente', NULL, NULL, NULL),
(13, 'PAPANICOLAOU', NULL, 'Control anual', 'Pendiente', NULL, NULL, NULL),
(14, 'TOMOGRAFIA_COMPUTARIZADA', NULL, 'Evaluación lumbar', 'Pendiente', NULL, NULL, NULL),
(15, 'ANALISIS_DE_ORINA', NULL, 'Control infección urinaria', 'Pendiente', NULL, NULL, NULL);

INSERT INTO patient_analysis (patient_id, analysis_id) VALUES
(1, 1), (1, 2), (1, 3), (3, 4), (6, 5), (7, 3), (7, 10), (5, 7), (15, 8),
(4, 9), (4, 13), (21, 10), (18, 11), (22, 12), (12, 13), (13, 14), (30, 15);

