package blackjack.modele.strategie;

import blackjack.modele.actions.Action;
import blackjack.modele.joueurs.Main;
import modele.cartes.Carte;
import java.util.List;

/**
 * Interface définissant le contrat pour les stratégies de jeu au Blackjack.
 * Implémente le Pattern Strategy pour permettre différents comportements d'IA.
 * 
 * Cette interface permet à un joueur IA de choisir une action (tirer, rester, doubler, etc.) 
 * en fonction de sa main, de la carte visible du croupier, et des actions disponibles.
 */
public interface StrategieJeu {
    
    /**
     * Choisit l'action à exécuter pour une main donnée.
     * Cette méthode est utilisée par un joueur IA pour déterminer quelle action prendre 
     * en fonction de sa main actuelle, de la carte visible du croupier et des actions disponibles.
     * 
     * @param main la main actuelle du joueur. Elle représente l'ensemble des cartes que possède le joueur.
     * @param carteVisibleCroupier la carte visible du croupier. Cette carte est révélée pendant le jeu.
     * @param actionsDisponibles la liste des actions possibles que le joueur peut choisir (tirer, rester, doubler, etc.).
     * @return l'action choisie par la stratégie. L'action peut être par exemple "tirer", "rester", "doubler", etc.
     */
    Action choisirAction(Main main, Carte carteVisibleCroupier, List<Action> actionsDisponibles);
    
    /**
     * Retourne le nom de la stratégie. 
     * Chaque implémentation de la stratégie doit définir un nom unique pour identifier le comportement de jeu.
     * 
     * @return le nom de la stratégie, sous forme de chaîne de caractères.
     */
    String getNom();
    
    /**
     * Retourne une description de la stratégie.
     * 
     * Par défaut, la description renvoie simplement le nom de la stratégie. 
     * Toutefois, une implémentation personnalisée peut retourner une description plus détaillée si nécessaire.
     * 
     * @return une description de la stratégie, souvent le nom ou un résumé du comportement.
     */
    default String getDescription() {
        return getNom();
    }
}
