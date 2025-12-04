package blackjack.vue.composantscontrol;

import blackjack.controleur.ControleurBlackjack;
import blackjack.modele.actions.Action;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.swing.*;

/**
 * Panneau affichant les boutons d'action disponibles pour un joueur.
 * Chaque bouton correspond à une action que le joueur peut effectuer pendant son tour (Tirer, Rester, Doubler, Séparer, Assurance).
 */
public class ActionsPanel extends JPanel {
    
    /**Référence vers le contrôleur pour exécuter les actions
     * 
     */
    private ControleurBlackjack controleur;
    
    /**Map pour associer le nom de l'action au bouton correspondant
     * 
     */
    private final Map<String, JButton> boutonsActions;

    /**
     * Constructeur de `ActionsPanel`.
     * Initialise le panneau avec une disposition en grille verticale.
     * Crée les boutons d'action possibles pour le joueur.
     */
    public ActionsPanel() {
        super(new GridLayout(0, 1, 5, 5)); // 1 colonne = boutons empilés verticalement
        this.boutonsActions = new HashMap<>();
        
        // Créer tous les boutons possibles
        creerBoutonAction("Tirer");
        creerBoutonAction("Rester");
        creerBoutonAction("Doubler");
        creerBoutonAction("Séparer");
        creerBoutonAction("Assurance");
    }
    
    /**
     * Crée un bouton pour chaque action possible.
     * @param nomAction Le nom de l'action pour laquelle créer un bouton.
     */
    private void creerBoutonAction(String nomAction) {
        JButton bouton = new JButton(nomAction);
        
        // Définir une taille préférée et maximale pour les boutons
        Dimension tailleBouton = new Dimension(100, 30);
        bouton.setPreferredSize(tailleBouton);
        bouton.setMaximumSize(tailleBouton);
        
        // Réduire la police du texte sur les boutons
        bouton.setFont(new Font("Arial", Font.PLAIN, 12));
        
        // Ajouter un écouteur pour gérer l'événement de clic
        bouton.addActionListener(e -> {
            if (controleur != null) {
                controleur.executerAction(nomAction);
            }
        });
        
        boutonsActions.put(nomAction, bouton);
    }
    
    /**
     * Retourne la map des boutons d'action disponibles.
     * @return La map des boutons d'action.
     */
    public Map<String, JButton> getBoutonsActions() {
        return this.boutonsActions;
    }
    
    /**
     * Active et affiche uniquement les actions disponibles pour le joueur.
     * Cela met à jour le panneau en affichant les boutons des actions qui sont possibles.
     * @param actionsDisponibles Liste des actions que le joueur peut exécuter.
     */
    public void activerActions(List<Action> actionsDisponibles) {
        // Vider le panel avant d'ajouter les nouveaux boutons
        this.removeAll();
        
        if (actionsDisponibles != null && !actionsDisponibles.isEmpty()) {
            // Ajouter uniquement les boutons correspondant aux actions disponibles
            for (Action action : actionsDisponibles) {
                JButton bouton = boutonsActions.get(action.getNom());
                if (bouton != null) {
                    bouton.setEnabled(true);
                    this.add(bouton);
                }
            }
        }
        
        // Rafraîchir l'affichage du panneau pour qu'il soit mis à jour
        this.revalidate();
        this.repaint();
    }
    
    /**
     * Masque tous les boutons d'action, les rendant invisibles et inaccessibles.
     */
    public void desactiverBoutons() {
        this.removeAll();
        this.revalidate();
        this.repaint();
    }
    
    /**
     * Définit le contrôleur associé à ce panneau d'actions.
     * @param controleur Le contrôleur de Blackjack qui gère les actions du joueur.
     */
    public void setControleur(ControleurBlackjack controleur) {
        this.controleur = controleur;
    }
}
