package blackjack.vue.composantstable;
import modele.cartes.Paquet;
import modele.cartes.Carte;
import vue.VuePaquet;
import javax.swing.*;
import java.awt.*;

/**
 * Vue représentant la main d'un joueur dans le jeu de Blackjack.
 * Cette classe gère l'affichage des cartes du joueur dans une interface graphique,
 * en utilisant un layout horizontal pour disposer les cartes de manière fluide.
 */
public class VueMainJoueur extends VuePaquet {
    
    /**
     * Constructeur de la vue de la main du joueur.
     * Initialise la vue avec le paquet de cartes représentant la main du joueur.
     * 
     * @param mainJoueur Le paquet de cartes représentant la main du joueur.
     */
    public VueMainJoueur(Paquet mainJoueur){
        super(mainJoueur); // Appel du constructeur de la classe parente
        this.setBackground(new Color(0, 100, 0)); // Vert pour la couleur de fond (comme une table de Blackjack)
        afficherPaquet(); // Afficher les cartes de la main du joueur
    }

    /**
     * Affiche les cartes de la main du joueur.
     * Si le paquet est vide, un message "Main vide" est affiché.
     * Chaque carte de la main est représentée graphiquement dans un layout horizontal.
     */
    @Override
    public void afficherPaquet() {
        this.removeAll(); // Retirer les cartes actuellement affichées
        
        // Vérifier si le paquet de cartes est vide
        if (getPaquet() == null || getPaquet().getPaquet().isEmpty()) {
            JLabel lblVide = new JLabel("Main vide", SwingConstants.CENTER);
            lblVide.setForeground(Color.WHITE); // Texte en blanc
            this.add(lblVide); // Ajouter le message "Main vide" si le paquet est vide
        } else {
            this.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5)); // Disposition des cartes en ligne (horizontalement)
            
            // Parcourir chaque carte et l'ajouter à l'interface
            for (Carte carte : getPaquet().getPaquet()) {
                // Créer un composant visuel pour chaque carte
                VueCarteBlackjack panelCarte = new VueCarteBlackjack(carte, true);
                this.add(panelCarte); // Ajouter la carte à la vue
            }
        }
        
        // Revalider et repeindre l'affichage pour qu'il prenne en compte les modifications
        this.revalidate();
        this.repaint();
    }

    /**
     * Méthode appelée pour mettre à jour l'affichage lorsque l'état du modèle change.
     * Elle rafraîchit l'affichage de la main du joueur en appelant `afficherPaquet()`.
     * 
     * @param o Objet qui a été modifié. Dans ce cas, il s'agit du paquet de cartes du joueur.
     */
    public void modeleMiseAJour(Object o) {
        afficherPaquet(); // Mettre à jour l'affichage après la modification du modèle
    }
}
