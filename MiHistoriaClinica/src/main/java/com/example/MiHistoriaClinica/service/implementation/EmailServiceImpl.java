package com.example.MiHistoriaClinica.service.implementation;

import com.example.MiHistoriaClinica.persistence.model.Analysis;
import com.example.MiHistoriaClinica.persistence.model.Patient;
import com.example.MiHistoriaClinica.persistence.model.Turnos;
import com.example.MiHistoriaClinica.service.EmailService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.format.DateTimeFormatter;
import java.io.IOException;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
    
    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;
    
    @Value("${sendgrid.from.email}")
    private String fromEmail;
    
    @Value("${sendgrid.from.name}")
    private String fromName;
    
    @Value("${app.reminders.enabled:true}")
    private boolean remindersEnabled;

    @Override
    public void sendTurnoReminderEmail(Patient patient, Turnos turno) {
        if (!remindersEnabled) {
            logger.info("Recordatorios deshabilitados");
            return;
        }

        try {
            String subject = "Recordatorio: Turno médico mañana";
            String htmlContent = buildTurnoReminderHtml(patient, turno);
            
            sendEmail(patient.getEmail(), subject, htmlContent);
            logger.info("Email de recordatorio de turno enviado exitosamente a: {}", patient.getEmail());
            
        } catch (Exception e) {
            logger.error("Error enviando email de recordatorio de turno: ", e);
        }
    }

    @Override
    public void sendAnalysisReminderEmail(Patient patient, Analysis analysis) {
        if (!remindersEnabled) {
            logger.info("Recordatorios deshabilitados");
            return;
        }

        try {
            String subject = "Recordatorio: Estudio médico pendiente";
            String htmlContent = buildAnalysisReminderHtml(patient, analysis);
            
            sendEmail(patient.getEmail(), subject, htmlContent);
            logger.info("Email de recordatorio de análisis enviado exitosamente a: {}", patient.getEmail());
            
        } catch (Exception e) {
            logger.error("Error enviando email de recordatorio de análisis: ", e);
        }
    }

    @Override
    public void sendTurnoConfirmationEmail(Patient patient, Turnos turno) {
        if (!remindersEnabled) {
            logger.info("Recordatorios deshabilitados");
            return;
        }

        try {
            String subject = "Confirmación: Turno médico reservado";
            String htmlContent = buildTurnoConfirmationHtml(patient, turno);
            
            sendEmail(patient.getEmail(), subject, htmlContent);
            logger.info("Email de confirmación de turno enviado exitosamente a: {}", patient.getEmail());
            
        } catch (Exception e) {
            logger.error("Error enviando email de confirmación de turno: ", e);
        }
    }

    @Override
    public void sendAnalysisConfirmationEmail(Patient patient, Analysis analysis) {
        if (!remindersEnabled) {
            logger.info("Recordatorios deshabilitados");
            return;
        }

        try {
            String subject = "Confirmación: Estudio médico solicitado";
            String htmlContent = buildAnalysisConfirmationHtml(patient, analysis);
            
            sendEmail(patient.getEmail(), subject, htmlContent);
            logger.info("Email de confirmación de análisis enviado exitosamente a: {}", patient.getEmail());
            
        } catch (Exception e) {
            logger.error("Error enviando email de confirmación de análisis: ", e);
        }
    }

    private void sendEmail(String toEmail, String subject, String htmlContent) throws IOException {
        Email from = new Email(fromEmail, fromName);
        Email to = new Email(toEmail);
        Content content = new Content("text/html", htmlContent);
        
        Mail mail = new Mail(from, subject, to, content);
        
        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();
        
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            
            Response response = sg.api(request);
            
            if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
                logger.info("Email enviado exitosamente. Status: {}", response.getStatusCode());
            } else {
                logger.error("Error enviando email. Status: {}, Body: {}", 
                           response.getStatusCode(), response.getBody());
            }
            
        } catch (IOException ex) {
            logger.error("Excepción enviando email: ", ex);
            throw ex;
        }
    }

    private String buildTurnoReminderHtml(Patient patient, Turnos turno) {
        return String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Recordatorio de Turno</title>
                <style>
                    body { font-family: Arial, sans-serif; margin: 20px; }
                    .container { max-width: 600px; margin: 0 auto; }
                    .header { background-color: #007bff; color: white; padding: 20px; text-align: center; }
                    .content { padding: 20px; background-color: #f8f9fa; }
                    .details { background-color: white; padding: 15px; margin: 15px 0; border-radius: 5px; }
                    .footer { text-align: center; color: #666; margin-top: 20px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>🏥 Recordatorio de Turno Médico</h1>
                    </div>
                    <div class="content">
                        <p>Estimado/a <strong>%s</strong>,</p>
                        <p>Le recordamos que tiene un turno médico programado para <strong>mañana</strong>:</p>
                        <div class="details">
                            <p><strong>📅 Fecha:</strong> %s</p>
                            <p><strong>🕐 Hora:</strong> %s</p>
                            <p><strong>👨‍⚕️ Médico:</strong> %s</p>
                            <p><strong>🏥 Especialidad:</strong> %s</p>
                            <p><strong>📍 Centro Médico:</strong> %s</p>
                        </div>
                        <p>⏰ <strong>Importante:</strong> Por favor, asegúrese de llegar 15 minutos antes de la hora programada.</p>
                        <p>Si no puede asistir, por favor comuníquese con nosotros con anticipación.</p>
                    </div>
                    <div class="footer">
                        <p>Saludos cordiales,<br><strong>Mi Historia Clínica</strong></p>
                    </div>
                </div>
            </body>
            </html>
            """, 
            patient.getName() + " " + patient.getLastname(),
            turno.getFechaTurno().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
            turno.getHoraTurno().format(DateTimeFormatter.ofPattern("HH:mm")),
            turno.getMedicFullName(),
            turno.getMedicSpecialty().getName(),
            turno.getMedicalCenter().getName()
        );
    }

    private String buildAnalysisReminderHtml(Patient patient, Analysis analysis) {
        return String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Recordatorio de Estudio</title>
                <style>
                    body { font-family: Arial, sans-serif; margin: 20px; }
                    .container { max-width: 600px; margin: 0 auto; }
                    .header { background-color: #28a745; color: white; padding: 20px; text-align: center; }
                    .content { padding: 20px; background-color: #f8f9fa; }
                    .details { background-color: white; padding: 15px; margin: 15px 0; border-radius: 5px; }
                    .footer { text-align: center; color: #666; margin-top: 20px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>🔬 Recordatorio de Estudio Médico</h1>
                    </div>
                    <div class="content">
                        <p>Estimado/a <strong>%s</strong>,</p>
                        <p>Le recordamos que tiene un estudio médico <strong>pendiente</strong>:</p>
                        <div class="details">
                            <p><strong>🔬 Estudio:</strong> %s</p>
                            <p><strong>📋 Descripción:</strong> %s</p>
                            <p><strong>🏥 Centro Médico:</strong> %s</p>
                            <p><strong>📊 Estado:</strong> %s</p>
                        </div>
                        <p>📞 <strong>Acción requerida:</strong> Por favor, comuníquese con el centro médico para programar su cita.</p>
                        <p>Es importante completar este estudio según las indicaciones médicas.</p>
                    </div>
                    <div class="footer">
                        <p>Saludos cordiales,<br><strong>Mi Historia Clínica</strong></p>
                    </div>
                </div>
            </body>
            </html>
            """, 
            patient.getName() + " " + patient.getLastname(),
            analysis.getName().getName(),
            analysis.getDescription(),
            analysis.getMedicalCenterE().getName(),
            analysis.getStatus()
        );
    }

    private String buildTurnoConfirmationHtml(Patient patient, Turnos turno) {
        return String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Confirmación de Turno</title>
                <style>
                    body { font-family: Arial, sans-serif; margin: 20px; }
                    .container { max-width: 600px; margin: 0 auto; }
                    .header { background-color: #17a2b8; color: white; padding: 20px; text-align: center; }
                    .content { padding: 20px; background-color: #f8f9fa; }
                    .details { background-color: white; padding: 15px; margin: 15px 0; border-radius: 5px; }
                    .footer { text-align: center; color: #666; margin-top: 20px; }
                    .success { color: #28a745; font-weight: bold; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>✅ Confirmación de Turno Médico</h1>
                    </div>
                    <div class="content">
                        <p>Estimado/a <strong>%s</strong>,</p>
                        <p class="success">¡Su turno médico ha sido confirmado exitosamente!</p>
                        <div class="details">
                            <p><strong>📅 Fecha:</strong> %s</p>
                            <p><strong>🕐 Hora:</strong> %s</p>
                            <p><strong>👨‍⚕️ Médico:</strong> %s</p>
                            <p><strong>🏥 Especialidad:</strong> %s</p>
                            <p><strong>📍 Centro Médico:</strong> %s</p>
                        </div>
                        <p>📩 <strong>Recordatorio:</strong> Recibirá un recordatorio 24 horas antes de su cita.</p>
                        <p>⏰ <strong>Importante:</strong> Por favor, asegúrese de llegar 15 minutos antes de la hora programada.</p>
                    </div>
                    <div class="footer">
                        <p>Saludos cordiales,<br><strong>Mi Historia Clínica</strong></p>
                    </div>
                </div>
            </body>
            </html>
            """, 
            patient.getName() + " " + patient.getLastname(),
            turno.getFechaTurno().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
            turno.getHoraTurno().format(DateTimeFormatter.ofPattern("HH:mm")),
            turno.getMedicFullName(),
            turno.getMedicSpecialty().getName(),
            turno.getMedicalCenter().getName()
        );
    }

    private String buildAnalysisConfirmationHtml(Patient patient, Analysis analysis) {
        return String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Confirmación de Estudio</title>
                <style>
                    body { font-family: Arial, sans-serif; margin: 20px; }
                    .container { max-width: 600px; margin: 0 auto; }
                    .header { background-color: #6f42c1; color: white; padding: 20px; text-align: center; }
                    .content { padding: 20px; background-color: #f8f9fa; }
                    .details { background-color: white; padding: 15px; margin: 15px 0; border-radius: 5px; }
                    .footer { text-align: center; color: #666; margin-top: 20px; }
                    .success { color: #28a745; font-weight: bold; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>📋 Confirmación de Estudio Médico</h1>
                    </div>
                    <div class="content">
                        <p>Estimado/a <strong>%s</strong>,</p>
                        <p class="success">¡Su estudio médico ha sido solicitado exitosamente!</p>
                        <div class="details">
                            <p><strong>🔬 Estudio:</strong> %s</p>
                            <p><strong>📋 Descripción:</strong> %s</p>
                            <p><strong>🏥 Centro Médico:</strong> %s</p>
                            <p><strong>📊 Estado:</strong> %s</p>
                        </div>
                        <p>📩 <strong>Recordatorio:</strong> Recibirá recordatorios para completar este estudio.</p>
                        <p>📞 <strong>Próximo paso:</strong> Por favor, comuníquese con el centro médico para programar su cita.</p>
                    </div>
                    <div class="footer">
                        <p>Saludos cordiales,<br><strong>Mi Historia Clínica</strong></p>
                    </div>
                </div>
            </body>
            </html>
            """, 
            patient.getName() + " " + patient.getLastname(),
            analysis.getName().getName(),
            analysis.getDescription(),
            analysis.getMedicalCenterE().getName(),
            analysis.getStatus()
        );
    }
} 