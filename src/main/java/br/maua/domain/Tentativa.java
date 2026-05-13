package br.maua.domain;

import br.maua.infrastructure.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Tentativa {

    private Double nota;
    private Aluno aluno;
    private Tarefa tarefa;
    private boolean concluida = false;

    public Tentativa(Double nota, Aluno aluno, Tarefa tarefa){
        this.setNota(nota);
        this.setAluno(aluno);
        this.setTarefa(tarefa);
    }

    public Tentativa(Aluno aluno, Tarefa tarefa){
        this.aluno = aluno;
        this.tarefa = tarefa;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(double nota) {
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

    public boolean isConcluida() {
        return concluida;
    }
    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }
    public void registraTentativa(){
        String sql = "INSERT INTO tentativa (id_questionario, id_usuario, concluido) VALUES (?, ?, ?)";

        try(
                Connection cx = ConnectionFactory.obterConexao();
        ) {
            assert cx != null;
            try(PreparedStatement ps = cx.prepareStatement(sql);

                    ){
                ps.setInt(1, tarefa.getIdTarefa());
                ps.setInt(2, aluno.getIdAluno());
                ps.setBoolean(3, concluida);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}