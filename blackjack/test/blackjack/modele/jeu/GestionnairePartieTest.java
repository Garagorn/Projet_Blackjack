package blackjack.modele.jeu;

import blackjack.modele.actions.Action;
import blackjack.modele.joueurs.Croupier;
import blackjack.modele.joueurs.JoueurHumain;
import blackjack.modele.joueurs.Joueur;
import blackjack.modele.joueurs.Main;
import blackjack.modele.util.EtatPartie;
import modele.cartes.Carte;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class GestionnairePartieTest {
    
    // Suppression de ModeleBlackjack - tests isolés
    private GestionnairePartie gestionnaire;
    private GestionnairePaquet gestionnairePaquet;
    private Croupier croupier;
    private ArrayList<Joueur> joueurs;
    private Joueur joueur;
    
    @Before
    public void setUp(){
        // Créer les composants directement
        joueurs = new ArrayList<>();
        joueur = new JoueurHumain("Alice", 1000);
        joueurs.add(joueur);
        
        gestionnairePaquet = new GestionnairePaquet();
        croupier = new Croupier();
        
        gestionnaire = new GestionnairePartie(joueurs, gestionnairePaquet, croupier);
    }
    
    @Test
    public void testConstructeur(){
        assertEquals(EtatPartie.EN_ATTENTE, gestionnaire.getEtatPartie());
        assertEquals(0, gestionnaire.getJoueurActifIndex());
    }


/**
 * Tests de partie
 */    
    @Test
    public void testDemarrerPartie(){
        joueur.demarrerNouvelleMain(100);
        
        gestionnaire.demarrerPartie();
        
        assertEquals(EtatPartie.TOUR_JOUEURS, gestionnaire.getEtatPartie());
        assertEquals(0, gestionnaire.getJoueurActifIndex());
        
        // Vérifier que les cartes ont été distribuées
        assertEquals(2, joueur.getMainActuelle().getNombreCartes());
        assertEquals(2, croupier.getMain().getNombreCartes());
    }
    
    @Test
    public void testResetPartie(){
        joueur.demarrerNouvelleMain(100);
        gestionnaire.demarrerPartie();
        
        gestionnaire.resetPartie();
        
        assertEquals(EtatPartie.EN_ATTENTE, gestionnaire.getEtatPartie());
        assertEquals(0, gestionnaire.getJoueurActifIndex());
        assertEquals(0, croupier.getMain().getNombreCartes());
        assertFalse(croupier.getRevelerDeuxiemeCarte());
    }
    
    
/**
 * Tests des joueurs
 */
    @Test
    public void testGetJoueurActif(){
        joueur.demarrerNouvelleMain(100);
        gestionnaire.demarrerPartie();
        
        Joueur actif = gestionnaire.getJoueurActif();
        
        assertNotNull(actif);
        assertEquals(joueur, actif);
    }
    
    @Test
    public void testGetJoueurActifNull(){
        ArrayList<Joueur> listeVide = new ArrayList<>();
        GestionnairePartie gestionnaireVide = new GestionnairePartie(
            listeVide, 
            new GestionnairePaquet(), 
            new Croupier()
        );
        
        assertNull(gestionnaireVide.getJoueurActif());
    }
    
    @Test
    public void testPasserAuJoueurSuivantAvecUnSeulJoueur(){
        joueur.demarrerNouvelleMain(100);
        gestionnaire.demarrerPartie();

        boolean finDesJoueurs = gestionnaire.passerAuJoueurSuivant();

        // Vérifier que tous les joueurs ont joué
        assertTrue(finDesJoueurs);

        // Maintenant appeler le tour du croupier
        gestionnaire.tourCroupier();

        // Passage au croupier
        assertEquals(EtatPartie.TERMINE, gestionnaire.getEtatPartie());
        assertTrue(croupier.getRevelerDeuxiemeCarte());
    }
    
    @Test
    public void testPasserAuJoueurSuivantAvecDeuxJoueurs(){
        Joueur joueur2 = new JoueurHumain("Bob", 1000);
        joueurs.add(joueur2);
        
        joueur.demarrerNouvelleMain(100);
        joueur2.demarrerNouvelleMain(100);
        gestionnaire.demarrerPartie();
        
        assertEquals(0, gestionnaire.getJoueurActifIndex());
        gestionnaire.passerAuJoueurSuivant();
        
        assertEquals(1, gestionnaire.getJoueurActifIndex());
        assertEquals(joueur2, gestionnaire.getJoueurActif());
    }
    
    @Test
    public void testPasserAuJoueurSuivantAvecDeuxMains(){
        joueur.demarrerNouvelleMain(100);
        
        //Test du Split
        Main deuxiemeMain = new Main(100);
        deuxiemeMain.ajouterCarte(new Carte("8", "Coeur"));
        joueur.ajouterMain(deuxiemeMain);
        
        gestionnaire.demarrerPartie();
        assertEquals(0, joueur.getMainActuelleIndex());
        gestionnaire.passerAuJoueurSuivant();
        
        // Passage à la deuxieme main
        assertEquals(1, joueur.getMainActuelleIndex());
        assertEquals(EtatPartie.TOUR_JOUEURS, gestionnaire.getEtatPartie());
    }
    

/**
 * Tests sur croupier
 */
    @Test
    public void testTourCroupier(){
        joueur.demarrerNouvelleMain(100);
        gestionnaire.demarrerPartie();
        
        gestionnaire.tourCroupier();
        
        assertEquals(EtatPartie.TERMINE, gestionnaire.getEtatPartie());
        assertTrue(croupier.getRevelerDeuxiemeCarte());
        assertTrue(croupier.getScore() >= 17 || croupier.estBuste());
    }
    
    @Test
    public void testTourCroupierTousJoueursBusts(){
        joueur.demarrerNouvelleMain(100);
        gestionnaire.demarrerPartie();
        
        // Joueur buster 
        while (!joueur.estBuste()){
            Carte carte = gestionnairePaquet.getPaquet().getPaquet().get(0);
            joueur.ajouterCarte(carte);
            gestionnairePaquet.getPaquet().retirer(carte);
        }
        
        int scoreCroupierAvant = croupier.getScore();
        
        gestionnaire.tourCroupier();
        
        assertTrue(croupier.getScore() <= scoreCroupierAvant + 11);
    }
      
    
    
    @Test
    public void testGetEtatPartie(){
        assertEquals(EtatPartie.EN_ATTENTE, gestionnaire.getEtatPartie());
        
        joueur.demarrerNouvelleMain(100);
        gestionnaire.demarrerPartie();
        
        assertEquals(EtatPartie.TOUR_JOUEURS, gestionnaire.getEtatPartie());
    }
    
    @Test
    public void testGetJoueurActifIndex(){
        assertEquals(0, gestionnaire.getJoueurActifIndex());
        
        Joueur joueur2 = new JoueurHumain("Bob", 1000);
        joueurs.add(joueur2);
        
        joueur.demarrerNouvelleMain(100);
        joueur2.demarrerNouvelleMain(100);
        
        gestionnaire.demarrerPartie();
        gestionnaire.passerAuJoueurSuivant();
        
        assertEquals(1, gestionnaire.getJoueurActifIndex());
    }
    
    
    
/**
 * Tests sur les victoires
 */
    @Test
    public void testDeterminerGagnantsJoueurGagne(){
        joueur.demarrerNouvelleMain(100);
        gestionnaire.demarrerPartie();
        
        // Forcer le score du joueur 
        while (joueur.getScore() < 18 && !joueur.estBuste()){
            Carte carte = gestionnairePaquet.getPaquet().getPaquet().get(0);
            joueur.ajouterCarte(carte);
            gestionnairePaquet.getPaquet().retirer(carte);
        }
        int jetonsAvant = joueur.getJetons();
        gestionnaire.tourCroupier();

        if (!joueur.estBuste() && joueur.getScore() > croupier.getScore() && !croupier.estBuste()){
            assertTrue(joueur.getJetons() > jetonsAvant);
        }
    }
    
    @Test
    public void testDeterminerGagnantsJoueurBuste(){
        joueur.demarrerNouvelleMain(100);
        gestionnaire.demarrerPartie();
        
        // Forcer le joueur a bust
        while (!joueur.estBuste()){
            Carte carte = gestionnairePaquet.getPaquet().getPaquet().get(0);
            joueur.ajouterCarte(carte);
            gestionnairePaquet.getPaquet().retirer(carte);
        }
        int jetonsAvant = joueur.getJetons();
        
        gestionnaire.tourCroupier();
        
        // Le joueur doit avoir perdu
        assertEquals(jetonsAvant, joueur.getJetons());
    }
    
    @Test
    public void testDeterminerGagnantsEgalite(){
        joueur.demarrerNouvelleMain(100);
        gestionnaire.demarrerPartie();
        
        int jetonsAvant = joueur.getJetons();
        
        gestionnaire.tourCroupier();
        
        // Si égalité -> le joueur recupere sa mise
        if (joueur.getScore() == croupier.getScore() && !joueur.estBuste()){
            assertEquals(jetonsAvant + 100, joueur.getJetons());
        }
    }
    
    
    @Test
    public void testResetPartieAvecPlusieursJoueurs(){
        Joueur joueur2 = new JoueurHumain("Bob", 1000);
        joueurs.add(joueur2);
        
        joueur.demarrerNouvelleMain(100);
        joueur2.demarrerNouvelleMain(100);
        
        gestionnaire.demarrerPartie();
        gestionnaire.resetPartie();
        
        assertEquals(0, joueur.getNombreMains());
        assertEquals(0, joueur2.getNombreMains());
        assertEquals(EtatPartie.EN_ATTENTE, gestionnaire.getEtatPartie());
    }


/**
 * Tests divers
 */    
    @Test
    public void testDemarrerPartieAvecBlackjackCroupier(){
        joueur.demarrerNouvelleMain(100);
        
        gestionnaire.demarrerPartie();
        
        // Blackjack du croupier -> fin de la partie
        if (croupier.aBlackjack()){
            assertEquals(EtatPartie.TERMINE, gestionnaire.getEtatPartie());
            assertTrue(croupier.getRevelerDeuxiemeCarte());
        } else {
            assertEquals(EtatPartie.TOUR_JOUEURS, gestionnaire.getEtatPartie());
        }
    }
    
    @Test
    public void testResetPartieRemelangeSiBesoin(){
        joueur.demarrerNouvelleMain(100);
        gestionnaire.demarrerPartie();

        // Défausser toutes les cartes de la pioche dans la défausse
        while (!gestionnairePaquet.getPaquet().estVide()){
            Carte carte = gestionnairePaquet.getPaquet().getPaquet().get(0);
            gestionnairePaquet.getPaquet().retirer(carte);
            gestionnairePaquet.getDefausse().ajouter(carte);
        }

        assertTrue(gestionnairePaquet.getPaquet().estVide());

        gestionnaire.resetPartie();

        // Paquet rempli au redémarrage
        joueur.demarrerNouvelleMain(100);
        gestionnaire.demarrerPartie();
        assertFalse(gestionnairePaquet.getPaquet().estVide());
    }
    
    @Test
    public void testPasserAuJoueurSuivantPlusieursMainsEtPlusieursJoueurs(){
        Joueur joueur2 = new JoueurHumain("Bob", 1000);
        joueurs.add(joueur2);
        
        joueur.demarrerNouvelleMain(100);
        joueur2.demarrerNouvelleMain(100);
        
        // Ajout d'une 2e main
        Main deuxiemeMain = new Main(100);
        joueur.ajouterMain(deuxiemeMain);
        
        gestionnaire.demarrerPartie();
        
        // Premiere main
        assertEquals(0, gestionnaire.getJoueurActifIndex());
        assertEquals(0, joueur.getMainActuelleIndex());
        
        gestionnaire.passerAuJoueurSuivant();
        
        // Deuxieme main
        assertEquals(0, gestionnaire.getJoueurActifIndex());
        assertEquals(1, joueur.getMainActuelleIndex());
        
        gestionnaire.passerAuJoueurSuivant();
        
        // Deuxième joueur
        assertEquals(1, gestionnaire.getJoueurActifIndex());
    }
}