package com.example.MiHistoriaClinica.service.implementation;

import com.example.MiHistoriaClinica.persistence.model.*;
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

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

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

    // ============================================
    // ESTILOS BASE COMPARTIDOS
    // ============================================
    
    private static final String BASE_STYLES = """
        body {
            font-family: 'Roboto', 'Segoe UI', Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f0f4f8;
        }
        .container {
            max-width: 600px;
            margin: 20px auto;
            background-color: white;
            border-radius: 12px;
            box-shadow: 0 4px 20px rgba(74, 144, 226, 0.15), 0 2px 8px rgba(0,0,0,0.08);
            overflow: hidden;
        }
        .header {
            background: linear-gradient(135deg, #5ba3e8 0%, #4A90E2 50%, #3a7bc8 100%);
            color: white;
            padding: 45px 20px;
            text-align: center;
            position: relative;
        }
        .header::after {
            content: '';
            position: absolute;
            bottom: 0;
            left: 0;
            right: 0;
            height: 4px;
            background: linear-gradient(90deg, rgba(255,255,255,0.3) 0%, rgba(255,255,255,0.6) 50%, rgba(255,255,255,0.3) 100%);
        }
        .header h1 {
            margin: 0;
            font-size: 28px;
            font-weight: bold;
            text-shadow: 0 2px 4px rgba(0,0,0,0.2);
        }
        .header .icon {
            font-size: 48px;
            margin-bottom: 15px;
        }
        .header .subtitle {
            margin-top: 10px;
            font-size: 15px;
            opacity: 0.95;
        }
        .content {
            padding: 40px 35px;
            color: #333;
            background: linear-gradient(180deg, #ffffff 0%, #fafbfc 100%);
        }
        .content h2 {
            color: #4A90E2;
            font-size: 24px;
            margin-bottom: 20px;
        }
        .content p {
            line-height: 1.7;
            font-size: 16px;
            margin-bottom: 20px;
            color: #555;
        }
        .button-container {
            text-align: center;
            margin: 30px 0;
        }
        .action-button {
            display: inline-block;
            background: linear-gradient(135deg, #4A90E2 0%, #357ABD 100%);
            color: white !important;
            padding: 16px 45px;
            text-decoration: none;
            border-radius: 8px;
            font-weight: bold;
            font-size: 16px;
            box-shadow: 0 4px 15px rgba(74, 144, 226, 0.4);
            transition: all 0.3s ease;
        }
        .info-box {
            background: linear-gradient(135deg, #E8F4FD 0%, #d6ebf9 100%);
            border-left: 4px solid #4A90E2;
            padding: 18px;
            margin: 22px 0;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(74, 144, 226, 0.1);
        }
        .warning-box {
            background: linear-gradient(135deg, #f5f7fa 0%, #e8ecf1 100%);
            border-left: 4px solid #6c757d;
            padding: 18px;
            margin: 22px 0;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.05);
        }
        .details-box {
            background: linear-gradient(135deg, #f8fafc 0%, #eef2f7 100%);
            border-radius: 12px;
            padding: 28px;
            margin: 28px 0;
            border-left: 5px solid #4A90E2;
            box-shadow: 0 2px 12px rgba(74, 144, 226, 0.1), inset 0 1px 0 rgba(255,255,255,0.8);
        }
        .details-box h3 {
            color: #357ABD;
            margin: 0 0 22px 0;
            font-size: 18px;
            font-weight: 600;
        }
        .detail-row {
            display: flex;
            padding: 14px 0;
            border-bottom: 1px solid rgba(74, 144, 226, 0.15);
        }
        .detail-row:last-child {
            border-bottom: none;
            padding-bottom: 0;
        }
        .detail-label {
            font-weight: 600;
            color: #4A90E2;
            width: 140px;
            flex-shrink: 0;
        }
        .detail-value {
            color: #2c3e50;
            font-weight: 500;
        }
        .feature-list {
            background: linear-gradient(135deg, #f8fafc 0%, #eef2f7 100%);
            padding: 22px;
            border-radius: 10px;
            margin: 22px 0;
            box-shadow: 0 2px 8px rgba(0,0,0,0.05);
        }
        .feature-item {
            padding: 14px 0;
            border-bottom: 1px solid rgba(74, 144, 226, 0.12);
            color: #555;
        }
        .feature-item:last-child {
            border-bottom: none;
        }
        .feature-item strong {
            color: #4A90E2;
        }
        .footer {
            background: linear-gradient(180deg, #f8f9fa 0%, #e9ecef 100%);
            padding: 25px;
            text-align: center;
            color: #6c757d;
            font-size: 14px;
            border-top: 1px solid rgba(74, 144, 226, 0.1);
        }
        .footer a {
            color: #4A90E2;
            text-decoration: none;
            font-weight: 500;
        }
        """;

    private static final String FOOTER_HTML = """
        <div class="footer">
            <p><strong>Mi Historia Clínica</strong><br>
            Universidad Austral - Facultad de Ingeniería<br>
            Pilar, Buenos Aires, Argentina</p>
            <p style="margin-top: 12px;">
                ¿Necesitas ayuda? <a href="mailto:mihistoriaclinica.austral@gmail.com">Contáctanos</a>
            </p>
        </div>
        """;

    // ============================================
    // MÉTODOS DE ENVÍO
    // ============================================

    @Override
    public void sendVerificationEmail(Patient patient, String verificationUrl) {
        if (!remindersEnabled) {
            logger.info("Envío de emails deshabilitado");
            return;
        }

        try {
            String subject = "Verificación de cuenta - Mi Historia Clínica";
            String htmlContent = buildVerificationEmailHtml(patient, verificationUrl);
            
            sendEmail(patient.getEmail(), subject, htmlContent);
            logger.info("Email de verificación enviado exitosamente a: {}", patient.getEmail());
            
        } catch (Exception e) {
            logger.error("Error enviando email de verificación: ", e);
        }
    }

    @Override
    public void sendWelcomeEmail(Patient patient) {
        if (!remindersEnabled) {
            logger.info("Envío de emails deshabilitado");
            return;
        }

        try {
            String subject = "Bienvenido a Mi Historia Clínica";
            String htmlContent = buildWelcomeEmailHtml(patient);
            
            sendEmail(patient.getEmail(), subject, htmlContent);
            logger.info("Email de bienvenida enviado exitosamente a: {}", patient.getEmail());
            
        } catch (Exception e) {
            logger.error("Error enviando email de bienvenida: ", e);
        }
    }

    @Override
    public void sendMedicVerificationEmail(Medic medic, String verificationUrl) {
        if (!remindersEnabled) {
            logger.info("Envío de emails deshabilitado");
            return;
        }

        try {
            String subject = "Verificación de cuenta médica - Mi Historia Clínica";
            String htmlContent = buildMedicVerificationEmailHtml(medic, verificationUrl);
            
            sendEmail(medic.getEmail(), subject, htmlContent);
            logger.info("Email de verificación enviado exitosamente a médico: {}", medic.getEmail());
            
        } catch (Exception e) {
            logger.error("Error enviando email de verificación a médico: ", e);
        }
    }

    @Override
    public void sendMedicWelcomeEmail(Medic medic) {
        if (!remindersEnabled) {
            logger.info("Envío de emails deshabilitado");
            return;
        }

        try {
            String subject = "Bienvenido Dr/Dra. " + medic.getLastname() + " a Mi Historia Clínica";
            String htmlContent = buildMedicWelcomeEmailHtml(medic);
            
            sendEmail(medic.getEmail(), subject, htmlContent);
            logger.info("Email de bienvenida enviado exitosamente a médico: {}", medic.getEmail());
            
        } catch (Exception e) {
            logger.error("Error enviando email de bienvenida a médico: ", e);
        }
    }

    @Override
    public void sendTurnoConfirmationEmail(Patient patient, Turnos turno) {
        if (!remindersEnabled) {
            logger.info("Envío de emails deshabilitado");
            return;
        }

        try {
            String subject = "Turno Confirmado - Mi Historia Clínica";
            String htmlContent = buildTurnoConfirmationEmailHtml(patient, turno);
            
            sendEmail(patient.getEmail(), subject, htmlContent);
            logger.info("Email de confirmación de turno enviado exitosamente a: {}", patient.getEmail());
            
        } catch (Exception e) {
            logger.error("Error enviando email de confirmación de turno: ", e);
        }
    }

    @Override
    public void sendTurnoReminderEmail(Patient patient, Turnos turno) {
        if (!remindersEnabled) {
            logger.info("Envío de emails deshabilitado");
            return;
        }

        try {
            String subject = "Recordatorio de Turno para Mañana - Mi Historia Clínica";
            String htmlContent = buildTurnoReminderEmailHtml(patient, turno);
            
            sendEmail(patient.getEmail(), subject, htmlContent);
            logger.info("Email de recordatorio de turno enviado exitosamente a: {}", patient.getEmail());
            
        } catch (Exception e) {
            logger.error("Error enviando email de recordatorio de turno: ", e);
        }
    }

    @Override
    public void sendTurnoCancellationEmail(Patient patient, Turnos turno) {
        if (!remindersEnabled) {
            logger.info("Envío de emails deshabilitado");
            return;
        }

        try {
            String subject = "Turno Cancelado - Mi Historia Clínica";
            String htmlContent = buildTurnoCancellationEmailHtml(patient, turno);
            
            sendEmail(patient.getEmail(), subject, htmlContent);
            logger.info("Email de cancelación de turno enviado exitosamente a: {}", patient.getEmail());
            
        } catch (Exception e) {
            logger.error("Error enviando email de cancelación de turno: ", e);
        }
    }

    @Override
    public void sendAnalysisReminderEmail(Patient patient, Analysis analysis) {
        if (!remindersEnabled) {
            logger.info("Envío de emails deshabilitado");
            return;
        }

        try {
            String subject = "Recordatorio de Estudio para Mañana - Mi Historia Clínica";
            String htmlContent = buildAnalysisReminderEmailHtml(patient, analysis);
            
            sendEmail(patient.getEmail(), subject, htmlContent);
            logger.info("Email de recordatorio de estudio enviado exitosamente a: {}", patient.getEmail());
            
        } catch (Exception e) {
            logger.error("Error enviando email de recordatorio de estudio: ", e);
        }
    }

    @Override
    public void sendAnalysisScheduledEmail(Patient patient, Analysis analysis) {
        if (!remindersEnabled) {
            logger.info("Envío de emails deshabilitado");
            return;
        }

        try {
            String subject = "Turno de Estudio Confirmado - Mi Historia Clínica";
            String htmlContent = buildAnalysisScheduledEmailHtml(patient, analysis);
            
            sendEmail(patient.getEmail(), subject, htmlContent);
            logger.info("Email de confirmación de estudio enviado exitosamente a: {}", patient.getEmail());
            
        } catch (Exception e) {
            logger.error("Error enviando email de confirmación de estudio: ", e);
        }
    }

    @Override
    public void sendConsultationSummaryEmail(Patient patient, Medic medic, 
                                              List<String> estudios, List<String> medicamentos, 
                                              boolean historiaActualizada) {
        if (!remindersEnabled) {
            logger.info("Envío de emails deshabilitado");
            return;
        }

        try {
            String subject = "Resumen de tu Consulta Médica - Mi Historia Clínica";
            String htmlContent = buildConsultationSummaryEmailHtml(patient, medic, estudios, medicamentos, historiaActualizada);
            
            sendEmail(patient.getEmail(), subject, htmlContent);
            logger.info("Email de resumen de consulta enviado exitosamente a: {}", patient.getEmail());
            
        } catch (Exception e) {
            logger.error("Error enviando email de resumen de consulta: ", e);
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
                String errorMsg = String.format("Error enviando email. Status: %d, Body: %s", 
                           response.getStatusCode(), response.getBody());
                logger.error(errorMsg);
                throw new IOException(errorMsg);
            }
            
        } catch (IOException ex) {
            logger.error("Excepción enviando email: ", ex);
            throw ex;
        }
    }

    // ============================================
    // PLANTILLAS HTML - PACIENTES
    // ============================================

    private String buildVerificationEmailHtml(Patient patient, String verificationUrl) {
        return String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Verificación de Email</title>
                <style>
                    %s
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>Mi Historia Clínica</h1>
                        <p class="subtitle">Verificación de cuenta</p>
                    </div>
                    <div class="content">
                        <h2>Bienvenido/a, %s</h2>
                        <p>Gracias por registrarte en <strong>Mi Historia Clínica</strong>. Estamos contentos de tenerte en nuestra plataforma.</p>
                        
                        <p>Para completar tu registro y comenzar a utilizar todos los servicios, necesitamos verificar tu dirección de correo electrónico.</p>
                        
                        <div class="button-container">
                            <a href="%s" class="action-button">Verificar mi Email</a>
                        </div>
                        
                        <div class="info-box">
                            <p style="margin: 0; color: #2980b9;"><strong>Importante:</strong> Este enlace expirará en 24 horas por razones de seguridad.</p>
                        </div>
                        
                        <p>Si no puedes hacer clic en el botón, copia y pega el siguiente enlace en tu navegador:</p>
                        <p style="word-break: break-all; color: #4A90E2; font-size: 14px; background: #f8fafc; padding: 12px; border-radius: 6px;">%s</p>
                        
                        <p style="margin-top: 30px; font-size: 14px; color: #888;">Si no te registraste en Mi Historia Clínica, puedes ignorar este correo de forma segura.</p>
                    </div>
                    %s
                </div>
            </body>
            </html>
            """, 
            BASE_STYLES,
            patient.getName(),
            verificationUrl,
            verificationUrl,
            FOOTER_HTML
        );
    }

    private String buildWelcomeEmailHtml(Patient patient) {
        return String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Bienvenida</title>
                <style>
                    %s
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>Cuenta Verificada</h1>
                        <p class="subtitle">Ya puedes comenzar a usar la plataforma</p>
                    </div>
                    <div class="content">
                        <h2>Hola, %s %s</h2>
                        <p>Tu cuenta ha sido verificada exitosamente. Ya puedes acceder a todas las funcionalidades de <strong>Mi Historia Clínica</strong>.</p>
                        
                        <div class="feature-list">
                            <div class="feature-item">
                                <strong>Vinculación con médicos:</strong> Conecta con tus profesionales de salud
                            </div>
                            <div class="feature-item">
                                <strong>Gestión de turnos:</strong> Programa y administra tus citas médicas
                            </div>
                            <div class="feature-item">
                                <strong>Historia clínica:</strong> Accede a tu historial médico completo
                            </div>
                            <div class="feature-item">
                                <strong>Medicamentos:</strong> Lleva un registro de tus tratamientos
                            </div>
                            <div class="feature-item">
                                <strong>Estudios:</strong> Gestiona tus análisis y resultados
                            </div>
                        </div>
                        
                        <div class="info-box">
                            <p style="margin: 0; color: #2980b9;">Ya puedes iniciar sesión en la plataforma y comenzar a utilizar todos nuestros servicios.</p>
                        </div>
                        
                        <p style="font-size: 14px; color: #888; margin-top: 30px;">Si tienes alguna pregunta o necesitas ayuda, no dudes en contactarnos.</p>
                    </div>
                    %s
                </div>
            </body>
            </html>
            """, 
            BASE_STYLES,
            patient.getName(),
            patient.getLastname(),
            FOOTER_HTML
        );
    }

    // ============================================
    // PLANTILLAS HTML - MÉDICOS
    // ============================================

    private String buildMedicVerificationEmailHtml(Medic medic, String verificationUrl) {
        String specialtyName = medic.getSpecialty() != null ? medic.getSpecialty().getName() : "No especificada";
        
        return String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Verificación de Cuenta Médica</title>
                <style>
                    %s
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>Mi Historia Clínica</h1>
                        <p class="subtitle">Portal Médico Profesional</p>
                    </div>
                    <div class="content">
                        <h2>Verificación de Cuenta Médica</h2>
                        <p>Estimado/a <strong>Dr/Dra. %s %s</strong>,</p>
                        <p>Gracias por registrarte como profesional médico en nuestra plataforma. Para activar tu cuenta y comenzar a atender pacientes, necesitamos verificar tu dirección de correo electrónico.</p>
                        
                        <div class="button-container">
                            <a href="%s" class="action-button">Verificar mi Cuenta Médica</a>
                        </div>
                        
                        <div class="info-box">
                            <p style="margin: 0; color: #2980b9;"><strong>Importante:</strong> Este enlace expirará en 24 horas por razones de seguridad.</p>
                        </div>
                        
                        <div class="details-box">
                            <h3>Datos de registro</h3>
                            <div class="detail-row">
                                <span class="detail-label">Matrícula:</span>
                                <span class="detail-value">%s</span>
                            </div>
                            <div class="detail-row">
                                <span class="detail-label">Especialidad:</span>
                                <span class="detail-value">%s</span>
                            </div>
                        </div>
                        
                        <p style="margin-top: 20px;">Una vez verificada tu cuenta, podrás:</p>
                        <div class="feature-list">
                            <div class="feature-item">Crear y gestionar historias clínicas</div>
                            <div class="feature-item">Vincular y atender pacientes</div>
                            <div class="feature-item">Gestionar tu agenda de turnos</div>
                            <div class="feature-item">Prescribir medicamentos</div>
                            <div class="feature-item">Solicitar estudios médicos</div>
                        </div>
                        
                        <p>Si el botón no funciona, copia y pega el siguiente enlace:</p>
                        <p style="word-break: break-all; color: #4A90E2; font-size: 14px; background: #f8fafc; padding: 12px; border-radius: 6px;">%s</p>
                        
                        <p style="margin-top: 30px; font-size: 14px; color: #888;">Si no te registraste como médico en Mi Historia Clínica, puedes ignorar este correo.</p>
                    </div>
                    %s
                </div>
            </body>
            </html>
            """, 
            BASE_STYLES,
            medic.getName(),
            medic.getLastname(),
            verificationUrl,
            medic.getMatricula(),
            specialtyName,
            verificationUrl,
            FOOTER_HTML
        );
    }

    private String buildMedicWelcomeEmailHtml(Medic medic) {
        String specialtyName = medic.getSpecialty() != null ? medic.getSpecialty().getName() : "No especificada";
        
        return String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Bienvenida - Cuenta Médica Activada</title>
                <style>
                    %s
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>Cuenta Médica Verificada</h1>
                        <p class="subtitle">Ya puede comenzar a atender pacientes</p>
                    </div>
                    <div class="content">
                        <h2>Bienvenido/a, Dr/Dra. %s %s</h2>
                        <p>Tu cuenta médica ha sido verificada exitosamente. Ya puedes acceder a todas las funcionalidades profesionales de <strong>Mi Historia Clínica</strong>.</p>
                        
                        <div class="details-box">
                            <h3>Datos de tu cuenta</h3>
                            <div class="detail-row">
                                <span class="detail-label">Matrícula:</span>
                                <span class="detail-value">%s</span>
                            </div>
                            <div class="detail-row">
                                <span class="detail-label">Especialidad:</span>
                                <span class="detail-value">%s</span>
                            </div>
                            <div class="detail-row">
                                <span class="detail-label">Email:</span>
                                <span class="detail-value">%s</span>
                            </div>
                        </div>
                        
                        <div class="feature-list">
                            <div class="feature-item">
                                <strong>Gestión de historias clínicas:</strong> Crea y mantén actualizados los registros de tus pacientes
                            </div>
                            <div class="feature-item">
                                <strong>Vinculación con pacientes:</strong> Acepta solicitudes de vinculación mediante códigos únicos
                            </div>
                            <div class="feature-item">
                                <strong>Agenda de turnos:</strong> Configura tus horarios disponibles y gestiona las reservas
                            </div>
                            <div class="feature-item">
                                <strong>Prescripción de medicamentos:</strong> Receta tratamientos a tus pacientes
                            </div>
                            <div class="feature-item">
                                <strong>Solicitud de estudios:</strong> Ordena análisis y estudios complementarios
                            </div>
                        </div>
                        
                        <div class="info-box">
                            <p style="margin: 0; color: #2980b9; text-align: center; font-size: 17px;"><strong>Ya puede iniciar sesión y comenzar a atender pacientes</strong></p>
                        </div>
                        
                        <p style="font-size: 14px; color: #888; margin-top: 30px;">Si necesita ayuda para comenzar, no dude en contactarnos.</p>
                    </div>
                    %s
                </div>
            </body>
            </html>
            """, 
            BASE_STYLES,
            medic.getName(),
            medic.getLastname(),
            medic.getMatricula(),
            specialtyName,
            medic.getEmail(),
            FOOTER_HTML
        );
    }

    // ============================================
    // PLANTILLAS HTML - TURNOS
    // ============================================

    private String buildTurnoConfirmationEmailHtml(Patient patient, Turnos turno) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        
        String fechaFormateada = turno.getFechaTurno().format(dateFormatter);
        String horaFormateada = turno.getHoraTurno().format(timeFormatter);
        String medicalCenterName = turno.getMedicalCenter() != null ? turno.getMedicalCenter().getName() : "No especificado";
        String specialtyName = turno.getMedicSpecialty() != null ? turno.getMedicSpecialty().getName() : "No especificada";

        return String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Confirmación de Turno</title>
                <style>
                    %s
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>Turno Confirmado</h1>
                        <p class="subtitle">Tu reserva ha sido procesada exitosamente</p>
                    </div>
                    <div class="content">
                        <h2>Hola, %s %s</h2>
                        <p>Tu turno ha sido reservado exitosamente. A continuación encontrarás los detalles de tu cita médica:</p>
                        
                        <div class="details-box">
                            <h3>Detalles del Turno</h3>
                            <div class="detail-row">
                                <span class="detail-label">Fecha:</span>
                                <span class="detail-value">%s</span>
                            </div>
                            <div class="detail-row">
                                <span class="detail-label">Hora:</span>
                                <span class="detail-value">%s hs</span>
                            </div>
                            <div class="detail-row">
                                <span class="detail-label">Médico:</span>
                                <span class="detail-value">Dr/Dra. %s</span>
                            </div>
                            <div class="detail-row">
                                <span class="detail-label">Especialidad:</span>
                                <span class="detail-value">%s</span>
                            </div>
                            <div class="detail-row">
                                <span class="detail-label">Centro:</span>
                                <span class="detail-value">%s</span>
                            </div>
                        </div>
                        
                        <div class="info-box">
                            <p style="margin: 0; color: #2980b9;"><strong>Recordatorio:</strong> Te enviaremos un email de recordatorio 24 horas antes de tu turno.</p>
                        </div>
                        
                        <div class="warning-box">
                            <p style="margin: 0; color: #495057;"><strong>Importante:</strong> Por favor, llega 15 minutos antes de tu turno. Si necesitas cancelar, hazlo con al menos 24 horas de anticipación desde la plataforma.</p>
                        </div>
                        
                        <p style="margin-top: 30px; font-size: 14px; color: #888;">Si tienes alguna pregunta o necesitas reprogramar tu turno, puedes hacerlo desde la plataforma Mi Historia Clínica.</p>
                    </div>
                    %s
                </div>
            </body>
            </html>
            """,
            BASE_STYLES,
            patient.getName(),
            patient.getLastname(),
            fechaFormateada,
            horaFormateada,
            turno.getMedicFullName(),
            specialtyName,
            medicalCenterName,
            FOOTER_HTML
        );
    }

    private String buildTurnoReminderEmailHtml(Patient patient, Turnos turno) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        
        String fechaFormateada = turno.getFechaTurno().format(dateFormatter);
        String horaFormateada = turno.getHoraTurno().format(timeFormatter);
        String medicalCenterName = turno.getMedicalCenter() != null ? turno.getMedicalCenter().getName() : "No especificado";
        String specialtyName = turno.getMedicSpecialty() != null ? turno.getMedicSpecialty().getName() : "No especificada";

        return String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Recordatorio de Turno</title>
                <style>
                    %s
                    .countdown-box {
                        background: linear-gradient(135deg, #E8F4FD 0%%, #cce5f8 100%%);
                        border-radius: 12px;
                        padding: 25px;
                        margin: 28px 0;
                        text-align: center;
                        box-shadow: 0 3px 15px rgba(74, 144, 226, 0.2);
                        border: 1px solid rgba(74, 144, 226, 0.2);
                    }
                    .countdown-box h3 {
                        color: #2980b9;
                        margin: 0;
                        font-size: 22px;
                        font-weight: 600;
                    }
                    .countdown-box p {
                        margin: 12px 0 0 0;
                        font-size: 15px;
                        color: #4A90E2;
                    }
                    .checklist {
                        background: linear-gradient(135deg, #f5f7fa 0%%, #e8ecf1 100%%);
                        padding: 22px;
                        border-radius: 10px;
                        margin: 22px 0;
                    }
                    .checklist h4 {
                        color: #2c3e50;
                        margin: 0 0 18px 0;
                        font-size: 16px;
                    }
                    .checklist-item {
                        padding: 10px 0;
                        color: #555;
                        border-bottom: 1px solid rgba(0,0,0,0.05);
                    }
                    .checklist-item:last-child {
                        border-bottom: none;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>Recordatorio de Turno</h1>
                        <p class="subtitle">Tu cita médica es mañana</p>
                    </div>
                    <div class="content">
                        <h2>Hola, %s %s</h2>
                        <p>Te recordamos que tienes un turno médico programado para <strong>mañana</strong>. Por favor, revisa los detalles a continuación:</p>
                        
                        <div class="countdown-box">
                            <h3>Tu turno es en menos de 24 horas</h3>
                            <p>No olvides prepararte para tu cita</p>
                        </div>
                        
                        <div class="details-box">
                            <h3>Detalles del Turno</h3>
                            <div class="detail-row">
                                <span class="detail-label">Fecha:</span>
                                <span class="detail-value">%s</span>
                            </div>
                            <div class="detail-row">
                                <span class="detail-label">Hora:</span>
                                <span class="detail-value">%s hs</span>
                            </div>
                            <div class="detail-row">
                                <span class="detail-label">Médico:</span>
                                <span class="detail-value">Dr/Dra. %s</span>
                            </div>
                            <div class="detail-row">
                                <span class="detail-label">Especialidad:</span>
                                <span class="detail-value">%s</span>
                            </div>
                            <div class="detail-row">
                                <span class="detail-label">Centro:</span>
                                <span class="detail-value">%s</span>
                            </div>
                        </div>
                        
                        <div class="checklist">
                            <h4>No olvides llevar:</h4>
                            <div class="checklist-item">• DNI o documento de identidad</div>
                            <div class="checklist-item">• Credencial de obra social/prepaga (si corresponde)</div>
                            <div class="checklist-item">• Estudios previos relacionados</div>
                            <div class="checklist-item">• Lista de medicamentos actuales</div>
                        </div>
                        
                        <div class="warning-box">
                            <p style="margin: 0; color: #495057;"><strong>Importante:</strong> Por favor, llega 15 minutos antes de tu turno. Si no puedes asistir, cancela tu turno desde la plataforma para que otro paciente pueda tomarlo.</p>
                        </div>
                        
                        <p style="margin-top: 30px; font-size: 14px; color: #888;">Te deseamos una excelente consulta. Gracias por confiar en nosotros.</p>
                    </div>
                    %s
                </div>
            </body>
            </html>
            """, 
            BASE_STYLES,
            patient.getName(),
            patient.getLastname(),
            fechaFormateada,
            horaFormateada,
            turno.getMedicFullName(),
            specialtyName,
            medicalCenterName,
            FOOTER_HTML
        );
    }

    private String buildTurnoCancellationEmailHtml(Patient patient, Turnos turno) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        
        String fechaFormateada = turno.getFechaTurno().format(dateFormatter);
        String horaFormateada = turno.getHoraTurno().format(timeFormatter);
        String medicalCenterName = turno.getMedicalCenter() != null ? turno.getMedicalCenter().getName() : "No especificado";
        String specialtyName = turno.getMedicSpecialty() != null ? turno.getMedicSpecialty().getName() : "No especificada";

        return String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Turno Cancelado</title>
                <style>
                    %s
                    .header-cancel {
                        background: linear-gradient(135deg, #95a5a6 0%%, #7f8c8d 50%%, #6c7a7d 100%%);
                        color: white;
                        padding: 45px 20px;
                        text-align: center;
                    }
                    .header-cancel h1 {
                        margin: 0;
                        font-size: 28px;
                        font-weight: bold;
                    }
                    .header-cancel .subtitle {
                        margin-top: 10px;
                        font-size: 15px;
                        opacity: 0.95;
                    }
                    .cancelled-details {
                        background: linear-gradient(135deg, #f8f9fa 0%%, #e9ecef 100%%);
                        border-radius: 12px;
                        padding: 28px;
                        margin: 28px 0;
                        border-left: 5px solid #95a5a6;
                    }
                    .cancelled-details h3 {
                        color: #6c757d;
                        margin: 0 0 22px 0;
                        font-size: 18px;
                        font-weight: 600;
                    }
                    .cancelled-details .detail-label {
                        color: #6c757d;
                    }
                    .reschedule-box {
                        background: linear-gradient(135deg, #E8F4FD 0%%, #d6ebf9 100%%);
                        border-radius: 12px;
                        padding: 25px;
                        margin: 28px 0;
                        text-align: center;
                        border: 1px solid rgba(74, 144, 226, 0.2);
                    }
                    .reschedule-box h3 {
                        color: #2980b9;
                        margin: 0 0 10px 0;
                        font-size: 18px;
                    }
                    .reschedule-box p {
                        margin: 0;
                        color: #4A90E2;
                        font-size: 15px;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header-cancel">
                        <h1>Turno Cancelado</h1>
                        <p class="subtitle">Tu reserva ha sido cancelada</p>
                    </div>
                    <div class="content">
                        <h2>Hola, %s %s</h2>
                        <p>Te confirmamos que tu turno ha sido <strong>cancelado exitosamente</strong>. A continuación los detalles del turno que fue cancelado:</p>
                        
                        <div class="cancelled-details">
                            <h3>Turno Cancelado</h3>
                            <div class="detail-row">
                                <span class="detail-label">Fecha:</span>
                                <span class="detail-value" style="text-decoration: line-through; color: #95a5a6;">%s</span>
                        </div>
                            <div class="detail-row">
                                <span class="detail-label">Hora:</span>
                                <span class="detail-value" style="text-decoration: line-through; color: #95a5a6;">%s hs</span>
                            </div>
                            <div class="detail-row">
                                <span class="detail-label">Médico:</span>
                                <span class="detail-value">Dr/Dra. %s</span>
                            </div>
                            <div class="detail-row">
                                <span class="detail-label">Especialidad:</span>
                                <span class="detail-value">%s</span>
                            </div>
                            <div class="detail-row">
                                <span class="detail-label">Centro:</span>
                                <span class="detail-value">%s</span>
                            </div>
                            </div>
                        
                        <div class="reschedule-box">
                            <h3>¿Necesitas reprogramar?</h3>
                            <p>Puedes reservar un nuevo turno desde la plataforma Mi Historia Clínica</p>
                            </div>
                        
                        <div class="info-box">
                            <p style="margin: 0; color: #2980b9;"><strong>Recomendación:</strong> Te sugerimos reservar tu nuevo turno con anticipación para asegurar disponibilidad con tu médico preferido.</p>
                        </div>
                        
                        <p style="margin-top: 30px; font-size: 14px; color: #888;">Si tienes alguna pregunta o necesitas ayuda, no dudes en contactarnos.</p>
                    </div>
                    %s
                </div>
            </body>
            </html>
            """, 
            BASE_STYLES,
            patient.getName(),
            patient.getLastname(),
            fechaFormateada,
            horaFormateada,
            turno.getMedicFullName(),
            specialtyName,
            medicalCenterName,
            FOOTER_HTML
        );
    }

    // ============================================
    // PLANTILLAS HTML - ESTUDIOS/ANÁLISIS
    // ============================================

    private String buildAnalysisReminderEmailHtml(Patient patient, Analysis analysis) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        
        String fechaFormateada = analysis.getScheduledDate() != null 
            ? analysis.getScheduledDate().format(dateFormatter) 
            : "No especificada";
        String analysisName = analysis.getName() != null ? analysis.getName().getName() : "No especificado";
        String medicalCenterName = analysis.getMedicalCenterE() != null ? analysis.getMedicalCenterE().getName() : "No especificado";
        String description = analysis.getDescription() != null ? analysis.getDescription() : "";

        return String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Recordatorio de Estudio</title>
                <style>
                    %s
                    .countdown-box {
                        background: linear-gradient(135deg, #E8F4FD 0%%, #cce5f8 100%%);
                        border-radius: 12px;
                        padding: 25px;
                        margin: 28px 0;
                        text-align: center;
                        border: 1px solid rgba(74, 144, 226, 0.2);
                    }
                    .countdown-box h3 {
                        color: #2980b9;
                        margin: 0;
                        font-size: 22px;
                        font-weight: 600;
                    }
                    .countdown-box p {
                        margin: 12px 0 0 0;
                        font-size: 15px;
                        color: #4A90E2;
                    }
                    .checklist {
                        background: linear-gradient(135deg, #f5f7fa 0%%, #e8ecf1 100%%);
                        padding: 22px;
                        border-radius: 10px;
                        margin: 22px 0;
                    }
                    .checklist h4 {
                        color: #2c3e50;
                        margin: 0 0 18px 0;
                        font-size: 16px;
                    }
                    .checklist-item {
                        padding: 10px 0;
                        color: #555;
                        border-bottom: 1px solid rgba(0,0,0,0.05);
                    }
                    .checklist-item:last-child {
                        border-bottom: none;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>Recordatorio de Estudio</h1>
                        <p class="subtitle">Tu estudio está programado para mañana</p>
                    </div>
                    <div class="content">
                        <h2>Hola, %s %s</h2>
                        <p>Te recordamos que tienes un <strong>estudio médico</strong> programado para <strong>mañana</strong>. Por favor, revisa los detalles a continuación:</p>
                        
                        <div class="countdown-box">
                            <h3>Tu estudio es en menos de 24 horas</h3>
                            <p>No olvides prepararte adecuadamente</p>
                        </div>
                        
                        <div class="details-box">
                            <h3>Detalles del Estudio</h3>
                            <div class="detail-row">
                                <span class="detail-label">Fecha:</span>
                                <span class="detail-value">%s</span>
                            </div>
                            <div class="detail-row">
                                <span class="detail-label">Estudio:</span>
                                <span class="detail-value">%s</span>
                            </div>
                            <div class="detail-row">
                                <span class="detail-label">Descripción:</span>
                                <span class="detail-value">%s</span>
                            </div>
                            <div class="detail-row">
                                <span class="detail-label">Centro:</span>
                                <span class="detail-value">%s</span>
                            </div>
                        </div>
                        
                        <div class="checklist">
                            <h4>Recomendaciones generales:</h4>
                            <div class="checklist-item">• DNI o documento de identidad</div>
                            <div class="checklist-item">• Orden médica o indicación del estudio</div>
                            <div class="checklist-item">• Credencial de obra social/prepaga (si corresponde)</div>
                            <div class="checklist-item">• Seguir las indicaciones de preparación del estudio</div>
                            <div class="checklist-item">• Llegar con tiempo de anticipación</div>
                        </div>
                        
                        <div class="warning-box">
                            <p style="margin: 0; color: #495057;"><strong>Importante:</strong> Algunos estudios requieren preparación especial (ayuno, suspensión de medicamentos, etc.). Por favor, consulta las indicaciones específicas de tu estudio.</p>
                        </div>
                        
                        <p style="margin-top: 30px; font-size: 14px; color: #888;">Te deseamos que todo salga bien. Gracias por confiar en nosotros.</p>
                    </div>
                    %s
                </div>
            </body>
            </html>
            """, 
            BASE_STYLES,
            patient.getName(),
            patient.getLastname(),
            fechaFormateada,
            analysisName,
            description,
            medicalCenterName,
            FOOTER_HTML
        );
    }

    private String buildAnalysisScheduledEmailHtml(Patient patient, Analysis analysis) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        
        String fechaFormateada = analysis.getScheduledDate() != null 
            ? analysis.getScheduledDate().format(dateFormatter) 
            : "No especificada";
        String horaFormateada = analysis.getScheduledTime() != null 
            ? analysis.getScheduledTime().format(timeFormatter) 
            : "No especificada";
        String analysisName = analysis.getName() != null ? analysis.getName().getName() : "No especificado";
        String medicalCenterName = analysis.getMedicalCenterE() != null ? analysis.getMedicalCenterE().getName() : "No especificado";
        String description = analysis.getDescription() != null ? analysis.getDescription() : "";

        return String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Turno de Estudio Confirmado</title>
                <style>
                    %s
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>Turno de Estudio Confirmado</h1>
                        <p class="subtitle">Tu reserva ha sido procesada exitosamente</p>
                    </div>
                    <div class="content">
                        <h2>Hola, %s %s</h2>
                        <p>Tu turno para realizar el estudio ha sido reservado exitosamente. A continuación encontrarás los detalles:</p>
                        
                        <div class="details-box">
                            <h3>Detalles del Estudio</h3>
                            <div class="detail-row">
                                <span class="detail-label">Estudio:</span>
                                <span class="detail-value">%s</span>
                            </div>
                            <div class="detail-row">
                                <span class="detail-label">Descripción:</span>
                                <span class="detail-value">%s</span>
                            </div>
                            <div class="detail-row">
                                <span class="detail-label">Fecha:</span>
                                <span class="detail-value">%s</span>
                            </div>
                            <div class="detail-row">
                                <span class="detail-label">Hora:</span>
                                <span class="detail-value">%s hs</span>
                            </div>
                            <div class="detail-row">
                                <span class="detail-label">Centro:</span>
                                <span class="detail-value">%s</span>
                            </div>
                        </div>
                        
                        <div class="info-box">
                            <p style="margin: 0; color: #2980b9;"><strong>Recordatorio:</strong> Te enviaremos un email de recordatorio 24 horas antes de tu estudio.</p>
                        </div>
                        
                        <div class="warning-box">
                            <p style="margin: 0; color: #495057;"><strong>Importante:</strong> Algunos estudios requieren preparación especial (ayuno, suspensión de medicamentos, etc.). Consulta las indicaciones específicas de tu estudio.</p>
                        </div>
                        
                        <p style="margin-top: 30px; font-size: 14px; color: #888;">Si necesitas reprogramar o cancelar tu turno, puedes hacerlo desde la plataforma Mi Historia Clínica.</p>
                    </div>
                    %s
                </div>
            </body>
            </html>
            """,
            BASE_STYLES,
            patient.getName(),
            patient.getLastname(),
            analysisName,
            description,
            fechaFormateada,
            horaFormateada,
            medicalCenterName,
            FOOTER_HTML
        );
    }

    private String buildConsultationSummaryEmailHtml(Patient patient, Medic medic,
                                                      List<String> estudios, List<String> medicamentos,
                                                      boolean historiaActualizada) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        
        String fechaConsulta = java.time.LocalDate.now().format(dateFormatter);
        String medicName = medic.getName() + " " + medic.getLastname();
        String specialtyName = medic.getSpecialty() != null ? medic.getSpecialty().getName() : "No especificada";
        
        // Construir sección de cambios
        StringBuilder cambiosHtml = new StringBuilder();
        
        if (historiaActualizada) {
            cambiosHtml.append("""
                <div class="feature-item">
                    <strong>Historia Clínica:</strong> Se actualizó tu historia médica con nueva información.
                </div>
                """);
        }
        
        if (estudios != null && !estudios.isEmpty()) {
            StringBuilder listaEstudios = new StringBuilder();
            for (String estudio : estudios) {
                listaEstudios.append(String.format("<li>%s</li>", estudio));
            }
            cambiosHtml.append(String.format("""
                <div class="feature-item">
                    <strong>Estudios Asignados:</strong>
                    <ul style="margin: 8px 0 0 0; padding-left: 20px;">
                        %s
                    </ul>
                    <p style="font-size: 13px; color: #666; margin-top: 8px;">Ingresa a la plataforma para programar el turno de tus estudios.</p>
                </div>
                """, listaEstudios.toString()));
        }
        
        if (medicamentos != null && !medicamentos.isEmpty()) {
            StringBuilder listaMedicamentos = new StringBuilder();
            for (String medicamento : medicamentos) {
                listaMedicamentos.append(String.format("<li>%s</li>", medicamento));
            }
            cambiosHtml.append(String.format("""
                <div class="feature-item">
                    <strong>Medicamentos Recetados:</strong>
                    <ul style="margin: 8px 0 0 0; padding-left: 20px;">
                        %s
                    </ul>
                    <p style="font-size: 13px; color: #666; margin-top: 8px;">Sigue las indicaciones de tu médico para cada medicamento.</p>
                </div>
                """, listaMedicamentos.toString()));
        }
        
        // Si no hay cambios específicos
        if (cambiosHtml.length() == 0) {
            cambiosHtml.append("""
                <div class="feature-item">
                    <strong>Consulta de Control:</strong> Se realizó una consulta de seguimiento.
                </div>
                """);
        }

        return String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Resumen de Consulta Médica</title>
                <style>
                    %s
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>Resumen de Consulta</h1>
                        <p class="subtitle">Tu consulta médica ha finalizado</p>
                    </div>
                    <div class="content">
                        <h2>Hola, %s %s</h2>
                        <p>Tu consulta médica ha finalizado. A continuación encontrarás el detalle de lo realizado:</p>
                        
                        <div class="details-box">
                            <h3>Datos de la Consulta</h3>
                            <div class="detail-row">
                                <span class="detail-label">Fecha:</span>
                                <span class="detail-value">%s</span>
                            </div>
                            <div class="detail-row">
                                <span class="detail-label">Médico:</span>
                                <span class="detail-value">Dr/Dra. %s</span>
                            </div>
                            <div class="detail-row">
                                <span class="detail-label">Especialidad:</span>
                                <span class="detail-value">%s</span>
                            </div>
                        </div>
                        
                        <h3 style="color: #4A90E2; margin-top: 25px; margin-bottom: 15px;">Cambios Realizados</h3>
                        <div class="feature-list">
                            %s
                        </div>
                        
                        <div class="info-box">
                            <p style="margin: 0; color: #2980b9;"><strong>Recordatorio:</strong> Ingresa a la plataforma Mi Historia Clínica para ver todos los detalles y gestionar tus turnos.</p>
                        </div>
                        
                        <p style="margin-top: 30px; font-size: 14px; color: #888;">Si tienes alguna pregunta sobre tu consulta, no dudes en contactar a tu médico.</p>
                    </div>
                    %s
                </div>
            </body>
            </html>
            """,
            BASE_STYLES,
            patient.getName(),
            patient.getLastname(),
            fechaConsulta,
            medicName,
            specialtyName,
            cambiosHtml.toString(),
            FOOTER_HTML
        );
    }
}
