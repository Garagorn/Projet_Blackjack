package blackjack.vue.menus;

import blackjack.modele.strategie.*;
import blackjack.modele.joueurs.*;
import blackjack.vue.AppWindows;
import javax.swing.*;
 
/**
 * Menu "Joueurs" dans la barre de menu de l'application Blackjack.
 * Ce menu permet d'ajouter un joueur IA avec une stratégie spécifique ou de retirer un joueur de la partie.
 */
public class MenuJoueurs extends JMenu {

    /**
     * Constructeur du menu "Joueurs".
     * Ce menu contient deux options principales : "Ajouter IA" et "Retirer Joueur".
     * Chaque option est associée à une action spécifique pour ajouter ou retirer un joueur.
     *
     * @param appWindows la fenêtre principale de l'application, utilisée pour appeler les méthodes du contrôleur et du modèle.
     */
    public MenuJoueurs(AppWindows appWindows) {
        super("Joueurs");
        
        // Item pour ajouter un joueur IA
        JMenuItem itemAjouterIA = new JMenuItem("Ajouter IA");
        itemAjouterIA.addActionListener(e -> ajouterJoueurIA(appWindows));
        
        // Item pour retirer un joueur
        JMenuItem itemRetirerJoueur = new JMenuItem("Retirer Joueur");
        itemRetirerJoueur.addActionListener(e -> retirerJoueur(appWindows));
        
        // Ajouter les éléments au menu
        this.add(itemAjouterIA);
        this.add(itemRetirerJoueur);
    }
    
    /**
     * Affiche un dialogue pour ajouter un joueur IA à la partie.
     * L'utilisateur peut choisir un nom et une stratégie parmi les options disponibles.
     * Le joueur IA sera ajouté à la partie si les conditions sont remplies.
     * 
     * @param appWindows la fenêtre principale de l'application, utilisée pour interagir avec le modèle et le contrôleur.
     */
    private void ajouterJoueurIA(AppWindows appWindows) {
        int NBR_MAX_JOUEURS = 5; // Nombre maximal de joueurs
        if(appWindows.getModele().getEtatPartie() != blackjack.modele.util.EtatPartie.EN_ATTENTE){
             JOptionPane.showMessageDialog(appWindows, "Attendez la fin de la partie");
             return;
        }
        
        if(appWindows.getModele().getJoueurs().size() < NBR_MAX_JOUEURS ){
            // Demande du nom du joueur IA
            String nom = JOptionPane.showInputDialog(appWindows,
                "Nom du joueur IA :",
                "Ajouter IA",
                JOptionPane.QUESTION_MESSAGE);

            if (nom != null && !nom.trim().isEmpty()) {
                // Choix de la stratégie
                String[] strategies = {"Basique", "Agressive", "Conservatrice"};
                String strategieChoisie = (String) JOptionPane.showInputDialog(appWindows,
                    "Choisissez une stratégie :",
                    "Stratégie IA",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    strategies,
                    strategies[0]);

                if (strategieChoisie != null) {
                    JoueurIA nouvelleIA;
                    switch(strategieChoisie) {
                        case "Basique":
                            nouvelleIA = new JoueurIA(nom, 1000, new StrategieBasique());
                            break;
                        case "Agressive":
                            nouvelleIA = new  JoueurIA(nom, 1000, new StrategieAgressive());
                            break;
                        case "Conservatrice":
                            nouvelleIA = new  JoueurIA(nom, 1000, new StrategieConservatrice());
                            break;
                        default:
                            nouvelleIA = new  JoueurIA(nom, 1000, new StrategieBasique());
                    }

                    // Ajouter l'IA au modèle via le contrôleur
                    appWindows.ajouterJoueur(nouvelleIA);
                    JOptionPane.showMessageDialog(appWindows,
                        "Joueur IA " + nom + " (" + strategieChoisie + ") ajouté avec succès !");
                }
            }
        } else {
            // Si la table a atteint le nombre maximal de joueurs
            JOptionPane.showMessageDialog(appWindows, "La table ne peut pas accueillir plus que 5 joueurs");
        }
    }
    
    /**
     * Affiche un dialogue pour retirer un joueur de la partie.
     * L'utilisateur sélectionne un joueur dans la liste des joueurs actifs.
     * Le joueur sélectionné est retiré de la partie.
     * 
     * @param appWindows la fenêtre principale de l'application, utilisée pour interagir avec le modèle et le contrôleur.
     */
    private void retirerJoueur(AppWindows appWindows) {
        if (appWindows.getModele().getJoueurs().size() <= 1) {
            JOptionPane.showMessageDialog(appWindows,
                "Il faut au moins un joueur dans la partie !",
                "Erreur",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Récupérer les noms des joueurs actifs
        String[] nomsJoueurs = appWindows.getModele().getJoueurs().stream()
            .map(j -> j.getNom())
            .toArray(String[]::new);
        
        // Demande à l'utilisateur de choisir un joueur à retirer
        String nomChoisi = (String) JOptionPane.showInputDialog(appWindows,
            "Sélectionnez le joueur à retirer :",
            "Retirer Joueur",
            JOptionPane.QUESTION_MESSAGE,
            null,
            nomsJoueurs,
            nomsJoueurs[0]);
        
        if (nomChoisi != null) {
            // Retirer le joueur choisi
            appWindows.getModele().getJoueurs().stream()
                .filter(j -> j.getNom().equals(nomChoisi))
                .findFirst()
                .ifPresent((Joueur j) -> {
                    appWindows.retirerJoueur(j);
                    JOptionPane.showMessageDialog(appWindows,
                        "Joueur " + nomChoisi + " retiré !");
                });
        }
    }
}
