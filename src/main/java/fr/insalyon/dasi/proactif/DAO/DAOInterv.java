/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.DAO;

import fr.insalyon.dasi.proactif.metier.OM.Intervention;
import javax.persistence.EntityManager;

/**
 *
 * @author cgangalic & mduraffour
 */
public class DAOInterv {
    
    public void createIntervention(Intervention i) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.persist(i);
    }
    
    public Intervention searchInterventionById(Integer id) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Intervention i = (Intervention) em.find(Intervention.class, id);
        return i;
    }
    
    public Intervention modifyIntervention(Intervention i) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        return em.merge(i);
    }
}
