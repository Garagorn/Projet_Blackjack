package blackjack.modele.jeu;

import blackjack.modele.actions.Action;
import blackjack.modele.actions.GestionnaireActions;
import blackjack.modele.event.AbstractModeleEcoutable;
import blackjack.modele.joueurs.Croupier;
import blackjack.modele.joueurs.Joueur;
import blackjack.modele.joueurs.JoueurHumain;
import blackjack.modele.util.EtatPartie;
import modele.cartes.Carte;
import modele.cartes.Paquet;
import java.util.ArrayList;
import java.util.List;

/**
 * Modèle principal du jeu Blackjack.
 * 
 * RÔLE DANS LE PATTERN MVC :
 * Le modèle contient TOUTES les données du jeu et TOUTE la logique métier.
 * - Il ne connaît PAS la vue
 * - Il ne connaît PAS le contrôleur
 * - Il notifie les changements via le pattern Observer
 * 
 * DÉLÉGATION DES RESPONSABILITÉS :
 * - GestionnairePartie : Logique du déroulement d'une partie
 * - GestionnairePaquet : Gestion de la pioche et de la défausse
 * - GestionnaireActions : Gestion des actions disponibles
 * 
 * @author siaghi231
 */
public class ModeleBlackjack extends AbstractModeleEcoutable {
    
    // ===== DONNÉES DU JEU =====
    private ArrayList<Joueur> joueurs;
    private Croupier croupier;
    private int miseMinimale;
    
    // ===== GESTIONNAIRES =====
    private GestionnairePartie gestionnairePartie;
    private GestionnaireActions gestionnaireActions;
    private GestionnairePaquet gestionnairePaquet;
    
    /**
     * Constructeur du modèle de jeu Blackjack.
     * 
     * Initialise les joueurs, le croupier, et les gestionnaires pour la partie, 
     * les actions et le paquet de cartes.
     * 
     * @param miseMinimale La mise minimale que chaque joueur doit mettre pour participer à la partie.
     */
    public ModeleBlackjack(int miseMinimale) {
        this.joueurs = new ArrayList<>();
        this.croupier = new Croupier();
        this.gestionnairePaquet = new GestionnairePaquet();
        this.gestionnaireActions = new GestionnaireActions(this);
        this.gestionnairePartie = new GestionnairePartie(joueurs, gestionnairePaquet, croupier);
        this.miseMinimale = miseMinimale;
    }
    
    // ===== GESTION DES JOUEURS =====
    
    /**
     * Ajoute un joueur à la partie.
     * 
     * @param joueur Le joueur à ajouter. Il ne sera ajouté que s'il n'est pas déjà dans la liste des joueurs.
     */
    public void ajouterJoueur(Joueur joueur) {
        if (joueur != null && !joueurs.contains(joueur)) {
            joueurs.add(joueur);
            fireChangement(); // Notifier la vue qu'il y a eu un changement
        }
    }
    
    /**
     * Retire un joueur de la partie.
     * 
     * @param joueur Le joueur à retirer de la partie.
     */
    public void retirerJoueur(Joueur joueur) {
        if (joueurs.remove(joueur)) {
            fireChangement(); // Notifier la vue qu'il y a eu un changement
        }
    }
    
    /**
     * Retourne le joueur actuellement actif dans la partie.
     * 
     * @return Le joueur actif.
     */
    public Joueur getJoueurActif() {
        return gestionnairePartie.getJoueurActif();
    }
    
    /**
     * Retourne l'index du joueur actif dans la liste des joueurs.
     * 
     * @return L'index du joueur actif.
     */
    public int getJoueurActifIndex() {
        return gestionnairePartie.getJoueurActifIndex();
    }
    
    /**
     * Retourne le joueur humain, s'il existe dans la partie.
     * 
     * @return Le joueur humain, ou null si aucun joueur humain n'est trouvé.
     */
    public JoueurHumain getJoueurHumain() {
        return (JoueurHumain) joueurs.stream()
                .filter(j -> j instanceof JoueurHumain)
                .findFirst()
                .orElse(null);
    }
    
    // ===== GESTION DE LA PARTIE =====
    
    /**
     * Démarre une nouvelle partie de Blackjack.
     * 
     * Après avoir distribué les cartes, cette méthode vérifie si certains joueurs ont un blackjack 
     * et les passe automatiquement (ils ne jouent pas leur tour).
     */
    public void demarrerPartie() {
        gestionnairePartie.demarrerPartie();
        verifierEtPasserBlackjack();
        fireChangement(); // Notifier la vue qu'il y a eu un changement
    }
    
