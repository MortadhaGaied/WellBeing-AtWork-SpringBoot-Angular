package com.wellbeignatwork.backend.Controller;

import com.wellbeignatwork.backend.ServiceImp.IServiceTest;
import com.wellbeignatwork.backend.ServiceImp.IServicesQuiz;
import com.wellbeignatwork.backend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/Quiz")
public class QuizController {

    @Autowired
    private IServicesQuiz iServicesQuiz;
    @Autowired
    private IServiceTest iServiceTest;

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

    @PostMapping("/addQuiz/{idTest}")
    public void addQuiz(@RequestBody QuizTheme quiz, @PathVariable(name = "idTest") Integer idTest)
    {
        iServicesQuiz.addQuiz(quiz,idTest);
    }
    @PostMapping("/addQuestionAndAsigntoQuiz/{idQuiz}")
    public void addQuestionAndAsigntoQuiz(@RequestBody Question question, @PathVariable(name = "idQuiz")  Integer idQuiz)
    {
        iServicesQuiz.addQuestionAndAsigntoQuiz(question, idQuiz);
    }


    @GetMapping("/getQuizQuestionId/{id}")
    public List<Question> getQuizQuestionId(@PathVariable("id") Integer idQ)
    {
        return iServicesQuiz.getQuizQuestion(idQ);
    }

    @GetMapping("/getQuestions")
    public List<Question> getQuestions()
    {
        return iServicesQuiz.getQuestions();
    }

    @PostMapping("/SaveScore/{idU}/{idQ}")
    public Integer saveScore(@RequestBody Result result,@PathVariable(name = "idU") Long idUser,@PathVariable(name = "idQ") Integer idQuiz)
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

    @PostMapping("/addDisLikes/{idF}")
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

    @GetMapping("/getQuizByFormation/{idTest}")
    @ResponseBody
    public List<Question> getQuizByFormation(@PathVariable("idTest") Integer idTest)
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
