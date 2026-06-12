package br.maua.domain;

import java.io.File; 

public class RespostaUpload extends Resposta{
    private File arquivo;
    private String nomeArquivo;

     public String gerarRespostaBanco(String arquivo_resposta){
        String sql = String.format("(%s)", arquivo_resposta);
        return sql;
    }

    public File getArquivo() {
        return arquivo;
    }

    public void setArquivo(File arquivo) {
        this.arquivo = arquivo;
    } 

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }
}
