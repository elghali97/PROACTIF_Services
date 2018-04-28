/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.metier.OM;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;

/**
 *
 * @author cgangalic & mduraffour
 */
@Entity
public class Client extends Personne implements Serializable { 
    
    public Client(String civilite, String nom, String prenom, Date dateNaiss, 
            String codePostal, String ville, String rue, String numTel, 
            String mail, String mdp){
        super(civilite, nom, prenom, dateNaiss, codePostal, ville, rue, numTel, 
                mail, mdp);
    }

    public Client() {
        super();
    }

    @Override
    public String toString() {
        return nom + " " + prenom;
    }
    
}
