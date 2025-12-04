package blackjack.vue;

import blackjack.controleur.ControleurBlackjack;
import blackjack.modele.event.EcouteurModele;
import blackjack.modele.jeu.ModeleBlackjack;
import blackjack.modele.joueurs.Croupier;
import blackjack.modele.joueurs.Joueur;
import blackjack.modele.joueurs.JoueurIA;
import blackjack.modele.util.EtatPartie;
import blackjack.vue.adapter.AdaptateurJoueurTable;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Vue principale du jeu Blackjack (JPanel).
 * Intègre tous les sous-panneaux et observe le modèle pour se mettre à jour.
 */
public class VueBlackjack extends JPanel implements EcouteurModele {
    
    private ModeleBlackjack modele;
    private ControleurBlackjack controleur;
    private AdaptateurJoueurTable adaptateur;
    
    // Composants visuels
    private PanneauTable panneauTable;
    private PanneauControles panneauControles;
    private PanneauInfosJoueur panneauInfos;
    private JTable tableJoueurs;
    private JScrollPane scrollPaneTable;
    
    /**
     * Constructeur de la vue Blackjack
     */
    public VueBlackjack(ModeleBlackjack modele) {
        this.modele = modele;
        initialiserComposants();
        this.adaptateur = new AdaptateurJoueurTable(this.modele);
        tableJoueurs.setModel(adaptateur.getModeleTable());
        
        modele.ajouterEcouteur(this);
       
    }
    
    /**
     * Initialise les composants de la vue
     */
    private void initialiserComposants() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panneau central : la table de jeu
        panneauTable = new PanneauTable(this.modele);
        add(panneauTable, BorderLayout.CENTER);
        
        // Panneau de droite : contrôles + infos
        JPanel panneauDroit = new JPanel(new BorderLayout(5, 5));
        
        panneauInfos = new PanneauInfosJoueur();
        panneauDroit.add(panneauInfos, BorderLayout.NORTH);
        
        panneauControles = new PanneauControles();
        panneauDroit.add(panneauControles, BorderLayout.CENTER);
        
        add(panneauDroit, BorderLayout.EAST);
        
        // Panneau du bas : table des joueurs
        JPanel panneauBas = creerPanneauTableJoueurs();
        add(panneauBas, BorderLayout.SOUTH);
    }
    
    /**
     * Crée le panneau contenant la JTable des joueurs
     */
    private JPanel creerPanneauTableJoueurs() {
        JPanel panneau = new JPanel(new BorderLayout());
        panneau.setBorder(BorderFactory.createTitledBorder("Liste des Joueurs"));
        
        tableJoueurs = new JTable();
        tableJoueurs.setFillsViewportHeight(true);
        
        scrollPaneTable = new JScrollPane(tableJoueurs);
        scrollPaneTable.setPreferredSize(new Dimension(0, 120));
        
        panneau.add(scrollPaneTable, BorderLayout.CENTER);
        
        return panneau;
    }
    
    
    /**
     * Définit le contrôleur
     * @param controleur le contrôleur
     */
    public void setControleur(ControleurBlackjack controleur) {
        this.controleur = controleur;
        panneauControles.setControleur(controleur);
    }
    
    /**
     * Actualise l'affichage complet de la vue
     */
    public void actualiserAffichage() {
        if (modele == null) {
            return;
        }
        
        EtatPartie etat = modele.getEtatPartie();
        Joueur joueurActif = modele.getJoueurActif();
        Croupier croupier = modele.getCroupier();
        
        // Actualiser la table de jeu
        panneauTable.actualiser();
        
        // Actualiser les infos du joueur actif
        panneauInfos.afficherInfos(joueurActif);
        
        // Actualiser les contrôles selon l'état
        switch (etat) {
            case EN_ATTENTE:
                panneauControles.activerMise(true);
                panneauControles.desactiverBoutons();
                panneauInfos.afficherMessage("Placez vos mises");
                break;
                
            case DISTRIBUTION:
                panneauControles.activerMise(false);
                panneauControles.desactiverBoutons();
                panneauInfos.afficherMessage("Distribution en cours...");
                break;
                
            case TOUR_JOUEURS:
                panneauControles.activerMise(false);
                if (joueurActif != null && joueurActif.estHumain()) {
                    panneauControles.activerActions(modele.getActionsDisponibles(joueurActif));
                    panneauInfos.afficherMessage("À votre tour !");
                } else {
                    panneauControles.desactiverBoutons();
                    if (joueurActif != null) {
                        System.out.println(joueurActif.getNom());
                        panneauInfos.afficherMessage(joueurActif.getNom() + " joue...");
                    }
                }
                break;
                
            case TOUR_CROUPIER:
                System.out.println("tour croupier");
                panneauControles.desactiverBoutons();
                panneauControles.activerMise(false);
                panneauInfos.afficherMessage("Le croupier joue...");
                break;
                
            case TERMINE:
                panneauControles.desactiverBoutons();
                panneauControles.activerMise(false);
                afficherResultats();
                break;
        }
        
        // Actualiser la JTable des joueurs
        adaptateur.rafraichirAffichage();
    }
    
    /**
     * Affiche les résultats de la partie
     */
    private void afficherResultats() {
        ArrayList<String> resultats;
        resultats = new ArrayList<>();

        for (Joueur joueur : modele.getJoueurs()) {
            int bilan = joueur.getBilanSession();
            String message;

            if (bilan > 0) {
                message = joueur.getNom() + " gagne " + bilan + " €!";
            } else if (bilan < 0) {
                message = joueur.getNom() + " perd " + Math.abs(bilan) + " €";
            } else {
                message = joueur.getNom() + " égalité!";
            }

            resultats.add(message); // Ajouter le résultat à
        }

        // Créer un Timer pour afficher les résultats un par un avec un délai
        final int[] index = {0};  // Utilisation d'un tableau pour une variable mutable dans le Timer

        Timer timer = new Timer(2000, e -> {  // Délai de 1500 ms (1.5 secondes)
            if (index[0] < resultats.size()) {
                panneauInfos.afficherMessage(resultats.get(index[0]));  // Afficher le message du joueur
                index[0]++;  // Passer au joueur suivant
            } else {
                ((Timer) e.getSource()).stop();  // Arrêter le Timer une fois que tous les résultats sont affichés
            }
        });

        timer.setRepeats(true);
        timer.start();
    }


    
    public void afficherMessage(String message) {
        panneauInfos.afficherMessage(message);
    }
    
   
    public void reinitialiser() {
        panneauTable.reinitialiser();
        panneauControles.desactiverBoutons();
        panneauControles.activerMise(true);
        panneauInfos.afficherInfos(null);
        panneauInfos.effacerMessage();
    }

    public void modeleMiseAJour(Object source) {
      actualiserAffichage();
    }
}