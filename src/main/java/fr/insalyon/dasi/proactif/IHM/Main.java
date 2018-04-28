package fr.insalyon.dasi.proactif.IHM;

import fr.insalyon.dasi.proactif.DAO.JpaUtil;
import static fr.insalyon.dasi.proactif.Outils.Saisie.lireChaine;
import static fr.insalyon.dasi.proactif.Outils.Saisie.lireInteger;
import fr.insalyon.dasi.proactif.metier.OM.Animal;
import fr.insalyon.dasi.proactif.metier.OM.Client;
import fr.insalyon.dasi.proactif.metier.OM.Employe;
import fr.insalyon.dasi.proactif.metier.OM.Incident;
import fr.insalyon.dasi.proactif.metier.OM.Intervention;
import fr.insalyon.dasi.proactif.metier.OM.Livraison;
import fr.insalyon.dasi.proactif.metier.Service.ServiceUtile;
import fr.insalyon.dasi.proactif.metier.Service.ServiceMetier;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author cgangalic & mduraffour
 */
public class Main {

    public static void main(String[] args) throws ParseException {
        
        
        JpaUtil.init();
        boolean continuer=true;       
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = sdf.parse("07/09/1996");
        Employe em = new Employe(7, 17, null, "Mr", "Gangalic", "Catalin", date,
                "69100", "Villeurbanne", "1 Avenue Albert Einstein", "07833333", 
                "cgangalic@gmail.com", "12ga34ca");
        ServiceMetier.creerPersonne(em);
        date = sdf.parse("06/06/1990");
        em = new Employe(10, 20, null, "Mrs", "Duraffourg", "Maud", date,
                "69100", "Villeurbanne", "20 Avenue Albert Einstein", "0787777", 
                "mdouraffourg@gmail.com", "12du34ma");
        ServiceMetier.creerPersonne(em);
        
        
        while(continuer){
            System.out.println("\nBienvenue, que voulez vous faire ?");
            System.out.println("1-creer un client");
            System.out.println("2-chercher une personne par son mdp/mail");
            System.out.println("3-historique d'un client");
            System.out.println("4-creer une intervention");
            System.out.println("5-tableau de bord Employe");
            System.out.println("6-creer Employe");
            System.out.println("7-cloturer Intervention");
            System.out.println("0-Quitter");
            List <Integer> valeurPoss = new ArrayList<>();
            valeurPoss.add(1);
            valeurPoss.add(2);
            valeurPoss.add(3);
            valeurPoss.add(4);
            valeurPoss.add(5);
            valeurPoss.add(6);
            valeurPoss.add(7);
            valeurPoss.add(0);
            int rep = lireInteger("Choix ", valeurPoss);
            switch (rep){
                case 0:
                    continuer = false;
                    break;
                case 1:
                        String civilite = lireChaine("Quel est la civilité "
                                + "à utiliser ? ");
                        String nom = lireChaine("Quel est le nom du client ? ");
                        String prenom = lireChaine("Quel est le prenom du "
                                + "client ? ");
                        String dateString = lireChaine("Quel est la date de "
                                + "naissance ? (A entrer sous la forme 'dd/mm/yyyy') ");
                        sdf = new SimpleDateFormat("dd/MM/yyyy");
                        date = sdf.parse(dateString);
                        String codePost = lireChaine("Quel est le code postale "
                                + "? ");
                        String ville = lireChaine("Dans quelle ville habite le "
                                + "client ? ");
                        String rue = lireChaine("Dans quelles rues habite le "
                                + "client ? ");
                        String tel = lireChaine("Quel est son numero de tel ? ");
                        String mail = lireChaine("Quel est son email ? ");
                        String mdp = lireChaine("Quel est son mdp ? ");

                        Client cl = new Client(civilite, nom, prenom, date, 
                                codePost, ville, rue, tel, mail, mdp);
                        if (!ServiceMetier.creerPersonne(cl)){
                            System.out.println("\nPersonne pas creee");
                        }
                        break;

                case 2:
                        mail =lireChaine("Mail : ");
                        mdp = lireChaine("Mot de passe : "); 
                        if (ServiceMetier.chercherPersonneMailEtMdp(mail, mdp)!= null){
                            System.out.println(
                                    ServiceMetier.chercherPersonneMailEtMdp(mail,
                                            mdp) + "\n");
                        }else{
                            System.out.println("\nPersonne pas trouvee");
                        }
                        break;

                case 3:
                        int id = lireInteger("Id du client dont vous voulez "
                                + "voir l'historique : ");
                        Client c =ServiceUtile.chercherClientId(id);
                        List <Intervention> historique = 
                                ServiceMetier.historiqueClient(c);
                        if (!(historique == null || historique.isEmpty())){
                            for(Intervention i : historique) 
                                System.out.println(i);
                        } else {
                            System.out.println("\nPas d'historique :( ");
                        }
                        
                        break;

                case 4:
                        id = lireInteger("Id du client : ");
                        c =ServiceUtile.chercherClientId(id);

                        dateString = lireChaine("Quel est la date d'Intervention"
                                + " ? (A entrer sous la forme 'dd/mm/yyyy HH') ");
                        sdf = new SimpleDateFormat("dd/MM/yyyy HH");
                        date = sdf.parse(dateString);

                        System.out.println("Quel type d'intervention voulez-vous"
                                + " creer ? ");
                        List <Integer> valeurPossInter = new ArrayList<>();
                        System.out.println("1-Animal");
                        valeurPossInter.add(1);
                        System.out.println("2-Incident");
                        valeurPossInter.add(2);
                        System.out.println("3-Entreprise");
                        valeurPossInter.add(3);

                        int choixInter = lireInteger("Choix : ", valeurPossInter);

                        String comm=lireChaine("Decrivez votre besoin : ");

                        switch (choixInter){
                          case 1:
                              String animal = lireChaine("Quel type d'animal "
                                      + "avez vous ? ");
                              Animal a = new Animal(animal, comm, date, c);
                              if (ServiceMetier.creerIntervention(a)){
                                  System.out.println(a);
                              }else{
                                  System.out.println("\nPas d'employe pour "
                                          + "vous :( ");
                              }
                              break;
                          case 2:
                              Incident i = new Incident(comm, date, c);
                              if (ServiceMetier.creerIntervention(i)){
                                  System.out.println(i);
                              }else{
                                  System.out.println("\nPas d'employe pour "
                                          + "vous :( ");
                              }
                              break;
                          case 3:
                              String livraison = lireChaine("Quelle entreprise "
                                      + "vous livre ? ");
                              String objet = lireChaine("Quel objet vous est "
                                      + "livre ? ");
                              Livraison l = new Livraison(objet, livraison, comm,
                                      date, c);
                              if (ServiceMetier.creerIntervention(l)){
                                  System.out.println(l);
                              }else{
                                  System.out.println("\nPas d'employe pour "
                                          + "vous :( ");
                              }
                              break;
                        }
                        break;

                case 5:
                        id = lireInteger("Id de l'employe dont vous voulez voir "
                                + "l'historique : ");
                        Employe e =ServiceUtile.chercherEmployeId(id);
                        historique = ServiceMetier.historiqueEmploye(e);
                        if (!(historique == null || historique.isEmpty())){
                            for(Intervention i : historique) System.out.println(i);
                        }else{
                            System.out.println("\nPas d'historique :( ");
                        }
                        break;
                        
                case 6:
                        civilite = lireChaine("Quel est la civilité à utiliser ? ");
                        nom = lireChaine("Quel est le nom du client ? ");
                        prenom = lireChaine("Quel est le prenom du client ? ");
                        dateString = lireChaine("Quel est la date de naissance ?"
                                + " (A entrer sous la forme 'dd/mm/yyyy') ");
                        sdf = new SimpleDateFormat("dd/mm/yyyy");
                        date = sdf.parse(dateString);
                        codePost = lireChaine("Quel est le code postale ? ");
                        ville = lireChaine("Dans quelle ville habite le client ? ");
                        rue = lireChaine("Dans quelles rues habite le client ? ");
                        tel = lireChaine("Quel est son numero de tel ? ");
                        mail = lireChaine("Quel est son email ? ");
                        mdp = lireChaine("Quel est son mdp ? ");
                        Integer hDeb = lireInteger("A quelle heure commence-t-il"
                                + " le travail? ");
                        Integer hFin = lireInteger("A quelle heure finit-il "
                                + "le travail ? ");

                        em = new Employe(hDeb, hFin, null, civilite, nom,
                                prenom,  date, codePost, ville, rue, tel, mail, 
                                mdp);
                        if (!ServiceMetier.creerPersonne(em)){
                            System.out.println("\nPersonne pas creee");
                        }
                        break;
                case 7:
                        id = lireInteger("Id d'intervention : ");
                        dateString = lireChaine("Quel est la date du fin ? "
                                + "(A entrer sous la forme 'dd/mm/yyyy HH') ");
                        sdf = new SimpleDateFormat("dd/mm/yyyy HH");
                        date = sdf.parse(dateString);
                        String com = lireChaine("Est-ce vous avez des "
                                + "commentaires ? ");
                        if (ServiceMetier.clotureIntervention(id, date, com)){
                            System.out.println("\nIntervention cloturee");
                        } else {
                            System.out.println("\nIntervention pas cloturee");
                        }
                        break;
            }
        
        }
        JpaUtil.destroy();
    }
}