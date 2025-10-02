package com.poo.proyecto.entity.base;
import com.poo.proyecto.entity.TipoDocumento;
import jakarta.persistence.*;

@Embeddable
public class Documento {

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento", length = 20, nullable = false)
    private TipoDocumento tipo;

    @Column(name = "numero_documento", length = 20, nullable = false)
    private String numero;

    protected Documento() { }

    public Documento(TipoDocumento tipo, String numero) {
        this.tipo = tipo;
        this.numero = numero;
    }

    public TipoDocumento tipo() { return tipo; }
    public String numero() { return numero; }
}
