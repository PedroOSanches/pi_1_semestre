package br.maua.infrastructure;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SecaoDAO {

    public void salvarNoBanco(String tituloSecao, int ordemSecao, String descricaoSecao) throws SQLException {
        String sql = "INSERT INTO secao (titulo_secao, ordem_secao, descricao_secao) VALUES (?, ?, ?)";

        try (Connection conexao = ConnectionFactory.obterConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, tituloSecao);
            comando.setInt(2, ordemSecao);
            comando.setString(3, descricaoSecao);

            comando.executeUpdate();
        }
    }
}