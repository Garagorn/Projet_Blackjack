package blackjack.controleur;

import blackjack.modele.actions.Action;
import blackjack.modele.jeu.ModeleBlackjack;
import blackjack.modele.joueurs.Joueur;
import blackjack.modele.util.EtatPartie;
import blackjack.vue.VueBlackjack;

/**
 * Gestionnaire dédié aux actions du joueur humain
 */
public class GestionnaireActionsJoueurHumain{
    private ModeleBlackjack modele;
    private VueBlackjack vue;
    public GestionnaireActionsJoueurHumain(ModeleBlackjack modele, VueBlackjack vue) {
        this.modele = modele;
        this.vue = vue;

    }
    
    public void executerAction(String nomAction) {
        if (!estTourJoueurHumain()) {
            return;
        }
        
        Action action = obtenirActionValide(nomAction);
        if (action == null) {
            return;
        }
        modele.executerAction(action);
        gererFinAction(action);
    }
    
    private boolean estTourJoueurHumain() {
        if (modele.getEtatPartie() != EtatPartie.TOUR_JOUEURS) {
            return false;
        }
        
        Joueur joueurActif = modele.getJoueurActif();
        return joueurActif != null && joueurActif.estHumain();
    }
    
    /**
     * Obtient et valide une action
     */
    private Action obtenirActionValide(String nomAction) {
        Action action = modele.getGestionnaireActions().obtenirAction(nomAction);
        
        if (action == null) {
            vue.afficherMessage("Action non disponible");
            return null;
        }
        
        return action;
    }
    
    /**
     * Gère la fin d'une action
     */
    private void gererFinAction(Action action) {
        Joueur joueur = modele.getJoueurActif();
        if (actionTermineTour(action, joueur)) {
            modele.passerAuJoueurSuivant();
        }
    }
    
    private boolean actionTermineTour(Action action, Joueur joueur) {
        return action.getNom().equals("Rester") || 
               action.getNom().equals("Doubler") ||
               (joueur.getMainActuelle() != null && joueur.getMainActuelle().estBuste());
    }
}