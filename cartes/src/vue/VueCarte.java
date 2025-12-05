package vue;

import javax.swing.*;
import java.awt.*;
import modele.cartes.Carte;

/**
 * VueCarte représente graphiquement une carte à jouer.
 * Elle peut afficher la face visible de la carte (valeur + symbole),
 * ou bien son dos si elle est censée être cachée.
 *
 * Cette classe étend {@link JPanel} et redéfinit la méthode
 * {@code paintComponent} pour dessiner la carte.
 */
public class VueCarte extends JPanel {
    
    /**
     * La carte associée à cette vue.
     */
    protected Carte carte;

    /**
     * Indique si la carte est affichée face visible (true) ou dos caché (false).
     */
    protected boolean visible;

    /**
     * Construit une vue graphique pour une carte.
     *
     * @param carte   La carte à représenter (peut être null si face cachée).
     * @param visible Indique si la carte doit être visible (true) ou affichée face cachée (false).
     */
    public VueCarte(Carte carte, boolean visible) {
        this.carte = carte;
        this.visible = visible;
        this.setPreferredSize(new Dimension(90, 130));
        this.setBackground(Color.WHITE);
    }

    /**
     * Renvoie la carte associée à cette vue.
     *
     * @return La carte modélisée par cette vue.
     */
    public Carte getCarte() {
        return this.carte;
    }

    /**
     * Dessine la carte à l'écran.
     * Si {@code visible} est vrai, affiche la hauteur et le symbole de la carte.
     * Sinon, affiche un dos de carte stylisé.
     *
     * @param g Le contexte graphique utilisé pour le dessin.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Dessiner le cadre de la carte
        g2.setColor(Color.BLACK);
        g2.drawRect(5, 5, getWidth() - 10, getHeight() - 10);

        if (visible) {
            // Afficher la hauteur + symbole
            g2.setFont(new Font("Arial", Font.BOLD, 14));
            g2.drawString(carte.hauteur, 10, 25);
            g2.drawString(carte.getSymboleCouleur(), 10, 45);
        } else {
            // Afficher dos de carte
            g2.setColor(Color.RED);
            g2.fillRect(10, 10, getWidth() - 20, getHeight() - 20);
        }
    }
}
