package vue;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import modele.cartes.Paquet;

/**
 * {@code VuePaquetCache} est une vue graphique d'un {@link Paquet} affiché face cachée.
 * 
 * Cette vue affiche uniquement une représentation stylisée du dos des cartes, avec un
 * label indiquant le nombre total de cartes dans le paquet.
 * 
 * Elle est utilisée typiquement pour représenter la pioche dans le jeu.
 * Elle se met à jour automatiquement lorsque le modèle associé est modifié.
 */
public class VuePaquetCache extends VuePaquet {

    /**
     * Construit une vue pour un paquet affiché face cachée.
     *
     * @param p Le paquet à représenter graphiquement.
     * @param titre titre du panneau
     */
    public VuePaquetCache(Paquet p,String titre) {
        super(p);
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        afficherPaquet();
        TitledBorder bordure = BorderFactory.createTitledBorder(titre);
        this.setBorder(bordure);

    }

    /**
     * Méthode appelée automatiquement lorsqu'une mise à jour du modèle est détectée.
     *
     * @param source La source de l'événement (le modèle modifié).
     */
    @Override
    public void modeleMiseAJour(Object source) {
        afficherPaquet();
    }

    /**
     * Affiche le paquet face cachée.
     * Si le paquet contient au moins une carte, affiche un rectangle bleu stylisé
     * avec une bordure dont l'épaisseur dépend du nombre de cartes.
     * Un label indique le nombre total de cartes dans le paquet.
     */
    @Override
    public void afficherPaquet() {
        this.removeAll();

        int taille = paquet.getPaquet().size();
        if (taille > 0) {
            VueCarte dos = new VueCarte(null, false);

            // Épaisseur de la bordure en fonction du nombre de cartes
            int borderWidth = Math.min(10, taille / 3); // max 10px
            dos.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.DARK_GRAY, borderWidth),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));

            // Ajout d'un label pour indiquer le nombre de cartes
            JLabel label = new JLabel(taille + " cartes");
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setForeground(Color.WHITE);
            dos.setLayout(new BorderLayout());
            dos.add(label, BorderLayout.SOUTH);

            this.add(dos);
        }

        revalidate();
        repaint();
    }
}
