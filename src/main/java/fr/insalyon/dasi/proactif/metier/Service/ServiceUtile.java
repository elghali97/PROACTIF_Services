/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.metier.Service;

import fr.insalyon.dasi.proactif.DAO.DAOClient;
import fr.insalyon.dasi.proactif.DAO.DAOEmp;
import fr.insalyon.dasi.proactif.DAO.JpaUtil;
import fr.insalyon.dasi.proactif.metier.OM.Client;
import fr.insalyon.dasi.proactif.metier.OM.Employe;

/**
 *
 * @author cgangalic & mduraffour
 */
public class ServiceUtile {
    
    public static Client chercherClientId(Integer id) {
        Client res;
        JpaUtil.creerEntityManager();
        DAOClient dc = new DAOClient();
        res = dc.searchClientById(id);
        JpaUtil.fermerEntityManager();
        return res;
    }
 
    public static Employe chercherEmployeId(Integer id) {
        Employe res;
        JpaUtil.creerEntityManager();
        DAOEmp de = new DAOEmp();
        res = de.searchEmployeById(id);
        JpaUtil.fermerEntityManager();
        return res;
    }

}
