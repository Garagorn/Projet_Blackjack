package blackjack.modele.strategie;

import blackjack.modele.actions.Action;
import blackjack.modele.joueurs.Main;
import modele.cartes.Carte;
import java.util.List;

/**
 * Interface définissant le contrat pour les stratégies de jeu au Blackjack.
 * Implémente le Pattern Strategy pour permettre différents comportements d'IA.
 * 
 * Chaque stratégie décide quelle action prendre en fonction de :
 * - La main du joueur
 * - La carte visible du croupier
 * - Les actions disponibles
 */
public interface StrategieJeu {
    
    /**
     * Choisit l'action à exécuter pour une main donnée.
     * 
     * @param main la main actuelle du joueur
     * @param carteVisibleCroupier la carte visible du croupier
     * @param actionsDisponibles la liste des actions possibles
     * @return l'action choisie par la stratégie
     */
    Action choisirAction(Main main, Carte carteVisibleCroupier, List<Action> actionsDisponibles);
    
    /**
     * Retourne le nom de la stratégie
     * @return le nom de la stratégie
     */
    String getNom();
    
    /**
     * Retourne une description de la stratégie
     * @return la description
     */
    default String getDescription() {
        return getNom();
    }
}