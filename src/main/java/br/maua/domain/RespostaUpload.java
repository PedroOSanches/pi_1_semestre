package br.maua.domain;

public class RespostaUpload {
     public String gerarRespostaBanco(String arquivo_resposta){
        String sql = String.format("(%s)", arquivo_resposta);
        return sql;
    }
}
