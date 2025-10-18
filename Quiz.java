import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;

public class Quiz extends JFrame {
    public static int pontuacao = 0;
    public static int tempoRestante = 120; // 2 minutos
    public static String nomeJogador = "";
    private JPanel panelPrincipal;
    private CardLayout cardLayout;
    private Timer timer;
    private JLabel lblTempo;
    
    public static PanePergunta1 p1;
    public static PanePergunta2 p2;
    public static PanePergunta3 p3;
    public static PanePergunta4 p4;
    public static PanePergunta5 p5;
    
    // Ranking
    public static ArrayList<RankingEntry> ranking = new ArrayList<>();

    public Quiz() {
        initComponents();
        setupFrame();
        iniciarTelaBoasVindas();
    }

    private void initComponents() {
        setTitle("Quiz - POO II");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        cardLayout = new CardLayout();
        panelPrincipal = new JPanel(cardLayout);
        panelPrincipal.setPreferredSize(new Dimension(600, 400));

        p1 = new PanePergunta1(this);
        p2 = new PanePergunta2(this);
        p3 = new PanePergunta3(this);
        p4 = new PanePergunta4(this);
        p5 = new PanePergunta5(this);

        panelPrincipal.add(p1, "P1");
        panelPrincipal.add(p2, "P2");
        panelPrincipal.add(p3, "P3");
        panelPrincipal.add(p4, "P4");
        panelPrincipal.add(p5, "P5");

        add(panelPrincipal);
        pack();
        setLocationRelativeTo(null);
    }

    // NOVA FUNCIONALIDADE 1: Tela de boas-vindas
    private void iniciarTelaBoasVindas() {
        nomeJogador = JOptionPane.showInputDialog(this, 
            "Bem-vindo ao Quiz de POO II!\n\nDigite seu nome:",
            "Tela de Boas-Vindas",
            JOptionPane.QUESTION_MESSAGE);
            
        if (nomeJogador == null || nomeJogador.trim().isEmpty()) {
            nomeJogador = "Jogador";
        }
        
        JOptionPane.showMessageDialog(this,
            "Olá " + nomeJogador + "!\n\nVocê tem 2 minutos para responder 5 perguntas.\nBoa sorte!",
            "Instruções",
            JOptionPane.INFORMATION_MESSAGE);
        
        iniciarCronometro();
        cardLayout.show(panelPrincipal, "P1");
    }

    // NOVA FUNCIONALIDADE 2: Cronômetro
    private void iniciarCronometro() {
        // Painel do tempo
        JPanel panelTempo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblTempo = new JLabel("Tempo: " + formatarTempo(tempoRestante));
        panelTempo.add(lblTempo);
        add(panelTempo, BorderLayout.NORTH);
        
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                tempoRestante--;
                SwingUtilities.invokeLater(() -> {
                    lblTempo.setText("Tempo: " + formatarTempo(tempoRestante));
                });
                
                if (tempoRestante <= 0) {
                    timer.cancel();
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(Quiz.this, 
                            "Tempo esgotado!", "Fim do Quiz", JOptionPane.WARNING_MESSAGE);
                        exibirPontuacaoFinal();
                    });
                }
            }
        }, 1000, 1000); // Atualiza a cada segundo
    }

    private String formatarTempo(int segundos) {
        int min = segundos / 60;
        int seg = segundos % 60;
        return String.format("%02d:%02d", min, seg);
    }

    private void setupFrame() {
        cardLayout.show(panelPrincipal, "P1");
    }

    public void mostrarProximoPainel(JPanel painelAtual) {
        if (painelAtual instanceof PanePergunta1) {
            cardLayout.show(panelPrincipal, "P2");
        } else if (painelAtual instanceof PanePergunta2) {
            cardLayout.show(panelPrincipal, "P3");
        } else if (painelAtual instanceof PanePergunta3) {
            cardLayout.show(panelPrincipal, "P4");
        } else if (painelAtual instanceof PanePergunta4) {
            cardLayout.show(panelPrincipal, "P5");
        } else if (painelAtual instanceof PanePergunta5) {
            timer.cancel();
            exibirPontuacaoFinal();
        }
    }

    // NOVA FUNCIONALIDADE 3: Ranking
    private void exibirPontuacaoFinal() {
        // Adicionar ao ranking
        ranking.add(new RankingEntry(nomeJogador, pontuacao, tempoRestante));
        
        // Ordenar ranking (maior pontuação, menor tempo)
        Collections.sort(ranking);
        
        String mensagem = String.format("Sua pontuação final: %d/5\n", pontuacao);
        if (pontuacao == 5) {
            mensagem += "🎉 Excelente! Você acertou todas!";
        } else if (pontuacao >= 3) {
            mensagem += "👍 Bom trabalho!";
        } else {
            mensagem += "📚 Continue estudando!";
        }
        
        // Mostrar ranking
        mensagem += "\n\n🏆 RANKING:\n";
        for (int i = 0; i < Math.min(ranking.size(), 5); i++) {
            RankingEntry entry = ranking.get(i);
            mensagem += String.format("%d. %s - %d pontos (%s)\n", 
                i + 1, entry.nome, entry.pontuacao, formatarTempo(entry.tempoRestante));
        }
        
        JOptionPane.showMessageDialog(this, mensagem, "Quiz Finalizado", JOptionPane.INFORMATION_MESSAGE);
        
        // Opção para reiniciar
        int resposta = JOptionPane.showConfirmDialog(this, 
            "Deseja reiniciar o quiz?", "Reiniciar", JOptionPane.YES_NO_OPTION);
        if (resposta == JOptionPane.YES_OPTION) {
            reiniciarQuiz();
        } else {
            System.exit(0);
        }
    }

    private void reiniciarQuiz() {
        pontuacao = 0;
        tempoRestante = 120;
        p1.reset();
        p2.reset();
        p3.reset();
        p4.reset();
        p5.reset();
        
        if (timer != null) {
            timer.cancel();
        }
        
        iniciarTelaBoasVindas();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            new Quiz().setVisible(true);
        });
    }
    
    // Classe para o ranking
    static class RankingEntry implements Comparable<RankingEntry> {
        String nome;
        int pontuacao;
        int tempoRestante;
        
        public RankingEntry(String nome, int pontuacao, int tempoRestante) {
            this.nome = nome;
            this.pontuacao = pontuacao;
            this.tempoRestante = tempoRestante;
        }
        
        @Override
        public int compareTo(RankingEntry outro) {
            if (this.pontuacao != outro.pontuacao) {
                return Integer.compare(outro.pontuacao, this.pontuacao); // Decrescente
            }
            return Integer.compare(outro.tempoRestante, this.tempoRestante); // Mais tempo = melhor
        }
    }
}