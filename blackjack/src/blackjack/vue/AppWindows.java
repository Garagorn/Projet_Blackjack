package blackjack.vue;

import blackjack.controleur.ControleurBlackjack;
import blackjack.modele.jeu.ModeleBlackjack;
import blackjack.modele.joueurs.JoueurHumain;
import blackjack.modele.joueurs.JoueurIA;
import blackjack.modele.strategie.StrategieBasique;
import blackjack.modele.strategie.StrategieAgressive;
import javax.swing.*;
import java.awt.*;

/**
 * Fenêtre principale de l'application Blackjack.
 * Contient la vue et initialise le modèle MVC.
 */
public class AppWindows extends JFrame {
    
    private ModeleBlackjack modele;
    private VueBlackjack vue;
    private ControleurBlackjack controleur;
    
    /**
     * Constructeur de la fenêtre principale
     */
    public AppWindows() {
        super("Blackjack - Jeu de Cartes");
        initialiser();
    }
    
    /**
     * Initialise l'application MVC
     */
    private void initialiser() {
        // Créer le modèle
        modele = new ModeleBlackjack(10); // Mise minimale = 10
        
        // Ajouter des joueurs par défaut
        JoueurHumain joueurHumain = new JoueurHumain("Vous", 1000);
        JoueurIA ia1 = new JoueurIA("Bot Alice", 1000, new StrategieBasique());
        JoueurIA ia2 = new JoueurIA("Bot Bob", 1000, new StrategieAgressive());
        
        modele.ajouterJoueur(joueurHumain);
        modele.ajouterJoueur(ia1);
        modele.ajouterJoueur(ia2);
        
        // Créer la vue
        vue = new VueBlackjack(modele);
         
        
        // Créer le contrôleur
        controleur = new ControleurBlackjack(modele, vue);
        vue.setControleur(controleur);
        
        // Configuration de la fenêtre
        configurerFenetre();
    }
    
    /**
     * Configure la fenêtre principale
     */
    private void configurerFenetre() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Ajouter la vue
        add(vue, BorderLayout.CENTER);
        
        // Créer la barre de menu
        setJMenuBar(creerBarreMenu());
        
        // Taille et centrage
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1000, 700));
    }
    
    /**
     * Crée la barre de menu
     */
    private JMenuBar creerBarreMenu() {
        JMenuBar menuBar = new JMenuBar();
        
        // Menu Jeu
        JMenu menuJeu = new JMenu("Jeu");
        
        JMenuItem itemNouvellePartie = new JMenuItem("Nouvelle Partie");
        itemNouvellePartie.setAccelerator(KeyStroke.getKeyStroke("F2"));
        itemNouvellePartie.addActionListener(e -> controleur.actionNouvellePartie());
        
        JMenuItem itemReinitialiser = new JMenuItem("Réinitialiser");
        itemReinitialiser.addActionListener(e -> {
            int choix = JOptionPane.showConfirmDialog(this,
                "Voulez-vous vraiment réinitialiser le jeu ?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);
            if (choix == JOptionPane.YES_OPTION) {
                modele.resetPartie();
            }
        });
        
        JMenuItem itemQuitter = new JMenuItem("Quitter");
        itemQuitter.setAccelerator(KeyStroke.getKeyStroke("alt Q"));
        itemQuitter.addActionListener(e -> System.exit(0));
        
        menuJeu.add(itemNouvellePartie);
        menuJeu.add(itemReinitialiser);
        menuJeu.addSeparator();
        menuJeu.add(itemQuitter);
        
        // Menu Joueurs
        JMenu menuJoueurs = new JMenu("Joueurs");
        
        JMenuItem itemAjouterIA = new JMenuItem("Ajouter IA");
        itemAjouterIA.addActionListener(e -> ajouterJoueurIA());
        
        JMenuItem itemRetirerJoueur = new JMenuItem("Retirer Joueur");
        itemRetirerJoueur.addActionListener(e -> retirerJoueur());
        
        menuJoueurs.add(itemAjouterIA);
        menuJoueurs.add(itemRetirerJoueur);
        
        // Menu Aide
        JMenu menuAide = new JMenu("Aide");
        
        JMenuItem itemRegles = new JMenuItem("Règles du Blackjack");
        itemRegles.addActionListener(e -> afficherRegles());
        
        JMenuItem itemAPropos = new JMenuItem("À propos");
        itemAPropos.addActionListener(e -> afficherAPropos());
        
        menuAide.add(itemRegles);
        menuAide.addSeparator();
        menuAide.add(itemAPropos);
        
        menuBar.add(menuJeu);
        menuBar.add(menuJoueurs);
        menuBar.add(menuAide);
        
        return menuBar;
    }
    
    /**
     * Ajoute un joueur IA via une boîte de dialogue
     */
    private void ajouterJoueurIA() {
        String nom = JOptionPane.showInputDialog(this,
            "Nom du joueur IA :",
            "Ajouter IA",
            JOptionPane.QUESTION_MESSAGE);
        
        if (nom != null && !nom.trim().isEmpty()) {
            JoueurIA nouvelleIA = new JoueurIA(nom, 1000, new StrategieBasique());
            modele.ajouterJoueur(nouvelleIA);
            JOptionPane.showMessageDialog(this,
                "Joueur IA " + nom + " ajouté avec succès !");
        }
    }
    
    /**
     * Retire un joueur via une boîte de dialogue
     */
    private void retirerJoueur() {
        if (modele.getJoueurs().size() <= 1) {
            JOptionPane.showMessageDialog(this,
                "Il faut au moins un joueur dans la partie !",
                "Erreur",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String[] nomsJoueurs = modele.getJoueurs().stream()
            .map(j -> j.getNom())
            .toArray(String[]::new);
        
        String nomChoisi = (String) JOptionPane.showInputDialog(this,
            "Sélectionnez le joueur à retirer :",
            "Retirer Joueur",
            JOptionPane.QUESTION_MESSAGE,
            null,
            nomsJoueurs,
            nomsJoueurs[0]);
        
        if (nomChoisi != null) {
            modele.getJoueurs().stream()
                .filter(j -> j.getNom().equals(nomChoisi))
                .findFirst()
                .ifPresent(j -> {
                    modele.retirerJoueur(j);
                    JOptionPane.showMessageDialog(this,
                        "Joueur " + nomChoisi + " retiré !");
                });
        }
    }
    
    /**
     * Affiche les règles du Blackjack
     */
    private void afficherRegles() {
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

        JTextArea textArea = new JTextArea(regles);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));

        JOptionPane.showMessageDialog(this, scrollPane, "Règles du Blackjack",
            JOptionPane.INFORMATION_MESSAGE);
    }

    
    /**
     * Affiche les informations "À propos"
     */
    private void afficherAPropos() {
        JOptionPane.showMessageDialog(this,
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