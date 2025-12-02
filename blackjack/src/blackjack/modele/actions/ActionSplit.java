package blackjack.modele.actions;

import blackjack.modele.jeu.ModeleBlackjack;
import blackjack.modele.joueurs.Joueur;
import blackjack.modele.joueurs.Main;
import modele.cartes.Carte;

/**
 * Action permettant au joueur de séparer sa main en deux (Split).
 * Possible uniquement avec deux cartes de même valeur.
 * Crée deux mains indépendantes avec la même mise chacune.
 */
public class ActionSplit extends Action {
    
    public ActionSplit(ModeleBlackjack modele) {
        super(modele);
    }
    
    @Override
    public void  executer(Joueur joueur) {
        if (peutExecuter(joueur)) {
             
            Main mainActuelle = joueur.getMainActuelle();
            Main nouvelleMain = mainActuelle.split();

            joueur.ajouterMain(nouvelleMain);
            joueur.debiterJetons(nouvelleMain.getMise());

            // Distribuer une carte à chaque main
            Carte carte1 = modele.getPioche().getPaquet().get(0);
            Carte carte2 = modele.getPioche().getPaquet().get(1);

            if (carte1 != null) {
                mainActuelle.ajouterCarte(carte1);
                modele.getPioche().retirer(carte1);
            }

            if (carte2 != null) {
                nouvelleMain.ajouterCarte(carte2);
                modele.getPioche().retirer(carte2);
            }
             
        }
    }
    
    @Override
    public boolean peutExecuter(Joueur joueur) {
        if (joueur == null) {
            return false;
        }
        
        Main mainActuelle = joueur.getMainActuelle();
        
        if (mainActuelle.estVide() || !mainActuelle.peutSplit() || modele.getPioche().estVide()) {
            return false;
        }
        
        // Vérifier si la main peut être splittée
        // Vérifier si le joueur a assez de jetons pour la nouvelle mise
        int miseActuelle = mainActuelle.getMise();
        if (joueur.getJetons() < miseActuelle) {
            return false;
        }
        
        
        return true;
    }
    
    @Override
    public String getNom() {
        return "Séparer";
    }
    
    @Override
    public String getDescription() {
        return "Séparer la main en deux mains indépendantes";
    }
}