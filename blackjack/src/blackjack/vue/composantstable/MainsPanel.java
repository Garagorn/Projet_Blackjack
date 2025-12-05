package blackjack.vue.composantstable;
import java.awt.*;
import javax.swing.*;

/**
 * Panneau représentant l'ensemble des mains de cartes d'un joueur ou du croupier.
 * Ce panneau est utilisé pour contenir plusieurs `MainPanel` et afficher les mains 
 * dans une disposition en ligne (FlowLayout).
 */
public class MainsPanel extends JPanel {
    
    /**
     * Constructeur de `MainsPanel`. Initialise le panneau avec une disposition de type `FlowLayout`
     * et une couleur de fond verte pour rappeler l'environnement de jeu de Blackjack.
     */
    public MainsPanel() {
        super(new FlowLayout(FlowLayout.CENTER, 16, 8));
        
        // Définit la couleur de fond du panneau en vert foncé (table de Blackjack)
        super.setBackground(new Color(0, 100, 0));  // Couleur de fond vert foncé
    }
}
