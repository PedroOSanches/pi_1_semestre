package br.maua.domain;

public class RespostaAlternativa extends Resposta {

    private int idAlternativaAssinalada;
    private QuestaoAlternativa questao;

    public int getIdAlternativaAssinalada() {
        return idAlternativaAssinalada;
    }

    public void setIdAlternativaAssinalada(int idAlternativaAssinalada) {
        this.idAlternativaAssinalada = idAlternativaAssinalada;
    }

    public QuestaoAlternativa getQuestao() {
        return questao;
    }

    public void setQuestao(QuestaoAlternativa questao) {
        this.questao = questao;
    }

    public String gerarRespostaBanco(int idAlternativa){
        String sql = String.format("(%d)", idAlternativa);
        return sql;
    }
}
