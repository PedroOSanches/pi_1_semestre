package br.maua;

import br.maua.infrastructure.ConnectionFactory;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
            Connection cx = ConnectionFactory.obterConexao();
            System.out.println(cx);
            System.out.println("Projeto Inicializado com Sucesso!");
    }
    
}