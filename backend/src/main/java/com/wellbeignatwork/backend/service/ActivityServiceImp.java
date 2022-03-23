package com.wellbeignatwork.backend.service;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.wellbeignatwork.backend.entity.*;

import com.wellbeignatwork.backend.entity.Event;
import com.wellbeignatwork.backend.repository.EventRepository;
import com.wellbeignatwork.backend.repository.SubscriptionRepository;
import com.wellbeignatwork.backend.repository.UserRepo;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

import static javax.mail.Transport.send;

@RestController
public class ActivityServiceImp implements IActivityService{

    EventRepository eventRepository;
    UserRepo userRepo;
    SubscriptionRepository subscriptionRepository;
    private JavaMailSender javaMailSender;

    @Autowired
    public ActivityServiceImp(EventRepository eventRepository,UserRepo userRepo,SubscriptionRepository subscriptionRepository,JavaMailSender javaMailSender){
        this.eventRepository=eventRepository;
        this.userRepo=userRepo;
        this.subscriptionRepository = subscriptionRepository;
        this.javaMailSender = javaMailSender;
    }


    @Override
    public void addEvent(Event e) {
        eventRepository.save(e);
    }

    @Override
    public void deleteEvent(Event e) {
        eventRepository.delete(e);
    }

    @Override
    public Event updateEvent(Event e) {
        return eventRepository.save(e);
    }

    @Override
    public List<Event> getAllEvents() {
        return (List<Event>) eventRepository.findAll();    }

