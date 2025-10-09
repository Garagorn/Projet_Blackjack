/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package jeucarte;

/**
 *
 * @author tellier212
 */
public class JeuCarte {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Carte c1=new Carte("as", "pique");
        Carte c2=new Carte("valet", "coeur");
        System.out.println(c1);
        System.out.println(c2);
        
        Paquet p1 = new Paquet();
        p1.ajouter(new Carte("As", "Pique"));
        p1.ajouter(new Carte("2", "Pique"));
        p1.ajouter(new Carte("7", "Coeur"));
        System.out.println("Paquet créer : "+p1);
        
        Paquet jeu52 = Paquet.creerJeu52();
        System.out.println("Jeu de 52 cartes : "+jeu52);
        Paquet jeu32 = Paquet.creerJeu32();
        System.out.println("Jeu de 32 cartes : "+jeu32);
        System.out.println("Une carte du jeu de 52 : "+jeu52.carteRandom());
    }
    
}
