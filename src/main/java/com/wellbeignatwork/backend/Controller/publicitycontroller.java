package com.wellbeignatwork.backend.Controller;


import com.sun.mail.iap.Response;
import com.wellbeignatwork.backend.ServiceImp.IPublicityService;
import com.wellbeignatwork.backend.model.Publicity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;


@RestController
@RequestMapping("/Publicity")
public class publicitycontroller {
    @Autowired
    IPublicityService publicityService;



    //http://localhost:8085/Offer/datePublicity/idPublicity/starDate/finDate
    @GetMapping("/datePublicity/{idPublicity}/{starDateOf}/{finDateOf}")
    @ResponseBody
    public boolean dateOffer(@PathVariable long idPublicity, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date starDateOf, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date finDateOf){
        return   publicityService.dateOffer(idPublicity,starDateOf,finDateOf);
    }


    @Value("${file.upload-dir}")
    String FILE_DIR;

    //http://localhost:8080/Publicity/uploadImage
    @PostMapping("/uploadImage")
    @ResponseBody
    public ResponseEntity<Object> uploadImage(@RequestParam MultipartFile imageFile, Model model , Publicity publicity) throws IOException {
    File myFile =new File(FILE_DIR + imageFile.getOriginalFilename());
    myFile.createNewFile();
    FileOutputStream fos = new FileOutputStream(myFile);
    fos.write(imageFile.getBytes());
    fos.close();
    return  new ResponseEntity<Object>("the File Uploaded Successfully", HttpStatus.OK);
    }

    //http://localhost:8080/Publicity/upload-banner
    @PutMapping("/upload-banner")
    @PreAuthorize("hasRole('ADMIN')")
    public Response uploadBanner(@RequestParam MultipartFile img, @RequestParam Long eventId) {
        try {
            publicityService.uploadPubBanner(img, eventId);
            return new Response("Event banner has been uploaded successfully!", true);
        } catch (Exception e) {
            return new Response(e.getMessage(), false);
        }
    }

    @DeleteMapping("/delete-image/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public Response deleteImage(@PathVariable String name) {
        try {
            publicityService.deleteImage(name);
            return new Response("Image has been deleted successfully!", true);
        } catch (Exception e) {
            return new Response(e.getMessage(), false);
        }
    }

}

