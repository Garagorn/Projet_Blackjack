package modele.cartes;

import javax.swing.*;
import vue.VueFrame;

/**
 * Classe principale du projet.
 * Initialise l'interface graphique et les paquets de jeu.
 * 
 * @author ...
 */
public class MainClass {
<<<<<<< HEAD

    /**
     * Constructeur par défaut.
     * Ce constructeur est vide car la classe ne contient que la méthode main.
     */
    public MainClass() {
        // Rien à initialiser ici.
    }

    /**
     * Méthode principale (point d'entrée du programme).
     * @param args Arguments de la ligne de commande (non utilisés ici).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Création des paquets
            Paquet pioche = Paquet.creerJeu32(); // Paquet initialisé avec 32 cartes
            Paquet mainJoueur = new Paquet();     // Vide au départ
            Paquet defausse = new Paquet();       // Vide aussi

            // Création et affichage de la fenêtre principale
            new VueFrame(pioche, mainJoueur, defausse);
        });
    }
    
}