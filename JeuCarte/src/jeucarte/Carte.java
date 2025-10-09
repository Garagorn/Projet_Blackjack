/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jeucarte;

/**
 *
 * @author tellier212
 */
public class Carte {
    public String couleur;
    public String hauteur;

    public Carte(String hauteur,String couleur) {
        this.couleur = couleur;
        this.hauteur = hauteur;
    }

    @Override
    public String toString() {
        return "Carte : " + hauteur + " de " + couleur;
    }
    
}
