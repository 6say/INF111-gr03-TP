package controleur;

import com.chat.client.ClientChat;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EcouteurTicTacToe implements ActionListener {

    private ClientChat clientChat;
    private String symbole;

    public EcouteurTicTacToe(ClientChat clientChat, String symbole) {
        this.clientChat = clientChat;
        this.symbole = symbole;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JButton){
            String commande =((JButton) e.getSource()).getActionCommand();

            if(commande != null && commande.length() == 2){
                char ligne = commande.charAt(0);
                char colonne = commande.charAt(1);
                //On envoye le coup au serveur dans le format "Coup X 0 0"
                if(clientChat!=null){
                    clientChat.envoyer("COUP " + symbole + " " + ligne + " " + colonne);
                }
            }
        }
    }
}
