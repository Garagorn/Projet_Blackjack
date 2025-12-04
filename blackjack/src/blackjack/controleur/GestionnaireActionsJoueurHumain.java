package blackjack.controleur;

import blackjack.modele.actions.Action;
import blackjack.modele.jeu.ModeleBlackjack;
import blackjack.modele.joueurs.Joueur;
import blackjack.modele.util.EtatPartie;
import blackjack.vue.VueBlackjack;

/**
 * Gestionnaire dédié aux actions du joueur humain dans le jeu de Blackjack.
 * Cette classe est responsable de la gestion des actions du joueur humain pendant son tour,
 * en vérifiant si l'action est valide et en mettant à jour l'état du jeu après l'exécution de l'action.
 */
public class GestionnaireActionsJoueurHumain {
    private ModeleBlackjack modele;  // L'objet modèle qui contient l'état actuel du jeu
    private VueBlackjack vue;        // L'interface graphique pour afficher les messages et l'état du jeu
    
    /**
     * Constructeur du gestionnaire des actions du joueur humain.
     * 
     * @param modele L'objet modèle qui contient l'état actuel du jeu.
     * @param vue L'interface graphique qui permet d'afficher l'état du jeu et les messages.
     */
    public GestionnaireActionsJoueurHumain(ModeleBlackjack modele, VueBlackjack vue) {
        this.modele = modele;
        this.vue = vue;
    }
    
    /**
     * Exécute l'action demandée par le joueur humain.
     * Vérifie si c'est le tour du joueur humain, si l'action est valide, et exécute l'action.
     * Après l'exécution de l'action, gère la fin du tour du joueur si nécessaire.
     * 
     * @param nomAction Le nom de l'action à exécuter (ex: "Tirer", "Rester", "Doubler").
     */
    public void executerAction(String nomAction) {
        if (!estTourJoueurHumain()) {
            return;  // Si ce n'est pas le tour du joueur humain, ne rien faire
        }
        
        Action action = obtenirActionValide(nomAction);
        if (action == null) {
            return;  // Si l'action n'est pas valide, ne rien faire
        }
        
        modele.executerAction(action);  // Exécuter l'action dans le modèle
        gererFinAction(action);  // Vérifier si l'action termine le tour du joueur
    }
    
    /**
     * Vérifie si c'est le tour du joueur humain.
     * 
     * @return true si c'est le tour du joueur humain, false sinon.
     */
    private boolean estTourJoueurHumain() {
        // Vérifie si l'état actuel de la partie permet de jouer, et si le joueur actif est humain
        if (modele.getEtatPartie() != EtatPartie.TOUR_JOUEURS) {
            return false;  // Ce n'est pas le tour des joueurs
        }
      
        Joueur joueurActif = modele.getJoueurActif();  // Récupère le joueur actif
        return joueurActif != null && joueurActif.estHumain();  // Vérifie si le joueur actif est humain
    }
    
    /**
     * Récupère l'action valide en fonction du nom de l'action.
     * 
     * @param nomAction Le nom de l'action demandée (ex: "Tirer").
     * @return L'action correspondante si elle est valide, sinon null.
     */
    private Action obtenirActionValide(String nomAction) {
        Action action = modele.getGestionnaireActions().obtenirAction(nomAction);  // Cherche l'action dans le gestionnaire
        
        if (action == null) {
            vue.afficherMessage("Action non disponible");  // Affiche un message d'erreur si l'action est invalide
            return null;
        }
        
        return action;  // Retourne l'action si elle est valide
    }
    
    /**
     * Gère la fin de l'action effectuée par le joueur.
     * Si l'action termine le tour du joueur (ex: "Rester", "Doubler", ou si le joueur est buste),
     * passe au joueur suivant.
     * 
     * @param action L'action qui vient d'être exécutée.
     */
    private void gererFinAction(Action action) {
        Joueur joueur = modele.getJoueurActif();  // Récupère le joueur actif
        if (actionTermineTour(action, joueur)) {
            modele.passerAuJoueurSuivant();  // Si l'action termine le tour, passe au joueur suivant
        }
    }
    
    /**
     * Vérifie si l'action effectuée termine le tour du joueur.
     * Cela se produit si l'action est "Rester", "Doubler" ou si le joueur est buste.
     * 
     * @param action L'action exécutée.
     * @param joueur Le joueur actif.
     * @return true si l'action termine le tour, sinon false.
     */
    private boolean actionTermineTour(Action action, Joueur joueur) {
        return action.getNom().equals("Rester") ||  // Si l'action est "Rester"
               action.getNom().equals("Doubler") || // Si l'action est "Doubler"
               (joueur.getMainActuelle() != null && joueur.getMainActuelle().estBuste());  // Ou si le joueur est buste
    }
}
