/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blackjack;
import blackjack.modele.jeu.Blackjack;
import blackjack.modele.joueurs.JoueurBlackjackH;
import blackjack.vue.JeuFrame;
import blackjack.vue.VueBlackjack;
import blackjack.controleur.ControleurJeu;


/**
 *
 * @author siaghi231
 */
public class MainClass {
    
    public static void main(String[] args)
    {
        JoueurBlackjackH joueur = new JoueurBlackjackH("Massi", 1000);
        Blackjack bj = new Blackjack(joueur);
        JeuFrame fenetreJeu = new JeuFrame(bj);
        ControleurJeu cj =  new ControleurJeu(bj,fenetreJeu);
        bj.demarrer();
        fenetreJeu.setControleur(cj);
    }
}
