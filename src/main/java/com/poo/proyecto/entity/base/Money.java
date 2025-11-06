package com.poo.proyecto.entity.base;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;
import java.util.Objects;

@Embeddable // indica que esta clase se puede incrustar en una entidad
public class Money {

    @Column(name = "amount", precision = 12, scale = 2, nullable = false)
    @DecimalMin("0.00")
    private BigDecimal amount;

    @Column(name = "currency", length = 3, nullable = false)
    private String currency = "ARS";

    protected Money() { }

    public Money(BigDecimal amount) {
        this.amount = amount == null ? null : amount.setScale(2);
    }

    public static Money of(BigDecimal amount) { return new Money(amount); }

    public BigDecimal amount() { return amount; }
    public String currency() { return currency; }

    // helpers de dominio (no rompen el “solo models”)
    public Money add(Money other) { return new Money(this.amount.add(other.amount)); }
    public Money multiply(BigDecimal factor) { return new Money(this.amount.multiply(factor)); }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money m)) return false;
        return Objects.equals(amount, m.amount) && Objects.equals(currency, m.currency);
    }
    @Override public int hashCode() { return Objects.hash(amount, currency); }
    @Override public String toString() { return amount + " " + currency; }
}
