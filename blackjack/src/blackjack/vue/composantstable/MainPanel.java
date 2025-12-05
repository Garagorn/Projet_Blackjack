package blackjack.vue.composantstable;

import java.awt.*;
import javax.swing.*;

/**
 * Panneau représentant une main de cartes dans le jeu de Blackjack.
 * Ce panneau est utilisé pour afficher visuellement les cartes d'une main du joueur ou du croupier.
 */
public class MainPanel extends JPanel {
    
    /**
     * Constructeur de `MainPanel`. Initialise le panneau avec une disposition de type `BorderLayout`
     * et une couleur de fond verte pour rappeler l'environnement de jeu de Blackjack.
     */
    public MainPanel() {
        super(new BorderLayout());
        
        // Définit la couleur de fond du panneau en vert (pour rappeler la table de Blackjack)
        super.setBackground(new Color(0, 100, 0));  // Couleur de fond vert foncé
    }
}
