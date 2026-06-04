package br.maua.domain;

public class RespostaDissertativa extends Resposta{
    private String textoResposta;

     public String gerarRespostaBanco(String resposta){
        String sql = String.format("(%s)", resposta);
        return sql;
    }


    public String getTextoResposta() { 
        return textoResposta; 
    }
    public void setTextoResposta(String textoResposta) {
         this.textoResposta = textoResposta; 
    }
    
}
