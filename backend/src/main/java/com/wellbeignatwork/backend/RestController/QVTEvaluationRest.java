package com.wellbeignatwork.backend.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.pidev.spring.version0backend.Entity.Answer;
import tn.pidev.spring.version0backend.Entity.Survey;
import tn.pidev.spring.version0backend.Entity.User;
import tn.pidev.spring.version0backend.Service.IntQVTService;

import java.util.List;

@RestController
@RequestMapping("/QVT")
public class QVTEvaluationRest {

  @Autowired
    private IntQVTService MyQVTService;

    private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/QrCode/QRCode.png";

    @PostMapping("/addUser")
    public User adduser( @RequestBody User u)
    {
        return MyQVTService.addUser(u);

    }

    @GetMapping("/Survey")
    public List<Survey> retrieveAllSurveys()
    {
        return MyQVTService.retrieveAllSurveys();

    }

    @PostMapping("/PutYourAnswerAndGetYourAdvice")
    public void UserAnswer(@RequestBody List<Answer>  answer)
    {
        MyQVTService.UserAnswer(answer);
    }




/*
    @GetMapping(value = "generateRandomCODEPROMO")
     public void RandomCode ()
    {
        MyQVTService.generateRandomCODEPROMO();
    }

    @GetMapping(value = "/genrateAndDownloadQRCode/{width}/{height}")
    public void download(

            @PathVariable("width") Integer width,
            @PathVariable("height") Integer height)
            throws Exception {
        QRCodeGenerator.generateQRCodeImage(MyQVTService.generateRandomCODEPROMO() ,width, height, QR_CODE_IMAGE_PATH);
    }

    @GetMapping(value = "/genrateQRCode/{width}/{height}")
    public ResponseEntity<byte[]> generateQRCode(

            @PathVariable("width") Integer width,
            @PathVariable("height") Integer height)
            throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(QRCodeGenerator.getQRCodeImage(MyQVTService.generateRandomCODEPROMO(), width, height));
    }


 */

















}
