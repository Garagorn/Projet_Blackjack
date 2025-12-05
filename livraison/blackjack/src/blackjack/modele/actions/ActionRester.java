package blackjack.modele.actions;

import blackjack.modele.joueurs.Joueur;
import blackjack.modele.joueurs.Main;
import modele.cartes.Paquet;

/**
 * Action permettant au joueur de rester avec sa main actuelle (Stand).
 * Le joueur décide de ne plus tirer de cartes et passe automatiquement au joueur suivant.
 * 
 * L'action "Rester" est toujours disponible tant que la main du joueur n'est pas "brûlée"
 * (c'est-à-dire que le score total du joueur ne dépasse pas 21).
 * 
 * @see Action
 */
public class ActionRester extends Action {
    
    /**
     * Constructeur de l'action "Rester".
     * Initialise l'action avec le paquet de cartes à utiliser.
     * 
     * @param pioche le paquet de cartes à utiliser dans le jeu
     */
    public ActionRester(Paquet pioche) {
        super(pioche);
    }
    
    /**
     * Exécute l'action "Rester". Cette méthode ne fait rien de spécifique
     * car l'action consiste simplement à ne pas tirer de cartes supplémentaires.
     * Le joueur passe au tour suivant.
     * 
     * @param joueur le joueur qui choisit de rester
     */
    @Override
    public void executer(Joueur joueur) {
        if (peutExecuter(joueur)) {
            // L'action "Rester" n'a pas d'effet spécifique, c'est une décision qui termine le tour du joueur
        }
    }
    
    /**
     * Vérifie si l'action "Rester" peut être exécutée pour le joueur.
     * Un joueur peut toujours "rester" tant que sa main n'est pas "brûlée"
     * (i.e. que son score est inférieur ou égal à 21).
     * 
     * @param joueur le joueur pour lequel vérifier si l'action est possible
     * @return true si l'action peut être exécutée (main non brûlée), sinon false
     */
    @Override
    public boolean peutExecuter(Joueur joueur) {
        if (joueur == null) {
            return false;
        }
        
        Main mainActuelle = joueur.getMainActuelle();
        
        if (mainActuelle == null) {
            return false;
        }
        
        // Peut toujours rester tant que la main du joueur n'est pas "brûlée" (busted)
        return !mainActuelle.estBuste();
    }
    
    /**
     * Retourne le nom de l'action, utilisé pour l'affichage dans l'interface utilisateur.
     * 
     * @return le nom de l'action : "Rester"
     */
    @Override
    public String getNom() {
        return "Rester";
    }
    
    /**
     * Retourne une description de l'action, utilisée pour fournir plus d'informations à l'utilisateur.
     * 
     * @return une description de l'action : "Garder sa main actuelle sans tirer"
     */
    @Override
    public String getDescription() {
        return "Garder sa main actuelle sans tirer";
    }
}
