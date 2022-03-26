package com.wellbeignatwork.backend.Controller;



import com.itextpdf.text.DocumentException;
import com.wellbeignatwork.backend.API.ApiService.PDFGeneratorServiceOffre;
import com.wellbeignatwork.backend.API.PdfAllOffre;
import com.wellbeignatwork.backend.ServiceImp.IReservationService;
import com.wellbeignatwork.backend.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RequestMapping("/Reservation")
@RestController
public class ReservationController {

    @Autowired
    IReservationService reservationService;
    @Autowired
    private PdfAllOffre pdf;

    private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/Image/QRCode.png";

    private final PDFGeneratorServiceOffre pdfGeneratorService;

    public ReservationController(PDFGeneratorServiceOffre pdfGeneratorService) {
        this.pdfGeneratorService = pdfGeneratorService;
    }

    //Genrate Pdf
    //http://localhost:8085/Reservation/addResevation/1/1
    @GetMapping("/pdf/generate")
    public void generatePDF(HttpServletResponse response) throws IOException, DocumentException, com.lowagie.text.DocumentException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        this.pdfGeneratorService.export(response);
    }
    @PostMapping("/addResevation/{idOffer}/{idUser}")
    @ResponseBody
    public void addOffer(@RequestBody Reservation r, @PathVariable long idUser, @PathVariable long idOffer, HttpServletResponse response) throws DocumentException, IOException, com.lowagie.text.DocumentException {
        reservationService.reservation(idUser,idOffer,r);
        generatePDF(response);
    }

    //http://localhost:8085/Reservation/calculTotal/1/1
    @GetMapping("/calculTotal/{idOffer}/{idReservation}")
    @ResponseBody
    public float calculTotal( @PathVariable long idReservation, @PathVariable long idOffer){
        return reservationService.prixTotale(idReservation,idOffer);
    }


}
