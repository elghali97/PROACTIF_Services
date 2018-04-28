/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.metier.OM;

import com.google.maps.model.LatLng;
import static fr.insalyon.dasi.proactif.Outils.GeoTest.getLatLng;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;

/**
 *
 * @author cgangalic & mduraffour
 */
@Entity
@Inheritance (strategy = InheritanceType.TABLE_PER_CLASS)
public class Personne implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    protected String civilite;
    protected String nom;
    protected String prenom;
    @Temporal(javax.persistence.TemporalType.DATE)
    protected Date dateNaiss;
    protected String adressePost;
    protected String numTel; 
    @Column(unique=true)
    protected String mail;
    protected String mdp;
    protected LatLng latitudeLongitude;

    //Constructeurs
    public Personne(String civilite, String nom, String prenom, Date dateNaiss, 
            String codePostal, String ville, String rue, String numTel, 
            String mail, String mdp) {
        this.civilite = civilite;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaiss = dateNaiss;
        this.adressePost = rue+" "+codePostal+" "+ville;
        this.numTel = numTel;
        this.mail = mail;
        this.mdp = mdp;
        this.latitudeLongitude = getLatLng(adressePost);
    }

    public Personne() {
    }

    //getters
    public Integer getId() {
        return id;
    }
    
    public String getCivilite() {
        return civilite;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Date getDateNaiss() {
        return dateNaiss;
    }

    public String getAdressePost() {
        return adressePost;
    }

    public String getNumTel() {
        return numTel;
    }

    public String getMail() {
        return mail;
    }

    public String getMdp() {
        return mdp;
    }

    public LatLng getLatitudeLongitude() {
        return latitudeLongitude;
    }

    @Override
    public String toString() {
        return "\nid=" + id + ", nom=" + nom + ", prenom=" + 
                prenom ;
    } 
}