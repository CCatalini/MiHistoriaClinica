package com.example.MiHistoriaClinica.util.constant;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum MedicineName {
    PARACETAMOL("Analgésico y antipirético utilizado para aliviar el dolor y reducir la fiebre."),
    IBUPROFENO("Antiinflamatorio no esteroideo que alivia el dolor, reduce la inflamación y baja la fiebre."),
    ASPIRINA("Antiinflamatorio, analgésico y antipirético que también actúa como antiagregante plaquetario para prevenir coágulos."),
    OMEPRAZOL("Inhibidor de la bomba de protones utilizado para tratar problemas gastrointestinales como la acidez estomacal."),
    AMOXICILINA("Antibiótico de amplio espectro utilizado para tratar infecciones bacterianas como la neumonía y la bronquitis."),
    LORATADINA("Antihistamínico que alivia los síntomas de alergias como la picazón y el estornudo."),
    ATORVASTATINA("Estatina que ayuda a reducir los niveles de colesterol en sangre."),
    METFORMINA("Medicamento antidiabético que controla los niveles de azúcar en sangre en pacientes con diabetes tipo 2."),
    SIMVASTATINA("Otra estatina utilizada para reducir el colesterol y prevenir enfermedades cardiovasculares."),
    LOSARTAN("Bloqueador de los receptores de angiotensina II que se utiliza para tratar la hipertensión."),
    ALPRAZOLAM("Ansiolítico de acción rápida que trata trastornos de ansiedad y pánico."),
    ESCITALOPRAM("Antidepresivo utilizado para tratar trastornos de ansiedad y depresión."),
    TRAMADOL("Analgésico opioide que alivia el dolor moderado a severo."),
    PANTOPRAZOL("Inhibidor de la bomba de protones utilizado para tratar condiciones gastrointestinales como la acidez y la úlcera péptica."),
    SERTRALINA("Antidepresivo que trata la depresión, trastorno obsesivo-compulsivo y trastorno de pánico."),
    AMLODIPINO("Bloqueador de los canales de calcio utilizado para tratar la hipertensión y enfermedades cardiovasculares."),
    GABAPENTINA("Medicamento antiepiléptico y analgésico utilizado para controlar convulsiones y aliviar el dolor neuropático."),
    CLONAZEPAM("Benzodiazepina que trata trastornos de ansiedad y convulsiones."),
    RANITIDINA("Bloqueador de los receptores H2 utilizado para reducir la producción de ácido estomacal."),
    DULOXETINA("Antidepresivo que trata la depresión y el dolor neuropático."),
    PREGABALINA("Medicamento utilizado para tratar el dolor neuropático, convulsiones y trastorno de ansiedad generalizada."),
    CIPROFLOXACINO("Antibiótico fluoroquinolona que trata infecciones bacterianas."),
    MELATONINA("Hormona utilizada para regular el sueño y tratar problemas de insomnio."),
    VALSARTAN("Bloqueador de los receptores de angiotensina II utilizado para tratar la hipertensión."),
    CLOPIDOGREL("Antiagregante plaquetario que previene la formación de coágulos sanguíneos."),
    VITAMINA_D("Nutriente esencial para la salud ósea y el sistema inmunológico."),
    VITAMINA_C("Antioxidante que fortalece el sistema inmunológico y promueve la salud de la piel."),
    VITAMINA_B12("Importante para la formación de glóbulos rojos y el funcionamiento del sistema nervioso."),
    VITAMINA_B6("Contribuye al metabolismo de proteínas y al funcionamiento del sistema nervioso."),
    VITAMINA_B("Complejo de vitaminas esenciales para el metabolismo y la salud general.");

    private final String description;

    MedicineName(String description) {
        this.description = description;
    }

    public static List<String> getAllMedicinesNames() {
        return Arrays.stream(MedicineName.values())
                .map(MedicineName::name)
                .collect(Collectors.toList());
    }
}

