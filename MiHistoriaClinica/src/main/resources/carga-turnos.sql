-- ============================================
-- Script para insertar turnos de médicos
-- ============================================


-- ============================================
-- MÉDICO ID 1 - Ejemplo: Dr. Juan Pérez (CARDIOLOGIA = 0)
-- Centro: SEDE_PRINCIPAL_HOSPITAL_AUSTRAL
-- ============================================
INSERT INTO turnos (available, fecha_turno, hora_turno, medic_full_name, medic_specialty, medical_center, medic_id) VALUES
                                                                                                                        (true, '2025-12-09', '08:00:00', 'Juan Pérez', 0, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 1),
                                                                                                                        (true, '2025-12-09', '08:35:00', 'Juan Pérez', 0, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 1),
                                                                                                                        (true, '2025-12-09', '09:10:00', 'Juan Pérez', 0, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 1),
                                                                                                                        (true, '2025-12-09', '09:45:00', 'Juan Pérez', 0, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 1),
                                                                                                                        (true, '2025-12-09', '10:20:00', 'Juan Pérez', 0, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 1),
                                                                                                                        (true, '2025-12-09', '10:55:00', 'Juan Pérez', 0, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 1),
                                                                                                                        (true, '2025-12-09', '11:30:00', 'Juan Pérez', 0, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 1),
                                                                                                                        (true, '2025-12-09', '12:05:00', 'Juan Pérez', 0, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 1),
                                                                                                                        (true, '2025-12-12', '08:00:00', 'Juan Pérez', 0, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 1),
                                                                                                                        (true, '2025-12-12', '08:35:00', 'Juan Pérez', 0, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 1),
                                                                                                                        (true, '2025-12-12', '09:10:00', 'Juan Pérez', 0, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 1),
                                                                                                                        (true, '2025-12-12', '09:45:00', 'Juan Pérez', 0, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 1),
                                                                                                                        (true, '2025-12-12', '10:20:00', 'Juan Pérez', 0, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 1),
                                                                                                                        (true, '2025-12-12', '10:55:00', 'Juan Pérez', 0, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 1),
                                                                                                                        (true, '2025-12-12', '11:30:00', 'Juan Pérez', 0, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 1),
                                                                                                                        (true, '2025-12-12', '12:05:00', 'Juan Pérez', 0, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 1),
                                                                                                                        (true, '2025-12-16', '08:00:00', 'Juan Pérez', 0, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 1),
                                                                                                                        (true, '2025-12-16', '08:35:00', 'Juan Pérez', 0, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 1),
                                                                                                                        (true, '2025-12-16', '09:10:00', 'Juan Pérez', 0, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 1),
                                                                                                                        (true, '2025-12-16', '09:45:00', 'Juan Pérez', 0, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 1),
                                                                                                                        (true, '2025-12-16', '10:20:00', 'Juan Pérez', 0, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 1),
                                                                                                                        (true, '2025-12-16', '10:55:00', 'Juan Pérez', 0, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 1),
                                                                                                                        (true, '2025-12-16', '11:30:00', 'Juan Pérez', 0, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 1),
                                                                                                                        (true, '2025-12-16', '12:05:00', 'Juan Pérez', 0, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 1),
                                                                                                                        (true, '2025-12-19', '08:00:00', 'Juan Pérez', 0, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 1),
                                                                                                                        (true, '2025-12-19', '08:35:00', 'Juan Pérez', 0, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 1),
                                                                                                                        (true, '2025-12-19', '09:10:00', 'Juan Pérez', 0, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 1),
                                                                                                                        (true, '2025-12-19', '09:45:00', 'Juan Pérez', 0, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 1),
                                                                                                                        (true, '2025-12-19', '10:20:00', 'Juan Pérez', 0, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 1),
                                                                                                                        (true, '2025-12-19', '10:55:00', 'Juan Pérez', 0, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 1),
                                                                                                                        (true, '2025-12-19', '11:30:00', 'Juan Pérez', 0, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 1),
                                                                                                                        (true, '2025-12-19', '12:05:00', 'Juan Pérez', 0, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 1);

