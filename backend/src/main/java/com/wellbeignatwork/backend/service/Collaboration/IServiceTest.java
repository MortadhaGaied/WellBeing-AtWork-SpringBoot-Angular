package com.wellbeignatwork.backend.service.Collaboration;



import com.wellbeignatwork.backend.entity.Collaboration.Domain;
import com.wellbeignatwork.backend.entity.Collaboration.Test;
import com.wellbeignatwork.backend.entity.User.User;
import java.util.Date;
import java.util.List;


public interface IServiceTest {

    void addTest(Test test);
    void updateTest(Test test, Integer idTest);
    void deleteTest(Integer idTest);

    List<Test> afficherTest();

    List<User> afficherEmployee();

    void CertifactionEmployee();


    void affecterEmployeeWithMaxTest(Long idEmployee, Integer idTest);

    void AffecterEmployeeATest(Long idUser, Integer idTest);

    Integer getNbrEmployeeByTestt(String title);

    void likeTest(Integer idF);
    void dislikeTest(Integer idF);

}
