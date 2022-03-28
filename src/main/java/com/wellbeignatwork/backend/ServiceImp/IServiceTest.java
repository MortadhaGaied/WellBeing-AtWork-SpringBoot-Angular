package com.wellbeignatwork.backend.ServiceImp;



import com.wellbeignatwork.backend.model.Domain;
import com.wellbeignatwork.backend.model.Test;
import com.wellbeignatwork.backend.model.User;

import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

public interface IServiceTest {

    void addTest(Test test);
    void updateTest(Test test, Integer idTest);
    void deleteTest(Integer idTest);
    List<Test> afficherTest();
    List<User> afficherTest();
    List<User> afficherEmployee();

    void CertifactionStudents();


    List<Test>  SearchMultiple(String key);

    void affecterEmployeeWithMaxTest(Long idEmployee, Integer idTest);

    Integer getNbrEmployeeByTest(String title);
    public void ajouterEtAffecterEmployeeATest(Test test, Long idTest);

    Integer getNbrTestByEmployee(Long idApp, Domain domain , Date dateDebut, Date dateFin);

    List<User> getEmployeeByTest(Integer idF);

    void likeTest(Integer idF);
    void dislikeTest(Integer idF);
    void SearchHistorique(String keyword);

}
