package br.maua.service;

import br.maua.infrastructure.DAO.TabuleiroDAO;
import br.maua.domain.Casa;
import br.maua.domain.Secao;
import br.maua.domain.Tentativa; 

public class JornadaService {

    private final TabuleiroDAO tabuleiroDAO;

    public JornadaService() {
        this.tabuleiroDAO = new TabuleiroDAO();
    }

    public void avancarCasa(Tentativa tentativa, Casa casa) {
        if (tentativa == null || casa == null) {
            System.err.println("Erro: Dados inválidos para processar o avanço.");
            return;
        }

        int idUsuario = tentativa.getAluno().getId();
        int idTentativa = tentativa.getIdTentativa();

        System.out.println("Processando avanço do usuário " + idUsuario + " na Casa: " + casa.getTitulo());

        double notaAtingida = tabuleiroDAO.calcularESalvarNotaDaTentativa(idTentativa);

        System.out.println("Nota final calculada: " + notaAtingida + " para a Casa ID " + casa.getIdCasa());

        if (notaAtingida >= 6.0) {
            System.out.println("Parabéns! Casa concluída com sucesso. Próxima casa liberada.");
        } else {
            System.out.println("Nota abaixo da média (6.0). A casa continuará retendo o avanço.");
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
            System.out.println("Ainda existem casas obrigatórias pendentes na seção atual.");
        }
    }
}