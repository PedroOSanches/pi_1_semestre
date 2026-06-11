package br.maua.enums;

public enum SemestreEnum {
    PRIMEIRO("Primeiro", "primeiro"),
    SEGUNDO("Segundo", "segundo");

    private final String semestre;
    private final String semestreLower;

    SemestreEnum(String semestre, String semestreLower) {
        this.semestre = semestre;
        this.semestreLower = semestreLower;
    }

    public String getSemestreLower() {
        return semestreLower;
    }

    public String toString() {
        return this.semestre;
    }
}
