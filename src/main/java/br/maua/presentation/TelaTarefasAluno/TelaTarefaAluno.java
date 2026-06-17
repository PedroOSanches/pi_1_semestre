package br.maua.presentation.TelaTarefasAluno;

import br.maua.config.IconUtil;
import br.maua.infrastructure.DAO.TentativaDAO;
import br.maua.infrastructure.DAO.TentativaDAO.TarefaTentadaDTO;
import br.maua.presentation.TelaAlunosDaTurma.TelaAlunosDaTurma;
// import br.maua.presentation.TelaCorrecaoProfessor.TelaCorrecaoProfessor; // Descomente quando criar/ajustar a tela de correção

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;

/**
 * Tela unificada: Exibe dinamicamente as tarefas que o aluno selecionado
 * realizou.
 *
 * @author Luiza / Lenovo (Adaptado para visão do Professor)
 */
public class TelaTarefaAluno extends JFrame {

    private final Integer idAluno;
    private final Integer idCasa;
    private final String tituloTarefa;
    private final JFrame telaAnterior;

    /**
     * Construtor chamado pela TelaAlunosDaTurma ao clicar em "Acessar"
     */
    public TelaTarefaAluno(JFrame telaAnterior, Integer idAluno, Integer idCasa) {
        this(idAluno, idCasa, "Tarefas Realizadas", telaAnterior);
        this.setIconImages(IconUtil.ICONS);
        this.setTitle("Jornada Mauá");
    }

