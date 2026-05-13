package br.maua.domain;

public class Casa {

    private int numeroCasa;
    private int nivelCasa;



    public Casa(int numeroCasa, int nivelCasa){
        this.numeroCasa = numeroCasa;
        this.nivelCasa = nivelCasa;
    }

    public int getNumeroCasa() {
        return numeroCasa;
    }
    public void exibirCasa(Tarefa tarefa) {

        System.out.printf("Casa %d | Nível %d\n", numeroCasa, nivelCasa);

    }

    public void avancarCasa() {

    }
}
