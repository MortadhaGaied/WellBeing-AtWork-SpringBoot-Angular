package com.wellbeignatwork.backend.API.ApiController;

import com.wellbeignatwork.backend.API.PdfReservation;
import com.wellbeignatwork.backend.ServiceImp.IReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class PdfControllerReservation {

    @Autowired
    private PdfReservation export;

    @Autowired
    private IReservationService reservation;

    @GetMapping("/exportPDF")
    @ResponseBody
    public ResponseEntity<InputStreamResource> exportPDF()throws IOException {
        ByteArrayInputStream bais = export.FormationPDFReport(reservation.findAll());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition","Inline ;filename=formation.pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(bais));
    }
}
