package vue;

import com.chat.tictactoe.EtatPartieTicTacToe;
import controleur.EcouteurTicTacToe;
import observer.Observable;
import observer.Observateur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PanneauTicTacToe extends JPanel implements Observateur {
    private JButton[][] boutons = new JButton[3][3];
    private EtatPartieTicTacToe partie;
    private ActionListener ecouteurTicTacToe;

    public PanneauTicTacToe(EtatPartieTicTacToe partie) {
        this.partie = partie;
        char[][] etatPlateau = partie.getEtatPlateau();
        this.setLayout(new GridLayout(3,3));
        ecouteurTicTacToe = new EcouteurTicTacToe(null, null); //on fait en sorte d'envoyer le signe du coup
        for (int i=0;i<boutons.length;i++)
            for (int j=0;j<boutons[i].length;j++) {
                boutons[i][j] = new JButton();
                boutons[i][j].setActionCommand(i+""+j);
                boutons[i][j].setBackground(Color.WHITE);
                this.add(boutons[i][j]);
                if (etatPlateau[i][j]!='.')
                    boutons[i][j].setIcon(ServiceImages.getIconePourSymbole(etatPlateau[i][j]));
            }
        //Connecter l'observateur sur l'observable :
        partie.ajouterObservateur(this);
    }
    public void setEcouteurTicTacToe(ActionListener ecouteurTicTacToe) {
        this.ecouteurTicTacToe = ecouteurTicTacToe;

        for (int i=0;i<boutons.length;i++)
            for (int j=0;j<boutons[i].length;j++)
                boutons[i][j].addActionListener(ecouteurTicTacToe);
    }

    @Override
    public void seMettreAJour(Observable observable) {
        //On regade si jamais le symbole correspondant à une case n'est pas le '.', qui signifie que c'est une case vide. Sinon, on change le symbole de la case sur laquelle le coup a été joué
        if (observable instanceof EtatPartieTicTacToe) {
            char[][] etat = ((EtatPartieTicTacToe) observable).getEtatPlateau();

            // On demande à Swing de faire la mise à jour sur le thread dédié
            SwingUtilities.invokeLater(() -> {
                for (int i=0; i<3; i++){
                    for (int j = 0; j < 3; j++) {
                        if (etat[i][j] != '.'){
                            boutons[i][j].setIcon(ServiceImages.getIconePourSymbole(etat[i][j]));
                        } else {
                            boutons[i][j].setIcon(null);
                        }
                    }
                }
                this.repaint();
                this.revalidate();
            });
        }

    }
}