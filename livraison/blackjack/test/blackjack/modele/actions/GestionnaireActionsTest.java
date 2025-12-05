package blackjack.modele.actions;

import blackjack.modele.jeu.ModeleBlackjack;
import blackjack.modele.joueurs.Joueur;
import blackjack.modele.joueurs.JoueurHumain;
import modele.cartes.Carte;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class GestionnaireActionsTest {
    
    private ModeleBlackjack modele;
    private GestionnaireActions gestionnaire;
    private Joueur joueur;
    
    @Before
    public void setUp() {
        modele = new ModeleBlackjack(10);
        gestionnaire = new GestionnaireActions(modele);
        joueur = new JoueurHumain("Alice", 1000);
        modele.ajouterJoueur(joueur);
    }
     
    @Test
    public void testConstructeur() {
        assertNotNull(gestionnaire);
        assertEquals(5, gestionnaire.getNombreActions());
    }
    
    @Test
    public void testInitialiserActions() {
        assertTrue(gestionnaire.actionExiste("Tirer"));
        assertTrue(gestionnaire.actionExiste("Rester"));
        assertTrue(gestionnaire.actionExiste("Doubler"));
        assertTrue(gestionnaire.actionExiste("Séparer"));
        assertTrue(gestionnaire.actionExiste("Assurance"));
    }
    
    @Test
    public void testObtenirAction() {
        Action actionTirer = gestionnaire.obtenirAction("Tirer");
        
        assertNotNull(actionTirer);
        assertEquals("Tirer", actionTirer.getNom());
    }
    
    @Test
    public void testObtenirActionInexistante() {
        Action action = gestionnaire.obtenirAction("ActionInexistante");
        
        assertNull(action);
    }
    
    @Test
    public void testEnregistrerAction() {
        Action nouvelleAction = new Action(modele.getPioche()) {
            @Override
            public void executer(Joueur j) {}
            @Override
            public boolean peutExecuter(Joueur j) { return true; }
            @Override
            public String getNom() { return "ActionTest"; }
            @Override
            public String getDescription() { return "Test"; }
        };
        
        gestionnaire.enregistrerAction(nouvelleAction);
        
        assertTrue(gestionnaire.actionExiste("ActionTest"));
        assertEquals(6, gestionnaire.getNombreActions());
    }
    
    @Test
    public void testEnregistrerActionNull() {
        int nombreAvant = gestionnaire.getNombreActions();
        gestionnaire.enregistrerAction(null);
        assertEquals(nombreAvant, gestionnaire.getNombreActions());
    }
    
    @Test
    public void testRetirerAction() {
        Action actionRetiree = gestionnaire.retirerAction("Tirer");
        
        assertNotNull(actionRetiree);
        assertEquals("Tirer", actionRetiree.getNom());
        assertFalse(gestionnaire.actionExiste("Tirer"));
        assertEquals(4, gestionnaire.getNombreActions());
    }
    
    @Test
    public void testRetirerActionInexistante() {
        Action action = gestionnaire.retirerAction("ActionInexistante");
        assertNull(action);
        assertEquals(5, gestionnaire.getNombreActions());
    }
    
    @Test
    public void testObtenirToutesLesActions() {
        ArrayList<Action> actions = gestionnaire.obtenirToutesLesActions();
        assertNotNull(actions);
        assertEquals(5, actions.size());
    }
    
    @Test
    public void testObtenirActionsExecutables() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("10","Coeur"));
        joueur.ajouterCarte(new Carte("6","Pique"));
        
        ArrayList<Action> actionsExecutables = gestionnaire.obtenirActionsExecutables(joueur);
        
        assertNotNull(actionsExecutables);
        assertTrue(actionsExecutables.size() >= 2);
        
        boolean aTirer = false;
        boolean aRester = false;
        for (Action action : actionsExecutables) {
            if (action.getNom().equals("Tirer")) aTirer = true;
            if (action.getNom().equals("Rester")) aRester = true;
        }
        assertTrue(aTirer);
        assertTrue(aRester);
    }
    
    @Test
    public void testObtenirActionsExecutablesAvecDoubler() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("6","Coeur"));
        joueur.ajouterCarte(new Carte("5","Pique"));
        
        ArrayList<Action> actionsExecutables = gestionnaire.obtenirActionsExecutables(joueur);
        
        boolean aDoubler = false;
        for (Action action : actionsExecutables) {
            if (action.getNom().equals("Doubler")) aDoubler = true;
        }
        assertTrue("Devrait pouvoir doubler sur 11", aDoubler);
    }
    
    @Test
    public void testObtenirActionsExecutablesAvecSplit() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("8","Coeur"));
        joueur.ajouterCarte(new Carte("8","Pique"));
        
        ArrayList<Action> actionsExecutables = gestionnaire.obtenirActionsExecutables(joueur);
        
        boolean aSplit = false;
        for (Action action : actionsExecutables) {
            if (action.getNom().equals("Séparer")) aSplit = true;
        }
        assertTrue("Devrait pouvoir séparer des 8", aSplit);
    }
    
    @Test
    public void testObtenirActionsExecutablesAvecAssurance() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("10","Coeur"));
        joueur.ajouterCarte(new Carte("9","Pique"));
        
        modele.getCroupier().ajouterCarte(new Carte("As","Carreau"));
        modele.getCroupier().ajouterCarte(new Carte("7","Trefle"));
        
        ArrayList<Action> actionsExecutables = gestionnaire.obtenirActionsExecutables(joueur);
        
        boolean aAssurance = false;
        for (Action action : actionsExecutables) {
            if (action.getNom().equals("Assurance")) aAssurance = true;
        }
        assertTrue("Devrait pouvoir prendre assurance", aAssurance);
    }
    
    @Test
    public void testObtenirActionsExecutablesAvecJoueurNull() {
        ArrayList<Action> actionsExecutables = gestionnaire.obtenirActionsExecutables(null);
        assertNotNull(actionsExecutables);
        assertTrue(actionsExecutables.isEmpty());
    }
    
    @Test
    public void testObtenirActionsExecutablesMainBustee() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("10", "Coeur"));
        joueur.ajouterCarte(new Carte("10", "Pique"));
        joueur.ajouterCarte(new Carte("5", "Carreau"));
        
        ArrayList<Action> actionsExecutables = gestionnaire.obtenirActionsExecutables(joueur);
        assertTrue(actionsExecutables.isEmpty());
    }
    
    @Test
    public void testExecuterAction() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("10", "Coeur"));
        joueur.ajouterCarte(new Carte("6", "Pique"));
        
        int nombreCartesAvant = joueur.getMainActuelle().getNombreCartes();
        gestionnaire.executerAction("Tirer", joueur);
        assertEquals(nombreCartesAvant + 1, joueur.getMainActuelle().getNombreCartes());
    }
    
    @Test
    public void testExecuterActionInexistante() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("10", "Coeur"));
        joueur.ajouterCarte(new Carte("6", "Pique"));
        
        int nombreCartesAvant = joueur.getMainActuelle().getNombreCartes();
        gestionnaire.executerAction("ActionInexistante", joueur);
        assertEquals(nombreCartesAvant, joueur.getMainActuelle().getNombreCartes());
    }
    
    @Test
    public void testExecuterActionAvecJoueurNull() {
        gestionnaire.executerAction("Tirer", null);
    }
    
    @Test
    public void testObtenirNomsActions() {
        List<String> noms = gestionnaire.obtenirNomsActions();
        assertNotNull(noms);
        assertEquals(5, noms.size());
        assertTrue(noms.contains("Tirer"));
        assertTrue(noms.contains("Rester"));
        assertTrue(noms.contains("Doubler"));
        assertTrue(noms.contains("Séparer"));
        assertTrue(noms.contains("Assurance"));
    }
    
    @Test
    public void testActionExiste() {
        assertTrue(gestionnaire.actionExiste("Tirer"));
        assertTrue(gestionnaire.actionExiste("Rester"));
        assertFalse(gestionnaire.actionExiste("ActionInexistante"));
    }
    
    @Test
    public void testGetNombreActions() {
        assertEquals(5, gestionnaire.getNombreActions());
        gestionnaire.retirerAction("Tirer");
        assertEquals(4, gestionnaire.getNombreActions());
        
        Action nouvelleAction = new Action(modele.getPioche()) {
            @Override
            public void executer(Joueur j) {}
            @Override
            public boolean peutExecuter(Joueur j) { return true; }
            @Override
            public String getNom() {
                return "Test";
            }
            
            @Override
            public String getDescription() {
                return "Test";
            }
        };
        
        gestionnaire.enregistrerAction(nouvelleAction);
        assertEquals(5, gestionnaire.getNombreActions());
    }
    
    @Test
    public void testReinitialiser() {
        gestionnaire.retirerAction("Tirer");
        gestionnaire.retirerAction("Rester");
        
        assertEquals(3, gestionnaire.getNombreActions());
        
        gestionnaire.reinitialiser();
        
        assertEquals(5, gestionnaire.getNombreActions());
        assertTrue(gestionnaire.actionExiste("Tirer"));
        assertTrue(gestionnaire.actionExiste("Rester"));
    }
    
    @Test
    public void testToString() {
        String result = gestionnaire.toString();
        
        assertNotNull(result);
        assertTrue(result.contains("GestionnaireActions"));
        assertTrue(result.contains("5 actions"));
    }
    
    @Test
    public void testEnregistrerActionRemplaceSiMemeNom() {
        Action action1 = new Action(modele.getPioche()) {
            @Override
            public void executer(Joueur j) {}
            
            @Override
            public boolean peutExecuter(Joueur j) {
                return true;
            }
            
            @Override
            public String getNom() {
                return "TestAction";
            }
            
            @Override
            public String getDescription() {
                return "Version 1";
            }
        };
        
        Action action2 = new Action(modele.getPioche()) {
            @Override
            public void executer(Joueur j) {}
            
            @Override
            public boolean peutExecuter(Joueur j) {
                return true;
            }
            
            @Override
            public String getNom() {
                return "TestAction";
            }
            
            @Override
            public String getDescription() {
                return "Version 2";
            }
        };
        
        gestionnaire.enregistrerAction(action1);
        int nombreAvant = gestionnaire.getNombreActions();
        
        gestionnaire.enregistrerAction(action2);
        
        // Le nombre ne devrait pas changer (remplacement)
        assertEquals(nombreAvant, gestionnaire.getNombreActions());
        
        // Vérifier que c'est la version 2
        Action actionRecuperee = gestionnaire.obtenirAction("TestAction");
        assertEquals("Version 2", actionRecuperee.getDescription());
    }
    
    @Test
    public void testExecuterActionDoubler() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("6","Coeur"));
        joueur.ajouterCarte(new Carte("5","Pique"));
        
        int jetonsAvant = joueur.getJetons();
        
        gestionnaire.executerAction("Doubler", joueur);
        
        assertEquals(200, joueur.getMiseActuelle());
        assertEquals(jetonsAvant - 100, joueur.getJetons());
    }
    
    @Test
    public void testExecuterActionRester() {
        joueur.demarrerNouvelleMain(100);
        joueur.ajouterCarte(new Carte("10","Coeur"));
        joueur.ajouterCarte(new Carte("7","Pique"));
        
        int nombreCartesAvant = joueur.getMainActuelle().getNombreCartes();
        
        gestionnaire.executerAction("Rester", joueur);
        
        // Rien ne devrait changer
        assertEquals(nombreCartesAvant, joueur.getMainActuelle().getNombreCartes());
    }
}