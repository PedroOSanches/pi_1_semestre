package br.maua.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.maua.infrastructure.ConnectionFactory;

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

    public void salvar() {
        String sqlResposta = "INSERT INTO resposta (id_tentativa, id_questao, nota_resposta) VALUES (?, ?, ?)";
        try (Connection cx = ConnectionFactory.obterConexao();
             PreparedStatement ps = cx.prepareStatement(sqlResposta, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, getTentativa().getIdTentativa());
            ps.setInt(2, getQuestao().getIdQuestao());
            ps.setDouble(3, getNota() != null ? getNota() : 0.0);

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    setIdResposta(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar resposta base", e);
        }

    String sqlUpload = "INSERT INTO resposta_upload (id_resposta, arquivo_resposta) VALUES (?, ?)";
        try (Connection cx = ConnectionFactory.obterConexao();
             PreparedStatement ps = cx.prepareStatement(sqlUpload)) {

            ps.setInt(1, getIdResposta());
            ps.setString(2, caminhoArquivo);
            
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar resposta upload", e);
        }
    }    
}
