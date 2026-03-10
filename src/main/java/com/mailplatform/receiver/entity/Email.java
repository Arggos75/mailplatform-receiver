package com.mailplatform.receiver.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "emails")
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "from_addr", length = 512)
    private String fromAddr;

    @Column(name = "to_addr", length = 512)
    private String toAddr;

    @Column(name = "recipient", length = 512)
    private String recipient;

    @Column(name = "sender", length = 512)
    private String sender;

    @Column(name = "subject", length = 1000)
    private String subject;

    @Column(name = "body_plain", columnDefinition = "TEXT")
    private String bodyPlain;

    @Column(name = "body_html", columnDefinition = "TEXT")
    private String bodyHtml;

    @Column(name = "stripped_text", columnDefinition = "TEXT")
    private String strippedText;

    @Column(name = "received_at", nullable = false)
    private Instant receivedAt;

    @Column(name = "direction", length = 20, nullable = false)
    private String direction = "inbound";

    public Email() {
    }

    public UUID getId() {
        return id;
    }

    public String getFromAddr() {
        return fromAddr;
    }

    public void setFromAddr(String fromAddr) {
        this.fromAddr = fromAddr;
    }

    public String getToAddr() {
        return toAddr;
    }

    public void setToAddr(String toAddr) {
        this.toAddr = toAddr;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBodyPlain() {
        return bodyPlain;
    }

    public void setBodyPlain(String bodyPlain) {
        this.bodyPlain = bodyPlain;
    }

    public String getBodyHtml() {
        return bodyHtml;
    }

    public void setBodyHtml(String bodyHtml) {
        this.bodyHtml = bodyHtml;
    }

    public String getStrippedText() {
        return strippedText;
    }

    public void setStrippedText(String strippedText) {
        this.strippedText = strippedText;
    }

    public Instant getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(Instant receivedAt) {
        this.receivedAt = receivedAt;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
