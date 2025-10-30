package com.atoudeft.tictactoe.classes;

import com.atoudeft.tictactoe.MethodeNonImplementeeException;

import java.util.ArrayList;
import java.util.List;

public final class Partie {
    private final Plateau plateau = new Plateau();
    private Symbole joueurCourant;
    private StatutPartie statut;

    public Plateau getPlateau()       {
        return plateau;
    }
    public Symbole getJoueurCourant() {
       return joueurCourant;
    }
    public StatutPartie getStatut()   {
        return statut;
    }

    public Partie(Symbole joueurCourant) {
        this.joueurCourant = joueurCourant;
        statut = StatutPartie.EN_COURS;
    }
    public Partie() {
        this(Symbole.X);
    }

    public boolean jouer(Symbole symbole, Position position) {

        if(!isPartieEnCours()){
            return false; //On s'assure que la partie n'est pas terminée
        }
        if(symbole !=joueurCourant){
            System.out.println("Ce n'est pas à votre tour de jouer"); //J'affiche un message au joueur qui a envoyé une commande quand ce n'est pas son tour
            return  false; //on retourne false si jamais un coup a été joué par un joueur dont ce n'est pas le tour
        }
        Coup coup = new Coup(position, symbole); //On crée un objet coup qui va nous permettre de le mettre sur le plateau

        if(plateau.placer(coup)){ //Si on peut placer le coup, on met à jour le statut
            mettreAJourStatutApresCoup();

            if( statut == StatutPartie.EN_COURS){ //Si la partie est encore en cours, on change le statut et le symbole du joueur courant
                joueurCourant = (joueurCourant == Symbole.X) ? Symbole.O : Symbole.X;
            }
            return  true; //On return vrai vu que le coup a bien été joué
        }
        return  false; //on retourne faux vu que rien n'a pu être joué
    }
    public boolean isPartieEnCours() {
        if (statut != StatutPartie.EN_COURS) {
            return false;
        }
        return true;
    }
    private void mettreAJourStatutApresCoup() {
        //On crée un tableau qui va vérifier si regarder si jamais on a une position gagnante

        Symbole gagnant = null;
        System.out.println(plateau.estPlein());
        if(plateau.estPlein()){
            statut = StatutPartie.NULLE;   //Si le plateau est plein alors c'est null
        }
        if(!plateau.ligneGagnante().isEmpty()){       //Si la liste gagnante est null rien faire
           gagnant = plateau.get(plateau.ligneGagnante().get(0).getLigne(), plateau.ligneGagnante().get(0).getColonne() ); //On recupere le symbole du gagnant
           if(gagnant == Symbole.X){                    //Dependament du Gagnant on change le status
               statut = StatutPartie.X_GAGNE;
           }else if(gagnant == Symbole.O){
               statut = StatutPartie.O_GAGNE;
           }
        }
    }

    @Override
    public String toString() {
        String str = "";
        str = plateau +"\n"
                +"Joueur Courant : " + joueurCourant +"\n"
                +"Etat : " + statut + "\n";
        return str;
    }
}