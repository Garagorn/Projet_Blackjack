package blackjack.modele.joueurs;

import modele.cartes.Carte;
import java.util.ArrayList;

/**
 * Représente un joueur dans une partie de Blackjack.
 * 
 * Un joueur peut avoir plusieurs mains, gérer son nombre de jetons, 
 * et suivre son bilan pour une session ou une partie. Il peut effectuer
 * des actions comme démarrer une nouvelle main, miser des jetons, et
 * gérer son score.
 */
public abstract class Joueur {
    
    /**
     *le nom du joueur
     */
    protected String nom;               // Nom du joueur

    /**
     *les jetons initiaux du joeur
     */
    protected int jetons;               // Nombre de jetons du joueur

    /**
     *les (la) main(s) du joueur
     */
    protected ArrayList<Main> mains;    // Liste des mains du joueur

    /**
     *l'indice du la main du joueur (très util pour passer à la deuxième main en cas split)
     */
    protected int mainActuelleIndex;    // Index de la main active du joueur

    /**
     *le gain total dans toute les manches jouées
     */
    protected int gainsSession;         // Total des gains du joueur dans la session

    /**
     *les pertes cumulées dans toute les manches du jeu
     */
    protected int pertesSession;        // Total des pertes du joueur dans la session

    /**
     *le gain ou la perte dans une seule manche
     */
    protected int bilanPartie;          // Bilan financier pour la partie en cours

    /**
     * Constructeur d'un joueur.
     * 
     * @param nom Le nom du joueur
     * @param jetonsInitiaux Le nombre initial de jetons
     */
    public Joueur(String nom, int jetonsInitiaux) {
        this.nom = nom;
        this.jetons = jetonsInitiaux;
        this.mains = new ArrayList<>();
        this.mainActuelleIndex = 0;
        this.gainsSession = 0;
        this.pertesSession = 0;
        this.bilanPartie = 0;
    }
    
    /**
     * Démarre une nouvelle main pour le joueur avec une mise spécifiée.
     * 
     * Cette méthode crée une nouvelle main pour le joueur, ajoute la mise,
     * et débite les jetons correspondants.
     * 
     * @param mise La mise pour la nouvelle main
     */
    public void demarrerNouvelleMain(int mise) {
        Main main = new Main(mise);
        this.mains.add(main);
        this.mainActuelleIndex = mains.size() - 1;
        debiterJetons(mise);
    }
    
    /**
     * Ajoute une main supplémentaire au joueur.
     * 
     * @param nouvelleMain La nouvelle main à ajouter
     */
    public void ajouterMain(Main nouvelleMain){
        this.mains.add(nouvelleMain);
    }
    
    /**
     * Ajoute une carte à la main active du joueur.
     * 
     * @param carte La carte à ajouter
     */
    public void ajouterCarte(Carte carte) {
        Main mainActuelle = getMainActuelle();
        if (mainActuelle != null) {
            mainActuelle.ajouterCarte(carte);
        }
    }
    
    /**
     * Passe à la main suivante du joueur.
     * 
     * Si le joueur a plusieurs mains, cette méthode permet de passer à la
     * main suivante.
     */
    public void passerAMainSuivante() {
        if (mainActuelleIndex < mains.size() - 1) {
            this.mainActuelleIndex++;
        }
    }
    
    /**
     * Vérifie si le joueur a une deuxième main.
     * 
     * @return true si le joueur a plus d'une main, sinon false
     */
    public boolean aUneDeuxiemeMain() {
        return mains.size() > 1 && mainActuelleIndex < mains.size() - 1;
    }
    
    /**
     * Débiter des jetons pour une mise.
     * 
     * Cette méthode réduit le nombre de jetons du joueur et met à jour
     * ses pertes et son bilan de la partie en cours.
     * 
     * @param montant Le montant à débiter
     */
    public void debiterJetons(int montant) {
        jetons -= montant;
        pertesSession += montant;
        this.bilanPartie -= montant;
    }
    
    /**
     * Crédite des jetons au joueur en fonction de ses gains.
     * 
     * Cette méthode augmente le nombre de jetons du joueur et met à jour
     * ses gains et son bilan de la partie en cours.
     * 
     * @param montant Le montant à créditer
     */
    public void crediterJetons(int montant) {
        jetons += montant;
        gainsSession += montant;
        this.bilanPartie += montant;
    }
    
    /**
     * Calcule et renvoie le score de la main active du joueur.
     * 
     * @return Le score de la main active, ou 0 si aucune main n'est active
     */
    public int getScore() {
        Main mainActuelle = getMainActuelle();
        return mainActuelle != null ? mainActuelle.getScore() : 0;
    }
    
    /**
     * Vérifie si le joueur a assez de jetons pour effectuer une mise.
     * 
     * @param montant Le montant à miser
     * @return true si le joueur peut miser, false sinon
     */
    public boolean peutMiser(int montant) {
        return jetons >= montant;
    }
    
