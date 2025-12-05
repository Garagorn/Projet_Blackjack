package blackjack.modele.joueurs;

import blackjack.modele.actions.Action;
import blackjack.modele.strategie.StrategieJeu;
import modele.cartes.Carte;
import java.util.List;

/**
 * Représente un joueur contrôlé par l'intelligence artificielle (IA).
 * 
 * Ce joueur prend ses décisions en fonction d'une stratégie de jeu spécifiée
 * au moment de sa création. L'IA choisit les actions à entreprendre et détermine
 * la mise en fonction de ses jetons et des règles définies dans la stratégie.
 */
public class JoueurIA extends Joueur {
    
    private StrategieJeu strategie;  // La stratégie de jeu utilisée par l'IA
    
    /**
     * Constructeur d'un joueur IA.
     * 
     * Crée un joueur contrôlé par l'IA avec un nom, un nombre de jetons et une stratégie.
     * 
     * @param nom Le nom du joueur IA
     * @param jetonsInitiaux Le nombre de jetons du joueur IA
     * @param strategie La stratégie de jeu que l'IA utilisera pour prendre ses décisions
     * @throws IllegalArgumentException Si la stratégie est nulle
     */
    public JoueurIA(String nom, int jetonsInitiaux, StrategieJeu strategie) {
        super(nom, jetonsInitiaux);
        this.strategie = strategie;
        
        if (strategie == null) {
            throw new IllegalArgumentException("Un joueur IA doit avoir une stratégie");
        }
    }
    
    /**
     * Permet à l'IA de jouer son tour en choisissant une action en fonction de la stratégie.
     * 
     * L'IA choisit une action (comme "tirer", "rester", etc.) en fonction de la carte visible du croupier
     * et des actions disponibles. La stratégie de jeu détermine le choix final.
     * 
     * @param carteVisibleCroupier La carte visible du croupier
     * @param actionsDisponibles Liste des actions disponibles pour le joueur (ex: tirer, rester, doubler)
     * @return L'action choisie par l'IA
     * @throws IllegalStateException Si aucune stratégie n'a été définie
     */
    public Action jouerTour(Carte carteVisibleCroupier, List<Action> actionsDisponibles) {
        if (strategie == null) {
            throw new IllegalStateException("Aucune stratégie définie pour ce joueur IA");
        }
        
        Main mainActuelle = getMainActuelle();
        return strategie.choisirAction(mainActuelle, carteVisibleCroupier, actionsDisponibles);
    }
    
    /**
     * Détermine la mise que l'IA va placer pour la main en cours.
     * 
     * L'IA mise entre 5% et 10% de ses jetons disponibles, tout en respectant la mise minimale
     * et en s'assurant de ne pas miser plus que ce qu'elle possède.
     * 
     * @param miseMinimale La mise minimale imposée pour la main
     * @return Le montant de la mise
     */
    public int determinerMise(int miseMinimale) {
        // L'IA mise entre 5% et 10% de ses jetons
        int miseSuggeree = (int) (jetons * 0.05);
        
        // Respecter la mise minimale
        if (miseSuggeree < miseMinimale) {
            miseSuggeree = miseMinimale;
        }
        
        // Ne pas miser plus que ce qu'on a
        if (miseSuggeree > jetons) {
            miseSuggeree = jetons;
        }
        
        return miseSuggeree;
    }
    
    /**
     * Récupère la stratégie de jeu utilisée par l'IA.
     * 
     * @return La stratégie actuelle de l'IA
     */
    public StrategieJeu getStrategie(){
        return this.strategie;
    }
    
    /**
     * Change la stratégie de jeu de l'IA.
     * 
     * @param nouvelleStrategie La nouvelle stratégie à utiliser
     */
    public void changerStrategie(StrategieJeu nouvelleStrategie) {
        this.strategie = nouvelleStrategie;
    }
    
    /**
     * Indique si le joueur est humain ou non.
     * 
     * @return false, car ce joueur est contrôlé par l'IA
     */
    @Override
    public boolean estHumain() {
        return false;
    }
    
    /**
     * Renvoie une représentation sous forme de chaîne de caractères du joueur IA.
     * 
     * La chaîne retourne le type du joueur (IA) ainsi que le nom de la stratégie utilisée.
     * 
     * @return La chaîne représentant le joueur IA
     */
    @Override
    public String toString() {
        String strategieNom = strategie != null ? strategie.getNom() : "Aucune";
        return "[IA - " + strategieNom + "] " + super.toString();
    }
}
