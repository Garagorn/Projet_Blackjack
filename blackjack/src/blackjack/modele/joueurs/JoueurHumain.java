package blackjack.modele.joueurs;

/**
 * Représente un joueur humain au Blackjack.
 */
public class JoueurHumain extends Joueur {
    
    public JoueurHumain(String nom, int jetonsInitiaux) {
        super(nom, jetonsInitiaux);
    }
    
    @Override
    public boolean estHumain() {
        return true;
    }
    
    @Override
    public String toString() {
        return "[HUMAIN] " + super.toString();
    }
}