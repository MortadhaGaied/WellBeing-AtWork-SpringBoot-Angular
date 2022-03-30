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

    @PutMapping("/updateTest/{id}")
    @ResponseBody
    public void updateTest(@RequestBody Test test,@PathVariable(name = "id") Integer idTest){
        iServiceTest.updateTest(test,idTest);
    }

    @GetMapping("/deleteTest/{id}")
    @ResponseBody
    public void deleteTest(@PathVariable(name = "id") Integer idTest){
        iServiceTest.deleteTest(idTest);
    }
    @GetMapping("/retrieveTest")
    @ResponseBody
    public List<Test> afficherTest(){

        return iServiceTest.afficherTest();
    }

    @GetMapping("/retrieveEmployee")
    @ResponseBody
    public List<User> afficherEmployee()
    {
        return iServiceTest.afficherEmployee();
    }

    @PostMapping(  "/AffecterEmployeeATest/{idEmployee}/{idTest}")
    @ResponseBody
    public void affecterApprenantFormation(@PathVariable(name = "idEmployee") Long idEmployee,@PathVariable(name = "idTest") Integer idTest)
    {
        iServiceTest.AffecterEmployeeATest(idEmployee, idTest);
    }

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
    @PostMapping("/addQuestionAndAsigntoQuiz/{idQuiz}")
    public void addQuestionAndAsigntoQuiz(@RequestBody QuestionCollaboration question, @PathVariable(name = "idQuiz")  Integer idQuiz)
    {
        iServicesQuiz.addQuestionAndAsigntoQuiz(question, idQuiz);
    }


    @GetMapping("/getQuizQuestionId/{id}")
    public List<QuestionCollaboration> getQuizQuestionId(@PathVariable("id") Integer idQ)
    {
        return iServicesQuiz.getQuizQuestion(idQ);
    }

    @GetMapping("/getQuestions")
    public List<QuestionCollaboration> getQuestions()
    {
        return iServicesQuiz.getQuestions();
    }

    @PostMapping("/SaveScore/{idU}/{idQ}")
    public Integer saveScore(@RequestBody Result result, @PathVariable(name = "idU") Long idUser, @PathVariable(name = "idQ") Integer idQuiz)
    {
        return   this.iServicesQuiz.saveScore(result,idUser,idQuiz);
    }

    @GetMapping("/MaxScoreInTest")
    @ResponseBody
    public Integer MaxScoreInTest()
    {
        return this.iServicesQuiz.MaxScoreInTest();
    }

    @PostMapping("/addLikes/{idTest}")
    void likeFormation(@PathVariable(name = "idTest") Integer idTest){
        iServiceTest.likeTest(idTest);
    }

    @PostMapping("/addDisLikes/{idTest}")
    void dislikeFormation(@PathVariable(name = "idTest") Integer idTest)
    {
        iServiceTest.dislikeTest(idTest);
    }

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

    @GetMapping("/getQuizByTest/{idTest}")
    @ResponseBody
    public List<QuizTheme> getQuizByTest(@PathVariable("idTest") Integer idTest)
    {
        return this.iServicesQuiz.getQuizByTest(idTest);
    }

    @GetMapping("/DeleteQuiz/{id}")
    @ResponseBody
    public void DeleteQuiz(@PathVariable("id") Integer idQ)
    {
        this.iServicesQuiz.DeleteQuiz(idQ);
    }

    @GetMapping("/getTopScore")
    @ResponseBody
    public List<Result> getTopScore()
    {
        return iServicesQuiz.getTopScore();
    }

    @GetMapping("/getScore/{id}")
    @ResponseBody
    public Integer getScore(@PathVariable("id") Long idU)
    {
        return iServicesQuiz.getScore(idU);
    }
}
