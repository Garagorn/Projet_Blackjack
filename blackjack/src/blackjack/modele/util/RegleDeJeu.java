package blackjack.modele.util;

import modele.cartes.Carte;
import modele.cartes.Paquet;
import blackjack.modele.joueurs.*;

public class RegleDeJeu {

    public static int calculerScore(Paquet main) {
        int score = 0;
        int nbr_As = 0;
        for (Carte carte : main.getPaquet()) {
            switch (carte.hauteur) {
                case "As":
                    nbr_As++;
                    score += 11;   
                    break;
                case "Valet":
                case "Dame":
                case "Roi":
                    score += 10;   
                    break;
                default:
                    score += Integer.parseInt(carte.hauteur);   
            }
        }
        while (score > 21 && nbr_As > 0) {
            score -= 10;   
            nbr_As--;
        }

        return score;
    }

    
    public static boolean aBlackjack(Paquet main) {
        if (main.getPaquet().size() != 2) {
            return false;
        }

        Carte carte1 = main.getPaquet().get(0);
        Carte carte2 = main.getPaquet().get(1);

        return (carte1.hauteur.equals("As") && estBuche(carte2)) ||
               (carte2.hauteur.equals("As") && estBuche(carte1));
    }

     
    private static boolean estBuche(Carte carte) {
        return carte.hauteur.equals("10") || carte.hauteur.equals("Valet") ||
               carte.hauteur.equals("Dame") || carte.hauteur.equals("Roi");
    }

}
