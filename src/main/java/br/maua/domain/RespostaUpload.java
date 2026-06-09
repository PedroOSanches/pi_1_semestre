package br.maua.domain;

public class RespostaUpload extends Resposta {

    private String pathArquivo;

    public String getPathArquivo() {
        return pathArquivo;
    }

    public void setPathArquivo(String pathArquivo) {
        this.pathArquivo = pathArquivo;
    }

    public String gerarRespostaBanco(String arquivo_resposta){
        String sql = String.format("(%s)", arquivo_resposta);
        return sql;
    }
}
