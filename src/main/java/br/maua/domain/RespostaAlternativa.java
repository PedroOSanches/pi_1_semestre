package br.maua.domain;

public class RespostaAlternativa extends Resposta {

    private int idAlternativaAssinalada;

    public int getIdAlternativaAssinalada() {
        return idAlternativaAssinalada;
    }

    public void setIdAlternativaAssinalada(int idAlternativaAssinalada) {
        this.idAlternativaAssinalada = idAlternativaAssinalada;
    }

    public String gerarRespostaBanco(int idAlternativa){
        String sql = String.format("(%d)", idAlternativa);
        return sql;
    }
}
