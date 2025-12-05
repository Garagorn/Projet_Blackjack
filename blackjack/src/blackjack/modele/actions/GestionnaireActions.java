package blackjack.modele.actions;

import blackjack.modele.jeu.ModeleBlackjack;
import blackjack.modele.joueurs.Croupier;
import blackjack.modele.joueurs.Joueur;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import modele.cartes.Paquet;

/**
 * La classe GestionnaireActions gère les actions disponibles pour les joueurs dans une partie de Blackjack.
 * Elle permet d'enregistrer, retirer, récupérer et exécuter des actions spécifiques comme "Tirer", "Rester", "Doubler", etc.
 * Elle est responsable de la gestion des actions de chaque joueur en fonction de l'état du jeu.
 */
public class GestionnaireActions {
    
    private Map<String, Action> actionsDisponibles;  // Map des actions disponibles
    private Paquet pioche;  // Paquet de cartes à tirer
    private Croupier croupier;  // Croupier du jeu
    
    /**
     * Constructeur qui initialise le gestionnaire d'actions avec les actions disponibles pour le jeu de Blackjack.
     * 
     * @param modele l'objet ModeleBlackjack qui contient l'état du jeu, y compris le paquet et le croupier.
     */
    public GestionnaireActions(ModeleBlackjack modele) {
        this.pioche = modele.getPioche();
        this.croupier = modele.getCroupier();
        this.actionsDisponibles = new HashMap<>();
        initialiserActions();
    }
    
    /**
     * Méthode qui initialise les actions de base disponibles dans le jeu de Blackjack.
     */
    private void initialiserActions() {
        enregistrerAction(new ActionTirer(pioche));     // Action de tirer une carte
        enregistrerAction(new ActionRester(pioche));    // Action de rester
        enregistrerAction(new ActionDoubler(pioche));   // Action de doubler la mise
        enregistrerAction(new ActionSplit(pioche));     // Action de séparer la main
        enregistrerAction(new ActionAssurance(pioche, croupier));  // Action d'assurance
    }
    
    /**
     * Enregistre une nouvelle action dans le gestionnaire.
     * 
     * @param action l'action à enregistrer.
     */
    public void enregistrerAction(Action action) {
        if (action != null) {
            actionsDisponibles.put(action.getNom(), action);
        }
    }
    
    /**
     * Retire une action existante du gestionnaire en fonction de son nom.
     * 
     * @param nomAction le nom de l'action à retirer.
     * @return l'action retirée ou null si l'action n'existe pas.
     */
    public Action retirerAction(String nomAction) {
        return actionsDisponibles.remove(nomAction);
    }
    
    /**
     * Obtient une action par son nom.
     * 
     * @param nomAction le nom de l'action à récupérer.
     * @return l'action associée au nom ou null si l'action n'existe pas.
     */
    public Action obtenirAction(String nomAction) {
        return actionsDisponibles.get(nomAction);
    }
    
    /**
     * Obtient toutes les actions disponibles.
     * 
     * @return une liste contenant toutes les actions disponibles.
     */
    public ArrayList<Action> obtenirToutesLesActions() {
        return new ArrayList<>(actionsDisponibles.values());
    }
    
    /**
     * Obtient les actions exécutables pour un joueur donné.
     * 
     * @param joueur le joueur pour lequel récupérer les actions exécutables.
     * @return une liste d'actions que le joueur peut exécuter.
     */
    public ArrayList<Action> obtenirActionsExecutables(Joueur joueur) {
        ArrayList<Action> actionsExecutables = new ArrayList<>();
        
        if (joueur == null) {
            return actionsExecutables;
        }
        for (Action action : actionsDisponibles.values()) {
            if (action.peutExecuter(joueur)) {
                actionsExecutables.add(action);
            }
        }
        
        return actionsExecutables;
    }
    
    /**
     * Exécute une action pour un joueur donné.
     * 
     * @param nomAction le nom de l'action à exécuter.
     * @param joueur le joueur qui exécute l'action.
     */
    public void executerAction(String nomAction, Joueur joueur) {
        Action action = obtenirAction(nomAction);
        if (action != null && joueur != null) {
            action.executer(joueur);
        }
    }
    
    /**
     * Obtient une liste des noms des actions disponibles pour affichage.
     * 
     * @return une liste des noms d'actions.
     */
    public List<String> obtenirNomsActions() {
        return new ArrayList<>(actionsDisponibles.keySet());
    }
    
    /**
     * Vérifie si une action existe dans le gestionnaire en fonction de son nom.
     * 
     * @param nomAction le nom de l'action à vérifier.
     * @return true si l'action existe, sinon false.
     */
    public boolean actionExiste(String nomAction) {
        return actionsDisponibles.containsKey(nomAction);
    }
    
    /**
     * Retourne le nombre d'actions disponibles dans le gestionnaire.
     * 
     * @return le nombre d'actions disponibles.
     */
    public int getNombreActions() {
        return actionsDisponibles.size();
    }
    
    /**
     * Réinitialise le gestionnaire d'actions en réinitialisant toutes les actions disponibles.
     */
    public void reinitialiser() {
        actionsDisponibles.clear();
        initialiserActions();
    }
    
    /**
     * Retourne une représentation sous forme de chaîne de caractères du gestionnaire d'actions,
     * incluant le nombre d'actions et leurs noms.
     * 
     * @return une chaîne représentant l'état du gestionnaire d'actions.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("GestionnaireActions [");
        sb.append(actionsDisponibles.size()).append(" actions: ");
        sb.append(String.join(", ", actionsDisponibles.keySet()));
        sb.append("]");
        return sb.toString();
    }
}
