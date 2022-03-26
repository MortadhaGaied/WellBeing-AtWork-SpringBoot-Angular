package com.wellbeignatwork.backend.API.ApiController;


import com.wellbeignatwork.backend.API.PdfAllOffre;
import com.wellbeignatwork.backend.ServiceImp.IOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class PdfController {

    @Autowired
    private PdfAllOffre export;

    @Autowired
    private IOfferService iOfferService;

    @GetMapping("/exportPDF")
    @ResponseBody
    public ResponseEntity<InputStreamResource> exportPDF() throws IOException {
        ByteArrayInputStream bais = export.FormationPDFReport(iOfferService.retrieveAllOffers());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition","Inline ;filename=formation.pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(bais));
    }
}
