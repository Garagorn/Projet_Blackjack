package blackjack.controleur;

import blackjack.modele.actions.Action;
import blackjack.modele.jeu.ModeleBlackjack;
import blackjack.modele.joueurs.JoueurIA;
import blackjack.vue.VueBlackjack;
import javax.swing.Timer;

/**
 * Gestionnaire dédié aux tours de l'IA (Intelligence Artificielle) dans le jeu de Blackjack.
 * Cette classe gère l'exécution du tour de l'IA, en démarrant le timer pour simuler un délai 
 * avant que l'IA ne prenne une action. Elle vérifie également si l'IA doit passer son tour ou
 * continuer à jouer en fonction de son état de main et des actions possibles.
 */
public class GestionnaireIA {
    private ModeleBlackjack modele;    // L'objet modèle qui contient l'état actuel du jeu
    private VueBlackjack vue;          // L'interface graphique pour afficher les messages du jeu
    private Timer timerIA;             // Timer utilisé pour simuler un délai dans le tour de l'IA
    private boolean enCours;           // Indicateur pour savoir si le tour de l'IA est en cours
    
    /**
     * Constructeur du gestionnaire des tours de l'IA.
     * 
     * @param modele L'objet modèle qui contient l'état actuel du jeu.
     * @param vue L'interface graphique qui permet d'afficher l'état du jeu et les messages.
     */
    public GestionnaireIA(ModeleBlackjack modele, VueBlackjack vue) {
        this.modele = modele;
        this.vue = vue;
        this.enCours = false;
        this.timerIA = new Timer(1500, e -> executerTourIA());  // Timer qui exécute le tour de l'IA après un délai
        this.timerIA.setRepeats(false);  // Le timer ne se répète pas, il est déclenché une seule fois
    }
    
    /**
     * Démarre le tour de l'IA si nécessaire.
     * Vérifie si c'est le tour de l'IA et si le tour n'est pas déjà en cours et lance l'IA
     * 
     */
    public void demarrerTourSiNecessaire() {
        if (enCours || !estTourIA()) {
            return;  // Si le tour est déjà en cours ou si ce n'est pas le tour de l'IA, rien à faire
        }
        
        enCours = true;  // Indique que le tour de l'IA commence
        timerIA.start();  // Démarre le timer qui exécutera le tour de l'IA après un délai
    }
    
    /**
     * Vérifie si c'est le tour de l'IA de jouer.
     * 
     * @return true si c'est le tour de l'IA, false sinon.
     */
    private boolean estTourIA() {
        return modele.getJoueurActif() instanceof JoueurIA;  // Vérifie si le joueur actif est un joueur contrôlé par l'IA
    }
    
    /**
     * Exécute le tour de l'IA.
     * L'IA choisit une action basée sur sa stratégie et l'état du jeu.
     * Après avoir effectué une action, le tour est terminé si l'action est "Rester", "Doubler" ou si l'IA est buste.
     * 
     * @return true si une action a été effectuée, false sinon.
     */
    private void executerTourIA() {
        if (!estTourIA()) {
            enCours = false;
            return;  // Si ce n'est pas le tour de l'IA, arrête le processus
        }
        
        JoueurIA ia = (JoueurIA) modele.getJoueurActif();  // Récupère l'IA qui joue actuellement
        Action action = ia.jouerTour(modele.getCroupier().getCarteVisible(), modele.getActionsDisponibles(ia));  // L'IA joue son tour
        
        if (action != null) {
            vue.afficherMessage(ia.getNom() + " : " + action.getNom());  // Affiche l'action de l'IA
            modele.executerAction(action);  // Exécute l'action dans le modèle
            gererFinActionIA(action, ia);  // Vérifie si l'action termine le tour de l'IA
        }
    }
    
    /**
     * Gère la fin du tour de l'IA après l'exécution d'une action.
     * Si l'action termine le tour (par exemple, "Rester" ou "Doubler"), passe au joueur suivant.
     * Si le tour de l'IA n'est pas terminé, redémarre le timer pour simuler une nouvelle action.
     * 
     * @param action L'action effectuée par l'IA.
     * @param ia Le joueur IA qui a effectué l'action.
     */
    private void gererFinActionIA(Action action, JoueurIA ia) {
        boolean actionTermineTour = action.getNom().equals("Rester") || 
                                   action.getNom().equals("Doubler") ||
                                   (ia.getMainActuelle() != null && ia.getMainActuelle().estBuste());  // Vérifie si l'action termine le tour
        
        if (actionTermineTour) {
            passerAuJoueurSuivant();  // Passe au joueur suivant si l'action termine le tour
        } else {  
            timerIA.restart();  // Sinon, redémarre le timer pour que l'IA fasse une nouvelle action
        }
    }
    
    /**
     * Passe au joueur suivant après que l'IA ait terminé son tour.
     * Si c'est toujours le tour de l'IA, redémarre le processus.
     */
    private void passerAuJoueurSuivant() {
        modele.passerAuJoueurSuivant();  // Passe au joueur suivant
        enCours = false;  // L'IA ne joue plus, donc le tour est terminé
        
        // Si c'est toujours le tour de l'IA, démarre le tour de l'IA à nouveau
        if (estTourIA()) {
            demarrerTourSiNecessaire();
        }
    }
    
    /**
     * Arrête le processus du tour de l'IA.
     * Si le timer est en cours, il sera stoppé.
     */
    public void arreter() {
        enCours = false;  // Indique que le tour de l'IA est terminé
        if (timerIA.isRunning()) {
            timerIA.stop();  // Arrête le timer si il est en cours
        }
    }
}
