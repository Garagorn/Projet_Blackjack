package vue;

import modele.cartes.Carte;

/**
 * Interface représentant un écouteur d'événements de clic sur une carte.
 * 
 * Toute classe implémentant cette interface doit définir le comportement 
 * à effectuer lorsqu'une carte est cliquée.
 * 
 * Utilisée principalement pour gérer la communication entre la vue (VuePaquetLisible) 
 * et le contrôleur.
 * 
 * @author siaghi231
 */
public interface EcouteurCarteClique {

    /**
     * Méthode appelée lorsqu'une carte est cliquée.
     * 
     * @param carte La carte qui a été cliquée.
     */
    void carteCliquee(Carte carte);
}
