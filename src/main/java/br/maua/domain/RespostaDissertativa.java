package br.maua.domain;

public class RespostaDissertativa extends Resposta {

    private String respostaAluno;

    public String getRespostaAluno() {
        return respostaAluno;
    }

    public void setRespostaAluno(String respostaAluno) {
        this.respostaAluno = respostaAluno;
    }

    public String gerarRespostaBanco(String resposta){
        String sql = String.format("(%s)", resposta);
        return sql;
    }
}
