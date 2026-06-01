package br.maua.domain;

public class Alternativa {

    private int idAlternativa;
    private String enunciado;
    private boolean alternativaAssinalada;

    public int getIdAlternativa() {
        return idAlternativa;
    }

    public void setIdAlternativa(int idAlternativa) {
        this.idAlternativa = idAlternativa;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public boolean isAlternativaAssinalada() {
        return alternativaAssinalada;
    }

    public void setAlternativaAssinalada(boolean alternativaAssinalada) {
        this.alternativaAssinalada = alternativaAssinalada;
    }
}
