package blackjack.modele.jeu;

import blackjack.modele.joueurs.Croupier;
import blackjack.modele.joueurs.Joueur;
import modele.cartes.Carte;
import modele.cartes.Paquet;

/**
 * Gestionnaire du paquet de cartes (pioche et défausse).
 * 
 * RÔLE :
 * - Distribuer les cartes aux joueurs et au croupier
 * - Gérer la pioche et la défausse
 * - Remélanger quand nécessaire
 * 
 * RÈGLE DE REMÉLANGE :
 * La pioche est remélangée AVANT une distribution si le nombre de cartes
 * restantes est insuffisant pour une manche complète.
 * Formule : pioche.size() inférieure ou égale à (nombre de joueurs * 2) + 2
 * 
 * GESTION DE LA DÉFAUSSE :
 * - Les cartes ne vont PAS dans la défausse pendant la distribution
 * - Les cartes vont dans la défausse UNIQUEMENT à la fin de la partie
 */
public class GestionnairePaquet {
    
    private Paquet paquet;      // La pioche (cartes disponibles)
    private Paquet defausse;    // La défausse (cartes utilisées)
    
    /**
     * Constructeur du gestionnaire.
     * Crée un jeu de 52 cartes et le mélange.
     */
    public GestionnairePaquet() {
        this.paquet = Paquet.creerJeu52();  // Crée un jeu de 52 cartes
        this.defausse = new Paquet();  // Initialise la défausse
        this.paquet.melanger();  // Mélange les cartes de la pioche
    }
    
    /**
     * Distribue une carte à un joueur.
     * 
     * IMPORTANT : La carte est retirée de la pioche mais N'EST PAS
     * ajoutée à la défausse immédiatement. Elle ira dans la défausse
     * à la fin de la partie.
     * 
     * @param joueur Le joueur qui reçoit la carte
     */
    public void distribuerCarteJoueurs(Joueur joueur) {
        // Vérifier s'il faut remélanger AVANT de distribuer
        verifierEtRemelangerSiNecessaire();
        
        // Prendre la première carte de la pioche
        Carte carte = paquet.getPaquet().get(0);
        
        // Ajouter la carte à la main du joueur
        joueur.ajouterCarte(carte);
        
        // Retirer la carte de la pioche
        paquet.retirer(carte);
        
        // NOTE : La carte N'est PAS ajoutée à la défausse maintenant.
        // Elle ira dans la défausse à la fin de la partie.
    }
    
    /**
     * Distribue une carte au croupier.
     * 
     * IMPORTANT : La carte est retirée de la pioche mais N'EST PAS
     * ajoutée à la défausse immédiatement.
     * 
     * @param croupier Le croupier qui reçoit la carte
     */
    public void distribuerCarteCroupier(Croupier croupier) {
        // Vérifier s'il faut remélanger AVANT de distribuer
        verifierEtRemelangerSiNecessaire();
        
        // Prendre la première carte de la pioche
        Carte carte = paquet.getPaquet().get(0);
        
        // Ajouter la carte à la main du croupier
        croupier.ajouterCarte(carte);
        
        // Retirer la carte de la pioche
        paquet.retirer(carte);
        
        // NOTE : La carte N'est PAS ajoutée à la défausse maintenant
    }
    
    /**
     * Vérifie s'il faut remélanger la pioche avant une distribution.
     * 
     * CONDITION DE REMÉLANGE :
     * On remélange si le nombre de cartes restantes est insuffisant
     * pour une manche complète.
     * 
     * POURQUOI CETTE FORMULE ?
     * - Chaque joueur reçoit 2 cartes
     * - Le croupier reçoit 2 cartes
     * - On garde une marge de sécurité pour les actions (Tirer, Split)
     */
    private void verifierEtRemelangerSiNecessaire() {
        // Si la pioche est vide, remélanger immédiatement
        if (paquet.estVide()) {
            remelanger();
        }
    }
    
    /**
     * Détermine s'il faut remélanger en fonction du nombre de joueurs.
     * 
     * Formule : pioche.size() pl us petit que (nombreJoueurs * 2) + 2
     * 
     * @param nombreJoueurs Le nombre de joueurs dans la partie
     * @return true s'il faut remélanger, false sinon
     */
    public boolean doitRemelanger(int nombreJoueurs) {
        int cartesNecessaires = (nombreJoueurs * 2) + 2;
        return paquet.nbr_carte() <= cartesNecessaires;
    }
    
    /**
     * Remélange la pioche en y ajoutant les cartes de la défausse.
     * 
     * PROCESSUS :
     * 1. Ajouter toutes les cartes de la défausse à la pioche
     * 2. Vider la défausse
     * 3. Mélanger la pioche
     */
    public void remelanger() {
        // Ajouter toutes les cartes de la défausse à la pioche
        for (Carte c : this.defausse.getPaquet()) {
            this.paquet.ajouter(c);
        }
        
        // Vider la défausse
        this.defausse.viderPaquet();
        
        // Mélanger la pioche
        this.paquet.melanger();
    }
    
    /**
     * Envoie toutes les cartes d'une main dans la défausse.
     * 
     * APPELÉ À LA FIN DE LA PARTIE pour chaque main de chaque joueur.
     * 
     * @param main Le paquet représentant la main à défausser
     */
    public void defausserMain(Paquet main) {
        for (Carte carte : main.getPaquet()) {
            defausse.ajouter(carte);
        }
    }
    
    /**
     * Getter de la pioche.
     * 
     * @return Le paquet de la pioche
     */
    public Paquet getPaquet() {
        return paquet;
    }
    
    /**
     * Getter de la défausse.
     * 
     * @return Le paquet de la défausse
     */
    public Paquet getDefausse() {
        return this.defausse;
    }
}
