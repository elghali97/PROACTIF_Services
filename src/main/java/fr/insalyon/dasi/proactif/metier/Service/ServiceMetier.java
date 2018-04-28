/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.metier.Service;

import fr.insalyon.dasi.proactif.DAO.DAOClient;
import fr.insalyon.dasi.proactif.DAO.DAOEmp;
import fr.insalyon.dasi.proactif.DAO.DAOInterv;
import fr.insalyon.dasi.proactif.DAO.DAOPersonne;
import fr.insalyon.dasi.proactif.DAO.JpaUtil;
import fr.insalyon.dasi.proactif.Outils.GeoTest;
import fr.insalyon.dasi.proactif.metier.OM.Client;
import fr.insalyon.dasi.proactif.metier.OM.Employe;
import fr.insalyon.dasi.proactif.metier.OM.Intervention;
import fr.insalyon.dasi.proactif.metier.OM.Personne;
import java.util.Date;
import java.util.List;
import javax.persistence.RollbackException;

/**
 *
 * @author cgangalic & mduraffour
 */
public class ServiceMetier {

    static public boolean creerPersonne(Personne p) {
        boolean res = false;
        try {
            JpaUtil.creerEntityManager();
            JpaUtil.ouvrirTransaction();
            DAOPersonne dp = new DAOPersonne();
            if (dp.searchPersonneByMail(p.getMail()) == null) {
                dp.createPersonne(p);
                JpaUtil.validerTransaction();
                res=true;
            } else {
                JpaUtil.annulerTransaction();
            }
        } catch (RollbackException e) {
            e.printStackTrace();
            JpaUtil.annulerTransaction();
        } finally {
            envoyeMail(res, p.getMail(), p.getPrenom(), p.getId());
            JpaUtil.fermerEntityManager();
        }
        return res;
    }

    static public Personne chercherPersonneMailEtMdp(String mail, String mdp) {
        JpaUtil.creerEntityManager();
        DAOPersonne dp = new DAOPersonne();
        Personne p = dp.searchPersonneByLoginAndPass(mail, mdp);
        JpaUtil.fermerEntityManager();
        return p;
    }

    static public List<Intervention> historiqueClient(Client c) {
        JpaUtil.creerEntityManager();
        DAOClient dc = new DAOClient();
        List res = dc.searchInterventionHistoryClient(c);
        JpaUtil.fermerEntityManager();
        return res;
    }

    static public List<Intervention> historiqueEmploye(Employe e) {
        JpaUtil.creerEntityManager();
        DAOEmp de = new DAOEmp();
        List res = de.searchInterventionHistoryEmployer(e);
        JpaUtil.fermerEntityManager();
        return res;
    }
    
    static public boolean creerIntervention(Intervention i){ //Empl free + good hour + closest (to my ass)
        boolean res=false;
        
        try{
            JpaUtil.creerEntityManager();
            JpaUtil.ouvrirTransaction();
            
            //Employe libre et dans les bons horaires
            DAOEmp de = new DAOEmp();
            List <Employe> listEmp = de.searchFreeEmpInDate(i.getHorodate());
            
            if(!(listEmp.isEmpty())){
                
                //le plus pres
                Employe plusProche = listEmp.get(0);
                double tempsMin = GeoTest.getTripDurationByBicycleInMinute(
                        i.getClient().getLatitudeLongitude(), 
                        plusProche.getLatitudeLongitude());
                if (listEmp.size()>1){
                    int emplAct = 1;
                    while (emplAct<listEmp.size())
                    {
                        double tAct = GeoTest.getTripDurationByBicycleInMinute(
                                i.getClient().getLatitudeLongitude(),
                                listEmp.get(emplAct).getLatitudeLongitude());
                        if( tempsMin > tAct)
                        {
                            plusProche=listEmp.get(emplAct);
                            tempsMin=tAct;
                        }
                        emplAct++;
                    }
                }                    
                
                //creation intervention
                if (plusProche.getDisponibilite()==true){
                    DAOInterv di = new DAOInterv();
                    plusProche.setDisponibilite(false);
                    plusProche.setInterventionActive(i);
                    i.setEmploye(plusProche);
                    di.createIntervention(i);
                    de.modifyEmploye(plusProche);
                    envoyeNotifEmploye(i, plusProche);
                    JpaUtil.validerTransaction();
                    res =true;
                }else
                {
                    JpaUtil.annulerTransaction();
                    res=false;
                }
            }else
            {
                JpaUtil.annulerTransaction();
                res = false;
            }
            
        } catch(RollbackException e){
            JpaUtil.annulerTransaction();
            res = false;
        }
        finally{
            JpaUtil.fermerEntityManager();
        }
        
        return res;
    }
    
    static public boolean clotureIntervention(Integer id, Date dateFin, 
                                            String com){
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        DAOInterv di = new DAOInterv();
        Intervention i = di.searchInterventionById(id);
        boolean res = true;
        if (i!=null && i.getEmploye()!=null){
            DAOEmp de = new DAOEmp();
            i.setDateTermine(dateFin);
            i.setCommentaireTermine(com);
            di.modifyIntervention(i);
            i.getEmploye().setDisponibilite(true);
            i.getEmploye().setInterventionActive(null);
            de.modifyEmploye(i.getEmploye());
            envoyeNotifClient(i, i.getEmploye());
            JpaUtil.validerTransaction();
        }else{
            res=false;
            JpaUtil.annulerTransaction();
        }
        JpaUtil.fermerEntityManager();
        return res;
    }
    
    
    // private pour affichage de Mails et Notifications
    static private void envoyeMail(boolean succes, String mail, String Prenom, 
                                Integer id){
        System.out.println("\nExpediteur : contact@proact.if"
                + "\nPour : " + mail
                + "\nSujet : Bienvenue chez Proact'IF"
                + "\nCorps : ");
        if (succes){
            System.out.println("Bonjour " + Prenom + ",\n"
            + "Nous vous confirmons votre inscription au service Proact'IF. "
            + "Votre numero est : " + id +".\n");
        } else {
            System.out.println("Bonjour " + Prenom + ",\n"
            + "Votre inscription au service Proact'IF a malencontreusement "
            + "echoue... Merci de recommencer ulterieurement.\n");
        }
    }
    
    static private void envoyeNotifEmploye(Intervention i, Employe e){
        System.out.println("\nTelephone employee:");
        System.out.println("\nIntervention " + i.getType() + " demandee le "
            + i.getHorodate() + " pour " + i.getClient().getPrenom() + " "
            + i.getClient().getNom() + " (#" + i.getClient().getId() + "), "
            + i.getClient().getAdressePost() + " ("
            + GeoTest.getTripDistanceByCarInKm(e.getLatitudeLongitude(), 
                    i.getClient().getLatitudeLongitude()) + "km) : "
            + i.getDescription() + ".\n\n");
    }
    
    static private void envoyeNotifClient(Intervention i, Employe e){
        System.out.println("\nTelephone client:");
        i.toString();
        System.out.println("\nDate fin : " + i.getDateTermine() + 
                "\nResponsable : " + e.getPrenom() + " " + e.getNom() + "\n");
    }
}
