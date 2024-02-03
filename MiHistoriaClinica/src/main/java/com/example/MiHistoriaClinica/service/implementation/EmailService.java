package com.example.MiHistoriaClinica.service.implementation;

import com.example.MiHistoriaClinica.util.exception.InvalidTokenException;
import com.example.MiHistoriaClinica.util.jwt.JwtGenerator;
import com.example.MiHistoriaClinica.util.jwt.JwtGeneratorImpl;
import com.example.MiHistoriaClinica.util.jwt.JwtValidator;
import com.example.MiHistoriaClinica.util.jwt.JwtValidatorImpl;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.*;
import com.sendgrid.helpers.mail.objects.Email;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EmailService {

    @Value("${sendgrid.api-key}")
    private String sendGridApiKey;
    private static final String CONFIRMATION_TEMPLATE_ID = "d-2a79a8b8dd90406e8d0da24c0ff466f6";
    private final JwtGenerator jwt = new JwtGeneratorImpl();
    private final JwtValidator jwtValidator = new JwtValidatorImpl(jwt);


    public void sendConfirmationEmail(String to, String confirmationLink, String token) {
        Mail mail = getEmailContent(to, confirmationLink);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();

        try {
            // Añadir el token como encabezado
            mail.addHeader("Authorization", "Bearer " + token);

            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Mail getEmailContent(String to, String confirmationLink) {
        Email from = new Email("mihistoriaclinica.usuarios@gmail.com");
        Email toEmail = new Email(to);

        // Objeto personalizable para la plantilla
        Personalization personalization = new Personalization();
        personalization.addTo(toEmail);
        personalization.addDynamicTemplateData("Verifica tu mail", confirmationLink);

        // Crea el contenido de la plantilla
        Mail emailContent = new Mail();
        emailContent.setFrom(from);
        emailContent.setTemplateId(CONFIRMATION_TEMPLATE_ID);
        emailContent.addPersonalization(personalization);
        return emailContent;
    }

/*
    public boolean confirmPatientEmail(String token) throws InvalidTokenException {
        String email = jwt.getEmailFromToken(token);
        Long patientId = jwtValidator.getId(token);

        if (isValidToken(token, email)) {
            patientService.confirmAccount(patientId);
            return true;
        }

        return false;
    }

 */

    private boolean isValidToken(String token, String email) {
       try{
           Claims claims = jwt.getClaims(token);

           boolean isTokenNotExpired = !claims.getExpiration().before(new Date());
           boolean isEmailMatch = email.equals(claims.get("id", String.class));

           return isTokenNotExpired && isEmailMatch;
       } catch (InvalidTokenException e) {
           throw new RuntimeException(e);
       }
    }



}
