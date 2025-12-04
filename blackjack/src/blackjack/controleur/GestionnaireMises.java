package blackjack.controleur;

import blackjack.modele.jeu.ModeleBlackjack;
import blackjack.modele.joueurs.Joueur;
import blackjack.modele.joueurs.JoueurHumain;
import blackjack.modele.joueurs.JoueurIA;
import blackjack.modele.util.EtatPartie;
import blackjack.vue.VueBlackjack;
import javax.swing.JOptionPane;

/**
 * Gestionnaire dédié aux mises
 */
public class GestionnaireMises {
    private ModeleBlackjack modele;
    private VueBlackjack vue;
    
    public GestionnaireMises(ModeleBlackjack modele, VueBlackjack vue) {
        this.modele = modele;
        this.vue = vue;
    }
   
    
    public boolean traiterMise(int mise) {
        try {
            if (!validerEtatJeu() || !validerMise(mise)) {
                return false;
            }
             
            placerMises(mise);
            modele.demarrerPartie();
            vue.afficherMessage("La partie commence !");
            return true;
            
        } catch (Exception e) {
            afficherErreur("Erreur : " + e.getMessage());
            return false;
        }
    }
    
    private boolean validerEtatJeu() {
        if (modele.getEtatPartie() != EtatPartie.EN_ATTENTE) {
            vue.afficherMessage("Attendez la fin de la partie actuelle");
            return false;
        }
        return true;
    }
    
   
    private boolean validerMise(int mise) {
        if (mise < modele.getMiseMinimale()) {
            afficherAvertissement("Mise invalide", 
                "La mise minimale est de " + modele.getMiseMinimale() + " €");
            return false;
        }
        
        JoueurHumain joueurHumain = modele.getJoueurHumain();
        if (joueurHumain != null && !joueurHumain.peutMiser(mise)) {
            afficherAvertissement("Mise invalide", "Jetons insuffisants pour cette mise");
            return false;
        }
        
        return true;
    }
    
    private void placerMises(int miseHumain) {
        for (Joueur joueur : modele.getJoueurs()) {
            if (joueur instanceof JoueurIA) {
                int miseIA = ((JoueurIA) joueur).determinerMise(modele.getMiseMinimale());
                joueur.demarrerNouvelleMain(miseIA);
                vue.afficherMessage(joueur.getNom() + " mise " + miseIA + " €");
            } else if (joueur instanceof JoueurHumain) {
                joueur.demarrerNouvelleMain(miseHumain);
                vue.afficherMessage("Vous misez " + miseHumain + " €");
            }
        }
    }
    


    
    
    private void afficherAvertissement(String titre, String message) {
        JOptionPane.showMessageDialog(null, message, titre, JOptionPane.WARNING_MESSAGE);
    }
    
    private void afficherErreur(String message) {
        JOptionPane.showMessageDialog(null, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }
    
     
    public boolean peutMiser() {
        return modele.getEtatPartie() == EtatPartie.EN_ATTENTE;
    }
    
    /**
     * Obtient la mise minimale
     */
    public int getMiseMinimale() {
        return modele.getMiseMinimale();
    }
}