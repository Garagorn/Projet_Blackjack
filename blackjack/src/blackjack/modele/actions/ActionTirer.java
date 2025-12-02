package blackjack.modele.actions;

import blackjack.modele.jeu.ModeleBlackjack;
import blackjack.modele.joueurs.Joueur;
import blackjack.modele.joueurs.Main;
import modele.cartes.Carte;

/**
 * Action permettant au joueur de tirer une carte supplémentaire (Hit).
 */
public class ActionTirer extends Action {
    
    public ActionTirer(ModeleBlackjack modele) {
        super(modele);
    }
    
    @Override
    public void executer(Joueur joueur) {
        if (peutExecuter(joueur)) {
            Main mainActuelle = joueur.getMainActuelle();

            // Tirer une carte du paquet
            Carte carte = modele.getPioche().getPaquet().get(0);

            if (carte != null) {
                mainActuelle.ajouterCarte(carte);
                modele.getPioche().retirer(carte);
            } 
        }
    }
    
    @Override
    public boolean peutExecuter(Joueur joueur) {
        if (joueur == null) {
            return false;
        }
        
        Main mainActuelle = joueur.getMainActuelle();
        if(mainActuelle.estVide() || mainActuelle.estBuste() || mainActuelle.estDoublee() || modele.getPioche().estVide()){
            return false;
        }
        return true;
    }
    
    @Override
    public String getNom() {
        return "Tirer";
    }
    
    @Override
    public String getDescription() {
        return "Tirer une carte supplémentaire";
    }
}