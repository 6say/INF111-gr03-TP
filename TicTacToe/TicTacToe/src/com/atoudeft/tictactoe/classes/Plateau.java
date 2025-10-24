package com.atoudeft.tictactoe.classes;

import com.atoudeft.tictactoe.MethodeNonImplementeeException;
import javafx.geometry.Pos;
import jdk.nashorn.internal.ir.Symbol;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
public final class Plateau {
    private final Symbole[][] grille = new Symbole[3][3];
    private int casesRemplies = 0;
    public Symbole get(int ligne, int colonne) {
        return grille[ligne][colonne]; }
    public boolean estVide(Position p) {
        return grille[p.getLigne()][p.getColonne()] == null;
    }
    public int getNombreCasesRemplies() {
        return casesRemplies;
    }
    public boolean estPlein() {
        return casesRemplies == 9;
    }
    public boolean placer(Coup coup) {
        Position position = coup.getPosition(); //On récupère la position du coup de la classe position

        if(estVide(position))//Si jamais la case est vide, on met la position jouée sur le plateau
        {
         grille[position.getLigne()][position.getColonne()] = coup.getSymbole();//On met le coup joué à la ligne et la collone correspondante ainsi que le bon symbole
            casesRemplies++; //on incrémente le nombre de cases remplies
            return  true;   //on retourne true une fois l'opération effectuée

        }
        return  false;//Dans le cas contraire, cela veut dire que le coup à déjà été joué, donc on retourne false
    }
    public List<Position> ligneGagnante() {
        int[][][] lignes = {
            {{0,0},{0,1},{0,2}}, {{1,0},{1,1},{1,2}}, {{2,0},{2,1},{2,2}},//Lignes 1, 2 et 3 (je les note comme ça malgré les 0 1 et 2)
            {{0,0},{1,0},{2,0}}, {{0,1},{1,1},{2,1}}, {{0,2},{1,2},{2,2}},//Collones 1, 2 et 3
            {{0,0},{1,1},{2,2}}, {{0,2},{1,1},{2,0}} //Correspond aux diagonales
        };

        for (int[][] ligne: lignes) {
            Symbole s1 = grille[ligne[0][0]] [ligne[0][1]]; //On regarde dans le tableau lignes la première case et ses coordonnées (donc pour la première itération on tombe dans la case en haut à gauche)
            Symbole s2= grille[ligne[1][0]][ligne[1][1]]; //On regarde dans le tableau lignes la deuxième case et ses coordonneés (donc pour la première itération on tombe dans la case du milieu en haut)
            Symbole s3 = grille[ligne[2][0]][ligne[2][1]];//On regarde dans le tableau lignes la troisième case et ses coordonnées (donc pour la première itération on tombe dans la case en haut à droite)


            if(s1 != null && s1==s2 && s2==s3){//On regarde si jamais s1 contient un symbole (vu que la ligne gagnante doit être remplie) et ensuite on compare les symboles avec s2 et s3, si jamais ce sont les mêmes, on rentre dans la boucle pour retourner la ligne gaganante
                List<Position> ligneGagnante = new ArrayList<>();
                ligneGagnante.add(new Position(ligne[0][0], ligne[0][1]));//On rentre la position de s1
                ligneGagnante.add(new Position(ligne[1][0], ligne[1][1]));//On rentre la position de s2
                ligneGagnante.add(new Position(ligne[2][0], ligne[2][1]));//On rentre la position de s3
                return  ligneGagnante; //On retourne la ligne gagnante
            }
        }
        return Collections.emptyList(); //On retourne une liste vide vu qu'ucune position n'est gagnante (code que vous avez donné dans la question 4.4)
    }
}