package vue;

import javax.swing.*;
import modele.event.EcouteurModele;
import modele.cartes.Paquet;

/**
 * {@code VuePaquet} est une classe abstraite représentant une vue graphique d'un {@link Paquet} de cartes.
 * 
 * Elle observe un modèle de type {@code Paquet} et est conçue pour être mise à jour automatiquement
 * lorsque le modèle change (pattern Observer).
 * 
 * Les sous-classes doivent définir le comportement d'affichage spécifique via la méthode {@code afficherPaquet()}.
 *
 * Cette classe implémente {@code EcouteurModele} pour recevoir les notifications du modèle.
 */
public abstract class VuePaquet extends JPanel implements EcouteurModele {

    /** Le paquet de cartes associé à cette vue. */
    protected Paquet paquet;

    /**
     * Construit une vue abstraite pour un paquet donné.
     * 
     * @param p Le paquet de cartes à observer et représenter graphiquement.
     */
    public VuePaquet(Paquet p) {
        this.paquet = p;
        paquet.ajouterEcouteur(this); // inscription au modèle comme observateur
    }

    /**
     * Retourne le paquet associé à cette vue.
     * 
     * @return Le paquet observé.
     */
    public Paquet getPaquet() {
        return this.paquet;
    }

    /**
     * Méthode abstraite que les sous-classes doivent implémenter pour afficher visuellement le paquet.
     */
    public abstract void afficherPaquet();
}
