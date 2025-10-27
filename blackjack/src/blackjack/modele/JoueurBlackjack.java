/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blackjack.modele;

import modele.cartes.Carte;
import modele.cartes.Joueur;
import modele.cartes.Paquet;
/**
 *
 * @author tellier212@CAMPUS
 */
public interface JoueurBlackjack extends Joueur{
    
    /**
     * Obtient le nom du joueur.
     * 
     * @return Le nom du joueur sous forme de chaîne de caractères.
     */
    public String getNom();

     /**
     * Obtient le score actuel du joueur.
     * 
     * @return Le score du joueur sous forme d'entier.
     */
    public int getScore();
    
    /**
     * Obtient la main du joueur, c'est-à-dire le paquet de cartes qu'il possède.
     * 
     * @return Le paquet représentant la main du joueur.
     */
    public Paquet getMain();

    /**
     * Reçoit une carte et l'ajoute à la main du joueur.
     * 
     * @param c La carte à recevoir.
     */
    public void recevoirCarte(Carte c);

    /**
     * Tirer une carte, le joueur à le droit ou non de tirer une carte
     * 
     * @return bool Son droit de recevoir une carte
     */
    public boolean tirerCarte();
    
    /**
     * Savoir si le joueur à dépasser les 21 de scores
     * 
     * @return bool 
     */
    public boolean aDepasse();
    
    /**
     * Savoir si le joueur à fini de jouer, il s'est couché et doit attendre la fin de la partie
     * 
     * @return bool 
     */
    public boolean getFini();
        
}
