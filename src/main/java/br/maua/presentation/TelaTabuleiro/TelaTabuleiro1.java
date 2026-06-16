package br.maua.presentation.TelaTabuleiro;

import br.maua.domain.Aluno;
import br.maua.domain.Casa;
import br.maua.domain.Professor;
import br.maua.domain.Secao;
import br.maua.domain.Usuario;
import br.maua.service.JornadaService;
import br.maua.presentation.TelaAdicionarCasa.TelaAdicionarCasa;
import br.maua.presentation.TelaAdicionarSecao.TelaAdicionarSecao;
import br.maua.presentation.TelaModalPerfilAluno.ModalPerfilAluno;
import br.maua.presentation.TelaModalPerfilProfessor.ModalPerfilProfessor;


import br.maua.presentation.TelaEscolhaDeQuestionario.TelaEscolhaDeQuestionario;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class TelaTabuleiro1 extends JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TelaTabuleiro1.class.getName());

    private Aluno alunoLogado;
    private final Professor professorLogado;
    private final boolean mostrarVoltarParaAdmin;
    private final List<Casa> casasInformadas;
    private final List<Secao> secoesInformadas;

    private final JornadaService jornadaService = new JornadaService();

    private JScrollPane jScrollPane1;
    private JPanel jPanel1;
    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private JButton jButton4;

    private static final Color COR_AZUL = new Color(19, 112, 178);
    private static final Color COR_LARANJA = new Color(240, 147, 32);
    private static final Color COR_FUNDO = new Color(234, 242, 248);
    private static final int LARGURA_SECAO = 420;
    private static final int ALTURA_SECAO = 230;
    private static final int LARGURA_CASA = 450;
    private static final int ALTURA_CASA = 168;

    public TelaTabuleiro1(Aluno aluno) {
        this(aluno, null, false, null, null);
    }

    public TelaTabuleiro1(Aluno aluno, List<Casa> casas, List<Secao> secoes) {
        this(aluno, null, false, casas, secoes);
    }

    public TelaTabuleiro1(boolean mostrarVoltarParaAdmin) {
        this(null, null, mostrarVoltarParaAdmin, null, null);
    }

    public TelaTabuleiro1(Professor professor, boolean mostrarVoltarParaAdmin) {
        this(null, professor, mostrarVoltarParaAdmin, null, null);
    }

    public TelaTabuleiro1(Professor professor, boolean mostrarVoltarParaAdmin, List<Casa> casas, List<Secao> secoes) {
        this(null, professor, mostrarVoltarParaAdmin, casas, secoes);
    }

    public TelaTabuleiro1() {
        this(null);
    }

    private TelaTabuleiro1(Aluno aluno, Professor professor, boolean mostrarVoltarParaAdmin, List<Casa> casas, List<Secao> secoes) {
        this.alunoLogado = aluno;
        this.professorLogado = professor;
        this.mostrarVoltarParaAdmin = mostrarVoltarParaAdmin;
        this.casasInformadas = casas;
        this.secoesInformadas = secoes;
        initComponents();
        montarTabuleiroDinamico();

    }

    public void atualizarTabuleiro() {
        SwingUtilities.invokeLater(() -> {
            montarTabuleiroDinamico();
        });
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tabuleiro");

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(COR_FUNDO);

        JPanel acoes = new JPanel();
        acoes.setBackground(COR_FUNDO);
        acoes.setBorder(BorderFactory.createEmptyBorder(12, 16, 8, 16));
        acoes.setLayout(new BoxLayout(acoes, BoxLayout.X_AXIS));

        jButton1 = new JButton("Perfil");
        jButton1.setBackground(COR_LARANJA);
        jButton1.setForeground(Color.WHITE);
        jButton1.addActionListener(evt -> abrirPerfil());

        jButton2 = new JButton("Voltar");
        jButton2.setBackground(COR_LARANJA);
        jButton2.setForeground(Color.WHITE);
        jButton2.addActionListener(evt -> voltarParaPainel());

        jButton3 = new JButton("Adicionar Casa");
        jButton3.setBackground(COR_AZUL);
        jButton3.setForeground(Color.WHITE);
        jButton3.addActionListener(evt -> {
            br.maua.presentation.TelaNavegacao.abrir(this, new TelaAdicionarCasa());
        });

        jButton4 = new JButton("Adicionar Seção");
        jButton4.setBackground(COR_AZUL);
        jButton4.setForeground(Color.WHITE);
        jButton4.addActionListener(evt -> {
            br.maua.presentation.TelaNavegacao.abrir(this, new TelaAdicionarSecao());
        });

        acoes.add(jButton1);
        acoes.add(Box.createRigidArea(new Dimension(8, 0)));
        acoes.add(jButton2);
        acoes.add(Box.createHorizontalGlue());
        acoes.add(jButton3);
        acoes.add(Box.createRigidArea(new Dimension(8, 0)));
        acoes.add(jButton4);

        jPanel1 = new JPanel();
        jPanel1.setBackground(COR_FUNDO);
        jPanel1.setLayout(new BoxLayout(jPanel1, BoxLayout.Y_AXIS));

        jScrollPane1 = new JScrollPane(jPanel1);
        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        root.add(acoes, BorderLayout.NORTH);
        root.add(jScrollPane1, BorderLayout.CENTER);
        setContentPane(root);

        boolean usuarioProfessor = this.professorLogado != null;
        jButton2.setVisible(mostrarVoltarParaAdmin);
        jButton3.setVisible(usuarioProfessor);
        jButton4.setVisible(usuarioProfessor);

        setSize(1140, 760);
        setLocationRelativeTo(null);
    }

    private void abrirPerfil() {
        if (mostrarVoltarParaAdmin) {
            ModalPerfilProfessor modal = new ModalPerfilProfessor(this.professorLogado);
            br.maua.presentation.TelaNavegacao.abrir(this, modal);
            return;
        }

        if (this.alunoLogado != null) {
            ModalPerfilAluno modal = new ModalPerfilAluno(this.alunoLogado, this);
            br.maua.presentation.TelaNavegacao.abrir(this, modal);
            return;
        }

        JOptionPane.showMessageDialog(this, "Perfil indisponível nesta tela.");
    }

    private void voltarParaPainel() {
        br.maua.presentation.TelaNavegacao.voltar(this);
    }

    private void montarTabuleiroDinamico() {
        jPanel1.removeAll();

        jPanel1.add(criarCabecalhoTabuleiro());
        jPanel1.add(Box.createRigidArea(new Dimension(0, 16)));

        try {
            List<Casa> registros = temListasInformadas()
                    ? carregarCasasDasListas()
                    : br.maua.infrastructure.DAO.CasaDAO.carregarCasasTabuleiro();

            registros.sort((c1, c2) -> {
                int secao1 = c1.getSecao() != null ? c1.getSecao().getidSecao() : Integer.MAX_VALUE;
                int secao2 = c2.getSecao() != null ? c2.getSecao().getidSecao() : Integer.MAX_VALUE;
                if (secao1 != secao2) return Integer.compare(secao1, secao2);
                return Integer.compare(c1.getIdCasa(), c2.getIdCasa());
            });

            if (registros.isEmpty()) {
                jPanel1.add(criarEstadoVazio());
            } else {
                int numeroCasaExibida = 0;
                Integer secaoAtual = null;
                int contadorSecoes = 0;


            Integer idProximaCasaObrigatoria = descobrirProximaCasaObrigatoria();
            boolean jaPassouDaObrigatoria = false;


                for (Casa casa : registros) {
                    int idSecaoDaCasa = casa.getSecao() != null ? casa.getSecao().getidSecao() : 0;
                    if (!Objects.equals(secaoAtual, idSecaoDaCasa)) {
                        if (secaoAtual != null) {
                            jPanel1.add(Box.createRigidArea(new Dimension(0, 20)));
                        }
                        contadorSecoes++;

                        jPanel1.add(criarCabecalhoSecao(casa.getSecao(), contadorSecoes));
                        jPanel1.add(Box.createRigidArea(new Dimension(0, 12)));
                        secaoAtual = idSecaoDaCasa;
                    }

                    numeroCasaExibida++;
                    boolean casaBloqueada = false;
                    if (this.alunoLogado != null && idProximaCasaObrigatoria != null) {
                        if (jaPassouDaObrigatoria) {
                            boolean prazoAnteriorExpirou = false;
                            if (casa.getDataLimiteCasa() != null) {
                                LocalDateTime agora = LocalDateTime.now();
                                java.time.LocalDateTime prazoCasa = casa.getDataLimiteCasa().toLocalDateTime();
                                prazoAnteriorExpirou = agora.isAfter(prazoCasa);
                            }
                                if (!prazoAnteriorExpirou) {
                                    casaBloqueada = true;
                                }
                            }

                        if (casa.getIdCasa() == idProximaCasaObrigatoria.intValue()) {
                            jaPassouDaObrigatoria = true;
                        }
                    }
                    if (this.alunoLogado == null) {
                        casaBloqueada = false;
                    }

                    jPanel1.add(criarCartaoCasa(casa, casaBloqueada, numeroCasaExibida));
                    jPanel1.add(Box.createRigidArea(new Dimension(0, 12)));
                }
            }
        } catch (SQLException e) {
            jPanel1.add(criarEstadoErro(e.getMessage()));
        }

        jPanel1.add(Box.createVerticalGlue());
        jPanel1.revalidate();
        jPanel1.repaint();
    }

    private boolean temListasInformadas() {
        return (this.casasInformadas != null && !this.casasInformadas.isEmpty())
                || (this.secoesInformadas != null && !this.secoesInformadas.isEmpty());
    }

    private List<Casa> carregarCasasDasListas() {
        List<Casa> registros = new ArrayList<>();
        List<Casa> casasBase = this.casasInformadas != null ? new ArrayList<>(this.casasInformadas) : new ArrayList<>();
        List<Secao> secoesBase = this.secoesInformadas != null ? new ArrayList<>(this.secoesInformadas) : new ArrayList<>();

        secoesBase.sort((s1, s2) -> Integer.compare(s1.getidSecao(), s2.getidSecao()));

        if (!secoesBase.isEmpty()) {
            for (Secao secao : secoesBase) {
                for (Casa casa : casasBase) {
                    if (casa.getSecao() != null && casa.getSecao().getidSecao() == secao.getidSecao()) {
                        casa.setSecao(secao);
                        registros.add(casa);
                    }
                }
            }
            return registros;
        }
        return casasBase;
    }

    public void atualizarTabuleiro(Aluno aluno) {
        this.alunoLogado = aluno;
        if (this.alunoLogado != null) {
            Secao secao = new Secao();
            jornadaService.avancarSecao(this.alunoLogado.getId(), secao);
        }
        montarTabuleiroDinamico();
    }

    private JPanel criarCabecalhoTabuleiro() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(COR_AZUL);
        painel.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));
        painel.setAlignmentX(Component.CENTER_ALIGNMENT);
        painel.setPreferredSize(new Dimension(LARGURA_CASA, 96));
        painel.setMinimumSize(new Dimension(LARGURA_CASA, 96));
        painel.setMaximumSize(new Dimension(LARGURA_CASA, 96));

        JLabel titulo = new JLabel("Início");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 34));
        titulo.setForeground(Color.WHITE);

        JLabel subtitulo = new JLabel("Casas e seções carregadas do banco");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subtitulo.setForeground(Color.WHITE);

        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.add(titulo);
        textos.add(Box.createRigidArea(new Dimension(0, 6)));
        textos.add(subtitulo);

        painel.add(textos, BorderLayout.CENTER);
        return painel;
    }

    private JPanel criarCabecalhoSecao(Secao secao, int ordemSecao) {
        JPanel painel = new JPanel(new BorderLayout());
        boolean secaoPar = secao.getidSecao() % 2 == 0;
        Color corPrincipal = secaoPar ? COR_AZUL : COR_LARANJA;
        Color corFundo = corPrincipal;
        Color corTexto = Color.WHITE;

        painel.setBackground(corFundo);
        painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 8),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)));
        painel.setAlignmentX(Component.CENTER_ALIGNMENT);
        painel.setPreferredSize(new Dimension(LARGURA_SECAO, ALTURA_SECAO));
        painel.setMinimumSize(new Dimension(LARGURA_SECAO, ALTURA_SECAO));
        painel.setMaximumSize(new Dimension(LARGURA_SECAO, ALTURA_SECAO));

        JLabel numero = new JLabel(String.valueOf(ordemSecao));
        numero.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 54));
        numero.setForeground(corTexto);

        JLabel titulo = criarLabelFormatado(secao.getTitulo(), new Font("Segoe UI", Font.BOLD, 18), corTexto, 280);
        titulo.setHorizontalAlignment(SwingConstants.LEFT);

        boolean liberarIcone = isSecaoConcluidaComSucesso(secao.getidSecao());

        JLabel icone = new JLabel();
        if (liberarIcone) {
            icone.setIcon(carregarIconeSecao(ordemSecao, corPrincipal));
        }
        icone.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel descricao = criarLabelFormatado(secao.getDescricaoSecao(), new Font("Segoe UI", Font.PLAIN, 13), corTexto, 280);
        descricao.setHorizontalAlignment(SwingConstants.LEFT);

        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));

        JPanel topo = new JPanel(new BorderLayout());
        topo.setOpaque(false);
        topo.add(numero, BorderLayout.WEST);
        topo.add(icone, BorderLayout.EAST);

        textos.add(topo);
        textos.add(Box.createRigidArea(new Dimension(0, 10)));
        textos.add(titulo);
        textos.add(Box.createRigidArea(new Dimension(0, 6)));
        textos.add(descricao);

        painel.add(textos, BorderLayout.CENTER);
        return painel;
    }

    private JLabel criarLabelFormatado(String texto, Font fonte, Color cor, int largura) {
        String safe = texto == null ? "" : texto.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
        int fs = fonte.getSize();
        String html = "<html><div style='width:" + largura + "px; text-align:left; word-wrap: break-word; line-height:1.2; font-family: Segoe UI, Arial; font-size: " + fs + "px;'>" + safe + "</div></html>";
        JLabel label = new JLabel(html);
        label.setFont(fonte);
        label.setForeground(cor);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private ImageIcon carregarIconeSecao(int ordemSecao, Color corPrincipal) {
        int tamanho = 90;
        String recurso = "/assets/icone_secao_" + ordemSecao + ".png";

        try {
            var url = TelaTabuleiro1.class.getResource(recurso);
            if (url != null) {
                Image imagem = ImageIO.read(url).getScaledInstance(tamanho, tamanho, Image.SCALE_SMOOTH);
                return new ImageIcon(imagem);
            }
        } catch (IOException e) {
            // fallback abaixo
        }

        return criarIconeFallback(ordemSecao, corPrincipal, tamanho);
    }

    private ImageIcon criarIconeFallback(int ordemSecao, Color corPrincipal, int tamanho) {
        java.awt.image.BufferedImage imagem = new java.awt.image.BufferedImage(tamanho, tamanho, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2 = imagem.createGraphics();
        try {
            g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(corPrincipal);
            g2.fillOval(0, 0, tamanho - 1, tamanho - 1);

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
            String numero = String.valueOf(ordemSecao);
            int larguraTexto = g2.getFontMetrics().stringWidth(numero);
            int x = (tamanho - larguraTexto) / 2;
            int y = ((tamanho - g2.getFontMetrics().getHeight()) / 2) + g2.getFontMetrics().getAscent();
            g2.drawString(numero, x, y);
        } finally {
            g2.dispose();
        }

        return new ImageIcon(imagem);
    }


    private boolean isSecaoConcluidaComSucesso(int idSecao) {
        if (this.alunoLogado == null) {
            return true;
        }
        try {
            return br.maua.infrastructure.DAO.CasaDAO.isSecaoConcluidaComSucesso(this.alunoLogado.getId(), idSecao);
        } catch (SQLException e) {
            logger.log(java.util.logging.Level.SEVERE, "Erro ao verificar conclusão da seção via DAO", e);
            return false;
        }
    }
    private Integer descobrirProximaCasaObrigatoria() {
        if (this.alunoLogado == null) {
            return null;
        }
        try {
            return br.maua.infrastructure.DAO.CasaDAO.descobrirProximaCasaObrigatoria(this.alunoLogado.getId());
        } catch (SQLException e) {
            logger.log(java.util.logging.Level.SEVERE, "Erro ao descobrir próxima casa obrigatória via DAO", e);
            return null;
        }
    }

    private JPanel criarCartaoCasa(Casa casa, boolean casaBloqueada, int numeroCasaExibida) {
        JPanel painel = new JPanel(new BorderLayout(18, 10));
        int idSecao = casa.getSecao() != null ? casa.getSecao().getidSecao() : 0;
        boolean secaoPar = idSecao % 2 == 0;
        Color corBase = secaoPar ? COR_AZUL : COR_LARANJA;

        Color corBorda = casaBloqueada ? Color.LIGHT_GRAY : corBase;

        painel.setBackground(Color.WHITE);
        painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(corBorda, 2),
                BorderFactory.createEmptyBorder(16, 18, 16, 18)));
        painel.setAlignmentX(Component.CENTER_ALIGNMENT);
        painel.setPreferredSize(new Dimension(LARGURA_CASA, ALTURA_CASA));
        painel.setMinimumSize(new Dimension(LARGURA_CASA, ALTURA_CASA));
        painel.setMaximumSize(new Dimension(LARGURA_CASA, ALTURA_CASA));

        JLabel badgeCasa = new JLabel(String.valueOf(numeroCasaExibida));
        badgeCasa.setHorizontalAlignment(SwingConstants.CENTER);
        badgeCasa.setPreferredSize(new Dimension(60, 60));
        badgeCasa.setFont(new Font("Segoe UI", Font.BOLD, 36));
        badgeCasa.setForeground(corBorda);

        JLabel tituloCasa = criarLabelFormatado(casa.getTitulo(), new Font("Segoe UI", Font.BOLD, 22), corBorda, 310);
        tituloCasa.setHorizontalAlignment(SwingConstants.LEFT);

        // MUDANÇA: Como Casa não possui data limite, simplificamos o texto informativo de apoio
        JLabel info = criarLabelFormatado(
                "Casa " + numeroCasaExibida + " | Data limite: " + formatoData(casa.getDataLimiteCasa()),
                new Font("Segoe UI", Font.PLAIN, 15),
                new Color(130, 130, 130),
                310);
        info.setHorizontalAlignment(SwingConstants.LEFT);

        JButton linkPagina = new JButton();
        linkPagina.setFocusPainted(false);

        if (casaBloqueada) {
            linkPagina.setText("Bloqueado");
            linkPagina.setBackground(Color.GRAY);
            linkPagina.setForeground(Color.LIGHT_GRAY);
            linkPagina.addActionListener(evt -> {
                JOptionPane.showMessageDialog(
                        this,
                        "Acesso Bloqueado!\nPara liberar esta casa você precisa:\n" +
                                "1. Concluir as tarefas da casa anterior com nota igual ou maior que 6.0",
                        "Conteúdo Bloqueado",
                        JOptionPane.WARNING_MESSAGE
                );
            });
        } else {
            linkPagina.setText("Tarefas");
            linkPagina.setBackground(COR_LARANJA);
            linkPagina.setForeground(Color.WHITE);
            linkPagina.addActionListener(evt -> abrirTelaEscolhaQuestionario(casa.getIdCasa(), casa.getTitulo()));
        }

        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.add(tituloCasa);
        textos.add(Box.createRigidArea(new Dimension(0, 6)));
        textos.add(info);
        textos.add(Box.createRigidArea(new Dimension(0, 12)));
        textos.add(linkPagina);

        painel.add(badgeCasa, BorderLayout.WEST);
        painel.add(textos, BorderLayout.CENTER);
        return painel;
    }

    private JPanel criarEstadoVazio() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(Color.WHITE);
        painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COR_AZUL, 2),
                BorderFactory.createEmptyBorder(18, 20, 18, 20)));
        painel.setAlignmentX(Component.CENTER_ALIGNMENT);
        painel.setPreferredSize(new Dimension(LARGURA_CASA, 80));
        painel.setMinimumSize(new Dimension(LARGURA_CASA, 80));
        painel.setMaximumSize(new Dimension(LARGURA_CASA, 80));

        JLabel mensagem = new JLabel("Nenhuma casa cadastrada no momento.");
        mensagem.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        mensagem.setForeground(new Color(60, 60, 60));
        painel.add(mensagem, BorderLayout.CENTER);
        return painel;
    }

    private JPanel criarEstadoErro(String mensagemErro) {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(new Color(255, 245, 245));
        painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(204, 0, 0), 2),
                BorderFactory.createEmptyBorder(18, 20, 18, 20)));
        painel.setAlignmentX(Component.LEFT_ALIGNMENT);
        painel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        JLabel mensagem = new JLabel("Erro ao carregar o tabuleiro: " + mensagemErro);
        mensagem.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        mensagem.setForeground(new Color(204, 0, 0));
        painel.add(mensagem, BorderLayout.CENTER);
        return painel;
    }

    private String formatoData(Timestamp dataLimite) {
        if (dataLimite == null) {
            return "sem data";
        }
        LocalDateTime dataHora = dataLimite.toLocalDateTime();
        return dataHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }


    private void abrirTelaEscolhaQuestionario(int idCasa, String tituloCasa) {
        Usuario usuarioLogado = null;
        
        if (this.alunoLogado != null) {
            usuarioLogado = this.alunoLogado; 
        } else if (this.professorLogado != null) {
            usuarioLogado = this.professorLogado; 
        }

        TelaEscolhaDeQuestionario telaEscolha =
                new TelaEscolhaDeQuestionario(usuarioLogado, idCasa, tituloCasa, this);
        telaEscolha.setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new TelaTabuleiro1().setVisible(true));
    }
}