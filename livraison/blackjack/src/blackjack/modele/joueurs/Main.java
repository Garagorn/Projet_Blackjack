package blackjack.modele.joueurs;

import modele.cartes.Carte;
import modele.cartes.Paquet;
import java.util.List;

/**
 * Représente une main de cartes au Blackjack.
 * 
 * Une main est constituée d'un paquet de cartes et possède des caractéristiques 
 * telles que la mise, le score, et les états spéciaux comme "split" (séparation de la main)
 * et "double" (doublage de la mise). La main est utilisée par les joueurs pour 
 * effectuer des actions pendant leur tour, comme doubler, splitter, ou tirer des cartes.
 */
public class Main {
    private Paquet main;          // Paquet de cartes dans la main
    private int mise;             // Mise associée à cette main
    private int score;            // Score total de la main
    private boolean estSplit;     // Indique si la main a été splittée
    private boolean estAssure;    // Indique si la main est assurée
    private boolean estDoublee;   // Indique si la main est doublée
    
    /**
     * Constructeur par défaut d'une main.
     * Initialise un paquet vide et les autres attributs.
     */
    public Main() {
        this.main = new Paquet();
        this.mise = 0;
        this.score = 0;
        this.estSplit = false;
        this.estAssure = false;
        this.estDoublee = false;
    }
    
    /**
     * Constructeur d'une main avec une mise initiale.
     * 
     * @param mise La mise pour cette main
     */
    public Main(int mise) {
        this();
        this.mise = mise;
    }
    
    /**
     * Ajoute une carte à la main.
     * 
     * Cette méthode met également à jour le score de la main après l'ajout de la carte.
     * 
     * @param carte La carte à ajouter à la main
     */
    public void ajouterCarte(Carte carte) {
        if (carte != null) {
            main.ajouter(carte);
            this.score = calculerScore();
        }
    }
    
    /**
     * Retire une carte de la main.
     * 
     * Cette méthode met également à jour le score de la main après le retrait de la carte.
     * 
     * @param carte La carte à retirer de la main
     */
    public void retirerCarte(Carte carte) {
        if (carte != null) {
            main.retirer(carte);
            this.score = calculerScore();
        }
    }
    
    /**
     * Calcule le score total de la main.
     * 
     * Le score est calculé en fonction de la valeur des cartes dans la main,
     * avec des ajustements pour les As (qui valent 1 ou 11 selon le total de la main).
     * 
     * @return Le score total de la main
     */
    public int calculerScore() {
        int scoreCalcul = 0;
        int nombreAs = 0;
        
        // Parcours de toutes les cartes pour calculer le score
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
        
        // Ajuster les As si nécessaire pour éviter un score supérieur à 21
        while (scoreCalcul > 21 && nombreAs > 0) {
            scoreCalcul -= 10;
            nombreAs--;
        }

        return scoreCalcul;
    }
    
    /**
     * Vérifie si la main est un Blackjack.
     * 
     * Un Blackjack est une main composée d'un As et d'une carte de valeur 10
     * (10, Valet, Dame ou Roi).
     * 
     * @return true si la main est un Blackjack, false sinon
     */
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
    
    /**
     * Vérifie si une carte est une carte de valeur 10 (10, Valet, Dame, Roi).
     * 
     * @param carte La carte à vérifier
     * @return true si la carte a une valeur de 10, false sinon
     */
    private boolean estBuche(Carte carte) {
        return carte.hauteur.equals("10") || 
               carte.hauteur.equals("Valet") ||
               carte.hauteur.equals("Dame") || 
               carte.hauteur.equals("Roi");
    }
    
    /**
     * Vérifie si la main est buste.
     * 
     * Une main est buste si son score est supérieur à 21.
     * 
     * @return true si la main est buste, false sinon
     */
    public boolean estBuste() {
        return this.score > 21;
    }
    
