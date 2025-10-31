package blackjack.modele.event;

import java.util.ArrayList;

/**
 * Classe abstraite fournissant une implémentation de base du pattern observateur
 * pour les modèles écoutables.
 * 
 * Cette classe gère une liste d'écouteurs (observers) qui sont notifiés à chaque
 * changement du modèle via la méthode {@link #fireChangement()}.
 * 
 * Elle implémente l'interface {@link ModeleEcoutable}.
 * 
 * @author siaghi231
 */
public abstract class AbstractModeleEcoutable implements ModeleEcoutable {

    /**
     * Liste des écouteurs enregistrés pour ce modèle.
     */
    private ArrayList<EcouteurModele> ecouteurs = new ArrayList<>();

    /**
     * Constructeur par défaut.
     * Initialise la liste des écouteurs du modèle.
     */
    public AbstractModeleEcoutable() {
        // Rien à initialiser de spécial ici.
    }

    /**
     * Ajoute un écouteur au modèle.
     * 
     * @param e L'écouteur à ajouter
     */
    public void ajouterEcouteur(EcouteurModele e) {
        ecouteurs.add(e);
    }

    /**
     * Retire un écouteur du modèle.
     * 
     * @param e L'écouteur à retirer
     */
    public void retirerEcouteur(EcouteurModele e) {
        ecouteurs.remove(e);
    }

    /**
     * Notifie tous les écouteurs enregistrés que le modèle a changé.
     */
    protected void fireChangement() {
        for (EcouteurModele e : ecouteurs) {
            e.modeleMiseAJour(this);
        }
    }
}

