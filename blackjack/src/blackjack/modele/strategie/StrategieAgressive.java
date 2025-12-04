package blackjack.modele.strategie;

import blackjack.modele.actions.Action;
import blackjack.modele.joueurs.Main;
import modele.cartes.Carte;
import java.util.List;

/**
 * Stratégie agressive : prend plus de risques pour maximiser les gains.
 * 
 * Règles :
 * - Tire jusqu'à 18 (au lieu de 17)
 * - Double/split plus souvent
 * - Prend l'assurance si disponible
 */
public class StrategieAgressive implements StrategieJeu {
    
    private static final int SCORE_OBJECTIF = 18;
    
    @Override
    public Action choisirAction(Main main, Carte carteVisibleCroupier, List<Action> actionsDisponibles) {
        if (main == null || actionsDisponibles == null || actionsDisponibles.isEmpty()) {
            return null;
        }
        
        int score = main.getScore();
        int valeurCroupier = obtenirValeurCarte(carteVisibleCroupier);
        
        // Prendre l'assurance si disponible et le croupier a un As
        Action assurance = trouverAction("Assurance", actionsDisponibles);
        if (assurance != null) {
            return assurance;
        }
        
        // Split agressif : séparer les 8, 9, 10 et As
        Action split = trouverAction("Séparer", actionsDisponibles);
        if (split != null && main.getNombreCartes() == 2) {
            String hauteur = main.getPaquetMain().getPaquet().get(0).hauteur;
            if (hauteur.equals("As") || hauteur.equals("8") || 
                hauteur.equals("9") || hauteur.equals("10")) {
                return split;
            }
        }
        
        // Doubler agressif : sur 9, 10, 11
        Action doubler = trouverAction("Doubler", actionsDisponibles);
        if (doubler != null && (score == 9 || score == 10 || score == 11)) {
            // Double surtout si le croupier est faible
            if (valeurCroupier <= 6) {
                return doubler;
            }
            // Double sur 10 ou 11 même si le croupier est moyen
            if ((score == 10 || score == 11) && valeurCroupier <= 9) {
                return doubler;
            }
        }
        
        // Tire agressivement jusqu'à 18
        if (score < SCORE_OBJECTIF) {
            // Tire toujours si score < 12
            if (score < 12) {
                return trouverAction("Tirer", actionsDisponibles);
            }
            
            // Entre 12 et 17 : tire si le croupier a une carte moyenne ou forte
            if (valeurCroupier >= 4) {
                return trouverAction("Tirer", actionsDisponibles);
            }
            
            // Tire quand même entre 12-16 (agressif)
            if (score < 17) {
                return trouverAction("Tirer", actionsDisponibles);
            }
        }
        
        // Reste si score >= 18
        return trouverAction("Rester", actionsDisponibles);
    }
    
    /**
     * Obtient la valeur d'une carte pour la décision
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
     * Trouve une action par son nom dans la liste
     */
    private Action trouverAction(String nom, List<Action> actions) {
        for (Action action : actions) {
            if (action.getNom().equals(nom)) {
                return action;
            }
        }
        return null;
    }
    
    @Override
    public String getNom() {
        return "Stratégie Agressive";
    }
    
    @Override
    public String getDescription() {
        return "Prend plus de risques, tire jusqu'à 18, double/split souvent";
    }
}