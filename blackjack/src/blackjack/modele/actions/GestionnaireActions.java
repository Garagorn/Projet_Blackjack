package blackjack.modele.actions;

import blackjack.modele.jeu.ModeleBlackjack;
import blackjack.modele.joueurs.Joueur;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GestionnaireActions {
    
    private Map<String, Action> actionsDisponibles;
    private ModeleBlackjack modele;
    
  
    public GestionnaireActions(ModeleBlackjack modele) {
        this.modele = modele;
        this.actionsDisponibles = new HashMap<>();
        initialiserActions();
    }
    
   
    private void initialiserActions() {
        enregistrerAction(new ActionTirer(modele));
        enregistrerAction(new ActionRester(modele));
        enregistrerAction(new ActionDoubler(modele));
        enregistrerAction(new ActionSplit(modele));
        enregistrerAction(new ActionAssurance(modele));
    }
    
    public void enregistrerAction(Action action) {
        if (action != null) {
            actionsDisponibles.put(action.getNom(), action);
        }
    }
    
    public Action retirerAction(String nomAction) {
        return actionsDisponibles.remove(nomAction);
    }
    
    
    public Action obtenirAction(String nomAction) {
        return actionsDisponibles.get(nomAction);
    }
    
    
    public ArrayList<Action> obtenirToutesLesActions() {
        return new ArrayList<>(actionsDisponibles.values());
    }
    
    
    public ArrayList<Action> obtenirActionsExecutables(Joueur joueur) {
        ArrayList<Action> actionsExecutables = new ArrayList<>();
        
        if (joueur == null) {
            return actionsExecutables;
        }
        for (Action action : actionsDisponibles.values()) {
            if (action.peutExecuter(joueur)) {
                
                actionsExecutables.add(action);
            }
        }
        
        return actionsExecutables;
    }
    /**
     * Exécute une action pour un joueur donné
     * @param nomAction le nom de l'action
     * @param joueur le joueur qui exécute l'action
     */
    public void executerAction(String nomAction, Joueur joueur) {
        Action action = obtenirAction(nomAction);
        if (action != null && joueur != null) {
            action.executer(joueur);
        }
    }
  
    
     
    public List<String> obtenirNomsActions() {
        return new ArrayList<>(actionsDisponibles.keySet());
    }
    
     
    public boolean actionExiste(String nomAction) {
        return actionsDisponibles.containsKey(nomAction);
    }
    
     
    public int getNombreActions() {
        return actionsDisponibles.size();
    }
    
    
    public void reinitialiser() {
        actionsDisponibles.clear();
        initialiserActions();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("GestionnaireActions [");
        sb.append(actionsDisponibles.size()).append(" actions: ");
        sb.append(String.join(", ", actionsDisponibles.keySet()));
        sb.append("]");
        return sb.toString();
    }
}