package br.maua.domain;
import javax.management.ValueExp;
import java.util.ArrayList;
import java.util.regex.*;
import java.util.List;

public class Tarefa {
    private int idTarefa;
    private String titulo;
    private String prazo;
    private Casa casa;
    private List<QuestaoAlternativa> questoesAlternativa = new ArrayList<>();
    private List<QuestaoDissertativa> questoesDissertativas= new ArrayList<>();
    private List<QuestaoUpload> questoesUploads = new ArrayList<>();

    public Tarefa(String prazo, Casa casa) {
      this.prazo = prazo;
      this.casa = casa;

    }

    public Tarefa(){}

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

    public String getPrazo() {
        return prazo;
  }
    public void setPrazo(String prazo) {
        Pattern pattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
        if (!pattern.matcher(prazo).matches())
            throw new IllegalArgumentException("O valor deve ser no formato dd/mm/yyyy");
        this.prazo = prazo;
  }
    public Casa getCasa() {
        return casa;
  }
    public void setCasa(Casa casa) {
        this.casa = casa;
  }
    public void addQuestao(QuestaoAlternativa questaoAlternativa) {
        this.questoesAlternativa.add(questaoAlternativa);
    }
    public void addQuestao(QuestaoDissertativa questaoDissertativa) {
        this.questoesDissertativas.add(questaoDissertativa);
    }
    public void addQuestao(QuestaoUpload questaoUpload) {
        this.questoesUploads.add(questaoUpload);
    }

    public void commitTarefa(){

    }
    }

