/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modele.cartes;
/**
 *
 * @author siaghi231
 */
public class MainClass {
    
    public static void main(String[] args){
        Carte c1=new Carte("as", "pique");
        Carte c2=new Carte("valet", "coeur");
        System.out.println(c1);
        System.out.println(c2);
        
        System.out.println("\n Test Création paquet :  ");
        Paquet p1 = new Paquet();
        System.out.println("Paquet créer : "+p1);
        
        
        System.out.println("\n Test carte Ajoutée paquet :  ");
        p1.ajouter(new Carte("As", "Pique"));
        p1.ajouter(new Carte("2", "Pique"));
        p1.ajouter(new Carte("7", "Coeur"));
        System.out.println("Paquet avec ajout : "+p1);
        
        System.out.println("\n Test carte Retirée paquet :  ");
        p1.retirer(new Carte("7", "Coeur"));
        System.out.println("Paquet avec carte retirée : "+p1);
        
        System.out.println("\n Test Création 52 et 32 :  ");
        Paquet jeu52 = Paquet.creerJeu52();
        System.out.println("Jeu de 52 cartes : "+jeu52);
        Paquet jeu32 = Paquet.creerJeu32();
        System.out.println("Jeu de 32 cartes : "+jeu32);
        
        System.out.println("Une carte du jeu de 52 : "+jeu52.carteRandom());
        
        System.out.println("\n Test melange :  ");
        jeu52.melanger();
        System.out.println("Une paquet de 52 mélanger : "+jeu52);
        
        System.out.println("\n Test coupure :  ");
        Paquet nvjeu52 = Paquet.creerJeu52();
        nvjeu52.couper();
        System.out.println("Une paquet de 52 couper : "+jeu52);
    }
    
}
