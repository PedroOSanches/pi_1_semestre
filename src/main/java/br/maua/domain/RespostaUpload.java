package br.maua.domain;

import br.maua.exception.UpdateException;
import br.maua.infrastructure.DAO.RespostaUploadDAO;
import br.maua.service.ArquivoService;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

public class RespostaUpload extends Resposta{
    private File arquivo;
    private String nomeArquivo;

    public RespostaUpload() {
    }
    public RespostaUpload(
            Tentativa tentativa,
            QuestaoUpload questao,
            File arquivo
    ) {
        super(tentativa, questao);
        setArquivo(arquivo);
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


    @Override
    public void commitResposta(Connection cx) throws SQLException, UpdateException {
        File arquivoDestino = ArquivoService.gerarArquivoDestino(this);
        File arquivoSalvo = ArquivoService.salvarArquivo(arquivo, arquivoDestino);
        RespostaUploadDAO.commitResposta(cx, this, arquivoSalvo);
    }
}
