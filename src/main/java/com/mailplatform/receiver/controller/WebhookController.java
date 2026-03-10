package com.mailplatform.receiver.controller;

import com.mailplatform.receiver.entity.Email;
import com.mailplatform.receiver.repository.EmailRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    private final EmailRepository emailRepository;

    public WebhookController(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    @PostMapping(value = "/email", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> receiveEmail(
            @RequestParam(value = "from", required = false) String from,
            @RequestParam(value = "to", required = false) String to,
            @RequestParam(value = "recipient", required = false) String recipient,
            @RequestParam(value = "sender", required = false) String sender,
            @RequestParam(value = "subject", required = false) String subject,
            @RequestParam(value = "body-plain", required = false) String bodyPlain,
            @RequestParam(value = "body-html", required = false) String bodyHtml,
            @RequestParam(value = "stripped-text", required = false) String strippedText,
            @RequestParam(value = "timestamp", required = false) String timestamp
    ) {
        Email email = new Email();
        email.setFromAddr(from);
        email.setToAddr(to);
        email.setRecipient(recipient);
        email.setSender(sender);
        email.setSubject(subject);
        email.setBodyPlain(bodyPlain);
        email.setBodyHtml(bodyHtml);
        email.setStrippedText(strippedText);
        email.setReceivedAt(parseTimestamp(timestamp));

        emailRepository.save(email);

        return ResponseEntity.ok().build();
    }

    private Instant parseTimestamp(String timestamp) {
        if (timestamp == null || timestamp.isBlank()) {
            return Instant.now();
        }
        try {
            long epochSeconds = Long.parseLong(timestamp);
            return Instant.ofEpochSecond(epochSeconds);
        } catch (NumberFormatException e) {
            return Instant.now();
        }
    }
}