-- ============================================
-- MÉDICO ID 2 - Ejemplo: Dra. María García (DERMATOLOGIA = 1)
-- Centro: CENTRO_ESPECIALIDAD_OFFICIA
-- ============================================
INSERT INTO turnos (available, fecha_turno, hora_turno, medic_full_name, medic_specialty, medical_center, medic_id) VALUES
                                                                                                                        (true, '2025-12-10', '08:00:00', 'María García', 1, 'CENTRO_ESPECIALIDAD_OFFICIA', 2),
                                                                                                                        (true, '2025-12-10', '08:35:00', 'María García', 1, 'CENTRO_ESPECIALIDAD_OFFICIA', 2),
                                                                                                                        (true, '2025-12-10', '09:10:00', 'María García', 1, 'CENTRO_ESPECIALIDAD_OFFICIA', 2),
                                                                                                                        (true, '2025-12-10', '09:45:00', 'María García', 1, 'CENTRO_ESPECIALIDAD_OFFICIA', 2),
                                                                                                                        (true, '2025-12-10', '10:20:00', 'María García', 1, 'CENTRO_ESPECIALIDAD_OFFICIA', 2),
                                                                                                                        (true, '2025-12-10', '10:55:00', 'María García', 1, 'CENTRO_ESPECIALIDAD_OFFICIA', 2),
                                                                                                                        (true, '2025-12-10', '11:30:00', 'María García', 1, 'CENTRO_ESPECIALIDAD_OFFICIA', 2),
                                                                                                                        (true, '2025-12-10', '12:05:00', 'María García', 1, 'CENTRO_ESPECIALIDAD_OFFICIA', 2),
                                                                                                                        (true, '2025-12-13', '08:00:00', 'María García', 1, 'CENTRO_ESPECIALIDAD_OFFICIA', 2),
                                                                                                                        (true, '2025-12-13', '08:35:00', 'María García', 1, 'CENTRO_ESPECIALIDAD_OFFICIA', 2),
                                                                                                                        (true, '2025-12-13', '09:10:00', 'María García', 1, 'CENTRO_ESPECIALIDAD_OFFICIA', 2),
                                                                                                                        (true, '2025-12-13', '09:45:00', 'María García', 1, 'CENTRO_ESPECIALIDAD_OFFICIA', 2),
                                                                                                                        (true, '2025-12-13', '10:20:00', 'María García', 1, 'CENTRO_ESPECIALIDAD_OFFICIA', 2),
                                                                                                                        (true, '2025-12-13', '10:55:00', 'María García', 1, 'CENTRO_ESPECIALIDAD_OFFICIA', 2),
                                                                                                                        (true, '2025-12-13', '11:30:00', 'María García', 1, 'CENTRO_ESPECIALIDAD_OFFICIA', 2),
                                                                                                                        (true, '2025-12-13', '12:05:00', 'María García', 1, 'CENTRO_ESPECIALIDAD_OFFICIA', 2),
                                                                                                                        (true, '2025-12-17', '08:00:00', 'María García', 1, 'CENTRO_ESPECIALIDAD_OFFICIA', 2),
                                                                                                                        (true, '2025-12-17', '08:35:00', 'María García', 1, 'CENTRO_ESPECIALIDAD_OFFICIA', 2),
                                                                                                                        (true, '2025-12-17', '09:10:00', 'María García', 1, 'CENTRO_ESPECIALIDAD_OFFICIA', 2),
                                                                                                                        (true, '2025-12-17', '09:45:00', 'María García', 1, 'CENTRO_ESPECIALIDAD_OFFICIA', 2),
                                                                                                                        (true, '2025-12-17', '10:20:00', 'María García', 1, 'CENTRO_ESPECIALIDAD_OFFICIA', 2),
                                                                                                                        (true, '2025-12-17', '10:55:00', 'María García', 1, 'CENTRO_ESPECIALIDAD_OFFICIA', 2),
                                                                                                                        (true, '2025-12-17', '11:30:00', 'María García', 1, 'CENTRO_ESPECIALIDAD_OFFICIA', 2),
                                                                                                                        (true, '2025-12-17', '12:05:00', 'María García', 1, 'CENTRO_ESPECIALIDAD_OFFICIA', 2),
                                                                                                                        (true, '2025-12-20', '08:00:00', 'María García', 1, 'CENTRO_ESPECIALIDAD_OFFICIA', 2),
                                                                                                                        (true, '2025-12-20', '08:35:00', 'María García', 1, 'CENTRO_ESPECIALIDAD_OFFICIA', 2),
                                                                                                                        (true, '2025-12-20', '09:10:00', 'María García', 1, 'CENTRO_ESPECIALIDAD_OFFICIA', 2),
                                                                                                                        (true, '2025-12-20', '09:45:00', 'María García', 1, 'CENTRO_ESPECIALIDAD_OFFICIA', 2),
                                                                                                                        (true, '2025-12-20', '10:20:00', 'María García', 1, 'CENTRO_ESPECIALIDAD_OFFICIA', 2),
                                                                                                                        (true, '2025-12-20', '10:55:00', 'María García', 1, 'CENTRO_ESPECIALIDAD_OFFICIA', 2),
                                                                                                                        (true, '2025-12-20', '11:30:00', 'María García', 1, 'CENTRO_ESPECIALIDAD_OFFICIA', 2),
                                                                                                                        (true, '2025-12-20', '12:05:00', 'María García', 1, 'CENTRO_ESPECIALIDAD_OFFICIA', 2);

