package com.wellbeignatwork.backend.ServiceImp;



import com.wellbeignatwork.backend.model.Domain;
import com.wellbeignatwork.backend.model.Test;
import com.wellbeignatwork.backend.model.User;
import java.util.Date;
import java.util.List;


public interface IServiceTest {

    void addTest(Test test);
    void updateTest(Test test, Integer idTest);
    void deleteTest(Integer idTest);

    List<Test> afficherTest();

    List<User> afficherEmployee();

    void CertifactionEmployee();


    List<Test>  SearchMultiple(String key);

    void affecterEmployeeWithMaxTest(Long idEmployee, Integer idTest);

    Integer getNbrEmployeeByTest(String title);

    Integer getNbrTestByEmployee(Long idApp, Domain domain , Date dateDebut, Date dateFin);

    List<User> getEmployeeByTest(Integer idF);

    void AffecterEmployeeATest(Long idUser, Integer idTest);

    Integer getNbrEmployeeByTestt(String title);

    void likeTest(Integer idF);
    void dislikeTest(Integer idF);
    void SearchHistorique(String keyword);

}
