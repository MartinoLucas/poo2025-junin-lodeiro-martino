package com.poo.proyecto.api;

public class ApiResponse<T> {
    private int code;
    private String message;
    private T body;

    public ApiResponse() {}

    public ApiResponse(int code, String message, T body) {
        this.code = code;
        this.message = message;
        this.body = body;
    }

    public static <T> ApiResponse<T> ok(T body) {
        return new ApiResponse<>(200, "OK", body);
    }

    public static <T> ApiResponse<T> created(T body) {
        return new ApiResponse<>(201, "Created", body);
    }

    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public T getBody() { return body; }
    public void setBody(T body) { this.body = body; }
}
