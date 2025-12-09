-- ============================================
-- Script para insertar turnos de médicos
-- ============================================

-- Para ver cantidad de turnos asignados por medico
SELECT m.medic_id, m.name, m.lastname, m.specialty,
       (SELECT COUNT(*) FROM turnos t WHERE t.medic_id = m.medic_id) as cantidad_turnos
FROM medic m;


-- ============================================
-- 2 (Lunes y Jueves, mañana)
-- Centro: SEDE_PRINCIPAL_HOSPITAL_AUSTRAL
-- ============================================
INSERT INTO turnos (available, fecha_turno, hora_turno, medic_full_name, medic_specialty, medical_center, medic_id) VALUES
(true, '2025-12-09', '08:00:00', 'Valeria Torres', 2, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 38),
(true, '2025-12-09', '08:35:00', 'Valeria Torres', 2, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 38),
(true, '2025-12-09', '09:10:00', 'Valeria Torres', 2, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 38),
(true, '2025-12-09', '09:45:00', 'Valeria Torres', 2, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 38),
(true, '2025-12-09', '10:20:00', 'Valeria Torres', 2, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 38),
(true, '2025-12-09', '10:55:00', 'Valeria Torres', 2, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 38),
(true, '2025-12-11', '08:00:00', 'Valeria Torres', 2, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 38),
(true, '2025-12-11', '08:35:00', 'Valeria Torres', 2, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 38),
(true, '2025-12-11', '09:10:00', 'Valeria Torres', 2, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 38),
(true, '2025-12-11', '09:45:00', 'Valeria Torres', 2, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 38),
(true, '2025-12-11', '10:20:00', 'Valeria Torres', 2, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 38),
(true, '2025-12-11', '10:55:00', 'Valeria Torres', 2, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 38),
(true, '2025-12-16', '08:00:00', 'Valeria Torres', 2, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 38),
(true, '2025-12-16', '08:35:00', 'Valeria Torres', 2, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 38),
(true, '2025-12-16', '09:10:00', 'Valeria Torres', 2, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 38),
(true, '2025-12-16', '09:45:00', 'Valeria Torres', 2, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 38),
(true, '2025-12-18', '08:00:00', 'Valeria Torres', 2, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 38),
(true, '2025-12-18', '08:35:00', 'Valeria Torres', 2, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 38),
(true, '2025-12-18', '09:10:00', 'Valeria Torres', 2, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 38),
(true, '2025-12-18', '09:45:00', 'Valeria Torres', 2, 'SEDE_PRINCIPAL_HOSPITAL_AUSTRAL', 38);

-- ============================================
-- MÉDICO ID 2 - DERMATOLOGIA (Martes y Viernes, tarde)
-- Centro: CENTRO_ESPECIALIDAD_OFFICIA
-- ============================================
INSERT INTO turnos (available, fecha_turno, hora_turno, medic_full_name, medic_specialty, medical_center, medic_id) VALUES
(true, '2025-12-10', '14:00:00', 'Valeria Torres', 2, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 38),
(true, '2025-12-10', '14:35:00', 'Valeria Torres', 2, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 38),
(true, '2025-12-10', '15:10:00', 'Valeria Torres', 2, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 38),
(true, '2025-12-10', '15:45:00', 'Valeria Torres', 2, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 38),
(true, '2025-12-10', '16:20:00', 'Valeria Torres', 2, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 38),
(true, '2025-12-12', '14:00:00', 'Valeria Torres', 2, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 38),
(true, '2025-12-12', '14:35:00', 'Valeria Torres', 2, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 38),
(true, '2025-12-12', '15:10:00', 'Valeria Torres', 2, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 38),
(true, '2025-12-12', '15:45:00', 'Valeria Torres', 2, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 38),
(true, '2025-12-12', '16:20:00', 'Valeria Torres', 2, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 38),
(true, '2025-12-17', '14:00:00', 'Valeria Torres', 2, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 38),
(true, '2025-12-17', '14:35:00', 'Valeria Torres', 2, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 38),
(true, '2025-12-17', '15:10:00', 'Valeria Torres', 2, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 38),
(true, '2025-12-17', '15:45:00', 'Valeria Torres', 2, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 38),
(true, '2025-12-19', '14:00:00', 'Valeria Torres', 2, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 38),
(true, '2025-12-19', '14:35:00', 'Valeria Torres', 2, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 38),
(true, '2025-12-19', '15:10:00', 'Valeria Torres', 2, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 38),
(true, '2025-12-19', '15:45:00', 'Valeria Torres', 2, 'CENTRO_ESPECIALIDAD_CHAMPAGNAT', 38);




