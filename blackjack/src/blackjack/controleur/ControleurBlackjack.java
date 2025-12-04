package blackjack.controleur;

import blackjack.modele.jeu.ModeleBlackjack;
import blackjack.vue.VueBlackjack;

public class ControleurBlackjack {
    
    private ModeleBlackjack modele;
    private VueBlackjack vue;
    private GestionnaireMises gestionnaireMises;
    private GestionnaireActionsJoueurHumain gestionnaireActionsHumain;
    private GestionnaireIA gestionnaireIA;
    
    public ControleurBlackjack(ModeleBlackjack modele, VueBlackjack vue) {
        this.modele = modele;
        this.vue = vue;
        
        // Initialisation des gestionnaires
        this.gestionnaireMises = new GestionnaireMises(modele, vue);
        this.gestionnaireActionsHumain = new GestionnaireActionsJoueurHumain(modele, vue);
        this.gestionnaireIA = new GestionnaireIA(modele, vue);
        
    }
    
    public void actionMiser(int mise) {
        if (gestionnaireMises.traiterMise(mise)) {
            gestionnaireIA.demarrerTourSiNecessaire();
        }
    }
    
    public void executerAction(String nomAction) {
        gestionnaireActionsHumain.executerAction(nomAction);
        gestionnaireIA.demarrerTourSiNecessaire();
    }
    
    public void actionNouvellePartie() {
        gestionnaireIA.arreter();
        modele.resetPartie();
        //vue.reinitialiser();
        vue.afficherMessage("Placez vos mises pour commencer");
    }
    
     
   
}