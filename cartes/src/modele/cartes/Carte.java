/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modele.cartes;

import java.util.Objects;

/**
 * Representation d'une cartes
 */
public class Carte {
    public String couleur; 
    public String hauteur;

    /**
     * Constructeur pour une carte
     * @param hauteur Hauteur de la carte
     * @param couleur Couleur de la carte
     */
    public Carte(String hauteur,String couleur) {
        this.couleur = couleur;
        this.hauteur = hauteur;
    }

    @Override
    public String toString() {
        return "Carte : " + hauteur + " de " + couleur;
    }
    
    @Override
    public int hashCode() {
      return Objects.hash(hauteur, couleur);
    }

    /**
     * Compare 2 Cartes, Les deux cartes sont egales si elles partagent la  meme hauteur et  couleur
     * @param o Autre carte a comparer
     * @return Bool Les 2 cartes sont  egales ?
     */
    @Override
    public boolean equals(Object o) {
      if (this == o) { //Compare a lui meme
        return true;
      }
      if (!(o instanceof Carte)) return false; //L'objet de la omparaison n'est pas une carte 
      Carte autre = (Carte) o; //Recuperation  de l'autre carte
      return this.hauteur.equals(autre.hauteur) && this.couleur.equals(autre.couleur); //Meme (hauteur,couleur) 
    }
}
