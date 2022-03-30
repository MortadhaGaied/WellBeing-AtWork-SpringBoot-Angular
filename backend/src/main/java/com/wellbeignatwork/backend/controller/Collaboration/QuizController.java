package com.wellbeignatwork.backend.controller.Collaboration;


import com.wellbeignatwork.backend.entity.Collaboration.QuestionCollaboration;
import com.wellbeignatwork.backend.entity.Collaboration.QuizTheme;
import com.wellbeignatwork.backend.entity.Collaboration.Result;
import com.wellbeignatwork.backend.entity.Collaboration.Test;
import com.wellbeignatwork.backend.entity.User.User;
import com.wellbeignatwork.backend.service.Collaboration.IServiceTest;
import com.wellbeignatwork.backend.service.Collaboration.IServicesQuiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Quiz")
public class QuizController {

    @Autowired
    private IServicesQuiz iServicesQuiz;
    @Autowired
    private IServiceTest iServiceTest;

    //http://localhost:8081/Wellbeignatwork/Quiz/ajouterTest
    @RequestMapping(value = {"/ajouterTest"}, method = RequestMethod.POST)
    @ResponseBody
    public void addTestn(@RequestBody Test test){
        iServiceTest.addTest(test);
    }

    //http://localhost:8081/Wellbeignatwork/Quiz/updateTest/1
    @PutMapping("/updateTest/{id}")
    @ResponseBody
    public void updateTest(@RequestBody Test test,@PathVariable(name = "id") Integer idTest){
        iServiceTest.updateTest(test,idTest);
    }

    //http://localhost:8081/Wellbeignatwork/Quiz/deleteTest/1
    @DeleteMapping("/deleteTest/{id}")
    @ResponseBody
    public void deleteTest(@PathVariable(name = "id") Integer idTest){
        iServiceTest.deleteTest(idTest);
    }
    @GetMapping("/retrieveTest")
    @ResponseBody
    public List<Test> afficherTest(){

        return iServiceTest.afficherTest();
    }

    //http://localhost:8081/Wellbeignatwork/Quiz/retrieveEmployee
    @GetMapping("/retrieveEmployee")
    @ResponseBody
    public List<User> afficherEmployee()
    {
        return iServiceTest.afficherEmployee();
    }

    //http://localhost:8081/Wellbeignatwork/Quiz/AffecterEmployeeATest/1/1
    @PostMapping(  "/AffecterEmployeeATest/{idEmployee}/{idTest}")
    @ResponseBody
    public void affecterApprenantFormation(@PathVariable(name = "idEmployee") Long idEmployee,@PathVariable(name = "idTest") Integer idTest)
    {
        iServiceTest.AffecterEmployeeATest(idEmployee, idTest);
    }

    //http://localhost:8081/Wellbeignatwork/Quiz/affecterEmployeeWithMaxTest/1/1
    @RequestMapping(value = {"/affecterEmployeeWithMaxTest/{idEmployee}/{idTest}"}, method = RequestMethod.POST)
    @ResponseBody
    public void affecterEmployeeWithMaxTest(@PathVariable(name = "idEmployee") Long idEmployee,@PathVariable(name = "idTest") Integer idTest)
    {
        iServiceTest.affecterEmployeeWithMaxTest(idEmployee, idTest);
    }

   // http://localhost:8081/Wellbeignatwork/Quiz/addQuiz/1
    @PostMapping("/addQuiz/{idTest}")
    public void addQuiz(@RequestBody QuizTheme quiz, @PathVariable(name = "idTest") Integer idTest)
    {
        iServicesQuiz.addQuiz(quiz,idTest);
    }
    // http://localhost:8081/Wellbeignatwork/Quiz/addQuestionAndAsigntoQuiz/1
    @PostMapping("/addQuestionAndAsigntoQuiz/{idQuiz}")
    public void addQuestionAndAsigntoQuiz(@RequestBody QuestionCollaboration question, @PathVariable(name = "idQuiz")  Integer idQuiz)
    {
        iServicesQuiz.addQuestionAndAsigntoQuiz(question, idQuiz);
    }


    //http://localhost:8081/Wellbeignatwork/Quiz/SaveScore/1/1
    @PostMapping("/SaveScore/{idU}/{idQ}")
    public Integer saveScore(@RequestBody Result result, @PathVariable(name = "idU") Long idUser, @PathVariable(name = "idQ") Integer IdQuizTheme)
    {
        return   this.iServicesQuiz.saveScore(result,idUser,IdQuizTheme);
    }

    //http://localhost:8081/Wellbeignatwork/Quiz/MaxScoreInTest
    @GetMapping("/MaxScoreInTest")
    @ResponseBody
    public Integer MaxScoreInTest()
    {
        return this.iServicesQuiz.MaxScoreInTest();
    }

    //http://localhost:8081/Wellbeignatwork/Quiz/addLikes/1
    @PostMapping("/addLikes/{idTest}")
    void likeFormation(@PathVariable(name = "idTest") Integer idTest){
        iServiceTest.likeTest(idTest);
    }

    //http://localhost:8081/Wellbeignatwork/Quiz/addDisLikes/1
    @PostMapping("/addDisLikes/{idTest}")
    void dislikeFormation(@PathVariable(name = "idTest") Integer idTest)
    {
        iServiceTest.dislikeTest(idTest);
    }

    //http://localhost:8081/Wellbeignatwork/Quiz/getEmployeeWithScoreQuiz/1
    @GetMapping("/getEmployeeWithScoreQuiz/{idTest}")
    @ResponseBody
    List<Object> getEmployeeWithScoreQuiz(@PathVariable("idTest") Integer idTest)
    {
        return this.iServicesQuiz.getEmployeeWithScoreQuiz(idTest);
    }


    @GetMapping("/EmployeewithMaxScoreQuiz/{idTest}")
    @ResponseBody
    public User EmployeewithMaxScoreQuiz(@PathVariable("idTest") Integer idTest)
    {
        return iServicesQuiz.EmployeewithMaxScoreQuiz(idTest);
    }

    //http://localhost:8081/Wellbeignatwork/Quiz/getQuizByTest/1
    @GetMapping("/getQuizByTest/{idTest}")
    @ResponseBody
    public List<QuizTheme> getQuizByTest(@PathVariable("idTest") Integer idTest)
    {
        return this.iServicesQuiz.getQuizByTest(idTest);
    }

    //http://localhost:8081/Wellbeignatwork/Quiz/DeleteQuiz/1
    @GetMapping("/DeleteQuiz/{id}")
    @ResponseBody
    public void DeleteQuiz(@PathVariable("id") Integer idQ)
    {
        this.iServicesQuiz.DeleteQuiz(idQ);
    }

    //http://localhost:8081/Wellbeignatwork/Quiz/getTopScore
    @GetMapping("/getTopScore")
    @ResponseBody
    public List<Result> getTopScore()
    {
        return iServicesQuiz.getTopScore();
    }

    //http://localhost:8081/Wellbeignatwork/Quiz/getScore/1
    @GetMapping("/getScore/{id}")
    @ResponseBody
    public Integer getScore(@PathVariable("id") Long idU)
    {
        return iServicesQuiz.getScore(idU);
    }
}