-- ============================================
-- MÉDICO ID 3 - Ejemplo: Dr. Carlos López (GASTROENTEROLOGIA = 3)
-- Centro: CENTRO_ESPECIALIDAD_LUJAN
-- ============================================
INSERT INTO turnos (available, fecha_turno, hora_turno, medic_full_name, medic_specialty, medical_center, medic_id) VALUES
                                                                                                                        (true, '2025-12-11', '08:00:00', 'Carlos López', 3, 'CENTRO_ESPECIALIDAD_LUJAN', 3),
                                                                                                                        (true, '2025-12-11', '08:35:00', 'Carlos López', 3, 'CENTRO_ESPECIALIDAD_LUJAN', 3),
                                                                                                                        (true, '2025-12-11', '09:10:00', 'Carlos López', 3, 'CENTRO_ESPECIALIDAD_LUJAN', 3),
                                                                                                                        (true, '2025-12-11', '09:45:00', 'Carlos López', 3, 'CENTRO_ESPECIALIDAD_LUJAN', 3),
                                                                                                                        (true, '2025-12-11', '10:20:00', 'Carlos López', 3, 'CENTRO_ESPECIALIDAD_LUJAN', 3),
                                                                                                                        (true, '2025-12-11', '10:55:00', 'Carlos López', 3, 'CENTRO_ESPECIALIDAD_LUJAN', 3),
                                                                                                                        (true, '2025-12-11', '11:30:00', 'Carlos López', 3, 'CENTRO_ESPECIALIDAD_LUJAN', 3),
                                                                                                                        (true, '2025-12-11', '12:05:00', 'Carlos López', 3, 'CENTRO_ESPECIALIDAD_LUJAN', 3),
                                                                                                                        (true, '2025-12-18', '08:00:00', 'Carlos López', 3, 'CENTRO_ESPECIALIDAD_LUJAN', 3),
                                                                                                                        (true, '2025-12-18', '08:35:00', 'Carlos López', 3, 'CENTRO_ESPECIALIDAD_LUJAN', 3),
                                                                                                                        (true, '2025-12-18', '09:10:00', 'Carlos López', 3, 'CENTRO_ESPECIALIDAD_LUJAN', 3),
                                                                                                                        (true, '2025-12-18', '09:45:00', 'Carlos López', 3, 'CENTRO_ESPECIALIDAD_LUJAN', 3),
                                                                                                                        (true, '2025-12-18', '10:20:00', 'Carlos López', 3, 'CENTRO_ESPECIALIDAD_LUJAN', 3),
                                                                                                                        (true, '2025-12-18', '10:55:00', 'Carlos López', 3, 'CENTRO_ESPECIALIDAD_LUJAN', 3),
                                                                                                                        (true, '2025-12-18', '11:30:00', 'Carlos López', 3, 'CENTRO_ESPECIALIDAD_LUJAN', 3),
                                                                                                                        (true, '2025-12-18', '12:05:00', 'Carlos López', 3, 'CENTRO_ESPECIALIDAD_LUJAN', 3),
                                                                                                                        (true, '2026-01-08', '08:00:00', 'Carlos López', 3, 'CENTRO_ESPECIALIDAD_LUJAN', 3),
                                                                                                                        (true, '2026-01-08', '08:35:00', 'Carlos López', 3, 'CENTRO_ESPECIALIDAD_LUJAN', 3),
                                                                                                                        (true, '2026-01-08', '09:10:00', 'Carlos López', 3, 'CENTRO_ESPECIALIDAD_LUJAN', 3),
                                                                                                                        (true, '2026-01-08', '09:45:00', 'Carlos López', 3, 'CENTRO_ESPECIALIDAD_LUJAN', 3),
                                                                                                                        (true, '2026-01-08', '10:20:00', 'Carlos López', 3, 'CENTRO_ESPECIALIDAD_LUJAN', 3),
                                                                                                                        (true, '2026-01-08', '10:55:00', 'Carlos López', 3, 'CENTRO_ESPECIALIDAD_LUJAN', 3),
                                                                                                                        (true, '2026-01-08', '11:30:00', 'Carlos López', 3, 'CENTRO_ESPECIALIDAD_LUJAN', 3),
                                                                                                                        (true, '2026-01-08', '12:05:00', 'Carlos López', 3, 'CENTRO_ESPECIALIDAD_LUJAN', 3);

-- ============================================
-- MÉDICO ID 4 - Ejemplo: Dra. Ana Martínez (NEUROLOGIA = 6)
-- Centro: SEDE_PRINCIPAL_HOSPITAL_AUSTRAL
-- ============================================
INSERT INTO turnos (available, fecha_turno, hora_turno, medic_full_name, medic_specialty, medical_center, medic_id) VALUES
                                                                                                                        (true, '2025-12-09', '14:00:00', 'Ana Martínez', 6, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 4),
                                                                                                                        (true, '2025-12-09', '14:35:00', 'Ana Martínez', 6, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 4),
                                                                                                                        (true, '2025-12-09', '15:10:00', 'Ana Martínez', 6, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 4),
                                                                                                                        (true, '2025-12-09', '15:45:00', 'Ana Martínez', 6, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 4),
                                                                                                                        (true, '2025-12-09', '16:20:00', 'Ana Martínez', 6, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 4),
                                                                                                                        (true, '2025-12-09', '16:55:00', 'Ana Martínez', 6, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 4),
                                                                                                                        (true, '2025-12-09', '17:30:00', 'Ana Martínez', 6, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 4),
                                                                                                                        (true, '2025-12-09', '18:05:00', 'Ana Martínez', 6, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 4),
                                                                                                                        (true, '2025-12-12', '14:00:00', 'Ana Martínez', 6, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 4),
                                                                                                                        (true, '2025-12-12', '14:35:00', 'Ana Martínez', 6, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 4),
                                                                                                                        (true, '2025-12-12', '15:10:00', 'Ana Martínez', 6, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 4),
                                                                                                                        (true, '2025-12-12', '15:45:00', 'Ana Martínez', 6, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 4),
                                                                                                                        (true, '2025-12-12', '16:20:00', 'Ana Martínez', 6, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 4),
                                                                                                                        (true, '2025-12-12', '16:55:00', 'Ana Martínez', 6, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 4),
                                                                                                                        (true, '2025-12-12', '17:30:00', 'Ana Martínez', 6, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 4),
                                                                                                                        (true, '2025-12-12', '18:05:00', 'Ana Martínez', 6, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 4),
                                                                                                                        (true, '2025-12-16', '14:00:00', 'Ana Martínez', 6, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 4),
                                                                                                                        (true, '2025-12-16', '14:35:00', 'Ana Martínez', 6, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 4),
                                                                                                                        (true, '2025-12-16', '15:10:00', 'Ana Martínez', 6, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 4),
                                                                                                                        (true, '2025-12-16', '15:45:00', 'Ana Martínez', 6, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 4),
                                                                                                                        (true, '2025-12-16', '16:20:00', 'Ana Martínez', 6, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 4),
                                                                                                                        (true, '2025-12-16', '16:55:00', 'Ana Martínez', 6, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 4),
                                                                                                                        (true, '2025-12-16', '17:30:00', 'Ana Martínez', 6, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 4),
                                                                                                                        (true, '2025-12-16', '18:05:00', 'Ana Martínez', 6, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 4);