    /**
     * Vérifie si la main peut être doublée.
     * 
     * Une main peut être doublée si elle a exactement deux cartes et que
     * son score est compris entre 9 et 11, et qu'elle n'a pas déjà été doublée.
     * 
     * @return true si la main peut être doublée, false sinon
     */
    public boolean peutDoubler() {
        if (getNombreCartes() != 2 || estDoublee) {
            return false;
        }
        return score >= 9 && score <= 11;
    }
    
    /**
     * Vérifie si la main peut être splittée.
     * 
     * Une main peut être splittée si elle a exactement deux cartes de même valeur.
     * 
     * @return true si la main peut être splittée, false sinon
     */
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
    
    /**
     * Obtient la valeur d'une carte pour déterminer si elle peut être utilisée dans un split.
     * 
     * @param carte La carte à vérifier
     * @return La valeur numérique de la carte
     */
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
    
    /**
     * Effectue le split de la main, créant une nouvelle main avec la deuxième carte.
     * 
     * @return La nouvelle main obtenue après le split
     * @throws IllegalStateException si le split n'est pas possible
     */
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
    
    /**
     * Double la mise de la main.
     * 
     * La mise est doublée et l'état de la main devient "doublee".
     * 
     * @throws IllegalStateException si la main ne peut pas être doublée
     */
    public void doubler() {
        if (!peutDoubler()) {
            throw new IllegalStateException("Impossible de doubler cette main");
        }
        this.mise *= 2;
        this.estDoublee = true;
    }
    
    // ========== GETTERS & SETTERS ==========
    
     
    /**
     * Renvoie le paquet de cartes de la main.
     * 
     * @return Le paquet de cartes dans la main
     */
    public Paquet getPaquetMain() {
        return main;
    }

    /**
     * Renvoie la mise associée à cette main.
     * 
     * @return La mise de la main
     */
    public int getMise() {
        return mise;
    }
    
    /**
     *un accesseur
     *@return score le score  du joueur
     */
    public int getScore(){
        return this.score;
    }

    /**
     * Définit la mise de la main.
     * 
     * @param mise La mise à définir pour la main
     * @throws IllegalArgumentException Si la mise est négative
     */
    public void setMise(int mise) {
        if (mise < 0) {
            throw new IllegalArgumentException("La mise ne peut pas être négative");
        }
        this.mise = mise;
    }

    /**
     * Vérifie si la main est splittée.
     * 
     * @return true si la main est splittée, false sinon
     */
    public boolean estSplit() {
        return estSplit;
    }

    /**
     * Vérifie si la main est assurée.
     * 
     * @return true si la main est assurée, false sinon
     */
    public boolean estAssure() {
        return estAssure;
    }

    /**
     * Définit l'état d'assurance de la main.
     * 
     * @param assure true si la main doit être assurée, false sinon
     */
    public void setAssure(boolean assure) {
        this.estAssure = assure;
    }

    /**
     * Vérifie si la main est doublée.
     * 
     * @return true si la main est doublée, false sinon
     */
    public boolean estDoublee() {
        return estDoublee;
    }

    /**
     * Renvoie le nombre de cartes dans la main.
     * 
     * @return Le nombre de cartes dans la main
     */
    public int getNombreCartes() {
        return main.getPaquet().size();
    }

    /**
     * Vérifie si la main est vide (ne contient aucune carte).
     * 
     * @return true si la main est vide, false sinon
     */
    public boolean estVide() {
        return main.estVide();
    }

    /**
     * Réinitialise la main, la mettant dans un état initial (vide).
     * 
     * Cette méthode vide la main, réinitialise le score, la mise et les états spéciaux (split, assure, doublée).
     */
    public void reinitialiser() {
        this.main = new Paquet();
        this.score = 0;
        this.mise = 0;
        this.estSplit = false;
        this.estAssure = false;
        this.estDoublee = false;
    }


    /**
     * Renvoie une représentation sous forme de chaîne de caractères de la main.
     * 
     * @return La chaîne représentant la main
     */
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
