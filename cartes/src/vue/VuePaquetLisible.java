package vue;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import modele.cartes.Paquet;
import modele.cartes.Carte;
import vue.EcouteurCarteClique;

/**
 * VuePaquetLisible représente la vue graphique d'un paquet de cartes
 * où les cartes sont visibles et interactives (cliquables).
 * 
 * Cette classe étend VuePaquet et gère l'affichage des cartes ainsi que 
 * les événements de clic sur chaque carte.
 * 
 * Elle peut aussi gérer un mode "défausse" où les cartes ne sont pas surlignées 
 * lors du survol par la souris.
 */
public class VuePaquetLisible extends VuePaquet {

    /**
     * Indique si ce paquet est une défausse. Si true, les cartes ne changent
     * pas de couleur au survol et ne sont pas cliquables.
     */
    private boolean estDefausse;

    /**
     * Constructeur de VuePaquetLisible.
     * 
     * @param p Le paquet de cartes à afficher.
     * @param estDefausse Indique si ce paquet est une défausse (true)
     *                    ou un paquet normal (false).
     * @param titre titre du panneau
     */
    public VuePaquetLisible(Paquet p, boolean estDefausse,String titre) {
        super(p);
        this.estDefausse = estDefausse;
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        afficherPaquet();
        TitledBorder bordure = BorderFactory.createTitledBorder(titre);
        this.setBorder(bordure);

       
    }

    /**
     * Mise à jour de la vue en cas de changement du modèle.
     * Cette méthode est appelée quand le modèle (Paquet) signale une modification.
     * 
     * @param source L'objet source qui a déclenché la mise à jour.
     */
    public void modeleMiseAJour(Object source) {
        afficherPaquet();
    }

    /**
     * Liste des écouteurs qui réagissent au clic sur une carte.
     */
    private List<EcouteurCarteClique> ecouteursCarte = new ArrayList<>();

    /**
     * Ajoute un écouteur pour détecter le clic sur une carte.
     * 
     * @param e L'instance d'EcouteurCarteClique à ajouter.
     */
    public void ajouterEcouteurCarte(EcouteurCarteClique e) {
        ecouteursCarte.add(e);
    }

    /**
     * Notifie tous les écouteurs qu'une carte a été cliquée.
     * 
     * @param carte La carte qui a été cliquée.
     */
    private void notifierCarteCliquee(Carte carte) {
        for (EcouteurCarteClique e : ecouteursCarte) {
            e.carteCliquee(carte);
        }
    }

    /**
     * Affiche le paquet en créant une vue graphique pour chaque carte.
     * Chaque carte est affichée via VueCarte et devient cliquable et surlignable 
     * au survol si ce n'est pas une défausse.
     * 
     * Les cartes de la défausse ne changent pas de couleur au survol et ne 
     * répondent pas au clic.
     */
    @Override
    public void afficherPaquet() {
        this.removeAll();

        for (Carte carte : paquet.getPaquet()) {
            VueCarte vueCarte = new VueCarte(carte, true);

            vueCarte.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (!estDefausse) {
                        notifierCarteCliquee(carte);
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    if (!estDefausse) {
                        vueCarte.setBackground(Color.GREEN);
                        vueCarte.setOpaque(true);
                        vueCarte.repaint();
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (!estDefausse) {
                        vueCarte.setBackground(Color.WHITE);
                        vueCarte.setOpaque(true);
                        vueCarte.repaint();
                    }
                }
            });

            this.add(vueCarte);
        }

        this.revalidate();
        this.repaint();
    }
}
