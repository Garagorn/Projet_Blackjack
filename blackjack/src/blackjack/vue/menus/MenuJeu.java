package blackjack.vue.menus;

import blackjack.vue.AppWindows;
import javax.swing.*;

/**
 * Menu "Jeu" dans la barre de menu de l'application Blackjack.
 * Ce menu offre plusieurs options liées à la gestion de la partie,
 * telles que la création d'une nouvelle partie, la réinitialisation du jeu, et la fermeture de l'application.
 */
public class MenuJeu extends JMenu {
    
    /**
     * Constructeur du menu "Jeu".
     * Ce menu contient trois options principales : "Nouvelle Partie", "Réinitialiser" et "Quitter".
     * Chaque option est associée à une action spécifique pour gérer la partie.
     *
     * @param appWindows la fenêtre principale de l'application, utilisée pour appeler les méthodes du contrôleur et du modèle.
     */
    public MenuJeu(AppWindows appWindows) {
        super("Jeu");
        
        // Item pour démarrer une nouvelle partie
        JMenuItem itemNouvellePartie = new JMenuItem("Nouvelle Partie");
        itemNouvellePartie.setAccelerator(KeyStroke.getKeyStroke("F2"));
        itemNouvellePartie.addActionListener(e -> appWindows.getControleur().actionNouvellePartie());
        
        // Item pour réinitialiser la partie en cours
        JMenuItem itemReinitialiser = new JMenuItem("Réinitialiser");
        itemReinitialiser.addActionListener(e -> {
            int choix = JOptionPane.showConfirmDialog(appWindows,
                "Voulez-vous vraiment réinitialiser le jeu ?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);
            if (choix == JOptionPane.YES_OPTION) {
                appWindows.getModele().resetPartie();
            }
        });
        
        // Item pour quitter l'application
        JMenuItem itemQuitter = new JMenuItem("Quitter");
        itemQuitter.setAccelerator(KeyStroke.getKeyStroke("alt Q"));
        itemQuitter.addActionListener(e -> System.exit(0));
        
        // Ajouter les éléments au menu
        this.add(itemNouvellePartie);
        this.add(itemReinitialiser);
        this.addSeparator();
        this.add(itemQuitter);
    }
}
