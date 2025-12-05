package blackjack.modele.strategie;

import blackjack.modele.actions.Action;
import blackjack.modele.joueurs.Main;
import modele.cartes.Carte;
import java.util.List;

/**
 * Stratégie conservatrice : minimise les risques.
 * 
 * Cette stratégie préfère limiter les risques en prenant moins de décisions risquées.
 * Les règles principales de la stratégie sont les suivantes :
 * - Ne tire jamais si le score du joueur est supérieur ou égal à 15.
 * - Ne double ou split que rarement, uniquement dans des situations favorables.
 * - Privilégie de rester plutôt que de prendre des risques.
 */
public class StrategieConservatrice implements StrategieJeu {
    
    // Seuil de sécurité pour la stratégie : rester dès que le score est supérieur ou égal à ce seuil
    private static final int SCORE_SECURITE = 15;
    
    /**
     *Constructeur vide
     *
     */
    public StrategieConservatrice(){
        
    }
    /**
     * Choisit l'action à exécuter en fonction de la main du joueur et de la carte visible du croupier.
     * 
     * Les décisions sont basées sur la main actuelle du joueur et la carte visible du croupier :
     * - Si le joueur a un score >= 15, il choisit de rester.
     * - Si le score est 11 et que la carte du croupier est faible (inf à 6), il double.
     * - Si le score est faible (inf à12), il tire.
     * - Si le score est entre 12 et 14, il tire seulement si la carte visible du croupier est forte (7 ou plus).
     * 
     * @param main la main actuelle du joueur
     * @param carteVisibleCroupier la carte visible du croupier
     * @param actionsDisponibles les actions possibles que le joueur peut prendre
     * @return l'action choisie par la stratégie (par exemple, tirer, rester, doubler, etc.)
     */
    @Override
    public Action choisirAction(Main main, Carte carteVisibleCroupier, List<Action> actionsDisponibles) {
        if (main.estVide() || actionsDisponibles == null || actionsDisponibles.isEmpty()) {
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
        
        // Stratégie conservatrice : rester dès 15
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
     * Obtient la valeur d'une carte pour la prise de décision dans la stratégie.
     * 
     * Cette méthode permet de calculer la valeur d'une carte selon les règles du Blackjack :
     * - L'As a une valeur de 11
     * - Les cartes de figures (Valet, Dame, Roi) ont une valeur de 10
     * - Les cartes numérotées ont leur valeur respective.
     * 
     * @param carte la carte dont on veut obtenir la valeur
     * @return la valeur numérique de la carte
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
     * Cette méthode parcourt la liste des actions possibles et renvoie l'action correspondante
     * au nom spécifié.
     * 
     * @param nom le nom de l'action que l'on recherche
     * @param actions la liste d'actions disponibles
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
     * Retourne le nom de cette stratégie.
     * 
     * @return le nom de la stratégie : "Stratégie Conservatrice"
     */
    @Override
    public String getNom() {
        return "Stratégie Conservatrice";
    }
    
    /**
     * Retourne une description de la stratégie conservatrice.
     * 
     * La description met en avant les principes de cette stratégie qui minimise les risques
     * et évite les prises de décision trop risquées.
     * 
     * @return une brève description de la stratégie
     */
    @Override
    public String getDescription() {
        return "Minimise les risques, reste dès 15, double/split rarement";
    }
}
