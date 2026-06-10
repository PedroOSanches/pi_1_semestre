package br.maua.infrastructure.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.maua.domain.Questao;
import br.maua.domain.QuestaoAlternativa;
import br.maua.domain.QuestaoDissertativa;
import br.maua.domain.QuestaoUpload;
import br.maua.domain.Tarefa;
import br.maua.exception.QuestoesException;
import br.maua.infrastructure.ConnectionFactory;

public class QuestaoDAO{
    public static void commit(Questao q, Connection cx, String tipo) throws SQLException {
        String sql;
        sql = "INSERT INTO questao(id_tarefa, tipo_questao, enunciado_questao) VALUES (?, ?, ?);";

        try(
                PreparedStatement ps = cx.prepareStatement(sql,  Statement.RETURN_GENERATED_KEYS);
        ){

            ps.setInt(1, q.getTarefa().getIdTarefa());
            ps.setString(2, tipo);
            ps.setString(3, q.getEnunciado());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(!rs.next()){
                throw new QuestoesException(q.getEnunciado());
            }
            q.setIdQuestao(rs.getInt(1));
        }
    }

    public static List<Questao> buscarPorTarefa(Tarefa tarefa) throws SQLException {
    List<Questao> questoes = new ArrayList<>();

    String sql = 
        "SELECT id_questao, enunciado_questao, tipo_questao " +
        "FROM questao " +
        "WHERE id_tarefa = ?";

    try (
        Connection cx = ConnectionFactory.obterConexao();
        PreparedStatement ps = cx.prepareStatement(sql)
    ) {

        ps.setInt(1, tarefa.getIdTarefa());

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            String tipo = rs.getString("tipo_questao");
            String enunciado = rs.getString("enunciado_questao");
            int idQuestao = rs.getInt("id_questao");

            switch(tipo) {   
                case "alternativa":
                    QuestaoAlternativa qa = new QuestaoAlternativa(idQuestao, enunciado, tarefa);
                    AlternativaDAO.consultarAlternativas(cx, qa);
                    tarefa.addQuestao(qa);
                    break;
            
                case "dissertativa" :
                    QuestaoDissertativa qd = new QuestaoDissertativa(idQuestao, enunciado, tarefa);
                    tarefa.addQuestao(qd);
                    break;
            
                case "upload":
                    QuestaoUpload qu = new QuestaoUpload(idQuestao, enunciado, tarefa);
                    QuestaoUploadDAO.consultarArquivo(qu);
                    tarefa.addQuestao(qu);
                    break;
        }

    }

    return questoes;
    }
}
}
