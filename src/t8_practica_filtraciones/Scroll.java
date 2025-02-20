package t8_practica_filtraciones;

import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Scroll {

    final private JTextArea TEXTAREA;
    final private JScrollPane JSCROLL;
    private String messageArea;

    public Scroll() {

        TEXTAREA = new JTextArea(50, 20);
        TEXTAREA.setEditable(false);
        TEXTAREA.setWrapStyleWord(true);
        TEXTAREA.setLineWrap(true);

        JSCROLL = new JScrollPane(TEXTAREA);
        JSCROLL.setPreferredSize(new Dimension(200, 200));

        messageArea = "";
        TEXTAREA.setText(messageArea);
    }

    public void setMessage(String message) {
        messageArea = message;
        TEXTAREA.setText(messageArea);
    }
    
    public JScrollPane getJScrollPane() {
        return JSCROLL;
    }
}
