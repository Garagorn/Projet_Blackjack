package blackjack.modele.actions;

import blackjack.modele.jeu.ModeleBlackjack;
import blackjack.modele.joueurs.Joueur;
import blackjack.modele.joueurs.Main;

/**
 * Action permettant au joueur de rester avec sa main actuelle (Stand).
 * Le joueur ne tire plus de cartes et passe au joueur suivant.
 */
public class ActionRester extends Action {
    
    public ActionRester(ModeleBlackjack modele) {
        super(modele);
    }
    
    @Override
    public void executer(Joueur joueur) {
        if (peutExecuter(joueur)) {
        }
       
    }
    
    @Override
    public boolean peutExecuter(Joueur joueur) {
        if (joueur == null) {
            return false;
        }
        
        Main mainActuelle = joueur.getMainActuelle();
        
        if (mainActuelle == null) {
            return false;
        }
        
        // Peut toujours rester tant que la main n'est pas brûlée
        return !mainActuelle.estBuste();
    }
    
    @Override
    public String getNom() {
        return "Rester";
    }
    
    @Override
    public String getDescription() {
        return "Garder sa main actuelle sans tirer";
    }
}