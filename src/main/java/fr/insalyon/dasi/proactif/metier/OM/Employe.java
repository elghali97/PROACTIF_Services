/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.metier.OM;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 *
 * @author cgangalic & mduraffour
 */
@Entity
public class Employe extends Personne implements Serializable {
    
    protected Integer heureDebut;
    protected Integer heureFin;
    protected Boolean disponibilite;
    @OneToOne
    protected Intervention interventionActive;

    //Constructeur
    public Employe(Integer heureDebut, Integer heureFin, 
            Intervention interventionActive, String civilite, String nom, 
            String prenom, Date dateNaiss, String codePostal, String ville, 
            String rue, String numTel, String mail, String mdp) {
        super(civilite, nom, prenom, dateNaiss, codePostal, ville, rue, numTel, 
                mail, mdp);
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.disponibilite = true;
        this.interventionActive = interventionActive;
    }
    
    
    public Employe() {
        super();
    }

    //setter
    public void setDisponibilite(Boolean Disponibilite) {
        this.disponibilite = Disponibilite;
    }

    public void setInterventionActive(Intervention interventionActive) {
        this.interventionActive = interventionActive;
    }
    
    
    //getter
    public Integer getHeureDebut() {
        return heureDebut;
    }

    public Integer getHeureFin() {
        return heureFin;
    }

    public Boolean getDisponibilite() {
        return disponibilite;
    }
}
