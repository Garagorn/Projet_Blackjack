package blackjack.controleur;

import blackjack.modele.actions.Action;
import blackjack.modele.jeu.ModeleBlackjack;
import blackjack.modele.joueurs.JoueurIA;
import blackjack.vue.VueBlackjack;
import javax.swing.Timer;

/**
 * Gestionnaire dédié aux tours de l'IA
 */
public class GestionnaireIA {
    private ModeleBlackjack modele;
    private VueBlackjack vue;
    private Timer timerIA;
    private boolean enCours;
    
    public GestionnaireIA(ModeleBlackjack modele, VueBlackjack vue) {
        this.modele = modele;
        this.vue = vue;
        this.enCours = false;
        this.timerIA = new Timer(1500, e -> executerTourIA());
        this.timerIA.setRepeats(false);
    }
    
    public void demarrerTourSiNecessaire() {
        if (enCours || !estTourIA()) {
            return;
        }
        
        enCours = true;
        timerIA.start();
    }
    
    private boolean estTourIA() {
        return modele.getJoueurActif() instanceof JoueurIA;
    }
    
    
    private void executerTourIA() {
        if (!estTourIA()) {
            enCours = false;
            return;
        }
        
        JoueurIA ia = (JoueurIA) modele.getJoueurActif();
        Action action = ia.jouerTour(modele.getCroupier().getCarteVisible(),modele.getActionsDisponibles(ia));
        
        if (action != null) {
            vue.afficherMessage(ia.getNom() + " : " + action.getNom());
            modele.executerAction(action);
            gererFinActionIA(action, ia);
           
        }
    }
    
     
    private void gererFinActionIA(Action action, JoueurIA ia) {
        boolean actionTermineTour = action.getNom().equals("Rester") || 
                                   action.getNom().equals("Doubler") ||
                                   (ia.getMainActuelle() != null && ia.getMainActuelle().estBuste());
        
        if (actionTermineTour) {
            passerAuJoueurSuivant();
        } else {  
            timerIA.restart();
        }
    }
    
    /**
     * Passe au joueur suivant
     */
    private void passerAuJoueurSuivant() {
        modele.passerAuJoueurSuivant();
        enCours = false;
        if (estTourIA()) {
            demarrerTourSiNecessaire();
        }
    }
    
    
    public void arreter() {
        enCours = false;
        if (timerIA.isRunning()) {
            timerIA.stop();
        }
    }
}