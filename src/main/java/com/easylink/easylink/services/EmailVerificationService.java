package com.easylink.easylink.services;

import com.easylink.easylink.entities.VibeAccount;
import com.easylink.easylink.repositories.VibeAccountRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

import java.util.Optional;
import java.util.UUID;

@Service
public class EmailVerificationService {

    private final VibeAccountRepository vibeAccountRepository;
    private final JavaMailSender mailSender;

    @Value("${app.verification.base-url}")
    private String baseUrl;

    public EmailVerificationService(VibeAccountRepository vibeAccountRepository,
                                    JavaMailSender mailSender) {
        this.vibeAccountRepository = vibeAccountRepository;
        this.mailSender = mailSender;
    }

    public boolean verifyToken(String token) {
        Optional<VibeAccount> optionalAccount = vibeAccountRepository.findByEmailVerificationToken(token);

        if (optionalAccount.isEmpty()) return false;

        VibeAccount account = optionalAccount.get();

        if (account.getTokenExpiry() == null || account.getTokenExpiry().isBefore(LocalDateTime.now())) {
            return false;
        }

        account.setIsEmailVerified(true);
        account.setEmailVerificationToken(null);
        account.setTokenExpiry(null);

        vibeAccountRepository.save(account);

        return true;
    }

    public void sendVerificationEmail(VibeAccount account) {
        String token = UUID.randomUUID().toString();
        account.setEmailVerificationToken(token);
        account.setTokenExpiry(LocalDateTime.now().plusHours(24));
        vibeAccountRepository.save(account);

        String link = baseUrl + "/api/v3/auth/verify-email?token=" + token;

        String html = "<!DOCTYPE html>" +
                "<html><head><meta charset=\"UTF-8\"></head><body>" +
                "<table style=\"width:100%;background-color:#FFFFFF;padding:20px 0;\">" +
                "<tr><td>" +
                "<table align=\"center\" style=\"font-family:Arial,sans-serif;width:600px;background:#fff;padding:20px;border:1px solid #eee;border-radius:10px;\">" +
                "<tr><td>" +
                "<h2 style=\"color:#4CAF50;margin-top:0\">Verify Your Email</h2>" +
                "<p style=\"font-size:16px;color:#333;\">Hello,</p>" +
                "<p style=\"font-size:16px;color:#333;\">Thanks for signing up. Please click the button below to verify your email address:</p>" +
                "<a href=\"" + link + "\" target=\"_blank\" rel=\"noopener noreferrer\" style=\"display:inline-block;padding:12px 24px;background-color:#4CAF50;color:white;text-decoration:none;border-radius:5px;margin:20px 0;font-size:16px;\">Verify Email</a>" +
                "<p style=\"margin-top:20px;font-size:14px;color:#444;\">Or open this link in your browser:</p>" +
                "<p style=\"word-break:break-all;font-size:13px;color:#1a0dab;\">" + link + "</p>" +
                "<p style=\"margin-top:20px;font-size:12px;color:#777;\">If you did not request this, please ignore this email.</p>" +
                "</td></tr></table>" +
                "</td></tr></table>" +
                "</body></html>";


        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(account.getEmail());
            helper.setSubject("Verify your email");
            helper.setText(html, true); // true = is HTML

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send verification email", e);
        }
    }
}
