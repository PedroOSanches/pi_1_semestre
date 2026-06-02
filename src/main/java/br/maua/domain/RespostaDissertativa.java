package br.maua.domain;

public class RespostaDissertativa {
     public String gerarRespostaBanco(String resposta){
        String sql = String.format("(%s)", resposta);
        return sql;
    }
}
