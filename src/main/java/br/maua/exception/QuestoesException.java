package br.maua.exception;

import java.sql.SQLException;

public class QuestoesException extends SQLException {
    public QuestoesException(String enunciado) {
        super(String.format("Erro ao gerar questao: %s", enunciado));
    }
}
