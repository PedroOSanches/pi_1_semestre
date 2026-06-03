package br.maua.domain;

public class RespostaUpload extends Resposta{
    private String caminhoArquivo;
    private String nomeArquivo;

     public String gerarRespostaBanco(String arquivo_resposta){
        String sql = String.format("(%s)", arquivo_resposta);
        return sql;
    }

    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }

    public void setCaminhoArquivo(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    } 

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }
}
