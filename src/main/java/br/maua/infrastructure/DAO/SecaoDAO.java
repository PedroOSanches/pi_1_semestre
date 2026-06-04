package br.maua.infrastructure.DAO;

import br.maua.domain.Secao;
import br.maua.infrastructure.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SecaoDAO {
    Secao secao;

    public SecaoDAO() {
    }

    public SecaoDAO(Secao secao) {
        this.secao = secao;
    }

    public static List<Secao> listarSecoes() throws SQLException {
        String sql = "SELECT id_secao, titulo_secao FROM secao ORDER BY id_secao";

        try (
            Connection cx = ConnectionFactory.obterConexao();
            PreparedStatement ps = cx.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            List<Secao> secoes = new ArrayList<>();
            while (rs.next()) {
                int idSecao = rs.getInt("id_secao");
                String tituloSecao = rs.getString("titulo_secao");

                Secao secao = new Secao(idSecao, tituloSecao);
                secoes.add(secao);
            }
            return secoes;
        }
    }

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