package com.wellbeignatwork.backend.Controller;



import com.google.zxing.WriterException;
import com.itextpdf.text.DocumentException;
import com.wellbeignatwork.backend.API.PdfAllOffre;
import com.wellbeignatwork.backend.API.QRCodeGenerator;
import com.wellbeignatwork.backend.API.ReservationPDFExporter;
import com.wellbeignatwork.backend.ServiceImp.IReservationService;
import com.wellbeignatwork.backend.ServiceImp.ISendEmailService;
import com.wellbeignatwork.backend.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@RequestMapping("/Reservation")
@RestController
public class ReservationController {

    @Autowired
    IReservationService reservationService;
    @Autowired
    private PdfAllOffre pdf;
        @Autowired
    private ISendEmailService iServiceEmail;

    private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/Image/QRCode.png";


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

    }
    @PostMapping("/addResevation/{idOffer}/{idUser}")
    @ResponseBody
    public void addOffer(@RequestBody Reservation r, @PathVariable long idUser, @PathVariable long idOffer, HttpServletResponse response) throws DocumentException, IOException, com.lowagie.text.DocumentException {

        generatePDF(response);

        byte[] image = new byte[0];
        try {

            // Generate and Return Qr Code in Byte Array
            image = QRCodeGenerator.getQRCodeImage(r.getUserRes().getEmail(),250,250);

            QRCodeGenerator.generateQRCodeImage(r.getUserRes().getEmail(),250,250,QR_CODE_IMAGE_PATH);

            // Generate and Save Qr Code Image in static/image folder

        } catch (WriterException | IOException e) {

            e.printStackTrace();
        }
        // Convert Byte Array into Base64 Encode String
        String qrcode = Base64.getEncoder().encodeToString(image);
        // log.info(qrcode);


        //  generatePDF(response,formateur.getEmail(),formateur.getEmail(),qrcode);
        iServiceEmail.sendSimpleEmail("mahdijr2015@gmail.com"," add Formateur " ," add succesful ... ");
        reservationService.reservation(idUser,idOffer,r);
    }

    //http://localhost:8080/Reservation/calculTotal/1/1
    @GetMapping("/calculTotal/{idOffer}/{idReservation}")
    @ResponseBody
    public float calculTotal( @PathVariable long idReservation, @PathVariable long idOffer){
        return reservationService.prixTotale(idReservation,idOffer);
    }

    @GetMapping("/exportReservation/pdf")
    public void exportToPDF(HttpServletResponse response) throws IOException, com.lowagie.text.DocumentException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<Reservation> listReservations = reservationService.listAll();

        ReservationPDFExporter exporter = new ReservationPDFExporter(listReservations);
        exporter.export(response);

    }
}
