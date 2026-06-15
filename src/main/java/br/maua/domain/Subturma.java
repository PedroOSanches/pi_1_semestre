package br.maua.domain;

import java.security.InvalidParameterException;
import java.util.regex.Pattern;

public class Subturma {
    int idSubTurma;
    String codSubTurma;

    public Subturma(int idSubTurma, String codSubTurma) {
        setCodSubTurma(codSubTurma);
        setIdSubTurma(idSubTurma);
    }

    public Subturma(String codSubTurma) {
        setCodSubTurma(codSubTurma);
    }

    public int getIdSubTurma() {
        return idSubTurma;
    }

    public void setIdSubTurma(int idSubTurma) {
        this.idSubTurma = idSubTurma;
    }

    public String getCodSubTurma() {
        return codSubTurma;
    }

    public void setCodSubTurma(String codSubTurma) {
        Pattern pattern = Pattern.compile("^SUB\\d{2}$");

        if (codSubTurma.equals("Carregando...")) {
            this.codSubTurma = codSubTurma;
            return;
        }

        if (!pattern.matcher(codSubTurma).matches()) {
            throw new InvalidParameterException("Código Subturma Inválido");
        }

        this.codSubTurma = codSubTurma;
    }

    @Override
    public String toString() {
        return codSubTurma;
    }
}