    /**
     * Vérifie si le joueur actif a un blackjack et le fait passer automatiquement.
     * 
     * Cette méthode est appelée en boucle pour passer tous les joueurs qui ont un blackjack 
     * dès le début de la partie, avant que les joueurs ne commencent à jouer leur tour.
     */
    private void verifierEtPasserBlackjack() {
        while (gestionnairePartie.getEtatPartie() == EtatPartie.TOUR_JOUEURS && 
               gestionnairePartie.joueurActifADejaBlackjack()) {
            
            Joueur joueur = gestionnairePartie.getJoueurActif();
            
            // Passer au joueur suivant
            if (gestionnairePartie.passerAuJoueurSuivant()) {
                // Si plus de joueurs, on passe au croupier
                tourCroupier();
                return;
            }
        }
    }
    
    /**
     * Réinitialise la partie, en défaussant les cartes et réinitialisant les joueurs et l'état du jeu.
     */
    public void resetPartie() {
        gestionnairePartie.resetPartie();
        fireChangement(); // Notifier la vue qu'il y a eu un changement
    }
    
    /**
     * Passe au joueur suivant.
     * 
     * Cette méthode vérifie si la partie est terminée pour les joueurs et, dans ce cas, passe le tour au croupier.
     */
    public void passerAuJoueurSuivant() {
        boolean finDesJoueurs = gestionnairePartie.passerAuJoueurSuivant();
        
        if (finDesJoueurs) {
            // Plus de joueurs, on passe au croupier
            tourCroupier();
        } else {
            // Vérifier si le nouveau joueur actif a déjà un blackjack
            verifierEtPasserBlackjack();
            fireChangement(); // Notifier la vue qu'il y a eu un changement
        }
    }
    
    /**
     * Gère le tour du croupier.
     * 
     * Cette méthode est appelée lorsque tous les joueurs ont joué leur tour et que c'est au tour du croupier.
     */
    public void tourCroupier() {
        gestionnairePartie.tourCroupier();
        fireChangement(); // Notifier la vue qu'il y a eu un changement
    }
    
    /**
     * Exécute une action pour le joueur actif.
     * 
     * @param action L'action à exécuter pour le joueur actif (par exemple, "tirer", "rester").
     */
    public void executerAction(Action action) {
        gestionnairePartie.executerAction(action);
        fireChangement(); // Notifier la vue qu'il y a eu un changement
    }
    
    // ===== GETTERS =====
    
    /**
     * Retourne la liste des joueurs dans la partie.
     * 
     * @return La liste des joueurs.
     */
    public ArrayList<Joueur> getJoueurs() {
        return this.joueurs;
    }
    
    /**
     * Retourne le croupier.
     * 
     * @return Le croupier de la partie.
     */
    public Croupier getCroupier() {
        return croupier;
    }
    
    /**
     * Retourne la pioche (le paquet de cartes restant).
     * 
     * @return Le paquet de cartes restant.
     */
    public Paquet getPioche() {
        return gestionnairePaquet.getPaquet();
    }
    
    /**
     * Retourne la défausse (les cartes qui ont été jouées ou retirées).
     * 
     * @return La défausse de cartes.
     */
    public Paquet getDefausse() {
        return gestionnairePaquet.getDefausse();
    }
    
    /**
     * Retourne l'état actuel de la partie.
     * 
     * @return L'état actuel de la partie.
     */
    public EtatPartie getEtatPartie() {
        return gestionnairePartie.getEtatPartie();
    }
    
    /**
     * Retourne la mise minimale pour participer à la partie.
     * 
     * @return La mise minimale.
     */
    public int getMiseMinimale() {
        return miseMinimale;
    }
    
    /**
     * Modifie la mise minimale pour participer à la partie.
     * 
     * @param miseMinimale La nouvelle mise minimale.
     */
    public void setMiseMinimale(int miseMinimale) {
        if (miseMinimale > 0) {
            this.miseMinimale = miseMinimale;
        }
    }
    
    /**
     * Retourne le gestionnaire d'actions qui gère les actions disponibles pour les joueurs.
     * 
     * @return Le gestionnaire d'actions.
     */
    public GestionnaireActions getGestionnaireActions() {
        return gestionnaireActions;
    }
    
    /**
     * Retourne la carte visible du croupier.
     * 
     * @return La carte visible du croupier.
     */
    public Carte getCarteVisibleCroupier() {
        return croupier.getCarteVisible();
    }
    
    /**
     * Retourne le nombre total de joueurs dans la partie.
     * 
     * @return Le nombre de joueurs.
     */
    public int getNombreJoueurs() {
        return joueurs.size();
    }
    
    /**
     * Retourne les actions disponibles pour un joueur donné.
     * 
     * @param joueur Le joueur pour lequel on veut connaître les actions disponibles.
     * @return La liste des actions possibles pour ce joueur.
     */
    public List<Action> getActionsDisponibles(Joueur joueur) {
        return gestionnaireActions.obtenirActionsExecutables(joueur);
    }
    
    /**
     * Retourne une chaîne de caractères représentant l'état actuel du modèle.
     * 
     * @return Une chaîne qui décrit l'état du modèle.
     */
    @Override
    public String toString() {
        return "ModeleBlackjack [État: " + gestionnairePartie.getEtatPartie() + 
               ", Joueurs: " + joueurs.size() + "]";
    }
}
