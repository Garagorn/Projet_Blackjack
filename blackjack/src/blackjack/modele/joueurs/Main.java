package blackjack.modele.joueurs;

import modele.cartes.Carte;
import modele.cartes.Paquet;
import java.util.List;

/**
 * Représente une main de cartes au Blackjack.
 */
public class Main {
    private Paquet main;
    private int mise;
    private int score;
    private boolean estSplit;
    private boolean estAssure;
    private boolean estDoublee;
    
    public Main() {
        this.main = new Paquet();
        this.mise = 0;
        this.score = 0;
        this.estSplit = false;
        this.estAssure = false;
        this.estDoublee = false;
    }
    
    public Main(int mise) {
        this();
        this.mise = mise;
    }
    
    public void ajouterCarte(Carte carte) {
        if (carte != null) {
            main.ajouter(carte);
            this.score = calculerScore();
        }
    }
    
    public void retirerCarte(Carte carte) {
        if (carte != null) {
            main.retirer(carte);
            this.score = calculerScore();
        }
    }
    
    public int calculerScore() {
        int scoreCalcul = 0;
        int nombreAs = 0;
        
        for (Carte carte : this.main.getPaquet()) {
            switch (carte.hauteur) {
                case "As":
                    nombreAs++;
                    scoreCalcul += 11;
                    break;
                case "Valet":
                case "Dame":
                case "Roi":
                    scoreCalcul += 10;
                    break;
                default:
                    try {
                        scoreCalcul += Integer.parseInt(carte.hauteur);
                    } catch (NumberFormatException e) {
                        scoreCalcul += 0;
                    }
            }
        }
        
        // Ajuster les As si nécessaire
        while (scoreCalcul > 21 && nombreAs > 0) {
            scoreCalcul -= 10;
            nombreAs--;
        }

        return scoreCalcul;
    }
    
    public int getScore() {
        return score;
    }
    
    public boolean estBlackjack() {
        List<Carte> cartes = this.main.getPaquet();
        if (cartes.size() != 2) {
            return false;
        }

        Carte carte1 = cartes.get(0);
        Carte carte2 = cartes.get(1);
        return (carte1.hauteur.equals("As") && estBuche(carte2)) ||
               (carte2.hauteur.equals("As") && estBuche(carte1));
    }
    
    private boolean estBuche(Carte carte) {
        return carte.hauteur.equals("10") || 
               carte.hauteur.equals("Valet") ||
               carte.hauteur.equals("Dame") || 
               carte.hauteur.equals("Roi");
    }
    
    public boolean estBuste() {
        return this.score > 21;
    }
    
    public boolean peutDoubler() {
        if (getNombreCartes() != 2 || estDoublee) {
            return false;
        }
        return score >= 9 && score <= 11;
    }
    
    public boolean peutSplit() {
        if (getNombreCartes() != 2 || estSplit) {
            return false;
        }
        
        List<Carte> cartes = this.main.getPaquet();
        Carte c1 = cartes.get(0);
        Carte c2 = cartes.get(1);
        
        // Deux cartes de même valeur
        return getValeurCartePourSplit(c1) == getValeurCartePourSplit(c2);
    }
    
    private int getValeurCartePourSplit(Carte carte) {
        switch (carte.hauteur) {
            case "As": return 1;
            case "Valet": case "Dame": case "Roi": return 10;
            default: 
                try {
                    return Integer.parseInt(carte.hauteur);
                } catch (NumberFormatException e) {
                    return 0;
                }
        }
    }
    
    public Main split() {
        if (!peutSplit()) {
            throw new IllegalStateException("Impossible de splitter cette main");
        }
        
        Main nouvelleMain = new Main(this.mise);
        
        // Retirer la deuxième carte pour la nouvelle main
        Carte carteRetiree = this.main.getPaquet().get(1);
        nouvelleMain.ajouterCarte(carteRetiree);
        this.retirerCarte(carteRetiree);
        
        this.estSplit = true;
        nouvelleMain.estSplit = true;
        
        return nouvelleMain;
    }
    
    public void doubler() {
        if (!peutDoubler()) {
            throw new IllegalStateException("Impossible de doubler cette main");
        }
        this.mise *= 2;
        this.estDoublee = true;
    }
    
    // ========== GETTERS & SETTERS ==========
    
    public Paquet getPaquetMain() {
        return main;
    }
    
    public int getMise() {
        return mise;
    }
    
    public void setMise(int mise) {
        if (mise < 0) {
            throw new IllegalArgumentException("La mise ne peut pas être négative");
        }
        this.mise = mise;
    }
    
    public boolean estSplit() {
        return estSplit;
    }
    
    public boolean estAssure() {
        return estAssure;
    }
    
    public void setAssure(boolean assure) {
        this.estAssure = assure;
    }
    
    public boolean estDoublee() {
        return estDoublee;
    }
    
    public int getNombreCartes() {
        return main.getPaquet().size();
    }
    
    public boolean estVide() {
        return main.estVide();
    }
    
    public void reinitialiser() {
        this.main = new Paquet();
        this.score = 0;
        this.mise = 0;
        this.estSplit = false;
        this.estAssure = false;
        this.estDoublee = false;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Main [Score: ").append(score);
        sb.append(", Cartes: ").append(getNombreCartes());
        sb.append(", Mise: ").append(mise);
        if (estBlackjack()) sb.append(", BLACKJACK!");
        if (estBuste()) sb.append(", BRÛLÉ!");
        if (estSplit()) sb.append(", SPLIT");
        if (estDoublee()) sb.append(", DOUBLÉ");
        sb.append("]");
        return sb.toString();
    }
}