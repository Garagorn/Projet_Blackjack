package blackjack.vue.menus;

import blackjack.vue.AppWindows;
import javax.swing.*;
import java.awt.*;

/**
 * Menu d'aide qui offre des options pour afficher les règles du jeu
 * et les informations à propos de l'application.
 * Ce menu est ajouté à la barre de menus principale de l'application.
 */
public class MenuAide extends JMenu {
    
    /**
     * Constructeur du menu Aide.
     * Ce menu contient deux options : "Règles du Blackjack" et "À propos".
     * Chaque option est associée à une action qui affiche un dialogue.
     *
     * @param appWindows la fenêtre principale de l'application (utilisée pour afficher les dialogues)
     */
    public MenuAide(AppWindows appWindows) {
        super("Aide");
        
        // Item pour afficher les règles du Blackjack
        JMenuItem itemRegles = new JMenuItem("Règles du Blackjack");
        itemRegles.addActionListener(e -> afficherRegles(appWindows));
        
        // Item pour afficher les informations à propos de l'application
        JMenuItem itemAPropos = new JMenuItem("À propos");
        itemAPropos.addActionListener(e -> afficherAPropos(appWindows));
        
        // Ajouter les éléments au menu
        this.add(itemRegles);
        this.addSeparator();
        this.add(itemAPropos);
    }
    
    /**
     * Affiche un dialogue avec les règles du Blackjack.
     * Les règles sont expliquées dans un format texte dans une zone défilante.
     *
     * @param appWindows la fenêtre principale de l'application, utilisée pour afficher le message.
     */
    private void afficherRegles(AppWindows appWindows) {
        String regles = 
            "RÈGLES DU BLACKJACK\n\n" +
            "Objectif : Battre le croupier en obtenant un score plus élevé sans dépasser 21.\n\n" +
            "Valeurs des cartes :\n" +
            "- As = 1 ou 11\n" +
            "- Figures (Valet, Dame, Roi) = 10\n" +
            "- Autres cartes = valeur nominale\n\n" +
            "Actions disponibles :\n" +
            "- Tirer : Prendre une carte supplémentaire\n" +
            "- Rester : Garder sa main actuelle\n" +
            "- Doubler : Doubler la mise et recevoir une seule carte (score 9-11)\n" +
            "- Séparer : Séparer deux cartes identiques en deux mains\n" +
            "- Assurance : Parier contre le blackjack du croupier (si As visible)\n\n" +
            "Le croupier tire jusqu'à 17, puis reste.\n\n" +
            "Gains :\n" +
            "- Blackjack : 3:2 (1.5x la mise)\n" +
            "- Victoire : 1:1 (mise doublée)\n" +
            "- Égalité : remboursement";

        // Créer une zone de texte non éditable pour afficher les règles
        JTextArea textArea = new JTextArea(regles);
        textArea.setEditable(false);
        
        // Envelopper la zone de texte dans un JScrollPane pour permettre le défilement
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));

        // Afficher un message dans un dialogue modal
        JOptionPane.showMessageDialog(appWindows, scrollPane, "Règles du Blackjack",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Affiche un dialogue à propos de l'application.
     * Ce message fournit des informations sur le projet et les fonctionnalités principales.
     *
     * @param appWindows la fenêtre principale de l'application, utilisée pour afficher le message.
     */
    private void afficherAPropos(AppWindows appWindows) {
        // Affichage des informations à propos de l'application
        JOptionPane.showMessageDialog(appWindows,
            "Blackjack - Jeu de Cartes\n\n" +
            "Version 1.0\n" +
            "Architecture MVC avec Patterns\n" +
            "- Strategy (IA)\n" +
            "- Command (Actions)\n" +
            "- Adapter (JTable)\n" +
            "- Observer (Vue/Modèle)\n\n" +
            "Projet Universitaire 2025",
            "À propos",
            JOptionPane.INFORMATION_MESSAGE);
    }
}
