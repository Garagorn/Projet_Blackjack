package blackjack.modele.actions;

import blackjack.modele.joueurs.Croupier;
import blackjack.modele.joueurs.Joueur;
import blackjack.modele.joueurs.Main;
import modele.cartes.Carte;
import modele.cartes.Paquet;

/**
 * Action permettant au joueur de prendre une assurance (Insurance).
 * Cette action est possible uniquement lorsque le croupier montre un As.
 * Le joueur parie la moitié de sa mise initiale que le croupier a un Blackjack.
 * Si le croupier a un Blackjack, l'assurance paie 2:1.
 * 
 * Conditions pour pouvoir exécuter l'action :
 * - Le joueur doit avoir exactement 2 cartes.
 * - Le croupier doit montrer un As comme carte visible.
 * - Le joueur ne doit pas déjà avoir pris une assurance.
 * - Le joueur doit avoir suffisamment de jetons pour parier la moitié de sa mise.
 * 
 * @see Action
 */
public class ActionAssurance extends Action {
    
    // Référence au croupier pour obtenir la carte visible du croupier
    private Croupier croupier;
    
    /**
     * Constructeur de l'action Assurance.
     * Initialise l'action avec le paquet et le croupier.
     * 
     * @param pioche le paquet de cartes à utiliser
     * @param croupier le croupier, nécessaire pour vérifier si son As est visible
     */
    public ActionAssurance(Paquet pioche, Croupier croupier) {
        super(pioche);
        this.croupier = croupier;
    }
    
    /**
     * Exécute l'action d'assurance pour le joueur.
     * Si l'action peut être exécutée (cf. `peutExecuter`), le joueur parie la moitié de sa mise
     * sur le fait que le croupier a un Blackjack. L'argent est débité de son compte de jetons.
     * 
     * @param joueur le joueur qui exécute l'action
     */
    @Override
    public void executer(Joueur joueur) {
        if (peutExecuter(joueur)) {
            Main mainActuelle = joueur.getMainActuelle();
            
            // Calcul du coût de l'assurance (moitié de la mise)
            int coutAssurance = mainActuelle.getMise() / 2;

            // Débiter le joueur de l'assurance
            joueur.debiterJetons(coutAssurance);

            // Marquer la main du joueur comme assurée
            mainActuelle.setAssure(true);
        }
    }
    
    /**
     * Vérifie si l'action d'assurance peut être exécutée par le joueur.
     * L'action peut être exécutée si :
     * - Le joueur n'a pas encore pris d'assurance.
     * - Le joueur a exactement deux cartes.
     * - Le croupier a un As comme carte visible.
     * - Le joueur a suffisamment de jetons pour parier la moitié de sa mise.
     * 
     * @param joueur le joueur qui tente de réaliser l'action
     * @return true si l'action peut être exécutée, sinon false
     */
    @Override
    public boolean peutExecuter(Joueur joueur) {
        if (joueur == null) {
            return false;
        }
        
        Main mainActuelle = joueur.getMainActuelle();
        
        // Vérifie si la main est vide, si l'assurance a déjà été prise, ou si le nombre de cartes n'est pas 2
        if (mainActuelle.getPaquetMain().estVide() || mainActuelle.estAssure() || (mainActuelle.getNombreCartes() != 2)) {
            return false;
        }
        
        // Vérifie si la carte visible du croupier est un As
        Carte carteVisibleCroupier = croupier.getCarteVisible();
        if (carteVisibleCroupier == null || !carteVisibleCroupier.hauteur.equals("As")) {
            return false;
        }
        
        // Vérifie si le joueur a suffisamment de jetons pour l'assurance
        int coutAssurance = mainActuelle.getMise() / 2;
        if (joueur.getJetons() < coutAssurance) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Retourne le nom de l'action (assurance).
     * 
     * @return le nom de l'action : "Assurance"
     */
    @Override
    public String getNom() {
        return "Assurance";
    }
    
    /**
     * Retourne une description de l'action.
     * Cette description est utilisée pour fournir plus d'informations à l'utilisateur.
     * 
     * @return une description de l'action d'assurance
     */
    @Override
    public String getDescription() {
        return "Prendre une assurance contre le Blackjack du croupier";
    }
}
