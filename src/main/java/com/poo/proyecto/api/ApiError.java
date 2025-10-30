package com.poo.proyecto.api;

public class ApiError {
    private String type;       // ej: "resource_not_found"
    private String title;      // ej: "Participante no encontrado"
    private int code;          // ej: 404
    private String detail;     // opcional, ej: "No existe el id=123"
    private String instance;   // ej: "/api/participantes/123"

    public ApiError() {}

    public ApiError(String type, String title, int code, String detail, String instance) {
        this.type = type;
        this.title = title;
        this.code = code;
        this.detail = detail;
        this.instance = instance;
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }

    public String getDetail() { return detail; }
    public void setDetail(String detail) { this.detail = detail; }

    public String getInstance() { return instance; }
    public void setInstance(String instance) { this.instance = instance; }
}
