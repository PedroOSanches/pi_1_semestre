package br.maua.domain;

import br.maua.enums.SemestreEnum;

import javax.swing.*;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Turma {
    int idTurma;
    String codTurma;

    public Turma(String codTurma) {
        setCodTurma(codTurma);
    }

    public Turma(int idTurma, String codTurma) {
        setIdTurma(idTurma);
        setCodTurma(codTurma);
    }

    public int getIdTurma() {
        return idTurma;
    }

    public void setIdTurma(int idTurma) {
        this.idTurma = idTurma;
    }

    public String getCodTurma() {
        return codTurma;
    }

    public void setCodTurma(String codTurma) {
        Pattern pattern = Pattern.compile("^[A-Z]\\d{2}$");
        if (codTurma.equals("Carregando...")) {
            this.codTurma = codTurma;
        } else if (pattern.matcher(codTurma).find()) {
            this.codTurma = codTurma;
        } else {
            throw new InvalidParameterException("Código Inválido");
        }
    }

    @Override
    public String toString() {
        return codTurma;
    }
}