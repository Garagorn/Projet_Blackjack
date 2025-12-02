package blackjack.modele.actions;

import blackjack.modele.jeu.ModeleBlackjack;
import blackjack.modele.joueurs.Joueur;

/**
 * Classe abstraite représentant une action dans le jeu de Blackjack.
 * Implémente le Pattern Command pour rendre le système d'actions extensible.
 * 
 * Chaque action concrète hérite de cette classe et définit :
 * - Comment elle s'exécute
 * - Dans quelles conditions elle peut être exécutée
 * - Son nom pour l'affichage
 */
public abstract class Action {
    
    protected ModeleBlackjack modele;
    
    /**
     * Constructeur de l'action
     * @param modele le modèle du jeu Blackjack
     */
    public Action(ModeleBlackjack modele) {
        this.modele = modele;
    }
    
    /**
     * Exécute l'action pour un joueur donné.
     * 
     * @param joueur le joueur qui exécute l'action
     * @return true si l'action a été exécutée avec succès, false sinon
     */
    public abstract void executer(Joueur joueur);
    
    /**
     * Vérifie si l'action peut être exécutée pour un joueur donné.
     * Permet d'activer/désactiver les boutons dans l'interface.
     * 
     * @param joueur le joueur pour lequel vérifier la possibilité
     * @return true si l'action est possible, false sinon
     */
    public abstract boolean peutExecuter(Joueur joueur);
    
    /**
     * Retourne le nom de l'action pour l'affichage.
     * 
     * @return le nom de l'action (ex: "Tirer", "Rester", "Doubler")
     */
    public abstract String getNom();
    
    /**
     * Retourne une description de l'action (optionnel).
     * Par défaut, retourne le nom de l'action.
     * 
     * @return la description de l'action
     */
    public String getDescription() {
        return getNom();
    }
    
    @Override
    public String toString() {
        return getNom();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Action other = (Action) obj;
        return getNom().equals(other.getNom());
    }
    
    @Override
    public int hashCode() {
        return getNom().hashCode();
    }
}