    @Override
    public void assignUserToEvent(Long idUser, Long idEvent) {

        User user = userRepo.findById(idUser).orElse(null);
        Event event = eventRepository.findById(idEvent).orElse(null);
        if  (event.getUsers().size()>=event.getNbrMaxParticipant()){
            System.out.println("tu ne peux pas affecter");
        }
        else {
        user.getEvents().add(event);
        event.setRevenue(event.getRevenue()+event.getFrais());
        userRepo.save(user);
    }
    }
    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Event Name", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Event Locatisation", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Fees", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("User Name", font));
        table.addCell(cell);


    }

    private void writeTableData(PdfPTable table, Long idEvent, Long idUser) {
        User user = userRepo.findById(idUser).orElse(null);
        Event event = eventRepository.findById(idEvent).orElse(null);
            table.addCell(event.getEventName());
            table.addCell(event.getEventLocalisation());
            table.addCell(String.valueOf(event.getFrais()));
            table.addCell(user.getFirstName()+ " " +user.getLastName() );

    }

    public void export(HttpServletResponse response, Long idEvent,
                       Long idUser, String text,
                       int width, int height, String filePath)
            throws DocumentException, IOException, WriterException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("List of Events" +
                "", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 1.5f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table,idEvent,idUser);

        document.add(table);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        System.out.println(path);
       Image image = Image.getInstance(path.toString());
       document.add(image);
        document.close();

    }

    public static byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        return pngData;
    }




    public String getlatitudelieu(String adresse) {
        String map = adresse;
        String[] arrOfStr = map.split(",");
        String Latitude = arrOfStr[0];

        return Latitude;
    }

    public String getlongitudelieu(String adresse) {
        String map = adresse;
        String[] arrOfStr = map.split(",");
        String Longitude = arrOfStr[1];

        return Longitude;
    }
    public double convertRad(double input) {
        return (Math.PI * input) / 180;
    }
    @Override
    public double calculDistance (String a , String b ){
        double lata =  convertRad( Double.parseDouble(getlatitudelieu(a)));
        double latb =  convertRad(Double.parseDouble(getlatitudelieu(b)));
        double longa = convertRad(Double.parseDouble(getlongitudelieu(a)));
        double longb = convertRad(Double.parseDouble(getlongitudelieu(b)));
        double rayon = 6378137;
        double distance = Math.acos((Math.sin(lata) * Math.sin(latb))
                + Math.cos(lata) * Math.cos(latb) * (Math.cos(longb - longa)));
        return distance*rayon/1000;
    }
    public List<Event> sortedByDistance (){
        Map<Event, Double> envdis=new HashMap<>();
        for(Event e:eventRepository.findAll()){
            envdis.put(e, calculDistance("36.718854,9.199704",e.getEventLocalisation()));
        }
        List<Map.Entry<Event, Double>> list = new ArrayList<>(envdis.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<Event, Double> result = new LinkedHashMap<>();
        for (Map.Entry<Event, Double> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        List<Event> dis =new ArrayList<>();
        for (Map.Entry<Event, Double> entry : list) {
            dis.add(entry.getKey());
        }
        return dis;
    }

    @Override
    public List<Event> sortedByFrais() {
        List<Event> events = new ArrayList<>();
        eventRepository.findAll().forEach(events::add);

        return events.stream().sorted(Comparator.comparing(Event::getFrais))
                .collect(Collectors.toList());
    }

  //  @Override
  //  public List<ITagsCount> tags() {
//        return eventRepository.tags();
   // }

    @Override
    public double getRevenueByEvent(Long idEvent ) {

      Event event = eventRepository.findById(idEvent).orElse(null);

        return event.getRevenue() ;
    }

    @Override
    public Set<Event> showEventsByUser(Long idUser) {
        Set<Event> result=new HashSet<>();
        User u = userRepo.findById(idUser).orElse(null);
        for(Event event : eventRepository.findAll()){
            if(compareTags(u.getUserTags(),event.getEventTags())){
               result.add(event);
            }
        }
        return result;
    }

    @Override
    public void reductionEvent(Long idEvent, Long idUser, int familyNumber) {
        double x=0;
        User u= userRepo.findById(idUser).orElse(null);
        Event event=eventRepository.findById(idEvent).orElse(null);
        if (event.getFrais()>20){
            x=(((event.getFrais()*8)/10)*(familyNumber+1));

            u.getEvents().add(event);
            event.setRevenue(event.getRevenue()+x);
            userRepo.save(u);
        }
    }
    @Transactional
    @Scheduled(cron="*/30 * * * * *")
    public String reminderEvent (){
        String a="Mail Send Successfully";
        Calendar cal= Calendar.getInstance();
        cal.add(Calendar.DATE,+1);
        Date start =cal.getTime();
        List<Event> events=eventRepository.reminder(new Date(),start);
        if (!events.isEmpty()){
            for(Event e :events){
                for(User u:e.getUsers()){
                    System.out.println("vous avez un evenement : "+u.getFirstName());
                    SimpleMailMessage message = new SimpleMailMessage();
                    message.setFrom("wellbeingatworkevent@gmail.com");
                    message.setTo("nourhene.maaouia@esprit.tn");
                    message.setSubject("Test Subject");
                    message.setText("Test Body ");

                    javaMailSender.send(message);

                }
            }
        }
        return a ;
    }

    @Override
    public Event popularEvent() {
        Event eventMax=eventRepository.findAll().iterator().next();
        System.out.println( eventMax);
      //  Comparator<Event> particComparator = (e1,e2) -> e1.getUsers().size().compareTo(e2.getUsers().size());
      //  List<Event> result=getAllEvents().stream().sorted(Comparator.comparing(event -> ))
        for (Event event : eventRepository.findAll()){
                if (event.getUsers().size()>eventMax.getUsers().size()){
                    System.out.println("slt");
                    eventMax=event;
                }
        }
        return eventMax;
    }

    @Override
    public void assignPointToUser(Long idUser, Long idEvent) {
        User u= userRepo.findById(idUser).orElse(null);
        Event event= eventRepository.findById(idEvent).orElse(null);
        if (event.getEventTags().contains(Tag.SPORT)){
            u.setPoints(u.getPoints()+3);
        }
    userRepo.save(u);

    }

    @Override
    public Set<Event> filtreByDepartement(Departement departement) {
        Event e1 = new Event();
        int i=0;
        int size= IterableUtils.size(eventRepository.findAll());
        List<Integer> list=new ArrayList<Integer>(Collections.nCopies(size,0));
       for(Event e:eventRepository.findAll()){
           for(User u:e.getUsers()){
               if(u.getDepartement().equals(departement)){
                   int a=list.get(i);
                   list.set(i,a+=1);
               }
           }
           i++;
       }

        int x=0;
       Iterator<Event> iter = eventRepository.findAll().iterator();
        while (x<=list.indexOf(Collections.max(list))){
            e1= iter.next();
            x++;
        }
        System.out.println(e1);
       return null;
    }

    public boolean compareTags(Set<Tag> eventTags, Set<Tag> userTags) {
        return eventTags.containsAll(userTags);
    }
    /*
     @Override
    public Integer getRevenueByEvent(Long idEvent , Date startDate , Date endDate) {
      int revenue=0;
      Event event = eventRepository.findById(idEvent).orElse(null);
     if(event.getEventDate().after(startDate) && event.getEventDate().before(endDate)){
          revenue = (event.getFrais()*getNbrOfParticipant(idEvent));
     }
        return revenue ;
    }
     */

    @Override
    public int getNbrOfParticipant(Long idEvenet) {
        int nb =0;
        for(Event event : eventRepository.findAll()){
            event.getUsers().size();
            nb ++;
        }
        return nb;    }
   /*
    @Override
    public void addEventToFavory(Long idUser, Event e) {
        User u = userRepo.findById(idUser).orElse(null);
        e.getUsersFavorites().add(idUser);
        eventRepository.save(e);

    }*/

    @Override
    public void addUser(User u) {
        userRepo.save(u);
    }

    @Override
    public void addSubscription(Subscription s) {
        subscriptionRepository.save(s);
    }

    @Override
    public void deleteSubscription(Subscription s) {
        subscriptionRepository.delete(s);
    }

    @Override
    public void updateSubscription(Subscription s) {
        subscriptionRepository.save(s);
    }

    @Override
    public List<Subscription> getAllSubscriptions() {
        return (List<Subscription>) subscriptionRepository.findAll();    }

    @Override
    public void assignUserToSubscription(Long idUser, Long idSubscription) {
        User user = userRepo.findById(idUser).orElse(null);
        Subscription subscription = subscriptionRepository.findById(idSubscription).orElse(null);
        subscription.setUser(user);
        subscriptionRepository.save(subscription);
    }


}
