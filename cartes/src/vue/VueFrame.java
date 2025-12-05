package vue;

import javax.swing.*;
import modele.cartes.*;
import controleur.*;

/**
 * {@code VueFrame} est la fenêtre principale de l'application graphique du jeu de cartes.
 * Elle assemble et affiche les différents éléments de l'interface utilisateur :
 * - La pioche (face cachée)
 * - La main du joueur (face visible)
 * - La défausse (face visible)
 *
 * Cette classe initialise également les contrôleurs qui gèrent les interactions
 * entre la vue et le modèle.
 *
 * Elle respecte l'architecture MVC en séparant clairement les vues, les modèles et les contrôleurs.
 */
public class VueFrame extends JFrame {
    
    /** Modèle représentant la pioche (pile de cartes face cachée). */
    private final Paquet pioche;
    
    /** Modèle représentant la main du joueur. */
    private final Paquet mainJoueur;
    
    /** Modèle représentant la défausse (cartes jouées). */
    private final Paquet defausse;

    /** Vue de la pioche (face cachée). */
    private final VuePaquetCache vuePioche;

    /** Vue de la main du joueur (face visible). */
    private final VuePaquetLisible vueMain;

    /** Vue de la défausse (face visible). */
    private final VuePaquetLisible vueDefausse;

    /**
     * Construit la fenêtre principale du jeu de cartes.
     * 
     * @param pioche      Le paquet représentant la pioche initiale.
     * @param mainJoueur  Le paquet représentant la main du joueur.
     * @param defausse    Le paquet représentant la défausse.
     */
    public VueFrame(Paquet pioche ,Paquet mainJoueur,Paquet defausse) {
        // Initialisation des modèles
        this.pioche = pioche;
        this.mainJoueur = mainJoueur;
        this.defausse =  defausse;

        // Initialisation des vues associées
        vuePioche = new VuePaquetCache(pioche,"PIOCHE");
        vueMain = new VuePaquetLisible(mainJoueur,false,"MAIN JOUEUR");
        vueDefausse = new VuePaquetLisible(defausse,true,"DEFAUSSE");
        
        // Initialisation des contrôleurs pour gérer les interactions
        new ControleurPiocheVersPaquet(vuePioche, mainJoueur);
        new ControleurChoixCarteVersPaquet(vueMain, defausse);

        // Mise en page de la fenêtre
        setTitle("Jeu de cartes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Ajout des composants à la fenêtre
        this.add(vuePioche);
        this.add(vueMain);
        this.add(vueDefausse);

        setVisible(true);
    }
}