    public TelaTarefaAluno(Integer idAluno, Integer idCasa, String tituloTarefa, JFrame telaAnterior) {
        this.idAluno = idAluno;
        this.idCasa = idCasa;
        this.tituloTarefa = tituloTarefa != null && !tituloTarefa.isBlank() ? tituloTarefa : "Atividade";
        this.telaAnterior = telaAnterior;
        this.setIconImages(IconUtil.ICONS);
        this.setTitle("Jornada Mauá");
        initComponents();
        configurarConteudo();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        painelAzul = new javax.swing.JPanel();
        painelCinza = new javax.swing.JPanel();
        painelAzul1 = new javax.swing.JPanel();
        nomeTitulo = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        painelAzul.setBackground(new java.awt.Color(19, 112, 178));

        painelCinza.setBackground(new java.awt.Color(217, 217, 217));
        painelCinza.setMaximumSize(new java.awt.Dimension(900, 600));
        painelCinza.setMinimumSize(new java.awt.Dimension(900, 600));
        painelCinza.setPreferredSize(new java.awt.Dimension(900, 600));
        painelCinza.setLayout(null);

        painelAzul1.setBackground(new java.awt.Color(19, 112, 178));

        nomeTitulo.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 36)); // NOI18N
        nomeTitulo.setForeground(new java.awt.Color(255, 255, 255));
        nomeTitulo.setText("Tarefas do Aluno");

        javax.swing.GroupLayout painelAzul1Layout = new javax.swing.GroupLayout(painelAzul1);
        painelAzul1.setLayout(painelAzul1Layout);
        painelAzul1Layout.setHorizontalGroup(
                painelAzul1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(painelAzul1Layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(nomeTitulo)
                                .addContainerGap(420, Short.MAX_VALUE)));
        painelAzul1Layout.setVerticalGroup(
                painelAzul1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(painelAzul1Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(nomeTitulo)
                                .addContainerGap(20, Short.MAX_VALUE)));

        painelCinza.add(painelAzul1);
        painelAzul1.setBounds(94, 37, 720, 90);

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
                                .addComponent(painelCinza, javax.swing.GroupLayout.PREFERRED_SIZE, 900,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(68, 68, 68)));
        painelAzulLayout.setVerticalGroup(
                painelAzulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(painelAzulLayout.createSequentialGroup()
                                .addGap(78, 78, 78)
                                .addComponent(painelCinza, javax.swing.GroupLayout.PREFERRED_SIZE, 600,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(90, Short.MAX_VALUE)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(painelAzul, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(painelAzul, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
        telaAnterior.setVisible(true);
        if (telaAnterior instanceof TelaAlunosDaTurma) {
            ((TelaAlunosDaTurma) telaAnterior).recarregarTabela();
        }
    }// GEN-LAST:event_jButton1ActionPerformed

    /**
     * Limpa, gerencia o scroll e renderiza os cards dinâmicos na tela.
     */
    private void configurarConteudo() {
        if (this.tituloTarefa != null && !this.tituloTarefa.equals("Atividade")) {
            nomeTitulo.setText(this.tituloTarefa);
        }

        JPanel containerCards = new JPanel();
        containerCards.setBackground(new java.awt.Color(217, 217, 217));
        containerCards.setLayout(new BoxLayout(containerCards, BoxLayout.Y_AXIS));

        try {
            if (idAluno != null) {
                List<TarefaTentadaDTO> tarefas = TentativaDAO.buscarTarefasTentadasPeloAluno(idAluno, idCasa);

                if (tarefas.isEmpty()) {
                    JLabel vazio = new JLabel("Nenhuma tarefa realizada por este aluno.");
                    vazio.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                    vazio.setForeground(new Color(90, 90, 90));
                    vazio.setAlignmentX(LEFT_ALIGNMENT);
                    containerCards.add(vazio);
                } else {
                    for (TarefaTentadaDTO tarefa : tarefas) {
                        containerCards.add(criarCardTarefaClicavel(tarefa));
                        containerCards.add(Box.createRigidArea(new Dimension(0, 12)));
                    }
                }
            }
        } catch (java.sql.SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados do banco: " + e.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }

        // Configuração do ScrollPane para encaixar perfeitamente no painelCinza
        JScrollPane scrollPane = new JScrollPane(containerCards);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        painelCinza.add(scrollPane);
        scrollPane.setBounds(94, 150, 720, 420);

        painelCinza.revalidate();
        painelCinza.repaint();
    }

    /**
     * Desenha o Card conforme o print enviado (Cinza, texto "Atividade: [Nome]")
     * Adiciona o evento de clique do Mouse para redirecionar para a correção.
     */
    private JPanel criarCardTarefaClicavel(TarefaTentadaDTO tarefa) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(217, 217, 217)); // Cor cinza idêntica ao print
        card.setMaximumSize(new Dimension(720, 60));
        card.setPreferredSize(new Dimension(720, 60));
        card.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        card.setAlignmentX(LEFT_ALIGNMENT);

        JLabel labelTitulo = new JLabel("Atividade: " + tarefa.tituloTarefa());
        labelTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        labelTitulo.setForeground(Color.BLACK);

        card.add(labelTitulo, BorderLayout.WEST);

        // Ouvinte de eventos do Mouse para clique e efeito de foco (hover)
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                abrirCorrecaoProfessor(tarefa.idTentativa(), tarefa.tituloTarefa());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(200, 200, 200)); // Escurece de leve ao passar o mouse
                card.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(new Color(217, 217, 217)); // Retorna à cor original
            }
        });

        return card;
    }

    /**
     * Executa a transição para a tela de Correção enviando os IDs de contexto
     * obtidos
     */
    private void abrirCorrecaoProfessor(int idTentativa, String titulo) {
        try {
            if (idTentativa != -1) {
                br.maua.domain.Tentativa tentativaParaCorrigir = new br.maua.domain.Tentativa(idTentativa);

                br.maua.presentation.TelaCorrecaoTarefa.TelaCorrecaoTarefa telaCorrecao = new br.maua.presentation.TelaCorrecaoTarefa.TelaCorrecaoTarefa(
                        tentativaParaCorrigir, this);

                telaCorrecao.setVisible(true);
                this.dispose();
            } else {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Não foi possível localizar o registro da tentativa para este aluno.",
                        "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Erro ao mapear tentativa: " + e.getMessage(),
                    "Erro", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel nomeTitulo;
    private javax.swing.JPanel painelAzul;
    private javax.swing.JPanel painelAzul1;
    private javax.swing.JPanel painelCinza;
    // End of variables declaration//GEN-END:variables
}