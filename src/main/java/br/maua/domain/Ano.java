package br.maua.domain;

import java.time.Year;

public class Ano {
    private int idAno;
    private Year ano;

    public Ano(int idAno, Year ano) {
        setIdAno(idAno);
        setAno(ano);
    }

    public Ano(Year ano) {
        setAno(ano);
    }

    public int getIdAno() {
        return idAno;
    }

    public void setIdAno(int idAno) {
        this.idAno = idAno;
    }

    public Year getAno() {
        return ano;
    }

    public void setAno(Year ano) {
        this.ano = ano;
    }

    @Override
    public String toString() {
        try {
            return ano.toString();
        } catch (Exception e) {
            return "Carregando...";
        }
    }
}
