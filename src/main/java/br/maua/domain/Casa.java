package br.maua.domain;

public class Casa {
    private String titulo;
    private int numeroCasa;
    private int nivelCasa;

    public Casa(int numeroCasa, int nivelCasa){
        this.numeroCasa = numeroCasa;
        this.nivelCasa = nivelCasa;
    }
    public Casa(String titulo){
        this.titulo = titulo;
    }

    public int getNumeroCasa() {
        return numeroCasa;
    }
    public void exibirCasa(Tarefa tarefa) {

        System.out.printf("Casa %d | Nível %d\n", numeroCasa, nivelCasa);

    }

    public void avancarCasa() {

    }

    public String toString(){
            return titulo;
    }
}
