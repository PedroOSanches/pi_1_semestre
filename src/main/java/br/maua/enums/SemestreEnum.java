package br.maua.enums;

import java.sql.SQLException;

public enum SemestreEnum {
    PRIMEIRO("Primeiro", "primeiro", "1º"),
    SEGUNDO("Segundo", "segundo", "2º");

    private final String semestre;
    private final String semestreLower;
    private final String semestreOrdinal;

    SemestreEnum(String semestre, String semestreLower, String semestreOrdinal) {
        this.semestre = semestre;
        this.semestreLower = semestreLower;
        this.semestreOrdinal = semestreOrdinal;
    }

    public String getSemestreLower() {
        return semestreLower;
    }

    public String getSemestreOrdinal() {
        return semestreOrdinal;
    }

    public static SemestreEnum descobreSemestre(String semestre){
        if(semestre.equalsIgnoreCase("primeiro")){
            return SemestreEnum.PRIMEIRO;
        }
        return SemestreEnum.SEGUNDO;
    }
    public String toString() {
        return this.semestre;
    }
}
