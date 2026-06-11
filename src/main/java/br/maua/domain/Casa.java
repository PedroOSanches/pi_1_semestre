package br.maua.domain;

public class Casa {
    private int idCasa;
    private String titulo;
    private int ordemCasa;
    private Secao secao;

    public Casa(String titulo) {
        setTitulo(titulo);
    }
    public Casa(String titulo, int idCasa) {
        setTitulo(titulo);
        setIdCasa(idCasa);
    }

    public int getOrdemCasa() {
        return ordemCasa;
    }

    public void avancarCasa() {

    }

    public String toString(){
            return titulo;
    }

    public int getIdCasa() {
        return idCasa;
    }

    public void setIdCasa(int id) {
        this.idCasa = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setOrdemCasa(int ordemCasa) {
        this.ordemCasa = ordemCasa;
    }

    public Secao getSecao() {
        return secao;
    }

    public void setSecao(Secao secao) {
        this.secao = secao;
    }
}

