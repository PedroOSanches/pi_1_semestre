package br.maua.presentation.TelaTarefasAluno;

import br.maua.infrastructure.ConnectionFactory;
import br.maua.presentation.TelaQuestionarioAluno.TelaQuestionarioAluno;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


/**
 * Tela unificada: Design limpo com carregamento dinâmico de tarefas.
 * @author Luiza / Lenovo
 */
public class TelaTarefaAluno extends javax.swing.JFrame {
    
    private final Integer idAluno;
    private final Integer idCasa;
    private final String tituloTarefa;

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TelaTarefaAluno.class.getName());

    public TelaTarefaAluno() {
        this(null, null, "Atividade");
    }

    public TelaTarefaAluno(String tituloTarefa) {
        this(null, null, tituloTarefa);
    }

    public TelaTarefaAluno(Integer idAluno, Integer idCasa, String tituloTarefa) {
        this.idAluno = idAluno;
        this.idCasa = idCasa;
        this.tituloTarefa = tituloTarefa != null && !tituloTarefa.isBlank() ? tituloTarefa : "Atividade";
        initComponents();
        configurarConteudo();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelAzul = new javax.swing.JPanel();
        painelCinza = new javax.swing.JPanel();
        painelAzul1 = new javax.swing.JPanel();
        nomeTitulo = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        // Design base da primeira tela
        painelAzul.setBackground(new java.awt.Color(19, 112, 178));

        painelCinza.setBackground(new java.awt.Color(217, 217, 217));
        painelCinza.setMaximumSize(new java.awt.Dimension(900, 600));
        painelCinza.setMinimumSize(new java.awt.Dimension(900, 600));
        painelCinza.setPreferredSize(new java.awt.Dimension(900, 600));
        painelCinza.setLayout(null);

        painelAzul1.setBackground(new java.awt.Color(19, 112, 178));
        painelAzul1.setMaximumSize(new java.awt.Dimension(710, 90));
        painelAzul1.setMinimumSize(new java.awt.Dimension(710, 90));
        painelAzul1.setPreferredSize(new java.awt.Dimension(710, 90));

        nomeTitulo.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 48)); // NOI18N
        nomeTitulo.setForeground(new java.awt.Color(255, 255, 255));
        nomeTitulo.setText("Tarefas");

        javax.swing.GroupLayout painelAzul1Layout = new javax.swing.GroupLayout(painelAzul1);
        painelAzul1.setLayout(painelAzul1Layout);
        painelAzul1Layout.setHorizontalGroup(
            painelAzul1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelAzul1Layout.createSequentialGroup()
                .addGap(271, 271, 271)
                .addComponent(nomeTitulo)
                .addContainerGap(301, Short.MAX_VALUE))
        );
        painelAzul1Layout.setVerticalGroup(
            painelAzul1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelAzul1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(nomeTitulo)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        painelCinza.add(painelAzul1);
        painelAzul1.setBounds(94, 37, 720, 90);

        // Botão voltar com a cor laranja original do design
        jButton1.setBackground(new java.awt.Color(240, 147, 23));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Voltar");
        jButton1.addActionListener(this::jButton1ActionPerformed);
        painelCinza.add(jButton1);
        jButton1.setBounds(8, 10, 90, 30);

        javax.swing.GroupLayout painelAzulLayout = new javax.swing.GroupLayout(painelAzul);
        painelAzul.setLayout(painelAzulLayout);
        painelAzulLayout.setHorizontalGroup(
            painelAzulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelAzulLayout.createSequentialGroup()
                .addContainerGap(78, Short.MAX_VALUE)
                .addComponent(painelCinza, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68))
        );
        painelAzulLayout.setVerticalGroup(
            painelAzulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelAzulLayout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(painelCinza, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(90, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelAzul, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelAzul, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        br.maua.presentation.TelaNavegacao.voltar(this);
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * Mescla o carregamento dinâmico dentro da estrutura fixa do layout
     */
    private void configurarConteudo() {
        // Altera o título dinâmico se houver
        if (this.tituloTarefa != null && !this.tituloTarefa.equals("Atividade")) {
            nomeTitulo.setText(this.tituloTarefa);
        }

        // Criamos um container interno com Scroll para os cards de tarefa não estourarem o painel cinza
        JPanel containerCards = new JPanel();
        containerCards.setBackground(new java.awt.Color(217, 217, 217));
        containerCards.setLayout(new BoxLayout(containerCards, BoxLayout.Y_AXIS));

        // Carrega os dados do banco
        List<RegistroTarefaAluno> tarefas = carregarTarefasDaCasa();
        if (tarefas.isEmpty()) {
            JLabel vazio = new JLabel("Nenhuma tarefa encontrada para esta casa.");
            vazio.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            vazio.setForeground(new Color(90, 90, 90));
            vazio.setAlignmentX(LEFT_ALIGNMENT);
            containerCards.add(vazio);
        } else {
            for (RegistroTarefaAluno tarefa : tarefas) {
                containerCards.add(criarCardTarefa(tarefa));
                containerCards.add(Box.createRigidArea(new Dimension(0, 12)));
            }
        }

        // Criamos uma barra de rolagem invisível ou discreta para encaixar no painel fixo
        JScrollPane scrollPane = new JScrollPane(containerCards);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Adiciona o ScrollPane dinâmico na parte inferior do painelCinza fixo
        painelCinza.add(scrollPane);
        scrollPane.setBounds(94, 150, 720, 420); 

        painelCinza.revalidate();
        painelCinza.repaint();
    }

    private List<RegistroTarefaAluno> carregarTarefasDaCasa() {
        List<RegistroTarefaAluno> tarefas = new ArrayList<>();
        if (idCasa == null) {
            return tarefas;
        }

        String sql = "SELECT t.id_tarefa, t.titulo_tarefa, COUNT(te.id_tentativa) AS total_tentativas "
            + "FROM tarefa t "
            + "LEFT JOIN tentativa te ON te.id_tarefa = t.id_tarefa "
            + (idAluno != null ? "AND te.id_usuario = ? " : "")
            + "WHERE t.id_casa = ? "
            + "GROUP BY t.id_tarefa, t.titulo_tarefa "
            + "ORDER BY t.id_tarefa";

        try (Connection conexao = ConnectionFactory.obterConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            int index = 1;
            if (idAluno != null) {
                comando.setInt(index++, idAluno);
            }
            comando.setInt(index, idCasa);

            try (ResultSet resultado = comando.executeQuery()) {
                while (resultado.next()) {
                    tarefas.add(new RegistroTarefaAluno(
                        resultado.getInt("id_tarefa"),
                        resultado.getString("titulo_tarefa"),
                        resultado.getInt("total_tentativas")
                    ));
                }
            }
        } catch (SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Erro ao carregar tarefas: " + e.getMessage(), "Erro", javax.swing.JOptionPane.ERROR_MESSAGE);
        }

        return tarefas;
    }

    private JPanel criarCardTarefa(RegistroTarefaAluno tarefa) {
        JPanel card = new JPanel(new BorderLayout(10, 8));
        card.setBackground(Color.WHITE);
        card.setMaximumSize(new Dimension(720, 90)); // Fixa o tamanho para alinhar com o cabeçalho azul
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(240, 147, 23), 2), // Laranja combinando com o botão voltar
            BorderFactory.createEmptyBorder(12, 16, 12, 16)));
        card.setAlignmentX(LEFT_ALIGNMENT);

        JPanel painelTextos = new JPanel();
        painelTextos.setOpaque(false);
        painelTextos.setLayout(new BoxLayout(painelTextos, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel(tarefa.tituloTarefa);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(new Color(19, 112, 178));
        titulo.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

        JLabel id = new JLabel("Tarefa #" + tarefa.idTarefa);
        id.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        id.setForeground(new Color(100, 100, 100));

        JLabel tentativas = new JLabel("Tentativas: " + tarefa.totalTentativas);
        tentativas.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tentativas.setForeground(new Color(100, 100, 100));

        JButton abrir = new JButton("Questionário");
        abrir.setBackground(new Color(240, 147, 23)); // Laranja padrão do design
        abrir.setForeground(Color.WHITE);
        abrir.setFont(new Font("Segoe UI", Font.BOLD, 13));
        abrir.setFocusPainted(false);
        abrir.addActionListener(evt -> abrirQuestionarioAluno());

        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.add(titulo);
        textos.add(Box.createRigidArea(new Dimension(0, 4)));
        
        JPanel subTextos = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));
        subTextos.setOpaque(false);
        subTextos.add(id);
        subTextos.add(Box.createRigidArea(new Dimension(15, 0)));
        subTextos.add(tentativas);
        textos.add(subTextos);

        card.add(textos, BorderLayout.CENTER);
        card.add(abrir, BorderLayout.EAST);
        return card;
    }

    private void abrirQuestionarioAluno() {
        TelaQuestionarioAluno questionario = new TelaQuestionarioAluno();
        br.maua.presentation.TelaNavegacao.abrir(this, questionario);
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new TelaTarefaAluno().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel nomeTitulo;
    private javax.swing.JPanel painelAzul;
    private javax.swing.JPanel painelAzul1;
    private javax.swing.JPanel painelCinza;
    // End of variables declaration//GEN-END:variables

    private static final class RegistroTarefaAluno {
        private final int idTarefa;
        private final String tituloTarefa;
        private final int totalTentativas;

        private RegistroTarefaAluno(int idTarefa, String tituloTarefa, int totalTentativas) {
            this.idTarefa = idTarefa;
            this.tituloTarefa = tituloTarefa;
            this.totalTentativas = totalTentativas;
        }
    }
}