package blackjack.vue;

import blackjack.vue.VueBlackjack;
import blackjack.controleur.ControleurBlackjack;
import blackjack.modele.jeu.ModeleBlackjack;
import blackjack.modele.joueurs.Joueur;
import blackjack.modele.joueurs.JoueurHumain;
import blackjack.modele.joueurs.JoueurIA;
import blackjack.modele.strategie.StrategieBasique;
import blackjack.modele.strategie.StrategieAgressive;
import blackjack.vue.menus.*;
import javax.swing.*;
import java.awt.*;

/**
 * Fenêtre principale de l'application Blackjack. 
 * Cette classe est responsable de la création de la fenêtre principale de l'application et 
 * de l'initialisation des composants nécessaires au jeu, tels que le modèle, la vue et le contrôleur. 
 * Elle suit le pattern MVC (Modèle-Vue-Contrôleur) pour séparer la logique du jeu, l'affichage et les interactions de l'utilisateur.
 */
public class AppWindows extends JFrame {
    
    /** L'objet modèle contenant la logique du jeu de Blackjack */
    protected ModeleBlackjack modele;
    
    /** L'objet vue permettant d'afficher l'état actuel du jeu */
    protected VueBlackjack vue;
    
    /** L'objet contrôleur permettant de gérer les interactions entre la vue et le modèle */
    protected ControleurBlackjack controleur;
    
    /**
     * Constructeur de la fenêtre principale.
     * Initialise l'application avec un modèle, une vue, et un contrôleur. 
     * Crée les joueurs (humain et IA) et les ajoute au modèle.
     */
    public AppWindows() {
        super("Blackjack - Jeu de Cartes");
        initialiser();
    }
   
    /**
     * Initialisation des composants de l'application : modèle, vue, contrôleur et joueurs.
     * Configure également les propriétés de la fenêtre.
     */
    private void initialiser() {
        // Créer le modèle avec une mise minimale de 10
        modele = new ModeleBlackjack(10); 
        
        // Créer les joueurs
        JoueurHumain joueurHumain = new JoueurHumain("Vous", 10000);
        JoueurIA ia1 = new JoueurIA("Bot Alice", 10000, new StrategieBasique());
        JoueurIA ia2 = new JoueurIA("Bot Bob", 10000, new StrategieAgressive());
        
        // Ajouter les joueurs au modèle
        modele.ajouterJoueur(joueurHumain);
        modele.ajouterJoueur(ia1);
        modele.ajouterJoueur(ia2);
        
        // Créer la vue
        vue = new VueBlackjack(modele);
        
        // Créer le contrôleur
        controleur = new ControleurBlackjack(modele, vue);
        
        // Lier le contrôleur à la vue
        vue.setControleur(controleur);
        
        // Configurer la fenêtre
        configurerFenetre();
    }
    
    /**
     * Configure les propriétés de la fenêtre principale (taille, comportement de fermeture, etc.)
     * et ajoute la vue à la fenêtre.
     */
    private void configurerFenetre() {
        // Configurer la fermeture de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Définir la mise en page de la fenêtre
        setLayout(new BorderLayout());
        
        // Ajouter la vue à la fenêtre
        add(vue, BorderLayout.CENTER);
        
        // Ajouter la barre de menus
        setJMenuBar(creerBarreMenu());
        
        // Configurer la taille de la fenêtre
        setSize(1200, 800);
        setLocationRelativeTo(null);  // Centrer la fenêtre
        setMinimumSize(new Dimension(1000, 700));  // Taille minimale de la fenêtre
    }
    
    /**
     * Crée et retourne la barre de menus de l'application.
     * Elle contient les menus pour gérer le jeu, les joueurs et l'aide.
     * 
     * @return La barre de menus configurée
     */
    private JMenuBar creerBarreMenu() {
        JMenuBar menuBar = new JMenuBar();
        
        // Ajouter les menus au menu bar
        menuBar.add(new MenuJeu(this));       // Menu de gestion du jeu (nouvelle partie, quitter, etc.)
        menuBar.add(new MenuJoueurs(this));   // Menu de gestion des joueurs (ajouter, retirer, etc.)
        menuBar.add(new MenuAide(this));      // Menu d'aide (informations, règles, etc.)
        
        return menuBar;
    }
    /**
     * Ajoute un joueur dans le controleur
     * @param j Lajoueur à ajouter
     */
    public void ajouterJoueur(Joueur j){
        controleur.ajouterJoueur(j);
    }
    /**
     * Retire un joueur dans le controleur
     * @param j Le joueur à retirer
     */
    public void retirerJoueur(Joueur j){
        controleur.retirerJoueur(j);
    }
    /**
     * accesseur 
     * 
     * @return controleur
     */
    public ControleurBlackjack getControleur(){
        return controleur;
    }
    
    /**
     * accesseur 
     * 
     * @return modele
     */
    public ModeleBlackjack getModele(){
        return modele;
    }
}
