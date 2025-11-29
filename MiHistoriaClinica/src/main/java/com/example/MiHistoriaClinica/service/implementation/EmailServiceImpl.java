package com.example.MiHistoriaClinica.service.implementation;

import com.example.MiHistoriaClinica.persistence.model.Patient;
import com.example.MiHistoriaClinica.persistence.model.Medic;
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
            String subject = "¡Bienvenido a Mi Historia Clínica!";
            String htmlContent = buildWelcomeEmailHtml(patient);
            
            sendEmail(patient.getEmail(), subject, htmlContent);
            logger.info("Email de bienvenida enviado exitosamente a: {}", patient.getEmail());
            
        } catch (Exception e) {
            logger.error("Error enviando email de bienvenida: ", e);
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

    private String buildVerificationEmailHtml(Patient patient, String verificationUrl) {
        return String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Verificación de Email</title>
                <style>
                    body {
                        font-family: 'Arial', sans-serif;
                        margin: 0;
                        padding: 0;
                        background-color: #f5f5f5;
                    }
                    .container {
                        max-width: 600px;
                        margin: 20px auto;
                        background-color: white;
                        border-radius: 10px;
                        box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                        overflow: hidden;
                    }
                    .header {
                        background: #4A90E2;
                        color: white;
                        padding: 40px 20px;
                        text-align: center;
                    }
                    .header h1 {
                        margin: 0;
                        font-size: 28px;
                        font-weight: bold;
                    }
                    .content {
                        padding: 40px 30px;
                        color: #333;
                    }
                    .content h2 {
                        color: #4A90E2;
                        font-size: 24px;
                        margin-bottom: 20px;
                    }
                    .content p {
                        line-height: 1.6;
                        font-size: 16px;
                        margin-bottom: 20px;
                        color: #555;
                    }
                    .button-container {
                        text-align: center;
                        margin: 30px 0;
                    }
                    .verify-button {
                        display: inline-block;
                        background: #4A90E2;
                        color: white !important;
                        padding: 15px 40px;
                        text-decoration: none;
                        border-radius: 5px;
                        font-weight: bold;
                        font-size: 16px;
                        box-shadow: 0 2px 8px rgba(74, 144, 226, 0.3);
                    }
                    .verify-button:hover {
                        background: #357ABD;
                    }
                    .info-box {
                        background-color: #E8F4FD;
                        border-left: 4px solid #4A90E2;
                        padding: 15px;
                        margin: 20px 0;
                        border-radius: 5px;
                    }
                    .footer {
                        background-color: #f8f9fa;
                        padding: 20px;
                        text-align: center;
                        color: #777;
                        font-size: 14px;
                    }
                    .footer a {
                        color: #4A90E2;
                        text-decoration: none;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>Mi Historia Clínica</h1>
                    </div>
                    <div class="content">
                        <h2>¡Bienvenido/a, %s!</h2>
                        <p>Gracias por registrarte en <strong>Mi Historia Clínica</strong>. Estamos emocionados de tenerte en nuestra plataforma.</p>
                        
                        <p>Para completar tu registro y comenzar a utilizar todos los servicios, necesitamos verificar tu dirección de correo electrónico.</p>
                        
                        <div class="button-container">
                            <a href="%s" class="verify-button">Verificar mi Email</a>
                        </div>
                        
                        <div class="info-box">
                            <p style="margin: 0; color: #555;"><strong>Importante:</strong> Este enlace expirará en 24 horas por razones de seguridad.</p>
                        </div>
                        
                        <p>Si no puedes hacer clic en el botón, copia y pega el siguiente enlace en tu navegador:</p>
                        <p style="word-break: break-all; color: #4A90E2; font-size: 14px;">%s</p>
                        
                        <p style="margin-top: 30px; font-size: 14px; color: #888;">Si no te registraste en Mi Historia Clínica, puedes ignorar este correo de forma segura.</p>
                    </div>
                    <div class="footer">
                        <p><strong>Mi Historia Clínica</strong><br>
                        Universidad Austral - Facultad de Ingeniería<br>
                        Pilar, Buenos Aires, Argentina</p>
                        <p style="margin-top: 10px;">
                            ¿Necesitas ayuda? <a href="mailto:mihistoriaclinica.austral@gmail.com">Contáctanos</a>
                        </p>
                    </div>
                </div>
            </body>
            </html>
            """, 
            patient.getName(),
            verificationUrl,
            verificationUrl
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
                    body {
                        font-family: 'Arial', sans-serif;
                        margin: 0;
                        padding: 0;
                        background-color: #f5f5f5;
                    }
                    .container {
                        max-width: 600px;
                        margin: 20px auto;
                        background-color: white;
                        border-radius: 10px;
                        box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                        overflow: hidden;
                    }
                    .header {
                        background: #4A90E2;
                        color: white;
                        padding: 40px 20px;
                        text-align: center;
                    }
                    .header h1 {
                        margin: 0;
                        font-size: 28px;
                        font-weight: bold;
                    }
                    .content {
                        padding: 40px 30px;
                        color: #333;
                    }
                    .content h2 {
                        color: #4A90E2;
                        font-size: 24px;
                        margin-bottom: 20px;
                    }
                    .content p {
                        line-height: 1.6;
                        font-size: 16px;
                        margin-bottom: 20px;
                        color: #555;
                    }
                    .feature-list {
                        background-color: #f8f9fa;
                        padding: 20px;
                        border-radius: 8px;
                        margin: 20px 0;
                    }
                    .feature-item {
                        padding: 10px 0;
                        border-bottom: 1px solid #e9ecef;
                        color: #555;
                    }
                    .feature-item:last-child {
                        border-bottom: none;
                    }
                    .feature-item strong {
                        color: #4A90E2;
                    }
                    .footer {
                        background-color: #f8f9fa;
                        padding: 20px;
                        text-align: center;
                        color: #777;
                        font-size: 14px;
                    }
                    .footer a {
                        color: #4A90E2;
                        text-decoration: none;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>¡Cuenta Verificada!</h1>
                    </div>
                    <div class="content">
                        <h2>¡Hola, %s %s!</h2>
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
                        
                        <p style="margin-top: 30px;">Ya puedes <strong>iniciar sesión</strong> en la plataforma y comenzar a utilizar todos nuestros servicios.</p>
                        
                        <p style="font-size: 14px; color: #888; margin-top: 30px;">Si tienes alguna pregunta o necesitas ayuda, no dudes en contactarnos.</p>
                    </div>
                    <div class="footer">
                        <p><strong>Mi Historia Clínica</strong><br>
                        Universidad Austral - Facultad de Ingeniería<br>
                        Pilar, Buenos Aires, Argentina</p>
                        <p style="margin-top: 10px;">
                            <a href="mailto:mihistoriaclinica.austral@gmail.com">mihistoriaclinica.austral@gmail.com</a>
                        </p>
                    </div>
                </div>
            </body>
            </html>
            """, 
            patient.getName(),
            patient.getLastname()
        );
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
            String subject = "¡Bienvenido Dr/Dra. " + medic.getLastname() + " a Mi Historia Clínica!";
            String htmlContent = buildMedicWelcomeEmailHtml(medic);
            
            sendEmail(medic.getEmail(), subject, htmlContent);
            logger.info("Email de bienvenida enviado exitosamente a médico: {}", medic.getEmail());
            
        } catch (Exception e) {
            logger.error("Error enviando email de bienvenida a médico: ", e);
        }
    }

    private String buildMedicVerificationEmailHtml(Medic medic, String verificationUrl) {
        return String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Verificación de Cuenta Médica</title>
                <style>
                    body {
                        font-family: 'Arial', sans-serif;
                        margin: 0;
                        padding: 0;
                        background-color: #f5f5f5;
                    }
                    .container {
                        max-width: 600px;
                        margin: 20px auto;
                        background-color: white;
                        border-radius: 10px;
                        box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                        overflow: hidden;
                    }
                    .header {
                        background: #4A90E2;
                        color: white;
                        padding: 40px 20px;
                        text-align: center;
                    }
                    .header h1 {
                        margin: 0;
                        font-size: 28px;
                        font-weight: bold;
                    }
                    .header p {
                        margin: 10px 0 0 0;
                        font-size: 14px;
                        opacity: 0.9;
                    }
                    .content {
                        padding: 40px 30px;
                        color: #333;
                    }
                    .content h2 {
                        color: #4A90E2;
                        font-size: 24px;
                        margin-bottom: 20px;
                    }
                    .content p {
                        line-height: 1.6;
                        font-size: 16px;
                        margin-bottom: 20px;
                        color: #555;
                    }
                    .button-container {
                        text-align: center;
                        margin: 30px 0;
                    }
                    .verify-button {
                        display: inline-block;
                        background: #4A90E2;
                        color: white !important;
                        padding: 15px 40px;
                        text-decoration: none;
                        border-radius: 5px;
                        font-weight: bold;
                        font-size: 16px;
                        box-shadow: 0 2px 8px rgba(74, 144, 226, 0.3);
                    }
                    .verify-button:hover {
                        background: #357ABD;
                    }
                    .info-box {
                        background-color: #E8F4FD;
                        border-left: 4px solid #4A90E2;
                        padding: 15px;
                        margin: 20px 0;
                        border-radius: 5px;
                    }
                    .credential-box {
                        background-color: #f8f9fa;
                        padding: 15px;
                        border-radius: 5px;
                        margin: 20px 0;
                    }
                    .footer {
                        background-color: #f8f9fa;
                        padding: 20px;
                        text-align: center;
                        color: #777;
                        font-size: 14px;
                    }
                    .footer a {
                        color: #4A90E2;
                        text-decoration: none;
                    }
                    ul {
                        color: #555;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>Mi Historia Clínica</h1>
                        <p>Portal Médico Profesional</p>
                    </div>
                    <div class="content">
                        <h2>Verificación de Cuenta Médica</h2>
                        <p>Estimado/a <strong>Dr/Dra. %s %s</strong>,</p>
                        <p>Gracias por registrarte como profesional médico en nuestra plataforma. Para activar tu cuenta y comenzar a atender pacientes, necesitamos verificar tu dirección de correo electrónico.</p>
                        
                        <div class="button-container">
                            <a href="%s" class="verify-button">Verificar mi Cuenta Médica</a>
                        </div>
                        
                        <div class="info-box">
                            <p style="margin: 0; color: #555;"><strong>Importante:</strong> Este enlace expirará en 24 horas por razones de seguridad.</p>
                        </div>
                        
                        <div class="credential-box">
                            <p style="margin: 5px 0;"><strong>Matrícula profesional:</strong> %s</p>
                            <p style="margin: 5px 0;"><strong>Especialidad:</strong> %s</p>
                        </div>
                        
                        <p style="margin-top: 30px;">Una vez verificada tu cuenta, podrás:</p>
                        <ul style="line-height: 1.8;">
                            <li>Crear y gestionar historias clínicas</li>
                            <li>Vincular y atender pacientes</li>
                            <li>Gestionar tu agenda de turnos</li>
                            <li>Prescribir medicamentos</li>
                            <li>Solicitar estudios médicos</li>
                            <li>Acceder al historial completo de tus pacientes</li>
                        </ul>
                        
                        <p>Si el botón no funciona, copia y pega el siguiente enlace en tu navegador:</p>
                        <p style="word-break: break-all; color: #4A90E2; font-size: 14px;">%s</p>
                        
                        <p style="margin-top: 30px; font-size: 14px; color: #888;">Si no te registraste como médico en Mi Historia Clínica, puedes ignorar este correo de forma segura.</p>
                    </div>
                    <div class="footer">
                        <p><strong>Mi Historia Clínica</strong><br>
                        Universidad Austral - Facultad de Ingeniería<br>
                        Pilar, Buenos Aires, Argentina</p>
                        <p style="margin-top: 10px;">
                            <a href="mailto:mihistoriaclinica.austral@gmail.com">mihistoriaclinica.austral@gmail.com</a>
                        </p>
                    </div>
                </div>
            </body>
            </html>
            """, 
            medic.getName(),
            medic.getLastname(),
            verificationUrl,
            medic.getMatricula(),
            medic.getSpecialty() != null ? medic.getSpecialty().getName() : "No especificada",
            verificationUrl
        );
    }

    private String buildMedicWelcomeEmailHtml(Medic medic) {
        return String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Bienvenida - Cuenta Médica Activada</title>
                <style>
                    body {
                        font-family: 'Arial', sans-serif;
                        margin: 0;
                        padding: 0;
                        background-color: #f5f5f5;
                    }
                    .container {
                        max-width: 600px;
                        margin: 20px auto;
                        background-color: white;
                        border-radius: 10px;
                        box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                        overflow: hidden;
                    }
                    .header {
                        background: #4A90E2;
                        color: white;
                        padding: 40px 20px;
                        text-align: center;
                    }
                    .header h1 {
                        margin: 0;
                        font-size: 28px;
                        font-weight: bold;
                    }
                    .content {
                        padding: 40px 30px;
                        color: #333;
                    }
                    .content h2 {
                        color: #4A90E2;
                        font-size: 24px;
                        margin-bottom: 20px;
                    }
                    .content p {
                        line-height: 1.6;
                        font-size: 16px;
                        margin-bottom: 20px;
                        color: #555;
                    }
                    .feature-list {
                        background-color: #f8f9fa;
                        padding: 20px;
                        border-radius: 8px;
                        margin: 20px 0;
                    }
                    .feature-item {
                        padding: 12px 0;
                        border-bottom: 1px solid #e9ecef;
                        color: #555;
                    }
                    .feature-item:last-child {
                        border-bottom: none;
                    }
                    .feature-item strong {
                        color: #4A90E2;
                    }
                    .footer {
                        background-color: #f8f9fa;
                        padding: 20px;
                        text-align: center;
                        color: #777;
                        font-size: 14px;
                    }
                    .footer a {
                        color: #4A90E2;
                        text-decoration: none;
                    }
                    .credentials-box {
                        background-color: #E8F4FD;
                        border-left: 4px solid #4A90E2;
                        padding: 20px;
                        border-radius: 5px;
                        margin: 20px 0;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>¡Cuenta Médica Verificada!</h1>
                    </div>
                    <div class="content">
                        <h2>¡Bienvenido/a, Dr/Dra. %s %s!</h2>
                        <p>Tu cuenta médica ha sido verificada exitosamente. Ya puedes acceder a todas las funcionalidades profesionales de <strong>Mi Historia Clínica</strong>.</p>
                        
                        <div class="credentials-box">
                            <p style="margin: 0; font-size: 18px; color: #333;"><strong>Datos de tu cuenta:</strong></p>
                            <p style="margin: 10px 0 0 0; color: #555;"><strong>Matrícula:</strong> %s</p>
                            <p style="margin: 5px 0 0 0; color: #555;"><strong>Especialidad:</strong> %s</p>
                            <p style="margin: 5px 0 0 0; color: #555;"><strong>Email:</strong> %s</p>
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
                            <div class="feature-item">
                                <strong>Consultas médicas:</strong> Registra cada atención con detalles completos
                            </div>
                        </div>
                        
                        <p style="margin-top: 30px; font-size: 18px; text-align: center; color: #4A90E2;">
                            <strong>Ya puedes iniciar sesión y comenzar a atender pacientes</strong>
                        </p>
                        
                        <p style="font-size: 14px; color: #888; margin-top: 30px;">Si necesitas ayuda para comenzar, no dudes en contactarnos.</p>
                    </div>
                    <div class="footer">
                        <p><strong>Mi Historia Clínica</strong><br>
                        Universidad Austral - Facultad de Ingeniería<br>
                        Pilar, Buenos Aires, Argentina</p>
                        <p style="margin-top: 10px;">
                            <a href="mailto:mihistoriaclinica.austral@gmail.com">mihistoriaclinica.austral@gmail.com</a>
                        </p>
                    </div>
                </div>
            </body>
            </html>
            """, 
            medic.getName(),
            medic.getLastname(),
            medic.getMatricula(),
            medic.getSpecialty() != null ? medic.getSpecialty().getName() : "No especificada",
            medic.getEmail()
        );
    }
}

