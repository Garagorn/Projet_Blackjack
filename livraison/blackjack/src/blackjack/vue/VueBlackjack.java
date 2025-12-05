package blackjack.vue;

import blackjack.controleur.ControleurBlackjack;
import blackjack.modele.event.EcouteurModele;
import blackjack.modele.jeu.ModeleBlackjack;
import blackjack.modele.joueurs.Joueur;
import blackjack.modele.util.EtatPartie;
import blackjack.vue.adapter.ModeleTableJoueurs;
import blackjack.vue.composantscontrol.*;
import blackjack.vue.composantstable.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Classe représentant la vue du jeu de Blackjack.
 * Elle est responsable de l'affichage de l'état du jeu, y compris la table de jeu, 
 * les informations des joueurs et les contrôles de mise et d'action. 
 * Elle implémente l'interface {@link EcouteurModele} pour recevoir les mises à jour du modèle et actualiser l'affichage.
 */
public class VueBlackjack extends JPanel implements EcouteurModele {
    
    /** Modèle du jeu contenant la logique du jeu */
    private ModeleBlackjack modele;
    
    /** Contrôleur du jeu permettant de gérer les actions de l'utilisateur */
    private ControleurBlackjack controleur;
    
    /** Adaptateur pour gérer l'affichage des informations des joueurs dans un tableau */
    private ModeleTableJoueurs adaptateur;
    
    /** 
     * Composants visuels de la vue */
    /**
     *Affiche la table de jeu
     */
    private PanneauTable panneauTable;
  
    /**
     *Affiche les contrôles pour les mises et actions
     */
    private PanneauControles panneauControles; 
    
    /**
     *Affiche les informations sur le joueur actif
     */ 
    private PanneauInfosJoueur panneauInfos;
    
    /**
     *Tableau des joueurs
     */
    private JTable tableJoueurs; 
    
    /**
     *Scroll pane pour la liste des joueurs
     */
    private JScrollPane scrollPaneTable;                 
    
    /**
     * Constructeur de la vue du jeu de Blackjack.
     * Initialise les composants visuels et enregistre l'écouteur pour mettre à jour la vue lorsque le modèle change.
     * 
     * @param modele Le modèle contenant la logique du jeu
     */
    public VueBlackjack(ModeleBlackjack modele) {
        this.modele = modele;
        initialiserComposants();
        this.adaptateur = new ModeleTableJoueurs(this.modele);
        tableJoueurs.setModel(adaptateur);
        
        // Ajout de l'écouteur pour recevoir les mises à jour du modèle
        modele.ajouterEcouteur(this);
    }
    
    /**
     * Initialise les composants visuels de la vue.
     * Crée les différents panneaux pour l'affichage de la table, les informations des joueurs et les contrôles de jeu.
     */
    private void initialiserComposants() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panneau principal affichant la table de jeu
        panneauTable = new PanneauTable(this.modele);
        add(panneauTable, BorderLayout.CENTER);
        
        // Panneau de droite contenant les informations sur le joueur et les contrôles
        JPanel panneauDroit = new JPanel(new BorderLayout(5, 5));
        
        panneauInfos = new PanneauInfosJoueur();
        panneauDroit.add(panneauInfos, BorderLayout.NORTH);
        
        panneauControles = new PanneauControles();
        panneauDroit.add(panneauControles, BorderLayout.CENTER);
        
        add(panneauDroit, BorderLayout.EAST);
        
        // Panneau du bas affichant la liste des joueurs
        JPanel panneauBas = creerPanneauTableJoueurs();
        add(panneauBas, BorderLayout.SOUTH);
    }
    
    /**
     * Crée le panneau affichant la liste des joueurs sous forme de tableau.
     * 
     * @return Le panneau contenant la liste des joueurs
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
     * Associe un contrôleur à la vue.
     * 
     * @param controleur Le contrôleur à associer
     */
    public void setControleur(ControleurBlackjack controleur) {
        this.controleur = controleur;
        panneauControles.setControleur(controleur);
    }
    
    /**
     * Actualise l'affichage de la vue en fonction de l'état actuel du jeu.
     * Cette méthode est appelée dans modeleMiseAjour() à chaque fois que le modèle change.
     */
    public void actualiserAffichage() {
        if (modele == null) {
            return;
        }
        
        EtatPartie etat = modele.getEtatPartie();
        Joueur joueurActif = modele.getJoueurActif();
        panneauTable.actualiser();
        panneauInfos.afficherInfos(joueurActif);
        
        // Mise à jour de l'affichage en fonction de l'état de la partie
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
       
    }
    
    /**
     * Affiche les résultats de la partie pour chaque joueur.
     * Cette méthode affiche un message pour chaque joueur en indiquant s'il a gagné, perdu ou si la partie est une égalité.
     */
    private void afficherResultats() {
        ArrayList<String> resultats;
        resultats = new ArrayList<>();

        // Calcul des résultats pour chaque joueur
        for (Joueur joueur : modele.getJoueurs()) {
            int bilan = joueur.getBilanPartie();
            String message;

            if (bilan > 0) {
                message = joueur.getNom() + " gagne " + bilan + " € dans cette partie";
            } else if (bilan < 0) {
                message = joueur.getNom() + " perd " + Math.abs(bilan) + " € dans cette partie";
            } else {
                message = joueur.getNom() + " n'a rien perdu dans cette partie";
            }

            resultats.add(message);
        }

        // Affichage des résultats avec un délai entre chaque message
        final int[] index = {0};   
        Timer timer = new Timer(2000, e -> {  
            if (index[0] < resultats.size()) {
                panneauInfos.afficherMessage(resultats.get(index[0]));  
                index[0]++;  
            } else {
                ((Timer) e.getSource()).stop();  
            }
        });
        timer.setRepeats(true);
        timer.start();
    }

    /**
     * Affiche un message dans la vue (généralement dans le panneau d'informations).
     * 
     * @param message Le message à afficher
     */
    public void afficherMessage(String message) {
        panneauInfos.afficherMessage(message);
    }
    


    /**
     * Méthode appelée par le modèle pour mettre à jour l'affichage.
     * 
     * @param source Le modèle qui a été mis à jour
     */
    public void modeleMiseAJour(Object source) {
        actualiserAffichage();
    }
}
