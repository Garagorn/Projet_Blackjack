package blackjack.vue;

import blackjack.controleur.ControleurBlackjack;
import blackjack.modele.actions.Action; // Import explicite de votre Action
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.swing.*;

public class ActionsPanel extends JPanel {
    
    private ControleurBlackjack controleur;
    private final Map<String, JButton> boutonsActions;
    
    public ActionsPanel() {
        super(new GridLayout(3, 2, 5, 5));
        this.boutonsActions = new HashMap<>();
        creerBoutonAction("Tirer");
        creerBoutonAction("Rester");
        creerBoutonAction("Doubler");
        creerBoutonAction("Séparer");
        creerBoutonAction("Assurance");
        
        for (JButton bouton : boutonsActions.values()) {
            this.add(bouton);
        }
    }
    
    private void creerBoutonAction(String nomAction) {
        JButton bouton = new JButton(nomAction);
        bouton.setEnabled(false);
        
        bouton.addActionListener(e -> {
            if (controleur != null) {
                controleur.executerAction(nomAction);
            }
        });
        
        boutonsActions.put(nomAction, bouton);
    }
    
    public Map<String, JButton> getBoutonsActions(){
        return this.boutonsActions;
    }
    
    public void activerActions(List<Action> actionsDisponibles) {
        if (actionsDisponibles != null) { 
            for (Action action : actionsDisponibles) {
                JButton bouton = boutonsActions.get(action.getNom());
                if (bouton != null) {
                    bouton.setEnabled(true);
                }
            }
        }
    }
    
    public void desactiverBoutons() {
        for (JButton bouton : boutonsActions.values()) {
            bouton.setEnabled(false);
        }
    }
    
    public void setControleur(ControleurBlackjack controleur) {
        this.controleur = controleur;
    }
}