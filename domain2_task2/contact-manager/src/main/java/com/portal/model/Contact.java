package com.portal.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Represents a single contact entry in the system.
 * Follows the JavaBean convention so JSP/JSTL can access
 * properties via ${contact.name} etc.
 */
public class Contact {

    private String id;
    private String name;
    private String email;
    private String phone;
    private String createdAt;

    // No-arg constructor required by JavaBean spec
    public Contact() {
        this.id = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.createdAt = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a"));
    }

    public Contact(String name, String email, String phone) {
        this();
        this.name  = name;
        this.email = email;
        this.phone = phone;
    }

    // ── Getters & Setters ────────────────────────────────────────────────────

    public String getId()        { return id; }
    public void   setId(String id) { this.id = id; }

    public String getName()          { return name; }
    public void   setName(String name) { this.name = name; }

    public String getEmail()           { return email; }
    public void   setEmail(String email) { this.email = email; }

    public String getPhone()           { return phone; }
    public void   setPhone(String phone) { this.phone = phone; }

    public String getCreatedAt()               { return createdAt; }
    public void   setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Contact{id='" + id + "', name='" + name +
               "', email='" + email + "', phone='" + phone + "'}";
    }
}
