/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.metier.OM;

import java.util.Date;
import javax.persistence.Entity;

/**
 *
 * @author cgangalic & mduraffour
 */
@Entity
public class Livraison extends Intervention {
    
    protected String Objet;
    protected String Entreprise;

    public Livraison() {
    }

    //Constructeur
    public Livraison(String Objet, String Entreprise, String Description, 
            Date Horodate, Client Client) {
        super(Description, Horodate, Client);
        this.Objet = Objet;
        this.Entreprise = Entreprise;
    }
    
    //getters
    public String getObjet() {
        return Objet;
    }

    public String getEntreprise() {
        return Entreprise;
    }
    
    @Override
    public String getType(){
        return "Livraison";
    }

    @Override
    public String toString() {
        return "\nType : Livraison\nClient : " + this.Client +
                "\nObjet : " + this.Objet + 
                "\nEntreprise : " + this.Entreprise +
                "\nDescription : "+ this.Description +
                "\nHorodate : " + this.Horodate;
    }
    
    
}
