package com.poo.proyecto.entity.base;
import com.poo.proyecto.entity.TipoDocumento;
import jakarta.persistence.*;

@Embeddable // indica que esta clase se incrusta dentro de otra tabla. Sus atributos se guardan como columnas dentro de la entidad donde se usa.
public class Documento {

    @Enumerated(EnumType.STRING) // se guarda en la base de datos como tipo string
    @Column(name = "tipo_documento", length = 20, nullable = false)
    private TipoDocumento tipo;

    @Column(name = "numero_documento", length = 20, nullable = false)
    private String numero;

    protected Documento() { }

    public Documento(TipoDocumento tipo, String numero) {
        this.tipo = tipo;
        this.numero = numero;
    }

    public TipoDocumento getTipo() { return tipo; }
    public String getNumero() { return numero; }
}
