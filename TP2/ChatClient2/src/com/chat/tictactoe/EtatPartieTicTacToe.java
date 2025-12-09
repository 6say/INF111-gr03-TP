package com.chat.tictactoe;

import observer.Observable;

public class EtatPartieTicTacToe extends Observable {
    private char[][] etatPlateau = new char[3][3];

    public EtatPartieTicTacToe() {
        etatPlateau = new char[][]{
                {'.','.','.'},
                {'.','.','.'},
                {'.','.','.'}
        };
    }
    public boolean coup(String strCoup) {
        boolean res = false;

        try{
            String[] coupEssai = strCoup.trim().split(" ");
            //On récupère le coup joué et on s'assure qu'il est bien dans les limites de la grille.
            if(coupEssai.length >=3) {
                char symbole = coupEssai[0].charAt(0);
                int ligne = Integer.parseInt(coupEssai[1]);
                int colonne = Integer.parseInt(coupEssai[2]);

                //On checke si jamais le coup est correct, ensuite on change l'état du booléen et on avertit les observateurs du coup à changer sur le Pannel
                if(ligne >=0 && ligne <3 && colonne >=0 && colonne<3){
                    etatPlateau[ligne][colonne] = symbole;
                    res = true;

                    notifierObservateurs();
                }
            }

        } catch(Exception e){
            System.out.println("Erreur, le coup : " + strCoup + " est invalide.");
        }
        return res;
    }

    @Override
    public String toString() {
        String s = "";
        for (byte i=0;i<3;i++) {
            s+=(byte)(3-i)+" ";
            for (int j=0;j<3;j++)
                s+=((etatPlateau[i][j]==' ')?".": etatPlateau[i][j])+" ";
            s+="\n";
        }
        s+="  ";
        for (char j='1';j<='3';j++)
            s+=j+" ";
        return s;
    }

    public char[][] getEtatPlateau() {
        return etatPlateau;
    }

    public void setEtatPlateau(char[][] etatPlateau) {
        this.etatPlateau = etatPlateau;
    }
}
