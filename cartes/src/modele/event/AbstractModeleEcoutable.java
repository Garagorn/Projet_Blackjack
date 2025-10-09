/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package  modele.event;
import java.util.ArrayList;
 /**
 *
 * @author siaghi231
 */
public abstract class AbstractModeleEcoutable  implements ModeleEcoutable{
    private ArrayList<EcouteurModele> ecouteurs = new ArrayList<>();
    
    public void ajouterEcouteur(EcouteurModele e){
        ecouteurs.add(e);
    }
    public void retirerEcouteur(EcouteurModele e){
        ecouteurs.remove(e);
    }
    
    protected void fireChangement(){
        for(EcouteurModele e : ecouteurs){
            e.modeleMiseAJour(this);
        }
    
    }
    
}
