package vue;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-11-01
 */
public class PanneauConfigServeur extends JPanel {
    private JTextField txtAdrServeur, txtNumPort;
    private JLabel labelIP, labelPort;

    public PanneauConfigServeur(String adr, int port) {
        //On instancie les labels et les TextFields avec les informations et le nombre de caractères que l'on veut dedans
        labelIP = new JLabel("Adresse IP: ");
        labelPort = new JLabel("Port: ");
        txtAdrServeur = new JTextField(9);
        txtNumPort = new JTextField(5);

        //On transforme la page en GridLayout pour avoir 2 lignes qui séparent l'IP du port du serveur
        this.setLayout(new GridLayout(2,2));
        //On ajoute les informations nécessaires pour l'IP
        this.add(labelIP);
        this.add(txtAdrServeur);
        //On ajoute les informations nécessaires pour le Port
        this.add(labelPort);
        this.add(txtNumPort);
    }
    public String getAdresseServeur() {
        return txtAdrServeur.getText();
    }
    public String getPortServeur() {
        return txtNumPort.getText();
    }
}