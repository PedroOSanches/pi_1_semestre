package br.maua.domain;

import br.maua.infrastructure.ConnectionFactory;
import br.maua.infrastructure.TentativaDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Tentativa {

    private Double nota;
    private Aluno aluno;
    private Tarefa tarefa;
    private int idTentativa;
    private List <Resposta> respostas = new ArrayList<>();
    private TentativaDAO tentativaDAO;

    public Tentativa(Double nota, Aluno aluno, Tarefa tarefa){
        this.setNota(nota);
        this.setAluno(aluno);
        this.setTarefa(tarefa);
    }

    public Tentativa(Aluno aluno, Tarefa tarefa){
        this.aluno = aluno;
        this.tarefa = tarefa;
    }

    public Tentativa(int idTentativa){
        this.idTentativa = idTentativa;
        this.tentativaDAO = new TentativaDAO();
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Tarefa getTarefa() {
        return tarefa;
    }

    public void setTarefa(Tarefa tarefa) {
        this.tarefa = tarefa;
    }

    public int getIdTentativa() {
        return idTentativa;
    }

    public void setIdTentativa(int idTentativa) {
        this.idTentativa = idTentativa;
    }

    public List<Resposta> getRespostas() {
        return respostas;
    }

    public void setRespostas(List<Resposta> respostas) {
        this.respostas = respostas;
    }

    public void registraTentativa(){
        String sql = "INSERT INTO tentativa (id_questionario, id_usuario, status)" +
                "VALUES (?, ?, ?)";

        try(
                Connection cx = ConnectionFactory.obterConexao();
        ) {
            assert cx != null;
            try(PreparedStatement ps = cx.prepareStatement(sql);

                    ){
                ps.setInt(1, tarefa.getIdTarefa());
                ps.setInt(2, aluno.getIdAluno());
                ps.setString(3, "concluída");
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean commitCorrecao(){
        return tentativaDAO.atualizarNota(this);
    }
}