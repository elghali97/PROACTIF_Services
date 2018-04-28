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
public class Incident extends Intervention {

    public Incident() {
    }

    public Incident(String Description, Date Horodate, 
            Client Client) {
        super(Description, Horodate, Client);
    }

    @Override
    public String getType(){
        return "Incident";
    }
    
    @Override
    public String toString() {
        return "\nType : Incident\nClient : " + this.Client +
                "\nDescription : "+ this.Description +
                "\nHorodate : " + this.Horodate;
    }
    
    
}
