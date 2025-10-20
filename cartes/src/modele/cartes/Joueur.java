package modele.cartes;

/**
 * Interface représentant un joueur dans le jeu de cartes.
 * 
 * Un joueur a un nom, un score, une main de cartes, et peut recevoir des cartes.
 * Cette interface définit les comportements essentiels que doit implémenter
 * toute classe représentant un joueur.
 * 
 * @author tellier212
 */
public interface Joueur {

    /**
     * Obtient le nom du joueur.
     * 
     * @return Le nom du joueur sous forme de chaîne de caractères.
     */
    public String getNom();

    /**
     * Obtient le score actuel du joueur.
     * 
     * @return Le score du joueur sous forme d'entier.
     */
    public int getScore();

    /**
     * Obtient la main du joueur, c'est-à-dire le paquet de cartes qu'il possède.
     * 
     * @return Le paquet représentant la main du joueur.
     */
    public Paquet getMain();

    /**
     * Reçoit une carte et l'ajoute à la main du joueur.
     * 
     * @param c La carte à recevoir.
     */
    public void recevoirCarte(Carte c);

    // public Carte jouerCarte(); // Méthode éventuellement pour jouer une carte (non implémentées)
}
