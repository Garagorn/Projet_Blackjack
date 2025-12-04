package blackjack.vue.composantstable;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.*;

/**
 * Panneau personnalisé pour contenir les éléments visuels des joueurs.
 * Ce panneau utilise un `FlowLayout` pour disposer les éléments de manière centrée avec un espacement entre eux.
 * Il a un fond de couleur verte pour simuler l'aspect d'une table de Blackjack.
 */
public class ConteneurJoueursPanel extends JPanel {

    /**
     * Constructeur de `ConteneurJoueursPanel`.
     * Ce constructeur initialise le panneau avec un `FlowLayout` pour organiser les éléments enfants de manière fluide et centrée.
     * Le fond du panneau est défini à une couleur verte pour imiter l'apparence d'une table de Blackjack.
     */
    public ConteneurJoueursPanel() {
        super(new FlowLayout(FlowLayout.CENTER, 20, 10));
        super.setBackground(new Color(0, 100, 0));  // Couleur verte
    }
}
