/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jeucarte;

import java.util.ArrayList;

/**
 *
 * @author tellier212
 */
public class AbstractModeleEcoutable implements EcouteurModele{
    private ArrayList<EcouteurModele> ecouteurs = new ArrayList<>();
    
    public void ajoutEcouteur(EcouteurModele e){
        ecouteurs.add(e);
    }
    
    public void retrait(EcouteurModele e){
        ecouteurs.remove(e);
    }
    
    protected void fireChangement(){
        for(EcouteurModele e : ecouteurs){
            e.modeleMisAJour(this);
        }
    }
}
