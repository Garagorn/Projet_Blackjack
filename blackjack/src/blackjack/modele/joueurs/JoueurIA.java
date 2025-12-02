package blackjack.modele.joueurs;

import blackjack.modele.actions.Action;
import blackjack.modele.strategie.StrategieJeu;
import modele.cartes.Carte;
import java.util.List;

/**
 * Représente un joueur contrôlé par l'intelligence artificielle.
 */
public class JoueurIA extends Joueur {
    private StrategieJeu strategie;
    
    public JoueurIA(String nom, int jetonsInitiaux, StrategieJeu strategie) {
        super(nom, jetonsInitiaux);
        this.strategie = strategie;
        
        if (strategie == null) {
            throw new IllegalArgumentException("Un joueur IA doit avoir une stratégie");
        }
    }
    
    public Action jouerTour(Carte carteVisibleCroupier, List<Action> actionsDisponibles) {
        if (strategie == null) {
            throw new IllegalStateException("Aucune stratégie définie pour ce joueur IA");
        }
        
        Main mainActuelle = getMainActuelle();
        return strategie.choisirAction(mainActuelle, carteVisibleCroupier, actionsDisponibles);
    }
    
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
    public StrategieJeu getStrategie(){
        return this.strategie;
    }
    
    public void changerStrategie(StrategieJeu nouvelleStrategie) {
        this.strategie = nouvelleStrategie;
    }
    
    @Override
    public boolean estHumain() {
        return false;
    }
    
    @Override
    public String toString() {
        String strategieNom = strategie != null ? strategie.getNom() : "Aucune";
        return "[IA - " + strategieNom + "] " + super.toString();
    }
}