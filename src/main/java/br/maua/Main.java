package br.maua;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
            Connection cx = ConnectionFactory.obterConexao();
            System.out.println(cx);

    }
}