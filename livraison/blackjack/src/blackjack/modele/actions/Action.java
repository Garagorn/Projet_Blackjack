package blackjack.modele.actions;

import blackjack.modele.jeu.ModeleBlackjack;
import blackjack.modele.joueurs.Joueur;
import modele.cartes.Paquet;

/**
 * Classe abstraite représentant une action dans le jeu de Blackjack.
 * Implémente le Pattern Command pour rendre le système d'actions extensible.
 * 
 * Chaque action concrète hérite de cette classe et définit :
 * - Comment elle s'exécute
 * - Dans quelles conditions elle peut être exécutée
 * - Son nom pour l'affichage
 * 
 * Par exemple : Tirer, Rester, Doubler, Séparer, etc.
 */
public abstract class Action {
    
    // Paquet depuis lequel les cartes sont tirées

    /**
     * la pioche du jeu
     */
    protected Paquet pioche;
    
    /**
     * Constructeur de la classe Action.
     * Permet d'initialiser le paquet à partir duquel les cartes seront tirées pour cette action.
     * 
     * @param pioche le paquet de cartes à utiliser pour l'action
     */
    public Action(Paquet pioche){
        this.pioche = pioche;
    }
    
    /**
     * Exécute l'action pour un joueur donné.
     * Chaque sous-classe de Action implémentera cette méthode pour effectuer l'action correspondante.
     * 
     * Par exemple, pour l'action "Tirer", cela implique de retirer une carte du paquet et de l'ajouter à la main du joueur.
     * 
     * @param joueur le joueur qui exécute l'action
     */
    public abstract void executer(Joueur joueur);
    
    /**
     * Vérifie si l'action peut être exécutée pour un joueur donné.
     * Cette méthode permet d'activer ou de désactiver des boutons dans l'interface graphique (par exemple, "Tirer", "Rester").
     * Chaque sous-classe de Action définira les conditions sous lesquelles l'action peut être réalisée.
     * 
     * @param joueur le joueur pour lequel vérifier la possibilité
     * @return true si l'action est possible, false sinon
     */
    public abstract boolean peutExecuter(Joueur joueur);
    
    /**
     * Retourne le nom de l'action pour l'affichage.
     * Ce nom sera utilisé dans l'interface utilisateur ou dans les logs pour afficher l'action exécutée.
     * Par exemple, pour l'action "Tirer", cela retournera "Tirer".
     * 
     * @return le nom de l'action (par exemple, "Tirer", "Rester", "Doubler")
     */
    public abstract String getNom();
    
    /**
     * Retourne une description de l'action.
     * Cette méthode peut être redéfinie pour fournir plus d'informations sur l'action, mais par défaut,
     * elle renvoie simplement le nom de l'action.
     * 
     * @return la description de l'action
     */
    public String getDescription() {
        return getNom();
    }
    
    /**
     * Méthode redéfinie de la classe Object.
     * Elle permet de comparer deux objets Action pour savoir s'ils sont égaux. 
     * Deux actions sont considérées égales si elles ont le même nom.
     * 
     * @param obj l'objet à comparer avec l'instance courante
     * @return true si les actions sont égales, false sinon
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Action other = (Action) obj;
        return getNom().equals(other.getNom());
    }
    
    /**
     * Méthode redéfinie de la classe Object.
     * Fournit un code de hachage pour l'action en utilisant son nom.
     * 
     * @return le code de hachage de l'action
     */
    @Override
    public int hashCode() {
        return getNom().hashCode();
    }
    
    /**
     * Méthode redéfinie de la classe Object.
     * Fournit une représentation sous forme de chaîne de caractères de l'action (son nom).
     * Cela peut être utilisé pour afficher l'action dans des logs ou des messages.
     * 
     * @return le nom de l'action
     */
    @Override
    public String toString() {
        return getNom();
    }
}
