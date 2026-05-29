package br.maua.presentation.TelaTabuleiro;

import br.maua.domain.Aluno;
import br.maua.domain.Professor;
import br.maua.infrastructure.ConnectionFactory;
import br.maua.presentation.TelaAdicionarCasa.TelaAdicionarCasa;
import br.maua.presentation.TelaAdicionarSecao.TelaAdicionarSecao;
import br.maua.presentation.TelaModalPerfilAluno.ModalPerfilAluno;
import br.maua.presentation.TelaModalPerfilProfessor.ModalPerfilProfessor;
import br.maua.presentation.TelaQuestionarioAluno.TelaQuestionarioAluno;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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

public class TelaTabuleiro1 extends JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TelaTabuleiro1.class.getName());

    private Aluno alunoLogado;
    private Professor professorLogado;
    private boolean mostrarVoltarParaAdmin;

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
        this.alunoLogado = aluno;
        this.professorLogado = null;
        this.mostrarVoltarParaAdmin = false;
        initComponents();
        montarTabuleiroDinamico();
    }

    public TelaTabuleiro1(boolean mostrarVoltarParaAdmin) {
        this.alunoLogado = null;
        this.professorLogado = null;
        this.mostrarVoltarParaAdmin = mostrarVoltarParaAdmin;
        initComponents();
        montarTabuleiroDinamico();
    }

    public TelaTabuleiro1(Professor professor, boolean mostrarVoltarParaAdmin) {
        this.alunoLogado = null;
        this.professorLogado = professor;
        this.mostrarVoltarParaAdmin = mostrarVoltarParaAdmin;
        initComponents();
        montarTabuleiroDinamico();
    }

    public TelaTabuleiro1() {
        this((Aluno) null);
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
            new TelaAdicionarCasa().setVisible(true);
            dispose();
        });

        jButton4 = new JButton("Adicionar Seção");
        jButton4.setBackground(COR_AZUL);
        jButton4.setForeground(Color.WHITE);
        jButton4.addActionListener(evt -> {
            new TelaAdicionarSecao().setVisible(true);
            dispose();
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

        jButton2.setVisible(mostrarVoltarParaAdmin);

        setSize(1140, 760);
        setLocationRelativeTo(null);
    }

    private void abrirPerfil() {
        if (mostrarVoltarParaAdmin) {
            ModalPerfilProfessor modal = new ModalPerfilProfessor(this.professorLogado);
            modal.setLocationRelativeTo(this);
            modal.setVisible(true);
            return;
        }

        if (this.alunoLogado != null) {
            ModalPerfilAluno modal = new ModalPerfilAluno(this.alunoLogado);
            modal.setLocationRelativeTo(this);
            modal.setVisible(true);
            return;
        }

        JOptionPane.showMessageDialog(this, "Perfil indisponível nesta tela.");
    }

    private void voltarParaPainel() {
        new br.maua.presentation.TelaPainelDeControle.TelaPainelDeControle().setVisible(true);
        dispose();
    }

    private void montarTabuleiroDinamico() {
        jPanel1.removeAll();

        jPanel1.add(criarCabecalhoTabuleiro());
        jPanel1.add(Box.createRigidArea(new Dimension(0, 16)));

        try {
            List<RegistroCasaTabuleiro> registros = carregarCasasDoBanco();
            if (registros.isEmpty()) {
                jPanel1.add(criarEstadoVazio());
            } else {
                Integer secaoAtual = null;
                for (RegistroCasaTabuleiro registro : registros) {
                    if (!Objects.equals(secaoAtual, registro.idSecao)) {
                        if (secaoAtual != null) {
                            jPanel1.add(Box.createRigidArea(new Dimension(0, 20)));
                        }
                        jPanel1.add(criarCabecalhoSecao(registro));
                        jPanel1.add(Box.createRigidArea(new Dimension(0, 12)));
                        secaoAtual = registro.idSecao;
                    }

                    if (registro.idCasa != null) {
                        jPanel1.add(criarCartaoCasa(registro));
                        jPanel1.add(Box.createRigidArea(new Dimension(0, 12)));
                    }
                }
            }
        } catch (SQLException e) {
            jPanel1.add(criarEstadoErro(e.getMessage()));
        }

        jPanel1.add(Box.createVerticalGlue());
        jPanel1.revalidate();
        jPanel1.repaint();
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

    private JPanel criarCabecalhoSecao(RegistroCasaTabuleiro registro) {
        JPanel painel = new JPanel(new BorderLayout());
        boolean secaoPar = registro.ordemSecao % 2 == 0;
        Color corPrincipal = secaoPar ? COR_AZUL : COR_LARANJA;
        Color corFundo = corPrincipal;
        Color corTexto = Color.WHITE;

        painel.setBackground(corFundo);
        // usar borda contrastante (branca) para ficar visível sobre a cor da seção
        painel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 8),
            BorderFactory.createEmptyBorder(12, 12, 12, 12)));
        painel.setAlignmentX(Component.CENTER_ALIGNMENT);
        painel.setPreferredSize(new Dimension(LARGURA_SECAO, ALTURA_SECAO));
        painel.setMinimumSize(new Dimension(LARGURA_SECAO, ALTURA_SECAO));
        painel.setMaximumSize(new Dimension(LARGURA_SECAO, ALTURA_SECAO));

        JLabel numero = new JLabel(String.valueOf(registro.ordemSecao));
        numero.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 54));
        numero.setForeground(corTexto);

        JLabel titulo = criarLabelFormatado(registro.tituloSecao, new Font("Segoe UI", Font.BOLD, 18), corTexto, 280);
        titulo.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel icone = new JLabel(carregarIconeSecao(registro.ordemSecao, corPrincipal));
        icone.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descricao = criarLabelFormatado(registro.descricaoSecao, new Font("Segoe UI", Font.PLAIN, 13), corTexto, 280);
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

    private JPanel criarCartaoCasa(RegistroCasaTabuleiro registro) {
        JPanel painel = new JPanel(new BorderLayout(18, 8));
        boolean secaoPar = registro.ordemSecao % 2 == 0;
        Color corBase = secaoPar ? COR_AZUL : COR_LARANJA;
        painel.setBackground(Color.WHITE);
        painel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(corBase, 2),
                BorderFactory.createEmptyBorder(16, 18, 16, 18)));
        painel.setAlignmentX(Component.CENTER_ALIGNMENT);
        painel.setPreferredSize(new Dimension(LARGURA_CASA, ALTURA_CASA));
        painel.setMinimumSize(new Dimension(LARGURA_CASA, ALTURA_CASA));
        painel.setMaximumSize(new Dimension(LARGURA_CASA, ALTURA_CASA));

        JLabel numeroCasa = new JLabel(String.valueOf(registro.ordemCasa));
        numeroCasa.setHorizontalAlignment(SwingConstants.CENTER);
        numeroCasa.setPreferredSize(new Dimension(60, 60));
        numeroCasa.setFont(new Font("Segoe UI", Font.BOLD, 36));
        numeroCasa.setForeground(corBase);

        JLabel tituloCasa = new JLabel(registro.tituloCasa);
        tituloCasa.setFont(new Font("Segoe UI", Font.BOLD, 22));
        tituloCasa.setForeground(corBase);

        JLabel prazoCasa = new JLabel("Data limite: " + formatoData(registro.dataLimiteCasa));
        prazoCasa.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        prazoCasa.setForeground(new Color(90, 90, 90));

        // substituir botão por um pequeno painel que mostra um checkbox e o título do questionário
        JPanel miniQuestionario = new JPanel();
        miniQuestionario.setLayout(new BorderLayout(6, 0));
        miniQuestionario.setBackground(Color.WHITE);
        miniQuestionario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)));

        javax.swing.JCheckBox cb = new javax.swing.JCheckBox();
        cb.setBackground(Color.WHITE);
        cb.setSelected(false);

        String tituloQuestionario = registro.tituloQuestionario != null ? registro.tituloQuestionario : "Questionário";
        JLabel lblMini = new JLabel(tituloQuestionario);
        lblMini.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblMini.setForeground(new Color(60, 60, 60));

        miniQuestionario.add(cb, BorderLayout.WEST);
        miniQuestionario.add(lblMini, BorderLayout.CENTER);
        // tornar clicável: abre a tela do questionário ao clicar no painel ou no label
        java.awt.event.MouseAdapter abrirClique = new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                abrirQuestionarioAluno();
            }
        };
        miniQuestionario.addMouseListener(abrirClique);
        lblMini.addMouseListener(abrirClique);

        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.add(tituloCasa);
        textos.add(Box.createRigidArea(new Dimension(0, 6)));
        textos.add(prazoCasa);
        textos.add(Box.createRigidArea(new Dimension(0, 12)));
        textos.add(miniQuestionario);

        painel.add(numeroCasa, BorderLayout.WEST);
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

    private List<RegistroCasaTabuleiro> carregarCasasDoBanco() throws SQLException {
        String sql = "SELECT c.id_casa, c.id_secao, c.ordem_casa, c.titulo_casa, c.data_limite_casa, "
            + "s.titulo_secao, s.ordem_secao, s.descricao_secao "
            + "FROM secao s LEFT JOIN casa c ON c.id_secao = s.id_secao "
            + "ORDER BY s.ordem_secao, c.ordem_casa";

        List<RegistroCasaTabuleiro> registros = new ArrayList<>();

        try (Connection conexao = ConnectionFactory.obterConexao();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            QuestionarioDAO qdao = new QuestionarioDAO();
            while (resultado.next()) {
                Integer idCasa = resultado.getObject("id_casa") != null ? resultado.getInt("id_casa") : null;
                String tituloQ = null;
                if (idCasa != null) {
                    try {
                        tituloQ = qdao.obterTituloPorCasa(idCasa);
                    } catch (SQLException e) {
                        // não interrompe o carregamento do tabuleiro; apenas logue
                        logger.warning("Erro ao obter título do questionário para casa " + idCasa + ": " + e.getMessage());
                    }
                }

                registros.add(new RegistroCasaTabuleiro(
                        idCasa,
                        resultado.getInt("id_secao"),
                        resultado.getObject("ordem_casa") != null ? resultado.getInt("ordem_casa") : null,
                        resultado.getString("titulo_casa"),
                        resultado.getTimestamp("data_limite_casa"),
                        resultado.getString("titulo_secao"),
                        resultado.getInt("ordem_secao"),
                        resultado.getString("descricao_secao"),
                        tituloQ
                ));
            }
        }

        return registros;
    }

    private void abrirQuestionarioAluno() {
        new TelaQuestionarioAluno().setVisible(true);
        dispose();
    }

    private static final class RegistroCasaTabuleiro {
        private final Integer idCasa;
        private final int idSecao;
        private final Integer ordemCasa;
        private final String tituloCasa;
        private final Timestamp dataLimiteCasa;
        private final String tituloSecao;
        private final int ordemSecao;
        private final String descricaoSecao;
        private final String tituloQuestionario;

        private RegistroCasaTabuleiro(Integer idCasa, int idSecao, Integer ordemCasa, String tituloCasa,
                                    Timestamp dataLimiteCasa, String tituloSecao, int ordemSecao,
                                                              String descricaoSecao, String tituloQuestionario) {
            this.idCasa = idCasa;
            this.idSecao = idSecao;
            this.ordemCasa = ordemCasa;
            this.tituloCasa = tituloCasa;
            this.dataLimiteCasa = dataLimiteCasa;
            this.tituloSecao = tituloSecao;
            this.ordemSecao = ordemSecao;
                                    this.descricaoSecao = descricaoSecao;
                                    this.tituloQuestionario = tituloQuestionario;
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new TelaTabuleiro1().setVisible(true));
    }
}
