package blackjack.vue.composantstable;

import modele.cartes.Paquet;
import vue.VuePaquet;
import javax.swing.*;
import java.awt.*;

/**
 * Vue représentant la pioche (paquet de cartes) dans le jeu de Blackjack.
 * Cette vue affiche le dos des cartes de la pioche, avec un effet de pile si la pioche contient plusieurs cartes.
 * Si la pioche est vide, un message indiquant "Pioche vide" est affiché.
 */
public class VuePaquetCache extends VuePaquet {

    private static final int MAX_EPAISSEUR = 10; // L'épaisseur maximale de la pile de cartes affichées
    private static final int LARGEUR_CARTE = 90; // Largeur d'une carte
    private static final int HAUTEUR_CARTE = 130; // Hauteur d'une carte

    /**
     * Constructeur de la vue de la pioche.
     * Initialise la vue avec le paquet de cartes représentant la pioche et définit la couleur de fond verte,
     * représentant la table de Blackjack.
     * 
     * @param pioche Le paquet représentant la pioche du jeu.
     */
    public VuePaquetCache(Paquet pioche) {
        super(pioche);
        this.setBackground(new Color(0, 100, 0)); // Vert de la table
        this.setPreferredSize(new Dimension(LARGEUR_CARTE + 20, HAUTEUR_CARTE + 40)); // Taille de la vue
    }

    /**
     * Méthode pour afficher le paquet (pioche). Dans ce cas, elle ne fait rien car les cartes sont dessinées
     * manuellement dans la méthode `paintComponent`.
     */
    @Override
    public void afficherPaquet() {
        removeAll();
        revalidate();
        repaint();
    }

    /**
     * Méthode pour dessiner le paquet sur le composant graphique.
     * Elle dessine soit une pile de cartes avec des effets de superposition, soit un message indiquant que la pioche est vide.
     *
     * @param g L'objet `Graphics` utilisé pour dessiner les éléments.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int nbCartes = getPaquet().nbr_carte(); // Nombre de cartes dans la pioche
        if (nbCartes == 0) {
            dessinerPiocheVide(g2); // Si la pioche est vide, dessiner un message
        } else {
            dessinerPile(g2, nbCartes); // Sinon, dessiner la pile de cartes
        }
    }

    /**
     * Méthode pour dessiner l'état de la pioche lorsqu'elle est vide.
     * Affiche un message indiquant que la pioche est vide.
     *
     * @param g2 L'objet `Graphics2D` utilisé pour dessiner.
     */
    private void dessinerPiocheVide(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 14));
        g2.drawString("Pioche vide", 10, 50);
    }

    /**
     * Méthode pour dessiner la pile de cartes dans la pioche.
     * Elle affiche les cartes en empilant des rectangles (pour représenter les cartes) avec des décalages progressifs,
     * et affiche également le nombre de cartes dans la pioche.
     *
     * @param g2 L'objet `Graphics2D` utilisé pour dessiner.
     * @param nbCartes Le nombre de cartes actuellement dans la pioche.
     */
    private void dessinerPile(Graphics2D g2, int nbCartes) {
        int epaisseur = Math.min(MAX_EPAISSEUR, nbCartes); // Limiter l'épaisseur à un maximum de cartes visibles

        // Coordonnées de base pour la première carte
        int x = 10;
        int y = 10;

        // Dessiner un certain nombre de cartes en fonction de l'épaisseur de la pile
        for (int i = 0; i < epaisseur; i += 2) {
            // Décalage progressif pour créer un effet de pile
            int decalageX = i;
            int decalageY = i;

            // Dessiner le rectangle représentant le dos de la carte
            g2.setColor(Color.WHITE);
            g2.fillRect(x + decalageX, y + decalageY, LARGEUR_CARTE, HAUTEUR_CARTE);
            g2.setColor(Color.RED); // Couleur du dos de la carte (rouge)
            g2.fillRect(x + decalageX + 2, y + decalageY + 2, LARGEUR_CARTE - 4, HAUTEUR_CARTE - 4);

            // Dessiner la bordure de la carte
            g2.setColor(Color.WHITE);
            g2.drawRect(x + decalageX, y + decalageY, LARGEUR_CARTE, HAUTEUR_CARTE);
        }

        // Afficher le nombre de cartes dans la pioche
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 12));
        g2.drawString(nbCartes + " cartes", x, y + HAUTEUR_CARTE + 20);
    }

    /**
     * Méthode appelée pour mettre à jour l'affichage du paquet de cartes.
     * Elle appelle `afficherPaquet()` pour redessiner le paquet après une mise à jour.
     *
     * @param o L'objet modifié, dans ce cas la pioche.
     */
    @Override
    public void modeleMiseAJour(Object o) {
        afficherPaquet();
    }
}
