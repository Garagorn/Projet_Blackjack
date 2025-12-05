package blackjack.vue.composantscontrol;

import blackjack.controleur.ControleurBlackjack;
import java.awt.*;
import javax.swing.*;

/**
 * Panneau permettant de démarrer une nouvelle partie dans le jeu de Blackjack.
 * Ce panneau contient un bouton "Nouvelle Partie" qui, lorsqu'il est cliqué,
 * déclenche la création d'une nouvelle partie via le contrôleur.
 */
public class NouvellePartiePanel extends JPanel {
    
    /**
     * boutton pour lancer une nouvelle partie
     */
    private final JButton btnNouvellePartie;
    /**
     * référence sur le controleur pour rénitialiser la partie
     */
    private ControleurBlackjack controleur;

    /**
     * Constructeur de NouvellePartiePanel.
     * Ce constructeur initialise le bouton "Nouvelle Partie", définit son apparence
     * (couleur de fond, police, etc.), et lie l'action du bouton à l'exécution
     * de la méthode `actionNouvellePartie` du contrôleur.
     */
    public NouvellePartiePanel() {
        super(new FlowLayout(FlowLayout.CENTER));
        
        // Initialiser le bouton avec son texte et sa mise en forme
        btnNouvellePartie = new JButton("Nouvelle Partie");
        btnNouvellePartie.setFont(new Font("Arial", Font.BOLD, 14));
        btnNouvellePartie.setBackground(new Color(34, 139, 34)); // Vert
         
        
        // Lier l'action du bouton à l'action du contrôleur
        btnNouvellePartie.addActionListener(e -> {
            if (controleur != null) {
                controleur.actionNouvellePartie(); // Appeler le contrôleur pour démarrer une nouvelle partie
            }
        });
        
        // Ajouter le bouton au panneau
        this.add(btnNouvellePartie);
    }

    /**
     * Associe un contrôleur à ce panneau.
     * Le contrôleur est utilisé pour gérer l'action de démarrer une nouvelle partie.
     *
     * @param controleur Le contrôleur qui gère le démarrage de la nouvelle partie.
     */
    public void setControleur(ControleurBlackjack controleur) {
        this.controleur = controleur;
    }
}