-- ============================================
-- MÉDICO ID 5 - Ejemplo: Dr. Roberto Sánchez (PEDIATRIA = 10)
-- Centro: CENTRO_ESPECIALIDAD_CHAMPAGNAT
-- ============================================
INSERT INTO turnos (available, fecha_turno, hora_turno, medic_full_name, medic_specialty, medical_center, medic_id) VALUES
                                                                                                                        (true, '2025-12-10', '08:00:00', 'Roberto Sánchez', 10, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 5),
                                                                                                                        (true, '2025-12-10', '08:35:00', 'Roberto Sánchez', 10, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 5),
                                                                                                                        (true, '2025-12-10', '09:10:00', 'Roberto Sánchez', 10, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 5),
                                                                                                                        (true, '2025-12-10', '09:45:00', 'Roberto Sánchez', 10, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 5),
                                                                                                                        (true, '2025-12-10', '10:20:00', 'Roberto Sánchez', 10, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 5),
                                                                                                                        (true, '2025-12-10', '10:55:00', 'Roberto Sánchez', 10, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 5),
                                                                                                                        (true, '2025-12-10', '11:30:00', 'Roberto Sánchez', 10, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 5),
                                                                                                                        (true, '2025-12-10', '12:05:00', 'Roberto Sánchez', 10, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 5),
                                                                                                                        (true, '2025-12-17', '08:00:00', 'Roberto Sánchez', 10, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 5),
                                                                                                                        (true, '2025-12-17', '08:35:00', 'Roberto Sánchez', 10, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 5),
                                                                                                                        (true, '2025-12-17', '09:10:00', 'Roberto Sánchez', 10, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 5),
                                                                                                                        (true, '2025-12-17', '09:45:00', 'Roberto Sánchez', 10, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 5),
                                                                                                                        (true, '2025-12-17', '10:20:00', 'Roberto Sánchez', 10, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 5),
                                                                                                                        (true, '2025-12-17', '10:55:00', 'Roberto Sánchez', 10, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 5),
                                                                                                                        (true, '2025-12-17', '11:30:00', 'Roberto Sánchez', 10, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 5),
                                                                                                                        (true, '2025-12-17', '12:05:00', 'Roberto Sánchez', 10, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 5),
                                                                                                                        (true, '2026-01-07', '08:00:00', 'Roberto Sánchez', 10, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 5),
                                                                                                                        (true, '2026-01-07', '08:35:00', 'Roberto Sánchez', 10, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 5),
                                                                                                                        (true, '2026-01-07', '09:10:00', 'Roberto Sánchez', 10, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 5),
                                                                                                                        (true, '2026-01-07', '09:45:00', 'Roberto Sánchez', 10, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 5),
                                                                                                                        (true, '2026-01-07', '10:20:00', 'Roberto Sánchez', 10, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 5),
                                                                                                                        (true, '2026-01-07', '10:55:00', 'Roberto Sánchez', 10, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 5),
                                                                                                                        (true, '2026-01-07', '11:30:00', 'Roberto Sánchez', 10, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 5),
                                                                                                                        (true, '2026-01-07', '12:05:00', 'Roberto Sánchez', 10, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 5);

-- ============================================
-- MÉDICO ID 6 - Ejemplo: Dra. Laura Rodríguez (GINECOLOGIA = 16)
-- Centro: CENTRO_ESPECIALIDAD_OFFICIA
-- ============================================
INSERT INTO turnos (available, fecha_turno, hora_turno, medic_full_name, medic_specialty, medical_center, medic_id) VALUES
                                                                                                                        (true, '2025-12-11', '14:00:00', 'Laura Rodríguez', 16, 'CENTRO_ESPECIALIDAD_OFFICIA', 6),
                                                                                                                        (true, '2025-12-11', '14:35:00', 'Laura Rodríguez', 16, 'CENTRO_ESPECIALIDAD_OFFICIA', 6),
                                                                                                                        (true, '2025-12-11', '15:10:00', 'Laura Rodríguez', 16, 'CENTRO_ESPECIALIDAD_OFFICIA', 6),
                                                                                                                        (true, '2025-12-11', '15:45:00', 'Laura Rodríguez', 16, 'CENTRO_ESPECIALIDAD_OFFICIA', 6),
                                                                                                                        (true, '2025-12-11', '16:20:00', 'Laura Rodríguez', 16, 'CENTRO_ESPECIALIDAD_OFFICIA', 6),
                                                                                                                        (true, '2025-12-11', '16:55:00', 'Laura Rodríguez', 16, 'CENTRO_ESPECIALIDAD_OFFICIA', 6),
                                                                                                                        (true, '2025-12-11', '17:30:00', 'Laura Rodríguez', 16, 'CENTRO_ESPECIALIDAD_OFFICIA', 6),
                                                                                                                        (true, '2025-12-11', '18:05:00', 'Laura Rodríguez', 16, 'CENTRO_ESPECIALIDAD_OFFICIA', 6),
                                                                                                                        (true, '2025-12-18', '14:00:00', 'Laura Rodríguez', 16, 'CENTRO_ESPECIALIDAD_OFFICIA', 6),
                                                                                                                        (true, '2025-12-18', '14:35:00', 'Laura Rodríguez', 16, 'CENTRO_ESPECIALIDAD_OFFICIA', 6),
                                                                                                                        (true, '2025-12-18', '15:10:00', 'Laura Rodríguez', 16, 'CENTRO_ESPECIALIDAD_OFFICIA', 6),
                                                                                                                        (true, '2025-12-18', '15:45:00', 'Laura Rodríguez', 16, 'CENTRO_ESPECIALIDAD_OFFICIA', 6),
                                                                                                                        (true, '2025-12-18', '16:20:00', 'Laura Rodríguez', 16, 'CENTRO_ESPECIALIDAD_OFFICIA', 6),
                                                                                                                        (true, '2025-12-18', '16:55:00', 'Laura Rodríguez', 16, 'CENTRO_ESPECIALIDAD_OFFICIA', 6),
                                                                                                                        (true, '2025-12-18', '17:30:00', 'Laura Rodríguez', 16, 'CENTRO_ESPECIALIDAD_OFFICIA', 6),
                                                                                                                        (true, '2025-12-18', '18:05:00', 'Laura Rodríguez', 16, 'CENTRO_ESPECIALIDAD_OFFICIA', 6),
                                                                                                                        (true, '2026-01-09', '14:00:00', 'Laura Rodríguez', 16, 'CENTRO_ESPECIALIDAD_OFFICIA', 6),
                                                                                                                        (true, '2026-01-09', '14:35:00', 'Laura Rodríguez', 16, 'CENTRO_ESPECIALIDAD_OFFICIA', 6),
                                                                                                                        (true, '2026-01-09', '15:10:00', 'Laura Rodríguez', 16, 'CENTRO_ESPECIALIDAD_OFFICIA', 6),
                                                                                                                        (true, '2026-01-09', '15:45:00', 'Laura Rodríguez', 16, 'CENTRO_ESPECIALIDAD_OFFICIA', 6),
                                                                                                                        (true, '2026-01-09', '16:20:00', 'Laura Rodríguez', 16, 'CENTRO_ESPECIALIDAD_OFFICIA', 6),
                                                                                                                        (true, '2026-01-09', '16:55:00', 'Laura Rodríguez', 16, 'CENTRO_ESPECIALIDAD_OFFICIA', 6),
                                                                                                                        (true, '2026-01-09', '17:30:00', 'Laura Rodríguez', 16, 'CENTRO_ESPECIALIDAD_OFFICIA', 6),
                                                                                                                        (true, '2026-01-09', '18:05:00', 'Laura Rodríguez', 16, 'CENTRO_ESPECIALIDAD_OFFICIA', 6);

