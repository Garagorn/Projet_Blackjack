/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blackjack.modele;

import modele.cartes.Carte;
import modele.cartes.Paquet;

/**
 *
 * @author tellier212@CAMPUS
 */
public class JoueurBlackjackH implements JoueurBlackjack{
    
    private String nom;
    private int score;
    private Paquet main;
    private boolean aFini;

    public JoueurBlackjackH(String nom) {
        this.nom = nom;
        this.main = new Paquet();
        this.score = 0;
        this.aFini = false;
    }    

    /**
     * Renvoie si le joueur à fini
     * 
     * @return bool L'état de fin de partie du joueur
     */
    @Override
    public boolean getFini() {
        return this.aFini;
    }    
    
    /**
     * Renvoie le nom du joueur IA.
     * 
     * @return Le nom du joueur IA sous forme de chaîne.
     */
    @Override
    public String getNom() {
        return this.nom;
    }
    
    /**
     * Récupère la main du joueur IA.
     * 
     * @return Le paquet représentant la main du joueur IA.
     */
    @Override
    public Paquet getMain() {
        return this.main;
    }
    
    //public Carte jouerCarte() { } // Cette méthode est ici implémentée en-dessous
    
    /**
     * Ajoute une carte reçue à la main du joueur IA.
     * 
     * @param c La carte à ajouter.
     */
    @Override
    public void recevoirCarte(Carte c) {
        main.ajouter(c);
    }

    public int getScore() {
        return this.score;
    }

    public boolean tirerCarte() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * Retourne l'état de la partie du joueur, à moins de 21 de score il continue et à  plus il ne peut plus jouer
     * 
     * @return 
     */
    public boolean aDepasse(){
        if(score>21){
            aFini= true;
            return true;
        }
        return false;
    }

    public boolean finJouer() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
