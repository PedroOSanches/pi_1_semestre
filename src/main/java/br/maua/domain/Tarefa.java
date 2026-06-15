package br.maua.domain;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import br.maua.infrastructure.DAO.TarefaDAO;

public class Tarefa {

    private int idTarefa;
    private String titulo;
    private Date prazo;
    private Casa casa;
    private final List<Questao> questoes = new ArrayList<>();

    public Tarefa(String prazo, Casa casa) {
        setPrazo(prazo);
        setCasa(casa);
    }

    public Tarefa(){}

    public Tarefa(int idTarefa) {

        this.idTarefa = idTarefa;

    }

    public int getIdTarefa() {
        return idTarefa;
    }

    public void setIdTarefa(int idTarefa) {
        this.idTarefa = idTarefa;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getPrazo() {
        return prazo;
    }

    public void setPrazo(String prazo) {
        Pattern pattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
        if (!pattern.matcher(prazo).matches())
            throw new IllegalArgumentException("O valor deve ser no formato yyyy-MM-dd");
        this.prazo = Date.valueOf(prazo);
    }

    public Casa getCasa() {
        return casa;
    }

    public void setCasa(Casa casa) {
        this.casa = casa;
    }

    public List<Questao> getQuestoes() {
        return questoes;
    }

    public void addQuestao(Questao questao) {
        this.questoes.add(questao);
    }

    public void commitTarefa() throws SQLException{
        TarefaDAO.commitTarefa(this);
    }
}
