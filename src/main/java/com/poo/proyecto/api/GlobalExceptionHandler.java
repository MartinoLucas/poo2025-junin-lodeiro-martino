package com.poo.proyecto.api;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFound(EntityNotFoundException ex, HttpServletRequest request) {
        return new ApiError(
                "resource_not_found",
                "Recurso no encontrado",
                404,
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String detail = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining("; "));

        return new ApiError(
                "invalid_payload",
                "Datos inválidos enviados",
                400,
                detail,
                request.getRequestURI()
        );
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleIllegalState(IllegalStateException ex, HttpServletRequest request) {
        return new ApiError(
                "invalid_state",
                "Operación inválida en el estado actual",
                409,
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleGeneric(Exception ex, HttpServletRequest request) {
        // En prod podrías loguear el trace con un ID
        return new ApiError(
                "internal_error",
                "Error inesperado",
                500,
                ex.getMessage(),
                request.getRequestURI()
        );
    }
}