    /**
     * Réinitialise les mains du joueur à la fin de la partie.
     */
    public void reinitialiserMains() {
        mains.clear();
        mainActuelleIndex = 0;
    }
    
    /**
     * Réinitialise le bilan financier de la partie en cours.
     */
    public void reinitialiserBilanPartie(){
        this.bilanPartie = 0;
    }
    
    /**
     * Réinitialise les gains et pertes de la session en cours.
     */
    public void reinitialiserSession() {
        gainsSession = 0;
        pertesSession = 0;
    }
    
    // ========== GETTERS & SETTERS ==========
    
    /**
     * Renvoie le bilan financier de la partie en cours.
     * 
     * @return Le bilan financier de la partie
     */
    public int getBilanPartie(){
        return this.bilanPartie;
    }
    
    /**
     * Renvoie le nom du joueur.
     * 
     * @return Le nom du joueur
     */
    public String getNom() {
        return nom;
    }
    
    /**
     * Définit le nom du joueur.
     * 
     * @param nom Le nouveau nom du joueur
     */
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    /**
     * Renvoie le nombre de jetons du joueur.
     * 
     * @return Le nombre de jetons du joueur
     */
    public int getJetons() {
        return jetons;
    }
    
    /**
     * Définit le nombre de jetons du joueur.
     * 
     * @param jetons Le nombre de jetons à définir
     * @throws IllegalArgumentException Si le nombre de jetons est négatif
     */
    public void setJetons(int jetons) {
        if (jetons < 0) {
            throw new IllegalArgumentException("Jetons négatifs");
        }
        this.jetons = jetons;
    }
    
    /**
     * Renvoie la liste des mains du joueur.
     * 
     * @return La liste des mains du joueur
     */
    public ArrayList<Main> getMains() {
        return this.mains;
    }
    
    /**
     * Renvoie la main active du joueur.
     * 
     * @return La main active du joueur, ou null si aucune main n'est active
     */
    public Main getMainActuelle() {
        if (mains.isEmpty() || mainActuelleIndex >= mains.size()) {
            return null;
        }
        return mains.get(mainActuelleIndex);
    }
    
    /**
     * Renvoie l'index de la main active.
     * 
     * @return L'index de la main active
     */
    public int getMainActuelleIndex() {
        return mainActuelleIndex;
    }
    
    /**
     * Renvoie le nombre total de mains du joueur.
     * 
     * @return Le nombre total de mains
     */
    public int getNombreMains() {
        return mains.size();
    }
    
    /**
     * Renvoie les gains du joueur pour la session en cours.
     * 
     * @return Les gains de la session
     */
    public int getGainsSession() {
        return gainsSession;
    }
    
    /**
     * Renvoie les pertes du joueur pour la session en cours.
     * 
     * @return Les pertes de la session
     */
    public int getPertesSession() {
        return pertesSession;
    }
    
    /**
     * Renvoie la mise actuelle du joueur pour la main active.
     * 
     * @return La mise de la main active
     */
    public int getMiseActuelle() {
        Main mainActuelle = getMainActuelle();
        return mainActuelle != null ? mainActuelle.getMise() : 0;
    }
   
    /**
     * Renvoie le bilan financier pour la session en cours (gains - pertes).
     * 
     * @return Le bilan de la session
     */
    public int getBilanSession() {
        return gainsSession - pertesSession;
    }
    
    /**
     * Vérifie si le joueur a un blackjack dans sa main active.
     * 
     * @return true si le joueur a un blackjack, false sinon
     */
    public boolean aBlackjack() {
        Main mainActuelle = getMainActuelle();
        return mainActuelle != null && mainActuelle.estBlackjack();
    }
    
    /**
     * Vérifie si le joueur est buste (si la somme de ses cartes dépasse 21).
     * 
     * @return true si le joueur est buste, false sinon
     */
    public boolean estBuste() {
        Main mainActuelle = getMainActuelle();
        return mainActuelle != null && mainActuelle.estBuste();
    }
    
    /**
     * Méthode abstraite permettant de déterminer si le joueur est humain ou non.
     * 
     * @return true si le joueur est humain, false si c'est un joueur virtuel (par exemple, un croupier)
     */
    public abstract boolean estHumain();
    
    /**
     * Renvoie une représentation sous forme de chaîne de caractères du joueur.
     * 
     * @return La chaîne représentant le joueur
     */
    @Override
    public String toString() {
        return nom + " [Jetons: " + jetons + ", Bilan: " + getBilanSession() + "]";
    }
    
    /**
     * Vérifie si deux joueurs sont égaux.
     * 
     * @param obj L'objet à comparer
     * @return true si les deux joueurs sont égaux, false sinon
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Joueur joueur = (Joueur) obj;
        return nom.equals(joueur.nom);
    }
    
    /**
     * Renvoie le code de hachage du joueur basé sur son nom.
     * 
     * @return Le code de hachage du joueur
     */
    @Override
    public int hashCode() {
        return nom.hashCode();
    }
}
