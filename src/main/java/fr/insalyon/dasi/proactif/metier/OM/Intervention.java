/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.metier.OM;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author cgangalic & mduraffour
 */
@Entity
@Inheritance (strategy = InheritanceType.SINGLE_TABLE)  //will create a table that contains all the under details
public class Intervention implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer IdIntervention;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    protected Date DateTermine;
    protected String CommentaireTermine;
    protected String Description;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    protected Date Horodate;
    @ManyToOne
    protected Client Client;
    @ManyToOne
    protected Employe Employe;


    public Intervention() {
    }

    //constructor
    public Intervention(String Description, Date Horodate, 
            Client Client) {
        this.Description = Description;
        this.Horodate = Horodate;
        this.Client = Client;
        this.Employe = null;
    }

    @Override
    public String toString() {
        return "\nClient : " + this.Client +
                "\nDescription : "+ this.Description +
                "\nHorodate : " + this.Horodate;
    }
    
    public String getType(){
        return "";
    }

    //setter
    public void setDateTermine(Date DateTermine) {
        this.DateTermine = DateTermine;
    }

    public void setCommentaireTermine(String CommentaireTermine) {
        this.CommentaireTermine = CommentaireTermine;
    }

    //setter
    public void setEmploye(Employe Employe) {
        this.Employe = Employe;
    }
    
    //getters
    public Date getDateTermine() {
        return DateTermine;
    }

    public String getCommentaireTermine() {
        return CommentaireTermine;
    }

    public String getDescription() {
        return Description;
    }

    public Date getHorodate() {
        return Horodate;
    }

    public Client getClient() {
        return Client;
    }    

    public Employe getEmploye() {
        return Employe;
    }
        
}
