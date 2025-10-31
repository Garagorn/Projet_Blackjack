package blackjack.vue;

import modele.cartes.Paquet;
import vue.VuePaquetLisible;


public class VueMainJoueur extends VuePaquetLisible{
    
    public VueMainJoueur(Paquet p, boolean estDefausse, String titre) {
        super(p, estDefausse, titre);
    }
    
}