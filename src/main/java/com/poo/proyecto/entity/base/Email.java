package com.poo.proyecto.entity.base;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Email {

    @Column(name = "email", length = 255, nullable = false)
    private String value;

    public Email() { }

    public Email(String raw) {
        this.value = normalize(raw);
    }

    public String value() { return value; }

    private static String normalize(String v) {
        return v == null ? null : v.trim().toLowerCase();
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
