package br.maua.service;

import br.maua.infrastructure.DAO.TabuleiroDAO;
import br.maua.domain.Casa;
import br.maua.domain.Secao;

public class JornadaService {

    private final TabuleiroDAO tabuleiroDAO;

    public JornadaService() {
        this.tabuleiroDAO = new TabuleiroDAO();
    }

    public void avancarCasa(int idAluno, Casa casa, double notaAtingida) {
        if (casa == null) {
            System.err.println("Erro: Casa inválida para avanço.");
            return;
        }

        System.out.println("Processando avanço do aluno " + idAluno + " na Casa: " + casa.getTitulo());

        boolean sucessoSalvamento = tabuleiroDAO.salvarNotaDaTentativa(idAluno, casa.getIdCasa(), notaAtingida);

        if (sucessoSalvamento) {
            System.out.println("Nota " + notaAtingida + " registrada com sucesso para a Casa ID " + casa.getIdCasa());

            if (notaAtingida >= 6.0) {
                System.out.println("Parabéns! Casa concluída com sucesso. Próxima casa liberada.");
            } else {
                System.out.println("Nota abaixo da média (6.0). A casa continuará retendo o avanço até o prazo expirar.");
            }
        } else {
            System.err.println("Falha crítica ao persistir o avanço da casa no banco de dados.");
        }
    }

    public void avancarSecao(int idAluno, Secao secaoAtual) {
        if (secaoAtual == null) {
            return;
        }

        boolean possuiCasasPendentesNaSecao = tabuleiroDAO.verificarPendenciasNaSecao(idAluno, secaoAtual.getidSecao());

        if (!possuiCasasPendentesNaSecao) {
            System.out.println("Seção " + secaoAtual.getidSecao() + " (" + secaoAtual.getTitulo() + ") 100% concluída!");

        } else {
            System.out.println("Ainda existem casas obrigatórias pendentes ou dentro do prazo na seção atual.");
        }
    }
}