-- ============================================
-- MÉDICO ID 7 - Ejemplo: Dr. Fernando Torres (TRAUMATOLOGIA = 14)
-- Centro: SEDE_PRINCIPAL_HOSPITAL_AUSTRAL
-- ============================================
INSERT INTO turnos (available, fecha_turno, hora_turno, medic_full_name, medic_specialty, medical_center, medic_id) VALUES
                                                                                                                        (true, '2025-12-10', '08:00:00', 'Fernando Torres', 14, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 7),
                                                                                                                        (true, '2025-12-10', '08:35:00', 'Fernando Torres', 14, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 7),
                                                                                                                        (true, '2025-12-10', '09:10:00', 'Fernando Torres', 14, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 7),
                                                                                                                        (true, '2025-12-10', '09:45:00', 'Fernando Torres', 14, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 7),
                                                                                                                        (true, '2025-12-10', '10:20:00', 'Fernando Torres', 14, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 7),
                                                                                                                        (true, '2025-12-10', '10:55:00', 'Fernando Torres', 14, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 7),
                                                                                                                        (true, '2025-12-10', '11:30:00', 'Fernando Torres', 14, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 7),
                                                                                                                        (true, '2025-12-10', '12:05:00', 'Fernando Torres', 14, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 7),
                                                                                                                        (true, '2025-12-13', '08:00:00', 'Fernando Torres', 14, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 7),
                                                                                                                        (true, '2025-12-13', '08:35:00', 'Fernando Torres', 14, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 7),
                                                                                                                        (true, '2025-12-13', '09:10:00', 'Fernando Torres', 14, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 7),
                                                                                                                        (true, '2025-12-13', '09:45:00', 'Fernando Torres', 14, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 7),
                                                                                                                        (true, '2025-12-13', '10:20:00', 'Fernando Torres', 14, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 7),
                                                                                                                        (true, '2025-12-13', '10:55:00', 'Fernando Torres', 14, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 7),
                                                                                                                        (true, '2025-12-13', '11:30:00', 'Fernando Torres', 14, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 7),
                                                                                                                        (true, '2025-12-13', '12:05:00', 'Fernando Torres', 14, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 7),
                                                                                                                        (true, '2025-12-17', '08:00:00', 'Fernando Torres', 14, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 7),
                                                                                                                        (true, '2025-12-17', '08:35:00', 'Fernando Torres', 14, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 7),
                                                                                                                        (true, '2025-12-17', '09:10:00', 'Fernando Torres', 14, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 7),
                                                                                                                        (true, '2025-12-17', '09:45:00', 'Fernando Torres', 14, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 7),
                                                                                                                        (true, '2025-12-17', '10:20:00', 'Fernando Torres', 14, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 7),
                                                                                                                        (true, '2025-12-17', '10:55:00', 'Fernando Torres', 14, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 7),
                                                                                                                        (true, '2025-12-17', '11:30:00', 'Fernando Torres', 14, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 7),
                                                                                                                        (true, '2025-12-17', '12:05:00', 'Fernando Torres', 14, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 7),
                                                                                                                        (true, '2025-12-20', '08:00:00', 'Fernando Torres', 14, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 7),
                                                                                                                        (true, '2025-12-20', '08:35:00', 'Fernando Torres', 14, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 7),
                                                                                                                        (true, '2025-12-20', '09:10:00', 'Fernando Torres', 14, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 7),
                                                                                                                        (true, '2025-12-20', '09:45:00', 'Fernando Torres', 14, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 7),
                                                                                                                        (true, '2025-12-20', '10:20:00', 'Fernando Torres', 14, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 7),
                                                                                                                        (true, '2025-12-20', '10:55:00', 'Fernando Torres', 14, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 7),
                                                                                                                        (true, '2025-12-20', '11:30:00', 'Fernando Torres', 14, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 7),
                                                                                                                        (true, '2025-12-20', '12:05:00', 'Fernando Torres', 14, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 7);

