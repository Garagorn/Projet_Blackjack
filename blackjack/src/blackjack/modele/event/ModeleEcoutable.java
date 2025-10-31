package blackjack.modele.event;

/**
 * Interface représentant un modèle observable.
 * 
 * Un modèle écoutable permet d'ajouter ou de retirer des écouteurs
 * qui seront notifiés lors de changements dans le modèle.
 * 
 * @author siaghi231
 */
public interface ModeleEcoutable {

    /**
     * Ajoute un écouteur au modèle.
     * 
     * @param e L'écouteur à ajouter.
     */
    void ajouterEcouteur(EcouteurModele e);

    /**
     * Retire un écouteur du modèle.
     * 
     * @param e L'écouteur à retirer.
     */
    void retirerEcouteur(EcouteurModele e);

}
