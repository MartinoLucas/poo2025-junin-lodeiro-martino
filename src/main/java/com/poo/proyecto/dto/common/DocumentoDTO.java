package com.poo.proyecto.dto.common;

import com.poo.proyecto.entity.TipoDocumento;

public class DocumentoDTO {
    private TipoDocumento tipo;
    private String numero;

    public TipoDocumento getTipo() { return tipo; }
    public void setTipo(TipoDocumento tipo) { this.tipo = tipo; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
}