-- ============================================
-- MÉDICO ID 8 - Claudio Figueroa (MEDICINA_CLINICA = 20)
-- Centro: CENTRO_ESPECIALIDAD_CHAMPAGNAT
-- ============================================
INSERT INTO turnos (available, fecha_turno, hora_turno, medic_full_name, medic_specialty, medical_center, medic_id) VALUES
                                                                                                                        (true, '2025-12-09', '08:00:00', 'Claudio Figueroa', 20, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 8),
                                                                                                                        (true, '2025-12-09', '08:35:00', 'Claudio Figueroa', 20, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 8),
                                                                                                                        (true, '2025-12-09', '09:10:00', 'Claudio Figueroa', 20, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 8),
                                                                                                                        (true, '2025-12-09', '09:45:00', 'Claudio Figueroa', 20, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 8),
                                                                                                                        (true, '2025-12-09', '10:20:00', 'Claudio Figueroa', 20, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 8),
                                                                                                                        (true, '2025-12-09', '10:55:00', 'Claudio Figueroa', 20, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 8),
                                                                                                                        (true, '2025-12-09', '11:30:00', 'Claudio Figueroa', 20, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 8),
                                                                                                                        (true, '2025-12-09', '12:05:00', 'Claudio Figueroa', 20, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 8),
                                                                                                                        (true, '2025-12-12', '08:00:00', 'Claudio Figueroa', 20, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 8),
                                                                                                                        (true, '2025-12-12', '08:35:00', 'Claudio Figueroa', 20, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 8),
                                                                                                                        (true, '2025-12-12', '09:10:00', 'Claudio Figueroa', 20, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 8),
                                                                                                                        (true, '2025-12-12', '09:45:00', 'Claudio Figueroa', 20, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 8),
                                                                                                                        (true, '2025-12-12', '10:20:00', 'Claudio Figueroa', 20, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 8),
                                                                                                                        (true, '2025-12-12', '10:55:00', 'Claudio Figueroa', 20, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 8),
                                                                                                                        (true, '2025-12-12', '11:30:00', 'Claudio Figueroa', 20, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 8),
                                                                                                                        (true, '2025-12-12', '12:05:00', 'Claudio Figueroa', 20, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 8),
                                                                                                                        (true, '2025-12-16', '08:00:00', 'Claudio Figueroa', 20, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 8),
                                                                                                                        (true, '2025-12-16', '08:35:00', 'Claudio Figueroa', 20, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 8),
                                                                                                                        (true, '2025-12-16', '09:10:00', 'Claudio Figueroa', 20, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 8),
                                                                                                                        (true, '2025-12-16', '09:45:00', 'Claudio Figueroa', 20, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 8),
                                                                                                                        (true, '2025-12-16', '10:20:00', 'Claudio Figueroa', 20, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 8),
                                                                                                                        (true, '2025-12-16', '10:55:00', 'Claudio Figueroa', 20, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 8),
                                                                                                                        (true, '2025-12-16', '11:30:00', 'Claudio Figueroa', 20, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 8),
                                                                                                                        (true, '2025-12-16', '12:05:00', 'Claudio Figueroa', 20, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 8),
                                                                                                                        (true, '2025-12-19', '08:00:00', 'Claudio Figueroa', 20, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 8),
                                                                                                                        (true, '2025-12-19', '08:35:00', 'Claudio Figueroa', 20, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 8),
                                                                                                                        (true, '2025-12-19', '09:10:00', 'Claudio Figueroa', 20, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 8),
                                                                                                                        (true, '2025-12-19', '09:45:00', 'Claudio Figueroa', 20, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 8),
                                                                                                                        (true, '2025-12-19', '10:20:00', 'Claudio Figueroa', 20, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 8),
                                                                                                                        (true, '2025-12-19', '10:55:00', 'Claudio Figueroa', 20, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 8),
                                                                                                                        (true, '2025-12-19', '11:30:00', 'Claudio Figueroa', 20, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 8),
                                                                                                                        (true, '2025-12-19', '12:05:00', 'Claudio Figueroa', 20, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 8),
                                                                                                                        (true, '2026-01-06', '08:00:00', 'Claudio Figueroa', 20, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 8),
                                                                                                                        (true, '2026-01-06', '08:35:00', 'Claudio Figueroa', 20, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 8),
                                                                                                                        (true, '2026-01-06', '09:10:00', 'Claudio Figueroa', 20, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 8),
                                                                                                                        (true, '2026-01-06', '09:45:00', 'Claudio Figueroa', 20, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 8),
                                                                                                                        (true, '2026-01-06', '10:20:00', 'Claudio Figueroa', 20, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 8),
                                                                                                                        (true, '2026-01-06', '10:55:00', 'Claudio Figueroa', 20, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 8),
                                                                                                                        (true, '2026-01-06', '11:30:00', 'Claudio Figueroa', 20, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 8),
                                                                                                                        (true, '2026-01-06', '12:05:00', 'Claudio Figueroa', 20, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 8);

