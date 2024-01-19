package com.example.MiHistoriaClinica.util.constant;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum AnalysisE {
    HEMOGRAMA_COMPLETO("Hemograma Completo", "Análisis de sangre que proporciona información sobre los glóbulos rojos, glóbulos blancos y plaquetas."),
    PERFIL_LIPIDICO("Perfil Lipídico", "Medición de los niveles de colesterol y triglicéridos en la sangre para evaluar el riesgo cardiovascular."),
    GLUCEMIA_EN_AYUNAS("Glucemia en Ayunas", "Medición de los niveles de glucosa en la sangre después de un ayuno nocturno."),
    PRUEBA_DE_EMABARAZO("Prueba de Embarazo", "Análisis para detectar la presencia de la hormona hCG y confirmar el embarazo."),
    ANALISIS_DE_ORINA("Análisis de Orina", "Examen de la orina que evalúa la presencia de sustancias anormales, células y microorganismos."),
    ELECTROCARDIOGRAMA("Electrocardiograma", "Registro gráfico de la actividad eléctrica del corazón para evaluar su función."),
    RADIOGRAFIA_DE_TORAX("Radiografía de Tórax", "Imagen radiológica de la región torácica para evaluar pulmones y corazón."),
    ECOGRAFIA_ABDOMINAL("Ecografía Abdominal", "Imagen por ultrasonido para visualizar los órganos internos del abdomen."),
    DENSITOMETRIA_OSEA("Densitometría Ósea", "Medición de la densidad mineral ósea para evaluar la salud ósea y detectar la osteoporosis."),
    ANALISIS_DE_TIROIDES("Análisis de Tiroides", "Pruebas que evalúan la función de la glándula tiroides y los niveles de hormonas tiroideas."),
    TOMOGRAFIA_COMPUTARIZADA("Tomografía Computarizada", "Imagen tridimensional de estructuras internas del cuerpo para diagnóstico preciso."),
    RESONANCIA_MAGNETICA("Resonancia Magnética", "Imágenes detalladas de estructuras internas mediante campos magnéticos y ondas de radio."),
    COLONOSCOPIA("Colonoscopía", "Examen endoscópico del colon para detectar pólipos, inflamación o cáncer."),
    MAMOGRAFIA("Mamografía", "Radiografía de las mamas para detectar posibles tumores o anomalías."),
    TEST_DE_VIH("Test de VIH", "Prueba para detectar la presencia del virus de inmunodeficiencia humana en la sangre."),
    ANALISIS_DE_HEPATITIS("Análisis de Hepatitis", "Pruebas para detectar infecciones virales que afectan al hígado."),
    HOLTER_CARDIACO("Holter Cardíaco", "Monitoreo continuo de la actividad eléctrica del corazón durante 24 horas."),
    ANALISIS_DE_ALERGIAS("Análisis de Alergias", "Pruebas para identificar alérgenos que pueden causar reacciones inmunológicas."),
    PAPANICOLAOU("Papanicolau", "Examen citológico para detectar células anormales en el cuello uterino y prevenir el cáncer cervical."),
    TEST_DE_GLUCOSA_POSPRANDIAL("Test de Glucosa Posprandial", "Medición de los niveles de glucosa en sangre después de 2 horas de comer."),
    CITOLOGIA_DE_ESPUTO("Citología de Esputo", "Análisis microscópico de células del esputo para detectar enfermedades respiratorias.");

    private final String name;
    private final String description;

    AnalysisE(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static String getAnalysisDescription(String analysisName) {
        for (AnalysisE analysisE : AnalysisE.values()) {
            if (analysisE.getName().equalsIgnoreCase(analysisName)) {
                return analysisE.getDescription();
            }
        }
        return " ";
    }

    public static List<String> getNames(){
        return Arrays.stream(AnalysisE.values())
                .map(AnalysisE::getName)
                .collect(Collectors.toList());

    }


}
