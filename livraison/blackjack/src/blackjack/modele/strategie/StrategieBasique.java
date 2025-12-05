package blackjack.modele.strategie;

import blackjack.modele.actions.Action;
import blackjack.modele.joueurs.Main;
import modele.cartes.Carte;
import java.util.List;

/**
 * Stratégie basique du Blackjack : basée sur les probabilités mathématiques.
 * 
 * C'est la stratégie optimale qui minimise l'avantage de la maison en prenant les décisions les plus favorables selon les règles de base du Blackjack.
 * 
 * Cette stratégie suit les règles standards de la "Basic Strategy", souvent utilisée par les joueurs professionnels pour maximiser leurs chances de gagner.
 */
public class StrategieBasique implements StrategieJeu {
    
    
    
     /**
     *Constructeur vide
     *
     */
    public StrategieBasique(){
        
    }
    /**
     * Choisit l'action à exécuter en fonction de la main du joueur et de la carte visible du croupier.
     * 
     * La décision du joueur est basée sur les règles de la stratégie basique qui dépendent du score de la main du joueur, de la carte visible du croupier, 
     * ainsi que des actions disponibles. La stratégie couvre toutes les situations possibles, comme le split, le double, tirer, ou rester.
     * 
     * - **Split** : Split les cartes spécifiques comme les As et les 8, ou 9 contre des cartes faibles du croupier.
     * - **Doubler** : Doubler sur 11, 10, 9 ou sur des mains souples avec un As.
     * - **Main souple (avec As)** : Reste ou tire en fonction du score et de la carte visible du croupier.
     * - **Main dure (sans As)** : Reste ou tire en fonction du score, généralement reste à 17 ou plus, sinon tire entre 12 et 16 contre un croupier faible.
     * - **Tirer** : Toujours tirer sur des mains faibles (score inf à 11) ou dans certaines situations spécifiques (par exemple, score entre 12 et 16 contre un croupier fort).
     * 
     * @param main la main actuelle du joueur
     * @param carteVisibleCroupier la carte visible du croupier
     * @param actionsDisponibles les actions possibles que le joueur peut prendre (tirer, doubler, rester, etc.)
     * @return l'action choisie par la stratégie (par exemple, tirer, rester, doubler, etc.)
     */
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
     * Obtient la valeur d'une carte pour la prise de décision.
     * 
     * Cette méthode retourne la valeur numérique de la carte en suivant les règles du Blackjack :
     * - L'As vaut 11.
     * - Les cartes de figures (Valet, Dame, Roi) valent 10.
     * - Les cartes numérotées ont leur valeur respective.
     * 
     * @param carte la carte à évaluer
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
     * Parcourt la liste des actions disponibles et retourne l'action qui correspond au nom fourni.
     * 
     * @param nom le nom de l'action à rechercher
     * @param actions la liste des actions disponibles
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
     * Retourne le nom de la stratégie basique.
     * 
     * @return le nom de la stratégie : "Stratégie Basique"
     */
    @Override
    public String getNom() {
        return "Stratégie Basique";
    }
    
    /**
     * Retourne une description de la stratégie basique.
     * 
     * La stratégie suit les probabilités et les règles mathématiques pour offrir la meilleure chance de victoire.
     * 
     * @return une brève description de la stratégie
     */
    @Override
    public String getDescription() {
        return "Stratégie optimale basée sur les probabilités mathématiques";
    }
}
