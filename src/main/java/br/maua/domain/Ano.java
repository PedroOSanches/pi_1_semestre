package br.maua.domain;

import java.time.Year;

public class Ano {
    private int idAno;
    private Year ano;

    public Ano(int idAno, int ano) {
        setIdAno(idAno);
        setAno(ano);
    }

    public Ano(int ano) {
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

    public void setAno(int ano) {
        this.ano = Year.of(ano);
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
