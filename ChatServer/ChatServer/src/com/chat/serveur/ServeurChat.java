package com.chat.serveur;

import com.commun.net.Connexion;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.Vector;

/**
 * Cette classe �tend (h�rite) la classe abstraite Serveur et y ajoute le n�cessaire pour que le
 * serveur soit un serveur de chat.
 *
 * @author Abdelmoum�ne Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-15
 */
public class ServeurChat extends Serveur {
    //Attribut historque qui permet au serveur de se rappeler de chaque message sous forme de String dans un Vecteur
    private Vector<String> historique;

    private Vector<Invitation> listInvitations;
    private Vector<SalonPrive> listSalonPrives;

    public Vector<Invitation> getListInvitations() {
        return listInvitations;
    }


    public Vector<SalonPrive> getListSalonPrives() {
        return listSalonPrives;
    }

    /**
     * Cr�e un serveur de chat qui va �couter sur le port sp�cifi�.
     *
     * @param port int Port d'�coute du serveur
     */
    public ServeurChat(int port) {
        super(port);
        listInvitations = new Vector<Invitation>();
        listSalonPrives = new Vector<SalonPrive>();
        historique = new Vector<String>();
    }

    @Override
    public synchronized boolean ajouter(Connexion connexion) {
        String hist = this.historique();
        if ("".equals(hist)) {
            connexion.envoyer("OK");
        }
        else {
            connexion.envoyer("HIST " + hist);
        }
        return super.ajouter(connexion);
    }
    /**
     * Valide l'arriv�e d'un nouveau client sur le serveur. Cette red�finition
     * de la m�thode h�rit�e de Serveur v�rifie si le nouveau client a envoy�
     * un alias compos� uniquement des caract�res a-z, A-Z, 0-9, - et _.
     *
     * @param connexion Connexion la connexion repr�sentant le client
     * @return boolean true, si le client a valid� correctement son arriv�e, false, sinon
     */
    @Override
    protected boolean validerConnexion(Connexion connexion) {

        String aliasFourni = connexion.getAvailableText().trim();
        char c;
        int taille;
        boolean res = true;
        if ("".equals(aliasFourni)) {
            return false;
        }
        taille = aliasFourni.length();
        for (int i=0;i<taille;i++) {
            c = aliasFourni.charAt(i);
            if ((c<'a' || c>'z') && (c<'A' || c>'Z') && (c<'0' || c>'9')
                    && c!='_' && c!='-') {
                res = false;
                break;
            }
        }
        if (!res)
            return false;
        for (Connexion cnx:connectes) {
            if (aliasFourni.equalsIgnoreCase(cnx.getAlias())) { //alias d�j� utilis�
                res = false;
                break;
            }
        }
        if (!res)
            return false;
        connexion.setAlias(aliasFourni);
        return true;
    }

    /**
     * Retourne la liste des alias des connect�s au serveur dans une cha�ne de caract�res.
     *
     * @return String cha�ne de caract�res contenant la liste des alias des membres connect�s sous la
     * forme alias1:alias2:alias3 ...
     */
    public String list() {
        String s = "";
        for (Connexion cnx:connectes)
            s+=cnx.getAlias()+":";
        return s;
    }
    /**
     * Retourne la liste des messages de l'historique de chat dans une cha�ne
     * de caract�res.
     *
     * @return String cha�ne de caract�res contenant la liste des alias des membres connect�s sous la
     * forme message1\nmessage2\nmessage3 ...
     */
    public void ajouterHistorique(String msg,String alias) {
        historique.add(alias + " >> " + msg);
    }

    public String historique() {
        String s = "";
        for (int i = 0; i < historique.size(); i++){
            s += historique.elementAt(i) + '\n';
        }
        return s;
    }

    public void envoyerATousSauf(String str,String aliasExpediteur){
        String s = "";
        for (Connexion cnx:connectes){
            if (!cnx.getAlias().equalsIgnoreCase(aliasExpediteur)){
                cnx.envoyer(aliasExpediteur + " >> " + str);
            }
        }
    }

    public void envoyerPrive(String aliasExpediteur, String aliasInvite) {

        String[] split = aliasInvite.split(" ");
        String message = aliasExpediteur + " ";
        for(int i=1;i<split.length;i++){
            message+=split[i]+" ";
        }

        if(getConnexionParAlias(split[0]) != null){
            if(listSalonPrives.contains(new SalonPrive(aliasExpediteur,split[0]))){
                Connexion connexionAliasInvite = getConnexionParAlias(split[0]);
                connexionAliasInvite.envoyer(message);
            }
        }



        /*
        Connexion cnx = null;

        if(listSalonPrives.contains(new SalonPrive(aliasExpediteur,aliasInvite))) {
            cnx = getConnexionParAlias(aliasInvite);
            cnx.envoyer(split[0] + " >> " + split[1]);
        }

         */
    }

    public void addSalonPrive(SalonPrive salonPrive){
        listSalonPrives.add(salonPrive);
    }
    public boolean removeSalonPrive(SalonPrive salonPrive){
        if(listSalonPrives.contains(salonPrive)) {
            listSalonPrives.remove(salonPrive);
            return true;
        }
        return false;
    }

    public boolean addInvitation(Invitation invite){
        if(!listInvitations.contains(invite)) {
            listInvitations.add(invite);
            return true;
        }
        return false;
    }

    public void removeInvitation(String alias1, String alias2){
        listInvitations.remove(new Invitation(alias1, alias2));
    }

    public Connexion getConnexionParAlias(String alias) {
        for (Connexion cnx : connectes) {
            if (cnx.getAlias().equalsIgnoreCase(alias)) {
                return cnx;
            }
        }
        return null;
    }
    public boolean quitterSalonPrive(String aliasHote,String aliasInvite){
        boolean quitte = false;
        SalonPrive quitterSalon = new SalonPrive(aliasHote,aliasInvite); //on crée un salon à effacer

        if(listSalonPrives.remove(quitterSalon)){ // On s'Assure de pouvoir effacer le salon, si oui, on return true

            Connexion cnxInvite= getConnexionParAlias(aliasInvite);

            if(cnxInvite!=null){ //Si on a bel et bien encore un invité de l'autre côté du salon, on lui envoie un message pour dire que l'hôte à quitté
                cnxInvite.envoyer("QUIT "+ aliasHote);
            }
            quitte = true;
        }
        return quitte; // Aucun invité n'a été trouvé, on ne peut pas effacer le salon
    }

    public String listeInvitations(String alias) {
        String s = "";
        for (Invitation invitations: listInvitations) {
            if(invitations.getAliasInvite().equalsIgnoreCase(alias)){
                s += invitations.getAliasHote() + ":";
            }
        }
        if(s.isEmpty()){
            s = "Vous Avez Aucune Invitation";
        }
        return s;
    }


}
