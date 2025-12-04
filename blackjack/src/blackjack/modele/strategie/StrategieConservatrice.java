package blackjack.modele.strategie;

import blackjack.modele.actions.Action;
import blackjack.modele.joueurs.Main;
import modele.cartes.Carte;
import java.util.List;

/**
 * Stratégie conservatrice : minimise les risques.
 * 
 * Règles :
 * - Ne tire jamais si le score est >= 15
 * - Ne double/split que rarement (uniquement dans les meilleures situations)
 * - Préfère rester plutôt que prendre des risques
 */
public class StrategieConservatrice implements StrategieJeu {
    
    private static final int SCORE_SECURITE = 15;
    
    @Override
    public Action choisirAction(Main main, Carte carteVisibleCroupier, List<Action> actionsDisponibles) {
        if (main.estVide()|| actionsDisponibles == null || actionsDisponibles.isEmpty()) {
            return null;
        }
        
        int score = main.getScore();
        int valeurCroupier = obtenirValeurCarte(carteVisibleCroupier);
        
        // Split uniquement les As
        Action split = trouverAction("Séparer", actionsDisponibles);
        if (split != null && main.getNombreCartes() == 2) {
            if (main.getPaquetMain().getPaquet().get(0).hauteur.equals("As")) {
                return split;
            }
        }
        
        // Doubler uniquement sur 11 contre une faible carte du croupier
        Action doubler = trouverAction("Doubler", actionsDisponibles);
        if (doubler != null && score == 11 && valeurCroupier <= 6) {
            return doubler;
        }
        
        // Stratégie conservatrice : reste dès 15
        if (score >= SCORE_SECURITE) {
            return trouverAction("Rester", actionsDisponibles);
        }
        
        // Tire si le score est bas
        if (score < 12) {
            return trouverAction("Tirer", actionsDisponibles);
        }
        
        // Entre 12 et 14 : tire seulement si le croupier a une forte carte (7+)
        if (valeurCroupier >= 7) {
            return trouverAction("Tirer", actionsDisponibles);
        }
        
        // Sinon, reste
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
        return "Stratégie Conservatrice";
    }
    
    @Override
    public String getDescription() {
        return "Minimise les risques, reste dès 15, double/split rarement";
    }
}