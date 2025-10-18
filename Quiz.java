import javax.swing.*;
import java.awt.*;

public class Quiz extends JFrame {
    public static int pontuacao = 0;
    private JPanel panelPrincipal;
    private CardLayout cardLayout;
    
    // Painéis das perguntas
    public static PanePergunta1 p1;
    public static PanePergunta2 p2;
    public static PanePergunta3 p3;
    public static PanePergunta4 p4;
    public static PanePergunta5 p5;

    public Quiz() {
        initComponents();
        setupFrame();
    }

    private void initComponents() {
        setTitle("Quiz - POO II");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        // Configura CardLayout para trocar entre painéis
        cardLayout = new CardLayout();
        panelPrincipal = new JPanel(cardLayout);
        panelPrincipal.setPreferredSize(new Dimension(600, 400));

        // Inicializar painéis das perguntas
        p1 = new PanePergunta1(this);
        p2 = new PanePergunta2(this);
        p3 = new PanePergunta3(this);
        p4 = new PanePergunta4(this);
        p5 = new PanePergunta5(this);

        // Adicionar painéis ao CardLayout
        panelPrincipal.add(p1, "P1");
        panelPrincipal.add(p2, "P2");
        panelPrincipal.add(p3, "P3");
        panelPrincipal.add(p4, "P4");
        panelPrincipal.add(p5, "P5");

        add(panelPrincipal);
        pack();
        setLocationRelativeTo(null); // Centralizar na tela
    }

    private void setupFrame() {
        // Mostrar primeiro painel
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
            exibirPontuacaoFinal();
        }
    }

    private void exibirPontuacaoFinal() {
        String mensagem = String.format("Sua pontuação final: %d/5\n", pontuacao);
        if (pontuacao == 5) {
            mensagem += "Excelente! Você acertou todas!";
        } else if (pontuacao >= 3) {
            mensagem += "Bom trabalho!";
        } else {
            mensagem += "Continue estudando!";
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
        // Resetar todos os painéis
        p1.reset();
        p2.reset();
        p3.reset();
        p4.reset();
        p5.reset();
        cardLayout.show(panelPrincipal, "P1");
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
}