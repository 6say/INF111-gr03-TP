package controleur;

import com.chat.client.ClientChat;
import vue.PanneauChat;
import vue.PanneauChatPrive;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-11-01
 */
public class EcouteurChatPrive extends EcouteurChatPublic implements ActionListener {
    private String alias;
    public EcouteurChatPrive(String alias, ClientChat clientChat, PanneauChat panneauChat) {
        super(clientChat, panneauChat);
        this.alias = alias;
    }
    //à compléter (redéfinir la méthode actionPerformed())
    @Override
    public void actionPerformed(ActionEvent evt) {
        Object source = evt.getSource();
        if(source instanceof JButton){
            JButton button = (JButton) source;
            if(button.getText().equalsIgnoreCase("Inviter TTT")){
                clientChat.envoyer("TTT " + alias);
            }
            if(button.getText().equalsIgnoreCase("Accepter")){
                clientChat.envoyer("TTT " + alias);
            }
            if(button.getText().equalsIgnoreCase("REFUSER")){
                clientChat.envoyer("DECLINE " + alias);
                PanneauChatPrive chatPriver = (PanneauChatPrive) panneauChat;
                chatPriver.invitationAJouerAnnulee();
            }
        }

        if(source instanceof JTextField){
            String message = ((JTextField) source).getText();
            if(!message.isEmpty()){
            switch (message){
                case "QUIT":
                    clientChat.envoyer("QUIT " + alias);
                    ((JTextField) source).setText("");
                    break;
                case "ABANDON":
                    clientChat.envoyer("ABANDON " + alias);
                    ((JTextField) source).setText("");
                    break;
                default:
                    clientChat.envoyer("PRV " + alias + " " + message);
                    panneauChat.ajouter("MOI>>" + message);
                    ((JTextField) source).setText("");
                }
            }
        }

    }
}