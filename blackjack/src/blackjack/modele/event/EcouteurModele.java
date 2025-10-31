package blackjack.modele.event;

/**
 * Interface représentant un écouteur pour un modèle observable.
 * 
 * Un écouteur modèle est notifié lorsqu'un changement se produit dans le modèle
 * observé via la méthode {@link #modeleMiseAJour(Object)}.
 * 
 * @author siaghi231
 */
public interface EcouteurModele {

    /**
     * Méthode appelée lorsqu'un changement est détecté dans le modèle observé.
     * 
     * @param source L'objet source qui a déclenché la mise à jour, généralement le modèle.
     */
    void modeleMiseAJour(Object source);

}
