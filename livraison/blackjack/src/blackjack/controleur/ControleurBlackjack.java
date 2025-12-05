package blackjack.controleur;

import blackjack.modele.jeu.ModeleBlackjack;
import blackjack.modele.joueurs.Joueur;
import blackjack.vue.VueBlackjack;

/**
 * Le contrôleur du jeu de Blackjack. 
 * Il est responsable de la gestion des interactions entre le modèle, la vue, et les gestionnaires d'actions du jeu.
 * Il s'assure que les actions des joueurs sont traitées et que l'état du jeu est mis à jour en conséquence.
 */
public class ControleurBlackjack {
    
    private ModeleBlackjack modele;  // L'objet modèle qui contient l'état du jeu
    private VueBlackjack vue;        // L'interface utilisateur pour afficher l'état du jeu
    private GestionnaireMises gestionnaireMises;  // Gestionnaire des mises des joueurs
    private GestionnaireActionsJoueurHumain gestionnaireActionsHumain;  // Gestionnaire des actions pour le joueur humain
    private GestionnaireIA gestionnaireIA;  // Gestionnaire des actions pour l'IA
    
    /**
     * Constructeur du contrôleur du jeu de Blackjack.
     * 
     * @param modele L'objet modèle qui contient l'état du jeu de Blackjack.
     * @param vue L'interface graphique (ou vue) pour afficher l'état du jeu.
     */
    public ControleurBlackjack(ModeleBlackjack modele, VueBlackjack vue) {
        this.modele = modele;
        this.vue = vue;
        
        // Initialisation des gestionnaires
        this.gestionnaireMises = new GestionnaireMises(modele, vue);
        this.gestionnaireActionsHumain = new GestionnaireActionsJoueurHumain(modele, vue);
        this.gestionnaireIA = new GestionnaireIA(modele, vue);
    }
    
    /**
     * Permet au joueur de placer une mise.
     * Vérifie que la mise est valide et la traite via le gestionnaire des mises.
     * Si la mise est acceptée, le tour de l'IA peut commencer si nécessaire.
     * 
     * @param mise Le montant de la mise du joueur.
     */
    public void actionMiser(int mise) {
        if (gestionnaireMises.traiterMise(mise)) {
            gestionnaireIA.demarrerTourSiNecessaire();  // Si la mise est validée, démarrer le tour de l'IA
        }
    }
    
    /**
     * Exécute une action pour le joueur humain, comme "Tirer", "Rester", "Doubler", etc.
     * Une fois l'action effectuée, le tour de l'IA peut commencer si nécessaire.
     * 
     * @param nomAction Le nom de l'action à exécuter (par exemple, "Tirer").
     */
    public void executerAction(String nomAction) {
        gestionnaireActionsHumain.executerAction(nomAction);  // Exécuter l'action du joueur humain
        gestionnaireIA.demarrerTourSiNecessaire();  // Démarrer le tour de l'IA si nécessaire
    }
    
    /**
     * Démarre une nouvelle partie de Blackjack. 
     * Cette méthode réinitialise l'état du jeu et affiche un message pour inviter les joueurs à miser.
     */
    public void actionNouvellePartie() {
        gestionnaireIA.arreter();  // Arrêter l'IA si elle est en cours
        modele.resetPartie();  // Réinitialiser l'état du jeu
        vue.afficherMessage("Placez vos mises pour commencer");  // Afficher un message pour commencer la partie
    }
    
    /**
     * Ajoute un joueur à la partie.
     * 
     * @param j Le joueur à ajouter.
     */
    public void ajouterJoueur(Joueur j) {
        modele.ajouterJoueur(j);  // Ajouter le joueur au modèle
    }
    
    /**
     * Retire un joueur de la partie.
     * 
     * @param j Le joueur à retirer.
     */
    public void retirerJoueur(Joueur j) {
        modele.retirerJoueur(j);  // Retirer le joueur du modèle
    }
}
