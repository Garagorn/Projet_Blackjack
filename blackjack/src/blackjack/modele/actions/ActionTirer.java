package blackjack.modele.actions;

import blackjack.modele.joueurs.Joueur;
import blackjack.modele.joueurs.Main;
import modele.cartes.Carte;
import modele.cartes.Paquet;

/**
 * Action permettant au joueur de tirer une carte supplémentaire (Hit).
 * 
 * Cette action permet au joueur de tirer une carte du paquet et de l'ajouter à sa main.
 * Elle peut être exécutée tant que la main du joueur n'est pas vide, que le joueur n'est pas buste,
 * et que le paquet contient des cartes.
 * 
 * @see Action
 */
public class ActionTirer extends Action {
    
    /**
     * Constructeur de l'action "Tirer".
     * Initialise l'action avec le paquet de cartes à utiliser.
     * 
     * @param pioche le paquet de cartes à utiliser dans le jeu
     */
    public ActionTirer(Paquet pioche) {
        super(pioche);
    }
    
    /**
     * Exécute l'action "Tirer" pour le joueur.
     * Si l'action peut être exécutée, une carte est tirée du paquet et ajoutée à la main du joueur.
     * 
     * @param joueur le joueur qui choisit de tirer une carte
     */
    @Override
    public void executer(Joueur joueur) {
        if (peutExecuter(joueur)) {
            Main mainActuelle = joueur.getMainActuelle();

            // Tirer une carte du paquet
            Carte carte = pioche.getPaquet().get(0);

            if (carte != null) {
                mainActuelle.ajouterCarte(carte);
                pioche.retirer(carte);
            } 
        }
    }
    
    /**
     * Vérifie si l'action "Tirer" peut être exécutée par le joueur.
     * L'action peut être effectuée si :
     * - La main du joueur n'est pas vide
     * - Le joueur n'est pas buste (a plus de 21 points)
     * - Le joueur n'a pas déjà doublé sa mise (et tiré sa carte supplémentaire)
     * - Le paquet contient encore des cartes
     * 
     * @param joueur le joueur pour lequel vérifier si l'action peut être effectuée
     * @return true si l'action peut être exécutée, sinon false
     */
    @Override
    public boolean peutExecuter(Joueur joueur) {
        if (joueur == null) {
            return false;
        }
        
        Main mainActuelle = joueur.getMainActuelle();
        if(mainActuelle.estVide() || mainActuelle.estBuste() || mainActuelle.estDoublee() || pioche.estVide()){
            return false;
        }
        return true;
    }
    
    /**
     * Retourne le nom de l'action, utilisé pour l'affichage dans l'interface utilisateur.
     * 
     * @return le nom de l'action : "Tirer"
     */
    @Override
    public String getNom() {
        return "Tirer";
    }
    
    /**
     * Retourne une description de l'action, utilisée pour fournir plus d'informations à l'utilisateur.
     * 
     * @return une description de l'action : "Tirer une carte supplémentaire"
     */
    @Override
    public String getDescription() {
        return "Tirer une carte supplémentaire";
    }
}
