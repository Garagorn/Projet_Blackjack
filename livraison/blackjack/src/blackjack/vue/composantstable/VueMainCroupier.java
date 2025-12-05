package blackjack.vue.composantstable;

import modele.cartes.Paquet;
import modele.cartes.Carte;
import vue.VuePaquet;
import javax.swing.*;
import java.awt.*;

/**
 * Vue représentant la main du croupier dans le jeu de Blackjack.
 * Cette classe est responsable de l'affichage des cartes du croupier,
 * en tenant compte de la règle selon laquelle la deuxième carte du croupier
 * ne peut être révélée que sous certaines conditions.
 */
public class VueMainCroupier extends VuePaquet {
    
    /**
     * Indique si la deuxième carte du croupier doit être révélée
     */
    private boolean revelerDeuxiemeCarte; 
    
    /**
     * Constructeur de la vue de la main du croupier.
     * Initialise la vue avec le paquet de cartes du croupier et l'indicateur
     * pour révéler la deuxième carte.
     * 
     * @param mainCroupier Le paquet de cartes représentant la main du croupier.
     * @param revelerDeuxiemeCarte Booléen indiquant si la deuxième carte doit être révélée.
     */
    public VueMainCroupier(Paquet mainCroupier, boolean revelerDeuxiemeCarte){
        super(mainCroupier);
        this.revelerDeuxiemeCarte = revelerDeuxiemeCarte;
        this.setBackground(new Color(0, 100, 0)); // Vert pour la couleur de fond (comme une table de Blackjack)
        afficherPaquet(); // Afficher les cartes de la main du croupier
    }

    /**
     * Affiche les cartes de la main du croupier.
     * Si la deuxième carte doit être révélée, elle est affichée.
     * Sinon, la deuxième carte reste face cachée.
     * 
     * Cette méthode met également à jour l'affichage de la vue.
     */
    @Override
    public void afficherPaquet() {
        this.removeAll(); // Retirer les cartes actuellement affichées
        
        // Vérifier si le paquet est vide
        if (getPaquet() == null || getPaquet().getPaquet().isEmpty()) {
            JLabel lblVide = new JLabel("Main vide", SwingConstants.CENTER);
            lblVide.setForeground(Color.WHITE);
            this.add(lblVide); // Afficher un message si la main est vide
        } else {
            this.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5)); // Disposition des cartes (alignées au centre)
            
            // Récupérer toutes les cartes du paquet
            java.util.List<Carte> cartes = getPaquet().getPaquet();
            for (int i = 0; i < cartes.size(); i++) {
                Carte carte = cartes.get(i);
                boolean visible = (i == 0) || revelerDeuxiemeCarte;  // La première carte est toujours visible, ou si le flag `revelerDeuxiemeCarte` est activé
                
                // Créer un composant pour afficher la carte
                VueCarteBlackjack panelCarte = new VueCarteBlackjack(carte, visible);
                this.add(panelCarte); // Ajouter la carte à l'affichage
            }
        }
        
        // Revalider et repeindre l'affichage pour qu'il prenne en compte les modifications
        this.revalidate();
        this.repaint();
    }

    /**
     * Met à jour l'affichage en fonction de l'état de la révélation de la deuxième carte.
     * Cette méthode est utilisée lorsque l'on veut révéler ou cacher la deuxième carte du croupier.
     * 
     * @param reveler Booléen qui indique si la deuxième carte doit être révélée ou non.
     */
    public void setRevelerDeuxiemeCarte(boolean reveler){
        this.revelerDeuxiemeCarte = reveler;
        afficherPaquet(); // Réafficher la main avec la mise à jour de la visibilité de la deuxième carte
    }

    /**
     * Méthode utilisée pour mettre à jour la vue lorsque l'état du modèle change.
     * Elle appelle `afficherPaquet()` pour mettre à jour l'affichage des cartes du croupier.
     * 
     * @param o Objet qui a été modifié, dans ce cas la main du croupier.
     */
    public void modeleMiseAJour(Object o) {
        afficherPaquet(); // Mettre à jour l'affichage après la modification du modèle
    }
}
