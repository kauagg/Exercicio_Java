import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PanePergunta1 extends JPanel {
    private JRadioButton rb1, rb2, rb3, rb4;
    private ButtonGroup grupo;
    private Quiz quiz;

    public PanePergunta1(Quiz quiz) {
        this.quiz = quiz;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 400));

        // Painel da pergunta
        JPanel panelPergunta = new JPanel();
        panelPergunta.setLayout(new BoxLayout(panelPergunta, BoxLayout.Y_AXIS));
        panelPergunta.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblPergunta = new JLabel("<html><div style='text-align: center;'><h2>Pergunta 1</h2><br>Qual é o resultado de 8 + 5?</div></html>");
        lblPergunta.setAlignmentX(CENTER_ALIGNMENT);

        // Painel das alternativas
        JPanel panelAlternativas = new JPanel();
        panelAlternativas.setLayout(new BoxLayout(panelAlternativas, BoxLayout.Y_AXIS));
        
        rb1 = new JRadioButton("11");
        rb2 = new JRadioButton("12");
        rb3 = new JRadioButton("13");
        rb4 = new JRadioButton("14");

        // Agrupar radio buttons
        grupo = new ButtonGroup();
        grupo.add(rb1);
        grupo.add(rb2);
        grupo.add(rb3);
        grupo.add(rb4);

        panelAlternativas.add(rb1);
        panelAlternativas.add(rb2);
        panelAlternativas.add(rb3);
        panelAlternativas.add(rb4);

        // Botão verificar
        JButton btnVerificar = new JButton("Verificar");
        btnVerificar.setAlignmentX(CENTER_ALIGNMENT);
        btnVerificar.addActionListener(this::verificarResposta);

        panelPergunta.add(lblPergunta);
        panelPergunta.add(Box.createRigidArea(new Dimension(0, 20)));
        panelPergunta.add(panelAlternativas);
        panelPergunta.add(Box.createRigidArea(new Dimension(0, 20)));
        panelPergunta.add(btnVerificar);

        add(panelPergunta, BorderLayout.CENTER);
    }

    private void verificarResposta(ActionEvent evt) {
        if (rb3.isSelected()) {
            JOptionPane.showMessageDialog(this, "Resposta correta!", "Resultado", JOptionPane.INFORMATION_MESSAGE);
            Quiz.pontuacao += 1;
        } else if (grupo.getSelection() == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma alternativa!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        } else {
            JOptionPane.showMessageDialog(this, "Resposta incorreta! A resposta correta é 13.", "Resultado", JOptionPane.ERROR_MESSAGE);
        }
        quiz.mostrarProximoPainel(this);
    }

    public void reset() {
        grupo.clearSelection();
    }
}