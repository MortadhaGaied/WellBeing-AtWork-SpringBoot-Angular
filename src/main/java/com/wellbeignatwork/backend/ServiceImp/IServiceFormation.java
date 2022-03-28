package com.wellbeignatwork.backend.ServiceImp;



import com.wellbeignatwork.backend.model.Domain;
import com.wellbeignatwork.backend.model.Test;
import com.wellbeignatwork.backend.model.User;

import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

public interface IServiceFormation {

    void ajouterFormateur(User formateur);


    void addFormation(Test formation);
    void updateFormation(Test formation, Integer idFormateur);
    void deleteFormation(Integer idFormation);
    List<Test> afficherFormation();
    List<User> afficherFormateur();
    List<User> afficherApprenant();

    User FormateurwithMaxHo();

     User getFormateurRemunerationMaxSalaire();

     TreeMap<Integer, User> getFormateurRemunerationMaxSalaireTrie();

    List<Object> getFormateurRemunerationByDateTrie();

    void CertifactionStudents();


    List<Test>  SearchMultiple(String key);







    void ajouterApprenant(User apprenant);
    void ajouterEtAffecterFormationAFormateur(Test formation, Long idFormateur);
    Test getFile(Integer fileId) throws FileNotFoundException;


    void affecterApprenantFormationWithMax(Long idApprenant, Integer idFormation);

    void affecterApprenantFormation(Long idApprenant,Integer idFormation);




    Integer nbrCoursesParFormateur(Long idF,Date dateDebut, Date dateFin);

    Integer getNbrApprenantByFormation(String title);
    void getNbrApprenantByFormationn();

    Integer getNbrFormationByApprenant(Long idApp, Domain domain , Date dateDebut, Date dateFin);

    List<Object[]> getNbrApprenantByFormation();




    List<User> getApprenantByFormation(Integer idF);


    Integer getFormateurRemunerationByDate(Long idFormateur, Date dateDebut,Date dateFin);
    Integer getRevenueByFormation(Integer idFormation);



    void likeFormation(Integer idF);
    void dislikeFormation(Integer idF);



    void SearchHistorique(String keyword);






}
