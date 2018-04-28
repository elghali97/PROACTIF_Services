/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.DAO;

import fr.insalyon.dasi.proactif.metier.OM.Personne;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author cgangalic & mduraffour
 */
public class DAOPersonne {

    public void createPersonne(Personne cl) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.persist(cl);
    }

    public Personne searchPersonneById(Integer id) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Personne p = (Personne) em.find(Personne.class, id);
        return p;
    }
    
    
    //to use for the client/employer connection service
    public Personne searchPersonneByLoginAndPass(String mail, String mdp) {
      EntityManager em = JpaUtil.obtenirEntityManager();
      Query query = em.createQuery("select p from Personne p where p.mail = :email and p.mdp= :mdp");
      query.setParameter("email", mail);
      query.setParameter("mdp", mdp);
      List result = query.getResultList();
      if (result == null || result.isEmpty()){
          return null;
      }else{
          return (Personne)result.get(0);
      }
    }
    
    public Personne searchPersonneByMail(String mail) {
      EntityManager em = JpaUtil.obtenirEntityManager();
      Query query = em.createQuery("select p from Personne p where p.mail = :email");
      query.setParameter("email", mail);
      List result = query.getResultList();
      if (result == null || result.isEmpty()){
          return null;
      }else{
          return (Personne)result.get(0);
      }
    }
}
