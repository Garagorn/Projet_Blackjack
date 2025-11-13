package controleur;

import modele.cartes.*;
import vue.VuePaquetCache;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Contrôleur qui gère l'interaction entre une vue de pioche (vue non lisible)
 * et un paquet de destination.
 * 
 * Lorsqu'on clique sur la vue représentant la pioche, le contrôleur retire la carte
 * du dessus de la pioche et l'ajoute au paquet destination.
 * 
 * Ce contrôleur illustre le pattern écouteur d'événements (EventListener) en capturant
 * les clics de souris sur la vue de la pioche.
 * 
 * Usage typique : tirer une carte de la pioche vers la main du joueur.
 * 
 * @author siaghi231
 */
public class ControleurPiocheVersPaquet {

    /**
     * Crée un contrôleur qui écoute les clics sur la vue de la pioche
     * et transfère la carte du dessus de la pioche vers le paquet destination.
     * 
     * @param vuePioche La vue représentant la pioche (vue non lisible)
     * @param destination Le paquet destination (par exemple la main du joueur)
     */
    public ControleurPiocheVersPaquet(VuePaquetCache vuePioche, Paquet destination) {
        vuePioche.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Paquet pioche = vuePioche.getPaquet();
                if (!pioche.getPaquet().isEmpty()) {
                    Carte carte = pioche.getPaquet().get(0);
                    pioche.retirer(carte);
                    destination.ajouter(carte);
                }
            }
        });
    }
}
