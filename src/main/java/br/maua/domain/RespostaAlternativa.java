package br.maua.domain;

public class RespostaAlternativa {
     public String gerarRespostaBanco(int idAlternativa){
        String sql = String.format("(%d)", idAlternativa);
        return sql;
    }
}
