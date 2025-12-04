package blackjack.modele.actions;

import blackjack.modele.joueurs.Joueur;
import blackjack.modele.joueurs.Main;
import modele.cartes.Carte;
import modele.cartes.Paquet;

/**
 * Action permettant au joueur de séparer sa main en deux (Split).
 * 
 * Cette action est possible uniquement lorsque le joueur a deux cartes de même valeur.
 * Elle crée deux mains indépendantes avec la même mise pour chacune.
 * 
 * @see Action
 */
public class ActionSplit extends Action {
    
    /**
     * Constructeur de l'action "Séparer".
     * Initialise l'action avec le paquet de cartes à utiliser.
     * 
     * @param pioche le paquet de cartes à utiliser dans le jeu
     */
    public ActionSplit(Paquet pioche) {
        super(pioche);
    }
    
    /**
     * Exécute l'action "Séparer" pour le joueur.
     * Si le joueur peut exécuter cette action, sa main est séparée en deux,
     * une carte est ajoutée à chaque nouvelle main, et la mise est doublée.
     * 
     * @param joueur le joueur qui choisit de séparer sa main
     */
    @Override
    public void executer(Joueur joueur) {
        if (peutExecuter(joueur)) {
             
            Main mainActuelle = joueur.getMainActuelle();
            Main nouvelleMain = mainActuelle.split();

            // Ajouter la nouvelle main au joueur
            joueur.ajouterMain(nouvelleMain);

            // Débiter le joueur de la mise supplémentaire
            joueur.debiterJetons(nouvelleMain.getMise());

            // Distribuer une carte à chaque main
            Carte carte1 = pioche.getPaquet().get(0);
            Carte carte2 = pioche.getPaquet().get(1);

            if (carte1 != null) {
                mainActuelle.ajouterCarte(carte1);
                pioche.retirer(carte1);
            }

            if (carte2 != null) {
                nouvelleMain.ajouterCarte(carte2);
                pioche.retirer(carte2);
            }
        }
    }
    
    /**
     * Vérifie si l'action "Séparer" peut être exécutée par le joueur.
     * L'action peut être effectuée si :
     * - Le joueur a une main valide avec deux cartes de même valeur
     * - Le joueur dispose de suffisamment de jetons pour doubler sa mise
     * - Le paquet contient suffisamment de cartes pour distribuer à chaque main
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
        
        if (mainActuelle.estVide() || !mainActuelle.peutSplit() || pioche.estVide()) {
            return false;
        }
        
        // Vérifier si le joueur a assez de jetons pour doubler sa mise
        int miseActuelle = mainActuelle.getMise();
        if (joueur.getJetons() < miseActuelle) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Retourne le nom de l'action, utilisé pour l'affichage dans l'interface utilisateur.
     * 
     * @return le nom de l'action : "Séparer"
     */
    @Override
    public String getNom() {
        return "Séparer";
    }
    
    /**
     * Retourne une description de l'action, utilisée pour fournir plus d'informations à l'utilisateur.
     * 
     * @return une description de l'action : "Séparer la main en deux mains indépendantes"
     */
    @Override
    public String getDescription() {
        return "Séparer la main en deux mains indépendantes";
    }
}
