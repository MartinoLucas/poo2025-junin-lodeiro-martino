package com.poo.proyecto.dto.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class EmailDTO {
    private final String value;

    @JsonCreator
    public EmailDTO(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() { return value; }
}
