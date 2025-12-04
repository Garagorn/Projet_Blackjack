package blackjack.modele.actions;

import blackjack.modele.jeu.ModeleBlackjack;
import blackjack.modele.joueurs.Joueur;
import blackjack.modele.joueurs.Main;
import modele.cartes.Carte;

/**
 * Action permettant au joueur de prendre une assurance (Insurance).
 * Possible uniquement quand le croupier montre un As.
 * Le joueur parie la moitié de sa mise initiale que le croupier a un Blackjack.
 * Si le croupier a un Blackjack, l'assurance paie 2:1.
 */
public class ActionAssurance extends Action {
    
    public ActionAssurance(ModeleBlackjack modele) {
        super(modele);
    }
    
    @Override
    public void executer(Joueur joueur) {
        if (peutExecuter(joueur)) {
             Main mainActuelle = joueur.getMainActuelle();
   
            int coutAssurance = mainActuelle.getMise() / 2;

            joueur.debiterJetons(coutAssurance);

            mainActuelle.setAssure(true);
        
      
        }
    }
    
    @Override
    public boolean peutExecuter(Joueur joueur) {
        if (joueur == null) {
            return false;
        }
        
        Main mainActuelle = joueur.getMainActuelle();
        
        if (mainActuelle.getPaquetMain().estVide() || mainActuelle.estAssure() || (mainActuelle.getNombreCartes() != 2) ) {
            return false;
        }
        
        
        // Vérifier si le croupier montre un As
        Carte carteVisibleCroupier = modele.getCroupier().getCarteVisible();
        if (carteVisibleCroupier == null || !carteVisibleCroupier.hauteur.equals("As")) {
            return false;
        }
        
        // Vérifier si le joueur a assez de jetons pour l'assurance
        int coutAssurance = mainActuelle.getMise() / 2;
        if (joueur.getJetons() < coutAssurance) {
            return false;
        }
       
        return true;
    }
    
    @Override
    public String getNom() {
        return "Assurance";
    }
    
    @Override
    public String getDescription() {
        return "Prendre une assurance contre le Blackjack du croupier";
    }
}