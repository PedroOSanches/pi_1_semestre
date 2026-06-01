package br.maua.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.maua.infrastructure.ConnectionFactory;

public class RespostaDissertativa extends Resposta{
    private String textoResposta;

     public String gerarRespostaBanco(String resposta){
        String sql = String.format("(%s)", resposta);
        return sql;
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
            throw new RuntimeException("Erro ao salvar resposta", e);
        }

        String sqlDissertativa = "INSERT INTO resposta_dissertativa (id_resposta, resposta) VALUES (?, ?)";
        try (Connection cx = ConnectionFactory.obterConexao();
             PreparedStatement ps = cx.prepareStatement(sqlDissertativa)) {

            ps.setInt(1, getIdResposta());
            ps.setString(2, textoResposta);
            
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar resposta dissertativa", e);
        }
    }

    public String getTextoResposta() { return textoResposta; }
    public void setTextoResposta(String textoResposta) { this.textoResposta = textoResposta; }
    
}
