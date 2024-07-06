package com.alura.literalura.model;

public enum Languages {

    EN("en", "Inglés"),
    ES("es", "Español"),
    FR("fr", "Francés"),
    PT("pt", "Portugués");

    private final String code;
    private final String description;

    Languages(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description + " [" + code + "]";
    }
}
