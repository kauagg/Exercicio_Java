import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PanePergunta5 extends JPanel {
    private JRadioButton rb1, rb2, rb3, rb4;
    private ButtonGroup grupo;
    private Quiz quiz;

    public PanePergunta5(Quiz quiz) {
        this.quiz = quiz;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 400));

        JPanel panelPergunta = new JPanel();
        panelPergunta.setLayout(new BoxLayout(panelPergunta, BoxLayout.Y_AXIS));
        panelPergunta.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblPergunta = new JLabel("<html><div style='text-align: center;'><h2>Pergunta 5</h2><br>Qual destes NÃO é um sistema operacional?</div></html>");
        lblPergunta.setAlignmentX(CENTER_ALIGNMENT);

        JPanel panelAlternativas = new JPanel();
        panelAlternativas.setLayout(new BoxLayout(panelAlternativas, BoxLayout.Y_AXIS));
        
        rb1 = new JRadioButton("Windows");
        rb2 = new JRadioButton("Linux");
        rb3 = new JRadioButton("Microsoft Word");
        rb4 = new JRadioButton("macOS");

        grupo = new ButtonGroup();
        grupo.add(rb1);
        grupo.add(rb2);
        grupo.add(rb3);
        grupo.add(rb4);

        panelAlternativas.add(rb1);
        panelAlternativas.add(rb2);
        panelAlternativas.add(rb3);
        panelAlternativas.add(rb4);

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
            JOptionPane.showMessageDialog(this, "Resposta incorreta! A resposta correta é Microsoft Word.", "Resultado", JOptionPane.ERROR_MESSAGE);
        }
        quiz.mostrarProximoPainel(this);
    }

    public void reset() {
        grupo.clearSelection();
    }
}