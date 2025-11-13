package controleur;

import modele.cartes.*;
import vue.VuePaquetLisible;
import vue.EcouteurCarteClique;

/**
 * Contrôleur qui gère le transfert d'une carte depuis une vue de paquet lisible 
 * vers un paquet de destination.
 * 
 * Il écoute les clics sur les cartes affichées dans la vue source,
 * retire la carte cliquée du paquet source, puis l'ajoute au paquet destination.
 * 
 * Cette classe illustre le pattern Observateur en capturant l'événement de clic sur une carte
 * via l'interface {@link EcouteurCarteClique}.
 * 
 * Usage typique : transférer une carte de la main du joueur vers la défausse, 
 * ou d'une pioche vers la main, etc.
 * 
 * @author siaghi231
 */
public class ControleurChoixCarteVersPaquet {

    /**
     * Crée un contrôleur qui écoute les clics sur les cartes dans la vue source,
     * et transfère la carte cliquée vers le paquet destination.
     * 
     * @param vueSource La vue contenant le paquet source (vue lisible)
     * @param destination Le paquet destination vers lequel les cartes seront transférées
     */
    public ControleurChoixCarteVersPaquet(VuePaquetLisible vueSource, Paquet destination) {
        vueSource.ajouterEcouteurCarte(new EcouteurCarteClique() {
            @Override
            public void carteCliquee(Carte carte) {
                vueSource.getPaquet().retirer(carte);
                destination.ajouter(carte);
            }
        });
    }
}
