package blackjack.modele.joueurs;

/**
 * Représente un joueur humain au Blackjack.
 * 
 * Cette classe hérite de la classe `Joueur` et permet de définir un joueur
 * humain dans une partie de Blackjack. Le joueur humain peut effectuer
 * des actions basées sur les décisions qu'il prend pendant son tour.
 */
public class JoueurHumain extends Joueur {
    
    /**
     * Constructeur d'un joueur humain.
     * 
     * Crée un joueur humain avec un nom et un nombre de jetons initiaux.
     * 
     * @param nom Le nom du joueur humain
     * @param jetonsInitiaux Le nombre de jetons initial du joueur humain
     */
    public JoueurHumain(String nom, int jetonsInitiaux) {
        super(nom, jetonsInitiaux);
    }
    
    /**
     * Indique que ce joueur est un humain.
     * 
     * @return true, car ce joueur est humain
     */
    @Override
    public boolean estHumain() {
        return true;
    }
    
    /**
     * Renvoie une représentation sous forme de chaîne de caractères du joueur humain.
     * 
     * La chaîne retourne le type du joueur (HUMAIN) ainsi que son solde de jetons et son bilan.
     * 
     * @return La chaîne représentant le joueur humain
     */
    @Override
    public String toString() {
        return "[HUMAIN] " + super.toString();
    }
}
