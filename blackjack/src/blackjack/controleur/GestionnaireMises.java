package blackjack.controleur;

import blackjack.modele.jeu.ModeleBlackjack;
import blackjack.modele.joueurs.Joueur;
import blackjack.modele.joueurs.JoueurHumain;
import blackjack.modele.joueurs.JoueurIA;
import blackjack.modele.util.EtatPartie;
import blackjack.vue.VueBlackjack;
import javax.swing.JOptionPane;

/**
 * Gestionnaire dédié aux mises des joueurs dans une partie de Blackjack.
 * Cette classe gère la validation des mises, l'affichage des messages relatifs aux mises, 
 * ainsi que le placement des mises pour les joueurs humains et l'IA.
 */
public class GestionnaireMises {
    
    private ModeleBlackjack modele;  // L'objet modèle qui contient l'état actuel du jeu
    private VueBlackjack vue;        // L'interface graphique pour afficher les messages du jeu
    
    /**
     * Constructeur du gestionnaire de mises.
     * 
     * @param modele L'objet modèle qui contient l'état actuel du jeu.
     * @param vue L'interface graphique qui permet d'afficher l'état du jeu et les messages.
     */
    public GestionnaireMises(ModeleBlackjack modele, VueBlackjack vue) {
        this.modele = modele;
        this.vue = vue;
    }
    
    /**
     * Traite la mise d'un joueur et démarre la partie si la mise est valide.
     * 
     * @param mise La mise effectuée par le joueur.
     * @return true si la mise a été validée et la partie démarrée, false sinon.
     */
    public boolean traiterMise(int mise) {
        try {
            // Vérification de l'état du jeu et de la validité de la mise
            if (!validerEtatJeu() || !validerMise(mise)) {
                return false;
            }
             
            // Placement des mises et démarrage de la partie
            placerMises(mise);
            modele.demarrerPartie();
            vue.afficherMessage("La partie commence !");
            return true;
            
        } catch (Exception e) {
            afficherErreur("Erreur : ");
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Vérifie si l'état du jeu permet de placer une mise.
     * 
     * @return true si le jeu est dans l'état "EN_ATTENTE" et qu'une mise peut être effectuée, false sinon.
     */
    private boolean validerEtatJeu() {
        if (modele.getEtatPartie() != EtatPartie.EN_ATTENTE) {
            vue.afficherMessage("Attendez la fin de la partie actuelle");
            return false;
        }
        return true;
    }
    
    /**
     * Vérifie la validité de la mise effectuée par le joueur.
     * 
     * @param mise La mise effectuée par le joueur.
     * @return true si la mise est valide, false sinon.
     */
    private boolean validerMise(int mise) {
        // Vérification de la mise minimale
        if (mise < modele.getMiseMinimale()) {
            afficherAvertissement("Mise invalide", 
                "La mise minimale est de " + modele.getMiseMinimale() + " €");
            return false;
        }
        
        // Vérification de la capacité du joueur humain à miser
        JoueurHumain joueurHumain = modele.getJoueurHumain();
        if (joueurHumain != null && !joueurHumain.peutMiser(mise)) {
            afficherAvertissement("Mise invalide", "Jetons insuffisants pour cette mise");
            return false;
        }
        
        return true;
    }
    
    /**
     * Place les mises des joueurs humains et IA avant le début de la partie.
     * 
     * @param miseHumain La mise du joueur humain.
     */
    private void placerMises(int miseHumain) {
        for (Joueur joueur : modele.getJoueurs()) {
            if (joueur instanceof JoueurIA) {
                // L'IA détermine sa mise et l'affiche
                int miseIA = ((JoueurIA) joueur).determinerMise(modele.getMiseMinimale());
                joueur.demarrerNouvelleMain(miseIA);
                vue.afficherMessage(joueur.getNom() + " mise " + miseIA + " €");
            } else if (joueur instanceof JoueurHumain) {
                // Le joueur humain place sa mise
                joueur.demarrerNouvelleMain(miseHumain);
                vue.afficherMessage("Vous misez " + miseHumain + " €");
            }
        }
    }
    
    /**
     * Affiche une fenêtre de message d'avertissement avec le titre et le message donnés.
     * 
     * @param titre Le titre de l'avertissement.
     * @param message Le message de l'avertissement.
     */
    private void afficherAvertissement(String titre, String message) {
        JOptionPane.showMessageDialog(null, message, titre, JOptionPane.WARNING_MESSAGE);
    }
    
    /**
     * Affiche une fenêtre de message d'erreur.
     * 
     * @param message Le message d'erreur à afficher.
     */
    private void afficherErreur(String message) {
        JOptionPane.showMessageDialog(null, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Vérifie si le joueur peut placer une mise.
     * 
     * @return true si le joueur peut miser (le jeu est dans l'état "EN_ATTENTE"), false sinon.
     */
    public boolean peutMiser() {
        return modele.getEtatPartie() == EtatPartie.EN_ATTENTE;
    }
    
    /**
     * Retourne la mise minimale autorisée pour la partie.
     * 
     * @return La mise minimale autorisée.
     */
    public int getMiseMinimale() {
        return modele.getMiseMinimale();
    }
}
