package it.epicode.bwfinalboss.service;

import it.epicode.bwfinalboss.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void inviaBenvenuto(Cliente cliente) {
        if (cliente.getEmailContatto() == null || cliente.getEmailContatto().isBlank()) return;

        SimpleMailMessage messaggio = new SimpleMailMessage();
        messaggio.setTo(cliente.getEmailContatto());
        messaggio.setSubject("Benvenuto in piattaforma!");
        messaggio.setText("Ciao " + cliente.getNomeContatto() + ", " +
                "grazie per esserti registrato alla nostra piattaforma. " +
                "Siamo felici di collaborare con " + cliente.getRagioneSociale() + " " +
                "Cordiali saluti. Il team 2");
        javaMailSender.send(messaggio);
    }
}
