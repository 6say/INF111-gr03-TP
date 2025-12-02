package controleur;

import com.chat.client.ClientChat;
import vue.PanneauInvitations;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

public class EcouteurInvitation implements ActionListener {

    private ClientChat clientChat;
    private PanneauInvitations panneauInvitations;
    private int delais = 75;

    public EcouteurInvitation(ClientChat clientChat, PanneauInvitations panneauInvitations){
        this.clientChat = clientChat;
        this.panneauInvitations = panneauInvitations;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = null;
        if(e.getSource() instanceof JButton){
             button = (JButton) e.getSource();
        }

       List<String> list = panneauInvitations.getElementsSelectionnes();
        Iterator<String> iterator = list.iterator();
       if(button.getText().equals("+")){

          while (iterator.hasNext()){
              clientChat.envoyer("JOIN " + iterator.next());
              try {
                  //Delais entre chaque envois de message, si non sa envoie 1 seul gros String
                  Thread.sleep(delais);
              } catch (InterruptedException ex) {
                  throw new RuntimeException(ex);
              }
          }
       }
       if(button.getText().equals("x")){
           for(String alias: list){
               clientChat.envoyer("DECLINE " + alias);
               panneauInvitations.retirerInvitationRecue(alias);
               //Delais entre chaque envois de message, si non sa envoie 1 seul gros String
               try {
                   Thread.sleep(delais);
               } catch (InterruptedException ex) {
                   throw new RuntimeException(ex);
               }
           }
       }
    }
}
