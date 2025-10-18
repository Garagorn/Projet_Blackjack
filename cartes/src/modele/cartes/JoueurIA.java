/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modele.cartes;

import java.util.*;


    /**
     * nom : Nom du joueur
     * main : Paquet du joueur
     * score : Score du  joueur
     * @author tellier212@CAMPUS
     */
public class JoueurIA implements Joueur{
    private final String nom;
    private final Paquet main;
    private final int score;
    private Random rand;

    /**
     * Constructeur du joueur  IA
     * @param nom Nom du joueur
     */
    public JoueurIA(String nom) {
        this.nom = nom;
        this.main = new Paquet();
        this.score =0;
    }

    
    /**
     * Renvoie le nom du joueur
     * @return nom le Nom du joueur
     */
    @Override
    public String getNom() {
        return nom;
    }

    /**
     * Recuperer la main du joueur
     * @return main Le paquet du joueur 
     */
    @Override
    public Paquet getMain() {
        return main;
    }

    /**
     * Recuperer le score du joueur
     * @return score Le score du joueur 
     */
    @Override
    public int getScore() {
        return this.score;
    }
    
    //public Carte jouerCarte(){}
    
    /**
     * Ajouter une carte au paquet
     * @param c La carte recue par le joueur
     */
    @Override
    public void recevoirCarte(Carte c){
        main.ajouter(c);
    }
    
    @Override
    public String toString() {
        return ("Je suis un bot : " + nom + ", j'ai en main= " + main + ", et j'ai " + score + " de score.");
    }
    
    public Carte jouerCarte() {
        int i = rand.nextInt(main.getPaquet().size());
        return main.getPaquet().remove(i);
    }
    
}
