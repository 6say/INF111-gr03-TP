package vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-11-01
 */
public class PanneauChat extends JPanel {
    protected JTextArea zoneChat;
    protected JTextField champDeSaisie;
    protected JScrollPane scroll;
    protected JPanel priver = new JPanel();


    public PanneauChat() {
        initialiserComponent();

    }

    public void initialiserComponent(){
        this.setLayout(new BorderLayout());
        this.add(priver,BorderLayout.NORTH);
        champDeSaisie = new JTextField();
        champDeSaisie.setEnabled(true);


        zoneChat = new JTextArea();
        zoneChat.setEditable(false);
        scroll = new JScrollPane(zoneChat,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVisible(true);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(scroll, BorderLayout.CENTER);
        this.add(champDeSaisie, BorderLayout.SOUTH);
    }

    public void ajouter(String msg) {
        zoneChat.append("\n"+msg);
    }

    public void setEcouteur(ActionListener ecouteur) {
        champDeSaisie.addActionListener(ecouteur);

    }

    public void vider() {
        this.zoneChat.setText("");
    }
}