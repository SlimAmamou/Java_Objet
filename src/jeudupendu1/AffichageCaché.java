/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeudupendu1;

/**
 *
 * @author Slim
 */
public class AffichageCaché {

    private final String Mot;
    public AffichageCaché(String Mot){
        this.Mot =Mot;
    }
    public String Cacher(){
        String result = "";
        int n = Mot.length();
        for(int i=0 ; i<n ; i++)result+="*";
        
        return result;
    }
}
