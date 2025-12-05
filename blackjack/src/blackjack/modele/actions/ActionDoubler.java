package blackjack.modele.actions;

import blackjack.modele.joueurs.Joueur;
import blackjack.modele.joueurs.Main;
import modele.cartes.Carte;
import modele.cartes.Paquet;

/**
 * Action permettant au joueur de doubler sa mise (Double Down).
 * Lors de l'exécution de cette action, le joueur double sa mise initiale,
 * reçoit exactement UNE carte supplémentaire et passe automatiquement au joueur suivant.
 * 
 * Conditions pour pouvoir exécuter l'action :
 * - Le joueur doit avoir une main non vide.
 * - Le joueur doit pouvoir doubler la mise (c'est-à-dire que le jeu doit permettre un double down à ce moment).
 * - Le joueur doit avoir suffisamment de jetons pour doubler la mise.
 * - Le paquet de cartes ne doit pas être vide.
 * 
 * @see Action
 */
public class ActionDoubler extends Action {
    
    /**
     * Constructeur de l'action Doubler.
     * Initialise l'action avec le paquet de cartes à utiliser.
     * 
     * @param pioche le paquet de cartes à utiliser pour tirer la carte supplémentaire
     */
    public ActionDoubler(Paquet pioche) {
        super(pioche);
    }
    
    /**
     * Exécute l'action de doubler la mise pour le joueur.
     * Si l'action peut être exécutée (cf. `peutExecuter`), le joueur double sa mise,
     * reçoit exactement une carte supplémentaire et passe son tour.
     * 
     * @param joueur le joueur qui exécute l'action
     */
    @Override
    public void executer(Joueur joueur) {
        if (peutExecuter(joueur)) {
            Main mainActuelle = joueur.getMainActuelle();

            // Doubler la mise du joueur
            mainActuelle.doubler();

            // Débiter la moitié de la mise initiale (le joueur double)
            joueur.debiterJetons(mainActuelle.getMise() / 2); 
            
            // Tirer une carte et l'ajouter à la main du joueur
            Carte carte = pioche.getPaquet().get(0);

            if (carte != null) {
                mainActuelle.ajouterCarte(carte);
                pioche.retirer(carte); // Retirer la carte du paquet
            }   
        }
    }
    
    /**
     * Vérifie si l'action "Doubler" peut être exécutée pour un joueur donné.
     * L'action peut être exécutée si :
     * - Le joueur a une main non vide.
     * - La main du joueur peut être doublée (le jeu autorise cette action à ce moment).
     * - Le joueur a suffisamment de jetons pour doubler sa mise.
     * - Le paquet de cartes n'est pas vide.
     * 
     * @param joueur le joueur pour lequel vérifier la possibilité de doubler
     * @return true si l'action peut être exécutée, sinon false
     */
    @Override
    public boolean peutExecuter(Joueur joueur) {
        if (joueur == null) {
            return false;
        }
        
        Main mainActuelle = joueur.getMainActuelle();
        
        // Vérifier que la main n'est pas vide et que le joueur peut doubler
        if (mainActuelle.estVide() || !mainActuelle.peutDoubler()) {
            return false;
        }
        
        int miseActuelle = mainActuelle.getMise();
        // Vérifier que le joueur a assez de jetons pour doubler sa mise
        if (joueur.getJetons() < miseActuelle) {
            return false;
        }
        
        // Vérifier que le paquet de cartes n'est pas vide
        if (pioche.estVide()) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Retourne le nom de l'action (doubler).
     * 
     * @return le nom de l'action : "Doubler"
     */
    @Override
    public String getNom() {
        return "Doubler";
    }
    
    /**
     * Retourne une description de l'action "Doubler".
     * Cette description est utilisée pour fournir plus d'informations à l'utilisateur.
     * 
     * @return une description de l'action : "Doubler la mise et recevoir une seule carte"
     */
    @Override
    public String getDescription() {
        return "Doubler la mise et recevoir une seule carte";
    }
}
