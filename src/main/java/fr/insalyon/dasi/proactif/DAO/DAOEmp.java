/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.DAO;

import fr.insalyon.dasi.proactif.metier.OM.Employe;
import fr.insalyon.dasi.proactif.metier.OM.Intervention;
import java.util.Calendar;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author cgangalic & mduraffour
 */
public class DAOEmp {

    public List<Intervention> searchInterventionHistoryEmployer(Employe e) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Calendar c = new GregorianCalendar();
        c.set(HOUR_OF_DAY, 0);
        c.set(MINUTE, 0);
        c.set(SECOND, 0);
        Date start = c.getTime();
        c.set(HOUR_OF_DAY, 23);
        c.set(MINUTE, 59);
        c.set(SECOND, 59);
        Date end = c.getTime();
        Query query = em.createQuery("select i from Intervention i where "
                + "i.Employe = :emp and i.Horodate >= :sDate and "
                + "i.Horodate <= :fDate");
        query.setParameter("emp", e);
        query.setParameter("sDate",start);
        query.setParameter("fDate",end);
        List result = query.getResultList();
        if (result.isEmpty()) {
            return null;
        } else {
            return result;
        }
    }
    
    
    // here we always return the result even if it's null -> for the sake of use in Service
    public List<Employe> searchFreeEmpInDate(Date d) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        boolean state = true;
        Query query = em.createQuery("select e from Employe e where e.heureDebut < :hour1 and e.heureFin >= :hour2 and e.disponibilite = :status");
        query.setParameter("hour1", hour);
        query.setParameter("hour2", hour);
        query.setParameter("status", state);
        List result = query.getResultList();
        return result;
        
    }
    
    public Employe searchEmployeById(Integer Id)
     {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Query query = em.createQuery("select e from Employe e where e.id = :id");
        query.setParameter("id", Id);
        List result = query.getResultList();
        if (result == null || result.isEmpty()){
            return null;
        }else{
            return (Employe) result.get(0);
        }
     }
    
    public Employe modifyEmploye(Employe e){
        EntityManager em = JpaUtil.obtenirEntityManager();
        return em.merge(e);
    }
}
