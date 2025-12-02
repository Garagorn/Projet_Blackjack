package blackjack.modele.util;

/**
 * Énumération représentant les différents états d'une partie de Blackjack.
 * Permet de contrôler le flux du jeu et de déterminer quelles actions sont possibles.
 */
public enum EtatPartie {
    /**
     * En attente du début de la partie.
     * Les joueurs peuvent placer leurs mises.
     */
    EN_ATTENTE("En attente"),
    
    /**
     * Distribution des cartes initiales en cours.
     * 2 cartes pour chaque joueur et le croupier.
     */
    DISTRIBUTION("Distribution"),
    
    /**
     * Tour des joueurs.
     * Chaque joueur joue à tour de rôle (tirer, rester, doubler, split, etc.)
     */
    TOUR_JOUEURS("Tour des joueurs"),
    
    /**
     * Tour du croupier.
     * Le croupier révèle sa carte cachée et tire selon les règles.
     */
    TOUR_CROUPIER("Tour du croupier"),
    
    /**
     * Partie terminée.
     * Calcul des gains et pertes, affichage des résultats.
     */
    TERMINE("Terminé");
    
    private final String libelle;
    
    /**
     * Constructeur privé de l'énumération
     * @param libelle le libellé descriptif de l'état
     */
    private EtatPartie(String libelle) {
        this.libelle = libelle;
    }
    
    /**
     * Retourne le libellé descriptif de l'état
     * @return le libellé
     */
    public String getLibelle() {
        return libelle;
    }
    
    /**
     * Vérifie si la partie est en cours (ni en attente, ni terminée)
     * @return true si la partie est en cours
     */
    public boolean estEnCours() {
        return this == DISTRIBUTION || this == TOUR_JOUEURS || this == TOUR_CROUPIER;
    }
    
    /**
     * Vérifie si les joueurs peuvent agir dans cet état
     * @return true si les joueurs peuvent jouer
     */
    public boolean joueursActifs() {
        return this == TOUR_JOUEURS;
    }
    
    @Override
    public String toString() {
        return libelle;
    }
}