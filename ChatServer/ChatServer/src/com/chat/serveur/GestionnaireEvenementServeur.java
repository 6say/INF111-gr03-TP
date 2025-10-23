package com.chat.serveur;

import com.commun.evenement.Evenement;
import com.commun.evenement.GestionnaireEvenement;
import com.commun.net.Connexion;

import java.util.Vector;

/**
 * Cette classe represente un gestionnaire d'evenement d'un serveur. Lorsqu'un serveur reeoit un texte d'un client,
 * il cree un evenement e partir du texte reeu et alerte ce gestionnaire qui reagit en gerant l'evenement.
 *
 * @author Abdelmoumene Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class GestionnaireEvenementServeur implements GestionnaireEvenement {
    private Serveur serveur;

    /**
     * Construit un gestionnaire d'evenements pour un serveur.
     *
     * @param serveur Serveur Le serveur pour lequel ce gestionnaire gere des evenements
     */
    public GestionnaireEvenementServeur(Serveur serveur) {
        this.serveur = serveur;
    }

    /**
     * Methode de gestion d'evenements. Cette methode contiendra le code qui gere les reponses obtenues d'un client.
     *
     * @param evenement L'evenement e gerer.
     */
    @Override
    public void traiter(Evenement evenement) {
        Object source = evenement.getSource();
        Connexion cnx;
        String msg, typeEvenement, aliasExpediteur,aliasInvite;
        ServeurChat serveur = (ServeurChat) this.serveur;

        if (source instanceof Connexion) {
            cnx = (Connexion) source;
            System.out.println("SERVEUR-Recu : " + evenement.getType() + " " + evenement.getArgument());
            typeEvenement = evenement.getType();
            switch (typeEvenement) {
                case "EXIT": //Ferme la connexion avec le client qui a envoye "EXIT":
                    cnx.envoyer("END");
                    serveur.enlever(cnx);
                    cnx.close();
                    break;
                case "LIST": //Envoie la liste des alias des personnes connect�es :
                    cnx.envoyer("LIST " + serveur.list());
                    break;
                case "MSG":
                    aliasExpediteur = cnx.getAlias();
                    msg = evenement.getArgument();
                    serveur.envoyerATousSauf(msg, aliasExpediteur);
                    serveur.ajouterHistorique(msg,aliasExpediteur);
                    break;
                case "JOIN":
                    aliasExpediteur = cnx.getAlias();
                    aliasInvite = evenement.getArgument();
                    if(serveur.getListInvitations().contains(new Invitation(aliasExpediteur, aliasInvite))){
                        serveur.addSalonPrive(new SalonPrive(aliasExpediteur,aliasInvite));
                        cnx.envoyer("JOINOK " + aliasInvite );
                        cnx = serveur.getConnexionParAlias(aliasInvite);
                        cnx.envoyer("JOINOK " + aliasExpediteur);
                    }else{
                        if(!serveur.getListInvitations().contains(new Invitation(aliasInvite, aliasExpediteur))){ //Check is cette invitation existe déjà pour ne pas créer de doublon.
                            serveur.addInvitation(new Invitation(aliasExpediteur, aliasInvite));
                            aliasInvite =  evenement.getArgument().trim();
                            cnx = serveur.getConnexionParAlias(aliasInvite);
                            cnx.envoyer("JOIN " + aliasExpediteur);
                        }

                    }

                    break;
                //Ajoutez ici deautres case pour gerer deautres commandes.
                case "DECLINE":
                    aliasExpediteur = cnx.getAlias();
                    aliasInvite = evenement.getArgument();
                    serveur.removeInvitation(aliasInvite, aliasExpediteur);
                    cnx =  serveur.getConnexionParAlias(aliasInvite);
                    cnx.envoyer("DECLINE");
                    break;
                case "PRV":
                    aliasExpediteur = cnx.getAlias();
                    aliasInvite = evenement.getArgument();
                    msg = evenement.getArgument();
                    if(!serveur.getListSalonPrives().contains(new SalonPrive(aliasExpediteur,aliasInvite)))
                        cnx.envoyer("Vous ne partagez pas un salon privée avec "+aliasInvite);

                    serveur.envoyerPrive(msg,aliasExpediteur,aliasInvite);
                    break;
                case "INV":

                    cnx.envoyer("INV "+ serveur.listeInvitations(cnx.getAlias()).trim());
                    break;
                case "QUIT":
//                    aliasExpediteur = cnx.getAlias();
//                    aliasInvite = evenement.getArgument();
//                    if(!serveur.quitterSalonPrive(aliasExpediteur, aliasInvite)) {
//                        cnx.envoyer("Le salon n'a pas pu être effacé");
//                        serveur.getListSalonPrives().remove(aliasInvite);
//                    }
//                    cnx.envoyer("QUIT : +\n +  Vous avez bien quitté le salon privé");
                    aliasExpediteur = cnx.getAlias();
                    aliasInvite = evenement.getArgument();
                    if(!serveur.quitterSalonPrive(aliasExpediteur, aliasInvite)) {
                        cnx.envoyer("Le salon n'a pas pu être effacé");
                        //serveur.removeSalonPrive(aliasInvite);
                    }
                    cnx.envoyer("QUIT");
                    break;
                default: //Renvoyer le texte recu convertit en majuscules :
                    msg = (evenement.getType() + " " + evenement.getArgument()).toUpperCase();
                    cnx.envoyer(msg);
            }
        }
    }
}