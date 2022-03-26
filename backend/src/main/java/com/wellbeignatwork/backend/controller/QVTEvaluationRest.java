package com.wellbeignatwork.backend.controller;

import com.wellbeignatwork.backend.entity.Answer;
import com.wellbeignatwork.backend.entity.Survey;
import com.wellbeignatwork.backend.entity.User;
import com.wellbeignatwork.backend.service.IntQVTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/QVT")
public class QVTEvaluationRest {

  @Autowired
    private IntQVTService MyQVTService;

    private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/QrCode/QRCode.png";

    @PostMapping("/addUser")
    public User adduser(@RequestBody User u)
    { return MyQVTService.addUser(u); }

    @GetMapping("/Survey")
    public List<Survey> retrieveAllSurveys()
    { return MyQVTService.retrieveAllSurveys(); }

    @PostMapping("/PutYourAnswerAndGetYourAdvice")
    public String UserAnswer(@RequestBody List<Answer>  answer)
    { return MyQVTService.UserAnswer(answer); }

     @GetMapping("/Stastic")
     public String nbreSentiment()
    { return MyQVTService.nbreSentiment();}



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
