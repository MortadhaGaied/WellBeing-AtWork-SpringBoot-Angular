package com.wellbeignatwork.backend.Controller;

import com.wellbeignatwork.backend.ServiceImp.IServiceTest;
import com.wellbeignatwork.backend.ServiceImp.IServicesQuiz;
import com.wellbeignatwork.backend.model.Domain;
import com.wellbeignatwork.backend.model.Test;
import com.wellbeignatwork.backend.model.User;
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

}