-- ============================================
-- MÉDICO ID 9 - Ejemplo: Dra. Valeria Ruiz (OFTALMOLOGIA = 8)
-- Centro: CENTRO_ESPECIALIDAD_LUJAN
-- ============================================
INSERT INTO turnos (available, fecha_turno, hora_turno, medic_full_name, medic_specialty, medical_center, medic_id) VALUES
                                                                                                                        (true, '2025-12-09', '14:00:00', 'Valeria Ruiz', 8, 'CENTRO_ESPECIALIDAD_LUJAN', 9),
                                                                                                                        (true, '2025-12-09', '14:35:00', 'Valeria Ruiz', 8, 'CENTRO_ESPECIALIDAD_LUJAN', 9),
                                                                                                                        (true, '2025-12-09', '15:10:00', 'Valeria Ruiz', 8, 'CENTRO_ESPECIALIDAD_LUJAN', 9),
                                                                                                                        (true, '2025-12-09', '15:45:00', 'Valeria Ruiz', 8, 'CENTRO_ESPECIALIDAD_LUJAN', 9),
                                                                                                                        (true, '2025-12-09', '16:20:00', 'Valeria Ruiz', 8, 'CENTRO_ESPECIALIDAD_LUJAN', 9),
                                                                                                                        (true, '2025-12-09', '16:55:00', 'Valeria Ruiz', 8, 'CENTRO_ESPECIALIDAD_LUJAN', 9),
                                                                                                                        (true, '2025-12-09', '17:30:00', 'Valeria Ruiz', 8, 'CENTRO_ESPECIALIDAD_LUJAN', 9),
                                                                                                                        (true, '2025-12-09', '18:05:00', 'Valeria Ruiz', 8, 'CENTRO_ESPECIALIDAD_LUJAN', 9),
                                                                                                                        (true, '2025-12-16', '14:00:00', 'Valeria Ruiz', 8, 'CENTRO_ESPECIALIDAD_LUJAN', 9),
                                                                                                                        (true, '2025-12-16', '14:35:00', 'Valeria Ruiz', 8, 'CENTRO_ESPECIALIDAD_LUJAN', 9),
                                                                                                                        (true, '2025-12-16', '15:10:00', 'Valeria Ruiz', 8, 'CENTRO_ESPECIALIDAD_LUJAN', 9),
                                                                                                                        (true, '2025-12-16', '15:45:00', 'Valeria Ruiz', 8, 'CENTRO_ESPECIALIDAD_LUJAN', 9),
                                                                                                                        (true, '2025-12-16', '16:20:00', 'Valeria Ruiz', 8, 'CENTRO_ESPECIALIDAD_LUJAN', 9),
                                                                                                                        (true, '2025-12-16', '16:55:00', 'Valeria Ruiz', 8, 'CENTRO_ESPECIALIDAD_LUJAN', 9),
                                                                                                                        (true, '2025-12-16', '17:30:00', 'Valeria Ruiz', 8, 'CENTRO_ESPECIALIDAD_LUJAN', 9),
                                                                                                                        (true, '2025-12-16', '18:05:00', 'Valeria Ruiz', 8, 'CENTRO_ESPECIALIDAD_LUJAN', 9),
                                                                                                                        (true, '2026-01-06', '14:00:00', 'Valeria Ruiz', 8, 'CENTRO_ESPECIALIDAD_LUJAN', 9),
                                                                                                                        (true, '2026-01-06', '14:35:00', 'Valeria Ruiz', 8, 'CENTRO_ESPECIALIDAD_LUJAN', 9),
                                                                                                                        (true, '2026-01-06', '15:10:00', 'Valeria Ruiz', 8, 'CENTRO_ESPECIALIDAD_LUJAN', 9),
                                                                                                                        (true, '2026-01-06', '15:45:00', 'Valeria Ruiz', 8, 'CENTRO_ESPECIALIDAD_LUJAN', 9),
                                                                                                                        (true, '2026-01-06', '16:20:00', 'Valeria Ruiz', 8, 'CENTRO_ESPECIALIDAD_LUJAN', 9),
                                                                                                                        (true, '2026-01-06', '16:55:00', 'Valeria Ruiz', 8, 'CENTRO_ESPECIALIDAD_LUJAN', 9),
                                                                                                                        (true, '2026-01-06', '17:30:00', 'Valeria Ruiz', 8, 'CENTRO_ESPECIALIDAD_LUJAN', 9),
                                                                                                                        (true, '2026-01-06', '18:05:00', 'Valeria Ruiz', 8, 'CENTRO_ESPECIALIDAD_LUJAN', 9);

