package br.maua.domain;

import java.util.ArrayList;
import java.util.List;

public class Secao {
    private int idSecao;
    private String titulo;
    private List<Casa> casas = new ArrayList<>();


    public Secao(){}
    public Secao(int idSecao, String titulo){
        setidSecao(idSecao);
        setTitulo(titulo);
    }
    public int getidSecao() {
        return idSecao;
    }

    public void setidSecao(int idSecao) {
        this.idSecao = idSecao;
    }

    public String getTitulo() {
        return titulo;
    }

    @Override
    public String toString() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Casa> getCasas() {
        return casas;
    }

    public void setCasas(List<Casa> casas) {
        this.casas = casas;
    }
}
