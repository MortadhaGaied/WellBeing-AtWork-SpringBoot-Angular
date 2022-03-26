package com.wellbeignatwork.backend.API;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.wellbeignatwork.backend.model.Reservation;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.stream.Stream;

@Service
public class PdfReservation {

            public static ByteArrayInputStream FormationPDFReport(List<Reservation> reservations) throws IOException {
            Document document=new Document();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                PdfWriter.getInstance(document,out);
                document.open();
                //add text to pdf file
                Font font= FontFactory.getFont(FontFactory.COURIER,12,BaseColor.LIGHT_GRAY);
                Paragraph para = new Paragraph("Formation List ",font);
                para.setAlignment(Element.ALIGN_CENTER);
                document.add(para);
                document.add(Chunk.NEWLINE);
                PdfPTable table=new PdfPTable(9);
                //make the columns
                Stream.of("IdOffer","Title","Descrption","StarDate","EndDate","NPlaces","promotion","pourcentage","localisation").forEach(headerTitle -> {
                    PdfPCell header = new PdfPCell();
                    Font headfont= FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                    header.setBackgroundColor(BaseColor.YELLOW);
                    header.setBorderWidth(12);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setBorderWidth(1);
                    header.setPhrase(new Phrase(headerTitle,headfont));
                    table.addCell(header);


                });

                for (Reservation reservation : reservations)  {

                    //id User
                    PdfPCell IdUser = new PdfPCell(new Phrase((reservation.getUserRes().getId())));
                    IdUser.setPaddingLeft(1);
                    IdUser.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    IdUser.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table.addCell(IdUser);

                    //id Offer
                    PdfPCell IdOffer = new PdfPCell(new Phrase((reservation.getOffersRes().getIdOffer())));
                    IdOffer.setPaddingLeft(1);
                    IdOffer.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    IdOffer.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table.addCell(IdOffer);

                    //id Reservation
                    PdfPCell IdReservation = new PdfPCell(new Phrase((reservation.getIdReservation())));
                    IdReservation.setPaddingLeft(1);
                    IdReservation.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    IdReservation.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table.addCell(IdReservation);
                    // StartDate
                    PdfPCell StartDate = new PdfPCell(new Phrase(reservation.getStartDateRes().toString()));
                    StartDate.setPaddingLeft(1);
                    StartDate.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    StartDate.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table.addCell(StartDate);

                    PdfPCell EndDate = new PdfPCell(new Phrase(reservation.getEndDateRes().toString()));
                    EndDate.setPaddingLeft(1);
                    EndDate.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    EndDate.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table.addCell(EndDate);

                    PdfPCell NmPalce = new PdfPCell(new Phrase(String.valueOf(reservation.getNmPalce())));
                    NmPalce.setPaddingLeft(1);
                    NmPalce.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    NmPalce.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table.addCell(NmPalce);

                }
                // Creating an ImageData object
                String url = "./src/main/resources/Image/QRCode.png";
                Image image = Image.getInstance(url);
                document.add(image);


                document.add(table);
                document.close();

            } catch ( DocumentException | MalformedURLException e) {
                e.printStackTrace();
            }
            return new ByteArrayInputStream(out.toByteArray());
        }

    }
