package modele.cartes;

import java.util.Objects;

/**
 * Représentation d'une carte à jouer.
 * <p>
 * Une carte est caractérisée par une hauteur (valeur) et une couleur (famille).
 * </p>
 * 
 * @author tellier212
 * @author siaghi231
 */
public class Carte {
    /**
     * La couleur (famille) de la carte (ex: Pique, Coeur, Carreau, Trefle).
     */
    public String couleur;

    /**
     * La hauteur (valeur) de la carte (ex: As, 2, Roi, etc.).
     */
    public String hauteur;

    /**
     * Constructeur pour une carte.
     * 
     * @param hauteur La hauteur de la carte.
     * @param couleur La couleur (famille) de la carte.
     */
    public Carte(String hauteur, String couleur) {
        this.couleur = couleur;
        this.hauteur = hauteur;
    }

    /**
     * Retourne le symbole Unicode correspondant à la couleur de la carte.
     * 
     * @return Le symbole de la couleur ("♠", "♥", "♦", "♣") ou chaîne vide si inconnue.
     */
    public String getSymboleCouleur() {
        switch (couleur) {
            case "Pique":
                return "♠";
            case "Coeur":
                return "♥";
            case "Carreau":
                return "♦";
            case "Trefle":
                return "♣";
            default:
                return "";
        }
    }

    /**
     * Retourne la représentation textuelle de la carte.
     * 
     * @return Une chaîne décrivant la carte (ex: "Carte : As de ♠").
     */
    @Override
    public String toString() {
        return "Carte : " + hauteur + " de " + getSymboleCouleur();
    }

    /**
     * Calcule le code de hachage basé sur la hauteur et la couleur de la carte.
     * 
     * @return Le code de hachage.
     */
    @Override
    public int hashCode() {
        return Objects.hash(hauteur, couleur);
    }

    /**
     * Compare cette carte à un autre objet.
     * Deux cartes sont égales si elles ont la même hauteur et la même couleur.
     * 
     * @param o L'objet à comparer.
     * @return true si les cartes sont égales, false sinon.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Carte)) {
            return false;
        }
        Carte autre = (Carte) o;
        return this.hauteur.equals(autre.hauteur) && this.couleur.equals(autre.couleur);
    }
}
