package blackjack.modele.joueurs;

import modele.cartes.Carte;
import java.util.ArrayList;

public abstract class Joueur {
    
    protected String nom;
    protected int jetons;
    protected ArrayList<Main> mains;
    protected int mainActuelleIndex;
    protected int gainsSession;
    protected int pertesSession;

    public Joueur(String nom, int jetonsInitiaux) {
        this.nom = nom;
        this.jetons = jetonsInitiaux;
        this.mains = new ArrayList<>();
        this.mainActuelleIndex = 0;
        this.gainsSession = 0;
        this.pertesSession = 0;
    }
    
    /**
     * Démarre une nouvelle main avec une mise.
     * @param mise la mise pour cette main
     */
    public void demarrerNouvelleMain(int mise) {
        Main main = new Main(mise);
        this.mains.add(main);
        this.mainActuelleIndex = mains.size() - 1;
        debiterJetons(mise);
    }
    public void ajouterMain(Main nouvelleMain){
        this.mains.add(nouvelleMain);
               
    }
    
    public void ajouterCarte(Carte carte) {
        Main mainActuelle = getMainActuelle();
        if (mainActuelle != null) {
            mainActuelle.ajouterCarte(carte);
        }
    }
    
    public void passerAMainSuivante() {
        if (mainActuelleIndex < mains.size() - 1) {
            this.mainActuelleIndex++;
        }
    }
    
    public boolean aUneDeuxiemeMain() {
        return mains.size() > 1 && mainActuelleIndex < mains.size() - 1;
    }
    
    public void debiterJetons(int montant) {
        jetons -= montant;
        pertesSession += montant;
    }
    
    public void crediterJetons(int montant) {
        jetons += montant;
        gainsSession += montant;
    }
    
    public int getScore() {
        Main mainActuelle = getMainActuelle();
        return mainActuelle != null ? mainActuelle.getScore() : 0;
    }
    
    public boolean peutMiser(int montant) {
        return jetons >= montant;
    }
    
    public void reinitialiserMains() {
        mains.clear();
        mainActuelleIndex = 0;
    }
    
    public void reinitialiserSession() {
        gainsSession = 0;
        pertesSession = 0;
    }
    
    // ========== GETTERS & SETTERS ==========
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public int getJetons() {
        return jetons;
    }
    
    public void setJetons(int jetons) {
        if (jetons < 0) {
            throw new IllegalArgumentException("Jetons négatifs");
        }
        this.jetons = jetons;
    }
    
    public ArrayList<Main> getMains() {
        return this.mains;
    }
    
    public Main getMainActuelle() {
        if (mains.isEmpty() || mainActuelleIndex >= mains.size()) {
            return null;
        }
        return mains.get(mainActuelleIndex);
    }
    
    public int getMainActuelleIndex() {
        return mainActuelleIndex;
    }
    
    public int getNombreMains() {
        return mains.size();
    }
    
    public int getGainsSession() {
        return gainsSession;
    }
    
    public int getPertesSession() {
        return pertesSession;
    }
    
    public int getMiseActuelle() {
        Main mainActuelle = getMainActuelle();
        return mainActuelle != null ? mainActuelle.getMise() : 0;
    }
   
    public int getBilanSession() {
        return gainsSession - pertesSession;
    }
    
    public boolean aBlackjack() {
        Main mainActuelle = getMainActuelle();
        return mainActuelle != null && mainActuelle.estBlackjack();
    }
    
    public boolean estBuste() {
        Main mainActuelle = getMainActuelle();
        return mainActuelle != null && mainActuelle.estBuste();
    }
    
    public abstract boolean estHumain();
    
    @Override
    public String toString() {
        return nom + " [Jetons: " + jetons + ", Bilan: " + getBilanSession() + "]";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Joueur joueur = (Joueur) obj;
        return nom.equals(joueur.nom);
    }
    
    @Override
    public int hashCode() {
        return nom.hashCode();
    }
}