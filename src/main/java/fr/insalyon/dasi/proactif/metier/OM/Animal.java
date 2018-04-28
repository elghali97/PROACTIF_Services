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
public class Animal extends Intervention{

    protected String Animal;

    public Animal() {
    }

    //constructeur
    public Animal(String Animal, String Description, 
            Date Horodate, Client Client) {
        super(Description, Horodate, Client);
        this.Animal = Animal;
    }

    
    //getter
    public String getAnimal() {
        return Animal;
    }
    
    @Override
    public String getType(){
        return "Animal";
    }

    @Override
    public String toString() {
        return "\nType : Animal\nClient : " + this.Client +
                "\nAnimal : " + this.Animal + 
                "\nDescription : "+ this.Description +
                "\nHorodate : " + this.Horodate;
    }
    
}
