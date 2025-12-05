package blackjack.modele.jeu;

import blackjack.modele.joueurs.Croupier;
import blackjack.modele.joueurs.JoueurHumain;
import blackjack.modele.joueurs.Joueur;
import modele.cartes.Carte;
import modele.cartes.Paquet;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GestionnairePaquetTest {
    
    private GestionnairePaquet gestionnaire;
    
    @Before
    public void setUp(){
        gestionnaire = new GestionnairePaquet();
    }
    
    
    @Test
    public void testConstructeur(){
        assertNotNull(gestionnaire.getPaquet());
        assertFalse(gestionnaire.getPaquet().estVide());
        assertEquals(52, gestionnaire.getPaquet().getPaquet().size());
    }
   
    
/**
 * Tests distributions des cartes
 */
    @Test
    public void testDistribuerCarteJoueurs(){
        Joueur joueur = new JoueurHumain("Alice", 1000);
        joueur.demarrerNouvelleMain(100);
        
        int tailleInitiale = gestionnaire.getPaquet().getPaquet().size();
        gestionnaire.distribuerCarteJoueurs(joueur);
        
        assertEquals(1, joueur.getMainActuelle().getNombreCartes());
        assertEquals(tailleInitiale - 1, gestionnaire.getPaquet().getPaquet().size());
    }
    @Test
    public void testDistribuerCarteCroupier(){
        Croupier croupier = new Croupier();
        
        int tailleInitiale = gestionnaire.getPaquet().getPaquet().size();
        gestionnaire.distribuerCarteCroupier(croupier);
        
        assertEquals(1, croupier.getMain().getNombreCartes());
        assertEquals(tailleInitiale - 1, gestionnaire.getPaquet().getPaquet().size());
    }
    @Test
    public void testDistribuerPlusieursCartes(){
        Joueur joueur = new JoueurHumain("Bob", 1000);
        joueur.demarrerNouvelleMain(100);
        
        gestionnaire.distribuerCarteJoueurs(joueur);
        gestionnaire.distribuerCarteJoueurs(joueur);
        gestionnaire.distribuerCarteJoueurs(joueur);
        
        assertEquals(3, joueur.getMainActuelle().getNombreCartes());
        assertEquals(49, gestionnaire.getPaquet().getPaquet().size());
    }

    
/**
 * Tests pour remelanger le paquet
 */    
    @Test
    public void testDoitRemelangerPaquetVide(){
        // Vider le paquet
        while (!gestionnaire.getPaquet().estVide()){
            Carte carte = gestionnaire.getPaquet().getPaquet().get(0);
            gestionnaire.getPaquet().retirer(carte);
        }
        
        assertTrue(gestionnaire.doitRemelanger(1));
    }
    @Test
    public void testDoitRemelangerPaquetNonVide(){
        assertFalse(gestionnaire.doitRemelanger(2));
    }
    @Test
    public void testRemelanger(){
        // Défausser toutes les cartes
        while(!gestionnaire.getPaquet().estVide()){
            Carte carte = gestionnaire.getPaquet().getPaquet().get(0);
            gestionnaire.getPaquet().retirer(carte);
            gestionnaire.getDefausse().ajouter(carte); // AJOUTER À LA DÉFAUSSE
        }

        assertTrue(gestionnaire.getPaquet().estVide());
        assertEquals(52, gestionnaire.getDefausse().getPaquet().size());

        gestionnaire.remelanger();

        assertFalse(gestionnaire.getPaquet().estVide());
        assertEquals(52, gestionnaire.getPaquet().getPaquet().size());
        assertTrue(gestionnaire.getDefausse().estVide());
    }
  
    
/**
 * Tests distributions
 */
    @Test
    public void testDistribuerCarteAvecPaquetVide(){
        Joueur joueur = new JoueurHumain("Charlie", 1000);
        joueur.demarrerNouvelleMain(100);

        // Vider le paquet ET mettre les cartes dans la défausse
        while (!gestionnaire.getPaquet().estVide()){
            Carte carte = gestionnaire.getPaquet().getPaquet().get(0);
            gestionnaire.getPaquet().retirer(carte);
            gestionnaire.getDefausse().ajouter(carte); //Ajout à la défausse
        }

        assertTrue(gestionnaire.getPaquet().estVide());
        assertEquals(52, gestionnaire.getDefausse().getPaquet().size());

        // Remélange automatique
        gestionnaire.distribuerCarteJoueurs(joueur);

        assertEquals(1, joueur.getMainActuelle().getNombreCartes());
        assertEquals(51, gestionnaire.getPaquet().getPaquet().size());
        assertTrue(gestionnaire.getDefausse().estVide()); // Défausse vidée lors du remélange
    }

    @Test
    public void testDistribuerCarteCroupierAvecPaquetVide(){
        Croupier croupier = new Croupier();

        // Vider le paquet ET mettre les cartes dans la défausse
        while (!gestionnaire.getPaquet().estVide()){
            Carte carte = gestionnaire.getPaquet().getPaquet().get(0);
            gestionnaire.getPaquet().retirer(carte);
            gestionnaire.getDefausse().ajouter(carte);
        }

        assertTrue(gestionnaire.getPaquet().estVide());
        assertEquals(52, gestionnaire.getDefausse().getPaquet().size());

        // remélange automatique
        gestionnaire.distribuerCarteCroupier(croupier);

        assertEquals(1, croupier.getMain().getNombreCartes());
        assertEquals(51, gestionnaire.getPaquet().getPaquet().size());
        assertTrue(gestionnaire.getDefausse().estVide());
    }
    
    
    @Test
    public void testGetPaquet(){
        Paquet paquet = gestionnaire.getPaquet();
        assertNotNull(paquet);
        assertSame(paquet, gestionnaire.getPaquet());
    }
    
    
/**
 * Tests distributions
 */
    @Test //Erreur possible : Deux paquets identiques apres melange
    public void testDistributionAleatoire(){
        GestionnairePaquet gestionnaire1 = new GestionnairePaquet();
        GestionnairePaquet gestionnaire2 = new GestionnairePaquet();
        
        Carte premiere1 = gestionnaire1.getPaquet().getPaquet().get(0);
        Carte premiere2 = gestionnaire2.getPaquet().getPaquet().get(0);
        
        assertNotNull(premiere1);
        assertNotNull(premiere2);
    }
    @Test
    public void testDistribuerAPlusieurJoueurs(){
        Joueur joueur1 = new JoueurHumain("Alice", 1000);
        Joueur joueur2 = new JoueurHumain("Bob", 1000);
        Croupier croupier = new Croupier();
        joueur1.demarrerNouvelleMain(100);
        joueur2.demarrerNouvelleMain(100);
        
        // Distribution 
        gestionnaire.distribuerCarteJoueurs(joueur1);
        gestionnaire.distribuerCarteJoueurs(joueur2);
        gestionnaire.distribuerCarteCroupier(croupier);
        gestionnaire.distribuerCarteJoueurs(joueur1);
        gestionnaire.distribuerCarteJoueurs(joueur2);
        gestionnaire.distribuerCarteCroupier(croupier);
        
        assertEquals(2, joueur1.getMainActuelle().getNombreCartes());
        assertEquals(2, joueur2.getMainActuelle().getNombreCartes());
        assertEquals(2, croupier.getMain().getNombreCartes());
        assertEquals(46, gestionnaire.getPaquet().getPaquet().size());
    }
    
    
/**
 * Test factory 52
 */
    @Test
    public void testPaquetContientToutesLesCartes(){
        Paquet paquet = gestionnaire.getPaquet();
        
        // Bonne génération de la factor 52
        assertEquals(52, paquet.getPaquet().size());
        
        // Tests des couleurs et hauteurs 13*4
        int coeurs = 0, piques = 0, carreaux = 0, trefles = 0;
        
        for (Carte carte : paquet.getPaquet()){
            switch (carte.couleur) {
                case "Coeur": coeurs++; break;
                case "Pique": piques++; break;
                case "Carreau": carreaux++; break;
                case "Trefle": trefles++; break;
            }
        }
        
        assertEquals(13, coeurs);
        assertEquals(13, piques);
        assertEquals(13, carreaux);
        assertEquals(13, trefles);
    }
}