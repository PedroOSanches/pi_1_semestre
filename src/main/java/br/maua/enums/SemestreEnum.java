package br.maua.enums;

public enum SemestreEnum {
    PRIMEIRO("Primeiro"),
    SEGUNDO("Segundo");

    private final String semestre;

    SemestreEnum(String semestre) {
        this.semestre = semestre;
    }

    public String toString() {
        return this.semestre;
    }
}
