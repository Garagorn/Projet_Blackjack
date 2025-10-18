/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modele.cartes;

/**
 *
 * @author tellier212@CAMPUS
 */
public interface Joueur {
    
    public String getNom();
    
    public int getScore();
    
    public Paquet getMain();
    
    //public Carte jouerCarte();
    
    public void recevoirCarte(Carte c);
}
