package blackjack.modele.strategie;

import blackjack.modele.actions.Action;
import blackjack.modele.joueurs.Main;
import modele.cartes.Carte;
import java.util.List;

/**
 * Stratégie basique du Blackjack : basée sur les probabilités mathématiques.
 * C'est la stratégie optimale qui minimise l'avantage de la maison.
 * 
 * Suit les règles standard de la "Basic Strategy" utilisée par les joueurs pros.
 */
public class StrategieBasique implements StrategieJeu {
    
    @Override
    public Action choisirAction(Main main, Carte carteVisibleCroupier, List<Action> actionsDisponibles) {
        if (main == null || actionsDisponibles == null || actionsDisponibles.isEmpty()) {
            return null;
        }
        
        int score = main.getScore();
        int valeurCroupier = obtenirValeurCarte(carteVisibleCroupier);
        boolean aUnAs = main.getPaquetMain().getPaquet().stream()
                .anyMatch(c -> c.hauteur.equals("As") && score <= 21);
        
        // SPLIT
        Action split = trouverAction("Séparer", actionsDisponibles);
        if (split != null && main.getNombreCartes() == 2) {
            String hauteur = main.getPaquetMain().getPaquet().get(0).hauteur;
            
            // Toujours split les As et les 8
            if (hauteur.equals("As") || hauteur.equals("8")) {
                return split;
            }
            
            // Split les 9 sauf contre 7, 10, As du croupier
            if (hauteur.equals("9") && valeurCroupier != 7 && valeurCroupier < 10) {
                return split;
            }
            
            // Split les 2, 3, 6, 7 contre croupier faible (2-6)
            if ((hauteur.equals("2") || hauteur.equals("3") || 
                 hauteur.equals("6") || hauteur.equals("7")) && 
                valeurCroupier >= 2 && valeurCroupier <= 6) {
                return split;
            }
        }
        
        // DOUBLER
        Action doubler = trouverAction("Doubler", actionsDisponibles);
        if (doubler != null) {
            // Double sur 11 toujours (sauf si croupier a As)
            if (score == 11 && valeurCroupier < 11) {
                return doubler;
            }
            
            // Double sur 10 contre croupier 2-9
            if (score == 10 && valeurCroupier <= 9) {
                return doubler;
            }
            
            // Double sur 9 contre croupier 3-6
            if (score == 9 && valeurCroupier >= 3 && valeurCroupier <= 6) {
                return doubler;
            }
            
            // Main souple (avec As) : double A-2 à A-7 contre croupier 5-6
            if (aUnAs && score >= 13 && score <= 18 && 
                valeurCroupier >= 5 && valeurCroupier <= 6) {
                return doubler;
            }
        }
        
        // MAIN SOUPLE (avec As)
        if (aUnAs && score <= 18) {
            // A-8 ou A-9 : toujours rester
            if (score >= 19) {
                return trouverAction("Rester", actionsDisponibles);
            }
            
            // A-7 : rester contre 2, 7, 8 ; tirer contre 9, 10, As
            if (score == 18) {
                if (valeurCroupier >= 9) {
                    return trouverAction("Tirer", actionsDisponibles);
                }
                return trouverAction("Rester", actionsDisponibles);
            }
            
            // A-2 à A-6 : toujours tirer
            return trouverAction("Tirer", actionsDisponibles);
        }
        
        // MAIN DURE (sans As ou avec As compté comme 1)
        if (score >= 17) {
            // 17+ : toujours rester
            return trouverAction("Rester", actionsDisponibles);
        }
        
        if (score >= 13 && score <= 16) {
            // 13-16 : rester si croupier a 2-6, sinon tirer
            if (valeurCroupier >= 2 && valeurCroupier <= 6) {
                return trouverAction("Rester", actionsDisponibles);
            }
            return trouverAction("Tirer", actionsDisponibles);
        }
        
        if (score == 12) {
            // 12 : rester contre 4-6, sinon tirer
            if (valeurCroupier >= 4 && valeurCroupier <= 6) {
                return trouverAction("Rester", actionsDisponibles);
            }
            return trouverAction("Tirer", actionsDisponibles);
        }
        
        // Score <= 11 : toujours tirer (impossible de brûler)
        return trouverAction("Tirer", actionsDisponibles);
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
        return "Stratégie Basique";
    }
    
    @Override
    public String getDescription() {
        return "Stratégie optimale basée sur les probabilités mathématiques";
    }
}