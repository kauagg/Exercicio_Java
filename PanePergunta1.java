import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PanePergunta1 extends JPanel {
    private JRadioButton rb1, rb2, rb3, rb4;
    private ButtonGroup grupo;
    private Quiz quiz;
    private JLabel lblFeedback;

    public PanePergunta1(Quiz quiz) {
        this.quiz = quiz;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 400));

        JPanel panelPergunta = new JPanel();
        panelPergunta.setLayout(new BoxLayout(panelPergunta, BoxLayout.Y_AXIS));
        panelPergunta.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblPergunta = new JLabel("<html><div style='text-align: center;'><h2>Pergunta 1</h2><br>Qual é o resultado de 8 + 5?</div></html>");
        lblPergunta.setAlignmentX(CENTER_ALIGNMENT);

        JPanel panelAlternativas = new JPanel();
        panelAlternativas.setLayout(new BoxLayout(panelAlternativas, BoxLayout.Y_AXIS));
        
        rb1 = new JRadioButton("11");
        rb2 = new JRadioButton("12");
        rb3 = new JRadioButton("13");
        rb4 = new JRadioButton("14");

        grupo = new ButtonGroup();
        grupo.add(rb1);
        grupo.add(rb2);
        grupo.add(rb3);
        grupo.add(rb4);

        panelAlternativas.add(rb1);
        panelAlternativas.add(rb2);
        panelAlternativas.add(rb3);
        panelAlternativas.add(rb4);

        // Label para feedback imediato
        lblFeedback = new JLabel(" ");
        lblFeedback.setAlignmentX(CENTER_ALIGNMENT);
        lblFeedback.setForeground(Color.BLUE);

        JButton btnVerificar = new JButton("Verificar");
        btnVerificar.setAlignmentX(CENTER_ALIGNMENT);
        btnVerificar.addActionListener(this::verificarResposta);

        panelPergunta.add(lblPergunta);
        panelPergunta.add(Box.createRigidArea(new Dimension(0, 20)));
        panelPergunta.add(panelAlternativas);
        panelPergunta.add(Box.createRigidArea(new Dimension(0, 10)));
        panelPergunta.add(lblFeedback);
        panelPergunta.add(Box.createRigidArea(new Dimension(0, 10)));
        panelPergunta.add(btnVerificar);

        add(panelPergunta, BorderLayout.CENTER);
    }

    private void verificarResposta(ActionEvent evt) {
        if (rb3.isSelected()) {
            lblFeedback.setText("✅ Resposta correta!");
            lblFeedback.setForeground(Color.GREEN);
            Quiz.pontuacao += 1;
        } else if (grupo.getSelection() == null) {
            lblFeedback.setText("⚠️ Selecione uma alternativa!");
            lblFeedback.setForeground(Color.ORANGE);
            return;
        } else {
            lblFeedback.setText("❌ Resposta incorreta! A resposta correta é 13.");
            lblFeedback.setForeground(Color.RED);
        }
        
        // Aguardar 2 segundos antes de mudar de pergunta
        Timer timer = new Timer(2000, e -> {
            quiz.mostrarProximoPainel(this);
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void reset() {
        grupo.clearSelection();
        lblFeedback.setText(" ");
    }
}