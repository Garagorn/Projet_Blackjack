package blackjack.modele.strategie;

import blackjack.modele.actions.Action;
import blackjack.modele.joueurs.Main;
import modele.cartes.Carte;
import java.util.List;

/**
 * Stratégie agressive : prend plus de risques pour maximiser les gains.
 * 
 * Règles de la stratégie agressive :
 * - Tire jusqu'à 18 (au lieu de 17 dans d'autres stratégies)
 * - Double et split plus souvent (pour maximiser les gains potentiels)
 * - Prend l'assurance si elle est disponible
 */
public class StrategieAgressive implements StrategieJeu {
    
    /**
     *Constructeur vide
     *
     */
    public StrategieAgressive(){
        
    }
    /**
     * Le score cible du joueur dans cette stratégie.
     * L'objectif est de tirer jusqu'à ce que le score atteigne 18.
     */
    private static final int SCORE_OBJECTIF = 18;
    
    /**
     * Choisit l'action à exécuter en fonction de la main du joueur et de la carte visible du croupier.
     * 
     * La stratégie agressive prend plus de risques que la stratégie standard, avec un objectif
     * de maximiser les gains. Elle permet de tirer jusqu'à 18, double et split plus souvent, 
     * et prend l'assurance si disponible.
     * 
     * Les règles incluent :
     * - Si le score est inférieur à 18, le joueur prend un risque en tirant.
     * - Le joueur double plus souvent, en particulier sur des mains avec un score de 9, 10 ou 11.
     * - Le split est plus fréquent, en particulier avec les As, 8, 9, et 10.
     * - L'assurance est prise si elle est disponible.
     * 
     * @param main la main actuelle du joueur
     * @param carteVisibleCroupier la carte visible du croupier
     * @param actionsDisponibles les actions possibles que le joueur peut effectuer (tirer, doubler, rester, etc.)
     * @return l'action choisie par la stratégie (par exemple, tirer, rester, doubler, etc.)
     */
    @Override
    public Action choisirAction(Main main, Carte carteVisibleCroupier, List<Action> actionsDisponibles) {
        if (main == null || actionsDisponibles == null || actionsDisponibles.isEmpty()) {
            return null;
        }
        
        int score = main.getScore();
        int valeurCroupier = obtenirValeurCarte(carteVisibleCroupier);
        
        // Si l'assurance est disponible, elle est prise.
        Action assurance = trouverAction("Assurance", actionsDisponibles);
        if (assurance != null) {
            return assurance;
        }

        // Split : plus fréquent, surtout pour les As, 8, 9 et 10.
        Action split = trouverAction("Séparer", actionsDisponibles);
        if (split != null && main.getNombreCartes() == 2) {
            String hauteur = main.getPaquetMain().getPaquet().get(0).hauteur;
            if (hauteur.equals("As") || hauteur.equals("8") || 
                hauteur.equals("9") || hauteur.equals("10")) {
                return split;
            }
        }
        
        // Doubler : doubler sur 9, 10, ou 11, particulièrement contre un croupier faible.
        Action doubler = trouverAction("Doubler", actionsDisponibles);
        if (doubler != null && (score == 9 || score == 10 || score == 11)) {
            if (valeurCroupier <= 6) {
                return doubler;
            }
            if ((score == 10 || score == 11) && valeurCroupier <= 9) {
                return doubler;
            }
        }
        
        // Si le score est inférieur à 18, continuer à tirer.
        if (score < SCORE_OBJECTIF) {
            if (score < 12) {
                return trouverAction("Tirer", actionsDisponibles);
            }
           
            if (valeurCroupier >= 4) {
                return trouverAction("Tirer", actionsDisponibles);
            }
            
            if (score < 17) {
                return trouverAction("Tirer", actionsDisponibles);
            }
        }
        
        // Si aucune condition particulière n'est remplie, rester.
        return trouverAction("Rester", actionsDisponibles);
    }
    
    /**
     * Obtient la valeur d'une carte en fonction de sa hauteur.
     * 
     * Cette méthode retourne la valeur numérique de la carte :
     * - L'As vaut 11.
     * - Les cartes de figures (Valet, Dame, Roi) valent 10.
     * - Les cartes numérotées ont leur valeur respective.
     * 
     * @param carte la carte dont on veut obtenir la valeur
     * @return la valeur de la carte
     */
    private int obtenirValeurCarte(Carte carte) {
        if (carte == null) return 0;
        
        switch (carte.hauteur) {
            case "As":
                return 11;
            case "Valet":
            case "Dame":
            case "Roi":
                return 10;
            default:
                try {
                    return Integer.parseInt(carte.hauteur);
                } catch (NumberFormatException e) {
                    return 0;
                }
        }
    }
    
    /**
     * Trouve une action par son nom dans la liste d'actions disponibles.
     * 
     * Cette méthode parcourt la liste des actions disponibles et retourne celle correspondant
     * au nom donné.
     * 
     * @param nom le nom de l'action à rechercher
     * @param actions la liste des actions possibles
     * @return l'action correspondant au nom, ou null si l'action n'est pas trouvée
     */
    private Action trouverAction(String nom, List<Action> actions) {
        for (Action action : actions) {
            if (action.getNom().equals(nom)) {
                return action;
            }
        }
        return null;
    }
    
    /**
     * Retourne le nom de la stratégie agressive.
     * 
     * @return le nom de la stratégie : "Stratégie Agressive"
     */
    public String getNom() {
        return "Stratégie Agressive";
    }
    
    /**
     * Retourne une description de la stratégie agressive.
     * 
     * Cette stratégie privilégie les risques en tirant jusqu'à 18, double/split fréquemment
     * et prenant l'assurance quand cela est possible.
     * 
     * @return une brève description de la stratégie
     */
    @Override
    public String getDescription() {
        return "Prend plus de risques, tire jusqu'à 18, double/split souvent";
    }
}
