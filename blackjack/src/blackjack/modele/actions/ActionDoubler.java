package blackjack.modele.actions;

import blackjack.modele.jeu.ModeleBlackjack;
import blackjack.modele.joueurs.Joueur;
import blackjack.modele.joueurs.Main;
import modele.cartes.Carte;

/**
 * Action permettant au joueur de doubler sa mise (Double Down).
 * Le joueur double sa mise, reçoit exactement UNE carte supplémentaire,
 * puis passe automatiquement au joueur suivant.
 */
public class ActionDoubler extends Action {
    
    public ActionDoubler(ModeleBlackjack modele) {
        super(modele);
    }
    
    @Override
    public void executer(Joueur joueur) {
        if (peutExecuter(joueur)) {
            Main mainActuelle = joueur.getMainActuelle();

            mainActuelle.doubler();

            joueur.debiterJetons(mainActuelle.getMise() / 2); 
            
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
        
        // Vérifier si la main permet le doublement (2 cartes, score 9-11
 
        if (mainActuelle.estVide() || !mainActuelle.peutDoubler()) {
            return false;
        }
        
        
        // Vérifier si le joueur a assez de jetons pour doubler
        int miseActuelle = mainActuelle.getMise();
        if (joueur.getJetons() < miseActuelle) {
            return false;
        }
        
        // Vérifier si le paquet n'est pas vide
        if (modele.getPioche().estVide()) {
            return false;
        }
        
        return true;
    }
    
    @Override
    public String getNom() {
        return "Doubler";
    }
    
    @Override
    public String getDescription() {
        return "Doubler la mise et recevoir une seule carte";
    }
}