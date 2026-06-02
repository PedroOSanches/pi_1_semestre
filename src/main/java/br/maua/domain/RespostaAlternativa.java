package br.maua.domain;

public class RespostaAlternativa extends Resposta {
    private int idAlternativa;

    public String gerarRespostaBanco(int idAlternativa) {
        String sql = String.format("(%d)", idAlternativa);
        return sql;
    }

    public int getIdAlternativa() {
        return idAlternativa;
    }

    public void setIdAlternativa(int idAlternativa) {
        this.idAlternativa = idAlternativa;
    }
}