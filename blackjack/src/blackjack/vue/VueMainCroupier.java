package blackjack.vue;

import modele.cartes.Paquet;
import vue.VuePaquetLisible;


public class VueMainCroupier extends VuePaquetLisible{
    
    public VueMainCroupier(Paquet p, boolean estDefausse, String titre) {
        super(p, estDefausse, titre);
    }
    
}