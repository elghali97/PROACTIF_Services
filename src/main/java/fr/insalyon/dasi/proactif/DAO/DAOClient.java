 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.DAO;

import fr.insalyon.dasi.proactif.metier.OM.Client;
import fr.insalyon.dasi.proactif.metier.OM.Intervention;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author cgangalic & mduraffour
 */
public class DAOClient {
    
     public void createClient(Client cl) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.persist(cl);
    }
     
     
     public Client searchClientById(Integer Id)
     {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Query query = em.createQuery("select c from Client c where c.id = :id");
        query.setParameter("id", Id);
        List result = query.getResultList();
        if (result == null || result.isEmpty()){
            return null;
        }else{
            return (Client) result.get(0);
        }
        
     }
    
    public List<Intervention> searchInterventionHistoryClient(Client c){
        EntityManager em = JpaUtil.obtenirEntityManager();
        Query query = em.createQuery("select i from Intervention i where i.Client = :cl");
        query.setParameter("cl", c);
        List result = query.getResultList();
        return result;
    }
}
