package blackjack.vue.composantscontrol;


import blackjack.controleur.ControleurBlackjack;
import blackjack.modele.actions.Action;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Panneau des contrôles de l'interface utilisateur pour le jeu de Blackjack.
 * Ce panneau contient plusieurs sous-panneaux permettant de gérer les actions de mise,
 * les actions du joueur (tirer, rester, doubler, etc.), ainsi qu'un bouton pour démarrer une nouvelle partie.
 * Il permet également d'activer ou de désactiver ces contrôles en fonction de l'état de la partie.
 */
public class PanneauControles extends JPanel {
    /**
     *panneau qui contient tout ce qui a lien avec la mise, le champs du texte le bouton, le label
     */
    private MisePanel panneauMise;
    
    /**
     * panneau qui contient le bouton nouvelle Partie
     */
    private NouvellePartiePanel panneauNouvellePartie;
    /**
     * le panneau qui affiche les actions disponibles au joueur (des boutons)
     */
    private ActionsPanel panneauActions;
    
    /**
     *reference sur le controleur pour orchestrer le jeu
     */
    private ControleurBlackjack controleur;
    
    /**
     * Constructeur de PanneauControles.
     * Initialise les composants du panneau et les place dans la disposition.
     */
    public PanneauControles() {
        initialiserComposants();
    }
    
    /**
     * Initialise les composants du panneau de contrôles.
     * Ajoute le panneau de mise, le panneau d'actions et le panneau de nouvelle partie.
     */
    private void initialiserComposants() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createTitledBorder("Actions"));
        
        // Initialisation des panneaux
        panneauMise = new MisePanel();
        add(panneauMise, BorderLayout.NORTH);  // Panneau de mise en haut
        
        panneauActions = new ActionsPanel();
        add(panneauActions, BorderLayout.CENTER);  // Panneau des actions au centre
        
        panneauNouvellePartie = new NouvellePartiePanel();
        add(panneauNouvellePartie, BorderLayout.SOUTH);  // Panneau de nouvelle partie en bas
    }
    
    /**
     * Active les boutons correspondant aux actions disponibles pour le joueur.
     * Les actions possibles peuvent inclure "Tirer", "Rester", "Doubler", etc.
     *
     * @param actionsDisponibles La liste des actions disponibles pour le joueur.
     */
    public void activerActions(List<Action> actionsDisponibles) {
        panneauActions.activerActions(actionsDisponibles);
    }
    
    /**
     * Désactive tous les boutons d'actions.
     * Utilisé pour désactiver les actions lorsque ce n'est pas le tour du joueur ou à la fin de la partie.
     */
    public void desactiverBoutons() {
        panneauActions.desactiverBoutons();
    }
    
    /**
     * Active ou désactive les champs de mise.
     * Utilisé pour permettre au joueur de placer une mise en début de partie,
     * ou de l'empêcher lorsqu'il est en train de jouer.
     *
     * @param actif Indique si les champs de mise doivent être activés (true) ou désactivés (false).
     */
    public void activerMise(boolean actif) {
        panneauMise.getChampMise().setEnabled(actif);
        panneauMise.getBtnMiser().setEnabled(actif);
    }
    
    /**
     * Associe un contrôleur à ce panneau de contrôles.
     * Le contrôleur est utilisé pour gérer les actions du joueur (mise, choix d'actions, etc.).
     *
     * @param controleur Le contrôleur qui gère les actions de l'interface.
     */
    public void setControleur(ControleurBlackjack controleur) {
        this.controleur = controleur;
        this.panneauMise.setControleur(controleur);
        this.panneauNouvellePartie.setControleur(controleur);
        this.panneauActions.setControleur(controleur);
    }
}
