package blackjack.vue;

import modele.cartes.Paquet;
import vue.VuePaquet;
import javax.swing.*;
import java.awt.*;

public class VuePioche extends VuePaquet {

    private static final int MAX_EPAISSEUR = 10; // Épaisseur maximale de la pile en pixels
    private static final int LARGEUR_CARTE = 90;
    private static final int HAUTEUR_CARTE = 130;

    public VuePioche(Paquet pioche) {
        super(pioche);
        this.setBackground(new Color(0, 100, 0)); // Vert de la table
        this.setPreferredSize(new Dimension(LARGEUR_CARTE + 20, HAUTEUR_CARTE + 40));
    }

    @Override
    public void afficherPaquet() {
        removeAll();
        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int nbCartes = getPaquet().nbr_carte();
        if (nbCartes == 0) {
            dessinerPiocheVide(g2);
        } else {
            dessinerPile(g2, nbCartes);
        }
    }

    private void dessinerPiocheVide(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 14));
        g2.drawString("Pioche vide", 10, 50);
    }

    private void dessinerPile(Graphics2D g2, int nbCartes) {
        int epaisseur = Math.min(MAX_EPAISSEUR, nbCartes);

        // Coordonnées de base pour la première carte
        int x = 10;
        int y = 10;

        // Dessiner le dos des cartes en empilant avec un décalage
        for (int i = 0; i < epaisseur; i= i + 2) {
            // Décalage progressif pour l'effet de pile
            int decalageX = i;
            int decalageY = i;

            // Dessiner un rectangle pour la carte
            g2.setColor(Color.WHITE);
            g2.fillRect(x + decalageX, y + decalageY, LARGEUR_CARTE, HAUTEUR_CARTE);
            g2.setColor(Color.RED); // Dos de carte bleu
            g2.fillRect(x + decalageX + 2, y + decalageY + 2, LARGEUR_CARTE - 4, HAUTEUR_CARTE - 4);

            // Bordure noire
            g2.setColor(Color.WHITE);
            g2.drawRect(x + decalageX, y + decalageY, LARGEUR_CARTE, HAUTEUR_CARTE);
        }

        // Dessiner le nombre de cartes
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 12));
        g2.drawString(nbCartes + " cartes", x, y + HAUTEUR_CARTE + 20);
    }

    @Override
    public void modeleMiseAJour(Object o) {
        afficherPaquet();
    }
}