-- ============================================
-- MÉDICO ID 10 - Ejemplo: Dr. Miguel Fernández (UROLOGIA = 15)
-- Centro: CENTRO_ESPECIALIDAD_OFFICIA
-- ============================================
INSERT INTO turnos (available, fecha_turno, hora_turno, medic_full_name, medic_specialty, medical_center, medic_id) VALUES
                                         (true, '2025-12-11', '08:00:00', 'Miguel Fernández', 15, 'CENTRO_ESPECIALIDAD_OFFICIA', 10),
                                         (true, '2025-12-11', '08:35:00', 'Miguel Fernández', 15, 'CENTRO_ESPECIALIDAD_OFFICIA', 10),
                                         (true, '2025-12-11', '09:10:00', 'Miguel Fernández', 15, 'CENTRO_ESPECIALIDAD_OFFICIA', 10),
                                         (true, '2025-12-11', '09:45:00', 'Miguel Fernández', 15, 'CENTRO_ESPECIALIDAD_OFFICIA', 10),
                                         (true, '2025-12-11', '10:20:00', 'Miguel Fernández', 15, 'CENTRO_ESPECIALIDAD_OFFICIA', 10),
                                         (true, '2025-12-11', '10:55:00', 'Miguel Fernández', 15, 'CENTRO_ESPECIALIDAD_OFFICIA', 10),
                                         (true, '2025-12-11', '11:30:00', 'Miguel Fernández', 15, 'CENTRO_ESPECIALIDAD_OFFICIA', 10),
                                         (true, '2025-12-11', '12:05:00', 'Miguel Fernández', 15, 'CENTRO_ESPECIALIDAD_OFFICIA', 10),
                                         (true, '2025-12-18', '08:00:00', 'Miguel Fernández', 15, 'CENTRO_ESPECIALIDAD_OFFICIA', 10),
                                         (true, '2025-12-18', '08:35:00', 'Miguel Fernández', 15, 'CENTRO_ESPECIALIDAD_OFFICIA', 10),
                                         (true, '2025-12-18', '09:10:00', 'Miguel Fernández', 15, 'CENTRO_ESPECIALIDAD_OFFICIA', 10),
                                         (true, '2025-12-18', '09:45:00', 'Miguel Fernández', 15, 'CENTRO_ESPECIALIDAD_OFFICIA', 10),
                                         (true, '2025-12-18', '10:20:00', 'Miguel Fernández', 15, 'CENTRO_ESPECIALIDAD_OFFICIA', 10),
                                         (true, '2025-12-18', '10:55:00', 'Miguel Fernández', 15, 'CENTRO_ESPECIALIDAD_OFFICIA', 10),
                                         (true, '2025-12-18', '11:30:00', 'Miguel Fernández', 15, 'CENTRO_ESPECIALIDAD_OFFICIA', 10),
                                         (true, '2025-12-18', '12:05:00', 'Miguel Fernández', 15, 'CENTRO_ESPECIALIDAD_OFFICIA', 10),
                                         (true, '2026-01-09', '08:00:00', 'Miguel Fernández', 15, 'CENTRO_ESPECIALIDAD_OFFICIA', 10),
                                         (true, '2026-01-09', '08:35:00', 'Miguel Fernández', 15, 'CENTRO_ESPECIALIDAD_OFFICIA', 10),
                                         (true, '2026-01-09', '09:10:00', 'Miguel Fernández', 15, 'CENTRO_ESPECIALIDAD_OFFICIA', 10),
                                         (true, '2026-01-09', '09:45:00', 'Miguel Fernández', 15, 'CENTRO_ESPECIALIDAD_OFFICIA', 10),
                                         (true, '2026-01-09', '10:20:00', 'Miguel Fernández', 15, 'CENTRO_ESPECIALIDAD_OFFICIA', 10),
                                         (true, '2026-01-09', '10:55:00', 'Miguel Fernández', 15, 'CENTRO_ESPECIALIDAD_OFFICIA', 10),
                                         (true, '2026-01-09', '11:30:00', 'Miguel Fernández', 15, 'CENTRO_ESPECIALIDAD_OFFICIA', 10),
                                         (true, '2026-01-09', '12:05:00', 'Miguel Fernández', 15, 'CENTRO_ESPECIALIDAD_OFFICIA', 10);

-- ============================================
-- MÉDICO ID 11 - Ejemplo: Dra. Patricia Díaz (PSIQUIATRIA = 11)
-- Centro: SEDE_PRINCIPAL_HOSPITAL_AUSTRAL
-- ============================================
INSERT INTO turnos (available, fecha_turno, hora_turno, medic_full_name, medic_specialty, medical_center, medic_id) VALUES
                                                                                                                        (true, '2025-12-10', '14:00:00', 'Patricia Díaz', 11, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 11),
                                                                                                                        (true, '2025-12-10', '14:35:00', 'Patricia Díaz', 11, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 11),
                                                                                                                        (true, '2025-12-10', '15:10:00', 'Patricia Díaz', 11, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 11),
                                                                                                                        (true, '2025-12-10', '15:45:00', 'Patricia Díaz', 11, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 11),
                                                                                                                        (true, '2025-12-10', '16:20:00', 'Patricia Díaz', 11, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 11),
                                                                                                                        (true, '2025-12-10', '16:55:00', 'Patricia Díaz', 11, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 11),
                                                                                                                        (true, '2025-12-10', '17:30:00', 'Patricia Díaz', 11, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 11),
                                                                                                                        (true, '2025-12-10', '18:05:00', 'Patricia Díaz', 11, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 11),
                                                                                                                        (true, '2025-12-13', '14:00:00', 'Patricia Díaz', 11, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 11),
                                                                                                                        (true, '2025-12-13', '14:35:00', 'Patricia Díaz', 11, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 11),
                                                                                                                        (true, '2025-12-13', '15:10:00', 'Patricia Díaz', 11, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 11),
                                                                                                                        (true, '2025-12-13', '15:45:00', 'Patricia Díaz', 11, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 11),
                                                                                                                        (true, '2025-12-13', '16:20:00', 'Patricia Díaz', 11, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 11),
                                                                                                                        (true, '2025-12-13', '16:55:00', 'Patricia Díaz', 11, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 11),
                                                                                                                        (true, '2025-12-13', '17:30:00', 'Patricia Díaz', 11, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 11),
                                                                                                                        (true, '2025-12-13', '18:05:00', 'Patricia Díaz', 11, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 11),
                                                                                                                        (true, '2025-12-17', '14:00:00', 'Patricia Díaz', 11, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 11),
                                                                                                                        (true, '2025-12-17', '14:35:00', 'Patricia Díaz', 11, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 11),
                                                                                                                        (true, '2025-12-17', '15:10:00', 'Patricia Díaz', 11, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 11),
                                                                                                                        (true, '2025-12-17', '15:45:00', 'Patricia Díaz', 11, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 11),
                                                                                                                        (true, '2025-12-17', '16:20:00', 'Patricia Díaz', 11, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 11),
                                                                                                                        (true, '2025-12-17', '16:55:00', 'Patricia Díaz', 11, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 11),
                                                                                                                        (true, '2025-12-17', '17:30:00', 'Patricia Díaz', 11, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 11)
