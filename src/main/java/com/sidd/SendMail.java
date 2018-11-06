package com.sidd;

import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import java.util.Optional;
import java.util.logging.Level;

public class SendMail {

    @FunctionName("sendMail")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET, HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        String to   = request.getQueryParameters().get("to");

        if(null == to)
            to = "siddharth.patnaik@walmartlabs.com";

        String from = "siddharth.patnaik@walmartlabs.com";
        context.getLogger().info("To: " + to);
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", "smtp-gw1.wal-mart.com");
        Session session = Session.getDefaultInstance(properties);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Hybrid connection testing");
            message.setText("Hybrid connection testing");
            Transport.send(message);
            return request.createResponseBuilder(HttpStatus.OK).body("Mail sent successfully....").build();
        } catch (MessagingException mex)
        {
            context.getLogger().log(Level.SEVERE, mex.getMessage(), mex);
            mex.printStackTrace();
            return request.createResponseBuilder(HttpStatus.valueOf(500)).body("Failed. " + mex.getMessage()).build();
        }
    }
}
