package blackjack.vue;
import modele.cartes.Paquet;
import modele.cartes.Carte;
import vue.VuePaquet;
import javax.swing.*;
import java.awt.*;

public class VueMainJoueur extends VuePaquet {
    
    public VueMainJoueur(Paquet mainJoueur){
        super(mainJoueur);
        this.setBackground(new Color(0, 100, 0));
        afficherPaquet();
    }

    @Override
    public void afficherPaquet() {
        this.removeAll();
        
        if (getPaquet() == null || getPaquet().getPaquet().isEmpty()) {
            JLabel lblVide = new JLabel("Main vide", SwingConstants.CENTER);
            lblVide.setForeground(Color.WHITE);
            this.add(lblVide);
        } else {
            // Utiliser un layout horizontal pour les cartes
            this.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
            
            for (Carte carte : getPaquet().getPaquet()) {
                // Créer une représentation visuelle de la carte
                VueCarteBlackjack panelCarte = new VueCarteBlackjack(carte, true);
                this.add(panelCarte);
            }
        }
        
        this.revalidate();
        this.repaint();
    }

    

    public void modeleMiseAJour(Object o) {
        afficherPaquet();
    }
}