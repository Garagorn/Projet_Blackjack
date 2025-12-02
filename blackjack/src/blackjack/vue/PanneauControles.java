package blackjack.vue;

import blackjack.controleur.ControleurBlackjack;
import blackjack.modele.actions.Action;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanneauControles extends JPanel {
    
    private MisePanel panneauMise;
    private NouvellePartiePanel panneauNouvellePartie;
    private ActionsPanel panneauActions;
    private ControleurBlackjack controleur;
    
    public PanneauControles() {
        initialiserComposants();
    }
    
    private void initialiserComposants() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createTitledBorder("Actions"));
        
        panneauMise = new MisePanel();
        add(panneauMise, BorderLayout.NORTH);
        
        panneauActions = new ActionsPanel();
        add(panneauActions, BorderLayout.CENTER);
        
        panneauNouvellePartie = new NouvellePartiePanel();
        add(panneauNouvellePartie, BorderLayout.SOUTH);
    }
    
    public void activerActions(List<Action> actionsDisponibles) {
        panneauActions.activerActions(actionsDisponibles);
    }
    
    public void desactiverBoutons() {
        panneauActions.desactiverBoutons();
    }
    
    public void activerMise(boolean actif) {
        panneauMise.getChampMise().setEnabled(actif);
        panneauMise.getBtnMiser().setEnabled(actif);
    }
    
    /*public int getMise() throws NumberFormatException {
        return panneauMise.getMise();
    }*/
    
    public void setControleur(ControleurBlackjack controleur) {
        this.controleur = controleur;
        this.panneauMise.setControleur(controleur);
        this.panneauNouvellePartie.setControleur(controleur);
        this.panneauActions.setControleur(controleur);
    }
}