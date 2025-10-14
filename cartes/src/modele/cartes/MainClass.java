/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modele.cartes;

public class MainClass {
    
    public static void main(String[] args){
        //1er  test  pour la creation de carte
        Carte c1=new Carte("as", "pique");
        Carte c2=new Carte("valet", "coeur");
        System.out.println(c1);
        System.out.println(c2);
        System.out.println("Comparaison As pique = Valet Coeur");
        System.out.println(c1==c2);
        System.out.println("Comparaison As pique != Valet Coeur");
        System.out.println(c1!=c2);
        System.out.println("Comparaison As pique == As pique");
        System.out.println(c1==c1);
        Carte c3=new Carte("as", "pique");
        System.out.println("Comparaison As pique c1 == As pique c3");
        System.out.println(c1==c3);
        //1er  test  pour la creation d'un paquet
        System.out.println("\n Test Création paquet :  ");
        Paquet p1 = new Paquet();
        System.out.println("Paquet créer : "+p1);
        
        //Test  pour l'ajout de carte a un paquet
        System.out.println("\n Test carte Ajoutée paquet :  ");
        p1.ajouter(new Carte("As", "Pique"));
        p1.ajouter(new Carte("2", "Pique"));
        p1.ajouter(new Carte("7", "Coeur"));
        System.out.println("Paquet avec ajout : "+p1);
        
        //Test  pour retirer une carte a un paquet
        System.out.println("\n Test carte Retirée paquet :  ");
        p1.retirer(new Carte("7", "Coeur"));
        System.out.println("Paquet avec carte retirée : "+p1);
        
        //Test  pour creer un paquet de  52 et un autre de 32 cartes
        System.out.println("\n Test Création 52 et 32 :  ");
        Paquet jeu52 = Paquet.creerJeu52();
        System.out.println("Jeu de 52 cartes : "+jeu52);
        Paquet jeu32 = Paquet.creerJeu32();
        System.out.println("Jeu de 32 cartes : "+jeu32);
        
        //Test pour tirer  une  carte aleatoire du paquet
        System.out.println("Une carte du jeu de 52 : "+jeu52.carteRandom());
        
        //Test de  melange du paquet
        System.out.println("\n Test melange :  ");
        jeu52.melanger();
        System.out.println("Une paquet de 52 mélanger : "+jeu52);
        
        //Test pour  couper  le paquet
        System.out.println("\n Test coupure :  ");
        Paquet nvjeu52 = Paquet.creerJeu52();
        nvjeu52.couper();
        System.out.println("Une paquet de 52 couper : "+jeu52);
    }
    
}
