package com.wellbeignatwork.backend.Controller;

import com.google.zxing.WriterException;
import com.itextpdf.text.DocumentException;
import com.wellbeignatwork.backend.API.OfferPDFExporter;
import com.wellbeignatwork.backend.API.QRCodeGenerator;
import com.wellbeignatwork.backend.API.ReservationPDFExporter;
import com.wellbeignatwork.backend.ServiceImp.IOfferService;
import com.wellbeignatwork.backend.ServiceImp.ISendEmailService;
import com.wellbeignatwork.backend.model.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/Offer")
public class OfferController {

    @Autowired
    IOfferService offerService;

    @Autowired
    private ISendEmailService iSendEmailService;


    private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/Image/QRCode.png";


    //http://localhost:8080/addOffer
    @PostMapping("/addOffer/{idCollaboration}")
    @ResponseBody
    public void addOffer(@RequestBody Offer o, @PathVariable long idCollaboration, HttpServletResponse response) throws DocumentException, IOException{
        byte[] image = new byte[0];
        try {

            // Generate and Return Qr Code in Byte Array
            image = QRCodeGenerator.getQRCodeImage(o.getTitle(),250,250);

            QRCodeGenerator.generateQRCodeImage(o.getLocalisation(),250,250,QR_CODE_IMAGE_PATH);

            // Generate and Save Qr Code Image in static/image folder

        } catch (IOException e) {

            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        }
        // Convert Byte Array into Base64 Encode String
        String qrcode = Base64.getEncoder().encodeToString(image);
        // log.info(qrcode);

        offerService.addOffer(o,idCollaboration);
    }

    //http://localhost:8080/deleteOffer/id
    @DeleteMapping("/deleteOffer/{id}")
    @ResponseBody
    public void deleteOffer(@PathVariable Long id){
        offerService.deleteOffer(id);
    }

    //http://localhost:8080/Offer/updateOffer
    @PutMapping("/Offer/updateOffer")
    @ResponseBody
    public Offer updateOffer(@RequestBody Offer o){
        return offerService.updateOffer(o);
    }

    //http://localhost:8080/Offer/retrieveAllOffers
    @GetMapping("/Offer/retrieveAllOffers")
    @ResponseBody
    public List<Offer> retrieveAllOffers(){
        return offerService.retrieveAllOffers();
    }

    //http://localhost:8080/Offer/retrieveAllOffers/id
    @GetMapping("/Offer/retrieveOffer/{id}")
    @ResponseBody
    public Offer retrieveOffer(@PathVariable Long id){
        return offerService.retrieveOffer(id);
    }

    @PostMapping("/calculPromotion/{idOffer}")
    @ResponseBody
    public float calculPromotion (@PathVariable long idOffer){
       return offerService.calculProm(idOffer);
    }

    @GetMapping("/exportOffer/pdf")
    public void exportToPDF(HttpServletResponse response) throws IOException, com.lowagie.text.DocumentException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<Offer> listOffers = offerService.listAll();

        OfferPDFExporter exporter = new OfferPDFExporter(listOffers);
        exporter.export(response);

    }
}
